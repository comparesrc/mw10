/*    */ package com.modularwarfare.client.model;
/*    */ 
/*    */ import com.modularwarfare.common.guns.BulletType;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import com.modularwarfare.loader.MWModelBase;
/*    */ import com.modularwarfare.loader.api.ObjModelLoader;
/*    */ import com.modularwarfare.loader.api.model.AbstractObjModel;
/*    */ 
/*    */ public class ModelShell extends MWModelBase {
/*    */   public ModelShell(BulletType bulletType, boolean loadDefault) {
/* 11 */     if (bulletType.isInDirectory) {
/* 12 */       if (!loadDefault) {
/* 13 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(bulletType.contentPack + "/obj/shells/" + bulletType.shellModelFileName);
/*    */       } else {
/* 15 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(bulletType.contentPack + "/obj/shells/" + bulletType.defaultModel);
/*    */       }
/*    */     
/* 18 */     } else if (!loadDefault) {
/* 19 */       this.staticModel = (AbstractObjModel)ObjModelLoader.load((BaseType)bulletType, "obj/shells/" + bulletType.shellModelFileName);
/*    */     } else {
/* 21 */       this.staticModel = (AbstractObjModel)ObjModelLoader.load((BaseType)bulletType, "obj/shells/" + bulletType.defaultModel);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderShell(float f) {
/* 27 */     renderPart("shellModel", f);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\model\ModelShell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */