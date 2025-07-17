/*    */ package com.modularwarfare.utility;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.shader.Framebuffer;
/*    */ import org.lwjgl.opengl.EXTFramebufferObject;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Stencil
/*    */ {
/* 17 */   static Minecraft mc = Minecraft.func_71410_x();
/*    */   
/*    */   public static void dispose() {
/* 20 */     GL11.glDisable(2960);
/* 21 */     GlStateManager.func_179118_c();
/* 22 */     GlStateManager.func_179084_k();
/*    */   }
/*    */   
/*    */   public static void erase(boolean invert) {
/* 26 */     GL11.glStencilFunc(invert ? 514 : 517, 1, 65535);
/* 27 */     GL11.glStencilOp(7680, 7680, 7681);
/* 28 */     GlStateManager.func_179135_a(true, true, true, true);
/* 29 */     GlStateManager.func_179141_d();
/* 30 */     GlStateManager.func_179147_l();
/* 31 */     GL11.glAlphaFunc(516, 0.0F);
/*    */   }
/*    */   
/*    */   public static void write(boolean renderClipLayer) {
/* 35 */     checkSetupFBO();
/* 36 */     GL11.glClearStencil(0);
/* 37 */     GL11.glClear(1024);
/* 38 */     GL11.glEnable(2960);
/* 39 */     GL11.glStencilFunc(519, 1, 65535);
/* 40 */     GL11.glStencilOp(7680, 7680, 7681);
/* 41 */     if (!renderClipLayer) {
/* 42 */       GlStateManager.func_179135_a(false, false, false, false);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void write(boolean renderClipLayer, Framebuffer fb) {
/* 47 */     checkSetupFBO(fb);
/* 48 */     GL11.glClearStencil(0);
/* 49 */     GL11.glClear(1024);
/* 50 */     GL11.glEnable(2960);
/* 51 */     GL11.glStencilFunc(519, 1, 65535);
/* 52 */     GL11.glStencilOp(7680, 7680, 7681);
/* 53 */     if (!renderClipLayer) {
/* 54 */       GlStateManager.func_179135_a(false, false, false, false);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void checkSetupFBO() {
/* 59 */     Framebuffer fbo = mc.func_147110_a();
/* 60 */     if (fbo != null && fbo.field_147624_h > -1) {
/* 61 */       setupFBO(fbo);
/* 62 */       fbo.field_147624_h = -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void checkSetupFBO(Framebuffer fbo) {
/* 67 */     if (fbo != null && fbo.field_147624_h > -1) {
/* 68 */       setupFBO(fbo);
/* 69 */       fbo.field_147624_h = -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void setupFBO(Framebuffer fbo) {
/* 74 */     EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.field_147624_h);
/* 75 */     int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
/* 76 */     EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
/* 77 */     EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, (Minecraft.func_71410_x()).field_71443_c, (Minecraft.func_71410_x()).field_71440_d);
/* 78 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
/* 79 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfar\\utility\Stencil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */