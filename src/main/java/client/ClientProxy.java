/*     */ package com.modularwarfare.client;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.GenerateJsonModelsEvent;
/*     */ import com.modularwarfare.api.WeaponAnimation;
/*     */ import com.modularwarfare.api.WeaponAnimations;
/*     */ import com.modularwarfare.client.export.ItemModelExport;
/*     */ import com.modularwarfare.client.fpp.basic.animations.ReloadType;
/*     */ import com.modularwarfare.client.fpp.basic.animations.anims.AnimationRevolver;
/*     */ import com.modularwarfare.client.fpp.basic.animations.anims.AnimationShotgun;
/*     */ import com.modularwarfare.client.fpp.basic.animations.anims.AnimationSniperBottom;
/*     */ import com.modularwarfare.client.fpp.basic.configs.GunRenderConfig;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderDecal;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderGrenade;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderGrenadeEntity;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderParameters;
/*     */ import com.modularwarfare.client.fpp.enhanced.animation.AnimationController;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*     */ import com.modularwarfare.client.fpp.enhanced.models.ModelEnhancedGun;
/*     */ import com.modularwarfare.client.handler.ClientTickHandler;
/*     */ import com.modularwarfare.client.hud.AttachmentUI;
/*     */ import com.modularwarfare.client.hud.FlashSystem;
/*     */ import com.modularwarfare.client.hud.GunUI;
/*     */ import com.modularwarfare.client.killchat.KillFeedManager;
/*     */ import com.modularwarfare.client.model.ModelGun;
/*     */ import com.modularwarfare.client.model.layers.RenderLayerBody;
/*     */ import com.modularwarfare.client.patch.customnpc.CustomNPCListener;
/*     */ import com.modularwarfare.client.patch.galacticraft.GCCompatInterop;
/*     */ import com.modularwarfare.client.patch.galacticraft.GCDummyInterop;
/*     */ import com.modularwarfare.client.renderers.RenderItemLoot;
/*     */ import com.modularwarfare.client.renderers.RenderProjectile;
/*     */ import com.modularwarfare.client.scope.ScopeUtils;
/*     */ import com.modularwarfare.client.view.AutoSwitchToFirstView;
/*     */ import com.modularwarfare.common.armor.ArmorType;
/*     */ import com.modularwarfare.common.armor.ItemMWArmor;
/*     */ import com.modularwarfare.common.armor.ItemSpecialArmor;
/*     */ import com.modularwarfare.common.backpacks.ItemBackpack;
/*     */ import com.modularwarfare.common.entity.EntityBulletClient;
/*     */ import com.modularwarfare.common.entity.decals.EntityBulletHole;
/*     */ import com.modularwarfare.common.entity.grenades.EntityGrenade;
/*     */ import com.modularwarfare.common.entity.grenades.EntitySmokeGrenade;
/*     */ import com.modularwarfare.common.entity.item.EntityItemLoot;
/*     */ import com.modularwarfare.common.extra.ItemLight;
/*     */ import com.modularwarfare.common.grenades.ItemGrenade;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAmmo;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.guns.ItemBullet;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.ItemSpray;
/*     */ import com.modularwarfare.common.guns.SkinType;
/*     */ import com.modularwarfare.common.guns.WeaponAnimationType;
/*     */ import com.modularwarfare.common.guns.WeaponSoundType;
/*     */ import com.modularwarfare.common.init.ModSounds;
/*     */ import com.modularwarfare.common.particle.EntityBloodFX;
/*     */ import com.modularwarfare.common.particle.ParticleExplosion;
/*     */ import com.modularwarfare.common.particle.ParticleRocket;
/*     */ import com.modularwarfare.common.textures.TextureType;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import com.modularwarfare.objects.SoundEntry;
/*     */ import com.modularwarfare.utility.MWResourcePack;
/*     */ import com.modularwarfare.utility.MWSound;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.function.Predicate;
/*     */ import mchhui.modularmovements.tactical.client.ClientLitener;
/*     */ import moe.komi.mwprotect.IZip;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.client.renderer.entity.MWFRenderHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*     */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.model.ModelLoader;
/*     */ import net.minecraftforge.client.resource.IResourceType;
/*     */ import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.RegistryEvent;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*     */ import net.minecraftforge.fml.client.registry.RenderingRegistry;
/*     */ import net.minecraftforge.fml.common.FMLModContainer;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.MetadataCollection;
/*     */ import net.minecraftforge.fml.common.ModContainer;
/*     */ import net.minecraftforge.fml.common.discovery.ContainerType;
/*     */ import net.minecraftforge.fml.common.discovery.ModCandidate;
/*     */ import net.minecraftforge.fml.common.event.FMLConstructionEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.registries.IForgeRegistry;
/*     */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ClientProxy extends CommonProxy {
/* 123 */   public static String modelDir = "com.modularwarfare.client.model.";
/*     */   
/*     */   public static RenderGunStatic gunStaticRenderer;
/*     */   
/*     */   public static RenderGunEnhanced gunEnhancedRenderer;
/*     */   
/*     */   public static RenderAmmo ammoRenderer;
/*     */   
/*     */   public static RenderAttachment attachmentRenderer;
/*     */   public static RenderGrenade grenadeRenderer;
/* 133 */   public static HashMap<String, SoundEvent> modSounds = new HashMap<>();
/*     */   
/*     */   public static ScopeUtils scopeUtils;
/*     */   
/*     */   public static FlashSystem flashImage;
/* 138 */   public static ItemLight itemLight = new ItemLight("light");
/*     */   
/*     */   public static ClientRenderHooks renderHooks;
/*     */   
/*     */   public static AttachmentUI attachmentUI;
/*     */   
/*     */   public static GunUI gunUI;
/*     */   
/*     */   public static KillFeedManager killFeedManager;
/*     */   
/*     */   public static AutoSwitchToFirstView autoSwitchToFirstView;
/*     */   
/*     */   public static GCCompatInterop galacticraftInterop;
/*     */   
/*     */   public static ObfuscateCompatInterop obfuscateInterop;
/*     */   
/* 154 */   private static int lastBobbingParm = 1;
/*     */   
/*     */   public KillFeedManager getKillChatManager() {
/* 157 */     this; return killFeedManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void construction(FMLConstructionEvent event) {
/* 162 */     super.construction(event);
/*     */     
/* 164 */     for (File file : modularWarfareDir.listFiles()) {
/* 165 */       if (!file.getName().contains("cache") && !file.getName().contains("officialmw") && !file.getName().contains("highres")) {
/* 166 */         if (zipJar.matcher(file.getName()).matches()) {
/*     */           
/*     */           try {
/* 169 */             IZip izip = ModularWarfare.getiZip(file);
/*     */             
/* 171 */             HashMap<String, Object> map = new HashMap<>();
/* 172 */             map.put("modid", "modularwarfare");
/* 173 */             map.put("name", "ModularWarfare : " + file.getName());
/* 174 */             map.put("version", "1");
/*     */             
/* 176 */             MWResourcePack.Container container = new MWResourcePack.Container("com.modularwarfare.ModularWarfare", new ModCandidate(file, file, ContainerType.JAR), map, izip, "ModularWarfare : " + file.getName());
/* 177 */             container.bindMetadata(MetadataCollection.from(null, ""));
/* 178 */             FMLClientHandler.instance().addModAsResource((ModContainer)container);
/* 179 */             ModularWarfare.contentPacks.add(file);
/* 180 */           } catch (Exception e) {
/* 181 */             e.printStackTrace();
/*     */           } 
/* 183 */         } else if (file.isDirectory()) {
/*     */           try {
/* 185 */             HashMap<String, Object> map = new HashMap<>();
/* 186 */             map.put("modid", "modularwarfare");
/* 187 */             map.put("name", "ModularWarfare : " + file.getName());
/* 188 */             map.put("version", "1");
/* 189 */             FMLModContainer container = new FMLModContainer("com.modularwarfare.ModularWarfare", new ModCandidate(file, file, file.isDirectory() ? ContainerType.DIR : ContainerType.JAR), map);
/* 190 */             container.bindMetadata(MetadataCollection.from(null, ""));
/* 191 */             FMLClientHandler.instance().addModAsResource((ModContainer)container);
/* 192 */           } catch (Exception e) {
/* 193 */             e.printStackTrace();
/*     */           } 
/* 195 */           ModularWarfare.contentPacks.add(file);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preload() {
/* 205 */     SmoothSwingTicker.startSmoothSwingTimer();
/*     */     
/* 207 */     MinecraftForge.EVENT_BUS.register(this);
/* 208 */     MinecraftForge.EVENT_BUS.register(new CPEventHandler());
/* 209 */     startPatches();
/* 210 */     (Minecraft.func_71410_x()).field_71474_y.field_178881_t = false;
/*     */   }
/*     */   
/*     */   public void startPatches() {
/* 214 */     if (Loader.isModLoaded("customnpcs")) {
/* 215 */       CustomNPCListener customNPCListener = new CustomNPCListener();
/* 216 */       MinecraftForge.EVENT_BUS.register(customNPCListener);
/*     */     } 
/* 218 */     if (Loader.isModLoaded("galacticraftcore")) {
/*     */       try {
/* 220 */         galacticraftInterop = Class.forName("com.modularwarfare.client.patch.galacticraft.GCInteropImpl").<GCCompatInterop>asSubclass(GCCompatInterop.class).newInstance();
/* 221 */         ModularWarfare.LOGGER.info("Galatic Craft has been detected! Will attempt to patch.");
/* 222 */         galacticraftInterop.applyFix();
/* 223 */       } catch (Exception e) {
/* 224 */         e.printStackTrace();
/* 225 */         galacticraftInterop = (GCCompatInterop)new GCDummyInterop();
/*     */       } 
/*     */     } else {
/* 228 */       galacticraftInterop = (GCCompatInterop)new GCDummyInterop();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() {
/* 234 */     super.load();
/* 235 */     new KeyInputHandler();
/* 236 */     new ClientTickHandler();
/* 237 */     new ClientGunHandler();
/* 238 */     new RenderGuiHandler();
/*     */     
/* 240 */     this; renderHooks = new ClientRenderHooks();
/* 241 */     this; MinecraftForge.EVENT_BUS.register(renderHooks);
/*     */     
/* 243 */     this; scopeUtils = new ScopeUtils();
/* 244 */     this; MinecraftForge.EVENT_BUS.register(scopeUtils);
/*     */     
/* 246 */     this; flashImage = new FlashSystem();
/* 247 */     this; MinecraftForge.EVENT_BUS.register(flashImage);
/*     */     
/* 249 */     this; attachmentUI = new AttachmentUI();
/* 250 */     this; MinecraftForge.EVENT_BUS.register(attachmentUI);
/*     */     
/* 252 */     this; gunUI = new GunUI();
/* 253 */     this; MinecraftForge.EVENT_BUS.register(gunUI);
/*     */     
/* 255 */     this; killFeedManager = new KillFeedManager();
/* 256 */     this; MinecraftForge.EVENT_BUS.register(new KillFeedRender(killFeedManager));
/*     */     
/* 258 */     this; autoSwitchToFirstView = new AutoSwitchToFirstView();
/* 259 */     this; MinecraftForge.EVENT_BUS.register(autoSwitchToFirstView);
/*     */     
/* 261 */     WeaponAnimations.registerAnimation("rifle", (WeaponAnimation)new AnimationRifle());
/* 262 */     WeaponAnimations.registerAnimation("rifle2", (WeaponAnimation)new AnimationRifle2());
/* 263 */     WeaponAnimations.registerAnimation("rifle3", (WeaponAnimation)new AnimationRifle3());
/* 264 */     WeaponAnimations.registerAnimation("rifle4", (WeaponAnimation)new AnimationRifle4());
/* 265 */     WeaponAnimations.registerAnimation("pistol", (WeaponAnimation)new AnimationPistol());
/* 266 */     WeaponAnimations.registerAnimation("revolver", (WeaponAnimation)new AnimationRevolver());
/* 267 */     WeaponAnimations.registerAnimation("shotgun", (WeaponAnimation)new AnimationShotgun());
/* 268 */     WeaponAnimations.registerAnimation("sniper", (WeaponAnimation)new AnimationSniperBottom());
/* 269 */     WeaponAnimations.registerAnimation("sniper_top", (WeaponAnimation)new AnimationSniperTop());
/* 270 */     WeaponAnimations.registerAnimation("sideclip", (WeaponAnimation)new AnimationSideClip());
/* 271 */     WeaponAnimations.registerAnimation("toprifle", (WeaponAnimation)new AnimationTopRifle());
/* 272 */     WeaponAnimations.registerAnimation("rocket_launcher", (WeaponAnimation)new AnimationRocketLauncher());
/*     */     
/* 274 */     Map<String, RenderPlayer> skinMap = Minecraft.func_71410_x().func_175598_ae().getSkinMap();
/* 275 */     for (RenderPlayer renderer : skinMap.values()) {
/* 276 */       setupLayers(renderer);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setupLayers(RenderPlayer renderer) {
/* 281 */     MWFRenderHelper helper = new MWFRenderHelper((RenderLivingBase)renderer);
/* 282 */     helper.getLayerRenderers().add(0, new ResetHiddenModelLayer(renderer));
/* 283 */     renderer.func_177094_a((LayerRenderer)new RenderLayerBackpack(renderer, (renderer.func_177087_b()).field_178730_v));
/* 284 */     renderer.func_177094_a((LayerRenderer)new RenderLayerBody(renderer, (renderer.func_177087_b()).field_178730_v));
/* 285 */     renderer.func_177094_a((LayerRenderer)new RenderLayerHeldGun((RenderLivingBase)renderer));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 291 */     if (ModUtil.isMac()) {
/* 292 */       ModConfig.INSTANCE.model_optimization = false;
/*     */     }
/*     */     
/* 295 */     ((IReloadableResourceManager)Minecraft.func_71410_x().func_110442_L()).func_110542_a((IResourceManagerReloadListener)new ISelectiveResourceReloadListener()
/*     */         {
/*     */           
/*     */           public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate)
/*     */           {
/* 300 */             ClientProxy.this.loadTextures();
/*     */           }
/*     */         });
/*     */     
/* 304 */     loadTextures();
/*     */     
/* 306 */     ClientCommandHandler.instance.func_71560_a((ICommand)new CommandMWClient());
/*     */     
/* 308 */     Programs.init();
/*     */   }
/*     */   
/*     */   public void loadTextures() {
/* 312 */     ModularWarfare.LOGGER.info("Preloading textures");
/* 313 */     long time = System.currentTimeMillis();
/* 314 */     preloadSkinTypes.forEach((skin, type) -> {
/*     */           for (int i = 0; i < skin.textures.length; i++) {
/*     */             ResourceLocation resource = new ResourceLocation("modularwarfare", String.format((skin.textures[i]).format, new Object[] { type.getAssetDir(), skin.getSkin() }));
/*     */             
/*     */             Minecraft.func_71410_x().func_110434_K().func_110577_a(resource);
/*     */             
/*     */             if (skin.sampling.equals(SkinType.Sampling.LINEAR)) {
/*     */               GL11.glTexParameteri(3553, 10240, 9729);
/*     */               GL11.glTexParameteri(3553, 10241, 9729);
/*     */             } 
/*     */           } 
/*     */         });
/* 326 */     preloadFlashTex.forEach(type -> type.resourceLocations.forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     ModularWarfare.LOGGER.info("All textures are ready(" + (System.currentTimeMillis() - time) + "ms)");
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onModelRegistry(ModelRegistryEvent event) {
/* 339 */     for (ItemGun itemGun : ModularWarfare.gunTypes.values()) {
/* 340 */       ModelLoader.setCustomModelResourceLocation((Item)itemGun, 0, new ModelResourceLocation("modularwarfare:" + itemGun.type.internalName));
/*     */     }
/*     */     
/* 343 */     for (ItemAmmo itemAmmo : ModularWarfare.ammoTypes.values()) {
/* 344 */       ModelLoader.setCustomModelResourceLocation((Item)itemAmmo, 0, new ModelResourceLocation("modularwarfare:" + itemAmmo.type.internalName));
/*     */     }
/*     */     
/* 347 */     for (ItemAttachment itemAttachment : ModularWarfare.attachmentTypes.values()) {
/* 348 */       ModelLoader.setCustomModelResourceLocation((Item)itemAttachment, 0, new ModelResourceLocation("modularwarfare:" + itemAttachment.type.internalName));
/*     */     }
/*     */     
/* 351 */     for (ItemBullet itemBullet : ModularWarfare.bulletTypes.values()) {
/* 352 */       ModelLoader.setCustomModelResourceLocation((Item)itemBullet, 0, new ModelResourceLocation("modularwarfare:" + itemBullet.type.internalName));
/*     */     }
/*     */     
/* 355 */     for (ItemMWArmor itemArmor : ModularWarfare.armorTypes.values()) {
/* 356 */       ModelLoader.setCustomModelResourceLocation((Item)itemArmor, 0, new ModelResourceLocation("modularwarfare:" + itemArmor.internalName));
/*     */     }
/*     */     
/* 359 */     for (ItemSpecialArmor itemArmor : ModularWarfare.specialArmorTypes.values()) {
/* 360 */       ModelLoader.setCustomModelResourceLocation((Item)itemArmor, 0, new ModelResourceLocation("modularwarfare:" + itemArmor.type.internalName));
/*     */     }
/*     */     
/* 363 */     for (ItemSpray itemSpray : ModularWarfare.sprayTypes.values()) {
/* 364 */       ModelLoader.setCustomModelResourceLocation((Item)itemSpray, 0, new ModelResourceLocation("modularwarfare:" + itemSpray.type.internalName));
/*     */     }
/*     */     
/* 367 */     for (ItemBackpack itemBackpack : ModularWarfare.backpackTypes.values()) {
/* 368 */       ModelLoader.setCustomModelResourceLocation((Item)itemBackpack, 0, new ModelResourceLocation("modularwarfare:" + itemBackpack.type.internalName));
/*     */     }
/*     */     
/* 371 */     for (ItemGrenade itemGrenade : ModularWarfare.grenadeTypes.values()) {
/* 372 */       ModelLoader.setCustomModelResourceLocation((Item)itemGrenade, 0, new ModelResourceLocation("modularwarfare:" + itemGrenade.type.internalName));
/*     */     }
/*     */     
/* 375 */     ModelLoader.setCustomModelResourceLocation((Item)itemLight, 0, new ModelResourceLocation(itemLight.getRegistryName(), "inventory"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void forceReload() {
/* 381 */     FMLClientHandler.instance().refreshResources(new IResourceType[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getModelName(String in) {
/* 391 */     String[] split = in.split("\\.");
/*     */     
/* 393 */     if (split.length == 1) {
/* 394 */       return in;
/*     */     }
/* 396 */     if (split.length > 1) {
/* 397 */       String out = split[split.length - 1];
/* 398 */       for (int i = split.length - 2; i >= 0; i--) {
/* 399 */         out = split[i] + "." + out;
/*     */       }
/* 401 */       return out;
/*     */     } 
/* 403 */     return in;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T loadModel(String s, String shortName, Class<T> typeClass) {
/* 411 */     if (s == null || shortName == null)
/* 412 */       return null; 
/*     */     try {
/* 414 */       return typeClass.cast(Class.forName(modelDir + getModelName(s)).getConstructor(new Class[0]).newInstance(new Object[0]));
/* 415 */     } catch (Exception e) {
/* 416 */       ModularWarfare.LOGGER.error("Failed to load staticModel : " + shortName + " (" + s + ")");
/* 417 */       e.printStackTrace();
/*     */       
/* 419 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reloadModels(boolean reloadSkins) {
/* 424 */     EnhancedModel.clearCache();
/* 425 */     for (BaseType baseType : ModularWarfare.baseTypes) {
/* 426 */       if (baseType.hasModel()) {
/* 427 */         baseType.reloadModel();
/*     */       }
/*     */     } 
/* 430 */     if (reloadSkins) {
/* 431 */       forceReload();
/*     */     }
/*     */   }
/*     */   
/*     */   public void generateJsonModels(ArrayList<BaseType> types) {
/* 436 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/*     */     
/* 438 */     GenerateJsonModelsEvent event = new GenerateJsonModelsEvent();
/* 439 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     
/* 441 */     for (BaseType type : types) {
/* 442 */       if (type.contentPack == null) {
/*     */         continue;
/*     */       }
/* 445 */       File contentPackDir = new File(ModularWarfare.MOD_DIR, type.contentPack);
/*     */       
/* 447 */       if (zipJar.matcher(contentPackDir.getName()).matches()) {
/*     */         continue;
/*     */       }
/* 450 */       if (contentPackDir.exists() && contentPackDir.isDirectory()) {
/*     */         
/* 452 */         File itemModelsDir = new File(contentPackDir, "/assets/modularwarfare/models/item");
/* 453 */         if (!itemModelsDir.exists()) {
/* 454 */           itemModelsDir.mkdirs();
/*     */         }
/* 456 */         File typeModel = new File(itemModelsDir, type.internalName + ".json");
/* 457 */         if (!typeModel.exists()) {
/* 458 */           if (type instanceof ArmorType) {
/* 459 */             ArmorType armorType = (ArmorType)type;
/* 460 */             for (ArmorType.ArmorInfo armorInfo : armorType.armorTypes.values()) {
/* 461 */               String internalName = (armorInfo.internalName != null) ? armorInfo.internalName : armorType.internalName;
/* 462 */               typeModel = new File(itemModelsDir, internalName + ".json");
/*     */               try {
/* 464 */                 FileWriter fileWriter = new FileWriter(typeModel, false);
/* 465 */                 gson.toJson(createJson(type, internalName), fileWriter);
/* 466 */                 fileWriter.flush();
/* 467 */                 fileWriter.close();
/* 468 */               } catch (Exception e) {
/* 469 */                 e.printStackTrace();
/*     */               } 
/*     */             } 
/*     */           } else {
/*     */             try {
/* 474 */               FileWriter fileWriter = new FileWriter(typeModel, false);
/* 475 */               gson.toJson(createJson(type), fileWriter);
/* 476 */               fileWriter.flush();
/* 477 */               fileWriter.close();
/* 478 */             } catch (Exception e) {
/* 479 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 488 */       if (ModularWarfare.DEV_ENV) {
/* 489 */         File dir = new File(contentPackDir, "/" + type.getAssetDir() + "/render");
/* 490 */         if (!dir.exists()) {
/* 491 */           dir.mkdirs();
/*     */         }
/* 493 */         File renderFile = new File(dir, type.internalName + ".render.json");
/* 494 */         if (!renderFile.exists()) {
/*     */           try {
/* 496 */             FileWriter fileWriter = new FileWriter(renderFile, true);
/* 497 */             if (type instanceof GunType) {
/* 498 */               if (((GunType)type).animationType.equals(WeaponAnimationType.ENHANCED)) {
/* 499 */                 GunEnhancedRenderConfig renderConfig = new GunEnhancedRenderConfig();
/* 500 */                 renderConfig.modelFileName = type.internalName.replaceAll(type.contentPack + ".", "");
/* 501 */                 renderConfig.modelFileName += ".glb";
/* 502 */                 gson.toJson(renderConfig, fileWriter);
/*     */               } else {
/* 504 */                 GunRenderConfig renderConfig = new GunRenderConfig();
/* 505 */                 renderConfig.modelFileName = type.internalName.replaceAll(type.contentPack + ".", "");
/* 506 */                 renderConfig.modelFileName += ".obj";
/* 507 */                 gson.toJson(renderConfig, fileWriter);
/*     */               } 
/* 509 */               fileWriter.flush();
/* 510 */               fileWriter.close();
/*     */             } 
/* 512 */           } catch (Exception e) {
/* 513 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateJsonSounds(Collection<BaseType> types, boolean replace) {
/* 522 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/* 523 */     HashMap<String, ArrayList<String>> cpSounds = new HashMap<>();
/*     */     
/* 525 */     for (BaseType baseType : types) {
/* 526 */       if (baseType.contentPack == null) {
/*     */         continue;
/*     */       }
/* 529 */       String contentPack = baseType.contentPack;
/*     */       
/* 531 */       if (!cpSounds.containsKey(contentPack)) {
/* 532 */         cpSounds.put(contentPack, new ArrayList<>());
/*     */       }
/* 534 */       for (WeaponSoundType weaponSoundType : baseType.weaponSoundMap.keySet()) {
/* 535 */         ArrayList<SoundEntry> soundEntries = (ArrayList<SoundEntry>)baseType.weaponSoundMap.get(weaponSoundType);
/* 536 */         for (SoundEntry soundEntry : soundEntries) {
/* 537 */           if (soundEntry.soundName != null && !((ArrayList)cpSounds.get(contentPack)).contains(soundEntry.soundName)) {
/* 538 */             ((ArrayList<String>)cpSounds.get(contentPack)).add(soundEntry.soundName);
/*     */           }
/* 540 */           if (soundEntry.soundNameDistant != null && !((ArrayList)cpSounds.get(contentPack)).contains(soundEntry.soundNameDistant)) {
/* 541 */             ((ArrayList<String>)cpSounds.get(contentPack)).add(soundEntry.soundNameDistant);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 546 */     for (String contentPack : cpSounds.keySet()) {
/*     */       try {
/* 548 */         File contentPackDir = new File(ModularWarfare.MOD_DIR, contentPack);
/* 549 */         if (contentPackDir.exists() && contentPackDir.isDirectory()) {
/* 550 */           ArrayList<String> soundEntries = cpSounds.get(contentPack);
/* 551 */           if (soundEntries != null && !soundEntries.isEmpty()) {
/* 552 */             Path assetsDir = Paths.get(ModularWarfare.MOD_DIR.getAbsolutePath() + "/" + contentPack + "/assets/modularwarfare/", new String[0]);
/* 553 */             if (!Files.exists(assetsDir, new java.nio.file.LinkOption[0]))
/* 554 */               Files.createDirectories(assetsDir, (FileAttribute<?>[])new FileAttribute[0]); 
/* 555 */             Path soundsFile = Paths.get(assetsDir + "/sounds.json", new String[0]);
/*     */             
/* 557 */             boolean soundsExists = Files.exists(soundsFile, new java.nio.file.LinkOption[0]);
/* 558 */             boolean shouldCreate = soundsExists ? replace : true;
/* 559 */             if (shouldCreate) {
/* 560 */               if (!soundsExists) {
/* 561 */                 Files.createFile(soundsFile, (FileAttribute<?>[])new FileAttribute[0]);
/*     */               }
/* 563 */               ArrayList<String> jsonEntries = new ArrayList<>();
/* 564 */               String format = "\"%s\":{\"category\": \"player\",\"subtitle\": \"MW Sound\",\"sounds\": [\"modularwarfare:%s\"]}";
/* 565 */               jsonEntries.add("{");
/* 566 */               for (int i = 0; i < soundEntries.size(); i++) {
/* 567 */                 if (i + 1 < soundEntries.size()) {
/*     */                   
/* 569 */                   jsonEntries.add(format.replaceAll("%s", soundEntries.get(i)) + ",");
/*     */                 } else {
/*     */                   
/* 572 */                   jsonEntries.add(format.replaceAll("%s", soundEntries.get(i)));
/*     */                 } 
/*     */               } 
/* 575 */               jsonEntries.add("}");
/* 576 */               Files.write(soundsFile, (Iterable)jsonEntries, Charset.forName("UTF-8"), new java.nio.file.OpenOption[0]);
/*     */             } 
/*     */           } 
/*     */         } 
/* 580 */       } catch (Exception exception) {
/* 581 */         if (ModularWarfare.DEV_ENV) {
/* 582 */           exception.printStackTrace(); continue;
/*     */         } 
/* 584 */         ModularWarfare.LOGGER.error(String.format("Failed to create sounds.json for content pack '%s'", new Object[] { contentPack }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateLangFiles(ArrayList<BaseType> types, boolean replace) {
/* 592 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/* 593 */     HashMap<String, ArrayList<BaseType>> langEntryMap = new HashMap<>();
/*     */     
/* 595 */     for (BaseType baseType : types) {
/* 596 */       if (baseType.contentPack == null) {
/*     */         continue;
/*     */       }
/* 599 */       String contentPack = baseType.contentPack;
/*     */       
/* 601 */       if (!langEntryMap.containsKey(contentPack)) {
/* 602 */         langEntryMap.put(contentPack, new ArrayList<>());
/*     */       }
/* 604 */       if (baseType.displayName != null && !((ArrayList)langEntryMap.get(contentPack)).contains(baseType)) {
/* 605 */         ((ArrayList<BaseType>)langEntryMap.get(contentPack)).add(baseType);
/*     */       }
/* 607 */       if (baseType instanceof ArmorType) {
/* 608 */         ((ArrayList<BaseType>)langEntryMap.get(contentPack)).add(baseType);
/*     */       }
/*     */     } 
/* 611 */     for (String contentPack : langEntryMap.keySet()) {
/*     */       try {
/* 613 */         File contentPackDir = new File(ModularWarfare.MOD_DIR, contentPack);
/* 614 */         if (contentPackDir.exists() && contentPackDir.isDirectory()) {
/* 615 */           ArrayList<BaseType> langEntries = langEntryMap.get(contentPack);
/* 616 */           if (langEntries != null && !langEntries.isEmpty()) {
/* 617 */             Path langDir = Paths.get(ModularWarfare.MOD_DIR.getAbsolutePath() + "/" + contentPack + "/assets/modularwarfare/lang/", new String[0]);
/* 618 */             if (!Files.exists(langDir, new java.nio.file.LinkOption[0]))
/* 619 */               Files.createDirectories(langDir, (FileAttribute<?>[])new FileAttribute[0]); 
/* 620 */             Path langPath = Paths.get(langDir + "/en_US.lang", new String[0]);
/*     */             
/* 622 */             boolean soundsExists = Files.exists(langPath, new java.nio.file.LinkOption[0]);
/* 623 */             boolean shouldCreate = soundsExists ? replace : true;
/* 624 */             if (shouldCreate) {
/* 625 */               if (!soundsExists) {
/* 626 */                 Files.createFile(langPath, (FileAttribute<?>[])new FileAttribute[0]);
/*     */               }
/* 628 */               ArrayList<String> jsonEntries = new ArrayList<>();
/* 629 */               String format = "item.%s.name=%s";
/* 630 */               for (int i = 0; i < langEntries.size(); i++) {
/* 631 */                 BaseType type = langEntries.get(i);
/* 632 */                 if (type instanceof ArmorType) {
/* 633 */                   ArmorType armorType = (ArmorType)type;
/* 634 */                   for (ArmorType.ArmorInfo armorInfo : armorType.armorTypes.values()) {
/* 635 */                     String internalName = (armorInfo.internalName != null) ? armorInfo.internalName : armorType.internalName;
/* 636 */                     jsonEntries.add(String.format(format, new Object[] { internalName, armorInfo.displayName }));
/*     */                   } 
/*     */                 } else {
/* 639 */                   jsonEntries.add(String.format(format, new Object[] { type.internalName, type.displayName }));
/*     */                 } 
/*     */               } 
/* 642 */               Files.write(langPath, (Iterable)jsonEntries, Charset.forName("UTF-8"), new java.nio.file.OpenOption[0]);
/*     */             } 
/*     */           } 
/*     */         } 
/* 646 */       } catch (Exception exception) {
/* 647 */         if (ModularWarfare.DEV_ENV) {
/* 648 */           exception.printStackTrace(); continue;
/*     */         } 
/* 650 */         ModularWarfare.LOGGER.error(String.format("Failed to create sounds.json for content pack '%s'", new Object[] { contentPack }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ItemModelExport createJson(BaseType type) {
/* 657 */     ItemModelExport exportedModel = new ItemModelExport();
/*     */     
/* 659 */     if (!(type instanceof GunType) && !(type instanceof com.modularwarfare.common.grenades.GrenadeType)) {
/* 660 */       exportedModel.display.thirdperson_lefthand.scale[0] = 0.4F;
/* 661 */       exportedModel.display.thirdperson_lefthand.scale[1] = 0.4F;
/* 662 */       exportedModel.display.thirdperson_lefthand.scale[2] = 0.4F;
/*     */       
/* 664 */       exportedModel.display.thirdperson_righthand.scale[0] = 0.4F;
/* 665 */       exportedModel.display.thirdperson_righthand.scale[1] = 0.4F;
/* 666 */       exportedModel.display.thirdperson_righthand.scale[2] = 0.4F;
/*     */     } else {
/* 668 */       exportedModel.display.thirdperson_lefthand.scale[0] = 0.0F;
/* 669 */       exportedModel.display.thirdperson_lefthand.scale[1] = 0.0F;
/* 670 */       exportedModel.display.thirdperson_lefthand.scale[2] = 0.0F;
/*     */       
/* 672 */       exportedModel.display.thirdperson_righthand.scale[0] = 0.0F;
/* 673 */       exportedModel.display.thirdperson_righthand.scale[1] = 0.0F;
/* 674 */       exportedModel.display.thirdperson_righthand.scale[2] = 0.0F;
/*     */     } 
/* 676 */     exportedModel.setBaseLayer(type.getAssetDir() + "/" + ((type.iconName != null) ? type.iconName : type.internalName));
/* 677 */     return exportedModel;
/*     */   }
/*     */   
/*     */   private ItemModelExport createJson(BaseType type, String iconName) {
/* 681 */     ItemModelExport exportedModel = new ItemModelExport();
/*     */     
/* 683 */     exportedModel.display.thirdperson_lefthand.scale[0] = 0.4F;
/* 684 */     exportedModel.display.thirdperson_lefthand.scale[1] = 0.4F;
/* 685 */     exportedModel.display.thirdperson_lefthand.scale[2] = 0.4F;
/*     */     
/* 687 */     exportedModel.display.thirdperson_righthand.scale[0] = 0.4F;
/* 688 */     exportedModel.display.thirdperson_righthand.scale[1] = 0.4F;
/* 689 */     exportedModel.display.thirdperson_righthand.scale[2] = 0.4F;
/*     */     
/* 691 */     exportedModel.setBaseLayer(type.getAssetDir() + "/" + iconName);
/* 692 */     return exportedModel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(MWSound sound) {
/* 697 */     SoundEvent soundEvent = modSounds.get(sound.soundName);
/* 698 */     if (soundEvent == null) {
/* 699 */       ModularWarfare.LOGGER.error(String.format("The sound named '%s' does not exist. Skipping playSound", new Object[] { sound.soundName }));
/*     */       
/*     */       return;
/*     */     } 
/* 703 */     (Minecraft.func_71410_x()).field_71441_e.func_184133_a((EntityPlayer)(Minecraft.func_71410_x()).field_71439_g, sound.blockPos, soundEvent, SoundCategory.PLAYERS, sound.volume, sound.pitch);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSound(String soundName) {
/* 708 */     ResourceLocation resourceLocation = new ResourceLocation("modularwarfare", soundName);
/* 709 */     modSounds.put(soundName, (new SoundEvent(resourceLocation)).setRegistryName(resourceLocation));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
/* 714 */     IForgeRegistry<SoundEvent> registry = event.getRegistry();
/* 715 */     for (WeaponSoundType weaponSoundType : WeaponSoundType.values()) {
/* 716 */       if (weaponSoundType.defaultSound != null) {
/* 717 */         registerSound(weaponSoundType.defaultSound);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 722 */     for (SoundEvent soundEvent : modSounds.values()) {
/* 723 */       if (!registry.containsKey(soundEvent.getRegistryName())) {
/* 724 */         registry.register((IForgeRegistryEntry)soundEvent);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
/* 732 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
/*     */ 
/*     */       
/* 735 */       RenderingRegistry.registerEntityRenderingHandler(EntityBulletHole.class, (IRenderFactory)RenderDecal.FACTORY);
/*     */ 
/*     */       
/* 738 */       RenderingRegistry.registerEntityRenderingHandler(EntityShell.class, (IRenderFactory)RenderShell.FACTORY);
/*     */ 
/*     */       
/* 741 */       RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, (IRenderFactory)RenderGrenadeEntity.FACTORY);
/* 742 */       RenderingRegistry.registerEntityRenderingHandler(EntitySmokeGrenade.class, (IRenderFactory)RenderGrenadeEntity.FACTORY);
/* 743 */       RenderingRegistry.registerEntityRenderingHandler(EntityStunGrenade.class, (IRenderFactory)RenderGrenadeEntity.FACTORY);
/*     */       
/* 745 */       RenderingRegistry.registerEntityRenderingHandler(EntityItemLoot.class, (IRenderFactory)RenderItemLoot.FACTORY);
/*     */       
/* 747 */       RenderingRegistry.registerEntityRenderingHandler(EntityBulletClient.class, (IRenderFactory)RenderBullet.FACTORY);
/*     */ 
/*     */       
/* 750 */       RenderingRegistry.registerEntityRenderingHandler(EntityExplosiveProjectile.class, (IRenderFactory)RenderProjectile.FACTORY);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onShootAnimation(EntityPlayer player, String wepType, int fireTickDelay, float recoilPitch, float recoilYaw) {
/* 757 */     GunType gunType = ((ItemGun)ModularWarfare.gunTypes.get(wepType)).type;
/* 758 */     if (gunType != null) {
/* 759 */       if (gunType.animationType == WeaponAnimationType.BASIC) {
/* 760 */         ClientRenderHooks.getAnimMachine((EntityLivingBase)player).triggerShoot((ModelGun)gunType.model, gunType, fireTickDelay);
/*     */       } else {
/* 762 */         float rand = (float)Math.random();
/* 763 */         ClientEventHandler.cemeraBobbing = lastBobbingParm * (0.3F + 0.4F * Math.abs(rand)) * ((GunEnhancedRenderConfig)gunType.enhancedModel.config).extra.bobbingFactor;
/* 764 */         lastBobbingParm = -lastBobbingParm;
/* 765 */         AnimationController controller = gunEnhancedRenderer.getController((EntityLivingBase)player, (GunEnhancedRenderConfig)gunType.enhancedModel.config);
/* 766 */         ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)player).triggerShoot(controller, (ModelEnhancedGun)gunType.enhancedModel, gunType, fireTickDelay);
/*     */       } 
/*     */ 
/*     */       
/* 770 */       RenderParameters.rate = Math.min(RenderParameters.rate + 0.07F, 1.0F);
/*     */       
/* 772 */       float recoilPitchGripFactor = 1.0F;
/* 773 */       float recoilYawGripFactor = 1.0F;
/*     */       
/* 775 */       float recoilPitchBarrelFactor = 1.0F;
/* 776 */       float recoilYawBarrelFactor = 1.0F;
/*     */       
/* 778 */       float recoilPitchStockFactor = 1.0F;
/* 779 */       float recoilYawStockFactor = 1.0F;
/*     */       
/* 781 */       if (GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Grip) != null) {
/* 782 */         ItemAttachment gripAttachment = (ItemAttachment)GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Grip).func_77973_b();
/* 783 */         recoilPitchGripFactor = gripAttachment.type.grip.recoilPitchFactor;
/* 784 */         recoilYawGripFactor = gripAttachment.type.grip.recoilYawFactor;
/*     */       } 
/*     */       
/* 787 */       if (GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Barrel) != null) {
/* 788 */         ItemAttachment barrelAttachment = (ItemAttachment)GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Barrel).func_77973_b();
/* 789 */         recoilPitchBarrelFactor = barrelAttachment.type.barrel.recoilPitchFactor;
/* 790 */         recoilYawBarrelFactor = barrelAttachment.type.barrel.recoilYawFactor;
/*     */       } 
/*     */       
/* 793 */       if (GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Stock) != null) {
/* 794 */         ItemAttachment stockAttachment = (ItemAttachment)GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Stock).func_77973_b();
/* 795 */         recoilPitchStockFactor = stockAttachment.type.stock.recoilPitchFactor;
/* 796 */         recoilYawStockFactor = stockAttachment.type.stock.recoilYawFactor;
/*     */       } 
/*     */       
/* 799 */       boolean isCrawling = false;
/* 800 */       if (ModularWarfare.isLoadedModularMovements && 
/* 801 */         ClientLitener.clientPlayerState.isCrawling) {
/* 802 */         isCrawling = true;
/*     */       }
/*     */       
/* 805 */       float offsetYaw = 0.0F;
/* 806 */       float offsetPitch = 0.0F;
/* 807 */       if (!ClientRenderHooks.isAiming && !ClientRenderHooks.isAimingScope) {
/* 808 */         offsetPitch = gunType.recoilPitch;
/* 809 */         offsetPitch += gunType.randomRecoilPitch * 2.0F - gunType.randomRecoilPitch;
/* 810 */         offsetPitch *= recoilPitchGripFactor * recoilPitchBarrelFactor * recoilPitchStockFactor;
/*     */ 
/*     */         
/* 813 */         offsetYaw = gunType.recoilYaw;
/* 814 */         offsetYaw *= (new Random()).nextFloat() * gunType.randomRecoilYaw * 2.0F - gunType.randomRecoilYaw;
/* 815 */         offsetYaw *= recoilYawGripFactor * recoilYawBarrelFactor * recoilYawStockFactor;
/* 816 */         offsetYaw *= RenderParameters.rate * (isCrawling ? 0.2F : 1.0F);
/* 817 */         offsetYaw *= RenderParameters.phase ? 1.0F : -1.0F;
/*     */       } else {
/*     */         
/* 820 */         offsetPitch = gunType.recoilPitch;
/* 821 */         offsetPitch += gunType.randomRecoilPitch * 2.0F - gunType.randomRecoilPitch;
/* 822 */         offsetPitch *= recoilPitchGripFactor * recoilPitchBarrelFactor * recoilPitchStockFactor;
/* 823 */         offsetPitch *= gunType.recoilAimReducer;
/*     */         
/* 825 */         offsetYaw = gunType.recoilYaw;
/* 826 */         offsetYaw *= (new Random()).nextFloat() * gunType.randomRecoilYaw * 2.0F - gunType.randomRecoilYaw;
/* 827 */         offsetYaw *= recoilYawGripFactor * recoilYawBarrelFactor * recoilYawStockFactor;
/* 828 */         offsetYaw *= RenderParameters.rate * (isCrawling ? 0.2F : 1.0F);
/* 829 */         offsetYaw *= gunType.recoilAimReducer;
/* 830 */         offsetYaw *= RenderParameters.phase ? 1.0F : -1.0F;
/*     */       } 
/* 832 */       if (ModularWarfare.isLoadedModularMovements && 
/* 833 */         ClientLitener.clientPlayerState.isCrawling) {
/* 834 */         offsetPitch *= gunType.recoilCrawlPitchFactor;
/* 835 */         offsetYaw *= gunType.recoilCrawlYawFactor;
/*     */       } 
/*     */       
/* 838 */       RenderParameters.playerRecoilPitch += offsetPitch;
/* 839 */       if (Math.random() > 0.5D) {
/* 840 */         RenderParameters.playerRecoilYaw += offsetYaw;
/*     */       } else {
/* 842 */         RenderParameters.playerRecoilYaw -= offsetYaw;
/*     */       } 
/* 844 */       RenderParameters.phase = !RenderParameters.phase;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onReloadAnimation(EntityPlayer player, String wepType, int reloadTime, int reloadCount, int reloadType) {
/* 850 */     ClientTickHandler.playerReloadCooldown.put(player.func_110124_au(), Integer.valueOf(reloadTime));
/* 851 */     ItemGun gunType = (ItemGun)ModularWarfare.gunTypes.get(wepType);
/* 852 */     if (gunType != null) {
/* 853 */       if (gunType.type.animationType == WeaponAnimationType.BASIC) {
/* 854 */         ClientRenderHooks.getAnimMachine((EntityLivingBase)player).triggerReload(reloadTime, reloadCount, (ModelGun)gunType.type.model, ReloadType.getTypeFromInt(reloadType), player.func_70051_ag());
/*     */       } else {
/* 856 */         AnimationController controller = gunEnhancedRenderer.getController((EntityLivingBase)player, (GunEnhancedRenderConfig)gunType.type.enhancedModel.config);
/* 857 */         ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)player).triggerReload(controller, (EntityLivingBase)player, reloadTime, reloadCount, (ModelEnhancedGun)gunType.type.enhancedModel, ReloadType.getTypeFromInt(reloadType));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onShootFailedAnimation(EntityPlayer player, String wepType) {
/* 864 */     ItemGun gunType = (ItemGun)ModularWarfare.gunTypes.get(wepType);
/* 865 */     if (gunType != null && 
/* 866 */       gunType.type.animationType == WeaponAnimationType.ENHANCED) {
/* 867 */       AnimationController controller = gunEnhancedRenderer.getController((EntityLivingBase)player, (GunEnhancedRenderConfig)gunType.type.enhancedModel.config);
/* 868 */       ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)player).triggerShoot(controller, (ModelEnhancedGun)gunType.type.enhancedModel, gunType.type, 0, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onModeChangeAnimation(EntityPlayer player, String wepType) {
/* 875 */     ItemGun gunType = (ItemGun)ModularWarfare.gunTypes.get(wepType);
/* 876 */     if (gunType != null && 
/* 877 */       gunType.type.animationType == WeaponAnimationType.ENHANCED && 
/* 878 */       gunEnhancedRenderer.getController((EntityLivingBase)player, null) != null) {
/* 879 */       (gunEnhancedRenderer.getController((EntityLivingBase)player, null)).MODE_CHANGE = 0.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getClientWorld() {
/* 887 */     return (World)(FMLClientHandler.instance().getClient()).field_71441_e;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerEventHandlers() {
/* 892 */     super.registerEventHandlers();
/* 893 */     MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
/* 894 */     MinecraftForge.EVENT_BUS.register(new OBBPlayerManager());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlood(EntityLivingBase living, int amount, boolean onhit) {
/* 900 */     if (onhit) {
/* 901 */       addBlood(living, amount);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void playHitmarker(boolean headshot) {
/* 907 */     if (ModConfig.INSTANCE.hud.hitmarkers) {
/* 908 */       Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_194007_a(modSounds.get("hitmarker"), 1.0F, 4.0F));
/* 909 */       GunUI.addHitMarker(headshot);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBlood(EntityLivingBase living, int amount) {
/* 915 */     for (int k = 0; k < amount; k++) {
/* 916 */       float attenuator = 0.3F;
/* 917 */       double mX = (-MathHelper.func_76126_a(living.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(living.field_70125_A / 180.0F * 3.1415927F) * attenuator);
/* 918 */       double mZ = (MathHelper.func_76134_b(living.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(living.field_70125_A / 180.0F * 3.1415927F) * attenuator);
/* 919 */       double mY = (-MathHelper.func_76126_a(living.field_70125_A / 180.0F * 3.1415927F) * attenuator + 0.1F);
/* 920 */       attenuator = 0.02F;
/* 921 */       float var5 = living.func_70681_au().nextFloat() * 3.1415927F * 2.0F;
/* 922 */       attenuator *= living.func_70681_au().nextFloat();
/* 923 */       mX += Math.cos(var5) * attenuator;
/* 924 */       mY += ((living.func_70681_au().nextFloat() - living.func_70681_au().nextFloat()) * 0.1F);
/* 925 */       mZ += Math.sin(var5) * attenuator;
/* 926 */       EntityBloodFX entityBloodFX = new EntityBloodFX(living.func_130014_f_(), living.field_70165_t, living.field_70163_u + 0.5D + living.func_70681_au().nextDouble() * 0.7D, living.field_70161_v, living.field_70159_w * 2.0D + mX, living.field_70181_x + mY, living.field_70179_y * 2.0D + mZ, 0.0D);
/* 927 */       (Minecraft.func_71410_x()).field_71452_i.func_78873_a((Particle)entityBloodFX);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetSens() {
/* 933 */     ClientRenderHooks.isAimingScope = false;
/* 934 */     ClientRenderHooks.isAiming = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnExplosionParticle(World world, double x, double y, double z) {
/* 939 */     if (!world.field_72995_K) {
/* 940 */       super.spawnExplosionParticle(world, x, y, z);
/*     */       return;
/*     */     } 
/* 943 */     ParticleExplosion particleExplosion = new ParticleExplosion(world, x, y, z);
/* 944 */     (Minecraft.func_71410_x()).field_71452_i.func_78873_a((Particle)particleExplosion);
/*     */   }
/*     */   
/*     */   public void spawnRocketParticle(World world, double x, double y, double z) {
/* 948 */     if (!world.field_72995_K) {
/* 949 */       super.spawnRocketParticle(world, x, y, z);
/*     */       return;
/*     */     } 
/* 952 */     ParticleRocket particleRocket = new ParticleRocket(world, x, y, z);
/* 953 */     (Minecraft.func_71410_x()).field_71452_i.func_78873_a((Particle)particleRocket);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playFlashSound(EntityPlayer entityPlayer) {
/* 958 */     Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)new PositionedSoundRecord(ModSounds.FLASHED, SoundCategory.PLAYERS, FlashSystem.flashValue / 1000.0F, 1.0F, (float)entityPlayer.field_70165_t, (float)entityPlayer.field_70163_u, (float)entityPlayer.field_70161_v));
/* 959 */     Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)new PositionedSoundRecord(ModSounds.FLASHED, SoundCategory.PLAYERS, 5.0F, 0.2F, (float)entityPlayer.field_70165_t, (float)entityPlayer.field_70163_u, (float)entityPlayer.field_70161_v));
/* 960 */     Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)new PositionedSoundRecord(ModSounds.FLASHED, SoundCategory.PLAYERS, 5.0F, 0.1F, (float)entityPlayer.field_70165_t, (float)entityPlayer.field_70163_u, (float)entityPlayer.field_70161_v));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\ClientProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */