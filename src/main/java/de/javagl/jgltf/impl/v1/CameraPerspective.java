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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CameraPerspective
/*     */   extends GlTFProperty
/*     */ {
/*     */   private Float aspectRatio;
/*     */   private Float yfov;
/*     */   private Float zfar;
/*     */   private Float znear;
/*     */   
/*     */   public void setAspectRatio(Float aspectRatio) {
/*  59 */     if (aspectRatio == null) {
/*  60 */       this.aspectRatio = aspectRatio;
/*     */       return;
/*     */     } 
/*  63 */     if (aspectRatio.floatValue() < 0.0D) {
/*  64 */       throw new IllegalArgumentException("aspectRatio < 0.0");
/*     */     }
/*  66 */     this.aspectRatio = aspectRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getAspectRatio() {
/*  77 */     return this.aspectRatio;
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
/*     */   public void setYfov(Float yfov) {
/*  91 */     if (yfov == null) {
/*  92 */       throw new NullPointerException("Invalid value for yfov: " + yfov + ", may not be null");
/*     */     }
/*  94 */     if (yfov.floatValue() < 0.0D) {
/*  95 */       throw new IllegalArgumentException("yfov < 0.0");
/*     */     }
/*  97 */     this.yfov = yfov;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getYfov() {
/* 108 */     return this.yfov;
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
/*     */   public Float getZfar() {
/* 139 */     return this.zfar;
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
/* 153 */     if (znear == null) {
/* 154 */       throw new NullPointerException("Invalid value for znear: " + znear + ", may not be null");
/*     */     }
/* 156 */     if (znear.floatValue() <= 0.0D) {
/* 157 */       throw new IllegalArgumentException("znear <= 0.0");
/*     */     }
/* 159 */     this.znear = znear;
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
/* 170 */     return this.znear;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\CameraPerspective.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */