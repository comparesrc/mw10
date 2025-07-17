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
/*     */ import net.minecraft.entity.EntityLivingBase;
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
/*     */ 
/*     */ public class EnhancedStateMachine
/*     */ {
/*     */   public AnimationController controller;
/*     */   public float reloadTime;
/*     */   private ReloadType reloadType;
/*     */   public boolean reloading = false;
/*  39 */   public int reloadCount = 0;
/*  40 */   public int reloadMaxCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public float gunRecoil = 0.0F, lastGunRecoil = 0.0F;
/*  46 */   public float recoilSide = 0.0F;
/*     */ 
/*     */ 
/*     */   
/*  50 */   public float gunSlide = 0.0F; public float lastGunSlide = 0.0F;
/*     */ 
/*     */   
/*     */   public boolean shooting = false;
/*     */   
/*     */   private float shootTime;
/*     */   
/*  57 */   public int flashCount = 0;
/*     */   
/*     */   public boolean isFailedShoot = false;
/*     */   public ModelEnhancedGun currentModel;
/*  61 */   public Phase reloadPhase = Phase.PRE;
/*  62 */   public Phase lastReloadPhase = null;
/*  63 */   public Phase shootingPhase = Phase.PRE;
/*     */   
/*  65 */   public ItemStack heldItemstStack = ItemStack.field_190927_a;
/*     */   
/*     */   public enum Phase {
/*  68 */     PRE, FIRST, SECOND, POST;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  72 */     this.reloadTime = 0.0F;
/*  73 */     this.reloadType = null;
/*  74 */     this.reloading = false;
/*  75 */     this.reloadCount = 0;
/*  76 */     this.reloadMaxCount = 0;
/*  77 */     this.gunRecoil = 0.0F;
/*  78 */     this.lastGunRecoil = 0.0F;
/*  79 */     this.recoilSide = 0.0F;
/*  80 */     this.gunSlide = 0.0F;
/*  81 */     this.lastGunSlide = 0.0F;
/*  82 */     this.shooting = false;
/*  83 */     this.shootTime = 0.0F;
/*  84 */     this.flashCount = 0;
/*  85 */     this.isFailedShoot = false;
/*  86 */     this.currentModel = null;
/*  87 */     this.reloadPhase = Phase.PRE;
/*  88 */     this.lastReloadPhase = null;
/*  89 */     this.shootingPhase = Phase.PRE;
/*     */   }
/*     */   
/*     */   public void triggerShoot(AnimationController controller, ModelEnhancedGun model, GunType gunType, int fireTickDelay) {
/*  93 */     triggerShoot(controller, model, gunType, fireTickDelay, false);
/*     */   }
/*     */   
/*     */   public void triggerShoot(AnimationController controller, ModelEnhancedGun model, GunType gunType, int fireTickDelay, boolean isFailed) {
/*  97 */     this.lastGunRecoil = this.gunRecoil = 1.0F;
/*  98 */     this.lastGunSlide = this.gunSlide = 1.0F;
/*     */     
/* 100 */     this.shooting = true;
/* 101 */     this.shootTime = fireTickDelay;
/* 102 */     this.recoilSide = (float)(-1.0D + Math.random() * 2.0D);
/* 103 */     if (isFailed) {
/* 104 */       this.recoilSide = 0.0F;
/* 105 */       this.lastGunRecoil = this.gunRecoil = 0.0F;
/* 106 */       this.lastGunSlide = this.gunSlide = 0.0F;
/*     */     } 
/* 108 */     this.isFailedShoot = isFailed;
/* 109 */     this.shootingPhase = Phase.PRE;
/* 110 */     this.currentModel = model;
/* 111 */     this.controller = controller;
/*     */   }
/*     */   
/*     */   public void triggerReload(AnimationController controller, EntityLivingBase entity, int reloadTime, int reloadCount, ModelEnhancedGun model, ReloadType reloadType) {
/* 115 */     reset();
/* 116 */     updateCurrentItem(entity);
/* 117 */     this.reloadTime = (reloadType != ReloadType.Full) ? (reloadTime * 0.65F) : reloadTime;
/* 118 */     this.reloadCount = reloadCount;
/* 119 */     Item item = this.heldItemstStack.func_77973_b();
/* 120 */     if (item instanceof ItemGun) {
/* 121 */       GunType type = ((ItemGun)item).type;
/* 122 */       if (reloadType == ReloadType.Unload) {
/* 123 */         this.reloadCount -= type.modifyUnloadBullets;
/*     */       }
/*     */     } 
/* 126 */     this.reloadMaxCount = reloadCount;
/* 127 */     this.reloadType = reloadType;
/* 128 */     this.reloadPhase = Phase.PRE;
/* 129 */     this.lastReloadPhase = null;
/* 130 */     this.reloading = true;
/* 131 */     this.currentModel = model;
/*     */     
/* 133 */     this.controller = controller;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTickUpdate() {
/* 138 */     this.lastGunRecoil = this.gunRecoil;
/* 139 */     if (this.gunRecoil > 0.0F)
/* 140 */       this.gunRecoil *= 0.5F; 
/*     */   }
/*     */   
/*     */   public ReloadType getReloadType() {
/* 144 */     return this.reloadType;
/*     */   }
/*     */   
/*     */   public AnimationType getReloadAnimationType() {
/* 148 */     AnimationType aniType = null;
/* 149 */     if (this.reloadType == ReloadType.Load) {
/* 150 */       ItemStack stack = this.heldItemstStack;
/* 151 */       Item item = stack.func_77973_b();
/* 152 */       if (item instanceof ItemGun) {
/* 153 */         GunType type = ((ItemGun)item).type;
/* 154 */         if (type.acceptedAmmo != null) {
/* 155 */           if (this.reloadPhase == Phase.FIRST) {
/* 156 */             aniType = AnimationType.LOAD;
/* 157 */           } else if (this.reloadPhase == Phase.POST) {
/* 158 */             aniType = AnimationType.POST_LOAD;
/* 159 */           } else if (this.reloadPhase == Phase.PRE) {
/* 160 */             aniType = AnimationType.PRE_LOAD;
/*     */           }
/*     */         
/*     */         }
/* 164 */         else if (this.reloadPhase == Phase.FIRST) {
/* 165 */           if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.RELOAD_FIRST_EMPTY)) {
/* 166 */             aniType = AnimationType.RELOAD_FIRST_EMPTY;
/*     */           } else {
/* 168 */             aniType = AnimationType.RELOAD_FIRST;
/*     */           } 
/* 170 */         } else if (this.reloadPhase == Phase.SECOND) {
/* 171 */           if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.RELOAD_SECOND_EMPTY)) {
/* 172 */             aniType = AnimationType.RELOAD_SECOND_EMPTY;
/*     */           } else {
/* 174 */             aniType = AnimationType.RELOAD_SECOND;
/*     */           } 
/* 176 */         } else if (this.reloadPhase == Phase.POST) {
/* 177 */           if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.POST_RELOAD_EMPTY)) {
/* 178 */             aniType = AnimationType.POST_RELOAD_EMPTY;
/*     */           } else {
/* 180 */             aniType = AnimationType.POST_RELOAD;
/*     */           }
/*     */         
/* 183 */         } else if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.PRE_RELOAD_EMPTY)) {
/* 184 */           aniType = AnimationType.PRE_RELOAD_EMPTY;
/*     */         } else {
/* 186 */           aniType = AnimationType.PRE_RELOAD;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 191 */     } else if (this.reloadType == ReloadType.Unload) {
/* 192 */       if (this.reloadPhase == Phase.FIRST) {
/* 193 */         aniType = AnimationType.UNLOAD;
/* 194 */       } else if (this.reloadPhase == Phase.POST) {
/* 195 */         aniType = AnimationType.POST_UNLOAD;
/* 196 */       } else if (this.reloadPhase == Phase.PRE) {
/* 197 */         aniType = AnimationType.PRE_UNLOAD;
/*     */       } 
/* 199 */     } else if (this.reloadType == ReloadType.Full) {
/* 200 */       if (this.reloadPhase == Phase.FIRST) {
/* 201 */         if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.RELOAD_FIRST_EMPTY)) {
/* 202 */           aniType = AnimationType.RELOAD_FIRST_EMPTY;
/*     */         } else {
/* 204 */           aniType = AnimationType.RELOAD_FIRST;
/*     */         } 
/* 206 */       } else if (this.reloadPhase == Phase.SECOND) {
/* 207 */         if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.RELOAD_SECOND_EMPTY)) {
/* 208 */           aniType = AnimationType.RELOAD_SECOND_EMPTY;
/*     */         } else {
/* 210 */           aniType = AnimationType.RELOAD_SECOND;
/*     */         } 
/* 212 */       } else if (this.reloadPhase == Phase.POST) {
/* 213 */         if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.POST_RELOAD_EMPTY)) {
/* 214 */           aniType = AnimationType.POST_RELOAD_EMPTY;
/*     */         } else {
/* 216 */           aniType = AnimationType.POST_RELOAD;
/*     */         }
/*     */       
/* 219 */       } else if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.PRE_RELOAD_EMPTY)) {
/* 220 */         aniType = AnimationType.PRE_RELOAD_EMPTY;
/*     */       } else {
/* 222 */         aniType = AnimationType.PRE_RELOAD;
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     return aniType;
/*     */   }
/*     */   
/*     */   public AnimationType getShootingAnimationType() {
/* 230 */     AnimationType aniType = AnimationType.PRE_FIRE;
/* 231 */     if (this.shootingPhase == Phase.FIRST) {
/* 232 */       if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.FIRE_LAST)) {
/* 233 */         aniType = AnimationType.FIRE_LAST;
/*     */       } else {
/* 235 */         aniType = AnimationType.FIRE;
/*     */       } 
/* 237 */     } else if (this.shootingPhase == Phase.POST) {
/* 238 */       if (!ItemGun.hasNextShot(this.heldItemstStack) && ((GunEnhancedRenderConfig)this.currentModel.config).animations.containsKey(AnimationType.POST_FIRE_EMPTY)) {
/* 239 */         aniType = AnimationType.POST_FIRE_EMPTY;
/*     */       } else {
/* 241 */         aniType = AnimationType.POST_FIRE;
/*     */       } 
/*     */     } 
/* 244 */     if (this.isFailedShoot && this.shootingPhase != Phase.PRE) {
/* 245 */       return null;
/*     */     }
/* 247 */     return aniType;
/*     */   }
/*     */   
/*     */   public float getReloadSppedFactor() {
/* 251 */     ItemStack stack = this.heldItemstStack;
/* 252 */     Item item = stack.func_77973_b();
/* 253 */     if (this.controller != null && 
/* 254 */       item instanceof ItemGun) {
/* 255 */       GunType type = ((ItemGun)item).type;
/* 256 */       if (ItemGun.hasAmmoLoaded(stack)) {
/* 257 */         ItemStack stackAmmo = new ItemStack(stack.func_77978_p().func_74775_l("ammo"));
/* 258 */         stackAmmo = this.controller.getRenderAmmo(stackAmmo);
/* 259 */         if (stackAmmo != null && stackAmmo.func_77973_b() instanceof ItemAmmo) {
/* 260 */           ItemAmmo itemAmmo = (ItemAmmo)stackAmmo.func_77973_b();
/* 261 */           return itemAmmo.type.reloadSpeedFactor;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 266 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public void updateCurrentItem(EntityLivingBase player) {
/* 270 */     if (!ItemStack.areItemStacksEqualUsingNBTShareTag(this.heldItemstStack, player.func_184614_ca())) {
/* 271 */       if (this.reloading) {
/* 272 */         stopReload();
/*     */       }
/* 274 */       if (!this.shooting) {
/* 275 */         reset();
/*     */       }
/*     */     } 
/*     */     
/* 279 */     this.heldItemstStack = player.func_184614_ca();
/*     */   }
/*     */   
/*     */   public void onRenderTickUpdate(float partialTick) {
/* 283 */     if (this.controller == null) {
/*     */       return;
/*     */     }
/* 286 */     ItemStack stack = this.heldItemstStack;
/* 287 */     Item item = stack.func_77973_b();
/* 288 */     if (item instanceof ItemGun) {
/* 289 */       GunType type = ((ItemGun)item).type;
/* 290 */       if (this.reloading) {
/*     */         
/* 292 */         AnimationType aniType = getReloadAnimationType();
/* 293 */         Passer<Phase> phase = new Passer(this.reloadPhase);
/* 294 */         Passer<Double> progess = new Passer(Double.valueOf(this.controller.RELOAD));
/* 295 */         this.reloading = phaseUpdate(aniType, partialTick, getReloadSppedFactor(), phase, progess, () -> {
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
/* 319 */         if (this.reloadPhase != this.lastReloadPhase && aniType != null) {
/* 320 */           switch (aniType) {
/*     */             case PRE_LOAD:
/* 322 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.PreLoad));
/*     */               break;
/*     */             case LOAD:
/* 325 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.Load));
/*     */               break;
/*     */             case POST_LOAD:
/* 328 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.PostLoad));
/*     */               break;
/*     */             case PRE_UNLOAD:
/* 331 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.PreUnload));
/*     */               break;
/*     */             case UNLOAD:
/* 334 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.Unload));
/*     */               break;
/*     */             case POST_UNLOAD:
/* 337 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.PostUnload));
/*     */               break;
/*     */             case PRE_RELOAD:
/* 340 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.PreReload));
/*     */               break;
/*     */             case PRE_RELOAD_EMPTY:
/* 343 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.PreReloadEmpty));
/*     */               break;
/*     */             case RELOAD_FIRST:
/* 346 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.Reload));
/*     */               break;
/*     */             case RELOAD_SECOND:
/* 349 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.ReloadSecond));
/*     */               break;
/*     */             case RELOAD_FIRST_QUICKLY:
/* 352 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.Reload));
/*     */               break;
/*     */             case RELOAD_SECOND_QUICKLY:
/* 355 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.ReloadSecond));
/*     */               break;
/*     */             case RELOAD_FIRST_EMPTY:
/* 358 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.ReloadEmpty));
/*     */               break;
/*     */             case RELOAD_SECOND_EMPTY:
/* 361 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.ReloadSecondEmpty));
/*     */               break;
/*     */             case POST_RELOAD:
/* 364 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.PostReload));
/*     */               break;
/*     */             case POST_RELOAD_EMPTY:
/* 367 */               ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.PostReloadEmpty));
/*     */               break;
/*     */           } 
/*     */ 
/*     */         
/*     */         }
/* 373 */         this.lastReloadPhase = this.reloadPhase;
/* 374 */         this.reloadPhase = (Phase)phase.get();
/*     */         
/* 376 */         this.controller.RELOAD = ((Double)progess.get()).doubleValue();
/* 377 */         if (!this.reloading) {
/* 378 */           this.controller.updateActionAndTime();
/* 379 */           stopReload();
/*     */         } 
/*     */       } 
/* 382 */       if (this.shooting) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 391 */         AnimationType aniType = getShootingAnimationType();
/* 392 */         Passer<Phase> phase = new Passer(this.shootingPhase);
/* 393 */         Passer<Double> progess = new Passer(Double.valueOf(this.controller.FIRE));
/* 394 */         Random r = new Random();
/* 395 */         int Low = 0;
/* 396 */         int High = type.flashType.resourceLocations.size() - 1;
/* 397 */         int result = r.nextInt(High - Low) + Low;
/* 398 */         this.shooting = phaseUpdate(aniType, partialTick, 1.0F, phase, progess, () -> { this.flashCount = result; phase.set(Phase.FIRST); }() -> phase.set(Phase.POST), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 404 */         this.shootingPhase = (Phase)phase.get();
/* 405 */         this.controller.FIRE = ((Double)progess.get()).doubleValue();
/* 406 */         if (!this.shooting) {
/* 407 */           this.controller.updateActionAndTime();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean phaseUpdate(AnimationType aniType, float partialTick, float speedFactor, Passer<Phase> phase, Passer<Double> progress, Runnable preCall, Runnable firstCall, Runnable secondCall) {
/* 415 */     boolean flag = true;
/* 416 */     GunEnhancedRenderConfig.Animation ani = null;
/* 417 */     if (aniType != null) {
/* 418 */       ani = (GunEnhancedRenderConfig.Animation)((GunEnhancedRenderConfig)this.currentModel.config).animations.get(aniType);
/*     */     }
/*     */     
/* 421 */     if (ani != null) {
/* 422 */       double speed = ani.getSpeed(this.currentModel.config.FPS) * speedFactor * partialTick;
/* 423 */       double val = ((Double)progress.get()).doubleValue() + speed;
/* 424 */       progress.set(Double.valueOf(val));
/* 425 */       if (((Double)progress.get()).doubleValue() > 1.0D) {
/* 426 */         progress.set(Double.valueOf(1.0D));
/* 427 */       } else if (((Double)progress.get()).doubleValue() < 0.0D) {
/* 428 */         progress.set(Double.valueOf(0.0D));
/*     */       } 
/*     */     } else {
/* 431 */       progress.set(Double.valueOf(1.0D));
/*     */     } 
/* 433 */     if (((Double)progress.get()).doubleValue() >= 1.0D) {
/* 434 */       if (phase.get() == Phase.POST) {
/* 435 */         flag = false;
/* 436 */         progress.set(Double.valueOf(0.0D));
/* 437 */       } else if (phase.get() == Phase.FIRST) {
/* 438 */         progress.set(Double.valueOf(Double.MIN_VALUE));
/* 439 */         if (firstCall != null) {
/* 440 */           firstCall.run();
/*     */         }
/* 442 */       } else if (phase.get() == Phase.SECOND) {
/* 443 */         progress.set(Double.valueOf(Double.MIN_VALUE));
/* 444 */         if (secondCall != null) {
/* 445 */           secondCall.run();
/*     */         }
/* 447 */       } else if (phase.get() == Phase.PRE) {
/* 448 */         progress.set(Double.valueOf(Double.MIN_VALUE));
/* 449 */         if (preCall != null) {
/* 450 */           preCall.run();
/*     */         }
/*     */       } 
/*     */     }
/* 454 */     return flag;
/*     */   }
/*     */   
/*     */   public void stopReload() {
/* 458 */     PacketGunReloadEnhancedStop packet = null;
/* 459 */     ItemStack stack = this.heldItemstStack;
/* 460 */     Item item = stack.func_77973_b();
/* 461 */     if (item instanceof ItemGun) {
/* 462 */       GunType type = ((ItemGun)item).type;
/*     */       
/* 464 */       AnimationType reloadAni = getReloadAnimationType();
/*     */       
/* 466 */       if (this.reloadType == ReloadType.Load) {
/* 467 */         if (type.acceptedAmmo != null) {
/* 468 */           if (this.reloadPhase == Phase.PRE) {
/* 469 */             packet = new PacketGunReloadEnhancedStop(0, false, false);
/*     */           } else {
/* 471 */             packet = new PacketGunReloadEnhancedStop(0, true, true);
/*     */           }
/*     */         
/* 474 */         } else if (this.reloadPhase == Phase.PRE) {
/* 475 */           packet = new PacketGunReloadEnhancedStop(0, false, false);
/*     */         } else {
/* 477 */           packet = new PacketGunReloadEnhancedStop(this.reloadMaxCount - this.reloadCount, true, true);
/*     */         }
/*     */       
/* 480 */       } else if (this.reloadType == ReloadType.Full) {
/* 481 */         if (type.acceptedAmmo != null) {
/* 482 */           if (this.reloadPhase == Phase.POST || this.reloadPhase == Phase.SECOND) {
/* 483 */             packet = new PacketGunReloadEnhancedStop(0, true, true);
/* 484 */           } else if (this.reloadPhase == Phase.FIRST) {
/* 485 */             packet = new PacketGunReloadEnhancedStop(0, true, false);
/*     */           } else {
/* 487 */             packet = new PacketGunReloadEnhancedStop(0, false, false);
/*     */           }
/*     */         
/* 490 */         } else if (this.reloadPhase == Phase.PRE) {
/* 491 */           packet = new PacketGunReloadEnhancedStop(0, false, false);
/*     */         } else {
/* 493 */           packet = new PacketGunReloadEnhancedStop(this.reloadMaxCount - this.reloadCount, true, true);
/*     */         }
/*     */       
/* 496 */       } else if (this.reloadType == ReloadType.Unload) {
/* 497 */         if (this.reloadPhase == Phase.PRE) {
/* 498 */           packet = new PacketGunReloadEnhancedStop(0, false, false);
/*     */         }
/* 500 */         else if (type.acceptedAmmo != null) {
/* 501 */           packet = new PacketGunReloadEnhancedStop(0, true, false);
/*     */         } else {
/* 503 */           packet = new PacketGunReloadEnhancedStop(this.reloadMaxCount - this.reloadCount, true, false);
/*     */         } 
/*     */       } 
/*     */       
/* 507 */       if (packet != null) {
/* 508 */         if (type.acceptedAmmo != null) {
/* 509 */           if (packet.loaded) {
/* 510 */             ItemStack ammoStack = ClientTickHandler.reloadEnhancedPrognosisAmmoRendering;
/* 511 */             if (ammoStack != null && !ammoStack.func_190926_b()) {
/* 512 */               ammoStack.func_77964_b(0);
/* 513 */               if (reloadAni == AnimationType.RELOAD_FIRST || reloadAni == AnimationType.RELOAD_FIRST_QUICKLY || reloadAni == AnimationType.UNLOAD) {
/* 514 */                 ammoStack = ItemStack.field_190927_a;
/*     */               }
/* 516 */               if (ammoStack.func_77973_b() instanceof ItemAmmo) {
/* 517 */                 this.heldItemstStack.func_77978_p().func_74782_a("ammo", (NBTBase)ammoStack.func_77955_b(new NBTTagCompound()));
/*     */               }
/*     */             } 
/* 520 */           } else if (packet.unloaded) {
/* 521 */             this.heldItemstStack.func_77978_p().func_82580_o("ammo");
/*     */           }
/*     */         
/* 524 */         } else if (packet.loaded) {
/* 525 */           ItemStack bulletStack = ClientTickHandler.reloadEnhancedPrognosisAmmoRendering;
/* 526 */           if (bulletStack != null && !bulletStack.func_190926_b()) {
/* 527 */             bulletStack.func_77964_b(0);
/* 528 */             int offset = getAmmoCountOffset(true);
/* 529 */             int ammoCount = this.heldItemstStack.func_77978_p().func_74762_e("ammocount");
/* 530 */             this.heldItemstStack.func_77978_p().func_74768_a("ammocount", ammoCount + offset);
/* 531 */             this.heldItemstStack.func_77978_p().func_74782_a("bullet", (NBTBase)bulletStack.func_77955_b(new NBTTagCompound()));
/*     */           } 
/* 533 */         } else if (packet.unloaded) {
/* 534 */           this.heldItemstStack.func_77978_p().func_74768_a("ammocount", 0);
/* 535 */           this.heldItemstStack.func_77978_p().func_82580_o("bullet");
/*     */         } 
/*     */         
/* 538 */         ModularWarfare.NETWORK.sendToServer((PacketBase)packet);
/* 539 */         ClientTickHandler.reloadEnhancedPrognosisAmmo = null;
/* 540 */         ClientTickHandler.reloadEnhancedPrognosisAmmoRendering = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSprint() {
/* 547 */     return (!this.shooting && !this.reloading && this.controller.ADS < 0.800000011920929D);
/*     */   }
/*     */   
/*     */   public int getAmmoCountOffset(boolean really) {
/* 551 */     ItemStack stack = this.heldItemstStack;
/* 552 */     if (this.heldItemstStack != null) {
/* 553 */       Item item = stack.func_77973_b();
/* 554 */       if (item instanceof ItemGun) {
/* 555 */         GunType type = ((ItemGun)item).type;
/* 556 */         if (this.reloading) {
/* 557 */           if (this.reloadType == ReloadType.Unload) {
/* 558 */             if (really) {
/* 559 */               return -(this.reloadMaxCount - this.reloadCount);
/*     */             }
/* 561 */             return -(this.reloadMaxCount - this.reloadCount - type.modifyUnloadBullets);
/*     */           } 
/*     */           
/* 564 */           return this.reloadMaxCount - this.reloadCount;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 569 */     if (this.reloadType == ReloadType.Unload) {
/* 570 */       return -this.reloadMaxCount;
/*     */     }
/* 572 */     return this.reloadMaxCount;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\enhanced\animation\EnhancedStateMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */