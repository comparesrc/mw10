/*    */ package com.modularwarfare.client.renderers;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.ClientRenderHooks;
/*    */ import com.modularwarfare.client.model.ModelBullet;
/*    */ import com.modularwarfare.common.entity.EntityBullet;
/*    */ import com.modularwarfare.common.guns.ItemBullet;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*    */ 
/*    */ 
/*    */ public class RenderProjectile
/*    */   extends Render<EntityBullet>
/*    */ {
/* 20 */   public static final Factory FACTORY = new Factory();
/*    */   
/*    */   protected RenderProjectile(RenderManager renderManager) {
/* 23 */     super(renderManager);
/* 24 */     this.field_76989_e = 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getEntityTexture(EntityBullet entity) {
/* 30 */     return new ResourceLocation("modularwarfare", "textures/skins/bullets/" + entity.getBulletName() + ".png");
/*    */   }
/*    */   
/*    */   public void func_76979_b(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
/* 34 */     if (this.field_76990_c.field_78733_k != null) {
/* 35 */       doRenderProjectile((EntityBullet)entityIn, x, y, z, yaw, partialTicks);
/*    */     }
/*    */   }
/*    */   
/*    */   private void doRenderProjectile(EntityBullet entityIn, double x, double y, double z, float yaw, float partialTicks) {
/* 40 */     GlStateManager.func_179094_E();
/* 41 */     GlStateManager.func_179109_b((float)x, (float)y, (float)z);
/* 42 */     GlStateManager.func_179114_b(entityIn.field_70126_B + (entityIn.field_70177_z - entityIn.field_70126_B) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
/* 43 */     GlStateManager.func_179114_b(entityIn.field_70127_C + (entityIn.field_70125_A - entityIn.field_70127_C) * partialTicks, 0.0F, 0.0F, 1.0F);
/*    */     
/* 45 */     float worldScale = 0.0625F;
/*    */     
/* 47 */     if (ModularWarfare.bulletTypes.containsKey(entityIn.getBulletName())) {
/* 48 */       ItemBullet itemBullet = (ItemBullet)ModularWarfare.bulletTypes.get(entityIn.getBulletName());
/* 49 */       ModelBullet bullet = (ModelBullet)itemBullet.type.model;
/* 50 */       ClientRenderHooks.customRenderers[1].bindTexture("bullets", entityIn.getBulletName());
/* 51 */       bullet.renderBullet(0.0625F);
/*    */     } 
/*    */     
/* 54 */     GlStateManager.func_179121_F();
/*    */   }
/*    */   
/*    */   public static class Factory implements IRenderFactory {
/*    */     public Render createRenderFor(RenderManager manager) {
/* 59 */       return new RenderProjectile(manager);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\renderers\RenderProjectile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */