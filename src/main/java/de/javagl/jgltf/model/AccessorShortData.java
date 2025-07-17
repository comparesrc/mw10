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
/*     */ public final class AccessorShortData
/*     */   extends AbstractAccessorData
/*     */   implements AccessorData
/*     */ {
/*     */   private final boolean unsigned;
/*     */   
/*     */   public AccessorShortData(int componentType, ByteBuffer bufferViewByteBuffer, int byteOffset, int numElements, ElementType elementType, Integer byteStride) {
/*  75 */     super(componentType, short.class, bufferViewByteBuffer, byteOffset, numElements, elementType, 2, byteStride);
/*     */     
/*  77 */     AccessorDatas.validateShortType(componentType);
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
/*     */   public short get(int elementIndex, int componentIndex) {
/* 109 */     int byteIndex = getByteIndex(elementIndex, componentIndex);
/* 110 */     return getBufferViewByteBuffer().getShort(byteIndex);
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
/*     */   public short get(int globalComponentIndex) {
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
/*     */   public void set(int elementIndex, int componentIndex, short value) {
/* 141 */     int byteIndex = getByteIndex(elementIndex, componentIndex);
/* 142 */     getBufferViewByteBuffer().putShort(byteIndex, value);
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
/*     */   public void set(int globalComponentIndex, short value) {
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
/*     */   
/*     */   public int getInt(int elementIndex, int componentIndex) {
/* 177 */     short value = get(elementIndex, componentIndex);
/* 178 */     return this.unsigned ? Short.toUnsignedInt(value) : value;
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
/*     */   public int getInt(int globalComponentIndex) {
/* 194 */     short value = get(globalComponentIndex);
/* 195 */     return this.unsigned ? Short.toUnsignedInt(value) : value;
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
/*     */   public short[] computeMin() {
/* 207 */     short[] result = new short[getNumComponentsPerElement()];
/* 208 */     Arrays.fill(result, '翿');
/* 209 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 211 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 213 */         result[c] = (short)Math.min(result[c], get(e, c));
/*     */       }
/*     */     } 
/* 216 */     return result;
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
/*     */   public short[] computeMax() {
/* 228 */     short[] result = new short[getNumComponentsPerElement()];
/* 229 */     Arrays.fill(result, -32768);
/* 230 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 232 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 234 */         result[c] = (short)Math.max(result[c], get(e, c));
/*     */       }
/*     */     } 
/* 237 */     return result;
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
/*     */   public int[] computeMinInt() {
/* 250 */     int[] result = new int[getNumComponentsPerElement()];
/* 251 */     Arrays.fill(result, 2147483647);
/* 252 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 254 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 256 */         result[c] = Math.min(result[c], getInt(e, c));
/*     */       }
/*     */     } 
/* 259 */     return result;
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
/*     */   public int[] computeMaxInt() {
/* 272 */     int[] result = new int[getNumComponentsPerElement()];
/* 273 */     Arrays.fill(result, -2147483648);
/* 274 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 276 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 278 */         result[c] = Math.max(result[c], getInt(e, c));
/*     */       }
/*     */     } 
/* 281 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer createByteBuffer() {
/* 287 */     int totalNumComponents = getTotalNumComponents();
/* 288 */     int totalBytes = totalNumComponents * getNumBytesPerComponent();
/*     */     
/* 290 */     ByteBuffer result = ByteBuffer.allocateDirect(totalBytes).order(ByteOrder.nativeOrder());
/* 291 */     for (int i = 0; i < totalNumComponents; i++) {
/*     */       
/* 293 */       short component = get(i);
/* 294 */       result.putShort(component);
/*     */     } 
/* 296 */     result.position(0);
/* 297 */     return result;
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
/* 312 */     StringBuilder sb = new StringBuilder();
/* 313 */     int nc = getNumComponentsPerElement();
/* 314 */     sb.append("[");
/* 315 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 317 */       if (e > 0) {
/*     */         
/* 319 */         sb.append(", ");
/* 320 */         if (elementsPerRow > 0 && e % elementsPerRow == 0)
/*     */         {
/* 322 */           sb.append("\n ");
/*     */         }
/*     */       } 
/* 325 */       if (nc > 1)
/*     */       {
/* 327 */         sb.append("(");
/*     */       }
/* 329 */       for (int c = 0; c < nc; c++) {
/*     */         
/* 331 */         if (c > 0)
/*     */         {
/* 333 */           sb.append(", ");
/*     */         }
/* 335 */         int component = getInt(e, c);
/* 336 */         sb.append(String.format(locale, format, new Object[] { Integer.valueOf(component) }));
/*     */       } 
/* 338 */       if (nc > 1)
/*     */       {
/* 340 */         sb.append(")");
/*     */       }
/*     */     } 
/* 343 */     sb.append("]");
/* 344 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\AccessorShortData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */