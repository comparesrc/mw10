/*    */ package com.modularwarfare.common.vector;
/*    */ 
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class Matrix3f
/*    */ {
/*  8 */   float[][] matrix = new float[3][3];
/*    */   
/*    */   public Matrix3f(float[][] Matrix) {
/* 11 */     for (int i = 0; i < 3; i++) {
/* 12 */       System.arraycopy(Matrix[i], 0, this.matrix[i], 0, 3);
/*    */     }
/*    */   }
/*    */   
/*    */   public Matrix3f(float m11, float m12, float m13, float m21, float m22, float m23, float m31, float m32, float m33) {
/* 17 */     this.matrix[0][0] = m11;
/* 18 */     this.matrix[0][1] = m12;
/* 19 */     this.matrix[0][2] = m13;
/* 20 */     this.matrix[1][0] = m21;
/* 21 */     this.matrix[1][1] = m22;
/* 22 */     this.matrix[1][2] = m23;
/* 23 */     this.matrix[2][0] = m31;
/* 24 */     this.matrix[2][1] = m32;
/* 25 */     this.matrix[2][2] = m33;
/*    */   }
/*    */   
/*    */   public static Matrix3f getMatrixRotX(float r) {
/* 29 */     float sn = MathHelper.func_76126_a(r);
/* 30 */     float cs = MathHelper.func_76134_b(r);
/*    */     
/* 32 */     return new Matrix3f(new float[][] { { 1.0F, 0.0F, 0.0F }, { 0.0F, cs, -sn }, { 0.0F, sn, cs } });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Matrix3f getMatrixRotY(float r) {
/* 41 */     float sn = MathHelper.func_76126_a(r);
/* 42 */     float cs = MathHelper.func_76134_b(r);
/*    */     
/* 44 */     return new Matrix3f(new float[][] { { cs, 0.0F, sn }, { 0.0F, 1.0F, 0.0F }, { -sn, 0.0F, cs } });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Matrix3f getMatrixRotZ(float r) {
/* 53 */     float sn = MathHelper.func_76126_a(r);
/* 54 */     float cs = MathHelper.func_76134_b(r);
/*    */     
/* 56 */     return new Matrix3f(new float[][] { { cs, -sn, 0.0F }, { sn, cs, 0.0F }, { 0.0F, 0.0F, 1.0F } });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Vec3d multVec(Matrix3f m, Vec3d vec) {
/* 65 */     float[][] retMat = new float[3][3];
/* 66 */     float[] retVec = new float[3];
/*    */     
/* 68 */     for (int i = 0; i < 3; i++) {
/* 69 */       float[] row = { m.matrix[i][0], m.matrix[i][1], m.matrix[i][2] };
/* 70 */       float[] column = { (float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c };
/* 71 */       for (int sm = 0; sm < 3; ) { retVec[i] = retVec[i] + row[sm] * column[sm]; sm++; }
/*    */     
/*    */     } 
/* 74 */     return new Vec3d(retVec[0], retVec[1], retVec[2]);
/*    */   }
/*    */   
/*    */   public static Matrix3f multMatrix(Matrix3f m1, Matrix3f m2) {
/* 78 */     Matrix3f retMat = new Matrix3f(new float[3][3]);
/*    */     
/* 80 */     for (int i = 0; i < 3; i++) {
/* 81 */       float[] row = { m1.matrix[i][0], m1.matrix[i][1], m1.matrix[i][2] };
/*    */       
/* 83 */       for (int j = 0; j < 3; j++) {
/* 84 */         float[] column = { m2.matrix[0][j], m2.matrix[1][j], m2.matrix[2][j] };
/* 85 */         for (int sm = 0; sm < 3; ) { retMat.matrix[i][j] = retMat.matrix[i][j] + row[sm] * column[sm]; sm++; }
/*    */       
/*    */       } 
/*    */     } 
/* 89 */     return retMat;
/*    */   }
/*    */   
/*    */   public Matrix3f mult(Matrix3f m) {
/* 93 */     return multMatrix(this, m);
/*    */   }
/*    */   
/*    */   public Vec3d mult(Vec3d v) {
/* 97 */     return multVec(this, v);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\vector\Matrix3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */