/*    */ package com.modularwarfare.utility;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColorUtil
/*    */   extends Color
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public static ColorUtil getRainbow(int incr, int alpha) {
/* 15 */     ColorUtil color = fromHSB((float)((System.currentTimeMillis() + (incr * 200)) % 7200L) / 7200.0F, 0.5F, 1.0F);
/* 16 */     return new ColorUtil(color.getRed(), color.getBlue(), color.getGreen(), alpha);
/*    */   }
/*    */ 
/*    */   
/*    */   public ColorUtil(int rgb) {
/* 21 */     super(rgb);
/*    */   }
/*    */   
/*    */   public ColorUtil(int rgba, boolean hasalpha) {
/* 25 */     super(rgba, hasalpha);
/*    */   }
/*    */   
/*    */   public ColorUtil(int r, int g, int b) {
/* 29 */     super(r, g, b);
/*    */   }
/*    */   
/*    */   public ColorUtil(int r, int g, int b, int a) {
/* 33 */     super(r, g, b, a);
/*    */   }
/*    */   
/*    */   public ColorUtil(Color color) {
/* 37 */     super(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
/*    */   }
/*    */   
/*    */   public ColorUtil(ColorUtil color, int a) {
/* 41 */     super(color.getRed(), color.getGreen(), color.getBlue(), a);
/*    */   }
/*    */   
/*    */   public static ColorUtil fromHSB(float hue, float saturation, float brightness) {
/* 45 */     return new ColorUtil(Color.getHSBColor(hue, saturation, brightness));
/*    */   }
/*    */   
/*    */   public float getHue() {
/* 49 */     return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[0];
/*    */   }
/*    */   
/*    */   public float getSaturation() {
/* 53 */     return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[1];
/*    */   }
/*    */   
/*    */   public float getBrightness() {
/* 57 */     return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[2];
/*    */   }
/*    */   
/*    */   public void glColor() {
/* 61 */     GlStateManager.func_179131_c(getRed() / 255.0F, getGreen() / 255.0F, getBlue() / 255.0F, getAlpha() / 255.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfar\\utility\ColorUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */