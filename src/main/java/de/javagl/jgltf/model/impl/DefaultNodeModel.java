/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.CameraModel;
/*     */ import de.javagl.jgltf.model.MathUtils;
/*     */ import de.javagl.jgltf.model.MeshModel;
/*     */ import de.javagl.jgltf.model.NodeModel;
/*     */ import de.javagl.jgltf.model.SkinModel;
/*     */ import de.javagl.jgltf.model.Suppliers;
/*     */ import de.javagl.jgltf.model.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
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
/*     */ public class DefaultNodeModel
/*     */   extends AbstractNamedModelElement
/*     */   implements NodeModel
/*     */ {
/*  53 */   private static final ThreadLocal<float[]> TEMP_MATRIX_4x4_IN_LOCAL = (ThreadLocal)ThreadLocal.withInitial(() -> new float[16]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static final ThreadLocal<float[]> TEMP_MATRIX_4x4_IN_GLOBAL = (ThreadLocal)ThreadLocal.withInitial(() -> new float[16]);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NodeModel parent;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<NodeModel> children;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<MeshModel> meshModels;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SkinModel skinModel;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CameraModel cameraModel;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] matrix;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] translation;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] rotation;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] scale;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] weights;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultNodeModel() {
/* 116 */     this.children = new ArrayList<>();
/* 117 */     this.meshModels = new ArrayList<>();
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
/*     */   public DefaultNodeModel(NodeModel other) {
/* 129 */     this.cameraModel = other.getCameraModel();
/* 130 */     this.children = new ArrayList<>();
/* 131 */     this.matrix = other.getMatrix();
/* 132 */     this.meshModels = new ArrayList<>();
/* 133 */     this.parent = other.getParent();
/* 134 */     this.rotation = other.getRotation();
/* 135 */     this.scale = other.getScale();
/* 136 */     this.skinModel = other.getSkinModel();
/* 137 */     this.translation = other.getTranslation();
/* 138 */     this.weights = other.getWeights();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(DefaultNodeModel parent) {
/* 148 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(DefaultNodeModel child) {
/* 158 */     Objects.requireNonNull(child, "The child may not be null");
/* 159 */     this.children.add(child);
/* 160 */     child.setParent(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMeshModel(MeshModel meshModel) {
/* 170 */     Objects.requireNonNull(meshModel, "The meshModel may not be null");
/* 171 */     this.meshModels.add(meshModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkinModel(SkinModel skinModel) {
/* 181 */     this.skinModel = skinModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCameraModel(CameraModel cameraModel) {
/* 191 */     this.cameraModel = cameraModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeModel getParent() {
/* 197 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<NodeModel> getChildren() {
/* 203 */     return Collections.unmodifiableList(this.children);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<MeshModel> getMeshModels() {
/* 209 */     return Collections.unmodifiableList(this.meshModels);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SkinModel getSkinModel() {
/* 215 */     return this.skinModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CameraModel getCameraModel() {
/* 221 */     return this.cameraModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMatrix(float[] matrix) {
/* 227 */     this.matrix = check(matrix, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getMatrix() {
/* 233 */     return this.matrix;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTranslation(float[] translation) {
/* 239 */     this.translation = check(translation, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getTranslation() {
/* 245 */     return this.translation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotation(float[] rotation) {
/* 251 */     this.rotation = check(rotation, 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getRotation() {
/* 257 */     return this.rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScale(float[] scale) {
/* 263 */     this.scale = check(scale, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getScale() {
/* 269 */     return this.scale;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWeights(float[] weights) {
/* 275 */     this.weights = weights;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getWeights() {
/* 281 */     return this.weights;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] computeLocalTransform(float[] result) {
/* 288 */     return computeLocalTransform(this, result);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] computeGlobalTransform(float[] result) {
/* 294 */     return computeGlobalTransform(this, result);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Supplier<float[]> createGlobalTransformSupplier() {
/* 300 */     return Suppliers.createTransformSupplier(this, NodeModel::computeGlobalTransform);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Supplier<float[]> createLocalTransformSupplier() {
/* 307 */     return Suppliers.createTransformSupplier(this, NodeModel::computeLocalTransform);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] computeLocalTransform(NodeModel nodeModel, float[] result) {
/* 330 */     float[] localResult = Utils.validate(result, 16);
/* 331 */     if (nodeModel.getMatrix() != null) {
/*     */       
/* 333 */       float[] m = nodeModel.getMatrix();
/* 334 */       System.arraycopy(m, 0, localResult, 0, m.length);
/* 335 */       return localResult;
/*     */     } 
/*     */     
/* 338 */     MathUtils.setIdentity4x4(localResult);
/* 339 */     if (nodeModel.getTranslation() != null) {
/*     */       
/* 341 */       float[] t = nodeModel.getTranslation();
/* 342 */       localResult[12] = t[0];
/* 343 */       localResult[13] = t[1];
/* 344 */       localResult[14] = t[2];
/*     */     } 
/* 346 */     if (nodeModel.getRotation() != null) {
/*     */       
/* 348 */       float[] q = nodeModel.getRotation();
/* 349 */       float[] m = TEMP_MATRIX_4x4_IN_LOCAL.get();
/* 350 */       MathUtils.quaternionToMatrix4x4(q, m);
/* 351 */       MathUtils.mul4x4(localResult, m, localResult);
/*     */     } 
/* 353 */     if (nodeModel.getScale() != null) {
/*     */       
/* 355 */       float[] s = nodeModel.getScale();
/* 356 */       float[] m = TEMP_MATRIX_4x4_IN_LOCAL.get();
/* 357 */       MathUtils.setIdentity4x4(m);
/* 358 */       m[0] = s[0];
/* 359 */       m[5] = s[1];
/* 360 */       m[10] = s[2];
/* 361 */       m[15] = 1.0F;
/* 362 */       MathUtils.mul4x4(localResult, m, localResult);
/*     */     } 
/* 364 */     return localResult;
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
/*     */   private static float[] computeGlobalTransform(NodeModel nodeModel, float[] result) {
/* 380 */     float[] localResult = Utils.validate(result, 16);
/* 381 */     float[] tempLocalTransform = TEMP_MATRIX_4x4_IN_GLOBAL.get();
/* 382 */     NodeModel currentNode = nodeModel;
/* 383 */     MathUtils.setIdentity4x4(localResult);
/* 384 */     while (currentNode != null) {
/*     */       
/* 386 */       currentNode.computeLocalTransform(tempLocalTransform);
/* 387 */       MathUtils.mul4x4(tempLocalTransform, localResult, localResult);
/*     */       
/* 389 */       currentNode = currentNode.getParent();
/*     */     } 
/* 391 */     return localResult;
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
/*     */   private static float[] check(float[] array, int expectedLength) {
/* 409 */     if (array == null)
/*     */     {
/* 411 */       return null;
/*     */     }
/* 413 */     if (array.length != expectedLength)
/*     */     {
/* 415 */       throw new IllegalArgumentException("Expected " + expectedLength + " array elements, but found " + array.length);
/*     */     }
/*     */     
/* 418 */     return array;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultNodeModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */