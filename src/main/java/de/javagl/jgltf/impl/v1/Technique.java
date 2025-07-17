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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Technique
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private Map<String, TechniqueParameters> parameters;
/*     */   private Map<String, String> attributes;
/*     */   private String program;
/*     */   private Map<String, String> uniforms;
/*     */   private TechniqueStates states;
/*     */   
/*     */   public void setParameters(Map<String, TechniqueParameters> parameters) {
/*  65 */     if (parameters == null) {
/*  66 */       this.parameters = parameters;
/*     */       return;
/*     */     } 
/*  69 */     this.parameters = parameters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, TechniqueParameters> getParameters() {
/*  80 */     return this.parameters;
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
/*     */   public void addParameters(String key, TechniqueParameters value) {
/*  94 */     if (key == null) {
/*  95 */       throw new NullPointerException("The key may not be null");
/*     */     }
/*  97 */     if (value == null) {
/*  98 */       throw new NullPointerException("The value may not be null");
/*     */     }
/* 100 */     Map<String, TechniqueParameters> oldMap = this.parameters;
/* 101 */     Map<String, TechniqueParameters> newMap = new LinkedHashMap<>();
/* 102 */     if (oldMap != null) {
/* 103 */       newMap.putAll(oldMap);
/*     */     }
/* 105 */     newMap.put(key, value);
/* 106 */     this.parameters = newMap;
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
/* 121 */     if (key == null) {
/* 122 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 124 */     Map<String, TechniqueParameters> oldMap = this.parameters;
/* 125 */     Map<String, TechniqueParameters> newMap = new LinkedHashMap<>();
/* 126 */     if (oldMap != null) {
/* 127 */       newMap.putAll(oldMap);
/*     */     }
/* 129 */     newMap.remove(key);
/* 130 */     if (newMap.isEmpty()) {
/* 131 */       this.parameters = null;
/*     */     } else {
/* 133 */       this.parameters = newMap;
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
/*     */   public Map<String, TechniqueParameters> defaultParameters() {
/* 145 */     return new LinkedHashMap<>();
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
/*     */   public void setAttributes(Map<String, String> attributes) {
/* 157 */     if (attributes == null) {
/* 158 */       this.attributes = attributes;
/*     */       return;
/*     */     } 
/* 161 */     this.attributes = attributes;
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
/* 173 */     return this.attributes;
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
/* 187 */     if (key == null) {
/* 188 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 190 */     if (value == null) {
/* 191 */       throw new NullPointerException("The value may not be null");
/*     */     }
/* 193 */     Map<String, String> oldMap = this.attributes;
/* 194 */     Map<String, String> newMap = new LinkedHashMap<>();
/* 195 */     if (oldMap != null) {
/* 196 */       newMap.putAll(oldMap);
/*     */     }
/* 198 */     newMap.put(key, value);
/* 199 */     this.attributes = newMap;
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
/* 214 */     if (key == null) {
/* 215 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 217 */     Map<String, String> oldMap = this.attributes;
/* 218 */     Map<String, String> newMap = new LinkedHashMap<>();
/* 219 */     if (oldMap != null) {
/* 220 */       newMap.putAll(oldMap);
/*     */     }
/* 222 */     newMap.remove(key);
/* 223 */     if (newMap.isEmpty()) {
/* 224 */       this.attributes = null;
/*     */     } else {
/* 226 */       this.attributes = newMap;
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
/*     */   public void setProgram(String program) {
/* 249 */     if (program == null) {
/* 250 */       throw new NullPointerException("Invalid value for program: " + program + ", may not be null");
/*     */     }
/* 252 */     this.program = program;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getProgram() {
/* 262 */     return this.program;
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
/*     */   public void setUniforms(Map<String, String> uniforms) {
/* 274 */     if (uniforms == null) {
/* 275 */       this.uniforms = uniforms;
/*     */       return;
/*     */     } 
/* 278 */     this.uniforms = uniforms;
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
/*     */   public Map<String, String> getUniforms() {
/* 290 */     return this.uniforms;
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
/*     */   public void addUniforms(String key, String value) {
/* 304 */     if (key == null) {
/* 305 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 307 */     if (value == null) {
/* 308 */       throw new NullPointerException("The value may not be null");
/*     */     }
/* 310 */     Map<String, String> oldMap = this.uniforms;
/* 311 */     Map<String, String> newMap = new LinkedHashMap<>();
/* 312 */     if (oldMap != null) {
/* 313 */       newMap.putAll(oldMap);
/*     */     }
/* 315 */     newMap.put(key, value);
/* 316 */     this.uniforms = newMap;
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
/*     */   public void removeUniforms(String key) {
/* 331 */     if (key == null) {
/* 332 */       throw new NullPointerException("The key may not be null");
/*     */     }
/* 334 */     Map<String, String> oldMap = this.uniforms;
/* 335 */     Map<String, String> newMap = new LinkedHashMap<>();
/* 336 */     if (oldMap != null) {
/* 337 */       newMap.putAll(oldMap);
/*     */     }
/* 339 */     newMap.remove(key);
/* 340 */     if (newMap.isEmpty()) {
/* 341 */       this.uniforms = null;
/*     */     } else {
/* 343 */       this.uniforms = newMap;
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
/*     */   public Map<String, String> defaultUniforms() {
/* 355 */     return new LinkedHashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStates(TechniqueStates states) {
/* 366 */     if (states == null) {
/* 367 */       this.states = states;
/*     */       return;
/*     */     } 
/* 370 */     this.states = states;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TechniqueStates getStates() {
/* 381 */     return this.states;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TechniqueStates defaultStates() {
/* 392 */     return new TechniqueStates();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Technique.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */