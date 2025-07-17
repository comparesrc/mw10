/*     */ package de.javagl.jgltf.model.io.v2;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v2.Buffer;
/*     */ import de.javagl.jgltf.impl.v2.GlTF;
/*     */ import de.javagl.jgltf.impl.v2.Image;
/*     */ import de.javagl.jgltf.model.BufferModel;
/*     */ import de.javagl.jgltf.model.GltfModel;
/*     */ import de.javagl.jgltf.model.ImageModel;
/*     */ import de.javagl.jgltf.model.Optionals;
/*     */ import de.javagl.jgltf.model.impl.UriStrings;
/*     */ import de.javagl.jgltf.model.io.IO;
/*     */ import de.javagl.jgltf.model.v2.GltfCreatorV2;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DefaultAssetCreatorV2
/*     */ {
/*     */   private GltfAssetV2 gltfAsset;
/*     */   private Set<String> existingBufferUriStrings;
/*     */   private Set<String> existingImageUriStrings;
/*     */   
/*     */   GltfAssetV2 create(GltfModel gltfModel) {
/*  91 */     GlTF outputGltf = GltfCreatorV2.create(gltfModel);
/*     */     
/*  93 */     List<Buffer> buffers = Optionals.of(outputGltf.getBuffers());
/*  94 */     List<Image> images = Optionals.of(outputGltf.getImages());
/*     */     
/*  96 */     this.existingBufferUriStrings = collectUriStrings(buffers, Buffer::getUri);
/*  97 */     this.existingImageUriStrings = collectUriStrings(images, Image::getUri);
/*     */     
/*  99 */     this.gltfAsset = new GltfAssetV2(outputGltf, null);
/*     */     int i;
/* 101 */     for (i = 0; i < buffers.size(); i++) {
/*     */       
/* 103 */       Buffer buffer = buffers.get(i);
/* 104 */       storeBufferAsDefault(gltfModel, i, buffer);
/*     */     } 
/* 106 */     for (i = 0; i < images.size(); i++) {
/*     */       
/* 108 */       Image image = images.get(i);
/* 109 */       storeImageAsDefault(gltfModel, i, image);
/*     */     } 
/*     */     
/* 112 */     return this.gltfAsset;
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
/*     */   private static <T> Set<String> collectUriStrings(Collection<T> elements, Function<? super T, ? extends String> uriFunction) {
/* 127 */     return (Set<String>)elements.stream()
/* 128 */       .<String>map(uriFunction)
/* 129 */       .filter(Objects::nonNull)
/* 130 */       .filter(uriString -> !IO.isDataUriString(uriString))
/* 131 */       .collect(Collectors.toSet());
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
/*     */   
/*     */   private void storeBufferAsDefault(GltfModel gltfModel, int index, Buffer buffer) {
/* 153 */     BufferModel bufferModel = gltfModel.getBufferModels().get(index);
/* 154 */     ByteBuffer bufferData = bufferModel.getBufferData();
/*     */     
/* 156 */     String oldUriString = buffer.getUri();
/* 157 */     String newUriString = oldUriString;
/* 158 */     if (oldUriString == null || IO.isDataUriString(oldUriString)) {
/*     */       
/* 160 */       newUriString = UriStrings.createBufferUriString(this.existingBufferUriStrings);
/*     */       
/* 162 */       buffer.setUri(newUriString);
/* 163 */       this.existingBufferUriStrings.add(newUriString);
/*     */     } 
/*     */     
/* 166 */     this.gltfAsset.putReferenceData(newUriString, bufferData);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void storeImageAsDefault(GltfModel gltfModel, int index, Image image) {
/* 191 */     ImageModel imageModel = gltfModel.getImageModels().get(index);
/* 192 */     ByteBuffer imageData = imageModel.getImageData();
/*     */     
/* 194 */     String oldUriString = image.getUri();
/* 195 */     String newUriString = oldUriString;
/* 196 */     if (oldUriString == null || IO.isDataUriString(oldUriString)) {
/*     */       
/* 198 */       newUriString = UriStrings.createImageUriString(imageModel, this.existingImageUriStrings);
/*     */       
/* 200 */       image.setUri(newUriString);
/* 201 */       this.existingImageUriStrings.add(newUriString);
/*     */     } 
/*     */     
/* 204 */     if (image.getBufferView() != null)
/*     */     {
/* 206 */       image.setBufferView(null);
/*     */     }
/*     */     
/* 209 */     this.gltfAsset.putReferenceData(newUriString, imageData);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v2\DefaultAssetCreatorV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */