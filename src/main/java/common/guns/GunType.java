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
/*     */ 
/*     */   
/*  49 */   public float gunDamage = 0.0F;
/*     */   
/*  51 */   public float moveSpeedModifier = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public float gunDamageHeadshotBonus = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public int weaponMaxRange = 200;
/*     */ 
/*     */ 
/*     */   
/*  65 */   public int weaponEffectiveRange = 50;
/*     */ 
/*     */ 
/*     */   
/*  69 */   public int numBullets = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public int modifyUnloadBullets = 0;
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
/*  86 */   public int roundsPerMin = 1;
/*     */ 
/*     */ 
/*     */   
/*  90 */   public transient int fireTickDelay = 0;
/*     */ 
/*     */ 
/*     */   
/*  94 */   public int numBurstRounds = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnergyGun = false;
/*     */ 
/*     */ 
/*     */   
/* 103 */   public float recoilPitch = 10.0F;
/*     */ 
/*     */ 
/*     */   
/* 107 */   public float recoilYaw = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public float accuracySneakFactor = 0.75F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public float accuracyCrawlFactor = 0.75F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public float accuracyAimFactor = 0.75F;
/*     */   
/* 127 */   public float accuracyMoveOffset = 0.75F;
/* 128 */   public float accuracySprintOffset = 0.25F;
/* 129 */   public float accuracyHoverOffset = 1.5F;
/*     */ 
/*     */   
/* 132 */   public float randomRecoilPitch = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public float randomRecoilYaw = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public float recoilAimReducer = 0.8F;
/*     */   
/* 146 */   public float recoilCrawlYawFactor = 0.5F;
/* 147 */   public float recoilCrawlPitchFactor = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public WeaponFireMode[] fireModes = new WeaponFireMode[] { WeaponFireMode.SEMI };
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
/* 165 */   public int reloadTime = 40;
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
/* 177 */   public int chamberCapacity = 1;
/*     */ 
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
/*     */   
/* 225 */   public Vec3d shellEjectOffsetNormal = new Vec3d(-1.0D, 0.0D, 1.0D);
/* 226 */   public Vec3d shellEjectOffsetAiming = new Vec3d(0.0D, 0.11999999731779099D, 1.0D);
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
/* 242 */     if (heldStack.func_77978_p() != null) {
/* 243 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p();
/* 244 */       return nbtTagCompound.func_74764_b("punched") ? nbtTagCompound.func_74767_n("punched") : false;
/*     */     } 
/* 246 */     return false;
/*     */   }
/*     */   
/*     */   public static void setPackAPunched(ItemStack heldStack, boolean bool) {
/* 250 */     if (heldStack.func_77978_p() != null) {
/* 251 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p();
/* 252 */       nbtTagCompound.func_74757_a("punched", bool);
/* 253 */       heldStack.func_77982_d(nbtTagCompound);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static WeaponFireMode getFireMode(ItemStack heldStack) {
/* 258 */     if (heldStack.func_77978_p() != null) {
/* 259 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p();
/* 260 */       return nbtTagCompound.func_74764_b("firemode") ? WeaponFireMode.fromString(nbtTagCompound.func_74779_i("firemode")) : null;
/*     */     } 
/* 262 */     return null;
/*     */   }
/*     */   
/*     */   public static void setFireMode(ItemStack heldStack, WeaponFireMode fireMode) {
/* 266 */     if (heldStack.func_77978_p() != null) {
/* 267 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p();
/* 268 */       nbtTagCompound.func_74778_a("firemode", fireMode.name().toLowerCase());
/* 269 */       heldStack.func_77982_d(nbtTagCompound);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ItemStack getAttachment(ItemStack heldStack, AttachmentPresetEnum type) {
/* 274 */     if (heldStack.func_77978_p() != null) {
/* 275 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p(); return 
/* 276 */         nbtTagCompound.func_74764_b("attachment_" + type.typeName) ? new ItemStack(nbtTagCompound.func_74775_l("attachment_" + type.typeName)) : null;
/*     */     } 
/* 278 */     return null;
/*     */   }
/*     */   
/*     */   public static void addAttachment(ItemStack heldStack, AttachmentPresetEnum type, ItemStack attachment) {
/* 282 */     if (heldStack.func_77978_p() != null) {
/* 283 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p();
/* 284 */       nbtTagCompound.func_74782_a("attachment_" + type.typeName, (NBTBase)attachment.func_77955_b(new NBTTagCompound()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void removeAttachment(ItemStack heldStack, AttachmentPresetEnum type) {
/* 289 */     if (heldStack.func_77978_p() != null) {
/* 290 */       NBTTagCompound nbtTagCompound = heldStack.func_77978_p();
/* 291 */       nbtTagCompound.func_82580_o("attachment_" + type.typeName);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadExtraValues() {
/* 297 */     if (this.maxStackSize == null) {
/* 298 */       this.maxStackSize = Integer.valueOf(1);
/*     */     }
/* 300 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
/* 301 */       this.armPose = ModelBiped.ArmPose.BLOCK;
/* 302 */       this.armPoseAiming = ModelBiped.ArmPose.BOW_AND_ARROW;
/*     */       
/* 304 */       if (this.customFlashTexture != null) {
/* 305 */         if (ModularWarfare.textureTypes.containsKey(this.customFlashTexture)) {
/* 306 */           this.flashType = (TextureType)ModularWarfare.textureTypes.get(this.customFlashTexture);
/*     */         } else {
/* 308 */           this.flashType = new TextureType();
/* 309 */           this.flashType.initDefaultTextures(TextureEnumType.Flash);
/*     */         } 
/*     */       } else {
/* 312 */         this.flashType = new TextureType();
/* 313 */         this.flashType.initDefaultTextures(TextureEnumType.Flash);
/*     */       } 
/* 315 */       if (this.customHandsTexture != null && 
/* 316 */         ModularWarfare.textureTypes.containsKey(this.customHandsTexture)) {
/* 317 */         this.handsTextureType = (TextureType)ModularWarfare.textureTypes.get(this.customHandsTexture);
/*     */       }
/*     */     } 
/*     */     
/* 321 */     loadBaseValues();
/* 322 */     this.fireTickDelay = 1200 / this.roundsPerMin;
/*     */     try {
/* 324 */       for (ArrayList<SoundEntry> entryList : (Iterable<ArrayList<SoundEntry>>)this.weaponSoundMap.values()) {
/* 325 */         for (SoundEntry soundEntry : entryList) {
/* 326 */           if (soundEntry.soundName != null) {
/* 327 */             ModularWarfare.PROXY.registerSound(soundEntry.soundName);
/* 328 */             if (soundEntry.soundNameDistant != null)
/* 329 */               ModularWarfare.PROXY.registerSound(soundEntry.soundNameDistant);  continue;
/*     */           } 
/* 331 */           ModularWarfare.LOGGER.error(String.format("Sound entry event '%s' has null soundName for type '%s'", new Object[] { soundEntry.soundEvent, this.internalName }));
/*     */         }
/*     */       
/*     */       } 
/* 335 */     } catch (Exception exception) {
/* 336 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadModel() {
/*     */     try {
/* 343 */       if (this.animationType == WeaponAnimationType.BASIC) {
/* 344 */         this.model = (MWModelBase)new ModelGun((GunRenderConfig)ModularWarfare.getRenderConfig(this, GunRenderConfig.class), this);
/*     */       } else {
/* 346 */         this.enhancedModel = (EnhancedModel)new ModelEnhancedGun((GunEnhancedRenderConfig)ModularWarfare.getRenderConfig(this, GunEnhancedRenderConfig.class), this);
/*     */       } 
/* 348 */     } catch (Throwable t) {
/* 349 */       ModularWarfare.LOGGER.warn("Something is going wrong when reloading model:" + this.internalName);
/* 350 */       t.printStackTrace();
/* 351 */       FMLCommonHandler.instance().exitJava(0, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasFireMode(WeaponFireMode fireMode) {
/* 356 */     if (this.fireModes != null) {
/* 357 */       for (int i = 0; i < this.fireModes.length; i++) {
/* 358 */         if (this.fireModes[i] == fireMode) {
/* 359 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 363 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAssetDir() {
/* 368 */     return "guns";
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\GunType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */