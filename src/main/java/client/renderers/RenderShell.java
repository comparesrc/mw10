/*    */ package com.modularwarfare.client.renderers;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.ClientRenderHooks;
/*    */ import com.modularwarfare.client.model.ModelShell;
/*    */ import com.modularwarfare.common.entity.decals.EntityShell;
/*    */ import com.modularwarfare.common.guns.GunType;
/*    */ import com.modularwarfare.common.guns.ItemBullet;
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*    */ 
/*    */ 
/*    */ public class RenderShell
/*    */   extends Render<EntityShell>
/*    */ {
/* 22 */   public static final Factory FACTORY = new Factory();
/*    */   
/*    */   protected RenderShell(RenderManager renderManager) {
/* 25 */     super(renderManager);
/* 26 */     this.field_76989_e = 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getEntityTexture(EntityShell entity) {
/* 32 */     return new ResourceLocation("modularwarfare", "textures/skins/shells/" + entity.getBulletName() + ".png");
/*    */   }
/*    */   
/*    */   public void func_76979_b(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
/* 36 */     if (this.field_76990_c.field_78733_k != null) {
/* 37 */       doRenderShell((EntityShell)entityIn, x, y, z, yaw, partialTicks);
/*    */     }
/*    */   }
/*    */   
/*    */   private void doRenderShell(EntityShell entityIn, double x, double y, double z, float yaw, float partialTicks) {
/* 42 */     GlStateManager.func_179094_E();
/* 43 */     GlStateManager.func_179140_f();
/*    */ 
/*    */     
/* 46 */     GlStateManager.func_179137_b((float)x, (float)y + 0.02D, (float)z);
/*    */     
/* 48 */     GlStateManager.func_179114_b(entityIn.field_70126_B + (entityIn.field_70177_z - entityIn.field_70126_B) * partialTicks, 0.0F, 1.0F, 0.0F);
/*    */     
/* 50 */     GlStateManager.func_179114_b(-entityIn.field_70125_A, 1.0F, 0.0F, 0.0F);
/*    */     
/* 52 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 53 */     GlStateManager.func_179152_a(0.0625F, 0.0625F, 0.0625F);
/*    */ 
/*    */     
/* 56 */     if (ModularWarfare.bulletTypes.containsKey(entityIn.getBulletName())) {
/* 57 */       ItemBullet itemBullet = (ItemBullet)ModularWarfare.bulletTypes.get(entityIn.getBulletName());
/* 58 */       ModelShell shell = (ModelShell)((ItemBullet)ModularWarfare.bulletTypes.get(entityIn.getBulletName())).type.shell;
/* 59 */       boolean flag = true;
/* 60 */       if (itemBullet.type.sameTextureAsGun) {
/* 61 */         GunType gunType = ((ItemGun)ModularWarfare.gunTypes.get(entityIn.getGunName())).type;
/* 62 */         if (gunType.modelSkins != null && gunType.modelSkins.length > entityIn.getGunSkinID()) {
/* 63 */           flag = false;
/* 64 */           ClientRenderHooks.customRenderers[1].bindTexture("gun", gunType.modelSkins[entityIn.getGunSkinID()].getSkin());
/*    */         } 
/*    */       } 
/* 67 */       if (flag) {
/* 68 */         if (itemBullet.type.shellModelFileName.equals(itemBullet.type.defaultModel)) {
/* 69 */           ClientRenderHooks.customRenderers[1].bindTexture("bullets", "default");
/*    */         } else {
/* 71 */           String path = entityIn.getBulletName();
/* 72 */           if (itemBullet.type.modelSkins != null && itemBullet.type.modelSkins.length > 0) {
/* 73 */             path = itemBullet.type.modelSkins[0].getSkin();
/*    */           }
/* 75 */           ClientRenderHooks.customRenderers[1].bindTexture("bullets", path);
/*    */         } 
/*    */       }
/* 78 */       shell.renderShell(1.0F);
/*    */     } 
/*    */     
/* 81 */     GlStateManager.func_179145_e();
/* 82 */     GlStateManager.func_179121_F();
/*    */   }
/*    */   
/*    */   public static class Factory implements IRenderFactory {
/*    */     public Render createRenderFor(RenderManager manager) {
/* 87 */       return new RenderShell(manager);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\renderers\RenderShell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */