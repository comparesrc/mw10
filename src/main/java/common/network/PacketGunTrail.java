/*     */ package com.modularwarfare.common.network;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.client.model.InstantBulletRenderer;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.vector.Vector3f;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketGunTrail
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
/*     */   public PacketGunTrail() {}
/*     */   
/*     */   public PacketGunTrail(GunType gunType, String model, String tex, boolean glow, double X, double Y, double Z, double motionX, double motionZ, double x, double y, double z, double range, float bulletspeed, boolean isPunched) {
/*  38 */     this(gunType.internalName, model, tex, glow, X, Y, Z, motionX, motionZ, x, y, z, range, bulletspeed, isPunched);
/*     */   }
/*     */   
/*     */   public PacketGunTrail(String gunType, String model, String tex, boolean glow, double X, double Y, double Z, double motionX, double motionZ, double x, double y, double z, double range, float bulletspeed, boolean isPunched) {
/*  42 */     this.posX = X;
/*  43 */     this.posY = Y;
/*  44 */     this.posZ = Z;
/*     */     
/*  46 */     this.motionX = motionX;
/*  47 */     this.motionZ = motionZ;
/*     */     
/*  49 */     this.dirX = x;
/*  50 */     this.dirY = y;
/*  51 */     this.dirZ = z;
/*  52 */     this.range = range;
/*  53 */     this.bulletspeed = bulletspeed;
/*  54 */     this.isPunched = isPunched;
/*  55 */     this.gunType = gunType;
/*  56 */     this.model = model;
/*  57 */     this.tex = tex;
/*  58 */     this.glow = glow;
/*  59 */     if (this.model == null) {
/*  60 */       this.model = "";
/*     */     }
/*  62 */     if (this.tex == null) {
/*  63 */       this.tex = "";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  69 */     PacketBuffer buf = new PacketBuffer(data);
/*  70 */     buf.writeDouble(this.posX);
/*  71 */     buf.writeDouble(this.posY);
/*  72 */     buf.writeDouble(this.posZ);
/*     */     
/*  74 */     buf.writeDouble(this.motionX);
/*  75 */     buf.writeDouble(this.motionZ);
/*     */     
/*  77 */     buf.writeDouble(this.dirX);
/*  78 */     buf.writeDouble(this.dirY);
/*  79 */     buf.writeDouble(this.dirZ);
/*     */     
/*  81 */     buf.writeDouble(this.range);
/*  82 */     buf.writeFloat(this.bulletspeed);
/*  83 */     buf.writeBoolean(this.isPunched);
/*     */     
/*  85 */     buf.func_180714_a(this.gunType);
/*  86 */     buf.func_180714_a(this.model);
/*  87 */     buf.func_180714_a(this.tex);
/*  88 */     buf.writeBoolean(this.glow);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  93 */     PacketBuffer buf = new PacketBuffer(data);
/*  94 */     this.posX = buf.readDouble();
/*  95 */     this.posY = buf.readDouble();
/*  96 */     this.posZ = buf.readDouble();
/*     */     
/*  98 */     this.motionX = buf.readDouble();
/*  99 */     this.motionZ = buf.readDouble();
/*     */     
/* 101 */     this.dirX = buf.readDouble();
/* 102 */     this.dirY = buf.readDouble();
/* 103 */     this.dirZ = buf.readDouble();
/*     */     
/* 105 */     this.range = buf.readDouble();
/* 106 */     this.bulletspeed = buf.readFloat();
/* 107 */     this.isPunched = buf.readBoolean();
/*     */     
/* 109 */     this.gunType = buf.func_150789_c(32767);
/* 110 */     this.model = buf.func_150789_c(32767);
/* 111 */     this.tex = buf.func_150789_c(32767);
/* 112 */     this.glow = buf.readBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleServerSide(EntityPlayerMP entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleClientSide(EntityPlayer entityPlayer) {
/* 123 */     double dx = this.dirX * this.range;
/* 124 */     double dy = this.dirY * this.range;
/* 125 */     double dz = this.dirZ * this.range;
/* 126 */     Vector3f vec = new Vector3f((float)this.posX, (float)this.posY, (float)this.posZ);
/* 127 */     InstantBulletRenderer.AddTrail(new InstantBulletRenderer.InstantShotTrail(((ItemGun)ModularWarfare.gunTypes.get(this.gunType)).type, this.model, this.tex, this.glow, vec, new Vector3f((float)(vec.x + dx + this.motionX), (float)(vec.y + dy), (float)(vec.z + dz + this.motionZ)), this.bulletspeed, this.isPunched));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketGunTrail.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */