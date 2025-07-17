/*    */ package com.modularwarfare.raycast.obb;
/*    */ 
/*    */ import com.modularwarfare.common.vector.Matrix4f;
/*    */ import com.modularwarfare.common.vector.Vector3f;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class OBBModelBone
/*    */ {
/*    */   public String name;
/*    */   public OBBModelBone parent;
/* 11 */   public Vector3f oirign = new Vector3f();
/* 12 */   public Vector3f translation = new Vector3f(0.0F, 0.0F, 0.0F);
/* 13 */   public Vector3f rotation = new Vector3f();
/* 14 */   public ArrayList<OBBModelBone> children = new ArrayList<>();
/* 15 */   public Matrix4f currentPose = new Matrix4f();
/* 16 */   public static final Vector3f YAW = new Vector3f(0.0F, -1.0F, 0.0F);
/* 17 */   public static final Vector3f PITCH = new Vector3f(1.0F, 0.0F, 0.0F);
/* 18 */   public static final Vector3f ROOL = new Vector3f(0.0F, 0.0F, -1.0F);
/*    */   
/*    */   public void updatePose(OBBModelObject obbModelObject) {
/* 21 */     obbModelObject.onBoneUpdatePose(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void computePose(OBBModelObject obbModelObject, Matrix4f matrix) {
/* 26 */     matrix = matrix.translate(this.translation).translate(this.oirign).rotate(this.rotation.y, YAW).rotate(this.rotation.x, PITCH).rotate(this.rotation.z, ROOL).translate(this.oirign.negate(null));
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
/* 37 */     this.currentPose = new Matrix4f(matrix);
/* 38 */     for (int i = 0; i < this.children.size(); i++)
/* 39 */       ((OBBModelBone)this.children.get(i)).computePose(obbModelObject, new Matrix4f(matrix)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\raycast\obb\OBBModelBone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */