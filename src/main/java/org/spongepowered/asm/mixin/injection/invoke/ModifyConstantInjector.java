/*     */ package org.spongepowered.asm.mixin.injection.invoke;
/*     */ 
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.FieldInsnNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.objectweb.asm.tree.InsnNode;
/*     */ import org.objectweb.asm.tree.JumpInsnNode;
/*     */ import org.objectweb.asm.tree.LocalVariableNode;
/*     */ import org.objectweb.asm.tree.TypeInsnNode;
/*     */ import org.objectweb.asm.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*     */ import org.spongepowered.asm.mixin.injection.invoke.util.InsnFinder;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.Locals;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
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
/*     */ public class ModifyConstantInjector
/*     */   extends RedirectInjector
/*     */ {
/*     */   private static final int OPCODE_OFFSET = 6;
/*     */   
/*     */   public ModifyConstantInjector(InjectionInfo info) {
/*  66 */     super(info, "@ModifyConstant");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/*  71 */     if (!preInject(node)) {
/*     */       return;
/*     */     }
/*     */     
/*  75 */     if (node.isReplaced()) {
/*  76 */       throw new UnsupportedOperationException("Target failure for " + this.info);
/*     */     }
/*     */     
/*  79 */     AbstractInsnNode targetNode = node.getCurrentTarget();
/*  80 */     if (targetNode instanceof TypeInsnNode) {
/*  81 */       checkTargetModifiers(target, false);
/*  82 */       injectTypeConstantModifier(target, (TypeInsnNode)targetNode);
/*     */       
/*     */       return;
/*     */     } 
/*  86 */     if (targetNode instanceof JumpInsnNode) {
/*  87 */       checkTargetModifiers(target, false);
/*  88 */       injectExpandedConstantModifier(target, (JumpInsnNode)targetNode);
/*     */       
/*     */       return;
/*     */     } 
/*  92 */     if (Bytecode.isConstant(targetNode)) {
/*  93 */       checkTargetModifiers(target, false);
/*  94 */       injectConstantModifier(target, targetNode);
/*     */       
/*     */       return;
/*     */     } 
/*  98 */     throw new InvalidInjectionException(this.info, String.format("%s annotation is targetting an invalid insn in %s in %s", new Object[] { this.annotationType, target, this }));
/*     */   }
/*     */ 
/*     */   
/*     */   private void injectTypeConstantModifier(Target target, TypeInsnNode typeNode) {
/* 103 */     int opcode = typeNode.getOpcode();
/* 104 */     if (opcode != 193) {
/* 105 */       throw new InvalidInjectionException(this.info, String.format("%s annotation does not support %s insn in %s in %s", new Object[] { this.annotationType, 
/* 106 */               Bytecode.getOpcodeName(opcode), target, this }));
/*     */     }
/* 108 */     injectAtInstanceOf(target, typeNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectExpandedConstantModifier(Target target, JumpInsnNode jumpNode) {
/* 118 */     int opcode = jumpNode.getOpcode();
/* 119 */     if (opcode < 155 || opcode > 158) {
/* 120 */       throw new InvalidInjectionException(this.info, String.format("%s annotation selected an invalid opcode %s in %s in %s", new Object[] { this.annotationType, 
/* 121 */               Bytecode.getOpcodeName(opcode), target, this }));
/*     */     }
/*     */     
/* 124 */     Target.Extension extraStack = target.extendStack();
/* 125 */     InsnList insns = new InsnList();
/* 126 */     insns.add((AbstractInsnNode)new InsnNode(3));
/* 127 */     AbstractInsnNode invoke = invokeConstantHandler(Type.getType("I"), target, extraStack, insns, insns);
/* 128 */     insns.add((AbstractInsnNode)new JumpInsnNode(opcode + 6, jumpNode.label));
/* 129 */     extraStack.add(1).apply();
/* 130 */     target.replaceNode((AbstractInsnNode)jumpNode, invoke, insns);
/*     */   }
/*     */   
/*     */   private void injectConstantModifier(Target target, AbstractInsnNode constNode) {
/* 134 */     Type constantType = Bytecode.getConstantType(constNode);
/*     */     
/* 136 */     if (constantType.getSort() <= 5 && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/* 137 */       checkNarrowing(target, constNode, constantType);
/*     */     }
/*     */     
/* 140 */     Target.Extension extraStack = target.extendStack();
/* 141 */     InsnList before = new InsnList();
/* 142 */     InsnList after = new InsnList();
/* 143 */     AbstractInsnNode invoke = invokeConstantHandler(constantType, target, extraStack, before, after);
/* 144 */     extraStack.apply();
/* 145 */     target.wrapNode(constNode, invoke, before, after);
/*     */   }
/*     */   
/*     */   private AbstractInsnNode invokeConstantHandler(Type constantType, Target target, Target.Extension extraStack, InsnList before, InsnList after) {
/* 149 */     Injector.InjectorData handler = new Injector.InjectorData(target, "constant modifier");
/* 150 */     validateParams(handler, constantType, new Type[] { constantType });
/*     */     
/* 152 */     if (!this.isStatic) {
/* 153 */       before.insert((AbstractInsnNode)new VarInsnNode(25, 0));
/* 154 */       extraStack.add();
/*     */     } 
/*     */     
/* 157 */     if (handler.captureTargetArgs > 0) {
/* 158 */       pushArgs(target.arguments, after, target.getArgIndices(), 0, handler.captureTargetArgs, extraStack);
/*     */     }
/*     */     
/* 161 */     return invokeHandler(after);
/*     */   }
/*     */   
/*     */   private void checkNarrowing(Target target, AbstractInsnNode constNode, Type constantType) {
/* 165 */     AbstractInsnNode pop = (new InsnFinder()).findPopInsn(target, constNode);
/*     */     
/* 167 */     if (pop == null)
/*     */       return; 
/* 169 */     if (pop instanceof FieldInsnNode) {
/* 170 */       FieldInsnNode fieldNode = (FieldInsnNode)pop;
/* 171 */       Type fieldType = Type.getType(fieldNode.desc);
/* 172 */       checkNarrowing(target, constNode, constantType, fieldType, target.indexOf(pop), String.format("%s %s %s.%s", new Object[] {
/* 173 */               Bytecode.getOpcodeName(pop), SignaturePrinter.getTypeName(fieldType, false), fieldNode.owner.replace('/', '.'), fieldNode.name }));
/* 174 */     } else if (pop.getOpcode() == 172) {
/* 175 */       checkNarrowing(target, constNode, constantType, target.returnType, target.indexOf(pop), "RETURN " + 
/* 176 */           SignaturePrinter.getTypeName(target.returnType, false));
/* 177 */     } else if (pop.getOpcode() == 54) {
/* 178 */       int var = ((VarInsnNode)pop).var;
/* 179 */       LocalVariableNode localVar = Locals.getLocalVariableAt(target.classNode, target.method, pop, var);
/*     */ 
/*     */ 
/*     */       
/* 183 */       if (localVar != null && localVar.desc != null) {
/* 184 */         String name = (localVar.name != null) ? localVar.name : "unnamed";
/* 185 */         Type localType = Type.getType(localVar.desc);
/* 186 */         checkNarrowing(target, constNode, constantType, localType, target.indexOf(pop), String.format("ISTORE[var=%d] %s %s", new Object[] { Integer.valueOf(var), 
/* 187 */                 SignaturePrinter.getTypeName(localType, false), name }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkNarrowing(Target target, AbstractInsnNode constNode, Type constantType, Type type, int index, String description) {
/* 193 */     int fromSort = constantType.getSort();
/* 194 */     int toSort = type.getSort();
/* 195 */     if (toSort < fromSort) {
/* 196 */       String fromType = SignaturePrinter.getTypeName(constantType, false);
/* 197 */       String toType = SignaturePrinter.getTypeName(type, false);
/* 198 */       String message = (toSort == 1) ? ". Implicit conversion to <boolean> can cause nondeterministic (JVM-specific) behaviour!" : "";
/* 199 */       Level level = (toSort == 1) ? Level.ERROR : Level.WARN;
/* 200 */       Injector.logger.log(level, "Narrowing conversion of <{}> to <{}> in {} target {} at opcode {} ({}){}", new Object[] { fromType, toType, this.info, target, 
/* 201 */             Integer.valueOf(index), description, message });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\invoke\ModifyConstantInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */