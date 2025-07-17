/*     */ package de.javagl.jgltf.model.io.v1;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v1.Buffer;
/*     */ import de.javagl.jgltf.impl.v1.GlTF;
/*     */ import de.javagl.jgltf.impl.v1.GlTFProperty;
/*     */ import de.javagl.jgltf.impl.v1.Image;
/*     */ import de.javagl.jgltf.impl.v1.Shader;
/*     */ import de.javagl.jgltf.model.Optionals;
/*     */ import de.javagl.jgltf.model.io.Buffers;
/*     */ import de.javagl.jgltf.model.io.GltfAsset;
/*     */ import de.javagl.jgltf.model.io.GltfReference;
/*     */ import de.javagl.jgltf.model.io.IO;
/*     */ import de.javagl.jgltf.model.v1.BinaryGltfV1;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public final class GltfAssetV1
/*     */   implements GltfAsset
/*     */ {
/*     */   private final GlTF gltf;
/*     */   private final ByteBuffer binaryData;
/*     */   private final Map<String, ByteBuffer> referenceDatas;
/*     */   
/*     */   public GltfAssetV1(GlTF gltf, ByteBuffer binaryData) {
/*  78 */     this.gltf = Objects.<GlTF>requireNonNull(gltf, "The gltf may not be null");
/*  79 */     this.binaryData = binaryData;
/*  80 */     this.referenceDatas = new ConcurrentHashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void putReferenceData(String uriString, ByteBuffer byteBuffer) {
/*  91 */     if (byteBuffer == null) {
/*     */       
/*  93 */       this.referenceDatas.remove(uriString);
/*     */     }
/*     */     else {
/*     */       
/*  97 */       this.referenceDatas.put(uriString, byteBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GlTF getGltf() {
/* 104 */     return this.gltf;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getBinaryData() {
/* 110 */     return Buffers.createSlice(this.binaryData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<GltfReference> getReferences() {
/* 116 */     List<GltfReference> references = new ArrayList<>();
/* 117 */     references.addAll(getBufferReferences());
/* 118 */     references.addAll(getImageReferences());
/* 119 */     references.addAll(getShaderReferences());
/* 120 */     return references;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<GltfReference> getBufferReferences() {
/* 131 */     List<GltfReference> references = new ArrayList<>();
/* 132 */     Map<String, Buffer> buffers = Optionals.of(this.gltf.getBuffers());
/* 133 */     for (Map.Entry<String, Buffer> entry : buffers.entrySet()) {
/*     */       
/* 135 */       String bufferId = entry.getKey();
/* 136 */       if (BinaryGltfV1.isBinaryGltfBufferId(bufferId)) {
/*     */         continue;
/*     */       }
/*     */       
/* 140 */       Buffer buffer = buffers.get(bufferId);
/* 141 */       String uri = buffer.getUri();
/* 142 */       if (!IO.isDataUriString(uri)) {
/*     */         
/* 144 */         Consumer<ByteBuffer> target = byteBuffer -> putReferenceData(uri, byteBuffer);
/*     */         
/* 146 */         GltfReference reference = new GltfReference(bufferId, uri, target);
/*     */         
/* 148 */         references.add(reference);
/*     */       } 
/*     */     } 
/* 151 */     return references;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<GltfReference> getImageReferences() {
/* 162 */     List<GltfReference> references = new ArrayList<>();
/* 163 */     Map<String, Image> images = Optionals.of(this.gltf.getImages());
/* 164 */     for (Map.Entry<String, Image> entry : images.entrySet()) {
/*     */       
/* 166 */       String imageId = entry.getKey();
/* 167 */       Image image = entry.getValue();
/* 168 */       if (BinaryGltfV1.hasBinaryGltfExtension((GlTFProperty)image)) {
/*     */         continue;
/*     */       }
/*     */       
/* 172 */       String uri = image.getUri();
/* 173 */       if (!IO.isDataUriString(uri)) {
/*     */         
/* 175 */         Consumer<ByteBuffer> target = byteBuffer -> putReferenceData(uri, byteBuffer);
/*     */         
/* 177 */         GltfReference reference = new GltfReference(imageId, uri, target);
/*     */         
/* 179 */         references.add(reference);
/*     */       } 
/*     */     } 
/* 182 */     return references;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<GltfReference> getShaderReferences() {
/* 193 */     List<GltfReference> references = new ArrayList<>();
/* 194 */     Map<String, Shader> shaders = Optionals.of(this.gltf.getShaders());
/* 195 */     for (Map.Entry<String, Shader> entry : shaders.entrySet()) {
/*     */       
/* 197 */       String shaderId = entry.getKey();
/* 198 */       Shader shader = entry.getValue();
/* 199 */       if (BinaryGltfV1.hasBinaryGltfExtension((GlTFProperty)shader)) {
/*     */         continue;
/*     */       }
/*     */       
/* 203 */       String uri = shader.getUri();
/* 204 */       if (!IO.isDataUriString(uri)) {
/*     */         
/* 206 */         Consumer<ByteBuffer> target = byteBuffer -> putReferenceData(uri, byteBuffer);
/*     */         
/* 208 */         GltfReference reference = new GltfReference(shaderId, uri, target);
/*     */         
/* 210 */         references.add(reference);
/*     */       } 
/*     */     } 
/* 213 */     return references;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getReferenceData(String uriString) {
/* 219 */     return Buffers.createSlice(this.referenceDatas.get(uriString));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, ByteBuffer> getReferenceDatas() {
/* 225 */     return Collections.unmodifiableMap(this.referenceDatas);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v1\GltfAssetV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */