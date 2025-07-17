/*     */ package com.modularwarfare.common.guns;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.client.fpp.basic.configs.GunRenderConfig;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*     */ import com.modularwarfare.client.fpp.enhanced.models.EnhancedModel;
/*     */ import com.modularwarfare.client.fpp.enhanced.models.ModelEnhancedGun;
/*     */ import com.modularwarfare.client.model.ModelGun;
/*     */ import com.modularwarfare.common.textures.TextureEnumType;
/*     */ import com.modularwarfare.common.textures.TextureType;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import com.modularwarfare.loader.MWModelBase;
/*     */ import com.modularwarfare.objects.SoundEntry;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GunType
/*     */   extends BaseType
/*     */ {
/*     */   public WeaponType weaponType;
/*  41 */   public WeaponScopeModeType scopeModeType = WeaponScopeModeType.SIMPLE;
/*     */   
/*  43 */   public WeaponAnimationType animationType = WeaponAnimationType.BASIC;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean restrictingFireAnimation = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean firingReload = true;
/*     */ 
/*     */   
/*  54 */   public float gunDamage = 0.0F;
/*     */   
/*  56 */   public float moveSpeedModifier = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public float gunDamageHeadshotBonus = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public int weaponMaxRange = 200;
/*     */ 
/*     */ 
/*     */   
/*  70 */   public int weaponEffectiveRange = 50;
/*     */ 
/*     */ 
/*     */   
/*  74 */   public int numBullets = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public int modifyUnloadBullets = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float bulletSpread;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public int roundsPerMin = 1;
/*     */ 
/*     */ 
/*     */   
/*  95 */   public transient int fireTickDelay = 0;
/*     */ 
/*     */ 
/*     */   
/*  99 */   public int numBurstRounds = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnergyGun = false;
/*     */ 
/*     */ 
/*     */   
/* 108 */   public float recoilPitch = 10.0F;
/*     */ 
/*     */ 
/*     */   
/* 112 */   public float recoilYaw = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public float accuracySneakFactor = 0.75F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public float accuracyCrawlFactor = 0.75F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public float accuracyAimFactor = 0.75F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public float accuracyThirdAimFactor = 0.65F;
/*     */ 
/*     */ 
/*     */   
/* 139 */   public float accuracyMoveOffset = 0.75F;
/*     */ 
/*     */ 
/*     */   
/* 143 */   public float accuracySprintOffset = 0.25F;
/*     */ 
/*     */ 
/*     */   
/* 147 */   public float accuracyHoverOffset = 1.5F;
/*     */ 
/*     */   
/* 150 */   public float randomRecoilPitch = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public float randomRecoilYaw = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public float recoilAimReducer = 0.8F;
/*     */   
/* 164 */   public float recoilCrawlYawFactor = 0.5F;
/* 165 */   public float recoilCrawlPitchFactor = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public WeaponFireMode[] fireModes = new WeaponFireMode[] { WeaponFireMode.SEMI };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap<AttachmentPresetEnum, ArrayList<String>> acceptedAttachments;
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap<AttachmentPresetEnum, String> defaultAttachments;
/*     */ 
/*     */ 
/*     */   
/* 183 */   public int reloadTime = 40;
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer offhandReloadTime;
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] acceptedAmmo;
/*     */ 
/*     */ 
/*     */   
/* 195 */   public int chamberCapacity = 1;
/*     */ 
/*     */   
/*     */   public boolean dropBulletCasing = true;
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public ModelBiped.ArmPose armPose;
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public ModelBiped.ArmPose armPoseAiming;
/*     */ 
/*     */   
/*     */   public boolean dynamicAmmo = false;
/*     */ 
/*     */   
/*     */   public Integer internalAmmoStorage;
/*     */ 
/*     */   
/*     */   public String[] acceptedBullets;
/*     */ 
/*     */   
/*     */   public boolean allowEquipSounds = true;
/*     */ 
/*     */   
/*     */   public boolean allowSprintFiring = true;
/*     */ 
/*     */   
/*     */   public boolean allowReloadFiring = false;
/*     */ 
/*     */   
/*     */   public boolean allowReloadingSprint = true;
/*     */ 
/*     */   
/*     */   public boolean allowFiringSprint = true;
/*     */ 
/*     */   
/*     */   public boolean allowAimingSprint = true;
/*     */ 
/*     */   
/*     */   public String customFlashTexture;
/*     */ 
/*     */   
/*     */   public transient TextureType flashType;
/*     */ 
/*     */   
/*     */   public String extraLore;
/*     */   
/* 244 */   public Vec3d shellEjectOffsetNormal = new Vec3d(-1.0D, 0.0D, 1.0D);
/* 245 */   public Vec3d shellEjectOffsetAiming = new Vec3d(0.0D, 0.11999999731779099D, 1.0D);
/*     */ 
/*     */   
/*     */   public String customHandsTexture;
/*     */ 
/*     */   
/*     */   public transient TextureType handsTextureType;
/*     */   
/*     */   public String customTrailTexture;
/*     */   
/*     */   public String customTrailModel;
/*     */   
/*     */   public boolean customTrailGlow;
/*     */ 
/*     */   
/*     */   public static boolean isPackAPunched(ItemStack heldStack) {
/* 261 */     if (heldStack.func_77978_p() != null) {
/* 262 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p();
/* 263 */       return nbtTagCompound.func_74764_b("punched") ? nbtTagCompound.func_74767_n("punched") : false;
/*     */     } 
/* 265 */     return false;
/*     */   }
/*     */   
/*     */   public static void setPackAPunched(ItemStack heldStack, boolean bool) {
/* 269 */     if (heldStack.func_77978_p() != null) {
/* 270 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p();
/* 271 */       nbtTagCompound.func_74757_a("punched", bool);
/* 272 */       heldStack.func_77982_d(nbtTagCompound);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static WeaponFireMode getFireMode(ItemStack heldStack) {
/* 277 */     if (heldStack.func_77978_p() != null) {
/* 278 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p();
/* 279 */       return nbtTagCompound.func_74764_b("firemode") ? WeaponFireMode.fromString(nbtTagCompound.func_74779_i("firemode")) : null;
/*     */     } 
/* 281 */     return null;
/*     */   }
/*     */   
/*     */   public static void setFireMode(ItemStack heldStack, WeaponFireMode fireMode) {
/* 285 */     if (heldStack.func_77978_p() != null) {
/* 286 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p();
/* 287 */       nbtTagCompound.func_74778_a("firemode", fireMode.name().toLowerCase());
/* 288 */       heldStack.func_77982_d(nbtTagCompound);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ItemStack getAttachment(ItemStack heldStack, AttachmentPresetEnum type) {
/* 293 */     if (heldStack.func_77978_p() != null) {
/* 294 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p(); return 
/* 295 */         nbtTagCompound.func_74764_b("attachment_" + type.typeName) ? new ItemStack(nbtTagCompound.func_74775_l("attachment_" + type.typeName)) : null;
/*     */     } 
/* 297 */     return null;
/*     */   }
/*     */   
/*     */   public static void addAttachment(ItemStack heldStack, AttachmentPresetEnum type, ItemStack attachment) {
/* 301 */     if (heldStack.func_77978_p() != null) {
/* 302 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p();
/* 303 */       nbtTagCompound.func_74782_a("attachment_" + type.typeName, (NBTBase)attachment.func_77955_b(new NBTTagCompound()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void removeAttachment(ItemStack heldStack, AttachmentPresetEnum type) {
/* 308 */     if (heldStack.func_77978_p() != null) {
/* 309 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p();
/* 310 */       nbtTagCompound.func_82580_o("attachment_" + type.typeName);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadExtraValues() {
/* 316 */     if (this.maxStackSize == null) {
/* 317 */       this.maxStackSize = Integer.valueOf(1);
/*     */     }
/* 319 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
/* 320 */       this.armPose = ModelBiped.ArmPose.BLOCK;
/* 321 */       this.armPoseAiming = ModelBiped.ArmPose.BOW_AND_ARROW;
/*     */       
/* 323 */       if (this.customFlashTexture != null) {
/* 324 */         if (ModularWarfare.textureTypes.containsKey(this.customFlashTexture)) {
/* 325 */           this.flashType = (TextureType)ModularWarfare.textureTypes.get(this.customFlashTexture);
/*     */         } else {
/* 327 */           this.flashType = new TextureType();
/* 328 */           this.flashType.initDefaultTextures(TextureEnumType.Flash);
/*     */         } 
/*     */       } else {
/* 331 */         this.flashType = new TextureType();
/* 332 */         this.flashType.initDefaultTextures(TextureEnumType.Flash);
/*     */       } 
/* 334 */       if (this.customHandsTexture != null && 
/* 335 */         ModularWarfare.textureTypes.containsKey(this.customHandsTexture)) {
/* 336 */         this.handsTextureType = (TextureType)ModularWarfare.textureTypes.get(this.customHandsTexture);
/*     */       }
/*     */     } 
/*     */     
/* 340 */     loadBaseValues();
/* 341 */     this.fireTickDelay = 1200 / this.roundsPerMin;
/*     */     try {
/* 343 */       for (ArrayList<SoundEntry> entryList : (Iterable<ArrayList<SoundEntry>>)this.weaponSoundMap.values()) {
/* 344 */         for (SoundEntry soundEntry : entryList) {
/* 345 */           if (soundEntry.soundName != null) {
/* 346 */             ModularWarfare.PROXY.registerSound(soundEntry.soundName);
/* 347 */             if (soundEntry.soundNameDistant != null)
/* 348 */               ModularWarfare.PROXY.registerSound(soundEntry.soundNameDistant);  continue;
/*     */           } 
/* 350 */           ModularWarfare.LOGGER.error(String.format("Sound entry event '%s' has null soundName for type '%s'", new Object[] { soundEntry.soundEvent, this.internalName }));
/*     */         }
/*     */       
/*     */       } 
/* 354 */     } catch (Exception exception) {
/* 355 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadModel() {
/*     */     try {
/* 362 */       if (this.animationType == WeaponAnimationType.BASIC) {
/* 363 */         this.model = (MWModelBase)new ModelGun((GunRenderConfig)ModularWarfare.getRenderConfig(this, GunRenderConfig.class), this);
/*     */       } else {
/* 365 */         this.enhancedModel = (EnhancedModel)new ModelEnhancedGun((GunEnhancedRenderConfig)ModularWarfare.getRenderConfig(this, GunEnhancedRenderConfig.class), this);
/*     */       } 
/* 367 */     } catch (Throwable t) {
/* 368 */       ModularWarfare.LOGGER.warn("Something is going wrong when reloading model:" + this.internalName);
/* 369 */       t.printStackTrace();
/* 370 */       FMLCommonHandler.instance().exitJava(0, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasFireMode(WeaponFireMode fireMode) {
/* 375 */     if (this.fireModes != null) {
/* 376 */       for (int i = 0; i < this.fireModes.length; i++) {
/* 377 */         if (this.fireModes[i] == fireMode) {
/* 378 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 382 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAssetDir() {
/* 387 */     return "guns";
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\guns\GunType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */