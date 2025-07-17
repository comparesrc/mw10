/*     */ package com.modularwarfare.common.network;
/*     */ 
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.EntityHeadShotEvent;
/*     */ import com.modularwarfare.common.armor.ArmorType;
/*     */ import com.modularwarfare.common.armor.ItemSpecialArmor;
/*     */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*     */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*     */ import com.modularwarfare.common.guns.BulletProperty;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemBullet;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.PotionEntry;
/*     */ import com.modularwarfare.common.guns.WeaponFireMode;
/*     */ import com.modularwarfare.common.guns.WeaponSoundType;
/*     */ import com.modularwarfare.common.guns.manager.ShotValidation;
/*     */ import com.modularwarfare.utility.RayUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*     */ 
/*     */ 
/*     */ public class PacketExpGunFire
/*     */   extends PacketBase
/*     */ {
/*     */   public int entityId;
/*     */   public String internalname;
/*     */   public String hitboxType;
/*     */   public int fireTickDelay;
/*     */   public float recoilPitch;
/*     */   public float recoilYaw;
/*     */   public float recoilAimReducer;
/*     */   public float bulletSpread;
/*     */   private double posX;
/*     */   private double posY;
/*     */   private double posZ;
/*     */   private EnumFacing facing;
/*     */   
/*     */   public PacketExpGunFire() {}
/*     */   
/*     */   public PacketExpGunFire(int entityId, String internalname, String hitboxType, int fireTickDelay, float recoilPitch, float recoilYaw, float recoilAimReducer, float bulletSpread, double x, double y, double z) {
/*  58 */     this(entityId, internalname, hitboxType, fireTickDelay, recoilPitch, recoilYaw, recoilAimReducer, bulletSpread, x, y, z, null);
/*     */   }
/*     */   
/*     */   public PacketExpGunFire(int entityId, String internalname, String hitboxType, int fireTickDelay, float recoilPitch, float recoilYaw, float recoilAimReducer, float bulletSpread, double x, double y, double z, EnumFacing facing) {
/*  62 */     this.entityId = entityId;
/*  63 */     this.internalname = internalname;
/*  64 */     this.hitboxType = hitboxType;
/*     */     
/*  66 */     this.fireTickDelay = fireTickDelay;
/*  67 */     this.recoilPitch = recoilPitch;
/*  68 */     this.recoilYaw = recoilYaw;
/*  69 */     this.recoilAimReducer = recoilAimReducer;
/*  70 */     this.bulletSpread = bulletSpread;
/*     */     
/*  72 */     this.posX = x;
/*  73 */     this.posY = y;
/*  74 */     this.posZ = z;
/*  75 */     this.facing = facing;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  80 */     data.writeInt(this.entityId);
/*  81 */     ByteBufUtils.writeUTF8String(data, this.internalname);
/*  82 */     ByteBufUtils.writeUTF8String(data, this.hitboxType);
/*     */     
/*  84 */     data.writeInt(this.fireTickDelay);
/*  85 */     data.writeFloat(this.recoilPitch);
/*  86 */     data.writeFloat(this.recoilYaw);
/*  87 */     data.writeFloat(this.recoilAimReducer);
/*  88 */     data.writeFloat(this.bulletSpread);
/*     */     
/*  90 */     data.writeDouble(this.posX);
/*  91 */     data.writeDouble(this.posY);
/*  92 */     data.writeDouble(this.posZ);
/*  93 */     if (this.facing == null) {
/*  94 */       data.writeInt(-1);
/*     */     } else {
/*  96 */       data.writeInt(this.facing.ordinal());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 102 */     this.entityId = data.readInt();
/* 103 */     this.internalname = ByteBufUtils.readUTF8String(data);
/* 104 */     this.hitboxType = ByteBufUtils.readUTF8String(data);
/*     */     
/* 106 */     this.fireTickDelay = data.readInt();
/* 107 */     this.recoilPitch = data.readFloat();
/* 108 */     this.recoilYaw = data.readFloat();
/* 109 */     this.recoilAimReducer = data.readFloat();
/* 110 */     this.bulletSpread = data.readFloat();
/*     */     
/* 112 */     this.posX = data.readDouble();
/* 113 */     this.posY = data.readDouble();
/* 114 */     this.posZ = data.readDouble();
/* 115 */     int enumFacing = data.readInt();
/* 116 */     if (enumFacing != -1) {
/* 117 */       this.facing = EnumFacing.values()[enumFacing];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleServerSide(final EntityPlayerMP entityPlayer) {
/* 126 */     WorldServer worldServer = (WorldServer)entityPlayer.field_70170_p;
/* 127 */     worldServer.func_152344_a(new Runnable() {
/*     */           public void run() {
/* 129 */             if (entityPlayer.field_71138_i > 2000) {
/* 130 */               entityPlayer.func_145747_a((ITextComponent)new TextComponentString(TextFormatting.GRAY + "[" + TextFormatting.RED + "ModularWarfare" + TextFormatting.GRAY + "] Your ping is too high, shot not registered."));
/*     */               return;
/*     */             } 
/* 133 */             if (entityPlayer != null && 
/* 134 */               entityPlayer.func_184614_ca() != null && 
/* 135 */               entityPlayer.func_184614_ca().func_77973_b() instanceof ItemGun)
/*     */             {
/* 137 */               if (ModularWarfare.gunTypes.get(PacketExpGunFire.this.internalname) != null) {
/* 138 */                 ItemGun itemGun = (ItemGun)ModularWarfare.gunTypes.get(PacketExpGunFire.this.internalname);
/*     */                 
/* 140 */                 if (entityPlayer.func_184614_ca().func_77973_b() != itemGun) {
/*     */                   return;
/*     */                 }
/*     */                 
/* 144 */                 if (PacketExpGunFire.this.entityId != -1) {
/* 145 */                   Entity target = entityPlayer.field_70170_p.func_73045_a(PacketExpGunFire.this.entityId);
/* 146 */                   WeaponFireMode fireMode = GunType.getFireMode(entityPlayer.func_184614_ca());
/* 147 */                   if (fireMode == null)
/*     */                     return; 
/* 149 */                   IExtraItemHandler extraSlots = null;
/* 150 */                   ItemStack plate = null;
/* 151 */                   if (ShotValidation.verifShot((EntityPlayer)entityPlayer, entityPlayer.func_184614_ca(), itemGun, fireMode, PacketExpGunFire.this.fireTickDelay, PacketExpGunFire.this.recoilPitch, PacketExpGunFire.this.recoilYaw, PacketExpGunFire.this.recoilAimReducer, PacketExpGunFire.this.bulletSpread) && 
/* 152 */                     target != null) {
/* 153 */                     float damage = itemGun.type.gunDamage;
/* 154 */                     if (target instanceof EntityPlayer && PacketExpGunFire.this.hitboxType != null && 
/* 155 */                       PacketExpGunFire.this.hitboxType.contains("body")) {
/* 156 */                       EntityPlayer player = (EntityPlayer)target;
/* 157 */                       if (player.hasCapability(CapabilityExtra.CAPABILITY, null)) {
/* 158 */                         extraSlots = (IExtraItemHandler)player.getCapability(CapabilityExtra.CAPABILITY, null);
/* 159 */                         plate = extraSlots.getStackInSlot(1);
/* 160 */                         if (plate != null && 
/* 161 */                           plate.func_77973_b() instanceof ItemSpecialArmor) {
/* 162 */                           ArmorType armorType = ((ItemSpecialArmor)plate.func_77973_b()).type;
/* 163 */                           damage = (float)(damage - damage * armorType.defense);
/*     */                         } 
/*     */                       } 
/*     */                     } 
/*     */ 
/*     */                     
/* 169 */                     if (target instanceof EntityLivingBase && 
/* 170 */                       PacketExpGunFire.this.hitboxType != null && PacketExpGunFire.this.hitboxType.contains("head")) {
/* 171 */                       EntityHeadShotEvent headShot = new EntityHeadShotEvent((EntityLivingBase)target, (EntityLivingBase)entityPlayer);
/* 172 */                       MinecraftForge.EVENT_BUS.post((Event)headShot);
/*     */                     } 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 178 */                     ItemBullet bulletItem = ItemGun.getUsedBullet(entityPlayer.func_184614_ca(), itemGun.type);
/*     */                     
/* 180 */                     if (target instanceof EntityLivingBase) {
/* 181 */                       EntityLivingBase targetELB = (EntityLivingBase)target;
/* 182 */                       if (bulletItem != null && 
/* 183 */                         bulletItem.type != null && 
/* 184 */                         bulletItem.type.bulletProperties != null && 
/* 185 */                         !bulletItem.type.bulletProperties.isEmpty()) {
/* 186 */                         BulletProperty bulletProperty = (bulletItem.type.bulletProperties.get(targetELB.func_70005_c_()) != null) ? (BulletProperty)bulletItem.type.bulletProperties.get(targetELB.func_70005_c_()) : (BulletProperty)bulletItem.type.bulletProperties.get("All");
/* 187 */                         if (bulletProperty.potionEffects != null) {
/* 188 */                           for (PotionEntry potionEntry : bulletProperty.potionEffects) {
/* 189 */                             targetELB.func_70690_d(new PotionEffect(potionEntry.potionEffect.getPotion(), potionEntry.duration, potionEntry.level));
/*     */                           }
/*     */                         }
/* 192 */                         if (bulletProperty.fireLevel > 0) {
/* 193 */                           targetELB.func_70015_d(bulletProperty.fireLevel);
/*     */                         }
/* 195 */                         if (bulletProperty.explosionLevel > 0.0F) {
/* 196 */                           targetELB.field_70170_p.func_72876_a(null, targetELB.field_70165_t, targetELB.field_70163_u + 1.0D, targetELB.field_70161_v, bulletProperty.explosionLevel, bulletProperty.explosionBroken);
/*     */                         }
/* 198 */                         if (bulletProperty.knockLevel > 0.0F) {
/* 199 */                           targetELB.func_70653_a((Entity)entityPlayer, bulletProperty.knockLevel, entityPlayer.field_70165_t - targetELB.field_70165_t, entityPlayer.field_70161_v - targetELB.field_70161_v);
/*     */                         }
/* 201 */                         if (bulletProperty.banShield && 
/* 202 */                           targetELB instanceof EntityPlayer) {
/*     */                           
/* 204 */                           EntityPlayer ep = (EntityPlayer)targetELB;
/* 205 */                           ItemStack itemstack1 = ep.func_184587_cr() ? ep.func_184607_cu() : ItemStack.field_190927_a;
/*     */                           
/* 207 */                           if (!itemstack1.func_190926_b() && itemstack1.func_77973_b().isShield(itemstack1, (EntityLivingBase)ep)) {
/*     */                             
/* 209 */                             ep.func_184811_cZ().func_185145_a(itemstack1.func_77973_b(), 100);
/* 210 */                             ep.field_70170_p.func_72960_a((Entity)ep, (byte)30);
/*     */                           } 
/*     */                         } 
/*     */                       } 
/*     */                     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 221 */                     damage *= bulletItem.type.bulletDamageFactor;
/*     */ 
/*     */                     
/* 224 */                     boolean flag = false;
/* 225 */                     DamageSource damageSource = DamageSource.func_76365_a((EntityPlayer)entityPlayer).func_76349_b();
/* 226 */                     if (bulletItem.type.isFireDamage) {
/* 227 */                       damageSource.func_76361_j();
/*     */                     }
/* 229 */                     if (bulletItem.type.isAbsoluteDamage) {
/* 230 */                       damageSource.func_151518_m();
/*     */                     }
/* 232 */                     if (bulletItem.type.isBypassesArmorDamage) {
/* 233 */                       damageSource.func_76348_h();
/*     */                     }
/* 235 */                     if (bulletItem.type.isExplosionDamage) {
/* 236 */                       damageSource.func_94540_d();
/*     */                     }
/* 238 */                     if (bulletItem.type.isMagicDamage) {
/* 239 */                       damageSource.func_82726_p();
/*     */                     }
/* 241 */                     if (!ModConfig.INSTANCE.shots.knockback_entity_damage) {
/* 242 */                       flag = RayUtil.attackEntityWithoutKnockback(target, damageSource, PacketExpGunFire.this.hitboxType.contains("head") ? (damage + itemGun.type.gunDamageHeadshotBonus) : damage);
/*     */                     } else {
/* 244 */                       flag = target.func_70097_a(damageSource, PacketExpGunFire.this.hitboxType.contains("head") ? (damage + itemGun.type.gunDamageHeadshotBonus) : damage);
/*     */                     } 
/* 246 */                     target.field_70172_ad = 0;
/* 247 */                     if (flag && 
/* 248 */                       plate != null) {
/* 249 */                       plate.func_96631_a(1, entityPlayer.func_70681_au(), entityPlayer);
/*     */                       
/* 251 */                       if (plate.func_77952_i() >= plate.func_77958_k()) {
/* 252 */                         extraSlots.setStackInSlot(1, ItemStack.field_190927_a);
/*     */                       } else {
/* 254 */                         extraSlots.setStackInSlot(1, plate);
/*     */                       } 
/*     */                     } 
/*     */ 
/*     */                     
/* 259 */                     if (entityPlayer instanceof EntityPlayerMP) {
/* 260 */                       ModularWarfare.NETWORK.sendTo(new PacketPlayHitmarker(PacketExpGunFire.this.hitboxType.contains("head")), entityPlayer);
/* 261 */                       ModularWarfare.NETWORK.sendTo(new PacketPlaySound(target.func_180425_c(), "flyby", 1.0F, 1.0F), (EntityPlayerMP)target);
/*     */                       
/* 263 */                       if (ModConfig.INSTANCE.hud.snap_fade_hit) {
/* 264 */                         ModularWarfare.NETWORK.sendTo(new PacketPlayerHit(), (EntityPlayerMP)target);
/*     */                       }
/*     */                     } 
/*     */                   } 
/*     */                 } else {
/*     */                   
/* 270 */                   BlockPos blockPos = new BlockPos(PacketExpGunFire.this.posX, PacketExpGunFire.this.posY, PacketExpGunFire.this.posZ);
/* 271 */                   ItemGun.playImpactSound(entityPlayer.field_70170_p, blockPos, itemGun.type);
/* 272 */                   itemGun.type.playSoundPos(blockPos, entityPlayer.field_70170_p, WeaponSoundType.Crack, (EntityPlayer)entityPlayer, 1.0F);
/* 273 */                   ItemGun.doHit(PacketExpGunFire.this.posX, PacketExpGunFire.this.posY, PacketExpGunFire.this.posZ, PacketExpGunFire.this.facing, (EntityPlayer)entityPlayer);
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketExpGunFire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */