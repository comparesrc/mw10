/*     */ package com.modularwarfare.client.fpp.basic.renderers;
/*     */ 
/*     */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderType;
/*     */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderer;
/*     */ import com.modularwarfare.client.model.ModelAmmo;
/*     */ import com.modularwarfare.common.guns.AmmoType;
/*     */ import com.modularwarfare.common.guns.ItemAmmo;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ public class RenderAmmo
/*     */   extends CustomItemRenderer {
/*     */   public static float smoothing;
/*     */   public static float randomOffset;
/*     */   public static float randomRotateOffset;
/*  21 */   public static float adsSwitch = 0.0F;
/*     */   private static TextureManager renderEngine;
/*  23 */   public float modelScale = 1.0F;
/*  24 */   private int direction = 0;
/*     */ 
/*     */   
/*     */   public void renderItem(CustomItemRenderType type, EnumHand hand, ItemStack item, Object... data) {
/*  28 */     if (!(item.func_77973_b() instanceof ItemAmmo)) {
/*     */       return;
/*     */     }
/*  31 */     AmmoType ammoType = ((ItemAmmo)item.func_77973_b()).type;
/*  32 */     if (ammoType == null) {
/*     */       return;
/*     */     }
/*  35 */     ModelAmmo model = (ModelAmmo)ammoType.model;
/*  36 */     if (model == null) {
/*     */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderAmmo(CustomItemRenderType renderType, ItemStack item, AmmoType ammoType, Object... data) {
/*     */     float crouchOffset, modelScale, rotateX, rotateY, rotateZ;
/*     */     Vector3f translateXYZ;
/*  45 */     ModelAmmo model = (ModelAmmo)ammoType.model;
/*  46 */     EntityPlayerSP player = (Minecraft.func_71410_x()).field_71439_g;
/*     */     
/*  48 */     if (renderEngine == null) {
/*  49 */       renderEngine = (Minecraft.func_71410_x()).field_71446_o;
/*     */     }
/*  51 */     if (model == null) {
/*     */       return;
/*     */     }
/*  54 */     GL11.glPushMatrix();
/*     */     
/*  56 */     switch (renderType) {
/*     */       
/*     */       case ENTITY:
/*  59 */         GL11.glTranslatef(-0.45F, -0.05F, 0.0F);
/*     */         break;
/*     */ 
/*     */       
/*     */       case EQUIPPED:
/*  64 */         crouchOffset = player.func_70093_af() ? 0.2F : 0.0F;
/*  65 */         GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
/*  66 */         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/*  67 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*  68 */         GL11.glTranslatef(-0.15F, 0.15F, -0.025F);
/*  69 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/*  70 */         GL11.glTranslatef(model.thirdPersonOffset.x + crouchOffset, model.thirdPersonOffset.y, model.thirdPersonOffset.z);
/*     */         break;
/*     */ 
/*     */       
/*     */       case EQUIPPED_FIRST_PERSON:
/*  75 */         modelScale = this.modelScale;
/*     */         
/*  77 */         rotateX = 0.0F;
/*  78 */         rotateY = 46.0F - 1.0F * adsSwitch;
/*  79 */         rotateZ = 1.0F + -1.0F * adsSwitch;
/*  80 */         translateXYZ = new Vector3f(-1.3000001F, 0.834F - -0.064F * adsSwitch, -1.05F - 0.35F * adsSwitch);
/*     */         
/*  82 */         GL11.glRotatef(rotateX, 1.0F, 0.0F, 0.0F);
/*  83 */         GL11.glRotatef(rotateY, 0.0F, 1.0F, 0.0F);
/*  84 */         GL11.glRotatef(rotateZ, 0.0F, 0.0F, 1.0F);
/*  85 */         GL11.glTranslatef(translateXYZ.x, translateXYZ.y, translateXYZ.z);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     GL11.glPushMatrix();
/*     */     
/*  95 */     float f = 0.0625F;
/*  96 */     float f1 = this.modelScale;
/*  97 */     if (item.func_77942_o()) {
/*  98 */       int skinId = item.func_77978_p().func_74762_e("skinId");
/*  99 */       String path = (skinId > 0) ? ("skins/" + ammoType.modelSkins[skinId].getSkin()) : ammoType.modelSkins[0].getSkin();
/* 100 */       bindTexture("ammo", path);
/* 101 */       GL11.glScalef(f1, f1, f1);
/* 102 */       model.renderAmmo(f);
/*     */     } 
/*     */     
/* 105 */     GL11.glPopMatrix();
/*     */     
/* 107 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\renderers\RenderAmmo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */