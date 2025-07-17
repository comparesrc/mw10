/*    */ package com.modularwarfare.client.fpp.basic.renderers;
/*    */ 
/*    */ import com.modularwarfare.client.ClientRenderHooks;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderParameters
/*    */ {
/* 11 */   public static float adsSwitch = 0.0F;
/*    */   
/* 13 */   public static float sprintSwitch = 0.0F;
/* 14 */   public static float crouchSwitch = 0.0F;
/* 15 */   public static float reloadSwitch = 1.0F;
/* 16 */   public static float attachmentSwitch = 0.0F;
/*    */   
/*    */   public static float triggerPullSwitch;
/*    */   
/* 20 */   public static String lastModel = "";
/*    */ 
/*    */   
/*    */   public static float smoothing;
/*    */   
/* 25 */   public static float GUN_BALANCING_X = 0.0F;
/* 26 */   public static float GUN_BALANCING_Y = 0.0F;
/*    */   
/* 28 */   public static float GUN_CHANGE_Y = 0.0F;
/*    */   
/* 30 */   public static float GUN_ROT_X = 0.0F;
/* 31 */   public static float GUN_ROT_Y = 0.0F;
/* 32 */   public static float GUN_ROT_Z = 0.0F;
/*    */   
/* 34 */   public static float GUN_ROT_X_LAST = 0.0F;
/* 35 */   public static float GUN_ROT_Y_LAST = 0.0F;
/* 36 */   public static float GUN_ROT_Z_LAST = 0.0F;
/*    */ 
/*    */   
/*    */   public static float collideFrontDistance;
/*    */ 
/*    */   
/*    */   public static float playerRecoilPitch;
/*    */   
/*    */   public static float playerRecoilYaw;
/*    */   
/* 46 */   public static float prevPitch = 0.0F;
/*    */   
/*    */   public static float rate;
/*    */   
/*    */   public static boolean phase;
/*    */   
/*    */   public static float antiRecoilPitch;
/*    */   
/*    */   public static float antiRecoilYaw;
/*    */   
/*    */   public static float SMOOTH_SWING;
/*    */   
/*    */   public static float VAL;
/*    */   
/*    */   public static float VAL2;
/*    */   
/*    */   public static float VALROT;
/*    */   
/*    */   public static float VALSPRINT;
/*    */   
/*    */   public static float VALSPRINT2;
/*    */   
/* 68 */   public static float CROSS_ROTATE = 0.0F;
/*    */   
/* 70 */   public static HashSet<String> partsWithAmmo = new HashSet<>(Arrays.asList(new String[] { "flashModel", "leftArmModel", "leftArmLayerModel", "leftArmSlimModel", "leftArmLayerSlimModel", "rightArmModel", "rightArmLayerModel", "rightArmSlimModel", "rightArmLayerSlimModel" }));
/* 71 */   public static HashSet<String> partsWithoutAmmo = new HashSet<>(Arrays.asList(new String[] { "flashModel", "leftArmModel", "leftArmLayerModel", "leftArmSlimModel", "leftArmLayerSlimModel", "rightArmModel", "rightArmLayerModel", "rightArmSlimModel", "rightArmLayerSlimModel", "ammoModel" }));
/*    */   public RenderParameters() {
/*    */     int i;
/* 74 */     for (i = 0; i < 256; i++) {
/* 75 */       partsWithAmmo.add("bulletModel_" + i);
/*    */     }
/* 77 */     for (i = 0; i < 256; i++) {
/* 78 */       partsWithoutAmmo.add("bulletModel_" + i);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static void resetRenderMods() {
/* 84 */     reloadSwitch = 0.0F;
/* 85 */     sprintSwitch = 0.0F;
/* 86 */     adsSwitch = 0.0F;
/* 87 */     crouchSwitch = 0.0F;
/* 88 */     ClientRenderHooks.isAimingScope = false;
/* 89 */     ClientRenderHooks.isAiming = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\renderers\RenderParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */