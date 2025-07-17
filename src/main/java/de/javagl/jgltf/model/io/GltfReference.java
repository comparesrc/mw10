/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GltfReference
/*     */ {
/*     */   private final String name;
/*     */   private final String uri;
/*     */   private final Consumer<ByteBuffer> target;
/*     */   
/*     */   public GltfReference(String name, String uri, Consumer<ByteBuffer> target) {
/*  64 */     this.name = Objects.<String>requireNonNull(name, "The name may not be null");
/*     */     
/*  66 */     this.uri = Objects.<String>requireNonNull(uri, "The uri may not be null");
/*     */     
/*  68 */     this.target = Objects.<Consumer<ByteBuffer>>requireNonNull(target, "The target may not be null");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  79 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUri() {
/*  89 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Consumer<ByteBuffer> getTarget() {
/* 100 */     return this.target;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\GltfReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */