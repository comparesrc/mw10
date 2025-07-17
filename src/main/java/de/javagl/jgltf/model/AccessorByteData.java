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
/*     */ public final class AccessorByteData
/*     */   extends AbstractAccessorData
/*     */   implements AccessorData
/*     */ {
/*     */   private final boolean unsigned;
/*     */   
/*     */   public AccessorByteData(int componentType, ByteBuffer bufferViewByteBuffer, int byteOffset, int numElements, ElementType elementType, Integer byteStride) {
/*  74 */     super(componentType, byte.class, bufferViewByteBuffer, byteOffset, numElements, elementType, 1, byteStride);
/*     */     
/*  76 */     AccessorDatas.validateByteType(componentType);
/*  77 */     this.unsigned = AccessorDatas.isUnsignedType(componentType);
/*     */ 
/*     */     
/*  80 */     int numBytesPerElement = getNumComponentsPerElement() * getNumBytesPerComponent();
/*  81 */     AccessorDatas.validateCapacity(byteOffset, getNumElements(), numBytesPerElement, 
/*  82 */         getByteStridePerElement(), bufferViewByteBuffer
/*  83 */         .capacity());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnsigned() {
/*  93 */     return this.unsigned;
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
/*     */   public byte get(int elementIndex, int componentIndex) {
/* 107 */     int byteIndex = getByteIndex(elementIndex, componentIndex);
/* 108 */     return getBufferViewByteBuffer().get(byteIndex);
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
/*     */   public byte get(int globalComponentIndex) {
/* 122 */     int elementIndex = globalComponentIndex / getNumComponentsPerElement();
/*     */     
/* 124 */     int componentIndex = globalComponentIndex % getNumComponentsPerElement();
/* 125 */     return get(elementIndex, componentIndex);
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
/*     */   public void set(int elementIndex, int componentIndex, byte value) {
/* 139 */     int byteIndex = getByteIndex(elementIndex, componentIndex);
/* 140 */     getBufferViewByteBuffer().put(byteIndex, value);
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
/*     */   public void set(int globalComponentIndex, byte value) {
/* 154 */     int elementIndex = globalComponentIndex / getNumComponentsPerElement();
/*     */     
/* 156 */     int componentIndex = globalComponentIndex % getNumComponentsPerElement();
/* 157 */     set(elementIndex, componentIndex, value);
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
/* 175 */     byte value = get(elementIndex, componentIndex);
/* 176 */     return this.unsigned ? Byte.toUnsignedInt(value) : value;
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
/* 192 */     byte value = get(globalComponentIndex);
/* 193 */     return this.unsigned ? Byte.toUnsignedInt(value) : value;
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
/*     */   public byte[] computeMin() {
/* 205 */     byte[] result = new byte[getNumComponentsPerElement()];
/* 206 */     Arrays.fill(result, 127);
/* 207 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 209 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 211 */         result[c] = (byte)Math.min(result[c], get(e, c));
/*     */       }
/*     */     } 
/* 214 */     return result;
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
/*     */   public byte[] computeMax() {
/* 226 */     byte[] result = new byte[getNumComponentsPerElement()];
/* 227 */     Arrays.fill(result, -128);
/* 228 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 230 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 232 */         result[c] = (byte)Math.max(result[c], get(e, c));
/*     */       }
/*     */     } 
/* 235 */     return result;
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
/* 248 */     int[] result = new int[getNumComponentsPerElement()];
/* 249 */     Arrays.fill(result, 2147483647);
/* 250 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 252 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 254 */         result[c] = Math.min(result[c], getInt(e, c));
/*     */       }
/*     */     } 
/* 257 */     return result;
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
/* 270 */     int[] result = new int[getNumComponentsPerElement()];
/* 271 */     Arrays.fill(result, -2147483648);
/* 272 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 274 */       for (int c = 0; c < getNumComponentsPerElement(); c++)
/*     */       {
/* 276 */         result[c] = Math.max(result[c], getInt(e, c));
/*     */       }
/*     */     } 
/* 279 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer createByteBuffer() {
/* 285 */     int totalNumComponents = getTotalNumComponents();
/* 286 */     int totalBytes = totalNumComponents * getNumBytesPerComponent();
/*     */     
/* 288 */     ByteBuffer result = ByteBuffer.allocateDirect(totalBytes).order(ByteOrder.nativeOrder());
/* 289 */     for (int i = 0; i < totalNumComponents; i++) {
/*     */       
/* 291 */       byte component = get(i);
/* 292 */       result.put(component);
/*     */     } 
/* 294 */     result.position(0);
/* 295 */     return result;
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
/* 310 */     StringBuilder sb = new StringBuilder();
/* 311 */     int nc = getNumComponentsPerElement();
/* 312 */     sb.append("[");
/* 313 */     for (int e = 0; e < getNumElements(); e++) {
/*     */       
/* 315 */       if (e > 0) {
/*     */         
/* 317 */         sb.append(", ");
/* 318 */         if (elementsPerRow > 0 && e % elementsPerRow == 0)
/*     */         {
/* 320 */           sb.append("\n ");
/*     */         }
/*     */       } 
/* 323 */       if (nc > 1)
/*     */       {
/* 325 */         sb.append("(");
/*     */       }
/* 327 */       for (int c = 0; c < nc; c++) {
/*     */         
/* 329 */         if (c > 0)
/*     */         {
/* 331 */           sb.append(", ");
/*     */         }
/* 333 */         int component = getInt(e, c);
/* 334 */         sb.append(String.format(locale, format, new Object[] { Integer.valueOf(component) }));
/*     */       } 
/* 336 */       if (nc > 1)
/*     */       {
/* 338 */         sb.append(")");
/*     */       }
/*     */     } 
/* 341 */     sb.append("]");
/* 342 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\AccessorByteData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */