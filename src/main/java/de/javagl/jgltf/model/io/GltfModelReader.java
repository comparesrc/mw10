/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import de.javagl.jgltf.model.GltfModel;
/*     */ import de.javagl.jgltf.model.io.v1.GltfAssetV1;
/*     */ import de.javagl.jgltf.model.io.v2.GltfAssetV2;
/*     */ import de.javagl.jgltf.model.v1.GltfModelV1;
/*     */ import de.javagl.jgltf.model.v2.GltfModelCreatorV2;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.nio.file.Path;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GltfModelReader
/*     */ {
/*  50 */   private Consumer<? super JsonError> jsonErrorConsumer = JsonErrorConsumers.createLogging();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJsonErrorConsumer(Consumer<? super JsonError> jsonErrorConsumer) {
/*  69 */     this.jsonErrorConsumer = jsonErrorConsumer;
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
/*     */   public GltfModel read(URI uri) throws IOException {
/*  81 */     GltfAssetReader gltfAssetReader = new GltfAssetReader();
/*  82 */     gltfAssetReader.setJsonErrorConsumer(this.jsonErrorConsumer);
/*  83 */     GltfAsset gltfAsset = gltfAssetReader.read(uri);
/*  84 */     return createModel(gltfAsset);
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
/*     */   public GltfModel read(Path path) throws IOException {
/*  96 */     GltfAssetReader gltfAssetReader = new GltfAssetReader();
/*  97 */     gltfAssetReader.setJsonErrorConsumer(this.jsonErrorConsumer);
/*  98 */     GltfAsset gltfAsset = gltfAssetReader.read(path);
/*  99 */     return createModel(gltfAsset);
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
/*     */   public GltfModel readWithoutReferences(URI uri) throws IOException {
/* 116 */     try (InputStream inputStream = uri.toURL().openStream()) {
/*     */       
/* 118 */       GltfModel gltfModel = readWithoutReferences(inputStream);
/* 119 */       return gltfModel;
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
/*     */   
/*     */   public GltfModel readWithoutReferences(InputStream inputStream) throws IOException {
/* 138 */     GltfAssetReader gltfAssetReader = new GltfAssetReader();
/* 139 */     gltfAssetReader.setJsonErrorConsumer(this.jsonErrorConsumer);
/*     */     
/* 141 */     GltfAsset gltfAsset = gltfAssetReader.readWithoutReferences(inputStream);
/* 142 */     return createModel(gltfAsset);
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
/*     */   private static GltfModel createModel(GltfAsset gltfAsset) throws IOException {
/* 154 */     if (gltfAsset instanceof GltfAssetV1) {
/*     */       
/* 156 */       GltfAssetV1 gltfAssetV1 = (GltfAssetV1)gltfAsset;
/* 157 */       return (GltfModel)new GltfModelV1(gltfAssetV1);
/*     */     } 
/* 159 */     if (gltfAsset instanceof GltfAssetV2) {
/*     */       
/* 161 */       GltfAssetV2 gltfAssetV2 = (GltfAssetV2)gltfAsset;
/* 162 */       return (GltfModel)GltfModelCreatorV2.create(gltfAssetV2);
/*     */     } 
/* 164 */     throw new IOException("The glTF asset has an unknown version: " + gltfAsset);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\GltfModelReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */