/*    */ package com.modularwarfare.utility;
/*    */ 
/*    */ import org.lwjgl.util.vector.Matrix4f;
/*    */ import org.lwjgl.util.vector.Vector3f;
/*    */ 
/*    */ public class NumberHelper
/*    */ {
/*    */   public static float clamp(float val, float min, float max) {
/*  9 */     return Math.max(min, Math.min(max, val));
/*    */   }
/*    */   
/*    */   public static boolean isNegative(float val) {
/* 13 */     if (val < 0.0F) {
/* 14 */       return true;
/*    */     }
/*    */     
/* 17 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean isTargetMet(float target, float current) {
/* 21 */     if (isNegative(target)) {
/* 22 */       return (current <= target);
/*    */     }
/* 24 */     return (current >= target);
/*    */   }
/*    */ 
/*    */   
/*    */   public static float addTowards(float target, float current, float value) {
/* 29 */     if (isNegative(target)) {
/* 30 */       return current - value;
/*    */     }
/* 32 */     return current + value;
/*    */   }
/*    */ 
/*    */   
/*    */   public static float generateInRange(float val) {
/* 37 */     return (float)(Math.random() * val - (val / 2.0F));
/*    */   }
/*    */   
/*    */   public static float determineValue(boolean bool, float value) {
/* 41 */     if (bool) {
/* 42 */       return -value;
/*    */     }
/* 44 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Vector3f addVector(Vector3f left, Vector3f right) {
/* 49 */     Vector3f resultVector = new Vector3f();
/* 50 */     resultVector = Vector3f.add(left, right, resultVector);
/* 51 */     return resultVector;
/*    */   }
/*    */   
/*    */   public static Vector3f subtractVector(Vector3f left, Vector3f right) {
/* 55 */     Vector3f resultVector = new Vector3f();
/* 56 */     if (right != null && left != null) {
/* 57 */       resultVector = Vector3f.sub(left, right, resultVector);
/*    */     }
/* 59 */     return resultVector;
/*    */   }
/*    */   
/*    */   public static Vector3f multiplyVector(Vector3f vector, float amount) {
/* 63 */     vector.x *= amount;
/* 64 */     vector.y *= amount;
/* 65 */     vector.z *= amount;
/* 66 */     return vector;
/*    */   }
/*    */   
/*    */   public static Vector3f divideVector(Vector3f vector, float amount) {
/* 70 */     Vector3f newVector = new Vector3f(vector.x, vector.y, vector.z);
/* 71 */     newVector.x /= amount;
/* 72 */     newVector.y /= amount;
/* 73 */     newVector.z /= amount;
/* 74 */     return newVector;
/*    */   }
/*    */   
/*    */   public static boolean isInRange(float maxValue, float currentValue) {
/* 78 */     return (currentValue <= maxValue && currentValue >= -maxValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Vector3f findLocalVectorGlobally(Vector3f in, float yaw, float pitch, float roll) {
/* 83 */     Matrix4f mat = new Matrix4f();
/* 84 */     mat.m00 = in.x;
/* 85 */     mat.m10 = in.y;
/* 86 */     mat.m20 = in.z;
/*    */     
/* 88 */     mat.rotate(-yaw * 3.1415927F / 180.0F, new Vector3f(0.0F, 1.0F, 0.0F));
/* 89 */     mat.rotate(-pitch * 3.1415927F / 180.0F, new Vector3f(0.0F, 0.0F, 1.0F));
/* 90 */     mat.rotate(-roll * 3.1415927F / 180.0F, new Vector3f(1.0F, 0.0F, 0.0F));
/* 91 */     return new Vector3f(mat.m00, mat.m10, mat.m20);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfar\\utility\NumberHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */