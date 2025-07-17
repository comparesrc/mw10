/*    */ package com.modularwarfare.client.fpp.basic.configs;
/*    */ 
/*    */ import org.lwjgl.util.vector.Vector3f;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttachmentRenderConfig
/*    */ {
/* 10 */   public String modelFileName = "";
/*    */   
/* 12 */   public Extra extra = new Extra();
/*    */   
/* 14 */   public Sight sight = new Sight();
/* 15 */   public Grip grip = new Grip();
/* 16 */   public Stock stock = new Stock();
/*    */   
/*    */   public static class Extra
/*    */   {
/* 20 */     public float modelScale = 1.0F;
/*    */   }
/*    */   
/*    */   public static class Sight {
/* 24 */     public float fovZoom = 3.5F;
/* 25 */     public float mouseSensitivityFactor = 1.0F;
/* 26 */     public float rectileScale = 1.0F;
/*    */     
/* 28 */     public float factorCrossScale = 0.2F;
/* 29 */     public String maskTexture = "default_mask";
/* 30 */     public float maskSize = 0.75F;
/* 31 */     public float uniformMaskRange = 0.1F;
/* 32 */     public float uniformDrawRange = 0.153125F;
/* 33 */     public float uniformStrength = 3.0F;
/* 34 */     public float uniformScaleRangeY = 1.0F;
/* 35 */     public float uniformScaleStrengthY = 1.0F;
/* 36 */     public float uniformVerticality = 0.0F;
/*    */   }
/*    */   
/*    */   public static class Grip {
/* 40 */     public Vector3f leftArmOffset = new Vector3f(0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public static class Stock {
/* 44 */     public float modelRecoilBackwardsFactor = 1.0F;
/* 45 */     public float modelRecoilUpwardsFactor = 1.0F;
/* 46 */     public float modelRecoilShakeFactor = 1.0F;
/*    */   }
/*    */   
/*    */   public void init() {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\configs\AttachmentRenderConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */