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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Texture
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private Integer format;
/*     */   private Integer internalFormat;
/*     */   private String sampler;
/*     */   private String source;
/*     */   private Integer target;
/*     */   private Integer type;
/*     */   
/*     */   public void setFormat(Integer format) {
/*  73 */     if (format == null) {
/*  74 */       this.format = format;
/*     */       return;
/*     */     } 
/*  77 */     if (format.intValue() != 6406 && format.intValue() != 6407 && format.intValue() != 6408 && format.intValue() != 6409 && format.intValue() != 6410) {
/*  78 */       throw new IllegalArgumentException("Invalid value for format: " + format + ", valid: [6406, 6407, 6408, 6409, 6410]");
/*     */     }
/*  80 */     this.format = format;
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
/*     */   public Integer getFormat() {
/*  92 */     return this.format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultFormat() {
/* 103 */     return Integer.valueOf(6408);
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
/*     */   public void setInternalFormat(Integer internalFormat) {
/* 117 */     if (internalFormat == null) {
/* 118 */       this.internalFormat = internalFormat;
/*     */       return;
/*     */     } 
/* 121 */     if (internalFormat.intValue() != 6406 && internalFormat.intValue() != 6407 && internalFormat.intValue() != 6408 && internalFormat.intValue() != 6409 && internalFormat.intValue() != 6410) {
/* 122 */       throw new IllegalArgumentException("Invalid value for internalFormat: " + internalFormat + ", valid: [6406, 6407, 6408, 6409, 6410]");
/*     */     }
/* 124 */     this.internalFormat = internalFormat;
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
/*     */   public Integer getInternalFormat() {
/* 136 */     return this.internalFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultInternalFormat() {
/* 147 */     return Integer.valueOf(6408);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSampler(String sampler) {
/* 158 */     if (sampler == null) {
/* 159 */       throw new NullPointerException("Invalid value for sampler: " + sampler + ", may not be null");
/*     */     }
/* 161 */     this.sampler = sampler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSampler() {
/* 171 */     return this.sampler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSource(String source) {
/* 182 */     if (source == null) {
/* 183 */       throw new NullPointerException("Invalid value for source: " + source + ", may not be null");
/*     */     }
/* 185 */     this.source = source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSource() {
/* 195 */     return this.source;
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
/*     */   public void setTarget(Integer target) {
/* 209 */     if (target == null) {
/* 210 */       this.target = target;
/*     */       return;
/*     */     } 
/* 213 */     if (target.intValue() != 3553) {
/* 214 */       throw new IllegalArgumentException("Invalid value for target: " + target + ", valid: [3553]");
/*     */     }
/* 216 */     this.target = target;
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
/*     */   public Integer getTarget() {
/* 228 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultTarget() {
/* 239 */     return Integer.valueOf(3553);
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
/*     */   public void setType(Integer type) {
/* 253 */     if (type == null) {
/* 254 */       this.type = type;
/*     */       return;
/*     */     } 
/* 257 */     if (type.intValue() != 5121 && type.intValue() != 33635 && type.intValue() != 32819 && type.intValue() != 32820) {
/* 258 */       throw new IllegalArgumentException("Invalid value for type: " + type + ", valid: [5121, 33635, 32819, 32820]");
/*     */     }
/* 260 */     this.type = type;
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
/*     */   public Integer getType() {
/* 272 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultType() {
/* 283 */     return Integer.valueOf(5121);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Texture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */