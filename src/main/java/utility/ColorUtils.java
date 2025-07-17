/*     */ package com.modularwarfare.utility;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.vecmath.Vector3f;
/*     */ import javax.vecmath.Vector4f;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ColorUtils
/*     */ {
/*     */   @Nonnull
/*     */   public static Vector4f toFloat(@Nonnull Color color) {
/*  21 */     float[] rgba = color.getComponents(null);
/*  22 */     return new Vector4f(rgba[0], rgba[1], rgba[2], rgba[3]);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3f toFloat(int rgb) {
/*  27 */     int r = rgb >> 16 & 0xFF;
/*  28 */     int g = rgb >> 8 & 0xFF;
/*  29 */     int b = rgb & 0xFF;
/*  30 */     return new Vector3f(r / 255.0F, g / 255.0F, b / 255.0F);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Vector4f toFloat4(int rgb) {
/*  35 */     int r = rgb >> 16 & 0xFF;
/*  36 */     int g = rgb >> 8 & 0xFF;
/*  37 */     int b = rgb & 0xFF;
/*  38 */     return new Vector4f(r / 255.0F, g / 255.0F, b / 255.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public static int[] toRGB(int rgb) {
/*  42 */     int r = rgb >> 16 & 0xFF;
/*  43 */     int g = rgb >> 8 & 0xFF;
/*  44 */     int b = rgb & 0xFF;
/*     */     
/*  46 */     return new int[] { r, g, b };
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getRGB(@Nullable Color color) {
/*  51 */     return (color == null) ? 0 : getRGB(color.getRed(), color.getGreen(), color.getBlue());
/*     */   }
/*     */   
/*     */   public static int getRGBA(@Nullable Color color) {
/*  55 */     return (color == null) ? 0 : getRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
/*     */   }
/*     */   
/*     */   public static int getARGB(@Nullable Color color) {
/*  59 */     return (color == null) ? 0 : getRGBA(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
/*     */   }
/*     */   
/*     */   public static int getRGB(@Nonnull Vector3f rgb) {
/*  63 */     return getRGB(rgb.x, rgb.y, rgb.z);
/*     */   }
/*     */   
/*     */   public static int getRGB(float r, float g, float b) {
/*  67 */     return getRGB((int)(r * 255.0F), (int)(g * 255.0F), (int)(b * 255.0F));
/*     */   }
/*     */   
/*     */   public static int getRGBA(@Nonnull Vector4f col) {
/*  71 */     return getRGBA(col.x, col.y, col.z, col.w);
/*     */   }
/*     */   
/*     */   public static int getRGBA(float r, float g, float b, float a) {
/*  75 */     return getRGBA((int)(r * 255.0F), (int)(g * 255.0F), (int)(b * 255.0F), (int)(a * 255.0F));
/*     */   }
/*     */   
/*     */   public static int getARGB(float r, float g, float b, float a) {
/*  79 */     return getARGB((int)(a * 255.0F), (int)(r * 255.0F), (int)(g * 255.0F), (int)(b * 255.0F));
/*     */   }
/*     */   
/*     */   public static int getRGB(int r, int g, int b) {
/*  83 */     return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
/*     */   }
/*     */   
/*     */   public static int getARGB(int r, int g, int b, int a) {
/*  87 */     return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
/*     */   }
/*     */   
/*     */   public static int getRGBA(int r, int g, int b, int a) {
/*  91 */     return (r & 0xFF) << 24 | (g & 0xFF) << 16 | (b & 0xFF) << 8 | a & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setGLColorFromInt(int color) {
/* 101 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 102 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 103 */     float blue = (color & 0xFF) / 255.0F;
/* 104 */     GlStateManager.func_179131_c(red, green, blue, 1.0F);
/*     */   }
/*     */   
/*     */   public static int toHex(int r, int g, int b) {
/* 108 */     int hex = 0;
/* 109 */     hex |= r << 16;
/* 110 */     hex |= g << 8;
/* 111 */     hex |= b;
/* 112 */     return hex;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfar\\utility\ColorUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */