/*     */ package com.modularwarfare;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.modularwarfare.addon.AddonLoaderManager;
/*     */ import com.modularwarfare.addon.LibClassLoader;
/*     */ import com.modularwarfare.api.ItemRegisterEvent;
/*     */ import com.modularwarfare.client.customplayer.CPEventHandler;
/*     */ import com.modularwarfare.client.customplayer.CustomPlayerConfig;
/*     */ import com.modularwarfare.client.fpp.enhanced.AnimationType;
/*     */ import com.modularwarfare.common.CommonProxy;
/*     */ import com.modularwarfare.common.MWTab;
/*     */ import com.modularwarfare.common.armor.ItemMWArmor;
/*     */ import com.modularwarfare.common.armor.ItemSpecialArmor;
/*     */ import com.modularwarfare.common.backpacks.ItemBackpack;
/*     */ import com.modularwarfare.common.commands.CommandClear;
/*     */ import com.modularwarfare.common.commands.CommandDebug;
/*     */ import com.modularwarfare.common.commands.CommandNBT;
/*     */ import com.modularwarfare.common.commands.CommandPlay;
/*     */ import com.modularwarfare.common.commands.kits.CommandKit;
/*     */ import com.modularwarfare.common.entity.decals.EntityBulletHole;
/*     */ import com.modularwarfare.common.entity.decals.EntityShell;
/*     */ import com.modularwarfare.common.entity.grenades.EntityGrenade;
/*     */ import com.modularwarfare.common.entity.grenades.EntitySmokeGrenade;
/*     */ import com.modularwarfare.common.entity.grenades.EntityStunGrenade;
/*     */ import com.modularwarfare.common.entity.item.EntityItemLoot;
/*     */ import com.modularwarfare.common.extra.ItemLight;
/*     */ import com.modularwarfare.common.grenades.ItemGrenade;
/*     */ import com.modularwarfare.common.guns.ItemAmmo;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.guns.ItemBullet;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.ItemSpray;
/*     */ import com.modularwarfare.common.guns.SkinType;
/*     */ import com.modularwarfare.common.handler.CommonEventHandler;
/*     */ import com.modularwarfare.common.handler.GuiHandler;
/*     */ import com.modularwarfare.common.handler.ServerTickHandler;
/*     */ import com.modularwarfare.common.hitbox.playerdata.PlayerDataHandler;
/*     */ import com.modularwarfare.common.network.NetworkHandler;
/*     */ import com.modularwarfare.common.textures.TextureType;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import com.modularwarfare.common.type.ContentTypes;
/*     */ import com.modularwarfare.common.type.TypeEntry;
/*     */ import com.modularwarfare.raycast.DefaultRayCasting;
/*     */ import com.modularwarfare.raycast.RayCasting;
/*     */ import com.modularwarfare.script.ScriptHost;
/*     */ import com.modularwarfare.utility.GSONUtils;
/*     */ import com.modularwarfare.utility.ModUtil;
/*     */ import com.modularwarfare.utility.ZipContentPack;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import moe.komi.mwprotect.IZip;
/*     */ import moe.komi.mwprotect.IZipEntry;
/*     */ import moe.komi.mwprotect.LegacyZip;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.RegistryEvent;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.Mod;
/*     */ import net.minecraftforge.fml.common.Mod.EventHandler;
/*     */ import net.minecraftforge.fml.common.Mod.Instance;
/*     */ import net.minecraftforge.fml.common.SidedProxy;
/*     */ import net.minecraftforge.fml.common.event.FMLConstructionEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLInitializationEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.network.IGuiHandler;
/*     */ import net.minecraftforge.fml.common.registry.EntityEntry;
/*     */ import net.minecraftforge.fml.common.registry.EntityRegistry;
/*     */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ @Mod(modid = "modularwarfare", name = "ModularWarfare", version = "2023.2.4.4f")
/*     */ public class ModularWarfare {
/*  99 */   public static final String MOD_PREFIX = TextFormatting.GRAY + "[" + TextFormatting.RED + "ModularWarfare" + TextFormatting.GRAY + "]" + TextFormatting.GRAY;
/*     */   
/*     */   public static final String MOD_ID = "modularwarfare";
/*     */   
/*     */   public static final String MOD_NAME = "ModularWarfare";
/*     */   
/*     */   public static final String MOD_VERSION = "2023.2.4.4f";
/*     */   
/*     */   @Instance("modularwarfare")
/*     */   public static ModularWarfare INSTANCE;
/*     */   
/*     */   @SidedProxy(clientSide = "com.modularwarfare.client.ClientProxy", serverSide = "com.modularwarfare.common.CommonProxy")
/*     */   public static CommonProxy PROXY;
/*     */   
/*     */   public static boolean DEV_ENV = true;
/*     */   public static Logger LOGGER;
/*     */   public static NetworkHandler NETWORK;
/* 116 */   public static PlayerDataHandler PLAYERHANDLER = new PlayerDataHandler();
/*     */   
/* 118 */   public static Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/*     */   
/* 120 */   public static HashMap<String, ZipContentPack> zipContentsPack = new HashMap<>();
/*     */   
/*     */   public static File MOD_DIR;
/*     */   
/* 124 */   public static List<File> contentPacks = new ArrayList<>();
/*     */ 
/*     */   
/* 127 */   public static HashMap<String, ItemGun> gunTypes = new HashMap<>();
/* 128 */   public static HashMap<String, ItemAmmo> ammoTypes = new HashMap<>();
/* 129 */   public static HashMap<String, ItemAttachment> attachmentTypes = new HashMap<>();
/* 130 */   public static LinkedHashMap<String, ItemMWArmor> armorTypes = new LinkedHashMap<>();
/* 131 */   public static LinkedHashMap<String, ItemSpecialArmor> specialArmorTypes = new LinkedHashMap<>();
/* 132 */   public static HashMap<String, ItemBullet> bulletTypes = new HashMap<>();
/* 133 */   public static HashMap<String, ItemSpray> sprayTypes = new HashMap<>();
/* 134 */   public static HashMap<String, ItemBackpack> backpackTypes = new HashMap<>();
/* 135 */   public static HashMap<String, ItemGrenade> grenadeTypes = new HashMap<>();
/* 136 */   public static HashMap<String, TextureType> textureTypes = new HashMap<>();
/*     */   
/* 138 */   public static ArrayList<BaseType> baseTypes = new ArrayList<>();
/*     */   
/* 140 */   public static ArrayList<String> contentPackHashList = new ArrayList<>();
/*     */   
/*     */   public static boolean usingDirectoryContentPack = false;
/* 143 */   public static HashMap<String, MWTab> MODS_TABS = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public RayCasting RAY_CASTING;
/*     */ 
/*     */   
/* 150 */   public static final LibClassLoader LOADER = new LibClassLoader(ModularWarfare.class.getClassLoader());
/*     */   
/*     */   public static File addonDir;
/*     */   
/*     */   public static AddonLoaderManager loaderManager;
/*     */   
/*     */   public static boolean isLoadedModularMovements = false;
/*     */ 
/*     */   
/*     */   public static IZip getiZip(File file) throws IOException {
/*     */     LegacyZip legacyZip;
/*     */     try {
/* 162 */       Class<?> protectorClass = Class.forName("moe.komi.mwprotect.ProtectZip");
/* 163 */       Constructor<?> constructor = protectorClass.getConstructor(new Class[] { File.class });
/* 164 */       IZip izip = (IZip)constructor.newInstance(new Object[] { file });
/* 165 */     } catch (ClassNotFoundException|NoSuchMethodException|InstantiationException|IllegalAccessException|InvocationTargetException e) {
/*     */       
/* 167 */       if (e instanceof InvocationTargetException) {
/* 168 */         ((InvocationTargetException)e).getTargetException().printStackTrace();
/*     */       }
/* 170 */       legacyZip = new LegacyZip(file);
/*     */     } 
/* 172 */     return (IZip)legacyZip;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadContent() {
/* 177 */     usingDirectoryContentPack = false;
/* 178 */     for (File file : contentPacks) {
/* 179 */       if (!file.isDirectory()) {
/*     */         
/*     */         try {
/* 182 */           FileInputStream inputStream = new FileInputStream(file);
/* 183 */           MessageDigest md = MessageDigest.getInstance("MD5");
/* 184 */           byte[] buffer = new byte[1024];
/* 185 */           int length = -1;
/* 186 */           while ((length = inputStream.read(buffer, 0, 1024)) != -1) {
/* 187 */             md.update(buffer, 0, length);
/*     */           }
/* 189 */           String md5 = "";
/* 190 */           for (byte b : md.digest()) {
/* 191 */             md5 = md5 + b;
/*     */           }
/* 193 */           contentPackHashList.add(md5);
/* 194 */           inputStream.close();
/* 195 */         } catch (IOException|java.security.NoSuchAlgorithmException e) {
/*     */           
/* 197 */           e.printStackTrace();
/*     */         }  continue;
/*     */       } 
/* 200 */       usingDirectoryContentPack = true;
/*     */     } 
/*     */     
/* 203 */     for (File file : contentPacks) {
/* 204 */       if (!MODS_TABS.containsKey(file.getName())) {
/* 205 */         MODS_TABS.put(file.getName(), new MWTab(file.getName()));
/*     */       }
/* 207 */       if (CommonProxy.zipJar.matcher(file.getName()).matches() && 
/* 208 */         !zipContentsPack.containsKey(file.getName())) {
/*     */         try {
/*     */           LegacyZip legacyZip;
/* 211 */           if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
/* 212 */             IZip izip = getiZip(file);
/*     */           } else {
/* 214 */             legacyZip = new LegacyZip(file);
/*     */           } 
/* 216 */           ZipContentPack zipContentPack = new ZipContentPack(file.getName(), legacyZip.getFileList(), (IZip)legacyZip);
/* 217 */           zipContentsPack.put(file.getName(), zipContentPack);
/* 218 */           LOGGER.info("Registered content pack");
/* 219 */         } catch (IOException e) {
/* 220 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 225 */     getTypeFiles(contentPacks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadContentPacks(boolean reload) {
/* 233 */     loadContent();
/*     */     
/* 235 */     if (DEV_ENV) {
/* 236 */       PROXY.generateJsonModels(baseTypes);
/*     */     }
/*     */     
/* 239 */     for (TextureType type : textureTypes.values()) {
/* 240 */       type.loadExtraValues();
/*     */     }
/*     */     
/* 243 */     for (BaseType baseType : baseTypes) {
/* 244 */       baseType.loadExtraValues();
/* 245 */       ((TypeEntry)ContentTypes.values.get(baseType.id)).typeAssignFunction.accept(baseType, Boolean.valueOf(reload));
/*     */     } 
/*     */     
/* 248 */     if (DEV_ENV) {
/* 249 */       if (reload) {
/*     */         return;
/*     */       }
/* 252 */       PROXY.generateLangFiles(baseTypes, DEV_ENV);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T getRenderConfig(BaseType baseType, Class<T> typeClass) {
/* 260 */     if (baseType.isInDirectory) {
/*     */       try {
/* 262 */         File contentPackDir = new File(MOD_DIR, baseType.contentPack);
/* 263 */         if (contentPackDir.exists() && contentPackDir.isDirectory()) {
/* 264 */           File renderConfig = new File(contentPackDir, "/" + baseType.getAssetDir() + "/render");
/* 265 */           File typeRender = new File(renderConfig, baseType.internalName + ".render.json");
/* 266 */           JsonReader jsonReader = new JsonReader(new FileReader(typeRender));
/* 267 */           return (T)GSONUtils.fromJson(gson, jsonReader, typeClass, baseType.internalName + ".render.json");
/*     */         } 
/* 269 */       } catch (JsonParseException e) {
/* 270 */         e.printStackTrace();
/* 271 */       } catch (FileNotFoundException e) {
/* 272 */         e.printStackTrace();
/* 273 */       } catch (com.modularwarfare.client.fpp.enhanced.AnimationType.AnimationTypeJsonAdapter.AnimationTypeException err) {
/* 274 */         LOGGER.info(baseType.internalName + " was loaded. But something was wrong.");
/* 275 */         err.printStackTrace();
/*     */       }
/*     */     
/* 278 */     } else if (zipContentsPack.containsKey(baseType.contentPack)) {
/* 279 */       String typeName = baseType.getAssetDir();
/*     */       
/* 281 */       IZipEntry foundFile = ((ZipContentPack)zipContentsPack.get(baseType.contentPack)).fileHeaders.stream().filter(fileHeader -> (fileHeader.getFileName().startsWith(typeName + "/render/") && fileHeader.getFileName().replace(typeName + "/render/", "").equalsIgnoreCase(baseType.internalName + ".render.json"))).findFirst().orElse(null);
/* 282 */       if (foundFile != null) {
/*     */         try {
/* 284 */           InputStream stream = foundFile.getInputStream();
/* 285 */           JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
/* 286 */           return (T)GSONUtils.fromJson(gson, jsonReader, typeClass, baseType.internalName + ".render.json");
/* 287 */         } catch (JsonParseException|IOException e) {
/* 288 */           e.printStackTrace();
/* 289 */         } catch (com.modularwarfare.client.fpp.enhanced.AnimationType.AnimationTypeJsonAdapter.AnimationTypeException err) {
/* 290 */           LOGGER.info(baseType.internalName + " was loaded. But something was wrong.");
/* 291 */           err.printStackTrace();
/*     */         } 
/*     */       } else {
/* 294 */         LOGGER.info(baseType.internalName + ".render.json not found. Aborting");
/*     */       } 
/*     */     } 
/*     */     
/* 298 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getTypeFiles(List<File> contentPacks) {
/* 307 */     ScriptHost.INSTANCE.reset();
/*     */     
/* 309 */     for (File file : contentPacks) {
/* 310 */       if (!file.getName().contains("cache")) {
/* 311 */         if (file.isDirectory()) {
/* 312 */           for (TypeEntry type : ContentTypes.values) {
/* 313 */             File subFolder = new File(file, "/" + type.name + "/");
/* 314 */             if (subFolder.exists()) {
/* 315 */               for (File typeFile : subFolder.listFiles()) {
/*     */                 try {
/* 317 */                   if (typeFile.isFile()) {
/* 318 */                     JsonReader jsonReader = new JsonReader(new FileReader(typeFile));
/* 319 */                     BaseType parsedType = (BaseType)GSONUtils.fromJson(gson, jsonReader, type.typeClass, typeFile.getName());
/*     */                     
/* 321 */                     parsedType.id = type.id;
/* 322 */                     parsedType.contentPack = file.getName();
/* 323 */                     parsedType.isInDirectory = true;
/* 324 */                     baseTypes.add(parsedType);
/*     */                     
/* 326 */                     if (parsedType instanceof TextureType) {
/* 327 */                       textureTypes.put(parsedType.internalName, (TextureType)parsedType);
/*     */                     }
/*     */                   } 
/* 330 */                 } catch (JsonParseException ex) {
/* 331 */                   ex.printStackTrace();
/*     */                 }
/* 333 */                 catch (FileNotFoundException exception) {
/* 334 */                   exception.printStackTrace();
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 342 */           File scriptFolder = new File(file, "/sciprt/");
/* 343 */           if (scriptFolder.exists()) {
/* 344 */             for (File typeFile : scriptFolder.listFiles()) {
/* 345 */               if (typeFile.getName().endsWith(".js")) {
/* 346 */                 String text = "";
/*     */                 try {
/* 348 */                   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
/*     */                   String temp;
/* 350 */                   while ((temp = bufferedReader.readLine()) != null) {
/* 351 */                     text = text + temp;
/*     */                   }
/* 353 */                   bufferedReader.close();
/* 354 */                   ScriptHost.INSTANCE.initScript(new ResourceLocation("modularwarfare", "script/" + typeFile.getName() + ".js"), text);
/* 355 */                 } catch (IOException e) {
/*     */                   
/* 357 */                   e.printStackTrace();
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 368 */           CPEventHandler.cpConfig.clear();
/* 369 */           File cpFolder = new File(file, "/customplayer/");
/* 370 */           if (cpFolder.exists()) {
/* 371 */             for (File typeFile : cpFolder.listFiles()) {
/* 372 */               System.out.println("test1:" + typeFile.getName());
/* 373 */               if (typeFile.getName().endsWith(".json")) {
/* 374 */                 String text = "";
/*     */                 try {
/* 376 */                   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
/*     */                   String temp;
/* 378 */                   while ((temp = bufferedReader.readLine()) != null) {
/* 379 */                     text = text + temp;
/*     */                   }
/* 381 */                   bufferedReader.close();
/* 382 */                   CustomPlayerConfig cp = (CustomPlayerConfig)gson.fromJson(text, CustomPlayerConfig.class);
/* 383 */                   CPEventHandler.cpConfig.put(cp.name, cp);
/* 384 */                 } catch (IOException e) {
/*     */                   
/* 386 */                   e.printStackTrace();
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 395 */         if (zipContentsPack.containsKey(file.getName())) {
/* 396 */           for (IZipEntry fileHeader : ((ZipContentPack)zipContentsPack.get(file.getName())).fileHeaders) {
/* 397 */             for (TypeEntry type : ContentTypes.values) {
/* 398 */               String str1 = fileHeader.getFileName();
/* 399 */               String typeName = type.toString();
/* 400 */               if (str1.startsWith(typeName + "/") && (str1.split(typeName + "/")).length > 1 && str1.split(typeName + "/")[1].length() > 0 && !str1.contains("render")) {
/* 401 */                 InputStream stream = null;
/*     */                 try {
/* 403 */                   stream = fileHeader.getInputStream();
/* 404 */                   JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
/*     */                   
/*     */                   try {
/* 407 */                     BaseType parsedType = (BaseType)GSONUtils.fromJson(gson, jsonReader, type.typeClass, fileHeader.getFileName());
/* 408 */                     if (parsedType.internalName.equals("siz_bg.scope_win94_texture")) {
/* 409 */                       FMLLog.log.info("found - " + parsedType.internalName + " - " + file.getName() + " - " + fileHeader.getFileName() + " - " + fileHeader.getHandle());
/*     */                     }
/* 411 */                     parsedType.id = type.id;
/* 412 */                     parsedType.contentPack = file.getName();
/* 413 */                     parsedType.isInDirectory = false;
/* 414 */                     baseTypes.add(parsedType);
/*     */                     
/* 416 */                     if (parsedType instanceof TextureType) {
/* 417 */                       textureTypes.put(parsedType.internalName, (TextureType)parsedType);
/*     */                     }
/* 419 */                   } catch (JsonParseException ex) {}
/*     */                 
/*     */                 }
/* 422 */                 catch (IOException e) {
/* 423 */                   e.printStackTrace();
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 430 */             String zipName = fileHeader.getFileName();
/* 431 */             if (zipName.startsWith("script/") && zipName.endsWith(".js")) {
/* 432 */               String typeFile = zipName.replaceFirst("script/", "").replace(".js", "");
/* 433 */               String text = "";
/*     */               try {
/* 435 */                 InputStream inputStream = fileHeader.getInputStream();
/* 436 */                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
/*     */                 String temp;
/* 438 */                 while ((temp = bufferedReader.readLine()) != null) {
/* 439 */                   text = text + temp;
/*     */                 }
/* 441 */                 bufferedReader.close();
/* 442 */                 ScriptHost.INSTANCE.initScript(new ResourceLocation("modularwarfare", "script/" + typeFile + ".js"), text);
/* 443 */               } catch (IOException e) {
/*     */                 
/* 445 */                 e.printStackTrace();
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 454 */             zipName = fileHeader.getFileName();
/* 455 */             if (zipName.startsWith("customplayer/") && zipName.endsWith(".json")) {
/* 456 */               String typeFile = zipName.replaceFirst("customplayer/", "").replace(".json", "");
/* 457 */               String text = "";
/*     */               try {
/* 459 */                 InputStream inputStream = fileHeader.getInputStream();
/* 460 */                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
/*     */                 String temp;
/* 462 */                 while ((temp = bufferedReader.readLine()) != null) {
/* 463 */                   text = text + temp;
/*     */                 }
/* 465 */                 bufferedReader.close();
/* 466 */                 CustomPlayerConfig cp = (CustomPlayerConfig)gson.fromJson(text, CustomPlayerConfig.class);
/* 467 */                 CPEventHandler.cpConfig.put(cp.name, cp);
/* 468 */               } catch (IOException e) {
/*     */                 
/* 470 */                 e.printStackTrace();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
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
/*     */   @EventHandler
/*     */   public void onPreInitialization(FMLPreInitializationEvent event) {
/* 494 */     if (getClass().getClassLoader() instanceof LaunchClassLoader) {
/* 495 */       LaunchClassLoader loader = (LaunchClassLoader)getClass().getClassLoader();
/* 496 */       loader.addTransformerExclusion("com.fasterxml.jackson.");
/*     */       
/*     */       try {
/* 499 */         Field f = LaunchClassLoader.class.getDeclaredField("invalidClasses");
/* 500 */         f.setAccessible(true);
/* 501 */         Set<String> invalidClasses = (Set<String>)f.get(getClass().getClassLoader());
/* 502 */         invalidClasses.remove("com.fasterxml.jackson.databind.ObjectMapper");
/* 503 */       } catch (SecurityException|NoSuchFieldException|IllegalArgumentException|IllegalAccessException e1) {
/*     */         
/* 505 */         e1.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 509 */     PROXY.preload();
/*     */     
/* 511 */     if (FMLCommonHandler.instance().getSide().isServer()) {
/*     */       
/* 513 */       MOD_DIR = new File(event.getModConfigurationDirectory().getParentFile(), "ModularWarfare");
/* 514 */       if (!MOD_DIR.exists()) {
/* 515 */         MOD_DIR.mkdir();
/* 516 */         LOGGER.info("Created ModularWarfare folder, it's recommended to install content packs.");
/* 517 */         LOGGER.info("As the mod itself doesn't come with any content.");
/*     */       } 
/* 519 */       loadConfig();
/* 520 */       DEV_ENV = true;
/*     */       
/* 522 */       contentPacks = PROXY.getContentList();
/*     */     } 
/*     */     
/* 525 */     registerRayCasting((RayCasting)new DefaultRayCasting());
/* 526 */     this; loaderManager.preInitAddons(event);
/*     */ 
/*     */     
/* 529 */     ContentTypes.registerTypes();
/* 530 */     loadContentPacks(false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 535 */     PROXY.registerEventHandlers();
/*     */     
/* 537 */     MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
/* 538 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadConfig() {
/* 543 */     new ModConfig(new File(MOD_DIR, "mod_config.json"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInitialization(FMLInitializationEvent event) {
/* 553 */     new ServerTickHandler();
/*     */     
/* 555 */     PROXY.load();
/*     */     
/* 557 */     boolean bukkitOnline = false;
/*     */     try {
/* 559 */       Class.forName("org.bukkit.Bukkit");
/* 560 */       bukkitOnline = true;
/* 561 */     } catch (ClassNotFoundException classNotFoundException) {}
/*     */ 
/*     */     
/* 564 */     if (bukkitOnline) {
/* 565 */       MinecraftForge.EVENT_BUS.register(BukkitHelper.class);
/*     */     }
/*     */     
/* 568 */     NETWORK = new NetworkHandler();
/* 569 */     NETWORK.initialise();
/* 570 */     NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, (IGuiHandler)new GuiHandler());
/* 571 */     this; loaderManager.initAddons(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPostInitialization(FMLPostInitializationEvent event) {
/* 581 */     NETWORK.postInitialise();
/* 582 */     PROXY.init();
/* 583 */     this; loaderManager.postInitAddons(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onServerStarting(FMLServerStartingEvent event) {
/* 593 */     event.registerServerCommand((ICommand)new CommandClear());
/* 594 */     event.registerServerCommand((ICommand)new CommandNBT());
/* 595 */     event.registerServerCommand((ICommand)new CommandDebug());
/* 596 */     event.registerServerCommand((ICommand)new CommandKit());
/* 597 */     event.registerServerCommand((ICommand)new CommandPlay());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void constructionEvent(FMLConstructionEvent event) {
/* 607 */     LOGGER = LogManager.getLogger("modularwarfare");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 612 */     this; addonDir = new File(ModUtil.getGameFolder() + "/addons_mwf_shining");
/*     */     
/* 614 */     this; if (!addonDir.exists()) {
/* 615 */       this; addonDir.mkdirs();
/* 616 */     }  this; loaderManager = new AddonLoaderManager();
/* 617 */     this; this; loaderManager.constructAddons(addonDir, event.getSide());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 623 */     if (ModUtil.isIDE()) {
/* 624 */       File file = (new File(ModUtil.getGameFolder())).getParentFile().getParentFile();
/* 625 */       String folder = file.toString().replace("\\", "/");
/* 626 */       this; loaderManager.constructDevAddons(new File(folder + "/melee-addon/build/classes/java/main"), "com.modularwarfare.melee.ModularWarfareMelee", event.getSide());
/*     */     } 
/*     */     
/* 629 */     PROXY.construction(event);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void registerItems(RegistryEvent.Register<Item> event) {
/* 634 */     for (File file : contentPacks) {
/* 635 */       List<Item> tabOrder = new ArrayList<>();
/* 636 */       for (ItemGun itemGun : gunTypes.values()) {
/* 637 */         if (itemGun.type.contentPack.equals(file.getName())) {
/* 638 */           event.getRegistry().register((IForgeRegistryEntry)itemGun);
/* 639 */           tabOrder.add(itemGun);
/*     */         } 
/*     */       } 
/* 642 */       for (ItemAmmo itemAmmo : ammoTypes.values()) {
/* 643 */         if (itemAmmo.type.contentPack.equals(file.getName())) {
/* 644 */           event.getRegistry().register((IForgeRegistryEntry)itemAmmo);
/* 645 */           tabOrder.add(itemAmmo);
/*     */         } 
/*     */       } 
/* 648 */       for (ItemBullet itemBullet : bulletTypes.values()) {
/* 649 */         if (itemBullet.type.contentPack.equals(file.getName())) {
/* 650 */           event.getRegistry().register((IForgeRegistryEntry)itemBullet);
/* 651 */           tabOrder.add(itemBullet);
/*     */         } 
/*     */       } 
/* 654 */       for (ItemMWArmor itemArmor : armorTypes.values()) {
/* 655 */         if (itemArmor.type.contentPack.equals(file.getName())) {
/* 656 */           event.getRegistry().register((IForgeRegistryEntry)itemArmor);
/* 657 */           tabOrder.add(itemArmor);
/*     */         } 
/*     */       } 
/* 660 */       for (ItemAttachment itemAttachment : attachmentTypes.values()) {
/* 661 */         if (itemAttachment.type.contentPack.equals(file.getName())) {
/* 662 */           event.getRegistry().register((IForgeRegistryEntry)itemAttachment);
/* 663 */           tabOrder.add(itemAttachment);
/*     */         } 
/*     */       } 
/*     */       
/* 667 */       for (ItemSpecialArmor itemSpecialArmor : specialArmorTypes.values()) {
/* 668 */         if (itemSpecialArmor.type.contentPack.equals(file.getName())) {
/* 669 */           event.getRegistry().register((IForgeRegistryEntry)itemSpecialArmor);
/* 670 */           tabOrder.add(itemSpecialArmor);
/*     */         } 
/*     */       } 
/*     */       
/* 674 */       for (ItemSpray itemSpray : sprayTypes.values()) {
/* 675 */         if (itemSpray.type.contentPack.equals(file.getName())) {
/* 676 */           event.getRegistry().register((IForgeRegistryEntry)itemSpray);
/* 677 */           tabOrder.add(itemSpray);
/*     */         } 
/*     */       } 
/*     */       
/* 681 */       for (ItemBackpack itemBackpack : backpackTypes.values()) {
/* 682 */         if (itemBackpack.type.contentPack.equals(file.getName())) {
/* 683 */           event.getRegistry().register((IForgeRegistryEntry)itemBackpack);
/* 684 */           tabOrder.add(itemBackpack);
/*     */         } 
/*     */       } 
/*     */       
/* 688 */       for (ItemGrenade itemGrenade : grenadeTypes.values()) {
/* 689 */         if (itemGrenade.type.contentPack.equals(file.getName())) {
/* 690 */           event.getRegistry().register((IForgeRegistryEntry)itemGrenade);
/* 691 */           tabOrder.add(itemGrenade);
/*     */         } 
/*     */       } 
/*     */       
/* 695 */       ItemRegisterEvent itemRegisterEvent = new ItemRegisterEvent(event.getRegistry(), tabOrder);
/* 696 */       MinecraftForge.EVENT_BUS.post((Event)itemRegisterEvent);
/*     */       
/* 698 */       itemRegisterEvent.tabOrder.forEach(item -> {
/*     */             if (item instanceof ItemGun) {
/*     */               for (SkinType skin : ((ItemGun)item).type.modelSkins) {
/*     */                 CommonProxy.preloadSkinTypes.put(skin, ((ItemGun)item).type);
/*     */               }
/*     */               
/*     */               CommonProxy.preloadFlashTex.add(((ItemGun)item).type.flashType);
/*     */             } 
/*     */             
/*     */             if (item instanceof ItemBullet) {
/*     */               for (SkinType skin : ((ItemBullet)item).type.modelSkins) {
/*     */                 CommonProxy.preloadSkinTypes.put(skin, ((ItemBullet)item).type);
/*     */               }
/*     */             }
/*     */             
/*     */             if (item instanceof ItemMWArmor) {
/*     */               for (SkinType skin : ((ItemMWArmor)item).type.modelSkins) {
/*     */                 CommonProxy.preloadSkinTypes.put(skin, ((ItemMWArmor)item).type);
/*     */               }
/*     */             }
/*     */           });
/*     */       
/* 720 */       ((MWTab)MODS_TABS.get(file.getName())).preInitialize(tabOrder);
/*     */     } 
/*     */     
/* 723 */     event.getRegistry().register((IForgeRegistryEntry)new ItemLight("light"));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
/* 728 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "bullethole"), EntityBulletHole.class, "bullethole", 3, this, 80, 10, false);
/* 729 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "shell"), EntityShell.class, "shell", 4, this, 64, 1, false);
/* 730 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "itemloot"), EntityItemLoot.class, "itemloot", 6, this, 64, 1, true);
/* 731 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "grenade"), EntityGrenade.class, "grenade", 7, this, 64, 1, true);
/* 732 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "smoke_grenade"), EntitySmokeGrenade.class, "smoke_grenade", 8, this, 64, 1, true);
/* 733 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "stun_grenade"), EntityStunGrenade.class, "stun_grenade", 9, this, 64, 1, true);
/*     */ 
/*     */     
/* 736 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "explosive_projectile"), EntityExplosiveProjectile.class, "explosive_projectile", 15, this, 80, 1, true);
/*     */   }
/*     */   
/*     */   public static void registerRayCasting(RayCasting rayCasting) {
/* 740 */     INSTANCE.RAY_CASTING = rayCasting;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\ModularWarfare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */