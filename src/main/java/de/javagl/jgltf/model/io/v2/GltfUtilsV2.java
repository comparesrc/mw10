/*     */ package de.javagl.jgltf.model.io.v2;
/*     */ 
/*     */ import com.fasterxml.jackson.databind.ObjectMapper;
/*     */ import de.javagl.jgltf.impl.v2.BufferView;
/*     */ import de.javagl.jgltf.impl.v2.GlTF;
/*     */ import de.javagl.jgltf.impl.v2.Image;
/*     */ import de.javagl.jgltf.model.GltfException;
/*     */ import de.javagl.jgltf.model.io.JacksonUtils;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GltfUtilsV2
/*     */ {
/*     */   static GlTF copy(GlTF gltf) {
/*  61 */     ObjectMapper objectMapper = JacksonUtils.createObjectMapper();
/*  62 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     
/*     */     try {
/*  65 */       objectMapper.writeValue(baos, gltf);
/*  66 */       return (GlTF)objectMapper.readValue(baos.toByteArray(), GlTF.class);
/*     */     }
/*  68 */     catch (IOException e) {
/*     */       
/*  70 */       throw new GltfException("Could not copy glTF", e);
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
/*     */   static BufferView copy(BufferView bufferView) {
/*  82 */     BufferView copy = new BufferView();
/*  83 */     copy.setExtensions(bufferView.getExtensions());
/*  84 */     copy.setExtras(bufferView.getExtras());
/*  85 */     copy.setName(bufferView.getName());
/*  86 */     copy.setBuffer(bufferView.getBuffer());
/*  87 */     copy.setByteOffset(bufferView.getByteOffset());
/*  88 */     copy.setByteLength(bufferView.getByteLength());
/*  89 */     copy.setTarget(bufferView.getTarget());
/*  90 */     copy.setByteStride(bufferView.getByteStride());
/*  91 */     return copy;
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
/*     */   static Image copy(Image image) {
/* 103 */     Image copy = new Image();
/* 104 */     copy.setExtensions(image.getExtensions());
/* 105 */     copy.setExtras(image.getExtras());
/* 106 */     copy.setName(image.getName());
/* 107 */     copy.setUri(image.getUri());
/* 108 */     copy.setBufferView(image.getBufferView());
/* 109 */     copy.setMimeType(image.getMimeType());
/* 110 */     return copy;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v2\GltfUtilsV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */