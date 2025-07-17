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
/*     */ 
/*     */ public class Asset
/*     */   extends GlTFProperty
/*     */ {
/*     */   private String copyright;
/*     */   private String generator;
/*     */   private Boolean premultipliedAlpha;
/*     */   private AssetProfile profile;
/*     */   private String version;
/*     */   
/*     */   public void setCopyright(String copyright) {
/*  61 */     if (copyright == null) {
/*  62 */       this.copyright = copyright;
/*     */       return;
/*     */     } 
/*  65 */     this.copyright = copyright;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCopyright() {
/*  76 */     return this.copyright;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGenerator(String generator) {
/*  86 */     if (generator == null) {
/*  87 */       this.generator = generator;
/*     */       return;
/*     */     } 
/*  90 */     this.generator = generator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGenerator() {
/* 100 */     return this.generator;
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
/*     */   public void setPremultipliedAlpha(Boolean premultipliedAlpha) {
/* 112 */     if (premultipliedAlpha == null) {
/* 113 */       this.premultipliedAlpha = premultipliedAlpha;
/*     */       return;
/*     */     } 
/* 116 */     this.premultipliedAlpha = premultipliedAlpha;
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
/*     */   public Boolean isPremultipliedAlpha() {
/* 128 */     return this.premultipliedAlpha;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean defaultPremultipliedAlpha() {
/* 139 */     return Boolean.valueOf(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProfile(AssetProfile profile) {
/* 150 */     if (profile == null) {
/* 151 */       this.profile = profile;
/*     */       return;
/*     */     } 
/* 154 */     this.profile = profile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetProfile getProfile() {
/* 165 */     return this.profile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetProfile defaultProfile() {
/* 176 */     return new AssetProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVersion(String version) {
/* 187 */     if (version == null) {
/* 188 */       throw new NullPointerException("Invalid value for version: " + version + ", may not be null");
/*     */     }
/* 190 */     this.version = version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 200 */     return this.version;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Asset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */