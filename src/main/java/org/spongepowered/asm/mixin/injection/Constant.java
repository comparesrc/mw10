/*     */ package org.spongepowered.asm.mixin.injection;
/*     */ 
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Target({})
/*     */ @Retention(RetentionPolicy.RUNTIME)
/*     */ public @interface Constant
/*     */ {
/*     */   boolean nullValue() default false;
/*     */   
/*     */   int intValue() default 0;
/*     */   
/*     */   float floatValue() default 0.0F;
/*     */   
/*     */   long longValue() default 0L;
/*     */   
/*     */   double doubleValue() default 0.0D;
/*     */   
/*     */   String stringValue() default "";
/*     */   
/*     */   Class<?> classValue() default Object.class;
/*     */   
/*     */   int ordinal() default -1;
/*     */   
/*     */   String slice() default "";
/*     */   
/*     */   Condition[] expandZeroConditions() default {};
/*     */   
/*     */   boolean log() default false;
/*     */   
/*     */   public enum Condition
/*     */   {
/*  64 */     LESS_THAN_ZERO((String)new int[] { 155, 156
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }),
/*  71 */     LESS_THAN_OR_EQUAL_TO_ZERO((String)new int[] { 158, 157
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }),
/*  79 */     GREATER_THAN_OR_EQUAL_TO_ZERO((String)LESS_THAN_ZERO),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     GREATER_THAN_ZERO((String)LESS_THAN_OR_EQUAL_TO_ZERO);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int[] opcodes;
/*     */ 
/*     */ 
/*     */     
/*     */     private final Condition equivalence;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Condition(Condition equivalence, int... opcodes) {
/* 102 */       this.equivalence = (equivalence != null) ? equivalence : this;
/* 103 */       this.opcodes = opcodes;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Condition getEquivalentCondition() {
/* 110 */       return this.equivalence;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int[] getOpcodes() {
/* 117 */       return this.opcodes;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\Constant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */