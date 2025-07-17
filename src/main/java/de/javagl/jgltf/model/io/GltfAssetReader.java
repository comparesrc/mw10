/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v1.GlTF;
/*     */ import de.javagl.jgltf.impl.v2.GlTF;
/*     */ import de.javagl.jgltf.model.io.v1.GltfAssetV1;
/*     */ import de.javagl.jgltf.model.io.v2.GltfAssetV2;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GltfAssetReader
/*     */ {
/*     */   private Consumer<? super JsonError> jsonErrorConsumer;
/*     */   
/*     */   public void setJsonErrorConsumer(Consumer<? super JsonError> jsonErrorConsumer) {
/*  84 */     this.jsonErrorConsumer = jsonErrorConsumer;
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
/*     */   public GltfAsset read(URI uri) throws IOException {
/*  96 */     try (InputStream inputStream = uri.toURL().openStream()) {
/*     */       
/*  98 */       GltfAsset gltfAsset = readWithoutReferences(inputStream);
/*  99 */       URI baseUri = IO.getParent(uri);
/* 100 */       GltfReferenceResolver.resolveAll(gltfAsset
/* 101 */           .getReferences(), baseUri);
/* 102 */       return gltfAsset;
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
/*     */   public GltfAsset read(Path path) throws IOException {
/* 115 */     try (InputStream inputStream = path.toUri().toURL().openStream()) {
/*     */       
/* 117 */       GltfAsset gltfAsset = readWithoutReferences(inputStream);
/* 118 */       Path basePath = IO.getParent(path);
/* 119 */       GltfReferenceResolver.resolveAll(gltfAsset
/* 120 */           .getReferences(), basePath);
/* 121 */       return gltfAsset;
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
/*     */   public GltfAsset readWithoutReferences(URI uri) throws IOException {
/* 140 */     try (InputStream inputStream = uri.toURL().openStream()) {
/*     */       
/* 142 */       return readWithoutReferences(inputStream);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public GltfAsset readWithoutReferences(InputStream inputStream) throws IOException {
/* 164 */     RawGltfData rawGltfData = RawGltfDataReader.read(inputStream);
/* 165 */     return read(rawGltfData);
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
/*     */   GltfAsset read(RawGltfData rawGltfData) throws IOException {
/* 177 */     GltfReader gltfReader = new GltfReader();
/* 178 */     gltfReader.setJsonErrorConsumer(this.jsonErrorConsumer);
/* 179 */     ByteBuffer jsonData = rawGltfData.getJsonData();
/*     */     
/* 181 */     try (InputStream jsonInputStream = Buffers.createByteBufferInputStream(jsonData)) {
/*     */       
/* 183 */       gltfReader.read(jsonInputStream);
/* 184 */       int majorVersion = gltfReader.getMajorVersion();
/* 185 */       if (majorVersion == 1) {
/*     */ 
/*     */         
/* 188 */         GlTF gltfV1 = gltfReader.getAsGltfV1();
/* 189 */         return (GltfAsset)new GltfAssetV1(gltfV1, rawGltfData
/* 190 */             .getBinaryData());
/*     */       } 
/* 192 */       if (majorVersion == 2) {
/*     */ 
/*     */         
/* 195 */         GlTF gltfV2 = gltfReader.getAsGltfV2();
/* 196 */         return (GltfAsset)new GltfAssetV2(gltfV2, rawGltfData
/* 197 */             .getBinaryData());
/*     */       } 
/*     */ 
/*     */       
/* 201 */       throw new IOException("Unsupported major version: " + majorVersion);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\GltfAssetReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */