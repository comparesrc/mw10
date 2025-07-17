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
/*    */ 
/*    */ class SlerpQuaternionInterpolator
/*    */   implements Interpolator
/*    */ {
/*    */   public void interpolate(float[] a, float[] b, float alpha, float[] result) {
/* 40 */     float s0, s1, ax = a[0];
/* 41 */     float ay = a[1];
/* 42 */     float az = a[2];
/* 43 */     float aw = a[3];
/* 44 */     float bx = b[0];
/* 45 */     float by = b[1];
/* 46 */     float bz = b[2];
/* 47 */     float bw = b[3];
/*    */     
/* 49 */     float dot = ax * bx + ay * by + az * bz + aw * bw;
/* 50 */     if (dot < 0.0F) {
/*    */       
/* 52 */       bx = -bx;
/* 53 */       by = -by;
/* 54 */       bz = -bz;
/* 55 */       bw = -bw;
/* 56 */       dot = -dot;
/*    */     } 
/* 58 */     float epsilon = 1.0E-6F;
/*    */     
/* 60 */     if (1.0D - dot > epsilon) {
/*    */       
/* 62 */       float omega = (float)Math.acos(dot);
/* 63 */       float invSinOmega = 1.0F / (float)Math.sin(omega);
/* 64 */       s0 = (float)Math.sin((1.0D - alpha) * omega) * invSinOmega;
/* 65 */       s1 = (float)Math.sin((alpha * omega)) * invSinOmega;
/*    */     }
/*    */     else {
/*    */       
/* 69 */       s0 = 1.0F - alpha;
/* 70 */       s1 = alpha;
/*    */     } 
/* 72 */     float rx = s0 * ax + s1 * bx;
/* 73 */     float ry = s0 * ay + s1 * by;
/* 74 */     float rz = s0 * az + s1 * bz;
/* 75 */     float rw = s0 * aw + s1 * bw;
/* 76 */     result[0] = rx;
/* 77 */     result[1] = ry;
/* 78 */     result[2] = rz;
/* 79 */     result[3] = rw;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\animation\SlerpQuaternionInterpolator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */