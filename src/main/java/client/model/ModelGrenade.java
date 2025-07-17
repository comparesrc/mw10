/*    */ package com.modularwarfare.client.model;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.fpp.basic.configs.GrenadeRenderConfig;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import com.modularwarfare.loader.MWModelBase;
/*    */ import com.modularwarfare.loader.api.ObjModelLoader;
/*    */ import com.modularwarfare.loader.api.model.AbstractObjModel;
/*    */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ public class ModelGrenade extends MWModelBase {
/*    */   public GrenadeRenderConfig config;
/*    */   
/*    */   public ModelGrenade(GrenadeRenderConfig config, BaseType type) {
/* 16 */     this.config = config;
/* 17 */     if (this.config.modelFileName.endsWith(".obj")) {
/* 18 */       if (type.isInDirectory) {
/* 19 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type.contentPack + "/obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*    */       } else {
/* 21 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type, "obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*    */       } 
/*    */     } else {
/* 24 */       ModularWarfare.LOGGER.info("Internal error: " + this.config.modelFileName + " is not a valid format.");
/*    */     } 
/*    */   }
/*    */   
/*    */   public void render(String modelPart, float scale, float modelScale) {
/* 29 */     GlStateManager.func_179094_E();
/*    */     
/* 31 */     ObjModelRenderer part = this.staticModel.getPart(modelPart);
/* 32 */     if (part != null && 
/* 33 */       part != null) {
/* 34 */       part.render(scale * modelScale);
/*    */     }
/*    */     
/* 37 */     GlStateManager.func_179121_F();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\model\ModelGrenade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */