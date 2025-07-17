/*    */ package com.modularwarfare.client.fpp.basic.animations.anims;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.api.WeaponAnimation;
/*    */ import com.modularwarfare.client.fpp.basic.animations.AnimStateMachine;
/*    */ import com.modularwarfare.client.model.ModelGun;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnimationRifle4
/*    */   extends WeaponAnimation
/*    */ {
/*    */   public void onGunAnimation(float tiltProgress, AnimStateMachine animation) {
/* 18 */     ModularWarfare.LOGGER.info(Float.valueOf(tiltProgress));
/*    */     
/* 20 */     GL11.glTranslatef(0.15F * tiltProgress, 0.0F, 0.0F);
/*    */     
/* 22 */     GL11.glTranslatef(0.0F, 0.25F * tiltProgress, 0.0F);
/*    */     
/* 24 */     GL11.glTranslatef(0.0F, 0.0F, -0.15F * tiltProgress);
/*    */     
/* 26 */     GL11.glRotatef(-60.0F * tiltProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 28 */     GL11.glRotatef(-10.0F * tiltProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 30 */     GL11.glRotatef(70.0F * tiltProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onAmmoAnimation(ModelGun gunModel, float ammoProgress, int reloadAmmoCount, AnimStateMachine animation) {
/* 35 */     float multiAmmoPosition = ammoProgress * 1.0F;
/* 36 */     int bulletNum = MathHelper.func_76141_d(multiAmmoPosition);
/* 37 */     float bulletProgress = multiAmmoPosition - bulletNum;
/*    */ 
/*    */     
/* 40 */     GL11.glTranslatef(ammoProgress * -12.75F, 0.0F, 0.0F);
/*    */     
/* 42 */     GL11.glTranslatef(0.0F, ammoProgress * -5.0F, 0.0F);
/*    */     
/* 44 */     GL11.glTranslatef(0.0F, 0.0F, ammoProgress * -3.0F);
/*    */     
/* 46 */     GL11.glRotatef(0.0F * ammoProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 48 */     GL11.glRotatef(-50.0F * ammoProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 50 */     GL11.glRotatef(-150.0F * ammoProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\animations\anims\AnimationRifle4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */