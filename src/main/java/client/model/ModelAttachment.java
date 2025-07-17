/*    */ package com.modularwarfare.client.model;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.fpp.basic.configs.AttachmentRenderConfig;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import com.modularwarfare.loader.MWModelBase;
/*    */ import com.modularwarfare.loader.api.ObjModelLoader;
/*    */ import com.modularwarfare.loader.api.model.AbstractObjModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelAttachment
/*    */   extends MWModelBase
/*    */ {
/*    */   public AttachmentRenderConfig config;
/* 16 */   public float renderOffset = 0.0F;
/*    */   
/*    */   public ModelAttachment(AttachmentRenderConfig config, BaseType type) {
/* 19 */     this.config = config;
/* 20 */     if (this.config.modelFileName.endsWith(".obj")) {
/* 21 */       if (type.isInDirectory) {
/* 22 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type.contentPack + "/obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*    */       } else {
/* 24 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type, "obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*    */       } 
/*    */     } else {
/* 27 */       ModularWarfare.LOGGER.info("Internal error: " + this.config.modelFileName + " is not a valid format.");
/*    */     } 
/*    */   }
/*    */   
/*    */   public void renderAttachment(float f) {
/* 32 */     renderPart("attachmentModel", f);
/*    */   }
/*    */   
/*    */   public void renderScope(float f) {
/* 36 */     renderPart("scopeModel", f);
/*    */   }
/*    */   
/*    */   public void renderOverlay(float f) {
/* 40 */     renderPart("overlayModel", f);
/*    */   }
/*    */   
/*    */   public void renderOverlaySolid(float f) {
/* 44 */     renderPart("overlaySolidModel", f);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\model\ModelAttachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */