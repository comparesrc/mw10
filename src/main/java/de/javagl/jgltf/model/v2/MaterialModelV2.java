/*     */ package de.javagl.jgltf.model.v2;
/*     */ 
/*     */ import de.javagl.jgltf.model.MaterialModel;
/*     */ import de.javagl.jgltf.model.TextureModel;
/*     */ import de.javagl.jgltf.model.impl.AbstractNamedModelElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MaterialModelV2
/*     */   extends AbstractNamedModelElement
/*     */   implements MaterialModel
/*     */ {
/*     */   private float[] baseColorFactor;
/*     */   private TextureModel baseColorTexture;
/*     */   private Integer baseColorTexcoord;
/*     */   private float metallicFactor;
/*     */   private float roughnessFactor;
/*     */   private TextureModel metallicRoughnessTexture;
/*     */   private Integer metallicRoughnessTexcoord;
/*     */   private TextureModel normalTexture;
/*     */   private Integer normalTexcoord;
/*     */   private float normalScale;
/*     */   private TextureModel occlusionTexture;
/*     */   private Integer occlusionTexcoord;
/*     */   private float occlusionStrength;
/*     */   private TextureModel emissiveTexture;
/*     */   private Integer emissiveTexcoord;
/*     */   private float[] emissiveFactor;
/*     */   private AlphaMode alphaMode;
/*     */   private float alphaCutoff;
/*     */   private boolean doubleSided;
/*     */   
/*     */   public enum AlphaMode
/*     */   {
/*  50 */     OPAQUE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     MASK,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     BLEND;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MaterialModelV2() {
/* 164 */     this.baseColorFactor = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
/* 165 */     this.baseColorTexture = null;
/* 166 */     this.baseColorTexcoord = null;
/*     */     
/* 168 */     this.metallicFactor = 1.0F;
/* 169 */     this.roughnessFactor = 1.0F;
/* 170 */     this.metallicRoughnessTexture = null;
/* 171 */     this.metallicRoughnessTexcoord = null;
/*     */     
/* 173 */     this.normalScale = 1.0F;
/* 174 */     this.normalTexture = null;
/* 175 */     this.normalTexcoord = null;
/*     */     
/* 177 */     this.occlusionTexture = null;
/* 178 */     this.occlusionTexcoord = null;
/* 179 */     this.occlusionStrength = 1.0F;
/*     */     
/* 181 */     this.emissiveTexture = null;
/* 182 */     this.emissiveTexcoord = null;
/* 183 */     this.emissiveFactor = new float[] { 0.0F, 0.0F, 0.0F };
/*     */     
/* 185 */     this.alphaMode = AlphaMode.OPAQUE;
/* 186 */     this.alphaCutoff = 0.5F;
/*     */     
/* 188 */     this.doubleSided = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getBaseColorFactor() {
/* 198 */     return this.baseColorFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseColorFactor(float[] baseColorFactor) {
/* 208 */     this.baseColorFactor = baseColorFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureModel getBaseColorTexture() {
/* 218 */     return this.baseColorTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseColorTexture(TextureModel baseColorTexture) {
/* 228 */     this.baseColorTexture = baseColorTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getBaseColorTexcoord() {
/* 238 */     return this.baseColorTexcoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseColorTexcoord(Integer baseColorTexcoord) {
/* 248 */     this.baseColorTexcoord = baseColorTexcoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMetallicFactor() {
/* 258 */     return this.metallicFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMetallicFactor(float metallicFactor) {
/* 268 */     this.metallicFactor = metallicFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRoughnessFactor() {
/* 278 */     return this.roughnessFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRoughnessFactor(float roughnessFactor) {
/* 288 */     this.roughnessFactor = roughnessFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureModel getMetallicRoughnessTexture() {
/* 298 */     return this.metallicRoughnessTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMetallicRoughnessTexture(TextureModel metallicRoughnessTexture) {
/* 309 */     this.metallicRoughnessTexture = metallicRoughnessTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getMetallicRoughnessTexcoord() {
/* 319 */     return this.metallicRoughnessTexcoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMetallicRoughnessTexcoord(Integer metallicRoughnessTexcoord) {
/* 329 */     this.metallicRoughnessTexcoord = metallicRoughnessTexcoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureModel getNormalTexture() {
/* 339 */     return this.normalTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNormalTexture(TextureModel normalTexture) {
/* 349 */     this.normalTexture = normalTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getNormalTexcoord() {
/* 359 */     return this.normalTexcoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNormalTexcoord(Integer normalTexcoord) {
/* 369 */     this.normalTexcoord = normalTexcoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getNormalScale() {
/* 379 */     return this.normalScale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNormalScale(float normalScale) {
/* 389 */     this.normalScale = normalScale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureModel getOcclusionTexture() {
/* 399 */     return this.occlusionTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOcclusionTexture(TextureModel occlusionTexture) {
/* 409 */     this.occlusionTexture = occlusionTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getOcclusionTexcoord() {
/* 419 */     return this.occlusionTexcoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOcclusionTexcoord(Integer occlusionTexcoord) {
/* 429 */     this.occlusionTexcoord = occlusionTexcoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getOcclusionStrength() {
/* 439 */     return this.occlusionStrength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOcclusionStrength(float occlusionStrength) {
/* 449 */     this.occlusionStrength = occlusionStrength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureModel getEmissiveTexture() {
/* 459 */     return this.emissiveTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmissiveTexture(TextureModel emissiveTexture) {
/* 469 */     this.emissiveTexture = emissiveTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getEmissiveTexcoord() {
/* 479 */     return this.emissiveTexcoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmissiveTexcoord(Integer emissiveTexcoord) {
/* 489 */     this.emissiveTexcoord = emissiveTexcoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getEmissiveFactor() {
/* 499 */     return this.emissiveFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmissiveFactor(float[] emissiveFactor) {
/* 509 */     this.emissiveFactor = emissiveFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlphaMode getAlphaMode() {
/* 519 */     return this.alphaMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlphaMode(AlphaMode alphaMode) {
/* 529 */     this.alphaMode = alphaMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAlphaCutoff() {
/* 539 */     return this.alphaCutoff;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlphaCutoff(float alphaCutoff) {
/* 549 */     this.alphaCutoff = alphaCutoff;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDoubleSided() {
/* 559 */     return this.doubleSided;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDoubleSided(boolean doubleSided) {
/* 569 */     this.doubleSided = doubleSided;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v2\MaterialModelV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */