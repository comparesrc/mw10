/*     */ package de.javagl.jgltf.impl.v1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  55 */     if (xmag == null) {
/*  56 */       throw new NullPointerException("Invalid value for xmag: " + xmag + ", may not be null");
/*     */     }
/*  58 */     this.xmag = xmag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getXmag() {
/*  68 */     return this.xmag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYmag(Float ymag) {
/*  79 */     if (ymag == null) {
/*  80 */       throw new NullPointerException("Invalid value for ymag: " + ymag + ", may not be null");
/*     */     }
/*  82 */     this.ymag = ymag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getYmag() {
/*  92 */     return this.ymag;
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
/*     */   public void setZfar(Float zfar) {
/* 106 */     if (zfar == null) {
/* 107 */       throw new NullPointerException("Invalid value for zfar: " + zfar + ", may not be null");
/*     */     }
/* 109 */     if (zfar.floatValue() < 0.0D) {
/* 110 */       throw new IllegalArgumentException("zfar < 0.0");
/*     */     }
/* 112 */     this.zfar = zfar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getZfar() {
/* 123 */     return this.zfar;
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
/* 137 */     if (znear == null) {
/* 138 */       throw new NullPointerException("Invalid value for znear: " + znear + ", may not be null");
/*     */     }
/* 140 */     if (znear.floatValue() < 0.0D) {
/* 141 */       throw new IllegalArgumentException("znear < 0.0");
/*     */     }
/* 143 */     this.znear = znear;
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
/* 154 */     return this.znear;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\CameraOrthographic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */