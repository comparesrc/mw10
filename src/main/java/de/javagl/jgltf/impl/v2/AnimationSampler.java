/*     */ package de.javagl.jgltf.impl.v2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private Integer input;
/*     */   private String interpolation;
/*     */   private Integer output;
/*     */   
/*     */   public void setInput(Integer input) {
/*  51 */     if (input == null) {
/*  52 */       throw new NullPointerException("Invalid value for input: " + input + ", may not be null");
/*     */     }
/*  54 */     this.input = input;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getInput() {
/*  64 */     return this.input;
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
/*  78 */     if (interpolation == null) {
/*  79 */       this.interpolation = interpolation;
/*     */       return;
/*     */     } 
/*  82 */     if (!"LINEAR".equals(interpolation) && !"STEP".equals(interpolation) && !"CUBICSPLINE".equals(interpolation)) {
/*  83 */       throw new IllegalArgumentException("Invalid value for interpolation: " + interpolation + ", valid: [LINEAR, STEP, CUBICSPLINE]");
/*     */     }
/*  85 */     this.interpolation = interpolation;
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
/*  97 */     return this.interpolation;
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
/* 108 */     return "LINEAR";
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
/*     */   public void setOutput(Integer output) {
/* 120 */     if (output == null) {
/* 121 */       throw new NullPointerException("Invalid value for output: " + output + ", may not be null");
/*     */     }
/* 123 */     this.output = output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getOutput() {
/* 134 */     return this.output;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\AnimationSampler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */