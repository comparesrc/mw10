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
/*     */ public class MaterialPbrMetallicRoughness
/*     */   extends GlTFProperty
/*     */ {
/*     */   private float[] baseColorFactor;
/*     */   private TextureInfo baseColorTexture;
/*     */   private Float metallicFactor;
/*     */   private Float roughnessFactor;
/*     */   private TextureInfo metallicRoughnessTexture;
/*     */   
/*     */   public void setBaseColorFactor(float[] baseColorFactor) {
/*  78 */     if (baseColorFactor == null) {
/*  79 */       this.baseColorFactor = baseColorFactor;
/*     */       return;
/*     */     } 
/*  82 */     if (baseColorFactor.length < 4) {
/*  83 */       throw new IllegalArgumentException("Number of baseColorFactor elements is < 4");
/*     */     }
/*  85 */     if (baseColorFactor.length > 4) {
/*  86 */       throw new IllegalArgumentException("Number of baseColorFactor elements is > 4");
/*     */     }
/*  88 */     for (float baseColorFactorElement : baseColorFactor) {
/*  89 */       if (baseColorFactorElement > 1.0D) {
/*  90 */         throw new IllegalArgumentException("baseColorFactorElement > 1.0");
/*     */       }
/*  92 */       if (baseColorFactorElement < 0.0D) {
/*  93 */         throw new IllegalArgumentException("baseColorFactorElement < 0.0");
/*     */       }
/*     */     } 
/*  96 */     this.baseColorFactor = baseColorFactor;
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
/*     */   public float[] getBaseColorFactor() {
/* 112 */     return this.baseColorFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] defaultBaseColorFactor() {
/* 123 */     return new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseColorTexture(TextureInfo baseColorTexture) {
/* 133 */     if (baseColorTexture == null) {
/* 134 */       this.baseColorTexture = baseColorTexture;
/*     */       return;
/*     */     } 
/* 137 */     this.baseColorTexture = baseColorTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureInfo getBaseColorTexture() {
/* 147 */     return this.baseColorTexture;
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
/*     */   public void setMetallicFactor(Float metallicFactor) {
/* 162 */     if (metallicFactor == null) {
/* 163 */       this.metallicFactor = metallicFactor;
/*     */       return;
/*     */     } 
/* 166 */     if (metallicFactor.floatValue() > 1.0D) {
/* 167 */       throw new IllegalArgumentException("metallicFactor > 1.0");
/*     */     }
/* 169 */     if (metallicFactor.floatValue() < 0.0D) {
/* 170 */       throw new IllegalArgumentException("metallicFactor < 0.0");
/*     */     }
/* 172 */     this.metallicFactor = metallicFactor;
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
/*     */   public Float getMetallicFactor() {
/* 185 */     return this.metallicFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float defaultMetallicFactor() {
/* 196 */     return Float.valueOf(1.0F);
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
/*     */   public void setRoughnessFactor(Float roughnessFactor) {
/* 211 */     if (roughnessFactor == null) {
/* 212 */       this.roughnessFactor = roughnessFactor;
/*     */       return;
/*     */     } 
/* 215 */     if (roughnessFactor.floatValue() > 1.0D) {
/* 216 */       throw new IllegalArgumentException("roughnessFactor > 1.0");
/*     */     }
/* 218 */     if (roughnessFactor.floatValue() < 0.0D) {
/* 219 */       throw new IllegalArgumentException("roughnessFactor < 0.0");
/*     */     }
/* 221 */     this.roughnessFactor = roughnessFactor;
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
/*     */   public Float getRoughnessFactor() {
/* 234 */     return this.roughnessFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float defaultRoughnessFactor() {
/* 245 */     return Float.valueOf(1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMetallicRoughnessTexture(TextureInfo metallicRoughnessTexture) {
/* 255 */     if (metallicRoughnessTexture == null) {
/* 256 */       this.metallicRoughnessTexture = metallicRoughnessTexture;
/*     */       return;
/*     */     } 
/* 259 */     this.metallicRoughnessTexture = metallicRoughnessTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureInfo getMetallicRoughnessTexture() {
/* 269 */     return this.metallicRoughnessTexture;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\MaterialPbrMetallicRoughness.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */