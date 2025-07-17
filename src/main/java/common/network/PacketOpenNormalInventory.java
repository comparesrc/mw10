/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.util.IThreadListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketOpenNormalInventory
/*    */   extends PacketBase
/*    */ {
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {}
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {}
/*    */   
/*    */   public void handleServerSide(final EntityPlayerMP entityPlayer) {
/* 20 */     IThreadListener mainThread = (IThreadListener)entityPlayer.field_70170_p;
/* 21 */     mainThread.func_152344_a(new Runnable()
/*    */         {
/*    */           public void run() {
/* 24 */             entityPlayer.field_71070_bA.func_75134_a((EntityPlayer)entityPlayer);
/* 25 */             entityPlayer.field_71070_bA = entityPlayer.field_71069_bz;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketOpenNormalInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */