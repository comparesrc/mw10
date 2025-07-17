/*     */ package com.modularwarfare.client.fpp.basic.renderers;
/*     */ 
/*     */ import com.modularwarfare.api.GunBobbingEvent;
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderType;
/*     */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderer;
/*     */ import com.modularwarfare.client.model.ModelCustomArmor;
/*     */ import com.modularwarfare.client.model.ModelGrenade;
/*     */ import com.modularwarfare.common.armor.ItemMWArmor;
/*     */ import com.modularwarfare.common.grenades.GrenadeType;
/*     */ import com.modularwarfare.common.grenades.ItemGrenade;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.Timer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.relauncher.ReflectionHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ public class RenderGrenade
/*     */   extends CustomItemRenderer {
/*     */   public static float smoothing;
/*     */   public static float randomOffset;
/*     */   public static float randomRotateOffset;
/*  37 */   public static float adsSwitch = 0.0F;
/*     */   private static TextureManager renderEngine;
/*  39 */   private int direction = 0;
/*     */   
/*     */   private Timer timer;
/*     */ 
/*     */   
/*     */   public void renderItem(CustomItemRenderType type, EnumHand hand, ItemStack item, Object... data) {
/*  45 */     if (!(item.func_77973_b() instanceof ItemGrenade)) {
/*     */       return;
/*     */     }
/*  48 */     GrenadeType grenadeType = ((ItemGrenade)item.func_77973_b()).type;
/*  49 */     if (grenadeType == null) {
/*     */       return;
/*     */     }
/*  52 */     ModelGrenade model = (ModelGrenade)grenadeType.model;
/*  53 */     if (model == null) {
/*     */       return;
/*     */     }
/*  56 */     renderGrenade(type, item, grenadeType, data); } private void renderGrenade(CustomItemRenderType renderType, ItemStack item, GrenadeType grenadeType, Object... data) { float crouchOffset, modelScale; Vector3f vector3f; float worldScale, partialTicks, bobModifier, yawReducer; GunBobbingEvent event;
/*     */     EntityPlayer entityplayer;
/*     */     float f1, f2, f3, f4;
/*     */     Minecraft mc;
/*     */     Render<AbstractClientPlayer> render;
/*     */     RenderPlayer renderplayer;
/*  62 */     ModelGrenade model = (ModelGrenade)grenadeType.model;
/*  63 */     EntityPlayerSP player = (Minecraft.func_71410_x()).field_71439_g;
/*     */     
/*  65 */     if (renderEngine == null) {
/*  66 */       renderEngine = (Minecraft.func_71410_x()).field_71446_o;
/*     */     }
/*  68 */     if (model == null) {
/*     */       return;
/*     */     }
/*  71 */     GL11.glPushMatrix();
/*     */     
/*  73 */     switch (renderType) {
/*     */       
/*     */       case ENTITY:
/*  76 */         GL11.glTranslatef(-0.45F, -0.05F, 0.0F);
/*     */         break;
/*     */ 
/*     */       
/*     */       case EQUIPPED:
/*  81 */         crouchOffset = player.func_70093_af() ? 0.2F : 0.0F;
/*  82 */         GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
/*  83 */         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/*  84 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*  85 */         GL11.glTranslatef(-0.55F, 0.15F, 0.0F);
/*  86 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/*  87 */         GL11.glTranslatef(model.config.extra.thirdPersonOffset.x + crouchOffset, model.config.extra.thirdPersonOffset.y, model.config.extra.thirdPersonOffset.z);
/*     */         break;
/*     */ 
/*     */       
/*     */       case EQUIPPED_FIRST_PERSON:
/*  92 */         modelScale = model.config.extra.modelScale;
/*  93 */         vector3f = model.config.extra.translateAll;
/*     */         
/*  95 */         worldScale = 0.0625F;
/*     */         
/*  97 */         if (this.timer == null) {
/*  98 */           this.timer = (Timer)ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.func_71410_x(), "timer", "field_71428_T");
/*     */         }
/* 100 */         partialTicks = this.timer.field_194147_b;
/*     */         
/* 102 */         RenderParameters.VALSPRINT = (float)(Math.cos((RenderParameters.SMOOTH_SWING / 5.0F)) * 5.0D);
/* 103 */         RenderParameters.VAL = (float)(Math.sin((RenderParameters.SMOOTH_SWING / 100.0F)) * 8.0D);
/* 104 */         RenderParameters.VAL2 = (float)(Math.sin((RenderParameters.SMOOTH_SWING / 80.0F)) * 8.0D);
/* 105 */         RenderParameters.VALROT = (float)(Math.sin((RenderParameters.SMOOTH_SWING / 90.0F)) * 1.2000000476837158D);
/*     */         
/* 107 */         GL11.glRotatef(46.0F - RenderParameters.VALROT, 0.0F, 1.0F, 0.0F);
/*     */         
/* 109 */         GL11.glTranslatef(-1.88F, 1.1F, -1.05F);
/* 110 */         GL11.glTranslatef(0.0F, RenderParameters.VAL / 500.0F, RenderParameters.VAL2 / 500.0F);
/*     */ 
/*     */ 
/*     */         
/* 114 */         bobModifier = 0.4F;
/* 115 */         yawReducer = 1.0F;
/* 116 */         if (ClientRenderHooks.isAimingScope) {
/* 117 */           bobModifier *= 0.5F;
/* 118 */           yawReducer = 0.5F;
/*     */         } 
/* 120 */         event = new GunBobbingEvent(bobModifier);
/* 121 */         MinecraftForge.EVENT_BUS.post((Event)event);
/* 122 */         bobModifier = event.bobbing;
/* 123 */         entityplayer = (EntityPlayer)Minecraft.func_71410_x().func_175606_aa();
/* 124 */         f1 = (entityplayer.field_70140_Q - entityplayer.field_70141_P) * bobModifier;
/* 125 */         f2 = -(entityplayer.field_70140_Q + f1 * partialTicks) * bobModifier;
/* 126 */         f3 = (entityplayer.field_71107_bF + (entityplayer.field_71109_bG - entityplayer.field_71107_bF) * partialTicks) * bobModifier * yawReducer;
/* 127 */         f4 = (entityplayer.field_70727_aS + (entityplayer.field_70726_aT - entityplayer.field_70727_aS) * partialTicks) * bobModifier;
/* 128 */         GlStateManager.func_179109_b(MathHelper.func_76126_a(f2 * 3.1415927F) * f3 * 0.5F, -Math.abs(MathHelper.func_76134_b(f2 * 3.1415927F) * f3), 0.0F);
/* 129 */         GlStateManager.func_179114_b(MathHelper.func_76126_a(f2 * 3.1415927F) * f3 * 3.0F, 0.0F, 0.0F, 1.0F);
/* 130 */         GlStateManager.func_179114_b(Math.abs(MathHelper.func_76134_b(f2 * 3.1415927F - 0.2F) * f3) * 5.0F, 1.0F, 0.0F, 0.0F);
/* 131 */         GlStateManager.func_179114_b(f4, 1.0F, 0.0F, 0.0F);
/*     */ 
/*     */         
/* 134 */         mc = Minecraft.func_71410_x();
/* 135 */         mc.func_110434_K().func_110577_a((Minecraft.func_71410_x()).field_71439_g.func_110306_p());
/* 136 */         render = Minecraft.func_71410_x().func_175598_ae().func_78713_a((Entity)(Minecraft.func_71410_x()).field_71439_g);
/* 137 */         renderplayer = (RenderPlayer)render;
/*     */ 
/*     */         
/* 140 */         GL11.glPushMatrix();
/* 141 */         GL11.glScalef(model.config.arms.rightArm.armScale.x, model.config.arms.rightArm.armScale.y, model.config.arms.rightArm.armScale.z);
/*     */         
/* 143 */         GL11.glTranslatef(model.config.arms.rightArm.armPos.x, model.config.arms.rightArm.armPos.y, model.config.arms.rightArm.armPos.z);
/*     */         
/* 145 */         GL11.glRotatef(model.config.arms.rightArm.armRot.x, 1.0F, 0.0F, 0.0F);
/* 146 */         GL11.glRotatef(model.config.arms.rightArm.armRot.y, 0.0F, 1.0F, 0.0F);
/* 147 */         GL11.glRotatef(model.config.arms.rightArm.armRot.z, 0.0F, 0.0F, 1.0F);
/*     */         
/* 149 */         renderplayer.func_177087_b().func_78087_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)player);
/* 150 */         (renderplayer.func_177087_b()).field_178723_h.field_82906_o = 0.0F;
/* 151 */         renderplayer.func_177138_b((AbstractClientPlayer)(Minecraft.func_71410_x()).field_71439_g);
/* 152 */         renderRightSleeve((EntityPlayer)player, (ModelBiped)renderplayer.func_177087_b());
/*     */         
/* 154 */         GL11.glPopMatrix();
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     GL11.glPushMatrix();
/*     */ 
/*     */     
/* 165 */     GL11.glTranslatef(0.7F, -0.06F, 0.0F);
/*     */     
/* 167 */     float f = 0.0625F;
/* 168 */     float f5 = model.config.extra.modelScale;
/* 169 */     int skinId = 0;
/* 170 */     String path = (skinId > 0) ? ("skins/" + grenadeType.modelSkins[skinId].getSkin()) : grenadeType.modelSkins[0].getSkin();
/* 171 */     bindTexture("grenades", path);
/* 172 */     GL11.glScalef(f5, f5, f5);
/* 173 */     model.renderPart("grenadeModel", f);
/* 174 */     model.renderPart("pinModel", f);
/*     */     
/* 176 */     GL11.glPopMatrix();
/*     */     
/* 178 */     GL11.glPopMatrix(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderRightSleeve(EntityPlayer player, ModelBiped modelplayer) {
/* 183 */     if (player.field_71071_by.func_70440_f(2) != null) {
/* 184 */       ItemStack armorStack = player.field_71071_by.func_70440_f(2);
/* 185 */       if (armorStack.func_77973_b() instanceof ItemMWArmor) {
/* 186 */         ModelCustomArmor modelArmor = (ModelCustomArmor)((ItemMWArmor)armorStack.func_77973_b()).type.bipedModel;
/* 187 */         int skinId = 0;
/* 188 */         String path = (skinId > 0) ? ((ItemMWArmor)armorStack.func_77973_b()).type.modelSkins[skinId].getSkin() : ((ItemMWArmor)armorStack.func_77973_b()).type.modelSkins[0].getSkin();
/* 189 */         bindTexture("armor", path);
/* 190 */         GL11.glPushMatrix();
/*     */         
/* 192 */         float modelScale = modelArmor.config.extra.modelScale;
/* 193 */         GL11.glScalef(modelScale, modelScale, modelScale);
/* 194 */         modelArmor.showChest(true);
/*     */         
/* 196 */         modelArmor.renderRightArm((AbstractClientPlayer)player, modelplayer);
/*     */         
/* 198 */         GL11.glPopMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\renderers\RenderGrenade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */