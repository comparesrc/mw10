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
/*     */ public class BufferView
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private Integer buffer;
/*     */   private Integer byteOffset;
/*     */   private Integer byteLength;
/*     */   private Integer byteStride;
/*     */   private Integer target;
/*     */   
/*     */   public void setBuffer(Integer buffer) {
/*  64 */     if (buffer == null) {
/*  65 */       throw new NullPointerException("Invalid value for buffer: " + buffer + ", may not be null");
/*     */     }
/*  67 */     this.buffer = buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getBuffer() {
/*  77 */     return this.buffer;
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
/*     */   public void setByteOffset(Integer byteOffset) {
/*  91 */     if (byteOffset == null) {
/*  92 */       this.byteOffset = byteOffset;
/*     */       return;
/*     */     } 
/*  95 */     if (byteOffset.intValue() < 0) {
/*  96 */       throw new IllegalArgumentException("byteOffset < 0");
/*     */     }
/*  98 */     this.byteOffset = byteOffset;
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
/*     */   public void setByteLength(Integer byteLength) {
/* 135 */     if (byteLength == null) {
/* 136 */       throw new NullPointerException("Invalid value for byteLength: " + byteLength + ", may not be null");
/*     */     }
/* 138 */     if (byteLength.intValue() < 1) {
/* 139 */       throw new IllegalArgumentException("byteLength < 1");
/*     */     }
/* 141 */     this.byteLength = byteLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getByteLength() {
/* 152 */     return this.byteLength;
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
/*     */   public void setByteStride(Integer byteStride) {
/* 166 */     if (byteStride == null) {
/* 167 */       this.byteStride = byteStride;
/*     */       return;
/*     */     } 
/* 170 */     if (byteStride.intValue() > 252) {
/* 171 */       throw new IllegalArgumentException("byteStride > 252");
/*     */     }
/* 173 */     if (byteStride.intValue() < 4) {
/* 174 */       throw new IllegalArgumentException("byteStride < 4");
/*     */     }
/* 176 */     this.byteStride = byteStride;
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
/*     */   public Integer getByteStride() {
/* 188 */     return this.byteStride;
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
/* 202 */     if (target == null) {
/* 203 */       this.target = target;
/*     */       return;
/*     */     } 
/* 206 */     if (target.intValue() != 34962 && target.intValue() != 34963) {
/* 207 */       throw new IllegalArgumentException("Invalid value for target: " + target + ", valid: [34962, 34963]");
/*     */     }
/* 209 */     this.target = target;
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
/* 221 */     return this.target;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\BufferView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */