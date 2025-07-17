/*     */ package de.javagl.jgltf.impl.v2;
/*     */ 
/*     */ import java.util.LinkedHashMap;
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
/*     */ public class GlTFProperty
/*     */ {
/*     */   private Map<String, Object> extensions;
/*     */   private Object extras;
/*     */   
/*     */   public void setExtensions(Map<String, Object> extensions) {
/*  39 */     if (extensions == null) {
/*  40 */       this.extensions = extensions;
/*     */       return;
/*     */     } 
/*  43 */     this.extensions = extensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> getExtensions() {
/*  53 */     return this.extensions;
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
/*     */   public void addExtensions(String key, Object value) {
/*  67 */     if (key == null) {
/*  68 */       throw new NullPointerException("The key may not be null");
/*     */     }
/*  70 */     if (value == null) {
/*  71 */       throw new NullPointerException("The value may not be null");
/*     */     }
/*  73 */     Map<String, Object> oldMap = this.extensions;
/*  74 */     Map<String, Object> newMap = new LinkedHashMap<>();
/*  75 */     if (oldMap != null) {
/*  76 */       newMap.putAll(oldMap);
/*     */     }
/*  78 */     newMap.put(key, value);
/*  79 */     this.extensions = newMap;
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
/*     */   public void removeExtensions(String key) {
/*  94 */     if (key == null) {
/*  95 */       throw new NullPointerException("The key may not be null");
/*     */     }
/*  97 */     Map<String, Object> oldMap = this.extensions;
/*  98 */     Map<String, Object> newMap = new LinkedHashMap<>();
/*  99 */     if (oldMap != null) {
/* 100 */       newMap.putAll(oldMap);
/*     */     }
/* 102 */     newMap.remove(key);
/* 103 */     if (newMap.isEmpty()) {
/* 104 */       this.extensions = null;
/*     */     } else {
/* 106 */       this.extensions = newMap;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtras(Object extras) {
/* 117 */     if (extras == null) {
/* 118 */       this.extras = extras;
/*     */       return;
/*     */     } 
/* 121 */     this.extras = extras;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getExtras() {
/* 131 */     return this.extras;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\GlTFProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */