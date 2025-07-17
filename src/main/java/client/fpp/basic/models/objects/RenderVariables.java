/*    */ package com.modularwarfare.client.fpp.basic.models.objects;
/*    */ 
/*    */ import org.lwjgl.util.vector.Vector3f;
/*    */ 
/*    */ public class RenderVariables
/*    */ {
/*    */   public Vector3f offset;
/*    */   public Vector3f scale;
/*    */   public Float angle;
/*    */   public Vector3f rotation;
/*    */   
/*    */   public RenderVariables(Vector3f offset, Vector3f scale) {
/* 13 */     this.offset = offset;
/* 14 */     this.scale = scale;
/*    */   }
/*    */   
/*    */   public RenderVariables(Vector3f offset) {
/* 18 */     this.offset = offset;
/*    */   }
/*    */   
/*    */   public RenderVariables(Vector3f offset, float scale) {
/* 22 */     this(offset, new Vector3f(scale, scale, scale));
/*    */   }
/*    */   
/*    */   public RenderVariables(Vector3f offset, float angle, Vector3f rotation) {
/* 26 */     this.offset = offset;
/* 27 */     this.angle = Float.valueOf(angle);
/* 28 */     this.rotation = rotation;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\models\objects\RenderVariables.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */