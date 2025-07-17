/*     */ package com.modularwarfare.common.network;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketGunTrailAskServer
/*     */   extends PacketBase
/*     */ {
/*     */   double posX;
/*     */   double posY;
/*     */   double posZ;
/*     */   double motionX;
/*     */   double motionZ;
/*     */   double dirX;
/*     */   double dirY;
/*     */   double dirZ;
/*     */   double range;
/*     */   float bulletspeed;
/*     */   boolean isPunched;
/*     */   String gunType;
/*     */   String model;
/*     */   String tex;
/*     */   boolean glow;
/*     */   
/*     */   public PacketGunTrailAskServer() {}
/*     */   
/*     */   public PacketGunTrailAskServer(GunType gunType, String model, String tex, boolean glow, double X, double Y, double Z, double motionX, double motionZ, double x, double y, double z, double range, float bulletspeed, boolean isPunched) {
/*  37 */     this.posX = X;
/*  38 */     this.posY = Y;
/*  39 */     this.posZ = Z;
/*     */     
/*  41 */     this.motionX = motionX;
/*  42 */     this.motionZ = motionZ;
/*     */     
/*  44 */     this.dirX = x;
/*  45 */     this.dirY = y;
/*  46 */     this.dirZ = z;
/*  47 */     this.range = range;
/*  48 */     this.bulletspeed = bulletspeed;
/*  49 */     this.isPunched = isPunched;
/*     */     
/*  51 */     this.gunType = gunType.internalName;
/*  52 */     this.model = model;
/*  53 */     this.tex = tex;
/*  54 */     this.glow = glow;
/*  55 */     if (this.model == null) {
/*  56 */       this.model = "";
/*     */     }
/*  58 */     if (this.tex == null) {
/*  59 */       this.tex = "";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  65 */     PacketBuffer buf = new PacketBuffer(data);
/*     */     
/*  67 */     buf.writeDouble(this.posX);
/*  68 */     buf.writeDouble(this.posY);
/*  69 */     buf.writeDouble(this.posZ);
/*     */     
/*  71 */     buf.writeDouble(this.motionX);
/*  72 */     buf.writeDouble(this.motionZ);
/*     */     
/*  74 */     buf.writeDouble(this.dirX);
/*  75 */     buf.writeDouble(this.dirY);
/*  76 */     buf.writeDouble(this.dirZ);
/*     */     
/*  78 */     buf.writeDouble(this.range);
/*  79 */     buf.writeFloat(this.bulletspeed);
/*  80 */     buf.writeBoolean(this.isPunched);
/*     */     
/*  82 */     buf.func_180714_a(this.gunType);
/*  83 */     buf.func_180714_a(this.model);
/*  84 */     buf.func_180714_a(this.tex);
/*  85 */     buf.writeBoolean(this.glow);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  90 */     PacketBuffer buf = new PacketBuffer(data);
/*     */     
/*  92 */     this.posX = buf.readDouble();
/*  93 */     this.posY = buf.readDouble();
/*  94 */     this.posZ = buf.readDouble();
/*     */     
/*  96 */     this.motionX = buf.readDouble();
/*  97 */     this.motionZ = buf.readDouble();
/*     */     
/*  99 */     this.dirX = buf.readDouble();
/* 100 */     this.dirY = buf.readDouble();
/* 101 */     this.dirZ = buf.readDouble();
/*     */     
/* 103 */     this.range = buf.readDouble();
/* 104 */     this.bulletspeed = buf.readFloat();
/* 105 */     this.isPunched = buf.readBoolean();
/*     */     
/* 107 */     this.gunType = buf.func_150789_c(32767);
/* 108 */     this.model = buf.func_150789_c(32767);
/* 109 */     this.tex = buf.func_150789_c(32767);
/* 110 */     this.glow = buf.readBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleServerSide(EntityPlayerMP entityPlayer) {
/* 115 */     ModularWarfare.NETWORK.sendToDimension(new PacketGunTrail(this.gunType, this.model, this.tex, this.glow, this.posX, this.posY, this.posZ, this.motionX, this.motionZ, this.dirX, this.dirY, this.dirZ, this.range, 10.0F, this.isPunched), entityPlayer.field_70170_p.field_73011_w.getDimension());
/*     */   }
/*     */   
/*     */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketGunTrailAskServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */