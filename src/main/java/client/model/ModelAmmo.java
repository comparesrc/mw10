/*    */ package com.modularwarfare.client.model;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.fpp.basic.configs.AmmoRenderConfig;
/*    */ import com.modularwarfare.client.fpp.basic.models.objects.RenderVariables;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import com.modularwarfare.loader.MWModelBase;
/*    */ import com.modularwarfare.loader.api.ObjModelLoader;
/*    */ import com.modularwarfare.loader.api.model.AbstractObjModel;
/*    */ import java.util.HashMap;
/*    */ import org.lwjgl.util.vector.Vector3f;
/*    */ 
/*    */ public class ModelAmmo
/*    */   extends MWModelBase
/*    */ {
/*    */   public AmmoRenderConfig config;
/* 17 */   public Vector3f thirdPersonOffset = new Vector3f();
/*    */   
/* 19 */   public HashMap<Integer, RenderVariables> magCountOffset = new HashMap<>();
/*    */   
/*    */   public ModelAmmo(AmmoRenderConfig config, BaseType type) {
/* 22 */     this.config = config;
/* 23 */     if (this.config.modelFileName.endsWith(".obj")) {
/* 24 */       if (type.isInDirectory) {
/* 25 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type.contentPack + "/obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*    */       } else {
/* 27 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type, "obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*    */       } 
/*    */     } else {
/* 30 */       ModularWarfare.LOGGER.info("Internal error: " + this.config.modelFileName + " is not a valid format.");
/*    */     } 
/*    */   }
/*    */   
/*    */   public void renderAmmo(float f) {
/* 35 */     renderPart("ammoModel", f);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\model\ModelAmmo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */