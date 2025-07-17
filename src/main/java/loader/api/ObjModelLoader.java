/*    */ package com.modularwarfare.loader.api;
/*    */ 
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import com.modularwarfare.loader.ObjModel;
/*    */ import com.modularwarfare.loader.ObjModelBuilder;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjModelLoader
/*    */ {
/*    */   public static ObjModel load(String resourceLocation) {
/* 22 */     return (new ObjModelBuilder(resourceLocation)).loadModel();
/*    */   }
/*    */   
/*    */   public static ObjModel load(ResourceLocation resourceLocation) {
/* 26 */     return (new ObjModelBuilder(resourceLocation)).loadModelFromRL();
/*    */   }
/*    */   
/*    */   public static ObjModel load(BaseType type, String resourceLocation) {
/* 30 */     return (new ObjModelBuilder(resourceLocation)).loadModelFromZIP(type);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\loader\api\ObjModelLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */