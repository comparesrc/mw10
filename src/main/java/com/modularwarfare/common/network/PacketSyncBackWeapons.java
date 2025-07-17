/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketSyncBackWeapons
/*    */   extends PacketBase
/*    */ {
/* 15 */   private NBTTagCompound tag = BackWeaponsManager.INSTANCE.serializeNBT();
/*    */ 
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 20 */     ByteBufUtils.writeTag(data, this.tag);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 25 */     BackWeaponsManager.INSTANCE.deserializeNBT(ByteBufUtils.readTag(data));
/*    */   }
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP playerEntity) {}
/*    */   
/*    */   public void handleClientSide(EntityPlayer clientPlayer) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketSyncBackWeapons.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */