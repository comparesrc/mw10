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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientTickHandler
/*     */   extends ForgeEvent
/*     */ {
/*     */   private static final int SPS = 60;
/*  51 */   public static ConcurrentHashMap<UUID, Integer> playerShootCooldown = new ConcurrentHashMap<>();
/*  52 */   public static ConcurrentHashMap<UUID, Integer> playerReloadCooldown = new ConcurrentHashMap<>();
/*  53 */   public static ItemStack reloadEnhancedPrognosisAmmo = ItemStack.field_190927_a;
/*  54 */   public static ItemStack reloadEnhancedPrognosisAmmoRendering = ItemStack.field_190927_a;
/*     */   
/*     */   public static boolean reloadEnhancedIsQuickly = false;
/*     */   public static boolean reloadEnhancedIsQuicklyRendering = false;
/*     */   public static int oldCurrentItem;
/*  59 */   public static ItemStack oldItemStack = ItemStack.field_190927_a;
/*  60 */   public static ItemStack lastItemStack = ItemStack.field_190927_a;
/*     */   public static World lastWorld;
/*  62 */   int i = 0;
/*     */ 
/*     */   
/*     */   private static long lastSyncTime;
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void clientTick(TickEvent.ClientTickEvent event) {
/*  71 */     switch (event.phase) {
/*     */ 
/*     */       
/*     */       case START:
/*  75 */         if (lastWorld != (Minecraft.func_71410_x()).field_71441_e) {
/*  76 */           ClientRenderHooks.weaponEnhancedAnimations.clear();
/*  77 */           ClientRenderHooks.weaponBasicAnimations.clear();
/*  78 */           ClientProxy.gunEnhancedRenderer.getOtherControllers().clear();
/*  79 */           lastWorld = (World)(Minecraft.func_71410_x()).field_71441_e;
/*     */         } 
/*     */ 
/*     */         
/*  83 */         onClientTickStart(Minecraft.func_71410_x());
/*  84 */         ModularWarfare.NETWORK.handleClientPackets();
/*     */ 
/*     */         
/*  87 */         for (UUID uuid : playerShootCooldown.keySet()) {
/*  88 */           this.i++;
/*  89 */           int value = ((Integer)playerShootCooldown.get(uuid)).intValue() - 1;
/*  90 */           if (value <= 0) {
/*  91 */             playerShootCooldown.remove(uuid); continue;
/*     */           } 
/*  93 */           playerShootCooldown.replace(uuid, Integer.valueOf(value));
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  98 */         for (UUID uuid : playerReloadCooldown.keySet()) {
/*  99 */           this.i++;
/* 100 */           int value = ((Integer)playerReloadCooldown.get(uuid)).intValue() - 1;
/* 101 */           if (value <= 0) {
/* 102 */             playerReloadCooldown.remove(uuid); continue;
/*     */           } 
/* 104 */           playerReloadCooldown.replace(uuid, Integer.valueOf(value));
/*     */         } 
/*     */         break;
/*     */       
/*     */       case END:
/* 109 */         onClientTickEnd(Minecraft.func_71410_x());
/*     */         break;
/*     */     } 
/*     */   } @SubscribeEvent
/*     */   public void renderTick(TickEvent.RenderTickEvent event) {
/*     */     float renderTick;
/*     */     long time;
/* 116 */     switch (event.phase) {
/*     */       
/*     */       case START:
/* 119 */         renderTick = event.renderTickTime;
/* 120 */         renderTick = (float)(renderTick * 60.0D / Minecraft.func_175610_ah());
/* 121 */         StateEntry.smoothing = renderTick;
/* 122 */         onRenderTickStart(Minecraft.func_71410_x(), renderTick);
/*     */ 
/*     */ 
/*     */         
/* 126 */         time = System.currentTimeMillis();
/* 127 */         if (time > lastSyncTime + 6L) {
/* 128 */           if (lastSyncTime > 0L) {
/* 129 */             float stepTick = (float)(time - lastSyncTime) / 16.666666F;
/* 130 */             if (ClientProxy.gunEnhancedRenderer.getClientController() != null && 
/* 131 */               (Minecraft.func_71410_x()).field_71439_g != null && 
/* 132 */               (Minecraft.func_71410_x()).field_71439_g.func_184614_ca()
/* 133 */               .func_77973_b() instanceof ItemGun && 
/*     */               
/* 135 */               ((ItemGun)(Minecraft.func_71410_x()).field_71439_g.func_184614_ca().func_77973_b()).type.animationType.equals(WeaponAnimationType.ENHANCED)) {
/* 136 */               ClientProxy.gunEnhancedRenderer.getClientController().onTickRender(stepTick);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 142 */             ClientProxy.gunEnhancedRenderer.getOtherControllers().values().forEach(ctrl -> {
/*     */                   if (ctrl.player.func_184614_ca().func_77973_b() instanceof ItemGun && ((ItemGun)ctrl.player.func_184614_ca().func_77973_b()).type.animationType.equals(WeaponAnimationType.ENHANCED)) {
/*     */                     ctrl.onTickRender(stepTick);
/*     */                   }
/*     */                 });
/*     */ 
/*     */ 
/*     */             
/* 150 */             float offset = stepTick / 10.0F;
/* 151 */             if (Math.abs(ClientEventHandler.cemeraBobbing) < offset) {
/* 152 */               ClientEventHandler.cemeraBobbing = 0.0F;
/* 153 */             } else if (ClientEventHandler.cemeraBobbing > 0.0F) {
/* 154 */               ClientEventHandler.cemeraBobbing -= offset;
/*     */             } else {
/* 156 */               ClientEventHandler.cemeraBobbing += offset;
/*     */             } 
/*     */           } 
/* 159 */           lastSyncTime = time;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderTickStart(Minecraft minecraft, float renderTick) {
/* 167 */     if (minecraft.field_71439_g == null || minecraft.field_71441_e == null) {
/*     */       return;
/*     */     }
/* 170 */     if (minecraft.field_71417_B.field_74375_b > 0 || -minecraft.field_71417_B.field_74375_b > 1) {
/* 171 */       RenderParameters.antiRecoilPitch = (float)(RenderParameters.antiRecoilPitch * 0.25D);
/*     */     }
/* 173 */     if (minecraft.field_71417_B.field_74377_a > 2 || -minecraft.field_71417_B.field_74377_a > 2) {
/* 174 */       RenderParameters.antiRecoilYaw = (float)(RenderParameters.antiRecoilYaw * 0.25D);
/*     */     }
/* 176 */     EntityPlayerSP player = minecraft.field_71439_g;
/*     */     
/* 178 */     reloadEnhancedPrognosisAmmoRendering = reloadEnhancedPrognosisAmmo;
/* 179 */     reloadEnhancedIsQuicklyRendering = reloadEnhancedIsQuickly;
/*     */     
/* 181 */     OnTickRenderEvent event = new OnTickRenderEvent(renderTick);
/* 182 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (ClientProxy.gunEnhancedRenderer.getClientController() != null) {
/* 188 */       ClientProxy.gunEnhancedRenderer.getClientController().updateCurrentItem();
/*     */     }
/* 190 */     ClientProxy.gunEnhancedRenderer.getOtherControllers().values().forEach(ctrl -> ctrl.updateCurrentItem());
/*     */ 
/*     */ 
/*     */     
/* 194 */     for (EntityLivingBase entity : ClientRenderHooks.weaponEnhancedAnimations.keySet()) {
/* 195 */       if (entity != null) {
/* 196 */         ((EnhancedStateMachine)ClientRenderHooks.weaponEnhancedAnimations.get(entity)).updateCurrentItem(entity);
/*     */       }
/*     */     } 
/*     */     
/* 200 */     if (player.func_184614_ca() != null && player.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 201 */       float adsSpeed = 0.0F;
/* 202 */       if (((ItemGun)player.func_184614_ca().func_77973_b()).type.animationType.equals(WeaponAnimationType.BASIC)) {
/* 203 */         ModelGun model = (ModelGun)((ItemGun)player.func_184614_ca().func_77973_b()).type.model;
/* 204 */         if (!RenderParameters.lastModel.equalsIgnoreCase(model.getClass().getName())) {
/* 205 */           RenderParameters.resetRenderMods();
/* 206 */           RenderParameters.lastModel = model.getClass().getName();
/* 207 */           adsSpeed = model.config.extra.adsSpeed;
/*     */         } 
/* 209 */         AnimStateMachine anim = ClientRenderHooks.getAnimMachine((EntityLivingBase)player);
/*     */         
/* 211 */         float adsSpeedFinal = (0.1F + adsSpeed) * renderTick;
/* 212 */         boolean aimChargeMisc = !anim.reloading;
/* 213 */         float value = ((Minecraft.func_71410_x()).field_71415_G && Mouse.isButtonDown(1) && aimChargeMisc && !(ClientRenderHooks.getAnimMachine((EntityLivingBase)player)).attachmentMode) ? (RenderParameters.adsSwitch + adsSpeedFinal) : (RenderParameters.adsSwitch - adsSpeedFinal);
/* 214 */         RenderParameters.adsSwitch = Math.max(0.0F, Math.min(1.0F, value));
/*     */ 
/*     */         
/* 217 */         float sprintSpeed = 0.15F * renderTick;
/* 218 */         float sprintValue = (player.func_70051_ag() && !(ClientRenderHooks.getAnimMachine((EntityLivingBase)player)).attachmentMode) ? (RenderParameters.sprintSwitch + sprintSpeed) : (RenderParameters.sprintSwitch - sprintSpeed);
/* 219 */         RenderParameters.sprintSwitch = Math.max(0.0F, Math.min(1.0F, sprintValue));
/*     */ 
/*     */         
/* 222 */         float attachmentSpeed = 0.15F * renderTick;
/* 223 */         float attachmentValue = (ClientRenderHooks.getAnimMachine((EntityLivingBase)player)).attachmentMode ? (RenderParameters.attachmentSwitch + attachmentSpeed) : (RenderParameters.attachmentSwitch - attachmentSpeed);
/* 224 */         RenderParameters.attachmentSwitch = Math.max(0.0F, Math.min(1.0F, attachmentValue));
/*     */ 
/*     */         
/* 227 */         float crouchSpeed = 0.15F * renderTick;
/* 228 */         float crouchValue = player.func_70093_af() ? (RenderParameters.crouchSwitch + crouchSpeed) : (RenderParameters.crouchSwitch - crouchSpeed);
/* 229 */         RenderParameters.crouchSwitch = Math.max(0.0F, Math.min(1.0F, crouchValue));
/*     */ 
/*     */         
/* 232 */         float reloadSpeed = 0.15F * renderTick;
/* 233 */         float reloadValue = anim.reloading ? (RenderParameters.reloadSwitch - reloadSpeed) : (RenderParameters.reloadSwitch + reloadSpeed);
/* 234 */         RenderParameters.reloadSwitch = Math.max(0.0F, Math.min(1.0F, reloadValue));
/*     */ 
/*     */         
/* 237 */         float triggerPullSpeed = 0.03F * renderTick;
/* 238 */         float triggerPullValue = ((Minecraft.func_71410_x()).field_71415_G && Mouse.isButtonDown(0) && !(ClientRenderHooks.getAnimMachine((EntityLivingBase)player)).attachmentMode) ? (RenderParameters.triggerPullSwitch + triggerPullSpeed) : (RenderParameters.triggerPullSwitch - triggerPullSpeed);
/* 239 */         RenderParameters.triggerPullSwitch = Math.max(0.0F, Math.min(0.02F, triggerPullValue));
/*     */         
/* 241 */         float modeSwitchSpeed = 0.03F * renderTick;
/* 242 */         float modeSwitchValue = ((Minecraft.func_71410_x()).field_71415_G && Mouse.isButtonDown(0)) ? (RenderParameters.triggerPullSwitch + triggerPullSpeed) : (RenderParameters.triggerPullSwitch - triggerPullSpeed);
/* 243 */         RenderParameters.triggerPullSwitch = Math.max(0.0F, Math.min(0.02F, triggerPullValue));
/*     */       } 
/*     */       
/* 246 */       float balancing_speed_x = 0.08F * renderTick;
/* 247 */       if (player.field_70702_br > 0.0F) {
/* 248 */         RenderParameters.GUN_BALANCING_X = Math.min(1.0F, RenderParameters.GUN_BALANCING_X + balancing_speed_x);
/* 249 */       } else if (player.field_70702_br < 0.0F) {
/* 250 */         RenderParameters.GUN_BALANCING_X = Math.max(-1.0F, RenderParameters.GUN_BALANCING_X - balancing_speed_x);
/* 251 */       } else if (player.field_70702_br == 0.0F && RenderParameters.GUN_BALANCING_X != 0.0F) {
/* 252 */         if (RenderParameters.GUN_BALANCING_X > 0.0F) {
/* 253 */           RenderParameters.GUN_BALANCING_X = Math.max(0.0F, RenderParameters.GUN_BALANCING_X - balancing_speed_x);
/* 254 */         } else if (RenderParameters.GUN_BALANCING_X < 0.0F) {
/* 255 */           RenderParameters.GUN_BALANCING_X = Math.min(0.0F, RenderParameters.GUN_BALANCING_X + balancing_speed_x);
/*     */         } 
/*     */       } 
/*     */       
/* 259 */       float balancing_speed_y = 0.08F * renderTick;
/* 260 */       if (player.field_191988_bg > 0.0F) {
/* 261 */         RenderParameters.GUN_BALANCING_Y = Math.min(player.func_70051_ag() ? 3.0F : 1.0F, RenderParameters.GUN_BALANCING_Y + balancing_speed_y);
/* 262 */       } else if (player.field_191988_bg < 0.0F) {
/* 263 */         RenderParameters.GUN_BALANCING_Y = Math.max(-1.0F, RenderParameters.GUN_BALANCING_Y - balancing_speed_y);
/* 264 */       } else if (player.field_191988_bg == 0.0F && RenderParameters.GUN_BALANCING_Y != 0.0F) {
/* 265 */         if (RenderParameters.GUN_BALANCING_Y > 0.0F) {
/* 266 */           RenderParameters.GUN_BALANCING_Y = Math.max(0.0F, RenderParameters.GUN_BALANCING_Y - balancing_speed_y * 2.0F);
/* 267 */         } else if (RenderParameters.GUN_BALANCING_Y < 0.0F) {
/* 268 */           RenderParameters.GUN_BALANCING_Y = Math.min(0.0F, RenderParameters.GUN_BALANCING_Y + balancing_speed_y * 2.0F);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 274 */       if (player.field_71071_by.field_70461_c != oldCurrentItem) {
/* 275 */         if (oldItemStack.func_190926_b() || !(oldItemStack.func_77973_b() instanceof ItemGun)) {
/* 276 */           RenderParameters.GUN_CHANGE_Y = 0.5F;
/*     */         }
/* 278 */         if (RenderParameters.GUN_CHANGE_Y <= 0.5D) {
/* 279 */           if (!oldItemStack.func_190926_b() && oldItemStack.func_77973_b() instanceof ItemGun) {
/* 280 */             lastItemStack = oldItemStack;
/*     */           }
/* 282 */           RenderParameters.GUN_CHANGE_Y = 1.0F - RenderParameters.GUN_CHANGE_Y;
/*     */         } 
/*     */       } 
/* 285 */       if (RenderParameters.GUN_CHANGE_Y < 0.5D) {
/* 286 */         lastItemStack = ItemStack.field_190927_a;
/*     */       }
/* 288 */       float change_speed_y = 0.04F * renderTick;
/* 289 */       RenderParameters.GUN_CHANGE_Y = Math.max(0.0F, RenderParameters.GUN_CHANGE_Y - change_speed_y);
/*     */       
/* 291 */       Vec3d vecStart = player.func_174824_e(1.0F);
/* 292 */       RayTraceResult rayTraceResult = RayUtil.rayTrace((Entity)player, 5.0D, 1.0F);
/* 293 */       float recover = 0.1F;
/* 294 */       if (Mouse.isButtonDown(1)) {
/* 295 */         recover = 0.8F;
/*     */       }
/* 297 */       if (rayTraceResult != null) {
/* 298 */         if (rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 299 */           if (rayTraceResult.field_72307_f != null) {
/* 300 */             double d = vecStart.func_72438_d(rayTraceResult.field_72307_f);
/* 301 */             double testD = 1.5D;
/* 302 */             if (d <= testD) {
/* 303 */               RenderParameters.collideFrontDistance = (float)(RenderParameters.collideFrontDistance + (testD - d - RenderParameters.collideFrontDistance) * renderTick * 0.5D);
/*     */             } else {
/* 305 */               RenderParameters.collideFrontDistance = Math.max(0.0F, RenderParameters.collideFrontDistance - renderTick * recover);
/*     */             } 
/*     */           } else {
/* 308 */             RenderParameters.collideFrontDistance = Math.max(0.0F, RenderParameters.collideFrontDistance - renderTick * recover);
/*     */           } 
/*     */         } else {
/* 311 */           RenderParameters.collideFrontDistance = Math.max(0.0F, RenderParameters.collideFrontDistance - renderTick * recover);
/*     */         } 
/*     */       } else {
/* 314 */         RenderParameters.collideFrontDistance = Math.max(0.0F, RenderParameters.collideFrontDistance - renderTick * recover);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 320 */       for (AnimStateMachine stateMachine : ClientRenderHooks.weaponBasicAnimations.values()) {
/* 321 */         stateMachine.onRenderTickUpdate();
/*     */       }
/*     */     } else {
/* 324 */       RenderParameters.resetRenderMods();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClientTickStart(Minecraft minecraft) {
/* 330 */     if (minecraft.field_71439_g == null || minecraft.field_71441_e == null) {
/*     */       return;
/*     */     }
/* 333 */     ModularWarfare.PLAYERHANDLER.clientTick();
/*     */     
/* 335 */     RenderParameters.GUN_ROT_X_LAST = RenderParameters.GUN_ROT_X;
/* 336 */     RenderParameters.GUN_ROT_Y_LAST = RenderParameters.GUN_ROT_Y;
/* 337 */     RenderParameters.GUN_ROT_Z_LAST = RenderParameters.GUN_ROT_Z;
/*     */     
/* 339 */     Minecraft mc = FMLClientHandler.instance().getClient();
/*     */     
/* 341 */     if (mc.func_175606_aa() != null) {
/* 342 */       if (mc.func_175606_aa().func_70079_am() > (mc.func_175606_aa()).field_70126_B) {
/* 343 */         RenderParameters.GUN_ROT_X = (float)(RenderParameters.GUN_ROT_X + (mc.func_175606_aa().func_70079_am() - (mc.func_175606_aa()).field_70126_B) / 1.5D);
/* 344 */       } else if (mc.func_175606_aa().func_70079_am() < (mc.func_175606_aa()).field_70126_B) {
/* 345 */         RenderParameters.GUN_ROT_X = (float)(RenderParameters.GUN_ROT_X - ((mc.func_175606_aa()).field_70126_B - mc.func_175606_aa().func_70079_am()) / 1.5D);
/*     */       } 
/* 347 */       if ((mc.func_175606_aa()).field_70125_A > RenderParameters.prevPitch) {
/* 348 */         RenderParameters.GUN_ROT_Y += ((mc.func_175606_aa()).field_70125_A - RenderParameters.prevPitch) / 5.0F;
/* 349 */       } else if ((mc.func_175606_aa()).field_70125_A < RenderParameters.prevPitch) {
/* 350 */         RenderParameters.GUN_ROT_Y -= (RenderParameters.prevPitch - (mc.func_175606_aa()).field_70125_A) / 5.0F;
/*     */       } 
/* 352 */       RenderParameters.prevPitch = (mc.func_175606_aa()).field_70125_A;
/*     */     } 
/*     */     
/* 355 */     RenderParameters.GUN_ROT_X *= 0.2F;
/* 356 */     RenderParameters.GUN_ROT_Y *= 0.2F;
/* 357 */     RenderParameters.GUN_ROT_Z *= 0.2F;
/*     */     
/* 359 */     if (RenderParameters.GUN_ROT_X > 20.0F) {
/* 360 */       RenderParameters.GUN_ROT_X = 20.0F;
/* 361 */     } else if (RenderParameters.GUN_ROT_X < -20.0F) {
/* 362 */       RenderParameters.GUN_ROT_X = -20.0F;
/*     */     } 
/*     */     
/* 365 */     if (RenderParameters.GUN_ROT_Y > 20.0F) {
/* 366 */       RenderParameters.GUN_ROT_Y = 20.0F;
/* 367 */     } else if (RenderParameters.GUN_ROT_Y < -20.0F) {
/* 368 */       RenderParameters.GUN_ROT_Y = -20.0F;
/*     */     } 
/*     */     
/* 371 */     processGunChange();
/* 372 */     ItemGun.fireButtonHeld = Mouse.isButtonDown(0);
/*     */     
/* 374 */     if (GunUI.bulletSnapFade > 0.0F) {
/* 375 */       GunUI.bulletSnapFade -= 0.01F;
/*     */     }
/*     */     
/* 378 */     if (FlashSystem.flashValue > 0) {
/* 379 */       FlashSystem.flashValue -= 2;
/* 380 */     } else if (FlashSystem.flashValue < 0) {
/* 381 */       FlashSystem.flashValue = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClientTickEnd(Minecraft minecraft) {
/* 388 */     if (minecraft.field_71439_g == null || minecraft.field_71441_e == null) {
/*     */       return;
/*     */     }
/* 391 */     EntityPlayerSP player = minecraft.field_71439_g;
/*     */     
/* 393 */     if (RenderParameters.playerRecoilPitch > 0.0F || -RenderParameters.playerRecoilPitch > 0.0F) {
/* 394 */       RenderParameters.playerRecoilPitch *= 0.8F;
/*     */     }
/* 396 */     if (RenderParameters.playerRecoilYaw > 0.0F || -RenderParameters.playerRecoilYaw > 0.0F) {
/* 397 */       RenderParameters.playerRecoilYaw *= 0.8F;
/*     */     }
/* 399 */     player.field_70125_A -= RenderParameters.playerRecoilPitch;
/* 400 */     player.field_70177_z -= RenderParameters.playerRecoilYaw;
/* 401 */     RenderParameters.antiRecoilPitch += RenderParameters.playerRecoilPitch;
/* 402 */     if (RenderParameters.antiRecoilPitch >= 10.0F) {
/* 403 */       RenderParameters.antiRecoilPitch *= 0.9F;
/*     */     }
/* 405 */     RenderParameters.antiRecoilYaw += RenderParameters.playerRecoilYaw;
/*     */     
/* 407 */     player.field_70125_A += RenderParameters.antiRecoilPitch * 0.25F;
/* 408 */     player.field_70177_z += RenderParameters.antiRecoilYaw * 0.25F;
/*     */     
/* 410 */     RenderParameters.antiRecoilPitch *= 0.75F;
/* 411 */     RenderParameters.antiRecoilYaw *= 0.75F;
/*     */     
/* 413 */     if (!ItemGun.fireButtonHeld) {
/* 414 */       RenderParameters.rate = Math.max(RenderParameters.rate - 0.05F, 0.0F);
/*     */     }
/* 416 */     for (AnimStateMachine stateMachine : ClientRenderHooks.weaponBasicAnimations.values()) {
/* 417 */       stateMachine.onTickUpdate();
/*     */     }
/*     */     
/* 420 */     for (EnhancedStateMachine stateMachine : ClientRenderHooks.weaponEnhancedAnimations.values()) {
/* 421 */       stateMachine.onTickUpdate();
/*     */     }
/*     */     
/* 424 */     InstantBulletRenderer.UpdateAllTrails();
/*     */   }
/*     */ 
/*     */   
/*     */   public void processGunChange() {
/* 429 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/* 430 */     this; if (((EntityPlayer)entityPlayerSP).field_71071_by.field_70461_c != oldCurrentItem) {
/* 431 */       if (entityPlayerSP.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 432 */         GunType type = ((ItemGun)entityPlayerSP.func_184614_ca().func_77973_b()).type;
/* 433 */         if (type.animationType != WeaponAnimationType.ENHANCED && type.allowEquipSounds) {
/* 434 */           type.playClientSound((EntityPlayer)entityPlayerSP, WeaponSoundType.Equip);
/*     */         }
/* 436 */       } else if (entityPlayerSP.func_184614_ca().func_77973_b() instanceof com.modularwarfare.common.guns.ItemSpray) {
/* 437 */         ModularWarfare.PROXY.playSound(new MWSound(entityPlayerSP.func_180425_c(), "shake", 1.0F, 1.0F));
/* 438 */       } else if (entityPlayerSP.func_184614_ca().func_77973_b() instanceof com.modularwarfare.common.grenades.ItemGrenade) {
/* 439 */         ModularWarfare.PROXY.playSound(new MWSound(entityPlayerSP.func_180425_c(), "human.equip.extra", 1.0F, 1.0F));
/*     */       } 
/*     */     }
/* 442 */     this; if (oldCurrentItem != ((EntityPlayer)entityPlayerSP).field_71071_by.field_70461_c) {
/* 443 */       this; oldCurrentItem = ((EntityPlayer)entityPlayerSP).field_71071_by.field_70461_c;
/* 444 */       this; oldItemStack = entityPlayerSP.func_184614_ca();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\handler\ClientTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */