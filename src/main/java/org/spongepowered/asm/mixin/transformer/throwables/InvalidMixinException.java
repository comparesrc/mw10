/*    */ package org.spongepowered.asm.mixin.transformer.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*    */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*    */ import org.spongepowered.asm.mixin.throwables.MixinException;
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
/*    */ 
/*    */ public class InvalidMixinException
/*    */   extends MixinException
/*    */ {
/*    */   private static final long serialVersionUID = 2L;
/*    */   private final IMixinInfo mixin;
/*    */   
/*    */   public InvalidMixinException(IMixinInfo mixin, String message) {
/* 42 */     super(message);
/* 43 */     this.mixin = mixin;
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinInfo mixin, String message, ActivityStack activityContext) {
/* 47 */     super(message, activityContext);
/* 48 */     this.mixin = mixin;
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinContext context, String message) {
/* 52 */     this(context.getMixin(), message);
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinContext context, String message, ActivityStack activityContext) {
/* 56 */     this(context.getMixin(), message, activityContext);
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinInfo mixin, Throwable cause) {
/* 60 */     super(cause);
/* 61 */     this.mixin = mixin;
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinInfo mixin, Throwable cause, ActivityStack activityContext) {
/* 65 */     super(cause, activityContext);
/* 66 */     this.mixin = mixin;
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinContext context, Throwable cause) {
/* 70 */     this(context.getMixin(), cause);
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinContext context, Throwable cause, ActivityStack activityContext) {
/* 74 */     this(context.getMixin(), cause, activityContext);
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinInfo mixin, String message, Throwable cause) {
/* 78 */     super(message, cause);
/* 79 */     this.mixin = mixin;
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinInfo mixin, String message, Throwable cause, ActivityStack activityContext) {
/* 83 */     super(message, cause, activityContext);
/* 84 */     this.mixin = mixin;
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinContext context, String message, Throwable cause) {
/* 88 */     super(message, cause);
/* 89 */     this.mixin = context.getMixin();
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinContext context, String message, Throwable cause, ActivityStack activityContext) {
/* 93 */     super(message, cause, activityContext);
/* 94 */     this.mixin = context.getMixin();
/*    */   }
/*    */   
/*    */   public IMixinInfo getMixin() {
/* 98 */     return this.mixin;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\throwables\InvalidMixinException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */