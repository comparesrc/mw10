/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.api.WeaponAttachmentEvent;
/*    */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*    */ import com.modularwarfare.common.guns.AttachmentType;
/*    */ import com.modularwarfare.common.guns.GunType;
/*    */ import com.modularwarfare.common.guns.ItemAttachment;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*    */ 
/*    */ public class PacketGunUnloadAttachment
/*    */   extends PacketBase {
/*    */   public String attachmentType;
/*    */   
/*    */   public PacketGunUnloadAttachment(String attachmentType, boolean unloadAll) {
/* 25 */     this.attachmentType = attachmentType;
/* 26 */     this.unloadAll = unloadAll;
/*    */   }
/*    */   public boolean unloadAll;
/*    */   public PacketGunUnloadAttachment() {}
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 31 */     ByteBufUtils.writeUTF8String(data, this.attachmentType);
/* 32 */     data.writeBoolean(this.unloadAll);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 37 */     this.attachmentType = ByteBufUtils.readUTF8String(data);
/* 38 */     this.unloadAll = data.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {
/* 43 */     if (entityPlayer.func_184614_ca() != null && 
/* 44 */       entityPlayer.func_184614_ca().func_77973_b() instanceof com.modularwarfare.common.guns.ItemGun) {
/* 45 */       ItemStack gunStack = entityPlayer.func_184614_ca();
/* 46 */       InventoryPlayer inventory = entityPlayer.field_71071_by;
/* 47 */       WeaponAttachmentEvent.Unload event = new WeaponAttachmentEvent.Unload((EntityPlayer)entityPlayer, gunStack, AttachmentPresetEnum.getAttachment(this.attachmentType), this.unloadAll);
/* 48 */       if (MinecraftForge.EVENT_BUS.post((Event)event)) {
/*    */         return;
/*    */       }
/* 51 */       if (this.unloadAll) {
/* 52 */         for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/* 53 */           ItemStack itemStack = GunType.getAttachment(gunStack, attachment);
/* 54 */           if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/* 55 */             ItemAttachment itemAttachment = (ItemAttachment)itemStack.func_77973_b();
/* 56 */             AttachmentType attachType = itemAttachment.type;
/* 57 */             GunType.removeAttachment(gunStack, attachType.attachmentType);
/* 58 */             inventory.func_70441_a(itemStack);
/* 59 */             ModularWarfare.NETWORK.sendTo(new PacketPlaySound(entityPlayer.func_180425_c(), "attachment.apply", 1.0F, 1.0F), entityPlayer);
/*    */           } 
/*    */         } 
/*    */       } else {
/* 63 */         ItemStack itemStack = GunType.getAttachment(gunStack, AttachmentPresetEnum.getAttachment(this.attachmentType));
/* 64 */         if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/* 65 */           ItemAttachment itemAttachment = (ItemAttachment)itemStack.func_77973_b();
/* 66 */           AttachmentType attachType = itemAttachment.type;
/* 67 */           GunType.removeAttachment(gunStack, attachType.attachmentType);
/* 68 */           inventory.func_70441_a(itemStack);
/* 69 */           ModularWarfare.NETWORK.sendTo(new PacketPlaySound(entityPlayer.func_180425_c(), "attachment.apply", 1.0F, 1.0F), entityPlayer);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketGunUnloadAttachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */