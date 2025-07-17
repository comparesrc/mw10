/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.injection.selectors.InvalidSelectorException;
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
/*    */ public class TargetNotSupportedException
/*    */   extends InvalidSelectorException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public TargetNotSupportedException(String message) {
/* 39 */     super(message);
/*    */   }
/*    */   
/*    */   public TargetNotSupportedException(Throwable cause) {
/* 43 */     super(cause);
/*    */   }
/*    */   
/*    */   public TargetNotSupportedException(String message, Throwable cause) {
/* 47 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\struct\TargetNotSupportedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */