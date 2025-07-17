/*    */ package com.modularwarfare.client.patch.galacticraft;
/*    */ 
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class GCDummyInterop
/*    */   implements GCCompatInterop {
/*    */   public boolean isModLoaded() {
/* 10 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFixApplied() {
/* 15 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFixed() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void addLayers(RenderPlayer rp) {}
/*    */ 
/*    */   
/*    */   public boolean isGCLayer(LayerRenderer<EntityPlayer> layer) {
/* 28 */     return false;
/*    */   }
/*    */   
/*    */   public void applyFix() {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\patch\galacticraft\GCDummyInterop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */