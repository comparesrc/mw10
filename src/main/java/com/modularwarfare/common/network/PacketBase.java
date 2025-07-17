/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PacketBase
/*    */ {
/*    */   public static void writeUTF(ByteBuf data, String s) {
/* 16 */     ByteBufUtils.writeUTF8String(data, s);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String readUTF(ByteBuf data) {
/* 23 */     return ByteBufUtils.readUTF8String(data);
/*    */   }
/*    */   
/*    */   public abstract void encodeInto(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf);
/*    */   
/*    */   public abstract void decodeInto(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf);
/*    */   
/*    */   public abstract void handleServerSide(EntityPlayerMP paramEntityPlayerMP);
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public abstract void handleClientSide(EntityPlayer paramEntityPlayer);
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */