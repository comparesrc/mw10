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
/*     */ 
/*     */ 
/*     */ public class Vector2f
/*     */   extends Vector
/*     */   implements Serializable, ReadableVector2f, WritableVector2f
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public float x;
/*     */   public float y;
/*     */   
/*     */   public Vector2f() {}
/*     */   
/*     */   public Vector2f(ReadableVector2f src) {
/*  62 */     set(src);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f(float x, float y) {
/*  69 */     set(x, y);
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
/*     */   public static float dot(Vector2f left, Vector2f right) {
/*  81 */     return left.x * right.x + left.y * right.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float angle(Vector2f a, Vector2f b) {
/*  92 */     float dls = dot(a, b) / a.length() * b.length();
/*  93 */     if (dls < -1.0F) {
/*  94 */       dls = -1.0F;
/*  95 */     } else if (dls > 1.0F) {
/*  96 */       dls = 1.0F;
/*  97 */     }  return (float)Math.acos(dls);
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
/*     */   public static Vector2f add(Vector2f left, Vector2f right, Vector2f dest) {
/* 110 */     if (dest == null) {
/* 111 */       return new Vector2f(left.x + right.x, left.y + right.y);
/*     */     }
/* 113 */     dest.set(left.x + right.x, left.y + right.y);
/* 114 */     return dest;
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
/*     */   public static Vector2f sub(Vector2f left, Vector2f right, Vector2f dest) {
/* 128 */     if (dest == null) {
/* 129 */       return new Vector2f(left.x - right.x, left.y - right.y);
/*     */     }
/* 131 */     dest.set(left.x - right.x, left.y - right.y);
/* 132 */     return dest;
/*     */   }
/*     */ 
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
/*     */ 
/*     */   
/*     */   public Vector2f set(ReadableVector2f src) {
/* 152 */     this.x = src.getX();
/* 153 */     this.y = src.getY();
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float lengthSquared() {
/* 162 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f translate(float x, float y) {
/* 173 */     this.x += x;
/* 174 */     this.y += y;
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector negate() {
/* 185 */     this.x = -this.x;
/* 186 */     this.y = -this.y;
/* 187 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f negate(Vector2f dest) {
/* 197 */     if (dest == null)
/* 198 */       dest = new Vector2f(); 
/* 199 */     dest.x = -this.x;
/* 200 */     dest.y = -this.y;
/* 201 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f normalise(Vector2f dest) {
/* 211 */     float l = length();
/*     */     
/* 213 */     if (dest == null) {
/* 214 */       dest = new Vector2f(this.x / l, this.y / l);
/*     */     } else {
/* 216 */       dest.set(this.x / l, this.y / l);
/*     */     } 
/* 218 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector store(FloatBuffer buf) {
/* 229 */     buf.put(this.x);
/* 230 */     buf.put(this.y);
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector load(FloatBuffer buf) {
/* 242 */     this.x = buf.get();
/* 243 */     this.y = buf.get();
/* 244 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector scale(float scale) {
/* 253 */     this.x *= scale;
/* 254 */     this.y *= scale;
/*     */     
/* 256 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 265 */     return "Vector2f[" + this.x + ", " + this.y + ']';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getX() {
/* 273 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setX(float x) {
/* 283 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getY() {
/* 291 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setY(float y) {
/* 301 */     this.y = y;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\vector\Vector2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */