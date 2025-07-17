/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModConfig;
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
/*    */ public class PacketPlayerHit
/*    */   extends PacketBase
/*    */ {
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {}
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {}
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {}
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {
/* 33 */     Minecraft minecraft = Minecraft.func_71410_x();
/* 34 */     minecraft.func_152344_a(new Runnable() {
/*    */           public void run() {
/* 36 */             if (ModConfig.INSTANCE.hud.snap_fade_hit && 
/* 37 */               (Minecraft.func_71410_x()).field_71439_g.func_110143_aJ() > 0.0F) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 45 */               GunUI.bulletSnapFade += 0.25F;
/* 46 */               if (GunUI.bulletSnapFade > 0.9F)
/* 47 */                 GunUI.bulletSnapFade = 0.9F; 
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketPlayerHit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */