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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeshPrimitive
/*     */   extends GlTFProperty
/*     */ {
/*     */   private Map<String, String> attributes;
/*     */   private String indices;
/*     */   private String material;
/*     */   private Integer mode;
/*     */   
/*     */   public void setAttributes(Map<String, String> attributes) {
/*  60 */     if (attributes == null) {
/*  61 */       this.attributes = attributes;
/*     */       return;
/*     */     } 
/*  64 */     this.attributes = attributes;
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
/*     */   public Map<String, String> getAttributes() {
/*  76 */     return this.attributes;
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
/*     */   public void addAttributes(String key, String value) {
/*  90 */     if (key == null) {
/*  91 */       throw new NullPointerException("The key may not be null");
/*     */     }
/*  93 */     if (value == null) {
/*  94 */       throw new NullPointerException("The value may not be null");
/*     */     }
/*  96 */     Map<String, String> oldMap = this.attributes;
/*  97 */     Map<String, String> newMap = new LinkedHashMap<>();
/*  98 */     if (oldMap != null) {
/*  99 */       newMap.putAll(oldMap);
/*     */     }
/* 101 */     newMap.put(key, value);
/* 102 */     this.attributes = newMap;
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
/*     */   public void removeAttributes(String key) {
/* 117 */     if (key == null) {
/* 118 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 120 */     Map<String, String> oldMap = this.attributes;
/* 121 */     Map<String, String> newMap = new LinkedHashMap<>();
/* 122 */     if (oldMap != null) {
/* 123 */       newMap.putAll(oldMap);
/*     */     }
/* 125 */     newMap.remove(key);
/* 126 */     if (newMap.isEmpty()) {
/* 127 */       this.attributes = null;
/*     */     } else {
/* 129 */       this.attributes = newMap;
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
/*     */   public Map<String, String> defaultAttributes() {
/* 141 */     return new LinkedHashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndices(String indices) {
/* 151 */     if (indices == null) {
/* 152 */       this.indices = indices;
/*     */       return;
/*     */     } 
/* 155 */     this.indices = indices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIndices() {
/* 165 */     return this.indices;
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
/*     */   public void setMaterial(String material) {
/* 177 */     if (material == null) {
/* 178 */       throw new NullPointerException("Invalid value for material: " + material + ", may not be null");
/*     */     }
/* 180 */     this.material = material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMaterial() {
/* 191 */     return this.material;
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
/*     */   public void setMode(Integer mode) {
/* 205 */     if (mode == null) {
/* 206 */       this.mode = mode;
/*     */       return;
/*     */     } 
/* 209 */     if (mode.intValue() != 0 && mode.intValue() != 1 && mode.intValue() != 2 && mode.intValue() != 3 && mode.intValue() != 4 && mode.intValue() != 5 && mode.intValue() != 6) {
/* 210 */       throw new IllegalArgumentException("Invalid value for mode: " + mode + ", valid: [0, 1, 2, 3, 4, 5, 6]");
/*     */     }
/* 212 */     this.mode = mode;
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
/*     */   public Integer getMode() {
/* 224 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer defaultMode() {
/* 235 */     return Integer.valueOf(4);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\MeshPrimitive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */