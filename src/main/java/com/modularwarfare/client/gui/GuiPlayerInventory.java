/*    */ package com.modularwarfare.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.recipebook.GuiRecipeBook;
/*    */ import net.minecraft.client.gui.recipebook.IRecipeShownListener;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.InventoryEffectRenderer;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.inventory.Container;
/*    */ 
/*    */ public class GuiPlayerInventory extends InventoryEffectRenderer implements IRecipeShownListener {
/*    */   public GuiPlayerInventory(Container inventorySlotsIn) {
/* 17 */     super(inventorySlotsIn);
/*    */   }
/*    */   
/*    */   public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
/* 21 */     GlStateManager.func_179142_g();
/* 22 */     GlStateManager.func_179094_E();
/* 23 */     GlStateManager.func_179109_b(posX, posY, 50.0F);
/* 24 */     GlStateManager.func_179152_a(-scale, scale, scale);
/* 25 */     GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
/* 26 */     float f = ent.field_70761_aq;
/* 27 */     float f2 = ent.field_70177_z;
/* 28 */     float f3 = ent.field_70125_A;
/* 29 */     float f4 = ent.field_70758_at;
/* 30 */     float f5 = ent.field_70759_as;
/* 31 */     RenderHelper.func_74519_b();
/* 32 */     ent.field_70761_aq = (float)Math.atan((mouseX / -360.0F)) * 360.0F;
/* 33 */     ent.field_70177_z = (float)Math.atan((mouseX / -360.0F)) * 360.0F;
/* 34 */     ent.field_70125_A = -((float)Math.atan((mouseY / 40.0F)));
/* 35 */     ent.field_70759_as = ent.field_70177_z;
/* 36 */     ent.field_70758_at = ent.field_70177_z;
/* 37 */     GlStateManager.func_179109_b(0.0F, 0.0F, 0.0F);
/* 38 */     RenderManager rendermanager = Minecraft.func_71410_x().func_175598_ae();
/* 39 */     rendermanager.func_178631_a(180.0F);
/* 40 */     rendermanager.func_178633_a(false);
/* 41 */     rendermanager.func_188391_a((Entity)ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
/* 42 */     rendermanager.func_178633_a(true);
/* 43 */     ent.field_70761_aq = f;
/* 44 */     ent.field_70177_z = f2;
/* 45 */     ent.field_70125_A = f3;
/* 46 */     ent.field_70758_at = f4;
/* 47 */     ent.field_70759_as = f5;
/* 48 */     GlStateManager.func_179121_F();
/* 49 */     RenderHelper.func_74518_a();
/* 50 */     GlStateManager.func_179101_C();
/* 51 */     GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
/* 52 */     GlStateManager.func_179090_x();
/* 53 */     GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {}
/*    */ 
/*    */   
/*    */   public void func_192043_J_() {}
/*    */   
/*    */   public GuiRecipeBook func_194310_f() {
/* 63 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\gui\GuiPlayerInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */