/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.AnnotationNode;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.objectweb.asm.tree.FieldNode;
/*     */ import org.objectweb.asm.tree.InsnNode;
/*     */ import org.objectweb.asm.tree.MethodInsnNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.gen.Accessor;
/*     */ import org.spongepowered.asm.mixin.gen.Invoker;
/*     */ import org.spongepowered.asm.mixin.transformer.meta.MixinProxy;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MixinPostProcessor
/*     */   implements MixinConfig.IListener
/*     */ {
/*     */   private final String sessionId;
/*  67 */   private final Set<String> syntheticInnerClasses = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private final Map<String, MixinInfo> accessorMixins = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private final Set<String> loadable = new HashSet<>();
/*     */   
/*     */   MixinPostProcessor(String sessionId) {
/*  80 */     this.sessionId = sessionId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onInit(MixinInfo mixin) {
/*  85 */     for (String innerClass : mixin.getSyntheticInnerClasses()) {
/*  86 */       registerSyntheticInner(innerClass.replace('/', '.'));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPrepare(MixinInfo mixin) {
/*  92 */     String className = mixin.getClassName();
/*     */     
/*  94 */     if (mixin.isLoadable()) {
/*  95 */       registerLoadable(className);
/*     */     }
/*     */     
/*  98 */     if (mixin.isAccessor()) {
/*  99 */       registerAccessor(mixin);
/*     */     }
/*     */   }
/*     */   
/*     */   void registerSyntheticInner(String className) {
/* 104 */     this.syntheticInnerClasses.add(className);
/*     */   }
/*     */   
/*     */   void registerLoadable(String className) {
/* 108 */     this.loadable.add(className);
/*     */   }
/*     */   
/*     */   void registerAccessor(MixinInfo mixin) {
/* 112 */     registerLoadable(mixin.getClassName());
/* 113 */     this.accessorMixins.put(mixin.getClassName(), mixin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean canProcess(String className) {
/* 125 */     return (this.syntheticInnerClasses.contains(className) || this.loadable.contains(className));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processClass(String name, ClassNode classNode) {
/* 130 */     if (this.syntheticInnerClasses.contains(name)) {
/* 131 */       processSyntheticInner(classNode);
/* 132 */       return true;
/*     */     } 
/*     */     
/* 135 */     if (this.accessorMixins.containsKey(name)) {
/* 136 */       MixinInfo mixin = this.accessorMixins.get(name);
/* 137 */       return processAccessor(classNode, mixin);
/*     */     } 
/*     */     
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processSyntheticInner(ClassNode classNode) {
/* 149 */     classNode.access |= 0x1;
/*     */     
/* 151 */     for (FieldNode field : classNode.fields) {
/* 152 */       if ((field.access & 0x6) == 0) {
/* 153 */         field.access |= 0x1;
/*     */       }
/*     */     } 
/*     */     
/* 157 */     for (MethodNode method : classNode.methods) {
/* 158 */       if ((method.access & 0x6) == 0) {
/* 159 */         method.access |= 0x1;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean processAccessor(ClassNode classNode, MixinInfo mixin) {
/* 165 */     if (!MixinEnvironment.getCompatibilityLevel().supports(1)) {
/* 166 */       return false;
/*     */     }
/*     */     
/* 169 */     boolean transformed = false;
/* 170 */     MixinInfo.MixinClassNode mixinClassNode = mixin.getClassNode(0);
/* 171 */     ClassInfo targetClass = mixin.getTargets().get(0);
/*     */     
/* 173 */     for (MixinInfo.MixinMethodNode methodNode : mixinClassNode.mixinMethods) {
/* 174 */       if (!Bytecode.hasFlag((MethodNode)methodNode, 8)) {
/*     */         continue;
/*     */       }
/*     */       
/* 178 */       AnnotationNode accessor = methodNode.getVisibleAnnotation((Class)Accessor.class);
/* 179 */       AnnotationNode invoker = methodNode.getVisibleAnnotation((Class)Invoker.class);
/* 180 */       if (accessor != null || invoker != null) {
/* 181 */         ClassInfo.Method method = getAccessorMethod(mixin, (MethodNode)methodNode, targetClass);
/* 182 */         createProxy((MethodNode)methodNode, targetClass, method);
/* 183 */         Annotations.setVisible((MethodNode)methodNode, MixinProxy.class, new Object[] { "sessionId", this.sessionId });
/* 184 */         classNode.methods.add(methodNode);
/* 185 */         transformed = true;
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     if (!transformed) {
/* 190 */       return false;
/*     */     }
/*     */     
/* 193 */     Bytecode.replace(mixinClassNode, classNode);
/* 194 */     return true;
/*     */   }
/*     */   
/*     */   private ClassInfo.Method getAccessorMethod(MixinInfo mixin, MethodNode methodNode, ClassInfo targetClass) throws MixinTransformerError {
/* 198 */     ClassInfo.Method method = mixin.getClassInfo().findMethod(methodNode, 10);
/*     */ 
/*     */ 
/*     */     
/* 202 */     if (!method.isConformed()) {
/* 203 */       String uniqueName = targetClass.getMethodMapper().getUniqueName(methodNode, this.sessionId, true);
/* 204 */       method.conform(uniqueName);
/*     */     } 
/*     */     
/* 207 */     return method;
/*     */   }
/*     */   
/*     */   private static void createProxy(MethodNode methodNode, ClassInfo targetClass, ClassInfo.Method method) {
/* 211 */     methodNode.access |= 0x1000;
/* 212 */     methodNode.instructions.clear();
/* 213 */     Type[] args = Type.getArgumentTypes(methodNode.desc);
/* 214 */     Type returnType = Type.getReturnType(methodNode.desc);
/* 215 */     Bytecode.loadArgs(args, methodNode.instructions, 0);
/* 216 */     methodNode.instructions.add((AbstractInsnNode)new MethodInsnNode(184, targetClass.getName(), method.getName(), methodNode.desc, false));
/* 217 */     methodNode.instructions.add((AbstractInsnNode)new InsnNode(returnType.getOpcode(172)));
/* 218 */     methodNode.maxStack = Bytecode.getFirstNonArgLocalIndex(args, false);
/* 219 */     methodNode.maxLocals = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\MixinPostProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */