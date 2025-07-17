/*    */ package com.modularwarfare.common.guns.manager;
/*    */ 
/*    */ import com.modularwarfare.common.guns.GunType;
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import com.modularwarfare.common.guns.WeaponAnimationType;
/*    */ import com.modularwarfare.common.guns.WeaponFireMode;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShotValidation
/*    */ {
/*    */   public static boolean isValidShoot(long clientFireTickDelay, float recoilPitch, float recoilYaw, float recoilAimReducer, float bulletSpread, GunType type) {
/* 16 */     return (clientFireTickDelay == type.fireTickDelay && type.recoilPitch == recoilPitch && type.recoilYaw == recoilYaw && type.recoilAimReducer == recoilAimReducer && type.bulletSpread == bulletSpread);
/*    */   }
/*    */   
/*    */   public static boolean verifShot(EntityPlayer entityPlayer, ItemStack gunStack, ItemGun itemGun, WeaponFireMode fireMode, int clientFireTickDelay, float recoilPitch, float recoilYaw, float recoilAimReducer, float bulletSpread) {
/* 20 */     GunType gunType = itemGun.type;
/* 21 */     if (entityPlayer.func_175149_v()) {
/* 22 */       return false;
/*    */     }
/*    */     
/* 25 */     if (isValidShoot(clientFireTickDelay, recoilPitch, recoilYaw, recoilAimReducer, bulletSpread, itemGun.type)) {
/* 26 */       if (itemGun.type.animationType == WeaponAnimationType.BASIC && 
/* 27 */         ItemGun.isServerReloading(entityPlayer)) {
/* 28 */         return false;
/*    */       }
/*    */       
/* 31 */       if ((!itemGun.type.allowSprintFiring && entityPlayer.func_70051_ag()) || !itemGun.type.hasFireMode(fireMode))
/* 32 */         return false; 
/*    */     } 
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\manager\ShotValidation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */