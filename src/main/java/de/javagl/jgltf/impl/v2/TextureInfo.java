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
/*     */ public class TextureInfo
/*     */   extends GlTFProperty
/*     */ {
/*     */   private Integer index;
/*     */   private Integer texCoord;
/*     */   
/*     */   public void setIndex(Integer index) {
/*  45 */     if (index == null) {
/*  46 */       throw new NullPointerException("Invalid value for index: " + index + ", may not be null");
/*     */     }
/*  48 */     this.index = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getIndex() {
/*  58 */     return this.index;
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
/*     */   public void setTexCoord(Integer texCoord) {
/*  73 */     if (texCoord == null) {
/*  74 */       this.texCoord = texCoord;
/*     */       return;
/*     */     } 
/*  77 */     if (texCoord.intValue() < 0) {
/*  78 */       throw new IllegalArgumentException("texCoord < 0");
/*     */     }
/*  80 */     this.texCoord = texCoord;
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
/*     */   public Integer getTexCoord() {
/*  93 */     return this.texCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultTexCoord() {
/* 104 */     return Integer.valueOf(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\TextureInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */