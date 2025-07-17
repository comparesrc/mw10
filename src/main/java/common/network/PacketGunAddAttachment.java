/*    */ package com.modularwarfare.common.network;
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.api.WeaponAttachmentEvent;
/*    */ import com.modularwarfare.common.guns.AttachmentType;
/*    */ import com.modularwarfare.common.guns.GunType;
/*    */ import com.modularwarfare.common.guns.ItemAttachment;
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import com.modularwarfare.common.guns.ItemSpray;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class PacketGunAddAttachment extends PacketBase {
/*    */   public PacketGunAddAttachment(int slot) {
/* 24 */     this.slot = slot;
/*    */   }
/*    */   public int slot;
/*    */   public PacketGunAddAttachment() {}
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 29 */     data.writeInt(this.slot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 34 */     this.slot = data.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {
/* 39 */     if (entityPlayer.func_184614_ca() != null && 
/* 40 */       entityPlayer.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 41 */       ItemStack gunStack = entityPlayer.func_184614_ca();
/* 42 */       ItemGun itemGun = (ItemGun)entityPlayer.func_184614_ca().func_77973_b();
/* 43 */       GunType gunType = itemGun.type;
/* 44 */       InventoryPlayer inventory = entityPlayer.field_71071_by;
/* 45 */       ItemStack attachStack = inventory.func_70301_a(this.slot);
/* 46 */       WeaponAttachmentEvent.Load event = new WeaponAttachmentEvent.Load((EntityPlayer)entityPlayer, gunStack, attachStack);
/* 47 */       if (MinecraftForge.EVENT_BUS.post((Event)event)) {
/*    */         return;
/*    */       }
/* 50 */       attachStack = event.attach;
/* 51 */       if (attachStack != null && !attachStack.func_190926_b()) {
/* 52 */         if (attachStack.func_77973_b() instanceof ItemAttachment) {
/* 53 */           ItemAttachment itemAttachment = (ItemAttachment)attachStack.func_77973_b();
/* 54 */           AttachmentType attachType = itemAttachment.type;
/* 55 */           if (gunType.acceptedAttachments.get(attachType.attachmentType) != null && ((ArrayList)gunType.acceptedAttachments.get(attachType.attachmentType)).size() >= 1 && 
/* 56 */             gunType.acceptedAttachments.containsKey(attachType.attachmentType) && 
/* 57 */             gunType.acceptedAttachments.get(attachType.attachmentType) != null) {
/* 58 */             if (((ArrayList)gunType.acceptedAttachments.get(attachType.attachmentType)).size() >= 1 && (
/* 59 */               (ArrayList)gunType.acceptedAttachments.get(attachType.attachmentType)).contains(attachType.internalName)) {
/* 60 */               ItemStack itemStack = GunType.getAttachment(gunStack, attachType.attachmentType);
/* 61 */               if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/* 62 */                 ItemAttachment localItemAttachment = (ItemAttachment)itemStack.func_77973_b();
/* 63 */                 AttachmentType localAttachType = localItemAttachment.type;
/* 64 */                 GunType.removeAttachment(gunStack, localAttachType.attachmentType);
/* 65 */                 inventory.func_70441_a(itemStack);
/*    */               } 
/*    */             } 
/*    */             
/* 69 */             ItemStack attachmentStack = new ItemStack((Item)itemAttachment);
/* 70 */             NBTTagCompound tag = new NBTTagCompound();
/* 71 */             tag.func_74768_a("skinId", 0);
/* 72 */             attachmentStack.func_77982_d(tag);
/* 73 */             GunType.addAttachment(gunStack, attachType.attachmentType, attachmentStack);
/* 74 */             inventory.func_70301_a(this.slot).func_190918_g(1);
/* 75 */             ModularWarfare.NETWORK.sendTo(new PacketPlaySound(entityPlayer.func_180425_c(), "attachment.apply", 1.0F, 1.0F), entityPlayer);
/*    */           } 
/*    */         } 
/*    */ 
/*    */         
/* 80 */         if (attachStack.func_77973_b() instanceof ItemSpray) {
/* 81 */           ItemSpray spray = (ItemSpray)attachStack.func_77973_b();
/* 82 */           if (gunStack.func_77978_p() != null)
/* 83 */             for (int i = 0; i < gunType.modelSkins.length; i++) {
/* 84 */               if ((gunType.modelSkins[i]).internalName.equalsIgnoreCase(spray.type.skinName)) {
/* 85 */                 NBTTagCompound nbtTagCompound = gunStack.func_77978_p();
/* 86 */                 nbtTagCompound.func_74768_a("skinId", i);
/* 87 */                 gunStack.func_77982_d(nbtTagCompound);
/* 88 */                 inventory.func_70301_a(this.slot).func_77972_a(1, (EntityLivingBase)entityPlayer);
/* 89 */                 if (inventory.func_70301_a(this.slot).func_77958_k() != 0 && inventory.func_70301_a(this.slot).func_77952_i() == inventory.func_70301_a(this.slot).func_77958_k()) {
/* 90 */                   inventory.func_70304_b(this.slot);
/*    */                 }
/* 92 */                 ModularWarfare.NETWORK.sendTo(new PacketPlaySound(entityPlayer.func_180425_c(), "spray", 1.0F, 1.0F), entityPlayer);
/*    */               } 
/*    */             }  
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketGunAddAttachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */