/*     */ package com.modularwarfare.client.gui.api;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiUtils
/*     */ {
/*     */   public static void renderText(String text, int givenX, int givenY, int color) {
/*  34 */     GL11.glPushMatrix();
/*  35 */     Minecraft mc = Minecraft.func_71410_x();
/*  36 */     mc.field_71466_p.func_78276_b(text, givenX, givenY, color);
/*  37 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderTextWithShadow(String text, int givenX, int givenY, int color) {
/*  49 */     GL11.glPushMatrix();
/*  50 */     Minecraft mc = Minecraft.func_71410_x();
/*  51 */     mc.field_71466_p.func_175063_a(text, givenX, givenY, color);
/*  52 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderCenteredText(String text, int givenX, int givenY, int color) {
/*  64 */     GL11.glPushMatrix();
/*  65 */     Minecraft mc = Minecraft.func_71410_x();
/*  66 */     renderText(text, givenX - mc.field_71466_p.func_78256_a(text) / 2, givenY, color);
/*  67 */     GL11.glPopMatrix();
/*     */   }
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
/*     */   public static void renderTextScaled(String text, int givenX, int givenY, int color, double givenScale) {
/*  81 */     GL11.glPushMatrix();
/*  82 */     GL11.glTranslated(givenX, givenY, 0.0D);
/*  83 */     GL11.glScaled(givenScale, givenScale, givenScale);
/*  84 */     renderText(text, 0, 0, color);
/*  85 */     GL11.glPopMatrix();
/*     */   }
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
/*     */   public static void renderCenteredTextScaled(String text, int givenX, int givenY, int color, double givenScale) {
/* 100 */     GL11.glPushMatrix();
/* 101 */     GL11.glTranslated(givenX, givenY, 0.0D);
/* 102 */     GL11.glScaled(givenScale, givenScale, givenScale);
/* 103 */     renderCenteredText(text, 0, 0, color);
/* 104 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderCenteredTextScaledWithOutline(String text, int givenX, int givenY, int color, int givenOutlineColor, double givenScale) {
/* 110 */     GL11.glPushMatrix();
/* 111 */     GL11.glTranslated(givenX, givenY, 0.0D);
/* 112 */     GL11.glScaled(givenScale, givenScale, givenScale);
/* 113 */     renderCenteredTextWithOutline(text, 0, 0, color, givenOutlineColor);
/* 114 */     GL11.glPopMatrix();
/*     */   }
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
/*     */   public static void renderTextWithOutline(String text, int x, int y, int color, int outlineColor) {
/* 129 */     renderText(text, x - 1, y + 1, outlineColor);
/* 130 */     renderText(text, x, y + 1, outlineColor);
/* 131 */     renderText(text, x + 1, y + 1, outlineColor);
/* 132 */     renderText(text, x - 1, y, outlineColor);
/* 133 */     renderText(text, x + 1, y, outlineColor);
/* 134 */     renderText(text, x - 1, y - 1, outlineColor);
/* 135 */     renderText(text, x, y - 1, outlineColor);
/* 136 */     renderText(text, x + 1, y - 1, outlineColor);
/*     */     
/* 138 */     renderText(text, x, y, color);
/*     */   }
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
/*     */   public static void renderTextScaledWithOutline(String text, int x, int y, int color, int outlineColor, double givenScale) {
/* 154 */     renderTextScaled(text, x - 1, y + 1, outlineColor, givenScale);
/* 155 */     renderTextScaled(text, x, y + 1, outlineColor, givenScale);
/* 156 */     renderTextScaled(text, x + 1, y + 1, outlineColor, givenScale);
/* 157 */     renderTextScaled(text, x - 1, y, outlineColor, givenScale);
/* 158 */     renderTextScaled(text, x + 1, y, outlineColor, givenScale);
/* 159 */     renderTextScaled(text, x - 1, y - 1, outlineColor, givenScale);
/* 160 */     renderTextScaled(text, x, y - 1, outlineColor, givenScale);
/* 161 */     renderTextScaled(text, x + 1, y - 1, outlineColor, givenScale);
/*     */     
/* 163 */     renderTextScaled(text, x, y, color, givenScale);
/*     */   }
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
/*     */   public static void renderCenteredTextWithOutline(String text, int x, int y, int color, int outlineColor) {
/* 177 */     Minecraft mc = Minecraft.func_71410_x();
/* 178 */     FontRenderer fr = mc.field_71466_p;
/*     */     
/* 180 */     renderText(text, x - 1 - fr.func_78256_a(text) / 2, y + 1, outlineColor);
/* 181 */     renderText(text, x - fr.func_78256_a(text) / 2, y + 1, outlineColor);
/* 182 */     renderText(text, x + 1 - fr.func_78256_a(text) / 2, y + 1, outlineColor);
/* 183 */     renderText(text, x - 1 - fr.func_78256_a(text) / 2, y, outlineColor);
/* 184 */     renderText(text, x + 1 - fr.func_78256_a(text) / 2, y, outlineColor);
/* 185 */     renderText(text, x - 1 - fr.func_78256_a(text) / 2, y - 1, outlineColor);
/* 186 */     renderText(text, x - fr.func_78256_a(text) / 2, y - 1, outlineColor);
/* 187 */     renderText(text, x + 1 - fr.func_78256_a(text) / 2, y - 1, outlineColor);
/*     */     
/* 189 */     renderText(text, x - fr.func_78256_a(text) / 2, y, color);
/*     */   }
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
/*     */   public static void renderRect(int givenX, int givenY, int givenWidth, int givenHeight, int givenColor) {
/* 203 */     givenWidth = givenX + givenWidth;
/* 204 */     givenHeight = givenY + givenHeight;
/*     */     
/* 206 */     if (givenX < givenWidth) {
/* 207 */       int i = givenX;
/* 208 */       givenX = givenWidth;
/* 209 */       givenWidth = i;
/*     */     } 
/*     */     
/* 212 */     if (givenY < givenHeight) {
/* 213 */       int j = givenY;
/* 214 */       givenY = givenHeight;
/* 215 */       givenHeight = j;
/*     */     } 
/*     */     
/* 218 */     float f3 = (givenColor >> 24 & 0xFF) / 255.0F;
/*     */     
/* 220 */     float f = (givenColor >> 16 & 0xFF) / 255.0F;
/* 221 */     float f1 = (givenColor >> 8 & 0xFF) / 255.0F;
/* 222 */     float f2 = (givenColor & 0xFF) / 255.0F;
/*     */     
/* 224 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 225 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 226 */     GlStateManager.func_179147_l();
/* 227 */     GlStateManager.func_179118_c();
/* 228 */     GlStateManager.func_179090_x();
/* 229 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/* 230 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/* 231 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 232 */     bufferbuilder.func_181662_b(givenX, givenHeight, 0.0D).func_181675_d();
/* 233 */     bufferbuilder.func_181662_b(givenWidth, givenHeight, 0.0D).func_181675_d();
/* 234 */     bufferbuilder.func_181662_b(givenWidth, givenY, 0.0D).func_181675_d();
/* 235 */     bufferbuilder.func_181662_b(givenX, givenY, 0.0D).func_181675_d();
/* 236 */     tessellator.func_78381_a();
/* 237 */     GlStateManager.func_179098_w();
/* 238 */     GlStateManager.func_179141_d();
/* 239 */     GlStateManager.func_179084_k();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderRect(int givenX, int givenY, int givenWidth, int givenHeight, int givenColor, float givenAlpha) {
/* 245 */     givenWidth = givenX + givenWidth;
/* 246 */     givenHeight = givenY + givenHeight;
/*     */     
/* 248 */     if (givenX < givenWidth) {
/* 249 */       int i = givenX;
/* 250 */       givenX = givenWidth;
/* 251 */       givenWidth = i;
/*     */     } 
/*     */     
/* 254 */     if (givenY < givenHeight) {
/* 255 */       int j = givenY;
/* 256 */       givenY = givenHeight;
/* 257 */       givenHeight = j;
/*     */     } 
/*     */     
/* 260 */     float f3 = givenAlpha;
/*     */     
/* 262 */     float f = (givenColor >> 16 & 0xFF) / 255.0F;
/* 263 */     float f1 = (givenColor >> 8 & 0xFF) / 255.0F;
/* 264 */     float f2 = (givenColor & 0xFF) / 255.0F;
/*     */     
/* 266 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 267 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 268 */     GlStateManager.func_179147_l();
/* 269 */     GlStateManager.func_179090_x();
/* 270 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/* 271 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/* 272 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 273 */     bufferbuilder.func_181662_b(givenX, givenHeight, 0.0D).func_181675_d();
/* 274 */     bufferbuilder.func_181662_b(givenWidth, givenHeight, 0.0D).func_181675_d();
/* 275 */     bufferbuilder.func_181662_b(givenWidth, givenY, 0.0D).func_181675_d();
/* 276 */     bufferbuilder.func_181662_b(givenX, givenY, 0.0D).func_181675_d();
/* 277 */     tessellator.func_78381_a();
/* 278 */     GlStateManager.func_179098_w();
/* 279 */     GlStateManager.func_179084_k();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderRectWithFade(int givenX, int givenY, int givenWidth, int givenHeight, int givenColor, float givenFade) {
/* 285 */     givenWidth = givenX + givenWidth;
/* 286 */     givenHeight = givenY + givenHeight;
/*     */     
/* 288 */     if (givenX < givenWidth) {
/* 289 */       int i = givenX;
/* 290 */       givenX = givenWidth;
/* 291 */       givenWidth = i;
/*     */     } 
/*     */     
/* 294 */     if (givenY < givenHeight) {
/* 295 */       int j = givenY;
/* 296 */       givenY = givenHeight;
/* 297 */       givenHeight = j;
/*     */     } 
/*     */     
/* 300 */     float f3 = (givenColor >> 24 & 0xFF) / 255.0F;
/* 301 */     float f = (givenColor >> 16 & 0xFF) / 255.0F;
/* 302 */     float f1 = (givenColor >> 8 & 0xFF) / 255.0F;
/* 303 */     float f2 = (givenColor & 0xFF) / 255.0F;
/* 304 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 305 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 306 */     GlStateManager.func_179147_l();
/* 307 */     GlStateManager.func_179090_x();
/* 308 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/* 309 */     GlStateManager.func_179131_c(f, f1, f2, givenFade);
/* 310 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 311 */     bufferbuilder.func_181662_b(givenX, givenHeight, 0.0D).func_181675_d();
/* 312 */     bufferbuilder.func_181662_b(givenWidth, givenHeight, 0.0D).func_181675_d();
/* 313 */     bufferbuilder.func_181662_b(givenWidth, givenY, 0.0D).func_181675_d();
/* 314 */     bufferbuilder.func_181662_b(givenX, givenY, 0.0D).func_181675_d();
/* 315 */     tessellator.func_78381_a();
/* 316 */     GlStateManager.func_179098_w();
/* 317 */     GlStateManager.func_179084_k();
/*     */   }
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
/*     */   public static void renderRectWithOutline(int givenX, int givenY, int givenWidth, int givenHeight, int givenColor, int givenOutlineColor, int outlineThickness) {
/* 333 */     renderRect(givenX - outlineThickness, givenY - outlineThickness, givenWidth + outlineThickness * 2, givenHeight + outlineThickness * 2, givenOutlineColor);
/* 334 */     renderRect(givenX, givenY, givenWidth, givenHeight, givenColor);
/*     */   }
/*     */   
/*     */   public static void renderOutline(int givenX, int givenY, int givenWidth, int givenHeight, int givenOutlineColor, int outlineThickness) {
/* 338 */     renderRect(givenX - outlineThickness, givenY - outlineThickness, givenWidth + outlineThickness * 2, outlineThickness, givenOutlineColor);
/* 339 */     renderRect(givenX - outlineThickness + 1, givenY + givenHeight - outlineThickness + 1, givenWidth + outlineThickness * 2 - 2, outlineThickness, givenOutlineColor);
/*     */     
/* 341 */     renderRect(givenX - outlineThickness, givenY - outlineThickness + 1, outlineThickness, givenHeight + outlineThickness * 2 - 1, givenOutlineColor);
/* 342 */     renderRect(givenX + givenWidth + outlineThickness - 1, givenY - outlineThickness + 1, outlineThickness, givenHeight + outlineThickness * 2 - 1, givenOutlineColor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderRectWithGradient(int givenX, int givenY, int givenWidth, int givenHeight, int startColor, int endColor, double givenZLevel) {
/* 348 */     GlStateManager.func_179094_E();
/*     */     
/* 350 */     givenWidth = givenX + givenWidth;
/* 351 */     givenHeight = givenY + givenHeight;
/*     */     
/* 353 */     float f = (startColor >> 24 & 0xFF) / 255.0F;
/* 354 */     float f1 = (startColor >> 16 & 0xFF) / 255.0F;
/* 355 */     float f2 = (startColor >> 8 & 0xFF) / 255.0F;
/* 356 */     float f3 = (startColor & 0xFF) / 255.0F;
/* 357 */     float f4 = (endColor >> 24 & 0xFF) / 255.0F;
/* 358 */     float f5 = (endColor >> 16 & 0xFF) / 255.0F;
/* 359 */     float f6 = (endColor >> 8 & 0xFF) / 255.0F;
/* 360 */     float f7 = (endColor & 0xFF) / 255.0F;
/* 361 */     GlStateManager.func_179090_x();
/* 362 */     GlStateManager.func_179147_l();
/* 363 */     GlStateManager.func_179118_c();
/* 364 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/* 365 */     GlStateManager.func_179103_j(7425);
/* 366 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 367 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 368 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/* 369 */     bufferbuilder.func_181662_b(givenWidth, givenY, givenZLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
/* 370 */     bufferbuilder.func_181662_b(givenX, givenY, givenZLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
/* 371 */     bufferbuilder.func_181662_b(givenX, givenHeight, givenZLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
/* 372 */     bufferbuilder.func_181662_b(givenWidth, givenHeight, givenZLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
/* 373 */     tessellator.func_78381_a();
/* 374 */     GlStateManager.func_179103_j(7424);
/* 375 */     GlStateManager.func_179084_k();
/* 376 */     GlStateManager.func_179141_d();
/* 377 */     GlStateManager.func_179098_w();
/*     */     
/* 379 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderRectWithGradientWithAlpha(int givenX, int givenY, int givenWidth, int givenHeight, int startColor, int endColor, double givenZLevel, float givenAlphaStart, float givenAlphaEnd) {
/* 385 */     givenWidth = givenX + givenWidth;
/* 386 */     givenHeight = givenY + givenHeight;
/*     */     
/* 388 */     float f = (startColor >> 24 & 0xFF) / 255.0F;
/* 389 */     float f1 = (startColor >> 16 & 0xFF) / 255.0F;
/* 390 */     float f2 = (startColor >> 8 & 0xFF) / 255.0F;
/* 391 */     float f3 = (startColor & 0xFF) / 255.0F;
/* 392 */     float f4 = (endColor >> 24 & 0xFF) / 255.0F;
/* 393 */     float f5 = (endColor >> 16 & 0xFF) / 255.0F;
/* 394 */     float f6 = (endColor >> 8 & 0xFF) / 255.0F;
/* 395 */     float f7 = (endColor & 0xFF) / 255.0F;
/* 396 */     GlStateManager.func_179090_x();
/* 397 */     GlStateManager.func_179147_l();
/* 398 */     GlStateManager.func_179118_c();
/* 399 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/* 400 */     GlStateManager.func_179103_j(7425);
/* 401 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 402 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 403 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/* 404 */     bufferbuilder.func_181662_b(givenWidth, givenY, givenZLevel).func_181666_a(f1, f2, f3, givenAlphaStart).func_181675_d();
/* 405 */     bufferbuilder.func_181662_b(givenX, givenY, givenZLevel).func_181666_a(f1, f2, f3, givenAlphaStart).func_181675_d();
/* 406 */     bufferbuilder.func_181662_b(givenX, givenHeight, givenZLevel).func_181666_a(f5, f6, f7, givenAlphaEnd).func_181675_d();
/* 407 */     bufferbuilder.func_181662_b(givenWidth, givenHeight, givenZLevel).func_181666_a(f5, f6, f7, givenAlphaEnd).func_181675_d();
/* 408 */     tessellator.func_78381_a();
/* 409 */     GlStateManager.func_179103_j(7424);
/* 410 */     GlStateManager.func_179084_k();
/* 411 */     GlStateManager.func_179141_d();
/* 412 */     GlStateManager.func_179098_w();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderPositionedImageNoDepth(ResourceLocation par1, double par2, double par3, double par4, float par5, float width, float height) {
/* 418 */     GL11.glPushMatrix();
/*     */     
/* 420 */     GlStateManager.func_179132_a(false);
/* 421 */     GlStateManager.func_179097_i();
/* 422 */     renderPositionedImage(par1, par2, par3, par4, par5, width, height);
/* 423 */     GlStateManager.func_179132_a(true);
/* 424 */     GlStateManager.func_179126_j();
/*     */     
/* 426 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderPositionedImage(ResourceLocation par1, double par2, double par3, double par4, float par5, float width, float height) {
/* 432 */     Minecraft mc = Minecraft.func_71410_x();
/* 433 */     EntityPlayerSP entityPlayerSP = mc.field_71439_g;
/*     */     
/* 435 */     GL11.glPushMatrix();
/*     */     
/* 437 */     GL11.glTranslated(par2, par3, par4);
/* 438 */     GL11.glTranslated(-(mc.func_175598_ae()).field_78730_l, -(mc.func_175598_ae()).field_78731_m, -(mc.func_175598_ae()).field_78728_n);
/* 439 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*     */     
/* 441 */     GL11.glRotatef(-((EntityPlayer)entityPlayerSP).field_70177_z, 0.0F, 1.0F, 0.0F);
/* 442 */     GL11.glRotatef(((EntityPlayer)entityPlayerSP).field_70125_A, 1.0F, 0.0F, 0.0F);
/*     */     
/* 444 */     GL11.glScalef(-0.03F, -0.03F, 0.03F);
/* 445 */     GL11.glEnable(3042);
/* 446 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 448 */     renderImage((-width / 2.0F), (-height / 2.0F), par1, width, height);
/*     */     
/* 450 */     GL11.glDisable(3042);
/* 451 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderPositionedTextScaled(String givenText, double par2, double par3, double par4, float par5, int givenColor) {
/* 457 */     Minecraft mc = Minecraft.func_71410_x();
/* 458 */     EntityPlayerSP entityPlayerSP = mc.field_71439_g;
/*     */     
/* 460 */     GL11.glPushMatrix();
/*     */     
/* 462 */     GL11.glTranslated(par2, par3, par4);
/* 463 */     GL11.glTranslated(-(mc.func_175598_ae()).field_78730_l, -(mc.func_175598_ae()).field_78731_m, -(mc.func_175598_ae()).field_78728_n);
/* 464 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*     */     
/* 466 */     GL11.glRotatef(-((EntityPlayer)entityPlayerSP).field_70177_z, 0.0F, 1.0F, 0.0F);
/* 467 */     GL11.glRotatef(((EntityPlayer)entityPlayerSP).field_70125_A, 1.0F, 0.0F, 0.0F);
/*     */     
/* 469 */     GL11.glScalef(-0.03F, -0.03F, 0.03F);
/* 470 */     GL11.glEnable(3042);
/* 471 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 473 */     renderCenteredTextScaled(givenText, 0, 0, givenColor, par5);
/*     */     
/* 475 */     GL11.glDisable(3042);
/* 476 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderImage(double x, double y, ResourceLocation image, double width, double height) {
/* 482 */     renderColor(16777215);
/*     */     
/* 484 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(image);
/*     */     
/* 486 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 487 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*     */     
/* 489 */     GL11.glEnable(3042);
/* 490 */     GL11.glEnable(2832);
/* 491 */     GL11.glHint(3153, 4353);
/*     */     
/* 493 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*     */     
/* 495 */     bufferbuilder.func_181662_b(x, y + height, 0.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/* 496 */     bufferbuilder.func_181662_b(x + width, y + height, 0.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/* 497 */     bufferbuilder.func_181662_b(x + width, y, 0.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/* 498 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/*     */     
/* 500 */     tessellator.func_78381_a();
/*     */     
/* 502 */     GL11.glDisable(3042);
/* 503 */     GL11.glDisable(2832);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderImageTransparent(double x, double y, ResourceLocation image, double width, double height, double alpha) {
/* 509 */     renderColor(16777215, alpha);
/*     */     
/* 511 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(image);
/*     */     
/* 513 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 514 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*     */     
/* 516 */     GL11.glEnable(3042);
/* 517 */     GL11.glEnable(2832);
/* 518 */     GL11.glHint(3153, 4353);
/*     */     
/* 520 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*     */     
/* 522 */     bufferbuilder.func_181662_b(x, y + height, 0.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/* 523 */     bufferbuilder.func_181662_b(x + width, y + height, 0.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/* 524 */     bufferbuilder.func_181662_b(x + width, y, 0.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/* 525 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/*     */     
/* 527 */     tessellator.func_78381_a();
/*     */     
/* 529 */     GL11.glDisable(3042);
/* 530 */     GL11.glDisable(2832);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderImageCentered(double givenX, double givenY, ResourceLocation givenTexture, double givenWidth, double givenHeight) {
/* 536 */     GL11.glPushMatrix();
/* 537 */     renderImage(givenX - givenWidth / 2.0D, givenY - givenHeight / 2.0D, givenTexture, givenWidth, givenHeight);
/* 538 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderImageCenteredTransparent(double givenX, double givenY, ResourceLocation givenTexture, double givenWidth, double givenHeight, double alpha) {
/* 544 */     GlStateManager.func_179094_E();
/* 545 */     renderImageTransparent(givenX - givenWidth / 2.0D, givenY - givenHeight / 2.0D, givenTexture, givenWidth, givenHeight, alpha);
/* 546 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderImageCenteredScaled(double givenX, double givenY, ResourceLocation givenTexture, double givenWidth, double givenHeight, float givenScale) {
/* 552 */     GlStateManager.func_179094_E();
/*     */     
/* 554 */     GlStateManager.func_179137_b(givenX - givenX * givenScale, givenY - givenY * givenScale, 0.0D);
/* 555 */     GlStateManager.func_179152_a(givenScale, givenScale, givenScale);
/* 556 */     renderImageCentered(givenX, givenY, givenTexture, givenWidth, givenHeight);
/*     */     
/* 558 */     GlStateManager.func_179121_F();
/*     */   }
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
/*     */   public static void renderImageTransparent(int givenX, int givenY, ResourceLocation givenTexture, int givenWidth, int givenHeight, double givenAlpha) {
/* 573 */     GL11.glPushMatrix();
/* 574 */     GL11.glColor4d(255.0D, 255.0D, 255.0D, givenAlpha);
/* 575 */     GL11.glEnable(3042);
/* 576 */     GL11.glEnable(2832);
/* 577 */     renderImage(givenX, givenY, givenTexture, givenWidth, givenHeight);
/* 578 */     GL11.glDisable(2832);
/* 579 */     GL11.glDisable(3042);
/* 580 */     GL11.glColor4d(255.0D, 255.0D, 255.0D, 255.0D);
/* 581 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getScoreboardTitle(Minecraft mc) {
/* 592 */     if (mc.field_71441_e != null && mc.field_71441_e.func_96441_U() != null) {
/*     */       
/* 594 */       ScoreObjective scoreobjective = mc.field_71441_e.func_96441_U().func_96539_a(1);
/*     */       
/* 596 */       if (scoreobjective != null)
/*     */       {
/*     */ 
/*     */         
/* 600 */         String scoreTitle = scoreobjective.func_96678_d().replace("§", "").replaceAll("[a-z]", "").replaceAll("[0-9]", "");
/*     */         
/* 602 */         return scoreTitle;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 607 */       return null;
/*     */     } 
/*     */     
/* 610 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderColor(int par1) {
/* 615 */     Color color = Color.decode("" + par1);
/* 616 */     float red = color.getRed() / 255.0F;
/* 617 */     float green = color.getGreen() / 255.0F;
/* 618 */     float blue = color.getBlue() / 255.0F;
/* 619 */     GL11.glColor3f(red, green, blue);
/*     */   }
/*     */   
/*     */   public static void renderColor(int par1, double alpha) {
/* 623 */     Color color = Color.decode("" + par1);
/* 624 */     double red = color.getRed() / 255.0D;
/* 625 */     double green = color.getGreen() / 255.0D;
/* 626 */     double blue = color.getBlue() / 255.0D;
/* 627 */     GL11.glColor4d(red, green, blue, alpha);
/*     */   }
/*     */   
/*     */   public static void renderCenteredTextWithShadow(String text, int x, int y, int color, int outlineColor) {
/* 631 */     GlStateManager.func_179094_E();
/* 632 */     Minecraft mc = Minecraft.func_71410_x();
/* 633 */     FontRenderer fr = mc.field_71466_p;
/* 634 */     renderTextWithShadow(text, x - fr.func_78256_a(text) / 2, y, color);
/* 635 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public static boolean isInBox(int x, int y, int width, int height, int checkX, int checkY) {
/* 639 */     return (checkX >= x && checkY >= y && checkX <= x + width && checkY <= y + height);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderBoundingBox(AxisAlignedBB givenBB) {
/* 644 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 645 */     BufferBuilder worldRenderer = tessellator.func_178180_c();
/*     */     
/* 647 */     worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
/* 648 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 649 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 650 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 651 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 652 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 653 */     tessellator.func_78381_a();
/* 654 */     worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
/* 655 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 656 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 657 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 658 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 659 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 660 */     tessellator.func_78381_a();
/* 661 */     worldRenderer.func_181668_a(1, DefaultVertexFormats.field_181705_e);
/* 662 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 663 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 664 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 665 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 666 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 667 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 668 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 669 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 670 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public static void renderBoundingBoxFilled(AxisAlignedBB givenBB) {
/* 674 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 675 */     BufferBuilder worldRenderer = tessellator.func_178180_c();
/*     */     
/* 677 */     worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 678 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 679 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 680 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 681 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 682 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 683 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 684 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 685 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 686 */     tessellator.func_78381_a();
/*     */     
/* 688 */     worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 689 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 690 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 691 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 692 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 693 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 694 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 695 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 696 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 697 */     tessellator.func_78381_a();
/*     */     
/* 699 */     worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 700 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 701 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 702 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 703 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 704 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 705 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 706 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 707 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 708 */     tessellator.func_78381_a();
/*     */     
/* 710 */     worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 711 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 712 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 713 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 714 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 715 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 716 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 717 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 718 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 719 */     tessellator.func_78381_a();
/*     */     
/* 721 */     worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 722 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 723 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 724 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 725 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 726 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 727 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 728 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 729 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 730 */     tessellator.func_78381_a();
/*     */     
/* 732 */     worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 733 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 734 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 735 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 736 */     worldRenderer.func_181662_b(givenBB.field_72340_a, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 737 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72339_c).func_181675_d();
/* 738 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72339_c).func_181675_d();
/* 739 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72337_e, givenBB.field_72334_f).func_181675_d();
/* 740 */     worldRenderer.func_181662_b(givenBB.field_72336_d, givenBB.field_72338_b, givenBB.field_72334_f).func_181675_d();
/* 741 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public static double getDistanceToClientCamera(double x, double y, double z) {
/* 745 */     RenderManager renderManager = Minecraft.func_71410_x().func_175598_ae();
/* 746 */     double d3 = renderManager.field_78730_l - x;
/* 747 */     double d4 = renderManager.field_78731_m - y;
/* 748 */     double d5 = renderManager.field_78728_n - z;
/* 749 */     return MathHelper.func_76133_a(d3 * d3 + d4 * d4 + d5 * d5);
/*     */   }
/*     */   
/*     */   public static BufferedImage downloadBanner(String url) {
/*     */     try {
/* 754 */       return ImageIO.read(new URL(url));
/* 755 */     } catch (IOException e) {
/* 756 */       System.out.println("Errors reading online image: '" + url + "'");
/*     */       
/* 758 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\gui\api\GuiUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */