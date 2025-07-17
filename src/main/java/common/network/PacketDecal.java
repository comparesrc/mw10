/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.common.entity.decals.EntityBulletHole;
/*    */ import com.modularwarfare.common.entity.decals.EntityDecal;
/*    */ import com.modularwarfare.common.particle.EntityShotFX;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.particle.Particle;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketDecal
/*    */   extends PacketBase
/*    */ {
/*    */   private int decalIndex;
/*    */   private int decalSide;
/*    */   private double decalX;
/*    */   private double decalY;
/*    */   private double decalZ;
/*    */   private boolean flag;
/*    */   
/*    */   public PacketDecal() {}
/*    */   
/*    */   public PacketDecal(int decalIndex, EntityDecal.EnumDecalSide side, double x, double y, double z, boolean flag) {
/* 35 */     this.decalIndex = decalIndex;
/* 36 */     this.decalSide = side.getId();
/* 37 */     this.decalX = x;
/* 38 */     this.decalY = y;
/* 39 */     this.decalZ = z;
/* 40 */     this.flag = flag;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 45 */     data.writeInt(this.decalIndex);
/* 46 */     data.writeInt(this.decalSide);
/* 47 */     data.writeDouble(this.decalX);
/* 48 */     data.writeDouble(this.decalY);
/* 49 */     data.writeDouble(this.decalZ);
/* 50 */     data.writeBoolean(this.flag);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 55 */     this.decalIndex = data.readInt();
/* 56 */     this.decalSide = data.readInt();
/* 57 */     this.decalX = data.readDouble();
/* 58 */     this.decalY = data.readDouble();
/* 59 */     this.decalZ = data.readDouble();
/* 60 */     this.flag = data.readBoolean();
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
/*    */     EntityBulletHole entityBulletHole;
/* 72 */     EntityDecal decal = null;
/* 73 */     if (this.decalIndex == 0) {
/* 74 */       entityBulletHole = new EntityBulletHole((World)(Minecraft.func_71410_x()).field_71441_e);
/*    */     }
/*    */     
/* 77 */     if (entityBulletHole != null) {
/* 78 */       entityBulletHole.setSide(EntityDecal.EnumDecalSide.values()[this.decalSide]);
/* 79 */       entityBulletHole.func_70107_b(this.decalX, this.decalY, this.decalZ);
/* 80 */       (Minecraft.func_71410_x()).field_71441_e.func_72838_d((Entity)entityBulletHole);
/*    */       
/* 82 */       for (int i = 0; i < 5; i++) {
/* 83 */         EntityShotFX entityShotFX = new EntityShotFX((World)(Minecraft.func_71410_x()).field_71441_e, this.decalX, this.decalY, this.decalZ, (1.0F * (new Random()).nextFloat()), (1.0F * (new Random()).nextFloat()), (1.0F * (new Random()).nextFloat()), (2.0F * (new Random()).nextFloat()));
/* 84 */         (Minecraft.func_71410_x()).field_71452_i.func_78873_a((Particle)entityShotFX);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketDecal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */