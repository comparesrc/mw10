/*    */ package com.modularwarfare.client.view;
/*    */ 
/*    */ import com.modularwarfare.ModConfig;
/*    */ import com.modularwarfare.client.ClientRenderHooks;
/*    */ import com.teamderpy.shouldersurfing.client.ShoulderInstance;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraftforge.fml.common.Loader;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AutoSwitchToFirstView
/*    */ {
/* 17 */   private int initialThirdPersonView = -1;
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRenderTick(TickEvent.RenderTickEvent event) {
/* 21 */     if ((Minecraft.func_71410_x()).field_71439_g != null && ModConfig.INSTANCE.hud.autoSwitchToFirstView) {
/* 22 */       boolean isMouseDown = Mouse.isButtonDown(1);
/*    */       
/* 24 */       if (this.initialThirdPersonView == -1 && (Minecraft.func_71410_x()).field_71474_y.field_74320_O == 1) {
/* 25 */         this.initialThirdPersonView = 1;
/*    */       }
/*    */       
/* 28 */       if (this.initialThirdPersonView == 1) {
/* 29 */         if (isMouseDown && (Minecraft.func_71410_x()).field_71439_g.func_184614_ca().func_77973_b() instanceof com.modularwarfare.common.guns.ItemGun) {
/* 30 */           (Minecraft.func_71410_x()).field_71474_y.field_74320_O = 0;
/* 31 */         } else if (!isMouseDown && !ClientRenderHooks.isAimingScope) {
/* 32 */           if (Loader.isModLoaded("shouldersurfing")) {
/* 33 */             (Minecraft.func_71410_x()).field_71474_y.field_74320_O = 1;
/* 34 */             ShoulderInstance.getInstance().setShoulderSurfing(true);
/*    */           } else {
/* 36 */             (Minecraft.func_71410_x()).field_71474_y.field_74320_O = 1;
/*    */           } 
/* 38 */           this.initialThirdPersonView = -1;
/*    */         }
/*    */       
/* 41 */       } else if (isMouseDown) {
/*    */       
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\view\AutoSwitchToFirstView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */