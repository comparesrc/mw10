/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Base64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IO
/*     */ {
/*     */   public static URI makeAbsolute(URI baseUri, String uriString) throws IOException {
/*     */     try {
/*  63 */       String escapedUriString = uriString.replaceAll(" ", "%20");
/*  64 */       URI uri = new URI(escapedUriString);
/*  65 */       if (uri.isAbsolute())
/*     */       {
/*  67 */         return uri;
/*     */       }
/*  69 */       return baseUri.resolve(escapedUriString);
/*     */     }
/*  71 */     catch (URISyntaxException e) {
/*     */       
/*  73 */       throw new IOException("Invalid URI string: " + uriString, e);
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
/*     */   public static Path makeAbsolute(Path basePath, String uriString) throws IOException {
/*     */     try {
/*  91 */       String escapedUriString = uriString.replaceAll(" ", "%20");
/*     */       
/*  93 */       URI uri = new URI(escapedUriString);
/*  94 */       if (uri.isAbsolute())
/*     */       {
/*  96 */         return Paths.get(uri).toAbsolutePath();
/*     */       }
/*  98 */       return basePath.resolve(escapedUriString).toAbsolutePath();
/*     */     }
/* 100 */     catch (URISyntaxException e) {
/*     */       
/* 102 */       throw new IOException("Invalid URI string: " + uriString, e);
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
/*     */   public static URI getParent(URI uri) {
/* 117 */     if (uri.getPath().endsWith("/"))
/*     */     {
/* 119 */       return uri.resolve("..");
/*     */     }
/* 121 */     return uri.resolve(".");
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
/*     */   public static Path getParent(Path path) {
/* 135 */     return path.getParent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDataUri(URI uri) {
/* 146 */     return "data".equalsIgnoreCase(uri.getScheme());
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
/*     */   public static boolean isDataUriString(String uriString) {
/* 158 */     if (uriString == null)
/*     */     {
/* 160 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 164 */       URI uri = new URI(uriString);
/* 165 */       return isDataUri(uri);
/*     */     }
/* 167 */     catch (URISyntaxException e) {
/*     */       
/* 169 */       return false;
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
/*     */   public static String extractFileName(URI uri) {
/* 185 */     String s = uri.toString();
/* 186 */     int lastSlashIndex = s.lastIndexOf('/');
/* 187 */     if (lastSlashIndex != -1)
/*     */     {
/* 189 */       return s.substring(lastSlashIndex + 1);
/*     */     }
/* 191 */     return s;
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
/*     */   public static boolean existsUnchecked(URI uri) {
/*     */     try {
/* 206 */       return exists(uri);
/*     */     }
/* 208 */     catch (IOException e) {
/*     */       
/* 210 */       return false;
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
/*     */   private static boolean exists(URI uri) throws IOException {
/* 225 */     URL url = uri.toURL();
/* 226 */     URLConnection connection = url.openConnection();
/* 227 */     if (connection instanceof HttpURLConnection) {
/*     */       
/* 229 */       HttpURLConnection httpConnection = (HttpURLConnection)connection;
/* 230 */       httpConnection.setRequestMethod("HEAD");
/* 231 */       int responseCode = httpConnection.getResponseCode();
/* 232 */       return (responseCode == 200);
/*     */     } 
/* 234 */     String path = uri.getPath();
/* 235 */     return (new File(path)).exists();
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
/*     */   public static long getContentLength(URI uri) {
/*     */     try {
/* 250 */       URLConnection connection = uri.toURL().openConnection();
/* 251 */       return connection.getContentLengthLong();
/*     */     }
/* 253 */     catch (IOException e) {
/*     */       
/* 255 */       return -1L;
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
/*     */   public static InputStream createInputStream(URI uri) throws IOException {
/* 269 */     if ("data".equalsIgnoreCase(uri.getScheme())) {
/*     */       
/* 271 */       byte[] data = readDataUri(uri.toString());
/* 272 */       return new ByteArrayInputStream(data);
/*     */     } 
/*     */     
/*     */     try {
/* 276 */       return uri.toURL().openStream();
/*     */     }
/* 278 */     catch (MalformedURLException e) {
/*     */       
/* 280 */       throw new IOException(e);
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
/*     */   public static InputStream createInputStream(Path path) throws IOException {
/* 294 */     if ("data".equalsIgnoreCase(path.toUri().getScheme())) {
/*     */       
/* 296 */       byte[] data = readDataUri(path.toUri().toString());
/* 297 */       return new ByteArrayInputStream(data);
/*     */     } 
/*     */     
/*     */     try {
/* 301 */       return path.toUri().toURL().openStream();
/*     */     }
/* 303 */     catch (MalformedURLException e) {
/*     */       
/* 305 */       throw new IOException(e);
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
/*     */   public static byte[] read(URI uri) throws IOException {
/* 320 */     try (InputStream inputStream = createInputStream(uri)) {
/*     */       
/* 322 */       byte[] data = readStream(inputStream);
/* 323 */       return data;
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
/*     */   public static byte[] readDataUri(String uriString) {
/* 341 */     String encoding = "base64,";
/* 342 */     int encodingIndex = uriString.indexOf(encoding);
/* 343 */     if (encodingIndex < 0)
/*     */     {
/* 345 */       throw new IllegalArgumentException("The given URI string is not a base64 encoded data URI string: " + uriString);
/*     */     }
/*     */ 
/*     */     
/* 349 */     int contentStartIndex = encodingIndex + encoding.length();
/* 350 */     byte[] data = Base64.getDecoder().decode(uriString
/* 351 */         .substring(contentStartIndex));
/* 352 */     return data;
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
/*     */   public static byte[] readStream(InputStream inputStream) throws IOException {
/* 366 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 367 */     byte[] buffer = new byte[16384];
/*     */     
/*     */     while (true) {
/* 370 */       int read = inputStream.read(buffer);
/* 371 */       if (read == -1) {
/*     */         break;
/*     */       }
/*     */       
/* 375 */       baos.write(buffer, 0, read);
/* 376 */       if (Thread.currentThread().isInterrupted())
/*     */       {
/* 378 */         throw new IOException("Interrupted while reading stream", new InterruptedException());
/*     */       }
/*     */     } 
/*     */     
/* 382 */     baos.flush();
/* 383 */     return baos.toByteArray();
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
/*     */   static void read(InputStream inputStream, byte[] data, int offset, int numBytesToRead) throws IOException {
/* 404 */     if (offset < 0)
/*     */     {
/* 406 */       throw new IllegalArgumentException("Array offset is negative: " + offset);
/*     */     }
/*     */     
/* 409 */     if (offset + numBytesToRead > data.length)
/*     */     {
/* 411 */       throw new IllegalArgumentException("Cannot write " + numBytesToRead + " bytes into an array of length " + data.length + " with an offset of " + offset);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 416 */     int totalNumBytesRead = 0;
/*     */     
/*     */     do {
/* 419 */       int read = inputStream.read(data, offset + totalNumBytesRead, numBytesToRead - totalNumBytesRead);
/*     */ 
/*     */       
/* 422 */       if (read == -1)
/*     */       {
/* 424 */         throw new IOException("Could not read " + numBytesToRead + " bytes");
/*     */       }
/*     */       
/* 427 */       totalNumBytesRead += read;
/* 428 */     } while (totalNumBytesRead != numBytesToRead);
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
/*     */   public static void read(InputStream inputStream, byte[] data) throws IOException {
/* 448 */     read(inputStream, data, 0, data.length);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\IO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */