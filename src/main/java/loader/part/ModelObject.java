/*    */ package com.modularwarfare.loader.part;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class ModelObject
/*    */ {
/*    */   public String name;
/* 12 */   public ArrayList<Face> faces = new ArrayList<>();
/*    */   public int glDrawingMode;
/*    */   
/*    */   public ModelObject() {
/* 16 */     this("");
/*    */   }
/*    */   
/*    */   public ModelObject(String name) {
/* 20 */     this(name, -1);
/*    */   }
/*    */   
/*    */   public ModelObject(String name, int glDrawingMode) {
/* 24 */     this.name = name;
/* 25 */     this.glDrawingMode = glDrawingMode;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void render(BufferBuilder renderer, float scale) {
/* 30 */     if (this.faces.size() > 0) {
/* 31 */       for (Face face : this.faces) {
/* 32 */         face.render(this.glDrawingMode, renderer, scale);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int renderByVAO(List<Float> renderer, float scale) {
/* 39 */     int flag = 0;
/* 40 */     if (this.faces.size() > 0) {
/* 41 */       for (Face face : this.faces) {
/* 42 */         flag = face.renderByVAO(this.glDrawingMode, renderer, scale);
/*    */       }
/*    */     }
/* 45 */     return flag;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\loader\part\ModelObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */