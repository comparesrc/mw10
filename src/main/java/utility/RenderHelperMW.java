/*     */ package com.modularwarfare.utility;
/*     */ import java.awt.Color;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.lwjgl.Sys;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.Color;
/*     */ 
/*     */ public class RenderHelperMW {
/*  26 */   private static final DecimalFormat energyValue = new DecimalFormat("###,###,###,###,###");
/*     */   
/*     */   public static void renderItemStack(ItemStack stack, int x, int y, float partialTicks, boolean isJustOne) {
/*  29 */     GL11.glEnable(3042);
/*  30 */     GL11.glBlendFunc(770, 771);
/*  31 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  32 */     GL11.glEnable(32826);
/*  33 */     RenderHelper.func_74520_c();
/*  34 */     GL11.glEnable(3553);
/*  35 */     GL11.glEnable(32826);
/*  36 */     RenderHelper.func_74520_c();
/*  37 */     if (stack != null) {
/*  38 */       float f1 = stack.func_190921_D() - partialTicks;
/*  39 */       f1 = 0.0F;
/*  40 */       if (f1 > 0.0F) {
/*  41 */         GL11.glPushMatrix();
/*  42 */         float f2 = 1.0F + f1 / 5.0F;
/*  43 */         GL11.glTranslatef((x + 8), (y + 12), 0.0F);
/*  44 */         GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
/*  45 */         GL11.glTranslatef(-(x + 8), -(y + 12), 0.0F);
/*     */       } 
/*  47 */       Minecraft.func_71410_x().func_175599_af().func_180450_b(stack, x, y);
/*  48 */       if (f1 > 0.0F) GL11.glPopMatrix(); 
/*  49 */       ItemStack fits = new ItemStack(stack.func_77973_b(), 1);
/*  50 */       if (isJustOne) {
/*  51 */         Minecraft.func_71410_x().func_175599_af().func_180453_a((Minecraft.func_71410_x()).field_71466_p, fits, x, y, null);
/*     */       } else {
/*  53 */         Minecraft.func_71410_x().func_175599_af().func_180453_a((Minecraft.func_71410_x()).field_71466_p, stack, x, y, null);
/*     */       } 
/*  55 */     }  RenderHelper.func_74518_a();
/*  56 */     GL11.glDisable(32826);
/*     */   }
/*     */   
/*     */   public static void renderText(String text, int posX, int posY, int color) {
/*  60 */     Minecraft mc = Minecraft.func_71410_x();
/*  61 */     mc.field_71466_p.func_78276_b(text, posX, posY, color);
/*     */   }
/*     */   
/*     */   public static void renderTextWithShadow(String text, int posX, int posY, int color) {
/*  65 */     Minecraft mc = Minecraft.func_71410_x();
/*  66 */     mc.field_71466_p.func_175063_a(text, posX, posY, color);
/*     */   }
/*     */   
/*     */   public static void renderCenteredText(String text, int posX, int posY, int color) {
/*  70 */     Minecraft mc = Minecraft.func_71410_x();
/*  71 */     renderText(text, posX - mc.field_71466_p.func_78256_a(text) / 2, posY, color);
/*     */   }
/*     */   
/*     */   public static void renderCenteredTextWithShadow(String text, int posX, int posY, int color) {
/*  75 */     Minecraft mc = Minecraft.func_71410_x();
/*  76 */     renderTextWithShadow(text, posX - mc.field_71466_p.func_78256_a(text) / 2, posY, color);
/*     */   }
/*     */   
/*     */   public static void renderTextScaled(String text, int posX, int posY, int color, double givenScale) {
/*  80 */     GL11.glPushMatrix();
/*  81 */     GL11.glTranslated(posX, posY, 0.0D);
/*  82 */     GL11.glScaled(givenScale, givenScale, givenScale);
/*  83 */     renderText(text, 0, 0, color);
/*  84 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderCenteredTextScaled(String text, int posX, int posY, int color, double givenScale) {
/*  88 */     GL11.glPushMatrix();
/*  89 */     GL11.glTranslated(posX, posY, 0.0D);
/*  90 */     GL11.glScaled(givenScale, givenScale, givenScale);
/*  91 */     renderCenteredText(text, 0, 0, color);
/*  92 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderCenteredTextScaledWithShadow(String text, int posX, int posY, int color, double givenScale) {
/*  96 */     GL11.glPushMatrix();
/*  97 */     GL11.glTranslated(posX, posY, 0.0D);
/*  98 */     GL11.glScaled(givenScale, givenScale, givenScale);
/*  99 */     renderCenteredTextWithShadow(text, 0, 0, color);
/* 100 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderTextWithOutline(String text, int x, int y, int color, int outlineColor) {
/* 104 */     renderText(text, x - 1, y + 1, outlineColor);
/* 105 */     renderText(text, x, y + 1, outlineColor);
/* 106 */     renderText(text, x + 1, y + 1, outlineColor);
/* 107 */     renderText(text, x - 1, y, outlineColor);
/* 108 */     renderText(text, x + 1, y, outlineColor);
/* 109 */     renderText(text, x - 1, y - 1, outlineColor);
/* 110 */     renderText(text, x, y - 1, outlineColor);
/* 111 */     renderText(text, x + 1, y - 1, outlineColor);
/*     */     
/* 113 */     renderText(text, x, y, color);
/*     */   }
/*     */   
/*     */   public static void renderTextScaledWithOutline(String text, int x, int y, int color, int outlineColor, double givenScale) {
/* 117 */     renderTextScaled(text, x - 1, y + 1, outlineColor, givenScale);
/* 118 */     renderTextScaled(text, x, y + 1, outlineColor, givenScale);
/* 119 */     renderTextScaled(text, x + 1, y + 1, outlineColor, givenScale);
/* 120 */     renderTextScaled(text, x - 1, y, outlineColor, givenScale);
/* 121 */     renderTextScaled(text, x + 1, y, outlineColor, givenScale);
/* 122 */     renderTextScaled(text, x - 1, y - 1, outlineColor, givenScale);
/* 123 */     renderTextScaled(text, x, y - 1, outlineColor, givenScale);
/* 124 */     renderTextScaled(text, x + 1, y - 1, outlineColor, givenScale);
/*     */     
/* 126 */     renderTextScaled(text, x, y, color, givenScale);
/*     */   }
/*     */   
/*     */   public static void renderCenteredTextWithOutline(String text, int x, int y, int color, int outlineColor) {
/* 130 */     Minecraft mc = Minecraft.func_71410_x();
/* 131 */     FontRenderer fr = mc.field_71466_p;
/*     */     
/* 133 */     renderText(text, x - 1 - fr.func_78256_a(text) / 2, y + 1, outlineColor);
/* 134 */     renderText(text, x - fr.func_78256_a(text) / 2, y + 1, outlineColor);
/* 135 */     renderText(text, x + 1 - fr.func_78256_a(text) / 2, y + 1, outlineColor);
/* 136 */     renderText(text, x - 1 - fr.func_78256_a(text) / 2, y, outlineColor);
/* 137 */     renderText(text, x + 1 - fr.func_78256_a(text) / 2, y, outlineColor);
/* 138 */     renderText(text, x - 1 - fr.func_78256_a(text) / 2, y - 1, outlineColor);
/* 139 */     renderText(text, x - fr.func_78256_a(text) / 2, y - 1, outlineColor);
/* 140 */     renderText(text, x + 1 - fr.func_78256_a(text) / 2, y - 1, outlineColor);
/*     */     
/* 142 */     renderText(text, x - fr.func_78256_a(text) / 2, y, color);
/*     */   }
/*     */   
/*     */   public static void renderRect(int givenPosX, int givenPosY, int givenWidth, int givenHeight, int givenColor) {
/* 146 */     GL11.glPushMatrix();
/*     */     
/* 148 */     givenWidth = givenPosX + givenWidth;
/* 149 */     givenHeight = givenPosY + givenHeight;
/* 150 */     if (givenPosX < givenWidth) {
/* 151 */       int i = givenPosX;
/* 152 */       givenPosX = givenWidth;
/* 153 */       givenWidth = i;
/*     */     } 
/* 155 */     if (givenPosY < givenHeight) {
/* 156 */       int j = givenPosY;
/* 157 */       givenPosY = givenHeight;
/* 158 */       givenHeight = j;
/*     */     } 
/* 160 */     float f = (givenColor >> 16 & 0xFF) / 255.0F;
/* 161 */     float f1 = (givenColor >> 8 & 0xFF) / 255.0F;
/* 162 */     float f2 = (givenColor & 0xFF) / 255.0F;
/* 163 */     float f3 = (givenColor >> 24 & 0xFF) / 255.0F;
/*     */     
/* 165 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 166 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 167 */     GlStateManager.func_179147_l();
/* 168 */     GlStateManager.func_179090_x();
/* 169 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 170 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/* 171 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 172 */     bufferbuilder.func_181662_b(givenPosX, givenHeight, 0.0D).func_181675_d();
/* 173 */     bufferbuilder.func_181662_b(givenWidth, givenHeight, 0.0D).func_181675_d();
/* 174 */     bufferbuilder.func_181662_b(givenWidth, givenPosY, 0.0D).func_181675_d();
/* 175 */     bufferbuilder.func_181662_b(givenPosX, givenPosY, 0.0D).func_181675_d();
/* 176 */     tessellator.func_78381_a();
/* 177 */     GlStateManager.func_179098_w();
/* 178 */     GlStateManager.func_179084_k();
/*     */     
/* 180 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderRect(int givenPosX, int givenPosY, int givenWidth, int givenHeight) {
/* 184 */     GL11.glPushMatrix();
/*     */     
/* 186 */     givenWidth = givenPosX + givenWidth;
/* 187 */     givenHeight = givenPosY + givenHeight;
/* 188 */     if (givenPosX < givenWidth) {
/* 189 */       int i = givenPosX;
/* 190 */       givenPosX = givenWidth;
/* 191 */       givenWidth = i;
/*     */     } 
/* 193 */     if (givenPosY < givenHeight) {
/* 194 */       int j = givenPosY;
/* 195 */       givenPosY = givenHeight;
/* 196 */       givenHeight = j;
/*     */     } 
/* 198 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 199 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 200 */     GlStateManager.func_179147_l();
/* 201 */     GlStateManager.func_179090_x();
/* 202 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 203 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 204 */     bufferbuilder.func_181662_b(givenPosX, givenHeight, 0.0D).func_181675_d();
/* 205 */     bufferbuilder.func_181662_b(givenWidth, givenHeight, 0.0D).func_181675_d();
/* 206 */     bufferbuilder.func_181662_b(givenWidth, givenPosY, 0.0D).func_181675_d();
/* 207 */     bufferbuilder.func_181662_b(givenPosX, givenPosY, 0.0D).func_181675_d();
/* 208 */     tessellator.func_78381_a();
/* 209 */     GlStateManager.func_179098_w();
/* 210 */     GlStateManager.func_179084_k();
/*     */     
/* 212 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderRectAlphaComp(int givenPosX, int givenPosY, int givenWidth, int givenHeight, int givenColor, int alpha) {
/* 216 */     GL11.glPushMatrix();
/*     */     
/* 218 */     givenWidth = givenPosX + givenWidth;
/* 219 */     givenHeight = givenPosY + givenHeight;
/* 220 */     if (givenPosX < givenWidth) {
/* 221 */       int i = givenPosX;
/* 222 */       givenPosX = givenWidth;
/* 223 */       givenWidth = i;
/*     */     } 
/* 225 */     if (givenPosY < givenHeight) {
/* 226 */       int j = givenPosY;
/* 227 */       givenPosY = givenHeight;
/* 228 */       givenHeight = j;
/*     */     } 
/* 230 */     float f = (givenColor >> 16 & 0xFF) / 255.0F;
/* 231 */     float f1 = (givenColor >> 8 & 0xFF) / 255.0F;
/* 232 */     float f2 = (givenColor & 0xFF) / 255.0F;
/* 233 */     float f3 = alpha / 255.0F;
/*     */     
/* 235 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 236 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 237 */     GlStateManager.func_179147_l();
/* 238 */     GlStateManager.func_179090_x();
/* 239 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 240 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/* 241 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 242 */     bufferbuilder.func_181662_b(givenPosX, givenHeight, 0.0D).func_181675_d();
/* 243 */     bufferbuilder.func_181662_b(givenWidth, givenHeight, 0.0D).func_181675_d();
/* 244 */     bufferbuilder.func_181662_b(givenWidth, givenPosY, 0.0D).func_181675_d();
/* 245 */     bufferbuilder.func_181662_b(givenPosX, givenPosY, 0.0D).func_181675_d();
/* 246 */     tessellator.func_78381_a();
/* 247 */     GlStateManager.func_179098_w();
/* 248 */     GlStateManager.func_179084_k();
/*     */     
/* 250 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderRectWithOutline(int givenPosX, int givenPosY, int givenWidth, int givenHeight, int givenColor, int givenOutlineColor, int outlineThickness) {
/* 254 */     GL11.glPushMatrix();
/* 255 */     renderRect(givenPosX - outlineThickness, givenPosY - outlineThickness, givenWidth + outlineThickness * 2, givenHeight + outlineThickness * 2, givenOutlineColor);
/* 256 */     renderRect(givenPosX, givenPosY, givenWidth, givenHeight, givenColor);
/* 257 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderRectWithGradient(int givenPosX, int givenPosY, int givenWidth, int givenHeight, int startColor, int endColor, double givenZLevel) {
/* 261 */     GL11.glPushMatrix();
/*     */     
/* 263 */     givenWidth = givenPosX + givenWidth;
/* 264 */     givenHeight = givenPosY + givenHeight;
/*     */     
/* 266 */     float f = (startColor >> 24 & 0xFF) / 255.0F;
/* 267 */     float f1 = (startColor >> 16 & 0xFF) / 255.0F;
/* 268 */     float f2 = (startColor >> 8 & 0xFF) / 255.0F;
/* 269 */     float f3 = (startColor & 0xFF) / 255.0F;
/* 270 */     float f4 = (endColor >> 24 & 0xFF) / 255.0F;
/* 271 */     float f5 = (endColor >> 16 & 0xFF) / 255.0F;
/* 272 */     float f6 = (endColor >> 8 & 0xFF) / 255.0F;
/* 273 */     float f7 = (endColor & 0xFF) / 255.0F;
/* 274 */     GlStateManager.func_179090_x();
/* 275 */     GlStateManager.func_179147_l();
/* 276 */     GlStateManager.func_179118_c();
/* 277 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 278 */     GlStateManager.func_179103_j(7425);
/* 279 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 280 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 281 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/* 282 */     bufferbuilder.func_181662_b(givenWidth, givenPosY, givenZLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
/* 283 */     bufferbuilder.func_181662_b(givenPosX, givenPosY, givenZLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
/* 284 */     bufferbuilder.func_181662_b(givenPosX, givenHeight, givenZLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
/* 285 */     bufferbuilder.func_181662_b(givenWidth, givenHeight, givenZLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
/* 286 */     tessellator.func_78381_a();
/* 287 */     GlStateManager.func_179103_j(7424);
/* 288 */     GlStateManager.func_179084_k();
/* 289 */     GlStateManager.func_179141_d();
/* 290 */     GlStateManager.func_179098_w();
/*     */     
/* 292 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderPositionedImageNoDepth(ResourceLocation par1, double par2, double par3, double par4, float par5, float width, float height) {
/* 296 */     GL11.glPushMatrix();
/*     */     
/* 298 */     GL11.glDepthMask(false);
/* 299 */     GL11.glDisable(2929);
/* 300 */     renderPositionedImage(par1, par2, par3, par4, par5, width, height);
/* 301 */     GL11.glDepthMask(true);
/* 302 */     GL11.glEnable(2929);
/*     */     
/* 304 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderPositionedImage(ResourceLocation par1, double par2, double par3, double par4, float par5, float width, float height) {
/* 308 */     Minecraft mc = Minecraft.func_71410_x();
/* 309 */     EntityPlayerSP entityPlayerSP = mc.field_71439_g;
/*     */     
/* 311 */     GL11.glPushMatrix();
/*     */     
/* 313 */     GL11.glTranslated(par2, par3, par4);
/* 314 */     GL11.glTranslated(-(mc.func_175598_ae()).field_78730_l, -(mc.func_175598_ae()).field_78731_m, -(mc.func_175598_ae()).field_78728_n);
/* 315 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*     */     
/* 317 */     GL11.glRotatef(-((EntityPlayer)entityPlayerSP).field_70177_z, 0.0F, 1.0F, 0.0F);
/* 318 */     GL11.glRotatef(((EntityPlayer)entityPlayerSP).field_70125_A, 1.0F, 0.0F, 0.0F);
/*     */     
/* 320 */     GL11.glScalef(-0.03F, -0.03F, 0.03F);
/* 321 */     GL11.glEnable(3042);
/* 322 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 324 */     renderImage((-width / 2.0F), (-height / 2.0F), par1, width, height);
/*     */     
/* 326 */     GL11.glDisable(3042);
/* 327 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderPositionedTextScaled(String givenText, double par2, double par3, double par4, float par5, int givenColor) {
/* 331 */     Minecraft mc = Minecraft.func_71410_x();
/* 332 */     EntityPlayerSP entityPlayerSP = mc.field_71439_g;
/*     */     
/* 334 */     GL11.glPushMatrix();
/*     */     
/* 336 */     GL11.glTranslated(par2, par3, par4);
/* 337 */     GL11.glTranslated(-(mc.func_175598_ae()).field_78730_l, -(mc.func_175598_ae()).field_78731_m, -(mc.func_175598_ae()).field_78728_n);
/* 338 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*     */     
/* 340 */     GL11.glRotatef(-((EntityPlayer)entityPlayerSP).field_70177_z, 0.0F, 1.0F, 0.0F);
/* 341 */     GL11.glRotatef(((EntityPlayer)entityPlayerSP).field_70125_A, 1.0F, 0.0F, 0.0F);
/*     */     
/* 343 */     GL11.glScalef(-0.03F, -0.03F, 0.03F);
/* 344 */     GL11.glEnable(3042);
/* 345 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 347 */     renderCenteredTextScaled(givenText, 0, 0, givenColor, par5);
/*     */     
/* 349 */     GL11.glDisable(3042);
/* 350 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderImageCenteredScaled(double givenX, double givenY, ResourceLocation givenTexture, double givenWidth, double givenHeight, double givenScale) {
/* 354 */     GL11.glPushMatrix();
/* 355 */     GL11.glTranslated(givenX, givenY, 0.0D);
/* 356 */     GL11.glScaled(givenScale, givenScale, givenScale);
/* 357 */     renderImageCentered(givenX - givenWidth / 2.0D, givenY, givenTexture, givenWidth, givenHeight);
/* 358 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderImageCentered(double givenX, double givenY, ResourceLocation givenTexture, double givenWidth, double givenHeight) {
/* 362 */     GL11.glPushMatrix();
/* 363 */     renderImage(givenX - givenWidth / 2.0D, givenY, givenTexture, givenWidth, givenHeight);
/* 364 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderImage(double x, double y, ResourceLocation image, double width, double height) {
/* 369 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*     */     
/* 371 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(image);
/*     */     
/* 373 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 374 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*     */     
/* 376 */     GL11.glEnable(3042);
/* 377 */     GL11.glEnable(2832);
/* 378 */     GL11.glHint(3153, 4353);
/*     */     
/* 380 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*     */     
/* 382 */     bufferbuilder.func_181662_b(x, y + height, 0.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/* 383 */     bufferbuilder.func_181662_b(x + width, y + height, 0.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/* 384 */     bufferbuilder.func_181662_b(x + width, y, 0.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/* 385 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/*     */     
/* 387 */     tessellator.func_78381_a();
/*     */     
/* 389 */     GL11.glDisable(3042);
/* 390 */     GL11.glDisable(2832);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderImageSpecial(double x, double y, double i, double j, double k, double l, float alpha, ResourceLocation image) {
/* 395 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
/*     */     
/* 397 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(image);
/*     */     
/* 399 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 400 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*     */     
/* 402 */     GL11.glEnable(3042);
/* 403 */     GL11.glEnable(2832);
/* 404 */     GL11.glHint(3153, 4353);
/*     */     
/* 406 */     double w = i;
/* 407 */     double h = j;
/* 408 */     double we = k;
/* 409 */     double he = l;
/*     */     
/* 411 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*     */     
/* 413 */     bufferbuilder.func_181662_b(x + w, y + he, -90.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/* 414 */     bufferbuilder.func_181662_b(x + we, y + he, -90.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/* 415 */     bufferbuilder.func_181662_b(x + we, y + h, -90.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/* 416 */     bufferbuilder.func_181662_b(x + w, y + h, -90.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/*     */     
/* 418 */     tessellator.func_78381_a();
/*     */     
/* 420 */     GL11.glDisable(3042);
/* 421 */     GL11.glDisable(2832);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderImageAlpha(double x, double y, ResourceLocation image, double width, double height, double alpha) {
/* 426 */     GL11.glColor4d(255.0D, 255.0D, 255.0D, alpha);
/*     */     
/* 428 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(image);
/*     */     
/* 430 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 431 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*     */     
/* 433 */     GL11.glEnable(3042);
/* 434 */     GL11.glEnable(2832);
/* 435 */     GL11.glHint(3153, 4353);
/*     */     
/* 437 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*     */     
/* 439 */     bufferbuilder.func_181662_b(x, y + height, 0.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/* 440 */     bufferbuilder.func_181662_b(x + width, y + height, 0.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/* 441 */     bufferbuilder.func_181662_b(x + width, y, 0.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/* 442 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/*     */     
/* 444 */     tessellator.func_78381_a();
/*     */     
/* 446 */     GL11.glDisable(3042);
/* 447 */     GL11.glDisable(2832);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderTranspImage(double x, double y, ResourceLocation image, double width, double height) {
/* 452 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*     */     
/* 454 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(image);
/*     */     
/* 456 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 457 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*     */     
/* 459 */     GL11.glEnable(3042);
/* 460 */     GL11.glEnable(2832);
/* 461 */     GL11.glHint(3153, 4353);
/*     */     
/* 463 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*     */     
/* 465 */     bufferbuilder.func_181662_b(x, y + height, 0.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/* 466 */     bufferbuilder.func_181662_b(x + width, y + height, 0.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/* 467 */     bufferbuilder.func_181662_b(x + width, y, 0.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/* 468 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/*     */     
/* 470 */     tessellator.func_78381_a();
/*     */     
/* 472 */     GL11.glDisable(3042);
/* 473 */     GL11.glDisable(2832);
/*     */   }
/*     */   
/*     */   public static void renderCenteredTextScaledWithOutlineFade(String text, int posX, int posY, double par4, int givenColor, double givenFade) {
/* 477 */     Minecraft mc = Minecraft.func_71410_x();
/* 478 */     double width = (mc.field_71466_p.func_78256_a(text) / 2) * par4;
/* 479 */     GL11.glPushMatrix();
/* 480 */     GL11.glTranslated(posX - width, posY, 0.0D);
/* 481 */     GL11.glScaled(par4, par4, par4);
/* 482 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, -1, -1, 0);
/* 483 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, 1, -1, 0);
/* 484 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, -1, 1, 0);
/* 485 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, 1, 1, 0);
/* 486 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, 0, -1, 0);
/* 487 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, -1, 0, 0);
/* 488 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, 1, 0, 0);
/* 489 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, 0, 1, 0);
/* 490 */     mc.field_71466_p.func_78276_b(text, 0, 0, givenColor);
/* 491 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderCenteredTextScaledWithOutline(String text, int posX, int posY, double par4, int givenColor) {
/* 495 */     Minecraft mc = Minecraft.func_71410_x();
/* 496 */     double width = (mc.field_71466_p.func_78256_a(text) / 2) * par4;
/* 497 */     GL11.glPushMatrix();
/* 498 */     GL11.glTranslated(posX - width, posY, 0.0D);
/* 499 */     GL11.glScaled(par4, par4, par4);
/* 500 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, -1, -1, 0);
/* 501 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, 1, -1, 0);
/* 502 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, -1, 1, 0);
/* 503 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, 1, 1, 0);
/* 504 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, 0, -1, 0);
/* 505 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, -1, 0, 0);
/* 506 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, 1, 0, 0);
/* 507 */     mc.field_71466_p.func_78276_b(TextFormatting.BLACK + text, 0, 1, 0);
/* 508 */     mc.field_71466_p.func_78276_b(text, 0, 0, givenColor);
/* 509 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderPositionedTextInView(String par1, double par2, double par3, double par4, float par5) {
/* 513 */     renderPositionedTextInView(par1, par2, par3, par4, par5, 1.0F);
/*     */   }
/*     */   
/*     */   public static void renderPositionedTextInView(String par1, double par2, double par3, double par4, float par5, float alpha) {
/* 517 */     Minecraft mc = Minecraft.func_71410_x();
/* 518 */     EntityPlayerSP entityPlayerSP = mc.field_71439_g;
/* 519 */     GL11.glPushMatrix();
/* 520 */     GL11.glTranslated(par2, par3, par4);
/*     */     
/* 522 */     GL11.glTranslated(-(mc.func_175598_ae()).field_78730_l, -(mc.func_175598_ae()).field_78731_m, -(mc.func_175598_ae()).field_78728_n);
/* 523 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 524 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 525 */     int width = mc.field_71466_p.func_78256_a(par1);
/* 526 */     GL11.glRotatef(-((EntityPlayer)entityPlayerSP).field_70177_z, 0.0F, 1.0F, 0.0F);
/* 527 */     GL11.glRotatef(((EntityPlayer)entityPlayerSP).field_70125_A, 1.0F, 0.0F, 0.0F);
/* 528 */     GL11.glScalef(-0.01F, -0.01F, 0.01F);
/* 529 */     GL11.glDisable(2896);
/*     */     
/* 531 */     GL11.glDepthMask(false);
/*     */     
/* 533 */     GL11.glDisable(2929);
/* 534 */     GL11.glEnable(3042);
/* 535 */     GL11.glBlendFunc(770, 771);
/* 536 */     Color color = new Color(1.0F, 1.0F, 1.0F, alpha);
/* 537 */     FontRenderer fr = mc.field_71466_p;
/* 538 */     fr.func_78276_b(par1, -(width / 2), 0, color.getRGB());
/* 539 */     GL11.glDepthMask(true);
/* 540 */     GL11.glEnable(2929);
/* 541 */     GL11.glEnable(2896);
/* 542 */     GL11.glDisable(3042);
/* 543 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderPlayerHead(String playerName, int xPos, int yPos, String givenPlayerUUID) {
/* 547 */     ResourceLocation resourceLocation = new ResourceLocation("textures/hologram/steve.png");
/* 548 */     if (playerName.length() > 0) {
/* 549 */       getDownloadImageSkin(resourceLocation, givenPlayerUUID);
/*     */     }
/* 551 */     GL11.glPushMatrix();
/* 552 */     renderRect(xPos - 1, yPos - 1, 20, 21, 1140850688);
/* 553 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(resourceLocation);
/* 554 */     GL11.glTranslated(xPos, yPos, 30.0D);
/* 555 */     GL11.glScaled(0.75D, 0.39D, 0.0D);
/* 556 */     double scale = 0.75D;
/* 557 */     GL11.glScaled(0.75D, 0.75D, 0.75D);
/* 558 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 559 */     RenderHelper.func_74518_a();
/* 560 */     (Minecraft.func_71410_x()).field_71456_v.func_73729_b(0, 0, 32, 64, 32, 64);
/* 561 */     (Minecraft.func_71410_x()).field_71456_v.func_73729_b(0, 0, 160, 64, 32, 64);
/* 562 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void renderLine(double posX, double posY, double posZ, double posX2, double posY2, double posZ2, int givenColor, float width) {
/* 566 */     Minecraft mc = Minecraft.func_71410_x();
/* 567 */     float red = (givenColor >> 16 & 0xFF) / 255.0F;
/* 568 */     float blue = (givenColor >> 8 & 0xFF) / 255.0F;
/* 569 */     float green = (givenColor & 0xFF) / 255.0F;
/* 570 */     float alpha = (givenColor >> 24 & 0xFF) / 255.0F;
/* 571 */     double d0 = mc.field_71439_g.field_70169_q + mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q;
/* 572 */     double d2 = mc.field_71439_g.field_70167_r + mc.field_71439_g.field_70163_u - mc.field_71439_g.field_70167_r;
/* 573 */     double d3 = mc.field_71439_g.field_70166_s + mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s;
/* 574 */     GL11.glPushMatrix();
/* 575 */     GL11.glDisable(2896);
/* 576 */     GL11.glDisable(3553);
/* 577 */     GL11.glDisable(2929);
/* 578 */     GL11.glLineWidth(width);
/* 579 */     GL11.glTranslated(-d0, -d2, -d3);
/* 580 */     GL11.glColor4f(red, green, blue, alpha);
/* 581 */     GL11.glEnable(2848);
/* 582 */     GL11.glHint(3154, 4354);
/* 583 */     GL11.glBegin(3);
/* 584 */     GL11.glVertex3d(posX, posY, posZ);
/* 585 */     GL11.glVertex3d(posX2, posY2, posZ2);
/* 586 */     GL11.glEnd();
/* 587 */     GL11.glEnable(2896);
/* 588 */     GL11.glEnable(3553);
/* 589 */     GL11.glEnable(2929);
/* 590 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private static final String pad(String s) {
/* 594 */     return (s.length() == 1) ? ("0" + s) : s;
/*     */   }
/*     */   
/*     */   public static int toHex(Color color) {
/* 598 */     String alpha = pad(Integer.toHexString(color.getAlpha()));
/* 599 */     String red = pad(Integer.toHexString(color.getRed()));
/* 600 */     String green = pad(Integer.toHexString(color.getGreen()));
/* 601 */     String blue = pad(Integer.toHexString(color.getBlue()));
/* 602 */     String hex = "0x" + alpha + red + green + blue;
/* 603 */     return Integer.parseInt(hex, 16);
/*     */   }
/*     */   
/*     */   public static void renderPlayer(int x, int y, float givenScale, float givenRotation) {
/* 607 */     GL11.glPushMatrix();
/*     */     
/* 609 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String givenUUID) {
/* 613 */     TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
/* 614 */     Object object = texturemanager.func_110581_b(resourceLocationIn);
/* 615 */     if (object == null) {
/* 616 */       object = new ThreadDownloadImageData(null, String.format("https://crafatar.com/skins/%s.png", new Object[] { StringUtils.func_76338_a(givenUUID) }), new ResourceLocation("textures/hologram/steve.png"), (IImageBuffer)new ImageBufferDownload());
/* 617 */       texturemanager.func_110579_a(resourceLocationIn, (ITextureObject)object);
/*     */     } 
/* 619 */     return (ThreadDownloadImageData)object;
/*     */   }
/*     */   
/*     */   public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
/* 623 */     float f = 0.00390625F;
/* 624 */     float f1 = 0.00390625F;
/* 625 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 626 */     BufferBuilder buffer = tessellator.func_178180_c();
/* 627 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 628 */     buffer.func_181662_b((x + 0), (y + height), 0.0D).func_187315_a(((textureX + 0) * f), ((textureY + height) * f1)).func_181675_d();
/* 629 */     buffer.func_181662_b((x + width), (y + height), 0.0D).func_187315_a(((textureX + width) * f), ((textureY + height) * f1)).func_181675_d();
/* 630 */     buffer.func_181662_b((x + width), (y + 0), 0.0D).func_187315_a(((textureX + width) * f), ((textureY + 0) * f1)).func_181675_d();
/* 631 */     buffer.func_181662_b((x + 0), (y + 0), 0.0D).func_187315_a(((textureX + 0) * f), ((textureY + 0) * f1)).func_181675_d();
/* 632 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public static void renderBackgroundImage(double offsx, double offsy, int width, int height, float brightness, ResourceLocation background) {
/* 636 */     GL11.glPushMatrix();
/* 637 */     GL11.glDisable(2929);
/* 638 */     GL11.glDepthMask(false);
/* 639 */     GL11.glBlendFunc(770, 771);
/* 640 */     GL11.glColor4f(brightness, brightness, brightness, 1.0F);
/* 641 */     GL11.glDisable(3008);
/* 642 */     GL11.glEnable(3042);
/*     */     
/* 644 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(background);
/*     */     
/* 646 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 647 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*     */     
/* 649 */     GL11.glEnable(3042);
/* 650 */     GL11.glEnable(2832);
/* 651 */     GL11.glHint(3153, 4353);
/*     */     
/* 653 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*     */     
/* 655 */     bufferbuilder.func_181662_b(offsx, offsy + height, 0.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/* 656 */     bufferbuilder.func_181662_b(offsx + width, offsy + height, 0.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/* 657 */     bufferbuilder.func_181662_b(offsx + width, offsy, 0.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/* 658 */     bufferbuilder.func_181662_b(offsx, offsy, 0.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/*     */     
/* 660 */     tessellator.func_78381_a();
/*     */     
/* 662 */     GL11.glDepthMask(true);
/* 663 */     GL11.glEnable(2929);
/* 664 */     GL11.glEnable(3008);
/* 665 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 666 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static long getTime() {
/* 670 */     return Sys.getTime() * 1000L / Sys.getTimerResolution();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderMainMenuPlayer(int width, int height, float offsetx, float offsety, int par1, int par2) {}
/*     */ 
/*     */   
/*     */   public static void drawGradientRect(int left, int top, int right, int bottom, int colour1, int colour2, float fade, double zLevel) {
/* 678 */     float f = (colour1 >> 24 & 0xFF) / 255.0F * fade;
/* 679 */     float f1 = (colour1 >> 16 & 0xFF) / 255.0F;
/* 680 */     float f2 = (colour1 >> 8 & 0xFF) / 255.0F;
/* 681 */     float f3 = (colour1 & 0xFF) / 255.0F;
/* 682 */     float f4 = (colour2 >> 24 & 0xFF) / 255.0F * fade;
/* 683 */     float f5 = (colour2 >> 16 & 0xFF) / 255.0F;
/* 684 */     float f6 = (colour2 >> 8 & 0xFF) / 255.0F;
/* 685 */     float f7 = (colour2 & 0xFF) / 255.0F;
/* 686 */     GlStateManager.func_179090_x();
/* 687 */     GlStateManager.func_179147_l();
/* 688 */     GlStateManager.func_179118_c();
/* 689 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 690 */     GlStateManager.func_179103_j(7425);
/* 691 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 692 */     BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 693 */     vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/* 694 */     vertexbuffer.func_181662_b(right, top, zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
/* 695 */     vertexbuffer.func_181662_b(left, top, zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
/* 696 */     vertexbuffer.func_181662_b(left, bottom, zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
/* 697 */     vertexbuffer.func_181662_b(right, bottom, zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
/* 698 */     tessellator.func_78381_a();
/* 699 */     GlStateManager.func_179103_j(7424);
/* 700 */     GlStateManager.func_179084_k();
/* 701 */     GlStateManager.func_179141_d();
/* 702 */     GlStateManager.func_179098_w();
/*     */   }
/*     */   
/*     */   public static String formatNumber(double value) {
/* 706 */     if (value < 1000.0D) return String.valueOf(value); 
/* 707 */     if (value < 1000000.0D) return (Math.round(value) / 1000.0D) + "K"; 
/* 708 */     if (value < 1.0E9D) return (Math.round(value / 1000.0D) / 1000.0D) + "M"; 
/* 709 */     if (value < 1.0E12D) return (Math.round(value / 1000000.0D) / 1000.0D) + "B"; 
/* 710 */     return (Math.round(value / 1.0E9D) / 1000.0D) + "T";
/*     */   }
/*     */   
/*     */   public static String formatNumber(long value) {
/* 714 */     if (value < 1000L) return String.valueOf(value); 
/* 715 */     if (value < 1000000L) return (Math.round((float)value) / 1000.0D) + "K"; 
/* 716 */     if (value < 1000000000L) return (Math.round((float)(value / 1000L)) / 1000.0D) + "M"; 
/* 717 */     if (value < 1000000000000L) return (Math.round((float)(value / 1000000L)) / 1000.0D) + "G"; 
/* 718 */     if (value < 1000000000000000L) return (Math.round((float)(value / 1000000000L)) / 1000.0D) + "T"; 
/* 719 */     if (value < 1000000000000000000L) return (Math.round((float)(value / 1000000000000L)) / 1000.0D) + "P"; 
/* 720 */     if (value <= Long.MAX_VALUE) return (Math.round((float)(value / 1000000000000000L)) / 1000.0D) + "E"; 
/* 721 */     return "Something is very broken!!!!";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String addCommas(int value) {
/* 728 */     return energyValue.format(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String addCommas(long value) {
/* 735 */     return energyValue.format(value);
/*     */   }
/*     */   
/*     */   public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
/* 739 */     return (mouseX >= x && mouseX < x + xSize && mouseY >= y && mouseY < y + ySize);
/*     */   }
/*     */   
/*     */   public static void drawHoveringText(List list, int x, int y, FontRenderer font, int guiWidth, int guiHeight) {
/* 743 */     GuiUtils.drawHoveringText(list, x, y, guiWidth, guiHeight, -1, font);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawColouredRect(int posX, int posY, int xSize, int ySize, int colour) {
/* 748 */     drawGradientRect(posX, posY, posX + xSize, posY + ySize, colour, colour, 1.0F, 0.0D);
/*     */   }
/*     */   
/*     */   public static void drawTextInWorld(Minecraft mc, RenderManager renderManager, String text, double x, double y, double z, int color) {
/* 752 */     drawTextInWorld(mc, renderManager, text, x, y, z, color, 2130706432, 1.0F);
/*     */   }
/*     */   
/*     */   public static void drawTextInWorld(Minecraft mc, RenderManager renderManager, String text, double x, double y, double z, int color, int backgroundColour, float scale) {
/* 756 */     int strWidth = renderManager.func_78716_a().func_78256_a(text);
/* 757 */     int strCenter = strWidth / 2;
/* 758 */     int yOffset = -4;
/*     */     
/* 760 */     GL11.glPushMatrix();
/* 761 */     GlStateManager.func_179137_b(x, y, z);
/* 762 */     GlStateManager.func_187432_a(0.0F, 1.0F, 0.0F);
/* 763 */     GlStateManager.func_179114_b(-renderManager.field_78735_i, 0.0F, 1.0F, 0.0F);
/* 764 */     GlStateManager.func_179114_b(renderManager.field_78732_j, 1.0F, 0.0F, 0.0F);
/* 765 */     GlStateManager.func_179152_a(-0.025F * scale, -0.025F * scale, 0.025F * scale);
/* 766 */     GlStateManager.func_179140_f();
/* 767 */     GlStateManager.func_179132_a(false);
/*     */     
/* 769 */     GlStateManager.func_179147_l();
/* 770 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*     */     
/* 772 */     GlStateManager.func_179094_E();
/* 773 */     drawColouredRect(-strCenter - 1, yOffset - 1, strWidth + 1, 9, backgroundColour);
/* 774 */     mc.field_71466_p.func_78276_b(text, -strCenter, yOffset, color);
/* 775 */     GlStateManager.func_179121_F();
/*     */     
/* 777 */     GlStateManager.func_179145_e();
/* 778 */     GlStateManager.func_179084_k();
/* 779 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 780 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public static void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking) {
/* 784 */     GlStateManager.func_179094_E();
/* 785 */     GlStateManager.func_179109_b(x, y, z);
/* 786 */     GlStateManager.func_187432_a(0.0F, 1.0F, 0.0F);
/* 787 */     GlStateManager.func_179114_b(-viewerYaw, 0.0F, 1.0F, 0.0F);
/* 788 */     GlStateManager.func_179114_b((isThirdPersonFrontal ? -1 : true) * viewerPitch, 1.0F, 0.0F, 0.0F);
/* 789 */     GlStateManager.func_179152_a(-0.025F, -0.025F, 0.025F);
/* 790 */     GlStateManager.func_179140_f();
/* 791 */     GlStateManager.func_179132_a(false);
/*     */     
/* 793 */     if (!isSneaking) {
/* 794 */       GlStateManager.func_179097_i();
/*     */     }
/*     */     
/* 797 */     GlStateManager.func_179147_l();
/* 798 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 799 */     int i = fontRendererIn.func_78256_a(str) / 2;
/* 800 */     GlStateManager.func_179090_x();
/* 801 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 802 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 803 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/* 804 */     bufferbuilder.func_181662_b((-i - 1), (-1 + verticalShift), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
/* 805 */     bufferbuilder.func_181662_b((-i - 1), (8 + verticalShift), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
/* 806 */     bufferbuilder.func_181662_b((i + 1), (8 + verticalShift), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
/* 807 */     bufferbuilder.func_181662_b((i + 1), (-1 + verticalShift), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
/* 808 */     tessellator.func_78381_a();
/* 809 */     GlStateManager.func_179098_w();
/*     */     
/* 811 */     if (!isSneaking) {
/* 812 */       fontRendererIn.func_78276_b(str, -fontRendererIn.func_78256_a(str) / 2, verticalShift, 553648127);
/* 813 */       GlStateManager.func_179126_j();
/*     */     } 
/*     */     
/* 816 */     GlStateManager.func_179132_a(true);
/* 817 */     fontRendererIn.func_78276_b(str, -fontRendererIn.func_78256_a(str) / 2, verticalShift, isSneaking ? 553648127 : -1);
/* 818 */     GlStateManager.func_179145_e();
/* 819 */     GlStateManager.func_179084_k();
/* 820 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 821 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderSmoke(ResourceLocation par1, double par2, double par3, double par4, float par5, int width, int height, String color, double alpha) {
/* 827 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*     */     
/* 829 */     GL11.glPushMatrix();
/* 830 */     GL11.glEnable(3042);
/*     */     
/* 832 */     float scale2 = 0.02F;
/*     */     
/* 834 */     float d0 = (float)(((EntityPlayer)entityPlayerSP).field_70142_S + (((EntityPlayer)entityPlayerSP).field_70165_t - ((EntityPlayer)entityPlayerSP).field_70142_S) * par5);
/* 835 */     float d1 = (float)(((EntityPlayer)entityPlayerSP).field_70137_T + (((EntityPlayer)entityPlayerSP).field_70163_u - ((EntityPlayer)entityPlayerSP).field_70137_T) * par5);
/* 836 */     float d2 = (float)(((EntityPlayer)entityPlayerSP).field_70136_U + (((EntityPlayer)entityPlayerSP).field_70161_v - ((EntityPlayer)entityPlayerSP).field_70136_U) * par5);
/*     */     
/* 838 */     GL11.glTranslatef((float)par2, (float)par3, (float)par4);
/* 839 */     GL11.glTranslatef(-d0, -d1, -d2);
/* 840 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*     */ 
/*     */     
/* 843 */     GL11.glScalef(-scale2, -scale2, scale2);
/* 844 */     GL11.glDepthMask(false);
/*     */     
/* 846 */     float realTick = RenderParameters.SMOOTH_SWING;
/*     */     
/* 848 */     for (int i1 = 0; i1 < 4; i1++) {
/*     */       
/* 850 */       float val = (float)(Math.sin((realTick / 100.0F)) * 3.0D);
/*     */       
/* 852 */       if (i1 % 2 == 0) {
/* 853 */         val = -val;
/*     */       }
/*     */       
/* 856 */       for (int i = 0; i < 9; i++) {
/*     */         
/* 858 */         GL11.glBlendFunc(770, 771);
/* 859 */         GL11.glEnable(3008);
/*     */         
/* 861 */         renderImageAlpha((-width / 2), (-height / 2), par1, width, height, alpha);
/*     */         
/* 863 */         GL11.glDisable(3008);
/* 864 */         GL11.glRotatef(64.0F, 0.0F, 1.0F, 0.0F);
/* 865 */         GL11.glRotatef(val, 1.0F, 0.0F, 0.0F);
/*     */       } 
/*     */       
/* 868 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*     */     } 
/*     */     
/* 871 */     GL11.glDepthMask(true);
/* 872 */     GL11.glDisable(3042);
/* 873 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderPositionedImageInViewWithDepth(ResourceLocation img, double x, double y, double z, float width, float height, float givenAlpha) {
/* 878 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*     */     
/* 880 */     GL11.glPushMatrix();
/*     */     
/* 882 */     GL11.glTranslated(x, y, z);
/* 883 */     GL11.glTranslated(-(Minecraft.func_71410_x().func_175598_ae()).field_78730_l, -(Minecraft.func_71410_x().func_175598_ae()).field_78731_m, -(Minecraft.func_71410_x().func_175598_ae()).field_78728_n);
/* 884 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*     */ 
/*     */     
/* 887 */     GL11.glRotatef(-((EntityPlayer)entityPlayerSP).field_70177_z, 0.0F, 1.0F, 0.0F);
/* 888 */     GL11.glRotatef(((EntityPlayer)entityPlayerSP).field_70125_A, 1.0F, 0.0F, 0.0F);
/*     */ 
/*     */     
/* 891 */     GL11.glScalef(-0.03F, -0.03F, 0.03F);
/*     */     
/* 893 */     renderImageAlpha((-width / 2.0F), (-height / 2.0F), img, width, height, givenAlpha);
/* 894 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderRectFlash(double x, double y, double width, double height) {
/* 899 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 900 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*     */     
/* 902 */     GL11.glEnable(3042);
/* 903 */     GL11.glEnable(2832);
/* 904 */     GL11.glHint(3153, 4353);
/*     */     
/* 906 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*     */     
/* 908 */     bufferbuilder.func_181662_b(x, y + height, 0.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/* 909 */     bufferbuilder.func_181662_b(x + width, y + height, 0.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/* 910 */     bufferbuilder.func_181662_b(x + width, y, 0.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/* 911 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/*     */     
/* 913 */     tessellator.func_78381_a();
/*     */     
/* 915 */     GL11.glDisable(3042);
/* 916 */     GL11.glDisable(2832);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawTexturedRect(double x, double y, double width, double height) {
/* 921 */     drawTexturedRect((float)x, (float)y, (float)width, (float)height);
/*     */   }
/*     */   
/*     */   public static void drawTexturedRect(float x, float y, float width, float height) {
/* 925 */     drawTexturedRect(x, y, width, height, 0.0F, 0.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawTexturedRect(float x, float y, float width, float height, float minU, float minV, float maxU, float maxV) {
/* 930 */     float f = 0.00390625F;
/* 931 */     float f1 = 0.00390625F;
/* 932 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 933 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 934 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 935 */     bufferbuilder.func_181662_b(x, (y + height), 0.0D).func_187315_a(minU, maxV).func_181675_d();
/* 936 */     bufferbuilder.func_181662_b((x + width), (y + height), 0.0D).func_187315_a(maxU, maxV).func_181675_d();
/* 937 */     bufferbuilder.func_181662_b((x + width), y, 0.0D).func_187315_a(maxU, minV).func_181675_d();
/* 938 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_187315_a(minU, minV).func_181675_d();
/* 939 */     tessellator.func_78381_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfar\\utility\RenderHelperMW.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */