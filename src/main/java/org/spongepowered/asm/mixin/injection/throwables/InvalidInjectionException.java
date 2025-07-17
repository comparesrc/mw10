/*     */ package org.spongepowered.asm.mixin.injection.throwables;
/*     */ 
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.transformer.ActivityStack;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
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
/*     */ public class InvalidInjectionException
/*     */   extends InvalidMixinException
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*     */   private final InjectionInfo info;
/*     */   
/*     */   public InvalidInjectionException(IMixinContext context, String message) {
/*  43 */     super(context, message);
/*  44 */     this.info = null;
/*     */   }
/*     */   
/*     */   public InvalidInjectionException(IMixinContext context, String message, ActivityStack activityContext) {
/*  48 */     super(context, message, activityContext);
/*  49 */     this.info = null;
/*     */   }
/*     */   
/*     */   public InvalidInjectionException(InjectionInfo info, String message) {
/*  53 */     super(info.getContext(), message);
/*  54 */     this.info = info;
/*     */   }
/*     */   
/*     */   public InvalidInjectionException(InjectionInfo info, String message, ActivityStack activityContext) {
/*  58 */     super(info.getContext(), message, activityContext);
/*  59 */     this.info = info;
/*     */   }
/*     */   
/*     */   public InvalidInjectionException(IMixinContext context, Throwable cause) {
/*  63 */     super(context, cause);
/*  64 */     this.info = null;
/*     */   }
/*     */   
/*     */   public InvalidInjectionException(IMixinContext context, Throwable cause, ActivityStack activityContext) {
/*  68 */     super(context, cause, activityContext);
/*  69 */     this.info = null;
/*     */   }
/*     */   
/*     */   public InvalidInjectionException(InjectionInfo info, Throwable cause) {
/*  73 */     super(info.getContext(), cause);
/*  74 */     this.info = info;
/*     */   }
/*     */   
/*     */   public InvalidInjectionException(InjectionInfo info, Throwable cause, ActivityStack activityContext) {
/*  78 */     super(info.getContext(), cause, activityContext);
/*  79 */     this.info = info;
/*     */   }
/*     */   
/*     */   public InvalidInjectionException(IMixinContext context, String message, Throwable cause) {
/*  83 */     super(context, message, cause);
/*  84 */     this.info = null;
/*     */   }
/*     */   
/*     */   public InvalidInjectionException(IMixinContext context, String message, Throwable cause, ActivityStack activityContext) {
/*  88 */     super(context, message, cause, activityContext);
/*  89 */     this.info = null;
/*     */   }
/*     */   
/*     */   public InvalidInjectionException(InjectionInfo info, String message, Throwable cause) {
/*  93 */     super(info.getContext(), message, cause);
/*  94 */     this.info = info;
/*     */   }
/*     */   
/*     */   public InvalidInjectionException(InjectionInfo info, String message, Throwable cause, ActivityStack activityContext) {
/*  98 */     super(info.getContext(), message, cause, activityContext);
/*  99 */     this.info = info;
/*     */   }
/*     */   
/*     */   public InjectionInfo getInjectionInfo() {
/* 103 */     return this.info;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\throwables\InvalidInjectionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */