/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class WeaponAttachmentEvent
/*    */   extends Event {
/*    */   public final EntityPlayer player;
/*    */   public final ItemStack gun;
/*    */   
/*    */   public WeaponAttachmentEvent(EntityPlayer player, ItemStack gun) {
/* 15 */     this.player = player;
/* 16 */     this.gun = gun;
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class Load extends WeaponAttachmentEvent {
/*    */     public ItemStack attach;
/*    */     
/*    */     public Load(EntityPlayer player, ItemStack gun, ItemStack attach) {
/* 24 */       super(player, gun);
/* 25 */       this.attach = attach;
/*    */     }
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class Unload extends WeaponAttachmentEvent {
/*    */     public final boolean unloadAll;
/*    */     public final AttachmentPresetEnum type;
/*    */     
/*    */     public Unload(EntityPlayer player, ItemStack gun, AttachmentPresetEnum type, boolean unloadAll) {
/* 35 */       super(player, gun);
/* 36 */       this.type = type;
/* 37 */       this.unloadAll = unloadAll;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\WeaponAttachmentEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */