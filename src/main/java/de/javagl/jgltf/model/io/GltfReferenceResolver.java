/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
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
/*     */ 
/*     */ public class GltfReferenceResolver
/*     */ {
/*  47 */   private static final Logger logger = Logger.getLogger(GltfReferenceResolver.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resolveAll(Iterable<? extends GltfReference> references, URI baseUri) {
/*  60 */     Objects.requireNonNull(references, "The references may not be null");
/*  61 */     Objects.requireNonNull(baseUri, "The baseUri may not be null");
/*     */     
/*  63 */     Function<String, ByteBuffer> uriResolver = UriResolvers.createBaseUriResolver(baseUri);
/*  64 */     resolveAll(references, uriResolver);
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
/*     */   public static void resolveAll(Iterable<? extends GltfReference> references, Path basePath) {
/*  78 */     Objects.requireNonNull(references, "The references may not be null");
/*  79 */     Objects.requireNonNull(basePath, "The basePath may not be null");
/*     */     
/*  81 */     Function<String, ByteBuffer> uriResolver = UriResolvers.createBasePathResolver(basePath);
/*  82 */     resolveAll(references, uriResolver);
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
/*     */   public static void resolveAll(Iterable<? extends GltfReference> references, Function<? super String, ? extends ByteBuffer> uriResolver) {
/*  97 */     Objects.requireNonNull(references, "The references may not be null");
/*  98 */     Objects.requireNonNull(uriResolver, "The uriResolver may not be null");
/*     */     
/* 100 */     for (GltfReference reference : references)
/*     */     {
/* 102 */       resolve(reference, uriResolver);
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
/*     */   public static void resolve(GltfReference reference, Function<? super String, ? extends ByteBuffer> uriResolver) {
/* 120 */     Objects.requireNonNull(reference, "The reference may not be null");
/* 121 */     Objects.requireNonNull(uriResolver, "The uriResolver may not be null");
/*     */     
/* 123 */     String uri = reference.getUri();
/* 124 */     ByteBuffer byteBuffer = uriResolver.apply(uri);
/* 125 */     if (byteBuffer == null)
/*     */     {
/* 127 */       logger.warning("Could not resolve URI " + uri);
/*     */     }
/* 129 */     Consumer<ByteBuffer> target = reference.getTarget();
/* 130 */     target.accept(byteBuffer);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\GltfReferenceResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */