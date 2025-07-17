/*     */ package org.spongepowered.asm.launch.platform;
/*     */ 
/*     */ import com.google.common.io.ByteSource;
/*     */ import com.google.common.io.Files;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.Manifest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MainAttributes
/*     */ {
/*  46 */   private static final Map<URI, MainAttributes> instances = new HashMap<>();
/*     */ 
/*     */   
/*     */   protected final Attributes attributes;
/*     */ 
/*     */ 
/*     */   
/*     */   private MainAttributes() {
/*  54 */     this.attributes = new Attributes();
/*     */   }
/*     */   
/*     */   private MainAttributes(File jar) {
/*  58 */     this.attributes = getAttributes(jar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String get(String name) {
/*  69 */     if (this.attributes != null) {
/*  70 */       return this.attributes.getValue(name);
/*     */     }
/*  72 */     return null;
/*     */   }
/*     */   
/*     */   private static Attributes getAttributes(File codeSource) {
/*  76 */     if (codeSource == null) {
/*  77 */       return null;
/*     */     }
/*     */     
/*  80 */     if (codeSource.isFile()) {
/*  81 */       Attributes attributes = getJarAttributes(codeSource);
/*  82 */       if (attributes != null) {
/*  83 */         return attributes;
/*     */       }
/*     */     } 
/*     */     
/*  87 */     if (codeSource.isDirectory()) {
/*  88 */       Attributes attributes = getDirAttributes(codeSource);
/*  89 */       if (attributes != null) {
/*  90 */         return attributes;
/*     */       }
/*     */     } 
/*     */     
/*  94 */     return new Attributes();
/*     */   }
/*     */   
/*     */   private static Attributes getJarAttributes(File jar) {
/*  98 */     JarFile jarFile = null;
/*     */     
/* 100 */     try { jarFile = new JarFile(jar);
/* 101 */       Manifest manifest = jarFile.getManifest();
/* 102 */       if (manifest != null) {
/* 103 */         return manifest.getMainAttributes();
/*     */       } }
/* 105 */     catch (IOException iOException)
/*     */     
/*     */     { 
/*     */       try {
/* 109 */         if (jarFile != null) {
/* 110 */           jarFile.close();
/*     */         }
/* 112 */       } catch (IOException iOException1) {} } finally { try { if (jarFile != null) jarFile.close();  } catch (IOException iOException) {} }
/*     */ 
/*     */ 
/*     */     
/* 116 */     return null;
/*     */   }
/*     */   
/*     */   private static Attributes getDirAttributes(File dir) {
/* 120 */     File manifestFile = new File(dir, "META-INF/MANIFEST.MF");
/* 121 */     if (manifestFile.isFile()) {
/* 122 */       ByteSource source = Files.asByteSource(manifestFile);
/* 123 */       InputStream inputStream = null;
/*     */       
/* 125 */       try { inputStream = source.openBufferedStream();
/* 126 */         Manifest manifest = new Manifest(inputStream);
/* 127 */         return manifest.getMainAttributes(); }
/* 128 */       catch (IOException iOException)
/*     */       
/*     */       { 
/*     */         try {
/* 132 */           if (inputStream != null) {
/* 133 */             inputStream.close();
/*     */           }
/* 135 */         } catch (IOException iOException1) {} } finally { try { if (inputStream != null) inputStream.close();  } catch (IOException iOException) {} }
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MainAttributes of(File jar) {
/* 152 */     return of(jar.toURI());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MainAttributes of(URI uri) {
/* 162 */     MainAttributes attributes = instances.get(uri);
/* 163 */     if (attributes == null) {
/* 164 */       attributes = new MainAttributes(new File(uri));
/* 165 */       instances.put(uri, attributes);
/*     */     } 
/* 167 */     return attributes;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\MainAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */