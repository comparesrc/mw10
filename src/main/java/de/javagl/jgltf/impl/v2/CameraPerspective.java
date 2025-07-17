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
/*     */ public class CameraPerspective
/*     */   extends GlTFProperty
/*     */ {
/*     */   private Float aspectRatio;
/*     */   private Float yfov;
/*     */   private Float zfar;
/*     */   private Float znear;
/*     */   
/*     */   public void setAspectRatio(Float aspectRatio) {
/*  60 */     if (aspectRatio == null) {
/*  61 */       this.aspectRatio = aspectRatio;
/*     */       return;
/*     */     } 
/*  64 */     if (aspectRatio.floatValue() <= 0.0D) {
/*  65 */       throw new IllegalArgumentException("aspectRatio <= 0.0");
/*     */     }
/*  67 */     this.aspectRatio = aspectRatio;
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
/*  78 */     return this.aspectRatio;
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
/*     */   public void setYfov(Float yfov) {
/*  93 */     if (yfov == null) {
/*  94 */       throw new NullPointerException("Invalid value for yfov: " + yfov + ", may not be null");
/*     */     }
/*  96 */     if (yfov.floatValue() <= 0.0D) {
/*  97 */       throw new IllegalArgumentException("yfov <= 0.0");
/*     */     }
/*  99 */     this.yfov = yfov;
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
/*     */   public Float getYfov() {
/* 111 */     return this.yfov;
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
/*     */   public void setZfar(Float zfar) {
/* 124 */     if (zfar == null) {
/* 125 */       this.zfar = zfar;
/*     */       return;
/*     */     } 
/* 128 */     if (zfar.floatValue() <= 0.0D) {
/* 129 */       throw new IllegalArgumentException("zfar <= 0.0");
/*     */     }
/* 131 */     this.zfar = zfar;
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
/* 142 */     return this.zfar;
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
/* 156 */     if (znear == null) {
/* 157 */       throw new NullPointerException("Invalid value for znear: " + znear + ", may not be null");
/*     */     }
/* 159 */     if (znear.floatValue() <= 0.0D) {
/* 160 */       throw new IllegalArgumentException("znear <= 0.0");
/*     */     }
/* 162 */     this.znear = znear;
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
/* 173 */     return this.znear;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\CameraPerspective.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */