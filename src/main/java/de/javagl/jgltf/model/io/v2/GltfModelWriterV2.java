/*     */ package de.javagl.jgltf.model.io.v2;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v2.GlTF;
/*     */ import de.javagl.jgltf.model.GltfModel;
/*     */ import de.javagl.jgltf.model.io.GltfAssetWriter;
/*     */ import de.javagl.jgltf.model.io.GltfWriter;
/*     */ import java.io.File;
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
/*     */ public final class GltfModelWriterV2
/*     */ {
/*     */   public void write(GltfModel gltfModel, File file) throws IOException {
/*  69 */     GltfAssetV2 gltfAsset = GltfAssetsV2.createDefault(gltfModel);
/*  70 */     GltfAssetWriter gltfAssetWriter = new GltfAssetWriter();
/*  71 */     gltfAssetWriter.write(gltfAsset, file);
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
/*     */   public void writeBinary(GltfModel gltfModel, OutputStream outputStream) throws IOException {
/*  86 */     GltfAssetV2 gltfAsset = GltfAssetsV2.createBinary(gltfModel);
/*  87 */     GltfAssetWriterV2 gltfAssetWriter = new GltfAssetWriterV2();
/*  88 */     gltfAssetWriter.writeBinary(gltfAsset, outputStream);
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
/*     */   public void writeEmbedded(GltfModel gltfModel, OutputStream outputStream) throws IOException {
/* 103 */     GltfAssetV2 gltfAsset = GltfAssetsV2.createEmbedded(gltfModel);
/* 104 */     GltfWriter gltfWriter = new GltfWriter();
/* 105 */     GlTF gltf = gltfAsset.getGltf();
/* 106 */     gltfWriter.write(gltf, outputStream);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v2\GltfModelWriterV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */