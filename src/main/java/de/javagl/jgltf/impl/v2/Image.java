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
/*     */ public class Image
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private String uri;
/*     */   private String mimeType;
/*     */   private Integer bufferView;
/*     */   
/*     */   public void setUri(String uri) {
/*  50 */     if (uri == null) {
/*  51 */       this.uri = uri;
/*     */       return;
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
/*     */   public void setMimeType(String mimeType) {
/*  76 */     if (mimeType == null) {
/*  77 */       this.mimeType = mimeType;
/*     */       return;
/*     */     } 
/*  80 */     this.mimeType = mimeType;
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
/*     */   public String getMimeType() {
/*  92 */     return this.mimeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBufferView(Integer bufferView) {
/* 103 */     if (bufferView == null) {
/* 104 */       this.bufferView = bufferView;
/*     */       return;
/*     */     } 
/* 107 */     this.bufferView = bufferView;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getBufferView() {
/* 118 */     return this.bufferView;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Image.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */