/*     */ package de.javagl.jgltf.model.io.v1;
/*     */ 
/*     */ import com.fasterxml.jackson.databind.ObjectMapper;
/*     */ import de.javagl.jgltf.impl.v1.BufferView;
/*     */ import de.javagl.jgltf.impl.v1.GlTF;
/*     */ import de.javagl.jgltf.impl.v1.Image;
/*     */ import de.javagl.jgltf.impl.v1.Shader;
/*     */ import de.javagl.jgltf.model.GltfException;
/*     */ import de.javagl.jgltf.model.io.JacksonUtils;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GltfUtilsV1
/*     */ {
/*     */   static GlTF copy(GlTF gltf) {
/*  67 */     ObjectMapper objectMapper = JacksonUtils.createObjectMapper();
/*  68 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     
/*     */     try {
/*  71 */       objectMapper.writeValue(baos, gltf);
/*  72 */       return (GlTF)objectMapper.readValue(baos.toByteArray(), GlTF.class);
/*     */     }
/*  74 */     catch (IOException e) {
/*     */       
/*  76 */       throw new GltfException("Could not copy glTF", e);
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
/*  88 */     BufferView copy = new BufferView();
/*  89 */     copy.setExtensions(bufferView.getExtensions());
/*  90 */     copy.setExtras(bufferView.getExtras());
/*  91 */     copy.setName(bufferView.getName());
/*  92 */     copy.setBuffer(bufferView.getBuffer());
/*  93 */     copy.setByteOffset(bufferView.getByteOffset());
/*  94 */     copy.setByteLength(bufferView.getByteLength());
/*  95 */     copy.setTarget(bufferView.getTarget());
/*  96 */     return copy;
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
/* 108 */     Image copy = new Image();
/* 109 */     copy.setExtensions(image.getExtensions());
/* 110 */     copy.setExtras(image.getExtras());
/* 111 */     copy.setName(image.getName());
/* 112 */     copy.setUri(image.getUri());
/* 113 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Shader copy(Shader shader) {
/* 124 */     Shader copy = new Shader();
/* 125 */     copy.setExtensions(shader.getExtensions());
/* 126 */     copy.setExtras(shader.getExtras());
/* 127 */     copy.setName(shader.getName());
/* 128 */     copy.setType(shader.getType());
/* 129 */     copy.setUri(shader.getUri());
/* 130 */     return copy;
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
/*     */   static <K, V> Map<K, V> createMap(Map<? extends K, ?> map, Collection<? extends V> collection) {
/* 146 */     if (map == null)
/*     */     {
/* 148 */       return Collections.emptyMap();
/*     */     }
/* 150 */     if (map.size() != collection.size())
/*     */     {
/* 152 */       throw new IllegalArgumentException("The inputs must have the same size, but the sizes are " + map
/*     */           
/* 154 */           .size() + " and " + collection.size());
/*     */     }
/* 156 */     Iterator<? extends K> iterator0 = map.keySet().iterator();
/* 157 */     Iterator<? extends V> iterator1 = collection.iterator();
/* 158 */     Map<K, V> result = new LinkedHashMap<>();
/* 159 */     while (iterator0.hasNext()) {
/*     */       
/* 161 */       K k = iterator0.next();
/* 162 */       V v = iterator1.next();
/* 163 */       result.put(k, v);
/*     */     } 
/* 165 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v1\GltfUtilsV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */