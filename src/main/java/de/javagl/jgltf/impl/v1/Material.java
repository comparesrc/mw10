/*     */ package de.javagl.jgltf.impl.v1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Material
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private String technique;
/*     */   private Map<String, Object> values;
/*     */   
/*     */   public void setTechnique(String technique) {
/*  44 */     if (technique == null) {
/*  45 */       this.technique = technique;
/*     */       return;
/*     */     } 
/*  48 */     this.technique = technique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTechnique() {
/*  58 */     return this.technique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValues(Map<String, Object> values) {
/*  69 */     if (values == null) {
/*  70 */       this.values = values;
/*     */       return;
/*     */     } 
/*  73 */     this.values = values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> getValues() {
/*  84 */     return this.values;
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
/*     */   public void addValues(String key, Object value) {
/*  98 */     if (key == null) {
/*  99 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 101 */     if (value == null) {
/* 102 */       throw new NullPointerException("The value may not be null");
/*     */     }
/* 104 */     Map<String, Object> oldMap = this.values;
/* 105 */     Map<String, Object> newMap = new LinkedHashMap<>();
/* 106 */     if (oldMap != null) {
/* 107 */       newMap.putAll(oldMap);
/*     */     }
/* 109 */     newMap.put(key, value);
/* 110 */     this.values = newMap;
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
/*     */   public void removeValues(String key) {
/* 125 */     if (key == null) {
/* 126 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 128 */     Map<String, Object> oldMap = this.values;
/* 129 */     Map<String, Object> newMap = new LinkedHashMap<>();
/* 130 */     if (oldMap != null) {
/* 131 */       newMap.putAll(oldMap);
/*     */     }
/* 133 */     newMap.remove(key);
/* 134 */     if (newMap.isEmpty()) {
/* 135 */       this.values = null;
/*     */     } else {
/* 137 */       this.values = newMap;
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
/*     */   public Map<String, Object> defaultValues() {
/* 149 */     return new LinkedHashMap<>();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Material.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */