/*    */ package org.spongepowered.asm.mixin.injection;
/*    */ 
/*    */ import java.lang.annotation.Retention;
/*    */ import java.lang.annotation.RetentionPolicy;
/*    */ import java.lang.annotation.Target;
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
/*    */ @Target({})
/*    */ @Retention(RetentionPolicy.RUNTIME)
/*    */ public @interface At
/*    */ {
/*    */   String id() default "";
/*    */   
/*    */   String value();
/*    */   
/*    */   String slice() default "";
/*    */   
/*    */   Shift shift() default Shift.NONE;
/*    */   
/*    */   int by() default 0;
/*    */   
/*    */   String[] args() default {};
/*    */   
/*    */   String target() default "";
/*    */   
/*    */   int ordinal() default -1;
/*    */   
/*    */   int opcode() default -1;
/*    */   
/*    */   boolean remap() default true;
/*    */   
/*    */   public enum Shift
/*    */   {
/* 60 */     NONE,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 65 */     BEFORE,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 70 */     AFTER,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 75 */     BY;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\At.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */