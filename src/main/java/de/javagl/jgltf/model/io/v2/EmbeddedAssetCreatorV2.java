/*     */ package de.javagl.jgltf.model.io.v2;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v2.Buffer;
/*     */ import de.javagl.jgltf.impl.v2.GlTF;
/*     */ import de.javagl.jgltf.impl.v2.Image;
/*     */ import de.javagl.jgltf.model.BufferModel;
/*     */ import de.javagl.jgltf.model.GltfException;
/*     */ import de.javagl.jgltf.model.GltfModel;
/*     */ import de.javagl.jgltf.model.ImageModel;
/*     */ import de.javagl.jgltf.model.Optionals;
/*     */ import de.javagl.jgltf.model.io.IO;
/*     */ import de.javagl.jgltf.model.io.MimeTypes;
/*     */ import de.javagl.jgltf.model.v2.GltfCreatorV2;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Base64;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class EmbeddedAssetCreatorV2
/*     */ {
/*     */   GltfAssetV2 create(GltfModel gltfModel) {
/*  79 */     GlTF outputGltf = GltfCreatorV2.create(gltfModel);
/*     */     
/*  81 */     List<Buffer> buffers = Optionals.of(outputGltf.getBuffers());
/*  82 */     for (int i = 0; i < buffers.size(); i++) {
/*     */       
/*  84 */       Buffer buffer = buffers.get(i);
/*  85 */       convertBufferToEmbedded(gltfModel, i, buffer);
/*     */     } 
/*     */     
/*  88 */     List<Image> images = Optionals.of(outputGltf.getImages());
/*  89 */     for (int j = 0; j < images.size(); j++) {
/*     */       
/*  91 */       Image image = images.get(j);
/*  92 */       convertImageToEmbedded(gltfModel, j, image);
/*     */     } 
/*     */     
/*  95 */     return new GltfAssetV2(outputGltf, null);
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
/*     */   private static void convertBufferToEmbedded(GltfModel gltfModel, int index, Buffer buffer) {
/* 109 */     String uriString = buffer.getUri();
/* 110 */     if (IO.isDataUriString(uriString)) {
/*     */       return;
/*     */     }
/*     */     
/* 114 */     BufferModel bufferModel = gltfModel.getBufferModels().get(index);
/* 115 */     ByteBuffer bufferData = bufferModel.getBufferData();
/*     */     
/* 117 */     byte[] data = new byte[bufferData.capacity()];
/* 118 */     bufferData.slice().get(data);
/* 119 */     String encodedData = Base64.getEncoder().encodeToString(data);
/* 120 */     String dataUriString = "data:application/gltf-buffer;base64," + encodedData;
/*     */ 
/*     */     
/* 123 */     buffer.setUri(dataUriString);
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
/*     */   private static void convertImageToEmbedded(GltfModel gltfModel, int index, Image image) {
/* 139 */     String uriString = image.getUri();
/* 140 */     if (IO.isDataUriString(uriString)) {
/*     */       return;
/*     */     }
/*     */     
/* 144 */     ImageModel imageModel = gltfModel.getImageModels().get(index);
/* 145 */     ByteBuffer imageData = imageModel.getImageData();
/*     */     
/* 147 */     String uri = image.getUri();
/*     */     
/* 149 */     String imageMimeTypeString = MimeTypes.guessImageMimeTypeString(uri, imageData);
/* 150 */     if (imageMimeTypeString == null)
/*     */     {
/* 152 */       throw new GltfException("Could not detect MIME type of image " + index);
/*     */     }
/*     */ 
/*     */     
/* 156 */     byte[] data = new byte[imageData.capacity()];
/* 157 */     imageData.slice().get(data);
/* 158 */     String encodedData = Base64.getEncoder().encodeToString(data);
/* 159 */     String dataUriString = "data:" + imageMimeTypeString + ";base64," + encodedData;
/*     */ 
/*     */     
/* 162 */     image.setUri(dataUriString);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v2\EmbeddedAssetCreatorV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */