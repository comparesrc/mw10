/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class PacketOpenGui
/*    */   extends PacketBase {
/*    */   public int guiID;
/*    */   
/*    */   public PacketOpenGui() {}
/*    */   
/*    */   public PacketOpenGui(int guiID) {
/* 17 */     this.guiID = guiID;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 23 */     data.writeInt(this.guiID);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 28 */     this.guiID = data.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {
/* 33 */     entityPlayer.func_71121_q().func_152344_a(() -> entityPlayer.openGui(ModularWarfare.INSTANCE, 0, (World)entityPlayer.func_71121_q(), 0, 0, 0));
/*    */   }
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketOpenGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */