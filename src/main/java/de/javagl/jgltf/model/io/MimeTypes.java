/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import de.javagl.jgltf.model.image.ImageReaders;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.logging.Logger;
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
/*     */ public class MimeTypes
/*     */ {
/*  48 */   private static final Logger logger = Logger.getLogger(MimeTypes.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String guessImageMimeTypeString(String uriString) {
/*     */     try {
/*  67 */       URI uri = new URI(uriString);
/*  68 */       if ("data".equalsIgnoreCase(uri.getScheme()))
/*     */       {
/*  70 */         String raw = uri.getRawSchemeSpecificPart().toLowerCase();
/*  71 */         String type = getStringBetween(raw, "image/", ";base64");
/*  72 */         return "image/" + type.toLowerCase();
/*     */       }
/*     */     
/*  75 */     } catch (URISyntaxException e) {
/*     */       
/*  77 */       return null;
/*     */     } 
/*  79 */     int lastDotIndex = uriString.lastIndexOf('.');
/*  80 */     if (lastDotIndex == -1)
/*     */     {
/*  82 */       return null;
/*     */     }
/*  84 */     String end = uriString.substring(lastDotIndex + 1).toLowerCase();
/*  85 */     if (end.equalsIgnoreCase("jpg") || end.equalsIgnoreCase("jpeg"))
/*     */     {
/*  87 */       return "image/jpeg";
/*     */     }
/*  89 */     return "image/" + end.toLowerCase();
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
/*     */   private static String getStringBetween(String input, String before, String after) {
/* 106 */     int beforeIndex = input.indexOf(before);
/* 107 */     if (beforeIndex < 0)
/*     */     {
/* 109 */       return null;
/*     */     }
/* 111 */     int afterIndex = input.indexOf(after);
/* 112 */     if (afterIndex < beforeIndex)
/*     */     {
/* 114 */       return null;
/*     */     }
/* 116 */     return input.substring(beforeIndex + before.length(), afterIndex);
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
/*     */   private static String guessImageMimeTypeString(ByteBuffer imageData) throws IOException {
/* 136 */     ImageReader imageReader = null;
/*     */     
/*     */     try {
/* 139 */       imageReader = ImageReaders.findImageReader(imageData);
/* 140 */       return "image/" + imageReader.getFormatName().toLowerCase();
/*     */     }
/*     */     finally {
/*     */       
/* 144 */       if (imageReader != null)
/*     */       {
/* 146 */         imageReader.dispose();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String guessImageMimeTypeStringUnchecked(ByteBuffer imageData) {
/*     */     try {
/* 166 */       return guessImageMimeTypeString(imageData);
/*     */     }
/* 168 */     catch (IOException e) {
/*     */       
/* 170 */       return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String guessImageMimeTypeString(String uriString, ByteBuffer imageData) {
/* 195 */     if (uriString != null) {
/*     */ 
/*     */       
/* 198 */       String imageMimeTypeString = guessImageMimeTypeString(uriString);
/* 199 */       if (imageMimeTypeString != null)
/*     */       {
/* 201 */         return imageMimeTypeString;
/*     */       }
/*     */     } 
/* 204 */     if (imageData != null)
/*     */     {
/* 206 */       return guessImageMimeTypeStringUnchecked(imageData);
/*     */     }
/* 208 */     return null;
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
/*     */   public static String imageFileNameExtensionForMimeTypeString(String mimeTypeString) {
/* 229 */     if ("image/jpeg".equals(mimeTypeString))
/*     */     {
/* 231 */       return "jpg";
/*     */     }
/* 233 */     if ("image/png".equals(mimeTypeString))
/*     */     {
/* 235 */       return "png";
/*     */     }
/* 237 */     if ("image/gif".equals(mimeTypeString))
/*     */     {
/* 239 */       return "gif";
/*     */     }
/* 241 */     logger.warning("Invalid MIME type string: " + mimeTypeString);
/* 242 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\MimeTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */