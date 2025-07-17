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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private String bufferView;
/*     */   private Integer byteOffset;
/*     */   private Integer byteStride;
/*     */   private Integer componentType;
/*     */   private Integer count;
/*     */   private String type;
/*     */   private Number[] max;
/*     */   private Number[] min;
/*     */   
/*     */   public void setBufferView(String bufferView) {
/*  94 */     if (bufferView == null) {
/*  95 */       throw new NullPointerException("Invalid value for bufferView: " + bufferView + ", may not be null");
/*     */     }
/*  97 */     this.bufferView = bufferView;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBufferView() {
/* 107 */     return this.bufferView;
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
/* 122 */     if (byteOffset == null) {
/* 123 */       throw new NullPointerException("Invalid value for byteOffset: " + byteOffset + ", may not be null");
/*     */     }
/* 125 */     if (byteOffset.intValue() < 0) {
/* 126 */       throw new IllegalArgumentException("byteOffset < 0");
/*     */     }
/* 128 */     this.byteOffset = byteOffset;
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
/*     */   public Integer getByteOffset() {
/* 140 */     return this.byteOffset;
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
/*     */   public void setByteStride(Integer byteStride) {
/* 156 */     if (byteStride == null) {
/* 157 */       this.byteStride = byteStride;
/*     */       return;
/*     */     } 
/* 160 */     if (byteStride.intValue() > 255) {
/* 161 */       throw new IllegalArgumentException("byteStride > 255");
/*     */     }
/* 163 */     if (byteStride.intValue() < 0) {
/* 164 */       throw new IllegalArgumentException("byteStride < 0");
/*     */     }
/* 166 */     this.byteStride = byteStride;
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
/*     */   public Integer getByteStride() {
/* 180 */     return this.byteStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultByteStride() {
/* 191 */     return Integer.valueOf(0);
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
/* 205 */     if (componentType == null) {
/* 206 */       throw new NullPointerException("Invalid value for componentType: " + componentType + ", may not be null");
/*     */     }
/* 208 */     if (componentType.intValue() != 5120 && componentType.intValue() != 5121 && componentType.intValue() != 5122 && componentType.intValue() != 5123 && componentType.intValue() != 5126) {
/* 209 */       throw new IllegalArgumentException("Invalid value for componentType: " + componentType + ", valid: [5120, 5121, 5122, 5123, 5126]");
/*     */     }
/* 211 */     this.componentType = componentType;
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
/* 222 */     return this.componentType;
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
/* 236 */     if (count == null) {
/* 237 */       throw new NullPointerException("Invalid value for count: " + count + ", may not be null");
/*     */     }
/* 239 */     if (count.intValue() < 1) {
/* 240 */       throw new IllegalArgumentException("count < 1");
/*     */     }
/* 242 */     this.count = count;
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
/* 253 */     return this.count;
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
/*     */   public void setType(String type) {
/* 269 */     if (type == null) {
/* 270 */       throw new NullPointerException("Invalid value for type: " + type + ", may not be null");
/*     */     }
/* 272 */     if (!"SCALAR".equals(type) && !"VEC2".equals(type) && !"VEC3".equals(type) && !"VEC4".equals(type) && !"MAT2".equals(type) && !"MAT3".equals(type) && !"MAT4".equals(type)) {
/* 273 */       throw new IllegalArgumentException("Invalid value for type: " + type + ", valid: [\"SCALAR\", \"VEC2\", \"VEC3\", \"VEC4\", \"MAT2\", \"MAT3\", \"MAT4\"]");
/*     */     }
/* 275 */     this.type = type;
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
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Accessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */