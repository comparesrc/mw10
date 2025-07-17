/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.fpp.basic.configs.AmmoRenderConfig;
/*    */ import com.modularwarfare.client.model.ModelAmmo;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import com.modularwarfare.loader.MWModelBase;
/*    */ 
/*    */ 
/*    */ public class AmmoType
/*    */   extends BaseType
/*    */ {
/* 13 */   public int ammoCapacity = 30;
/*    */ 
/*    */ 
/*    */   
/* 17 */   public int magazineCount = 1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean allowEmptyMagazines = true;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDynamicAmmo = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public float reloadTimeFactor = 1.0F;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   public float reloadSpeedFactor = 1.0F;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean sameTextureAsGun = true;
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] subAmmo;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void loadExtraValues() {
/* 53 */     if (this.maxStackSize == null) {
/* 54 */       this.maxStackSize = Integer.valueOf(4);
/*    */     }
/* 56 */     loadBaseValues();
/*    */   }
/*    */ 
/*    */   
/*    */   public void reloadModel() {
/* 61 */     if (this.isDynamicAmmo) {
/* 62 */       this.model = (MWModelBase)new ModelAmmo((AmmoRenderConfig)ModularWarfare.getRenderConfig(this, AmmoRenderConfig.class), this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAssetDir() {
/* 68 */     return "ammo";
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\guns\AmmoType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */