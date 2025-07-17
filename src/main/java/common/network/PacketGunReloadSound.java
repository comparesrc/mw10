/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.common.guns.GunType;
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import com.modularwarfare.common.guns.WeaponSoundType;
/*    */ import com.modularwarfare.common.handler.ServerTickHandler;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*    */ 
/*    */ public class PacketGunReloadSound
/*    */   extends PacketBase {
/*    */   public WeaponSoundType soundType;
/*    */   
/*    */   public PacketGunReloadSound() {}
/*    */   
/*    */   public PacketGunReloadSound(WeaponSoundType soundType) {
/* 23 */     this.soundType = soundType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 28 */     ByteBufUtils.writeUTF8String(data, this.soundType.eventName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 33 */     this.soundType = WeaponSoundType.fromString(ByteBufUtils.readUTF8String(data));
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {
/* 38 */     if (entityPlayer.func_184614_ca() != null && 
/* 39 */       entityPlayer.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 40 */       ItemStack gunStack = entityPlayer.func_184614_ca();
/* 41 */       ItemGun itemGun = (ItemGun)entityPlayer.func_184614_ca().func_77973_b();
/* 42 */       GunType gunType = itemGun.type;
/* 43 */       InventoryPlayer inventory = entityPlayer.field_71071_by;
/*    */       
/* 45 */       if (!ServerTickHandler.playerReloadCooldown.containsKey(entityPlayer.func_110124_au()));
/*    */ 
/*    */ 
/*    */       
/* 49 */       if (this.soundType == null) {
/*    */         return;
/*    */       }
/* 52 */       gunType.playSound((EntityLivingBase)entityPlayer, this.soundType, gunStack);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketGunReloadSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */