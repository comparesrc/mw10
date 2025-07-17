/*     */ package com.modularwarfare.common;
/*     */ 
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.guns.SkinType;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketParticle;
/*     */ import com.modularwarfare.common.textures.TextureType;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import com.modularwarfare.utility.MWSound;
/*     */ import com.modularwarfare.utility.event.ForgeEvent;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ import mchhui.hebridge.HEBridge;
/*     */ import net.lingala.zip4j.core.ZipFile;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.event.FMLConstructionEvent;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry;
/*     */ import net.minecraftforge.fml.relauncher.FMLInjectionData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommonProxy
/*     */   extends ForgeEvent
/*     */ {
/*  41 */   public static Pattern zipJar = Pattern.compile("(.+).(zip|jar)$");
/*     */   
/*     */   public static File modularWarfareDir;
/*     */   
/*  45 */   public static HashMap<SkinType, BaseType> preloadSkinTypes = new HashMap<>();
/*  46 */   public static HashSet<TextureType> preloadFlashTex = new HashSet<>();
/*     */ 
/*     */   
/*     */   public void construction(FMLConstructionEvent event) {
/*  50 */     this; modularWarfareDir = new File(getGameFolder(), "ModularWarfare");
/*  51 */     File modFile = null;
/*     */ 
/*     */     
/*  54 */     ModularWarfare.MOD_DIR = modularWarfareDir;
/*  55 */     if (!ModularWarfare.MOD_DIR.exists()) {
/*  56 */       ModularWarfare.MOD_DIR.mkdir();
/*     */     }
/*  58 */     new ModConfig(new File(ModularWarfare.MOD_DIR, "mod_config.json"));
/*     */     
/*  60 */     ModularWarfare.DEV_ENV = ModConfig.INSTANCE.dev_mode;
/*     */     
/*  62 */     for (File source : (new File(modularWarfareDir.getParentFile(), "mods")).listFiles()) {
/*  63 */       if (source.getName().contains("modularwarfare")) {
/*  64 */         modFile = source;
/*     */       }
/*     */     } 
/*     */   }
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
/*     */   public void preload() {}
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
/*     */   public void load() {
/* 114 */     HEBridge.init();
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */ 
/*     */   
/*     */   public void forceReload() {}
/*     */   
/*     */   @Nonnull
/*     */   public static String getGameFolder() {
/* 125 */     return ((File)FMLInjectionData.data()[6]).getAbsolutePath();
/*     */   }
/*     */   
/*     */   public List<File> getContentList() {
/* 129 */     List<File> contentPacks = new ArrayList<>();
/* 130 */     for (File file : ModularWarfare.MOD_DIR.listFiles()) {
/* 131 */       if (!file.getName().contains("cache") && !file.getName().contains("officialmw") && !file.getName().contains("highres")) {
/* 132 */         if (file.isDirectory()) {
/* 133 */           contentPacks.add(file);
/* 134 */         } else if (zipJar.matcher(file.getName()).matches()) {
/*     */           try {
/* 136 */             ZipFile zipFile = new ZipFile(file);
/* 137 */             if (!zipFile.isEncrypted()) {
/* 138 */               contentPacks.add(file);
/*     */             } else {
/* 140 */               ModularWarfare.LOGGER.info("[WARNING] ModularWarfare can't load encrypted content-packs in server-side (" + file.getName() + ") !");
/*     */             } 
/* 142 */           } catch (ZipException e) {
/* 143 */             e.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 148 */     ModularWarfare.LOGGER.info("Loaded content pack list server side.");
/* 149 */     return contentPacks;
/*     */   }
/*     */   
/*     */   public <T> T loadModel(String s, String shortName, Class<T> typeClass) {
/* 153 */     return null;
/*     */   }
/*     */   
/*     */   public void spawnExplosionParticle(World world, double x, double y, double z) {
/* 157 */     if (!world.field_72995_K) {
/* 158 */       ModularWarfare.NETWORK.sendToAllAround((PacketBase)new PacketParticle(PacketParticle.ParticleType.EXPLOSION, x, y, z), new NetworkRegistry.TargetPoint(world.field_73011_w.getDimension(), x, y, z, 64.0D));
/*     */     }
/*     */   }
/*     */   
/*     */   public void spawnRocketParticle(World world, double x, double y, double z) {
/* 163 */     if (!world.field_72995_K) {
/* 164 */       ModularWarfare.NETWORK.sendToAllAround((PacketBase)new PacketParticle(PacketParticle.ParticleType.ROCKET, x, y, z), new NetworkRegistry.TargetPoint(world.field_73011_w.getDimension(), x, y, z, 64.0D));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadModels(boolean reloadSkins) {}
/*     */ 
/*     */   
/*     */   public void generateJsonModels(ArrayList<BaseType> types) {}
/*     */ 
/*     */   
/*     */   public void generateJsonSounds(Collection<BaseType> types, boolean replace) {}
/*     */ 
/*     */   
/*     */   public void generateLangFiles(ArrayList<BaseType> types, boolean replace) {}
/*     */ 
/*     */   
/*     */   public void playSound(MWSound sound) {}
/*     */ 
/*     */   
/*     */   public void playHitmarker(boolean headshot) {}
/*     */ 
/*     */   
/*     */   public void registerSound(String soundName) {}
/*     */ 
/*     */   
/*     */   public void onShootAnimation(EntityPlayer player, String wepType, int fireTickDelay, float recoilPitch, float recoilYaw) {}
/*     */ 
/*     */   
/*     */   public void onReloadAnimation(EntityPlayer player, String wepType, int reloadTime, int reloadCount, int reloadType) {}
/*     */ 
/*     */   
/*     */   public void onShootFailedAnimation(EntityPlayer player, String wepType) {}
/*     */ 
/*     */   
/*     */   public void onModeChangeAnimation(EntityPlayer player, String wepType) {}
/*     */ 
/*     */   
/*     */   public World getClientWorld() {
/* 203 */     return null;
/*     */   }
/*     */   
/*     */   public void addBlood(EntityLivingBase living, int amount) {}
/*     */   
/*     */   public void addBlood(EntityLivingBase living, int amount, boolean onhit) {}
/*     */   
/*     */   public void registerEventHandlers() {}
/*     */   
/*     */   public void resetSens() {}
/*     */   
/*     */   public void playFlashSound(EntityPlayer player) {}
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\CommonProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */