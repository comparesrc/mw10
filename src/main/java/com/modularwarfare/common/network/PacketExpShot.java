/*     */ package com.modularwarfare.common.network;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.WeaponExpShotEvent;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.WeaponFireMode;
/*     */ import com.modularwarfare.common.guns.WeaponSoundType;
/*     */ import com.modularwarfare.common.handler.ServerTickHandler;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import mchhui.easyeffect.EasyEffect;
/*     */ import mchhui.modularmovements.tactical.server.ServerListener;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*     */ 
/*     */ public class PacketExpShot
/*     */   extends PacketBase {
/*     */   public int entityId;
/*     */   
/*     */   public PacketExpShot(int entityId, String internalname) {
/*  34 */     this.entityId = entityId;
/*  35 */     this.internalname = internalname;
/*     */   }
/*     */   public String internalname;
/*     */   public PacketExpShot() {}
/*     */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  40 */     data.writeInt(this.entityId);
/*  41 */     ByteBufUtils.writeUTF8String(data, this.internalname);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  46 */     this.entityId = data.readInt();
/*  47 */     this.internalname = ByteBufUtils.readUTF8String(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleServerSide(final EntityPlayerMP entityPlayer) {
/*  52 */     WorldServer worldServer = (WorldServer)entityPlayer.field_70170_p;
/*  53 */     worldServer.func_152344_a(new Runnable() {
/*     */           public void run() {
/*  55 */             if (entityPlayer.field_71138_i > 2000) {
/*  56 */               entityPlayer.func_145747_a((ITextComponent)new TextComponentString(TextFormatting.GRAY + "[" + TextFormatting.RED + "ModularWarfare" + TextFormatting.GRAY + "] Your ping is too high, shot not registered."));
/*     */               return;
/*     */             } 
/*  59 */             if (entityPlayer != null && 
/*  60 */               entityPlayer.func_184614_ca() != null && 
/*  61 */               entityPlayer.func_184614_ca().func_77973_b() instanceof ItemGun && 
/*  62 */               ModularWarfare.gunTypes.get(PacketExpShot.this.internalname) != null) {
/*  63 */               ItemGun itemGun = (ItemGun)ModularWarfare.gunTypes.get(PacketExpShot.this.internalname);
/*  64 */               WeaponFireMode fireMode = GunType.getFireMode(entityPlayer.func_184614_ca());
/*  65 */               int shotCount = (fireMode == WeaponFireMode.BURST) ? ((entityPlayer.func_184614_ca().func_77978_p().func_74762_e("shotsremaining") > 0) ? entityPlayer.func_184614_ca().func_77978_p().func_74762_e("shotsremaining") : itemGun.type.numBurstRounds) : 1;
/*     */ 
/*     */               
/*  68 */               if (fireMode == WeaponFireMode.BURST) {
/*  69 */                 shotCount--;
/*  70 */                 entityPlayer.func_184614_ca().func_77978_p().func_74768_a("shotsremaining", shotCount);
/*     */               } 
/*     */               
/*  73 */               ItemGun.consumeShot(entityPlayer.func_184614_ca());
/*  74 */               entityPlayer.func_71111_a(entityPlayer.field_71069_bz, entityPlayer.field_71069_bz.field_75151_b
/*  75 */                   .size() - 1 - 9 + entityPlayer.field_71071_by.field_70461_c, entityPlayer.func_184614_ca());
/*     */ 
/*     */               
/*  78 */               if (GunType.getAttachment(entityPlayer.func_184614_ca(), AttachmentPresetEnum.Barrel) != null) {
/*  79 */                 ItemStack barrel = GunType.getAttachment(entityPlayer.func_184614_ca(), AttachmentPresetEnum.Barrel);
/*  80 */                 if (((ItemAttachment)barrel.func_77973_b()).type.barrel.isSuppressor) {
/*  81 */                   itemGun.type.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.FireSuppressed, entityPlayer.func_184614_ca(), (EntityPlayer)entityPlayer);
/*     */                 }
/*  83 */                 else if (!ItemGun.hasNextShot(entityPlayer.func_184614_ca()) && itemGun.type.weaponSoundMap.containsKey(WeaponSoundType.FireLast)) {
/*  84 */                   itemGun.type.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.FireLast, entityPlayer.func_184614_ca(), (EntityPlayer)entityPlayer);
/*     */                 } else {
/*  86 */                   itemGun.type.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.Fire, entityPlayer.func_184614_ca(), (EntityPlayer)entityPlayer);
/*     */                 }
/*     */               
/*     */               }
/*  90 */               else if (!ItemGun.hasNextShot(entityPlayer.func_184614_ca()) && itemGun.type.weaponSoundMap.containsKey(WeaponSoundType.FireLast)) {
/*  91 */                 itemGun.type.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.FireLast, entityPlayer.func_184614_ca(), (EntityPlayer)entityPlayer);
/*     */               } else {
/*  93 */                 itemGun.type.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.Fire, entityPlayer.func_184614_ca(), (EntityPlayer)entityPlayer);
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/*  98 */               if (ServerTickHandler.playerAimShootCooldown.get(entityPlayer.func_70005_c_()) == null) {
/*  99 */                 ModularWarfare.NETWORK.sendToAll(new PacketAimingReponse(entityPlayer.func_70005_c_(), true));
/*     */               }
/* 101 */               ServerTickHandler.playerAimShootCooldown.put(entityPlayer.func_70005_c_(), Integer.valueOf(60));
/*     */ 
/*     */               
/* 104 */               MinecraftForge.EVENT_BUS.post((Event)new WeaponExpShotEvent((EntityPlayer)entityPlayer));
/* 105 */               ModularWarfare.NETWORK.sendToAll(new PacketOtherPlayerAnimation(entityPlayer.func_70005_c_(), PacketOtherPlayerAnimation.AnimationType.FIRE, PacketExpShot.this.internalname, itemGun.type.fireTickDelay, false));
/* 106 */               Vec3d posSmoke = entityPlayer.func_174824_e(0.0F);
/* 107 */               if (ModularWarfare.isLoadedModularMovements) {
/* 108 */                 posSmoke = ServerListener.onGetPositionEyes((EntityPlayer)entityPlayer, 0.0F, posSmoke);
/*     */               }
/* 110 */               posSmoke = posSmoke.func_178787_e(entityPlayer.func_70040_Z().func_186678_a(0.800000011920929D));
/* 111 */               Vec3d crossVec = (new Vec3d(1.0D, 0.0D, 0.0D)).func_178785_b(-((float)Math.toRadians(entityPlayer.field_70177_z))).func_178789_a((float)Math.toRadians(entityPlayer.field_70125_A));
/*     */               
/* 113 */               for (int i = 0; i < 5; i++) {
/* 114 */                 double rand = Math.random() - 0.5D;
/* 115 */                 Vec3d offsetVec = crossVec.func_186678_a(rand / Math.abs(rand) * 0.5D).func_178787_e(entityPlayer.func_70040_Z().func_186678_a(0.8999999761581421D));
/* 116 */                 EasyEffect.sendEffect(entityPlayer, posSmoke.field_72450_a, posSmoke.field_72448_b - 0.10000000149011612D, posSmoke.field_72449_c, offsetVec.field_72450_a / (i + 1), 1.2000000476837158D, offsetVec.field_72449_c / (i + 1), 0.5D, -1.0D, 0.5D, 200 / (i + 1), 
/*     */                     
/* 118 */                     (int)(10.0D + 20.0D * Math.random()), 20, 5, 
/* 119 */                     Math.random() * 0.30000001192092896D + 0.20000000298023224D, "modularwarfare:textures/particles/fire_smoke.png");
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketExpShot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */