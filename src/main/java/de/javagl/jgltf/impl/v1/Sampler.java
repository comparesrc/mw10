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
/*     */ public class Sampler
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private Integer magFilter;
/*     */   private Integer minFilter;
/*     */   private Integer wrapS;
/*     */   private Integer wrapT;
/*     */   
/*     */   public void setMagFilter(Integer magFilter) {
/*  63 */     if (magFilter == null) {
/*  64 */       this.magFilter = magFilter;
/*     */       return;
/*     */     } 
/*  67 */     if (magFilter.intValue() != 9728 && magFilter.intValue() != 9729) {
/*  68 */       throw new IllegalArgumentException("Invalid value for magFilter: " + magFilter + ", valid: [9728, 9729]");
/*     */     }
/*  70 */     this.magFilter = magFilter;
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
/*     */   public Integer getMagFilter() {
/*  82 */     return this.magFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultMagFilter() {
/*  93 */     return Integer.valueOf(9729);
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
/*     */   public void setMinFilter(Integer minFilter) {
/* 107 */     if (minFilter == null) {
/* 108 */       this.minFilter = minFilter;
/*     */       return;
/*     */     } 
/* 111 */     if (minFilter.intValue() != 9728 && minFilter.intValue() != 9729 && minFilter.intValue() != 9984 && minFilter.intValue() != 9985 && minFilter.intValue() != 9986 && minFilter.intValue() != 9987) {
/* 112 */       throw new IllegalArgumentException("Invalid value for minFilter: " + minFilter + ", valid: [9728, 9729, 9984, 9985, 9986, 9987]");
/*     */     }
/* 114 */     this.minFilter = minFilter;
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
/*     */   public Integer getMinFilter() {
/* 126 */     return this.minFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultMinFilter() {
/* 137 */     return Integer.valueOf(9986);
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
/* 151 */     if (wrapS == null) {
/* 152 */       this.wrapS = wrapS;
/*     */       return;
/*     */     } 
/* 155 */     if (wrapS.intValue() != 33071 && wrapS.intValue() != 33648 && wrapS.intValue() != 10497) {
/* 156 */       throw new IllegalArgumentException("Invalid value for wrapS: " + wrapS + ", valid: [33071, 33648, 10497]");
/*     */     }
/* 158 */     this.wrapS = wrapS;
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
/* 170 */     return this.wrapS;
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
/* 181 */     return Integer.valueOf(10497);
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
/* 195 */     if (wrapT == null) {
/* 196 */       this.wrapT = wrapT;
/*     */       return;
/*     */     } 
/* 199 */     if (wrapT.intValue() != 33071 && wrapT.intValue() != 33648 && wrapT.intValue() != 10497) {
/* 200 */       throw new IllegalArgumentException("Invalid value for wrapT: " + wrapT + ", valid: [33071, 33648, 10497]");
/*     */     }
/* 202 */     this.wrapT = wrapT;
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
/* 214 */     return this.wrapT;
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
/* 225 */     return Integer.valueOf(10497);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Sampler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */