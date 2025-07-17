/*    */ package com.modularwarfare.raycast.obb;
/*    */ 
/*    */ import com.modularwarfare.common.vector.Matrix4f;
/*    */ import com.modularwarfare.common.vector.Vector3f;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ public class OBBModelScene
/*    */ {
/* 10 */   private Matrix4f matrix = new Matrix4f();
/* 11 */   public ArrayList<OBBModelBone> rootBones = new ArrayList<>();
/*    */   public void resetMatrix() {
/* 13 */     this.matrix = new Matrix4f();
/*    */   }
/*    */   
/*    */   public void translate(float x, float y, float z) {
/* 17 */     this.matrix.translate(new Vector3f(x, y, z));
/*    */   }
/*    */   
/*    */   public void translate(double x, double y, double z) {
/* 21 */     this.matrix.translate(new Vector3f(x, y, z));
/*    */   }
/*    */   
/*    */   public void rotate(float angle, float x, float y, float z) {
/* 25 */     this.matrix.rotate(angle, new Vector3f(x, y, z));
/*    */   }
/*    */   
/*    */   public void rotateDegree(float angle, float x, float y, float z) {
/* 29 */     this.matrix.rotate(angle / 180.0F * 3.14159F, new Vector3f(x, y, z));
/*    */   }
/*    */   
/*    */   public void scale(float x, float y, float z) {
/* 33 */     this.matrix.scale(new Vector3f(x, y, z));
/*    */   }
/*    */   
/*    */   public void computePose(OBBModelObject obbModelObject) {
/* 37 */     for (int i = 0; i < this.rootBones.size(); i++) {
/* 38 */       ((OBBModelBone)this.rootBones.get(i)).computePose(obbModelObject, new Matrix4f(this.matrix));
/*    */     }
/*    */   }
/*    */   
/*    */   public void updatePose(OBBModelObject obbModelObject) {
/* 43 */     for (int i = 0; i < this.rootBones.size(); i++)
/* 44 */       ((OBBModelBone)this.rootBones.get(i)).updatePose(obbModelObject); 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\raycast\obb\OBBModelScene.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */