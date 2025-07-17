/*     */ package com.modularwarfare.client.gui.api;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class GuiMWScreen
/*     */   extends GuiScreen
/*     */ {
/*  14 */   protected List<GuiMWContainer> containerList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  18 */     super.func_73866_w_();
/*  19 */     this.containerList.clear();
/*  20 */     initButtons();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initButtons() {}
/*     */ 
/*     */   
/*     */   public void addContainer(GuiMWContainer container) {
/*  28 */     container.initGui();
/*  29 */     this.containerList.add(container);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/*  34 */     super.func_73876_c();
/*  35 */     updateContainers();
/*     */   }
/*     */   
/*     */   public void updateContainers() {
/*  39 */     for (GuiMWContainer gui : this.containerList) {
/*  40 */       gui.updateScreen();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) throws IOException {
/*  46 */     super.func_146284_a(button);
/*  47 */     actionPerformedContainer(button);
/*     */   }
/*     */   
/*     */   public void actionPerformedContainer(GuiButton guiButton) {
/*  51 */     for (GuiMWContainer gui : this.containerList) {
/*  52 */       gui.parentActionPerformed(guiButton);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*  58 */     super.func_73863_a(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   public void drawContainers(int mouseX, int mouseY, float partialTicks) {
/*  62 */     for (GuiMWContainer gui : this.containerList) {
/*  63 */       GlStateManager.func_179094_E();
/*  64 */       gui.drawScreen(mouseX, mouseY, partialTicks);
/*  65 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   public GuiMWContainer getContainer(int containerID) {
/*  70 */     for (GuiMWContainer cont : this.containerList) {
/*  71 */       if (cont.containerID == containerID) {
/*  72 */         return cont;
/*     */       }
/*     */     } 
/*  75 */     return null;
/*     */   }
/*     */   
/*     */   public void func_146281_b() {
/*  79 */     this.containerList.forEach(GuiMWContainer::onClose);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146274_d() {
/*     */     try {
/*  85 */       super.func_146274_d();
/*  86 */     } catch (IOException e) {
/*  87 */       e.printStackTrace();
/*     */     } 
/*  89 */     int dWheel = Mouse.getEventDWheel();
/*  90 */     if (dWheel != 0) {
/*  91 */       int mouseX = Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c;
/*  92 */       int mouseY = this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1;
/*  93 */       for (GuiMWContainer container : this.containerList) {
/*  94 */         container.handleScroll(mouseX, mouseY, dWheel);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
/*     */     try {
/* 102 */       super.func_73864_a(mouseX, mouseY, mouseButton);
/* 103 */     } catch (IOException e) {
/* 104 */       e.printStackTrace();
/*     */     } 
/* 106 */     for (GuiMWContainer container : this.containerList) {
/* 107 */       container.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146273_a(int par1, int par2, int par3, long timeSinceLastClick) {
/* 113 */     super.func_146273_a(par1, par2, par3, timeSinceLastClick);
/*     */     
/* 115 */     if (par3 >= 0)
/* 116 */       for (GuiMWContainer container : this.containerList)
/* 117 */         container.mouseReleased(par1, par2);  
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\gui\api\GuiMWScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */