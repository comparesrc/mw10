/*     */ package com.modularwarfare.common.vector;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.io.Serializable;
/*     */ import java.nio.FloatBuffer;
/*     */ import net.minecraft.util.math.Vec3d;
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
/*     */ public class Vector3f
/*     */   extends Vector
/*     */   implements Serializable, ReadableVector3f, WritableVector3f
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*  53 */   public transient float[] array = new float[3];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(String input, String typeName) {
/*  64 */     String noBrackets = input.substring(1, input.length() - 1);
/*  65 */     String[] split = noBrackets.split(",");
/*  66 */     if (split.length == 3) {
/*  67 */       this.x = Float.parseFloat(split[0]);
/*  68 */       this.y = Float.parseFloat(split[1]);
/*  69 */       this.z = Float.parseFloat(split[2]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(ReadableVector3f src) {
/*  78 */     set(src);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(float x, float y, float z) {
/*  85 */     set(x, y, z);
/*     */   }
/*     */   
/*     */   public Vector3f(Vec3d vec) {
/*  89 */     this((float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c);
/*     */   }
/*     */   
/*     */   public Vector3f(double x, double y, double z) {
/*  93 */     this((float)x, (float)y, (float)z);
/*     */   }
/*     */   
/*     */   public Vector3f add(float x, float y, float z) {
/*  97 */     return add(this, new Vector3f(x, y, z), this);
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
/*     */   public static Vector3f add(Vector3f left, Vector3f right, Vector3f dest) {
/* 110 */     if (dest == null) {
/* 111 */       return new Vector3f(left.x + right.x, left.y + right.y, left.z + right.z);
/*     */     }
/* 113 */     dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
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
/*     */   public static Vector3f sub(Vector3f left, Vector3f right, Vector3f dest) {
/* 128 */     if (dest == null) {
/* 129 */       return new Vector3f(left.x - right.x, left.y - right.y, left.z - right.z);
/*     */     }
/* 131 */     dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
/* 132 */     return dest;
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
/*     */   public static Vector3f cross(Vector3f left, Vector3f right, Vector3f dest) {
/* 149 */     if (dest == null) {
/* 150 */       dest = new Vector3f();
/*     */     }
/* 152 */     dest.set(left.y * right.z - left.z * right.y, right.x * left.z - right.z * left.x, left.x * right.y - left.y * right.x);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     return dest;
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
/*     */   public static float dot(Vector3f left, Vector3f right) {
/* 170 */     return left.x * right.x + left.y * right.y + left.z * right.z;
/*     */   }
/*     */   public static double dotDouble(Vector3f left, Vector3f right) {
/* 173 */     return left.x * right.x + left.y * right.y + left.z * right.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float angle(Vector3f a, Vector3f b) {
/* 184 */     float dls = dot(a, b) / a.length() * b.length();
/* 185 */     if (dls < -1.0F) {
/* 186 */       dls = -1.0F;
/* 187 */     } else if (dls > 1.0F) {
/* 188 */       dls = 1.0F;
/* 189 */     }  return (float)Math.acos(dls);
/*     */   }
/*     */   
/*     */   public static Vector3f readFromBuffer(ByteBuf data) {
/* 193 */     return new Vector3f(data.readFloat(), data.readFloat(), data.readFloat());
/*     */   }
/*     */   
/*     */   public Vec3d toVec3() {
/* 197 */     return new Vec3d(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(float x, float y) {
/* 205 */     this.x = x;
/* 206 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(float x, float y, float z) {
/* 214 */     this.x = x;
/* 215 */     this.y = y;
/* 216 */     this.z = z;
/* 217 */     this.array[0] = x;
/* 218 */     this.array[1] = y;
/* 219 */     this.array[2] = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f set(ReadableVector3f src) {
/* 229 */     this.x = src.getX();
/* 230 */     this.y = src.getY();
/* 231 */     this.z = src.getZ();
/* 232 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float lengthSquared() {
/* 240 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f translate(float x, float y, float z) {
/* 251 */     set(this.x + x, this.y + y, this.z + z);
/* 252 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector negate() {
/* 262 */     this.x = -this.x;
/* 263 */     this.y = -this.y;
/* 264 */     this.z = -this.z;
/* 265 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f negate(Vector3f dest) {
/* 275 */     if (dest == null)
/* 276 */       dest = new Vector3f(); 
/* 277 */     dest.x = -this.x;
/* 278 */     dest.y = -this.y;
/* 279 */     dest.z = -this.z;
/* 280 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f normalise(Vector3f dest) {
/* 290 */     float l = length();
/*     */     
/* 292 */     if (dest == null) {
/* 293 */       dest = new Vector3f(this.x / l, this.y / l, this.z / l);
/*     */     } else {
/* 295 */       dest.set(this.x / l, this.y / l, this.z / l);
/*     */     } 
/* 297 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector load(FloatBuffer buf) {
/* 305 */     this.x = buf.get();
/* 306 */     this.y = buf.get();
/* 307 */     this.z = buf.get();
/* 308 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector scale(float scale) {
/* 317 */     this.x *= scale;
/* 318 */     this.y *= scale;
/* 319 */     this.z *= scale;
/*     */     
/* 321 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector store(FloatBuffer buf) {
/* 331 */     buf.put(this.x);
/* 332 */     buf.put(this.y);
/* 333 */     buf.put(this.z);
/*     */     
/* 335 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 344 */     return "Vector3f[" + this.x + ", " + this.y + ", " + this.z + ']';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getX() {
/* 352 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setX(float x) {
/* 362 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getY() {
/* 370 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setY(float y) {
/* 380 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getZ() {
/* 388 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZ(float z) {
/* 398 */     this.z = z;
/*     */   }
/*     */   
/*     */   public void writeToBuffer(ByteBuf data) {
/* 402 */     data.writeFloat(this.x);
/* 403 */     data.writeFloat(this.y);
/* 404 */     data.writeFloat(this.z);
/*     */   }
/*     */   
/*     */   public float[] getArray() {
/* 408 */     return this.array;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\vector\Vector3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */