/*    */ package com.modularwarfare.addon;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import java.io.File;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URLClassLoader;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*    */ import net.minecraftforge.fml.client.FMLClientHandler;
/*    */ import net.minecraftforge.fml.common.FMLModContainer;
/*    */ import net.minecraftforge.fml.common.MetadataCollection;
/*    */ import net.minecraftforge.fml.common.ModContainer;
/*    */ import net.minecraftforge.fml.common.discovery.ContainerType;
/*    */ import net.minecraftforge.fml.common.discovery.ModCandidate;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*    */ import org.apache.logging.log4j.Level;
/*    */ 
/*    */ public class LibClassLoader
/*    */   extends URLClassLoader
/*    */ {
/*    */   private LaunchClassLoader parentCL;
/*    */   
/*    */   public LibClassLoader(ClassLoader parentLoader) {
/* 25 */     super(new java.net.URL[0], parentLoader);
/* 26 */     this.parentCL = (LaunchClassLoader)parentLoader;
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadFile(File libFile, String main, Side side) throws MalformedURLException, AddonLoadingException {
/* 31 */     IContentAddon addon = null;
/* 32 */     this.parentCL.addURL(libFile.toURI().toURL());
/*    */     
/*    */     try {
/* 35 */       Class<?> mainClass = Class.forName(main, true, (ClassLoader)this.parentCL);
/*    */       
/*    */       try {
/* 38 */         addon = (IContentAddon)mainClass.newInstance();
/*    */         
/* 40 */         if (ModularWarfare.loaderManager.addons.contains(addon.getAddonID())) {
/* 41 */           throw new AddonLoadingException("Addon '" + addon.getAddonID() + "' is already loaded!");
/*    */         }
/* 43 */         ModularWarfare.LOGGER.log(Level.INFO, "ModularWarfare >> Loading " + addon.getName());
/* 44 */         ModularWarfare.loaderManager.addons.add(addon);
/* 45 */         ModularWarfare.loaderManager.loaded_addons.add(addon.getAddonID());
/* 46 */         if (side.isClient()) {
/* 47 */           ModularWarfare.LOGGER.info("Attempting to load container mod for " + addon.getAddonID());
/* 48 */           HashMap<String, Object> map = new HashMap<>();
/* 49 */           map.put("modid", addon.getAddonID());
/* 50 */           map.put("name", addon.getName());
/* 51 */           map.put("version", "1");
/* 52 */           FMLModContainer container = new FMLModContainer("com.modularwarfare", new ModCandidate(libFile, libFile, libFile.isDirectory() ? ContainerType.DIR : ContainerType.JAR), map);
/* 53 */           container.bindMetadata(MetadataCollection.from(null, ""));
/* 54 */           FMLClientHandler.instance().addModAsResource((ModContainer)container);
/*    */         } 
/* 56 */         addon.construct(side, ModularWarfare.loaderManager);
/* 57 */       } catch (ClassCastException cce) {
/* 58 */         ModularWarfare.LOGGER.warn("Main class '" + mainClass + "' in lib '" + libFile.getName() + "' does not implement required IContentAddon class!");
/* 59 */       } catch (InstantiationException ie) {
/* 60 */         ModularWarfare.LOGGER.warn("Main class '" + mainClass + "' in lib '" + libFile.getName() + "' could not be instatiated!");
/* 61 */       } catch (IllegalAccessException iae) {
/* 62 */         ModularWarfare.LOGGER.warn("Main class '" + mainClass + "' in lib '" + libFile.getName() + "' is inaccessible!");
/* 63 */       } catch (AddonLoadingException ale) {
/* 64 */         throw ale;
/* 65 */       } catch (Exception e) {
/* 66 */         ModularWarfare.LOGGER.warn("Unknow exception has been caught while loading '\" + libFile.getName() + \"'. More info: " + ExceptionUtils.getStackTrace(e));
/* 67 */         throw new AddonLoadingException("Unknow exception has been caught while loading '" + libFile.getName() + "'. More info in console");
/*    */       }
/*    */     
/* 70 */     } catch (ClassNotFoundException e) {
/* 71 */       ModularWarfare.LOGGER.warn("No main class '" + main + "' defined in main.mwf found in lib '" + libFile.getName() + "'!");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\addon\LibClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */