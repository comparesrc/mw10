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
/*  98 */   public static final String MOD_PREFIX = TextFormatting.GRAY + "[" + TextFormatting.RED + "ModularWarfare" + TextFormatting.GRAY + "]" + TextFormatting.GRAY;
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
/* 115 */   public static PlayerDataHandler PLAYERHANDLER = new PlayerDataHandler();
/*     */   
/* 117 */   public static Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/*     */   
/* 119 */   public static HashMap<String, ZipContentPack> zipContentsPack = new HashMap<>();
/*     */   
/*     */   public static File MOD_DIR;
/*     */   
/* 123 */   public static List<File> contentPacks = new ArrayList<>();
/*     */ 
/*     */   
/* 126 */   public static HashMap<String, ItemGun> gunTypes = new HashMap<>();
/* 127 */   public static HashMap<String, ItemAmmo> ammoTypes = new HashMap<>();
/* 128 */   public static HashMap<String, ItemAttachment> attachmentTypes = new HashMap<>();
/* 129 */   public static LinkedHashMap<String, ItemMWArmor> armorTypes = new LinkedHashMap<>();
/* 130 */   public static LinkedHashMap<String, ItemSpecialArmor> specialArmorTypes = new LinkedHashMap<>();
/* 131 */   public static HashMap<String, ItemBullet> bulletTypes = new HashMap<>();
/* 132 */   public static HashMap<String, ItemSpray> sprayTypes = new HashMap<>();
/* 133 */   public static HashMap<String, ItemBackpack> backpackTypes = new HashMap<>();
/* 134 */   public static HashMap<String, ItemGrenade> grenadeTypes = new HashMap<>();
/* 135 */   public static HashMap<String, TextureType> textureTypes = new HashMap<>();
/*     */   
/* 137 */   public static ArrayList<BaseType> baseTypes = new ArrayList<>();
/*     */   
/* 139 */   public static ArrayList<String> contentPackHashList = new ArrayList<>();
/*     */   
/*     */   public static boolean usingDirectoryContentPack = false;
/* 142 */   public static HashMap<String, MWTab> MODS_TABS = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public RayCasting RAY_CASTING;
/*     */ 
/*     */   
/* 149 */   public static final LibClassLoader LOADER = new LibClassLoader(ModularWarfare.class.getClassLoader());
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
/* 161 */       Class<?> protectorClass = Class.forName("moe.komi.mwprotect.ProtectZip");
/* 162 */       Constructor<?> constructor = protectorClass.getConstructor(new Class[] { File.class });
/* 163 */       IZip izip = (IZip)constructor.newInstance(new Object[] { file });
/* 164 */     } catch (ClassNotFoundException|NoSuchMethodException|InstantiationException|IllegalAccessException|InvocationTargetException e) {
/*     */       
/* 166 */       if (e instanceof InvocationTargetException) {
/* 167 */         ((InvocationTargetException)e).getTargetException().printStackTrace();
/*     */       }
/* 169 */       legacyZip = new LegacyZip(file);
/*     */     } 
/* 171 */     return (IZip)legacyZip;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadContent() {
/* 176 */     usingDirectoryContentPack = false;
/* 177 */     for (File file : contentPacks) {
/* 178 */       if (!file.isDirectory()) {
/*     */         
/*     */         try {
/* 181 */           FileInputStream inputStream = new FileInputStream(file);
/* 182 */           MessageDigest md = MessageDigest.getInstance("MD5");
/* 183 */           byte[] buffer = new byte[1024];
/* 184 */           int length = -1;
/* 185 */           while ((length = inputStream.read(buffer, 0, 1024)) != -1) {
/* 186 */             md.update(buffer, 0, length);
/*     */           }
/* 188 */           String md5 = "";
/* 189 */           for (byte b : md.digest()) {
/* 190 */             md5 = md5 + b;
/*     */           }
/* 192 */           contentPackHashList.add(md5);
/* 193 */           inputStream.close();
/* 194 */         } catch (IOException|java.security.NoSuchAlgorithmException e) {
/*     */           
/* 196 */           e.printStackTrace();
/*     */         }  continue;
/*     */       } 
/* 199 */       usingDirectoryContentPack = true;
/*     */     } 
/*     */     
/* 202 */     for (File file : contentPacks) {
/* 203 */       if (!MODS_TABS.containsKey(file.getName())) {
/* 204 */         MODS_TABS.put(file.getName(), new MWTab(file.getName()));
/*     */       }
/* 206 */       if (CommonProxy.zipJar.matcher(file.getName()).matches() && 
/* 207 */         !zipContentsPack.containsKey(file.getName())) {
/*     */         try {
/*     */           LegacyZip legacyZip;
/* 210 */           if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
/* 211 */             IZip izip = getiZip(file);
/*     */           } else {
/* 213 */             legacyZip = new LegacyZip(file);
/*     */           } 
/* 215 */           ZipContentPack zipContentPack = new ZipContentPack(file.getName(), legacyZip.getFileList(), (IZip)legacyZip);
/* 216 */           zipContentsPack.put(file.getName(), zipContentPack);
/* 217 */           LOGGER.info("Registered content pack");
/* 218 */         } catch (IOException e) {
/* 219 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 224 */     getTypeFiles(contentPacks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadContentPacks(boolean reload) {
/* 232 */     loadContent();
/*     */     
/* 234 */     if (DEV_ENV) {
/* 235 */       PROXY.generateJsonModels(baseTypes);
/*     */     }
/*     */     
/* 238 */     for (TextureType type : textureTypes.values()) {
/* 239 */       type.loadExtraValues();
/*     */     }
/*     */     
/* 242 */     for (BaseType baseType : baseTypes) {
/* 243 */       baseType.loadExtraValues();
/* 244 */       ((TypeEntry)ContentTypes.values.get(baseType.id)).typeAssignFunction.accept(baseType, Boolean.valueOf(reload));
/*     */     } 
/*     */     
/* 247 */     if (DEV_ENV) {
/* 248 */       if (reload) {
/*     */         return;
/*     */       }
/* 251 */       PROXY.generateLangFiles(baseTypes, DEV_ENV);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T getRenderConfig(BaseType baseType, Class<T> typeClass) {
/* 259 */     if (baseType.isInDirectory) {
/*     */       try {
/* 261 */         File contentPackDir = new File(MOD_DIR, baseType.contentPack);
/* 262 */         if (contentPackDir.exists() && contentPackDir.isDirectory()) {
/* 263 */           File renderConfig = new File(contentPackDir, "/" + baseType.getAssetDir() + "/render");
/* 264 */           File typeRender = new File(renderConfig, baseType.internalName + ".render.json");
/* 265 */           JsonReader jsonReader = new JsonReader(new FileReader(typeRender));
/* 266 */           return (T)GSONUtils.fromJson(gson, jsonReader, typeClass, baseType.internalName + ".render.json");
/*     */         } 
/* 268 */       } catch (JsonParseException e) {
/* 269 */         e.printStackTrace();
/* 270 */       } catch (FileNotFoundException e) {
/* 271 */         e.printStackTrace();
/* 272 */       } catch (com.modularwarfare.client.fpp.enhanced.AnimationType.AnimationTypeJsonAdapter.AnimationTypeException err) {
/* 273 */         LOGGER.info(baseType.internalName + " was loaded. But something was wrong.");
/* 274 */         err.printStackTrace();
/*     */       }
/*     */     
/* 277 */     } else if (zipContentsPack.containsKey(baseType.contentPack)) {
/* 278 */       String typeName = baseType.getAssetDir();
/*     */       
/* 280 */       IZipEntry foundFile = ((ZipContentPack)zipContentsPack.get(baseType.contentPack)).fileHeaders.stream().filter(fileHeader -> (fileHeader.getFileName().startsWith(typeName + "/render/") && fileHeader.getFileName().replace(typeName + "/render/", "").equalsIgnoreCase(baseType.internalName + ".render.json"))).findFirst().orElse(null);
/* 281 */       if (foundFile != null) {
/*     */         try {
/* 283 */           InputStream stream = foundFile.getInputStream();
/* 284 */           JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
/* 285 */           return (T)GSONUtils.fromJson(gson, jsonReader, typeClass, baseType.internalName + ".render.json");
/* 286 */         } catch (JsonParseException|IOException e) {
/* 287 */           e.printStackTrace();
/* 288 */         } catch (com.modularwarfare.client.fpp.enhanced.AnimationType.AnimationTypeJsonAdapter.AnimationTypeException err) {
/* 289 */           LOGGER.info(baseType.internalName + " was loaded. But something was wrong.");
/* 290 */           err.printStackTrace();
/*     */         } 
/*     */       } else {
/* 293 */         LOGGER.info(baseType.internalName + ".render.json not found. Aborting");
/*     */       } 
/*     */     } 
/*     */     
/* 297 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getTypeFiles(List<File> contentPacks) {
/* 306 */     ScriptHost.INSTANCE.reset();
/*     */     
/* 308 */     for (File file : contentPacks) {
/* 309 */       if (!file.getName().contains("cache")) {
/* 310 */         if (file.isDirectory()) {
/* 311 */           for (TypeEntry type : ContentTypes.values) {
/* 312 */             File subFolder = new File(file, "/" + type.name + "/");
/* 313 */             if (subFolder.exists()) {
/* 314 */               for (File typeFile : subFolder.listFiles()) {
/*     */                 try {
/* 316 */                   if (typeFile.isFile()) {
/* 317 */                     JsonReader jsonReader = new JsonReader(new FileReader(typeFile));
/* 318 */                     BaseType parsedType = (BaseType)GSONUtils.fromJson(gson, jsonReader, type.typeClass, typeFile.getName());
/*     */                     
/* 320 */                     parsedType.id = type.id;
/* 321 */                     parsedType.contentPack = file.getName();
/* 322 */                     parsedType.isInDirectory = true;
/* 323 */                     baseTypes.add(parsedType);
/*     */                     
/* 325 */                     if (parsedType instanceof TextureType) {
/* 326 */                       textureTypes.put(parsedType.internalName, (TextureType)parsedType);
/*     */                     }
/*     */                   } 
/* 329 */                 } catch (JsonParseException ex) {
/* 330 */                   ex.printStackTrace();
/*     */                 }
/* 332 */                 catch (FileNotFoundException exception) {
/* 333 */                   exception.printStackTrace();
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 341 */           File scriptFolder = new File(file, "/sciprt/");
/* 342 */           if (scriptFolder.exists()) {
/* 343 */             for (File typeFile : scriptFolder.listFiles()) {
/* 344 */               if (typeFile.getName().endsWith(".js")) {
/* 345 */                 String text = "";
/*     */                 try {
/* 347 */                   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
/*     */                   String temp;
/* 349 */                   while ((temp = bufferedReader.readLine()) != null) {
/* 350 */                     text = text + temp;
/*     */                   }
/* 352 */                   bufferedReader.close();
/* 353 */                   ScriptHost.INSTANCE.initScript(new ResourceLocation("modularwarfare", "script/" + typeFile.getName() + ".js"), text);
/* 354 */                 } catch (IOException e) {
/*     */                   
/* 356 */                   e.printStackTrace();
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
/* 367 */           CPEventHandler.cpConfig.clear();
/* 368 */           File cpFolder = new File(file, "/customplayer/");
/* 369 */           if (cpFolder.exists()) {
/* 370 */             for (File typeFile : cpFolder.listFiles()) {
/* 371 */               System.out.println("test1:" + typeFile.getName());
/* 372 */               if (typeFile.getName().endsWith(".json")) {
/* 373 */                 String text = "";
/*     */                 try {
/* 375 */                   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
/*     */                   String temp;
/* 377 */                   while ((temp = bufferedReader.readLine()) != null) {
/* 378 */                     text = text + temp;
/*     */                   }
/* 380 */                   bufferedReader.close();
/* 381 */                   CustomPlayerConfig cp = (CustomPlayerConfig)gson.fromJson(text, CustomPlayerConfig.class);
/* 382 */                   CPEventHandler.cpConfig.put(cp.name, cp);
/* 383 */                 } catch (IOException e) {
/*     */                   
/* 385 */                   e.printStackTrace();
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 394 */         if (zipContentsPack.containsKey(file.getName())) {
/* 395 */           for (IZipEntry fileHeader : ((ZipContentPack)zipContentsPack.get(file.getName())).fileHeaders) {
/* 396 */             for (TypeEntry type : ContentTypes.values) {
/* 397 */               String str1 = fileHeader.getFileName();
/* 398 */               String typeName = type.toString();
/* 399 */               if (str1.startsWith(typeName + "/") && (str1.split(typeName + "/")).length > 1 && str1.split(typeName + "/")[1].length() > 0 && !str1.contains("render")) {
/* 400 */                 InputStream stream = null;
/*     */                 try {
/* 402 */                   stream = fileHeader.getInputStream();
/* 403 */                   JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
/*     */                   
/*     */                   try {
/* 406 */                     BaseType parsedType = (BaseType)GSONUtils.fromJson(gson, jsonReader, type.typeClass, fileHeader.getFileName());
/* 407 */                     if (parsedType.internalName.equals("siz_bg.scope_win94_texture")) {
/* 408 */                       FMLLog.log.info("found - " + parsedType.internalName + " - " + file.getName() + " - " + fileHeader.getFileName() + " - " + fileHeader.getHandle());
/*     */                     }
/* 410 */                     parsedType.id = type.id;
/* 411 */                     parsedType.contentPack = file.getName();
/* 412 */                     parsedType.isInDirectory = false;
/* 413 */                     baseTypes.add(parsedType);
/*     */                     
/* 415 */                     if (parsedType instanceof TextureType) {
/* 416 */                       textureTypes.put(parsedType.internalName, (TextureType)parsedType);
/*     */                     }
/* 418 */                   } catch (JsonParseException ex) {}
/*     */                 
/*     */                 }
/* 421 */                 catch (IOException e) {
/* 422 */                   e.printStackTrace();
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 429 */             String zipName = fileHeader.getFileName();
/* 430 */             if (zipName.startsWith("script/") && zipName.endsWith(".js")) {
/* 431 */               String typeFile = zipName.replaceFirst("script/", "").replace(".js", "");
/* 432 */               String text = "";
/*     */               try {
/* 434 */                 InputStream inputStream = fileHeader.getInputStream();
/* 435 */                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
/*     */                 String temp;
/* 437 */                 while ((temp = bufferedReader.readLine()) != null) {
/* 438 */                   text = text + temp;
/*     */                 }
/* 440 */                 bufferedReader.close();
/* 441 */                 ScriptHost.INSTANCE.initScript(new ResourceLocation("modularwarfare", "script/" + typeFile + ".js"), text);
/* 442 */               } catch (IOException e) {
/*     */                 
/* 444 */                 e.printStackTrace();
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 453 */             zipName = fileHeader.getFileName();
/* 454 */             if (zipName.startsWith("customplayer/") && zipName.endsWith(".json")) {
/* 455 */               String typeFile = zipName.replaceFirst("customplayer/", "").replace(".json", "");
/* 456 */               String text = "";
/*     */               try {
/* 458 */                 InputStream inputStream = fileHeader.getInputStream();
/* 459 */                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
/*     */                 String temp;
/* 461 */                 while ((temp = bufferedReader.readLine()) != null) {
/* 462 */                   text = text + temp;
/*     */                 }
/* 464 */                 bufferedReader.close();
/* 465 */                 CustomPlayerConfig cp = (CustomPlayerConfig)gson.fromJson(text, CustomPlayerConfig.class);
/* 466 */                 CPEventHandler.cpConfig.put(cp.name, cp);
/* 467 */               } catch (IOException e) {
/*     */                 
/* 469 */                 e.printStackTrace();
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
/* 493 */     if (getClass().getClassLoader() instanceof LaunchClassLoader) {
/* 494 */       LaunchClassLoader loader = (LaunchClassLoader)getClass().getClassLoader();
/* 495 */       loader.addTransformerExclusion("com.fasterxml.jackson.");
/*     */       
/*     */       try {
/* 498 */         Field f = LaunchClassLoader.class.getDeclaredField("invalidClasses");
/* 499 */         f.setAccessible(true);
/* 500 */         Set<String> invalidClasses = (Set<String>)f.get(getClass().getClassLoader());
/* 501 */         invalidClasses.remove("com.fasterxml.jackson.databind.ObjectMapper");
/* 502 */       } catch (SecurityException|NoSuchFieldException|IllegalArgumentException|IllegalAccessException e1) {
/*     */         
/* 504 */         e1.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 508 */     PROXY.preload();
/*     */     
/* 510 */     if (FMLCommonHandler.instance().getSide().isServer()) {
/*     */       
/* 512 */       MOD_DIR = new File(event.getModConfigurationDirectory().getParentFile(), "ModularWarfare");
/* 513 */       if (!MOD_DIR.exists()) {
/* 514 */         MOD_DIR.mkdir();
/* 515 */         LOGGER.info("Created ModularWarfare folder, it's recommended to install content packs.");
/* 516 */         LOGGER.info("As the mod itself doesn't come with any content.");
/*     */       } 
/* 518 */       loadConfig();
/* 519 */       DEV_ENV = true;
/*     */       
/* 521 */       contentPacks = PROXY.getContentList();
/*     */     } 
/*     */     
/* 524 */     registerRayCasting((RayCasting)new DefaultRayCasting());
/* 525 */     this; loaderManager.preInitAddons(event);
/*     */ 
/*     */     
/* 528 */     ContentTypes.registerTypes();
/* 529 */     loadContentPacks(false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 534 */     PROXY.registerEventHandlers();
/*     */     
/* 536 */     MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
/* 537 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadConfig() {
/* 542 */     new ModConfig(new File(MOD_DIR, "mod_config.json"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInitialization(FMLInitializationEvent event) {
/* 552 */     new ServerTickHandler();
/*     */     
/* 554 */     PROXY.load();
/*     */     
/* 556 */     boolean bukkitOnline = false;
/*     */     try {
/* 558 */       Class.forName("org.bukkit.Bukkit");
/* 559 */       bukkitOnline = true;
/* 560 */     } catch (ClassNotFoundException classNotFoundException) {}
/*     */ 
/*     */     
/* 563 */     if (bukkitOnline) {
/* 564 */       MinecraftForge.EVENT_BUS.register(BukkitHelper.class);
/*     */     }
/*     */     
/* 567 */     NETWORK = new NetworkHandler();
/* 568 */     NETWORK.initialise();
/* 569 */     NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, (IGuiHandler)new GuiHandler());
/* 570 */     this; loaderManager.initAddons(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPostInitialization(FMLPostInitializationEvent event) {
/* 580 */     NETWORK.postInitialise();
/* 581 */     PROXY.init();
/* 582 */     this; loaderManager.postInitAddons(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onServerStarting(FMLServerStartingEvent event) {
/* 592 */     event.registerServerCommand((ICommand)new CommandClear());
/* 593 */     event.registerServerCommand((ICommand)new CommandNBT());
/* 594 */     event.registerServerCommand((ICommand)new CommandDebug());
/* 595 */     event.registerServerCommand((ICommand)new CommandKit());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void constructionEvent(FMLConstructionEvent event) {
/* 605 */     LOGGER = LogManager.getLogger("modularwarfare");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 610 */     this; addonDir = new File(ModUtil.getGameFolder() + "/addons_mwf_shining");
/*     */     
/* 612 */     this; if (!addonDir.exists()) {
/* 613 */       this; addonDir.mkdirs();
/* 614 */     }  this; loaderManager = new AddonLoaderManager();
/* 615 */     this; this; loaderManager.constructAddons(addonDir, event.getSide());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 621 */     if (ModUtil.isIDE()) {
/* 622 */       File file = (new File(ModUtil.getGameFolder())).getParentFile().getParentFile();
/* 623 */       String folder = file.toString().replace("\\", "/");
/* 624 */       this; loaderManager.constructDevAddons(new File(folder + "/melee-addon/build/classes/java/main"), "com.modularwarfare.melee.ModularWarfareMelee", event.getSide());
/*     */     } 
/*     */     
/* 627 */     PROXY.construction(event);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void registerItems(RegistryEvent.Register<Item> event) {
/* 632 */     for (File file : contentPacks) {
/* 633 */       List<Item> tabOrder = new ArrayList<>();
/* 634 */       for (ItemGun itemGun : gunTypes.values()) {
/* 635 */         if (itemGun.type.contentPack.equals(file.getName())) {
/* 636 */           event.getRegistry().register((IForgeRegistryEntry)itemGun);
/* 637 */           tabOrder.add(itemGun);
/*     */         } 
/*     */       } 
/* 640 */       for (ItemAmmo itemAmmo : ammoTypes.values()) {
/* 641 */         if (itemAmmo.type.contentPack.equals(file.getName())) {
/* 642 */           event.getRegistry().register((IForgeRegistryEntry)itemAmmo);
/* 643 */           tabOrder.add(itemAmmo);
/*     */         } 
/*     */       } 
/* 646 */       for (ItemBullet itemBullet : bulletTypes.values()) {
/* 647 */         if (itemBullet.type.contentPack.equals(file.getName())) {
/* 648 */           event.getRegistry().register((IForgeRegistryEntry)itemBullet);
/* 649 */           tabOrder.add(itemBullet);
/*     */         } 
/*     */       } 
/* 652 */       for (ItemMWArmor itemArmor : armorTypes.values()) {
/* 653 */         if (itemArmor.type.contentPack.equals(file.getName())) {
/* 654 */           event.getRegistry().register((IForgeRegistryEntry)itemArmor);
/* 655 */           tabOrder.add(itemArmor);
/*     */         } 
/*     */       } 
/* 658 */       for (ItemAttachment itemAttachment : attachmentTypes.values()) {
/* 659 */         if (itemAttachment.type.contentPack.equals(file.getName())) {
/* 660 */           event.getRegistry().register((IForgeRegistryEntry)itemAttachment);
/* 661 */           tabOrder.add(itemAttachment);
/*     */         } 
/*     */       } 
/*     */       
/* 665 */       for (ItemSpecialArmor itemSpecialArmor : specialArmorTypes.values()) {
/* 666 */         if (itemSpecialArmor.type.contentPack.equals(file.getName())) {
/* 667 */           event.getRegistry().register((IForgeRegistryEntry)itemSpecialArmor);
/* 668 */           tabOrder.add(itemSpecialArmor);
/*     */         } 
/*     */       } 
/*     */       
/* 672 */       for (ItemSpray itemSpray : sprayTypes.values()) {
/* 673 */         if (itemSpray.type.contentPack.equals(file.getName())) {
/* 674 */           event.getRegistry().register((IForgeRegistryEntry)itemSpray);
/* 675 */           tabOrder.add(itemSpray);
/*     */         } 
/*     */       } 
/*     */       
/* 679 */       for (ItemBackpack itemBackpack : backpackTypes.values()) {
/* 680 */         if (itemBackpack.type.contentPack.equals(file.getName())) {
/* 681 */           event.getRegistry().register((IForgeRegistryEntry)itemBackpack);
/* 682 */           tabOrder.add(itemBackpack);
/*     */         } 
/*     */       } 
/*     */       
/* 686 */       for (ItemGrenade itemGrenade : grenadeTypes.values()) {
/* 687 */         if (itemGrenade.type.contentPack.equals(file.getName())) {
/* 688 */           event.getRegistry().register((IForgeRegistryEntry)itemGrenade);
/* 689 */           tabOrder.add(itemGrenade);
/*     */         } 
/*     */       } 
/*     */       
/* 693 */       ItemRegisterEvent itemRegisterEvent = new ItemRegisterEvent(event.getRegistry(), tabOrder);
/* 694 */       MinecraftForge.EVENT_BUS.post((Event)itemRegisterEvent);
/*     */       
/* 696 */       itemRegisterEvent.tabOrder.forEach(item -> {
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
/* 718 */       ((MWTab)MODS_TABS.get(file.getName())).preInitialize(tabOrder);
/*     */     } 
/*     */     
/* 721 */     event.getRegistry().register((IForgeRegistryEntry)new ItemLight("light"));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
/* 726 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "bullethole"), EntityBulletHole.class, "bullethole", 3, this, 80, 10, false);
/* 727 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "shell"), EntityShell.class, "shell", 4, this, 64, 1, false);
/* 728 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "itemloot"), EntityItemLoot.class, "itemloot", 6, this, 64, 1, true);
/* 729 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "grenade"), EntityGrenade.class, "grenade", 7, this, 64, 1, true);
/* 730 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "smoke_grenade"), EntitySmokeGrenade.class, "smoke_grenade", 8, this, 64, 1, true);
/* 731 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "stun_grenade"), EntityStunGrenade.class, "stun_grenade", 9, this, 64, 1, true);
/*     */ 
/*     */     
/* 734 */     EntityRegistry.registerModEntity(new ResourceLocation("modularwarfare", "explosive_projectile"), EntityExplosiveProjectile.class, "explosive_projectile", 15, this, 80, 1, true);
/*     */   }
/*     */   
/*     */   public static void registerRayCasting(RayCasting rayCasting) {
/* 738 */     INSTANCE.RAY_CASTING = rayCasting;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\ModularWarfare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */