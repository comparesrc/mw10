/*     */ package de.javagl.jgltf.model.io.v2;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v2.Buffer;
/*     */ import de.javagl.jgltf.impl.v2.GlTF;
/*     */ import de.javagl.jgltf.impl.v2.Image;
/*     */ import de.javagl.jgltf.model.Optionals;
/*     */ import de.javagl.jgltf.model.io.Buffers;
/*     */ import de.javagl.jgltf.model.io.GltfAsset;
/*     */ import de.javagl.jgltf.model.io.GltfReference;
/*     */ import de.javagl.jgltf.model.io.IO;
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
/*     */ public final class GltfAssetV2
/*     */   implements GltfAsset
/*     */ {
/*     */   private final GlTF gltf;
/*     */   private final ByteBuffer binaryData;
/*     */   private final Map<String, ByteBuffer> referenceDatas;
/*     */   
/*     */   public GltfAssetV2(GlTF gltf, ByteBuffer binaryData) {
/*  75 */     this.gltf = Objects.<GlTF>requireNonNull(gltf, "The gltf may not be null");
/*  76 */     this.binaryData = binaryData;
/*  77 */     this.referenceDatas = new ConcurrentHashMap<>();
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
/*  88 */     if (byteBuffer == null) {
/*     */       
/*  90 */       this.referenceDatas.remove(uriString);
/*     */     }
/*     */     else {
/*     */       
/*  94 */       this.referenceDatas.put(uriString, byteBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GlTF getGltf() {
/* 101 */     return this.gltf;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getBinaryData() {
/* 107 */     return Buffers.createSlice(this.binaryData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<GltfReference> getReferences() {
/* 113 */     List<GltfReference> references = new ArrayList<>();
/* 114 */     references.addAll(getBufferReferences());
/* 115 */     references.addAll(getImageReferences());
/* 116 */     return references;
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
/* 127 */     List<GltfReference> references = new ArrayList<>();
/* 128 */     List<Buffer> buffers = Optionals.of(this.gltf.getBuffers());
/* 129 */     for (int i = 0; i < buffers.size(); i++) {
/*     */       
/* 131 */       Buffer buffer = buffers.get(i);
/* 132 */       if (buffer.getUri() != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 137 */         String uri = buffer.getUri();
/* 138 */         if (!IO.isDataUriString(uri)) {
/*     */           
/* 140 */           Consumer<ByteBuffer> target = byteBuffer -> putReferenceData(uri, byteBuffer);
/*     */           
/* 142 */           GltfReference reference = new GltfReference("buffer " + i, uri, target);
/*     */           
/* 144 */           references.add(reference);
/*     */         } 
/*     */       } 
/* 147 */     }  return references;
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
/* 158 */     List<GltfReference> references = new ArrayList<>();
/* 159 */     List<Image> images = Optionals.of(this.gltf.getImages());
/* 160 */     for (int i = 0; i < images.size(); i++) {
/*     */       
/* 162 */       Image image = images.get(i);
/* 163 */       if (image.getBufferView() == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 168 */         String uri = image.getUri();
/* 169 */         if (!IO.isDataUriString(uri)) {
/*     */           
/* 171 */           Consumer<ByteBuffer> target = byteBuffer -> putReferenceData(uri, byteBuffer);
/*     */           
/* 173 */           GltfReference reference = new GltfReference("image " + i, uri, target);
/*     */           
/* 175 */           references.add(reference);
/*     */         } 
/*     */       } 
/* 178 */     }  return references;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getReferenceData(String uriString) {
/* 184 */     return Buffers.createSlice(this.referenceDatas.get(uriString));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, ByteBuffer> getReferenceDatas() {
/* 190 */     return Collections.unmodifiableMap(this.referenceDatas);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v2\GltfAssetV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */