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
/*     */ import net.minecraft.client.settings.GameSettings;
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
/*     */   public static float calculateAccuracyServer(ItemGun item, EntityLivingBase player) {
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
/*  73 */     if (player.func_70093_af()) {
/*  74 */       acc *= gun.accuracySneakFactor;
/*     */     }
/*     */ 
/*     */     
/*  78 */     if (player.field_70170_p.field_72995_K) {
/*  79 */       if (ClientRenderHooks.isAiming || ClientRenderHooks.isAimingScope) {
/*  80 */         acc *= gun.accuracyAimFactor;
/*     */       }
/*     */ 
/*     */       
/*  84 */       if (ModularWarfare.isLoadedModularMovements) {
/*  85 */         if (ClientLitener.clientPlayerState.isCrawling) {
/*  86 */           acc *= gun.accuracyCrawlFactor;
/*  87 */         } else if (player.func_70093_af() || ClientLitener.clientPlayerState.isSitting) {
/*  88 */           acc *= gun.accuracySneakFactor;
/*     */         }
/*     */       
/*  91 */       } else if (player.func_70093_af()) {
/*  92 */         acc *= gun.accuracySneakFactor;
/*     */       } 
/*     */     } else {
/*     */       
/*  96 */       Boolean bb = (Boolean)ServerTickHandler.playerAimInstant.get(player);
/*  97 */       if (bb != null && bb.booleanValue()) {
/*  98 */         acc *= gun.accuracyAimFactor;
/*     */       }
/*     */       
/* 101 */       if (ModularWarfare.isLoadedModularMovements) {
/* 102 */         if (ServerListener.isCrawling(Integer.valueOf(player.func_145782_y()))) {
/* 103 */           acc *= gun.accuracyCrawlFactor;
/* 104 */         } else if (player.func_70093_af() || ServerListener.isSitting(Integer.valueOf(player.func_145782_y()))) {
/* 105 */           acc *= gun.accuracySneakFactor;
/*     */         }
/*     */       
/* 108 */       } else if (player.func_70093_af()) {
/* 109 */         acc *= gun.accuracySneakFactor;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     if (acc < 0.0F) {
/* 117 */       acc = 0.0F;
/*     */     }
/*     */     
/* 120 */     if (player.func_184614_ca() != null && 
/* 121 */       player.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 122 */       ItemBullet bullet = ItemGun.getUsedBullet(player.func_184614_ca(), ((ItemGun)player.func_184614_ca().func_77973_b()).type);
/* 123 */       if (bullet != null && 
/* 124 */         bullet.type != null) {
/* 125 */         acc *= bullet.type.bulletAccuracyFactor;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 130 */     return acc;
/*     */   }
/*     */   
/*     */   public static float calculateAccuracyClient(ItemGun item, EntityPlayer player) {
/* 134 */     GunType gun = item.type;
/*     */     
/* 136 */     float accuracyBarrelFactor = 1.0F;
/* 137 */     if (GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Barrel) != null) {
/* 138 */       ItemAttachment barrelAttachment = (ItemAttachment)GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Barrel).func_77973_b();
/* 139 */       accuracyBarrelFactor = barrelAttachment.type.barrel.accuracyFactor;
/*     */     } 
/* 141 */     float acc = gun.bulletSpread * accuracyBarrelFactor;
/* 142 */     GameSettings settings = (Minecraft.func_71410_x()).field_71474_y;
/* 143 */     if (settings.field_74351_w.func_151470_d() || settings.field_74370_x.func_151470_d() || settings.field_74368_y.func_151470_d() || settings.field_74366_z.func_151470_d()) {
/* 144 */       acc += 0.75F;
/*     */     }
/* 146 */     if (!player.field_70122_E) {
/* 147 */       acc += 1.5F;
/*     */     }
/* 149 */     if (player.func_70051_ag()) {
/* 150 */       acc += 0.25F;
/*     */     }
/* 152 */     if (player.func_70093_af()) {
/* 153 */       acc *= gun.accuracySneakFactor;
/*     */     }
/*     */ 
/*     */     
/* 157 */     if (player.field_70170_p.field_72995_K) {
/* 158 */       if (ClientRenderHooks.isAiming || ClientRenderHooks.isAimingScope) {
/* 159 */         acc *= gun.accuracyAimFactor;
/*     */       }
/*     */ 
/*     */       
/* 163 */       if (ModularWarfare.isLoadedModularMovements) {
/* 164 */         if (ClientLitener.clientPlayerState.isCrawling) {
/* 165 */           acc *= gun.accuracyCrawlFactor;
/* 166 */         } else if (player.func_70093_af() || ClientLitener.clientPlayerState.isSitting) {
/* 167 */           acc *= gun.accuracySneakFactor;
/*     */         }
/*     */       
/* 170 */       } else if (player.func_70093_af()) {
/* 171 */         acc *= gun.accuracySneakFactor;
/*     */       } 
/*     */     } else {
/*     */       
/* 175 */       Boolean bb = (Boolean)ServerTickHandler.playerAimInstant.get(player);
/* 176 */       if (bb != null && bb.booleanValue()) {
/* 177 */         acc *= gun.accuracyAimFactor;
/*     */       }
/*     */       
/* 180 */       if (ModularWarfare.isLoadedModularMovements) {
/* 181 */         if (ServerListener.isCrawling(Integer.valueOf(player.func_145782_y()))) {
/* 182 */           acc *= gun.accuracyCrawlFactor;
/* 183 */         } else if (player.func_70093_af() || ServerListener.isSitting(Integer.valueOf(player.func_145782_y()))) {
/* 184 */           acc *= gun.accuracySneakFactor;
/*     */         }
/*     */       
/* 187 */       } else if (player.func_70093_af()) {
/* 188 */         acc *= gun.accuracySneakFactor;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     if (acc < 0.0F) {
/* 196 */       acc = 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 200 */     if (player.func_184614_ca() != null && 
/* 201 */       player.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 202 */       ItemBullet bullet = ItemGun.getUsedBullet(player.func_184614_ca(), ((ItemGun)player.func_184614_ca().func_77973_b()).type);
/* 203 */       if (bullet != null && 
/* 204 */         bullet.type != null) {
/* 205 */         acc *= bullet.type.bulletAccuracyFactor;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 210 */     return acc;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static RayTraceResult rayTrace(Entity entity, double blockReachDistance, float partialTicks) {
/* 217 */     Vec3d vec3d = entity.func_174824_e(partialTicks);
/* 218 */     Vec3d vec3d1 = entity.func_70676_i(partialTicks);
/* 219 */     Vec3d vec3d2 = vec3d.func_72441_c(vec3d1.field_72450_a * blockReachDistance, vec3d1.field_72448_b * blockReachDistance, vec3d1.field_72449_c * blockReachDistance);
/*     */     
/* 221 */     if (ModularWarfare.isLoadedModularMovements && 
/* 222 */       entity instanceof EntityPlayer) {
/* 223 */       vec3d = ModularMovementsHooks.onGetPositionEyes((EntityPlayer)entity, partialTicks);
/*     */     }
/*     */ 
/*     */     
/* 227 */     return entity.field_70170_p.func_147447_a(vec3d, vec3d2, false, true, false);
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
/* 245 */     double vx = entity.field_70159_w;
/* 246 */     double vy = entity.field_70181_x;
/* 247 */     double vz = entity.field_70179_y;
/* 248 */     boolean succeeded = entity.func_70097_a(source, amount);
/* 249 */     entity.field_70159_w = vx;
/* 250 */     entity.field_70181_x = vy;
/* 251 */     entity.field_70179_y = vz;
/* 252 */     return succeeded;
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
/* 266 */     HashSet<Entity> hashset = new HashSet<>(1);
/* 267 */     hashset.add(player);
/*     */     
/* 269 */     float accuracy = calculateAccuracyServer(item, player);
/* 270 */     Vec3d dir = getGunAccuracy(rotationPitch, rotationYaw, accuracy, player.field_70170_p.field_73012_v);
/* 271 */     double dx = dir.field_72450_a * range;
/* 272 */     double dy = dir.field_72448_b * range;
/* 273 */     double dz = dir.field_72449_c * range;
/*     */     
/* 275 */     if (!side.isServer()) {
/*     */ 
/*     */       
/* 278 */       ItemStack gunStack = player.func_184614_ca();
/* 279 */       ItemStack bulletStack = null;
/* 280 */       String model = null;
/* 281 */       String tex = null;
/* 282 */       boolean glow = false;
/* 283 */       if (gunStack.func_77973_b() instanceof ItemGun) {
/* 284 */         GunType gunType = ((ItemGun)gunStack.func_77973_b()).type;
/* 285 */         if (gunType.acceptedBullets != null) {
/* 286 */           bulletStack = new ItemStack(gunStack.func_77978_p().func_74775_l("bullet"));
/*     */         } else {
/* 288 */           ItemStack stackAmmo = new ItemStack(gunStack.func_77978_p().func_74775_l("ammo"));
/* 289 */           if (stackAmmo != null && !stackAmmo.func_190926_b() && stackAmmo.func_77942_o()) {
/* 290 */             bulletStack = new ItemStack(stackAmmo.func_77978_p().func_74775_l("bullet"));
/*     */           }
/*     */         } 
/*     */       } 
/* 294 */       if (bulletStack != null && 
/* 295 */         bulletStack.func_77973_b() instanceof ItemBullet) {
/* 296 */         BulletType bulletType = ((ItemBullet)bulletStack.func_77973_b()).type;
/* 297 */         model = bulletType.trailModel;
/* 298 */         tex = bulletType.trailTex;
/* 299 */         glow = bulletType.trailGlow;
/*     */       } 
/*     */       
/* 302 */       ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunTrailAskServer(item.type, model, tex, glow, player.field_70165_t, (player.func_174813_aQ()).field_72338_b + player.func_70047_e() - 0.10000000149011612D, player.field_70161_v, player.field_70159_w, player.field_70179_y, dir.field_72450_a, dir.field_72448_b, dir.field_72449_c, range, 10.0F, isPunched));
/*     */     } 
/*     */     
/* 305 */     int ping = 0;
/* 306 */     if (player instanceof EntityPlayerMP) {
/* 307 */       EntityPlayerMP entityPlayerMP = (EntityPlayerMP)player;
/* 308 */       ping = entityPlayerMP.field_71138_i;
/*     */     } 
/*     */     
/* 311 */     Vec3d offsetVec = player.func_174824_e(1.0F);
/* 312 */     if (ModularWarfare.isLoadedModularMovements && 
/* 313 */       player instanceof EntityPlayer) {
/* 314 */       offsetVec = ModularMovementsHooks.onGetPositionEyes((EntityPlayer)player, 1.0F);
/*     */     }
/*     */ 
/*     */     
/* 318 */     return ModularWarfare.INSTANCE.RAY_CASTING.computeDetection(world, (float)offsetVec.field_72450_a, (float)offsetVec.field_72448_b, (float)offsetVec.field_72449_c, (float)(offsetVec.field_72450_a + dx), (float)(offsetVec.field_72448_b + dy), (float)(offsetVec.field_72449_c + dz), 0.001F, hashset, false, ping);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfar\\utility\RayUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */