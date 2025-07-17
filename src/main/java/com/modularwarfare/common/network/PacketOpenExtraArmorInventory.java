/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.util.IThreadListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketOpenExtraArmorInventory
/*    */   extends PacketBase
/*    */ {
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {}
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {}
/*    */   
/*    */   public void handleServerSide(final EntityPlayerMP entityPlayer) {
/* 21 */     IThreadListener mainThread = (IThreadListener)entityPlayer.field_70170_p;
/* 22 */     mainThread.func_152344_a(new Runnable()
/*    */         {
/*    */           public void run() {
/* 25 */             entityPlayer.field_71070_bA.func_75134_a((EntityPlayer)entityPlayer);
/* 26 */             entityPlayer.openGui(ModularWarfare.INSTANCE, 0, entityPlayer.field_70170_p, 0, 0, 0);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketOpenExtraArmorInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */