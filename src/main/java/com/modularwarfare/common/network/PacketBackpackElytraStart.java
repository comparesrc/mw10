/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.common.backpacks.BackpackType;
/*    */ import com.modularwarfare.common.backpacks.ItemBackpack;
/*    */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*    */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemElytra;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ 
/*    */ public class PacketBackpackElytraStart
/*    */   extends PacketBase
/*    */ {
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {}
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {}
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP playerEntity) {
/* 37 */     ItemStack itemstack = playerEntity.func_184582_a(EntityEquipmentSlot.CHEST);
/*    */     
/* 39 */     if (itemstack.func_77973_b() == Items.field_185160_cR && ItemElytra.func_185069_d(itemstack))
/*    */     {
/* 41 */       if (itemstack.func_77973_b() == Items.field_185160_cR && ItemElytra.func_185069_d(itemstack)) {
/*    */         return;
/*    */       }
/*    */     }
/*    */     
/* 46 */     if (playerEntity.hasCapability(CapabilityExtra.CAPABILITY, null)) {
/* 47 */       IExtraItemHandler extraSlots = (IExtraItemHandler)playerEntity.getCapability(CapabilityExtra.CAPABILITY, null);
/* 48 */       ItemStack itemstackBackpack = extraSlots.getStackInSlot(0);
/*    */       
/* 50 */       if (!itemstackBackpack.func_190926_b() && 
/* 51 */         itemstackBackpack.func_77973_b() instanceof ItemBackpack) {
/* 52 */         BackpackType backpack = ((ItemBackpack)itemstackBackpack.func_77973_b()).type;
/* 53 */         if (backpack.isElytra)
/* 54 */           if (!playerEntity.field_70122_E && playerEntity.field_70181_x < 0.0D && !playerEntity.func_184613_cA() && !playerEntity.func_70090_H()) {
/*    */             
/* 56 */             playerEntity.func_184847_M();
/*    */           }
/* 58 */           else if (backpack.elytraStoppable) {
/*    */             
/* 60 */             playerEntity.func_189103_N();
/*    */           }  
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void handleClientSide(EntityPlayer clientPlayer) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketBackpackElytraStart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */