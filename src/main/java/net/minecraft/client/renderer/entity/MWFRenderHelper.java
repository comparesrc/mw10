/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ 
/*    */ public class MWFRenderHelper
/*    */ {
/*    */   public RenderLivingBase renderLivingBase;
/*    */   
/*    */   public MWFRenderHelper(RenderLivingBase renderLivingBase) {
/* 13 */     this.renderLivingBase = renderLivingBase;
/*    */   }
/*    */   
/*    */   public List<LayerRenderer> getLayerRenderers() {
/* 17 */     return this.renderLivingBase.field_177097_h;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean setBrightness(EntityLivingBase entitylivingbaseIn, float partialTicks, boolean combineTextures) {
/* 22 */     return this.renderLivingBase.func_177092_a(entitylivingbaseIn, partialTicks, combineTextures);
/*    */   }
/*    */   
/*    */   public void unsetBrightness() {
/* 26 */     this.renderLivingBase.func_177091_f();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\minecraft\client\renderer\entity\MWFRenderHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */