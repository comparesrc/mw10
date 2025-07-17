/*    */ package org.spongepowered.asm.mixin.throwables;
/*    */ 
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
/*    */ public class MixinException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String activityDescriptor;
/*    */   
/*    */   public MixinException(String message) {
/* 39 */     super(message);
/*    */   }
/*    */   
/*    */   public MixinException(String message, ActivityStack context) {
/* 43 */     super(message);
/* 44 */     this.activityDescriptor = (context != null) ? context.toString() : null;
/*    */   }
/*    */   
/*    */   public MixinException(Throwable cause) {
/* 48 */     super(cause);
/*    */   }
/*    */   
/*    */   public MixinException(Throwable cause, ActivityStack context) {
/* 52 */     super(cause);
/* 53 */     this.activityDescriptor = (context != null) ? context.toString() : null;
/*    */   }
/*    */   
/*    */   public MixinException(String message, Throwable cause) {
/* 57 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public MixinException(String message, Throwable cause, ActivityStack context) {
/* 61 */     super(message, cause);
/* 62 */     this.activityDescriptor = (context != null) ? context.toString() : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void prepend(ActivityStack upstreamContext) {
/* 71 */     String strContext = upstreamContext.toString();
/* 72 */     this.activityDescriptor = (this.activityDescriptor != null) ? (strContext + " -> " + this.activityDescriptor) : (" -> " + strContext);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 78 */     String message = super.getMessage();
/* 79 */     return (this.activityDescriptor != null) ? (message + " [" + this.activityDescriptor + "]") : message;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\throwables\MixinException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */