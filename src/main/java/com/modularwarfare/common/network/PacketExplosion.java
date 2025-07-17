/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.common.init.ModSounds;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class PacketExplosion extends PacketBase {
/*    */   private double posX;
/*    */   private double posY;
/*    */   private double posZ;
/*    */   
/*    */   public PacketExplosion() {}
/*    */   
/*    */   public PacketExplosion(double x, double y, double z) {
/* 23 */     this.posX = x;
/* 24 */     this.posY = y;
/* 25 */     this.posZ = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 30 */     data.writeDouble(this.posX);
/* 31 */     data.writeDouble(this.posY);
/* 32 */     data.writeDouble(this.posZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 37 */     this.posX = data.readDouble();
/* 38 */     this.posY = data.readDouble();
/* 39 */     this.posZ = data.readDouble();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP entityPlayer) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {
/* 50 */     Minecraft.func_71410_x().func_152344_a(() -> {
/*    */           EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*    */           double d0 = entityPlayerSP.func_70011_f(this.posX, this.posY, this.posZ);
/*    */           if (d0 <= 10.0D) {
/*    */             Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_184371_a(ModSounds.EXPLOSIONS_CLOSE, 1.0F));
/*    */           } else if (d0 > 10.0D && d0 <= 20.0D) {
/*    */             Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_184371_a(ModSounds.EXPLOSIONS_DISTANT, 1.0F));
/*    */           } else if (d0 > 20.0D && d0 < 100.0D) {
/*    */             Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_184371_a(ModSounds.EXPLOSIONS_FAR, 1.0F));
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */