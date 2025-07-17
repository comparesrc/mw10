/*     */ package de.javagl.jgltf.model.io.v2;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v2.Buffer;
/*     */ import de.javagl.jgltf.impl.v2.BufferView;
/*     */ import de.javagl.jgltf.impl.v2.GlTF;
/*     */ import de.javagl.jgltf.impl.v2.Image;
/*     */ import de.javagl.jgltf.model.BufferModel;
/*     */ import de.javagl.jgltf.model.GltfModel;
/*     */ import de.javagl.jgltf.model.ImageModel;
/*     */ import de.javagl.jgltf.model.Optionals;
/*     */ import de.javagl.jgltf.model.io.Buffers;
/*     */ import de.javagl.jgltf.model.io.MimeTypes;
/*     */ import de.javagl.jgltf.model.v2.GltfCreatorV2;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
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
/*     */ final class BinaryAssetCreatorV2
/*     */ {
/*  61 */   private static final Logger logger = Logger.getLogger(BinaryAssetCreatorV2.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GltfAssetV2 create(GltfModel gltfModel) {
/*  82 */     GlTF outputGltf = GltfCreatorV2.create(gltfModel);
/*     */ 
/*     */ 
/*     */     
/*  86 */     int binaryGltfBufferSize = computeBinaryGltfBufferSize(gltfModel);
/*     */     
/*  88 */     ByteBuffer binaryGltfByteBuffer = Buffers.create(binaryGltfBufferSize);
/*     */ 
/*     */     
/*  91 */     Buffer binaryGltfBuffer = new Buffer();
/*  92 */     binaryGltfBuffer.setByteLength(Integer.valueOf(binaryGltfBufferSize));
/*  93 */     outputGltf.setBuffers(Collections.singletonList(binaryGltfBuffer));
/*     */ 
/*     */     
/*  96 */     List<Image> oldImages = copy(outputGltf.getImages());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     List<ByteBuffer> bufferDatas = (List<ByteBuffer>)gltfModel.getBufferModels().stream().map(BufferModel::getBufferData).collect(Collectors.toList());
/* 105 */     Map<Integer, Integer> bufferOffsets = concatBuffers(bufferDatas, binaryGltfByteBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     List<ByteBuffer> imageDatas = (List<ByteBuffer>)gltfModel.getImageModels().stream().map(ImageModel::getImageData).collect(Collectors.toList());
/* 111 */     Map<Integer, Integer> imageOffsets = concatBuffers(imageDatas, binaryGltfByteBuffer);
/*     */     
/* 113 */     binaryGltfByteBuffer.position(0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     List<BufferView> oldBufferViews = copy(outputGltf.getBufferViews());
/* 119 */     List<BufferView> newBufferViews = new ArrayList<>();
/*     */     
/* 121 */     for (int i = 0; i < oldBufferViews.size(); i++) {
/*     */       
/* 123 */       BufferView oldBufferView = oldBufferViews.get(i);
/* 124 */       BufferView newBufferView = GltfUtilsV2.copy(oldBufferView);
/*     */       
/* 126 */       newBufferView.setBuffer(Integer.valueOf(0));
/* 127 */       Integer oldBufferIndex = oldBufferView.getBuffer();
/* 128 */       int oldByteOffset = ((Integer)Optionals.of(oldBufferView.getByteOffset(), Integer.valueOf(0))).intValue();
/* 129 */       int bufferOffset = ((Integer)bufferOffsets.get(oldBufferIndex)).intValue();
/* 130 */       int newByteOffset = oldByteOffset + bufferOffset;
/* 131 */       newBufferView.setByteOffset(Integer.valueOf(newByteOffset));
/*     */       
/* 133 */       newBufferViews.add(newBufferView);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     List<Image> newImages = new ArrayList<>();
/* 140 */     for (int j = 0; j < oldImages.size(); j++) {
/*     */       
/* 142 */       Image oldImage = oldImages.get(j);
/* 143 */       Image newImage = GltfUtilsV2.copy(oldImage);
/*     */ 
/*     */       
/* 146 */       ImageModel imageModel = gltfModel.getImageModels().get(j);
/* 147 */       ByteBuffer imageData = imageModel.getImageData();
/* 148 */       int byteLength = imageData.capacity();
/* 149 */       int byteOffset = ((Integer)imageOffsets.get(Integer.valueOf(j))).intValue();
/* 150 */       BufferView imageBufferView = new BufferView();
/* 151 */       imageBufferView.setBuffer(Integer.valueOf(0));
/* 152 */       imageBufferView.setByteOffset(Integer.valueOf(byteOffset));
/* 153 */       imageBufferView.setByteLength(Integer.valueOf(byteLength));
/*     */       
/* 155 */       int newBufferViewIndex = newBufferViews.size();
/* 156 */       newImage.setBufferView(Integer.valueOf(newBufferViewIndex));
/* 157 */       newImage.setUri(null);
/*     */ 
/*     */       
/* 160 */       String imageMimeTypeString = MimeTypes.guessImageMimeTypeString(oldImage
/* 161 */           .getUri(), imageData);
/* 162 */       if (imageMimeTypeString == null) {
/*     */         
/* 164 */         logger.warning("Could not detect MIME type of image");
/*     */       }
/*     */       else {
/*     */         
/* 168 */         newImage.setMimeType(imageMimeTypeString);
/*     */       } 
/*     */       
/* 171 */       newBufferViews.add(imageBufferView);
/* 172 */       newImages.add(newImage);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 177 */     if (!oldImages.isEmpty())
/*     */     {
/* 179 */       outputGltf.setImages(newImages);
/*     */     }
/* 181 */     if (!newBufferViews.isEmpty())
/*     */     {
/* 183 */       outputGltf.setBufferViews(newBufferViews);
/*     */     }
/* 185 */     return new GltfAssetV2(outputGltf, binaryGltfByteBuffer);
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
/*     */   private static int computeBinaryGltfBufferSize(GltfModel gltfModel) {
/* 199 */     int binaryGltfBufferSize = 0;
/* 200 */     for (BufferModel bufferModel : gltfModel.getBufferModels()) {
/*     */       
/* 202 */       ByteBuffer bufferData = bufferModel.getBufferData();
/* 203 */       binaryGltfBufferSize += bufferData.capacity();
/*     */     } 
/* 205 */     for (ImageModel imageModel : gltfModel.getImageModels()) {
/*     */       
/* 207 */       ByteBuffer imageData = imageModel.getImageData();
/* 208 */       binaryGltfBufferSize += imageData.capacity();
/*     */     } 
/* 210 */     return binaryGltfBufferSize;
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
/*     */   private static Map<Integer, Integer> concatBuffers(List<? extends ByteBuffer> buffers, ByteBuffer targetBuffer) {
/* 225 */     Map<Integer, Integer> offsets = new LinkedHashMap<>();
/* 226 */     for (int i = 0; i < buffers.size(); i++) {
/*     */       
/* 228 */       ByteBuffer oldByteBuffer = buffers.get(i);
/* 229 */       int offset = targetBuffer.position();
/* 230 */       offsets.put(Integer.valueOf(i), Integer.valueOf(offset));
/* 231 */       targetBuffer.put(oldByteBuffer.slice());
/*     */     } 
/* 233 */     return offsets;
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
/*     */   private static <T> List<T> copy(List<T> list) {
/* 246 */     if (list == null)
/*     */     {
/* 248 */       return Collections.emptyList();
/*     */     }
/* 250 */     return new ArrayList<>(list);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v2\BinaryAssetCreatorV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */