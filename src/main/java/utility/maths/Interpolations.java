/*     */ package com.modularwarfare.utility.maths;
/*     */ 
/*     */ import net.minecraft.util.math.MathHelper;
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
/*     */ public class Interpolations
/*     */ {
/*     */   public static float lerp(float a, float b, float position) {
/*  22 */     return a + (b - a) * position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float lerpYaw(float a, float b, float position) {
/*  34 */     a = MathHelper.func_76142_g(a);
/*  35 */     b = MathHelper.func_76142_g(b);
/*     */     
/*  37 */     return lerp(a, normalizeYaw(a, b), position);
/*     */   }
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
/*     */   public static double cubicHermite(double y0, double y1, double y2, double y3, double x) {
/*  52 */     double a = -0.5D * y0 + 1.5D * y1 - 1.5D * y2 + 0.5D * y3;
/*  53 */     double b = y0 - 2.5D * y1 + 2.0D * y2 - 0.5D * y3;
/*  54 */     double c = -0.5D * y0 + 0.5D * y2;
/*     */     
/*  56 */     return ((a * x + b) * x + c) * x + y1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double cubicHermiteYaw(float y0, float y1, float y2, float y3, float position) {
/*  64 */     y0 = MathHelper.func_76142_g(y0);
/*  65 */     y1 = MathHelper.func_76142_g(y1);
/*  66 */     y2 = MathHelper.func_76142_g(y2);
/*  67 */     y3 = MathHelper.func_76142_g(y3);
/*     */     
/*  69 */     y1 = normalizeYaw(y0, y1);
/*  70 */     y2 = normalizeYaw(y1, y2);
/*  71 */     y3 = normalizeYaw(y2, y3);
/*     */     
/*  73 */     return cubicHermite(y0, y1, y2, y3, position);
/*     */   }
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
/*     */   public static float cubic(float y0, float y1, float y2, float y3, float x) {
/*  87 */     float a = y3 - y2 - y0 + y1;
/*  88 */     float b = y0 - y1 - a;
/*  89 */     float c = y2 - y0;
/*     */     
/*  91 */     return ((a * x + b) * x + c) * x + y1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float cubicYaw(float y0, float y1, float y2, float y3, float position) {
/*  99 */     y0 = MathHelper.func_76142_g(y0);
/* 100 */     y1 = MathHelper.func_76142_g(y1);
/* 101 */     y2 = MathHelper.func_76142_g(y2);
/* 102 */     y3 = MathHelper.func_76142_g(y3);
/*     */     
/* 104 */     y1 = normalizeYaw(y0, y1);
/* 105 */     y2 = normalizeYaw(y1, y2);
/* 106 */     y3 = normalizeYaw(y2, y3);
/*     */     
/* 108 */     return cubic(y0, y1, y2, y3, position);
/*     */   }
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
/*     */   public static float bezierX(float x1, float x2, float t, float epsilon) {
/* 122 */     float x = t;
/* 123 */     float init = bezier(0.0F, x1, x2, 1.0F, t);
/* 124 */     float factor = Math.copySign(0.1F, t - init);
/*     */     
/* 126 */     while (Math.abs(t - init) > epsilon) {
/*     */       
/* 128 */       float oldFactor = factor;
/*     */       
/* 130 */       x += factor;
/* 131 */       init = bezier(0.0F, x1, x2, 1.0F, x);
/*     */       
/* 133 */       if (Math.copySign(factor, t - init) != oldFactor)
/*     */       {
/* 135 */         factor *= -0.25F;
/*     */       }
/*     */     } 
/*     */     
/* 139 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float bezierX(float x1, float x2, float t) {
/* 148 */     return bezierX(x1, x2, t, 5.0E-4F);
/*     */   }
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
/*     */   public static float bezier(float x1, float x2, float x3, float x4, float t) {
/* 162 */     float t1 = lerp(x1, x2, t);
/* 163 */     float t2 = lerp(x2, x3, t);
/* 164 */     float t3 = lerp(x3, x4, t);
/* 165 */     float t4 = lerp(t1, t2, t);
/* 166 */     float t5 = lerp(t2, t3, t);
/*     */     
/* 168 */     return lerp(t4, t5, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float normalizeYaw(float a, float b) {
/* 177 */     float diff = a - b;
/*     */     
/* 179 */     if (diff > 180.0F || diff < -180.0F) {
/*     */       
/* 181 */       diff = Math.copySign(360.0F - Math.abs(diff), diff);
/*     */       
/* 183 */       return a + diff;
/*     */     } 
/*     */     
/* 186 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float envelope(float x, float duration, float fades) {
/* 196 */     return envelope(x, 0.0F, fades, duration - fades, duration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float envelope(float x, float lowIn, float lowOut, float highIn, float highOut) {
/* 206 */     if (x < lowIn || x > highOut) return 0.0F; 
/* 207 */     if (x < lowOut) return (x - lowIn) / (lowOut - lowIn); 
/* 208 */     if (x > highIn) return 1.0F - (x - highIn) / (highOut - highIn);
/*     */     
/* 210 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double lerp(double a, double b, double position) {
/* 220 */     return a + (b - a) * position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double lerpYaw(double a, double b, double position) {
/* 232 */     a = MathHelper.func_76138_g(a);
/* 233 */     b = MathHelper.func_76138_g(b);
/*     */     
/* 235 */     return lerp(a, normalizeYaw(a, b), position);
/*     */   }
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
/*     */   public static double cubic(double y0, double y1, double y2, double y3, double x) {
/* 249 */     double a = y3 - y2 - y0 + y1;
/* 250 */     double b = y0 - y1 - a;
/* 251 */     double c = y2 - y0;
/*     */     
/* 253 */     return ((a * x + b) * x + c) * x + y1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double cubicYaw(double y0, double y1, double y2, double y3, double position) {
/* 261 */     y0 = MathHelper.func_76138_g(y0);
/* 262 */     y1 = MathHelper.func_76138_g(y1);
/* 263 */     y2 = MathHelper.func_76138_g(y2);
/* 264 */     y3 = MathHelper.func_76138_g(y3);
/*     */     
/* 266 */     y1 = normalizeYaw(y0, y1);
/* 267 */     y2 = normalizeYaw(y1, y2);
/* 268 */     y3 = normalizeYaw(y2, y3);
/*     */     
/* 270 */     return cubic(y0, y1, y2, y3, position);
/*     */   }
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
/*     */   public static double bezierX(double x1, double x2, double t, double epsilon) {
/* 284 */     double x = t;
/* 285 */     double init = bezier(0.0D, x1, x2, 1.0D, t);
/* 286 */     double factor = Math.copySign(0.10000000149011612D, t - init);
/*     */     
/* 288 */     while (Math.abs(t - init) > epsilon) {
/*     */       
/* 290 */       double oldFactor = factor;
/*     */       
/* 292 */       x += factor;
/* 293 */       init = bezier(0.0D, x1, x2, 1.0D, x);
/*     */       
/* 295 */       if (Math.copySign(factor, t - init) != oldFactor)
/*     */       {
/* 297 */         factor *= -0.25D;
/*     */       }
/*     */     } 
/*     */     
/* 301 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double bezierX(double x1, double x2, float t) {
/* 310 */     return bezierX(x1, x2, t, 5.000000237487257E-4D);
/*     */   }
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
/*     */   public static double bezier(double x1, double x2, double x3, double x4, double t) {
/* 324 */     double t1 = lerp(x1, x2, t);
/* 325 */     double t2 = lerp(x2, x3, t);
/* 326 */     double t3 = lerp(x3, x4, t);
/* 327 */     double t4 = lerp(t1, t2, t);
/* 328 */     double t5 = lerp(t2, t3, t);
/*     */     
/* 330 */     return lerp(t4, t5, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double normalizeYaw(double a, double b) {
/* 339 */     double diff = a - b;
/*     */     
/* 341 */     if (diff > 180.0D || diff < -180.0D) {
/*     */       
/* 343 */       diff = Math.copySign(360.0D - Math.abs(diff), diff);
/*     */       
/* 345 */       return a + diff;
/*     */     } 
/*     */     
/* 348 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double envelope(double x, double duration, double fades) {
/* 358 */     return envelope(x, 0.0D, fades, duration - fades, duration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double envelope(double x, double lowIn, double lowOut, double highIn, double highOut) {
/* 368 */     if (x < lowIn || x > highOut) return 0.0D; 
/* 369 */     if (x < lowOut) return (x - lowIn) / (lowOut - lowIn); 
/* 370 */     if (x > highIn) return 1.0D - (x - highIn) / (highOut - highIn);
/*     */     
/* 372 */     return 1.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfar\\utility\maths\Interpolations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */