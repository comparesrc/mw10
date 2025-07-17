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
/*     */ public class Vector4f
/*     */   extends Vector
/*     */   implements Serializable, ReadableVector4f, WritableVector4f
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   public float w;
/*     */   
/*     */   public Vector4f() {}
/*     */   
/*     */   public Vector4f(ReadableVector4f src) {
/*  62 */     set(src);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(float x, float y, float z, float w) {
/*  69 */     set(x, y, z, w);
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
/*     */   public static Vector4f add(Vector4f left, Vector4f right, Vector4f dest) {
/*  82 */     if (dest == null) {
/*  83 */       return new Vector4f(left.x + right.x, left.y + right.y, left.z + right.z, left.w + right.w);
/*     */     }
/*  85 */     dest.set(left.x + right.x, left.y + right.y, left.z + right.z, left.w + right.w);
/*  86 */     return dest;
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
/*     */   public static Vector4f sub(Vector4f left, Vector4f right, Vector4f dest) {
/* 100 */     if (dest == null) {
/* 101 */       return new Vector4f(left.x - right.x, left.y - right.y, left.z - right.z, left.w - right.w);
/*     */     }
/* 103 */     dest.set(left.x - right.x, left.y - right.y, left.z - right.z, left.w - right.w);
/* 104 */     return dest;
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
/*     */   public static float dot(Vector4f left, Vector4f right) {
/* 117 */     return left.x * right.x + left.y * right.y + left.z * right.z + left.w * right.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float angle(Vector4f a, Vector4f b) {
/* 128 */     float dls = dot(a, b) / a.length() * b.length();
/* 129 */     if (dls < -1.0F) {
/* 130 */       dls = -1.0F;
/* 131 */     } else if (dls > 1.0F) {
/* 132 */       dls = 1.0F;
/* 133 */     }  return (float)Math.acos(dls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(float x, float y) {
/* 141 */     this.x = x;
/* 142 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(float x, float y, float z) {
/* 150 */     this.x = x;
/* 151 */     this.y = y;
/* 152 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(float x, float y, float z, float w) {
/* 160 */     this.x = x;
/* 161 */     this.y = y;
/* 162 */     this.z = z;
/* 163 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f set(ReadableVector4f src) {
/* 173 */     this.x = src.getX();
/* 174 */     this.y = src.getY();
/* 175 */     this.z = src.getZ();
/* 176 */     this.w = src.getW();
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float lengthSquared() {
/* 185 */     return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f translate(float x, float y, float z, float w) {
/* 196 */     this.x += x;
/* 197 */     this.y += y;
/* 198 */     this.z += z;
/* 199 */     this.w += w;
/* 200 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector negate() {
/* 210 */     this.x = -this.x;
/* 211 */     this.y = -this.y;
/* 212 */     this.z = -this.z;
/* 213 */     this.w = -this.w;
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f negate(Vector4f dest) {
/* 224 */     if (dest == null)
/* 225 */       dest = new Vector4f(); 
/* 226 */     dest.x = -this.x;
/* 227 */     dest.y = -this.y;
/* 228 */     dest.z = -this.z;
/* 229 */     dest.w = -this.w;
/* 230 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f normalise(Vector4f dest) {
/* 240 */     float l = length();
/*     */     
/* 242 */     if (dest == null) {
/* 243 */       dest = new Vector4f(this.x / l, this.y / l, this.z / l, this.w / l);
/*     */     } else {
/* 245 */       dest.set(this.x / l, this.y / l, this.z / l, this.w / l);
/*     */     } 
/* 247 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector load(FloatBuffer buf) {
/* 255 */     this.x = buf.get();
/* 256 */     this.y = buf.get();
/* 257 */     this.z = buf.get();
/* 258 */     this.w = buf.get();
/* 259 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector scale(float scale) {
/* 267 */     this.x *= scale;
/* 268 */     this.y *= scale;
/* 269 */     this.z *= scale;
/* 270 */     this.w *= scale;
/* 271 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector store(FloatBuffer buf) {
/* 280 */     buf.put(this.x);
/* 281 */     buf.put(this.y);
/* 282 */     buf.put(this.z);
/* 283 */     buf.put(this.w);
/*     */     
/* 285 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 290 */     return "Vector4f: " + this.x + " " + this.y + " " + this.z + " " + this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getX() {
/* 298 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setX(float x) {
/* 308 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getY() {
/* 316 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setY(float y) {
/* 326 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getZ() {
/* 334 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZ(float z) {
/* 344 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getW() {
/* 352 */     return this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setW(float w) {
/* 362 */     this.w = w;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\vector\Vector4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */