/*     */ package org.spongepowered.asm.mixin.injection.invoke;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.objectweb.asm.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
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
/*     */ public class ModifyArgInjector
/*     */   extends InvokeInjector
/*     */ {
/*     */   private final int index;
/*     */   private final boolean singleArgMode;
/*     */   
/*     */   public ModifyArgInjector(InjectionInfo info, int index) {
/*  66 */     super(info, "@ModifyArg");
/*  67 */     this.index = index;
/*  68 */     this.singleArgMode = (this.methodArgs.length == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
/*  78 */     super.sanityCheck(target, injectionPoints);
/*     */     
/*  80 */     if (this.singleArgMode && 
/*  81 */       !this.methodArgs[0].equals(this.returnType)) {
/*  82 */       throw new InvalidInjectionException(this.info, "@ModifyArg return type on " + this + " must match the parameter type. ARG=" + this.methodArgs[0] + " RETURN=" + this.returnType);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkTarget(Target target) {
/*  94 */     if (!this.isStatic && target.isStatic) {
/*  95 */       throw new InvalidInjectionException(this.info, "non-static callback method " + this + " targets a static method which is not supported");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/* 101 */     checkTargetForNode(target, node, InjectionPoint.RestrictTargetLevel.ALLOW_ALL);
/* 102 */     super.inject(target, node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode node) {
/* 110 */     MethodInsnNode methodNode = (MethodInsnNode)node.getCurrentTarget();
/* 111 */     Type[] args = Type.getArgumentTypes(methodNode.desc);
/* 112 */     int argIndex = findArgIndex(target, args);
/* 113 */     InsnList insns = new InsnList();
/* 114 */     Target.Extension extraLocals = target.extendLocals();
/*     */     
/* 116 */     if (this.singleArgMode) {
/* 117 */       injectSingleArgHandler(target, extraLocals, args, argIndex, insns);
/*     */     } else {
/* 119 */       injectMultiArgHandler(target, extraLocals, args, argIndex, insns);
/*     */     } 
/*     */     
/* 122 */     target.insns.insertBefore((AbstractInsnNode)methodNode, insns);
/* 123 */     target.extendStack().set(2 - extraLocals.get() - 1).apply();
/* 124 */     extraLocals.apply();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectSingleArgHandler(Target target, Target.Extension extraLocals, Type[] args, int argIndex, InsnList insns) {
/* 131 */     int[] argMap = storeArgs(target, args, insns, argIndex);
/* 132 */     invokeHandlerWithArgs(args, insns, argMap, argIndex, argIndex + 1);
/* 133 */     pushArgs(args, insns, argMap, argIndex + 1, args.length);
/* 134 */     extraLocals.add(argMap[argMap.length - 1] - target.getMaxLocals() + args[args.length - 1].getSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectMultiArgHandler(Target target, Target.Extension extraLocals, Type[] args, int argIndex, InsnList insns) {
/* 141 */     if (!Arrays.equals((Object[])args, (Object[])this.methodArgs)) {
/* 142 */       throw new InvalidInjectionException(this.info, "@ModifyArg method " + this + " targets a method with an invalid signature " + 
/* 143 */           Bytecode.getDescriptor(args) + ", expected " + Bytecode.getDescriptor(this.methodArgs));
/*     */     }
/*     */     
/* 146 */     int[] argMap = storeArgs(target, args, insns, 0);
/* 147 */     pushArgs(args, insns, argMap, 0, argIndex);
/* 148 */     invokeHandlerWithArgs(args, insns, argMap, 0, args.length);
/* 149 */     pushArgs(args, insns, argMap, argIndex + 1, args.length);
/* 150 */     extraLocals.add(argMap[argMap.length - 1] - target.getMaxLocals() + args[args.length - 1].getSize());
/*     */   }
/*     */   
/*     */   protected int findArgIndex(Target target, Type[] args) {
/* 154 */     if (this.index > -1) {
/* 155 */       if (this.index >= args.length || !args[this.index].equals(this.returnType)) {
/* 156 */         throw new InvalidInjectionException(this.info, "Specified index " + this.index + " for @ModifyArg is invalid for args " + 
/* 157 */             Bytecode.getDescriptor(args) + ", expected " + this.returnType + " on " + this);
/*     */       }
/* 159 */       return this.index;
/*     */     } 
/*     */     
/* 162 */     int argIndex = -1;
/*     */     
/* 164 */     for (int arg = 0; arg < args.length; arg++) {
/* 165 */       if (args[arg].equals(this.returnType)) {
/*     */ 
/*     */ 
/*     */         
/* 169 */         if (argIndex != -1) {
/* 170 */           throw new InvalidInjectionException(this.info, "Found duplicate args with index [" + argIndex + ", " + arg + "] matching type " + this.returnType + " for @ModifyArg target " + target + " in " + this + ". Please specify index of desired arg.");
/*     */         }
/*     */ 
/*     */         
/* 174 */         argIndex = arg;
/*     */       } 
/*     */     } 
/* 177 */     if (argIndex == -1) {
/* 178 */       throw new InvalidInjectionException(this.info, "Could not find arg matching type " + this.returnType + " for @ModifyArg target " + target + " in " + this);
/*     */     }
/*     */ 
/*     */     
/* 182 */     return argIndex;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\invoke\ModifyArgInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */