/*     */ package com.modularwarfare.client.fpp.enhanced.animation;
/*     */ 
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import com.modularwarfare.client.fpp.basic.animations.ReloadType;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderParameters;
/*     */ import com.modularwarfare.client.fpp.enhanced.AnimationType;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*     */ import com.modularwarfare.client.handler.ClientTickHandler;
/*     */ import com.modularwarfare.client.view.AutoSwitchToFirstView;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.WeaponAnimationType;
/*     */ import com.modularwarfare.common.guns.WeaponSoundType;
/*     */ import com.modularwarfare.utility.maths.Interpolation;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ public class AnimationController
/*     */ {
/*     */   public final EntityLivingBase player;
/*     */   private GunEnhancedRenderConfig config;
/*     */   private ActionPlayback playback;
/*     */   public double DEFAULT;
/*     */   public double DRAW;
/*     */   public double ADS;
/*     */   public double RELOAD;
/*     */   public double SPRINT;
/*     */   public double SPRINT_LOOP;
/*     */   public double SPRINT_RANDOM;
/*  57 */   public double INSPECT = 1.0D;
/*     */   
/*     */   public double FIRE;
/*     */   public double MODE_CHANGE;
/*     */   public double CUSTOM;
/*  62 */   public long sprintCoolTime = 0L;
/*  63 */   public long sprintLoopCoolTime = 0L;
/*     */   
/*     */   public int oldCurrentItem;
/*     */   
/*     */   public ItemStack oldItemstack;
/*     */   
/*     */   public boolean isJumping = false;
/*     */   public boolean nextResetDefault = false;
/*     */   public boolean hasPlayedDrawSound = true;
/*  72 */   public ISound inspectSound = null;
/*  73 */   public String customAnimation = "null";
/*     */   public double startTime;
/*     */   public double endTime;
/*  76 */   public double customAnimationSpeed = 1.0D;
/*     */   
/*     */   public boolean customAnimationReload = false;
/*     */   public boolean customAnimationFire = false;
/*  80 */   public static ISound drawSound = null;
/*     */   
/*  82 */   private static AnimationType[] RELOAD_TYPE = new AnimationType[] { AnimationType.PRE_LOAD, AnimationType.LOAD, AnimationType.POST_LOAD, AnimationType.PRE_UNLOAD, AnimationType.UNLOAD, AnimationType.POST_UNLOAD, AnimationType.PRE_RELOAD, AnimationType.PRE_RELOAD_EMPTY, AnimationType.RELOAD_FIRST, AnimationType.RELOAD_SECOND, AnimationType.RELOAD_FIRST_EMPTY, AnimationType.RELOAD_SECOND_EMPTY, AnimationType.RELOAD_FIRST_QUICKLY, AnimationType.RELOAD_SECOND_QUICKLY, AnimationType.POST_RELOAD, AnimationType.POST_RELOAD_EMPTY };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private static AnimationType[] FIRE_TYPE = new AnimationType[] { AnimationType.FIRE, AnimationType.FIRE_LAST, AnimationType.PRE_FIRE, AnimationType.POST_FIRE, AnimationType.POST_FIRE_EMPTY };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnimationController(EntityLivingBase player, GunEnhancedRenderConfig config) {
/*  95 */     this.config = config;
/*  96 */     this.playback = new ActionPlayback(this, config);
/*  97 */     this.playback.action = AnimationType.DEFAULT;
/*  98 */     this.player = player;
/*     */   }
/*     */   
/*     */   public void reset(boolean resetSprint) {
/* 102 */     this.DEFAULT = 0.0D;
/* 103 */     this.DRAW = 0.0D;
/* 104 */     this.hasPlayedDrawSound = false;
/* 105 */     this.ADS = 0.0D;
/* 106 */     this.RELOAD = 0.0D;
/* 107 */     if (resetSprint) {
/* 108 */       this.SPRINT = 0.0D;
/*     */     }
/* 110 */     this.SPRINT_LOOP = 0.0D;
/* 111 */     this.INSPECT = 1.0D;
/* 112 */     if (this.inspectSound != null) {
/* 113 */       Minecraft.func_71410_x().func_147118_V().func_147683_b(this.inspectSound);
/* 114 */       this.inspectSound = null;
/*     */     } 
/* 116 */     if (drawSound != null) {
/* 117 */       Minecraft.func_71410_x().func_147118_V().func_147683_b(drawSound);
/* 118 */       drawSound = null;
/*     */     } 
/* 120 */     this.FIRE = 0.0D;
/* 121 */     this.MODE_CHANGE = 1.0D;
/* 122 */     updateActionAndTime();
/*     */   }
/*     */   
/*     */   public void resetView() {
/* 126 */     this.INSPECT = 1.0D;
/* 127 */     this.MODE_CHANGE = 1.0D;
/*     */   }
/*     */   
/*     */   public void onTickRender(float stepTick) {
/* 131 */     if (this.config == null) {
/*     */       return;
/*     */     }
/* 134 */     long time = System.currentTimeMillis();
/* 135 */     EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine(this.player);
/* 136 */     float moveDistance = this.player.field_70140_Q - this.player.field_70141_P;
/*     */     
/* 138 */     double defaultSpeed = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.DEFAULT)).getSpeed(this.config.FPS) * stepTick;
/* 139 */     if (this.playback.action == AnimationType.DEFAULT_EMPTY) {
/* 140 */       defaultSpeed = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.DEFAULT_EMPTY)).getSpeed(this.config.FPS) * stepTick;
/*     */     }
/* 142 */     if (this.DEFAULT == 0.0D && this.DRAW == 1.0D && 
/* 143 */       this.player.func_184614_ca().func_77973_b() instanceof ItemGun && this.player instanceof EntityPlayer) {
/* 144 */       GunType type = ((ItemGun)this.player.func_184614_ca().func_77973_b()).type;
/* 145 */       if (this.playback.action == AnimationType.DEFAULT_EMPTY) {
/* 146 */         type.playClientSound((EntityPlayer)this.player, WeaponSoundType.IdleEmpty);
/*     */       } else {
/* 148 */         type.playClientSound((EntityPlayer)this.player, WeaponSoundType.Idle);
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     this.DEFAULT = Math.max(0.0D, this.DEFAULT + defaultSpeed);
/* 153 */     if (this.DEFAULT > 1.0D) {
/* 154 */       this.DEFAULT = 0.0D;
/*     */     }
/*     */ 
/*     */     
/* 158 */     double drawSpeed = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.DRAW)).getSpeed(this.config.FPS) * stepTick;
/* 159 */     if (this.playback.action == AnimationType.DRAW_EMPTY) {
/* 160 */       drawSpeed = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.DRAW_EMPTY)).getSpeed(this.config.FPS) * stepTick;
/*     */     }
/* 162 */     this.DRAW = Math.max(0.0D, this.DRAW + drawSpeed);
/* 163 */     if (this.DRAW > 1.0D) {
/* 164 */       this.DRAW = 1.0D;
/* 165 */       if (drawSound != null) {
/* 166 */         Minecraft.func_71410_x().func_147118_V().func_147683_b(drawSound);
/* 167 */         drawSound = null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 172 */     if (this.INSPECT == 0.0D && 
/* 173 */       this.player.func_184614_ca().func_77973_b() instanceof ItemGun && this.player instanceof EntityPlayer) {
/*     */       
/* 175 */       if (this.inspectSound != null) {
/* 176 */         Minecraft.func_71410_x().func_147118_V().func_147683_b(this.inspectSound);
/* 177 */         this.inspectSound = null;
/*     */       } 
/*     */       
/* 180 */       GunType type = ((ItemGun)this.player.func_184614_ca().func_77973_b()).type;
/* 181 */       SoundEvent se = type.getSound((EntityPlayer)this.player, WeaponSoundType.Inspect);
/* 182 */       if (!ItemGun.hasNextShot(this.player.func_184614_ca()) && 
/* 183 */         ((ItemGun)this.player.func_184614_ca().func_77973_b()).type.weaponSoundMap
/* 184 */         .containsKey(WeaponSoundType.InspectEmpty)) {
/* 185 */         se = type.getSound((EntityPlayer)this.player, WeaponSoundType.InspectEmpty);
/*     */       }
/* 187 */       if (se != null) {
/* 188 */         this.inspectSound = (ISound)PositionedSoundRecord.func_194007_a(se, 1.0F, 1.0F);
/* 189 */         Minecraft.func_71410_x().func_147118_V().func_147682_a(this.inspectSound);
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     if (this.INSPECT == 1.0D && 
/* 194 */       this.inspectSound != null) {
/* 195 */       Minecraft.func_71410_x().func_147118_V().func_147683_b(this.inspectSound);
/* 196 */       this.inspectSound = null;
/*     */     } 
/*     */     
/* 199 */     if (!this.config.animations.containsKey(AnimationType.INSPECT)) {
/* 200 */       this.INSPECT = 1.0D;
/*     */     } else {
/* 202 */       double modeChangeVal = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.INSPECT)).getSpeed(this.config.FPS) * stepTick;
/* 203 */       if (this.playback.action == AnimationType.INSPECT_EMPTY) {
/* 204 */         modeChangeVal = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.INSPECT_EMPTY)).getSpeed(this.config.FPS) * stepTick;
/*     */       }
/* 206 */       this.INSPECT += modeChangeVal;
/* 207 */       if (this.INSPECT >= 1.0D) {
/* 208 */         this.INSPECT = 1.0D;
/*     */       }
/*     */     } 
/* 211 */     if (this.CUSTOM < 1.0D) {
/*     */       try {
/* 213 */         AnimationType type = AnimationType.AnimationTypeJsonAdapter.fromString(this.customAnimation);
/* 214 */         this.CUSTOM += this.customAnimationSpeed * ((GunEnhancedRenderConfig.Animation)this.config.animations.get(type)).getSpeed(this.config.FPS) * stepTick;
/* 215 */       } catch (Exception e) {
/* 216 */         double a = this.endTime / this.config.FPS - this.startTime / this.config.FPS;
/* 217 */         if (a <= 0.0D) {
/* 218 */           a = 1.0D;
/*     */         }
/* 220 */         this.CUSTOM += this.customAnimationSpeed / a * stepTick;
/*     */       } 
/* 222 */       if (this.CUSTOM >= 1.0D) {
/* 223 */         this.CUSTOM = 1.0D;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 228 */     boolean aimChargeMisc = (ClientRenderHooks.getEnhancedAnimMachine(this.player)).reloading;
/* 229 */     double adsSpeed = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.AIM)).getSpeed(this.config.FPS) * stepTick;
/* 230 */     if (this.player.func_184614_ca().func_77973_b() instanceof ItemGun && this.player instanceof EntityPlayer && 
/* 231 */       GunType.getAttachment(this.player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Stock) != null) {
/*     */ 
/*     */ 
/*     */       
/* 235 */       ItemAttachment stockAttachment = (ItemAttachment)GunType.getAttachment(this.player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Stock).func_77973_b();
/* 236 */       adsSpeed *= stockAttachment.type.stock.aimSpeedFactor;
/*     */     } 
/*     */     
/* 239 */     if (this.player.func_184614_ca().func_77973_b() instanceof ItemGun && this.player instanceof EntityPlayer) {
/* 240 */       GunType gunType = ((ItemGun)this.player.func_184614_ca().func_77973_b()).type;
/*     */     }
/*     */     
/* 243 */     double val = 0.0D;
/* 244 */     if (RenderParameters.collideFrontDistance == 0.0F && (Minecraft.func_71410_x()).field_71415_G && (
/* 245 */       Mouse.isButtonDown(1) || AutoSwitchToFirstView.getAutoAimLock()) && !aimChargeMisc && this.INSPECT == 1.0D) {
/* 246 */       val = this.ADS + adsSpeed * (2.0D - this.ADS);
/*     */     } else {
/* 248 */       val = this.ADS - adsSpeed * (1.0D + this.ADS);
/*     */     } 
/* 250 */     if (this.player == (Minecraft.func_71410_x()).field_71439_g) {
/* 251 */       RenderParameters.adsSwitch = (float)this.ADS;
/*     */     }
/*     */     
/* 254 */     if (!isDrawing()) {
/* 255 */       this.ADS = Math.max(0.0D, Math.min(1.0D, val));
/*     */     } else {
/* 257 */       this.ADS = 0.0D;
/*     */     } 
/*     */     
/* 260 */     if (!anim.shooting) {
/* 261 */       this.FIRE = 0.0D;
/*     */     }
/*     */     
/* 264 */     if (!anim.reloading) {
/* 265 */       this.RELOAD = 0.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     double sprintSpeed = Math.sin(this.SPRINT * 3.14D) * 0.09000000357627869D;
/* 272 */     if (sprintSpeed < 0.029999999329447746D) {
/* 273 */       sprintSpeed = 0.029999999329447746D;
/*     */     }
/* 275 */     sprintSpeed *= stepTick;
/* 276 */     double sprintValue = 0.0D;
/*     */     
/* 278 */     if (this.player instanceof EntityPlayerSP) {
/* 279 */       if (((EntityPlayerSP)this.player).field_71158_b.field_78901_c) {
/* 280 */         this.isJumping = true;
/* 281 */       } else if (this.player.field_70122_E) {
/* 282 */         this.isJumping = false;
/*     */       } 
/*     */     }
/*     */     
/* 286 */     boolean flag = ((this.player.field_70122_E || this.player.field_70143_R < 2.0F) && !this.isJumping);
/*     */     
/* 288 */     if (this.player.func_70051_ag() && moveDistance > 0.05D && flag) {
/* 289 */       if (time > this.sprintCoolTime) {
/* 290 */         sprintValue = this.SPRINT + sprintSpeed;
/*     */       }
/*     */     } else {
/* 293 */       this.sprintCoolTime = time + 100L;
/* 294 */       sprintValue = this.SPRINT - sprintSpeed;
/*     */     } 
/* 296 */     if (anim.gunRecoil > 0.1F || this.ADS > 0.8D || this.RELOAD > 0.0D || this.INSPECT < 1.0D) {
/* 297 */       sprintValue = this.SPRINT - sprintSpeed * 2.5D;
/*     */     }
/*     */     
/* 300 */     this.SPRINT = Math.max(0.0D, Math.min(1.0D, sprintValue));
/*     */ 
/*     */     
/* 303 */     if (!this.config.animations.containsKey(AnimationType.SPRINT)) {
/* 304 */       this.SPRINT_LOOP = 0.0D;
/* 305 */       this.SPRINT_RANDOM = 0.0D;
/*     */     } else {
/* 307 */       double sprintLoopSpeed = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.SPRINT)).getSpeed(this.config.FPS) * stepTick * (moveDistance / 0.15F);
/*     */       
/* 309 */       boolean flagSprintRand = false;
/* 310 */       if (flag) {
/* 311 */         if (time > this.sprintLoopCoolTime && 
/* 312 */           this.player.func_70051_ag()) {
/* 313 */           this.SPRINT_LOOP += sprintLoopSpeed;
/* 314 */           this.SPRINT_RANDOM += sprintLoopSpeed;
/* 315 */           flagSprintRand = true;
/*     */         } 
/*     */       } else {
/*     */         
/* 319 */         this.sprintLoopCoolTime = time + 100L;
/*     */       } 
/* 321 */       if (!flagSprintRand) {
/* 322 */         this.SPRINT_RANDOM -= ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.SPRINT)).getSpeed(this.config.FPS) * 3.0D * stepTick;
/*     */       }
/* 324 */       if (this.SPRINT_LOOP > 1.0D) {
/* 325 */         this.SPRINT_LOOP = 0.0D;
/*     */       }
/* 327 */       if (this.SPRINT_RANDOM > 1.0D) {
/* 328 */         this.SPRINT_RANDOM = 0.0D;
/*     */       }
/* 330 */       if (this.SPRINT_RANDOM < 0.0D) {
/* 331 */         this.SPRINT_RANDOM = 0.0D;
/*     */       }
/* 333 */       if (Double.isNaN(this.SPRINT_RANDOM)) {
/* 334 */         this.SPRINT_RANDOM = 0.0D;
/*     */       }
/*     */     } 
/*     */     
/* 338 */     if (!this.config.animations.containsKey(AnimationType.MODE_CHANGE)) {
/* 339 */       this.MODE_CHANGE = 1.0D;
/*     */     } else {
/* 341 */       double modeChangeVal = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.MODE_CHANGE)).getSpeed(this.config.FPS) * stepTick;
/* 342 */       this.MODE_CHANGE += modeChangeVal;
/* 343 */       if (this.MODE_CHANGE >= 1.0D) {
/* 344 */         this.MODE_CHANGE = 1.0D;
/*     */       }
/*     */     } 
/*     */     
/* 348 */     ClientRenderHooks.getEnhancedAnimMachine(this.player).onRenderTickUpdate(stepTick);
/*     */     
/* 350 */     updateActionAndTime();
/*     */   }
/*     */   
/*     */   public AnimationType getPlayingAnimation() {
/* 354 */     return this.playback.action;
/*     */   }
/*     */   
/*     */   public void updateCurrentItem() {
/* 358 */     if (this.config == null) {
/*     */       return;
/*     */     }
/* 361 */     if (!(this.player instanceof EntityPlayer)) {
/*     */       return;
/*     */     }
/* 364 */     ItemStack stack = this.player.func_184614_ca();
/* 365 */     Item item = stack.func_77973_b();
/* 366 */     if (item instanceof ItemGun) {
/* 367 */       GunType type = ((ItemGun)item).type;
/* 368 */       if (!type.allowAimingSprint && this.ADS > 0.20000000298023224D) {
/* 369 */         this.player.func_70031_b(false);
/*     */       }
/* 371 */       if (!type.allowReloadingSprint && this.RELOAD > 0.0D) {
/* 372 */         this.player.func_70031_b(false);
/*     */       }
/* 374 */       if (!type.allowFiringSprint && this.FIRE > 0.0D) {
/* 375 */         this.player.func_70031_b(false);
/*     */       }
/* 377 */       if (this.ADS == 1.0D) {
/* 378 */         if (!ClientRenderHooks.isAiming) {
/* 379 */           ClientRenderHooks.isAiming = true;
/*     */         }
/*     */       } else {
/* 382 */         if (ClientRenderHooks.isAiming) {
/* 383 */           ClientRenderHooks.isAiming = false;
/*     */         }
/* 385 */         if (ClientRenderHooks.isAimingScope) {
/* 386 */           ClientRenderHooks.isAimingScope = false;
/*     */         }
/*     */       } 
/*     */     } 
/* 390 */     boolean resetFlag = false;
/* 391 */     if (this.oldCurrentItem != ((EntityPlayer)this.player).field_71071_by.field_70461_c) {
/* 392 */       resetFlag = true;
/* 393 */       this.oldCurrentItem = ((EntityPlayer)this.player).field_71071_by.field_70461_c;
/*     */     } 
/* 395 */     if (this.oldItemstack != this.player.func_184614_ca()) {
/* 396 */       if (this.oldItemstack == null || this.oldItemstack.func_190926_b()) {
/* 397 */         resetFlag = true;
/*     */       }
/* 399 */       this.oldItemstack = this.player.func_184614_ca();
/*     */     } 
/* 401 */     if (resetFlag) {
/* 402 */       reset(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateAction() {
/* 407 */     EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine(this.player);
/* 408 */     boolean flag = this.nextResetDefault;
/* 409 */     this.nextResetDefault = false;
/* 410 */     if (this.CUSTOM < 1.0D) {
/* 411 */       resetView();
/* 412 */       this.playback.action = AnimationType.CUSTOM;
/* 413 */     } else if (this.DRAW < 1.0D) {
/* 414 */       if (!this.hasPlayedDrawSound) {
/* 415 */         Item item = this.player.func_184614_ca().func_77973_b();
/* 416 */         if (item instanceof ItemGun) {
/* 417 */           if (!((Minecraft.func_71410_x()).field_71462_r instanceof com.modularwarfare.client.gui.GuiGunModify) && this.player instanceof EntityPlayer) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 423 */             if (drawSound != null) {
/* 424 */               Minecraft.func_71410_x().func_147118_V().func_147683_b(drawSound);
/* 425 */               drawSound = null;
/*     */             } 
/* 427 */             GunType type = ((ItemGun)this.player.func_184614_ca().func_77973_b()).type;
/* 428 */             if (type.animationType == WeaponAnimationType.ENHANCED && this.config == type.enhancedModel.config) {
/* 429 */               SoundEvent se = type.getSound((EntityPlayer)this.player, WeaponSoundType.Draw);
/* 430 */               if (!ItemGun.hasNextShot(this.player.func_184614_ca()) && 
/* 431 */                 ((ItemGun)this.player.func_184614_ca().func_77973_b()).type.weaponSoundMap
/* 432 */                 .containsKey(WeaponSoundType.DrawEmpty)) {
/* 433 */                 se = type.getSound((EntityPlayer)this.player, WeaponSoundType.DrawEmpty);
/*     */               }
/* 435 */               if (se != null) {
/* 436 */                 drawSound = (ISound)PositionedSoundRecord.func_194007_a(se, 1.0F, 1.0F);
/* 437 */                 Minecraft.func_71410_x().func_147118_V().func_147682_a(drawSound);
/*     */               } 
/*     */             } 
/*     */           } 
/* 441 */           this.hasPlayedDrawSound = true;
/*     */         } 
/*     */       } 
/* 444 */       this.playback.action = AnimationType.DRAW;
/* 445 */       if (!ItemGun.hasNextShot(this.player.func_184614_ca()) && 
/* 446 */         this.config.animations.containsKey(AnimationType.DRAW_EMPTY)) {
/* 447 */         this.playback.action = AnimationType.DRAW_EMPTY;
/*     */       }
/*     */     }
/* 450 */     else if (this.RELOAD > 0.0D) {
/* 451 */       resetView();
/* 452 */       this.playback.action = anim.getReloadAnimationType();
/* 453 */     } else if (this.FIRE > 0.0D) {
/* 454 */       resetView();
/* 455 */       this.playback.action = anim.getShootingAnimationType();
/* 456 */     } else if (this.INSPECT < 1.0D) {
/* 457 */       this.playback.action = AnimationType.INSPECT;
/* 458 */       if (!ItemGun.hasNextShot(this.player.func_184614_ca()) && 
/* 459 */         this.config.animations.containsKey(AnimationType.INSPECT_EMPTY)) {
/* 460 */         this.playback.action = AnimationType.INSPECT_EMPTY;
/*     */       }
/*     */     }
/* 463 */     else if (this.MODE_CHANGE < 1.0D) {
/* 464 */       this.playback.action = AnimationType.MODE_CHANGE;
/* 465 */     } else if (this.playback.hasPlayed || (this.playback.action != AnimationType.DEFAULT && this.playback.action != AnimationType.DEFAULT_EMPTY)) {
/* 466 */       if (flag) {
/* 467 */         this.playback.action = AnimationType.DEFAULT;
/*     */       }
/* 469 */       this.nextResetDefault = true;
/*     */     } 
/* 471 */     if (this.playback.action == AnimationType.DEFAULT && 
/* 472 */       !ItemGun.hasNextShot(this.player.func_184614_ca()) && 
/* 473 */       this.config.animations.containsKey(AnimationType.DEFAULT_EMPTY)) {
/* 474 */       this.playback.action = AnimationType.DEFAULT_EMPTY;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTime() {
/* 482 */     EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine(this.player);
/* 483 */     if (this.playback.action == null) {
/*     */       return;
/*     */     }
/* 486 */     switch (this.playback.action) {
/*     */       case CUSTOM:
/* 488 */         this.playback.updateTime(this.CUSTOM);
/*     */         break;
/*     */       case DEFAULT:
/* 491 */         this.playback.updateTime(this.DEFAULT);
/*     */         break;
/*     */       case DEFAULT_EMPTY:
/* 494 */         this.playback.updateTime(this.DEFAULT);
/*     */         break;
/*     */       case DRAW:
/* 497 */         this.playback.updateTime(this.DRAW);
/*     */         break;
/*     */       case DRAW_EMPTY:
/* 500 */         this.playback.updateTime(this.DRAW);
/*     */         break;
/*     */       case INSPECT:
/* 503 */         this.playback.updateTime(this.INSPECT);
/*     */         break;
/*     */       case INSPECT_EMPTY:
/* 506 */         this.playback.updateTime(this.INSPECT);
/*     */         break;
/*     */       case MODE_CHANGE:
/* 509 */         this.playback.updateTime(this.MODE_CHANGE);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 514 */     for (AnimationType reloadType : RELOAD_TYPE) {
/* 515 */       if (this.playback.action == reloadType) {
/* 516 */         this.playback.updateTime(this.RELOAD);
/*     */         break;
/*     */       } 
/*     */     } 
/* 520 */     for (AnimationType fireType : FIRE_TYPE) {
/* 521 */       if (this.playback.action == fireType) {
/* 522 */         this.playback.updateTime(this.FIRE);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateActionAndTime() {
/* 529 */     updateAction();
/* 530 */     updateTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTime() {
/* 535 */     return (float)this.playback.time;
/*     */   }
/*     */   
/*     */   public float getSprintTime() {
/* 539 */     if (this.config.animations.get(AnimationType.SPRINT) == null) {
/* 540 */       return 0.0F;
/*     */     }
/* 542 */     double startTime = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.SPRINT)).getStartTime(this.config.FPS);
/* 543 */     double endTime = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.SPRINT)).getEndTime(this.config.FPS);
/* 544 */     double result = Interpolation.LINEAR.interpolate(startTime, endTime, this.SPRINT_LOOP);
/* 545 */     if (Double.isNaN(result)) {
/* 546 */       return 0.0F;
/*     */     }
/* 548 */     return (float)result;
/*     */   }
/*     */   
/*     */   public void setConfig(GunEnhancedRenderConfig config) {
/* 552 */     this.config = config;
/*     */   }
/*     */   
/*     */   public GunEnhancedRenderConfig getConfig() {
/* 556 */     return this.config;
/*     */   }
/*     */   
/*     */   public boolean isDrawing() {
/* 560 */     if (this.player == null) {
/* 561 */       return false;
/*     */     }
/* 563 */     Item item = this.player.func_184614_ca().func_77973_b();
/* 564 */     if (item instanceof ItemGun && 
/* 565 */       ((ItemGun)item).type.animationType.equals(WeaponAnimationType.ENHANCED)) {
/* 566 */       return (this.playback.action == AnimationType.DRAW);
/*     */     }
/*     */     
/* 569 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isCouldReload() {
/* 573 */     if (this.player == null) {
/* 574 */       return true;
/*     */     }
/* 576 */     Item item = this.player.func_184614_ca().func_77973_b();
/* 577 */     if (item instanceof ItemGun && 
/* 578 */       ((ItemGun)item).type.animationType.equals(WeaponAnimationType.ENHANCED)) {
/* 579 */       if (isDrawing()) {
/* 580 */         return false;
/*     */       }
/* 582 */       if ((ClientRenderHooks.getEnhancedAnimMachine(this.player)).reloading) {
/* 583 */         return false;
/*     */       }
/* 585 */       if (((ItemGun)item).type.restrictingFireAnimation || ((ItemGun)item).type.firingReload || 
/* 586 */         !ItemGun.hasNextShot(this.player.func_184614_ca())) {
/* 587 */         for (AnimationType fireType : FIRE_TYPE) {
/* 588 */           if (this.playback.action == fireType) {
/* 589 */             return false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 595 */     if (!this.customAnimationReload && this.CUSTOM < 1.0D) {
/* 596 */       return false;
/*     */     }
/* 598 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isCouldShoot() {
/* 602 */     if (this.player == null) {
/* 603 */       return true;
/*     */     }
/* 605 */     Item item = this.player.func_184614_ca().func_77973_b();
/* 606 */     if (item instanceof ItemGun && 
/* 607 */       ((ItemGun)item).type.animationType.equals(WeaponAnimationType.ENHANCED)) {
/* 608 */       if (isDrawing()) {
/* 609 */         return false;
/*     */       }
/* 611 */       if ((ClientRenderHooks.getEnhancedAnimMachine(this.player)).reloading) {
/* 612 */         return false;
/*     */       }
/* 614 */       if (((ItemGun)item).type.restrictingFireAnimation || 
/* 615 */         !ItemGun.hasNextShot(this.player.func_184614_ca())) {
/* 616 */         for (AnimationType fireType : FIRE_TYPE) {
/* 617 */           if (this.playback.action == fireType) {
/* 618 */             return false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 624 */     if (!this.customAnimationFire && this.CUSTOM < 1.0D) {
/* 625 */       return false;
/*     */     }
/* 627 */     return true;
/*     */   }
/*     */   
/*     */   public ItemStack getRenderAmmo(ItemStack ammo) {
/* 631 */     EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine(this.player);
/* 632 */     if (anim.reloading) {
/* 633 */       AnimationType reloadAni = anim.getReloadAnimationType();
/* 634 */       if (anim.getReloadType() == ReloadType.Full && (reloadAni == AnimationType.PRE_RELOAD || reloadAni == AnimationType.RELOAD_FIRST || reloadAni == AnimationType.RELOAD_FIRST_QUICKLY))
/*     */       {
/* 636 */         return ammo;
/*     */       }
/* 638 */       if (reloadAni == AnimationType.PRE_UNLOAD || reloadAni == AnimationType.UNLOAD || reloadAni == AnimationType.POST_UNLOAD) {
/* 639 */         return ammo;
/*     */       }
/*     */     } 
/* 642 */     if (ClientTickHandler.reloadEnhancedPrognosisAmmoRendering != null && 
/* 643 */       !ClientTickHandler.reloadEnhancedPrognosisAmmoRendering.func_190926_b()) {
/* 644 */       return ClientTickHandler.reloadEnhancedPrognosisAmmoRendering;
/*     */     }
/* 646 */     return ammo;
/*     */   }
/*     */   
/*     */   public boolean shouldRenderAmmo() {
/* 650 */     EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine(this.player);
/* 651 */     if (anim.reloading) {
/* 652 */       if (anim.getReloadAnimationType() == AnimationType.POST_UNLOAD) {
/* 653 */         return false;
/*     */       }
/* 655 */       return true;
/*     */     } 
/* 657 */     if (ClientTickHandler.reloadEnhancedPrognosisAmmoRendering != null && 
/* 658 */       !ClientTickHandler.reloadEnhancedPrognosisAmmoRendering.func_190926_b()) {
/* 659 */       return true;
/*     */     }
/* 661 */     return ItemGun.hasAmmoLoaded(this.player.func_184614_ca());
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\enhanced\animation\AnimationController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */