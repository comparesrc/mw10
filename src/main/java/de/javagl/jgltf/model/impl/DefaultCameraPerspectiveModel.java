/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.CameraPerspectiveModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultCameraPerspectiveModel
/*     */   implements CameraPerspectiveModel
/*     */ {
/*     */   private Float aspectRatio;
/*     */   private Float yfov;
/*     */   private Float zfar;
/*     */   private Float znear;
/*     */   
/*     */   public void setAspectRatio(Float aspectRatio) {
/*  63 */     this.aspectRatio = aspectRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYfov(Float yfov) {
/*  73 */     this.yfov = yfov;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZfar(Float zfar) {
/*  83 */     this.zfar = zfar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZnear(Float znear) {
/*  93 */     this.znear = znear;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getAspectRatio() {
/*  99 */     return this.aspectRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getYfov() {
/* 105 */     return this.yfov;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getZfar() {
/* 111 */     return this.zfar;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getZnear() {
/* 117 */     return this.znear;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultCameraPerspectiveModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */