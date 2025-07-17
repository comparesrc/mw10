/*    */ package org.spongepowered.asm.mixin.throwables;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClassAlreadyLoadedException
/*    */   extends MixinException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ClassAlreadyLoadedException(String message) {
/* 39 */     super(message);
/*    */   }
/*    */   
/*    */   public ClassAlreadyLoadedException(Throwable cause) {
/* 43 */     super(cause);
/*    */   }
/*    */   
/*    */   public ClassAlreadyLoadedException(String message, Throwable cause) {
/* 47 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\throwables\ClassAlreadyLoadedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */