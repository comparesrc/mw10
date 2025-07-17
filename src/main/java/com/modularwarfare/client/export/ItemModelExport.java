/*    */ package com.modularwarfare.client.export;
/*    */ 
/*    */ public class ItemModelExport
/*    */ {
/*  5 */   public String parent = "item/generated";
/*  6 */   public TextureLayers textures = new TextureLayers();
/*  7 */   public Display display = new Display();
/*    */   
/*    */   public void setBaseLayer(String path) {
/* 10 */     this.textures.layer0 += path;
/*    */   }
/*    */   
/*    */   public static class TextureLayers {
/* 14 */     public String layer0 = "modularwarfare:items/";
/*    */   }
/*    */   
/*    */   public static class Display
/*    */   {
/* 19 */     public DisplayType thirdperson_lefthand = new DisplayType();
/* 20 */     public DisplayType thirdperson_righthand = new DisplayType();
/*    */     
/*    */     public static class DisplayType {
/* 23 */       public float[] scale = new float[] { 0.0F, 0.0F, 0.0F };
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\export\ItemModelExport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */