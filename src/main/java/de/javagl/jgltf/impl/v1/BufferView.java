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
/*     */ public class BufferView
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private String buffer;
/*     */   private Integer byteOffset;
/*     */   private Integer byteLength;
/*     */   private Integer target;
/*     */   
/*     */   public void setBuffer(String buffer) {
/*  56 */     if (buffer == null) {
/*  57 */       throw new NullPointerException("Invalid value for buffer: " + buffer + ", may not be null");
/*     */     }
/*  59 */     this.buffer = buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBuffer() {
/*  69 */     return this.buffer;
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
/*  83 */     if (byteOffset == null) {
/*  84 */       throw new NullPointerException("Invalid value for byteOffset: " + byteOffset + ", may not be null");
/*     */     }
/*  86 */     if (byteOffset.intValue() < 0) {
/*  87 */       throw new IllegalArgumentException("byteOffset < 0");
/*     */     }
/*  89 */     this.byteOffset = byteOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getByteOffset() {
/* 100 */     return this.byteOffset;
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
/* 114 */     if (byteLength == null) {
/* 115 */       this.byteLength = byteLength;
/*     */       return;
/*     */     } 
/* 118 */     if (byteLength.intValue() < 0) {
/* 119 */       throw new IllegalArgumentException("byteLength < 0");
/*     */     }
/* 121 */     this.byteLength = byteLength;
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
/*     */   public Integer getByteLength() {
/* 133 */     return this.byteLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultByteLength() {
/* 144 */     return Integer.valueOf(0);
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
/*     */   public void setTarget(Integer target) {
/* 157 */     if (target == null) {
/* 158 */       this.target = target;
/*     */       return;
/*     */     } 
/* 161 */     if (target.intValue() != 34962 && target.intValue() != 34963) {
/* 162 */       throw new IllegalArgumentException("Invalid value for target: " + target + ", valid: [34962, 34963]");
/*     */     }
/* 164 */     this.target = target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getTarget() {
/* 175 */     return this.target;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\BufferView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */