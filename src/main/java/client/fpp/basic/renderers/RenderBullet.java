/*     */ package com.modularwarfare.client.fpp.basic.renderers;
/*     */ 
/*     */ import com.modularwarfare.common.entity.EntityBulletClient;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL13;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderBullet
/*     */   extends Render<EntityBulletClient>
/*     */ {
/*  25 */   public static final Factory FACTORY = new Factory();
/*     */   
/*  27 */   private static final ResourceLocation texture = new ResourceLocation("modularwarfare:textures/shoot.png");
/*     */ 
/*     */   
/*     */   protected RenderBullet(RenderManager renderManager) {
/*  31 */     super(renderManager);
/*  32 */     this.field_76989_e = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getEntityTexture(EntityBulletClient entity) {
/*  38 */     return null;
/*     */   }
/*     */   
/*     */   public void func_76979_b(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
/*  42 */     if (this.field_76990_c.field_78733_k != null) {
/*  43 */       doRenderBullet((EntityBulletClient)entityIn, x, y, z, yaw, partialTicks);
/*     */     }
/*     */   }
/*     */   
/*     */   private void doRenderBullet(EntityBulletClient bullet, double x, double y, double z, float yaw, float partialTicks) {
/*  48 */     GlStateManager.func_179094_E();
/*     */     
/*  50 */     bullet.renderLifeTime += 1.0F + partialTicks;
/*  51 */     float distance = bullet.func_70032_d((Entity)(Minecraft.func_71410_x()).field_71439_g);
/*  52 */     if (distance < 15.0F)
/*  53 */     { distance /= 15.0F; }
/*  54 */     else { distance = 1.0F; }
/*  55 */      if (bullet.renderLifeTime > 2.6F) {
/*     */       
/*  57 */       GL11.glPushMatrix();
/*  58 */       GL11.glTranslatef((float)x, (float)y, (float)z);
/*  59 */       GL11.glDisable(2884);
/*  60 */       GL11.glEnable(3042);
/*  61 */       GL11.glBlendFunc(770, 771);
/*  62 */       GL11.glDepthMask(false);
/*  63 */       GL11.glAlphaFunc(516, 0.1F);
/*  64 */       GL11.glRotatef(bullet.field_70126_B + (bullet.field_70177_z - bullet.field_70126_B) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
/*  65 */       GL11.glRotatef(bullet.field_70127_C + (bullet.field_70125_A - bullet.field_70127_C) * partialTicks, 0.0F, 0.0F, 1.0F);
/*  66 */       float scale = 0.004F;
/*  67 */       GL11.glScalef(scale, scale, scale);
/*  68 */       GL13.glActiveTexture(33988);
/*  69 */       (Minecraft.func_71410_x()).field_71446_o.func_110577_a(texture);
/*  70 */       GL11.glColor4f(3.0F, 3.0F, 3.0F, 1.0F * distance);
/*     */       
/*  72 */       Tessellator t = Tessellator.func_178181_a();
/*  73 */       BufferBuilder buffer = t.func_178180_c();
/*     */       
/*  75 */       float sizeX = 100.0F;
/*  76 */       float sizeY = 20.0F;
/*     */       
/*  78 */       buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*  79 */       buffer.func_181662_b(-sizeX, sizeY, 1.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/*  80 */       buffer.func_181662_b(sizeX, sizeY, 1.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/*  81 */       buffer.func_181662_b(sizeX, -sizeY, 1.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/*  82 */       buffer.func_181662_b(-sizeX, -sizeY, 1.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/*  83 */       t.func_78381_a();
/*     */       
/*  85 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  86 */       buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*  87 */       buffer.func_181662_b(-sizeX, sizeY, 1.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/*  88 */       buffer.func_181662_b(sizeX, sizeY, 1.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/*  89 */       buffer.func_181662_b(sizeX, -sizeY, 1.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/*  90 */       buffer.func_181662_b(-sizeX, -sizeY, 1.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/*  91 */       t.func_78381_a();
/*     */       
/*  93 */       GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*  94 */       GL11.glScalef(0.05F, 1.0F, 1.0F);
/*  95 */       sizeY = 40.0F;
/*     */       
/*  97 */       buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*  98 */       buffer.func_181662_b(-sizeX, sizeY, 1.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/*  99 */       buffer.func_181662_b(sizeX, sizeY, 1.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/* 100 */       buffer.func_181662_b(sizeX, -sizeY, 1.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/* 101 */       buffer.func_181662_b(-sizeX, -sizeY, 1.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/* 102 */       t.func_78381_a();
/*     */       
/* 104 */       GL11.glDepthMask(true);
/* 105 */       GL11.glDisable(3042);
/* 106 */       GL11.glAlphaFunc(516, 0.1F);
/* 107 */       GL11.glEnable(2884);
/* 108 */       GL11.glPopMatrix();
/*     */     } 
/* 110 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public static class Factory implements IRenderFactory {
/*     */     public Render createRenderFor(RenderManager manager) {
/* 115 */       return new RenderBullet(manager);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\renderers\RenderBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */