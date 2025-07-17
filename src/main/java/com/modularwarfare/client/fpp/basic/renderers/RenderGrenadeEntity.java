/*    */ package com.modularwarfare.client.fpp.basic.renderers;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.ClientRenderHooks;
/*    */ import com.modularwarfare.client.model.ModelGrenade;
/*    */ import com.modularwarfare.common.entity.grenades.EntityGrenade;
/*    */ import com.modularwarfare.common.grenades.ItemGrenade;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderGrenadeEntity
/*    */   extends Render<EntityGrenade>
/*    */ {
/* 21 */   public static final Factory FACTORY = new Factory();
/*    */   
/*    */   protected RenderGrenadeEntity(RenderManager renderManager) {
/* 24 */     super(renderManager);
/* 25 */     this.field_76989_e = 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getEntityTexture(EntityGrenade entity) {
/* 31 */     return null;
/*    */   }
/*    */   
/*    */   public void func_76979_b(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
/* 35 */     if (this.field_76990_c.field_78733_k != null) {
/* 36 */       doRenderGrenade((EntityGrenade)entityIn, x, y, z, yaw, partialTicks);
/*    */     }
/*    */   }
/*    */   
/*    */   private void doRenderGrenade(EntityGrenade entityIn, double x, double y, double z, float yaw, float partialTicks) {
/* 41 */     GlStateManager.func_179094_E();
/* 42 */     GlStateManager.func_179140_f();
/*    */     
/* 44 */     GlStateManager.func_179109_b((float)x + 0.15F, (float)y + 0.1F, (float)z);
/*    */     
/* 46 */     if (entityIn.field_70122_E) {
/* 47 */       GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
/*    */     } else {
/*    */       
/* 50 */       GlStateManager.func_179114_b(entityIn.field_70173_aa * 10.0F, 1.0F, 0.0F, 0.0F);
/* 51 */       GlStateManager.func_179114_b(entityIn.field_70173_aa * 8.0F, 0.0F, 1.0F, 0.0F);
/* 52 */       GlStateManager.func_179114_b(entityIn.field_70173_aa * 15.0F, 0.0F, 0.0F, 1.0F);
/*    */     } 
/*    */     
/* 55 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 56 */     float worldScale = 0.0625F;
/*    */     
/* 58 */     if (ModularWarfare.grenadeTypes.containsKey(entityIn.getGrenadeName())) {
/* 59 */       ItemGrenade itemGrenade = (ItemGrenade)ModularWarfare.grenadeTypes.get(entityIn.getGrenadeName());
/* 60 */       ModelGrenade grenade = (ModelGrenade)((ItemGrenade)ModularWarfare.grenadeTypes.get(entityIn.getGrenadeName())).type.model;
/* 61 */       ClientRenderHooks.customRenderers[1].bindTexture("grenades", itemGrenade.type.internalName);
/* 62 */       grenade.renderPart("grenadeModel", 0.0625F);
/*    */     } 
/*    */     
/* 65 */     GlStateManager.func_179145_e();
/* 66 */     GlStateManager.func_179121_F();
/*    */   }
/*    */   
/*    */   public static class Factory implements IRenderFactory {
/*    */     public Render createRenderFor(RenderManager manager) {
/* 71 */       return new RenderGrenadeEntity(manager);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\renderers\RenderGrenadeEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */