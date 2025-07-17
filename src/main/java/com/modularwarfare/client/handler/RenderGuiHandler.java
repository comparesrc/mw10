/*    */ package com.modularwarfare.client.handler;
/*    */ 
/*    */ import com.modularwarfare.client.ClientProxy;
/*    */ import com.modularwarfare.client.ClientRenderHooks;
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import com.modularwarfare.utility.DevGui;
/*    */ import com.modularwarfare.utility.event.ForgeEvent;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class RenderGuiHandler
/*    */   extends ForgeEvent {
/*    */   @SubscribeEvent
/*    */   public void onRenderGui(RenderGameOverlayEvent.Post event) {
/* 18 */     if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
/*    */       return; 
/* 20 */     if ((Minecraft.func_71410_x()).field_71439_g != null) {
/* 21 */       EntityPlayerSP entityPlayer = (Minecraft.func_71410_x()).field_71439_g;
/* 22 */       if (entityPlayer.func_184614_ca() != null && entityPlayer.func_184614_ca().func_77973_b() instanceof ItemGun)
/* 23 */         new DevGui(Minecraft.func_71410_x(), entityPlayer.func_184614_ca(), (ItemGun)entityPlayer.func_184614_ca().func_77973_b(), ClientProxy.gunStaticRenderer, ClientRenderHooks.getAnimMachine((EntityLivingBase)entityPlayer)); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\handler\RenderGuiHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */