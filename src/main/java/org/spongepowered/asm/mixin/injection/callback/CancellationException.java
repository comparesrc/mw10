/*    */ package org.spongepowered.asm.mixin.injection.callback;
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
/*    */ public class CancellationException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public CancellationException() {}
/*    */   
/*    */   public CancellationException(String message) {
/* 39 */     super(message);
/*    */   }
/*    */   
/*    */   public CancellationException(Throwable cause) {
/* 43 */     super(cause);
/*    */   }
/*    */   
/*    */   public CancellationException(String message, Throwable cause) {
/* 47 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\callback\CancellationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */