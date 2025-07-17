/*     */ package de.javagl.jgltf.model.v1;
/*     */ 
/*     */ import com.fasterxml.jackson.databind.ObjectMapper;
/*     */ import de.javagl.jgltf.impl.v1.GlTF;
/*     */ import de.javagl.jgltf.impl.v1.GlTFProperty;
/*     */ import de.javagl.jgltf.model.io.JacksonUtils;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
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
/*     */ public class GltfExtensionsV1
/*     */ {
/*     */   public static void addExtensionUsed(GlTF gltf, String extensionName) {
/*  55 */     List<String> oldExtensionsUsed = gltf.getExtensionsUsed();
/*  56 */     if (oldExtensionsUsed == null || 
/*  57 */       !oldExtensionsUsed.contains(extensionName))
/*     */     {
/*  59 */       gltf.addExtensionsUsed(extensionName);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeExtensionUsed(GlTF gltf, String extensionName) {
/*  74 */     List<String> oldExtensionsUsed = gltf.getExtensionsUsed();
/*  75 */     if (oldExtensionsUsed != null && oldExtensionsUsed
/*  76 */       .contains(extensionName))
/*     */     {
/*  78 */       gltf.removeExtensionsUsed(extensionName);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<String, Object> getExtensionMap(GlTFProperty gltfProperty, String extensionName) {
/*  95 */     Map<String, Object> extensions = gltfProperty.getExtensions();
/*  96 */     if (extensions == null)
/*     */     {
/*  98 */       return null;
/*     */     }
/* 100 */     Object value = extensions.get(extensionName);
/* 101 */     if (value == null)
/*     */     {
/* 103 */       return null;
/*     */     }
/* 105 */     if (value instanceof Map) {
/*     */       
/* 107 */       Map<?, ?> map = (Map<?, ?>)value;
/*     */       
/* 109 */       return (Map)map;
/*     */     } 
/*     */     
/* 112 */     return null;
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
/*     */   static boolean hasExtension(GlTFProperty gltfProperty, String extensionName) {
/* 128 */     return (getExtensionMap(gltfProperty, extensionName) != null);
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
/*     */   static String getExtensionPropertyValueAsString(GlTFProperty gltfProperty, String extensionName, String propertyName) {
/* 147 */     Map<String, Object> extensionMap = getExtensionMap(gltfProperty, extensionName);
/* 148 */     if (extensionMap == null)
/*     */     {
/* 150 */       return null;
/*     */     }
/* 152 */     Object value = extensionMap.get(propertyName);
/* 153 */     if (value == null)
/*     */     {
/* 155 */       return null;
/*     */     }
/* 157 */     return String.valueOf(value);
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
/*     */   static void setExtensionPropertyValue(GlTFProperty gltfProperty, String extensionName, String propertyName, Object propertyValue) {
/* 178 */     Map<String, Object> extensionMap = getExtensionMap(gltfProperty, extensionName);
/* 179 */     if (extensionMap == null) {
/*     */       
/* 181 */       extensionMap = new LinkedHashMap<>();
/* 182 */       gltfProperty.addExtensions(extensionName, extensionMap);
/*     */     } 
/* 184 */     extensionMap.put(propertyName, propertyValue);
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
/*     */   static <T> T fetchExtensionObject(GlTFProperty gltfProperty, String extensionName, Class<T> type) {
/* 204 */     Map<String, Object> extensions = gltfProperty.getExtensions();
/* 205 */     if (extensions == null)
/*     */     {
/* 207 */       return null;
/*     */     }
/* 209 */     Object extensionObject = extensions.get(extensionName);
/* 210 */     if (extensionObject == null)
/*     */     {
/* 212 */       return null;
/*     */     }
/*     */     
/* 215 */     ObjectMapper objectMapper = JacksonUtils.createObjectMapper();
/* 216 */     T extension = (T)objectMapper.convertValue(extensionObject, type);
/* 217 */     return extension;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\GltfExtensionsV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */