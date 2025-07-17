/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.common.handler.ServerTickHandler;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketAimingRequest
/*    */   extends PacketBase
/*    */ {
/*    */   public String playername;
/*    */   public boolean aiming;
/*    */   
/*    */   public PacketAimingRequest() {}
/*    */   
/*    */   public PacketAimingRequest(String playername, boolean aiming) {
/* 21 */     this.playername = playername;
/* 22 */     this.aiming = aiming;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 27 */     ByteBufUtils.writeUTF8String(data, this.playername);
/* 28 */     data.writeBoolean(this.aiming);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 33 */     this.playername = ByteBufUtils.readUTF8String(data);
/* 34 */     this.aiming = data.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {
/* 39 */     ServerTickHandler.playerAimInstant.put(this.playername, Boolean.valueOf(this.aiming));
/*    */   }
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketAimingRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */