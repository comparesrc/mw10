/*     */ package com.modularwarfare.client.fpp.basic.renderers;
/*     */ 
/*     */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderType;
/*     */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderer;
/*     */ import com.modularwarfare.client.model.ModelAttachment;
/*     */ import com.modularwarfare.common.guns.AttachmentType;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ public class RenderAttachment
/*     */   extends CustomItemRenderer {
/*     */   public static float smoothing;
/*     */   public static float randomOffset;
/*     */   public static float randomRotateOffset;
/*  21 */   public static float adsSwitch = 0.0F;
/*     */   private static TextureManager renderEngine;
/*  23 */   private int direction = 0;
/*     */ 
/*     */   
/*     */   public void renderItem(CustomItemRenderType type, EnumHand hand, ItemStack item, Object... data) {
/*  27 */     if (!(item.func_77973_b() instanceof ItemAttachment)) {
/*     */       return;
/*     */     }
/*  30 */     AttachmentType AttachmentType = ((ItemAttachment)item.func_77973_b()).type;
/*  31 */     if (AttachmentType == null) {
/*     */       return;
/*     */     }
/*  34 */     ModelAttachment model = (ModelAttachment)AttachmentType.model;
/*  35 */     if (model == null) {
/*     */       return;
/*     */     }
/*  38 */     renderAttachment(type, item, AttachmentType, data);
/*     */   }
/*     */   
/*     */   private void renderAttachment(CustomItemRenderType renderType, ItemStack item, AttachmentType attachmentType, Object... data) {
/*     */     float crouchOffset, modelScale, rotateX, rotateY, rotateZ;
/*     */     Vector3f translateXYZ;
/*  44 */     ModelAttachment model = (ModelAttachment)attachmentType.model;
/*  45 */     EntityPlayerSP player = (Minecraft.func_71410_x()).field_71439_g;
/*     */     
/*  47 */     if (renderEngine == null) {
/*  48 */       renderEngine = (Minecraft.func_71410_x()).field_71446_o;
/*     */     }
/*  50 */     if (model == null) {
/*     */       return;
/*     */     }
/*  53 */     GL11.glPushMatrix();
/*     */     
/*  55 */     switch (renderType) {
/*     */       
/*     */       case ENTITY:
/*  58 */         GL11.glTranslatef(-0.45F, -0.05F, 0.0F);
/*     */         break;
/*     */ 
/*     */       
/*     */       case EQUIPPED:
/*  63 */         crouchOffset = player.func_70093_af() ? 0.2F : 0.0F;
/*  64 */         GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
/*  65 */         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/*  66 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*  67 */         GL11.glTranslatef(-0.15F, 0.15F, -0.025F);
/*  68 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case EQUIPPED_FIRST_PERSON:
/*  74 */         modelScale = model.config.extra.modelScale;
/*     */         
/*  76 */         rotateX = 0.0F;
/*  77 */         rotateY = 46.0F - 1.0F * adsSwitch;
/*  78 */         rotateZ = 1.0F + -1.0F * adsSwitch;
/*  79 */         translateXYZ = new Vector3f(-1.3000001F, 0.834F - -0.064F * adsSwitch, -1.05F - 0.35F * adsSwitch);
/*     */         
/*  81 */         GL11.glRotatef(rotateX, 1.0F, 0.0F, 0.0F);
/*  82 */         GL11.glRotatef(rotateY, 0.0F, 1.0F, 0.0F);
/*  83 */         GL11.glRotatef(rotateZ, 0.0F, 0.0F, 1.0F);
/*  84 */         GL11.glTranslatef(translateXYZ.x, translateXYZ.y, translateXYZ.z);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     GL11.glPushMatrix();
/*     */     
/*  94 */     if (item != null && item.func_77978_p() != null) {
/*  95 */       float f = 0.0625F;
/*  96 */       float f1 = model.config.extra.modelScale;
/*     */       
/*  98 */       int skinId = 0;
/*  99 */       String path = (skinId > 0) ? ("skins/" + attachmentType.modelSkins[skinId].getSkin()) : attachmentType.modelSkins[0].getSkin();
/* 100 */       bindTexture("attachments", path);
/* 101 */       GL11.glScalef(f1, f1, f1);
/* 102 */       model.renderAttachment(f);
/*     */     } 
/*     */     
/* 105 */     GL11.glPopMatrix();
/*     */     
/* 107 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\renderers\RenderAttachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */