/*     */ package com.modularwarfare.client.handler;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.OnTickRenderEvent;
/*     */ import com.modularwarfare.client.ClientEventHandler;
/*     */ import com.modularwarfare.client.ClientProxy;
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import com.modularwarfare.client.fpp.basic.animations.AnimStateMachine;
/*     */ import com.modularwarfare.client.fpp.basic.animations.StateEntry;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderParameters;
/*     */ import com.modularwarfare.client.fpp.enhanced.animation.AnimationController;
/*     */ import com.modularwarfare.client.fpp.enhanced.animation.EnhancedStateMachine;
/*     */ import com.modularwarfare.client.hud.FlashSystem;
/*     */ import com.modularwarfare.client.hud.GunUI;
/*     */ import com.modularwarfare.client.model.InstantBulletRenderer;
/*     */ import com.modularwarfare.client.model.ModelGun;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.WeaponAnimationType;
/*     */ import com.modularwarfare.common.guns.WeaponSoundType;
/*     */ import com.modularwarfare.utility.MWSound;
/*     */ import com.modularwarfare.utility.RayUtil;
/*     */ import com.modularwarfare.utility.event.ForgeEvent;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientTickHandler
/*     */   extends ForgeEvent
/*     */ {
/*     */   private static final int SPS = 60;
/*  48 */   public static ConcurrentHashMap<UUID, Integer> playerShootCooldown = new ConcurrentHashMap<>();
/*  49 */   public static ConcurrentHashMap<UUID, Integer> playerReloadCooldown = new ConcurrentHashMap<>();
/*  50 */   public static ItemStack reloadEnhancedPrognosisAmmo = ItemStack.field_190927_a;
/*  51 */   public static ItemStack reloadEnhancedPrognosisAmmoRendering = ItemStack.field_190927_a;
/*     */   
/*     */   public static boolean reloadEnhancedIsQuickly = false;
/*     */   public static boolean reloadEnhancedIsQuicklyRendering = false;
/*     */   public static int oldCurrentItem;
/*  56 */   public static ItemStack oldItemStack = ItemStack.field_190927_a;
/*  57 */   public static ItemStack lastItemStack = ItemStack.field_190927_a;
/*     */   public static World lastWorld;
/*  59 */   int i = 0;
/*     */ 
/*     */   
/*     */   private static long lastSyncTime;
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void clientTick(TickEvent.ClientTickEvent event) {
/*  68 */     switch (event.phase) {
/*     */ 
/*     */       
/*     */       case START:
/*  72 */         if (lastWorld != (Minecraft.func_71410_x()).field_71441_e) {
/*  73 */           ClientRenderHooks.weaponEnhancedAnimations.clear();
/*  74 */           ClientRenderHooks.weaponBasicAnimations.clear();
/*  75 */           ClientProxy.gunEnhancedRenderer.getOtherControllers().clear();
/*  76 */           lastWorld = (World)(Minecraft.func_71410_x()).field_71441_e;
/*     */         } 
/*     */ 
/*     */         
/*  80 */         onClientTickStart(Minecraft.func_71410_x());
/*  81 */         ModularWarfare.NETWORK.handleClientPackets();
/*     */ 
/*     */         
/*  84 */         for (UUID uuid : playerShootCooldown.keySet()) {
/*  85 */           this.i++;
/*  86 */           int value = ((Integer)playerShootCooldown.get(uuid)).intValue() - 1;
/*  87 */           if (value <= 0) {
/*  88 */             playerShootCooldown.remove(uuid); continue;
/*     */           } 
/*  90 */           playerShootCooldown.replace(uuid, Integer.valueOf(value));
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  95 */         for (UUID uuid : playerReloadCooldown.keySet()) {
/*  96 */           this.i++;
/*  97 */           int value = ((Integer)playerReloadCooldown.get(uuid)).intValue() - 1;
/*  98 */           if (value <= 0) {
/*  99 */             playerReloadCooldown.remove(uuid); continue;
/*     */           } 
/* 101 */           playerReloadCooldown.replace(uuid, Integer.valueOf(value));
/*     */         } 
/*     */         break;
/*     */       
/*     */       case END:
/* 106 */         onClientTickEnd(Minecraft.func_71410_x());
/*     */         break;
/*     */     } 
/*     */   } @SubscribeEvent
/*     */   public void renderTick(TickEvent.RenderTickEvent event) {
/*     */     float renderTick;
/*     */     long time;
/* 113 */     switch (event.phase) {
/*     */       
/*     */       case START:
/* 116 */         renderTick = event.renderTickTime;
/* 117 */         renderTick = (float)(renderTick * 60.0D / Minecraft.func_175610_ah());
/* 118 */         StateEntry.smoothing = renderTick;
/* 119 */         onRenderTickStart(Minecraft.func_71410_x(), renderTick);
/*     */ 
/*     */ 
/*     */         
/* 123 */         time = System.currentTimeMillis();
/* 124 */         if (time > lastSyncTime + 6L) {
/* 125 */           if (lastSyncTime > 0L) {
/* 126 */             float stepTick = (float)(time - lastSyncTime) / 16.666666F;
/* 127 */             if (ClientProxy.gunEnhancedRenderer.getClientController() != null && 
/* 128 */               (Minecraft.func_71410_x()).field_71439_g != null && 
/* 129 */               (Minecraft.func_71410_x()).field_71439_g.func_184614_ca()
/* 130 */               .func_77973_b() instanceof ItemGun && 
/*     */               
/* 132 */               ((ItemGun)(Minecraft.func_71410_x()).field_71439_g.func_184614_ca().func_77973_b()).type.animationType.equals(WeaponAnimationType.ENHANCED)) {
/* 133 */               ClientProxy.gunEnhancedRenderer.getClientController().onTickRender(stepTick);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 139 */             ClientProxy.gunEnhancedRenderer.getOtherControllers().values().forEach(ctrl -> {
/*     */                   if (ctrl.player.func_184614_ca().func_77973_b() instanceof ItemGun && ((ItemGun)ctrl.player.func_184614_ca().func_77973_b()).type.animationType.equals(WeaponAnimationType.ENHANCED)) {
/*     */                     ctrl.onTickRender(stepTick);
/*     */                   }
/*     */                 });
/*     */ 
/*     */ 
/*     */             
/* 147 */             float offset = stepTick / 10.0F;
/* 148 */             if (Math.abs(ClientEventHandler.cemeraBobbing) < offset) {
/* 149 */               ClientEventHandler.cemeraBobbing = 0.0F;
/* 150 */             } else if (ClientEventHandler.cemeraBobbing > 0.0F) {
/* 151 */               ClientEventHandler.cemeraBobbing -= offset;
/*     */             } else {
/* 153 */               ClientEventHandler.cemeraBobbing += offset;
/*     */             } 
/*     */           } 
/* 156 */           lastSyncTime = time;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderTickStart(Minecraft minecraft, float renderTick) {
/* 164 */     if (minecraft.field_71439_g == null || minecraft.field_71441_e == null) {
/*     */       return;
/*     */     }
/* 167 */     if (minecraft.field_71417_B.field_74375_b > 0 || -minecraft.field_71417_B.field_74375_b > 1) {
/* 168 */       RenderParameters.antiRecoilPitch = (float)(RenderParameters.antiRecoilPitch * 0.25D);
/*     */     }
/* 170 */     if (minecraft.field_71417_B.field_74377_a > 2 || -minecraft.field_71417_B.field_74377_a > 2) {
/* 171 */       RenderParameters.antiRecoilYaw = (float)(RenderParameters.antiRecoilYaw * 0.25D);
/*     */     }
/* 173 */     EntityPlayerSP player = minecraft.field_71439_g;
/*     */     
/* 175 */     reloadEnhancedPrognosisAmmoRendering = reloadEnhancedPrognosisAmmo;
/* 176 */     reloadEnhancedIsQuicklyRendering = reloadEnhancedIsQuickly;
/*     */     
/* 178 */     OnTickRenderEvent event = new OnTickRenderEvent(renderTick);
/* 179 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (ClientProxy.gunEnhancedRenderer.getClientController() != null) {
/* 185 */       ClientProxy.gunEnhancedRenderer.getClientController().updateCurrentItem();
/*     */     }
/* 187 */     ClientProxy.gunEnhancedRenderer.getOtherControllers().values().forEach(ctrl -> ctrl.updateCurrentItem());
/*     */ 
/*     */ 
/*     */     
/* 191 */     for (EntityLivingBase entity : ClientRenderHooks.weaponEnhancedAnimations.keySet()) {
/* 192 */       if (entity != null) {
/* 193 */         ((EnhancedStateMachine)ClientRenderHooks.weaponEnhancedAnimations.get(entity)).updateCurrentItem(entity);
/*     */       }
/*     */     } 
/*     */     
/* 197 */     if (player.func_184614_ca() != null && player.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 198 */       float adsSpeed = 0.0F;
/* 199 */       if (((ItemGun)player.func_184614_ca().func_77973_b()).type.animationType.equals(WeaponAnimationType.BASIC)) {
/* 200 */         ModelGun model = (ModelGun)((ItemGun)player.func_184614_ca().func_77973_b()).type.model;
/* 201 */         if (!RenderParameters.lastModel.equalsIgnoreCase(model.getClass().getName())) {
/* 202 */           RenderParameters.resetRenderMods();
/* 203 */           RenderParameters.lastModel = model.getClass().getName();
/* 204 */           adsSpeed = model.config.extra.adsSpeed;
/*     */         } 
/* 206 */         AnimStateMachine anim = ClientRenderHooks.getAnimMachine((EntityLivingBase)player);
/*     */         
/* 208 */         float adsSpeedFinal = (0.1F + adsSpeed) * renderTick;
/* 209 */         boolean aimChargeMisc = !anim.reloading;
/* 210 */         float value = ((Minecraft.func_71410_x()).field_71415_G && Mouse.isButtonDown(1) && aimChargeMisc && !(ClientRenderHooks.getAnimMachine((EntityLivingBase)player)).attachmentMode) ? (RenderParameters.adsSwitch + adsSpeedFinal) : (RenderParameters.adsSwitch - adsSpeedFinal);
/* 211 */         RenderParameters.adsSwitch = Math.max(0.0F, Math.min(1.0F, value));
/*     */ 
/*     */         
/* 214 */         float sprintSpeed = 0.15F * renderTick;
/* 215 */         float sprintValue = (player.func_70051_ag() && !(ClientRenderHooks.getAnimMachine((EntityLivingBase)player)).attachmentMode) ? (RenderParameters.sprintSwitch + sprintSpeed) : (RenderParameters.sprintSwitch - sprintSpeed);
/* 216 */         RenderParameters.sprintSwitch = Math.max(0.0F, Math.min(1.0F, sprintValue));
/*     */ 
/*     */         
/* 219 */         float attachmentSpeed = 0.15F * renderTick;
/* 220 */         float attachmentValue = (ClientRenderHooks.getAnimMachine((EntityLivingBase)player)).attachmentMode ? (RenderParameters.attachmentSwitch + attachmentSpeed) : (RenderParameters.attachmentSwitch - attachmentSpeed);
/* 221 */         RenderParameters.attachmentSwitch = Math.max(0.0F, Math.min(1.0F, attachmentValue));
/*     */ 
/*     */         
/* 224 */         float crouchSpeed = 0.15F * renderTick;
/* 225 */         float crouchValue = player.func_70093_af() ? (RenderParameters.crouchSwitch + crouchSpeed) : (RenderParameters.crouchSwitch - crouchSpeed);
/* 226 */         RenderParameters.crouchSwitch = Math.max(0.0F, Math.min(1.0F, crouchValue));
/*     */ 
/*     */         
/* 229 */         float reloadSpeed = 0.15F * renderTick;
/* 230 */         float reloadValue = anim.reloading ? (RenderParameters.reloadSwitch - reloadSpeed) : (RenderParameters.reloadSwitch + reloadSpeed);
/* 231 */         RenderParameters.reloadSwitch = Math.max(0.0F, Math.min(1.0F, reloadValue));
/*     */ 
/*     */         
/* 234 */         float triggerPullSpeed = 0.03F * renderTick;
/* 235 */         float triggerPullValue = ((Minecraft.func_71410_x()).field_71415_G && Mouse.isButtonDown(0) && !(ClientRenderHooks.getAnimMachine((EntityLivingBase)player)).attachmentMode) ? (RenderParameters.triggerPullSwitch + triggerPullSpeed) : (RenderParameters.triggerPullSwitch - triggerPullSpeed);
/* 236 */         RenderParameters.triggerPullSwitch = Math.max(0.0F, Math.min(0.02F, triggerPullValue));
/*     */         
/* 238 */         float modeSwitchSpeed = 0.03F * renderTick;
/* 239 */         float modeSwitchValue = ((Minecraft.func_71410_x()).field_71415_G && Mouse.isButtonDown(0)) ? (RenderParameters.triggerPullSwitch + triggerPullSpeed) : (RenderParameters.triggerPullSwitch - triggerPullSpeed);
/* 240 */         RenderParameters.triggerPullSwitch = Math.max(0.0F, Math.min(0.02F, triggerPullValue));
/*     */       } 
/*     */       
/* 243 */       float balancing_speed_x = 0.08F * renderTick;
/* 244 */       if (player.field_70702_br > 0.0F) {
/* 245 */         RenderParameters.GUN_BALANCING_X = Math.min(1.0F, RenderParameters.GUN_BALANCING_X + balancing_speed_x);
/* 246 */       } else if (player.field_70702_br < 0.0F) {
/* 247 */         RenderParameters.GUN_BALANCING_X = Math.max(-1.0F, RenderParameters.GUN_BALANCING_X - balancing_speed_x);
/* 248 */       } else if (player.field_70702_br == 0.0F && RenderParameters.GUN_BALANCING_X != 0.0F) {
/* 249 */         if (RenderParameters.GUN_BALANCING_X > 0.0F) {
/* 250 */           RenderParameters.GUN_BALANCING_X = Math.max(0.0F, RenderParameters.GUN_BALANCING_X - balancing_speed_x);
/* 251 */         } else if (RenderParameters.GUN_BALANCING_X < 0.0F) {
/* 252 */           RenderParameters.GUN_BALANCING_X = Math.min(0.0F, RenderParameters.GUN_BALANCING_X + balancing_speed_x);
/*     */         } 
/*     */       } 
/*     */       
/* 256 */       float balancing_speed_y = 0.08F * renderTick;
/* 257 */       if (player.field_191988_bg > 0.0F) {
/* 258 */         RenderParameters.GUN_BALANCING_Y = Math.min(player.func_70051_ag() ? 3.0F : 1.0F, RenderParameters.GUN_BALANCING_Y + balancing_speed_y);
/* 259 */       } else if (player.field_191988_bg < 0.0F) {
/* 260 */         RenderParameters.GUN_BALANCING_Y = Math.max(-1.0F, RenderParameters.GUN_BALANCING_Y - balancing_speed_y);
/* 261 */       } else if (player.field_191988_bg == 0.0F && RenderParameters.GUN_BALANCING_Y != 0.0F) {
/* 262 */         if (RenderParameters.GUN_BALANCING_Y > 0.0F) {
/* 263 */           RenderParameters.GUN_BALANCING_Y = Math.max(0.0F, RenderParameters.GUN_BALANCING_Y - balancing_speed_y * 2.0F);
/* 264 */         } else if (RenderParameters.GUN_BALANCING_Y < 0.0F) {
/* 265 */           RenderParameters.GUN_BALANCING_Y = Math.min(0.0F, RenderParameters.GUN_BALANCING_Y + balancing_speed_y * 2.0F);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 271 */       if (player.field_71071_by.field_70461_c != oldCurrentItem) {
/* 272 */         if (oldItemStack.func_190926_b() || !(oldItemStack.func_77973_b() instanceof ItemGun)) {
/* 273 */           RenderParameters.GUN_CHANGE_Y = 0.5F;
/*     */         }
/* 275 */         if (RenderParameters.GUN_CHANGE_Y <= 0.5D) {
/* 276 */           if (!oldItemStack.func_190926_b() && oldItemStack.func_77973_b() instanceof ItemGun) {
/* 277 */             lastItemStack = oldItemStack;
/*     */           }
/* 279 */           RenderParameters.GUN_CHANGE_Y = 1.0F - RenderParameters.GUN_CHANGE_Y;
/*     */         } 
/*     */       } 
/* 282 */       if (RenderParameters.GUN_CHANGE_Y < 0.5D) {
/* 283 */         lastItemStack = ItemStack.field_190927_a;
/*     */       }
/* 285 */       float change_speed_y = 0.04F * renderTick;
/* 286 */       RenderParameters.GUN_CHANGE_Y = Math.max(0.0F, RenderParameters.GUN_CHANGE_Y - change_speed_y);
/*     */       
/* 288 */       Vec3d vecStart = player.func_174824_e(1.0F);
/* 289 */       RayTraceResult rayTraceResult = RayUtil.rayTrace((Entity)player, 5.0D, 1.0F);
/* 290 */       float recover = 0.1F;
/* 291 */       if (Mouse.isButtonDown(1)) {
/* 292 */         recover = 0.8F;
/*     */       }
/* 294 */       if (rayTraceResult != null) {
/* 295 */         if (rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 296 */           if (rayTraceResult.field_72307_f != null) {
/* 297 */             double d = vecStart.func_72438_d(rayTraceResult.field_72307_f);
/* 298 */             double testD = 1.5D;
/* 299 */             if (d <= testD) {
/* 300 */               RenderParameters.collideFrontDistance = (float)(RenderParameters.collideFrontDistance + (testD - d - RenderParameters.collideFrontDistance) * renderTick * 0.5D);
/*     */             } else {
/* 302 */               RenderParameters.collideFrontDistance = Math.max(0.0F, RenderParameters.collideFrontDistance - renderTick * recover);
/*     */             } 
/*     */           } else {
/* 305 */             RenderParameters.collideFrontDistance = Math.max(0.0F, RenderParameters.collideFrontDistance - renderTick * recover);
/*     */           } 
/*     */         } else {
/* 308 */           RenderParameters.collideFrontDistance = Math.max(0.0F, RenderParameters.collideFrontDistance - renderTick * recover);
/*     */         } 
/*     */       } else {
/* 311 */         RenderParameters.collideFrontDistance = Math.max(0.0F, RenderParameters.collideFrontDistance - renderTick * recover);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 317 */       for (AnimStateMachine stateMachine : ClientRenderHooks.weaponBasicAnimations.values()) {
/* 318 */         stateMachine.onRenderTickUpdate();
/*     */       }
/*     */     } else {
/* 321 */       RenderParameters.resetRenderMods();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClientTickStart(Minecraft minecraft) {
/* 327 */     if (minecraft.field_71439_g == null || minecraft.field_71441_e == null) {
/*     */       return;
/*     */     }
/* 330 */     ModularWarfare.PLAYERHANDLER.clientTick();
/*     */     
/* 332 */     RenderParameters.GUN_ROT_X_LAST = RenderParameters.GUN_ROT_X;
/* 333 */     RenderParameters.GUN_ROT_Y_LAST = RenderParameters.GUN_ROT_Y;
/* 334 */     RenderParameters.GUN_ROT_Z_LAST = RenderParameters.GUN_ROT_Z;
/*     */     
/* 336 */     Minecraft mc = FMLClientHandler.instance().getClient();
/*     */     
/* 338 */     if (mc.func_175606_aa() != null) {
/* 339 */       if (mc.func_175606_aa().func_70079_am() > (mc.func_175606_aa()).field_70126_B) {
/* 340 */         RenderParameters.GUN_ROT_X = (float)(RenderParameters.GUN_ROT_X + (mc.func_175606_aa().func_70079_am() - (mc.func_175606_aa()).field_70126_B) / 1.5D);
/* 341 */       } else if (mc.func_175606_aa().func_70079_am() < (mc.func_175606_aa()).field_70126_B) {
/* 342 */         RenderParameters.GUN_ROT_X = (float)(RenderParameters.GUN_ROT_X - ((mc.func_175606_aa()).field_70126_B - mc.func_175606_aa().func_70079_am()) / 1.5D);
/*     */       } 
/* 344 */       if ((mc.func_175606_aa()).field_70125_A > RenderParameters.prevPitch) {
/* 345 */         RenderParameters.GUN_ROT_Y += ((mc.func_175606_aa()).field_70125_A - RenderParameters.prevPitch) / 5.0F;
/* 346 */       } else if ((mc.func_175606_aa()).field_70125_A < RenderParameters.prevPitch) {
/* 347 */         RenderParameters.GUN_ROT_Y -= (RenderParameters.prevPitch - (mc.func_175606_aa()).field_70125_A) / 5.0F;
/*     */       } 
/* 349 */       RenderParameters.prevPitch = (mc.func_175606_aa()).field_70125_A;
/*     */     } 
/*     */     
/* 352 */     RenderParameters.GUN_ROT_X *= 0.2F;
/* 353 */     RenderParameters.GUN_ROT_Y *= 0.2F;
/* 354 */     RenderParameters.GUN_ROT_Z *= 0.2F;
/*     */     
/* 356 */     if (RenderParameters.GUN_ROT_X > 20.0F) {
/* 357 */       RenderParameters.GUN_ROT_X = 20.0F;
/* 358 */     } else if (RenderParameters.GUN_ROT_X < -20.0F) {
/* 359 */       RenderParameters.GUN_ROT_X = -20.0F;
/*     */     } 
/*     */     
/* 362 */     if (RenderParameters.GUN_ROT_Y > 20.0F) {
/* 363 */       RenderParameters.GUN_ROT_Y = 20.0F;
/* 364 */     } else if (RenderParameters.GUN_ROT_Y < -20.0F) {
/* 365 */       RenderParameters.GUN_ROT_Y = -20.0F;
/*     */     } 
/*     */     
/* 368 */     processGunChange();
/* 369 */     ItemGun.fireButtonHeld = Mouse.isButtonDown(0);
/*     */     
/* 371 */     if (GunUI.bulletSnapFade > 0.0F) {
/* 372 */       GunUI.bulletSnapFade -= 0.01F;
/*     */     }
/*     */     
/* 375 */     if (FlashSystem.flashValue > 0) {
/* 376 */       FlashSystem.flashValue -= 2;
/* 377 */     } else if (FlashSystem.flashValue < 0) {
/* 378 */       FlashSystem.flashValue = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClientTickEnd(Minecraft minecraft) {
/* 385 */     if (minecraft.field_71439_g == null || minecraft.field_71441_e == null) {
/*     */       return;
/*     */     }
/* 388 */     EntityPlayerSP player = minecraft.field_71439_g;
/*     */     
/* 390 */     if (RenderParameters.playerRecoilPitch > 0.0F || -RenderParameters.playerRecoilPitch > 0.0F) {
/* 391 */       RenderParameters.playerRecoilPitch *= 0.8F;
/*     */     }
/* 393 */     if (RenderParameters.playerRecoilYaw > 0.0F || -RenderParameters.playerRecoilYaw > 0.0F) {
/* 394 */       RenderParameters.playerRecoilYaw *= 0.8F;
/*     */     }
/* 396 */     player.field_70125_A -= RenderParameters.playerRecoilPitch;
/* 397 */     player.field_70177_z -= RenderParameters.playerRecoilYaw;
/* 398 */     RenderParameters.antiRecoilPitch += RenderParameters.playerRecoilPitch;
/* 399 */     if (RenderParameters.antiRecoilPitch >= 10.0F) {
/* 400 */       RenderParameters.antiRecoilPitch *= 0.9F;
/*     */     }
/* 402 */     RenderParameters.antiRecoilYaw += RenderParameters.playerRecoilYaw;
/*     */     
/* 404 */     player.field_70125_A += RenderParameters.antiRecoilPitch * 0.25F;
/* 405 */     player.field_70177_z += RenderParameters.antiRecoilYaw * 0.25F;
/*     */     
/* 407 */     RenderParameters.antiRecoilPitch *= 0.75F;
/* 408 */     RenderParameters.antiRecoilYaw *= 0.75F;
/*     */     
/* 410 */     if (!ItemGun.fireButtonHeld) {
/* 411 */       RenderParameters.rate = Math.max(RenderParameters.rate - 0.05F, 0.0F);
/*     */     }
/* 413 */     for (AnimStateMachine stateMachine : ClientRenderHooks.weaponBasicAnimations.values()) {
/* 414 */       stateMachine.onTickUpdate();
/*     */     }
/*     */     
/* 417 */     for (EnhancedStateMachine stateMachine : ClientRenderHooks.weaponEnhancedAnimations.values()) {
/* 418 */       stateMachine.onTickUpdate();
/*     */     }
/*     */     
/* 421 */     InstantBulletRenderer.UpdateAllTrails();
/*     */   }
/*     */ 
/*     */   
/*     */   public void processGunChange() {
/* 426 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/* 427 */     this; if (((EntityPlayer)entityPlayerSP).field_71071_by.field_70461_c != oldCurrentItem) {
/* 428 */       if (entityPlayerSP.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 429 */         GunType type = ((ItemGun)entityPlayerSP.func_184614_ca().func_77973_b()).type;
/* 430 */         type.playClientSound((EntityPlayer)entityPlayerSP, WeaponSoundType.Equip);
/* 431 */       } else if (entityPlayerSP.func_184614_ca().func_77973_b() instanceof com.modularwarfare.common.guns.ItemSpray) {
/* 432 */         ModularWarfare.PROXY.playSound(new MWSound(entityPlayerSP.func_180425_c(), "shake", 1.0F, 1.0F));
/* 433 */       } else if (entityPlayerSP.func_184614_ca().func_77973_b() instanceof com.modularwarfare.common.grenades.ItemGrenade) {
/* 434 */         ModularWarfare.PROXY.playSound(new MWSound(entityPlayerSP.func_180425_c(), "human.equip.extra", 1.0F, 1.0F));
/*     */       } 
/*     */     }
/* 437 */     this; if (oldCurrentItem != ((EntityPlayer)entityPlayerSP).field_71071_by.field_70461_c) {
/* 438 */       this; oldCurrentItem = ((EntityPlayer)entityPlayerSP).field_71071_by.field_70461_c;
/* 439 */       this; oldItemStack = entityPlayerSP.func_184614_ca();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\handler\ClientTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */