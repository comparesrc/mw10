/*    */ package org.spongepowered.asm.mixin.transformer.throwables;
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
/*    */ public class ReEntrantTransformerError
/*    */   extends MixinTransformerError
/*    */ {
/*    */   private static final long serialVersionUID = 7073583236491579255L;
/*    */   
/*    */   public ReEntrantTransformerError(String message) {
/* 35 */     super(message);
/*    */   }
/*    */   
/*    */   public ReEntrantTransformerError(Throwable cause) {
/* 39 */     super(cause);
/*    */   }
/*    */   
/*    */   public ReEntrantTransformerError(String message, Throwable cause) {
/* 43 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\throwables\ReEntrantTransformerError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */