/*    */ package com.modularwarfare.client.gui.customization.containers;
/*    */ 
/*    */ import com.modularwarfare.client.gui.api.GuiMWButton;
/*    */ import com.modularwarfare.client.gui.api.GuiMWContainer;
/*    */ import com.modularwarfare.client.gui.api.GuiUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ 
/*    */ public class ContainerGunParts
/*    */   extends GuiMWContainer
/*    */ {
/* 17 */   public int colorTheme = 1426063360;
/*    */   
/* 19 */   public List<String> gunParts = new ArrayList<>();
/* 20 */   public HashMap<String, Boolean> partsSets = new HashMap<>();
/*    */   
/* 22 */   private int buttonWidth = 20;
/* 23 */   private int buttonHeight = 12;
/*    */   
/*    */   public ContainerGunParts(int givenID, int givenPosX, int givenPosY, int givenWidth, int givenHeight, GuiScreen givenParentGUI) {
/* 26 */     super(givenID, givenPosX, givenPosY, givenWidth, givenHeight, givenParentGUI);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 33 */     this.gunParts = Arrays.asList(new String[] { "gunModel", "slideModel", "ammoModel" });
/*    */     int i;
/* 35 */     for (i = 0; i < this.gunParts.size(); i++) {
/* 36 */       this.partsSets.put(this.gunParts.get(i), Boolean.valueOf(true));
/*    */     }
/* 38 */     this.partsSets.put("gunModel", Boolean.valueOf(true));
/*    */     
/* 40 */     for (i = 0; i < this.gunParts.size(); i++) {
/* 41 */       String displayName = ((String)this.gunParts.get(i)).substring(0, 1).toUpperCase() + ((String)this.gunParts.get(i)).substring(1).toLowerCase();
/* 42 */       GuiMWButton button = new GuiMWButton(i, this.posX + this.width / 2 - 8, this.posY + 15 * i + 4, this.buttonWidth, this.buttonHeight, displayName);
/*    */       
/* 44 */       addButton((GuiButton)button);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void actionPerformed(GuiButton givenButton) {
/* 49 */     this.partsSets.put(this.gunParts.get(givenButton.field_146127_k), Boolean.valueOf(!((Boolean)this.partsSets.get(this.gunParts.get(givenButton.field_146127_k))).booleanValue()));
/* 50 */     if (givenButton instanceof GuiMWButton) {
/* 51 */       ((GuiMWButton)givenButton).colorText = ((Boolean)this.partsSets.get(this.gunParts.get(givenButton.field_146127_k))).booleanValue() ? -1 : -43691;
/*    */     }
/*    */   }
/*    */   
/*    */   public void drawBackground() {
/* 56 */     GuiUtils.renderRectWithOutline(this.posX, this.posY, this.width, this.height, this.colorTheme - 570425344, this.colorTheme - 570425344, 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\gui\customization\containers\ContainerGunParts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */