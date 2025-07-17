/*    */ package org.spongepowered.asm.mixin.injection.selectors;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.throwables.MixinException;
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
/*    */ public class InvalidSelectorException
/*    */   extends MixinException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public InvalidSelectorException(String message) {
/* 38 */     super(message);
/*    */   }
/*    */   
/*    */   public InvalidSelectorException(Throwable cause) {
/* 42 */     super(cause);
/*    */   }
/*    */   
/*    */   public InvalidSelectorException(String message, Throwable cause) {
/* 46 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\selectors\InvalidSelectorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */