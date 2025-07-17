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
/*     */ public class Sampler
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private Integer magFilter;
/*     */   private Integer minFilter;
/*     */   private Integer wrapS;
/*     */   private Integer wrapT;
/*     */   
/*     */   public void setMagFilter(Integer magFilter) {
/*  60 */     if (magFilter == null) {
/*  61 */       this.magFilter = magFilter;
/*     */       return;
/*     */     } 
/*  64 */     if (magFilter.intValue() != 9728 && magFilter.intValue() != 9729) {
/*  65 */       throw new IllegalArgumentException("Invalid value for magFilter: " + magFilter + ", valid: [9728, 9729]");
/*     */     }
/*  67 */     this.magFilter = magFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getMagFilter() {
/*  78 */     return this.magFilter;
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
/*     */   public void setMinFilter(Integer minFilter) {
/*  91 */     if (minFilter == null) {
/*  92 */       this.minFilter = minFilter;
/*     */       return;
/*     */     } 
/*  95 */     if (minFilter.intValue() != 9728 && minFilter.intValue() != 9729 && minFilter.intValue() != 9984 && minFilter.intValue() != 9985 && minFilter.intValue() != 9986 && minFilter.intValue() != 9987) {
/*  96 */       throw new IllegalArgumentException("Invalid value for minFilter: " + minFilter + ", valid: [9728, 9729, 9984, 9985, 9986, 9987]");
/*     */     }
/*  98 */     this.minFilter = minFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getMinFilter() {
/* 109 */     return this.minFilter;
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
/*     */   public void setWrapS(Integer wrapS) {
/* 123 */     if (wrapS == null) {
/* 124 */       this.wrapS = wrapS;
/*     */       return;
/*     */     } 
/* 127 */     if (wrapS.intValue() != 33071 && wrapS.intValue() != 33648 && wrapS.intValue() != 10497) {
/* 128 */       throw new IllegalArgumentException("Invalid value for wrapS: " + wrapS + ", valid: [33071, 33648, 10497]");
/*     */     }
/* 130 */     this.wrapS = wrapS;
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
/*     */   public Integer getWrapS() {
/* 142 */     return this.wrapS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultWrapS() {
/* 153 */     return Integer.valueOf(10497);
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
/*     */   public void setWrapT(Integer wrapT) {
/* 167 */     if (wrapT == null) {
/* 168 */       this.wrapT = wrapT;
/*     */       return;
/*     */     } 
/* 171 */     if (wrapT.intValue() != 33071 && wrapT.intValue() != 33648 && wrapT.intValue() != 10497) {
/* 172 */       throw new IllegalArgumentException("Invalid value for wrapT: " + wrapT + ", valid: [33071, 33648, 10497]");
/*     */     }
/* 174 */     this.wrapT = wrapT;
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
/*     */   public Integer getWrapT() {
/* 186 */     return this.wrapT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultWrapT() {
/* 197 */     return Integer.valueOf(10497);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Sampler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */