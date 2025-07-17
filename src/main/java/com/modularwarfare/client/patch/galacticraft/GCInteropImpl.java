/*    */ package com.modularwarfare.client.patch.galacticraft;
/*    */ 
/*    */ import com.modularwarfare.client.model.layers.RenderLayerBackpack;
/*    */ import com.modularwarfare.client.model.layers.RenderLayerBody;
/*    */ import com.modularwarfare.client.model.layers.RenderLayerHeldGun;
/*    */ import java.lang.reflect.Field;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GCInteropImpl
/*    */   implements GCCompatInterop
/*    */ {
/*    */   public boolean fixApplied = false;
/*    */   
/*    */   public boolean isModLoaded() {
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFixApplied() {
/* 27 */     return this.fixApplied;
/*    */   }
/*    */   
/*    */   public void setFixed() {
/* 31 */     this.fixApplied = true;
/*    */   }
/*    */   
/*    */   public void addLayers(RenderPlayer rp) {
/* 35 */     rp.func_177094_a((LayerRenderer)new RenderLayerBackpack(rp, (rp.func_177087_b()).field_178730_v));
/* 36 */     rp.func_177094_a((LayerRenderer)new RenderLayerBody(rp, (rp.func_177087_b()).field_178730_v));
/* 37 */     rp.func_177094_a((LayerRenderer)new RenderLayerHeldGun((RenderLivingBase)rp));
/*    */   }
/*    */   
/*    */   public boolean isGCLayer(LayerRenderer<EntityPlayer> layer) {
/*    */     try {
/* 42 */       return (layer.getClass().equals(Class.forName("micdoodle8.mods.galacticraft.core.client.render.entities.layer.LayerOxygenTanks")) || layer.getClass().equals(Class.forName("micdoodle8.mods.galacticraft.core.client.render.entities.layer.LayerOxygenGear")) || layer.getClass().equals(Class.forName("micdoodle8.mods.galacticraft.core.client.render.entities.layer.LayerOxygenMask")) || layer.getClass().equals(Class.forName("micdoodle8.mods.galacticraft.core.client.render.entities.layer.LayerOxygenParachute")) || layer.getClass().equals(Class.forName("micdoodle8.mods.galacticraft.core.client.render.entities.layer.LayerFrequencyModule.class")));
/* 43 */     } catch (ClassNotFoundException e) {
/* 44 */       e.printStackTrace();
/*    */       
/* 46 */       return false;
/*    */     } 
/*    */   }
/*    */   public void applyFix() {
/*    */     try {
/* 51 */       Field field = Class.forName("micdoodle8.mods.galacticraft.core.util.CompatibilityManager").getField("RenderPlayerAPILoaded");
/* 52 */       field.set(Boolean.class, Boolean.valueOf(true));
/* 53 */     } catch (ClassNotFoundException e) {
/* 54 */       e.printStackTrace();
/* 55 */     } catch (NoSuchFieldException e) {
/* 56 */       e.printStackTrace();
/* 57 */     } catch (IllegalAccessException e) {
/* 58 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\patch\galacticraft\GCInteropImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */