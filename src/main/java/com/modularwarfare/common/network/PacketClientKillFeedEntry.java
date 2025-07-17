/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.ClientProxy;
/*    */ import com.modularwarfare.client.killchat.KillFeedEntry;
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
/*    */ public class PacketClientKillFeedEntry
/*    */   extends PacketBase
/*    */ {
/*    */   public String text;
/*    */   public String weaponInternalName;
/*    */   public int timeLiving;
/*    */   
/*    */   public PacketClientKillFeedEntry() {}
/*    */   
/*    */   public PacketClientKillFeedEntry(String text, int timeLiving, String weaponInternalName) {
/* 27 */     this.text = text;
/* 28 */     this.timeLiving = timeLiving * 20;
/* 29 */     this.weaponInternalName = weaponInternalName;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 34 */     writeUTF(data, this.text);
/* 35 */     writeUTF(data, this.weaponInternalName);
/* 36 */     data.writeInt(this.timeLiving);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 41 */     this.text = readUTF(data);
/* 42 */     this.weaponInternalName = readUTF(data);
/* 43 */     this.timeLiving = data.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP playerEntity) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleClientSide(EntityPlayer clientPlayer) {
/* 53 */     Minecraft.func_71410_x().func_152344_a(() -> ((ClientProxy)ModularWarfare.PROXY).getKillChatManager().add(new KillFeedEntry(this.text, this.timeLiving, this.weaponInternalName)));
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketClientKillFeedEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */