/*    */ package com.modularwarfare.client.model.layers;
/*    */ 
/*    */ import com.modularwarfare.client.ClientRenderHooks;
/*    */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderType;
/*    */ import com.modularwarfare.client.model.ModelCustomArmor;
/*    */ import com.modularwarfare.common.armor.ArmorType;
/*    */ import com.modularwarfare.common.armor.ItemSpecialArmor;
/*    */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*    */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*    */ import com.modularwarfare.common.network.BackWeaponsManager;
/*    */ import com.modularwarfare.common.type.BaseItem;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
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
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderLayerBody
/*    */   implements LayerRenderer<EntityPlayer> {
/*    */   private final ModelRenderer modelRenderer;
/*    */   private RenderPlayer renderer;
/*    */   
/*    */   public RenderLayerBody(RenderPlayer renderer, ModelRenderer modelRenderer) {
/* 34 */     this.modelRenderer = modelRenderer;
/* 35 */     this.renderer = renderer;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 39 */     int[] slots = { 1 };
/*    */     
/* 41 */     if (player.hasCapability(CapabilityExtra.CAPABILITY, null)) {
/* 42 */       IExtraItemHandler extraSlots = (IExtraItemHandler)player.getCapability(CapabilityExtra.CAPABILITY, null);
/* 43 */       for (int slot : slots) {
/* 44 */         ItemStack itemStackSpecialArmor = extraSlots.getStackInSlot(slot);
/* 45 */         if (!itemStackSpecialArmor.func_190926_b() && itemStackSpecialArmor.func_77973_b() instanceof ItemSpecialArmor) {
/* 46 */           renderBody(player, ((ItemSpecialArmor)itemStackSpecialArmor.func_77973_b()).type, scale);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     if (player instanceof AbstractClientPlayer) {
/*    */       
/* 53 */       ItemStack gun = BackWeaponsManager.INSTANCE.getItemToRender((AbstractClientPlayer)player);
/* 54 */       if (gun != ItemStack.field_190927_a && !gun.func_190926_b()) {
/* 55 */         BaseType type = ((BaseItem)gun.func_77973_b()).baseType;
/*    */         
/* 57 */         GlStateManager.func_179094_E();
/* 58 */         if (ClientRenderHooks.customRenderers[type.id] != null) {
/* 59 */           if (player.func_70093_af()) {
/* 60 */             GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
/* 61 */             GlStateManager.func_179114_b(30.0F, 1.0F, 0.0F, 0.0F);
/*    */           } 
/* 63 */           GlStateManager.func_179137_b(0.0D, -0.6D, 0.35D);
/* 64 */           ClientRenderHooks.customRenderers[type.id].renderItem(CustomItemRenderType.BACK, null, gun, new Object[] { player.field_70170_p, player, Float.valueOf(partialTicks) });
/*    */         } 
/* 66 */         GlStateManager.func_179121_F();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBody(EntityPlayer player, ArmorType armorType, float scale) {
/* 74 */     if (armorType.hasModel()) {
/* 75 */       ModelCustomArmor armorModel = (ModelCustomArmor)armorType.bipedModel;
/* 76 */       GlStateManager.func_179094_E();
/* 77 */       if (player.func_70093_af()) {
/* 78 */         GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
/* 79 */         GlStateManager.func_179114_b(30.0F, 1.0F, 0.0F, 0.0F);
/*    */       } 
/* 81 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 82 */       GlStateManager.func_179091_B();
/* 83 */       int skinId = 0;
/* 84 */       String path = armorType.modelSkins[0].getSkin();
/* 85 */       (Minecraft.func_71410_x().func_175598_ae()).field_78724_e.func_110577_a(new ResourceLocation("modularwarfare", "skins/armor/" + path + ".png"));
/* 86 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 87 */       GlStateManager.func_179103_j(7425);
/* 88 */       armorModel.render("armorModel", (this.renderer.func_177087_b()).field_78115_e, 0.0625F, 1.0F);
/* 89 */       GlStateManager.func_179103_j(7424);
/* 90 */       GlStateManager.func_179121_F();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_177142_b() {
/* 96 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\model\layers\RenderLayerBody.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */