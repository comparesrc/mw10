/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
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
/*     */ public class UriResolvers
/*     */ {
/*  47 */   private static final Logger logger = Logger.getLogger(UriResolvers.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Function<String, ByteBuffer> createBaseUriResolver(final URI baseUri) {
/*  65 */     Objects.requireNonNull(baseUri, "The baseUri may not be null");
/*  66 */     Function<String, InputStream> inputStreamFunction = new Function<String, InputStream>()
/*     */       {
/*     */ 
/*     */ 
/*     */         
/*     */         public InputStream apply(String uriString)
/*     */         {
/*     */           try {
/*  74 */             URI absoluteUri = IO.makeAbsolute(baseUri, uriString);
/*  75 */             return IO.createInputStream(absoluteUri);
/*     */           }
/*  77 */           catch (IOException e) {
/*     */             
/*  79 */             UriResolvers.logger.warning("Could not open input stream for URI " + uriString + ":  " + e
/*  80 */                 .getMessage());
/*  81 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/*  85 */     return reading(inputStreamFunction);
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
/*     */   public static Function<String, ByteBuffer> createBasePathResolver(final Path basePath) {
/* 105 */     Objects.requireNonNull(basePath, "The basePath may not be null");
/* 106 */     Function<String, InputStream> inputStreamFunction = new Function<String, InputStream>()
/*     */       {
/*     */ 
/*     */ 
/*     */         
/*     */         public InputStream apply(String uriString)
/*     */         {
/*     */           try {
/* 114 */             if (IO.isDataUriString(uriString))
/*     */             {
/* 116 */               return IO.createInputStream(URI.create(uriString));
/*     */             }
/* 118 */             Path absolutePath = IO.makeAbsolute(basePath, uriString);
/* 119 */             return IO.createInputStream(absolutePath);
/*     */           }
/* 121 */           catch (IOException e) {
/*     */             
/* 123 */             UriResolvers.logger.warning("Could not open input stream for URI " + uriString + ":  " + e
/* 124 */                 .getMessage());
/* 125 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/* 129 */     return reading(inputStreamFunction);
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
/*     */   public static Function<String, ByteBuffer> createResourceUriResolver(final Class<?> c) {
/* 142 */     Objects.requireNonNull(c, "The class may not be null");
/* 143 */     Function<String, InputStream> inputStreamFunction = new Function<String, InputStream>()
/*     */       {
/*     */ 
/*     */ 
/*     */         
/*     */         public InputStream apply(String uriString)
/*     */         {
/* 150 */           InputStream inputStream = c.getResourceAsStream("/" + uriString);
/* 151 */           if (inputStream == null)
/*     */           {
/* 153 */             UriResolvers.logger.warning("Could not obtain input stream for resource with URI " + uriString);
/*     */           }
/*     */ 
/*     */           
/* 157 */           return inputStream;
/*     */         }
/*     */       };
/* 160 */     return reading(inputStreamFunction);
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
/*     */   private static <T> Function<T, ByteBuffer> reading(final Function<? super T, ? extends InputStream> inputStreamFunction) {
/* 178 */     return new Function<T, ByteBuffer>()
/*     */       {
/*     */         
/*     */         public ByteBuffer apply(T t)
/*     */         {
/* 183 */           try (InputStream inputStream = (InputStream)inputStreamFunction.apply(t)) {
/*     */             
/* 185 */             if (inputStream == null) {
/*     */               
/* 187 */               UriResolvers.logger.warning("The input stream was null");
/* 188 */               return null;
/*     */             } 
/* 190 */             byte[] data = IO.readStream(inputStream);
/* 191 */             return Buffers.create(data);
/*     */           }
/* 193 */           catch (IOException e) {
/*     */             
/* 195 */             UriResolvers.logger.warning("Could not read from input stream: " + e
/* 196 */                 .getMessage());
/* 197 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\UriResolvers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */