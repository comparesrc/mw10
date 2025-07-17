/*     */ package de.javagl.jgltf.model.io.v1;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v1.Buffer;
/*     */ import de.javagl.jgltf.impl.v1.GlTF;
/*     */ import de.javagl.jgltf.impl.v1.GlTFProperty;
/*     */ import de.javagl.jgltf.impl.v1.Image;
/*     */ import de.javagl.jgltf.impl.v1.Shader;
/*     */ import de.javagl.jgltf.model.BufferModel;
/*     */ import de.javagl.jgltf.model.GltfModel;
/*     */ import de.javagl.jgltf.model.ImageModel;
/*     */ import de.javagl.jgltf.model.Optionals;
/*     */ import de.javagl.jgltf.model.gl.ShaderModel;
/*     */ import de.javagl.jgltf.model.impl.UriStrings;
/*     */ import de.javagl.jgltf.model.io.IO;
/*     */ import de.javagl.jgltf.model.v1.BinaryGltfV1;
/*     */ import de.javagl.jgltf.model.v1.GltfCreatorV1;
/*     */ import de.javagl.jgltf.model.v1.GltfExtensionsV1;
/*     */ import de.javagl.jgltf.model.v1.GltfModelV1;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DefaultAssetCreatorV1
/*     */ {
/*     */   private GltfAssetV1 gltfAsset;
/*     */   private Set<String> existingBufferUriStrings;
/*     */   private Set<String> existingImageUriStrings;
/*     */   private Set<String> existingShaderUriStrings;
/*     */   
/*     */   GltfAssetV1 create(GltfModelV1 gltfModel) {
/* 101 */     GlTF outputGltf = GltfCreatorV1.create((GltfModel)gltfModel);
/*     */ 
/*     */     
/* 104 */     GltfExtensionsV1.removeExtensionUsed(outputGltf, 
/* 105 */         BinaryGltfV1.getBinaryGltfExtensionName());
/*     */     
/* 107 */     this.existingBufferUriStrings = collectUriStrings(
/* 108 */         Optionals.of(outputGltf.getBuffers()).values(), Buffer::getUri);
/*     */     
/* 110 */     this.existingImageUriStrings = collectUriStrings(
/* 111 */         Optionals.of(outputGltf.getImages()).values(), Image::getUri);
/*     */     
/* 113 */     this.existingShaderUriStrings = collectUriStrings(
/* 114 */         Optionals.of(outputGltf.getShaders()).values(), Shader::getUri);
/*     */ 
/*     */     
/* 117 */     this.gltfAsset = new GltfAssetV1(outputGltf, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     Map<String, BufferModel> bufferIdToBuffer = GltfUtilsV1.createMap(outputGltf
/* 125 */         .getBuffers(), gltfModel
/* 126 */         .getBufferModels());
/* 127 */     Map<String, ImageModel> imageIdToImage = GltfUtilsV1.createMap(outputGltf
/* 128 */         .getImages(), gltfModel
/* 129 */         .getImageModels());
/* 130 */     Map<String, ShaderModel> shaderIdToShader = GltfUtilsV1.createMap(outputGltf
/* 131 */         .getShaders(), gltfModel
/* 132 */         .getShaderModels());
/*     */     
/* 134 */     Optionals.of(outputGltf.getBuffers()).forEach((id, value) -> storeBufferAsDefault(gltfModel, id, value, bufferIdToBuffer::get));
/*     */     
/* 136 */     Optionals.of(outputGltf.getImages()).forEach((id, value) -> storeImageAsDefault(gltfModel, id, value, imageIdToImage::get));
/*     */     
/* 138 */     Optionals.of(outputGltf.getShaders()).forEach((id, value) -> storeShaderAsDefault(gltfModel, id, value, shaderIdToShader::get));
/*     */ 
/*     */     
/* 141 */     return this.gltfAsset;
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
/* 156 */     return (Set<String>)elements.stream()
/* 157 */       .<String>map(uriFunction)
/* 158 */       .filter(Objects::nonNull)
/* 159 */       .filter(uriString -> !IO.isDataUriString(uriString))
/* 160 */       .collect(Collectors.toSet());
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void storeBufferAsDefault(GltfModelV1 gltfModel, String id, Buffer buffer, Function<? super String, ? extends BufferModel> lookup) {
/* 188 */     BufferModel bufferModel = lookup.apply(id);
/* 189 */     ByteBuffer bufferData = bufferModel.getBufferData();
/*     */     
/* 191 */     String oldUriString = buffer.getUri();
/* 192 */     String newUriString = oldUriString;
/* 193 */     if (oldUriString == null || 
/* 194 */       IO.isDataUriString(oldUriString) || 
/* 195 */       BinaryGltfV1.isBinaryGltfBufferId(id)) {
/*     */       
/* 197 */       newUriString = UriStrings.createBufferUriString(this.existingBufferUriStrings);
/*     */       
/* 199 */       buffer.setUri(newUriString);
/* 200 */       this.existingBufferUriStrings.add(newUriString);
/*     */     } 
/* 202 */     this.gltfAsset.putReferenceData(newUriString, bufferData);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void storeImageAsDefault(GltfModelV1 gltfModel, String id, Image image, Function<? super String, ? extends ImageModel> lookup) {
/* 230 */     ImageModel imageModel = lookup.apply(id);
/* 231 */     ByteBuffer imageData = imageModel.getImageData();
/*     */     
/* 233 */     String oldUriString = image.getUri();
/* 234 */     String newUriString = oldUriString;
/* 235 */     if (oldUriString == null || 
/* 236 */       IO.isDataUriString(oldUriString) || 
/* 237 */       BinaryGltfV1.hasBinaryGltfExtension((GlTFProperty)image)) {
/*     */       
/* 239 */       newUriString = UriStrings.createImageUriString(imageModel, this.existingImageUriStrings);
/*     */       
/* 241 */       image.setUri(newUriString);
/* 242 */       this.existingImageUriStrings.add(newUriString);
/*     */ 
/*     */       
/* 245 */       image.removeExtensions(BinaryGltfV1.getBinaryGltfExtensionName());
/*     */     } 
/* 247 */     this.gltfAsset.putReferenceData(newUriString, imageData);
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
/*     */ 
/*     */   
/*     */   private void storeShaderAsDefault(GltfModelV1 gltfModel, String id, Shader shader, Function<? super String, ? extends ShaderModel> lookup) {
/* 274 */     ShaderModel shaderModel = lookup.apply(id);
/* 275 */     ByteBuffer shaderData = shaderModel.getShaderData();
/*     */     
/* 277 */     String oldUriString = shader.getUri();
/* 278 */     String newUriString = oldUriString;
/* 279 */     if (oldUriString == null || 
/* 280 */       IO.isDataUriString(oldUriString) || 
/* 281 */       BinaryGltfV1.hasBinaryGltfExtension((GlTFProperty)shader)) {
/*     */       
/* 283 */       newUriString = UriStrings.createShaderUriString(shaderModel, this.existingShaderUriStrings);
/*     */       
/* 285 */       shader.setUri(newUriString);
/* 286 */       this.existingShaderUriStrings.add(newUriString);
/*     */ 
/*     */       
/* 289 */       shader.removeExtensions(BinaryGltfV1.getBinaryGltfExtensionName());
/*     */     } 
/* 291 */     this.gltfAsset.putReferenceData(newUriString, shaderData);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v1\DefaultAssetCreatorV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */