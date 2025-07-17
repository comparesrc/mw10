/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class WeaponEvent
/*    */   extends Event {
/*    */   private final EntityPlayer entityPlayer;
/*    */   private final ItemStack stackWeapon;
/*    */   private final ItemGun itemWeapon;
/*    */   
/*    */   public WeaponEvent(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon) {
/* 15 */     this.entityPlayer = entityPlayer;
/* 16 */     this.stackWeapon = stackWeapon;
/* 17 */     this.itemWeapon = itemWeapon;
/*    */   }
/*    */   
/*    */   public EntityPlayer getWeaponUser() {
/* 21 */     return this.entityPlayer;
/*    */   }
/*    */   
/*    */   public ItemStack getWeaponStack() {
/* 25 */     return this.stackWeapon;
/*    */   }
/*    */   
/*    */   public ItemGun getWeaponItem() {
/* 29 */     return this.itemWeapon;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\api\WeaponEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */