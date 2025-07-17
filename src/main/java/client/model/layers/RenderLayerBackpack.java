/*    */ package com.modularwarfare.client.model.layers;
/*    */ 
/*    */ import com.modularwarfare.api.AnimationUtils;
/*    */ import com.modularwarfare.client.model.ModelBackpack;
/*    */ import com.modularwarfare.common.backpacks.ItemBackpack;
/*    */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*    */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderLayerBackpack
/*    */   implements LayerRenderer<EntityPlayer>
/*    */ {
/*    */   private final ModelRenderer modelRenderer;
/*    */   private RenderPlayer renderer;
/*    */   
/*    */   public RenderLayerBackpack(RenderPlayer renderer, ModelRenderer modelRenderer) {
/* 28 */     this.modelRenderer = modelRenderer;
/* 29 */     this.renderer = renderer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 34 */     if (player.hasCapability(CapabilityExtra.CAPABILITY, null)) {
/* 35 */       IExtraItemHandler extraSlots = (IExtraItemHandler)player.getCapability(CapabilityExtra.CAPABILITY, null);
/* 36 */       ItemStack itemstackBackpack = extraSlots.getStackInSlot(0);
/*    */       
/* 38 */       if (!itemstackBackpack.func_190926_b()) {
/*    */         
/* 40 */         ItemBackpack backpack = (ItemBackpack)itemstackBackpack.func_77973_b();
/* 41 */         GlStateManager.func_179094_E();
/*    */         
/* 43 */         if (player.func_70093_af()) {
/* 44 */           GlStateManager.func_179109_b(0.0F, 0.3F, 0.0F);
/* 45 */           GlStateManager.func_179109_b(0.0F, 0.0F, 0.0F);
/* 46 */           GlStateManager.func_179114_b(30.0F, 1.0F, 0.0F, 0.0F);
/*    */         } 
/* 48 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 49 */         GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
/* 50 */         GlStateManager.func_179152_a(scale, scale, scale);
/*    */         
/* 52 */         int skinId = 0;
/* 53 */         if (itemstackBackpack.func_77942_o() && 
/* 54 */           itemstackBackpack.func_77978_p().func_74764_b("skinId")) {
/* 55 */           skinId = itemstackBackpack.func_77978_p().func_74762_e("skinId");
/*    */         }
/*    */ 
/*    */         
/* 59 */         String path = (skinId > 0) ? backpack.type.modelSkins[skinId].getSkin() : backpack.type.modelSkins[0].getSkin();
/*    */         
/* 61 */         (Minecraft.func_71410_x().func_175598_ae()).field_78724_e.func_110577_a(new ResourceLocation("modularwarfare", "skins/backpacks/" + path + ".png"));
/*    */         
/* 63 */         ModelBackpack model = (ModelBackpack)backpack.type.model;
/*    */         
/* 65 */         GlStateManager.func_179140_f();
/* 66 */         GlStateManager.func_179103_j(7425);
/* 67 */         model.render("backpackModel", 1.0F, ((ModelBackpack)backpack.type.model).config.extra.modelScale);
/* 68 */         if (player.func_184613_cA()) {
/* 69 */           model.render("elytraOnModel", 1.0F, ((ModelBackpack)backpack.type.model).config.extra.modelScale);
/*    */         } else {
/* 71 */           model.render("elytraOffModel", 1.0F, ((ModelBackpack)backpack.type.model).config.extra.modelScale);
/*    */         } 
/* 73 */         if (((Long)AnimationUtils.isJet.getOrDefault(player.func_70005_c_(), Long.valueOf(0L))).longValue() > System.currentTimeMillis()) {
/* 74 */           if (!player.func_184613_cA()) {
/* 75 */             model.render("jetOnModel", 1.0F, ((ModelBackpack)backpack.type.model).config.extra.modelScale);
/*    */           } else {
/* 77 */             model.render("jetBoostModel", 1.0F, ((ModelBackpack)backpack.type.model).config.extra.modelScale);
/*    */           } 
/*    */         } else {
/* 80 */           model.render("jetOffModel", 1.0F, ((ModelBackpack)backpack.type.model).config.extra.modelScale);
/*    */         } 
/* 82 */         GlStateManager.func_179103_j(7424);
/* 83 */         GlStateManager.func_179121_F();
/* 84 */         GlStateManager.func_179145_e();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean func_177142_b() {
/* 90 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\model\layers\RenderLayerBackpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */