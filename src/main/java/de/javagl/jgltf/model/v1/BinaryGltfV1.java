/*     */ package de.javagl.jgltf.model.v1;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v1.GlTFProperty;
/*     */ import de.javagl.jgltf.impl.v1.Image;
/*     */ import de.javagl.jgltf.model.GltfException;
/*     */ import de.javagl.jgltf.model.image.ImageReaders;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import javax.imageio.ImageReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinaryGltfV1
/*     */ {
/*     */   private static final String KHRONOS_BINARY_GLTF_EXTENSION_NAME = "KHR_binary_glTF";
/*     */   private static final String BINARY_GLTF_BUFFER_ID = "binary_glTF";
/*     */   
/*     */   public static boolean hasBinaryGltfExtension(GlTFProperty gltfProperty) {
/*  72 */     return GltfExtensionsV1.hasExtension(gltfProperty, "KHR_binary_glTF");
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
/*     */   static String getBinaryGltfBufferViewId(GlTFProperty gltfProperty) {
/*  87 */     return GltfExtensionsV1.getExtensionPropertyValueAsString(gltfProperty, "KHR_binary_glTF", "bufferView");
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
/*     */   public static void setBinaryGltfBufferViewId(GlTFProperty gltfProperty, String bufferViewId) {
/* 104 */     GltfExtensionsV1.setExtensionPropertyValue(gltfProperty, "KHR_binary_glTF", "bufferView", bufferViewId);
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
/*     */   public static void setBinaryGltfImageProperties(Image image, ByteBuffer imageData) {
/* 129 */     ImageReader imageReader = null;
/*     */     
/*     */     try {
/* 132 */       imageReader = ImageReaders.findImageReader(imageData);
/* 133 */       int width = imageReader.getWidth(0);
/* 134 */       int height = imageReader.getHeight(0);
/* 135 */       String mimeType = "image/" + imageReader.getFormatName();
/*     */       
/* 137 */       GltfExtensionsV1.setExtensionPropertyValue((GlTFProperty)image, "KHR_binary_glTF", "width", 
/* 138 */           Integer.valueOf(width));
/* 139 */       GltfExtensionsV1.setExtensionPropertyValue((GlTFProperty)image, "KHR_binary_glTF", "height", 
/* 140 */           Integer.valueOf(height));
/* 141 */       GltfExtensionsV1.setExtensionPropertyValue((GlTFProperty)image, "KHR_binary_glTF", "mimeType", mimeType);
/*     */     
/*     */     }
/* 144 */     catch (IOException e) {
/*     */       
/* 146 */       throw new GltfException("Could not derive image properties for binary glTF", e);
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 151 */       if (imageReader != null)
/*     */       {
/* 153 */         imageReader.dispose();
/*     */       }
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
/*     */   public static String getBinaryGltfExtensionName() {
/* 167 */     return "KHR_binary_glTF";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getBinaryGltfBufferId() {
/* 178 */     return "binary_glTF";
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
/*     */   public static boolean isBinaryGltfBufferId(String id) {
/* 190 */     return "binary_glTF".equals(id);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\BinaryGltfV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */