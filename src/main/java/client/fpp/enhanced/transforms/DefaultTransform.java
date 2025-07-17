/*    */ package com.modularwarfare.client.fpp.enhanced.transforms;
/*    */ 
/*    */ public class DefaultTransform
/*    */ {
/*  5 */   public float[] translation = new float[3];
/*  6 */   public float[] rotation = new float[4];
/*  7 */   public float[] scale = new float[3];
/*  8 */   public float[] weight = new float[3];
/*    */   
/*    */   public DefaultTransform(float[] translation, float[] rotation, float[] scale, float[] weight) {
/* 11 */     if (translation != null) {
/* 12 */       this.translation = translation;
/*    */     }
/* 14 */     if (rotation != null) {
/* 15 */       this.rotation = rotation;
/*    */     }
/* 17 */     if (scale != null) {
/* 18 */       this.scale = scale;
/*    */     }
/* 20 */     if (weight != null)
/* 21 */       this.weight = weight; 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\enhanced\transforms\DefaultTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */