/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.AccessorData;
/*     */ import de.javagl.jgltf.model.AccessorDatas;
/*     */ import de.javagl.jgltf.model.AccessorModel;
/*     */ import de.javagl.jgltf.model.Accessors;
/*     */ import de.javagl.jgltf.model.BufferViewModel;
/*     */ import de.javagl.jgltf.model.ElementType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultAccessorModel
/*     */   extends AbstractNamedModelElement
/*     */   implements AccessorModel
/*     */ {
/*     */   private final int componentType;
/*     */   private boolean normalized;
/*     */   private int byteOffset;
/*     */   private BufferViewModel bufferViewModel;
/*     */   private final ElementType elementType;
/*     */   private final int count;
/*     */   private int byteStride;
/*     */   private AccessorData accessorData;
/*     */   private Number[] max;
/*     */   private Number[] min;
/*     */   
/*     */   public DefaultAccessorModel(int componentType, int count, ElementType elementType) {
/* 104 */     this.componentType = componentType;
/* 105 */     this.count = count;
/* 106 */     this.elementType = elementType;
/* 107 */     this.byteStride = elementType.getByteStride(componentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBufferViewModel(BufferViewModel bufferViewModel) {
/* 117 */     this.bufferViewModel = bufferViewModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByteOffset(int byteOffset) {
/* 127 */     this.byteOffset = byteOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByteStride(int byteStride) {
/* 138 */     this.byteStride = byteStride;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferViewModel getBufferViewModel() {
/* 144 */     return this.bufferViewModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getComponentType() {
/* 150 */     return this.componentType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getComponentDataType() {
/* 156 */     return Accessors.getDataTypeForAccessorComponentType(
/* 157 */         getComponentType());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNormalized() {
/* 163 */     return this.normalized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNormalized(boolean normalized) {
/* 173 */     this.normalized = normalized;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getComponentSizeInBytes() {
/* 179 */     return Accessors.getNumBytesForAccessorComponentType(this.componentType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getElementSizeInBytes() {
/* 185 */     return this.elementType.getNumComponents() * getComponentSizeInBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPaddedElementSizeInBytes() {
/* 191 */     return this.elementType.getByteStride(this.componentType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getByteOffset() {
/* 197 */     return this.byteOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 203 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementType getElementType() {
/* 209 */     return this.elementType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getByteStride() {
/* 215 */     return this.byteStride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAccessorData(AccessorData accessorData) {
/* 225 */     this.accessorData = accessorData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessorData getAccessorData() {
/* 231 */     return this.accessorData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number[] getMin() {
/* 238 */     if (this.min == null)
/*     */     {
/* 240 */       this.min = AccessorDatas.computeMin(getAccessorData());
/*     */     }
/* 242 */     return (Number[])this.min.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Number[] getMax() {
/* 248 */     if (this.max == null)
/*     */     {
/* 250 */       this.max = AccessorDatas.computeMax(getAccessorData());
/*     */     }
/* 252 */     return (Number[])this.max.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultAccessorModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */