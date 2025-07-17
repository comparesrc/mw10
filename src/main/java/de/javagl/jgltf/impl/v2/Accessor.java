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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Accessor
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private Integer bufferView;
/*     */   private Integer byteOffset;
/*     */   private Integer componentType;
/*     */   private Boolean normalized;
/*     */   private Integer count;
/*     */   private String type;
/*     */   private Number[] max;
/*     */   private Number[] min;
/*     */   private AccessorSparse sparse;
/*     */   
/*     */   public void setBufferView(Integer bufferView) {
/*  94 */     if (bufferView == null) {
/*  95 */       this.bufferView = bufferView;
/*     */       return;
/*     */     } 
/*  98 */     this.bufferView = bufferView;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getBufferView() {
/* 108 */     return this.bufferView;
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
/* 123 */     if (byteOffset == null) {
/* 124 */       this.byteOffset = byteOffset;
/*     */       return;
/*     */     } 
/* 127 */     if (byteOffset.intValue() < 0) {
/* 128 */       throw new IllegalArgumentException("byteOffset < 0");
/*     */     }
/* 130 */     this.byteOffset = byteOffset;
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
/* 143 */     return this.byteOffset;
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
/* 154 */     return Integer.valueOf(0);
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
/* 168 */     if (componentType == null) {
/* 169 */       throw new NullPointerException("Invalid value for componentType: " + componentType + ", may not be null");
/*     */     }
/* 171 */     if (componentType.intValue() != 5120 && componentType.intValue() != 5121 && componentType.intValue() != 5122 && componentType.intValue() != 5123 && componentType.intValue() != 5125 && componentType.intValue() != 5126) {
/* 172 */       throw new IllegalArgumentException("Invalid value for componentType: " + componentType + ", valid: [5120, 5121, 5122, 5123, 5125, 5126]");
/*     */     }
/* 174 */     this.componentType = componentType;
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
/* 185 */     return this.componentType;
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
/*     */   public void setNormalized(Boolean normalized) {
/* 197 */     if (normalized == null) {
/* 198 */       this.normalized = normalized;
/*     */       return;
/*     */     } 
/* 201 */     this.normalized = normalized;
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
/*     */   public Boolean isNormalized() {
/* 213 */     return this.normalized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean defaultNormalized() {
/* 224 */     return Boolean.valueOf(false);
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
/*     */   public void setCount(Integer count) {
/* 238 */     if (count == null) {
/* 239 */       throw new NullPointerException("Invalid value for count: " + count + ", may not be null");
/*     */     }
/* 241 */     if (count.intValue() < 1) {
/* 242 */       throw new IllegalArgumentException("count < 1");
/*     */     }
/* 244 */     this.count = count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getCount() {
/* 255 */     return this.count;
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
/*     */   public void setType(String type) {
/* 270 */     if (type == null) {
/* 271 */       throw new NullPointerException("Invalid value for type: " + type + ", may not be null");
/*     */     }
/* 273 */     if (!"SCALAR".equals(type) && !"VEC2".equals(type) && !"VEC3".equals(type) && !"VEC4".equals(type) && !"MAT2".equals(type) && !"MAT3".equals(type) && !"MAT4".equals(type)) {
/* 274 */       throw new IllegalArgumentException("Invalid value for type: " + type + ", valid: [SCALAR, VEC2, VEC3, VEC4, MAT2, MAT3, MAT4]");
/*     */     }
/* 276 */     this.type = type;
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
/*     */   public String getType() {
/* 288 */     return this.type;
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
/*     */   public void setMax(Number[] max) {
/* 304 */     if (max == null) {
/* 305 */       this.max = max;
/*     */       return;
/*     */     } 
/* 308 */     if (max.length < 1) {
/* 309 */       throw new IllegalArgumentException("Number of max elements is < 1");
/*     */     }
/* 311 */     if (max.length > 16) {
/* 312 */       throw new IllegalArgumentException("Number of max elements is > 16");
/*     */     }
/* 314 */     this.max = max;
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
/*     */   public Number[] getMax() {
/* 328 */     return this.max;
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
/*     */   public void setMin(Number[] min) {
/* 344 */     if (min == null) {
/* 345 */       this.min = min;
/*     */       return;
/*     */     } 
/* 348 */     if (min.length < 1) {
/* 349 */       throw new IllegalArgumentException("Number of min elements is < 1");
/*     */     }
/* 351 */     if (min.length > 16) {
/* 352 */       throw new IllegalArgumentException("Number of min elements is > 16");
/*     */     }
/* 354 */     this.min = min;
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
/*     */   public Number[] getMin() {
/* 368 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSparse(AccessorSparse sparse) {
/* 379 */     if (sparse == null) {
/* 380 */       this.sparse = sparse;
/*     */       return;
/*     */     } 
/* 383 */     this.sparse = sparse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessorSparse getSparse() {
/* 394 */     return this.sparse;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Accessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */