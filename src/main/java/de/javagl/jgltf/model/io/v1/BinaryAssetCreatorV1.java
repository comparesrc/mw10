/*     */ package de.javagl.jgltf.model.io.v1;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v1.Buffer;
/*     */ import de.javagl.jgltf.impl.v1.BufferView;
/*     */ import de.javagl.jgltf.impl.v1.GlTF;
/*     */ import de.javagl.jgltf.impl.v1.GlTFProperty;
/*     */ import de.javagl.jgltf.impl.v1.Image;
/*     */ import de.javagl.jgltf.impl.v1.Shader;
/*     */ import de.javagl.jgltf.model.BufferModel;
/*     */ import de.javagl.jgltf.model.GltfModel;
/*     */ import de.javagl.jgltf.model.ImageModel;
/*     */ import de.javagl.jgltf.model.gl.ShaderModel;
/*     */ import de.javagl.jgltf.model.io.Buffers;
/*     */ import de.javagl.jgltf.model.v1.BinaryGltfV1;
/*     */ import de.javagl.jgltf.model.v1.GltfCreatorV1;
/*     */ import de.javagl.jgltf.model.v1.GltfExtensionsV1;
/*     */ import de.javagl.jgltf.model.v1.GltfIds;
/*     */ import de.javagl.jgltf.model.v1.GltfModelV1;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
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
/*     */ final class BinaryAssetCreatorV1
/*     */ {
/*     */   GltfAssetV1 create(GltfModelV1 gltfModel) {
/*  79 */     GlTF outputGltf = GltfCreatorV1.create((GltfModel)gltfModel);
/*     */ 
/*     */ 
/*     */     
/*  83 */     int binaryGltfBufferSize = computeBinaryGltfBufferSize(gltfModel);
/*     */     
/*  85 */     ByteBuffer binaryGltfByteBuffer = Buffers.create(binaryGltfBufferSize);
/*     */ 
/*     */     
/*  88 */     GltfExtensionsV1.addExtensionUsed(outputGltf, 
/*  89 */         BinaryGltfV1.getBinaryGltfExtensionName());
/*  90 */     Buffer binaryGltfBuffer = new Buffer();
/*  91 */     binaryGltfBuffer.setType("arraybuffer");
/*  92 */     binaryGltfBuffer.setUri(
/*  93 */         BinaryGltfV1.getBinaryGltfBufferId() + ".bin");
/*  94 */     binaryGltfBuffer.setByteLength(Integer.valueOf(binaryGltfBufferSize));
/*  95 */     Map<String, Buffer> newBuffers = Collections.singletonMap(
/*  96 */         BinaryGltfV1.getBinaryGltfBufferId(), binaryGltfBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     Map<String, Buffer> oldBuffers = copy(outputGltf.getBuffers());
/* 103 */     Map<String, Image> oldImages = copy(outputGltf.getImages());
/* 104 */     Map<String, Shader> oldShaders = copy(outputGltf.getShaders());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     Map<String, BufferModel> bufferIdToBuffer = GltfUtilsV1.createMap(oldBuffers, gltfModel
/* 112 */         .getBufferModels());
/* 113 */     Map<String, ImageModel> imageIdToImage = GltfUtilsV1.createMap(oldImages, gltfModel
/* 114 */         .getImageModels());
/* 115 */     Map<String, ShaderModel> shaderIdToShader = GltfUtilsV1.createMap(oldShaders, gltfModel
/* 116 */         .getShaderModels());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     Map<String, Integer> bufferOffsets = concatBuffers(oldBuffers
/* 123 */         .keySet(), id -> ((BufferModel)bufferIdToBuffer.get(id)).getBufferData(), binaryGltfByteBuffer);
/*     */ 
/*     */     
/* 126 */     Map<String, Integer> imageOffsets = concatBuffers(oldImages
/* 127 */         .keySet(), id -> ((ImageModel)imageIdToImage.get(id)).getImageData(), binaryGltfByteBuffer);
/*     */ 
/*     */     
/* 130 */     Map<String, Integer> shaderOffsets = concatBuffers(oldShaders
/* 131 */         .keySet(), id -> ((ShaderModel)shaderIdToShader.get(id)).getShaderData(), binaryGltfByteBuffer);
/*     */ 
/*     */     
/* 134 */     binaryGltfByteBuffer.position(0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     Map<String, BufferView> oldBufferViews = copy(outputGltf.getBufferViews());
/* 140 */     Map<String, BufferView> newBufferViews = new LinkedHashMap<>();
/*     */     
/* 142 */     for (Map.Entry<String, BufferView> oldEntry : oldBufferViews.entrySet()) {
/*     */       
/* 144 */       String id = oldEntry.getKey();
/* 145 */       BufferView oldBufferView = oldEntry.getValue();
/* 146 */       BufferView newBufferView = GltfUtilsV1.copy(oldBufferView);
/*     */       
/* 148 */       newBufferView.setBuffer(BinaryGltfV1.getBinaryGltfBufferId());
/* 149 */       String oldBufferId = oldBufferView.getBuffer();
/* 150 */       int oldByteOffset = oldBufferView.getByteOffset().intValue();
/* 151 */       int bufferOffset = ((Integer)bufferOffsets.get(oldBufferId)).intValue();
/* 152 */       int newByteOffset = oldByteOffset + bufferOffset;
/* 153 */       newBufferView.setByteOffset(Integer.valueOf(newByteOffset));
/*     */       
/* 155 */       newBufferViews.put(id, newBufferView);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     Map<String, Image> newImages = new LinkedHashMap<>();
/*     */     
/* 164 */     for (Map.Entry<String, Image> oldEntry : oldImages.entrySet()) {
/*     */       
/* 166 */       String id = oldEntry.getKey();
/* 167 */       Image oldImage = oldEntry.getValue();
/* 168 */       Image newImage = GltfUtilsV1.copy(oldImage);
/*     */ 
/*     */ 
/*     */       
/* 172 */       ByteBuffer imageData = ((ImageModel)imageIdToImage.get(id)).getImageData();
/* 173 */       int byteLength = imageData.capacity();
/* 174 */       int byteOffset = ((Integer)imageOffsets.get(id)).intValue();
/* 175 */       BufferView imageBufferView = new BufferView();
/* 176 */       imageBufferView.setBuffer(BinaryGltfV1.getBinaryGltfBufferId());
/* 177 */       imageBufferView.setByteOffset(Integer.valueOf(byteOffset));
/* 178 */       imageBufferView.setByteLength(Integer.valueOf(byteLength));
/*     */ 
/*     */ 
/*     */       
/* 182 */       String generatedBufferViewId = GltfIds.generateId("bufferView_for_image_" + id, oldBufferViews
/* 183 */           .keySet());
/* 184 */       newBufferViews.put(generatedBufferViewId, imageBufferView);
/*     */ 
/*     */       
/* 187 */       BinaryGltfV1.setBinaryGltfBufferViewId((GlTFProperty)newImage, generatedBufferViewId);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       BinaryGltfV1.setBinaryGltfImageProperties(newImage, imageData);
/*     */       
/* 194 */       newImages.put(id, newImage);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     Map<String, Shader> newShaders = new LinkedHashMap<>();
/*     */     
/* 203 */     for (Map.Entry<String, Shader> oldEntry : oldShaders.entrySet()) {
/*     */       
/* 205 */       String id = oldEntry.getKey();
/* 206 */       Shader oldShader = oldEntry.getValue();
/* 207 */       Shader newShader = GltfUtilsV1.copy(oldShader);
/*     */ 
/*     */ 
/*     */       
/* 211 */       ByteBuffer shaderData = ((ShaderModel)shaderIdToShader.get(id)).getShaderData();
/* 212 */       int byteLength = shaderData.capacity();
/* 213 */       int byteOffset = ((Integer)shaderOffsets.get(id)).intValue();
/* 214 */       BufferView shaderBufferView = new BufferView();
/* 215 */       shaderBufferView.setBuffer(BinaryGltfV1.getBinaryGltfBufferId());
/* 216 */       shaderBufferView.setByteOffset(Integer.valueOf(byteOffset));
/* 217 */       shaderBufferView.setByteLength(Integer.valueOf(byteLength));
/*     */ 
/*     */ 
/*     */       
/* 221 */       String generatedBufferViewId = GltfIds.generateId("bufferView_for_shader_" + id, oldBufferViews
/* 222 */           .keySet());
/* 223 */       newBufferViews.put(generatedBufferViewId, shaderBufferView);
/*     */ 
/*     */       
/* 226 */       BinaryGltfV1.setBinaryGltfBufferViewId((GlTFProperty)newShader, generatedBufferViewId);
/*     */ 
/*     */       
/* 229 */       newShaders.put(id, newShader);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 234 */     if (!newBuffers.isEmpty())
/*     */     {
/* 236 */       outputGltf.setBuffers(newBuffers);
/*     */     }
/* 238 */     if (!newImages.isEmpty())
/*     */     {
/* 240 */       outputGltf.setImages(newImages);
/*     */     }
/* 242 */     if (!newShaders.isEmpty())
/*     */     {
/* 244 */       outputGltf.setShaders(newShaders);
/*     */     }
/* 246 */     if (!newBufferViews.isEmpty())
/*     */     {
/* 248 */       outputGltf.setBufferViews(newBufferViews);
/*     */     }
/* 250 */     return new GltfAssetV1(outputGltf, binaryGltfByteBuffer);
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
/*     */   private static int computeBinaryGltfBufferSize(GltfModelV1 gltfModel) {
/* 264 */     int binaryGltfBufferSize = 0;
/* 265 */     for (BufferModel bufferModel : gltfModel.getBufferModels()) {
/*     */       
/* 267 */       ByteBuffer bufferData = bufferModel.getBufferData();
/* 268 */       binaryGltfBufferSize += bufferData.capacity();
/*     */     } 
/* 270 */     for (ImageModel imageModel : gltfModel.getImageModels()) {
/*     */       
/* 272 */       ByteBuffer imageData = imageModel.getImageData();
/* 273 */       binaryGltfBufferSize += imageData.capacity();
/*     */     } 
/* 275 */     for (ShaderModel shaderModel : gltfModel.getShaderModels()) {
/*     */       
/* 277 */       ByteBuffer shaderData = shaderModel.getShaderData();
/* 278 */       binaryGltfBufferSize += shaderData.capacity();
/*     */     } 
/* 280 */     return binaryGltfBufferSize;
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
/*     */   private static <K> Map<K, Integer> concatBuffers(Iterable<K> keys, Function<? super K, ? extends ByteBuffer> keyToByteBuffer, ByteBuffer targetBuffer) {
/* 303 */     Map<K, Integer> offsets = new LinkedHashMap<>();
/* 304 */     for (K key : keys) {
/*     */       
/* 306 */       ByteBuffer oldByteBuffer = keyToByteBuffer.apply(key);
/* 307 */       int offset = targetBuffer.position();
/* 308 */       offsets.put(key, Integer.valueOf(offset));
/* 309 */       targetBuffer.put(oldByteBuffer.slice());
/*     */     } 
/* 311 */     return offsets;
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
/*     */   private static <K, V> Map<K, V> copy(Map<K, V> map) {
/* 324 */     if (map == null)
/*     */     {
/* 326 */       return Collections.emptyMap();
/*     */     }
/* 328 */     return new LinkedHashMap<>(map);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v1\BinaryAssetCreatorV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */