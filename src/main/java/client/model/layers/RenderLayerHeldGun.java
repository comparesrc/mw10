/*    */ package com.modularwarfare.client.model.layers;
/*    */ 
/*    */ import com.modularwarfare.api.RenderHeldItemLayerEvent;
/*    */ import com.modularwarfare.client.ClientProxy;
/*    */ import com.modularwarfare.client.ClientRenderHooks;
/*    */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderType;
/*    */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*    */ import com.modularwarfare.client.fpp.enhanced.configs.RenderType;
/*    */ import com.modularwarfare.client.fpp.enhanced.models.EnhancedModel;
/*    */ import com.modularwarfare.common.guns.GunType;
/*    */ import com.modularwarfare.common.guns.WeaponAnimationType;
/*    */ import com.modularwarfare.common.type.BaseItem;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumHandSide;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderLayerHeldGun
/*    */   extends LayerHeldItem
/*    */ {
/*    */   public RenderLayerHeldGun(RenderLivingBase<?> livingEntityRendererIn) {
/* 49 */     super(livingEntityRendererIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_177141_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 54 */     ItemStack itemstack = entitylivingbaseIn.func_184614_ca();
/* 55 */     if (itemstack != ItemStack.field_190927_a && !itemstack.func_190926_b()) {
/*    */       
/* 57 */       RenderHeldItemLayerEvent event = new RenderHeldItemLayerEvent(itemstack, this, entitylivingbaseIn, partialTicks);
/* 58 */       MinecraftForge.EVENT_BUS.post((Event)event);
/*    */       
/* 60 */       if (!(itemstack.func_77973_b() instanceof com.modularwarfare.common.guns.ItemGun)) {
/*    */         return;
/*    */       }
/* 63 */       BaseType type = ((BaseItem)itemstack.func_77973_b()).baseType;
/* 64 */       if (!type.hasModel()) {
/*    */         return;
/*    */       }
/*    */       
/* 68 */       GlStateManager.func_179094_E();
/* 69 */       if (entitylivingbaseIn.func_70093_af()) {
/* 70 */         GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
/*    */       }
/*    */       
/* 73 */       if (((GunType)type).animationType == WeaponAnimationType.BASIC) {
/* 74 */         func_191361_a(EnumHandSide.RIGHT);
/* 75 */         GlStateManager.func_179137_b(-0.06D, 0.38D, -0.02D);
/* 76 */         if (ClientRenderHooks.customRenderers[type.id] != null) {
/* 77 */           ClientRenderHooks.customRenderers[type.id].renderItem(CustomItemRenderType.EQUIPPED, null, itemstack, new Object[] { entitylivingbaseIn.field_70170_p, entitylivingbaseIn, 
/* 78 */                 Float.valueOf(partialTicks) });
/*    */         }
/* 80 */       } else if (((GunType)type).animationType == WeaponAnimationType.ENHANCED) {
/*    */         
/* 82 */         GunType gunType = (GunType)type;
/* 83 */         EnhancedModel model = type.enhancedModel;
/*    */         
/* 85 */         GunEnhancedRenderConfig config = (GunEnhancedRenderConfig)gunType.enhancedModel.config;
/*    */         
/* 87 */         ClientProxy.gunEnhancedRenderer.drawThirdGun(this.field_177206_a, RenderType.PLAYER, entitylivingbaseIn, itemstack);
/*    */       } 
/*    */ 
/*    */       
/* 91 */       GlStateManager.func_179121_F();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\model\layers\RenderLayerHeldGun.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */