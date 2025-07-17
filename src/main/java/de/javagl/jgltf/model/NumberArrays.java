/*    */ package de.javagl.jgltf.model;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NumberArrays
/*    */ {
/*    */   static Number[] asNumbers(int[] array) {
/* 42 */     Number[] result = new Number[array.length];
/* 43 */     for (int i = 0; i < array.length; i++)
/*    */     {
/* 45 */       result[i] = Integer.valueOf(array[i]);
/*    */     }
/* 47 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static Number[] asNumbers(long[] array) {
/* 58 */     Number[] result = new Number[array.length];
/* 59 */     for (int i = 0; i < array.length; i++)
/*    */     {
/* 61 */       result[i] = Long.valueOf(array[i]);
/*    */     }
/* 63 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static Number[] asNumbers(float[] array) {
/* 74 */     Number[] result = new Number[array.length];
/* 75 */     for (int i = 0; i < array.length; i++)
/*    */     {
/* 77 */       result[i] = Float.valueOf(array[i]);
/*    */     }
/* 79 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\NumberArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */