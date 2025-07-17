/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.ClientProxy;
/*    */ import com.modularwarfare.client.ClientRenderHooks;
/*    */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*    */ import com.modularwarfare.client.fpp.enhanced.models.ModelEnhancedGun;
/*    */ import com.modularwarfare.client.model.ModelGun;
/*    */ import com.modularwarfare.common.guns.GunType;
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import com.modularwarfare.common.guns.WeaponAnimationType;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketOtherPlayerAnimation
/*    */   extends PacketBase
/*    */ {
/*    */   public String playerName;
/*    */   public AnimationType animationType;
/*    */   public String internalname;
/*    */   public int fireTickDelay;
/*    */   public boolean isFailed;
/*    */   
/*    */   public PacketOtherPlayerAnimation() {}
/*    */   
/*    */   public PacketOtherPlayerAnimation(String playerName, AnimationType animationType, String internalname, int fireTickDelay, boolean isFailed) {
/* 34 */     this.playerName = playerName;
/* 35 */     this.animationType = animationType;
/* 36 */     this.internalname = internalname;
/* 37 */     this.fireTickDelay = fireTickDelay;
/* 38 */     this.isFailed = isFailed;
/*    */   }
/*    */   
/*    */   public enum AnimationType {
/* 42 */     FIRE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 47 */     PacketBuffer buffer = new PacketBuffer(data);
/* 48 */     buffer.func_180714_a(this.playerName);
/* 49 */     buffer.func_179249_a(this.animationType);
/* 50 */     buffer.func_180714_a(this.internalname);
/* 51 */     buffer.writeInt(this.fireTickDelay);
/* 52 */     buffer.writeBoolean(this.isFailed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 57 */     PacketBuffer buffer = new PacketBuffer(data);
/* 58 */     this.playerName = buffer.func_150789_c(32767);
/* 59 */     this.animationType = (AnimationType)buffer.func_179257_a(AnimationType.class);
/* 60 */     this.internalname = buffer.func_150789_c(32767);
/* 61 */     this.fireTickDelay = buffer.readInt();
/* 62 */     this.isFailed = buffer.readBoolean();
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
/* 73 */     EntityPlayer player = (Minecraft.func_71410_x()).field_71441_e.func_72924_a(this.playerName);
/* 74 */     if (player == null) {
/*    */       return;
/*    */     }
/* 77 */     if (this.animationType == AnimationType.FIRE && 
/* 78 */       player != null) {
/* 79 */       GunType gunType = ((ItemGun)ModularWarfare.gunTypes.get(this.internalname)).type;
/* 80 */       if (gunType != null)
/* 81 */         if (gunType.animationType == WeaponAnimationType.BASIC) {
/* 82 */           ClientRenderHooks.getAnimMachine((EntityLivingBase)player).triggerShoot((ModelGun)gunType.model, gunType, this.fireTickDelay);
/*    */         } else {
/*    */           
/* 85 */           ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)player).triggerShoot(ClientProxy.gunEnhancedRenderer
/* 86 */               .getController((EntityLivingBase)player, (GunEnhancedRenderConfig)gunType.enhancedModel.config), (ModelEnhancedGun)gunType.enhancedModel, gunType, this.fireTickDelay, this.isFailed);
/*    */         }  
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketOtherPlayerAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */