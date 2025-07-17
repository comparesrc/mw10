/*     */ package de.javagl.jgltf.impl.v2;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CameraOrthographic
/*     */   extends GlTFProperty
/*     */ {
/*     */   private Float xmag;
/*     */   private Float ymag;
/*     */   private Float zfar;
/*     */   private Float znear;
/*     */   
/*     */   public void setXmag(Float xmag) {
/*  63 */     if (xmag == null) {
/*  64 */       throw new NullPointerException("Invalid value for xmag: " + xmag + ", may not be null");
/*     */     }
/*  66 */     this.xmag = xmag;
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
/*     */   public Float getXmag() {
/*  78 */     return this.xmag;
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
/*     */   public void setYmag(Float ymag) {
/*  91 */     if (ymag == null) {
/*  92 */       throw new NullPointerException("Invalid value for ymag: " + ymag + ", may not be null");
/*     */     }
/*  94 */     this.ymag = ymag;
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
/*     */   public Float getYmag() {
/* 106 */     return this.ymag;
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
/*     */   public void setZfar(Float zfar) {
/* 122 */     if (zfar == null) {
/* 123 */       throw new NullPointerException("Invalid value for zfar: " + zfar + ", may not be null");
/*     */     }
/* 125 */     if (zfar.floatValue() <= 0.0D) {
/* 126 */       throw new IllegalArgumentException("zfar <= 0.0");
/*     */     }
/* 128 */     this.zfar = zfar;
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
/*     */   public Float getZfar() {
/* 141 */     return this.zfar;
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
/*     */   public void setZnear(Float znear) {
/* 155 */     if (znear == null) {
/* 156 */       throw new NullPointerException("Invalid value for znear: " + znear + ", may not be null");
/*     */     }
/* 158 */     if (znear.floatValue() < 0.0D) {
/* 159 */       throw new IllegalArgumentException("znear < 0.0");
/*     */     }
/* 161 */     this.znear = znear;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getZnear() {
/* 172 */     return this.znear;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\CameraOrthographic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */