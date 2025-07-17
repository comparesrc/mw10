/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.ShortBuffer;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Buffers
/*     */ {
/*     */   public static String readAsString(ByteBuffer byteBuffer) {
/*  53 */     if (byteBuffer == null)
/*     */     {
/*  55 */       return null;
/*     */     }
/*  57 */     byte[] array = new byte[byteBuffer.capacity()];
/*  58 */     byteBuffer.slice().get(array);
/*  59 */     return new String(array);
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
/*     */   public static ByteBuffer createSlice(ByteBuffer byteBuffer) {
/*  73 */     if (byteBuffer == null)
/*     */     {
/*  75 */       return null;
/*     */     }
/*  77 */     return byteBuffer.slice().order(byteBuffer.order());
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
/*     */   
/*     */   public static ByteBuffer createSlice(ByteBuffer byteBuffer, int position, int length) {
/*  96 */     if (byteBuffer == null)
/*     */     {
/*  98 */       return null;
/*     */     }
/* 100 */     int oldPosition = byteBuffer.position();
/* 101 */     int oldLimit = byteBuffer.limit();
/*     */     
/*     */     try {
/* 104 */       int newLimit = position + length;
/* 105 */       if (newLimit > byteBuffer.capacity())
/*     */       {
/* 107 */         throw new IllegalArgumentException("The new limit is " + newLimit + ", but the capacity is " + byteBuffer
/*     */             
/* 109 */             .capacity());
/*     */       }
/* 111 */       byteBuffer.limit(newLimit);
/* 112 */       byteBuffer.position(position);
/* 113 */       ByteBuffer slice = byteBuffer.slice();
/* 114 */       slice.order(byteBuffer.order());
/* 115 */       return slice;
/*     */     }
/*     */     finally {
/*     */       
/* 119 */       byteBuffer.limit(oldLimit);
/* 120 */       byteBuffer.position(oldPosition);
/*     */     } 
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
/*     */   public static ByteBuffer create(byte[] data) {
/* 133 */     return create(data, 0, data.length);
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
/*     */   public static ByteBuffer create(byte[] data, int offset, int length) {
/* 147 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
/* 148 */     byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 149 */     byteBuffer.put(data, offset, length);
/* 150 */     byteBuffer.position(0);
/* 151 */     return byteBuffer;
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
/*     */   public static ByteBuffer create(int size) {
/* 164 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(size);
/* 165 */     byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 166 */     return byteBuffer;
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
/*     */   public static InputStream createByteBufferInputStream(ByteBuffer byteBuffer) {
/* 180 */     return new ByteBufferInputStream(byteBuffer);
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
/*     */   public static ByteBuffer concat(Collection<? extends ByteBuffer> byteBuffers) {
/* 196 */     if (byteBuffers == null || byteBuffers.isEmpty())
/*     */     {
/* 198 */       return ByteBuffer.allocateDirect(0).order(ByteOrder.nativeOrder());
/*     */     }
/*     */ 
/*     */     
/* 202 */     int resultCapacity = byteBuffers.stream().mapToInt(Buffer::capacity).reduce(0, (a, b) -> a + b);
/*     */ 
/*     */     
/* 205 */     ByteBuffer newByteBuffer = ByteBuffer.allocateDirect(resultCapacity).order(ByteOrder.nativeOrder());
/* 206 */     for (ByteBuffer byteBuffer : byteBuffers)
/*     */     {
/* 208 */       newByteBuffer.put(byteBuffer.slice());
/*     */     }
/* 210 */     newByteBuffer.position(0);
/* 211 */     return newByteBuffer;
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
/*     */   public static ByteBuffer createByteBufferFrom(FloatBuffer buffer) {
/* 225 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buffer.capacity() * 4);
/*     */     
/* 227 */     FloatBuffer floatBuffer = byteBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
/* 228 */     floatBuffer.put(buffer.slice());
/* 229 */     return byteBuffer;
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
/*     */   public static ByteBuffer createByteBufferFrom(IntBuffer buffer) {
/* 242 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buffer.capacity() * 4);
/*     */     
/* 244 */     IntBuffer intBuffer = byteBuffer.order(ByteOrder.nativeOrder()).asIntBuffer();
/* 245 */     intBuffer.put(buffer.slice());
/* 246 */     return byteBuffer;
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
/*     */   public static ByteBuffer createByteBufferFrom(ShortBuffer buffer) {
/* 259 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buffer.capacity() * 2);
/*     */     
/* 261 */     ShortBuffer shortBuffer = byteBuffer.order(ByteOrder.nativeOrder()).asShortBuffer();
/* 262 */     shortBuffer.put(buffer.slice());
/* 263 */     return byteBuffer;
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
/*     */   public static ByteBuffer castToByteBuffer(IntBuffer buffer) {
/* 277 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buffer.capacity()).order(ByteOrder.nativeOrder());
/* 278 */     for (int i = 0; i < buffer.capacity(); i++)
/*     */     {
/* 280 */       byteBuffer.put(i, (byte)buffer.get(i));
/*     */     }
/* 282 */     return byteBuffer;
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
/*     */   public static ByteBuffer castToShortByteBuffer(IntBuffer buffer) {
/* 296 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buffer.capacity() * 2);
/*     */     
/* 298 */     ShortBuffer shortBuffer = byteBuffer.order(ByteOrder.nativeOrder()).asShortBuffer();
/* 299 */     for (int i = 0; i < buffer.capacity(); i++)
/*     */     {
/* 301 */       shortBuffer.put(i, (short)buffer.get(i));
/*     */     }
/* 303 */     return byteBuffer;
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
/*     */   public static ByteBuffer copyOf(ByteBuffer buffer, int newCapacity) {
/* 320 */     ByteBuffer copy = ByteBuffer.allocateDirect(newCapacity);
/* 321 */     copy.order(buffer.order());
/* 322 */     if (newCapacity < buffer.capacity()) {
/*     */       
/* 324 */       copy.slice().put(createSlice(buffer, 0, newCapacity));
/*     */     }
/*     */     else {
/*     */       
/* 328 */       copy.slice().put(createSlice(buffer));
/*     */     } 
/* 330 */     return copy;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bufferCopy(ByteBuffer src, int srcPos, ByteBuffer dst, int dstPos, int length) {
/* 351 */     for (int i = 0; i < length; i++) {
/*     */       
/* 353 */       byte b = src.get(srcPos + i);
/* 354 */       dst.put(dstPos + i, b);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\Buffers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */