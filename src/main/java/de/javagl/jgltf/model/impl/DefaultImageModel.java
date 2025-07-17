/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.BufferViewModel;
/*     */ import de.javagl.jgltf.model.ImageModel;
/*     */ import de.javagl.jgltf.model.io.Buffers;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultImageModel
/*     */   extends AbstractNamedModelElement
/*     */   implements ImageModel
/*     */ {
/*     */   private String uri;
/*     */   private String mimeType;
/*     */   private BufferViewModel bufferViewModel;
/*     */   private ByteBuffer imageData;
/*     */   
/*     */   public void setUri(String uri) {
/*  76 */     this.uri = uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMimeType(String mimeType) {
/*  86 */     this.mimeType = mimeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBufferViewModel(BufferViewModel bufferViewModel) {
/*  96 */     this.bufferViewModel = bufferViewModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImageData(ByteBuffer imageData) {
/* 106 */     this.imageData = imageData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUri() {
/* 112 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMimeType() {
/* 118 */     return this.mimeType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferViewModel getBufferViewModel() {
/* 124 */     return this.bufferViewModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getImageData() {
/* 130 */     if (this.imageData == null)
/*     */     {
/* 132 */       return this.bufferViewModel.getBufferViewData();
/*     */     }
/* 134 */     return Buffers.createSlice(this.imageData);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultImageModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */