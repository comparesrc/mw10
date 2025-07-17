/*    */ package com.modularwarfare.common.handler.data;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class DataGunReloadEnhancedTask
/*    */ {
/*    */   public int gunSlot;
/*    */   public ItemStack reloadGun;
/*    */   public ItemStack prognosisAmmo;
/*    */   public int reloadCount;
/*    */   public boolean currentAmmo;
/*    */   public boolean multi;
/*    */   public Integer multiMagToLoad;
/*    */   public boolean isUnload;
/*    */   
/*    */   public DataGunReloadEnhancedTask() {}
/*    */   
/*    */   public DataGunReloadEnhancedTask(int gunSlot, ItemStack reloadGun, boolean isUnload) {
/* 20 */     this.gunSlot = gunSlot;
/* 21 */     this.reloadGun = reloadGun;
/* 22 */     this.isUnload = isUnload;
/*    */   }
/*    */   
/*    */   public DataGunReloadEnhancedTask(int gunSlot, ItemStack reloadGun, boolean isUnload, int reloadCount) {
/* 26 */     this.gunSlot = gunSlot;
/* 27 */     this.reloadGun = reloadGun;
/* 28 */     this.isUnload = isUnload;
/* 29 */     this.reloadCount = reloadCount;
/*    */   }
/*    */ 
/*    */   
/*    */   public DataGunReloadEnhancedTask(int gunSlot, ItemStack reloadGun, ItemStack prognosisAmmo, int reloadCount, boolean currentAmmo, boolean multi, Integer multiMagToLoad) {
/* 34 */     this.gunSlot = gunSlot;
/* 35 */     this.reloadGun = reloadGun;
/* 36 */     this.prognosisAmmo = prognosisAmmo;
/* 37 */     this.reloadCount = reloadCount;
/* 38 */     this.currentAmmo = currentAmmo;
/* 39 */     this.multi = multi;
/* 40 */     this.multiMagToLoad = multiMagToLoad;
/*    */   }
/*    */   
/*    */   public DataGunReloadEnhancedTask(int gunSlot, ItemStack reloadGun, ItemStack prognosisAmmo, int reloadCount) {
/* 44 */     this(gunSlot, reloadGun, prognosisAmmo, reloadCount, false, false, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\handler\data\DataGunReloadEnhancedTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */