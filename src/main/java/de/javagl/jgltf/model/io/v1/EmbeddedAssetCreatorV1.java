/*     */ package de.javagl.jgltf.model.io.v1;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v1.Buffer;
/*     */ import de.javagl.jgltf.impl.v1.GlTF;
/*     */ import de.javagl.jgltf.impl.v1.Image;
/*     */ import de.javagl.jgltf.impl.v1.Shader;
/*     */ import de.javagl.jgltf.model.BufferModel;
/*     */ import de.javagl.jgltf.model.GltfException;
/*     */ import de.javagl.jgltf.model.GltfModel;
/*     */ import de.javagl.jgltf.model.ImageModel;
/*     */ import de.javagl.jgltf.model.Optionals;
/*     */ import de.javagl.jgltf.model.gl.ShaderModel;
/*     */ import de.javagl.jgltf.model.io.IO;
/*     */ import de.javagl.jgltf.model.io.MimeTypes;
/*     */ import de.javagl.jgltf.model.v1.BinaryGltfV1;
/*     */ import de.javagl.jgltf.model.v1.GltfCreatorV1;
/*     */ import de.javagl.jgltf.model.v1.GltfModelV1;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Base64;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class EmbeddedAssetCreatorV1
/*     */ {
/*     */   GltfAssetV1 create(GltfModelV1 gltfModel) {
/*  84 */     GlTF outputGltf = GltfCreatorV1.create((GltfModel)gltfModel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     Map<String, BufferModel> bufferIdToBuffer = GltfUtilsV1.createMap(outputGltf
/*  92 */         .getBuffers(), gltfModel
/*  93 */         .getBufferModels());
/*  94 */     Map<String, ImageModel> imageIdToImage = GltfUtilsV1.createMap(outputGltf
/*  95 */         .getImages(), gltfModel
/*  96 */         .getImageModels());
/*  97 */     Map<String, ShaderModel> shaderIdToShader = GltfUtilsV1.createMap(outputGltf
/*  98 */         .getShaders(), gltfModel
/*  99 */         .getShaderModels());
/*     */     
/* 101 */     Optionals.of(outputGltf.getBuffers()).forEach((id, value) -> convertBufferToEmbedded(gltfModel, id, value, bufferIdToBuffer::get));
/*     */ 
/*     */     
/* 104 */     Optionals.of(outputGltf.getImages()).forEach((id, value) -> convertImageToEmbedded(gltfModel, id, value, imageIdToImage::get));
/*     */ 
/*     */     
/* 107 */     Optionals.of(outputGltf.getShaders()).forEach((id, value) -> convertShaderToEmbedded(gltfModel, id, value, shaderIdToShader::get));
/*     */ 
/*     */ 
/*     */     
/* 111 */     return new GltfAssetV1(outputGltf, null);
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
/*     */   private static void convertBufferToEmbedded(GltfModelV1 gltfModel, String id, Buffer buffer, Function<? super String, ? extends BufferModel> lookup) {
/* 127 */     String uriString = buffer.getUri();
/* 128 */     if (IO.isDataUriString(uriString)) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     BufferModel bufferModel = lookup.apply(id);
/* 133 */     ByteBuffer bufferData = bufferModel.getBufferData();
/*     */     
/* 135 */     byte[] data = new byte[bufferData.capacity()];
/* 136 */     bufferData.slice().get(data);
/* 137 */     String encodedData = Base64.getEncoder().encodeToString(data);
/* 138 */     String dataUriString = "data:application/gltf-buffer;base64," + encodedData;
/*     */ 
/*     */     
/* 141 */     buffer.setUri(dataUriString);
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
/*     */   private static void convertImageToEmbedded(GltfModelV1 gltfModel, String id, Image image, Function<? super String, ? extends ImageModel> lookup) {
/* 159 */     String uriString = image.getUri();
/* 160 */     if (IO.isDataUriString(uriString)) {
/*     */       return;
/*     */     }
/*     */     
/* 164 */     ImageModel imageModel = lookup.apply(id);
/* 165 */     ByteBuffer imageData = imageModel.getImageData();
/*     */     
/* 167 */     String uri = image.getUri();
/*     */     
/* 169 */     String imageMimeTypeString = MimeTypes.guessImageMimeTypeString(uri, imageData);
/* 170 */     if (imageMimeTypeString == null)
/*     */     {
/* 172 */       throw new GltfException("Could not detect MIME type of image " + id);
/*     */     }
/*     */ 
/*     */     
/* 176 */     byte[] data = new byte[imageData.capacity()];
/* 177 */     imageData.slice().get(data);
/* 178 */     String encodedData = Base64.getEncoder().encodeToString(data);
/* 179 */     String dataUriString = "data:" + imageMimeTypeString + ";base64," + encodedData;
/*     */ 
/*     */     
/* 182 */     image.removeExtensions(BinaryGltfV1.getBinaryGltfExtensionName());
/* 183 */     image.setUri(dataUriString);
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
/*     */   private static void convertShaderToEmbedded(GltfModelV1 gltfModel, String id, Shader shader, Function<? super String, ? extends ShaderModel> lookup) {
/* 199 */     String uriString = shader.getUri();
/* 200 */     if (IO.isDataUriString(uriString)) {
/*     */       return;
/*     */     }
/*     */     
/* 204 */     ShaderModel shaderModel = lookup.apply(id);
/* 205 */     ByteBuffer shaderData = shaderModel.getShaderData();
/*     */     
/* 207 */     byte[] data = new byte[shaderData.capacity()];
/* 208 */     shaderData.slice().get(data);
/* 209 */     String encodedData = Base64.getEncoder().encodeToString(data);
/* 210 */     String dataUriString = "data:text/plain;base64," + encodedData;
/*     */ 
/*     */     
/* 213 */     shader.removeExtensions(BinaryGltfV1.getBinaryGltfExtensionName());
/* 214 */     shader.setUri(dataUriString);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v1\EmbeddedAssetCreatorV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */