/*    */ package com.modularwarfare.client.gui.api;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ 
/*    */ public class GuiMWContainer
/*    */ {
/* 13 */   public List<GuiButton> buttons = new ArrayList<>();
/*    */   public int containerID;
/*    */   public int posX;
/*    */   public int posY;
/*    */   public int width;
/*    */   public int height;
/*    */   public Minecraft mc;
/*    */   public GuiScreen parentGUI;
/*    */   protected boolean hovered = false;
/*    */   
/*    */   public GuiMWContainer(int givenID, int givenPosX, int givenPosY, int givenWidth, int givenHeight, GuiScreen givenParentGUI) {
/* 24 */     this.containerID = givenID;
/*    */     
/* 26 */     this.posX = givenPosX;
/* 27 */     this.posY = givenPosY;
/* 28 */     this.width = givenWidth;
/* 29 */     this.height = givenHeight;
/*    */     
/* 31 */     this.parentGUI = givenParentGUI;
/*    */     
/* 33 */     this.mc = givenParentGUI.field_146297_k;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 38 */     this.buttons.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void parentActionPerformed(GuiButton givenButton) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void actionPerformed(GuiButton givenButton) {}
/*    */ 
/*    */   
/*    */   public void handleScroll(int mouseX, int mouseY, int dWheel) {}
/*    */ 
/*    */   
/*    */   public void mouseReleased(int mouseX, int mouseY) {}
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 61 */     if (mouseButton == 0) {
/* 62 */       Buttons.click(this.buttons, mouseX, mouseY, this::actionPerformed);
/*    */     }
/*    */   }
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 67 */     drawBackground();
/* 68 */     drawButtons(mouseX, mouseY, partialTicks);
/* 69 */     this.hovered = (mouseX >= this.posX && mouseY >= this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawBackground() {}
/*    */   
/*    */   public void drawButtons(int mouseX, int mouseY, float partialTicks) {
/* 76 */     for (GuiButton button : this.buttons) {
/* 77 */       GlStateManager.func_179094_E();
/* 78 */       button.func_191745_a(this.mc, mouseX, mouseY, partialTicks);
/* 79 */       GlStateManager.func_179121_F();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void addButton(GuiButton givenButton) {
/* 84 */     this.buttons.add(givenButton);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose() {}
/*    */ 
/*    */   
/*    */   public boolean isMouseOver() {
/* 92 */     return this.hovered;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\gui\api\GuiMWContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */