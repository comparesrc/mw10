/*     */ package org.spongepowered.asm.mixin.struct;
/*     */ 
/*     */ import org.objectweb.asm.tree.AnnotationNode;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.IInjectionPointContext;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.asm.MethodNodeEx;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SpecialMethodInfo
/*     */   implements IInjectionPointContext
/*     */ {
/*     */   protected final AnnotationNode annotation;
/*     */   protected final String annotationType;
/*     */   protected final ClassNode classNode;
/*     */   protected final MethodNode method;
/*     */   protected final String methodName;
/*     */   protected final MixinTargetContext mixin;
/*     */   
/*     */   public SpecialMethodInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/*  73 */     this.mixin = mixin;
/*  74 */     this.method = method;
/*  75 */     this.annotation = annotation;
/*  76 */     this.annotationType = (this.annotation != null) ? ("@" + Bytecode.getSimpleName(this.annotation)) : "Undecorated injector";
/*  77 */     this.classNode = mixin.getTargetClassNode();
/*  78 */     this.methodName = MethodNodeEx.getName(method);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final IMixinContext getContext() {
/*  88 */     return (IMixinContext)this.mixin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final AnnotationNode getAnnotation() {
/*  98 */     return this.annotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ClassNode getClassNode() {
/* 107 */     return this.classNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ClassInfo getClassInfo() {
/* 114 */     return this.mixin.getClassInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MethodNode getMethod() {
/* 124 */     return this.method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMethodName() {
/* 131 */     return this.methodName;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\struct\SpecialMethodInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */