/*     */ package com.modularwarfare.client.gui;
/*     */ 
/*     */ import com.modularwarfare.client.gui.api.GuiUtils;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.utility.RenderHelperMW;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureButton
/*     */   extends GuiButton
/*     */ {
/*  24 */   private ResourceLocation iconTexture = null;
/*     */   private int toolTipY;
/*     */   private String toolTip;
/*     */   private boolean showToolTip = false;
/*  28 */   public int isOver = 2;
/*  29 */   public int colorText = -43691;
/*     */   private int xMovement;
/*  31 */   public int colorTheme = 1426063360;
/*     */   private AttachmentPresetEnum attachment;
/*  33 */   private ItemStack itemStack = ItemStack.field_190927_a; private TypeEnum type;
/*     */   
/*     */   public TypeEnum getType() {
/*  36 */     return this.type;
/*     */   }
/*     */   public boolean hidden = false;
/*  39 */   public int state = -1; public double field_146128_h;
/*     */   public ItemStack getItemStack() {
/*  41 */     return this.itemStack;
/*     */   } public double field_146129_i;
/*     */   public TextureButton setItemStack(ItemStack itemStack) {
/*  44 */     this.itemStack = itemStack;
/*  45 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AttachmentPresetEnum getAttachmentType() {
/*  50 */     return this.attachment;
/*     */   }
/*     */   public TextureButton(int buttonId, double x, double y, String givenText) {
/*  53 */     super(buttonId, (int)x, (int)y, givenText);
/*     */   }
/*     */   public TextureButton(int buttonId, double x, double y, int givenWidth, int givenHeight, String givenText) {
/*  56 */     this(buttonId, x, y, givenText);
/*  57 */     this.field_146120_f = givenWidth;
/*  58 */     this.field_146121_g = givenHeight;
/*     */   }
/*     */   public TextureButton(int id, double x, double y, int width, int height, ResourceLocation iconTexture) {
/*  61 */     this(id, x, y, width, height, "");
/*  62 */     this.field_146128_h = x;
/*  63 */     this.field_146129_i = y;
/*  64 */     this.iconTexture = iconTexture;
/*     */   }
/*     */   public TextureButton setAttachment(AttachmentPresetEnum attachment) {
/*  67 */     this.attachment = attachment;
/*  68 */     return this;
/*     */   }
/*     */   public TextureButton addToolTip(String givenToolTip) {
/*  71 */     this.showToolTip = true;
/*  72 */     this.toolTip = givenToolTip;
/*  73 */     return this;
/*     */   }
/*     */   public TextureButton setType(TypeEnum type) {
/*  76 */     this.type = type;
/*  77 */     return this;
/*     */   }
/*     */   
/*  80 */   public enum TypeEnum { Button,
/*  81 */     Slot,
/*  82 */     SubSlot,
/*  83 */     SideButton,
/*  84 */     SideButtonVert; }
/*     */ 
/*     */   
/*     */   public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
/*  88 */     return (this.field_146124_l && this.field_146125_m && mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g);
/*     */   }
/*     */   
/*     */   public void func_191745_a(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
/*  92 */     ScaledResolution scaledresolution = new ScaledResolution(Minecraft.func_71410_x());
/*  93 */     GlStateManager.func_179094_E();
/*  94 */     GlStateManager.func_179147_l();
/*  95 */     this.field_146123_n = (mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g);
/*  96 */     if (this.hidden) {
/*  97 */       if (this.field_146123_n || this.state == 0) {
/*  98 */         this.field_146125_m = true;
/*     */       } else {
/* 100 */         this.field_146125_m = false;
/*     */       } 
/*     */     }
/* 103 */     if (this.field_146125_m) {
/*     */       
/* 105 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 107 */       this.isOver = func_146114_a(this.field_146123_n);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 116 */       func_146119_b(mc, mouseX, mouseY);
/* 117 */       String displayText = this.field_146126_j;
/* 118 */       if (this.isOver == 2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 139 */         GlStateManager.func_179094_E();
/*     */         
/* 141 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 152 */       if (this.iconTexture != null) {
/* 153 */         if (this.isOver == 2) {
/* 154 */           GuiUtils.renderColor(10066329);
/*     */         } else {
/* 156 */           GuiUtils.renderColor(16777215);
/*     */         } 
/*     */         
/* 159 */         (Minecraft.func_71410_x()).field_71446_o.func_110577_a(this.iconTexture);
/* 160 */         GlStateManager.func_187421_b(3553, 10240, 9729);
/* 161 */         GlStateManager.func_187421_b(3553, 10241, 9729);
/* 162 */         RenderHelperMW.drawTexturedRect(this.field_146128_h, this.field_146129_i, this.field_146120_f + 0.05D, this.field_146121_g);
/* 163 */         GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 164 */         if (this.isOver == 2) {
/* 165 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
/*     */           
/* 167 */           GlStateManager.func_179090_x();
/* 168 */           GlStateManager.func_179147_l();
/* 169 */           RenderHelperMW.drawTexturedRect(this.field_146128_h, this.field_146129_i, this.field_146120_f + 0.05D, this.field_146121_g);
/* 170 */           GlStateManager.func_179098_w();
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 176 */       if (!this.itemStack.func_190926_b()) {
/* 177 */         GlStateManager.func_179094_E();
/* 178 */         double scale = 5.9D / scaledresolution.func_78325_e() * 0.9D;
/* 179 */         GlStateManager.func_179109_b(0.0F, 0.0F, -135.0F);
/* 180 */         GlStateManager.func_179139_a(scale, scale, scale);
/*     */         
/* 182 */         RenderHelperMW.renderItemStack(this.itemStack, (int)((this.field_146128_h + 3.0D) / scale), (int)((this.field_146129_i + 2.5D) / scale), partialTicks, false);
/* 183 */         GlStateManager.func_179121_F();
/*     */         
/* 185 */         GlStateManager.func_179097_i();
/* 186 */         GlStateManager.func_179094_E();
/* 187 */         scale = 6.0D / scaledresolution.func_78325_e() * 0.4D;
/* 188 */         GlStateManager.func_179109_b(0.0F, 0.0F, 1.0F);
/* 189 */         GlStateManager.func_179139_a(scale, scale, scale);
/* 190 */         String str = this.itemStack.func_82833_r();
/* 191 */         int strW = mc.field_71466_p.func_78256_a(str);
/* 192 */         while (strW > 36) {
/* 193 */           str = str.substring(0, str.length() - 1);
/* 194 */           strW = mc.field_71466_p.func_78256_a(str);
/*     */         } 
/* 196 */         if (strW > 4);
/*     */ 
/*     */         
/* 199 */         RenderHelperMW.renderText(str, (int)((this.field_146128_h + (4 / scaledresolution.func_78325_e())) / scale), (int)((this.field_146129_i + (4 / scaledresolution.func_78325_e())) / scale), 16777215);
/* 200 */         GlStateManager.func_179121_F();
/* 201 */         GlStateManager.func_179126_j();
/* 202 */       } else if (this.attachment != null) {
/* 203 */         GlStateManager.func_179094_E();
/* 204 */         double scale = 6.0D / scaledresolution.func_78325_e() * 0.5D;
/* 205 */         GlStateManager.func_179139_a(scale, scale, scale);
/* 206 */         RenderHelperMW.renderText(I18n.func_135052_a("mwf:gui.modify.none", new Object[0]), (int)((this.field_146128_h + 2.0D) / scale), (int)((this.field_146129_i + 2.0D) / scale), 16777215);
/* 207 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */       
/* 210 */       if (!this.field_146124_l) {
/* 211 */         displayText = ChatFormatting.GRAY + displayText;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 217 */       if (this.type == TypeEnum.SideButton) {
/* 218 */         (Minecraft.func_71410_x()).field_71446_o.func_110577_a((this.state == 0) ? GuiGunModify.arrow_down : GuiGunModify.arrow_up);
/* 219 */         GlStateManager.func_187421_b(3553, 10240, 9729);
/* 220 */         GlStateManager.func_187421_b(3553, 10241, 9729);
/* 221 */         RenderHelperMW.drawTexturedRect(this.field_146128_h, this.field_146129_i, this.field_146120_f + 0.05D, this.field_146121_g);
/*     */       } 
/* 223 */       if (this.type == TypeEnum.SideButtonVert) {
/* 224 */         (Minecraft.func_71410_x()).field_71446_o.func_110577_a((this.state == 0) ? GuiGunModify.arrow_right : GuiGunModify.arrow_left);
/* 225 */         GlStateManager.func_187421_b(3553, 10240, 9729);
/* 226 */         GlStateManager.func_187421_b(3553, 10241, 9729);
/* 227 */         RenderHelperMW.drawTexturedRect(this.field_146128_h - this.field_146120_f / 1.7D, this.field_146129_i, (this.field_146121_g / 2), this.field_146121_g);
/*     */       } 
/* 229 */       if (this.type == TypeEnum.Slot && !this.itemStack.func_190926_b() && this.isOver == 2) {
/* 230 */         GlStateManager.func_179094_E();
/* 231 */         GlStateManager.func_179109_b(0.0F, 0.0F, 500.0F);
/* 232 */         double scale = 1.5D;
/* 233 */         GlStateManager.func_179139_a(scale, scale, scale);
/* 234 */         this.toolTip = this.itemStack.func_82840_a((EntityPlayer)mc.field_71439_g, mc.field_71474_y.field_82882_x ? (ITooltipFlag)ITooltipFlag.TooltipFlags.ADVANCED : (ITooltipFlag)ITooltipFlag.TooltipFlags.NORMAL).toString();
/* 235 */         int toolTipWidth = mc.field_71466_p.func_78256_a(this.toolTip);
/* 236 */         GuiUtils.renderRectWithOutline((int)(mouseX / scale), (int)((mouseY - 10) / scale), this.toolTipY, 10, this.colorTheme, this.colorTheme, 1);
/* 237 */         if (this.toolTipY < toolTipWidth + 2) {
/*     */           
/* 239 */           int toolTipGap = toolTipWidth + 2 - this.toolTipY;
/*     */           
/* 241 */           if (toolTipGap >= 10) {
/* 242 */             this.toolTipY += 10;
/*     */           } else {
/* 244 */             this.toolTipY++;
/*     */           }
/*     */         
/* 247 */         } else if (this.toolTipY > toolTipWidth + 2) {
/* 248 */           this.toolTipY--;
/*     */         } 
/*     */         
/* 251 */         if (this.toolTipY >= toolTipWidth + 2) {
/* 252 */           GuiUtils.renderText(this.toolTip, (int)((mouseX + 1) / scale), (int)((mouseY - 9) / scale), 16777215);
/*     */         }
/* 254 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */       
/* 257 */       GuiUtils.renderCenteredTextWithShadow(displayText, (int)this.field_146128_h + this.field_146120_f / 2 + this.xMovement, (int)this.field_146129_i + (this.field_146121_g - 8) / 2, (this.isOver == 2) ? -1 : this.colorText, 0);
/*     */     } 
/*     */     
/* 260 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\gui\TextureButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */