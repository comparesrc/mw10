/*    */ package com.modularwarfare.loader;
/*    */ 
/*    */ import com.modularwarfare.loader.api.model.AbstractObjModel;
/*    */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MWModelBipedBase
/*    */   extends ModelBiped
/*    */ {
/*    */   public AbstractObjModel staticModel;
/*    */   
/*    */   public MWModelBipedBase() {}
/*    */   
/*    */   public MWModelBipedBase(AbstractObjModel model) {
/* 22 */     this.staticModel = model;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void copyModelAngles(ObjModelRenderer source, ObjModelRenderer dest) {
/* 30 */     dest.rotateAngleX = source.rotateAngleX;
/* 31 */     dest.rotateAngleY = source.rotateAngleY;
/* 32 */     dest.rotateAngleZ = source.rotateAngleZ;
/* 33 */     dest.rotationPointX = source.rotationPointX;
/* 34 */     dest.rotationPointY = source.rotationPointY;
/* 35 */     dest.rotationPointZ = source.rotationPointZ;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void copyModelAngles(ObjModelRenderer source, ModelRenderer dest) {
/* 43 */     dest.field_78795_f = source.rotateAngleX;
/* 44 */     dest.field_78796_g = source.rotateAngleY;
/* 45 */     dest.field_78808_h = source.rotateAngleZ;
/* 46 */     dest.field_78800_c = source.rotationPointX;
/* 47 */     dest.field_78797_d = source.rotationPointY;
/* 48 */     dest.field_78798_e = source.rotationPointZ;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void copyModelAngles(ModelRenderer source, ObjModelRenderer dest) {
/* 56 */     dest.rotateAngleX = source.field_78795_f;
/* 57 */     dest.rotateAngleY = source.field_78796_g;
/* 58 */     dest.rotateAngleZ = source.field_78808_h;
/* 59 */     dest.rotationPointX = source.field_78800_c;
/* 60 */     dest.rotationPointY = source.field_78797_d;
/* 61 */     dest.rotationPointZ = source.field_78798_e;
/*    */   }
/*    */   
/*    */   public AbstractObjModel getStaticModel() {
/* 65 */     return this.staticModel;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void render(ObjModelRenderer model, float f) {
/* 70 */     GL11.glPushMatrix();
/* 71 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*    */     
/* 73 */     if (model != null) {
/* 74 */       model.render(f);
/*    */     }
/* 76 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void renderAll(float f) {
/* 81 */     GL11.glPushMatrix();
/* 82 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 83 */     if (this.staticModel != null)
/* 84 */       this.staticModel.renderAll(f); 
/* 85 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\loader\MWModelBipedBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */