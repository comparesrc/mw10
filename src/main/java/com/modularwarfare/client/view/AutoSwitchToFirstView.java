/*    */ package com.modularwarfare.client.view;
/*    */ 
/*    */ import com.modularwarfare.ModConfig;
/*    */ import com.modularwarfare.client.ClientProxy;
/*    */ import com.teamderpy.shouldersurfing.client.ShoulderInstance;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ import org.lwjgl.input.Mouse;
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
/*    */ public class AutoSwitchToFirstView
/*    */ {
/*    */   private static boolean aimlock = false;
/*    */   private static boolean aimFlag = false;
/*    */   private static long lastAimTime;
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRenderTick(TickEvent.RenderTickEvent event) {
/* 28 */     if ((Minecraft.func_71410_x()).field_71439_g != null && ModConfig.INSTANCE.hud.autoSwitchToFirstView) {
/* 29 */       if ((Minecraft.func_71410_x()).field_71439_g.func_184614_ca().func_77973_b() instanceof com.modularwarfare.common.guns.ItemGun) {
/* 30 */         boolean isMouseDown = Mouse.isButtonDown(1);
/*    */         
/* 32 */         long time = System.currentTimeMillis();
/* 33 */         if (isMouseDown) {
/* 34 */           if (!aimFlag) {
/* 35 */             aimFlag = true;
/* 36 */             lastAimTime = time;
/*    */           } 
/*    */         } else {
/* 39 */           if (aimFlag && 
/* 40 */             time - lastAimTime < 200L) {
/* 41 */             if (!aimlock && (Minecraft.func_71410_x()).field_71474_y.field_74320_O == 1) {
/* 42 */               aimlock = true;
/* 43 */             } else if (aimlock) {
/* 44 */               if (ClientProxy.shoulderSurfingLoaded) {
/* 45 */                 (Minecraft.func_71410_x()).field_71474_y.field_74320_O = 1;
/* 46 */                 ShoulderInstance.getInstance().setShoulderSurfing(true);
/*    */               } else {
/* 48 */                 (Minecraft.func_71410_x()).field_71474_y.field_74320_O = 1;
/*    */               } 
/* 50 */               (Minecraft.func_71410_x()).field_71438_f.func_174979_m();
/* 51 */               aimlock = false;
/*    */             } 
/*    */           }
/*    */           
/* 55 */           aimFlag = false;
/*    */         } 
/* 57 */         if (aimlock) {
/* 58 */           (Minecraft.func_71410_x()).field_71474_y.field_74320_O = 0;
/*    */         }
/*    */       } else {
/* 61 */         if (aimlock) {
/* 62 */           (Minecraft.func_71410_x()).field_71474_y.field_74320_O = 1;
/*    */         }
/* 64 */         aimFlag = false;
/* 65 */         aimlock = false;
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean getAutoAimLock() {
/* 72 */     return aimlock;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\view\AutoSwitchToFirstView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */