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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Material
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private MaterialPbrMetallicRoughness pbrMetallicRoughness;
/*     */   private MaterialNormalTextureInfo normalTexture;
/*     */   private MaterialOcclusionTextureInfo occlusionTexture;
/*     */   private TextureInfo emissiveTexture;
/*     */   private float[] emissiveFactor;
/*     */   private String alphaMode;
/*     */   private Float alphaCutoff;
/*     */   private Boolean doubleSided;
/*     */   
/*     */   public void setPbrMetallicRoughness(MaterialPbrMetallicRoughness pbrMetallicRoughness) {
/*  88 */     if (pbrMetallicRoughness == null) {
/*  89 */       this.pbrMetallicRoughness = pbrMetallicRoughness;
/*     */       return;
/*     */     } 
/*  92 */     this.pbrMetallicRoughness = pbrMetallicRoughness;
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
/*     */   public MaterialPbrMetallicRoughness getPbrMetallicRoughness() {
/* 105 */     return this.pbrMetallicRoughness;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNormalTexture(MaterialNormalTextureInfo normalTexture) {
/* 115 */     if (normalTexture == null) {
/* 116 */       this.normalTexture = normalTexture;
/*     */       return;
/*     */     } 
/* 119 */     this.normalTexture = normalTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MaterialNormalTextureInfo getNormalTexture() {
/* 129 */     return this.normalTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOcclusionTexture(MaterialOcclusionTextureInfo occlusionTexture) {
/* 139 */     if (occlusionTexture == null) {
/* 140 */       this.occlusionTexture = occlusionTexture;
/*     */       return;
/*     */     } 
/* 143 */     this.occlusionTexture = occlusionTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MaterialOcclusionTextureInfo getOcclusionTexture() {
/* 153 */     return this.occlusionTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmissiveTexture(TextureInfo emissiveTexture) {
/* 163 */     if (emissiveTexture == null) {
/* 164 */       this.emissiveTexture = emissiveTexture;
/*     */       return;
/*     */     } 
/* 167 */     this.emissiveTexture = emissiveTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureInfo getEmissiveTexture() {
/* 177 */     return this.emissiveTexture;
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
/*     */   public void setEmissiveFactor(float[] emissiveFactor) {
/* 195 */     if (emissiveFactor == null) {
/* 196 */       this.emissiveFactor = emissiveFactor;
/*     */       return;
/*     */     } 
/* 199 */     if (emissiveFactor.length < 3) {
/* 200 */       throw new IllegalArgumentException("Number of emissiveFactor elements is < 3");
/*     */     }
/* 202 */     if (emissiveFactor.length > 3) {
/* 203 */       throw new IllegalArgumentException("Number of emissiveFactor elements is > 3");
/*     */     }
/* 205 */     for (float emissiveFactorElement : emissiveFactor) {
/* 206 */       if (emissiveFactorElement > 1.0D) {
/* 207 */         throw new IllegalArgumentException("emissiveFactorElement > 1.0");
/*     */       }
/* 209 */       if (emissiveFactorElement < 0.0D) {
/* 210 */         throw new IllegalArgumentException("emissiveFactorElement < 0.0");
/*     */       }
/*     */     } 
/* 213 */     this.emissiveFactor = emissiveFactor;
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
/*     */   public float[] getEmissiveFactor() {
/* 229 */     return this.emissiveFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] defaultEmissiveFactor() {
/* 240 */     return new float[] { 0.0F, 0.0F, 0.0F };
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
/*     */   public void setAlphaMode(String alphaMode) {
/* 254 */     if (alphaMode == null) {
/* 255 */       this.alphaMode = alphaMode;
/*     */       return;
/*     */     } 
/* 258 */     if (!"OPAQUE".equals(alphaMode) && !"MASK".equals(alphaMode) && !"BLEND".equals(alphaMode)) {
/* 259 */       throw new IllegalArgumentException("Invalid value for alphaMode: " + alphaMode + ", valid: [OPAQUE, MASK, BLEND]");
/*     */     }
/* 261 */     this.alphaMode = alphaMode;
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
/*     */   public String getAlphaMode() {
/* 273 */     return this.alphaMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String defaultAlphaMode() {
/* 284 */     return "OPAQUE";
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
/*     */   public void setAlphaCutoff(Float alphaCutoff) {
/* 298 */     if (alphaCutoff == null) {
/* 299 */       this.alphaCutoff = alphaCutoff;
/*     */       return;
/*     */     } 
/* 302 */     if (alphaCutoff.floatValue() < 0.0D) {
/* 303 */       throw new IllegalArgumentException("alphaCutoff < 0.0");
/*     */     }
/* 305 */     this.alphaCutoff = alphaCutoff;
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
/*     */   public Float getAlphaCutoff() {
/* 317 */     return this.alphaCutoff;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float defaultAlphaCutoff() {
/* 328 */     return Float.valueOf(0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDoubleSided(Boolean doubleSided) {
/* 339 */     if (doubleSided == null) {
/* 340 */       this.doubleSided = doubleSided;
/*     */       return;
/*     */     } 
/* 343 */     this.doubleSided = doubleSided;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isDoubleSided() {
/* 354 */     return this.doubleSided;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean defaultDoubleSided() {
/* 365 */     return Boolean.valueOf(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Material.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */