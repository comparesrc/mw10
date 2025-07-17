/*     */ package com.modularwarfare.common.vector;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.nio.FloatBuffer;
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
/*     */ public class Matrix4f
/*     */   extends Matrix
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public float m00;
/*     */   public float m01;
/*     */   public float m02;
/*     */   public float m03;
/*     */   public float m10;
/*     */   public float m11;
/*     */   public float m12;
/*     */   public float m13;
/*     */   public float m20;
/*     */   public float m21;
/*     */   public float m22;
/*     */   public float m23;
/*     */   public float m30;
/*     */   public float m31;
/*     */   public float m32;
/*     */   public float m33;
/*     */   
/*     */   public Matrix4f() {
/*  52 */     setIdentity();
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix4f(Matrix4f src) {
/*  57 */     load(src);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix4f setIdentity(Matrix4f m) {
/*  67 */     m.m00 = 1.0F;
/*  68 */     m.m01 = 0.0F;
/*  69 */     m.m02 = 0.0F;
/*  70 */     m.m03 = 0.0F;
/*  71 */     m.m10 = 0.0F;
/*  72 */     m.m11 = 1.0F;
/*  73 */     m.m12 = 0.0F;
/*  74 */     m.m13 = 0.0F;
/*  75 */     m.m20 = 0.0F;
/*  76 */     m.m21 = 0.0F;
/*  77 */     m.m22 = 1.0F;
/*  78 */     m.m23 = 0.0F;
/*  79 */     m.m30 = 0.0F;
/*  80 */     m.m31 = 0.0F;
/*  81 */     m.m32 = 0.0F;
/*  82 */     m.m33 = 1.0F;
/*     */     
/*  84 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix4f setZero(Matrix4f m) {
/*  94 */     m.m00 = 0.0F;
/*  95 */     m.m01 = 0.0F;
/*  96 */     m.m02 = 0.0F;
/*  97 */     m.m03 = 0.0F;
/*  98 */     m.m10 = 0.0F;
/*  99 */     m.m11 = 0.0F;
/* 100 */     m.m12 = 0.0F;
/* 101 */     m.m13 = 0.0F;
/* 102 */     m.m20 = 0.0F;
/* 103 */     m.m21 = 0.0F;
/* 104 */     m.m22 = 0.0F;
/* 105 */     m.m23 = 0.0F;
/* 106 */     m.m30 = 0.0F;
/* 107 */     m.m31 = 0.0F;
/* 108 */     m.m32 = 0.0F;
/* 109 */     m.m33 = 0.0F;
/*     */     
/* 111 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix4f load(Matrix4f src, Matrix4f dest) {
/* 122 */     if (dest == null)
/* 123 */       dest = new Matrix4f(); 
/* 124 */     dest.m00 = src.m00;
/* 125 */     dest.m01 = src.m01;
/* 126 */     dest.m02 = src.m02;
/* 127 */     dest.m03 = src.m03;
/* 128 */     dest.m10 = src.m10;
/* 129 */     dest.m11 = src.m11;
/* 130 */     dest.m12 = src.m12;
/* 131 */     dest.m13 = src.m13;
/* 132 */     dest.m20 = src.m20;
/* 133 */     dest.m21 = src.m21;
/* 134 */     dest.m22 = src.m22;
/* 135 */     dest.m23 = src.m23;
/* 136 */     dest.m30 = src.m30;
/* 137 */     dest.m31 = src.m31;
/* 138 */     dest.m32 = src.m32;
/* 139 */     dest.m33 = src.m33;
/*     */     
/* 141 */     return dest;
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
/*     */   public static Matrix4f add(Matrix4f left, Matrix4f right, Matrix4f dest) {
/* 153 */     if (dest == null) {
/* 154 */       dest = new Matrix4f();
/*     */     }
/* 156 */     left.m00 += right.m00;
/* 157 */     left.m01 += right.m01;
/* 158 */     left.m02 += right.m02;
/* 159 */     left.m03 += right.m03;
/* 160 */     left.m10 += right.m10;
/* 161 */     left.m11 += right.m11;
/* 162 */     left.m12 += right.m12;
/* 163 */     left.m13 += right.m13;
/* 164 */     left.m20 += right.m20;
/* 165 */     left.m21 += right.m21;
/* 166 */     left.m22 += right.m22;
/* 167 */     left.m23 += right.m23;
/* 168 */     left.m30 += right.m30;
/* 169 */     left.m31 += right.m31;
/* 170 */     left.m32 += right.m32;
/* 171 */     left.m33 += right.m33;
/*     */     
/* 173 */     return dest;
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
/*     */   public static Matrix4f sub(Matrix4f left, Matrix4f right, Matrix4f dest) {
/* 185 */     if (dest == null) {
/* 186 */       dest = new Matrix4f();
/*     */     }
/* 188 */     left.m00 -= right.m00;
/* 189 */     left.m01 -= right.m01;
/* 190 */     left.m02 -= right.m02;
/* 191 */     left.m03 -= right.m03;
/* 192 */     left.m10 -= right.m10;
/* 193 */     left.m11 -= right.m11;
/* 194 */     left.m12 -= right.m12;
/* 195 */     left.m13 -= right.m13;
/* 196 */     left.m20 -= right.m20;
/* 197 */     left.m21 -= right.m21;
/* 198 */     left.m22 -= right.m22;
/* 199 */     left.m23 -= right.m23;
/* 200 */     left.m30 -= right.m30;
/* 201 */     left.m31 -= right.m31;
/* 202 */     left.m32 -= right.m32;
/* 203 */     left.m33 -= right.m33;
/*     */     
/* 205 */     return dest;
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
/*     */   public static Matrix4f mul(Matrix4f left, Matrix4f right, Matrix4f dest) {
/* 217 */     if (dest == null) {
/* 218 */       dest = new Matrix4f();
/*     */     }
/* 220 */     float m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02 + left.m30 * right.m03;
/* 221 */     float m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02 + left.m31 * right.m03;
/* 222 */     float m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02 + left.m32 * right.m03;
/* 223 */     float m03 = left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02 + left.m33 * right.m03;
/* 224 */     float m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12 + left.m30 * right.m13;
/* 225 */     float m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12 + left.m31 * right.m13;
/* 226 */     float m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12 + left.m32 * right.m13;
/* 227 */     float m13 = left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12 + left.m33 * right.m13;
/* 228 */     float m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22 + left.m30 * right.m23;
/* 229 */     float m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22 + left.m31 * right.m23;
/* 230 */     float m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 + left.m32 * right.m23;
/* 231 */     float m23 = left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22 + left.m33 * right.m23;
/* 232 */     float m30 = left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30 * right.m33;
/* 233 */     float m31 = left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31 * right.m33;
/* 234 */     float m32 = left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32 * right.m33;
/* 235 */     float m33 = left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33 * right.m33;
/*     */     
/* 237 */     dest.m00 = m00;
/* 238 */     dest.m01 = m01;
/* 239 */     dest.m02 = m02;
/* 240 */     dest.m03 = m03;
/* 241 */     dest.m10 = m10;
/* 242 */     dest.m11 = m11;
/* 243 */     dest.m12 = m12;
/* 244 */     dest.m13 = m13;
/* 245 */     dest.m20 = m20;
/* 246 */     dest.m21 = m21;
/* 247 */     dest.m22 = m22;
/* 248 */     dest.m23 = m23;
/* 249 */     dest.m30 = m30;
/* 250 */     dest.m31 = m31;
/* 251 */     dest.m32 = m32;
/* 252 */     dest.m33 = m33;
/*     */     
/* 254 */     return dest;
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
/*     */   public static Vector4f transform(Matrix4f left, Vector4f right, Vector4f dest) {
/* 267 */     if (dest == null) {
/* 268 */       dest = new Vector4f();
/*     */     }
/* 270 */     float x = left.m00 * right.x + left.m10 * right.y + left.m20 * right.z + left.m30 * right.w;
/* 271 */     float y = left.m01 * right.x + left.m11 * right.y + left.m21 * right.z + left.m31 * right.w;
/* 272 */     float z = left.m02 * right.x + left.m12 * right.y + left.m22 * right.z + left.m32 * right.w;
/* 273 */     float w = left.m03 * right.x + left.m13 * right.y + left.m23 * right.z + left.m33 * right.w;
/*     */     
/* 275 */     dest.x = x;
/* 276 */     dest.y = y;
/* 277 */     dest.z = z;
/* 278 */     dest.w = w;
/*     */     
/* 280 */     return dest;
/*     */   }
/*     */   
/*     */   public static Vector3f transform(Matrix4f left, Vector3f right, Vector3f dest) {
/* 284 */     if (dest == null) {
/* 285 */       dest = new Vector3f();
/*     */     }
/* 287 */     float x = left.m00 * right.x + left.m10 * right.y + left.m20 * right.z;
/* 288 */     float y = left.m01 * right.x + left.m11 * right.y + left.m21 * right.z;
/* 289 */     float z = left.m02 * right.x + left.m12 * right.y + left.m22 * right.z;
/*     */     
/* 291 */     dest.x = x;
/* 292 */     dest.y = y;
/* 293 */     dest.z = z;
/*     */     
/* 295 */     return dest;
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
/*     */   public static Matrix4f scale(Vector3f vec, Matrix4f src, Matrix4f dest) {
/* 307 */     if (dest == null)
/* 308 */       dest = new Matrix4f(); 
/* 309 */     src.m00 *= vec.x;
/* 310 */     src.m01 *= vec.x;
/* 311 */     src.m02 *= vec.x;
/* 312 */     src.m03 *= vec.x;
/* 313 */     src.m10 *= vec.y;
/* 314 */     src.m11 *= vec.y;
/* 315 */     src.m12 *= vec.y;
/* 316 */     src.m13 *= vec.y;
/* 317 */     src.m20 *= vec.z;
/* 318 */     src.m21 *= vec.z;
/* 319 */     src.m22 *= vec.z;
/* 320 */     src.m23 *= vec.z;
/* 321 */     return dest;
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
/*     */   public static Matrix4f rotate(float angle, Vector3f axis, Matrix4f src, Matrix4f dest) {
/* 335 */     if (dest == null)
/* 336 */       dest = new Matrix4f(); 
/* 337 */     float c = (float)Math.cos(angle);
/* 338 */     float s = (float)Math.sin(angle);
/* 339 */     float oneminusc = 1.0F - c;
/* 340 */     float xy = axis.x * axis.y;
/* 341 */     float yz = axis.y * axis.z;
/* 342 */     float xz = axis.x * axis.z;
/* 343 */     float xs = axis.x * s;
/* 344 */     float ys = axis.y * s;
/* 345 */     float zs = axis.z * s;
/*     */     
/* 347 */     float f00 = axis.x * axis.x * oneminusc + c;
/* 348 */     float f01 = xy * oneminusc + zs;
/* 349 */     float f02 = xz * oneminusc - ys;
/*     */     
/* 351 */     float f10 = xy * oneminusc - zs;
/* 352 */     float f11 = axis.y * axis.y * oneminusc + c;
/* 353 */     float f12 = yz * oneminusc + xs;
/*     */     
/* 355 */     float f20 = xz * oneminusc + ys;
/* 356 */     float f21 = yz * oneminusc - xs;
/* 357 */     float f22 = axis.z * axis.z * oneminusc + c;
/*     */     
/* 359 */     float t00 = src.m00 * f00 + src.m10 * f01 + src.m20 * f02;
/* 360 */     float t01 = src.m01 * f00 + src.m11 * f01 + src.m21 * f02;
/* 361 */     float t02 = src.m02 * f00 + src.m12 * f01 + src.m22 * f02;
/* 362 */     float t03 = src.m03 * f00 + src.m13 * f01 + src.m23 * f02;
/* 363 */     float t10 = src.m00 * f10 + src.m10 * f11 + src.m20 * f12;
/* 364 */     float t11 = src.m01 * f10 + src.m11 * f11 + src.m21 * f12;
/* 365 */     float t12 = src.m02 * f10 + src.m12 * f11 + src.m22 * f12;
/* 366 */     float t13 = src.m03 * f10 + src.m13 * f11 + src.m23 * f12;
/* 367 */     dest.m20 = src.m00 * f20 + src.m10 * f21 + src.m20 * f22;
/* 368 */     dest.m21 = src.m01 * f20 + src.m11 * f21 + src.m21 * f22;
/* 369 */     dest.m22 = src.m02 * f20 + src.m12 * f21 + src.m22 * f22;
/* 370 */     dest.m23 = src.m03 * f20 + src.m13 * f21 + src.m23 * f22;
/* 371 */     dest.m00 = t00;
/* 372 */     dest.m01 = t01;
/* 373 */     dest.m02 = t02;
/* 374 */     dest.m03 = t03;
/* 375 */     dest.m10 = t10;
/* 376 */     dest.m11 = t11;
/* 377 */     dest.m12 = t12;
/* 378 */     dest.m13 = t13;
/* 379 */     return dest;
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
/*     */   public static Matrix4f translate(Vector3f vec, Matrix4f src, Matrix4f dest) {
/* 391 */     if (dest == null) {
/* 392 */       dest = new Matrix4f();
/*     */     }
/* 394 */     dest.m30 += src.m00 * vec.x + src.m10 * vec.y + src.m20 * vec.z;
/* 395 */     dest.m31 += src.m01 * vec.x + src.m11 * vec.y + src.m21 * vec.z;
/* 396 */     dest.m32 += src.m02 * vec.x + src.m12 * vec.y + src.m22 * vec.z;
/* 397 */     dest.m33 += src.m03 * vec.x + src.m13 * vec.y + src.m23 * vec.z;
/*     */     
/* 399 */     return dest;
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
/*     */   public static Matrix4f translate(Vector2f vec, Matrix4f src, Matrix4f dest) {
/* 411 */     if (dest == null) {
/* 412 */       dest = new Matrix4f();
/*     */     }
/* 414 */     dest.m30 += src.m00 * vec.x + src.m10 * vec.y;
/* 415 */     dest.m31 += src.m01 * vec.x + src.m11 * vec.y;
/* 416 */     dest.m32 += src.m02 * vec.x + src.m12 * vec.y;
/* 417 */     dest.m33 += src.m03 * vec.x + src.m13 * vec.y;
/*     */     
/* 419 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix4f transpose(Matrix4f src, Matrix4f dest) {
/* 430 */     if (dest == null)
/* 431 */       dest = new Matrix4f(); 
/* 432 */     float m00 = src.m00;
/* 433 */     float m01 = src.m10;
/* 434 */     float m02 = src.m20;
/* 435 */     float m03 = src.m30;
/* 436 */     float m10 = src.m01;
/* 437 */     float m11 = src.m11;
/* 438 */     float m12 = src.m21;
/* 439 */     float m13 = src.m31;
/* 440 */     float m20 = src.m02;
/* 441 */     float m21 = src.m12;
/* 442 */     float m22 = src.m22;
/* 443 */     float m23 = src.m32;
/* 444 */     float m30 = src.m03;
/* 445 */     float m31 = src.m13;
/* 446 */     float m32 = src.m23;
/* 447 */     float m33 = src.m33;
/*     */     
/* 449 */     dest.m00 = m00;
/* 450 */     dest.m01 = m01;
/* 451 */     dest.m02 = m02;
/* 452 */     dest.m03 = m03;
/* 453 */     dest.m10 = m10;
/* 454 */     dest.m11 = m11;
/* 455 */     dest.m12 = m12;
/* 456 */     dest.m13 = m13;
/* 457 */     dest.m20 = m20;
/* 458 */     dest.m21 = m21;
/* 459 */     dest.m22 = m22;
/* 460 */     dest.m23 = m23;
/* 461 */     dest.m30 = m30;
/* 462 */     dest.m31 = m31;
/* 463 */     dest.m32 = m32;
/* 464 */     dest.m33 = m33;
/*     */     
/* 466 */     return dest;
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
/*     */   private static float determinant3x3(float t00, float t01, float t02, float t10, float t11, float t12, float t20, float t21, float t22) {
/* 478 */     return t00 * (t11 * t22 - t12 * t21) + t01 * (t12 * t20 - t10 * t22) + t02 * (t10 * t21 - t11 * t20);
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
/*     */   public static Matrix4f invert(Matrix4f src, Matrix4f dest) {
/* 491 */     float determinant = src.determinant();
/*     */     
/* 493 */     if (determinant != 0.0F) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 500 */       if (dest == null)
/* 501 */         dest = new Matrix4f(); 
/* 502 */       float determinant_inv = 1.0F / determinant;
/*     */ 
/*     */       
/* 505 */       float t00 = determinant3x3(src.m11, src.m12, src.m13, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
/* 506 */       float t01 = -determinant3x3(src.m10, src.m12, src.m13, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
/* 507 */       float t02 = determinant3x3(src.m10, src.m11, src.m13, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
/* 508 */       float t03 = -determinant3x3(src.m10, src.m11, src.m12, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
/*     */       
/* 510 */       float t10 = -determinant3x3(src.m01, src.m02, src.m03, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
/* 511 */       float t11 = determinant3x3(src.m00, src.m02, src.m03, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
/* 512 */       float t12 = -determinant3x3(src.m00, src.m01, src.m03, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
/* 513 */       float t13 = determinant3x3(src.m00, src.m01, src.m02, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
/*     */       
/* 515 */       float t20 = determinant3x3(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m31, src.m32, src.m33);
/* 516 */       float t21 = -determinant3x3(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m30, src.m32, src.m33);
/* 517 */       float t22 = determinant3x3(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m30, src.m31, src.m33);
/* 518 */       float t23 = -determinant3x3(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m30, src.m31, src.m32);
/*     */       
/* 520 */       float t30 = -determinant3x3(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m21, src.m22, src.m23);
/* 521 */       float t31 = determinant3x3(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m20, src.m22, src.m23);
/* 522 */       float t32 = -determinant3x3(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m20, src.m21, src.m23);
/* 523 */       float t33 = determinant3x3(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m20, src.m21, src.m22);
/*     */ 
/*     */       
/* 526 */       dest.m00 = t00 * determinant_inv;
/* 527 */       dest.m11 = t11 * determinant_inv;
/* 528 */       dest.m22 = t22 * determinant_inv;
/* 529 */       dest.m33 = t33 * determinant_inv;
/* 530 */       dest.m01 = t10 * determinant_inv;
/* 531 */       dest.m10 = t01 * determinant_inv;
/* 532 */       dest.m20 = t02 * determinant_inv;
/* 533 */       dest.m02 = t20 * determinant_inv;
/* 534 */       dest.m12 = t21 * determinant_inv;
/* 535 */       dest.m21 = t12 * determinant_inv;
/* 536 */       dest.m03 = t30 * determinant_inv;
/* 537 */       dest.m30 = t03 * determinant_inv;
/* 538 */       dest.m13 = t31 * determinant_inv;
/* 539 */       dest.m31 = t13 * determinant_inv;
/* 540 */       dest.m32 = t23 * determinant_inv;
/* 541 */       dest.m23 = t32 * determinant_inv;
/* 542 */       return dest;
/*     */     } 
/* 544 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix4f negate(Matrix4f src, Matrix4f dest) {
/* 555 */     if (dest == null) {
/* 556 */       dest = new Matrix4f();
/*     */     }
/* 558 */     dest.m00 = -src.m00;
/* 559 */     dest.m01 = -src.m01;
/* 560 */     dest.m02 = -src.m02;
/* 561 */     dest.m03 = -src.m03;
/* 562 */     dest.m10 = -src.m10;
/* 563 */     dest.m11 = -src.m11;
/* 564 */     dest.m12 = -src.m12;
/* 565 */     dest.m13 = -src.m13;
/* 566 */     dest.m20 = -src.m20;
/* 567 */     dest.m21 = -src.m21;
/* 568 */     dest.m22 = -src.m22;
/* 569 */     dest.m23 = -src.m23;
/* 570 */     dest.m30 = -src.m30;
/* 571 */     dest.m31 = -src.m31;
/* 572 */     dest.m32 = -src.m32;
/* 573 */     dest.m33 = -src.m33;
/*     */     
/* 575 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 583 */     return String.valueOf(this.m00) + ' ' + this.m10 + ' ' + this.m20 + ' ' + this.m30 + '\n' + this.m01 + ' ' + this.m11 + ' ' + this.m21 + ' ' + this.m31 + '\n' + this.m02 + ' ' + this.m12 + ' ' + this.m22 + ' ' + this.m32 + '\n' + this.m03 + ' ' + this.m13 + ' ' + this.m23 + ' ' + this.m33 + '\n';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix setIdentity() {
/* 593 */     return setIdentity(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix setZero() {
/* 603 */     return setZero(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix4f load(Matrix4f src) {
/* 613 */     return load(src, this);
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
/*     */   public Matrix load(FloatBuffer buf) {
/* 626 */     this.m00 = buf.get();
/* 627 */     this.m01 = buf.get();
/* 628 */     this.m02 = buf.get();
/* 629 */     this.m03 = buf.get();
/* 630 */     this.m10 = buf.get();
/* 631 */     this.m11 = buf.get();
/* 632 */     this.m12 = buf.get();
/* 633 */     this.m13 = buf.get();
/* 634 */     this.m20 = buf.get();
/* 635 */     this.m21 = buf.get();
/* 636 */     this.m22 = buf.get();
/* 637 */     this.m23 = buf.get();
/* 638 */     this.m30 = buf.get();
/* 639 */     this.m31 = buf.get();
/* 640 */     this.m32 = buf.get();
/* 641 */     this.m33 = buf.get();
/*     */     
/* 643 */     return this;
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
/*     */   public Matrix loadTranspose(FloatBuffer buf) {
/* 656 */     this.m00 = buf.get();
/* 657 */     this.m10 = buf.get();
/* 658 */     this.m20 = buf.get();
/* 659 */     this.m30 = buf.get();
/* 660 */     this.m01 = buf.get();
/* 661 */     this.m11 = buf.get();
/* 662 */     this.m21 = buf.get();
/* 663 */     this.m31 = buf.get();
/* 664 */     this.m02 = buf.get();
/* 665 */     this.m12 = buf.get();
/* 666 */     this.m22 = buf.get();
/* 667 */     this.m32 = buf.get();
/* 668 */     this.m03 = buf.get();
/* 669 */     this.m13 = buf.get();
/* 670 */     this.m23 = buf.get();
/* 671 */     this.m33 = buf.get();
/*     */     
/* 673 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix store(FloatBuffer buf) {
/* 684 */     buf.put(this.m00);
/* 685 */     buf.put(this.m01);
/* 686 */     buf.put(this.m02);
/* 687 */     buf.put(this.m03);
/* 688 */     buf.put(this.m10);
/* 689 */     buf.put(this.m11);
/* 690 */     buf.put(this.m12);
/* 691 */     buf.put(this.m13);
/* 692 */     buf.put(this.m20);
/* 693 */     buf.put(this.m21);
/* 694 */     buf.put(this.m22);
/* 695 */     buf.put(this.m23);
/* 696 */     buf.put(this.m30);
/* 697 */     buf.put(this.m31);
/* 698 */     buf.put(this.m32);
/* 699 */     buf.put(this.m33);
/* 700 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix storeTranspose(FloatBuffer buf) {
/* 711 */     buf.put(this.m00);
/* 712 */     buf.put(this.m10);
/* 713 */     buf.put(this.m20);
/* 714 */     buf.put(this.m30);
/* 715 */     buf.put(this.m01);
/* 716 */     buf.put(this.m11);
/* 717 */     buf.put(this.m21);
/* 718 */     buf.put(this.m31);
/* 719 */     buf.put(this.m02);
/* 720 */     buf.put(this.m12);
/* 721 */     buf.put(this.m22);
/* 722 */     buf.put(this.m32);
/* 723 */     buf.put(this.m03);
/* 724 */     buf.put(this.m13);
/* 725 */     buf.put(this.m23);
/* 726 */     buf.put(this.m33);
/* 727 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix store3f(FloatBuffer buf) {
/* 737 */     buf.put(this.m00);
/* 738 */     buf.put(this.m01);
/* 739 */     buf.put(this.m02);
/* 740 */     buf.put(this.m10);
/* 741 */     buf.put(this.m11);
/* 742 */     buf.put(this.m12);
/* 743 */     buf.put(this.m20);
/* 744 */     buf.put(this.m21);
/* 745 */     buf.put(this.m22);
/* 746 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix transpose() {
/* 756 */     return transpose(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix4f translate(Vector2f vec) {
/* 766 */     return translate(vec, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix4f translate(Vector3f vec) {
/* 776 */     return translate(vec, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix4f scale(Vector3f vec) {
/* 786 */     return scale(vec, this, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix4f rotate(float angle, Vector3f axis) {
/* 797 */     return rotate(angle, axis, this);
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
/*     */   public Matrix4f rotate(float angle, Vector3f axis, Matrix4f dest) {
/* 809 */     return rotate(angle, axis, this, dest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix4f translate(Vector3f vec, Matrix4f dest) {
/* 820 */     return translate(vec, this, dest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix4f translate(Vector2f vec, Matrix4f dest) {
/* 831 */     return translate(vec, this, dest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix4f transpose(Matrix4f dest) {
/* 841 */     return transpose(this, dest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float determinant() {
/* 849 */     float f = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 855 */     f -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 860 */     f += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 865 */     f -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 870 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix invert() {
/* 880 */     return invert(this, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix negate() {
/* 890 */     return negate(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix4f negate(Matrix4f dest) {
/* 900 */     return negate(this, dest);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\vector\Matrix4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */