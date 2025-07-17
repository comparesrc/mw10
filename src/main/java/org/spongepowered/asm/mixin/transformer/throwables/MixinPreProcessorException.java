/*    */ package org.spongepowered.asm.mixin.transformer.throwables;
/*    */ 
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
/*    */ public class MixinPreProcessorException
/*    */   extends MixinException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public MixinPreProcessorException(String message, ActivityStack context) {
/* 38 */     super(message, context);
/*    */   }
/*    */   
/*    */   public MixinPreProcessorException(String message, Throwable cause, ActivityStack context) {
/* 42 */     super(message, cause, context);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\throwables\MixinPreProcessorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */