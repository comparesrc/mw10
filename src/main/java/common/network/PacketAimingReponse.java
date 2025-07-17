/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.api.AnimationUtils;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*    */ 
/*    */ 
/*    */ public class PacketAimingReponse
/*    */   extends PacketBase
/*    */ {
/*    */   public String playername;
/*    */   public boolean aiming;
/*    */   
/*    */   public PacketAimingReponse() {}
/*    */   
/*    */   public PacketAimingReponse(String playername, boolean aiming) {
/* 20 */     this.playername = playername;
/* 21 */     this.aiming = aiming;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 26 */     ByteBufUtils.writeUTF8String(data, this.playername);
/* 27 */     data.writeBoolean(this.aiming);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 32 */     this.playername = ByteBufUtils.readUTF8String(data);
/* 33 */     this.aiming = data.readBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {}
/*    */ 
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {
/* 42 */     if (this.aiming) {
/* 43 */       AnimationUtils.isAiming.put(this.playername, Boolean.valueOf(this.aiming));
/*    */     } else {
/* 45 */       AnimationUtils.isAiming.remove(this.playername);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketAimingReponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */