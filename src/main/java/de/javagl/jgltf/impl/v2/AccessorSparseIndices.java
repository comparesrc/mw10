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
/*     */ public class AccessorSparseIndices
/*     */   extends GlTFProperty
/*     */ {
/*     */   private Integer bufferView;
/*     */   private Integer byteOffset;
/*     */   private Integer componentType;
/*     */   
/*     */   public void setBufferView(Integer bufferView) {
/*  59 */     if (bufferView == null) {
/*  60 */       throw new NullPointerException("Invalid value for bufferView: " + bufferView + ", may not be null");
/*     */     }
/*  62 */     this.bufferView = bufferView;
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
/*     */   public Integer getBufferView() {
/*  75 */     return this.bufferView;
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
/*  90 */     if (byteOffset == null) {
/*  91 */       this.byteOffset = byteOffset;
/*     */       return;
/*     */     } 
/*  94 */     if (byteOffset.intValue() < 0) {
/*  95 */       throw new IllegalArgumentException("byteOffset < 0");
/*     */     }
/*  97 */     this.byteOffset = byteOffset;
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
/* 110 */     return this.byteOffset;
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
/* 121 */     return Integer.valueOf(0);
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
/*     */   public void setComponentType(Integer componentType) {
/* 135 */     if (componentType == null) {
/* 136 */       throw new NullPointerException("Invalid value for componentType: " + componentType + ", may not be null");
/*     */     }
/* 138 */     if (componentType.intValue() != 5121 && componentType.intValue() != 5123 && componentType.intValue() != 5125) {
/* 139 */       throw new IllegalArgumentException("Invalid value for componentType: " + componentType + ", valid: [5121, 5123, 5125]");
/*     */     }
/* 141 */     this.componentType = componentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getComponentType() {
/* 152 */     return this.componentType;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\AccessorSparseIndices.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */