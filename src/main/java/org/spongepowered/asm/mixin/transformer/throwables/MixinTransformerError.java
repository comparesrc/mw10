/*    */ package org.spongepowered.asm.mixin.transformer.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.throwables.MixinError;
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
/*    */ public class MixinTransformerError
/*    */   extends MixinError
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public MixinTransformerError(String message) {
/* 37 */     super(message);
/*    */   }
/*    */   
/*    */   public MixinTransformerError(Throwable cause) {
/* 41 */     super(cause);
/*    */   }
/*    */   
/*    */   public MixinTransformerError(String message, Throwable cause) {
/* 45 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\throwables\MixinTransformerError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */