/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.fpp.basic.configs.BulletRenderConfig;
/*    */ import com.modularwarfare.client.model.ModelBullet;
/*    */ import com.modularwarfare.client.model.ModelShell;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import com.modularwarfare.loader.MWModelBase;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BulletType
/*    */   extends BaseType
/*    */ {
/*    */   public HashMap<String, BulletProperty> bulletProperties;
/* 17 */   public float bulletDamageFactor = 1.0F;
/* 18 */   public float bulletAccuracyFactor = 1.0F;
/*    */   
/*    */   public boolean isSlug = false;
/*    */   
/*    */   public boolean isFireDamage = false;
/*    */   
/*    */   public boolean isAbsoluteDamage = false;
/*    */   
/*    */   public boolean isBypassesArmorDamage = false;
/*    */   
/*    */   public boolean isExplosionDamage = false;
/*    */   
/*    */   public boolean isMagicDamage = false;
/*    */   public boolean renderBulletModel = false;
/* 32 */   public transient String defaultModel = "default.obj";
/* 33 */   public String shellModelFileName = this.defaultModel;
/*    */   
/*    */   public transient MWModelBase shell;
/* 36 */   public String shellSound = "casing";
/*    */ 
/*    */   
/*    */   public float projectileVelocity;
/*    */ 
/*    */   
/* 42 */   public float explosionStrength = 4.0F;
/*    */ 
/*    */   
/*    */   public boolean damageWorld = true;
/*    */   
/*    */   public boolean isDynamicBullet = false;
/*    */   
/*    */   public boolean sameTextureAsGun = false;
/*    */   
/*    */   public String trailModel;
/*    */   
/*    */   public String trailTex;
/*    */   
/*    */   public boolean trailGlow = false;
/*    */ 
/*    */   
/*    */   public void loadExtraValues() {
/* 59 */     if (this.maxStackSize == null) {
/* 60 */       this.maxStackSize = Integer.valueOf(64);
/*    */     }
/* 62 */     loadBaseValues();
/*    */   }
/*    */ 
/*    */   
/*    */   public void reloadModel() {
/* 67 */     if (this.renderBulletModel) {
/* 68 */       this.model = (MWModelBase)new ModelBullet((BulletRenderConfig)ModularWarfare.getRenderConfig(this, BulletRenderConfig.class), this);
/*    */     }
/* 70 */     if (!this.shellModelFileName.equals(this.defaultModel)) {
/* 71 */       this.shell = (MWModelBase)new ModelShell(this, false);
/*    */     } else {
/* 73 */       this.shell = (MWModelBase)new ModelShell(this, true);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAssetDir() {
/* 79 */     return "bullets";
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\guns\BulletType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */