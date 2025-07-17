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
/*     */ public class Camera
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private CameraOrthographic orthographic;
/*     */   private CameraPerspective perspective;
/*     */   private String type;
/*     */   
/*     */   public void setOrthographic(CameraOrthographic orthographic) {
/*  52 */     if (orthographic == null) {
/*  53 */       this.orthographic = orthographic;
/*     */       return;
/*     */     } 
/*  56 */     this.orthographic = orthographic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CameraOrthographic getOrthographic() {
/*  67 */     return this.orthographic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPerspective(CameraPerspective perspective) {
/*  78 */     if (perspective == null) {
/*  79 */       this.perspective = perspective;
/*     */       return;
/*     */     } 
/*  82 */     this.perspective = perspective;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CameraPerspective getPerspective() {
/*  93 */     return this.perspective;
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
/*     */   public void setType(String type) {
/* 108 */     if (type == null) {
/* 109 */       throw new NullPointerException("Invalid value for type: " + type + ", may not be null");
/*     */     }
/* 111 */     if (!"perspective".equals(type) && !"orthographic".equals(type)) {
/* 112 */       throw new IllegalArgumentException("Invalid value for type: " + type + ", valid: [\"perspective\", \"orthographic\"]");
/*     */     }
/* 114 */     this.type = type;
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
/*     */   public String getType() {
/* 126 */     return this.type;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Camera.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */