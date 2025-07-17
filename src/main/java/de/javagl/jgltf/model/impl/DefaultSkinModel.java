/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.AccessorDatas;
/*     */ import de.javagl.jgltf.model.AccessorFloatData;
/*     */ import de.javagl.jgltf.model.AccessorModel;
/*     */ import de.javagl.jgltf.model.MathUtils;
/*     */ import de.javagl.jgltf.model.NodeModel;
/*     */ import de.javagl.jgltf.model.SkinModel;
/*     */ import de.javagl.jgltf.model.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultSkinModel
/*     */   extends AbstractNamedModelElement
/*     */   implements SkinModel
/*     */ {
/*  73 */   private float[] bindShapeMatrix = MathUtils.createIdentity4x4();
/*  74 */   private final List<NodeModel> joints = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private NodeModel skeleton;
/*     */ 
/*     */   
/*     */   private AccessorModel inverseBindMatrices;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBindShapeMatrix(float[] bindShapeMatrix) {
/*  86 */     if (bindShapeMatrix == null) {
/*     */       
/*  88 */       this.bindShapeMatrix = MathUtils.createIdentity4x4();
/*     */     }
/*     */     else {
/*     */       
/*  92 */       this.bindShapeMatrix = (float[])bindShapeMatrix.clone();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addJoint(NodeModel joint) {
/* 103 */     Objects.requireNonNull(joint, "The joint may not be null");
/* 104 */     this.joints.add(joint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkeleton(NodeModel skeleton) {
/* 114 */     this.skeleton = skeleton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInverseBindMatrices(AccessorModel inverseBindMatrices) {
/* 124 */     this.inverseBindMatrices = Objects.<AccessorModel>requireNonNull(inverseBindMatrices, "The inverseBindMatrices may not be null");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getBindShapeMatrix(float[] result) {
/* 132 */     float[] localResult = Utils.validate(result, 16);
/* 133 */     System.arraycopy(this.bindShapeMatrix, 0, localResult, 0, 16);
/* 134 */     return localResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<NodeModel> getJoints() {
/* 141 */     return Collections.unmodifiableList(this.joints);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeModel getSkeleton() {
/* 147 */     return this.skeleton;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessorModel getInverseBindMatrices() {
/* 153 */     return this.inverseBindMatrices;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getInverseBindMatrix(int index, float[] result) {
/* 159 */     float[] localResult = Utils.validate(result, 16);
/*     */     
/* 161 */     AccessorFloatData inverseBindMatricesData = AccessorDatas.createFloat(this.inverseBindMatrices);
/* 162 */     for (int j = 0; j < 16; j++)
/*     */     {
/* 164 */       localResult[j] = inverseBindMatricesData.get(index, j);
/*     */     }
/* 166 */     return localResult;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultSkinModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */