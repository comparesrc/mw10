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
/*     */ public class Buffer
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private String uri;
/*     */   private Integer byteLength;
/*     */   private String type;
/*     */   
/*     */   public void setUri(String uri) {
/*  51 */     if (uri == null) {
/*  52 */       throw new NullPointerException("Invalid value for uri: " + uri + ", may not be null");
/*     */     }
/*  54 */     this.uri = uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUri() {
/*  64 */     return this.uri;
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
/*  78 */     if (byteLength == null) {
/*  79 */       this.byteLength = byteLength;
/*     */       return;
/*     */     } 
/*  82 */     if (byteLength.intValue() < 0) {
/*  83 */       throw new IllegalArgumentException("byteLength < 0");
/*     */     }
/*  85 */     this.byteLength = byteLength;
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
/*  97 */     return this.byteLength;
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
/* 108 */     return Integer.valueOf(0);
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
/*     */   public void setType(String type) {
/* 122 */     if (type == null) {
/* 123 */       this.type = type;
/*     */       return;
/*     */     } 
/* 126 */     if (!"arraybuffer".equals(type) && !"text".equals(type)) {
/* 127 */       throw new IllegalArgumentException("Invalid value for type: " + type + ", valid: [\"arraybuffer\", \"text\"]");
/*     */     }
/* 129 */     this.type = type;
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
/* 141 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String defaultType() {
/* 152 */     return "arraybuffer";
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Buffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */