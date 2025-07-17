/*     */ package com.modularwarfare.client.gui.api;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GuiMWButton
/*     */   extends GuiButton
/*     */ {
/*  19 */   public int isOver = 2;
/*     */   public boolean drawBackground = false;
/*     */   public boolean drawShadow = true;
/*  22 */   public int buttonColor = 0;
/*     */   public boolean centeredText = true;
/*     */   public boolean soundPlayed = true;
/*  25 */   public int colorText = -43691;
/*  26 */   private ResourceLocation iconTexture = null;
/*  27 */   private float fade = 0.0F;
/*     */   
/*     */   private int xMovement;
/*     */   private boolean animationStarted = true;
/*     */   private int toolTipY;
/*     */   private String toolTip;
/*     */   private boolean showToolTip = false;
/*  34 */   public int colorTheme = 1426063360;
/*     */   
/*     */   public GuiMWButton(int buttonId, int x, int y, int givenWidth, int givenHeight, String givenText) {
/*  37 */     this(buttonId, x, y, givenText);
/*  38 */     this.field_146120_f = givenWidth;
/*  39 */     this.field_146121_g = givenHeight;
/*     */   }
/*     */   
/*     */   public GuiMWButton(int buttonId, int x, int y, int givenWidth, int givenHeight, String givenText, int colorText) {
/*  43 */     this(buttonId, x, y, givenText);
/*  44 */     this.field_146120_f = givenWidth;
/*  45 */     this.field_146121_g = givenHeight;
/*  46 */     this.colorText = colorText;
/*     */   }
/*     */   
/*     */   public GuiMWButton(int buttonID, int x, int y, ResourceLocation iconTexture) {
/*  50 */     this(buttonID, x, y, 30, 20, "");
/*  51 */     this.iconTexture = iconTexture;
/*     */   }
/*     */   
/*     */   public GuiMWButton(int id, int x, int y, int width, int height, String displayString, String givenToolTip, Color givenColor, ResourceLocation iconTexture) {
/*  55 */     this(id, x, y, width, height, displayString);
/*  56 */     this.toolTip = givenToolTip;
/*  57 */     this.showToolTip = true;
/*  58 */     this.iconTexture = iconTexture;
/*     */   }
/*     */   
/*     */   public GuiMWButton(int id, int x, int y, int width, int height, String displayString, ResourceLocation iconTexture) {
/*  62 */     this(id, x, y, width, height, displayString);
/*  63 */     this.iconTexture = iconTexture;
/*     */   }
/*     */   
/*     */   public GuiMWButton(int id, int x, int y, String par6, int buttonColor, String givenToolTip) {
/*  67 */     this(id, x, y, 69, 20, par6);
/*  68 */     this.buttonColor = buttonColor;
/*  69 */     this.toolTip = givenToolTip;
/*  70 */     this.showToolTip = true;
/*     */   }
/*     */   
/*     */   public GuiMWButton(int id, int x, int y, int width, int height, String displayString, String givenToolTip, int givenColor) {
/*  74 */     this(id, x, y, width, height, displayString);
/*  75 */     this.toolTip = givenToolTip;
/*  76 */     this.showToolTip = true;
/*  77 */     this.buttonColor = givenColor;
/*     */   }
/*     */   
/*     */   public GuiMWButton(int buttonId, int x, int y, String givenText) {
/*  81 */     super(buttonId, x, y, givenText);
/*     */   }
/*     */   
/*     */   public GuiMWButton addToolTip(String givenToolTip) {
/*  85 */     this.showToolTip = true;
/*  86 */     this.toolTip = givenToolTip;
/*  87 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_191745_a(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
/*  93 */     GlStateManager.func_179094_E();
/*     */     
/*  95 */     if (this.field_146125_m) {
/*     */       
/*  97 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  98 */       this.field_146123_n = (mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g);
/*  99 */       this.isOver = func_146114_a(this.field_146123_n);
/*     */       
/* 101 */       if (this.drawBackground) {
/* 102 */         if (this.drawShadow) {
/* 103 */           GuiUtils.renderRectWithOutline(this.field_146128_h, this.field_146129_i, this.field_146120_f, this.field_146121_g, this.colorTheme, this.colorTheme, 1);
/*     */         } else {
/* 105 */           GuiUtils.renderRect(this.field_146128_h, this.field_146129_i, this.field_146120_f, this.field_146121_g, this.buttonColor);
/*     */         } 
/*     */       }
/* 108 */       func_146119_b(mc, mouseX, mouseY);
/* 109 */       String displayText = this.field_146126_j;
/* 110 */       if (this.isOver == 2) {
/*     */         
/* 112 */         if (!this.soundPlayed)
/*     */         {
/* 114 */           this.soundPlayed = true;
/*     */         }
/*     */         
/* 117 */         if (!this.animationStarted) {
/* 118 */           this.animationStarted = true;
/*     */         }
/*     */         
/* 121 */         if (this.fade <= 0.0F) {
/* 122 */           this.fade = 0.0F;
/*     */         } else {
/* 124 */           this.fade = (float)(this.fade - 0.2D);
/*     */         } 
/*     */         
/* 127 */         if (this.xMovement < 3) {
/* 128 */           this.xMovement++;
/*     */         }
/*     */         
/* 131 */         GlStateManager.func_179094_E();
/*     */         
/* 133 */         GlStateManager.func_179121_F();
/*     */       } else {
/*     */         
/* 136 */         this.toolTipY = 0;
/* 137 */         this.fade = 1.0F;
/* 138 */         this.soundPlayed = false;
/* 139 */         this.animationStarted = false;
/* 140 */         if (this.xMovement > 0) {
/* 141 */           this.xMovement--;
/*     */         }
/*     */       } 
/* 144 */       if (this.iconTexture != null) {
/* 145 */         if (this.isOver == 2) {
/* 146 */           GuiUtils.renderColor(10066329);
/*     */         } else {
/* 148 */           GuiUtils.renderColor(16777215);
/*     */         } 
/* 150 */         GuiUtils.renderImage((this.field_146128_h + this.field_146120_f / 2 - 8), (this.field_146129_i + this.field_146121_g - 8 - 10), this.iconTexture, 16.0D, 16.0D);
/* 151 */         GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*     */       } 
/* 153 */       if (!this.field_146124_l) {
/* 154 */         displayText = ChatFormatting.GRAY + displayText;
/*     */       }
/* 156 */       if (!this.centeredText) {
/* 157 */         GuiUtils.renderTextWithShadow(displayText, this.field_146128_h + 2, this.field_146129_i + (this.field_146121_g - 8) / 2, (this.isOver == 2) ? -1 : -1);
/*     */         
/*     */         return;
/*     */       } 
/* 161 */       if (this.showToolTip && this.isOver == 2) {
/* 162 */         int toolTipWidth = mc.field_71466_p.func_78256_a(this.toolTip);
/* 163 */         GuiUtils.renderRectWithOutline(mouseX, mouseY - 10, this.toolTipY, 10, this.colorTheme, this.colorTheme, 1);
/* 164 */         if (this.toolTipY < toolTipWidth + 2) {
/*     */           
/* 166 */           int toolTipGap = toolTipWidth + 2 - this.toolTipY;
/*     */           
/* 168 */           if (toolTipGap >= 10) {
/* 169 */             this.toolTipY += 10;
/*     */           } else {
/* 171 */             this.toolTipY++;
/*     */           }
/*     */         
/* 174 */         } else if (this.toolTipY > toolTipWidth + 2) {
/* 175 */           this.toolTipY--;
/*     */         } 
/*     */         
/* 178 */         if (this.toolTipY >= toolTipWidth + 2) {
/* 179 */           GuiUtils.renderText(this.toolTip, mouseX + 1, mouseY - 9, 16777215);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 184 */       GuiUtils.renderCenteredTextWithShadow(displayText, this.field_146128_h + this.field_146120_f / 2 + this.xMovement, this.field_146129_i + (this.field_146121_g - 8) / 2, (this.isOver == 2) ? -1 : this.colorText, 0);
/*     */     } 
/*     */     
/* 187 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\gui\api\GuiMWButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */