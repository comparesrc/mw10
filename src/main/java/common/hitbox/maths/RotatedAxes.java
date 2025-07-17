/*     */ package com.modularwarfare.common.hitbox.maths;
/*     */ 
/*     */ import com.modularwarfare.common.vector.Matrix4f;
/*     */ import com.modularwarfare.common.vector.Vector3f;
/*     */ 
/*     */ 
/*     */ public class RotatedAxes
/*     */ {
/*     */   private float rotationYaw;
/*     */   private float rotationPitch;
/*     */   private float rotationRoll;
/*     */   private Matrix4f rotationMatrix;
/*     */   
/*     */   public RotatedAxes() {
/*  15 */     this.rotationMatrix = new Matrix4f();
/*     */   }
/*     */   
/*     */   public RotatedAxes(Matrix4f mat) {
/*  19 */     this.rotationMatrix = mat;
/*  20 */     convertMatrixToAngles();
/*     */   }
/*     */   
/*     */   public RotatedAxes(float yaw, float pitch, float roll) {
/*  24 */     setAngles(yaw, pitch, roll);
/*     */   }
/*     */ 
/*     */   
/*     */   public RotatedAxes clone() {
/*  29 */     RotatedAxes newAxes = new RotatedAxes();
/*  30 */     newAxes.rotationMatrix.load(getMatrix());
/*  31 */     newAxes.convertMatrixToAngles();
/*  32 */     return newAxes;
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/*  36 */     if (this.rotationMatrix.determinant() == 0.0F) {
/*  37 */       return false;
/*     */     }
/*  39 */     if (Float.isNaN(this.rotationMatrix.determinant())) {
/*  40 */       return false;
/*     */     }
/*  42 */     return true;
/*     */   }
/*     */   
/*     */   public void setAngles(float yaw, float pitch, float roll) {
/*  46 */     this.rotationYaw = yaw;
/*  47 */     this.rotationPitch = pitch;
/*  48 */     this.rotationRoll = roll;
/*  49 */     convertAnglesToMatrix();
/*     */   }
/*     */   
/*     */   public float getYaw() {
/*  53 */     return this.rotationYaw;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/*  57 */     return this.rotationPitch;
/*     */   }
/*     */   
/*     */   public float getRoll() {
/*  61 */     return this.rotationRoll;
/*     */   }
/*     */   
/*     */   public Vector3f getXAxis() {
/*  65 */     return new Vector3f(this.rotationMatrix.m00, this.rotationMatrix.m10, this.rotationMatrix.m20);
/*     */   }
/*     */   
/*     */   public Vector3f getYAxis() {
/*  69 */     return new Vector3f(this.rotationMatrix.m01, this.rotationMatrix.m11, this.rotationMatrix.m21);
/*     */   }
/*     */   
/*     */   public Vector3f getZAxis() {
/*  73 */     return new Vector3f(-this.rotationMatrix.m02, -this.rotationMatrix.m12, -this.rotationMatrix.m22);
/*     */   }
/*     */   
/*     */   public Matrix4f getMatrix() {
/*  77 */     return this.rotationMatrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotateLocalYaw(float rotateBy) {
/*  82 */     this.rotationMatrix.rotate(rotateBy * 3.1415927F / 180.0F, getYAxis().normalise(null));
/*  83 */     convertMatrixToAngles();
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotateLocalPitch(float rotateBy) {
/*  88 */     this.rotationMatrix.rotate(rotateBy * 3.1415927F / 180.0F, getZAxis().normalise(null));
/*  89 */     convertMatrixToAngles();
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotateLocalRoll(float rotateBy) {
/*  94 */     this.rotationMatrix.rotate(rotateBy * 3.1415927F / 180.0F, getXAxis().normalise(null));
/*  95 */     convertMatrixToAngles();
/*     */   }
/*     */ 
/*     */   
/*     */   public RotatedAxes rotateGlobalYaw(float rotateBy) {
/* 100 */     this.rotationMatrix.rotate(rotateBy * 3.1415927F / 180.0F, new Vector3f(0.0F, 1.0F, 0.0F));
/* 101 */     convertMatrixToAngles();
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public RotatedAxes rotateGlobalPitch(float rotateBy) {
/* 107 */     this.rotationMatrix.rotate(rotateBy * 3.1415927F / 180.0F, new Vector3f(0.0F, 0.0F, 1.0F));
/* 108 */     convertMatrixToAngles();
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public RotatedAxes rotateGlobalRoll(float rotateBy) {
/* 114 */     this.rotationMatrix.rotate(rotateBy * 3.1415927F / 180.0F, new Vector3f(1.0F, 0.0F, 0.0F));
/* 115 */     convertMatrixToAngles();
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public RotatedAxes rotateGlobalYawInRads(float rotateBy) {
/* 121 */     this.rotationMatrix.rotate(rotateBy, new Vector3f(0.0F, 1.0F, 0.0F));
/* 122 */     convertMatrixToAngles();
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public RotatedAxes rotateGlobalPitchInRads(float rotateBy) {
/* 128 */     this.rotationMatrix.rotate(rotateBy, new Vector3f(0.0F, 0.0F, 1.0F));
/* 129 */     convertMatrixToAngles();
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public RotatedAxes rotateGlobalRollInRads(float rotateBy) {
/* 135 */     this.rotationMatrix.rotate(rotateBy, new Vector3f(1.0F, 0.0F, 0.0F));
/* 136 */     convertMatrixToAngles();
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotateLocal(float rotateBy, Vector3f rotateAround) {
/* 142 */     this.rotationMatrix.rotate(rotateBy * 3.1415927F / 180.0F, findLocalVectorGlobally(rotateAround));
/* 143 */     convertMatrixToAngles();
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotateGlobal(float rotateBy, Vector3f rotateAround) {
/* 148 */     this.rotationMatrix.rotate(rotateBy * 3.1415927F / 180.0F, rotateAround);
/* 149 */     convertMatrixToAngles();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f findGlobalVectorLocally(Vector3f in) {
/* 155 */     Matrix4f mat = new Matrix4f();
/* 156 */     mat.m00 = in.x;
/* 157 */     mat.m10 = in.y;
/* 158 */     mat.m20 = in.z;
/*     */     
/* 160 */     mat.rotate(-this.rotationYaw * 3.1415927F / 180.0F, new Vector3f(0.0F, 1.0F, 0.0F));
/* 161 */     mat.rotate(-this.rotationPitch * 3.1415927F / 180.0F, new Vector3f(0.0F, 0.0F, 1.0F));
/* 162 */     mat.rotate(-this.rotationRoll * 3.1415927F / 180.0F, new Vector3f(1.0F, 0.0F, 0.0F));
/* 163 */     return new Vector3f(mat.m00, mat.m10, mat.m20);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f findLocalVectorGlobally(Vector3f in) {
/* 169 */     Matrix4f mat = new Matrix4f();
/* 170 */     mat.m00 = in.x;
/* 171 */     mat.m10 = in.y;
/* 172 */     mat.m20 = in.z;
/*     */     
/* 174 */     mat.rotate(this.rotationRoll * 3.1415927F / 180.0F, new Vector3f(1.0F, 0.0F, 0.0F));
/* 175 */     mat.rotate(this.rotationPitch * 3.1415927F / 180.0F, new Vector3f(0.0F, 0.0F, 1.0F));
/* 176 */     mat.rotate(this.rotationYaw * 3.1415927F / 180.0F, new Vector3f(0.0F, 1.0F, 0.0F));
/* 177 */     return new Vector3f(mat.m00, mat.m10, mat.m20);
/*     */   }
/*     */ 
/*     */   
/*     */   private void convertAnglesToMatrix() {
/* 182 */     this.rotationMatrix = new Matrix4f();
/* 183 */     this.rotationMatrix.rotate(this.rotationRoll * 3.1415927F / 180.0F, new Vector3f(1.0F, 0.0F, 0.0F));
/* 184 */     this.rotationMatrix.rotate(this.rotationPitch * 3.1415927F / 180.0F, new Vector3f(0.0F, 0.0F, 1.0F));
/* 185 */     this.rotationMatrix.rotate(this.rotationYaw * 3.1415927F / 180.0F, new Vector3f(0.0F, 1.0F, 0.0F));
/* 186 */     convertMatrixToAngles();
/*     */   }
/*     */   
/*     */   private void convertMatrixToAngles() {
/* 190 */     this.rotationYaw = (float)Math.atan2(this.rotationMatrix.m20, this.rotationMatrix.m00) * 180.0F / 3.1415927F;
/* 191 */     this.rotationPitch = (float)Math.atan2(-this.rotationMatrix.m10, Math.sqrt((this.rotationMatrix.m12 * this.rotationMatrix.m12 + this.rotationMatrix.m11 * this.rotationMatrix.m11))) * 180.0F / 3.1415927F;
/* 192 */     this.rotationRoll = (float)Math.atan2(this.rotationMatrix.m12, this.rotationMatrix.m11) * 180.0F / 3.1415927F;
/*     */   }
/*     */ 
/*     */   
/*     */   public RotatedAxes findLocalAxesGlobally(RotatedAxes in) {
/* 197 */     Matrix4f mat = new Matrix4f();
/* 198 */     mat.load(in.getMatrix());
/*     */     
/* 200 */     mat.rotate(this.rotationRoll * 3.1415927F / 180.0F, new Vector3f(1.0F, 0.0F, 0.0F));
/* 201 */     mat.rotate(this.rotationPitch * 3.1415927F / 180.0F, new Vector3f(0.0F, 0.0F, 1.0F));
/* 202 */     mat.rotate(this.rotationYaw * 3.1415927F / 180.0F, new Vector3f(0.0F, 1.0F, 0.0F));
/*     */     
/* 204 */     return new RotatedAxes(mat);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 209 */     return "RotatedAxes[Yaw = " + getYaw() + ", Pitch = " + getPitch() + ", Roll = " + getRoll() + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\hitbox\maths\RotatedAxes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */