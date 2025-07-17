/*     */ package com.modularwarfare.client.model;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.client.fpp.basic.configs.GunRenderConfig;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import com.modularwarfare.loader.MWModelBase;
/*     */ import com.modularwarfare.loader.api.ObjModelLoader;
/*     */ import com.modularwarfare.loader.api.model.AbstractObjModel;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ public class ModelGun
/*     */   extends MWModelBase
/*     */ {
/*     */   private static float lightmapLastX;
/*     */   private static float lightmapLastY;
/*     */   private static boolean optifineBreak = false;
/*     */   public GunRenderConfig config;
/*     */   public boolean switchIsOnSlide = false;
/*  22 */   public Vector3f switchRotationPoint = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */   
/*     */   public float switchSemiRot;
/*     */ 
/*     */   
/*     */   public float switchBurstRot;
/*     */ 
/*     */   
/*     */   public float switchAutoRot;
/*     */   
/*     */   public boolean scopeIsOnSlide = false;
/*     */   
/*  35 */   public int hammerDelay = 0;
/*     */ 
/*     */ 
/*     */   
/*  39 */   public float triggerRotation = 0.0F;
/*     */ 
/*     */ 
/*     */   
/*  43 */   public Vector3f triggerRotationPoint = new Vector3f();
/*  44 */   public float triggerDistance = 0.02F;
/*     */ 
/*     */ 
/*     */   
/*  48 */   public float leverRotation = 0.0F;
/*     */ 
/*     */ 
/*     */   
/*  52 */   public Vector3f leverRotationPoint = new Vector3f();
/*     */ 
/*     */ 
/*     */   
/*  56 */   public float cylinderRotation = 0.0F;
/*     */ 
/*     */ 
/*     */   
/*  60 */   public Vector3f cylinderRotationPoint = new Vector3f();
/*     */ 
/*     */ 
/*     */   
/*  64 */   public Vector3f hammerRotationPoint = new Vector3f();
/*  65 */   public float hammerAngle = 75.0F;
/*     */ 
/*     */   
/*     */   public boolean slideLockOnEmpty = true;
/*     */ 
/*     */   
/*     */   public ModelGun(GunRenderConfig config, BaseType type) {
/*  72 */     this.config = config;
/*  73 */     if (this.config.modelFileName.endsWith(".obj")) {
/*  74 */       if (type.isInDirectory) {
/*  75 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type.contentPack + "/obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*     */       } else {
/*  77 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type, "obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*     */       } 
/*     */     } else {
/*  80 */       ModularWarfare.LOGGER.info("Internal error: " + this.config.modelFileName + " is not a valid format.");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glowOn() {
/*  85 */     glowOn(15);
/*     */   }
/*     */   
/*     */   public static void glowOn(int glow) {
/*  89 */     GL11.glPushAttrib(64);
/*     */     try {
/*  91 */       lightmapLastX = OpenGlHelper.lastBrightnessX;
/*  92 */       lightmapLastY = OpenGlHelper.lastBrightnessY;
/*  93 */     } catch (NoSuchFieldError e) {
/*  94 */       optifineBreak = true;
/*     */     } 
/*  96 */     RenderHelper.func_74518_a();
/*     */     
/*  98 */     float glowRatioX = Math.min(glow / 15.0F * 240.0F + lightmapLastX, 240.0F);
/*  99 */     float glowRatioY = Math.min(glow / 15.0F * 240.0F + lightmapLastY, 240.0F);
/* 100 */     if (!optifineBreak) {
/* 101 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, glowRatioX, glowRatioY);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void glowOff() {
/* 106 */     RenderHelper.func_74519_b();
/* 107 */     if (!optifineBreak) {
/* 108 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, lightmapLastX, lightmapLastY);
/*     */     }
/* 110 */     GL11.glPopAttrib();
/*     */   }
/*     */   
/*     */   public boolean hasArms() {
/* 114 */     return (this.config.arms.leftArm.armPos != null || this.config.arms.rightArm.armPos != null);
/*     */   }
/*     */   
/*     */   public boolean isType(GunRenderConfig.Arms.EnumArm arm, GunRenderConfig.Arms.EnumAction action) {
/* 118 */     return (this.config.arms.actionArm == arm && this.config.arms.actionType == action);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\model\ModelGun.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */