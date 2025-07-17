/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import de.javagl.jgltf.model.io.v1.RawBinaryGltfDataReaderV1;
/*     */ import de.javagl.jgltf.model.io.v2.RawBinaryGltfDataReaderV2;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RawGltfDataReader
/*     */ {
/*     */   private static final int MAGIC_BINARY_GLTF_HEADER = 1179937895;
/*     */   private static final int BINARY_GLTF_VERSION_1 = 1;
/*     */   private static final int BINARY_GLTF_VERSION_2 = 2;
/*     */   
/*     */   public static RawGltfData read(InputStream inputStream) throws IOException {
/*  81 */     byte[] rawData = IO.readStream(inputStream);
/*  82 */     if (rawData.length >= 8) {
/*     */ 
/*     */       
/*  85 */       ByteBuffer data = ByteBuffer.wrap(rawData).order(ByteOrder.LITTLE_ENDIAN);
/*  86 */       IntBuffer intData = data.asIntBuffer();
/*  87 */       int magic = intData.get(0);
/*  88 */       if (magic == 1179937895) {
/*     */         
/*  90 */         int version = intData.get(1);
/*  91 */         if (version == 1)
/*     */         {
/*  93 */           return RawBinaryGltfDataReaderV1.readBinaryGltf(data);
/*     */         }
/*  95 */         if (version == 2)
/*     */         {
/*  97 */           return RawBinaryGltfDataReaderV2.readBinaryGltf(data);
/*     */         }
/*  99 */         throw new IOException("Unknown binary glTF version: " + version);
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     ByteBuffer jsonData = Buffers.create(rawData);
/* 104 */     return new RawGltfData(jsonData, null);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\RawGltfDataReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */