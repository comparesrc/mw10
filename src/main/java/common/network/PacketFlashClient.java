/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.ClientProxy;
/*    */ import com.modularwarfare.client.hud.FlashSystem;
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
/*    */ public class PacketFlashClient
/*    */   extends PacketBase
/*    */ {
/*    */   private int flashAmount;
/*    */   
/*    */   public PacketFlashClient() {}
/*    */   
/*    */   public PacketFlashClient(int givenFlashAmount) {
/* 24 */     this.flashAmount = givenFlashAmount;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 29 */     data.writeInt(this.flashAmount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 34 */     this.flashAmount = data.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {
/* 44 */     FlashSystem.hasTookScreenshot = false;
/* 45 */     FlashSystem.flashValue += this.flashAmount;
/*    */     
/* 47 */     ((ClientProxy)ModularWarfare.PROXY).playFlashSound(entityPlayer);
/*    */     
/* 49 */     if (FlashSystem.flashValue > 255)
/* 50 */       FlashSystem.flashValue = 255; 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketFlashClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */