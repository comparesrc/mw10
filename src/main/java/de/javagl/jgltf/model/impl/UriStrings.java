/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.ImageModel;
/*     */ import de.javagl.jgltf.model.gl.ShaderModel;
/*     */ import de.javagl.jgltf.model.io.MimeTypes;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Collection;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UriStrings
/*     */ {
/*  49 */   private static final Logger logger = Logger.getLogger(UriStrings.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String createBufferUriString(Collection<? extends String> existingUriStrings) {
/*  61 */     int counter = 0;
/*     */     
/*     */     while (true) {
/*  64 */       String uri = "buffer" + counter + ".bin";
/*  65 */       if (!existingUriStrings.contains(uri))
/*     */       {
/*  67 */         return uri;
/*     */       }
/*  69 */       counter++;
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
/*     */   public static String createImageUriString(ImageModel imageModel, Collection<? extends String> existingUriStrings) {
/*  85 */     String extensionWithoutDot = determineImageFileNameExtension(imageModel);
/*  86 */     int counter = 0;
/*     */     
/*     */     while (true) {
/*  89 */       String uri = "image" + counter + "." + extensionWithoutDot;
/*  90 */       if (!existingUriStrings.contains(uri))
/*     */       {
/*  92 */         return uri;
/*     */       }
/*  94 */       counter++;
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
/*     */   private static String determineImageFileNameExtension(ImageModel imageModel) {
/* 109 */     String mimeTypeString = imageModel.getMimeType();
/* 110 */     if (mimeTypeString == null) {
/*     */       
/* 112 */       ByteBuffer imageData = imageModel.getImageData();
/*     */       
/* 114 */       mimeTypeString = MimeTypes.guessImageMimeTypeStringUnchecked(imageData);
/*     */     } 
/*     */ 
/*     */     
/* 118 */     if (mimeTypeString != null) {
/*     */ 
/*     */       
/* 121 */       String extensionWithoutDot = MimeTypes.imageFileNameExtensionForMimeTypeString(mimeTypeString);
/*     */       
/* 123 */       if (extensionWithoutDot != null)
/*     */       {
/* 125 */         return extensionWithoutDot;
/*     */       }
/*     */     } 
/* 128 */     logger.warning("Could not determine file extension for image URI");
/* 129 */     return "";
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
/*     */   public static String createShaderUriString(ShaderModel shaderModel, Collection<? extends String> existingUriStrings) {
/* 144 */     String extensionWithoutDot = determineShaderFileNameExtension(shaderModel);
/* 145 */     int counter = 0;
/*     */     
/*     */     while (true) {
/* 148 */       String uri = "shader" + counter + "." + extensionWithoutDot;
/* 149 */       if (!existingUriStrings.contains(uri))
/*     */       {
/* 151 */         return uri;
/*     */       }
/* 153 */       counter++;
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
/*     */   private static String determineShaderFileNameExtension(ShaderModel shaderModel) {
/* 167 */     if (shaderModel.getShaderType() == ShaderModel.ShaderType.VERTEX_SHADER)
/*     */     {
/* 169 */       return "vert";
/*     */     }
/* 171 */     return "frag";
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\UriStrings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */