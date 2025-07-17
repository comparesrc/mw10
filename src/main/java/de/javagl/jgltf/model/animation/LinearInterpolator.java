/*    */ package de.javagl.jgltf.model.animation;
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
/*    */ class LinearInterpolator
/*    */   implements Interpolator
/*    */ {
/*    */   public void interpolate(float[] a, float[] b, float alpha, float[] result) {
/* 39 */     for (int i = 0; i < a.length; i++) {
/*    */       
/* 41 */       float ai = a[i];
/* 42 */       float bi = b[i];
/* 43 */       float ri = ai + alpha * (bi - ai);
/* 44 */       result[i] = ri;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\animation\LinearInterpolator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */