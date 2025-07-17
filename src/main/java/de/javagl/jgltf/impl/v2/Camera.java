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
/*  55 */     if (orthographic == null) {
/*  56 */       this.orthographic = orthographic;
/*     */       return;
/*     */     } 
/*  59 */     this.orthographic = orthographic;
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
/*     */   public CameraOrthographic getOrthographic() {
/*  71 */     return this.orthographic;
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
/*     */   public void setPerspective(CameraPerspective perspective) {
/*  83 */     if (perspective == null) {
/*  84 */       this.perspective = perspective;
/*     */       return;
/*     */     } 
/*  87 */     this.perspective = perspective;
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
/*     */   public CameraPerspective getPerspective() {
/*  99 */     return this.perspective;
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
/* 114 */     if (type == null) {
/* 115 */       throw new NullPointerException("Invalid value for type: " + type + ", may not be null");
/*     */     }
/* 117 */     if (!"perspective".equals(type) && !"orthographic".equals(type)) {
/* 118 */       throw new IllegalArgumentException("Invalid value for type: " + type + ", valid: [perspective, orthographic]");
/*     */     }
/* 120 */     this.type = type;
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
/* 132 */     return this.type;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Camera.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */