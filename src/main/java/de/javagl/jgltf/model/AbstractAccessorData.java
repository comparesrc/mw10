/*     */ package de.javagl.jgltf.model;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractAccessorData
/*     */   implements AccessorData
/*     */ {
/*     */   private final Class<?> componentType;
/*     */   private final ByteBuffer bufferViewByteBuffer;
/*     */   private final int byteOffset;
/*     */   private final int numElements;
/*     */   private final ElementType elementType;
/*     */   private final int numBytesPerComponent;
/*     */   private final int byteStridePerElement;
/*     */   
/*     */   AbstractAccessorData(int accessorComponentType, Class<?> componentType, ByteBuffer bufferViewByteBuffer, int byteOffset, int numElements, ElementType elementType, int numBytesPerComponent, Integer byteStride) {
/*  95 */     Objects.requireNonNull(bufferViewByteBuffer, "The bufferViewByteBuffer is null");
/*     */ 
/*     */     
/*  98 */     this.componentType = componentType;
/*  99 */     this.bufferViewByteBuffer = bufferViewByteBuffer;
/* 100 */     this.byteOffset = byteOffset;
/* 101 */     this.numElements = numElements;
/* 102 */     this.elementType = elementType;
/* 103 */     this.numBytesPerComponent = numBytesPerComponent;
/* 104 */     if (byteStride == null || byteStride.intValue() == 0) {
/*     */       
/* 106 */       this
/* 107 */         .byteStridePerElement = elementType.getByteStride(accessorComponentType);
/*     */     }
/*     */     else {
/*     */       
/* 111 */       this.byteStridePerElement = byteStride.intValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Class<?> getComponentType() {
/* 118 */     return this.componentType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getNumElements() {
/* 124 */     return this.numElements;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getNumComponentsPerElement() {
/* 130 */     return this.elementType.getNumComponents();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getTotalNumComponents() {
/* 136 */     return this.numElements * getNumComponentsPerElement();
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
/*     */   protected final int getByteIndex(int elementIndex, int componentIndex) {
/* 151 */     int byteIndex = this.byteOffset + elementIndex * this.byteStridePerElement;
/*     */     
/* 153 */     if (this.componentType == byte.class) {
/*     */       
/* 155 */       if (this.elementType == ElementType.MAT2)
/*     */       {
/* 157 */         int columnIndex = componentIndex / 2;
/* 158 */         int rowIndex = componentIndex % 2;
/* 159 */         byteIndex += columnIndex * 4 + rowIndex;
/*     */       }
/* 161 */       else if (this.elementType == ElementType.MAT3)
/*     */       {
/* 163 */         int columnIndex = componentIndex / 3;
/* 164 */         int rowIndex = componentIndex % 3;
/* 165 */         byteIndex += columnIndex * 4 + rowIndex;
/*     */       }
/*     */       else
/*     */       {
/* 169 */         byteIndex += componentIndex * this.numBytesPerComponent;
/*     */       }
/*     */     
/* 172 */     } else if (this.componentType == short.class) {
/*     */       
/* 174 */       if (this.elementType == ElementType.MAT3)
/*     */       {
/* 176 */         int columnIndex = componentIndex / 3;
/* 177 */         int rowIndex = componentIndex % 3;
/* 178 */         byteIndex += columnIndex * 8 + rowIndex * 2;
/*     */       }
/*     */       else
/*     */       {
/* 182 */         byteIndex += componentIndex * this.numBytesPerComponent;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 187 */       byteIndex += componentIndex * this.numBytesPerComponent;
/*     */     } 
/* 189 */     return byteIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuffer getBufferViewByteBuffer() {
/* 200 */     return this.bufferViewByteBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getByteStridePerElement() {
/* 210 */     return this.byteStridePerElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getNumBytesPerComponent() {
/* 220 */     return this.numBytesPerComponent;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\AbstractAccessorData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */