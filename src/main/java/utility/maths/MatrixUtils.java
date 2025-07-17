/*     */ package com.modularwarfare.utility.maths;
/*     */ import java.nio.FloatBuffer;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.vecmath.Matrix3f;
/*     */ import javax.vecmath.Matrix4f;
/*     */ import javax.vecmath.Quat4d;
/*     */ import javax.vecmath.SingularMatrixException;
/*     */ import javax.vecmath.Tuple3f;
/*     */ import javax.vecmath.Vector3f;
/*     */ import javax.vecmath.Vector4f;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MatrixUtils {
/*  19 */   public static final FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  24 */   public static final float[] floats = new float[16];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix4f matrix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix4f readModelView(Matrix4f matrix4f) {
/*  36 */     buffer.clear();
/*  37 */     GL11.glGetFloat(2982, buffer);
/*  38 */     buffer.get(floats);
/*     */     
/*  40 */     matrix4f.set(floats);
/*  41 */     matrix4f.transpose();
/*     */     
/*  43 */     return matrix4f;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Matrix4f readModelView() {
/*  48 */     return readModelView(new Matrix4f());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadModelView(Matrix4f matrix4f) {
/*  56 */     matrixToFloat(floats, matrix4f);
/*     */     
/*  58 */     buffer.clear();
/*  59 */     buffer.put(floats);
/*  60 */     buffer.rewind();
/*  61 */     GL11.glLoadMatrix(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void matrixToFloat(float[] floats, Matrix4f matrix4f) {
/*  69 */     floats[0] = matrix4f.m00;
/*  70 */     floats[1] = matrix4f.m01;
/*  71 */     floats[2] = matrix4f.m02;
/*  72 */     floats[3] = matrix4f.m03;
/*  73 */     floats[4] = matrix4f.m10;
/*  74 */     floats[5] = matrix4f.m11;
/*  75 */     floats[6] = matrix4f.m12;
/*  76 */     floats[7] = matrix4f.m13;
/*  77 */     floats[8] = matrix4f.m20;
/*  78 */     floats[9] = matrix4f.m21;
/*  79 */     floats[10] = matrix4f.m22;
/*  80 */     floats[11] = matrix4f.m23;
/*  81 */     floats[12] = matrix4f.m30;
/*  82 */     floats[13] = matrix4f.m31;
/*  83 */     floats[14] = matrix4f.m32;
/*  84 */     floats[15] = matrix4f.m33;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean captureMatrix() {
/*  89 */     if (matrix == null) {
/*     */       
/*  91 */       matrix = readModelView(new Matrix4f());
/*     */       
/*  93 */       return true;
/*     */     } 
/*     */     
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void releaseMatrix() {
/* 101 */     matrix = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Quat4d matrixToQuaternion(Matrix3f matrix) {
/* 106 */     double tr = (matrix.m00 + matrix.m11 + matrix.m22);
/* 107 */     double qw = 0.0D;
/* 108 */     double qx = 0.0D;
/* 109 */     double qy = 0.0D;
/* 110 */     double qz = 0.0D;
/*     */     
/* 112 */     if (tr > 0.0D) {
/*     */       
/* 114 */       double S = Math.sqrt(tr + 1.0D) * 2.0D;
/* 115 */       qw = 0.25D * S;
/* 116 */       qx = (matrix.m21 - matrix.m12) / S;
/* 117 */       qy = (matrix.m02 - matrix.m20) / S;
/* 118 */       qz = (matrix.m10 - matrix.m01) / S;
/*     */     }
/* 120 */     else if ((((matrix.m00 > matrix.m11) ? 1 : 0) & ((matrix.m00 > matrix.m22) ? 1 : 0)) != 0) {
/*     */       
/* 122 */       double S = Math.sqrt(1.0D + matrix.m00 - matrix.m11 - matrix.m22) * 2.0D;
/* 123 */       qw = (matrix.m21 - matrix.m12) / S;
/* 124 */       qx = 0.25D * S;
/* 125 */       qy = (matrix.m01 + matrix.m10) / S;
/* 126 */       qz = (matrix.m02 + matrix.m20) / S;
/*     */     }
/* 128 */     else if (matrix.m11 > matrix.m22) {
/*     */       
/* 130 */       double S = Math.sqrt(1.0D + matrix.m11 - matrix.m00 - matrix.m22) * 2.0D;
/* 131 */       qw = (matrix.m02 - matrix.m20) / S;
/* 132 */       qx = (matrix.m01 + matrix.m10) / S;
/* 133 */       qy = 0.25D * S;
/* 134 */       qz = (matrix.m12 + matrix.m21) / S;
/*     */     }
/*     */     else {
/*     */       
/* 138 */       double S = Math.sqrt(1.0D + matrix.m22 - matrix.m00 - matrix.m11) * 2.0D;
/* 139 */       qw = (matrix.m10 - matrix.m01) / S;
/* 140 */       qx = (matrix.m02 + matrix.m20) / S;
/* 141 */       qy = (matrix.m12 + matrix.m21) / S;
/* 142 */       qz = 0.25D * S;
/*     */     } 
/*     */     
/* 145 */     return new Quat4d(qw, qx, qy, qz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector3f getAngularVelocity(Matrix3f rotation) {
/* 156 */     Matrix3f step = new Matrix3f(rotation);
/* 157 */     Matrix3f angularVelocity = new Matrix3f();
/* 158 */     Matrix3f i = new Matrix3f();
/*     */     
/* 160 */     i.setIdentity();
/* 161 */     angularVelocity.setIdentity();
/* 162 */     angularVelocity.mul(2.0F);
/*     */     
/* 164 */     step.add(i);
/* 165 */     step.invert();
/* 166 */     step.mul(4.0F);
/*     */     
/* 168 */     angularVelocity.sub(step);
/*     */     
/* 170 */     Vector3f angularV = new Vector3f(angularVelocity.m21, -angularVelocity.m20, angularVelocity.m10);
/*     */ 
/*     */ 
/*     */     
/* 174 */     return angularV;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Matrix3f getZYXrotationMatrix(float x, float y, float z) {
/* 179 */     Matrix3f rotation = new Matrix3f();
/* 180 */     Matrix3f rot = new Matrix3f();
/*     */     
/* 182 */     rotation.setIdentity();
/* 183 */     rot.rotZ(z);
/* 184 */     rotation.mul(rot);
/* 185 */     rot.rotY(y);
/* 186 */     rotation.mul(rot);
/* 187 */     rot.rotX(x);
/* 188 */     rotation.mul(rot);
/*     */     
/* 190 */     return rotation;
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
/*     */   public static Transformation extractTransformations(@Nullable Matrix4f cameraMatrix, Matrix4f modelView) {
/* 203 */     return extractTransformations(cameraMatrix, modelView, MatrixMajor.ROW);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Transformation extractTransformations(@Nullable Matrix4f cameraMatrix, Matrix4f modelView, MatrixMajor major) {
/* 208 */     Matrix4f parent = new Matrix4f(modelView);
/*     */     
/* 210 */     if (cameraMatrix != null) {
/*     */       
/* 212 */       parent.set(cameraMatrix);
/*     */ 
/*     */       
/*     */       try {
/* 216 */         parent.invert();
/*     */       }
/* 218 */       catch (SingularMatrixException e) {
/*     */         
/* 220 */         Transformation transformation = new Transformation();
/* 221 */         transformation.creationException = (Exception)e;
/*     */         
/* 223 */         return transformation;
/*     */       } 
/*     */       
/* 226 */       parent.mul(modelView);
/*     */     } 
/*     */     
/* 229 */     Matrix4f translation = new Matrix4f();
/* 230 */     Matrix4f scale = new Matrix4f();
/* 231 */     Matrix4f rotation = new Matrix4f();
/*     */     
/* 233 */     translation.setIdentity();
/* 234 */     rotation.setIdentity();
/* 235 */     scale.setIdentity();
/*     */     
/* 237 */     translation.m03 = parent.m03;
/* 238 */     translation.m13 = parent.m13;
/* 239 */     translation.m23 = parent.m23;
/*     */     
/* 241 */     Vector4f ax = new Vector4f(parent.m00, parent.m01, parent.m02, 0.0F);
/* 242 */     Vector4f ay = new Vector4f(parent.m10, parent.m11, parent.m12, 0.0F);
/* 243 */     Vector4f az = new Vector4f(parent.m20, parent.m21, parent.m22, 0.0F);
/*     */     
/* 245 */     if (major == MatrixMajor.COLUMN) {
/*     */       
/* 247 */       ax = new Vector4f(parent.m00, parent.m10, parent.m20, 0.0F);
/* 248 */       ay = new Vector4f(parent.m01, parent.m11, parent.m21, 0.0F);
/* 249 */       az = new Vector4f(parent.m02, parent.m12, parent.m22, 0.0F);
/*     */     } 
/*     */     
/* 252 */     ax.normalize();
/* 253 */     ay.normalize();
/* 254 */     az.normalize();
/* 255 */     rotation.setRow(0, ax);
/* 256 */     rotation.setRow(1, ay);
/* 257 */     rotation.setRow(2, az);
/*     */     
/* 259 */     if (major == MatrixMajor.COLUMN)
/*     */     {
/* 261 */       rotation.transpose();
/*     */     }
/*     */     
/* 264 */     scale.m00 = (float)Math.sqrt((parent.m00 * parent.m00 + parent.m01 * parent.m01 + parent.m02 * parent.m02));
/* 265 */     scale.m11 = (float)Math.sqrt((parent.m10 * parent.m10 + parent.m11 * parent.m11 + parent.m12 * parent.m12));
/* 266 */     scale.m22 = (float)Math.sqrt((parent.m20 * parent.m20 + parent.m21 * parent.m21 + parent.m22 * parent.m22));
/*     */     
/* 268 */     if (major == MatrixMajor.COLUMN) {
/*     */       
/* 270 */       scale.m00 = (float)Math.sqrt((parent.m00 * parent.m00 + parent.m10 * parent.m10 + parent.m20 * parent.m20));
/* 271 */       scale.m11 = (float)Math.sqrt((parent.m01 * parent.m01 + parent.m11 * parent.m11 + parent.m21 * parent.m21));
/* 272 */       scale.m22 = (float)Math.sqrt((parent.m02 * parent.m02 + parent.m12 * parent.m12 + parent.m22 * parent.m22));
/*     */     } 
/*     */     
/* 275 */     return new Transformation(translation, rotation, scale);
/*     */   }
/*     */   
/*     */   public static class Transformation
/*     */   {
/* 280 */     public Matrix4f translation = new Matrix4f();
/* 281 */     public Matrix4f rotation = new Matrix4f();
/* 282 */     public Matrix4f scale = new Matrix4f();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     private Exception creationException = null;
/*     */ 
/*     */     
/*     */     public Transformation(Matrix4f translation, Matrix4f rotation, Matrix4f scale) {
/* 291 */       this.translation.set(translation);
/* 292 */       this.rotation.set(rotation);
/* 293 */       this.scale.set(scale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Transformation() {
/* 298 */       this.translation.setIdentity();
/* 299 */       this.rotation.setIdentity();
/* 300 */       this.scale.setIdentity();
/*     */     }
/*     */ 
/*     */     
/*     */     public Matrix3f getScale3f() {
/* 305 */       Matrix3f scale3f = new Matrix3f();
/*     */       
/* 307 */       scale3f.setIdentity();
/*     */       
/* 309 */       scale3f.m00 = this.scale.m00;
/* 310 */       scale3f.m11 = this.scale.m11;
/* 311 */       scale3f.m22 = this.scale.m22;
/*     */       
/* 313 */       return scale3f;
/*     */     }
/*     */ 
/*     */     
/*     */     public Vector3f getTranslation3f() {
/* 318 */       Vector3f translation3f = new Vector3f();
/*     */       
/* 320 */       translation3f.set(this.translation.m03, this.translation.m13, this.translation.m23);
/*     */       
/* 322 */       return translation3f;
/*     */     }
/*     */ 
/*     */     
/*     */     public Matrix3f getRotation3f() {
/* 327 */       Matrix3f rotation3f = new Matrix3f();
/*     */       
/* 329 */       rotation3f.setIdentity();
/* 330 */       rotation3f.setRow(0, this.rotation.m00, this.rotation.m01, this.rotation.m02);
/* 331 */       rotation3f.setRow(1, this.rotation.m10, this.rotation.m11, this.rotation.m12);
/* 332 */       rotation3f.setRow(2, this.rotation.m20, this.rotation.m21, this.rotation.m22);
/*     */       
/* 334 */       return rotation3f;
/*     */     }
/*     */ 
/*     */     
/*     */     public Exception getCreationException() {
/* 339 */       return this.creationException;
/*     */     }
/*     */ 
/*     */     
/*     */     public Vector3f getRotation(RotationOrder order) {
/* 344 */       return getRotation(order, (Vector3f)null);
/*     */     }
/*     */ 
/*     */     
/*     */     public Vector3f getRotation(RotationOrder order, Vector3f ref) {
/* 349 */       return getRotation(order, ref, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Vector3f getRotation(RotationOrder order, int invAxis) {
/* 354 */       return getRotation(order, null, invAxis);
/*     */     }
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
/*     */     public Vector3f getRotation(RotationOrder order, Vector3f ref, int invAxis) {
/* 375 */       Matrix3f mat = getRotation3f();
/* 376 */       float[] rotation = new float[3];
/* 377 */       float[] refFloats = null;
/*     */       
/* 379 */       if (ref != null) {
/*     */         
/* 381 */         refFloats = new float[3];
/* 382 */         ref.get(refFloats);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 388 */       Vector3f x = new Vector3f(mat.m00, mat.m10, mat.m20);
/* 389 */       Vector3f y = new Vector3f(mat.m01, mat.m11, mat.m21);
/* 390 */       Vector3f z = new Vector3f(mat.m02, mat.m12, mat.m22);
/* 391 */       Vector3f crossY = new Vector3f();
/* 392 */       Vector3f originalY = new Vector3f();
/* 393 */       originalY.normalize(y);
/* 394 */       crossY.cross(z, x);
/* 395 */       crossY.normalize();
/*     */       
/* 397 */       if (crossY.dot(originalY) < 0.0F)
/*     */       {
/* 399 */         mat.mul(getInvertAxisMatrix(invAxis));
/*     */       }
/*     */       
/* 402 */       Float angle = order.doTest(order.thirdIndex, mat);
/*     */ 
/*     */       
/* 405 */       if (angle != null) {
/*     */         
/* 407 */         if (refFloats != null)
/*     */         {
/* 409 */           angle = Float.valueOf(refFloats[order.thirdIndex] + MathHelper.func_76142_g(2.0F * (angle.floatValue() - refFloats[order.thirdIndex])) / 2.0F);
/*     */         }
/*     */         
/* 412 */         rotation[order.thirdIndex] = angle.floatValue();
/* 413 */         mat.mul(getRotationMatrix(order.thirdIndex, -angle.floatValue()), mat);
/*     */       }
/* 415 */       else if (refFloats != null) {
/*     */         
/* 417 */         rotation[order.thirdIndex] = (angle = Float.valueOf(refFloats[order.thirdIndex])).floatValue();
/* 418 */         mat.mul(getRotationMatrix(order.thirdIndex, -angle.floatValue()), mat);
/*     */       } 
/*     */       
/* 421 */       angle = order.doTest(order.secondIndex, mat);
/*     */       
/* 423 */       if (angle == null)
/*     */       {
/*     */         
/* 426 */         return null;
/*     */       }
/*     */       
/* 429 */       if (refFloats != null)
/*     */       {
/* 431 */         angle = Float.valueOf(refFloats[order.secondIndex] + MathHelper.func_76142_g(angle.floatValue() - refFloats[order.secondIndex]));
/*     */       }
/*     */       
/* 434 */       rotation[order.secondIndex] = angle.floatValue();
/* 435 */       mat.mul(getRotationMatrix(order.secondIndex, -angle.floatValue()), mat);
/*     */       
/* 437 */       angle = order.doTest(order.firstIndex, mat);
/*     */       
/* 439 */       if (angle == null)
/*     */       {
/* 441 */         return null;
/*     */       }
/* 443 */       if (refFloats != null)
/*     */       {
/* 445 */         angle = Float.valueOf(refFloats[order.firstIndex] + MathHelper.func_76142_g(angle.floatValue() - refFloats[order.firstIndex]));
/*     */       }
/*     */       
/* 448 */       rotation[order.firstIndex] = angle.floatValue();
/*     */       
/* 450 */       return new Vector3f(rotation);
/*     */     }
/*     */ 
/*     */     
/*     */     public Vector3f getScale() {
/* 455 */       return getScale(0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Vector3f getScale(int invAxis) {
/* 460 */       Vector3f scale = new Vector3f(this.scale.m00, this.scale.m11, this.scale.m22);
/* 461 */       Vector3f x = new Vector3f(this.rotation.m00, this.rotation.m10, this.rotation.m20);
/* 462 */       Vector3f y = new Vector3f(this.rotation.m01, this.rotation.m11, this.rotation.m21);
/* 463 */       Vector3f z = new Vector3f(this.rotation.m02, this.rotation.m12, this.rotation.m22);
/* 464 */       Vector3f crossY = new Vector3f();
/* 465 */       Vector3f originalY = new Vector3f();
/*     */       
/* 467 */       originalY.normalize(y);
/* 468 */       crossY.cross(z, x);
/* 469 */       crossY.normalize();
/*     */       
/* 471 */       if (crossY.dot(originalY) < 0.0F)
/*     */       {
/* 473 */         getInvertAxisMatrix(invAxis).transform((Tuple3f)scale);
/*     */       }
/*     */       
/* 476 */       return scale;
/*     */     }
/*     */ 
/*     */     
/*     */     public static Matrix3f getRotationMatrix(int axis, double degrees) {
/* 481 */       Matrix3f mat = new Matrix3f();
/*     */       
/* 483 */       switch (axis) {
/*     */         
/*     */         case 0:
/* 486 */           mat.rotX((float)Math.toRadians(degrees));
/*     */           break;
/*     */         case 1:
/* 489 */           mat.rotY((float)Math.toRadians(degrees));
/*     */           break;
/*     */         case 2:
/* 492 */           mat.rotZ((float)Math.toRadians(degrees));
/*     */           break;
/*     */       } 
/*     */       
/* 496 */       return mat;
/*     */     }
/*     */ 
/*     */     
/*     */     public static Matrix3f getInvertAxisMatrix(int axis) {
/* 501 */       Matrix3f mat = new Matrix3f();
/*     */       
/* 503 */       mat.setIdentity();
/*     */       
/* 505 */       switch (axis) {
/*     */         
/*     */         case 0:
/* 508 */           mat.m00 = -1.0F;
/*     */           break;
/*     */         case 1:
/* 511 */           mat.m11 = -1.0F;
/*     */           break;
/*     */         case 2:
/* 514 */           mat.m22 = -1.0F;
/*     */           break;
/*     */       } 
/*     */       
/* 518 */       return mat;
/*     */     }
/*     */     
/*     */     public enum RotationOrder
/*     */     {
/* 523 */       XYZ, XZY, YXZ, YZX, ZXY, ZYX;
/*     */       
/*     */       public final int thirdIndex;
/*     */       
/*     */       public final int secondIndex;
/*     */       public final int firstIndex;
/*     */       
/*     */       RotationOrder() {
/* 531 */         String order = name().toUpperCase();
/* 532 */         this.firstIndex = order.charAt(0) - 88;
/* 533 */         this.secondIndex = order.charAt(1) - 88;
/* 534 */         this.thirdIndex = order.charAt(2) - 88;
/*     */       }
/*     */ 
/*     */       
/*     */       public Float doTest(int index, Matrix3f test) {
/* 539 */         float[] buffer = new float[3];
/*     */         
/* 541 */         buffer[(index == this.firstIndex) ? this.secondIndex : this.firstIndex] = 1.0F;
/*     */         
/* 543 */         Vector3f in = new Vector3f(buffer);
/* 544 */         Vector3f out = new Vector3f();
/*     */         
/* 546 */         test.transform((Tuple3f)in, (Tuple3f)out);
/* 547 */         out.get(buffer);
/* 548 */         buffer[index] = 0.0F;
/* 549 */         out.set(buffer);
/*     */         
/* 551 */         if (out.length() < 1.0E-7D)
/*     */         {
/* 553 */           return null;
/*     */         }
/*     */         
/* 556 */         out.normalize();
/*     */         
/* 558 */         float cos = in.dot(out);
/*     */         
/* 560 */         out.cross(in, out);
/* 561 */         out.get(buffer);
/*     */         
/* 563 */         float sin = out.length() * Math.signum(buffer[index]);
/*     */         
/* 565 */         return Float.valueOf((float)Math.toDegrees(Math.atan2(sin, cos)));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public enum MatrixMajor
/*     */   {
/* 572 */     ROW,
/* 573 */     COLUMN;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfar\\utility\maths\MatrixUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */