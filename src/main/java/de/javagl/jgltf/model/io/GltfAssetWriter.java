/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import de.javagl.jgltf.model.io.v1.GltfAssetV1;
/*     */ import de.javagl.jgltf.model.io.v1.GltfAssetWriterV1;
/*     */ import de.javagl.jgltf.model.io.v2.GltfAssetV2;
/*     */ import de.javagl.jgltf.model.io.v2.GltfAssetWriterV2;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GltfAssetWriter
/*     */ {
/*     */   public void write(GltfAsset gltfAsset, String fileName) throws IOException {
/*  71 */     write(gltfAsset, new File(fileName));
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
/*     */   public void write(GltfAsset gltfAsset, File file) throws IOException {
/*  89 */     try (OutputStream outputStream = new FileOutputStream(file)) {
/*     */       
/*  91 */       writeJson(gltfAsset, outputStream);
/*     */     } 
/*     */     
/*  94 */     for (Map.Entry<String, ByteBuffer> entry : gltfAsset.getReferenceDatas().entrySet()) {
/*     */       
/*  96 */       String relativeUrlString = entry.getKey();
/*  97 */       ByteBuffer data = entry.getValue();
/*     */ 
/*     */       
/* 100 */       String referenceFileName = file.toPath().getParent().resolve(relativeUrlString).toString();
/*     */ 
/*     */       
/* 103 */       try (WritableByteChannel writableByteChannel = Channels.newChannel(new FileOutputStream(referenceFileName))) {
/*     */         
/* 105 */         writableByteChannel.write(data.slice());
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeJson(GltfAsset gltfAsset, String fileName) throws IOException {
/* 123 */     writeJson(gltfAsset, new File(fileName));
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
/*     */   public void writeJson(GltfAsset gltfAsset, File file) throws IOException {
/* 139 */     try (OutputStream outputStream = new FileOutputStream(file)) {
/*     */       
/* 141 */       writeJson(gltfAsset, outputStream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeJson(GltfAsset gltfAsset, OutputStream outputStream) throws IOException {
/* 159 */     Object gltf = gltfAsset.getGltf();
/* 160 */     GltfWriter gltfWriter = new GltfWriter();
/* 161 */     gltfWriter.write(gltf, outputStream);
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
/*     */   public void writeBinary(GltfAsset gltfAsset, String fileName) throws IOException {
/* 175 */     writeBinary(gltfAsset, new File(fileName));
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
/*     */   public void writeBinary(GltfAsset gltfAsset, File file) throws IOException {
/* 189 */     try (OutputStream outputStream = new FileOutputStream(file)) {
/*     */       
/* 191 */       writeBinary(gltfAsset, outputStream);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBinary(GltfAsset gltfAsset, OutputStream outputStream) throws IOException {
/* 207 */     if (gltfAsset instanceof GltfAssetV1) {
/*     */       
/* 209 */       GltfAssetV1 gltfAssetV1 = (GltfAssetV1)gltfAsset;
/* 210 */       GltfAssetWriterV1 gltfAssetWriterV1 = new GltfAssetWriterV1();
/* 211 */       gltfAssetWriterV1.writeBinary(gltfAssetV1, outputStream);
/*     */     }
/* 213 */     else if (gltfAsset instanceof GltfAssetV2) {
/*     */       
/* 215 */       GltfAssetV2 gltfAssetV2 = (GltfAssetV2)gltfAsset;
/* 216 */       GltfAssetWriterV2 gltfAssetWriterV2 = new GltfAssetWriterV2();
/* 217 */       gltfAssetWriterV2.writeBinary(gltfAssetV2, outputStream);
/*     */     }
/*     */     else {
/*     */       
/* 221 */       throw new IOException("The gltfAsset has an unknown version: " + gltfAsset);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\GltfAssetWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */