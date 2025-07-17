/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.client.ClientProxy;
/*    */ import com.modularwarfare.client.fpp.enhanced.animation.AnimationController;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketCustomAnimation
/*    */   extends PacketBase
/*    */ {
/*    */   public UUID living;
/*    */   public String name;
/*    */   public double startTime;
/*    */   public double endTime;
/*    */   public float speedFactor;
/*    */   public boolean allowReload;
/*    */   public boolean allowFire;
/*    */   
/*    */   public PacketCustomAnimation() {}
/*    */   
/*    */   public PacketCustomAnimation(UUID living, String name, double startTime, double endTime, float speedFactor, boolean allowReload, boolean allowFire) {
/* 45 */     this.living = living;
/* 46 */     this.name = "" + name;
/* 47 */     this.startTime = startTime;
/* 48 */     this.endTime = endTime;
/* 49 */     this.speedFactor = speedFactor;
/* 50 */     this.allowReload = allowReload;
/* 51 */     this.allowFire = allowFire;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 58 */     PacketBuffer buffer = new PacketBuffer(data);
/* 59 */     buffer.func_179252_a(this.living);
/* 60 */     buffer.func_180714_a(this.name);
/* 61 */     buffer.writeDouble(this.startTime);
/* 62 */     buffer.writeDouble(this.endTime);
/* 63 */     buffer.writeFloat(this.speedFactor);
/* 64 */     buffer.writeBoolean(this.allowReload);
/* 65 */     buffer.writeBoolean(this.allowFire);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 70 */     PacketBuffer buffer = new PacketBuffer(data);
/* 71 */     this.living = buffer.func_179253_g();
/* 72 */     this.name = buffer.func_150789_c(32767);
/* 73 */     this.startTime = buffer.readDouble();
/* 74 */     this.endTime = buffer.readDouble();
/* 75 */     this.speedFactor = buffer.readFloat();
/* 76 */     this.allowReload = buffer.readBoolean();
/* 77 */     this.allowFire = buffer.readBoolean();
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
/* 88 */     AnimationController controller = ClientProxy.gunEnhancedRenderer.getController((EntityLivingBase)(Minecraft.func_71410_x()).field_71441_e.func_152378_a(this.living), null);
/* 89 */     controller.CUSTOM = 0.0D;
/* 90 */     controller.customAnimation = this.name;
/* 91 */     controller.startTime = this.startTime;
/* 92 */     controller.endTime = this.endTime;
/* 93 */     controller.customAnimationSpeed = this.speedFactor;
/* 94 */     controller.customAnimationReload = this.allowReload;
/* 95 */     controller.customAnimationFire = this.allowFire;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketCustomAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */