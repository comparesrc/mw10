/*     */ package com.modularwarfare.common.guns.manager;
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.WeaponFireEvent;
/*     */ import com.modularwarfare.api.WeaponHitEvent;
/*     */ import com.modularwarfare.client.ClientProxy;
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*     */ import com.modularwarfare.common.armor.ArmorType;
/*     */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*     */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*     */ import com.modularwarfare.common.entity.EntityExplosiveProjectile;
/*     */ import com.modularwarfare.common.entity.decals.EntityShell;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.common.guns.BulletProperty;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.guns.ItemBullet;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.PotionEntry;
/*     */ import com.modularwarfare.common.guns.WeaponFireMode;
/*     */ import com.modularwarfare.common.guns.WeaponSoundType;
/*     */ import com.modularwarfare.common.guns.WeaponType;
/*     */ import com.modularwarfare.common.handler.ServerTickHandler;
/*     */ import com.modularwarfare.common.hitbox.hits.BulletHit;
/*     */ import com.modularwarfare.common.hitbox.hits.OBBHit;
/*     */ import com.modularwarfare.common.hitbox.hits.PlayerHit;
/*     */ import com.modularwarfare.common.hitbox.maths.EnumHitboxType;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketExpGunFire;
/*     */ import com.modularwarfare.common.network.PacketPlayHitmarker;
/*     */ import com.modularwarfare.utility.RayUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ public class ShotManager {
/*     */   public static void fireClient(EntityPlayer entityPlayer, World world, ItemStack gunStack, ItemGun itemGun, WeaponFireMode fireMode) {
/*  49 */     GunType gunType = itemGun.type;
/*     */     
/*  51 */     if ((ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)entityPlayer)).reloading && 
/*  52 */       gunType.allowReloadFiring) {
/*  53 */       ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)entityPlayer).stopReload();
/*  54 */       ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)entityPlayer).reset();
/*  55 */       ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)entityPlayer).updateCurrentItem((EntityLivingBase)entityPlayer);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  60 */     if (!checkCanFireClient(entityPlayer, world, gunStack, itemGun, fireMode)) {
/*     */       return;
/*     */     }
/*     */     
/*  64 */     int shotCount = (fireMode == WeaponFireMode.BURST) ? ((gunStack.func_77978_p().func_74762_e("shotsremaining") > 0) ? gunStack.func_77978_p().func_74762_e("shotsremaining") : gunType.numBurstRounds) : 1;
/*     */ 
/*     */     
/*  67 */     WeaponFireEvent.PreClient preFireEvent = new WeaponFireEvent.PreClient(entityPlayer, gunStack, itemGun, gunType.weaponMaxRange);
/*  68 */     MinecraftForge.EVENT_BUS.post((Event)preFireEvent);
/*  69 */     if (preFireEvent.isCanceled()) {
/*     */       return;
/*     */     }
/*  72 */     if ((preFireEvent.getResult() == Event.Result.DEFAULT || preFireEvent.getResult() == Event.Result.ALLOW) && 
/*  73 */       !ItemGun.hasNextShot(gunStack)) {
/*  74 */       if (fireMode == WeaponFireMode.BURST) gunStack.func_77978_p().func_74768_a("shotsremaining", 0); 
/*  75 */       if (defemptyclickLock) {
/*     */         
/*  77 */         gunType.playClientSound(entityPlayer, WeaponSoundType.DryFire);
/*  78 */         ModularWarfare.PROXY.onShootFailedAnimation(entityPlayer, gunType.internalName);
/*  79 */         defemptyclickLock = false;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  85 */     ModularWarfare.PROXY.onShootAnimation(entityPlayer, gunType.internalName, gunType.fireTickDelay, itemGun.type.recoilPitch, itemGun.type.recoilYaw);
/*     */ 
/*     */     
/*  88 */     if (GunType.getAttachment(gunStack, AttachmentPresetEnum.Barrel) != null) {
/*  89 */       ItemAttachment barrelAttachment = (ItemAttachment)GunType.getAttachment(gunStack, AttachmentPresetEnum.Barrel).func_77973_b();
/*  90 */       if (barrelAttachment.type.barrel.isSuppressor) {
/*  91 */         gunType.playClientSound(entityPlayer, WeaponSoundType.FireSuppressed);
/*     */       } else {
/*  93 */         gunType.playClientSound(entityPlayer, WeaponSoundType.Fire);
/*     */       } 
/*  95 */     } else if (GunType.isPackAPunched(gunStack)) {
/*  96 */       gunType.playClientSound(entityPlayer, WeaponSoundType.Punched);
/*  97 */       gunType.playClientSound(entityPlayer, WeaponSoundType.Fire);
/*     */     } else {
/*  99 */       gunType.playClientSound(entityPlayer, WeaponSoundType.Fire);
/*     */     } 
/*     */     
/* 102 */     if (gunType.weaponType == WeaponType.BoltSniper || gunType.weaponType == WeaponType.Shotgun) {
/* 103 */       gunType.playClientSound(entityPlayer, WeaponSoundType.Pump);
/*     */     }
/*     */ 
/*     */     
/* 107 */     if (fireMode == WeaponFireMode.BURST) {
/* 108 */       shotCount--;
/* 109 */       gunStack.func_77978_p().func_74768_a("shotsremaining", shotCount);
/*     */     } 
/*     */     
/* 112 */     ClientTickHandler.playerShootCooldown.put(entityPlayer.func_110124_au(), Integer.valueOf(gunType.fireTickDelay));
/*     */ 
/*     */     
/* 115 */     if (gunType.dropBulletCasing) {
/*     */ 
/*     */ 
/*     */       
/* 119 */       int numBullets = gunType.numBullets;
/* 120 */       ItemBullet bulletItem = ItemGun.getUsedBullet(gunStack, gunType);
/* 121 */       if (bulletItem != null && 
/* 122 */         bulletItem.type.isSlug) {
/* 123 */         numBullets = 1;
/*     */       }
/*     */       
/* 126 */       GunEnhancedRenderConfig cfg = (GunEnhancedRenderConfig)ModularWarfare.getRenderConfig((BaseType)gunType, GunEnhancedRenderConfig.class);
/*     */       
/* 128 */       EntityShell shell = new EntityShell(world, entityPlayer, gunStack, itemGun, bulletItem);
/*     */       
/* 130 */       shell.setHeadingFromThrower((Entity)entityPlayer, entityPlayer.field_70125_A + cfg.extra.shellPitchOffset, entityPlayer.field_70177_z + 110.0F + cfg.extra.shellYawOffset, 0.0F, 0.2F, 5.0F, 0.1F + cfg.extra.shellForwardOffset);
/* 131 */       world.func_72838_d((Entity)shell);
/*     */     } 
/*     */     
/* 134 */     ItemGun.consumeShot(gunStack);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     if (gunType.weaponType == WeaponType.Launcher) {
/* 140 */       ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunFire(gunType.internalName, gunType.fireTickDelay, gunType.recoilPitch, gunType.recoilYaw, gunType.recoilAimReducer, gunType.bulletSpread, entityPlayer.field_70125_A, entityPlayer.field_70177_z));
/*     */     } else {
/* 142 */       fireClientSide(entityPlayer, itemGun);
/*     */     } 
/*     */   }
/*     */   public static boolean defemptyclickLock = true;
/*     */   public static boolean checkCanFireClient(EntityPlayer entityPlayer, World world, ItemStack gunStack, ItemGun itemGun, WeaponFireMode fireMode) {
/* 147 */     if (entityPlayer.func_175149_v()) {
/* 148 */       return false;
/*     */     }
/* 150 */     if (itemGun.type.animationType == WeaponAnimationType.BASIC && 
/* 151 */       ItemGun.isClientReloading(entityPlayer)) {
/* 152 */       return false;
/*     */     }
/*     */     
/* 155 */     if (ItemGun.isOnShootCooldown(entityPlayer.func_110124_au()) || 
/* 156 */       (ClientRenderHooks.getAnimMachine((EntityLivingBase)entityPlayer)).attachmentMode || (!itemGun.type.allowSprintFiring && entityPlayer
/* 157 */       .func_70051_ag()) || 
/* 158 */       !itemGun.type.hasFireMode(fireMode)) {
/* 159 */       return false;
/*     */     }
/* 161 */     if (ClientProxy.gunEnhancedRenderer.getController((EntityLivingBase)entityPlayer, null) != null && 
/* 162 */       !ClientProxy.gunEnhancedRenderer.getController((EntityLivingBase)entityPlayer, null).isCouldShoot()) {
/* 163 */       return false;
/*     */     }
/*     */     
/* 166 */     return true;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static void fireServer(EntityPlayer entityPlayer, float rotationPitch, float rotationYaw, World world, ItemStack gunStack, ItemGun itemGun, WeaponFireMode fireMode, int clientFireTickDelay, float recoilPitch, float recoilYaw, float recoilAimReducer, float bulletSpread) {
/* 171 */     GunType gunType = itemGun.type;
/*     */     
/* 173 */     if (ShotValidation.verifShot(entityPlayer, gunStack, itemGun, fireMode, clientFireTickDelay, recoilPitch, recoilYaw, recoilAimReducer, bulletSpread)) {
/*     */ 
/*     */       
/* 176 */       WeaponFireEvent.PreServer preFireEvent = new WeaponFireEvent.PreServer(entityPlayer, gunStack, itemGun, gunType.weaponMaxRange);
/* 177 */       MinecraftForge.EVENT_BUS.post((Event)preFireEvent);
/* 178 */       if (preFireEvent.isCanceled())
/*     */         return; 
/* 180 */       int shotCount = (fireMode == WeaponFireMode.BURST) ? ((gunStack.func_77978_p().func_74762_e("shotsremaining") > 0) ? gunStack.func_77978_p().func_74762_e("shotsremaining") : gunType.numBurstRounds) : 1;
/*     */       
/* 182 */       if ((preFireEvent.getResult() == Event.Result.DEFAULT || preFireEvent.getResult() == Event.Result.ALLOW) && 
/* 183 */         !ItemGun.hasNextShot(gunStack)) {
/* 184 */         if (ItemGun.canDryFire) {
/* 185 */           gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.DryFire, gunStack);
/* 186 */           ItemGun.canDryFire = false;
/*     */         } 
/* 188 */         if (fireMode == WeaponFireMode.BURST) gunStack.func_77978_p().func_74768_a("shotsremaining", 0);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 194 */       if (GunType.getAttachment(gunStack, AttachmentPresetEnum.Barrel) != null) {
/* 195 */         gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.FireSuppressed, gunStack, entityPlayer);
/* 196 */       } else if (GunType.isPackAPunched(gunStack)) {
/* 197 */         gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.Punched, gunStack, entityPlayer);
/* 198 */         gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.Fire, gunStack, entityPlayer);
/*     */       } else {
/* 200 */         gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.Fire, gunStack, entityPlayer);
/*     */       } 
/* 202 */       List<Entity> entities = new ArrayList<>();
/* 203 */       int numBullets = gunType.numBullets;
/* 204 */       ItemBullet bulletItem = ItemGun.getUsedBullet(gunStack, gunType);
/* 205 */       if (bulletItem != null && 
/* 206 */         bulletItem.type.isSlug) {
/* 207 */         numBullets = 1;
/*     */       }
/*     */ 
/*     */       
/* 211 */       if (gunType.weaponType != WeaponType.Launcher) {
/* 212 */         ArrayList<BulletHit> rayTraceList = new ArrayList<>();
/* 213 */         for (int i = 0; i < numBullets; i++) {
/* 214 */           BulletHit rayTrace = RayUtil.standardEntityRayTrace(Side.SERVER, world, rotationPitch, rotationYaw, (EntityLivingBase)entityPlayer, preFireEvent.getWeaponRange(), itemGun, GunType.isPackAPunched(gunStack));
/* 215 */           rayTraceList.add(rayTrace);
/*     */         } 
/*     */         
/* 218 */         boolean headshot = false;
/* 219 */         for (BulletHit rayTrace : rayTraceList) {
/* 220 */           if (rayTrace instanceof PlayerHit) {
/* 221 */             if (!world.field_72995_K) {
/* 222 */               EntityPlayer victim = ((PlayerHit)rayTrace).getEntity();
/* 223 */               if (victim != null && 
/* 224 */                 !victim.field_70128_L && victim.func_110143_aJ() > 0.0F) {
/* 225 */                 entities.add(victim);
/* 226 */                 gunType.playSoundPos(victim.func_180425_c(), world, WeaponSoundType.Penetration);
/* 227 */                 headshot = ((PlayerHit)rayTrace).hitbox.type.equals(EnumHitboxType.HEAD);
/* 228 */                 if (entityPlayer instanceof EntityPlayerMP) {
/* 229 */                   ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlayHitmarker(headshot), (EntityPlayerMP)entityPlayer);
/* 230 */                   ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlaySound(victim.func_180425_c(), "flyby", 1.0F, 1.0F), (EntityPlayerMP)victim);
/* 231 */                   if (ModConfig.INSTANCE.hud.snap_fade_hit) {
/* 232 */                     ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlayerHit(), (EntityPlayerMP)victim);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             continue;
/*     */           } 
/* 239 */           if (!world.field_72995_K && 
/* 240 */             rayTrace.rayTraceResult != null) {
/* 241 */             if (rayTrace.rayTraceResult.field_72308_g instanceof EntityGrenade) {
/* 242 */               ((EntityGrenade)rayTrace.rayTraceResult.field_72308_g).explode();
/*     */             }
/* 244 */             if (rayTrace.rayTraceResult.field_72308_g instanceof EntityLivingBase) {
/* 245 */               EntityLivingBase victim = (EntityLivingBase)rayTrace.rayTraceResult.field_72308_g;
/* 246 */               if (victim != null) {
/* 247 */                 entities.add(victim);
/* 248 */                 gunType.playSoundPos(victim.func_180425_c(), world, WeaponSoundType.Penetration);
/* 249 */                 headshot = (ItemGun.canEntityGetHeadshot((Entity)victim) && rayTrace.rayTraceResult.field_72307_f.field_72448_b >= (victim.func_180425_c().func_177956_o() + victim.func_70047_e() - 0.15F));
/* 250 */                 if (entityPlayer instanceof EntityPlayerMP)
/* 251 */                   ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlayHitmarker(headshot), (EntityPlayerMP)entityPlayer); 
/*     */               }  continue;
/*     */             } 
/* 254 */             if (rayTrace.rayTraceResult.field_72307_f != null) {
/* 255 */               BlockPos blockPos = rayTrace.rayTraceResult.func_178782_a();
/* 256 */               ItemGun.playImpactSound(world, blockPos, gunType);
/* 257 */               gunType.playSoundPos(blockPos, world, WeaponSoundType.Crack, entityPlayer, 1.0F);
/* 258 */               ItemGun.doHit(rayTrace.rayTraceResult, entityPlayer);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 266 */         WeaponFireEvent.Post postFireEvent = new WeaponFireEvent.Post(entityPlayer, gunStack, itemGun, entities);
/* 267 */         MinecraftForge.EVENT_BUS.post((Event)postFireEvent);
/*     */         
/* 269 */         if (postFireEvent.getAffectedEntities() != null && !postFireEvent.getAffectedEntities().isEmpty()) {
/* 270 */           for (Entity target : postFireEvent.getAffectedEntities()) {
/* 271 */             if (target != null && 
/* 272 */               target != entityPlayer) {
/*     */ 
/*     */               
/* 275 */               WeaponHitEvent.Pre preHitEvent = new WeaponHitEvent.Pre(entityPlayer, gunStack, itemGun, headshot, postFireEvent.getDamage(), target);
/* 276 */               MinecraftForge.EVENT_BUS.post((Event)preHitEvent);
/* 277 */               if (preHitEvent.isCanceled()) {
/*     */                 return;
/*     */               }
/* 280 */               if (headshot) {
/* 281 */                 preHitEvent.setDamage(preHitEvent.getDamage() + gunType.gunDamageHeadshotBonus);
/*     */               }
/*     */               
/* 284 */               if (target instanceof EntityLivingBase) {
/* 285 */                 EntityLivingBase targetELB = (EntityLivingBase)target;
/* 286 */                 if (bulletItem != null && 
/* 287 */                   bulletItem.type != null) {
/* 288 */                   preHitEvent.setDamage(preHitEvent.getDamage() * bulletItem.type.bulletDamageFactor);
/* 289 */                   if (bulletItem.type.bulletProperties != null && 
/* 290 */                     !bulletItem.type.bulletProperties.isEmpty()) {
/* 291 */                     BulletProperty bulletProperty = (bulletItem.type.bulletProperties.get(targetELB.func_70005_c_()) != null) ? (BulletProperty)bulletItem.type.bulletProperties.get(targetELB.func_70005_c_()) : (BulletProperty)bulletItem.type.bulletProperties.get("All");
/* 292 */                     if (bulletProperty.potionEffects != null) {
/* 293 */                       for (PotionEntry potionEntry : bulletProperty.potionEffects) {
/* 294 */                         targetELB.func_70690_d(new PotionEffect(potionEntry.potionEffect.getPotion(), potionEntry.duration, potionEntry.level));
/*     */                       }
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 303 */               if (target instanceof EntityPlayer && 
/* 304 */                 ((PlayerHit)rayTraceList.get(0)).hitbox.type.equals(EnumHitboxType.BODY)) {
/* 305 */                 EntityPlayer player = (EntityPlayer)target;
/* 306 */                 if (player.hasCapability(CapabilityExtra.CAPABILITY, null)) {
/* 307 */                   IExtraItemHandler extraSlots = (IExtraItemHandler)player.getCapability(CapabilityExtra.CAPABILITY, null);
/* 308 */                   ItemStack plate = extraSlots.getStackInSlot(1);
/* 309 */                   if (plate != null && 
/* 310 */                     plate.func_77973_b() instanceof ItemSpecialArmor) {
/* 311 */                     ArmorType armorType = ((ItemSpecialArmor)plate.func_77973_b()).type;
/* 312 */                     float damage = preHitEvent.getDamage();
/* 313 */                     preHitEvent.setDamage((float)(damage - damage * armorType.defense));
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 320 */               if (!ModConfig.INSTANCE.shots.knockback_entity_damage) {
/* 321 */                 RayUtil.attackEntityWithoutKnockback(target, DamageSource.func_76365_a(preFireEvent.getWeaponUser()).func_76349_b(), preHitEvent.getDamage());
/*     */               } else {
/* 323 */                 target.func_70097_a(DamageSource.func_76365_a(preFireEvent.getWeaponUser()).func_76349_b(), preHitEvent.getDamage());
/*     */               } 
/* 325 */               target.field_70172_ad = 0;
/*     */ 
/*     */               
/* 328 */               WeaponHitEvent.Post postHitEvent = new WeaponHitEvent.Post(entityPlayer, gunStack, itemGun, postFireEvent.getAffectedEntities(), preHitEvent.getDamage());
/* 329 */               MinecraftForge.EVENT_BUS.post((Event)postHitEvent);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } else {
/*     */         
/* 335 */         EntityExplosiveProjectile projectile = new EntityExplosiveProjectile(world, entityPlayer, 0.5F, 3.0F, 2.5F, bulletItem.type.internalName);
/* 336 */         world.func_72838_d((Entity)projectile);
/*     */       } 
/*     */ 
/*     */       
/* 340 */       if (fireMode == WeaponFireMode.BURST) {
/* 341 */         shotCount--;
/* 342 */         gunStack.func_77978_p().func_74768_a("shotsremaining", shotCount);
/*     */       } 
/*     */       
/* 345 */       if (preFireEvent.getResult() == Event.Result.DEFAULT || preFireEvent.getResult() == Event.Result.ALLOW) {
/* 346 */         ItemGun.consumeShot(gunStack);
/*     */       }
/*     */ 
/*     */       
/* 350 */       if (ServerTickHandler.playerAimShootCooldown.get(entityPlayer.func_70005_c_()) == null) {
/* 351 */         ModularWarfare.NETWORK.sendToAll((PacketBase)new PacketAimingReponse(entityPlayer.func_70005_c_(), true));
/*     */       }
/* 353 */       ServerTickHandler.playerAimShootCooldown.put(entityPlayer.func_70005_c_(), Integer.valueOf(60));
/*     */     }
/* 355 */     else if (ModConfig.INSTANCE.general.modified_pack_server_kick) {
/* 356 */       ((EntityPlayerMP)entityPlayer).field_71135_a.func_194028_b((ITextComponent)new TextComponentString("[ModularWarfare] Kicked for client-side modified content-pack. (Bad RPM/Recoil for the gun: " + itemGun.type.internalName + ") [RPM should be: " + itemGun.type.roundsPerMin + "]"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fireClientSide(EntityPlayer entityPlayer, ItemGun itemGun) {
/* 363 */     if (entityPlayer.field_70170_p.field_72995_K) {
/* 364 */       List<Entity> entities = new ArrayList<>();
/* 365 */       int numBullets = itemGun.type.numBullets;
/* 366 */       ItemBullet bulletItem = ItemGun.getUsedBullet(entityPlayer.func_184614_ca(), itemGun.type);
/* 367 */       if (bulletItem != null && 
/* 368 */         bulletItem.type.isSlug) {
/* 369 */         numBullets = 1;
/*     */       }
/*     */       
/* 372 */       ArrayList<BulletHit> rayTraceList = new ArrayList<>();
/* 373 */       for (int i = 0; i < numBullets; i++) {
/* 374 */         BulletHit rayTrace = RayUtil.standardEntityRayTrace(Side.CLIENT, entityPlayer.field_70170_p, entityPlayer.field_70125_A, entityPlayer.field_70177_z, (EntityLivingBase)entityPlayer, itemGun.type.weaponMaxRange, itemGun, false);
/* 375 */         rayTraceList.add(rayTrace);
/*     */       } 
/*     */       
/* 378 */       ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketExpShot(entityPlayer.func_145782_y(), itemGun.type.internalName));
/*     */       
/* 380 */       boolean headshot = false;
/* 381 */       for (BulletHit rayTrace : rayTraceList) {
/* 382 */         if (rayTrace instanceof OBBHit) {
/* 383 */           EntityLivingBase victim = ((OBBHit)rayTrace).entity;
/* 384 */           if (victim != null && 
/* 385 */             !victim.field_70128_L && victim.func_110143_aJ() > 0.0F) {
/* 386 */             entities.add(victim);
/*     */ 
/*     */             
/* 389 */             ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketExpGunFire(victim.func_145782_y(), itemGun.type.internalName, ((OBBHit)rayTrace).box.name, itemGun.type.fireTickDelay, itemGun.type.recoilPitch, itemGun.type.recoilYaw, itemGun.type.recoilAimReducer, itemGun.type.bulletSpread, rayTrace.rayTraceResult.field_72307_f.field_72450_a, rayTrace.rayTraceResult.field_72307_f.field_72448_b, rayTrace.rayTraceResult.field_72307_f.field_72449_c));
/*     */           } 
/*     */           continue;
/*     */         } 
/* 393 */         if (rayTrace.rayTraceResult != null && 
/* 394 */           rayTrace.rayTraceResult.field_72307_f != null) {
/* 395 */           if (rayTrace.rayTraceResult.field_72308_g != null) {
/*     */             
/* 397 */             headshot = (ItemGun.canEntityGetHeadshot(rayTrace.rayTraceResult.field_72308_g) && rayTrace.rayTraceResult.field_72307_f.field_72448_b >= (rayTrace.rayTraceResult.field_72308_g.func_180425_c().func_177956_o() + rayTrace.rayTraceResult.field_72308_g.func_70047_e() - 0.15F));
/* 398 */             ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketExpGunFire(rayTrace.rayTraceResult.field_72308_g.func_145782_y(), itemGun.type.internalName, headshot ? "head" : "", itemGun.type.fireTickDelay, itemGun.type.recoilPitch, itemGun.type.recoilYaw, itemGun.type.recoilAimReducer, itemGun.type.bulletSpread, rayTrace.rayTraceResult.field_72307_f.field_72450_a, rayTrace.rayTraceResult.field_72307_f.field_72448_b, rayTrace.rayTraceResult.field_72307_f.field_72449_c));
/*     */             continue;
/*     */           } 
/* 401 */           ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketExpGunFire(-1, itemGun.type.internalName, "", itemGun.type.fireTickDelay, itemGun.type.recoilPitch, itemGun.type.recoilYaw, itemGun.type.recoilAimReducer, itemGun.type.bulletSpread, rayTrace.rayTraceResult.field_72307_f.field_72450_a, rayTrace.rayTraceResult.field_72307_f.field_72448_b, rayTrace.rayTraceResult.field_72307_f.field_72449_c, rayTrace.rayTraceResult.field_178784_b));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\manager\ShotManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */