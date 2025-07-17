/*    */ package com.modularwarfare.client.fpp.basic.models.objects;
/*    */ 
/*    */ import org.lwjgl.util.vector.Vector3f;
/*    */ 
/*    */ public class BreakActionData
/*    */ {
/*    */   public String modelName;
/*    */   public Vector3f breakPoint;
/*    */   public float angle;
/*    */   public boolean scopePart;
/*    */   
/*    */   public BreakActionData(String modelName, Vector3f breakPoint, float angle, boolean scopePart) {
/* 13 */     this.modelName = modelName;
/* 14 */     this.breakPoint = breakPoint;
/* 15 */     this.angle = angle;
/* 16 */     this.scopePart = scopePart;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\models\objects\BreakActionData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */