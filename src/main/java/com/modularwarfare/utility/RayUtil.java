/*     */ package com.modularwarfare.utility;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.common.guns.BulletType;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.guns.ItemBullet;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.handler.ServerTickHandler;
/*     */ import com.modularwarfare.common.hitbox.hits.BulletHit;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketGunTrailAskServer;
/*     */ import java.util.HashSet;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import mchhui.modularmovements.coremod.ModularMovementsHooks;
/*     */ import mchhui.modularmovements.tactical.client.ClientLitener;
/*     */ import mchhui.modularmovements.tactical.server.ServerListener;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RayUtil
/*     */ {
/*     */   public static Vec3d getGunAccuracy(float pitch, float yaw, float accuracy, Random rand) {
/*  42 */     float randAccPitch = rand.nextFloat() * accuracy;
/*  43 */     float randAccYaw = rand.nextFloat() * accuracy;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  48 */     Vec3d vec3d = (new Vec3d(rand.nextBoolean() ? randAccYaw : -randAccYaw, rand.nextBoolean() ? randAccPitch : -randAccPitch, 100.0D)).func_72432_b();
/*  49 */     vec3d = vec3d.func_178789_a((float)(-pitch * Math.PI / 180.0D));
/*  50 */     vec3d = vec3d.func_178785_b((float)(-yaw * Math.PI / 180.0D));
/*  51 */     return vec3d;
/*     */   }
/*     */   
/*     */   public static float calculateAccuracy(ItemGun item, EntityLivingBase player) {
/*  55 */     GunType gun = item.type;
/*     */     
/*  57 */     float accuracyBarrelFactor = 1.0F;
/*  58 */     if (GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Barrel) != null) {
/*  59 */       ItemAttachment barrelAttachment = (ItemAttachment)GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Barrel).func_77973_b();
/*  60 */       accuracyBarrelFactor = barrelAttachment.type.barrel.accuracyFactor;
/*     */     } 
/*  62 */     float acc = gun.bulletSpread * accuracyBarrelFactor;
/*     */     
/*  64 */     if (player.field_70165_t != player.field_70142_S || player.field_70161_v != player.field_70136_U) {
/*  65 */       acc += gun.accuracyMoveOffset;
/*     */     }
/*  67 */     if (!player.field_70122_E) {
/*  68 */       acc += gun.accuracyHoverOffset;
/*     */     }
/*  70 */     if (player.func_70051_ag()) {
/*  71 */       acc += gun.accuracySprintOffset;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     if (player.field_70170_p.field_72995_K) {
/*  80 */       if (ClientRenderHooks.isAiming || ClientRenderHooks.isAimingScope) {
/*  81 */         boolean f1 = true;
/*  82 */         if (player.field_70170_p.field_72995_K && 
/*  83 */           player == (Minecraft.func_71410_x()).field_71439_g && 
/*  84 */           (Minecraft.func_71410_x()).field_71474_y.field_74320_O == 1) {
/*  85 */           acc *= gun.accuracyThirdAimFactor;
/*  86 */           f1 = false;
/*     */         } 
/*     */ 
/*     */         
/*  90 */         if (f1) {
/*  91 */           acc *= gun.accuracyAimFactor;
/*     */         }
/*     */       } 
/*  94 */       if (ModularWarfare.isLoadedModularMovements) {
/*  95 */         if (ClientLitener.clientPlayerState.isCrawling) {
/*  96 */           acc *= gun.accuracyCrawlFactor;
/*  97 */         } else if (player.func_70093_af() || ClientLitener.clientPlayerState.isSitting) {
/*  98 */           acc *= gun.accuracySneakFactor;
/*     */         }
/*     */       
/* 101 */       } else if (player.func_70093_af()) {
/* 102 */         acc *= gun.accuracySneakFactor;
/*     */       } 
/*     */     } else {
/*     */       
/* 106 */       Boolean bb = (Boolean)ServerTickHandler.playerAimInstant.get(player);
/* 107 */       if (bb != null && bb.booleanValue()) {
/* 108 */         acc *= gun.accuracyAimFactor;
/*     */       }
/* 110 */       if (ModularWarfare.isLoadedModularMovements) {
/* 111 */         if (ServerListener.isCrawling(Integer.valueOf(player.func_145782_y()))) {
/* 112 */           acc *= gun.accuracyCrawlFactor;
/* 113 */         } else if (player.func_70093_af() || ServerListener.isSitting(Integer.valueOf(player.func_145782_y()))) {
/* 114 */           acc *= gun.accuracySneakFactor;
/*     */         }
/*     */       
/* 117 */       } else if (player.func_70093_af()) {
/* 118 */         acc *= gun.accuracySneakFactor;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     if (acc < 0.0F) {
/* 126 */       acc = 0.0F;
/*     */     }
/*     */     
/* 129 */     if (player.func_184614_ca() != null && 
/* 130 */       player.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 131 */       ItemBullet bullet = ItemGun.getUsedBullet(player.func_184614_ca(), ((ItemGun)player.func_184614_ca().func_77973_b()).type);
/* 132 */       if (bullet != null && 
/* 133 */         bullet.type != null) {
/* 134 */         acc *= bullet.type.bulletAccuracyFactor;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 139 */     return acc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static RayTraceResult rayTrace(Entity entity, double blockReachDistance, float partialTicks) {
/* 226 */     Vec3d vec3d = entity.func_174824_e(partialTicks);
/* 227 */     Vec3d vec3d1 = entity.func_70676_i(partialTicks);
/* 228 */     Vec3d vec3d2 = vec3d.func_72441_c(vec3d1.field_72450_a * blockReachDistance, vec3d1.field_72448_b * blockReachDistance, vec3d1.field_72449_c * blockReachDistance);
/*     */     
/* 230 */     if (ModularWarfare.isLoadedModularMovements && 
/* 231 */       entity instanceof EntityPlayer) {
/* 232 */       vec3d = ModularMovementsHooks.onGetPositionEyes((EntityPlayer)entity, partialTicks);
/*     */     }
/*     */ 
/*     */     
/* 236 */     return entity.field_70170_p.func_147447_a(vec3d, vec3d2, false, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean attackEntityWithoutKnockback(Entity entity, DamageSource source, float amount) {
/* 254 */     double vx = entity.field_70159_w;
/* 255 */     double vy = entity.field_70181_x;
/* 256 */     double vz = entity.field_70179_y;
/* 257 */     boolean succeeded = entity.func_70097_a(source, amount);
/* 258 */     entity.field_70159_w = vx;
/* 259 */     entity.field_70181_x = vy;
/* 260 */     entity.field_70179_y = vz;
/* 261 */     return succeeded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static BulletHit standardEntityRayTrace(Side side, World world, float rotationPitch, float rotationYaw, EntityLivingBase player, double range, ItemGun item, boolean isPunched) {
/* 275 */     HashSet<Entity> hashset = new HashSet<>(1);
/* 276 */     hashset.add(player);
/*     */     
/* 278 */     float accuracy = calculateAccuracy(item, player);
/* 279 */     Vec3d dir = getGunAccuracy(rotationPitch, rotationYaw, accuracy, player.field_70170_p.field_73012_v);
/* 280 */     double dx = dir.field_72450_a * range;
/* 281 */     double dy = dir.field_72448_b * range;
/* 282 */     double dz = dir.field_72449_c * range;
/*     */     
/* 284 */     if (!side.isServer()) {
/*     */ 
/*     */       
/* 287 */       ItemStack gunStack = player.func_184614_ca();
/* 288 */       ItemStack bulletStack = null;
/* 289 */       String model = null;
/* 290 */       String tex = null;
/* 291 */       boolean glow = false;
/* 292 */       if (gunStack.func_77973_b() instanceof ItemGun) {
/* 293 */         GunType gunType = ((ItemGun)gunStack.func_77973_b()).type;
/* 294 */         if (gunType.acceptedBullets != null) {
/* 295 */           bulletStack = new ItemStack(gunStack.func_77978_p().func_74775_l("bullet"));
/*     */         } else {
/* 297 */           ItemStack stackAmmo = new ItemStack(gunStack.func_77978_p().func_74775_l("ammo"));
/* 298 */           if (stackAmmo != null && !stackAmmo.func_190926_b() && stackAmmo.func_77942_o()) {
/* 299 */             bulletStack = new ItemStack(stackAmmo.func_77978_p().func_74775_l("bullet"));
/*     */           }
/*     */         } 
/*     */       } 
/* 303 */       if (bulletStack != null && 
/* 304 */         bulletStack.func_77973_b() instanceof ItemBullet) {
/* 305 */         BulletType bulletType = ((ItemBullet)bulletStack.func_77973_b()).type;
/* 306 */         model = bulletType.trailModel;
/* 307 */         tex = bulletType.trailTex;
/* 308 */         glow = bulletType.trailGlow;
/*     */       } 
/*     */       
/* 311 */       ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunTrailAskServer(item.type, model, tex, glow, player.field_70165_t, (player.func_174813_aQ()).field_72338_b + player.func_70047_e() - 0.10000000149011612D, player.field_70161_v, player.field_70159_w, player.field_70179_y, dir.field_72450_a, dir.field_72448_b, dir.field_72449_c, range, 10.0F, isPunched));
/*     */     } 
/*     */     
/* 314 */     int ping = 0;
/* 315 */     if (player instanceof EntityPlayerMP) {
/* 316 */       EntityPlayerMP entityPlayerMP = (EntityPlayerMP)player;
/* 317 */       ping = entityPlayerMP.field_71138_i;
/*     */     } 
/*     */     
/* 320 */     Vec3d offsetVec = player.func_174824_e(1.0F);
/* 321 */     if (ModularWarfare.isLoadedModularMovements && 
/* 322 */       player instanceof EntityPlayer) {
/* 323 */       offsetVec = ModularMovementsHooks.onGetPositionEyes((EntityPlayer)player, 1.0F);
/*     */     }
/*     */ 
/*     */     
/* 327 */     return ModularWarfare.INSTANCE.RAY_CASTING.computeDetection(world, (float)offsetVec.field_72450_a, (float)offsetVec.field_72448_b, (float)offsetVec.field_72449_c, (float)(offsetVec.field_72450_a + dx), (float)(offsetVec.field_72448_b + dy), (float)(offsetVec.field_72449_c + dz), 0.001F, hashset, false, ping);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfar\\utility\RayUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */