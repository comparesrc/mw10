/*     */ package de.javagl.jgltf.model.io.v1;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v1.GlTF;
/*     */ import de.javagl.jgltf.model.io.GltfAssetWriter;
/*     */ import de.javagl.jgltf.model.io.GltfWriter;
/*     */ import de.javagl.jgltf.model.v1.GltfModelV1;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GltfModelWriterV1
/*     */ {
/*     */   public void write(GltfModelV1 gltfModel, File file) throws IOException {
/*  70 */     GltfAssetV1 gltfAsset = GltfAssetsV1.createDefault(gltfModel);
/*  71 */     GltfAssetWriter gltfAssetWriter = new GltfAssetWriter();
/*  72 */     gltfAssetWriter.write(gltfAsset, file);
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
/*     */   public void writeBinary(GltfModelV1 gltfModel, File file) throws IOException {
/*  86 */     try (OutputStream outputStream = new FileOutputStream(file)) {
/*     */       
/*  88 */       writeBinary(gltfModel, outputStream);
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
/*     */   public void writeBinary(GltfModelV1 gltfModel, OutputStream outputStream) throws IOException {
/* 104 */     GltfAssetV1 gltfAsset = GltfAssetsV1.createBinary(gltfModel);
/* 105 */     GltfAssetWriterV1 gltfAssetWriter = new GltfAssetWriterV1();
/* 106 */     gltfAssetWriter.writeBinary(gltfAsset, outputStream);
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
/*     */   public void writeEmbedded(GltfModelV1 gltfModel, OutputStream outputStream) throws IOException {
/* 121 */     GltfAssetV1 gltfAsset = GltfAssetsV1.createEmbedded(gltfModel);
/* 122 */     GltfWriter gltfWriter = new GltfWriter();
/* 123 */     GlTF gltf = gltfAsset.getGltf();
/* 124 */     gltfWriter.write(gltf, outputStream);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v1\GltfModelWriterV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */