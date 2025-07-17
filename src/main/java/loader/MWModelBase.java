/*     */ package com.modularwarfare.loader;
/*     */ 
/*     */ import com.modularwarfare.api.IMWModel;
/*     */ import com.modularwarfare.loader.api.model.AbstractObjModel;
/*     */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MWModelBase
/*     */   extends ModelBase
/*     */   implements IMWModel
/*     */ {
/*     */   public AbstractObjModel staticModel;
/*     */   
/*     */   public MWModelBase() {}
/*     */   
/*     */   public MWModelBase(AbstractObjModel model) {
/*  23 */     this.staticModel = model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyModelAngles(ObjModelRenderer source, ObjModelRenderer dest) {
/*  31 */     dest.rotateAngleX = source.rotateAngleX;
/*  32 */     dest.rotateAngleY = source.rotateAngleY;
/*  33 */     dest.rotateAngleZ = source.rotateAngleZ;
/*  34 */     dest.rotationPointX = source.rotationPointX;
/*  35 */     dest.rotationPointY = source.rotationPointY;
/*  36 */     dest.rotationPointZ = source.rotationPointZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyModelAngles(ObjModelRenderer source, ModelRenderer dest) {
/*  44 */     dest.field_78795_f = source.rotateAngleX;
/*  45 */     dest.field_78796_g = source.rotateAngleY;
/*  46 */     dest.field_78808_h = source.rotateAngleZ;
/*  47 */     dest.field_78800_c = source.rotationPointX;
/*  48 */     dest.field_78797_d = source.rotationPointY;
/*  49 */     dest.field_78798_e = source.rotationPointZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyModelAngles(ModelRenderer source, ObjModelRenderer dest) {
/*  57 */     dest.rotateAngleX = source.field_78795_f;
/*  58 */     dest.rotateAngleY = source.field_78796_g;
/*  59 */     dest.rotateAngleZ = source.field_78808_h;
/*  60 */     dest.rotationPointX = source.field_78800_c;
/*  61 */     dest.rotationPointY = source.field_78797_d;
/*  62 */     dest.rotationPointZ = source.field_78798_e;
/*     */   }
/*     */   
/*     */   public AbstractObjModel getStaticModel() {
/*  66 */     return this.staticModel;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderPart(String part, float f) {
/*  72 */     if (this.staticModel != null && 
/*  73 */       this.staticModel.getPart(part) != null) {
/*  74 */       render(this.staticModel.getPart(part), f);
/*     */       
/*  76 */       ObjModelRenderer glowPart = this.staticModel.getPart(part + "_glow");
/*  77 */       if (glowPart != null) {
/*  78 */         render(glowPart, f);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void render(ObjModelRenderer model, float f) {
/*  86 */     GL11.glPushMatrix();
/*     */     
/*  88 */     if (model != null) {
/*  89 */       model.render(f);
/*     */     }
/*  91 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderAll(float f) {
/*  96 */     GL11.glPushMatrix();
/*  97 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  98 */     if (this.staticModel != null)
/*  99 */       this.staticModel.renderAll(f); 
/* 100 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\loader\MWModelBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */