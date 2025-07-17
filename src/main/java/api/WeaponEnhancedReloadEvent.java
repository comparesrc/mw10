/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class WeaponEnhancedReloadEvent {
/*    */   @Cancelable
/*    */   public static class Unload extends Event {
/*    */     public final EntityPlayerMP player;
/*    */     public final ItemStack gunStack;
/*    */     
/*    */     public Unload(EntityPlayerMP player, ItemStack gunStack) {
/* 15 */       this.player = player;
/* 16 */       this.gunStack = gunStack;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class SearchAmmo
/*    */     extends Event
/*    */   {
/*    */     public final EntityPlayerMP player;
/*    */     public final ItemStack gunStack;
/*    */     public ItemStack ammo;
/*    */     
/*    */     public SearchAmmo(EntityPlayerMP player, ItemStack gunStack, ItemStack ammo) {
/* 28 */       this.player = player;
/* 29 */       this.gunStack = gunStack;
/* 30 */       this.ammo = ammo;
/*    */     }
/*    */     
/*    */     public void setAmmo(ItemStack ammo) {
/* 34 */       this.ammo = ammo;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class SearchBullet extends Event {
/*    */     public final EntityPlayerMP player;
/*    */     public final ItemStack gunStack;
/*    */     public ItemStack bullet;
/*    */     public int count;
/*    */     
/*    */     public SearchBullet(EntityPlayerMP player, ItemStack gunStack, ItemStack bullet, int count) {
/* 45 */       this.player = player;
/* 46 */       this.gunStack = gunStack;
/* 47 */       this.bullet = bullet;
/* 48 */       this.count = count;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class ReloadStopFirst
/*    */     extends Event
/*    */   {
/*    */     public final EntityPlayerMP player;
/*    */     
/*    */     public final ItemStack gunStack;
/*    */     public final ItemStack ammo;
/*    */     public final boolean isCurrentAmmo;
/*    */     public boolean unload;
/*    */     
/*    */     public ReloadStopFirst(EntityPlayerMP player, ItemStack gunStack, ItemStack ammo, boolean isCurrentAmmo, boolean unload) {
/* 64 */       this.player = player;
/* 65 */       this.gunStack = gunStack;
/* 66 */       this.ammo = ammo;
/* 67 */       this.isCurrentAmmo = isCurrentAmmo;
/* 68 */       this.unload = unload;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class ReloadStopSecond
/*    */     extends Event
/*    */   {
/*    */     public final EntityPlayerMP player;
/*    */     public final ItemStack gunStack;
/*    */     public final ItemStack ammo;
/*    */     public final boolean isCurrentAmmo;
/*    */     public boolean result;
/*    */     
/*    */     public ReloadStopSecond(EntityPlayerMP player, ItemStack gunStack, ItemStack ammo, boolean isCurrentAmmo, boolean result) {
/* 83 */       this.player = player;
/* 84 */       this.gunStack = gunStack;
/* 85 */       this.ammo = ammo;
/* 86 */       this.isCurrentAmmo = isCurrentAmmo;
/* 87 */       this.result = result;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\WeaponEnhancedReloadEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */