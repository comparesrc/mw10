/*     */ package de.javagl.jgltf.model.io.v1;
/*     */ 
/*     */ import de.javagl.jgltf.model.io.Buffers;
/*     */ import de.javagl.jgltf.model.io.RawGltfData;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RawBinaryGltfDataReaderV1
/*     */ {
/*  47 */   private static final Logger logger = Logger.getLogger(RawBinaryGltfDataReaderV1.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BINARY_GLTF_VERSION_1_HEADER_LENGTH_IN_BYTES = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int CONTENT_FORMAT_JSON = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RawGltfData readBinaryGltf(ByteBuffer data) throws IOException {
/*  70 */     int headerLength = 20;
/*  71 */     if (data.capacity() < headerLength)
/*     */     {
/*  73 */       throw new IOException("Expected header of size " + headerLength + ", but only found " + data
/*  74 */           .capacity() + " bytes");
/*     */     }
/*  76 */     IntBuffer intData = data.asIntBuffer();
/*  77 */     int length = intData.get(2);
/*  78 */     if (length > data.capacity())
/*     */     {
/*  80 */       throw new IOException("Data length is " + data
/*  81 */           .capacity() + ", expected " + length);
/*     */     }
/*  83 */     if (length < data.capacity()) {
/*     */       
/*  85 */       logger.info("Data length is " + data.capacity() + ", expected " + length + " - truncating");
/*     */       
/*  87 */       data = Buffers.createSlice(data, 0, length);
/*     */     } 
/*     */     
/*  90 */     int contentLength = intData.get(3);
/*  91 */     int contentFormat = intData.get(4);
/*  92 */     if (contentFormat != 0)
/*     */     {
/*  94 */       throw new IOException("Expected content format to be JSON (0), but found " + contentFormat);
/*     */     }
/*     */     
/*  97 */     ByteBuffer contentData = Buffers.createSlice(data, headerLength, contentLength);
/*     */     
/*  99 */     int bodyByteOffset = headerLength + contentLength;
/* 100 */     int bodyByteLength = length - bodyByteOffset;
/* 101 */     ByteBuffer bodyData = null;
/* 102 */     if (bodyByteLength > 0)
/*     */     {
/* 104 */       bodyData = Buffers.createSlice(data, bodyByteOffset, bodyByteLength);
/*     */     }
/*     */ 
/*     */     
/* 108 */     return new RawGltfData(contentData, bodyData);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v1\RawBinaryGltfDataReaderV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */