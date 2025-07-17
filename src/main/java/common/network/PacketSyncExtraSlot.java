/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*    */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*    */ 
/*    */ 
/*    */ public class PacketSyncExtraSlot
/*    */   extends PacketBase
/*    */ {
/*    */   int player;
/*    */   int slot;
/*    */   ItemStack itemStack;
/*    */   
/*    */   public PacketSyncExtraSlot() {}
/*    */   
/*    */   public PacketSyncExtraSlot(EntityPlayer player, int slot, ItemStack backpack) {
/* 26 */     this.player = player.func_145782_y();
/* 27 */     this.slot = slot;
/* 28 */     this.itemStack = backpack;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 33 */     data.writeInt(this.player);
/* 34 */     data.writeInt(this.slot);
/* 35 */     ByteBufUtils.writeItemStack(data, this.itemStack);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 40 */     this.player = data.readInt();
/* 41 */     this.slot = data.readInt();
/* 42 */     this.itemStack = ByteBufUtils.readItemStack(data);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP playerEntity) {}
/*    */ 
/*    */   
/*    */   public void handleClientSide(EntityPlayer clientPlayer) {
/* 51 */     Minecraft.func_71410_x().func_152344_a(() -> {
/*    */           Entity p = (Minecraft.func_71410_x()).field_71441_e.func_73045_a(this.player);
/*    */           if (p != null && p instanceof EntityPlayer)
/*    */             ((IExtraItemHandler)p.getCapability(CapabilityExtra.CAPABILITY, (EnumFacing)null)).setStackInSlot(this.slot, this.itemStack); 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketSyncExtraSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */