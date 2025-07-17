/*    */ package com.modularwarfare.utility.maths;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MathUtils
/*    */ {
/*    */   public static final float degreesToRadians = 0.017453292F;
/*    */   
/*    */   public static int clamp(int x, int min, int max) {
/* 11 */     return (x < min) ? min : ((x > max) ? max : x);
/*    */   }
/*    */ 
/*    */   
/*    */   public static float clamp(float x, float min, float max) {
/* 16 */     return (x < min) ? min : ((x > max) ? max : x);
/*    */   }
/*    */ 
/*    */   
/*    */   public static double clamp(double x, double min, double max) {
/* 21 */     return (x < min) ? min : ((x > max) ? max : x);
/*    */   }
/*    */ 
/*    */   
/*    */   public static long clamp(long x, long min, long max) {
/* 26 */     return (x < min) ? min : ((x > max) ? max : x);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int cycler(int x, int min, int max) {
/* 31 */     return (x < min) ? max : ((x > max) ? min : x);
/*    */   }
/*    */ 
/*    */   
/*    */   public static float cycler(float x, float min, float max) {
/* 36 */     return (x < min) ? max : ((x > max) ? min : x);
/*    */   }
/*    */ 
/*    */   
/*    */   public static double cycler(double x, double min, double max) {
/* 41 */     return (x < min) ? max : ((x > max) ? min : x);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int gridIndex(int x, int y, int size, int width) {
/* 46 */     x /= size;
/* 47 */     y /= size;
/*    */     
/* 49 */     return x + y * width / size;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int gridRows(int count, int size, int width) {
/* 54 */     double x = (count * size) / width;
/*    */     
/* 56 */     return (count <= 0) ? 1 : (int)Math.ceil(x);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float[] getQuat(float yaw, float pitch, float roll) {
/* 65 */     return setEulerAnglesRad(yaw * 0.017453292F, pitch * 0.017453292F, roll * 0.017453292F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float[] setEulerAnglesRad(float yaw, float pitch, float roll) {
/* 75 */     float hr = roll * 0.5F;
/* 76 */     float shr = (float)Math.sin(hr);
/* 77 */     float chr = (float)Math.cos(hr);
/* 78 */     float hp = pitch * 0.5F;
/* 79 */     float shp = (float)Math.sin(hp);
/* 80 */     float chp = (float)Math.cos(hp);
/* 81 */     float hy = yaw * 0.5F;
/* 82 */     float shy = (float)Math.sin(hy);
/* 83 */     float chy = (float)Math.cos(hy);
/* 84 */     float chy_shp = chy * shp;
/* 85 */     float shy_chp = shy * chp;
/* 86 */     float chy_chp = chy * chp;
/* 87 */     float shy_shp = shy * shp;
/*    */     
/* 89 */     float[] quat = new float[4];
/* 90 */     quat[0] = chy_shp * chr + shy_chp * shr;
/* 91 */     quat[1] = shy_chp * chr - chy_shp * shr;
/* 92 */     quat[2] = chy_chp * shr - shy_shp * chr;
/* 93 */     quat[3] = chy_chp * chr + shy_shp * shr;
/* 94 */     return quat;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfar\\utility\maths\MathUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */