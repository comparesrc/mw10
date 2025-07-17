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
/*     */ public class Asset
/*     */   extends GlTFProperty
/*     */ {
/*     */   private String copyright;
/*     */   private String generator;
/*     */   private String version;
/*     */   private String minVersion;
/*     */   
/*     */   public void setCopyright(String copyright) {
/*  56 */     if (copyright == null) {
/*  57 */       this.copyright = copyright;
/*     */       return;
/*     */     } 
/*  60 */     this.copyright = copyright;
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
/*  71 */     return this.copyright;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGenerator(String generator) {
/*  81 */     if (generator == null) {
/*  82 */       this.generator = generator;
/*     */       return;
/*     */     } 
/*  85 */     this.generator = generator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGenerator() {
/*  95 */     return this.generator;
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
/*     */   public void setVersion(String version) {
/* 107 */     if (version == null) {
/* 108 */       throw new NullPointerException("Invalid value for version: " + version + ", may not be null");
/*     */     }
/* 110 */     this.version = version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 121 */     return this.version;
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
/*     */   public void setMinVersion(String minVersion) {
/* 133 */     if (minVersion == null) {
/* 134 */       this.minVersion = minVersion;
/*     */       return;
/*     */     } 
/* 137 */     this.minVersion = minVersion;
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
/*     */   public String getMinVersion() {
/* 149 */     return this.minVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Asset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */