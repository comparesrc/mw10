/*    */ package org.spongepowered.asm.mixin.transformer.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*    */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*    */ import org.spongepowered.asm.mixin.transformer.ActivityStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MixinApplicatorException
/*    */   extends InvalidMixinException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public MixinApplicatorException(IMixinInfo context, String message) {
/* 39 */     super(context, message, (ActivityStack)null);
/*    */   }
/*    */   
/*    */   public MixinApplicatorException(IMixinInfo context, String message, ActivityStack activityContext) {
/* 43 */     super(context, message, activityContext);
/*    */   }
/*    */   
/*    */   public MixinApplicatorException(IMixinContext context, String message) {
/* 47 */     super(context, message, (ActivityStack)null);
/*    */   }
/*    */   
/*    */   public MixinApplicatorException(IMixinContext context, String message, ActivityStack activityContext) {
/* 51 */     super(context, message, activityContext);
/*    */   }
/*    */   
/*    */   public MixinApplicatorException(IMixinInfo mixin, String message, Throwable cause) {
/* 55 */     super(mixin, message, cause, (ActivityStack)null);
/*    */   }
/*    */   
/*    */   public MixinApplicatorException(IMixinInfo mixin, String message, Throwable cause, ActivityStack activityContext) {
/* 59 */     super(mixin, message, cause, activityContext);
/*    */   }
/*    */   
/*    */   public MixinApplicatorException(IMixinContext mixin, String message, Throwable cause) {
/* 63 */     super(mixin, message, cause, (ActivityStack)null);
/*    */   }
/*    */   
/*    */   public MixinApplicatorException(IMixinContext mixin, String message, Throwable cause, ActivityStack activityContext) {
/* 67 */     super(mixin, message, cause, activityContext);
/*    */   }
/*    */   
/*    */   public MixinApplicatorException(IMixinInfo mixin, Throwable cause) {
/* 71 */     super(mixin, cause, (ActivityStack)null);
/*    */   }
/*    */   
/*    */   public MixinApplicatorException(IMixinInfo mixin, Throwable cause, ActivityStack activityContext) {
/* 75 */     super(mixin, cause, activityContext);
/*    */   }
/*    */   
/*    */   public MixinApplicatorException(IMixinContext mixin, Throwable cause) {
/* 79 */     super(mixin, cause, (ActivityStack)null);
/*    */   }
/*    */   
/*    */   public MixinApplicatorException(IMixinContext mixin, Throwable cause, ActivityStack activityContext) {
/* 83 */     super(mixin, cause, activityContext);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\throwables\MixinApplicatorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */