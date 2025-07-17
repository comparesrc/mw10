/*    */ package com.modularwarfare.core;
/*    */ 
/*    */ import com.modularwarfare.ModConfig;
/*    */ import com.modularwarfare.client.ClientProxy;
/*    */ import com.modularwarfare.common.backpacks.BackpackType;
/*    */ import com.modularwarfare.common.backpacks.ItemBackpack;
/*    */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*    */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*    */ import com.modularwarfare.utility.OptifineHelper;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityHelper;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemElytra;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.optifine.shaders.MWFOptifineShadesHelper;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class MWFCoreHooks {
/*    */   public static void onRender0() {
/* 25 */     ClientProxy.scopeUtils.onPreRenderHand0();
/*    */   }
/*    */   
/*    */   public static void onRender1() {
/* 29 */     ClientProxy.scopeUtils.onPreRenderHand1();
/*    */   }
/*    */   
/*    */   public static void updateElytra(EntityLivingBase entityLivingBase) {
/* 33 */     boolean flag = entityLivingBase.func_184613_cA();
/*    */     
/* 35 */     if (flag && !entityLivingBase.field_70122_E && !entityLivingBase.func_184218_aH()) {
/* 36 */       ItemStack itemstack = entityLivingBase.func_184582_a(EntityEquipmentSlot.CHEST);
/*    */       
/* 38 */       if (itemstack.func_77973_b() == Items.field_185160_cR && ItemElytra.func_185069_d(itemstack)) {
/* 39 */         flag = true;
/*    */         
/* 41 */         if (!entityLivingBase.field_70170_p.field_72995_K && (
/* 42 */           EntityHelper.getTicksElytraFlying(entityLivingBase) + 1) % 20 == 0) {
/* 43 */           itemstack.func_77972_a(1, entityLivingBase);
/*    */         }
/* 45 */       } else if (entityLivingBase.hasCapability(CapabilityExtra.CAPABILITY, null)) {
/* 46 */         IExtraItemHandler extraSlots = (IExtraItemHandler)entityLivingBase.getCapability(CapabilityExtra.CAPABILITY, null);
/* 47 */         ItemStack itemstackBackpack = extraSlots.getStackInSlot(0);
/*    */         
/* 49 */         if (!itemstackBackpack.func_190926_b() && 
/* 50 */           itemstackBackpack.func_77973_b() instanceof ItemBackpack) {
/* 51 */           BackpackType backpack = ((ItemBackpack)itemstackBackpack.func_77973_b()).type;
/* 52 */           if (entityLivingBase.func_184613_cA() && !entityLivingBase.field_70122_E && 
/* 53 */             !entityLivingBase.func_184218_aH() && 
/* 54 */             backpack.isElytra) {
/* 55 */             flag = true;
/*    */           }
/*    */         }
/*    */       
/*    */       } else {
/*    */         
/* 61 */         flag = false;
/*    */       } 
/*    */     } else {
/* 64 */       flag = false;
/*    */     } 
/*    */     
/* 67 */     if (!entityLivingBase.field_70170_p.field_72995_K) {
/* 68 */       EntityHelper.setFlag((Entity)entityLivingBase, 7, flag);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void renderLivingAtForRenderPlayer(EntityLivingBase entityLivingBaseIn, double x, double y, double z) {
/* 73 */     GlStateManager.func_179109_b((float)x, (float)y, (float)z);
/* 74 */     if (OptifineHelper.isShadersEnabled() && 
/* 75 */       Shaders.isShadowPass && MWFOptifineShadesHelper.getPreShadowPassThirdPersonView() == 0 && 
/* 76 */       entityLivingBaseIn == (Minecraft.func_71410_x()).field_71439_g) {
/* 77 */       Vec3d vec = new Vec3d(0.0D, 0.0D, -ModConfig.INSTANCE.general.playerShadowOffset);
/* 78 */       vec = vec.func_178785_b((float)Math.toRadians(-(Minecraft.func_71410_x()).field_71439_g.field_70177_z));
/* 79 */       GlStateManager.func_179137_b(vec.field_72450_a, vec.field_72448_b, vec.field_72449_c);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\core\MWFCoreHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */