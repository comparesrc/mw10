/*     */ package com.modularwarfare.client.fpp.enhanced.animation;
/*     */ 
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import com.modularwarfare.client.fpp.basic.animations.ReloadType;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderParameters;
/*     */ import com.modularwarfare.client.fpp.enhanced.AnimationType;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*     */ import com.modularwarfare.client.handler.ClientTickHandler;
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
/*  55 */   public double INSPECT = 1.0D;
/*     */   
/*     */   public double FIRE;
/*     */   public double MODE_CHANGE;
/*  59 */   public long sprintCoolTime = 0L;
/*  60 */   public long sprintLoopCoolTime = 0L;
/*     */   
/*     */   public int oldCurrentItem;
/*     */   
/*     */   public ItemStack oldItemstack;
/*     */   
/*     */   public boolean isJumping = false;
/*     */   public boolean nextResetDefault = false;
/*     */   public boolean hasPlayedDrawSound = true;
/*  69 */   public ISound inspectSound = null;
/*     */   
/*  71 */   private static AnimationType[] RELOAD_TYPE = new AnimationType[] { AnimationType.PRE_LOAD, AnimationType.LOAD, AnimationType.POST_LOAD, AnimationType.PRE_UNLOAD, AnimationType.UNLOAD, AnimationType.POST_UNLOAD, AnimationType.PRE_RELOAD, AnimationType.PRE_RELOAD_EMPTY, AnimationType.RELOAD_FIRST, AnimationType.RELOAD_SECOND, AnimationType.RELOAD_FIRST_EMPTY, AnimationType.RELOAD_SECOND_EMPTY, AnimationType.RELOAD_FIRST_QUICKLY, AnimationType.RELOAD_SECOND_QUICKLY, AnimationType.POST_RELOAD, AnimationType.POST_RELOAD_EMPTY };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private static AnimationType[] FIRE_TYPE = new AnimationType[] { AnimationType.FIRE, AnimationType.FIRE_LAST, AnimationType.PRE_FIRE, AnimationType.POST_FIRE, AnimationType.POST_FIRE_EMPTY };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnimationController(EntityLivingBase player, GunEnhancedRenderConfig config) {
/*  85 */     this.config = config;
/*  86 */     this.playback = new ActionPlayback(config);
/*  87 */     this.playback.action = AnimationType.DEFAULT;
/*  88 */     this.player = player;
/*     */   }
/*     */   
/*     */   public void reset(boolean resetSprint) {
/*  92 */     this.DEFAULT = 0.0D;
/*  93 */     this.DRAW = 0.0D;
/*  94 */     this.hasPlayedDrawSound = false;
/*  95 */     this.ADS = 0.0D;
/*  96 */     this.RELOAD = 0.0D;
/*  97 */     if (resetSprint) {
/*  98 */       this.SPRINT = 0.0D;
/*     */     }
/* 100 */     this.SPRINT_LOOP = 0.0D;
/* 101 */     this.INSPECT = 1.0D;
/* 102 */     if (this.inspectSound != null) {
/* 103 */       Minecraft.func_71410_x().func_147118_V().func_147683_b(this.inspectSound);
/* 104 */       this.inspectSound = null;
/*     */     } 
/* 106 */     this.FIRE = 0.0D;
/* 107 */     this.MODE_CHANGE = 1.0D;
/* 108 */     updateActionAndTime();
/*     */   }
/*     */   
/*     */   public void resetView() {
/* 112 */     this.INSPECT = 1.0D;
/* 113 */     this.MODE_CHANGE = 1.0D;
/*     */   }
/*     */   
/*     */   public void onTickRender(float stepTick) {
/* 117 */     if (this.config == null) {
/*     */       return;
/*     */     }
/* 120 */     long time = System.currentTimeMillis();
/* 121 */     EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine(this.player);
/* 122 */     float moveDistance = this.player.field_70140_Q - this.player.field_70141_P;
/*     */     
/* 124 */     double defaultSpeed = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.DEFAULT)).getSpeed(this.config.FPS) * stepTick;
/* 125 */     if (this.playback.action == AnimationType.DEFAULT_EMPTY) {
/* 126 */       defaultSpeed = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.DEFAULT_EMPTY)).getSpeed(this.config.FPS) * stepTick;
/*     */     }
/* 128 */     if (this.DEFAULT == 0.0D && this.DRAW == 1.0D && 
/* 129 */       this.player.func_184614_ca().func_77973_b() instanceof ItemGun && this.player instanceof EntityPlayer) {
/* 130 */       GunType type = ((ItemGun)this.player.func_184614_ca().func_77973_b()).type;
/* 131 */       if (this.playback.action == AnimationType.DEFAULT_EMPTY) {
/* 132 */         type.playClientSound((EntityPlayer)this.player, WeaponSoundType.IdleEmpty);
/*     */       } else {
/* 134 */         type.playClientSound((EntityPlayer)this.player, WeaponSoundType.Idle);
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     this.DEFAULT = Math.max(0.0D, this.DEFAULT + defaultSpeed);
/* 139 */     if (this.DEFAULT > 1.0D) {
/* 140 */       this.DEFAULT = 0.0D;
/*     */     }
/*     */ 
/*     */     
/* 144 */     double drawSpeed = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.DRAW)).getSpeed(this.config.FPS) * stepTick;
/* 145 */     if (this.playback.action == AnimationType.DRAW_EMPTY) {
/* 146 */       drawSpeed = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.DRAW_EMPTY)).getSpeed(this.config.FPS) * stepTick;
/*     */     }
/* 148 */     this.DRAW = Math.max(0.0D, this.DRAW + drawSpeed);
/* 149 */     if (this.DRAW > 1.0D) {
/* 150 */       this.DRAW = 1.0D;
/*     */     }
/*     */ 
/*     */     
/* 154 */     if (this.INSPECT == 0.0D && 
/* 155 */       this.player.func_184614_ca().func_77973_b() instanceof ItemGun && this.player instanceof EntityPlayer) {
/* 156 */       GunType type = ((ItemGun)this.player.func_184614_ca().func_77973_b()).type;
/* 157 */       SoundEvent se = type.getSound((EntityPlayer)this.player, WeaponSoundType.Inspect);
/* 158 */       if (!ItemGun.hasNextShot(this.player.func_184614_ca()) && 
/* 159 */         ((ItemGun)this.player.func_184614_ca().func_77973_b()).type.weaponSoundMap
/* 160 */         .containsKey(WeaponSoundType.InspectEmpty)) {
/* 161 */         se = type.getSound((EntityPlayer)this.player, WeaponSoundType.InspectEmpty);
/*     */       }
/* 163 */       if (se != null) {
/* 164 */         this.inspectSound = (ISound)PositionedSoundRecord.func_194007_a(se, 1.0F, 1.0F);
/* 165 */         Minecraft.func_71410_x().func_147118_V().func_147682_a(this.inspectSound);
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     if (this.INSPECT == 1.0D && 
/* 170 */       this.inspectSound != null) {
/* 171 */       Minecraft.func_71410_x().func_147118_V().func_147683_b(this.inspectSound);
/* 172 */       this.inspectSound = null;
/*     */     } 
/*     */     
/* 175 */     if (!this.config.animations.containsKey(AnimationType.INSPECT)) {
/* 176 */       this.INSPECT = 1.0D;
/*     */     } else {
/* 178 */       double modeChangeVal = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.INSPECT)).getSpeed(this.config.FPS) * stepTick;
/* 179 */       if (this.playback.action == AnimationType.INSPECT_EMPTY) {
/* 180 */         modeChangeVal = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.INSPECT_EMPTY)).getSpeed(this.config.FPS) * stepTick;
/*     */       }
/* 182 */       this.INSPECT += modeChangeVal;
/* 183 */       if (this.INSPECT >= 1.0D) {
/* 184 */         this.INSPECT = 1.0D;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 189 */     boolean aimChargeMisc = (ClientRenderHooks.getEnhancedAnimMachine(this.player)).reloading;
/* 190 */     double adsSpeed = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.AIM)).getSpeed(this.config.FPS) * stepTick;
/* 191 */     if (this.player.func_184614_ca().func_77973_b() instanceof ItemGun && this.player instanceof EntityPlayer && 
/* 192 */       GunType.getAttachment(this.player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Stock) != null) {
/*     */ 
/*     */ 
/*     */       
/* 196 */       ItemAttachment stockAttachment = (ItemAttachment)GunType.getAttachment(this.player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Stock).func_77973_b();
/* 197 */       adsSpeed *= stockAttachment.type.stock.aimSpeedFactor;
/*     */     } 
/*     */     
/* 200 */     if (this.player.func_184614_ca().func_77973_b() instanceof ItemGun && this.player instanceof EntityPlayer) {
/* 201 */       GunType gunType = ((ItemGun)this.player.func_184614_ca().func_77973_b()).type;
/*     */     }
/*     */     
/* 204 */     double val = 0.0D;
/* 205 */     if (RenderParameters.collideFrontDistance == 0.0F && (Minecraft.func_71410_x()).field_71415_G && 
/* 206 */       Mouse.isButtonDown(1) && !aimChargeMisc && this.INSPECT == 1.0D) {
/* 207 */       val = this.ADS + adsSpeed * (2.0D - this.ADS);
/*     */     } else {
/* 209 */       val = this.ADS - adsSpeed * (1.0D + this.ADS);
/*     */     } 
/* 211 */     if (this.player == (Minecraft.func_71410_x()).field_71439_g) {
/* 212 */       RenderParameters.adsSwitch = (float)this.ADS;
/*     */     }
/*     */     
/* 215 */     if (!isDrawing()) {
/* 216 */       this.ADS = Math.max(0.0D, Math.min(1.0D, val));
/*     */     } else {
/* 218 */       this.ADS = 0.0D;
/*     */     } 
/*     */     
/* 221 */     if (!anim.shooting) {
/* 222 */       this.FIRE = 0.0D;
/*     */     }
/*     */     
/* 225 */     if (!anim.reloading) {
/* 226 */       this.RELOAD = 0.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     double sprintSpeed = Math.sin(this.SPRINT * 3.14D) * 0.09000000357627869D;
/* 233 */     if (sprintSpeed < 0.029999999329447746D) {
/* 234 */       sprintSpeed = 0.029999999329447746D;
/*     */     }
/* 236 */     sprintSpeed *= stepTick;
/* 237 */     double sprintValue = 0.0D;
/*     */     
/* 239 */     if (this.player instanceof EntityPlayerSP) {
/* 240 */       if (((EntityPlayerSP)this.player).field_71158_b.field_78901_c) {
/* 241 */         this.isJumping = true;
/* 242 */       } else if (this.player.field_70122_E) {
/* 243 */         this.isJumping = false;
/*     */       } 
/*     */     }
/*     */     
/* 247 */     boolean flag = ((this.player.field_70122_E || this.player.field_70143_R < 2.0F) && !this.isJumping);
/*     */     
/* 249 */     if (this.player.func_70051_ag() && moveDistance > 0.05D && flag) {
/* 250 */       if (time > this.sprintCoolTime) {
/* 251 */         sprintValue = this.SPRINT + sprintSpeed;
/*     */       }
/*     */     } else {
/* 254 */       this.sprintCoolTime = time + 100L;
/* 255 */       sprintValue = this.SPRINT - sprintSpeed;
/*     */     } 
/* 257 */     if (anim.gunRecoil > 0.1F || this.ADS > 0.8D || this.RELOAD > 0.0D || this.INSPECT < 1.0D) {
/* 258 */       sprintValue = this.SPRINT - sprintSpeed * 2.5D;
/*     */     }
/*     */     
/* 261 */     this.SPRINT = Math.max(0.0D, Math.min(1.0D, sprintValue));
/*     */ 
/*     */     
/* 264 */     if (!this.config.animations.containsKey(AnimationType.SPRINT)) {
/* 265 */       this.SPRINT_LOOP = 0.0D;
/* 266 */       this.SPRINT_RANDOM = 0.0D;
/*     */     } else {
/* 268 */       double sprintLoopSpeed = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.SPRINT)).getSpeed(this.config.FPS) * stepTick * (moveDistance / 0.15F);
/*     */       
/* 270 */       boolean flagSprintRand = false;
/* 271 */       if (flag) {
/* 272 */         if (time > this.sprintLoopCoolTime && 
/* 273 */           this.player.func_70051_ag()) {
/* 274 */           this.SPRINT_LOOP += sprintLoopSpeed;
/* 275 */           this.SPRINT_RANDOM += sprintLoopSpeed;
/* 276 */           flagSprintRand = true;
/*     */         } 
/*     */       } else {
/*     */         
/* 280 */         this.sprintLoopCoolTime = time + 100L;
/*     */       } 
/* 282 */       if (!flagSprintRand) {
/* 283 */         this.SPRINT_RANDOM -= ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.SPRINT)).getSpeed(this.config.FPS) * 3.0D * stepTick;
/*     */       }
/* 285 */       if (this.SPRINT_LOOP > 1.0D) {
/* 286 */         this.SPRINT_LOOP = 0.0D;
/*     */       }
/* 288 */       if (this.SPRINT_RANDOM > 1.0D) {
/* 289 */         this.SPRINT_RANDOM = 0.0D;
/*     */       }
/* 291 */       if (this.SPRINT_RANDOM < 0.0D) {
/* 292 */         this.SPRINT_RANDOM = 0.0D;
/*     */       }
/* 294 */       if (Double.isNaN(this.SPRINT_RANDOM)) {
/* 295 */         this.SPRINT_RANDOM = 0.0D;
/*     */       }
/*     */     } 
/*     */     
/* 299 */     if (!this.config.animations.containsKey(AnimationType.MODE_CHANGE)) {
/* 300 */       this.MODE_CHANGE = 1.0D;
/*     */     } else {
/* 302 */       double modeChangeVal = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.MODE_CHANGE)).getSpeed(this.config.FPS) * stepTick;
/* 303 */       this.MODE_CHANGE += modeChangeVal;
/* 304 */       if (this.MODE_CHANGE >= 1.0D) {
/* 305 */         this.MODE_CHANGE = 1.0D;
/*     */       }
/*     */     } 
/*     */     
/* 309 */     ClientRenderHooks.getEnhancedAnimMachine(this.player).onRenderTickUpdate(stepTick);
/*     */     
/* 311 */     updateActionAndTime();
/*     */   }
/*     */   
/*     */   public AnimationType getPlayingAnimation() {
/* 315 */     return this.playback.action;
/*     */   }
/*     */   
/*     */   public void updateCurrentItem() {
/* 319 */     if (this.config == null) {
/*     */       return;
/*     */     }
/* 322 */     if (!(this.player instanceof EntityPlayer)) {
/*     */       return;
/*     */     }
/* 325 */     ItemStack stack = this.player.func_184614_ca();
/* 326 */     Item item = stack.func_77973_b();
/* 327 */     if (item instanceof ItemGun) {
/* 328 */       GunType type = ((ItemGun)item).type;
/* 329 */       if (!type.allowAimingSprint && this.ADS > 0.20000000298023224D) {
/* 330 */         this.player.func_70031_b(false);
/*     */       }
/* 332 */       if (!type.allowReloadingSprint && this.RELOAD > 0.0D) {
/* 333 */         this.player.func_70031_b(false);
/*     */       }
/* 335 */       if (!type.allowFiringSprint && this.FIRE > 0.0D) {
/* 336 */         this.player.func_70031_b(false);
/*     */       }
/* 338 */       if (this.ADS == 1.0D) {
/* 339 */         if (!ClientRenderHooks.isAiming) {
/* 340 */           ClientRenderHooks.isAiming = true;
/*     */         }
/*     */       }
/* 343 */       else if (ClientRenderHooks.isAiming) {
/* 344 */         ClientRenderHooks.isAiming = false;
/*     */       } 
/*     */     } 
/*     */     
/* 348 */     boolean resetFlag = false;
/* 349 */     if (this.oldCurrentItem != ((EntityPlayer)this.player).field_71071_by.field_70461_c) {
/* 350 */       resetFlag = true;
/* 351 */       this.oldCurrentItem = ((EntityPlayer)this.player).field_71071_by.field_70461_c;
/*     */     } 
/* 353 */     if (this.oldItemstack != this.player.func_184614_ca()) {
/* 354 */       if (this.oldItemstack == null || this.oldItemstack.func_190926_b()) {
/* 355 */         resetFlag = true;
/*     */       }
/* 357 */       this.oldItemstack = this.player.func_184614_ca();
/*     */     } 
/* 359 */     if (resetFlag) {
/* 360 */       reset(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateAction() {
/* 365 */     EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine(this.player);
/* 366 */     boolean flag = this.nextResetDefault;
/* 367 */     this.nextResetDefault = false;
/* 368 */     if (this.DRAW < 1.0D) {
/* 369 */       if (!this.hasPlayedDrawSound) {
/* 370 */         Item item = this.player.func_184614_ca().func_77973_b();
/* 371 */         if (item instanceof ItemGun) {
/* 372 */           if (!((Minecraft.func_71410_x()).field_71462_r instanceof com.modularwarfare.client.gui.GuiGunModify) && this.player instanceof EntityPlayer) {
/* 373 */             if (!ItemGun.hasNextShot(this.player.func_184614_ca()) && ((ItemGun)item).type.weaponSoundMap.containsKey(WeaponSoundType.DrawEmpty)) {
/* 374 */               ((ItemGun)item).type.playClientSound((EntityPlayer)this.player, WeaponSoundType.DrawEmpty);
/*     */             } else {
/* 376 */               ((ItemGun)item).type.playClientSound((EntityPlayer)this.player, WeaponSoundType.Draw);
/*     */             } 
/*     */           }
/* 379 */           this.hasPlayedDrawSound = true;
/*     */         } 
/*     */       } 
/* 382 */       this.playback.action = AnimationType.DRAW;
/* 383 */       if (!ItemGun.hasNextShot(this.player.func_184614_ca()) && 
/* 384 */         this.config.animations.containsKey(AnimationType.DRAW_EMPTY)) {
/* 385 */         this.playback.action = AnimationType.DRAW_EMPTY;
/*     */       }
/*     */     }
/* 388 */     else if (this.RELOAD > 0.0D) {
/* 389 */       resetView();
/* 390 */       this.playback.action = anim.getReloadAnimationType();
/* 391 */     } else if (this.FIRE > 0.0D) {
/* 392 */       resetView();
/* 393 */       this.playback.action = anim.getShootingAnimationType();
/* 394 */     } else if (this.INSPECT < 1.0D) {
/* 395 */       this.playback.action = AnimationType.INSPECT;
/* 396 */       if (!ItemGun.hasNextShot(this.player.func_184614_ca()) && 
/* 397 */         this.config.animations.containsKey(AnimationType.INSPECT_EMPTY)) {
/* 398 */         this.playback.action = AnimationType.INSPECT_EMPTY;
/*     */       }
/*     */     }
/* 401 */     else if (this.MODE_CHANGE < 1.0D) {
/* 402 */       this.playback.action = AnimationType.MODE_CHANGE;
/* 403 */     } else if (this.playback.hasPlayed || (this.playback.action != AnimationType.DEFAULT && this.playback.action != AnimationType.DEFAULT_EMPTY)) {
/* 404 */       if (flag) {
/* 405 */         this.playback.action = AnimationType.DEFAULT;
/* 406 */         if (!ItemGun.hasNextShot(this.player.func_184614_ca()) && 
/* 407 */           this.config.animations.containsKey(AnimationType.DEFAULT_EMPTY)) {
/* 408 */           this.playback.action = AnimationType.DEFAULT_EMPTY;
/*     */         }
/*     */       } 
/*     */       
/* 412 */       this.nextResetDefault = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTime() {
/* 418 */     EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine(this.player);
/* 419 */     if (this.playback.action == null) {
/*     */       return;
/*     */     }
/* 422 */     switch (this.playback.action) {
/*     */       case DEFAULT:
/* 424 */         this.playback.updateTime(this.DEFAULT);
/*     */         break;
/*     */       case DEFAULT_EMPTY:
/* 427 */         this.playback.updateTime(this.DEFAULT);
/*     */         break;
/*     */       case DRAW:
/* 430 */         this.playback.updateTime(this.DRAW);
/*     */         break;
/*     */       case DRAW_EMPTY:
/* 433 */         this.playback.updateTime(this.DRAW);
/*     */         break;
/*     */       case INSPECT:
/* 436 */         this.playback.updateTime(this.INSPECT);
/*     */         break;
/*     */       case INSPECT_EMPTY:
/* 439 */         this.playback.updateTime(this.INSPECT);
/*     */         break;
/*     */       case MODE_CHANGE:
/* 442 */         this.playback.updateTime(this.MODE_CHANGE);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 447 */     for (AnimationType reloadType : RELOAD_TYPE) {
/* 448 */       if (this.playback.action == reloadType) {
/* 449 */         this.playback.updateTime(this.RELOAD);
/*     */         break;
/*     */       } 
/*     */     } 
/* 453 */     for (AnimationType fireType : FIRE_TYPE) {
/* 454 */       if (this.playback.action == fireType) {
/* 455 */         this.playback.updateTime(this.FIRE);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateActionAndTime() {
/* 462 */     updateAction();
/* 463 */     updateTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTime() {
/* 468 */     return (float)this.playback.time;
/*     */   }
/*     */   
/*     */   public float getSprintTime() {
/* 472 */     if (this.config.animations.get(AnimationType.SPRINT) == null) {
/* 473 */       return 0.0F;
/*     */     }
/* 475 */     double startTime = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.SPRINT)).getStartTime(this.config.FPS);
/* 476 */     double endTime = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(AnimationType.SPRINT)).getEndTime(this.config.FPS);
/* 477 */     double result = Interpolation.LINEAR.interpolate(startTime, endTime, this.SPRINT_LOOP);
/* 478 */     if (Double.isNaN(result)) {
/* 479 */       return 0.0F;
/*     */     }
/* 481 */     return (float)result;
/*     */   }
/*     */   
/*     */   public void setConfig(GunEnhancedRenderConfig config) {
/* 485 */     this.config = config;
/*     */   }
/*     */   
/*     */   public GunEnhancedRenderConfig getConfig() {
/* 489 */     return this.config;
/*     */   }
/*     */   
/*     */   public boolean isDrawing() {
/* 493 */     if (this.player == null) {
/* 494 */       return false;
/*     */     }
/* 496 */     Item item = this.player.func_184614_ca().func_77973_b();
/* 497 */     if (item instanceof ItemGun && 
/* 498 */       ((ItemGun)item).type.animationType.equals(WeaponAnimationType.ENHANCED)) {
/* 499 */       return (this.playback.action == AnimationType.DRAW);
/*     */     }
/*     */     
/* 502 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isCouldReload() {
/* 506 */     if (this.player == null) {
/* 507 */       return true;
/*     */     }
/* 509 */     Item item = this.player.func_184614_ca().func_77973_b();
/* 510 */     if (item instanceof ItemGun && 
/* 511 */       ((ItemGun)item).type.animationType.equals(WeaponAnimationType.ENHANCED)) {
/* 512 */       if (isDrawing()) {
/* 513 */         return false;
/*     */       }
/* 515 */       if ((ClientRenderHooks.getEnhancedAnimMachine(this.player)).reloading) {
/* 516 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 520 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isCouldShoot() {
/* 524 */     if (this.player == null) {
/* 525 */       return true;
/*     */     }
/* 527 */     Item item = this.player.func_184614_ca().func_77973_b();
/* 528 */     if (item instanceof ItemGun && 
/* 529 */       ((ItemGun)item).type.animationType.equals(WeaponAnimationType.ENHANCED)) {
/* 530 */       if (isDrawing()) {
/* 531 */         return false;
/*     */       }
/* 533 */       if ((ClientRenderHooks.getEnhancedAnimMachine(this.player)).reloading) {
/* 534 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 538 */     return true;
/*     */   }
/*     */   
/*     */   public ItemStack getRenderAmmo(ItemStack ammo) {
/* 542 */     EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine(this.player);
/* 543 */     if (anim.reloading) {
/* 544 */       AnimationType reloadAni = anim.getReloadAnimationType();
/* 545 */       if (anim.getReloadType() == ReloadType.Full && (reloadAni == AnimationType.PRE_RELOAD || reloadAni == AnimationType.RELOAD_FIRST || reloadAni == AnimationType.RELOAD_FIRST_QUICKLY))
/*     */       {
/* 547 */         return ammo;
/*     */       }
/* 549 */       if (reloadAni == AnimationType.PRE_UNLOAD || reloadAni == AnimationType.UNLOAD || reloadAni == AnimationType.POST_UNLOAD) {
/* 550 */         return ammo;
/*     */       }
/*     */     } 
/* 553 */     if (ClientTickHandler.reloadEnhancedPrognosisAmmoRendering != null && 
/* 554 */       !ClientTickHandler.reloadEnhancedPrognosisAmmoRendering.func_190926_b()) {
/* 555 */       return ClientTickHandler.reloadEnhancedPrognosisAmmoRendering;
/*     */     }
/* 557 */     return ammo;
/*     */   }
/*     */   
/*     */   public boolean shouldRenderAmmo() {
/* 561 */     EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine(this.player);
/* 562 */     if (anim.reloading) {
/* 563 */       if (anim.getReloadAnimationType() == AnimationType.POST_UNLOAD) {
/* 564 */         return false;
/*     */       }
/* 566 */       return true;
/*     */     } 
/* 568 */     if (ClientTickHandler.reloadEnhancedPrognosisAmmoRendering != null && 
/* 569 */       !ClientTickHandler.reloadEnhancedPrognosisAmmoRendering.func_190926_b()) {
/* 570 */       return true;
/*     */     }
/* 572 */     return ItemGun.hasAmmoLoaded(this.player.func_184614_ca());
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\enhanced\animation\AnimationController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */