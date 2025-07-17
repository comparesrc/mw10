/*     */ package com.modularwarfare.client.hud;
/*     */ import com.modularwarfare.utility.OptifineHelper;
/*     */ import com.modularwarfare.utility.RenderHelperMW;
/*     */ import java.lang.reflect.Field;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class FlashSystem {
/*  20 */   private static Minecraft mc = Minecraft.func_71410_x();
/*     */   
/*     */   public static final int quality = 1024;
/*     */   public static int FLASHED_TEX;
/*     */   public static boolean hasTookScreenshot = false;
/*     */   public static int flashValue;
/*     */   private Field renderEndNanoTime;
/*     */   
/*     */   public FlashSystem() {
/*  29 */     GL11.glPushMatrix();
/*     */     
/*  31 */     FLASHED_TEX = GL11.glGenTextures();
/*  32 */     GL11.glBindTexture(3553, FLASHED_TEX);
/*  33 */     GL11.glTexImage2D(3553, 0, 6408, 1024, 1024, 0, 6408, 5121, (ByteBuffer)null);
/*  34 */     GL11.glTexParameteri(3553, 10240, 9728);
/*  35 */     GL11.glTexParameteri(3553, 10241, 9728);
/*     */     
/*  37 */     GL11.glPopMatrix();
/*     */     try {
/*  39 */       this.renderEndNanoTime = EntityRenderer.class.getDeclaredField("renderEndNanoTime");
/*  40 */     } catch (Exception exception) {}
/*     */     
/*  42 */     if (this.renderEndNanoTime == null) {
/*  43 */       try { this.renderEndNanoTime = EntityRenderer.class.getDeclaredField("field_78534_ac"); }
/*  44 */       catch (Exception exception) {}
/*     */     }
/*  46 */     if (this.renderEndNanoTime != null) {
/*  47 */       this.renderEndNanoTime.setAccessible(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderPost(RenderGameOverlayEvent.Post event) {
/*  53 */     Minecraft mc = Minecraft.func_71410_x();
/*  54 */     if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && 
/*  55 */       flashValue != 0) {
/*  56 */       int width = Display.getWidth();
/*  57 */       int height = Display.getHeight();
/*  58 */       int x = 0;
/*  59 */       int y = 0;
/*  60 */       GL11.glPushMatrix();
/*  61 */       GL11.glScalef(0.5F, 0.5F, 0.0F);
/*     */       
/*  63 */       Tessellator tessellator = Tessellator.func_178181_a();
/*  64 */       BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*     */       
/*  66 */       GlStateManager.func_179147_l();
/*  67 */       GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  68 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, flashValue / 255.0F);
/*  69 */       GlStateManager.func_179144_i(FLASHED_TEX);
/*  70 */       bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*  71 */       bufferbuilder.func_181662_b(x, (y + height), 0.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/*  72 */       bufferbuilder.func_181662_b((x + width), (y + height), 0.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/*  73 */       bufferbuilder.func_181662_b((x + width), y, 0.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/*  74 */       bufferbuilder.func_181662_b(x, y, 0.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/*  75 */       tessellator.func_78381_a();
/*  76 */       GlStateManager.func_179084_k();
/*  77 */       GL11.glPopMatrix();
/*  78 */       RenderHelperMW.renderRectAlphaComp(0, 0, mc.field_71443_c, mc.field_71440_d, 16777215, flashValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderTick(TickEvent.RenderTickEvent event) {
/*  85 */     if (event.phase == TickEvent.Phase.START && 
/*  86 */       !hasTookScreenshot && flashValue > 0) {
/*  87 */       GL11.glPushMatrix();
/*  88 */       if (mc.field_71439_g != null && mc.field_71462_r == null && 
/*  89 */         !OptifineHelper.isShadersEnabled()) {
/*  90 */         takeScreenShot(mc, event.renderTickTime);
/*     */       }
/*     */       
/*  93 */       GL11.glPopMatrix();
/*  94 */       hasTookScreenshot = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void takeScreenShot(Minecraft mc, float partialTick) {
/* 100 */     float zoom = mc.field_71474_y.field_74334_X;
/*     */     
/* 102 */     GL11.glPushMatrix();
/*     */ 
/*     */     
/* 105 */     int width = Display.getWidth();
/* 106 */     int height = Display.getHeight();
/*     */     
/* 108 */     RenderGlobal renderBackup = mc.field_71438_f;
/*     */     
/* 110 */     long endTime = 0L;
/* 111 */     int w = mc.field_71443_c;
/* 112 */     int h = mc.field_71440_d;
/* 113 */     boolean hide = mc.field_71474_y.field_74319_N;
/* 114 */     int mipmapBackup = mc.field_71474_y.field_151442_I;
/* 115 */     int view = mc.field_71474_y.field_74320_O;
/* 116 */     int limit = mc.field_71474_y.field_74350_i;
/* 117 */     RayTraceResult mouseOver = mc.field_71476_x;
/* 118 */     float fov = mc.field_71474_y.field_74334_X;
/* 119 */     boolean fboBackup = mc.field_71474_y.field_151448_g;
/* 120 */     boolean bobbingBackup = mc.field_71474_y.field_74336_f;
/* 121 */     float mouseSensitivityBackup = mc.field_71474_y.field_74341_c;
/*     */ 
/*     */     
/* 124 */     mc.field_71474_y.field_74319_N = true;
/* 125 */     mc.field_71474_y.field_74320_O = 0;
/* 126 */     mc.field_71474_y.field_151442_I = 3;
/* 127 */     mc.field_71474_y.field_74334_X = zoom;
/* 128 */     mc.field_71474_y.field_151448_g = false;
/* 129 */     mc.field_71474_y.field_74336_f = false;
/*     */     
/* 131 */     mc.field_71440_d = height;
/* 132 */     mc.field_71443_c = width;
/*     */     
/* 134 */     if (mc.field_71474_y.field_74334_X < 0.0F) {
/* 135 */       mc.field_71474_y.field_74334_X = 1.0F;
/*     */     }
/*     */     
/* 138 */     if (limit != 0 && this.renderEndNanoTime != null) {
/*     */       try {
/* 140 */         endTime = this.renderEndNanoTime.getLong(mc.field_71460_t);
/* 141 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 146 */     int fps = Math.max(30, mc.field_71474_y.field_74350_i);
/* 147 */     mc.field_71460_t.func_78471_a(partialTick, endTime + (1000000000 / fps));
/*     */     
/* 149 */     GlStateManager.func_179097_i();
/*     */ 
/*     */     
/* 152 */     GL11.glBindTexture(3553, FLASHED_TEX);
/* 153 */     GL11.glCopyTexImage2D(3553, 0, 6407, 0, 0, width, height, 0);
/*     */ 
/*     */     
/* 156 */     if (limit != 0 && this.renderEndNanoTime != null) {
/*     */       try {
/* 158 */         this.renderEndNanoTime.setLong(mc.field_71460_t, endTime);
/* 159 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     mc.field_71476_x = mouseOver;
/* 166 */     mc.field_71474_y.field_74350_i = limit;
/* 167 */     mc.field_71474_y.field_74320_O = view;
/* 168 */     mc.field_71474_y.field_74319_N = hide;
/* 169 */     mc.field_71474_y.field_151442_I = mipmapBackup;
/* 170 */     mc.field_71443_c = w;
/* 171 */     mc.field_71440_d = h;
/* 172 */     mc.field_71474_y.field_151448_g = fboBackup;
/* 173 */     mc.field_71474_y.field_74336_f = bobbingBackup;
/* 174 */     mc.field_71474_y.field_74341_c = mouseSensitivityBackup;
/*     */     
/* 176 */     mc.field_71474_y.field_74334_X = fov;
/* 177 */     mc.field_71438_f = renderBackup;
/*     */ 
/*     */     
/* 180 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\hud\FlashSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */