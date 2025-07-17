/*    */ package org.spongepowered.asm.mixin.injection.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*    */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
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
/*    */ public class InvalidInjectionPointException
/*    */   extends InvalidInjectionException
/*    */ {
/*    */   private static final long serialVersionUID = 2L;
/*    */   
/*    */   public InvalidInjectionPointException(IMixinContext context, String format, Object... args) {
/* 38 */     super(context, String.format(format, args));
/*    */   }
/*    */   
/*    */   public InvalidInjectionPointException(InjectionInfo info, String format, Object... args) {
/* 42 */     super(info, String.format(format, args));
/*    */   }
/*    */   
/*    */   public InvalidInjectionPointException(IMixinContext context, Throwable cause, String format, Object... args) {
/* 46 */     super(context, String.format(format, args), cause);
/*    */   }
/*    */   
/*    */   public InvalidInjectionPointException(InjectionInfo info, Throwable cause, String format, Object... args) {
/* 50 */     super(info, String.format(format, args), cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\throwables\InvalidInjectionPointException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */