/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.CameraModel;
/*     */ import de.javagl.jgltf.model.CameraOrthographicModel;
/*     */ import de.javagl.jgltf.model.CameraPerspectiveModel;
/*     */ import de.javagl.jgltf.model.MathUtils;
/*     */ import de.javagl.jgltf.model.Utils;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Cameras
/*     */ {
/*  46 */   private static final Logger logger = Logger.getLogger(Cameras.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static float[] computeProjectionMatrix(CameraModel cameraModel, Float aspectRatio, float[] result) {
/*  67 */     float[] localResult = Utils.validate(result, 16);
/*     */ 
/*     */     
/*  70 */     CameraPerspectiveModel cameraPerspective = cameraModel.getCameraPerspectiveModel();
/*     */ 
/*     */     
/*  73 */     CameraOrthographicModel cameraOrthographic = cameraModel.getCameraOrthographicModel();
/*     */     
/*  75 */     if (cameraPerspective != null) {
/*     */       
/*  77 */       float fovRad = cameraPerspective.getYfov().floatValue();
/*  78 */       float fovDeg = (float)Math.toDegrees(fovRad);
/*  79 */       float localAspectRatio = 1.0F;
/*  80 */       if (aspectRatio != null) {
/*     */         
/*  82 */         localAspectRatio = aspectRatio.floatValue();
/*     */       }
/*  84 */       else if (cameraPerspective.getAspectRatio() != null) {
/*     */         
/*  86 */         localAspectRatio = cameraPerspective.getAspectRatio().floatValue();
/*     */       } 
/*  88 */       float zNear = cameraPerspective.getZnear().floatValue();
/*  89 */       Float zFar = cameraPerspective.getZfar();
/*  90 */       if (zFar == null)
/*     */       {
/*  92 */         MathUtils.infinitePerspective4x4(fovDeg, localAspectRatio, zNear, localResult);
/*     */       
/*     */       }
/*     */       else
/*     */       {
/*  97 */         MathUtils.perspective4x4(fovDeg, localAspectRatio, zNear, zFar
/*  98 */             .floatValue(), localResult);
/*     */       }
/*     */     
/* 101 */     } else if (cameraOrthographic != null) {
/*     */       
/* 103 */       float xMag = cameraOrthographic.getXmag().floatValue();
/* 104 */       float yMag = cameraOrthographic.getYmag().floatValue();
/* 105 */       float zNear = cameraOrthographic.getZnear().floatValue();
/* 106 */       float zFar = cameraOrthographic.getZfar().floatValue();
/* 107 */       MathUtils.setIdentity4x4(localResult);
/* 108 */       localResult[0] = 1.0F / xMag;
/* 109 */       localResult[5] = 1.0F / yMag;
/* 110 */       localResult[10] = 2.0F / (zNear - zFar);
/* 111 */       localResult[14] = (zFar + zNear) / (zNear - zFar);
/*     */     }
/*     */     else {
/*     */       
/* 115 */       logger.severe("Invalid camera type: " + cameraModel);
/* 116 */       MathUtils.setIdentity4x4(localResult);
/*     */     } 
/* 118 */     return localResult;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\Cameras.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */