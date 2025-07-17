/*    */ package com.modularwarfare.mixin.client;
/*    */ 
/*    */ import com.modularwarfare.core.MWFCoreHooks;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Overwrite;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({RenderPlayer.class})
/*    */ public class MixinRenderPlayer
/*    */ {
/*    */   @Overwrite
/*    */   protected void func_77039_a(EntityLivingBase entityLivingBaseIn, double x, double y, double z) {
/* 25 */     MWFCoreHooks.renderLivingAtForRenderPlayer(entityLivingBaseIn, x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\mixin\client\MixinRenderPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */