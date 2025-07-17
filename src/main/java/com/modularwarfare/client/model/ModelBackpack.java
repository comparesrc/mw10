/*    */ package com.modularwarfare.client.model;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.fpp.basic.configs.BackpackRenderConfig;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import com.modularwarfare.loader.MWModelBase;
/*    */ import com.modularwarfare.loader.api.ObjModelLoader;
/*    */ import com.modularwarfare.loader.api.model.AbstractObjModel;
/*    */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ public class ModelBackpack
/*    */   extends MWModelBase {
/*    */   public BackpackRenderConfig config;
/*    */   
/*    */   public ModelBackpack(BackpackRenderConfig config, BaseType type) {
/* 17 */     this.config = config;
/* 18 */     if (this.config.modelFileName.endsWith(".obj")) {
/* 19 */       if (type.isInDirectory) {
/* 20 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type.contentPack + "/obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*    */       } else {
/* 22 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type, "obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*    */       } 
/*    */     } else {
/* 25 */       ModularWarfare.LOGGER.info("Internal error: " + this.config.modelFileName + " is not a valid format.");
/*    */     } 
/*    */   }
/*    */   
/*    */   public void render(String modelPart, float scale, float modelScale) {
/* 30 */     GlStateManager.func_179094_E();
/*    */     
/* 32 */     ObjModelRenderer part = this.staticModel.getPart(modelPart);
/* 33 */     if (part != null && 
/* 34 */       part != null) {
/* 35 */       part.render(scale * modelScale);
/*    */     }
/*    */     
/* 38 */     GlStateManager.func_179121_F();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\model\ModelBackpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */