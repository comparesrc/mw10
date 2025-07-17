/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ 
/*    */ public class PacketParticle
/*    */   extends PacketBase {
/*    */   public ParticleType particleType;
/*    */   public double posX;
/*    */   public double posY;
/*    */   public double posZ;
/*    */   
/*    */   public enum ParticleType {
/* 17 */     UNKOWN, EXPLOSION, ROCKET;
/*    */   }
/*    */ 
/*    */   
/*    */   public PacketParticle() {}
/*    */ 
/*    */   
/*    */   public PacketParticle(ParticleType particleType, double posX, double posY, double posZ) {
/* 25 */     this.particleType = particleType;
/* 26 */     if (this.particleType == null) {
/* 27 */       this.particleType = ParticleType.UNKOWN;
/*    */     }
/* 29 */     this.posX = posX;
/* 30 */     this.posY = posY;
/* 31 */     this.posZ = posZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 36 */     data.writeInt(this.particleType.ordinal());
/* 37 */     data.writeDouble(this.posX);
/* 38 */     data.writeDouble(this.posY);
/* 39 */     data.writeDouble(this.posZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 44 */     this.particleType = ParticleType.values()[data.readInt()];
/* 45 */     this.posX = data.readDouble();
/* 46 */     this.posY = data.readDouble();
/* 47 */     this.posZ = data.readDouble();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP playerEntity) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleClientSide(EntityPlayer clientPlayer) {
/* 58 */     if (this.particleType == ParticleType.EXPLOSION) {
/* 59 */       ModularWarfare.PROXY.spawnExplosionParticle(clientPlayer.field_70170_p, this.posX, this.posY, this.posZ);
/* 60 */     } else if (this.particleType == ParticleType.ROCKET) {
/* 61 */       ModularWarfare.PROXY.spawnRocketParticle(clientPlayer.field_70170_p, this.posX, this.posY, this.posZ);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketParticle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */