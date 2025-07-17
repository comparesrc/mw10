/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ 
/*    */ public class PacketPlayHitmarker
/*    */   extends PacketBase
/*    */ {
/*    */   public boolean headshot;
/*    */   
/*    */   public PacketPlayHitmarker() {}
/*    */   
/*    */   public PacketPlayHitmarker(boolean headshot) {
/* 17 */     this.headshot = headshot;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 22 */     data.writeBoolean(this.headshot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 27 */     this.headshot = data.readBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {
/* 37 */     ModularWarfare.PROXY.playHitmarker(this.headshot);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketPlayHitmarker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */