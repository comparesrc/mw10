/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.CameraOrthographicModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultCameraOrthographicModel
/*     */   implements CameraOrthographicModel
/*     */ {
/*     */   private Float xmag;
/*     */   private Float ymag;
/*     */   private Float zfar;
/*     */   private Float znear;
/*     */   
/*     */   public void setXmag(Float xmag) {
/*  63 */     this.xmag = xmag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYmag(Float ymag) {
/*  73 */     this.ymag = ymag;
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
/*     */   public Float getXmag() {
/*  99 */     return this.xmag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getYmag() {
/* 105 */     return this.ymag;
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


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultCameraOrthographicModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */