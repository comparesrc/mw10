/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.CameraModel;
/*     */ import de.javagl.jgltf.model.CameraOrthographicModel;
/*     */ import de.javagl.jgltf.model.CameraPerspectiveModel;
/*     */ import de.javagl.jgltf.model.Suppliers;
/*     */ import java.util.function.DoubleSupplier;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultCameraModel
/*     */   extends AbstractNamedModelElement
/*     */   implements CameraModel
/*     */ {
/*     */   private CameraOrthographicModel cameraOrthographicModel;
/*     */   private CameraPerspectiveModel cameraPerspectiveModel;
/*     */   
/*     */   public void setCameraOrthographicModel(CameraOrthographicModel cameraOrthographicModel) {
/*  69 */     this.cameraOrthographicModel = cameraOrthographicModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CameraOrthographicModel getCameraOrthographicModel() {
/*  75 */     return this.cameraOrthographicModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCameraPerspectiveModel(CameraPerspectiveModel cameraPerspectiveModel) {
/*  86 */     this.cameraPerspectiveModel = cameraPerspectiveModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CameraPerspectiveModel getCameraPerspectiveModel() {
/*  92 */     return this.cameraPerspectiveModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] computeProjectionMatrix(float[] result, Float aspectRatio) {
/*  98 */     return Cameras.computeProjectionMatrix(this, aspectRatio, result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Supplier<float[]> createProjectionMatrixSupplier(DoubleSupplier aspectRatioSupplier) {
/* 105 */     return Suppliers.createTransformSupplier(this, (c, t) -> {
/*     */           Float aspectRatio = null;
/*     */           if (aspectRatioSupplier != null)
/*     */             aspectRatio = Float.valueOf((float)aspectRatioSupplier.getAsDouble()); 
/*     */           computeProjectionMatrix(t, aspectRatio);
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultCameraModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */