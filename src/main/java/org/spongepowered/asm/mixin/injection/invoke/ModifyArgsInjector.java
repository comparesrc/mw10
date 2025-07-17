/*     */ package org.spongepowered.asm.mixin.injection.invoke;
/*     */ 
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.objectweb.asm.tree.InsnNode;
/*     */ import org.objectweb.asm.tree.MethodInsnNode;
/*     */ import org.objectweb.asm.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
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
/*     */ public class ModifyArgsInjector
/*     */   extends InvokeInjector
/*     */ {
/*     */   private final ArgsClassGenerator argsClassGenerator;
/*     */   
/*     */   public ModifyArgsInjector(InjectionInfo info) {
/*  54 */     super(info, "@ModifyArgs");
/*     */     
/*  56 */     this.argsClassGenerator = (ArgsClassGenerator)info.getContext().getExtensions().getGenerator(ArgsClassGenerator.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkTarget(Target target) {
/*  65 */     checkTargetModifiers(target, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/*  70 */     checkTargetForNode(target, node, InjectionPoint.RestrictTargetLevel.ALLOW_ALL);
/*  71 */     super.inject(target, node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode node) {
/*  79 */     MethodInsnNode targetMethod = (MethodInsnNode)node.getCurrentTarget();
/*     */     
/*  81 */     Type[] args = Type.getArgumentTypes(targetMethod.desc);
/*  82 */     if (args.length == 0) {
/*  83 */       throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " targets a method invocation " + targetMethod.name + targetMethod.desc + " with no arguments!");
/*     */     }
/*     */ 
/*     */     
/*  87 */     String clArgs = this.argsClassGenerator.getArgsClass(targetMethod.desc, this.info.getContext().getMixin()).getName();
/*  88 */     boolean withArgs = verifyTarget(target);
/*     */     
/*  90 */     InsnList insns = new InsnList();
/*  91 */     Target.Extension extraStack = target.extendStack().add(1);
/*     */     
/*  93 */     packArgs(insns, clArgs, targetMethod);
/*     */     
/*  95 */     if (withArgs) {
/*  96 */       extraStack.add(target.arguments);
/*  97 */       Bytecode.loadArgs(target.arguments, insns, target.isStatic ? 0 : 1);
/*     */     } 
/*     */     
/* 100 */     invokeHandler(insns);
/* 101 */     unpackArgs(insns, clArgs, args);
/*     */     
/* 103 */     extraStack.apply();
/* 104 */     target.insns.insertBefore((AbstractInsnNode)targetMethod, insns);
/*     */   }
/*     */   
/*     */   private boolean verifyTarget(Target target) {
/* 108 */     String shortDesc = String.format("(L%s;)V", new Object[] { ArgsClassGenerator.ARGS_REF });
/* 109 */     if (!this.methodNode.desc.equals(shortDesc)) {
/* 110 */       String targetDesc = Bytecode.changeDescriptorReturnType(target.method.desc, "V");
/* 111 */       String longDesc = String.format("(L%s;%s", new Object[] { ArgsClassGenerator.ARGS_REF, targetDesc.substring(1) });
/*     */       
/* 113 */       if (this.methodNode.desc.equals(longDesc)) {
/* 114 */         return true;
/*     */       }
/*     */       
/* 117 */       throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " has an invalid signature " + this.methodNode.desc + ", expected " + shortDesc + " or " + longDesc);
/*     */     } 
/*     */     
/* 120 */     return false;
/*     */   }
/*     */   
/*     */   private void packArgs(InsnList insns, String clArgs, MethodInsnNode targetMethod) {
/* 124 */     String factoryDesc = Bytecode.changeDescriptorReturnType(targetMethod.desc, "L" + clArgs + ";");
/* 125 */     insns.add((AbstractInsnNode)new MethodInsnNode(184, clArgs, "of", factoryDesc, false));
/* 126 */     insns.add((AbstractInsnNode)new InsnNode(89));
/*     */     
/* 128 */     if (!this.isStatic) {
/* 129 */       insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 130 */       insns.add((AbstractInsnNode)new InsnNode(95));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void unpackArgs(InsnList insns, String clArgs, Type[] args) {
/* 135 */     for (int i = 0; i < args.length; i++) {
/* 136 */       if (i < args.length - 1) {
/* 137 */         insns.add((AbstractInsnNode)new InsnNode(89));
/*     */       }
/* 139 */       insns.add((AbstractInsnNode)new MethodInsnNode(182, clArgs, "$" + i, "()" + args[i].getDescriptor(), false));
/* 140 */       if (i < args.length - 1)
/* 141 */         if (args[i].getSize() == 1) {
/* 142 */           insns.add((AbstractInsnNode)new InsnNode(95));
/*     */         } else {
/* 144 */           insns.add((AbstractInsnNode)new InsnNode(93));
/* 145 */           insns.add((AbstractInsnNode)new InsnNode(88));
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\invoke\ModifyArgsInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */