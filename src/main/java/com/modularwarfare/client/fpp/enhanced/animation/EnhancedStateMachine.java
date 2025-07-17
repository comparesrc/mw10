/*     */ package com.modularwarfare.client.fpp.enhanced.animation;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.Passer;
/*     */ import com.modularwarfare.client.fpp.basic.animations.ReloadType;
/*     */ import com.modularwarfare.client.fpp.enhanced.AnimationType;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*     */ import com.modularwarfare.client.fpp.enhanced.models.ModelEnhancedGun;
/*     */ import com.modularwarfare.client.handler.ClientTickHandler;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAmmo;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.WeaponSoundType;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketGunReloadEnhancedStop;
/*     */ import com.modularwarfare.common.network.PacketGunReloadSound;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnhancedStateMachine
/*     */ {
/*     */   public AnimationController controller;
/*     */   public float reloadTime;
/*     */   private ReloadType reloadType;
/*     */   public boolean reloading = false;
/*  41 */   public int reloadCount = 0;
/*  42 */   public int reloadMaxCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   public float gunRecoil = 0.0F, lastGunRecoil = 0.0F;
/*  48 */   public float recoilSide = 0.0F;
/*     */ 
/*     */ 
/*     */   
/*  52 */   public float gunSlide = 0.0F; public float lastGunSlide = 0.0F;
/*     */ 
/*     */   
/*     */   public boolean shooting = false;
/*     */   
/*     */   private float shootTime;
/*     */   
/*  59 */   public int flashCount = 0;
/*     */   
/*     */   public boolean isFailedShoot = false;
/*     */   public ModelEnhancedGun currentModel;
/*  63 */   public Phase reloadPhase = Phase.PRE;
/*  64 */   public Phase lastReloadPhase = null;
/*  65 */   public Phase shootingPhase = Phase.PRE;
/*     */   
/*  67 */   public ItemStack heldItemstStack = ItemStack.field_190927_a;
/*  68 */   public int lastItemIndex = 0;
/*     */   
/*     */   public enum Phase {
/*  71 */     PRE, FIRST, SECOND, POST;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  75 */     this.reloadTime = 0.0F;
/*  76 */     this.reloadType = null;
/*  77 */     this.reloading = false;
/*  78 */     this.reloadCount = 0;
/*  79 */     this.reloadMaxCount = 0;
/*  80 */     this.gunRecoil = 0.0F;
/*  81 */     this.lastGunRecoil = 0.0F;
/*  82 */     this.recoilSide = 0.0F;
/*  83 */     this.gunSlide = 0.0F;
/*  84 */     this.lastGunSlide = 0.0F;
/*  85 */     this.shooting = false;
/*  86 */     this.shootTime = 0.0F;
/*  87 */     this.flashCount = 0;
/*  88 */     this.isFailedShoot = false;
/*  89 */     this.currentModel = null;
/*  90 */     this.reloadPhase = Phase.PRE;
/*  91 */     this.lastReloadPhase = null;
/*  92 */     this.shootingPhase = Phase.PRE;
/*     */   }
/*     */   
/*     */   public void triggerShoot(AnimationController controller, ModelEnhancedGun model, GunType gunType, int fireTickDelay) {
/*  96 */     triggerShoot(controller, model, gunType, fireTickDelay, false);
/*     */   }
/*     */   
/*     */   public void triggerShoot(AnimationController controller, ModelEnhancedGun model, GunType gunType, int fireTickDelay, boolean isFailed) {
/* 100 */     this.lastGunRecoil = this.gunRecoil = 1.0F;
/* 101 */     this.lastGunSlide = this.gunSlide = 1.0F;
/*     */     
/* 103 */     this.shooting = true;
/* 104 */     this.shootTime = fireTickDelay;
/* 105 */     this.recoilSide = (float)(-1.0D + Math.random() * 2.0D);
/* 106 */     if (isFailed) {
/* 107 */       this.recoilSide = 0.0F;
/* 108 */       this.lastGunRecoil = this.gunRecoil = 0.0F;
/* 109 */       this.lastGunSlide = this.gunSlide = 0.0F;
/*     */     } 
/* 111 */     this.isFailedShoot = isFailed;
/* 112 */     this.shootingPhase = Phase.PRE;
/* 113 */     this.currentModel = model;
/* 114 */     this.controller = controller;
/*     */   }
/*     */   
/*     */   public void triggerReload(AnimationController controller, EntityLivingBase entity, int reloadTime, int reloadCount, ModelEnhancedGun model, ReloadType reloadType) {
/* 118 */     reset();
/* 119 */     updateCurrentItem(entity);
/* 120 */     this.reloadTime = (reloadType != ReloadType.Full) ? (reloadTime * 0.65F) : reloadTime;
/* 121 */     this.reloadCount = reloadCount;
/* 122 */     Item item = this.heldItemstStack.func_77973_b();
/* 123 */     if (item instanceof ItemGun) {
/* 124 */       GunType type = ((ItemGun)item).type;
/* 125 */       if (reloadType == ReloadType.Unload) {
/* 126 */         this.reloadCount -= type.modifyUnloadBullets;
/*     */       }
/*     */     } 
/* 129 */     this.reloadMaxCount = reloadCount;
/* 130 */     this.reloadType = reloadType;
/* 131 */     this.reloadPhase = Phase.PRE;
/* 132 */     this.lastReloadPhase = null;
/* 133 */     this.reloading = true;
/* 134 */     this.currentModel = model;
/*     */     
/* 136 */     this.controller = controller;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTickUpdate() {
/* 141 */     this.lastGunRecoil = this.gunRecoil;
/* 142 */     if (this.gunRecoil > 0.0F)
/* 143 */       this.gunRecoil *= 0.5F; 
/*     */   }
/*     */   
/*     */   public ReloadType getReloadType() {
/* 147 */     return this.reloadType;
/*     */   }
/*     */   
/*     */   public AnimationType getReloadAnimationType() {
/* 151 */     AnimationType aniType = null;
/* 152 */     if (this.reloadType == ReloadType.Load) {
/* 153 */       ItemStack stack = this.heldItemstStack;
/* 154 */       Item item = stack.func_77973_b();
/* 155 */       if (item instanceof ItemGun) {
/* 156 */         GunType type = ((ItemGun)item).type;
/* 157 */         if (type.acceptedAmmo != null) {
/* 158 */           if (this.reloadPhase == Phase.FIRST) {
/* 159 */             aniType = AnimationType.LOAD;
/* 160 */           } else if (this.reloadPhase == Phase.POST) {
/* 161 */             aniType = AnimationType.POST_LOAD;
/* 162 */           } else if (this.reloadPhase == Phase.PRE) {
/* 163 */             aniType = AnimationType.PRE_LOAD;
/*     */           }
/*     */         
/*     */         }
/* 167 */         else if (this.reloadPhase == Phase.FIRST) {
/* 168 */           if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.RELOAD_FIRST_EMPTY)) {
/* 169 */             aniType = AnimationType.RELOAD_FIRST_EMPTY;
/*     */           } else {
/* 171 */             aniType = AnimationType.RELOAD_FIRST;
/*     */           } 
/* 173 */         } else if (this.reloadPhase == Phase.SECOND) {
/* 174 */           if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.RELOAD_SECOND_EMPTY)) {
/* 175 */             aniType = AnimationType.RELOAD_SECOND_EMPTY;
/*     */           } else {
/* 177 */             aniType = AnimationType.RELOAD_SECOND;
/*     */           } 
/* 179 */         } else if (this.reloadPhase == Phase.POST) {
/* 180 */           if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.POST_RELOAD_EMPTY)) {
/* 181 */             aniType = AnimationType.POST_RELOAD_EMPTY;
/*     */           } else {
/* 183 */             aniType = AnimationType.POST_RELOAD;
/*     */           }
/*     */         
/* 186 */         } else if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.PRE_RELOAD_EMPTY)) {
/* 187 */           aniType = AnimationType.PRE_RELOAD_EMPTY;
/*     */         } else {
/* 189 */           aniType = AnimationType.PRE_RELOAD;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 194 */     } else if (this.reloadType == ReloadType.Unload) {
/* 195 */       if (this.reloadPhase == Phase.FIRST) {
/* 196 */         aniType = AnimationType.UNLOAD;
/* 197 */       } else if (this.reloadPhase == Phase.POST) {
/* 198 */         aniType = AnimationType.POST_UNLOAD;
/* 199 */       } else if (this.reloadPhase == Phase.PRE) {
/* 200 */         aniType = AnimationType.PRE_UNLOAD;
/*     */       } 
/* 202 */     } else if (this.reloadType == ReloadType.Full) {
/* 203 */       if (this.reloadPhase == Phase.FIRST) {
/* 204 */         if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.RELOAD_FIRST_EMPTY)) {
/* 205 */           aniType = AnimationType.RELOAD_FIRST_EMPTY;
/*     */         } else {
/* 207 */           aniType = AnimationType.RELOAD_FIRST;
/*     */         } 
/* 209 */       } else if (this.reloadPhase == Phase.SECOND) {
/* 210 */         if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.RELOAD_SECOND_EMPTY)) {
/* 211 */           aniType = AnimationType.RELOAD_SECOND_EMPTY;
/*     */         } else {
/* 213 */           aniType = AnimationType.RELOAD_SECOND;
/*     */         } 
/* 215 */       } else if (this.reloadPhase == Phase.POST) {
/* 216 */         if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.POST_RELOAD_EMPTY)) {
/* 217 */           aniType = AnimationType.POST_RELOAD_EMPTY;
/*     */         } else {
/* 219 */           aniType = AnimationType.POST_RELOAD;
/*     */         }
/*     */       
/* 222 */       } else if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.PRE_RELOAD_EMPTY)) {
/* 223 */         aniType = AnimationType.PRE_RELOAD_EMPTY;
/*     */       } else {
/* 225 */         aniType = AnimationType.PRE_RELOAD;
/*     */       } 
/*     */     } 
/*     */     
/* 229 */     return aniType;
/*     */   }
/*     */   
/*     */   public AnimationType getShootingAnimationType() {
/* 233 */     AnimationType aniType = AnimationType.PRE_FIRE;
/* 234 */     if (this.shootingPhase == Phase.FIRST) {
/* 235 */       if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.FIRE_LAST)) {
/* 236 */         aniType = AnimationType.FIRE_LAST;
/*     */       } else {
/* 238 */         aniType = AnimationType.FIRE;
/*     */       } 
/* 240 */     } else if (this.shootingPhase == Phase.POST) {
/* 241 */       if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.POST_FIRE_EMPTY)) {
/* 242 */         aniType = AnimationType.POST_FIRE_EMPTY;
/*     */       } else {
/* 244 */         aniType = AnimationType.POST_FIRE;
/*     */       } 
/*     */     } 
/* 247 */     if (this.isFailedShoot && this.shootingPhase != Phase.PRE) {
/* 248 */       return AnimationType.DEFAULT;
/*     */     }
/* 250 */     return aniType;
/*     */   }
/*     */   
/*     */   public float getReloadSppedFactor() {
/* 254 */     ItemStack stack = this.heldItemstStack;
/* 255 */     Item item = stack.func_77973_b();
/* 256 */     if (this.controller != null && 
/* 257 */       item instanceof ItemGun) {
/* 258 */       GunType type = ((ItemGun)item).type;
/* 259 */       if (ItemGun.hasAmmoLoaded(stack)) {
/* 260 */         ItemStack stackAmmo = new ItemStack(stack.func_77978_p().func_74775_l("ammo"));
/* 261 */         stackAmmo = this.controller.getRenderAmmo(stackAmmo);
/* 262 */         if (stackAmmo != null && stackAmmo.func_77973_b() instanceof ItemAmmo) {
/* 263 */           ItemAmmo itemAmmo = (ItemAmmo)stackAmmo.func_77973_b();
/* 264 */           return itemAmmo.type.reloadSpeedFactor;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 269 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public void updateCurrentItem(EntityLivingBase player) {
/* 273 */     int index = 0;
/* 274 */     if (player == (Minecraft.func_71410_x()).field_71439_g) {
/* 275 */       index = (Minecraft.func_71410_x()).field_71439_g.field_71071_by.field_70461_c;
/*     */     }
/* 277 */     if (!ItemStack.areItemStacksEqualUsingNBTShareTag(this.heldItemstStack, player.func_184614_ca()) || index != this.lastItemIndex) {
/* 278 */       if (this.reloading) {
/* 279 */         stopReload();
/*     */       }
/* 281 */       if (!this.shooting) {
/* 282 */         reset();
/*     */       }
/*     */     } 
/*     */     
/* 286 */     this.heldItemstStack = player.func_184614_ca();
/*     */     
/* 288 */     this.lastItemIndex = index;
/*     */   }
/*     */   
/*     */   public void onRenderTickUpdate(float partialTick) {
/* 292 */     if (this.controller == null) {
/*     */       return;
/*     */     }
/* 295 */     ItemStack stack = this.heldItemstStack;
/* 296 */     Item item = stack.func_77973_b();
/* 297 */     if (item instanceof ItemGun) {
/* 298 */       GunType type = ((ItemGun)item).type;
/* 299 */       if (this.reloading) {
/*     */         
/* 301 */         AnimationType aniType = getReloadAnimationType();
/* 302 */         Passer<Phase> phase = new Passer(this.reloadPhase);
/* 303 */         Passer<Double> progess = new Passer(Double.valueOf(this.controller.RELOAD));
/* 304 */         this.reloading = phaseUpdate(aniType, partialTick, getReloadSppedFactor(), phase, progess, () -> {
/*     */               if (this.reloadCount > 0) {
/*     */                 phase.set(Phase.FIRST);
/*     */               } else {
/*     */                 phase.set(Phase.POST);
/*     */               } 
/*     */             }() -> {
/*     */               this.reloadCount--;
/*     */               
/*     */               if (type.acceptedAmmo != null) {
/*     */                 phase.set(Phase.SECOND);
/*     */               } else if (this.reloadCount <= 0) {
/*     */                 phase.set(Phase.POST);
/*     */               } else {
/*     */                 phase.set(Phase.SECOND);
/*     */               } 
/*     */             }() -> {
/*     */               if (this.reloadCount <= 0) {
/*     */                 phase.set(Phase.POST);
/*     */               } else {
/*     */                 phase.set(Phase.FIRST);
/*     */               } 
/*     */             });
/*     */         
/* 328 */         if (this.reloadPhase != this.lastReloadPhase && aniType != null) {
/* 329 */           switch (aniType) {
/*     */             case PRE_LOAD:
/* 331 */               playReloadSound(WeaponSoundType.PreLoad);
/*     */               break;
/*     */             case LOAD:
/* 334 */               playReloadSound(WeaponSoundType.Load);
/*     */               break;
/*     */             case POST_LOAD:
/* 337 */               playReloadSound(WeaponSoundType.PostLoad);
/*     */               break;
/*     */             case PRE_UNLOAD:
/* 340 */               playReloadSound(WeaponSoundType.PreUnload);
/*     */               break;
/*     */             case UNLOAD:
/* 343 */               playReloadSound(WeaponSoundType.Unload);
/*     */               break;
/*     */             case POST_UNLOAD:
/* 346 */               playReloadSound(WeaponSoundType.PostUnload);
/*     */               break;
/*     */             case PRE_RELOAD:
/* 349 */               playReloadSound(WeaponSoundType.PreReload);
/*     */               break;
/*     */             case PRE_RELOAD_EMPTY:
/* 352 */               playReloadSound(WeaponSoundType.PreReloadEmpty);
/*     */               break;
/*     */             case RELOAD_FIRST:
/* 355 */               playReloadSound(WeaponSoundType.Reload);
/*     */               break;
/*     */             case RELOAD_SECOND:
/* 358 */               playReloadSound(WeaponSoundType.ReloadSecond);
/*     */               break;
/*     */             case RELOAD_FIRST_QUICKLY:
/* 361 */               playReloadSound(WeaponSoundType.Reload);
/*     */               break;
/*     */             case RELOAD_SECOND_QUICKLY:
/* 364 */               playReloadSound(WeaponSoundType.ReloadSecond);
/*     */               break;
/*     */             case RELOAD_FIRST_EMPTY:
/* 367 */               playReloadSound(WeaponSoundType.ReloadEmpty);
/*     */               break;
/*     */             case RELOAD_SECOND_EMPTY:
/* 370 */               playReloadSound(WeaponSoundType.ReloadSecondEmpty);
/*     */               break;
/*     */             case POST_RELOAD:
/* 373 */               playReloadSound(WeaponSoundType.PostReload);
/*     */               break;
/*     */             
/*     */             case POST_RELOAD_EMPTY:
/* 377 */               playReloadSound(WeaponSoundType.PostReloadEmpty);
/*     */               break;
/*     */           } 
/*     */ 
/*     */         
/*     */         }
/* 383 */         this.lastReloadPhase = this.reloadPhase;
/* 384 */         this.reloadPhase = (Phase)phase.get();
/*     */         
/* 386 */         this.controller.RELOAD = ((Double)progess.get()).doubleValue();
/* 387 */         if (!this.reloading) {
/* 388 */           this.controller.updateActionAndTime();
/* 389 */           stopReload();
/*     */         } 
/*     */       } 
/* 392 */       if (this.shooting) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 401 */         AnimationType aniType = getShootingAnimationType();
/* 402 */         Passer<Phase> phase = new Passer(this.shootingPhase);
/* 403 */         Passer<Double> progess = new Passer(Double.valueOf(this.controller.FIRE));
/* 404 */         Random r = new Random();
/* 405 */         int Low = 0;
/* 406 */         int High = type.flashType.resourceLocations.size() - 1;
/* 407 */         int result = r.nextInt(High - Low) + Low;
/* 408 */         this.shooting = phaseUpdate(aniType, partialTick, 1.0F, phase, progess, () -> { this.flashCount = result; phase.set(Phase.FIRST); }() -> phase.set(Phase.POST), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 414 */         this.shootingPhase = (Phase)phase.get();
/* 415 */         this.controller.FIRE = ((Double)progess.get()).doubleValue();
/* 416 */         if (!this.shooting) {
/* 417 */           this.controller.updateActionAndTime();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void playReloadSound(WeaponSoundType soundType) {
/* 424 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/* 425 */     if (entityPlayerSP.func_184614_ca() != null && 
/* 426 */       entityPlayerSP.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 427 */       ItemStack gunStack = entityPlayerSP.func_184614_ca();
/* 428 */       ItemGun itemGun = (ItemGun)entityPlayerSP.func_184614_ca().func_77973_b();
/* 429 */       GunType gunType = itemGun.type;
/*     */       
/* 431 */       if (soundType != null) {
/* 432 */         gunType.playClientSound((EntityPlayer)entityPlayerSP, soundType);
/*     */       }
/*     */     } 
/*     */     
/* 436 */     ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(soundType));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean phaseUpdate(AnimationType aniType, float partialTick, float speedFactor, Passer<Phase> phase, Passer<Double> progress, Runnable preCall, Runnable firstCall, Runnable secondCall) {
/* 441 */     boolean flag = true;
/* 442 */     GunEnhancedRenderConfig.Animation ani = null;
/* 443 */     if (aniType != null) {
/* 444 */       ani = (GunEnhancedRenderConfig.Animation)((GunEnhancedRenderConfig)this.currentModel.config).animations.get(aniType);
/*     */     }
/*     */     
/* 447 */     if (ani != null) {
/* 448 */       double speed = ani.getSpeed(this.currentModel.config.FPS) * speedFactor * partialTick;
/* 449 */       double val = ((Double)progress.get()).doubleValue() + speed;
/* 450 */       progress.set(Double.valueOf(val));
/* 451 */       if (((Double)progress.get()).doubleValue() > 1.0D) {
/* 452 */         progress.set(Double.valueOf(1.0D));
/* 453 */       } else if (((Double)progress.get()).doubleValue() < 0.0D) {
/* 454 */         progress.set(Double.valueOf(0.0D));
/*     */       } 
/*     */     } else {
/* 457 */       progress.set(Double.valueOf(1.0D));
/*     */     } 
/* 459 */     if (((Double)progress.get()).doubleValue() >= 1.0D) {
/* 460 */       if (phase.get() == Phase.POST) {
/* 461 */         flag = false;
/* 462 */         progress.set(Double.valueOf(0.0D));
/* 463 */       } else if (phase.get() == Phase.FIRST) {
/* 464 */         progress.set(Double.valueOf(Double.MIN_VALUE));
/* 465 */         if (firstCall != null) {
/* 466 */           firstCall.run();
/*     */         }
/* 468 */       } else if (phase.get() == Phase.SECOND) {
/* 469 */         progress.set(Double.valueOf(Double.MIN_VALUE));
/* 470 */         if (secondCall != null) {
/* 471 */           secondCall.run();
/*     */         }
/* 473 */       } else if (phase.get() == Phase.PRE) {
/* 474 */         progress.set(Double.valueOf(Double.MIN_VALUE));
/* 475 */         if (preCall != null) {
/* 476 */           preCall.run();
/*     */         }
/*     */       } 
/*     */     }
/* 480 */     return flag;
/*     */   }
/*     */   
/*     */   public void stopReload() {
/* 484 */     PacketGunReloadEnhancedStop packet = null;
/* 485 */     ItemStack stack = this.heldItemstStack;
/* 486 */     Item item = stack.func_77973_b();
/* 487 */     if (item instanceof ItemGun) {
/* 488 */       GunType type = ((ItemGun)item).type;
/*     */       
/* 490 */       AnimationType reloadAni = getReloadAnimationType();
/*     */       
/* 492 */       if (this.reloadType == ReloadType.Load) {
/* 493 */         if (type.acceptedAmmo != null) {
/* 494 */           if (this.reloadPhase == Phase.PRE) {
/* 495 */             packet = new PacketGunReloadEnhancedStop(0, false, false);
/*     */           } else {
/* 497 */             packet = new PacketGunReloadEnhancedStop(0, true, true);
/*     */           }
/*     */         
/* 500 */         } else if (this.reloadPhase == Phase.PRE) {
/* 501 */           packet = new PacketGunReloadEnhancedStop(0, false, false);
/*     */         } else {
/* 503 */           packet = new PacketGunReloadEnhancedStop(this.reloadMaxCount - this.reloadCount, true, true);
/*     */         }
/*     */       
/* 506 */       } else if (this.reloadType == ReloadType.Full) {
/* 507 */         if (type.acceptedAmmo != null) {
/* 508 */           if (this.reloadPhase == Phase.POST || this.reloadPhase == Phase.SECOND) {
/* 509 */             packet = new PacketGunReloadEnhancedStop(0, true, true);
/* 510 */           } else if (this.reloadPhase == Phase.FIRST) {
/* 511 */             packet = new PacketGunReloadEnhancedStop(0, true, false);
/*     */           } else {
/* 513 */             packet = new PacketGunReloadEnhancedStop(0, false, false);
/*     */           }
/*     */         
/* 516 */         } else if (this.reloadPhase == Phase.PRE) {
/* 517 */           packet = new PacketGunReloadEnhancedStop(0, false, false);
/*     */         } else {
/* 519 */           packet = new PacketGunReloadEnhancedStop(this.reloadMaxCount - this.reloadCount, true, true);
/*     */         }
/*     */       
/* 522 */       } else if (this.reloadType == ReloadType.Unload) {
/* 523 */         if (this.reloadPhase == Phase.PRE) {
/* 524 */           packet = new PacketGunReloadEnhancedStop(0, false, false);
/*     */         }
/* 526 */         else if (type.acceptedAmmo != null) {
/* 527 */           packet = new PacketGunReloadEnhancedStop(0, true, false);
/*     */         } else {
/* 529 */           packet = new PacketGunReloadEnhancedStop(this.reloadMaxCount - this.reloadCount, true, false);
/*     */         } 
/*     */       } 
/*     */       
/* 533 */       if (packet != null) {
/* 534 */         if (type.acceptedAmmo != null) {
/* 535 */           if (packet.loaded) {
/* 536 */             ItemStack ammoStack = ClientTickHandler.reloadEnhancedPrognosisAmmoRendering;
/* 537 */             if (ammoStack != null && !ammoStack.func_190926_b()) {
/* 538 */               ammoStack.func_77964_b(0);
/* 539 */               if (reloadAni == AnimationType.RELOAD_FIRST || reloadAni == AnimationType.RELOAD_FIRST_QUICKLY || reloadAni == AnimationType.UNLOAD) {
/* 540 */                 ammoStack = ItemStack.field_190927_a;
/*     */               }
/* 542 */               if (ammoStack.func_77973_b() instanceof ItemAmmo) {
/* 543 */                 this.heldItemstStack.func_77978_p().func_74782_a("ammo", (NBTBase)ammoStack.func_77955_b(new NBTTagCompound()));
/*     */               }
/*     */             } 
/* 546 */           } else if (packet.unloaded) {
/* 547 */             this.heldItemstStack.func_77978_p().func_82580_o("ammo");
/*     */           }
/*     */         
/* 550 */         } else if (packet.loaded) {
/* 551 */           ItemStack bulletStack = ClientTickHandler.reloadEnhancedPrognosisAmmoRendering;
/* 552 */           if (bulletStack != null && !bulletStack.func_190926_b()) {
/* 553 */             bulletStack.func_77964_b(0);
/* 554 */             int offset = getAmmoCountOffset(true);
/* 555 */             int ammoCount = this.heldItemstStack.func_77978_p().func_74762_e("ammocount");
/* 556 */             this.heldItemstStack.func_77978_p().func_74768_a("ammocount", ammoCount + offset);
/* 557 */             this.heldItemstStack.func_77978_p().func_74782_a("bullet", (NBTBase)bulletStack.func_77955_b(new NBTTagCompound()));
/*     */           } 
/* 559 */         } else if (packet.unloaded) {
/* 560 */           this.heldItemstStack.func_77978_p().func_74768_a("ammocount", 0);
/* 561 */           this.heldItemstStack.func_77978_p().func_82580_o("bullet");
/*     */         } 
/*     */         
/* 564 */         ModularWarfare.NETWORK.sendToServer((PacketBase)packet);
/* 565 */         ClientTickHandler.reloadEnhancedPrognosisAmmo = null;
/* 566 */         ClientTickHandler.reloadEnhancedPrognosisAmmoRendering = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSprint() {
/* 573 */     return (!this.shooting && !this.reloading && this.controller.ADS < 0.800000011920929D);
/*     */   }
/*     */   
/*     */   public int getAmmoCountOffset(boolean really) {
/* 577 */     ItemStack stack = this.heldItemstStack;
/* 578 */     if (this.heldItemstStack != null) {
/* 579 */       Item item = stack.func_77973_b();
/* 580 */       if (item instanceof ItemGun) {
/* 581 */         GunType type = ((ItemGun)item).type;
/* 582 */         if (this.reloading) {
/* 583 */           if (this.reloadType == ReloadType.Unload) {
/* 584 */             if (really) {
/* 585 */               return -(this.reloadMaxCount - this.reloadCount);
/*     */             }
/* 587 */             return -(this.reloadMaxCount - this.reloadCount - type.modifyUnloadBullets);
/*     */           } 
/*     */           
/* 590 */           return this.reloadMaxCount - this.reloadCount;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 595 */     if (this.reloadType == ReloadType.Unload) {
/* 596 */       return -this.reloadMaxCount;
/*     */     }
/* 598 */     return this.reloadMaxCount;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\enhanced\animation\EnhancedStateMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */