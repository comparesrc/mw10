/*    */ package com.modularwarfare.client.model;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.fpp.basic.configs.BulletRenderConfig;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import com.modularwarfare.loader.MWModelBase;
/*    */ import com.modularwarfare.loader.api.ObjModelLoader;
/*    */ import com.modularwarfare.loader.api.model.AbstractObjModel;
/*    */ 
/*    */ public class ModelBullet extends MWModelBase {
/*    */   public BulletRenderConfig config;
/*    */   
/*    */   public ModelBullet(BulletRenderConfig config, BaseType type) {
/* 14 */     this.config = config;
/* 15 */     if (this.config.modelFileName.endsWith(".obj")) {
/* 16 */       if (type.isInDirectory) {
/* 17 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type.contentPack + "/obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*    */       } else {
/* 19 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type, "obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*    */       } 
/*    */     } else {
/* 22 */       ModularWarfare.LOGGER.info("Internal error: " + this.config.modelFileName + " is not a valid format.");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBullet(float f) {
/* 28 */     renderPart("bulletModel", f);
/*    */   }
/*    */   
/*    */   public void renderBullet(int num, float f) {
/* 32 */     for (int i = 1; i <= num; i++)
/* 33 */       renderPart("bulletModel_" + i, f); 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\model\ModelBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */