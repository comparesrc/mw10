/*    */ package org.spongepowered.asm.launch;
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
/*    */ 
/*    */ public class MixinInitialisationError
/*    */   extends MixinError
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public MixinInitialisationError() {}
/*    */   
/*    */   public MixinInitialisationError(String message) {
/* 40 */     super(message);
/*    */   }
/*    */   
/*    */   public MixinInitialisationError(Throwable cause) {
/* 44 */     super(cause);
/*    */   }
/*    */   
/*    */   public MixinInitialisationError(String message, Throwable cause) {
/* 48 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\MixinInitialisationError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */