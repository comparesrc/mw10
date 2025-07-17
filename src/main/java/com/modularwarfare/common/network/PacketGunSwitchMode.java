/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.common.guns.GunType;
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import com.modularwarfare.common.guns.WeaponFireMode;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketGunSwitchMode
/*    */   extends PacketBase
/*    */ {
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {}
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {}
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {
/* 31 */     if (entityPlayer.func_184614_ca() != null && entityPlayer.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 32 */       ItemGun itemGun = (ItemGun)entityPlayer.func_184614_ca().func_77973_b();
/* 33 */       GunType gunType = itemGun.type;
/* 34 */       WeaponFireMode fireMode = GunType.getFireMode(entityPlayer.func_184614_ca());
/*    */       
/* 36 */       if (fireMode == null || gunType.fireModes.length <= 1) {
/*    */         return;
/*    */       }
/* 39 */       int spot = 0;
/* 40 */       int length = gunType.fireModes.length;
/* 41 */       for (int i = 0; i < length; i++) {
/* 42 */         WeaponFireMode foundFireMode = gunType.fireModes[i];
/* 43 */         if (foundFireMode == fireMode) {
/* 44 */           spot = i;
/*    */         }
/*    */       } 
/* 47 */       spot = (spot + 1 >= length) ? 0 : (spot + 1);
/* 48 */       itemGun.onGunSwitchMode((EntityPlayer)entityPlayer, entityPlayer.field_70170_p, entityPlayer.func_184614_ca(), itemGun, gunType.fireModes[spot]);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*    */ 
/*    */   
/*    */   public static void switchClient(EntityPlayer entityPlayer) {
/* 58 */     if (entityPlayer.func_184614_ca() != null && entityPlayer
/* 59 */       .func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 60 */       ItemGun itemGun = (ItemGun)entityPlayer.func_184614_ca().func_77973_b();
/* 61 */       GunType gunType = itemGun.type;
/* 62 */       WeaponFireMode fireMode = GunType.getFireMode(entityPlayer.func_184614_ca());
/*    */       
/* 64 */       if (fireMode == null || gunType.fireModes.length <= 1) {
/*    */         return;
/*    */       }
/* 67 */       int spot = 0;
/* 68 */       int length = gunType.fireModes.length;
/* 69 */       for (int i = 0; i < length; i++) {
/* 70 */         WeaponFireMode foundFireMode = gunType.fireModes[i];
/* 71 */         if (foundFireMode == fireMode) {
/* 72 */           spot = i;
/*    */         }
/*    */       } 
/* 75 */       spot = (spot + 1 >= length) ? 0 : (spot + 1);
/* 76 */       itemGun.onGunSwitchMode(entityPlayer, entityPlayer.field_70170_p, entityPlayer.func_184614_ca(), itemGun, gunType.fireModes[spot]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketGunSwitchMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */