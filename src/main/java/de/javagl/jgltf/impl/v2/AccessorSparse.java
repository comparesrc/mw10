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
/*     */ public class AccessorSparse
/*     */   extends GlTFProperty
/*     */ {
/*     */   private Integer count;
/*     */   private AccessorSparseIndices indices;
/*     */   private AccessorSparseValues values;
/*     */   
/*     */   public void setCount(Integer count) {
/*  57 */     if (count == null) {
/*  58 */       throw new NullPointerException("Invalid value for count: " + count + ", may not be null");
/*     */     }
/*  60 */     if (count.intValue() < 1) {
/*  61 */       throw new IllegalArgumentException("count < 1");
/*     */     }
/*  63 */     this.count = count;
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
/*     */   public Integer getCount() {
/*  75 */     return this.count;
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
/*     */   public void setIndices(AccessorSparseIndices indices) {
/*  88 */     if (indices == null) {
/*  89 */       throw new NullPointerException("Invalid value for indices: " + indices + ", may not be null");
/*     */     }
/*  91 */     this.indices = indices;
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
/*     */   public AccessorSparseIndices getIndices() {
/* 103 */     return this.indices;
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
/*     */   public void setValues(AccessorSparseValues values) {
/* 115 */     if (values == null) {
/* 116 */       throw new NullPointerException("Invalid value for values: " + values + ", may not be null");
/*     */     }
/* 118 */     this.values = values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessorSparseValues getValues() {
/* 129 */     return this.values;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\AccessorSparse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */