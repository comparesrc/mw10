/*     */ package de.javagl.jgltf.model;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class MathUtils
/*     */ {
/*  56 */   private static final Logger logger = Logger.getLogger(MathUtils.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float FLOAT_EPSILON = 1.0E-8F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] createIdentity4x4() {
/*  70 */     float[] m = new float[16];
/*  71 */     setIdentity4x4(m);
/*  72 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setIdentity4x4(float[] m) {
/*  82 */     Arrays.fill(m, 0.0F);
/*  83 */     m[0] = 1.0F;
/*  84 */     m[5] = 1.0F;
/*  85 */     m[10] = 1.0F;
/*  86 */     m[15] = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void setIdentity3x3(float[] m) {
/*  96 */     Arrays.fill(m, 0.0F);
/*  97 */     m[0] = 1.0F;
/*  98 */     m[4] = 1.0F;
/*  99 */     m[8] = 1.0F;
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
/*     */   static void set(float[] source, float[] target) {
/* 112 */     System.arraycopy(source, 0, target, 0, 
/* 113 */         Math.min(source.length, target.length));
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
/*     */   public static void getRotationScale(float[] sourceMatrix4x4, float[] targetMatrix3x3) {
/* 127 */     targetMatrix3x3[0] = sourceMatrix4x4[0];
/* 128 */     targetMatrix3x3[1] = sourceMatrix4x4[1];
/* 129 */     targetMatrix3x3[2] = sourceMatrix4x4[2];
/* 130 */     targetMatrix3x3[3] = sourceMatrix4x4[4];
/* 131 */     targetMatrix3x3[4] = sourceMatrix4x4[5];
/* 132 */     targetMatrix3x3[5] = sourceMatrix4x4[6];
/* 133 */     targetMatrix3x3[6] = sourceMatrix4x4[8];
/* 134 */     targetMatrix3x3[7] = sourceMatrix4x4[9];
/* 135 */     targetMatrix3x3[8] = sourceMatrix4x4[10];
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
/*     */   static void transpose3x3(float[] m, float[] t) {
/* 148 */     float m0 = m[0];
/* 149 */     float m1 = m[1];
/* 150 */     float m2 = m[2];
/* 151 */     float m3 = m[3];
/* 152 */     float m4 = m[4];
/* 153 */     float m5 = m[5];
/* 154 */     float m6 = m[6];
/* 155 */     float m7 = m[7];
/* 156 */     float m8 = m[8];
/* 157 */     t[0] = m0;
/* 158 */     t[1] = m3;
/* 159 */     t[2] = m6;
/* 160 */     t[3] = m1;
/* 161 */     t[4] = m4;
/* 162 */     t[5] = m7;
/* 163 */     t[6] = m2;
/* 164 */     t[7] = m5;
/* 165 */     t[8] = m8;
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
/*     */   public static void transpose4x4(float[] m, float[] t) {
/* 177 */     float m0 = m[0];
/* 178 */     float m1 = m[1];
/* 179 */     float m2 = m[2];
/* 180 */     float m3 = m[3];
/* 181 */     float m4 = m[4];
/* 182 */     float m5 = m[5];
/* 183 */     float m6 = m[6];
/* 184 */     float m7 = m[7];
/* 185 */     float m8 = m[8];
/* 186 */     float m9 = m[9];
/* 187 */     float mA = m[10];
/* 188 */     float mB = m[11];
/* 189 */     float mC = m[12];
/* 190 */     float mD = m[13];
/* 191 */     float mE = m[14];
/* 192 */     float mF = m[15];
/* 193 */     t[0] = m0;
/* 194 */     t[1] = m4;
/* 195 */     t[2] = m8;
/* 196 */     t[3] = mC;
/* 197 */     t[4] = m1;
/* 198 */     t[5] = m5;
/* 199 */     t[6] = m9;
/* 200 */     t[7] = mD;
/* 201 */     t[8] = m2;
/* 202 */     t[9] = m6;
/* 203 */     t[10] = mA;
/* 204 */     t[11] = mE;
/* 205 */     t[12] = m3;
/* 206 */     t[13] = m7;
/* 207 */     t[14] = mB;
/* 208 */     t[15] = mF;
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
/*     */   public static void mul4x4(float[] a, float[] b, float[] m) {
/* 220 */     float a00 = a[0];
/* 221 */     float a10 = a[1];
/* 222 */     float a20 = a[2];
/* 223 */     float a30 = a[3];
/* 224 */     float a01 = a[4];
/* 225 */     float a11 = a[5];
/* 226 */     float a21 = a[6];
/* 227 */     float a31 = a[7];
/* 228 */     float a02 = a[8];
/* 229 */     float a12 = a[9];
/* 230 */     float a22 = a[10];
/* 231 */     float a32 = a[11];
/* 232 */     float a03 = a[12];
/* 233 */     float a13 = a[13];
/* 234 */     float a23 = a[14];
/* 235 */     float a33 = a[15];
/*     */     
/* 237 */     float b00 = b[0];
/* 238 */     float b10 = b[1];
/* 239 */     float b20 = b[2];
/* 240 */     float b30 = b[3];
/* 241 */     float b01 = b[4];
/* 242 */     float b11 = b[5];
/* 243 */     float b21 = b[6];
/* 244 */     float b31 = b[7];
/* 245 */     float b02 = b[8];
/* 246 */     float b12 = b[9];
/* 247 */     float b22 = b[10];
/* 248 */     float b32 = b[11];
/* 249 */     float b03 = b[12];
/* 250 */     float b13 = b[13];
/* 251 */     float b23 = b[14];
/* 252 */     float b33 = b[15];
/*     */     
/* 254 */     float m00 = a00 * b00 + a01 * b10 + a02 * b20 + a03 * b30;
/* 255 */     float m01 = a00 * b01 + a01 * b11 + a02 * b21 + a03 * b31;
/* 256 */     float m02 = a00 * b02 + a01 * b12 + a02 * b22 + a03 * b32;
/* 257 */     float m03 = a00 * b03 + a01 * b13 + a02 * b23 + a03 * b33;
/*     */     
/* 259 */     float m10 = a10 * b00 + a11 * b10 + a12 * b20 + a13 * b30;
/* 260 */     float m11 = a10 * b01 + a11 * b11 + a12 * b21 + a13 * b31;
/* 261 */     float m12 = a10 * b02 + a11 * b12 + a12 * b22 + a13 * b32;
/* 262 */     float m13 = a10 * b03 + a11 * b13 + a12 * b23 + a13 * b33;
/*     */     
/* 264 */     float m20 = a20 * b00 + a21 * b10 + a22 * b20 + a23 * b30;
/* 265 */     float m21 = a20 * b01 + a21 * b11 + a22 * b21 + a23 * b31;
/* 266 */     float m22 = a20 * b02 + a21 * b12 + a22 * b22 + a23 * b32;
/* 267 */     float m23 = a20 * b03 + a21 * b13 + a22 * b23 + a23 * b33;
/*     */     
/* 269 */     float m30 = a30 * b00 + a31 * b10 + a32 * b20 + a33 * b30;
/* 270 */     float m31 = a30 * b01 + a31 * b11 + a32 * b21 + a33 * b31;
/* 271 */     float m32 = a30 * b02 + a31 * b12 + a32 * b22 + a33 * b32;
/* 272 */     float m33 = a30 * b03 + a31 * b13 + a32 * b23 + a33 * b33;
/*     */     
/* 274 */     m[0] = m00;
/* 275 */     m[1] = m10;
/* 276 */     m[2] = m20;
/* 277 */     m[3] = m30;
/* 278 */     m[4] = m01;
/* 279 */     m[5] = m11;
/* 280 */     m[6] = m21;
/* 281 */     m[7] = m31;
/* 282 */     m[8] = m02;
/* 283 */     m[9] = m12;
/* 284 */     m[10] = m22;
/* 285 */     m[11] = m32;
/* 286 */     m[12] = m03;
/* 287 */     m[13] = m13;
/* 288 */     m[14] = m23;
/* 289 */     m[15] = m33;
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
/*     */   public static void quaternionToMatrix4x4(float[] q, float[] m) {
/* 303 */     float invLength = 1.0F / (float)Math.sqrt(dot(q, q));
/*     */ 
/*     */     
/* 306 */     float qx = q[0] * invLength;
/* 307 */     float qy = q[1] * invLength;
/* 308 */     float qz = q[2] * invLength;
/* 309 */     float qw = q[3] * invLength;
/* 310 */     m[0] = 1.0F - 2.0F * qy * qy - 2.0F * qz * qz;
/* 311 */     m[1] = 2.0F * (qx * qy + qw * qz);
/* 312 */     m[2] = 2.0F * (qx * qz - qw * qy);
/* 313 */     m[3] = 0.0F;
/* 314 */     m[4] = 2.0F * (qx * qy - qw * qz);
/* 315 */     m[5] = 1.0F - 2.0F * qx * qx - 2.0F * qz * qz;
/* 316 */     m[6] = 2.0F * (qy * qz + qw * qx);
/* 317 */     m[7] = 0.0F;
/* 318 */     m[8] = 2.0F * (qx * qz + qw * qy);
/* 319 */     m[9] = 2.0F * (qy * qz - qw * qx);
/* 320 */     m[10] = 1.0F - 2.0F * qx * qx - 2.0F * qy * qy;
/* 321 */     m[11] = 0.0F;
/* 322 */     m[12] = 0.0F;
/* 323 */     m[13] = 0.0F;
/* 324 */     m[14] = 0.0F;
/* 325 */     m[15] = 1.0F;
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
/*     */   
/*     */   public static void invert4x4(float[] m, float[] inv) {
/* 341 */     float m0 = m[0];
/* 342 */     float m1 = m[1];
/* 343 */     float m2 = m[2];
/* 344 */     float m3 = m[3];
/* 345 */     float m4 = m[4];
/* 346 */     float m5 = m[5];
/* 347 */     float m6 = m[6];
/* 348 */     float m7 = m[7];
/* 349 */     float m8 = m[8];
/* 350 */     float m9 = m[9];
/* 351 */     float mA = m[10];
/* 352 */     float mB = m[11];
/* 353 */     float mC = m[12];
/* 354 */     float mD = m[13];
/* 355 */     float mE = m[14];
/* 356 */     float mF = m[15];
/*     */     
/* 358 */     inv[0] = m5 * mA * mF - m5 * mB * mE - m9 * m6 * mF + m9 * m7 * mE + mD * m6 * mB - mD * m7 * mA;
/*     */     
/* 360 */     inv[4] = -m4 * mA * mF + m4 * mB * mE + m8 * m6 * mF - m8 * m7 * mE - mC * m6 * mB + mC * m7 * mA;
/*     */     
/* 362 */     inv[8] = m4 * m9 * mF - m4 * mB * mD - m8 * m5 * mF + m8 * m7 * mD + mC * m5 * mB - mC * m7 * m9;
/*     */     
/* 364 */     inv[12] = -m4 * m9 * mE + m4 * mA * mD + m8 * m5 * mE - m8 * m6 * mD - mC * m5 * mA + mC * m6 * m9;
/*     */     
/* 366 */     inv[1] = -m1 * mA * mF + m1 * mB * mE + m9 * m2 * mF - m9 * m3 * mE - mD * m2 * mB + mD * m3 * mA;
/*     */     
/* 368 */     inv[5] = m0 * mA * mF - m0 * mB * mE - m8 * m2 * mF + m8 * m3 * mE + mC * m2 * mB - mC * m3 * mA;
/*     */     
/* 370 */     inv[9] = -m0 * m9 * mF + m0 * mB * mD + m8 * m1 * mF - m8 * m3 * mD - mC * m1 * mB + mC * m3 * m9;
/*     */     
/* 372 */     inv[13] = m0 * m9 * mE - m0 * mA * mD - m8 * m1 * mE + m8 * m2 * mD + mC * m1 * mA - mC * m2 * m9;
/*     */     
/* 374 */     inv[2] = m1 * m6 * mF - m1 * m7 * mE - m5 * m2 * mF + m5 * m3 * mE + mD * m2 * m7 - mD * m3 * m6;
/*     */     
/* 376 */     inv[6] = -m0 * m6 * mF + m0 * m7 * mE + m4 * m2 * mF - m4 * m3 * mE - mC * m2 * m7 + mC * m3 * m6;
/*     */     
/* 378 */     inv[10] = m0 * m5 * mF - m0 * m7 * mD - m4 * m1 * mF + m4 * m3 * mD + mC * m1 * m7 - mC * m3 * m5;
/*     */     
/* 380 */     inv[14] = -m0 * m5 * mE + m0 * m6 * mD + m4 * m1 * mE - m4 * m2 * mD - mC * m1 * m6 + mC * m2 * m5;
/*     */     
/* 382 */     inv[3] = -m1 * m6 * mB + m1 * m7 * mA + m5 * m2 * mB - m5 * m3 * mA - m9 * m2 * m7 + m9 * m3 * m6;
/*     */     
/* 384 */     inv[7] = m0 * m6 * mB - m0 * m7 * mA - m4 * m2 * mB + m4 * m3 * mA + m8 * m2 * m7 - m8 * m3 * m6;
/*     */     
/* 386 */     inv[11] = -m0 * m5 * mB + m0 * m7 * m9 + m4 * m1 * mB - m4 * m3 * m9 - m8 * m1 * m7 + m8 * m3 * m5;
/*     */     
/* 388 */     inv[15] = m0 * m5 * mA - m0 * m6 * m9 - m4 * m1 * mA + m4 * m2 * m9 + m8 * m1 * m6 - m8 * m2 * m5;
/*     */ 
/*     */ 
/*     */     
/* 392 */     float det = m0 * inv[0] + m1 * inv[4] + m2 * inv[8] + m3 * inv[12];
/* 393 */     if (Math.abs(det) <= 1.0E-8F) {
/*     */       
/* 395 */       if (logger.isLoggable(Level.FINE))
/*     */       {
/* 397 */         logger.fine("Matrix is not invertible, determinant is " + det + ", returning identity");
/*     */       }
/*     */       
/* 400 */       setIdentity4x4(inv);
/*     */       return;
/*     */     } 
/* 403 */     float invDet = 1.0F / det;
/* 404 */     for (int i = 0; i < 16; i++)
/*     */     {
/* 406 */       inv[i] = inv[i] * invDet;
/*     */     }
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
/*     */   public static void invert3x3(float[] m, float[] inv) {
/* 421 */     float m0 = m[0];
/* 422 */     float m1 = m[1];
/* 423 */     float m2 = m[2];
/* 424 */     float m3 = m[3];
/* 425 */     float m4 = m[4];
/* 426 */     float m5 = m[5];
/* 427 */     float m6 = m[6];
/* 428 */     float m7 = m[7];
/* 429 */     float m8 = m[8];
/* 430 */     float det = m0 * (m4 * m8 - m5 * m7) - m3 * (m1 * m8 - m7 * m2) + m6 * (m1 * m5 - m4 * m2);
/*     */ 
/*     */     
/* 433 */     if (Math.abs(det) <= 1.0E-8F) {
/*     */       
/* 435 */       if (logger.isLoggable(Level.FINE))
/*     */       {
/* 437 */         logger.fine("Matrix is not invertible, determinant is " + det + ", returning identity");
/*     */       }
/*     */       
/* 440 */       setIdentity3x3(inv);
/*     */       return;
/*     */     } 
/* 443 */     float invDet = 1.0F / det;
/* 444 */     inv[0] = (m4 * m8 - m5 * m7) * invDet;
/* 445 */     inv[3] = (m6 * m5 - m3 * m8) * invDet;
/* 446 */     inv[6] = (m3 * m7 - m6 * m4) * invDet;
/* 447 */     inv[1] = (m7 * m2 - m1 * m8) * invDet;
/* 448 */     inv[4] = (m0 * m8 - m6 * m2) * invDet;
/* 449 */     inv[7] = (m1 * m6 - m0 * m7) * invDet;
/* 450 */     inv[2] = (m1 * m5 - m2 * m4) * invDet;
/* 451 */     inv[5] = (m2 * m3 - m0 * m5) * invDet;
/* 452 */     inv[8] = (m0 * m4 - m1 * m3) * invDet;
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
/*     */   
/*     */   public static void translate(float[] m, float x, float y, float z, float[] result) {
/* 468 */     set(m, result);
/* 469 */     result[12] = result[12] + x;
/* 470 */     result[13] = result[13] + y;
/* 471 */     result[14] = result[14] + z;
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
/*     */   public static void infinitePerspective4x4(float fovyDeg, float aspect, float zNear, float[] m) {
/* 486 */     setIdentity4x4(m);
/* 487 */     float fovyRad = (float)Math.toRadians(fovyDeg);
/* 488 */     float t = (float)Math.tan(0.5D * fovyRad);
/* 489 */     m[0] = 1.0F / aspect * t;
/* 490 */     m[5] = 1.0F / t;
/* 491 */     m[10] = -1.0F;
/* 492 */     m[11] = -1.0F;
/* 493 */     m[14] = 2.0F * zNear;
/* 494 */     m[15] = 0.0F;
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
/*     */   
/*     */   public static void perspective4x4(float fovyDeg, float aspect, float zNear, float zFar, float[] m) {
/* 510 */     setIdentity4x4(m);
/* 511 */     float fovyRad = (float)Math.toRadians(fovyDeg);
/* 512 */     float t = (float)Math.tan(0.5D * fovyRad);
/* 513 */     m[0] = 1.0F / aspect * t;
/* 514 */     m[5] = 1.0F / t;
/* 515 */     m[10] = (zFar + zNear) / (zNear - zFar);
/* 516 */     m[11] = -1.0F;
/* 517 */     m[14] = 2.0F * zFar * zNear / (zNear - zFar);
/* 518 */     m[15] = 0.0F;
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
/*     */   private static float dot(float[] a, float[] b) {
/* 532 */     float sum = 0.0F;
/* 533 */     for (int i = 0; i < a.length; i++)
/*     */     {
/* 535 */       sum += a[i] * b[i];
/*     */     }
/* 537 */     return sum;
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
/*     */   
/*     */   public static void transformPoint3D(float[] matrix4x4, float[] point3D, float[] result3D) {
/* 553 */     Arrays.fill(result3D, 0.0F);
/* 554 */     for (int r = 0; r < 3; r++) {
/*     */       
/* 556 */       for (int c = 0; c < 3; c++) {
/*     */         
/* 558 */         int i = c * 4 + r;
/* 559 */         float f = matrix4x4[i];
/* 560 */         result3D[r] = result3D[r] + f * point3D[c];
/*     */       } 
/* 562 */       int index = 12 + r;
/* 563 */       float m = matrix4x4[index];
/* 564 */       result3D[r] = result3D[r] + m;
/*     */     } 
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
/*     */   public static String createMatrixString(float[] array) {
/* 579 */     if (array == null)
/*     */     {
/* 581 */       return "null";
/*     */     }
/* 583 */     if (array.length == 9)
/*     */     {
/* 585 */       return createMatrixString(array, 3, 3);
/*     */     }
/* 587 */     if (array.length == 16)
/*     */     {
/* 589 */       return createMatrixString(array, 4, 4);
/*     */     }
/* 591 */     return "WARNING: Not a matrix: " + Arrays.toString(array);
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
/*     */   private static String createMatrixString(float[] array, int rows, int cols) {
/* 605 */     StringBuilder sb = new StringBuilder();
/* 606 */     for (int r = 0; r < rows; r++) {
/*     */       
/* 608 */       for (int c = 0; c < cols; c++) {
/*     */         
/* 610 */         sb.append(array[r + c * cols]);
/* 611 */         sb.append(", ");
/*     */       } 
/* 613 */       sb.append("\n");
/*     */     } 
/* 615 */     return sb.toString();
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
/*     */ 
/*     */   
/*     */   public static String createFormattedMatrixString(float[] array) {
/* 632 */     if (array == null)
/*     */     {
/* 634 */       return "null";
/*     */     }
/* 636 */     String format = "%10.5f ";
/* 637 */     if (array.length == 9)
/*     */     {
/* 639 */       return createFormattedMatrixString(array, 3, 3, format);
/*     */     }
/* 641 */     if (array.length == 16)
/*     */     {
/* 643 */       return createFormattedMatrixString(array, 4, 4, format);
/*     */     }
/* 645 */     return "WARNING: Not a matrix: " + Arrays.toString(array);
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
/*     */ 
/*     */   
/*     */   private static String createFormattedMatrixString(float[] array, int rows, int cols, String format) {
/* 662 */     StringBuilder sb = new StringBuilder();
/* 663 */     for (int r = 0; r < rows; r++) {
/*     */       
/* 665 */       for (int c = 0; c < cols; c++) {
/*     */         
/* 667 */         sb.append(String.format(Locale.ENGLISH, format, new Object[] {
/* 668 */                 Float.valueOf(array[r + c * cols]) }));
/*     */       } 
/* 670 */       sb.append("\n");
/*     */     } 
/* 672 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\MathUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */