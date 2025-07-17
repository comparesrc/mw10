/*     */ package com.modularwarfare.client.fpp.basic.animations;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.WeaponAnimations;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderParameters;
/*     */ import com.modularwarfare.client.input.KeyBindingDisable;
/*     */ import com.modularwarfare.client.input.KeyBindingEnable;
/*     */ import com.modularwarfare.client.model.ModelGun;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.WeaponSoundType;
/*     */ import com.modularwarfare.common.guns.WeaponType;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketGunReloadSound;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Optional;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnimStateMachine
/*     */ {
/*     */   public boolean reloading = false;
/*  29 */   public float reloadProgress = 0.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tiltHold = false;
/*     */ 
/*     */   
/*     */   public boolean attachmentMode = false;
/*     */ 
/*     */   
/*     */   public boolean shooting = false;
/*     */ 
/*     */   
/*  42 */   public float gunRecoil = 0.0F, lastGunRecoil = 0.0F;
/*     */ 
/*     */ 
/*     */   
/*  46 */   public float gunSlide = 0.0F, lastGunSlide = 0.0F;
/*     */ 
/*     */ 
/*     */   
/*  50 */   public float hammerRotation = 0.0F;
/*  51 */   public int timeUntilPullback = 0;
/*  52 */   public float gunPullback = -1.0F; public float lastGunPullback = -1.0F;
/*     */ 
/*     */   
/*     */   public boolean isFired = false;
/*     */   
/*  57 */   public float revolverBarrelRotationPerShoot = 0.0F;
/*  58 */   public float revolverBarrelRotation = 0.0F;
/*     */ 
/*     */ 
/*     */   
/*  62 */   public int bulletsToRender = 1;
/*     */ 
/*     */   
/*     */   public ItemStack cachedAmmoStack;
/*     */   
/*  67 */   public int reloadAmmoCount = 1;
/*     */ 
/*     */   
/*     */   public boolean isGunEmpty = false;
/*     */   
/*  72 */   public int muzzleFlashTime = 0;
/*  73 */   public int flashInt = 0;
/*     */   
/*     */   public boolean hasPlayedReloadSound = false;
/*     */   
/*     */   public boolean wasSprinting = false;
/*     */   
/*     */   private float reloadTime;
/*     */   
/*     */   private ReloadType reloadType;
/*     */   
/*     */   private ArrayList<StateEntry> reloadStateEntries;
/*     */   
/*     */   private StateEntry currentReloadState;
/*  86 */   private int reloadStateIndex = 0;
/*     */   private float shootTime;
/*  88 */   private float shootProgress = 0.0F;
/*     */   private ArrayList<StateEntry> shootStateEntries;
/*     */   private StateEntry currentShootState;
/*  91 */   private int shootStateIndex = 0;
/*     */   
/*     */   public static void disableSprinting(boolean bool) {
/*  94 */     if (bool) {
/*  95 */       if (!((Minecraft.func_71410_x()).field_71474_y.field_151444_V instanceof KeyBindingDisable)) {
/*  96 */         (Minecraft.func_71410_x()).field_71474_y.field_151444_V = (KeyBinding)new KeyBindingDisable((Minecraft.func_71410_x()).field_71474_y.field_151444_V);
/*     */       }
/*  98 */     } else if ((Minecraft.func_71410_x()).field_71474_y.field_151444_V instanceof KeyBindingDisable) {
/*  99 */       (Minecraft.func_71410_x()).field_71474_y.field_151444_V = (KeyBinding)new KeyBindingEnable((Minecraft.func_71410_x()).field_71474_y.field_151444_V);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onTickUpdate() {
/* 104 */     ItemGun gun = null;
/* 105 */     if ((Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && 
/* 106 */       (Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun) {
/* 107 */       gun = (ItemGun)(Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b();
/*     */     }
/*     */     
/* 110 */     if (this.reloading) {
/* 111 */       disableSprinting(true);
/* 112 */       (Minecraft.func_71410_x()).field_71439_g.func_70031_b(false);
/* 113 */       if (this.currentReloadState == null) {
/* 114 */         this.currentReloadState = this.reloadStateEntries.get(0);
/*     */       }
/* 116 */       if (this.currentReloadState.stateType == StateType.Tilt)
/* 117 */         this.tiltHold = true; 
/* 118 */       if (this.currentReloadState.stateType == StateType.Untilt) {
/* 119 */         this.tiltHold = false;
/*     */       }
/* 121 */       if (this.reloadProgress >= this.currentReloadState.cutOffTime && 
/* 122 */         this.reloadStateIndex + 1 < this.reloadStateEntries.size()) {
/* 123 */         this.reloadStateIndex++;
/* 124 */         this.currentReloadState.finished = true;
/* 125 */         this.currentReloadState = this.reloadStateEntries.get(this.reloadStateIndex);
/*     */       } 
/*     */ 
/*     */       
/* 129 */       this.reloadProgress += 1.0F / this.reloadTime;
/* 130 */       if (this.reloadProgress >= 0.8F) {
/* 131 */         disableSprinting(false);
/* 132 */         (Minecraft.func_71410_x()).field_71439_g.func_70031_b(this.wasSprinting);
/*     */       } 
/* 134 */       if (this.reloadProgress >= 1.0F) {
/* 135 */         this.isGunEmpty = false;
/*     */         
/* 137 */         this.reloading = false;
/* 138 */         this.reloadProgress = 0.0F;
/* 139 */         this.reloadStateEntries = null;
/* 140 */         this.currentReloadState = null;
/* 141 */         this.reloadStateIndex = 0;
/* 142 */         this.reloadType = null;
/*     */       } 
/* 144 */       if (!this.hasPlayedReloadSound) {
/* 145 */         ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReloadSound(WeaponSoundType.Load));
/* 146 */         this.hasPlayedReloadSound = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 151 */     if (gun != null && 
/* 152 */       !gun.type.allowAimingSprint && RenderParameters.adsSwitch > 0.2F) {
/* 153 */       (Minecraft.func_71410_x()).field_71439_g.func_70031_b(false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 158 */     if (this.shooting) {
/* 159 */       if (this.currentShootState == null) {
/* 160 */         this.currentShootState = this.shootStateEntries.get(0);
/*     */       }
/* 162 */       if (this.shootProgress >= this.currentShootState.cutOffTime && 
/* 163 */         this.shootStateIndex + 1 < this.shootStateEntries.size()) {
/* 164 */         this.shootStateIndex++;
/* 165 */         this.currentShootState.finished = true;
/* 166 */         this.currentShootState = this.shootStateEntries.get(this.shootStateIndex);
/*     */       } 
/*     */ 
/*     */       
/* 170 */       this.shootProgress += 1.0F / this.shootTime;
/*     */       
/* 172 */       if (this.shootProgress >= 1.0F) {
/* 173 */         this.shooting = false;
/* 174 */         this.shootProgress = 0.0F;
/* 175 */         this.shootStateEntries = null;
/* 176 */         this.currentShootState = null;
/* 177 */         this.shootStateIndex = 0;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     this.lastGunSlide = this.gunSlide;
/* 185 */     if (this.isGunEmpty)
/* 186 */       this.lastGunSlide = this.gunSlide = 0.5F; 
/* 187 */     if (!this.isGunEmpty && this.gunSlide > 0.9D) {
/* 188 */       this.gunSlide -= 0.1F;
/* 189 */     } else if (this.gunSlide > 0.0F && !this.isGunEmpty) {
/* 190 */       this.gunSlide *= 0.5F;
/*     */     } 
/*     */ 
/*     */     
/* 194 */     this.lastGunRecoil = this.gunRecoil;
/* 195 */     if (this.gunRecoil > 0.0F) {
/* 196 */       this.gunRecoil *= 0.5F;
/*     */     }
/*     */     
/* 199 */     if (this.isFired) {
/* 200 */       this.gunPullback += 0.5F;
/* 201 */       if (this.gunPullback >= 0.999F) {
/* 202 */         this.isFired = false;
/*     */       }
/*     */     } 
/* 205 */     if (this.timeUntilPullback > 0) {
/* 206 */       this.timeUntilPullback--;
/* 207 */       if (this.timeUntilPullback == 0) {
/*     */         
/* 209 */         this.isFired = true;
/* 210 */         this.lastGunPullback = this.gunPullback = -1.0F;
/*     */       } 
/*     */     } else {
/* 213 */       this.hammerRotation *= 0.6F;
/*     */     } 
/*     */     
/* 216 */     if (this.muzzleFlashTime > 0)
/* 217 */       this.muzzleFlashTime--; 
/*     */   }
/*     */   
/*     */   public void onRenderTickUpdate() {
/* 221 */     if (this.reloading && this.currentReloadState != null) {
/* 222 */       this.currentReloadState.onTick(this.reloadTime);
/*     */     }
/* 224 */     if (this.shooting && this.currentShootState != null)
/* 225 */       this.currentShootState.onTick(this.shootTime); 
/*     */   }
/*     */   
/*     */   public void triggerShoot(ModelGun model, GunType gunType, int fireTickDelay) {
/* 229 */     Random r = new Random();
/*     */     
/* 231 */     this.lastGunRecoil = this.gunRecoil = 1.0F;
/* 232 */     this.lastGunSlide = this.gunSlide = 1.0F;
/* 233 */     this.hammerRotation = model.hammerAngle;
/*     */     
/* 235 */     if (gunType.weaponType == WeaponType.Revolver && gunType.internalAmmoStorage != null) {
/* 236 */       this.revolverBarrelRotationPerShoot = (360 / gunType.internalAmmoStorage.intValue());
/* 237 */       this.revolverBarrelRotation += this.revolverBarrelRotationPerShoot;
/*     */     } 
/*     */     
/* 240 */     this.timeUntilPullback = model.hammerDelay;
/* 241 */     this.muzzleFlashTime = 2;
/*     */     
/* 243 */     int Low = 0;
/* 244 */     int High = gunType.flashType.resourceLocations.size() - 1;
/* 245 */     int result = r.nextInt(High - Low) + Low;
/* 246 */     this.flashInt = result;
/*     */     
/* 248 */     ArrayList<StateEntry> animEntries = WeaponAnimations.getAnimation(model.config.extra.reloadAnimation).getShootStates(model, gunType);
/* 249 */     if (animEntries.size() > 0) {
/* 250 */       this.shootStateEntries = adjustTiming(animEntries);
/* 251 */       this.shooting = true;
/* 252 */       this.shootTime = fireTickDelay;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void triggerReload(int reloadTime, int reloadCount, ModelGun model, ReloadType reloadType, boolean wasSprinting) {
/* 257 */     ArrayList<StateEntry> animEntries = WeaponAnimations.getAnimation(model.config.extra.reloadAnimation).getReloadStates(reloadType, reloadCount);
/* 258 */     this.reloadStateEntries = adjustTiming(animEntries);
/*     */     
/* 260 */     if ((Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && 
/* 261 */       (Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun) {
/* 262 */       ItemGun gun = (ItemGun)(Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b();
/* 263 */       if (gun.type.weaponType == WeaponType.Revolver && 
/* 264 */         gun.type.internalAmmoStorage != null) {
/* 265 */         this.bulletsToRender = gun.type.internalAmmoStorage.intValue() - reloadCount;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 270 */     this.reloadTime = (reloadType != ReloadType.Full) ? (reloadTime * 0.65F) : reloadTime;
/* 271 */     this.reloadType = reloadType;
/* 272 */     this.reloading = true;
/* 273 */     this.hasPlayedReloadSound = false;
/* 274 */     this.wasSprinting = wasSprinting;
/*     */   }
/*     */   
/*     */   public Optional<StateEntry> getReloadState() {
/* 278 */     return Optional.ofNullable(this.currentReloadState);
/*     */   }
/*     */   
/*     */   public boolean isReloadState(StateType stateType) {
/* 282 */     return (this.currentReloadState != null) ? ((this.currentReloadState.stateType == stateType)) : false;
/*     */   }
/*     */   
/*     */   public Optional<StateEntry> getShootState() {
/* 286 */     return Optional.ofNullable(this.currentShootState);
/*     */   }
/*     */   
/*     */   public boolean isShootState(StateType stateType) {
/* 290 */     return (this.currentShootState != null) ? ((this.currentShootState.stateType == stateType)) : false;
/*     */   }
/*     */   
/*     */   public boolean shouldRenderAmmo() {
/* 294 */     if (this.reloading) {
/* 295 */       Optional<StateEntry> state; switch (this.reloadType) {
/*     */         case Load:
/* 297 */           state = getState(StateType.Load);
/* 298 */           return state.isPresent() ? ((((StateEntry)state.get()).currentValue < 1.0F)) : false;
/*     */         
/*     */         case Unload:
/* 301 */           state = getState(StateType.Unload);
/* 302 */           return state.isPresent() ? ((((StateEntry)state.get()).currentValue < 1.0F)) : false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 309 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canSprint() {
/* 313 */     return (!this.shooting && !this.reloading);
/*     */   }
/*     */   
/*     */   public boolean isReloadType(ReloadType type) {
/* 317 */     return (this.reloadType != null && this.reloadType == type);
/*     */   }
/*     */ 
/*     */   
/*     */   private ArrayList<StateEntry> adjustTiming(ArrayList<StateEntry> animEntries) {
/* 322 */     float currentTiming = 0.0F;
/* 323 */     float dividedAmount = 0.0F;
/* 324 */     float cutOffTime = 0.0F;
/* 325 */     for (StateEntry entry : animEntries)
/* 326 */       currentTiming += entry.stateTime; 
/* 327 */     if (currentTiming < 1.0F)
/* 328 */       dividedAmount = (1.0F - currentTiming) / animEntries.size(); 
/* 329 */     if (dividedAmount > 0.0F) {
/* 330 */       for (StateEntry entry : animEntries) {
/* 331 */         entry.stateTime += dividedAmount;
/*     */       }
/*     */     }
/* 334 */     for (StateEntry entry : animEntries) {
/* 335 */       cutOffTime += entry.stateTime;
/* 336 */       entry.cutOffTime += cutOffTime;
/*     */     } 
/* 338 */     return animEntries;
/*     */   }
/*     */   
/*     */   private Optional<StateEntry> getState(StateType stateType) {
/* 342 */     StateEntry stateEntry = null;
/*     */     
/* 344 */     if (this.reloadStateEntries == null) {
/* 345 */       return Optional.ofNullable(stateEntry);
/*     */     }
/* 347 */     for (StateEntry entry : this.reloadStateEntries) {
/* 348 */       if (entry.stateType == stateType) {
/* 349 */         stateEntry = entry;
/*     */         break;
/*     */       } 
/*     */     } 
/* 353 */     return Optional.ofNullable(stateEntry);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\animations\AnimStateMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */