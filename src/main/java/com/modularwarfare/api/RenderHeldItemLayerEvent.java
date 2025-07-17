/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class RenderHeldItemLayerEvent
/*    */   extends Event {
/*    */   public ItemStack stack;
/*    */   public LayerHeldItem layerHeldItem;
/*    */   public EntityLivingBase entitylivingbaseIn;
/*    */   public float partialTicks;
/*    */   
/*    */   public RenderHeldItemLayerEvent(ItemStack stack, LayerHeldItem layerHeldItem, EntityLivingBase entitylivingbaseIn, float partialTicks) {
/* 16 */     this.stack = stack;
/* 17 */     this.layerHeldItem = layerHeldItem;
/* 18 */     this.entitylivingbaseIn = entitylivingbaseIn;
/* 19 */     this.partialTicks = partialTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\api\RenderHeldItemLayerEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */