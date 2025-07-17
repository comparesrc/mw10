/*    */ package org.spongepowered.asm.mixin.injection.throwables;
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
/*    */ public class InjectionError
/*    */   extends MixinError
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public InjectionError() {}
/*    */   
/*    */   public InjectionError(String message) {
/* 40 */     super(message);
/*    */   }
/*    */   
/*    */   public InjectionError(Throwable cause) {
/* 44 */     super(cause);
/*    */   }
/*    */   
/*    */   public InjectionError(String message, Throwable cause) {
/* 48 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\throwables\InjectionError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */