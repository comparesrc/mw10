/*     */ package de.javagl.jgltf.impl.v2;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private Map<String, Integer> attributes;
/*     */   private Integer indices;
/*     */   private Integer material;
/*     */   private Integer mode;
/*     */   private List<Map<String, Integer>> targets;
/*     */   
/*     */   public void setAttributes(Map<String, Integer> attributes) {
/*  75 */     if (attributes == null) {
/*  76 */       throw new NullPointerException("Invalid value for attributes: " + attributes + ", may not be null");
/*     */     }
/*  78 */     this.attributes = attributes;
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
/*     */   public Map<String, Integer> getAttributes() {
/*  90 */     return this.attributes;
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
/*     */   public void addAttributes(String key, Integer value) {
/* 104 */     if (key == null) {
/* 105 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 107 */     if (value == null) {
/* 108 */       throw new NullPointerException("The value may not be null");
/*     */     }
/* 110 */     Map<String, Integer> oldMap = this.attributes;
/* 111 */     Map<String, Integer> newMap = new LinkedHashMap<>();
/* 112 */     if (oldMap != null) {
/* 113 */       newMap.putAll(oldMap);
/*     */     }
/* 115 */     newMap.put(key, value);
/* 116 */     this.attributes = newMap;
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
/*     */   public void removeAttributes(String key) {
/* 129 */     if (key == null) {
/* 130 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 132 */     Map<String, Integer> oldMap = this.attributes;
/* 133 */     Map<String, Integer> newMap = new LinkedHashMap<>();
/* 134 */     if (oldMap != null) {
/* 135 */       newMap.putAll(oldMap);
/*     */     }
/* 137 */     newMap.remove(key);
/* 138 */     this.attributes = newMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndices(Integer indices) {
/* 148 */     if (indices == null) {
/* 149 */       this.indices = indices;
/*     */       return;
/*     */     } 
/* 152 */     this.indices = indices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getIndices() {
/* 162 */     return this.indices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaterial(Integer material) {
/* 173 */     if (material == null) {
/* 174 */       this.material = material;
/*     */       return;
/*     */     } 
/* 177 */     this.material = material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getMaterial() {
/* 188 */     return this.material;
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
/* 202 */     if (mode == null) {
/* 203 */       this.mode = mode;
/*     */       return;
/*     */     } 
/* 206 */     if (mode.intValue() != 0 && mode.intValue() != 1 && mode.intValue() != 2 && mode.intValue() != 3 && mode.intValue() != 4 && mode.intValue() != 5 && mode.intValue() != 6) {
/* 207 */       throw new IllegalArgumentException("Invalid value for mode: " + mode + ", valid: [0, 1, 2, 3, 4, 5, 6]");
/*     */     }
/* 209 */     this.mode = mode;
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
/* 221 */     return this.mode;
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
/* 232 */     return Integer.valueOf(4);
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
/*     */   public void setTargets(List<Map<String, Integer>> targets) {
/* 251 */     if (targets == null) {
/* 252 */       this.targets = targets;
/*     */       return;
/*     */     } 
/* 255 */     if (targets.size() < 1) {
/* 256 */       throw new IllegalArgumentException("Number of targets elements is < 1");
/*     */     }
/* 258 */     this.targets = targets;
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
/*     */   public List<Map<String, Integer>> getTargets() {
/* 275 */     return this.targets;
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
/*     */   public void addTargets(Map<String, Integer> element) {
/* 288 */     if (element == null) {
/* 289 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 291 */     List<Map<String, Integer>> oldList = this.targets;
/* 292 */     List<Map<String, Integer>> newList = new ArrayList<>();
/* 293 */     if (oldList != null) {
/* 294 */       newList.addAll(oldList);
/*     */     }
/* 296 */     newList.add(element);
/* 297 */     this.targets = newList;
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
/*     */   public void removeTargets(Map<String, Integer> element) {
/* 312 */     if (element == null) {
/* 313 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 315 */     List<Map<String, Integer>> oldList = this.targets;
/* 316 */     List<Map<String, Integer>> newList = new ArrayList<>();
/* 317 */     if (oldList != null) {
/* 318 */       newList.addAll(oldList);
/*     */     }
/* 320 */     newList.remove(element);
/* 321 */     if (newList.isEmpty()) {
/* 322 */       this.targets = null;
/*     */     } else {
/* 324 */       this.targets = newList;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\MeshPrimitive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */