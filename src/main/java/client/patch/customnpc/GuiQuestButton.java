/*    */ package com.modularwarfare.client.patch.customnpc;
/*    */ 
/*    */ import com.modularwarfare.utility.RenderHelperMW;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class GuiQuestButton
/*    */   extends GuiButton
/*    */ {
/*    */   private final GuiContainer parentGui;
/*    */   
/*    */   public GuiQuestButton(int buttonId, GuiContainer parentGui, int x, int y, int width, int height, String buttonText) {
/* 19 */     super(buttonId, x, parentGui.getGuiTop() + y, width, height, buttonText);
/* 20 */     this.parentGui = parentGui;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
/* 25 */     boolean pressed = super.func_146116_c(mc, mouseX - this.parentGui.getGuiLeft(), mouseY);
/* 26 */     if (pressed) {
/*    */       try {
/* 28 */         Class<?> classz = Class.forName("noppes.npcs.client.gui.player.GuiQuestLog");
/* 29 */         mc.func_147108_a((GuiScreen)classz.newInstance());
/* 30 */       } catch (ClassNotFoundException e) {
/* 31 */         e.printStackTrace();
/* 32 */       } catch (InstantiationException e) {
/* 33 */         e.printStackTrace();
/* 34 */       } catch (IllegalAccessException e) {
/* 35 */         e.printStackTrace();
/*    */       } 
/*    */     }
/* 38 */     return pressed;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_191745_a(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
/* 43 */     if (this.field_146125_m) {
/* 44 */       int x = this.field_146128_h + this.parentGui.getGuiLeft();
/*    */       
/* 46 */       FontRenderer fontrenderer = mc.field_71466_p;
/* 47 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 48 */       this.field_146123_n = (mouseX >= x && mouseY >= this.field_146129_i && mouseX < x + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g);
/* 49 */       int k = func_146114_a(this.field_146123_n);
/* 50 */       GlStateManager.func_179147_l();
/* 51 */       GlStateManager.func_179120_a(770, 771, 1, 0);
/* 52 */       GlStateManager.func_179112_b(770, 771);
/*    */       
/* 54 */       GlStateManager.func_179094_E();
/* 55 */       GlStateManager.func_179109_b(0.0F, 0.0F, 200.0F);
/*    */       
/* 57 */       RenderHelperMW.renderImage(x, this.field_146129_i, new ResourceLocation("modularwarfare", "textures/gui/quest.png"), 19.0D, 19.0D);
/*    */       
/* 59 */       GlStateManager.func_179121_F();
/* 60 */       func_146119_b(mc, mouseX, mouseY);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\patch\customnpc\GuiQuestButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */