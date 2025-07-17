/*     */ package de.javagl.jgltf.model.io.v2;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v2.GlTF;
/*     */ import de.javagl.jgltf.model.io.Buffers;
/*     */ import de.javagl.jgltf.model.io.GltfWriter;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GltfAssetWriterV2
/*     */ {
/*     */   private static final int MAGIC_BINARY_GLTF_HEADER = 1179937895;
/*     */   private static final int BINARY_GLTF_VERSION = 2;
/*     */   private static final int CHUNK_TYPE_JSON = 1313821514;
/*     */   private static final int CHUNK_TYPE_BIN = 5130562;
/*     */   
/*     */   public void writeBinary(GltfAssetV2 gltfAsset, OutputStream outputStream) throws IOException {
/*     */     byte[] jsonData;
/*  95 */     GlTF gltf = gltfAsset.getGltf();
/*     */     
/*  97 */     try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
/*     */       
/*  99 */       GltfWriter gltfWriter = new GltfWriter();
/* 100 */       gltfWriter.setIndenting(false);
/* 101 */       gltfWriter.write(gltf, baos);
/* 102 */       jsonData = baos.toByteArray();
/*     */     } 
/*     */     
/* 105 */     if (jsonData.length % 4 != 0) {
/*     */       
/* 107 */       int oldLength = jsonData.length;
/* 108 */       int padding = 4 - oldLength % 4;
/* 109 */       jsonData = Arrays.copyOf(jsonData, oldLength + padding);
/* 110 */       for (int i = 0; i < padding; i++)
/*     */       {
/* 112 */         jsonData[oldLength + i] = 32;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 117 */     ByteBuffer binaryData = gltfAsset.getBinaryData();
/* 118 */     if (binaryData == null)
/*     */     {
/* 120 */       binaryData = ByteBuffer.wrap(new byte[0]);
/*     */     }
/* 122 */     if (binaryData.capacity() % 4 != 0) {
/*     */       
/* 124 */       int padding = 4 - binaryData.capacity() % 4;
/*     */       
/* 126 */       binaryData = Buffers.copyOf(binaryData, binaryData.capacity() + padding);
/*     */     } 
/*     */ 
/*     */     
/* 130 */     ChunkData jsonChunkData = new ChunkData();
/* 131 */     jsonChunkData.append(jsonData.length);
/* 132 */     jsonChunkData.append(1313821514);
/* 133 */     jsonChunkData.append(ByteBuffer.wrap(jsonData));
/*     */ 
/*     */     
/* 136 */     ChunkData binChunkData = new ChunkData();
/* 137 */     binChunkData.append(binaryData.capacity());
/* 138 */     binChunkData.append(5130562);
/* 139 */     binChunkData.append(binaryData);
/*     */ 
/*     */     
/* 142 */     ChunkData headerData = new ChunkData();
/* 143 */     headerData.append(1179937895);
/* 144 */     headerData.append(2);
/* 145 */     int length = 12 + jsonData.length + 8 + binaryData.capacity() + 8;
/* 146 */     headerData.append(length);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     WritableByteChannel writableByteChannel = Channels.newChannel(outputStream);
/* 152 */     writableByteChannel.write(headerData.get());
/* 153 */     writableByteChannel.write(jsonChunkData.get());
/* 154 */     writableByteChannel.write(binChunkData.get());
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
/*     */   private static class ChunkData
/*     */   {
/* 172 */     private ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void append(int value) throws IOException {
/* 183 */       this.baos.write(value >> 0 & 0xFF);
/* 184 */       this.baos.write(value >> 8 & 0xFF);
/* 185 */       this.baos.write(value >> 16 & 0xFF);
/* 186 */       this.baos.write(value >> 24 & 0xFF);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void append(ByteBuffer buffer) throws IOException {
/* 199 */       WritableByteChannel writableByteChannel = Channels.newChannel(this.baos);
/* 200 */       writableByteChannel.write(buffer.slice());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ByteBuffer get() {
/* 210 */       return ByteBuffer.wrap(this.baos.toByteArray());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v2\GltfAssetWriterV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */