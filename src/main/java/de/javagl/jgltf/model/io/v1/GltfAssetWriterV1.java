/*     */ package de.javagl.jgltf.model.io.v1;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v1.GlTF;
/*     */ import de.javagl.jgltf.model.io.GltfWriter;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GltfAssetWriterV1
/*     */ {
/*     */   private static final int MAGIC_BINARY_GLTF_HEADER = 1179937895;
/*     */   private static final int BINARY_GLTF_VERSION = 1;
/*     */   private static final int CONTENT_FORMAT_JSON = 0;
/*     */   
/*     */   public void writeBinary(GltfAssetV1 gltfAsset, OutputStream outputStream) throws IOException {
/*     */     byte[] sceneData;
/*  88 */     GlTF gltf = gltfAsset.getGltf();
/*     */     
/*  90 */     try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
/*     */       
/*  92 */       GltfWriter gltfWriter = new GltfWriter();
/*  93 */       gltfWriter.setIndenting(false);
/*  94 */       gltfWriter.write(gltf, baos);
/*  95 */       sceneData = baos.toByteArray();
/*     */     } 
/*     */     
/*  98 */     ByteBuffer binaryData = gltfAsset.getBinaryData();
/*  99 */     if (binaryData == null)
/*     */     {
/* 101 */       binaryData = ByteBuffer.wrap(new byte[0]);
/*     */     }
/*     */ 
/*     */     
/* 105 */     byte[] headerData = new byte[20];
/* 106 */     int magic = 1179937895;
/* 107 */     int version = 1;
/*     */     
/* 109 */     int length = headerData.length + sceneData.length + binaryData.capacity();
/* 110 */     int contentLength = sceneData.length;
/* 111 */     int contentFormat = 0;
/*     */ 
/*     */     
/* 114 */     IntBuffer headerBuffer = ByteBuffer.wrap(headerData).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
/* 115 */     headerBuffer.put(magic);
/* 116 */     headerBuffer.put(version);
/* 117 */     headerBuffer.put(length);
/* 118 */     headerBuffer.put(contentLength);
/* 119 */     headerBuffer.put(contentFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     WritableByteChannel writableByteChannel = Channels.newChannel(outputStream);
/* 125 */     writableByteChannel.write(ByteBuffer.wrap(headerData));
/* 126 */     writableByteChannel.write(ByteBuffer.wrap(sceneData));
/* 127 */     writableByteChannel.write(binaryData.slice());
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v1\GltfAssetWriterV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */