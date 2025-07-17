/*     */ package de.javagl.jgltf.impl.v1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnimationSampler
/*     */   extends GlTFProperty
/*     */ {
/*     */   private String input;
/*     */   private String interpolation;
/*     */   private String output;
/*     */   
/*     */   public void setInput(String input) {
/*  53 */     if (input == null) {
/*  54 */       throw new NullPointerException("Invalid value for input: " + input + ", may not be null");
/*     */     }
/*  56 */     this.input = input;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInput() {
/*  67 */     return this.input;
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
/*     */   public void setInterpolation(String interpolation) {
/*  81 */     if (interpolation == null) {
/*  82 */       this.interpolation = interpolation;
/*     */       return;
/*     */     } 
/*  85 */     if (!"LINEAR".equals(interpolation) && !"STEP".equals(interpolation)) {
/*  86 */       throw new IllegalArgumentException("Invalid value for interpolation: " + interpolation + ", valid: [\"LINEAR\", \"STEP\"]");
/*     */     }
/*  88 */     this.interpolation = interpolation;
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
/*     */   public String getInterpolation() {
/* 100 */     return this.interpolation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String defaultInterpolation() {
/* 111 */     return "LINEAR";
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
/*     */   public void setOutput(String output) {
/* 123 */     if (output == null) {
/* 124 */       throw new NullPointerException("Invalid value for output: " + output + ", may not be null");
/*     */     }
/* 126 */     this.output = output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOutput() {
/* 137 */     return this.output;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\AnimationSampler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */