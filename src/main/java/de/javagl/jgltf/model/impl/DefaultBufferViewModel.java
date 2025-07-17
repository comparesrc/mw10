/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.BufferModel;
/*     */ import de.javagl.jgltf.model.BufferViewModel;
/*     */ import de.javagl.jgltf.model.io.Buffers;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultBufferViewModel
/*     */   extends AbstractNamedModelElement
/*     */   implements BufferViewModel
/*     */ {
/*     */   private BufferModel bufferModel;
/*     */   private int byteOffset;
/*     */   private int byteLength;
/*     */   private Integer byteStride;
/*     */   private final Integer target;
/*     */   private Consumer<? super ByteBuffer> sparseSubstitutionCallback;
/*     */   private boolean sparseSubstitutionApplied;
/*     */   
/*     */   public DefaultBufferViewModel(Integer target) {
/*  87 */     this.byteOffset = 0;
/*  88 */     this.byteLength = 0;
/*  89 */     this.target = target;
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
/*     */   public void setSparseSubstitutionCallback(Consumer<? super ByteBuffer> sparseSubstitutionCallback) {
/* 102 */     this.sparseSubstitutionCallback = sparseSubstitutionCallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBufferModel(BufferModel bufferModel) {
/* 112 */     this.bufferModel = bufferModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByteOffset(int byteOffset) {
/* 122 */     this.byteOffset = byteOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByteLength(int byteLength) {
/* 132 */     this.byteLength = byteLength;
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
/*     */   public void setByteStride(Integer byteStride) {
/* 144 */     this.byteStride = byteStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getBufferViewData() {
/* 151 */     ByteBuffer bufferData = this.bufferModel.getBufferData();
/*     */     
/* 153 */     ByteBuffer bufferViewData = Buffers.createSlice(bufferData, getByteOffset(), getByteLength());
/* 154 */     if (this.sparseSubstitutionCallback != null && !this.sparseSubstitutionApplied) {
/*     */       
/* 156 */       this.sparseSubstitutionCallback.accept(bufferViewData);
/* 157 */       this.sparseSubstitutionApplied = true;
/*     */     } 
/* 159 */     return bufferViewData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferModel getBufferModel() {
/* 165 */     return this.bufferModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getByteOffset() {
/* 171 */     return this.byteOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getByteLength() {
/* 177 */     return this.byteLength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getByteStride() {
/* 183 */     return this.byteStride;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getTarget() {
/* 189 */     return this.target;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultBufferViewModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */