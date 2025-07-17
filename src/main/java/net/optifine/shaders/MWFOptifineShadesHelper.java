/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.nio.IntBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MWFOptifineShadesHelper
/*    */ {
/*    */   public static FlipTextures getFlipTextures() {
/* 11 */     return Shaders.dfbColorTexturesFlip;
/*    */   }
/*    */   
/*    */   public static int getColorTexture(int i) {
/* 15 */     return Shaders.dfbColorTextures.get(i);
/*    */   }
/*    */   
/*    */   public static int getDFB() {
/* 19 */     return Shaders.dfb;
/*    */   }
/*    */   
/*    */   public static IntBuffer getDFBDrawBuffers() {
/* 23 */     return Shaders.dfbDrawBuffers;
/*    */   }
/*    */   
/*    */   public static int getUsedColorBuffers() {
/* 27 */     return Shaders.usedColorBuffers;
/*    */   }
/*    */   
/*    */   public static IntBuffer getDFBDepthTextures() {
/* 31 */     return Shaders.dfbDepthTextures;
/*    */   }
/*    */   
/*    */   public static int getPreShadowPassThirdPersonView() {
/* 35 */     return Shaders.preShadowPassThirdPersonView;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\optifine\shaders\MWFOptifineShadesHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */