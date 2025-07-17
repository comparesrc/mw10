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
/*     */ public class AnimationChannelTarget
/*     */   extends GlTFProperty
/*     */ {
/*     */   private Integer node;
/*     */   private String path;
/*     */   
/*     */   public void setNode(Integer node) {
/*  50 */     if (node == null) {
/*  51 */       this.node = node;
/*     */       return;
/*     */     } 
/*  54 */     this.node = node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getNode() {
/*  65 */     return this.node;
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
/*     */   
/*     */   public void setPath(String path) {
/*  85 */     if (path == null) {
/*  86 */       throw new NullPointerException("Invalid value for path: " + path + ", may not be null");
/*     */     }
/*  88 */     if (!"translation".equals(path) && !"rotation".equals(path) && !"scale".equals(path) && !"weights".equals(path)) {
/*  89 */       throw new IllegalArgumentException("Invalid value for path: " + path + ", valid: [translation, rotation, scale, weights]");
/*     */     }
/*  91 */     this.path = path;
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
/*     */   public String getPath() {
/* 108 */     return this.path;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\AnimationChannelTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */