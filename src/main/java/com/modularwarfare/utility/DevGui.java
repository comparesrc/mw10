/*     */ package com.modularwarfare.utility;
/*     */ 
/*     */ import com.modularwarfare.client.fpp.basic.animations.AnimStateMachine;
/*     */ import com.modularwarfare.client.fpp.basic.animations.StateType;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderGunStatic;
/*     */ import com.modularwarfare.client.model.ModelGun;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAmmo;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class DevGui
/*     */   extends Gui {
/*     */   public DevGui(Minecraft mc, ItemStack itemStack, ItemGun itemGun, RenderGunStatic renderGun, AnimStateMachine anim) {
/*  21 */     GunType gunType = ((ItemGun)itemStack.func_77973_b()).type;
/*  22 */     ModelGun gunModel = (ModelGun)gunType.model;
/*     */     
/*  24 */     ScaledResolution scaled = new ScaledResolution(mc);
/*     */     
/*  26 */     float textScale = 0.75F;
/*  27 */     int width = (int)((scaled.func_78326_a() / 50) * textScale);
/*  28 */     int height = (int)((scaled.func_78328_b() / 50) * textScale);
/*     */     
/*  30 */     if (mc.field_71439_g.field_71071_by.func_70440_f(3) != null && mc.field_71439_g.field_71071_by.func_70440_f(3).func_77973_b() == Items.field_151169_ag) {
/*     */       
/*  32 */       boolean hasAmmo = ItemGun.hasAmmoLoaded(itemStack);
/*     */ 
/*     */       
/*  35 */       String displayName = "Display Name- " + gunType.displayName;
/*  36 */       String internalName = "Internal Name - " + gunType.internalName;
/*  37 */       String modelScale = "Model Scale - " + Float.toString(((ModelGun)gunType.model).config.extra.modelScale);
/*  38 */       String iconName = "Icon Name - " + gunType.iconName;
/*  39 */       String skinNames = "Skin Name(s) - " + Arrays.toString((Object[])gunType.modelSkins).replace("[", "").replace("]", "");
/*  40 */       String dynamicAmmo = "Dynamic Ammo Model - " + Boolean.toString(gunType.dynamicAmmo);
/*  41 */       String reloadAnim = "Reload Anim - " + ((ModelGun)gunType.model).config.extra.reloadAnimation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  47 */       GL11.glPushMatrix();
/*     */       
/*  49 */       GL11.glScalef(textScale, textScale, textScale);
/*     */       
/*  51 */       mc.field_71466_p.func_175065_a("Gun Visuals;", width, (height + 88), Integer.parseInt("FF0000", 16), false);
/*  52 */       mc.field_71466_p.func_175065_a(internalName, width, (height + 96), Integer.parseInt("FFFFFF", 16), false);
/*  53 */       mc.field_71466_p.func_175065_a(displayName, width, (height + 104), Integer.parseInt("FFFFFF", 16), false);
/*  54 */       mc.field_71466_p.func_175065_a(modelScale, width, (height + 120), Integer.parseInt("FFFFFF", 16), false);
/*  55 */       mc.field_71466_p.func_175065_a(iconName, width, (height + 128), Integer.parseInt("FFFFFF", 16), false);
/*  56 */       mc.field_71466_p.func_175065_a(skinNames, width, (height + 136), Integer.parseInt("FFFFFF", 16), false);
/*  57 */       mc.field_71466_p.func_175065_a(dynamicAmmo, width, (height + 144), Integer.parseInt("FFFFFF", 16), false);
/*  58 */       mc.field_71466_p.func_175065_a(reloadAnim, width, (height + 152), Integer.parseInt("FFFFFF", 16), false);
/*     */       
/*  60 */       GL11.glPopMatrix();
/*     */       
/*  62 */       if (hasAmmo) {
/*  63 */         ItemStack ammoStack = new ItemStack(itemStack.func_77978_p().func_74775_l("ammo"));
/*  64 */         ItemAmmo itemAmmo = (ItemAmmo)ammoStack.func_77973_b();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  69 */     if (mc.field_71439_g.field_71071_by.func_70440_f(2) != null && mc.field_71439_g.field_71071_by.func_70440_f(2).func_77973_b() == Items.field_151171_ah) {
/*     */       
/*  71 */       String gunDamage = "Damage/RPM - " + Float.toString(gunType.gunDamage) + "/" + Float.toString(gunType.roundsPerMin);
/*  72 */       String reloadTime = "Reload Time - " + Float.toString(gunType.reloadTime) + "ticks";
/*  73 */       String ammoTypes = "Ammos - " + Arrays.toString((Object[])gunType.acceptedAmmo).replace("[", "").replace("]", "");
/*  74 */       String fireModes = "Fire Modes - " + Arrays.toString((Object[])gunType.fireModes).replace("[", "").replace("]", "");
/*  75 */       String gunRange = "Range Effective/Max - " + Float.toString(gunType.weaponEffectiveRange) + "/" + Float.toString(gunType.weaponMaxRange);
/*  76 */       String sprintFire = "Sprint Fire - " + Boolean.toString(gunType.allowSprintFiring);
/*  77 */       String recoilPitch = "Recoil Pitch - " + Float.toString(gunType.recoilPitch) + " +/- " + Float.toString(gunType.randomRecoilPitch);
/*  78 */       String recoilYaw = "Recoil Yaw - " + Float.toString(gunType.recoilYaw) + " +/- " + Float.toString(gunType.randomRecoilYaw);
/*  79 */       String modelRecoil = "Model Recoil Pitch/Back/Shake - " + Float.toString(((ModelGun)gunType.model).config.extra.modelRecoilUpwards) + "/" + Float.toString(((ModelGun)gunType.model).config.extra.modelRecoilBackwards) + "/" + Float.toString(((ModelGun)gunType.model).config.extra.modelRecoilShake);
/*  80 */       GL11.glPushMatrix();
/*     */       
/*  82 */       GL11.glScalef(textScale, textScale, textScale);
/*     */       
/*  84 */       mc.field_71466_p.func_175065_a("Gun Stats;", width, height, Integer.parseInt("FF0000", 16), false);
/*  85 */       mc.field_71466_p.func_175065_a(gunDamage, width, (height + 8), Integer.parseInt("FFFFFF", 16), false);
/*  86 */       mc.field_71466_p.func_175065_a(reloadTime, width, (height + 16), Integer.parseInt("FFFFFF", 16), false);
/*  87 */       mc.field_71466_p.func_175065_a(fireModes, width, (height + 24), Integer.parseInt("FFFFFF", 16), false);
/*  88 */       mc.field_71466_p.func_175065_a(gunRange, width, (height + 32), Integer.parseInt("FFFFFF", 16), false);
/*  89 */       mc.field_71466_p.func_175065_a(sprintFire, width, (height + 40), Integer.parseInt("FFFFFF", 16), false);
/*  90 */       mc.field_71466_p.func_175065_a(recoilPitch, width, (height + 48), Integer.parseInt("FFFFFF", 16), false);
/*  91 */       mc.field_71466_p.func_175065_a(recoilYaw, width, (height + 56), Integer.parseInt("FFFFFF", 16), false);
/*  92 */       mc.field_71466_p.func_175065_a(modelRecoil, width, (height + 64), Integer.parseInt("FFFFFF", 16), false);
/*  93 */       mc.field_71466_p.func_175065_a(ammoTypes, width, (height + 72), Integer.parseInt("FFFFFF", 16), false);
/*     */       
/*  95 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/*  98 */     if (mc.field_71439_g.field_71071_by.func_70440_f(0) != null && mc.field_71439_g.field_71071_by.func_70440_f(0).func_77973_b() == Items.field_151151_aj) {
/*     */ 
/*     */       
/* 101 */       String movingArmState = "Moving Arm State - " + RenderGunStatic.getMovingArmState(gunModel, anim);
/* 102 */       String staticArmState = "Static Arm State - " + RenderGunStatic.getStaticArmState(gunModel, anim);
/* 103 */       String animTilt = "Tilt State - " + Boolean.toString(anim.isReloadState(StateType.Tilt));
/* 104 */       String animUnload = "Unload State - " + Boolean.toString(anim.isReloadState(StateType.Unload));
/* 105 */       String animLoad = "Load State - " + Boolean.toString(anim.isReloadState(StateType.Load));
/* 106 */       String animUntilt = "Untilt State - " + Boolean.toString(anim.isReloadState(StateType.Untilt));
/* 107 */       GL11.glPushMatrix();
/*     */       
/* 109 */       GL11.glScalef(textScale, textScale, textScale);
/*     */       
/* 111 */       mc.field_71466_p.func_175065_a("Hand Debug;", width, height, Integer.parseInt("FF0000", 16), false);
/* 112 */       mc.field_71466_p.func_175065_a(movingArmState, width, (height + 8), Integer.parseInt("FFFFFF", 16), false);
/* 113 */       mc.field_71466_p.func_175065_a(staticArmState, width, (height + 16), Integer.parseInt("FFFFFF", 16), false);
/* 114 */       mc.field_71466_p.func_175065_a(animTilt, width, (height + 24), Integer.parseInt("FFFFFF", 16), false);
/* 115 */       mc.field_71466_p.func_175065_a(animUnload, width, (height + 32), Integer.parseInt("FFFFFF", 16), false);
/* 116 */       mc.field_71466_p.func_175065_a(animLoad, width, (height + 40), Integer.parseInt("FFFFFF", 16), false);
/* 117 */       mc.field_71466_p.func_175065_a(animUntilt, width, (height + 48), Integer.parseInt("FFFFFF", 16), false);
/*     */       
/* 119 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfar\\utility\DevGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */