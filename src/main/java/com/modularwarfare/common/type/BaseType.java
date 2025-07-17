/*     */ package com.modularwarfare.common.type;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.IMWModel;
/*     */ import com.modularwarfare.client.ClientProxy;
/*     */ import com.modularwarfare.client.fpp.enhanced.models.EnhancedModel;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.SkinType;
/*     */ import com.modularwarfare.common.guns.WeaponSoundType;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketPlaySound;
/*     */ import com.modularwarfare.loader.MWModelBase;
/*     */ import com.modularwarfare.loader.MWModelBipedBase;
/*     */ import com.modularwarfare.loader.ObjModel;
/*     */ import com.modularwarfare.objects.SoundEntry;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BaseType
/*     */ {
/*     */   @SideOnly(Side.CLIENT)
/*     */   public transient MWModelBase model;
/*     */   @SideOnly(Side.CLIENT)
/*     */   public transient MWModelBipedBase bipedModel;
/*     */   @SideOnly(Side.CLIENT)
/*     */   public transient EnhancedModel enhancedModel;
/*     */   public Integer maxStackSize;
/*     */   public SkinType[] modelSkins;
/*     */   public String internalName;
/*     */   public String displayName;
/*     */   public String iconName;
/*     */   public transient int id;
/*     */   public transient String contentPack;
/*     */   public transient boolean isInDirectory;
/*     */   public HashMap<WeaponSoundType, ArrayList<SoundEntry>> weaponSoundMap;
/*     */   public boolean allowDefaultSounds = true;
/*  81 */   public String toolipScript = "mwf/tooltip_main";
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static BaseType fromModel(ObjModel model) {
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadExtraValues() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadBaseValues() {
/*  99 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
/* 100 */       reloadModel();
/*     */     }
/* 102 */     if (this.modelSkins == null) {
/* 103 */       this.modelSkins = new SkinType[] { SkinType.getDefaultSkin(this) };
/*     */     }
/* 105 */     if (this.iconName == null) {
/* 106 */       this.iconName = this.internalName;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postLoad() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reloadModel() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasModel() {
/* 124 */     return (this.model != null || this.bipedModel != null || this.enhancedModel != null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IMWModel getModel() {
/* 129 */     if (this.enhancedModel != null) {
/* 130 */       return (IMWModel)this.enhancedModel;
/*     */     }
/* 132 */     if (this.model != null) {
/* 133 */       return (IMWModel)this.model;
/*     */     }
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 143 */     return this.internalName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAssetDir() {
/* 148 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playClientSound(EntityPlayer player, WeaponSoundType weaponSoundType) {
/* 157 */     if (weaponSoundType != null && 
/* 158 */       this.weaponSoundMap != null) {
/* 159 */       if (this.weaponSoundMap.containsKey(weaponSoundType)) {
/* 160 */         for (SoundEntry soundEntry : this.weaponSoundMap.get(weaponSoundType)) {
/* 161 */           (Minecraft.func_71410_x()).field_71441_e.func_184133_a(player, player.func_180425_c(), (SoundEvent)ClientProxy.modSounds.get(soundEntry.soundName), SoundCategory.PLAYERS, 1.0F, 1.0F);
/*     */         }
/*     */       }
/* 164 */       else if (this.allowDefaultSounds && weaponSoundType.defaultSound != null) {
/* 165 */         (Minecraft.func_71410_x()).field_71441_e.func_184133_a(player, player.func_180425_c(), (SoundEvent)ClientProxy.modSounds.get(weaponSoundType.defaultSound), SoundCategory.PLAYERS, 1.0F, 1.0F);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SoundEvent getSound(EntityPlayer player, WeaponSoundType weaponSoundType) {
/* 173 */     if (weaponSoundType != null && 
/* 174 */       this.weaponSoundMap != null) {
/* 175 */       if (this.weaponSoundMap.containsKey(weaponSoundType)) {
/* 176 */         Iterator<SoundEntry> iterator = ((ArrayList)this.weaponSoundMap.get(weaponSoundType)).iterator(); if (iterator.hasNext()) { SoundEntry soundEntry = iterator.next();
/* 177 */           return (SoundEvent)ClientProxy.modSounds.get(soundEntry.soundName); }
/*     */ 
/*     */       
/* 180 */       } else if (this.allowDefaultSounds && weaponSoundType.defaultSound != null) {
/* 181 */         return (SoundEvent)ClientProxy.modSounds.get(weaponSoundType.defaultSound);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 186 */     return null;
/*     */   }
/*     */   
/*     */   public void playSoundPos(BlockPos pos, World world, WeaponSoundType weaponSoundType) {
/* 190 */     playSoundPos(pos, world, weaponSoundType, null, 1.0F);
/*     */   }
/*     */   
/*     */   public void playSoundPos(BlockPos pos, World world, WeaponSoundType weaponSoundType, EntityPlayer excluded, float volume) {
/* 194 */     if (weaponSoundType != null) {
/* 195 */       if (this.weaponSoundMap.containsKey(weaponSoundType)) {
/* 196 */         Random random = new Random();
/* 197 */         for (SoundEntry soundEntry : this.weaponSoundMap.get(weaponSoundType)) {
/* 198 */           int soundRange = ((soundEntry.soundRange != null) ? soundEntry.soundRange : weaponSoundType.defaultRange).intValue();
/* 199 */           for (EntityPlayer hearingPlayer : world.func_175644_a(EntityPlayer.class, e -> (e.func_180425_c().func_185332_f(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p()) <= soundRange)))
/*     */           {
/* 201 */             if (!hearingPlayer.equals(excluded)) {
/* 202 */               ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlaySound(pos, soundEntry.soundName, (soundRange / 16) * soundEntry.soundVolumeMultiplier * volume, random.nextFloat() / soundEntry.soundRandomPitch + soundEntry.soundPitch), (EntityPlayerMP)hearingPlayer);
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/* 207 */       } else if (this.allowDefaultSounds && weaponSoundType.defaultSound != null) {
/* 208 */         Random random = new Random();
/* 209 */         String soundName = weaponSoundType.defaultSound;
/* 210 */         float soundRange = weaponSoundType.defaultRange.intValue();
/* 211 */         for (EntityPlayer hearingPlayer : world.func_175644_a(EntityPlayer.class, e -> (e.func_180425_c().func_185332_f(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p()) <= soundRange))) {
/*     */           
/* 213 */           if (!hearingPlayer.equals(excluded)) {
/* 214 */             ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlaySound(pos, soundName, soundRange / 16.0F * 1.0F, random.nextFloat() / 5.0F + 1.0F), (EntityPlayerMP)hearingPlayer);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(EntityLivingBase entityPlayer, WeaponSoundType weaponSoundType, ItemStack gunStack) {
/* 223 */     playSound(entityPlayer, weaponSoundType, gunStack, null);
/*     */   }
/*     */   
/*     */   public void playSound(EntityLivingBase entityPlayer, WeaponSoundType weaponSoundType, ItemStack gunStack, @Nullable EntityPlayer excluded) {
/* 227 */     if (weaponSoundType != null)
/* 228 */       if (this.weaponSoundMap.containsKey(weaponSoundType)) {
/* 229 */         BlockPos originPos = entityPlayer.func_180425_c();
/* 230 */         World world = entityPlayer.field_70170_p;
/* 231 */         Random random = new Random();
/* 232 */         for (SoundEntry soundEntry : this.weaponSoundMap.get(weaponSoundType)) {
/* 233 */           float soundRange = ((soundEntry.soundRange != null) ? soundEntry.soundRange : weaponSoundType.defaultRange).intValue();
/* 234 */           if (soundEntry.soundNameDistant != null && soundEntry.soundMaxRange != null) {
/* 235 */             int maxSoundRange = soundEntry.soundMaxRange.intValue();
/* 236 */             for (EntityPlayer hearingPlayer : world.func_175644_a(EntityPlayer.class, e -> (e.func_180425_c().func_185332_f(originPos.func_177958_n(), originPos.func_177956_o(), originPos.func_177952_p()) <= maxSoundRange))) {
/* 237 */               if (!hearingPlayer.equals(excluded)) {
/* 238 */                 double distance = hearingPlayer.func_180425_c().func_185332_f(originPos.func_177958_n(), originPos.func_177956_o(), originPos.func_177952_p());
/* 239 */                 float volume = 0.0F;
/* 240 */                 String soundName = "";
/*     */                 
/* 242 */                 if ((((distance > soundRange) ? 1 : 0) & ((distance <= maxSoundRange) ? 1 : 0)) != 0) {
/*     */                   
/* 244 */                   soundName = soundEntry.soundNameDistant;
/* 245 */                   volume = (float)((distance + (maxSoundRange / 6)) / 16.0D * soundEntry.soundFarVolumeMultiplier);
/*     */                 } else {
/*     */                   
/* 248 */                   soundName = soundEntry.soundName;
/* 249 */                   volume = (float)((distance + (maxSoundRange / 6)) / 16.0D * soundEntry.soundVolumeMultiplier);
/*     */                 } 
/*     */ 
/*     */ 
/*     */                 
/* 254 */                 float customPitch = random.nextFloat() / soundEntry.soundRandomPitch + soundEntry.soundPitch;
/* 255 */                 float emptyPitch = 0.05F;
/* 256 */                 float modifyPitch = (ItemGun.getMagazineBullets(gunStack) <= 5 && emptyPitch != 0.0F) ? (0.3F - emptyPitch * ItemGun.getMagazineBullets(gunStack)) : 0.0F;
/* 257 */                 customPitch += modifyPitch;
/* 258 */                 ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlaySound(originPos, soundName, volume, customPitch), (EntityPlayerMP)hearingPlayer);
/*     */               } 
/*     */             }  continue;
/*     */           } 
/* 262 */           for (EntityPlayer hearingPlayer : world.func_175644_a(EntityPlayer.class, e -> (e.func_180425_c().func_185332_f(originPos.func_177958_n(), originPos.func_177956_o(), originPos.func_177952_p()) <= soundRange))) {
/* 263 */             if (!hearingPlayer.equals(excluded))
/*     */             {
/* 265 */               ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlaySound(originPos, soundEntry.soundName, soundRange / 16.0F * soundEntry.soundVolumeMultiplier, random.nextFloat() / soundEntry.soundRandomPitch + soundEntry.soundPitch), (EntityPlayerMP)hearingPlayer);
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       
/* 271 */       } else if (this.allowDefaultSounds && weaponSoundType.defaultSound != null) {
/* 272 */         BlockPos originPos = entityPlayer.func_180425_c();
/* 273 */         World world = entityPlayer.field_70170_p;
/* 274 */         Random random = new Random();
/*     */         
/* 276 */         String soundName = weaponSoundType.defaultSound;
/* 277 */         float soundRange = weaponSoundType.defaultRange.intValue();
/*     */         
/* 279 */         for (EntityPlayer hearingPlayer : world.func_175644_a(EntityPlayer.class, e -> (e.func_180425_c().func_185332_f(originPos.func_177958_n(), originPos.func_177956_o(), originPos.func_177952_p()) <= soundRange)))
/*     */         {
/* 281 */           ModularWarfare.NETWORK.sendTo((PacketBase)new PacketPlaySound(originPos, soundName, soundRange / 16.0F * 1.0F, random.nextFloat() / 5.0F + 1.0F), (EntityPlayerMP)hearingPlayer);
/*     */         }
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\type\BaseType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */