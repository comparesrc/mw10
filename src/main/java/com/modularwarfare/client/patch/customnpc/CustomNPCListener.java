/*    */ package com.modularwarfare.client.patch.customnpc;
/*    */ 
/*    */ import com.modularwarfare.client.model.layers.RenderLayerHeldGun;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraftforge.client.event.GuiScreenEvent;
/*    */ import net.minecraftforge.client.event.RenderLivingEvent;
/*    */ import net.minecraftforge.fml.common.Loader;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ public class CustomNPCListener
/*    */ {
/*    */   public static boolean CNPCLayersInitialised = false;
/*    */   
/*    */   private static <T extends EntityLivingBase> void addCNPCLayers(Class<? extends Entity> entityClass) {
/* 23 */     Render<T> renderer = Minecraft.func_71410_x().func_175598_ae().func_78715_a(entityClass);
/* 24 */     RenderLayerHeldGun layer = new RenderLayerHeldGun((RenderLivingBase)renderer);
/* 25 */     ((RenderLivingBase)renderer).func_177094_a((LayerRenderer)layer);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void guiPostInit(GuiScreenEvent.InitGuiEvent.Post event) {
/* 30 */     if (Loader.isModLoaded("customnpcs") && (
/* 31 */       event.getGui() instanceof net.minecraft.client.gui.inventory.GuiInventory || event.getGui() instanceof com.modularwarfare.client.gui.GuiInventoryModified)) {
/* 32 */       GuiContainer gui = (GuiContainer)event.getGui();
/* 33 */       event.getButtonList().add(new GuiQuestButton(55, gui, 93, 60, 18, 19, 
/* 34 */             I18n.func_135052_a((event.getGui() instanceof net.minecraft.client.gui.inventory.GuiInventory) ? "QUEST" : "QUEST", new Object[0])));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void initLayersCNPCs(RenderLivingEvent.Pre<EntityLivingBase> event) {
/*    */     try {
/* 42 */       Class<?> classz = Class.forName("noppes.npcs.entity.EntityCustomNpc");
/* 43 */       Class<?> classzz = Class.forName("noppes.npcs.entity.EntityNPC64x32");
/* 44 */       if (event.getEntity().getClass().equals(classz) && 
/* 45 */         !CNPCLayersInitialised) {
/* 46 */         addCNPCLayers((Class)classz);
/* 47 */         addCNPCLayers((Class)classzz);
/* 48 */         CNPCLayersInitialised = true;
/*    */       }
/*    */     
/* 51 */     } catch (ClassNotFoundException e) {
/* 52 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\patch\customnpc\CustomNPCListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */