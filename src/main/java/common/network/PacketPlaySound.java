/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.utility.MWSound;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class PacketPlaySound
/*    */   extends PacketBase
/*    */ {
/*    */   public int posX;
/*    */   public int posY;
/*    */   public int posZ;
/*    */   public String soundName;
/*    */   public float volume;
/*    */   public float pitch;
/*    */   
/*    */   public PacketPlaySound() {}
/*    */   
/*    */   public PacketPlaySound(BlockPos blockPos, String soundName, float volume, float pitch) {
/* 24 */     this.posX = blockPos.func_177958_n();
/* 25 */     this.posY = blockPos.func_177956_o();
/* 26 */     this.posZ = blockPos.func_177952_p();
/* 27 */     this.soundName = soundName;
/* 28 */     this.volume = volume;
/* 29 */     this.pitch = pitch;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 34 */     data.writeInt(this.posX);
/* 35 */     data.writeInt(this.posY);
/* 36 */     data.writeInt(this.posZ);
/* 37 */     writeUTF(data, this.soundName);
/* 38 */     data.writeFloat(this.volume);
/* 39 */     data.writeFloat(this.pitch);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 44 */     this.posX = data.readInt();
/* 45 */     this.posY = data.readInt();
/* 46 */     this.posZ = data.readInt();
/* 47 */     this.soundName = readUTF(data);
/* 48 */     this.volume = data.readFloat();
/* 49 */     this.pitch = data.readFloat();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {
/* 59 */     ModularWarfare.PROXY.playSound(new MWSound(new BlockPos(this.posX, this.posY, this.posZ), this.soundName, this.volume, this.pitch));
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketPlaySound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */