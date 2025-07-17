/*    */ package com.modularwarfare.client.model.layers;
/*    */ 
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class ResetHiddenModelLayer implements LayerRenderer<EntityPlayer> {
/*    */   public ResetHiddenModelLayer(RenderPlayer renderPlayer) {
/* 10 */     this.renderPlayer = renderPlayer;
/*    */   }
/*    */   
/*    */   RenderPlayer renderPlayer;
/*    */   
/*    */   public void doRenderLayer(EntityPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 16 */     (this.renderPlayer.func_177087_b()).field_78116_c.field_78807_k = false;
/* 17 */     (this.renderPlayer.func_177087_b()).field_78115_e.field_78807_k = false;
/* 18 */     (this.renderPlayer.func_177087_b()).field_178724_i.field_78807_k = false;
/* 19 */     (this.renderPlayer.func_177087_b()).field_178723_h.field_78807_k = false;
/* 20 */     (this.renderPlayer.func_177087_b()).field_178722_k.field_78807_k = false;
/* 21 */     (this.renderPlayer.func_177087_b()).field_178721_j.field_78807_k = false;
/* 22 */     (this.renderPlayer.func_177087_b()).field_78116_c.field_78806_j = true;
/* 23 */     (this.renderPlayer.func_177087_b()).field_78115_e.field_78806_j = true;
/* 24 */     (this.renderPlayer.func_177087_b()).field_178724_i.field_78806_j = true;
/* 25 */     (this.renderPlayer.func_177087_b()).field_178723_h.field_78806_j = true;
/* 26 */     (this.renderPlayer.func_177087_b()).field_178722_k.field_78806_j = true;
/* 27 */     (this.renderPlayer.func_177087_b()).field_178721_j.field_78806_j = true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_177142_b() {
/* 33 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\model\layers\ResetHiddenModelLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */