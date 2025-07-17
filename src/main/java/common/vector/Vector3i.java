/*     */ package com.modularwarfare.common.vector;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class Vector3i
/*     */   extends Vector
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   
/*     */   public Vector3i() {}
/*     */   
/*     */   public Vector3i(int x, int y, int z) {
/*  17 */     set(x, y, z);
/*     */   }
/*     */   
/*     */   public Vector3i(Vec3d vec) {
/*  21 */     this((int)vec.field_72450_a, (int)vec.field_72448_b, (int)vec.field_72449_c);
/*     */   }
/*     */   
/*     */   public Vector3i(double x, double y, double z) {
/*  25 */     this((int)x, (int)y, (int)z);
/*     */   }
/*     */   
/*     */   public Vector3i(Vector3i v) {
/*  29 */     this(v.x, v.y, v.z);
/*     */   }
/*     */   
/*     */   public static Vector3i add(Vector3i left, Vector3i right, Vector3i dest) {
/*  33 */     if (dest == null) {
/*  34 */       return new Vector3i(left.x + right.x, left.y + right.y, left.z + right.z);
/*     */     }
/*  36 */     dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
/*  37 */     return dest;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vector3i sub(Vector3i left, Vector3i right, Vector3i dest) {
/*  42 */     if (dest == null) {
/*  43 */       return new Vector3i(left.x - right.x, left.y - right.y, left.z - right.z);
/*     */     }
/*  45 */     dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
/*  46 */     return dest;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vector3i cross(Vector3i left, Vector3i right, Vector3i dest) {
/*  51 */     if (dest == null) {
/*  52 */       dest = new Vector3i();
/*     */     }
/*  54 */     dest.set(left.y * right.z - left.z * right.y, right.x * left.z - right.z * left.x, left.x * right.y - left.y * right.x);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     return dest;
/*     */   }
/*     */   
/*     */   public static float dot(Vector3i left, Vector3i right) {
/*  64 */     return (left.x * right.x + left.y * right.y + left.z * right.z);
/*     */   }
/*     */   
/*     */   public static float angle(Vector3i a, Vector3i b) {
/*  68 */     float dls = dot(a, b) / a.length() * b.length();
/*  69 */     if (dls < -1.0F) {
/*  70 */       dls = -1.0F;
/*  71 */     } else if (dls > 1.0F) {
/*  72 */       dls = 1.0F;
/*  73 */     }  return (float)Math.acos(dls);
/*     */   }
/*     */   
/*     */   public Vec3d toVec3() {
/*  77 */     return new Vec3d(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public void set(int x, int y, int z) {
/*  81 */     this.x = x;
/*  82 */     this.y = y;
/*  83 */     this.z = z;
/*     */   }
/*     */   
/*     */   public float lengthSquared() {
/*  87 */     return (this.x * this.x + this.y * this.y + this.z * this.z);
/*     */   }
/*     */   
/*     */   public Vector3i translate(int x, int y, int z) {
/*  91 */     this.x += x;
/*  92 */     this.y += y;
/*  93 */     this.z += z;
/*  94 */     return this;
/*     */   }
/*     */   
/*     */   public Vector negate() {
/*  98 */     this.x = -this.x;
/*  99 */     this.y = -this.y;
/* 100 */     this.z = -this.z;
/* 101 */     return this;
/*     */   }
/*     */   
/*     */   public Vector3i negate(Vector3i dest) {
/* 105 */     if (dest == null)
/* 106 */       dest = new Vector3i(); 
/* 107 */     dest.x = -this.x;
/* 108 */     dest.y = -this.y;
/* 109 */     dest.z = -this.z;
/* 110 */     return dest;
/*     */   }
/*     */   
/*     */   public Vector3i normalise(Vector3i dest) {
/* 114 */     float l = length();
/*     */     
/* 116 */     if (dest == null) {
/* 117 */       dest = new Vector3i((this.x / l), (this.y / l), (this.z / l));
/*     */     } else {
/* 119 */       dest.set((int)(this.x / l), (int)(this.y / l), (int)(this.z / l));
/*     */     } 
/* 121 */     return dest;
/*     */   }
/*     */   
/*     */   public Vector load(FloatBuffer buf) {
/* 125 */     this.x = (int)buf.get();
/* 126 */     this.y = (int)buf.get();
/* 127 */     this.z = (int)buf.get();
/* 128 */     return this;
/*     */   }
/*     */   
/*     */   public Vector scale(float scale) {
/* 132 */     this.x = (int)(this.x * scale);
/* 133 */     this.y = (int)(this.y * scale);
/* 134 */     this.z = (int)(this.z * scale);
/*     */     
/* 136 */     return this;
/*     */   }
/*     */   
/*     */   public Vector store(FloatBuffer buf) {
/* 140 */     buf.put(this.x);
/* 141 */     buf.put(this.y);
/* 142 */     buf.put(this.z);
/*     */     
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 149 */     return "Vector3i[" + this.x + ", " + this.y + ", " + this.z + ']';
/*     */   }
/*     */   
/*     */   public final int getX() {
/* 153 */     return this.x;
/*     */   }
/*     */   
/*     */   public final void setX(int x) {
/* 157 */     this.x = x;
/*     */   }
/*     */   
/*     */   public final int getY() {
/* 161 */     return this.y;
/*     */   }
/*     */   
/*     */   public final void setY(int y) {
/* 165 */     this.y = y;
/*     */   }
/*     */   
/*     */   public int getZ() {
/* 169 */     return this.z;
/*     */   }
/*     */   
/*     */   public void setZ(int z) {
/* 173 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\vector\Vector3i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */