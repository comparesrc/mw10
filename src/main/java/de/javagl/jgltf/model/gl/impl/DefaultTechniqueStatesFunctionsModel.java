/*     */ package de.javagl.jgltf.model.gl.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.gl.TechniqueStatesFunctionsModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultTechniqueStatesFunctionsModel
/*     */   implements TechniqueStatesFunctionsModel
/*     */ {
/*     */   private float[] blendColor;
/*     */   private int[] blendEquationSeparate;
/*     */   private int[] blendFuncSeparate;
/*     */   private boolean[] colorMask;
/*     */   private int[] cullFace;
/*     */   private int[] depthFunc;
/*     */   private boolean[] depthMask;
/*     */   private float[] depthRange;
/*     */   private int[] frontFace;
/*     */   private float[] lineWidth;
/*     */   private float[] polygonOffset;
/*     */   
/*     */   public float[] getBlendColor() {
/* 107 */     return this.blendColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlendColor(float[] blendColor) {
/* 117 */     this.blendColor = blendColor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getBlendEquationSeparate() {
/* 123 */     return this.blendEquationSeparate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlendEquationSeparate(int[] blendEquationSeparate) {
/* 133 */     this.blendEquationSeparate = blendEquationSeparate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getBlendFuncSeparate() {
/* 139 */     return this.blendFuncSeparate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlendFuncSeparate(int[] blendFuncSeparate) {
/* 149 */     this.blendFuncSeparate = blendFuncSeparate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean[] getColorMask() {
/* 155 */     return this.colorMask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorMask(boolean[] colorMask) {
/* 165 */     this.colorMask = colorMask;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getCullFace() {
/* 171 */     return this.cullFace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCullFace(int[] cullFace) {
/* 181 */     this.cullFace = cullFace;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getDepthFunc() {
/* 187 */     return this.depthFunc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDepthFunc(int[] depthFunc) {
/* 197 */     this.depthFunc = depthFunc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean[] getDepthMask() {
/* 203 */     return this.depthMask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDepthMask(boolean[] depthMask) {
/* 213 */     this.depthMask = depthMask;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getDepthRange() {
/* 219 */     return this.depthRange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDepthRange(float[] depthRange) {
/* 229 */     this.depthRange = depthRange;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getFrontFace() {
/* 235 */     return this.frontFace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFrontFace(int[] frontFace) {
/* 245 */     this.frontFace = frontFace;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getLineWidth() {
/* 251 */     return this.lineWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineWidth(float[] lineWidth) {
/* 261 */     this.lineWidth = lineWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getPolygonOffset() {
/* 267 */     return this.polygonOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolygonOffset(float[] polygonOffset) {
/* 277 */     this.polygonOffset = polygonOffset;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\impl\DefaultTechniqueStatesFunctionsModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */