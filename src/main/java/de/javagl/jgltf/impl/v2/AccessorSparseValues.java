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
/*     */ public class AccessorSparseValues
/*     */   extends GlTFProperty
/*     */ {
/*     */   private Integer bufferView;
/*     */   private Integer byteOffset;
/*     */   
/*     */   public void setBufferView(Integer bufferView) {
/*  53 */     if (bufferView == null) {
/*  54 */       throw new NullPointerException("Invalid value for bufferView: " + bufferView + ", may not be null");
/*     */     }
/*  56 */     this.bufferView = bufferView;
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
/*     */   public Integer getBufferView() {
/*  68 */     return this.bufferView;
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
/*     */   public void setByteOffset(Integer byteOffset) {
/*  83 */     if (byteOffset == null) {
/*  84 */       this.byteOffset = byteOffset;
/*     */       return;
/*     */     } 
/*  87 */     if (byteOffset.intValue() < 0) {
/*  88 */       throw new IllegalArgumentException("byteOffset < 0");
/*     */     }
/*  90 */     this.byteOffset = byteOffset;
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
/*     */   public Integer getByteOffset() {
/* 103 */     return this.byteOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultByteOffset() {
/* 114 */     return Integer.valueOf(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\AccessorSparseValues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */