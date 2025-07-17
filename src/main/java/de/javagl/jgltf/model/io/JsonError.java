/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import com.fasterxml.jackson.core.JsonStreamContext;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Deque;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JsonError
/*     */ {
/*     */   private final String message;
/*     */   private final List<String> jsonPath;
/*     */   private final Throwable throwable;
/*     */   
/*     */   JsonError(String message, JsonStreamContext jsonStreamContext, Throwable throwable) {
/*  68 */     this.message = message;
/*  69 */     this.jsonPath = Collections.unmodifiableList(
/*  70 */         createJsonPath(jsonStreamContext));
/*  71 */     this.throwable = throwable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  81 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getJsonPath() {
/*  92 */     return this.jsonPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJsonPathString() {
/* 102 */     return this.jsonPath.stream().collect(Collectors.joining("."));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getThrowable() {
/* 113 */     return this.throwable;
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
/*     */   private static List<String> createJsonPath(JsonStreamContext streamContext) {
/* 127 */     Collection<JsonStreamContext> list = expand(streamContext);
/* 128 */     return (List<String>)list.stream()
/* 129 */       .map(c -> (c.getCurrentName() == null) ? "" : c.getCurrentName())
/*     */       
/* 131 */       .collect(Collectors.toList());
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
/*     */   private static Collection<JsonStreamContext> expand(JsonStreamContext streamContext) {
/* 144 */     Deque<JsonStreamContext> collection = new LinkedList<>();
/*     */     
/* 146 */     JsonStreamContext current = streamContext;
/* 147 */     while (current != null) {
/*     */       
/* 149 */       collection.addFirst(current);
/* 150 */       current = current.getParent();
/*     */     } 
/* 152 */     return collection;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\JsonError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */