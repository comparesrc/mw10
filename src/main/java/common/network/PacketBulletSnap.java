/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.client.hud.GunUI;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
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
/*    */ public class PacketBulletSnap
/*    */   extends PacketBase
/*    */ {
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {}
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {}
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {}
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {
/* 32 */     Minecraft minecraft = Minecraft.func_71410_x();
/* 33 */     minecraft.func_152344_a(new Runnable() {
/*    */           public void run() {
/* 35 */             GunUI.bulletSnapFade += 0.25F;
/* 36 */             if (GunUI.bulletSnapFade > 0.8F)
/* 37 */               GunUI.bulletSnapFade = 0.8F; 
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketBulletSnap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */