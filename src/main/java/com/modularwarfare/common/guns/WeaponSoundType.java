/*     */ package com.modularwarfare.common.guns;
/*     */ 
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum WeaponSoundType
/*     */ {
/*  11 */   Idle("weaponIdle", 8, null),
/*     */   
/*  13 */   IdleEmpty("weaponIdleEmpty", 8, null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  18 */   DryFire("weaponDryFire", 8, "defemptyclick"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  23 */   Fire("weaponFire", 64, null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   FireSuppressed("weaponFireSuppressed", 32, null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   FireLast("weaponFireLast", 16, null),
/*     */   
/*  35 */   PreReload("weaponPreReload", 16, null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   Reload("weaponReload", 16, "reload"),
/*     */   
/*  42 */   ReloadSecond("weaponReloadSecond", 16, null),
/*     */   
/*  44 */   PostReload("weaponPostReload", 16, null),
/*     */   
/*  46 */   PreReloadEmpty("weaponPreReloadEmpty", 16, null),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   ReloadEmpty("weaponReloadEmpty", 16, "reload"),
/*     */   
/*  54 */   ReloadSecondEmpty("weaponReloadSecondEmpty", 16, null),
/*     */   
/*  56 */   PostReloadEmpty("weaponPostReloadEmpty", 16, null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   Pump("weaponBolt", 8, null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   BulletLoad("weaponBulletLoad", 8, null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   Crack("crack", 10, "crack"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   Equip("equip", 8, "human.equip.gun"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   Hitmarker("hitmarker", 8, "hitmarker"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   Penetration("penetration", 20, "penetration"),
/*     */   
/*  88 */   PreLoad("weaponPreLoad", 12, null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   Load("weaponLoad", 12, "load"),
/*     */   
/*  95 */   PostLoad("weaponPostLoad", 12, null),
/*     */   
/*  97 */   PreUnload("weaponPreUnload", 12, null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   Unload("weaponUnload", 12, "unload"),
/*     */   
/* 104 */   Draw("weaponDraw", 12, "human.equip.gun"),
/*     */   
/* 106 */   DrawEmpty("weaponDrawEmpty", 12, null),
/*     */ 
/*     */   
/* 109 */   PostUnload("weaponPostUnload", 12, null),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   Charge("weaponCharge", 16, null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   ModeSwitch("weaponModeSwitch", 8, "defweaponmodeswitch"),
/*     */   
/* 122 */   Inspect("inspect", 8, null),
/* 123 */   InspectEmpty("inspectEmpty", 8, null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   FlyBy("bulletFlyBy", 3, "flyby"),
/*     */   
/* 130 */   JetWork("jetWork", 12, null),
/* 131 */   JetFire("jetFire", 12, null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   Casing("casing", 3, "casing"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   Casing_Gauge("casing_gauge", 3, "casing_gauge"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   Spray("spray", 8, "spray"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   Punched("punched", 64, "punched"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   AttachmentOpen("attachmentOpen", 10, "attachment.open"),
/*     */ 
/*     */ 
/*     */   
/* 162 */   AttachmentApply("attachmentApply", 10, "attachment.apply"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   ImpactDirt("impact.dirt", 10, "impact.dirt"),
/*     */ 
/*     */ 
/*     */   
/* 171 */   ImpactGlass("impact.glass", 10, "impact.glass"),
/*     */ 
/*     */ 
/*     */   
/* 175 */   ImpactMetal("impact.metal", 10, "impact.metal"),
/*     */ 
/*     */ 
/*     */   
/* 179 */   ImpactStone("impact.stone", 10, "impact.stone"),
/*     */ 
/*     */ 
/*     */   
/* 183 */   ImpactWater("impact.water", 10, "impact.water"),
/*     */ 
/*     */ 
/*     */   
/* 187 */   ImpactWood("impact.wood", 10, "impact.wood"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   MeleeDraw("meleeDraw", 5, "melee.draw"),
/*     */ 
/*     */ 
/*     */   
/* 196 */   MeleeInspect("meleeInspect", 5, "melee.inspect"),
/*     */   
/* 198 */   MeleePreAttack("meleePreAttack", 5, "melee.attack"),
/* 199 */   MeleeAttack("meleeAttack", 5, "melee.attack"),
/* 200 */   MeleeAttackSecond("meleeAttackSecond", 5, "melee.attack"),
/* 201 */   MeleePostAttack("meleePostAttack", 5, "melee.attack"),
/* 202 */   MeleeBounced("meleeBounced", 5, "melee.attack"),
/*     */ 
/*     */   
/* 205 */   MeleePreAttackHeavy("meleePreAttackHeavy", 5, "melee.attack"),
/* 206 */   MeleeAttackHeavy("meleeAttackHeavy", 5, "melee.attack"),
/* 207 */   MeleeAttackHeavySecond("meleeAttackHeavySecond", 5, "melee.attack"),
/* 208 */   MeleePostAttackHeavy("meleePostAttackHeavy", 5, "melee.attack"),
/* 209 */   MeleeBouncedHeavy("meleeBouncedHeavy", 5, "melee.attack"),
/* 210 */   MeleeHit("meleeHit", 5, "melee.attack");
/*     */   
/*     */   public String eventName;
/*     */   
/*     */   public Integer defaultRange;
/*     */   public String defaultSound;
/*     */   
/*     */   WeaponSoundType(String eventName, int defaultRange, String defaultSound) {
/* 218 */     this.eventName = eventName;
/* 219 */     this.defaultRange = Integer.valueOf(defaultRange);
/* 220 */     this.defaultSound = defaultSound;
/*     */   }
/*     */   
/*     */   public static WeaponSoundType fromString(String input) {
/* 224 */     for (WeaponSoundType soundType : values()) {
/* 225 */       if (soundType.toString().equalsIgnoreCase(input)) {
/* 226 */         return soundType;
/*     */       }
/*     */     } 
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 234 */     return this.eventName;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\guns\WeaponSoundType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */