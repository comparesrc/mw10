/*    */ package com.modularwarfare.common.grenades;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.fpp.basic.configs.GrenadeRenderConfig;
/*    */ import com.modularwarfare.client.model.ModelGrenade;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import com.modularwarfare.loader.MWModelBase;
/*    */ 
/*    */ public class GrenadeType
/*    */   extends BaseType {
/* 11 */   public GrenadesEnumType grenadeType = GrenadesEnumType.Frag;
/* 12 */   public float fuseTime = 5.0F;
/*    */   public boolean damageWorld = false;
/* 14 */   public int explosionPower = 8;
/*    */ 
/*    */ 
/*    */   
/* 18 */   public float explosionParamA = 30.0F;
/* 19 */   public float explosionParamK = -0.125F;
/* 20 */   public float throwStrength = 1.0F;
/*    */   
/*    */   public boolean throwerVulnerable = false;
/* 23 */   public float smokeTime = 10.0F;
/*    */ 
/*    */   
/*    */   public void loadExtraValues() {
/* 27 */     if (this.maxStackSize == null)
/* 28 */       this.maxStackSize = Integer.valueOf(1); 
/* 29 */     loadBaseValues();
/*    */   }
/*    */ 
/*    */   
/*    */   public void reloadModel() {
/* 34 */     this.model = (MWModelBase)new ModelGrenade((GrenadeRenderConfig)ModularWarfare.getRenderConfig(this, GrenadeRenderConfig.class), this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAssetDir() {
/* 39 */     return "grenades";
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\grenades\GrenadeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */