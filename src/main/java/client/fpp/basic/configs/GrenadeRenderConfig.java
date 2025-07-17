/*    */ package com.modularwarfare.client.fpp.basic.configs;
/*    */ 
/*    */ import org.lwjgl.util.vector.Vector3f;
/*    */ 
/*    */ public class GrenadeRenderConfig
/*    */ {
/*  7 */   public String modelFileName = "";
/*    */   
/*  9 */   public Arms arms = new Arms();
/*    */   
/* 11 */   public Extra extra = new Extra();
/*    */ 
/*    */   
/*    */   public static class Arms
/*    */   {
/* 16 */     public RightArm rightArm = new RightArm();
/*    */     
/*    */     public enum EnumArm {
/* 19 */       Left, Right;
/*    */     }
/*    */     
/*    */     public enum EnumAction {
/* 23 */       Bolt, Pump, Charge;
/*    */     }
/*    */     
/*    */     public class RightArm {
/* 27 */       public Vector3f armScale = new Vector3f(0.8F, 0.8F, 0.8F);
/*    */       
/* 29 */       public Vector3f armPos = new Vector3f(0.26F, -0.65F, 0.0F);
/* 30 */       public Vector3f armRot = new Vector3f(0.0F, 0.0F, -90.0F);
/*    */       
/* 32 */       public Vector3f armChargePos = new Vector3f(0.47F, -0.39F, 0.14F);
/* 33 */       public Vector3f armChargeRot = new Vector3f(0.0F, 0.0F, -90.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Extra
/*    */   {
/* 42 */     public float modelScale = 1.0F;
/* 43 */     public Vector3f thirdPersonOffset = new Vector3f(0.0F, 0.0F, 0.0F);
/* 44 */     public Vector3f translateAll = new Vector3f(0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\configs\GrenadeRenderConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */