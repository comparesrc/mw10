/*     */ package de.javagl.jgltf.model;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AccessorFloatData
/*     */   extends AbstractAccessorData
/*     */   implements AccessorData
/*     */ {
/*     */   public AccessorFloatData(int componentType, ByteBuffer bufferViewByteBuffer, int byteOffset, int numElements, ElementType elementType, Integer byteStride) {
/*  70 */     super(componentType, float.class, bufferViewByteBuffer, byteOffset, numElements, elementType, 4, byteStride);
/*     */     
/*  72 */     AccessorDatas.validateFloatType(componentType);
/*     */ 
/*     */     
/*  75 */     int numBytesPerElement = getNumComponentsPerElement() * getNumBytesPerComponent();
/*  76 */     AccessorDatas.validateCapacity(byteOffset, getNumElements(), numBytesPerElement, 
/*  77 */         getByteStridePerElement(), bufferViewByteBuffer
/*  78 */         .capacity());
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
/*     */   public float get(int elementIndex, int componentIndex) {
/*  92 */     int byteIndex = getByteIndex(elementIndex, componentIndex);
/*  93 */     return getBufferViewByteBuffer().getFloat(byteIndex);
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
/*     */   public float get(int globalComponentIndex) {
/* 107 */     int elementIndex = globalComponentIndex / getNumComponentsPerElement();
/*     */     
/* 109 */     int componentIndex = globalComponentIndex % getNumComponentsPerElement();
/* 110 */     return get(elementIndex, componentIndex);
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
/*     */   public void set(int elementIndex, int componentIndex, float value) {
/* 124 */     int byteIndex = getByteIndex(elementIndex, componentIndex);
/* 125 */     getBufferViewByteBuffer().putFloat(byteIndex, value);
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
/*     */   public void set(int globalComponentIndex, float value) {
/* 139 */     int elementIndex = globalComponentIndex / getNumComponentsPerElement();
/*     */     
/* 141 */     int componentIndex = globalComponentIndex % getNumComponentsPerElement();
/* 142 */     set(elementIndex, componentIndex, value);
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
/*     */   public float[] computeMin() {
/* 155 */     float[] result = new float[getNumComponentsPerElement()];
/* 156 */     Arrays.fill(result, Float.MAX_VALUE);
/* 157 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 159 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 161 */         result[c] = Math.min(result[c], get(e, c));
/*     */       }
/*     */     } 
/* 164 */     return result;
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
/*     */   public float[] computeMax() {
/* 176 */     float[] result = new float[getNumComponentsPerElement()];
/* 177 */     Arrays.fill(result, -3.4028235E38F);
/* 178 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 180 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 182 */         result[c] = Math.max(result[c], get(e, c));
/*     */       }
/*     */     } 
/* 185 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer createByteBuffer() {
/* 191 */     int totalNumComponents = getTotalNumComponents();
/* 192 */     int totalBytes = totalNumComponents * getNumBytesPerComponent();
/*     */     
/* 194 */     ByteBuffer result = ByteBuffer.allocateDirect(totalBytes).order(ByteOrder.nativeOrder());
/* 195 */     for (int i = 0; i < totalNumComponents; i++) {
/*     */       
/* 197 */       float component = get(i);
/* 198 */       result.putFloat(component);
/*     */     } 
/* 200 */     result.position(0);
/* 201 */     return result;
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
/*     */   public String createString(Locale locale, String format, int elementsPerRow) {
/* 216 */     StringBuilder sb = new StringBuilder();
/* 217 */     int nc = getNumComponentsPerElement();
/* 218 */     sb.append("[");
/* 219 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 221 */       if (e > 0) {
/*     */         
/* 223 */         sb.append(", ");
/* 224 */         if (elementsPerRow > 0 && e % elementsPerRow == 0)
/*     */         {
/* 226 */           sb.append("\n ");
/*     */         }
/*     */       } 
/* 229 */       if (nc > 1)
/*     */       {
/* 231 */         sb.append("(");
/*     */       }
/* 233 */       for (int c = 0; c < nc; c++) {
/*     */         
/* 235 */         if (c > 0)
/*     */         {
/* 237 */           sb.append(", ");
/*     */         }
/* 239 */         float component = get(e, c);
/* 240 */         sb.append(String.format(locale, format, new Object[] { Float.valueOf(component) }));
/*     */       } 
/* 242 */       if (nc > 1)
/*     */       {
/* 244 */         sb.append(")");
/*     */       }
/*     */     } 
/* 247 */     sb.append("]");
/* 248 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\AccessorFloatData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */