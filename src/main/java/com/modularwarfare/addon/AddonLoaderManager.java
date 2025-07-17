/*     */ package com.modularwarfare.addon;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import net.minecraft.command.CommandHandler;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.event.FMLInitializationEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AddonLoaderManager
/*     */ {
/*  30 */   public List<IContentAddon> addons = new ArrayList<>();
/*  31 */   public Map<String, List<Object>> eventClasses = new HashMap<>();
/*  32 */   public List<ICommand> registeredCommands = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*  36 */   public List<String> loaded_addons = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEventClass(Object eventClass) {
/*  45 */     MinecraftForge.EVENT_BUS.register(eventClass);
/*  46 */     String currentAddon = ((IContentAddon)this.addons.get(this.addons.size() - 1)).getAddonID();
/*     */     
/*  48 */     if (this.eventClasses.containsKey(currentAddon)) {
/*  49 */       ((List<Object>)this.eventClasses.get(currentAddon)).add(eventClass);
/*     */     } else {
/*     */       
/*  52 */       List<Object> newList = new ArrayList();
/*  53 */       newList.add(eventClass);
/*  54 */       this.eventClasses.put(currentAddon, newList);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerCommand(ICommand cmd) {
/*  64 */     CommandHandler ch = (CommandHandler)FMLCommonHandler.instance().getMinecraftServerInstance().func_71187_D();
/*  65 */     ch.func_71560_a(cmd);
/*  66 */     this.registeredCommands.add(cmd);
/*     */   }
/*     */   
/*     */   public boolean isLoaded(String modid) {
/*  70 */     return this.loaded_addons.contains(modid);
/*     */   }
/*     */   
/*     */   public void constructAddons(File dirAddon, Side side) {
/*  74 */     for (File file : dirAddon.listFiles()) {
/*  75 */       if (file.getName().endsWith(".jar")) {
/*     */         try {
/*  77 */           ModularWarfare.LOGGER.info("ModularWarfare >> Trying to load addon: " + file.getName());
/*  78 */           loadAddon(file, side);
/*  79 */         } catch (AddonLoadingException e) {
/*  80 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void constructDevAddons(File dirDevAddon, String mainClass, Side side) {
/*  87 */     ModularWarfare.LOGGER.info("ModularWarfare >> Trying to load addon: " + dirDevAddon.getName());
/*     */     try {
/*  89 */       loadDevAddon(dirDevAddon, mainClass, side);
/*  90 */     } catch (AddonLoadingException e) {
/*  91 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void preInitAddons(FMLPreInitializationEvent event) {
/*  96 */     for (IContentAddon addon : this.addons) {
/*  97 */       ModularWarfare.LOGGER.info("Trying to preInit " + addon.getName());
/*  98 */       addon.preInit(event, ModularWarfare.loaderManager);
/*     */     } 
/*     */   }
/*     */   public void initAddons(FMLInitializationEvent event) {
/* 102 */     for (IContentAddon addon : this.addons) {
/* 103 */       ModularWarfare.LOGGER.info("Trying to init " + addon.getName());
/* 104 */       addon.init(event, ModularWarfare.loaderManager);
/*     */     } 
/*     */   }
/*     */   public void postInitAddons(FMLPostInitializationEvent event) {
/* 108 */     for (IContentAddon addon : this.addons) {
/* 109 */       ModularWarfare.LOGGER.info("Trying to postInit " + addon.getName());
/* 110 */       addon.postInit(event, ModularWarfare.loaderManager);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadAddon(File file, Side side) throws AddonLoadingException {
/* 115 */     if (!file.exists()) {
/* 116 */       throw new AddonLoadingException("File '" + file.getAbsolutePath() + "' doesn't exist, aborting!");
/*     */     }
/* 118 */     JarFile jar = null;
/* 119 */     InputStream inputStream = null;
/* 120 */     String mainClass = null;
/*     */     try {
/* 122 */       jar = new JarFile(file);
/* 123 */       JarEntry entry = jar.getJarEntry("main.mwf");
/* 124 */       if (entry == null) {
/* 125 */         throw new AddonLoadingException("Jar does not contain main.mwf");
/*     */       }
/* 127 */       inputStream = jar.getInputStream(entry);
/* 128 */       mainClass = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
/* 129 */     } catch (IOException ex) {
/* 130 */       ModularWarfare.LOGGER.warn("Could not load main class from main.mwf file in '" + file.getName() + "'\n More info: " + ExceptionUtils.getStackTrace(ex));
/* 131 */       throw new AddonLoadingException("Could not load main class from main.mwf file in '" + file.getName() + "'\n More info in console");
/*     */     } finally {
/* 133 */       if (jar != null) {
/*     */         try {
/* 135 */           jar.close();
/* 136 */           ModularWarfare.LOGGER.warn("[Addon] Closed file !");
/* 137 */         } catch (IOException iOException) {}
/*     */       }
/*     */       
/* 140 */       if (inputStream != null) {
/*     */         try {
/* 142 */           inputStream.close();
/* 143 */         } catch (IOException iOException) {}
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 148 */       if (mainClass == null) {
/* 149 */         throw new AddonLoadingException("Could not load main class from main.mwf file in '" + file.getName() + "'");
/*     */       }
/* 151 */       ModularWarfare.LOADER.loadFile(file, mainClass, side);
/* 152 */     } catch (MalformedURLException mue) {
/* 153 */       ModularWarfare.LOGGER.warn("File path provided '" + file.getAbsolutePath() + "' threw an exception (MalformedURLException)\n More info: " + ExceptionUtils.getStackTrace(mue));
/* 154 */       throw new AddonLoadingException("File path provided threw an exception (MalformedURLException). More info in console.");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadDevAddon(File file, String mainClass, Side side) throws AddonLoadingException {
/* 159 */     if (!file.exists()) {
/* 160 */       throw new AddonLoadingException("File '" + file.getAbsolutePath() + "' doesn't exist, aborting!");
/*     */     }
/*     */     try {
/* 163 */       if (mainClass == null) {
/* 164 */         throw new AddonLoadingException("Could not load main class from main.mwf file in '" + file.getName() + "'");
/*     */       }
/* 166 */       ModularWarfare.LOADER.loadFile(file, mainClass, side);
/* 167 */     } catch (MalformedURLException mue) {
/* 168 */       ModularWarfare.LOGGER.warn("File path provided '" + file.getAbsolutePath() + "' threw an exception (MalformedURLException)\n More info: " + ExceptionUtils.getStackTrace(mue));
/* 169 */       throw new AddonLoadingException("File path provided threw an exception (MalformedURLException). More info in console.");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\addon\AddonLoaderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */