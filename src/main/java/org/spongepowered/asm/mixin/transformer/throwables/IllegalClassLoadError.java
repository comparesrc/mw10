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
/*    */ public class IllegalClassLoadError
/*    */   extends MixinTransformerError
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public IllegalClassLoadError(String message) {
/* 35 */     super(message);
/*    */   }
/*    */   
/*    */   public IllegalClassLoadError(Throwable cause) {
/* 39 */     super(cause);
/*    */   }
/*    */   
/*    */   public IllegalClassLoadError(String message, Throwable cause) {
/* 43 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\throwables\IllegalClassLoadError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */