/*     */ package com.modularwarfare.common.network;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketClientAnimation
/*     */   extends PacketBase
/*     */ {
/*     */   public String wepType;
/*     */   public int fireDelay;
/*     */   public float recoilPitch;
/*     */   public float recoilYaw;
/*     */   public int reloadTime;
/*     */   public int reloadCount;
/*     */   public int reloadType;
/*     */   private AnimationType animType;
/*     */   
/*     */   public PacketClientAnimation() {}
/*     */   
/*     */   public PacketClientAnimation(AnimationType animType, String wepType) {
/*  32 */     this.animType = animType;
/*  33 */     this.wepType = wepType;
/*     */   }
/*     */   public PacketClientAnimation(String wepType, int fireDelay, float recoilPitch, float recoilYaw) {
/*  36 */     this(AnimationType.Shoot, wepType);
/*  37 */     this.fireDelay = fireDelay;
/*  38 */     this.recoilPitch = recoilPitch;
/*  39 */     this.recoilYaw = recoilYaw;
/*     */   }
/*     */   
/*     */   public PacketClientAnimation(String wepType, int reloadTime, int reloadCount, int reloadType) {
/*  43 */     this(AnimationType.Reload, wepType);
/*  44 */     this.reloadTime = reloadTime;
/*  45 */     this.reloadCount = reloadCount;
/*  46 */     this.reloadType = reloadType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  51 */     data.writeByte(this.animType.i);
/*  52 */     writeUTF(data, this.wepType);
/*     */     
/*  54 */     switch (this.animType) {
/*     */       case Reload:
/*  56 */         data.writeInt(this.reloadTime);
/*  57 */         data.writeInt(this.reloadCount);
/*  58 */         data.writeInt(this.reloadType);
/*     */         break;
/*     */       
/*     */       case Shoot:
/*  62 */         data.writeInt(this.fireDelay);
/*  63 */         data.writeFloat(this.recoilPitch);
/*  64 */         data.writeFloat(this.recoilYaw);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  74 */     this.animType = AnimationType.getTypeFromInt(data.readByte());
/*  75 */     this.wepType = readUTF(data);
/*     */     
/*  77 */     switch (this.animType) {
/*     */       case Reload:
/*  79 */         this.reloadTime = data.readInt();
/*  80 */         this.reloadCount = data.readInt();
/*  81 */         this.reloadType = data.readInt();
/*     */         break;
/*     */       
/*     */       case Shoot:
/*  85 */         this.fireDelay = data.readInt();
/*  86 */         this.recoilPitch = data.readFloat();
/*  87 */         this.recoilYaw = data.readFloat();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleServerSide(EntityPlayerMP playerEntity) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleClientSide(EntityPlayer clientPlayer) {
/* 102 */     switch (this.animType) {
/*     */       case Reload:
/* 104 */         ModularWarfare.PROXY.onReloadAnimation(clientPlayer, this.wepType, this.reloadTime, this.reloadCount, this.reloadType);
/*     */         break;
/*     */       
/*     */       case Shoot:
/* 108 */         ModularWarfare.PROXY.onShootAnimation(clientPlayer, this.wepType, this.fireDelay, this.recoilPitch, this.recoilYaw);
/*     */         break;
/*     */       
/*     */       case ShootFailed:
/* 112 */         ModularWarfare.PROXY.onShootFailedAnimation(clientPlayer, this.wepType);
/*     */         break;
/*     */       
/*     */       case ModeChange:
/* 116 */         ModularWarfare.PROXY.onModeChangeAnimation(clientPlayer, this.wepType);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public enum AnimationType
/*     */   {
/* 124 */     Shoot(0), Reload(1), ShootFailed(2), ModeChange(3);
/*     */     
/*     */     public int i;
/*     */     
/*     */     AnimationType(int i) {
/* 129 */       this.i = i;
/*     */     }
/*     */     
/*     */     public static AnimationType getTypeFromInt(int i) {
/* 133 */       switch (i) {
/*     */         case 0:
/* 135 */           return Shoot;
/*     */         case 1:
/* 137 */           return Reload;
/*     */         case 2:
/* 139 */           return ShootFailed;
/*     */         case 3:
/* 141 */           return ModeChange;
/*     */       } 
/* 143 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketClientAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */