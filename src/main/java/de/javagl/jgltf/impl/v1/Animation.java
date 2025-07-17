/*     */ package de.javagl.jgltf.impl.v1;
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
/*     */ public class Animation
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private List<AnimationChannel> channels;
/*     */   private Map<String, String> parameters;
/*     */   private Map<String, AnimationSampler> samplers;
/*     */   
/*     */   public void setChannels(List<AnimationChannel> channels) {
/*  65 */     if (channels == null) {
/*  66 */       this.channels = channels;
/*     */       return;
/*     */     } 
/*  69 */     this.channels = channels;
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
/*     */   public List<AnimationChannel> getChannels() {
/*  84 */     return this.channels;
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
/*     */   public void addChannels(AnimationChannel element) {
/*  97 */     if (element == null) {
/*  98 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 100 */     List<AnimationChannel> oldList = this.channels;
/* 101 */     List<AnimationChannel> newList = new ArrayList<>();
/* 102 */     if (oldList != null) {
/* 103 */       newList.addAll(oldList);
/*     */     }
/* 105 */     newList.add(element);
/* 106 */     this.channels = newList;
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
/*     */   public void removeChannels(AnimationChannel element) {
/* 121 */     if (element == null) {
/* 122 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 124 */     List<AnimationChannel> oldList = this.channels;
/* 125 */     List<AnimationChannel> newList = new ArrayList<>();
/* 126 */     if (oldList != null) {
/* 127 */       newList.addAll(oldList);
/*     */     }
/* 129 */     newList.remove(element);
/* 130 */     if (newList.isEmpty()) {
/* 131 */       this.channels = null;
/*     */     } else {
/* 133 */       this.channels = newList;
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
/*     */   public List<AnimationChannel> defaultChannels() {
/* 145 */     return new ArrayList<>();
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
/*     */   public void setParameters(Map<String, String> parameters) {
/* 157 */     if (parameters == null) {
/* 158 */       this.parameters = parameters;
/*     */       return;
/*     */     } 
/* 161 */     this.parameters = parameters;
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
/*     */   public Map<String, String> getParameters() {
/* 173 */     return this.parameters;
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
/*     */   public void addParameters(String key, String value) {
/* 187 */     if (key == null) {
/* 188 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 190 */     if (value == null) {
/* 191 */       throw new NullPointerException("The value may not be null");
/*     */     }
/* 193 */     Map<String, String> oldMap = this.parameters;
/* 194 */     Map<String, String> newMap = new LinkedHashMap<>();
/* 195 */     if (oldMap != null) {
/* 196 */       newMap.putAll(oldMap);
/*     */     }
/* 198 */     newMap.put(key, value);
/* 199 */     this.parameters = newMap;
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
/*     */   public void removeParameters(String key) {
/* 214 */     if (key == null) {
/* 215 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 217 */     Map<String, String> oldMap = this.parameters;
/* 218 */     Map<String, String> newMap = new LinkedHashMap<>();
/* 219 */     if (oldMap != null) {
/* 220 */       newMap.putAll(oldMap);
/*     */     }
/* 222 */     newMap.remove(key);
/* 223 */     if (newMap.isEmpty()) {
/* 224 */       this.parameters = null;
/*     */     } else {
/* 226 */       this.parameters = newMap;
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
/*     */   public Map<String, String> defaultParameters() {
/* 238 */     return new LinkedHashMap<>();
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
/*     */   public void setSamplers(Map<String, AnimationSampler> samplers) {
/* 251 */     if (samplers == null) {
/* 252 */       this.samplers = samplers;
/*     */       return;
/*     */     } 
/* 255 */     this.samplers = samplers;
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
/*     */   public Map<String, AnimationSampler> getSamplers() {
/* 268 */     return this.samplers;
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
/*     */   public void addSamplers(String key, AnimationSampler value) {
/* 282 */     if (key == null) {
/* 283 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 285 */     if (value == null) {
/* 286 */       throw new NullPointerException("The value may not be null");
/*     */     }
/* 288 */     Map<String, AnimationSampler> oldMap = this.samplers;
/* 289 */     Map<String, AnimationSampler> newMap = new LinkedHashMap<>();
/* 290 */     if (oldMap != null) {
/* 291 */       newMap.putAll(oldMap);
/*     */     }
/* 293 */     newMap.put(key, value);
/* 294 */     this.samplers = newMap;
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
/*     */   public void removeSamplers(String key) {
/* 309 */     if (key == null) {
/* 310 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 312 */     Map<String, AnimationSampler> oldMap = this.samplers;
/* 313 */     Map<String, AnimationSampler> newMap = new LinkedHashMap<>();
/* 314 */     if (oldMap != null) {
/* 315 */       newMap.putAll(oldMap);
/*     */     }
/* 317 */     newMap.remove(key);
/* 318 */     if (newMap.isEmpty()) {
/* 319 */       this.samplers = null;
/*     */     } else {
/* 321 */       this.samplers = newMap;
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
/*     */   public Map<String, AnimationSampler> defaultSamplers() {
/* 333 */     return new LinkedHashMap<>();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Animation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */