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
/*    */ public class InvalidMemberDescriptorException
/*    */   extends InvalidSelectorException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public InvalidMemberDescriptorException(String message) {
/* 37 */     super(message);
/*    */   }
/*    */   
/*    */   public InvalidMemberDescriptorException(Throwable cause) {
/* 41 */     super(cause);
/*    */   }
/*    */   
/*    */   public InvalidMemberDescriptorException(String message, Throwable cause) {
/* 45 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\struct\InvalidMemberDescriptorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */