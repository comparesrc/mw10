/*     */ package com.modularwarfare.common.guns.manager;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.WeaponFireEvent;
/*     */ import com.modularwarfare.api.WeaponHitEvent;
/*     */ import com.modularwarfare.client.ClientProxy;
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*     */ import com.modularwarfare.client.handler.ClientTickHandler;
/*     */ import com.modularwarfare.common.armor.ArmorType;
/*     */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*     */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*     */ import com.modularwarfare.common.entity.EntityExplosiveProjectile;
/*     */ import com.modularwarfare.common.entity.decals.EntityShell;
/*     */ import com.modularwarfare.common.entity.grenades.EntityGrenade;
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
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import com.modularwarfare.utility.RayUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ public class ShotManager {
/*     */   public static void fireClient(EntityPlayer entityPlayer, World world, ItemStack gunStack, ItemGun itemGun, WeaponFireMode fireMode) {
/*  60 */     GunType gunType = itemGun.type;
/*     */     
/*  62 */     if ((ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)entityPlayer)).reloading && 
/*  63 */       gunType.allowReloadFiring) {
/*  64 */       ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)entityPlayer).stopReload();
/*  65 */       ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)entityPlayer).reset();
/*  66 */       ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)entityPlayer).updateCurrentItem((EntityLivingBase)entityPlayer);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  71 */     if (!checkCanFireClient(entityPlayer, world, gunStack, itemGun, fireMode)) {
/*     */       return;
/*     */     }
/*     */     
/*  75 */     int shotCount = (fireMode == WeaponFireMode.BURST) ? ((gunStack.func_77978_p().func_74762_e("shotsremaining") > 0) ? gunStack.func_77978_p().func_74762_e("shotsremaining") : gunType.numBurstRounds) : 1;
/*     */ 
/*     */     
/*  78 */     WeaponFireEvent.PreClient preFireEvent = new WeaponFireEvent.PreClient(entityPlayer, gunStack, itemGun, gunType.weaponMaxRange);
/*  79 */     MinecraftForge.EVENT_BUS.post((Event)preFireEvent);
/*  80 */     if (preFireEvent.isCanceled()) {
/*     */       return;
/*     */     }
/*  83 */     if ((preFireEvent.getResult() == Event.Result.DEFAULT || preFireEvent.getResult() == Event.Result.ALLOW) && 
/*  84 */       !ItemGun.hasNextShot(gunStack)) {
/*  85 */       if (fireMode == WeaponFireMode.BURST) gunStack.func_77978_p().func_74768_a("shotsremaining", 0); 
/*  86 */       if (defemptyclickLock) {
/*     */         
/*  88 */         gunType.playClientSound(entityPlayer, WeaponSoundType.DryFire);
/*  89 */         ModularWarfare.PROXY.onShootFailedAnimation(entityPlayer, gunType.internalName);
/*  90 */         defemptyclickLock = false;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  96 */     ModularWarfare.PROXY.onShootAnimation(entityPlayer, gunType.internalName, gunType.fireTickDelay, itemGun.type.recoilPitch, itemGun.type.recoilYaw);
/*     */ 
/*     */     
/*  99 */     if (GunType.getAttachment(gunStack, AttachmentPresetEnum.Barrel) != null) {
/* 100 */       ItemAttachment barrelAttachment = (ItemAttachment)GunType.getAttachment(gunStack, AttachmentPresetEnum.Barrel).func_77973_b();
/* 101 */       if (barrelAttachment.type.barrel.isSuppressor) {
/* 102 */         gunType.playClientSound(entityPlayer, WeaponSoundType.FireSuppressed);
/*     */       } else {
/* 104 */         gunType.playClientSound(entityPlayer, WeaponSoundType.Fire);
/*     */       } 
/* 106 */     } else if (GunType.isPackAPunched(gunStack)) {
/* 107 */       gunType.playClientSound(entityPlayer, WeaponSoundType.Punched);
/* 108 */       gunType.playClientSound(entityPlayer, WeaponSoundType.Fire);
/*     */     } else {
/* 110 */       gunType.playClientSound(entityPlayer, WeaponSoundType.Fire);
/*     */     } 
/*     */     
/* 113 */     if (gunType.weaponType == WeaponType.BoltSniper || gunType.weaponType == WeaponType.Shotgun) {
/* 114 */       gunType.playClientSound(entityPlayer, WeaponSoundType.Pump);
/*     */     }
/*     */ 
/*     */     
/* 118 */     if (fireMode == WeaponFireMode.BURST) {
/* 119 */       shotCount--;
/* 120 */       gunStack.func_77978_p().func_74768_a("shotsremaining", shotCount);
/*     */     } 
/*     */     
/* 123 */     ClientTickHandler.playerShootCooldown.put(entityPlayer.func_110124_au(), Integer.valueOf(gunType.fireTickDelay));
/*     */ 
/*     */     
/* 126 */     if (gunType.dropBulletCasing) {
/*     */ 
/*     */ 
/*     */       
/* 130 */       int numBullets = gunType.numBullets;
/* 131 */       ItemBullet bulletItem = ItemGun.getUsedBullet(gunStack, gunType);
/* 132 */       if (bulletItem != null && 
/* 133 */         bulletItem.type.isSlug) {
/* 134 */         numBullets = 1;
/*     */       }
/*     */       
/* 137 */       GunEnhancedRenderConfig cfg = (GunEnhancedRenderConfig)ModularWarfare.getRenderConfig((BaseType)gunType, GunEnhancedRenderConfig.class);
/*     */       
/* 139 */       EntityShell shell = new EntityShell(world, entityPlayer, gunStack, itemGun, bulletItem);
/*     */       
/* 141 */       shell.setHeadingFromThrower((Entity)entityPlayer, entityPlayer.field_70125_A + cfg.extra.shellPitchOffset, entityPlayer.field_70177_z + 110.0F + cfg.extra.shellYawOffset, 0.0F, 0.2F, 5.0F, 0.1F + cfg.extra.shellForwardOffset);
/* 142 */       world.func_72838_d((Entity)shell);
/*     */     } 
/*     */     
/* 145 */     ItemGun.consumeShot(gunStack);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     if (gunType.weaponType == WeaponType.Launcher) {
/* 151 */       ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunFire(gunType.internalName, gunType.fireTickDelay, gunType.recoilPitch, gunType.recoilYaw, gunType.recoilAimReducer, gunType.bulletSpread, entityPlayer.field_70125_A, entityPlayer.field_70177_z));
/*     */     } else {
/* 153 */       fireClientSide(entityPlayer, itemGun);
/*     */     } 
/*     */   }
/*     */   public static boolean defemptyclickLock = true;
/*     */   public static boolean checkCanFireClient(EntityPlayer entityPlayer, World world, ItemStack gunStack, ItemGun itemGun, WeaponFireMode fireMode) {
/* 158 */     if (entityPlayer.func_175149_v()) {
/* 159 */       return false;
/*     */     }
/* 161 */     if (itemGun.type.animationType == WeaponAnimationType.BASIC && 
/* 162 */       ItemGun.isClientReloading(entityPlayer)) {
/* 163 */       return false;
/*     */     }
/*     */     
/* 166 */     if (ItemGun.isOnShootCooldown(entityPlayer.func_110124_au()) || 
/* 167 */       (ClientRenderHooks.getAnimMachine((EntityLivingBase)entityPlayer)).attachmentMode || (!itemGun.type.allowSprintFiring && entityPlayer
/* 168 */       .func_70051_ag()) || 
/* 169 */       !itemGun.type.hasFireMode(fireMode)) {
/* 170 */       return false;
/*     */     }
/* 172 */     if (ClientProxy.gunEnhancedRenderer.getController((EntityLivingBase)entityPlayer, null) != null && 
/* 173 */       !ClientProxy.gunEnhancedRenderer.getController((EntityLivingBase)entityPlayer, null).isCouldShoot()) {
/* 174 */       return false;
/*     */     }
/*     */     
/* 177 */     return true;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static void fireServer(EntityPlayer entityPlayer, float rotationPitch, float rotationYaw, World world, ItemStack gunStack, ItemGun itemGun, WeaponFireMode fireMode, int clientFireTickDelay, float recoilPitch, float recoilYaw, float recoilAimReducer, float bulletSpread) {
/* 182 */     GunType gunType = itemGun.type;
/*     */     
/* 184 */     if (ShotValidation.verifShot(entityPlayer, gunStack, itemGun, fireMode, clientFireTickDelay, recoilPitch, recoilYaw, recoilAimReducer, bulletSpread)) {
/*     */ 
/*     */       
/* 187 */       WeaponFireEvent.PreServer preFireEvent = new WeaponFireEvent.PreServer(entityPlayer, gunStack, itemGun, gunType.weaponMaxRange);
/* 188 */       MinecraftForge.EVENT_BUS.post((Event)preFireEvent);
/* 189 */       if (preFireEvent.isCanceled())
/*     */         return; 
/* 191 */       int shotCount = (fireMode == WeaponFireMode.BURST) ? ((gunStack.func_77978_p().func_74762_e("shotsremaining") > 0) ? gunStack.func_77978_p().func_74762_e("shotsremaining") : gunType.numBurstRounds) : 1;
/*     */       
/* 193 */       if ((preFireEvent.getResult() == Event.Result.DEFAULT || preFireEvent.getResult() == Event.Result.ALLOW) && 
/* 194 */         !ItemGun.hasNextShot(gunStack)) {
/* 195 */         if (ItemGun.canDryFire) {
/* 196 */           gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.DryFire, gunStack);
/* 197 */           ItemGun.canDryFire = false;
/*     */         } 
/* 199 */         if (fireMode == WeaponFireMode.BURST) gunStack.func_77978_p().func_74768_a("shotsremaining", 0);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 205 */       if (GunType.getAttachment(gunStack, AttachmentPresetEnum.Barrel) != null) {
/* 206 */         gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.FireSuppressed, gunStack, entityPlayer);
/* 207 */       } else if (GunType.isPackAPunched(gunStack)) {
/* 208 */         gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.Punched, gunStack, entityPlayer);
/* 209 */         gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.Fire, gunStack, entityPlayer);
/*     */       } else {
/* 211 */         gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.Fire, gunStack, entityPlayer);
/*     */       } 
/* 213 */       List<Entity> entities = new ArrayList<>();
/* 214 */       int numBullets = gunType.numBullets;
/* 215 */       ItemBullet bulletItem = ItemGun.getUsedBullet(gunStack, gunType);
/* 216 */       if (bulletItem != null && 
/* 217 */         bulletItem.type.isSlug) {
/* 218 */         numBullets = 1;
/*     */       }
/*     */ 
/*     */       
/* 222 */       if (gunType.weaponType != WeaponType.Launcher) {
/* 223 */         ArrayList<BulletHit> rayTraceList = new ArrayList<>();
/* 224 */         for (int i = 0; i < numBullets; i++) {
/* 225 */           BulletHit rayTrace = RayUtil.standardEntityRayTrace(Side.SERVER, world, rotationPitch, rotationYaw, (EntityLivingBase)entityPlayer, preFireEvent.getWeaponRange(), itemGun, GunType.isPackAPunched(gunStack));
/* 226 */           rayTraceList.add(rayTrace);
/*     */         } 
/*     */         
/* 229 */         boolean headshot = false;
/* 230 */         for (BulletHit rayTrace : rayTraceList) {
/* 231 */           if (rayTrace instanceof PlayerHit) {
/* 232 */             if (!world.field_72995_K) {
/* 233 */               EntityPlayer victim = ((PlayerHit)rayTrace).getEntity();
/* 234 */               if (victim != null && 
/* 235 */                 !victim.field_70128_L && victim.func_110143_aJ() > 0.0F) {
/* 236 */                 entities.add(victim);
/* 237 */                 gunType.playSoundPos(victim.func_180425_c(), world, WeaponSoundType.Penetration);
/* 238 */                 headshot = ((PlayerHit)rayTrace).hitbox.type.equals(EnumHitboxType.HEAD);
/* 239 */                 if (entityPlayer instanceof EntityPlayerMP) {
/* 240 */                   ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlayHitmarker(headshot), (EntityPlayerMP)entityPlayer);
/* 241 */                   ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlaySound(victim.func_180425_c(), "flyby", 1.0F, 1.0F), (EntityPlayerMP)victim);
/* 242 */                   if (ModConfig.INSTANCE.hud.snap_fade_hit) {
/* 243 */                     ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlayerHit(), (EntityPlayerMP)victim);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             continue;
/*     */           } 
/* 250 */           if (!world.field_72995_K && 
/* 251 */             rayTrace.rayTraceResult != null) {
/* 252 */             if (rayTrace.rayTraceResult.field_72308_g instanceof EntityGrenade) {
/* 253 */               ((EntityGrenade)rayTrace.rayTraceResult.field_72308_g).explode();
/*     */             }
/* 255 */             if (rayTrace.rayTraceResult.field_72308_g instanceof EntityLivingBase) {
/* 256 */               EntityLivingBase victim = (EntityLivingBase)rayTrace.rayTraceResult.field_72308_g;
/* 257 */               if (victim != null) {
/* 258 */                 entities.add(victim);
/* 259 */                 gunType.playSoundPos(victim.func_180425_c(), world, WeaponSoundType.Penetration);
/* 260 */                 headshot = (ItemGun.canEntityGetHeadshot((Entity)victim) && rayTrace.rayTraceResult.field_72307_f.field_72448_b >= (victim.func_180425_c().func_177956_o() + victim.func_70047_e() - 0.15F));
/* 261 */                 if (entityPlayer instanceof EntityPlayerMP)
/* 262 */                   ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlayHitmarker(headshot), (EntityPlayerMP)entityPlayer); 
/*     */               }  continue;
/*     */             } 
/* 265 */             if (rayTrace.rayTraceResult.field_72307_f != null) {
/* 266 */               BlockPos blockPos = rayTrace.rayTraceResult.func_178782_a();
/* 267 */               ItemGun.playImpactSound(world, blockPos, gunType);
/* 268 */               gunType.playSoundPos(blockPos, world, WeaponSoundType.Crack, entityPlayer, 1.0F);
/* 269 */               ItemGun.doHit(rayTrace.rayTraceResult, entityPlayer);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 277 */         WeaponFireEvent.Post postFireEvent = new WeaponFireEvent.Post(entityPlayer, gunStack, itemGun, entities);
/* 278 */         MinecraftForge.EVENT_BUS.post((Event)postFireEvent);
/*     */         
/* 280 */         if (postFireEvent.getAffectedEntities() != null && !postFireEvent.getAffectedEntities().isEmpty()) {
/* 281 */           for (Entity target : postFireEvent.getAffectedEntities()) {
/* 282 */             if (target != null && 
/* 283 */               target != entityPlayer) {
/*     */ 
/*     */               
/* 286 */               WeaponHitEvent.Pre preHitEvent = new WeaponHitEvent.Pre(entityPlayer, gunStack, itemGun, headshot, postFireEvent.getDamage(), target);
/* 287 */               MinecraftForge.EVENT_BUS.post((Event)preHitEvent);
/* 288 */               if (preHitEvent.isCanceled()) {
/*     */                 return;
/*     */               }
/* 291 */               if (headshot) {
/* 292 */                 preHitEvent.setDamage(preHitEvent.getDamage() + gunType.gunDamageHeadshotBonus);
/*     */               }
/*     */               
/* 295 */               if (target instanceof EntityLivingBase) {
/* 296 */                 EntityLivingBase targetELB = (EntityLivingBase)target;
/* 297 */                 if (bulletItem != null && 
/* 298 */                   bulletItem.type != null) {
/* 299 */                   preHitEvent.setDamage(preHitEvent.getDamage() * bulletItem.type.bulletDamageFactor);
/* 300 */                   if (bulletItem.type.bulletProperties != null && 
/* 301 */                     !bulletItem.type.bulletProperties.isEmpty()) {
/* 302 */                     BulletProperty bulletProperty = (bulletItem.type.bulletProperties.get(targetELB.func_70005_c_()) != null) ? (BulletProperty)bulletItem.type.bulletProperties.get(targetELB.func_70005_c_()) : (BulletProperty)bulletItem.type.bulletProperties.get("All");
/* 303 */                     if (bulletProperty.potionEffects != null) {
/* 304 */                       for (PotionEntry potionEntry : bulletProperty.potionEffects) {
/* 305 */                         targetELB.func_70690_d(new PotionEffect(potionEntry.potionEffect.getPotion(), potionEntry.duration, potionEntry.level));
/*     */                       }
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 314 */               if (target instanceof EntityPlayer && 
/* 315 */                 ((PlayerHit)rayTraceList.get(0)).hitbox.type.equals(EnumHitboxType.BODY)) {
/* 316 */                 EntityPlayer player = (EntityPlayer)target;
/* 317 */                 if (player.hasCapability(CapabilityExtra.CAPABILITY, null)) {
/* 318 */                   IExtraItemHandler extraSlots = (IExtraItemHandler)player.getCapability(CapabilityExtra.CAPABILITY, null);
/* 319 */                   ItemStack plate = extraSlots.getStackInSlot(1);
/* 320 */                   if (plate != null && 
/* 321 */                     plate.func_77973_b() instanceof ItemSpecialArmor) {
/* 322 */                     ArmorType armorType = ((ItemSpecialArmor)plate.func_77973_b()).type;
/* 323 */                     float damage = preHitEvent.getDamage();
/* 324 */                     preHitEvent.setDamage((float)(damage - damage * armorType.defense));
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 331 */               if (!ModConfig.INSTANCE.shots.knockback_entity_damage) {
/* 332 */                 RayUtil.attackEntityWithoutKnockback(target, DamageSource.func_76365_a(preFireEvent.getWeaponUser()).func_76349_b(), preHitEvent.getDamage());
/*     */               } else {
/* 334 */                 target.func_70097_a(DamageSource.func_76365_a(preFireEvent.getWeaponUser()).func_76349_b(), preHitEvent.getDamage());
/*     */               } 
/* 336 */               target.field_70172_ad = 0;
/*     */ 
/*     */               
/* 339 */               WeaponHitEvent.Post postHitEvent = new WeaponHitEvent.Post(entityPlayer, gunStack, itemGun, postFireEvent.getAffectedEntities(), preHitEvent.getDamage());
/* 340 */               MinecraftForge.EVENT_BUS.post((Event)postHitEvent);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } else {
/*     */         
/* 346 */         EntityExplosiveProjectile projectile = new EntityExplosiveProjectile(world, entityPlayer, 0.5F, 3.0F, 2.5F, bulletItem.type.internalName);
/* 347 */         world.func_72838_d((Entity)projectile);
/*     */       } 
/*     */ 
/*     */       
/* 351 */       if (fireMode == WeaponFireMode.BURST) {
/* 352 */         shotCount--;
/* 353 */         gunStack.func_77978_p().func_74768_a("shotsremaining", shotCount);
/*     */       } 
/*     */       
/* 356 */       if (preFireEvent.getResult() == Event.Result.DEFAULT || preFireEvent.getResult() == Event.Result.ALLOW) {
/* 357 */         ItemGun.consumeShot(gunStack);
/*     */       }
/*     */ 
/*     */       
/* 361 */       if (ServerTickHandler.playerAimShootCooldown.get(entityPlayer.func_70005_c_()) == null) {
/* 362 */         ModularWarfare.NETWORK.sendToAll((PacketBase)new PacketAimingReponse(entityPlayer.func_70005_c_(), true));
/*     */       }
/* 364 */       ServerTickHandler.playerAimShootCooldown.put(entityPlayer.func_70005_c_(), Integer.valueOf(60));
/*     */     }
/* 366 */     else if (ModConfig.INSTANCE.general.modified_pack_server_kick) {
/* 367 */       ((EntityPlayerMP)entityPlayer).field_71135_a.func_194028_b((ITextComponent)new TextComponentString("[ModularWarfare] Kicked for client-side modified content-pack. (Bad RPM/Recoil for the gun: " + itemGun.type.internalName + ") [RPM should be: " + itemGun.type.roundsPerMin + "]"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fireClientSide(EntityPlayer entityPlayer, ItemGun itemGun) {
/* 374 */     if (entityPlayer.field_70170_p.field_72995_K) {
/* 375 */       List<Entity> entities = new ArrayList<>();
/* 376 */       int numBullets = itemGun.type.numBullets;
/* 377 */       ItemBullet bulletItem = ItemGun.getUsedBullet(entityPlayer.func_184614_ca(), itemGun.type);
/* 378 */       if (bulletItem != null && 
/* 379 */         bulletItem.type.isSlug) {
/* 380 */         numBullets = 1;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 385 */       RayTraceResult r = getMouseOver(ClientProxy.renderHooks.partialTicks);
/* 386 */       Minecraft mc = Minecraft.func_71410_x();
/* 387 */       Entity entity = mc.func_175606_aa();
/* 388 */       float pitch = entityPlayer.field_70125_A;
/* 389 */       float yaw = entityPlayer.field_70177_z;
/* 390 */       if (ClientProxy.shoulderSurfingLoaded) {
/* 391 */         Vec3d eye = entity.func_174824_e(ClientProxy.renderHooks.partialTicks);
/* 392 */         double posX = eye.field_72450_a;
/* 393 */         double posY = eye.field_72448_b;
/* 394 */         double posZ = eye.field_72449_c;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 400 */         posX = r.field_72307_f.field_72450_a - posX;
/* 401 */         posY = r.field_72307_f.field_72448_b - posY;
/* 402 */         posZ = r.field_72307_f.field_72449_c - posZ;
/* 403 */         pitch = (float)-Math.toDegrees(Math.atan(posY / Math.sqrt(posX * posX + posZ * posZ)));
/* 404 */         yaw = (float)Math.toDegrees(Math.acos((posX * 0.0D + posZ * 1.0D) / Math.sqrt(posX * posX + posZ * posZ)));
/* 405 */         if (posX > 0.0D) {
/* 406 */           yaw = -yaw;
/*     */         }
/*     */       } 
/* 409 */       ArrayList<BulletHit> rayTraceList = new ArrayList<>();
/* 410 */       for (int i = 0; i < numBullets; i++) {
/* 411 */         BulletHit rayTrace = RayUtil.standardEntityRayTrace(Side.CLIENT, entityPlayer.field_70170_p, pitch, yaw, (EntityLivingBase)entityPlayer, itemGun.type.weaponMaxRange, itemGun, false);
/* 412 */         rayTraceList.add(rayTrace);
/*     */       } 
/*     */       
/* 415 */       ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketExpShot(entityPlayer.func_145782_y(), itemGun.type.internalName));
/*     */       
/* 417 */       boolean headshot = false;
/* 418 */       for (BulletHit rayTrace : rayTraceList) {
/* 419 */         if (rayTrace instanceof OBBHit) {
/* 420 */           EntityLivingBase victim = ((OBBHit)rayTrace).entity;
/* 421 */           if (victim != null && 
/* 422 */             !victim.field_70128_L && victim.func_110143_aJ() > 0.0F) {
/* 423 */             entities.add(victim);
/*     */ 
/*     */             
/* 426 */             ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketExpGunFire(victim.func_145782_y(), itemGun.type.internalName, ((OBBHit)rayTrace).box.name, itemGun.type.fireTickDelay, itemGun.type.recoilPitch, itemGun.type.recoilYaw, itemGun.type.recoilAimReducer, itemGun.type.bulletSpread, rayTrace.rayTraceResult.field_72307_f.field_72450_a, rayTrace.rayTraceResult.field_72307_f.field_72448_b, rayTrace.rayTraceResult.field_72307_f.field_72449_c));
/*     */           } 
/*     */           continue;
/*     */         } 
/* 430 */         if (rayTrace.rayTraceResult != null && 
/* 431 */           rayTrace.rayTraceResult.field_72307_f != null) {
/* 432 */           if (rayTrace.rayTraceResult.field_72308_g != null) {
/*     */             
/* 434 */             headshot = (ItemGun.canEntityGetHeadshot(rayTrace.rayTraceResult.field_72308_g) && rayTrace.rayTraceResult.field_72307_f.field_72448_b >= (rayTrace.rayTraceResult.field_72308_g.func_180425_c().func_177956_o() + rayTrace.rayTraceResult.field_72308_g.func_70047_e() - 0.15F));
/* 435 */             ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketExpGunFire(rayTrace.rayTraceResult.field_72308_g.func_145782_y(), itemGun.type.internalName, headshot ? "head" : "", itemGun.type.fireTickDelay, itemGun.type.recoilPitch, itemGun.type.recoilYaw, itemGun.type.recoilAimReducer, itemGun.type.bulletSpread, rayTrace.rayTraceResult.field_72307_f.field_72450_a, rayTrace.rayTraceResult.field_72307_f.field_72448_b, rayTrace.rayTraceResult.field_72307_f.field_72449_c));
/*     */             continue;
/*     */           } 
/* 438 */           ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketExpGunFire(-1, itemGun.type.internalName, "", itemGun.type.fireTickDelay, itemGun.type.recoilPitch, itemGun.type.recoilYaw, itemGun.type.recoilAimReducer, itemGun.type.bulletSpread, rayTrace.rayTraceResult.field_72307_f.field_72450_a, rayTrace.rayTraceResult.field_72307_f.field_72448_b, rayTrace.rayTraceResult.field_72307_f.field_72449_c, rayTrace.rayTraceResult.field_178784_b));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RayTraceResult getMouseOver(float partialTicks) {
/* 447 */     Minecraft mc = Minecraft.func_71410_x();
/* 448 */     Entity entity = mc.func_175606_aa();
/* 449 */     RayTraceResult objectMouseOver = null;
/* 450 */     if (entity != null && 
/* 451 */       mc.field_71441_e != null) {
/* 452 */       objectMouseOver = entity.func_174822_a(128.0D, partialTicks);
/* 453 */       Vec3d vec3d = entity.func_174824_e(partialTicks);
/* 454 */       double d1 = 128.0D;
/* 455 */       if (objectMouseOver != null)
/* 456 */         d1 = objectMouseOver.field_72307_f.func_72438_d(vec3d); 
/* 457 */       Vec3d vec3d1 = entity.func_70676_i(1.0F);
/* 458 */       Vec3d vec3d2 = vec3d.func_72441_c(vec3d1.field_72450_a * d1, vec3d1.field_72448_b * d1, vec3d1.field_72449_c * d1);
/* 459 */       Entity pointedEntity = null;
/* 460 */       Vec3d vec3d3 = null;
/* 461 */       float f = 1.0F;
/* 462 */       List<Entity> list = mc.field_71441_e.func_175674_a(entity, entity.func_174813_aQ().func_72321_a(vec3d1.field_72450_a * d1, vec3d1.field_72448_b * d1, vec3d1.field_72449_c * d1).func_72314_b(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.field_180132_d, new Predicate<Entity>() {
/*     */               public boolean apply(@Nullable Entity p_apply_1_) {
/* 464 */                 return (p_apply_1_ != null && p_apply_1_.func_70067_L());
/*     */               }
/*     */             }));
/* 467 */       double d2 = d1;
/* 468 */       for (int j = 0; j < list.size(); j++) {
/* 469 */         Entity entity1 = list.get(j);
/* 470 */         AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_186662_g(entity1.func_70111_Y());
/* 471 */         RayTraceResult raytraceresult = axisalignedbb.func_72327_a(vec3d, vec3d2);
/* 472 */         if (axisalignedbb.func_72318_a(vec3d)) {
/* 473 */           if (d2 >= 0.0D) {
/* 474 */             pointedEntity = entity1;
/* 475 */             vec3d3 = (raytraceresult == null) ? vec3d : raytraceresult.field_72307_f;
/* 476 */             d2 = 0.0D;
/*     */           } 
/* 478 */         } else if (raytraceresult != null) {
/* 479 */           double d3 = vec3d.func_72438_d(raytraceresult.field_72307_f);
/* 480 */           if (d3 < d2 || d2 == 0.0D)
/* 481 */             if (entity1.func_184208_bv() == entity.func_184208_bv() && !entity1.canRiderInteract()) {
/* 482 */               if (d2 == 0.0D) {
/* 483 */                 pointedEntity = entity1;
/* 484 */                 vec3d3 = raytraceresult.field_72307_f;
/*     */               } 
/*     */             } else {
/* 487 */               pointedEntity = entity1;
/* 488 */               vec3d3 = raytraceresult.field_72307_f;
/* 489 */               d2 = d3;
/*     */             }  
/*     */         } 
/*     */       } 
/* 493 */       if (pointedEntity != null && (d2 < d1 || objectMouseOver == null))
/* 494 */         objectMouseOver = new RayTraceResult(pointedEntity, vec3d3); 
/*     */     } 
/* 496 */     return objectMouseOver;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\guns\manager\ShotManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */