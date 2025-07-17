/*    */ package de.javagl.jgltf.model.impl;
/*    */ 
/*    */ import de.javagl.jgltf.model.MeshModel;
/*    */ import de.javagl.jgltf.model.MeshPrimitiveModel;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultMeshModel
/*    */   extends AbstractNamedModelElement
/*    */   implements MeshModel
/*    */ {
/* 57 */   private final List<MeshPrimitiveModel> meshPrimitiveModels = new ArrayList<>();
/*    */ 
/*    */ 
/*    */   
/*    */   private float[] weights;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addMeshPrimitiveModel(MeshPrimitiveModel meshPrimitiveModel) {
/* 67 */     this.meshPrimitiveModels.add(meshPrimitiveModel);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setWeights(float[] weights) {
/* 78 */     this.weights = weights;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<MeshPrimitiveModel> getMeshPrimitiveModels() {
/* 84 */     return Collections.unmodifiableList(this.meshPrimitiveModels);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float[] getWeights() {
/* 90 */     return this.weights;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultMeshModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */