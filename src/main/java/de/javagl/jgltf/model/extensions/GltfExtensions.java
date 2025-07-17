/*     */ package de.javagl.jgltf.model.extensions;
/*     */ 
/*     */ import com.fasterxml.jackson.databind.ObjectMapper;
/*     */ import de.javagl.jgltf.impl.v1.GlTFProperty;
/*     */ import de.javagl.jgltf.impl.v2.GlTFProperty;
/*     */ import de.javagl.jgltf.model.io.JacksonUtils;
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
/*     */ 
/*     */ 
/*     */ public class GltfExtensions
/*     */ {
/*     */   public static <T> T obtain(Object gltfProperty, String extensionName, Class<T> extensionType) {
/*  60 */     if (gltfProperty instanceof GlTFProperty) {
/*     */       
/*  62 */       GlTFProperty gltfPropertyV1 = (GlTFProperty)gltfProperty;
/*     */       
/*  64 */       Map<String, Object> extensions = gltfPropertyV1.getExtensions();
/*  65 */       return obtainInternal(extensions, extensionName, extensionType);
/*     */     } 
/*  67 */     if (gltfProperty instanceof GlTFProperty) {
/*     */       
/*  69 */       GlTFProperty gltfPropertyV2 = (GlTFProperty)gltfProperty;
/*     */       
/*  71 */       Map<String, Object> extensions = gltfPropertyV2.getExtensions();
/*  72 */       return obtainInternal(extensions, extensionName, extensionType);
/*     */     } 
/*  74 */     throw new IllegalArgumentException("Not a valid glTF property: " + gltfProperty);
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
/*     */   private static <T> T obtainInternal(Map<String, Object> extensions, String extensionName, Class<T> extensionType) {
/*  92 */     if (extensions == null)
/*     */     {
/*  94 */       return null;
/*     */     }
/*  96 */     Object object = extensions.get(extensionName);
/*  97 */     if (object == null)
/*     */     {
/*  99 */       return null;
/*     */     }
/* 101 */     return convertValueOptional(object, extensionType);
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
/*     */   private static <T> T convertValue(Object object, Class<T> type) throws IllegalArgumentException {
/* 116 */     ObjectMapper objectMapper = JacksonUtils.createObjectMapper();
/* 117 */     return (T)objectMapper.convertValue(object, type);
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
/*     */   private static <T> T convertValueOptional(Object object, Class<T> type) {
/*     */     try {
/* 133 */       return convertValue(object, type);
/*     */     }
/* 135 */     catch (IllegalArgumentException e) {
/*     */       
/* 137 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\extensions\GltfExtensions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */