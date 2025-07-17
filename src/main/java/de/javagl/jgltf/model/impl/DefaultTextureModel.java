/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.ImageModel;
/*     */ import de.javagl.jgltf.model.TextureModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultTextureModel
/*     */   extends AbstractNamedModelElement
/*     */   implements TextureModel
/*     */ {
/*     */   private Integer magFilter;
/*     */   private Integer minFilter;
/*     */   private Integer wrapS;
/*     */   private Integer wrapT;
/*     */   private ImageModel imageModel;
/*     */   
/*     */   public void setImageModel(ImageModel imageModel) {
/*  78 */     this.imageModel = imageModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getMagFilter() {
/*  84 */     return this.magFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMagFilter(Integer magFilter) {
/*  94 */     this.magFilter = magFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getMinFilter() {
/* 100 */     return this.minFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinFilter(Integer minFilter) {
/* 110 */     this.minFilter = minFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getWrapS() {
/* 116 */     return this.wrapS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWrapS(Integer wrapS) {
/* 126 */     this.wrapS = wrapS;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getWrapT() {
/* 132 */     return this.wrapT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWrapT(Integer wrapT) {
/* 142 */     this.wrapT = wrapT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageModel getImageModel() {
/* 148 */     return this.imageModel;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultTextureModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */