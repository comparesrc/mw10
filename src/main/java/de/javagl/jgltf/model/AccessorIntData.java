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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AccessorIntData
/*     */   extends AbstractAccessorData
/*     */   implements AccessorData
/*     */ {
/*     */   private final boolean unsigned;
/*     */   
/*     */   public AccessorIntData(int componentType, ByteBuffer bufferViewByteBuffer, int byteOffset, int numElements, ElementType elementType, Integer byteStride) {
/*  75 */     super(componentType, int.class, bufferViewByteBuffer, byteOffset, numElements, elementType, 4, byteStride);
/*     */     
/*  77 */     AccessorDatas.validateIntType(componentType);
/*     */     
/*  79 */     this.unsigned = AccessorDatas.isUnsignedType(componentType);
/*     */ 
/*     */     
/*  82 */     int numBytesPerElement = getNumComponentsPerElement() * getNumBytesPerComponent();
/*  83 */     AccessorDatas.validateCapacity(byteOffset, getNumElements(), numBytesPerElement, 
/*  84 */         getByteStridePerElement(), bufferViewByteBuffer
/*  85 */         .capacity());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnsigned() {
/*  95 */     return this.unsigned;
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
/*     */   public int get(int elementIndex, int componentIndex) {
/* 109 */     int byteIndex = getByteIndex(elementIndex, componentIndex);
/* 110 */     return getBufferViewByteBuffer().getInt(byteIndex);
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
/*     */   public int get(int globalComponentIndex) {
/* 124 */     int elementIndex = globalComponentIndex / getNumComponentsPerElement();
/*     */     
/* 126 */     int componentIndex = globalComponentIndex % getNumComponentsPerElement();
/* 127 */     return get(elementIndex, componentIndex);
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
/*     */   public void set(int elementIndex, int componentIndex, int value) {
/* 141 */     int byteIndex = getByteIndex(elementIndex, componentIndex);
/* 142 */     getBufferViewByteBuffer().putInt(byteIndex, value);
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
/*     */   public void set(int globalComponentIndex, int value) {
/* 156 */     int elementIndex = globalComponentIndex / getNumComponentsPerElement();
/*     */     
/* 158 */     int componentIndex = globalComponentIndex % getNumComponentsPerElement();
/* 159 */     set(elementIndex, componentIndex, value);
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
/*     */   
/*     */   public long getLong(int elementIndex, int componentIndex) {
/* 176 */     int value = get(elementIndex, componentIndex);
/* 177 */     return this.unsigned ? Integer.toUnsignedLong(value) : value;
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
/*     */   public long getLong(int globalComponentIndex) {
/* 193 */     int value = get(globalComponentIndex);
/* 194 */     return this.unsigned ? Integer.toUnsignedLong(value) : value;
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
/*     */   public int[] computeMin() {
/* 206 */     int[] result = new int[getNumComponentsPerElement()];
/* 207 */     Arrays.fill(result, 2147483647);
/* 208 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 210 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 212 */         result[c] = Math.min(result[c], get(e, c));
/*     */       }
/*     */     } 
/* 215 */     return result;
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
/*     */   public int[] computeMax() {
/* 227 */     int[] result = new int[getNumComponentsPerElement()];
/* 228 */     Arrays.fill(result, -2147483648);
/* 229 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 231 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 233 */         result[c] = Math.max(result[c], get(e, c));
/*     */       }
/*     */     } 
/* 236 */     return result;
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
/*     */   public long[] computeMinLong() {
/* 249 */     long[] result = new long[getNumComponentsPerElement()];
/* 250 */     Arrays.fill(result, Long.MAX_VALUE);
/* 251 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 253 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 255 */         result[c] = Math.min(result[c], getLong(e, c));
/*     */       }
/*     */     } 
/* 258 */     return result;
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
/*     */   public long[] computeMaxLong() {
/* 271 */     long[] result = new long[getNumComponentsPerElement()];
/* 272 */     Arrays.fill(result, Long.MIN_VALUE);
/* 273 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 275 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 277 */         result[c] = Math.max(result[c], getLong(e, c));
/*     */       }
/*     */     } 
/* 280 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer createByteBuffer() {
/* 286 */     int totalNumComponents = getTotalNumComponents();
/* 287 */     int totalBytes = totalNumComponents * getNumBytesPerComponent();
/*     */     
/* 289 */     ByteBuffer result = ByteBuffer.allocateDirect(totalBytes).order(ByteOrder.nativeOrder());
/* 290 */     for (int i = 0; i < totalNumComponents; i++) {
/*     */       
/* 292 */       int component = get(i);
/* 293 */       result.putInt(component);
/*     */     } 
/* 295 */     result.position(0);
/* 296 */     return result;
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
/* 311 */     StringBuilder sb = new StringBuilder();
/* 312 */     int nc = getNumComponentsPerElement();
/* 313 */     sb.append("[");
/* 314 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 316 */       if (e > 0) {
/*     */         
/* 318 */         sb.append(", ");
/* 319 */         if (elementsPerRow > 0 && e % elementsPerRow == 0)
/*     */         {
/* 321 */           sb.append("\n ");
/*     */         }
/*     */       } 
/* 324 */       if (nc > 1)
/*     */       {
/* 326 */         sb.append("(");
/*     */       }
/* 328 */       for (int c = 0; c < nc; c++) {
/*     */         
/* 330 */         if (c > 0)
/*     */         {
/* 332 */           sb.append(", ");
/*     */         }
/* 334 */         long component = getLong(e, c);
/* 335 */         sb.append(String.format(locale, format, new Object[] { Long.valueOf(component) }));
/*     */       } 
/* 337 */       if (nc > 1)
/*     */       {
/* 339 */         sb.append(")");
/*     */       }
/*     */     } 
/* 342 */     sb.append("]");
/* 343 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\AccessorIntData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */