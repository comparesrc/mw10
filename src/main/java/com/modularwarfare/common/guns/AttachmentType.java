/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.client.fpp.basic.configs.AttachmentRenderConfig;
/*    */ import com.modularwarfare.client.model.ModelAttachment;
/*    */ import com.modularwarfare.common.textures.TextureEnumType;
/*    */ import com.modularwarfare.common.textures.TextureType;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import com.modularwarfare.loader.MWModelBase;
/*    */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class AttachmentType
/*    */   extends BaseType
/*    */ {
/*    */   public AttachmentPresetEnum attachmentType;
/* 17 */   public Grip grip = new Grip();
/*    */   
/* 19 */   public Barrel barrel = new Barrel();
/*    */   
/* 21 */   public Sight sight = new Sight();
/*    */   
/* 23 */   public Stock stock = new Stock();
/*    */ 
/*    */   
/*    */   public boolean sameTextureAsGun = false;
/*    */ 
/*    */ 
/*    */   
/*    */   public void loadExtraValues() {
/* 31 */     if (this.maxStackSize == null) {
/* 32 */       this.maxStackSize = Integer.valueOf(1);
/*    */     }
/* 34 */     loadBaseValues();
/*    */     
/* 36 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
/* 37 */       if (this.sight.customOverlayTexture != null) {
/* 38 */         if (ModularWarfare.textureTypes.containsKey(this.sight.customOverlayTexture)) {
/* 39 */           this.sight.overlayType = (TextureType)ModularWarfare.textureTypes.get(this.sight.customOverlayTexture);
/*    */         }
/*    */       } else {
/* 42 */         this.sight.overlayType = new TextureType();
/* 43 */         this.sight.overlayType.initDefaultTextures(TextureEnumType.Overlay);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void reloadModel() {
/* 50 */     this.model = (MWModelBase)new ModelAttachment((AttachmentRenderConfig)ModularWarfare.getRenderConfig(this, AttachmentRenderConfig.class), this);
/* 51 */     ((ModelAttachment)this.model).config.init();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAssetDir() {
/* 56 */     return "attachments";
/*    */   }
/*    */   
/*    */   public static class Sight
/*    */   {
/* 61 */     public WeaponDotColorType dotColorType = WeaponDotColorType.RED;
/* 62 */     public WeaponScopeModeType modeType = WeaponScopeModeType.NORMAL;
/*    */     
/*    */     public String customOverlayTexture;
/*    */     
/*    */     public transient TextureType overlayType;
/*    */     public boolean plumbCrossHair = false;
/*    */     public boolean usedDefaultOverlayModelTexture = true;
/*    */   }
/*    */   
/*    */   public static class Barrel
/*    */   {
/*    */     public boolean isSuppressor;
/*    */     public boolean hideFlash;
/* 75 */     public float recoilPitchFactor = 1.0F;
/* 76 */     public float recoilYawFactor = 1.0F;
/*    */     
/* 78 */     public float accuracyFactor = 1.0F;
/*    */   }
/*    */   
/*    */   public static class Grip {
/* 82 */     public float recoilPitchFactor = 1.0F;
/* 83 */     public float recoilYawFactor = 1.0F;
/*    */   }
/*    */   
/*    */   public static class Stock {
/* 87 */     public float recoilPitchFactor = 1.0F;
/* 88 */     public float recoilYawFactor = 1.0F;
/* 89 */     public float aimSpeedFactor = 1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\guns\AttachmentType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */