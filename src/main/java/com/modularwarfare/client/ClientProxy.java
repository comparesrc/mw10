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
/*     */   public static boolean shoulderSurfingLoaded = false;
/*     */   
/*     */   public static GCCompatInterop galacticraftInterop;
/*     */   
/*     */   public static ObfuscateCompatInterop obfuscateInterop;
/*     */   
/* 157 */   private static int lastBobbingParm = 1;
/*     */   
/*     */   public KillFeedManager getKillChatManager() {
/* 160 */     this; return killFeedManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void construction(FMLConstructionEvent event) {
/* 165 */     super.construction(event);
/*     */     
/* 167 */     for (File file : modularWarfareDir.listFiles()) {
/* 168 */       if (!file.getName().contains("cache") && !file.getName().contains("officialmw") && !file.getName().contains("highres")) {
/* 169 */         if (zipJar.matcher(file.getName()).matches()) {
/*     */           
/*     */           try {
/* 172 */             IZip izip = ModularWarfare.getiZip(file);
/*     */             
/* 174 */             HashMap<String, Object> map = new HashMap<>();
/* 175 */             map.put("modid", "modularwarfare");
/* 176 */             map.put("name", "ModularWarfare : " + file.getName());
/* 177 */             map.put("version", "1");
/*     */             
/* 179 */             MWResourcePack.Container container = new MWResourcePack.Container("com.modularwarfare.ModularWarfare", new ModCandidate(file, file, ContainerType.JAR), map, izip, "ModularWarfare : " + file.getName());
/* 180 */             container.bindMetadata(MetadataCollection.from(null, ""));
/* 181 */             FMLClientHandler.instance().addModAsResource((ModContainer)container);
/* 182 */             ModularWarfare.contentPacks.add(file);
/* 183 */           } catch (Exception e) {
/* 184 */             e.printStackTrace();
/*     */           } 
/* 186 */         } else if (file.isDirectory()) {
/*     */           try {
/* 188 */             HashMap<String, Object> map = new HashMap<>();
/* 189 */             map.put("modid", "modularwarfare");
/* 190 */             map.put("name", "ModularWarfare : " + file.getName());
/* 191 */             map.put("version", "1");
/* 192 */             FMLModContainer container = new FMLModContainer("com.modularwarfare.ModularWarfare", new ModCandidate(file, file, file.isDirectory() ? ContainerType.DIR : ContainerType.JAR), map);
/* 193 */             container.bindMetadata(MetadataCollection.from(null, ""));
/* 194 */             FMLClientHandler.instance().addModAsResource((ModContainer)container);
/* 195 */           } catch (Exception e) {
/* 196 */             e.printStackTrace();
/*     */           } 
/* 198 */           ModularWarfare.contentPacks.add(file);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preload() {
/* 208 */     SmoothSwingTicker.startSmoothSwingTimer();
/*     */     
/* 210 */     MinecraftForge.EVENT_BUS.register(this);
/* 211 */     MinecraftForge.EVENT_BUS.register(new CPEventHandler());
/* 212 */     startPatches();
/* 213 */     (Minecraft.func_71410_x()).field_71474_y.field_178881_t = false;
/*     */   }
/*     */   
/*     */   public void startPatches() {
/* 217 */     if (Loader.isModLoaded("shouldersurfing")) {
/* 218 */       shoulderSurfingLoaded = true;
/*     */     }
/* 220 */     if (Loader.isModLoaded("customnpcs")) {
/* 221 */       CustomNPCListener customNPCListener = new CustomNPCListener();
/* 222 */       MinecraftForge.EVENT_BUS.register(customNPCListener);
/*     */     } 
/* 224 */     if (Loader.isModLoaded("galacticraftcore")) {
/*     */       try {
/* 226 */         galacticraftInterop = Class.forName("com.modularwarfare.client.patch.galacticraft.GCInteropImpl").<GCCompatInterop>asSubclass(GCCompatInterop.class).newInstance();
/* 227 */         ModularWarfare.LOGGER.info("Galatic Craft has been detected! Will attempt to patch.");
/* 228 */         galacticraftInterop.applyFix();
/* 229 */       } catch (Exception e) {
/* 230 */         e.printStackTrace();
/* 231 */         galacticraftInterop = (GCCompatInterop)new GCDummyInterop();
/*     */       } 
/*     */     } else {
/* 234 */       galacticraftInterop = (GCCompatInterop)new GCDummyInterop();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() {
/* 240 */     super.load();
/* 241 */     new KeyInputHandler();
/* 242 */     new ClientTickHandler();
/* 243 */     new ClientGunHandler();
/* 244 */     new RenderGuiHandler();
/*     */     
/* 246 */     this; renderHooks = new ClientRenderHooks();
/* 247 */     this; MinecraftForge.EVENT_BUS.register(renderHooks);
/*     */     
/* 249 */     this; scopeUtils = new ScopeUtils();
/* 250 */     this; MinecraftForge.EVENT_BUS.register(scopeUtils);
/*     */     
/* 252 */     this; flashImage = new FlashSystem();
/* 253 */     this; MinecraftForge.EVENT_BUS.register(flashImage);
/*     */     
/* 255 */     this; attachmentUI = new AttachmentUI();
/* 256 */     this; MinecraftForge.EVENT_BUS.register(attachmentUI);
/*     */     
/* 258 */     this; gunUI = new GunUI();
/* 259 */     this; MinecraftForge.EVENT_BUS.register(gunUI);
/*     */     
/* 261 */     this; killFeedManager = new KillFeedManager();
/* 262 */     this; MinecraftForge.EVENT_BUS.register(new KillFeedRender(killFeedManager));
/*     */     
/* 264 */     this; autoSwitchToFirstView = new AutoSwitchToFirstView();
/* 265 */     this; MinecraftForge.EVENT_BUS.register(autoSwitchToFirstView);
/*     */     
/* 267 */     WeaponAnimations.registerAnimation("rifle", (WeaponAnimation)new AnimationRifle());
/* 268 */     WeaponAnimations.registerAnimation("rifle2", (WeaponAnimation)new AnimationRifle2());
/* 269 */     WeaponAnimations.registerAnimation("rifle3", (WeaponAnimation)new AnimationRifle3());
/* 270 */     WeaponAnimations.registerAnimation("rifle4", (WeaponAnimation)new AnimationRifle4());
/* 271 */     WeaponAnimations.registerAnimation("pistol", (WeaponAnimation)new AnimationPistol());
/* 272 */     WeaponAnimations.registerAnimation("revolver", (WeaponAnimation)new AnimationRevolver());
/* 273 */     WeaponAnimations.registerAnimation("shotgun", (WeaponAnimation)new AnimationShotgun());
/* 274 */     WeaponAnimations.registerAnimation("sniper", (WeaponAnimation)new AnimationSniperBottom());
/* 275 */     WeaponAnimations.registerAnimation("sniper_top", (WeaponAnimation)new AnimationSniperTop());
/* 276 */     WeaponAnimations.registerAnimation("sideclip", (WeaponAnimation)new AnimationSideClip());
/* 277 */     WeaponAnimations.registerAnimation("toprifle", (WeaponAnimation)new AnimationTopRifle());
/* 278 */     WeaponAnimations.registerAnimation("rocket_launcher", (WeaponAnimation)new AnimationRocketLauncher());
/*     */     
/* 280 */     Map<String, RenderPlayer> skinMap = Minecraft.func_71410_x().func_175598_ae().getSkinMap();
/* 281 */     for (RenderPlayer renderer : skinMap.values()) {
/* 282 */       setupLayers(renderer);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setupLayers(RenderPlayer renderer) {
/* 287 */     MWFRenderHelper helper = new MWFRenderHelper((RenderLivingBase)renderer);
/* 288 */     helper.getLayerRenderers().add(0, new ResetHiddenModelLayer(renderer));
/* 289 */     renderer.func_177094_a((LayerRenderer)new RenderLayerBackpack(renderer, (renderer.func_177087_b()).field_178730_v));
/* 290 */     renderer.func_177094_a((LayerRenderer)new RenderLayerBody(renderer, (renderer.func_177087_b()).field_178730_v));
/* 291 */     renderer.func_177094_a((LayerRenderer)new RenderLayerHeldGun((RenderLivingBase)renderer));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 297 */     if (ModUtil.isMac()) {
/* 298 */       ModConfig.INSTANCE.model_optimization = false;
/*     */     }
/*     */     
/* 301 */     ((IReloadableResourceManager)Minecraft.func_71410_x().func_110442_L()).func_110542_a((IResourceManagerReloadListener)new ISelectiveResourceReloadListener()
/*     */         {
/*     */           
/*     */           public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate)
/*     */           {
/* 306 */             ClientProxy.this.loadTextures();
/*     */           }
/*     */         });
/*     */     
/* 310 */     loadTextures();
/*     */     
/* 312 */     ClientCommandHandler.instance.func_71560_a((ICommand)new CommandMWClient());
/*     */     
/* 314 */     Programs.init();
/*     */   }
/*     */   
/*     */   public void loadTextures() {
/* 318 */     ModularWarfare.LOGGER.info("Preloading textures");
/* 319 */     long time = System.currentTimeMillis();
/* 320 */     preloadSkinTypes.forEach((skin, type) -> {
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
/* 332 */     preloadFlashTex.forEach(type -> type.resourceLocations.forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     ModularWarfare.LOGGER.info("All textures are ready(" + (System.currentTimeMillis() - time) + "ms)");
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onModelRegistry(ModelRegistryEvent event) {
/* 345 */     for (ItemGun itemGun : ModularWarfare.gunTypes.values()) {
/* 346 */       ModelLoader.setCustomModelResourceLocation((Item)itemGun, 0, new ModelResourceLocation("modularwarfare:" + itemGun.type.internalName));
/*     */     }
/*     */     
/* 349 */     for (ItemAmmo itemAmmo : ModularWarfare.ammoTypes.values()) {
/* 350 */       ModelLoader.setCustomModelResourceLocation((Item)itemAmmo, 0, new ModelResourceLocation("modularwarfare:" + itemAmmo.type.internalName));
/*     */     }
/*     */     
/* 353 */     for (ItemAttachment itemAttachment : ModularWarfare.attachmentTypes.values()) {
/* 354 */       ModelLoader.setCustomModelResourceLocation((Item)itemAttachment, 0, new ModelResourceLocation("modularwarfare:" + itemAttachment.type.internalName));
/*     */     }
/*     */     
/* 357 */     for (ItemBullet itemBullet : ModularWarfare.bulletTypes.values()) {
/* 358 */       ModelLoader.setCustomModelResourceLocation((Item)itemBullet, 0, new ModelResourceLocation("modularwarfare:" + itemBullet.type.internalName));
/*     */     }
/*     */     
/* 361 */     for (ItemMWArmor itemArmor : ModularWarfare.armorTypes.values()) {
/* 362 */       ModelLoader.setCustomModelResourceLocation((Item)itemArmor, 0, new ModelResourceLocation("modularwarfare:" + itemArmor.internalName));
/*     */     }
/*     */     
/* 365 */     for (ItemSpecialArmor itemArmor : ModularWarfare.specialArmorTypes.values()) {
/* 366 */       ModelLoader.setCustomModelResourceLocation((Item)itemArmor, 0, new ModelResourceLocation("modularwarfare:" + itemArmor.type.internalName));
/*     */     }
/*     */     
/* 369 */     for (ItemSpray itemSpray : ModularWarfare.sprayTypes.values()) {
/* 370 */       ModelLoader.setCustomModelResourceLocation((Item)itemSpray, 0, new ModelResourceLocation("modularwarfare:" + itemSpray.type.internalName));
/*     */     }
/*     */     
/* 373 */     for (ItemBackpack itemBackpack : ModularWarfare.backpackTypes.values()) {
/* 374 */       ModelLoader.setCustomModelResourceLocation((Item)itemBackpack, 0, new ModelResourceLocation("modularwarfare:" + itemBackpack.type.internalName));
/*     */     }
/*     */     
/* 377 */     for (ItemGrenade itemGrenade : ModularWarfare.grenadeTypes.values()) {
/* 378 */       ModelLoader.setCustomModelResourceLocation((Item)itemGrenade, 0, new ModelResourceLocation("modularwarfare:" + itemGrenade.type.internalName));
/*     */     }
/*     */     
/* 381 */     ModelLoader.setCustomModelResourceLocation((Item)itemLight, 0, new ModelResourceLocation(itemLight.getRegistryName(), "inventory"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void forceReload() {
/* 387 */     FMLClientHandler.instance().refreshResources(new IResourceType[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getModelName(String in) {
/* 397 */     String[] split = in.split("\\.");
/*     */     
/* 399 */     if (split.length == 1) {
/* 400 */       return in;
/*     */     }
/* 402 */     if (split.length > 1) {
/* 403 */       String out = split[split.length - 1];
/* 404 */       for (int i = split.length - 2; i >= 0; i--) {
/* 405 */         out = split[i] + "." + out;
/*     */       }
/* 407 */       return out;
/*     */     } 
/* 409 */     return in;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T loadModel(String s, String shortName, Class<T> typeClass) {
/* 417 */     if (s == null || shortName == null)
/* 418 */       return null; 
/*     */     try {
/* 420 */       return typeClass.cast(Class.forName(modelDir + getModelName(s)).getConstructor(new Class[0]).newInstance(new Object[0]));
/* 421 */     } catch (Exception e) {
/* 422 */       ModularWarfare.LOGGER.error("Failed to load staticModel : " + shortName + " (" + s + ")");
/* 423 */       e.printStackTrace();
/*     */       
/* 425 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reloadModels(boolean reloadSkins) {
/* 430 */     EnhancedModel.clearCache();
/* 431 */     for (BaseType baseType : ModularWarfare.baseTypes) {
/* 432 */       if (baseType.hasModel()) {
/* 433 */         baseType.reloadModel();
/*     */       }
/*     */     } 
/* 436 */     if (reloadSkins) {
/* 437 */       forceReload();
/*     */     }
/*     */   }
/*     */   
/*     */   public void generateJsonModels(ArrayList<BaseType> types) {
/* 442 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/*     */     
/* 444 */     GenerateJsonModelsEvent event = new GenerateJsonModelsEvent();
/* 445 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     
/* 447 */     for (BaseType type : types) {
/* 448 */       if (type.contentPack == null) {
/*     */         continue;
/*     */       }
/* 451 */       File contentPackDir = new File(ModularWarfare.MOD_DIR, type.contentPack);
/*     */       
/* 453 */       if (zipJar.matcher(contentPackDir.getName()).matches()) {
/*     */         continue;
/*     */       }
/* 456 */       if (contentPackDir.exists() && contentPackDir.isDirectory()) {
/*     */         
/* 458 */         File itemModelsDir = new File(contentPackDir, "/assets/modularwarfare/models/item");
/* 459 */         if (!itemModelsDir.exists()) {
/* 460 */           itemModelsDir.mkdirs();
/*     */         }
/* 462 */         File typeModel = new File(itemModelsDir, type.internalName + ".json");
/* 463 */         if (!typeModel.exists()) {
/* 464 */           if (type instanceof ArmorType) {
/* 465 */             ArmorType armorType = (ArmorType)type;
/* 466 */             for (ArmorType.ArmorInfo armorInfo : armorType.armorTypes.values()) {
/* 467 */               String internalName = (armorInfo.internalName != null) ? armorInfo.internalName : armorType.internalName;
/* 468 */               typeModel = new File(itemModelsDir, internalName + ".json");
/*     */               try {
/* 470 */                 FileWriter fileWriter = new FileWriter(typeModel, false);
/* 471 */                 gson.toJson(createJson(type, internalName), fileWriter);
/* 472 */                 fileWriter.flush();
/* 473 */                 fileWriter.close();
/* 474 */               } catch (Exception e) {
/* 475 */                 e.printStackTrace();
/*     */               } 
/*     */             } 
/*     */           } else {
/*     */             try {
/* 480 */               FileWriter fileWriter = new FileWriter(typeModel, false);
/* 481 */               gson.toJson(createJson(type), fileWriter);
/* 482 */               fileWriter.flush();
/* 483 */               fileWriter.close();
/* 484 */             } catch (Exception e) {
/* 485 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 494 */       if (ModularWarfare.DEV_ENV) {
/* 495 */         File dir = new File(contentPackDir, "/" + type.getAssetDir() + "/render");
/* 496 */         if (!dir.exists()) {
/* 497 */           dir.mkdirs();
/*     */         }
/* 499 */         File renderFile = new File(dir, type.internalName + ".render.json");
/* 500 */         if (!renderFile.exists()) {
/*     */           try {
/* 502 */             FileWriter fileWriter = new FileWriter(renderFile, true);
/* 503 */             if (type instanceof GunType) {
/* 504 */               if (((GunType)type).animationType.equals(WeaponAnimationType.ENHANCED)) {
/* 505 */                 GunEnhancedRenderConfig renderConfig = new GunEnhancedRenderConfig();
/* 506 */                 renderConfig.modelFileName = type.internalName.replaceAll(type.contentPack + ".", "");
/* 507 */                 renderConfig.modelFileName += ".glb";
/* 508 */                 gson.toJson(renderConfig, fileWriter);
/*     */               } else {
/* 510 */                 GunRenderConfig renderConfig = new GunRenderConfig();
/* 511 */                 renderConfig.modelFileName = type.internalName.replaceAll(type.contentPack + ".", "");
/* 512 */                 renderConfig.modelFileName += ".obj";
/* 513 */                 gson.toJson(renderConfig, fileWriter);
/*     */               } 
/* 515 */               fileWriter.flush();
/* 516 */               fileWriter.close();
/*     */             } 
/* 518 */           } catch (Exception e) {
/* 519 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateJsonSounds(Collection<BaseType> types, boolean replace) {
/* 528 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/* 529 */     HashMap<String, ArrayList<String>> cpSounds = new HashMap<>();
/*     */     
/* 531 */     for (BaseType baseType : types) {
/* 532 */       if (baseType.contentPack == null) {
/*     */         continue;
/*     */       }
/* 535 */       String contentPack = baseType.contentPack;
/*     */       
/* 537 */       if (!cpSounds.containsKey(contentPack)) {
/* 538 */         cpSounds.put(contentPack, new ArrayList<>());
/*     */       }
/* 540 */       for (WeaponSoundType weaponSoundType : baseType.weaponSoundMap.keySet()) {
/* 541 */         ArrayList<SoundEntry> soundEntries = (ArrayList<SoundEntry>)baseType.weaponSoundMap.get(weaponSoundType);
/* 542 */         for (SoundEntry soundEntry : soundEntries) {
/* 543 */           if (soundEntry.soundName != null && !((ArrayList)cpSounds.get(contentPack)).contains(soundEntry.soundName)) {
/* 544 */             ((ArrayList<String>)cpSounds.get(contentPack)).add(soundEntry.soundName);
/*     */           }
/* 546 */           if (soundEntry.soundNameDistant != null && !((ArrayList)cpSounds.get(contentPack)).contains(soundEntry.soundNameDistant)) {
/* 547 */             ((ArrayList<String>)cpSounds.get(contentPack)).add(soundEntry.soundNameDistant);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 552 */     for (String contentPack : cpSounds.keySet()) {
/*     */       try {
/* 554 */         File contentPackDir = new File(ModularWarfare.MOD_DIR, contentPack);
/* 555 */         if (contentPackDir.exists() && contentPackDir.isDirectory()) {
/* 556 */           ArrayList<String> soundEntries = cpSounds.get(contentPack);
/* 557 */           if (soundEntries != null && !soundEntries.isEmpty()) {
/* 558 */             Path assetsDir = Paths.get(ModularWarfare.MOD_DIR.getAbsolutePath() + "/" + contentPack + "/assets/modularwarfare/", new String[0]);
/* 559 */             if (!Files.exists(assetsDir, new java.nio.file.LinkOption[0]))
/* 560 */               Files.createDirectories(assetsDir, (FileAttribute<?>[])new FileAttribute[0]); 
/* 561 */             Path soundsFile = Paths.get(assetsDir + "/sounds.json", new String[0]);
/*     */             
/* 563 */             boolean soundsExists = Files.exists(soundsFile, new java.nio.file.LinkOption[0]);
/* 564 */             boolean shouldCreate = soundsExists ? replace : true;
/* 565 */             if (shouldCreate) {
/* 566 */               if (!soundsExists) {
/* 567 */                 Files.createFile(soundsFile, (FileAttribute<?>[])new FileAttribute[0]);
/*     */               }
/* 569 */               ArrayList<String> jsonEntries = new ArrayList<>();
/* 570 */               String format = "\"%s\":{\"category\": \"player\",\"subtitle\": \"MW Sound\",\"sounds\": [\"modularwarfare:%s\"]}";
/* 571 */               jsonEntries.add("{");
/* 572 */               for (int i = 0; i < soundEntries.size(); i++) {
/* 573 */                 if (i + 1 < soundEntries.size()) {
/*     */                   
/* 575 */                   jsonEntries.add(format.replaceAll("%s", soundEntries.get(i)) + ",");
/*     */                 } else {
/*     */                   
/* 578 */                   jsonEntries.add(format.replaceAll("%s", soundEntries.get(i)));
/*     */                 } 
/*     */               } 
/* 581 */               jsonEntries.add("}");
/* 582 */               Files.write(soundsFile, (Iterable)jsonEntries, Charset.forName("UTF-8"), new java.nio.file.OpenOption[0]);
/*     */             } 
/*     */           } 
/*     */         } 
/* 586 */       } catch (Exception exception) {
/* 587 */         if (ModularWarfare.DEV_ENV) {
/* 588 */           exception.printStackTrace(); continue;
/*     */         } 
/* 590 */         ModularWarfare.LOGGER.error(String.format("Failed to create sounds.json for content pack '%s'", new Object[] { contentPack }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateLangFiles(ArrayList<BaseType> types, boolean replace) {
/* 598 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/* 599 */     HashMap<String, ArrayList<BaseType>> langEntryMap = new HashMap<>();
/*     */     
/* 601 */     for (BaseType baseType : types) {
/* 602 */       if (baseType.contentPack == null) {
/*     */         continue;
/*     */       }
/* 605 */       String contentPack = baseType.contentPack;
/*     */       
/* 607 */       if (!langEntryMap.containsKey(contentPack)) {
/* 608 */         langEntryMap.put(contentPack, new ArrayList<>());
/*     */       }
/* 610 */       if (baseType.displayName != null && !((ArrayList)langEntryMap.get(contentPack)).contains(baseType)) {
/* 611 */         ((ArrayList<BaseType>)langEntryMap.get(contentPack)).add(baseType);
/*     */       }
/* 613 */       if (baseType instanceof ArmorType) {
/* 614 */         ((ArrayList<BaseType>)langEntryMap.get(contentPack)).add(baseType);
/*     */       }
/*     */     } 
/* 617 */     for (String contentPack : langEntryMap.keySet()) {
/*     */       try {
/* 619 */         File contentPackDir = new File(ModularWarfare.MOD_DIR, contentPack);
/* 620 */         if (contentPackDir.exists() && contentPackDir.isDirectory()) {
/* 621 */           ArrayList<BaseType> langEntries = langEntryMap.get(contentPack);
/* 622 */           if (langEntries != null && !langEntries.isEmpty()) {
/* 623 */             Path langDir = Paths.get(ModularWarfare.MOD_DIR.getAbsolutePath() + "/" + contentPack + "/assets/modularwarfare/lang/", new String[0]);
/* 624 */             if (!Files.exists(langDir, new java.nio.file.LinkOption[0]))
/* 625 */               Files.createDirectories(langDir, (FileAttribute<?>[])new FileAttribute[0]); 
/* 626 */             Path langPath = Paths.get(langDir + "/en_US.lang", new String[0]);
/*     */             
/* 628 */             boolean soundsExists = Files.exists(langPath, new java.nio.file.LinkOption[0]);
/* 629 */             boolean shouldCreate = soundsExists ? replace : true;
/* 630 */             if (shouldCreate) {
/* 631 */               if (!soundsExists) {
/* 632 */                 Files.createFile(langPath, (FileAttribute<?>[])new FileAttribute[0]);
/*     */               }
/* 634 */               ArrayList<String> jsonEntries = new ArrayList<>();
/* 635 */               String format = "item.%s.name=%s";
/* 636 */               for (int i = 0; i < langEntries.size(); i++) {
/* 637 */                 BaseType type = langEntries.get(i);
/* 638 */                 if (type instanceof ArmorType) {
/* 639 */                   ArmorType armorType = (ArmorType)type;
/* 640 */                   for (ArmorType.ArmorInfo armorInfo : armorType.armorTypes.values()) {
/* 641 */                     String internalName = (armorInfo.internalName != null) ? armorInfo.internalName : armorType.internalName;
/* 642 */                     jsonEntries.add(String.format(format, new Object[] { internalName, armorInfo.displayName }));
/*     */                   } 
/*     */                 } else {
/* 645 */                   jsonEntries.add(String.format(format, new Object[] { type.internalName, type.displayName }));
/*     */                 } 
/*     */               } 
/* 648 */               Files.write(langPath, (Iterable)jsonEntries, Charset.forName("UTF-8"), new java.nio.file.OpenOption[0]);
/*     */             } 
/*     */           } 
/*     */         } 
/* 652 */       } catch (Exception exception) {
/* 653 */         if (ModularWarfare.DEV_ENV) {
/* 654 */           exception.printStackTrace(); continue;
/*     */         } 
/* 656 */         ModularWarfare.LOGGER.error(String.format("Failed to create sounds.json for content pack '%s'", new Object[] { contentPack }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ItemModelExport createJson(BaseType type) {
/* 663 */     ItemModelExport exportedModel = new ItemModelExport();
/*     */     
/* 665 */     if (!(type instanceof GunType) && !(type instanceof com.modularwarfare.common.grenades.GrenadeType)) {
/* 666 */       exportedModel.display.thirdperson_lefthand.scale[0] = 0.4F;
/* 667 */       exportedModel.display.thirdperson_lefthand.scale[1] = 0.4F;
/* 668 */       exportedModel.display.thirdperson_lefthand.scale[2] = 0.4F;
/*     */       
/* 670 */       exportedModel.display.thirdperson_righthand.scale[0] = 0.4F;
/* 671 */       exportedModel.display.thirdperson_righthand.scale[1] = 0.4F;
/* 672 */       exportedModel.display.thirdperson_righthand.scale[2] = 0.4F;
/*     */     } else {
/* 674 */       exportedModel.display.thirdperson_lefthand.scale[0] = 0.0F;
/* 675 */       exportedModel.display.thirdperson_lefthand.scale[1] = 0.0F;
/* 676 */       exportedModel.display.thirdperson_lefthand.scale[2] = 0.0F;
/*     */       
/* 678 */       exportedModel.display.thirdperson_righthand.scale[0] = 0.0F;
/* 679 */       exportedModel.display.thirdperson_righthand.scale[1] = 0.0F;
/* 680 */       exportedModel.display.thirdperson_righthand.scale[2] = 0.0F;
/*     */     } 
/* 682 */     exportedModel.setBaseLayer(type.getAssetDir() + "/" + ((type.iconName != null) ? type.iconName : type.internalName));
/* 683 */     return exportedModel;
/*     */   }
/*     */   
/*     */   private ItemModelExport createJson(BaseType type, String iconName) {
/* 687 */     ItemModelExport exportedModel = new ItemModelExport();
/*     */     
/* 689 */     exportedModel.display.thirdperson_lefthand.scale[0] = 0.4F;
/* 690 */     exportedModel.display.thirdperson_lefthand.scale[1] = 0.4F;
/* 691 */     exportedModel.display.thirdperson_lefthand.scale[2] = 0.4F;
/*     */     
/* 693 */     exportedModel.display.thirdperson_righthand.scale[0] = 0.4F;
/* 694 */     exportedModel.display.thirdperson_righthand.scale[1] = 0.4F;
/* 695 */     exportedModel.display.thirdperson_righthand.scale[2] = 0.4F;
/*     */     
/* 697 */     exportedModel.setBaseLayer(type.getAssetDir() + "/" + iconName);
/* 698 */     return exportedModel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(MWSound sound) {
/* 703 */     SoundEvent soundEvent = modSounds.get(sound.soundName);
/* 704 */     if (soundEvent == null) {
/* 705 */       ModularWarfare.LOGGER.error(String.format("The sound named '%s' does not exist. Skipping playSound", new Object[] { sound.soundName }));
/*     */       
/*     */       return;
/*     */     } 
/* 709 */     (Minecraft.func_71410_x()).field_71441_e.func_184133_a((EntityPlayer)(Minecraft.func_71410_x()).field_71439_g, sound.blockPos, soundEvent, SoundCategory.PLAYERS, sound.volume, sound.pitch);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSound(String soundName) {
/* 714 */     ResourceLocation resourceLocation = new ResourceLocation("modularwarfare", soundName);
/* 715 */     modSounds.put(soundName, (new SoundEvent(resourceLocation)).setRegistryName(resourceLocation));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
/* 720 */     IForgeRegistry<SoundEvent> registry = event.getRegistry();
/* 721 */     for (WeaponSoundType weaponSoundType : WeaponSoundType.values()) {
/* 722 */       if (weaponSoundType.defaultSound != null) {
/* 723 */         registerSound(weaponSoundType.defaultSound);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 728 */     for (SoundEvent soundEvent : modSounds.values()) {
/* 729 */       if (!registry.containsKey(soundEvent.getRegistryName())) {
/* 730 */         registry.register((IForgeRegistryEntry)soundEvent);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
/* 738 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
/*     */ 
/*     */       
/* 741 */       RenderingRegistry.registerEntityRenderingHandler(EntityBulletHole.class, (IRenderFactory)RenderDecal.FACTORY);
/*     */ 
/*     */       
/* 744 */       RenderingRegistry.registerEntityRenderingHandler(EntityShell.class, (IRenderFactory)RenderShell.FACTORY);
/*     */ 
/*     */       
/* 747 */       RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, (IRenderFactory)RenderGrenadeEntity.FACTORY);
/* 748 */       RenderingRegistry.registerEntityRenderingHandler(EntitySmokeGrenade.class, (IRenderFactory)RenderGrenadeEntity.FACTORY);
/* 749 */       RenderingRegistry.registerEntityRenderingHandler(EntityStunGrenade.class, (IRenderFactory)RenderGrenadeEntity.FACTORY);
/*     */       
/* 751 */       RenderingRegistry.registerEntityRenderingHandler(EntityItemLoot.class, (IRenderFactory)RenderItemLoot.FACTORY);
/*     */       
/* 753 */       RenderingRegistry.registerEntityRenderingHandler(EntityBulletClient.class, (IRenderFactory)RenderBullet.FACTORY);
/*     */ 
/*     */       
/* 756 */       RenderingRegistry.registerEntityRenderingHandler(EntityExplosiveProjectile.class, (IRenderFactory)RenderProjectile.FACTORY);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onShootAnimation(EntityPlayer player, String wepType, int fireTickDelay, float recoilPitch, float recoilYaw) {
/* 763 */     GunType gunType = ((ItemGun)ModularWarfare.gunTypes.get(wepType)).type;
/* 764 */     if (gunType != null) {
/* 765 */       if (gunType.animationType == WeaponAnimationType.BASIC) {
/* 766 */         ClientRenderHooks.getAnimMachine((EntityLivingBase)player).triggerShoot((ModelGun)gunType.model, gunType, fireTickDelay);
/*     */       } else {
/* 768 */         float rand = (float)Math.random();
/* 769 */         ClientEventHandler.cemeraBobbing = lastBobbingParm * (0.3F + 0.4F * Math.abs(rand)) * ((GunEnhancedRenderConfig)gunType.enhancedModel.config).extra.bobbingFactor;
/* 770 */         lastBobbingParm = -lastBobbingParm;
/* 771 */         AnimationController controller = gunEnhancedRenderer.getController((EntityLivingBase)player, (GunEnhancedRenderConfig)gunType.enhancedModel.config);
/* 772 */         ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)player).triggerShoot(controller, (ModelEnhancedGun)gunType.enhancedModel, gunType, fireTickDelay);
/*     */       } 
/*     */ 
/*     */       
/* 776 */       RenderParameters.rate = Math.min(RenderParameters.rate + 0.07F, 1.0F);
/*     */       
/* 778 */       float recoilPitchGripFactor = 1.0F;
/* 779 */       float recoilYawGripFactor = 1.0F;
/*     */       
/* 781 */       float recoilPitchBarrelFactor = 1.0F;
/* 782 */       float recoilYawBarrelFactor = 1.0F;
/*     */       
/* 784 */       float recoilPitchStockFactor = 1.0F;
/* 785 */       float recoilYawStockFactor = 1.0F;
/*     */       
/* 787 */       if (GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Grip) != null) {
/* 788 */         ItemAttachment gripAttachment = (ItemAttachment)GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Grip).func_77973_b();
/* 789 */         recoilPitchGripFactor = gripAttachment.type.grip.recoilPitchFactor;
/* 790 */         recoilYawGripFactor = gripAttachment.type.grip.recoilYawFactor;
/*     */       } 
/*     */       
/* 793 */       if (GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Barrel) != null) {
/* 794 */         ItemAttachment barrelAttachment = (ItemAttachment)GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Barrel).func_77973_b();
/* 795 */         recoilPitchBarrelFactor = barrelAttachment.type.barrel.recoilPitchFactor;
/* 796 */         recoilYawBarrelFactor = barrelAttachment.type.barrel.recoilYawFactor;
/*     */       } 
/*     */       
/* 799 */       if (GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Stock) != null) {
/* 800 */         ItemAttachment stockAttachment = (ItemAttachment)GunType.getAttachment(player.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Stock).func_77973_b();
/* 801 */         recoilPitchStockFactor = stockAttachment.type.stock.recoilPitchFactor;
/* 802 */         recoilYawStockFactor = stockAttachment.type.stock.recoilYawFactor;
/*     */       } 
/*     */       
/* 805 */       boolean isCrawling = false;
/* 806 */       if (ModularWarfare.isLoadedModularMovements && 
/* 807 */         ClientLitener.clientPlayerState.isCrawling) {
/* 808 */         isCrawling = true;
/*     */       }
/*     */       
/* 811 */       float offsetYaw = 0.0F;
/* 812 */       float offsetPitch = 0.0F;
/* 813 */       if (!ClientRenderHooks.isAiming && !ClientRenderHooks.isAimingScope) {
/* 814 */         offsetPitch = gunType.recoilPitch;
/* 815 */         offsetPitch += gunType.randomRecoilPitch * 2.0F - gunType.randomRecoilPitch;
/* 816 */         offsetPitch *= recoilPitchGripFactor * recoilPitchBarrelFactor * recoilPitchStockFactor;
/*     */ 
/*     */         
/* 819 */         offsetYaw = gunType.recoilYaw;
/* 820 */         offsetYaw *= (new Random()).nextFloat() * gunType.randomRecoilYaw * 2.0F - gunType.randomRecoilYaw;
/* 821 */         offsetYaw *= recoilYawGripFactor * recoilYawBarrelFactor * recoilYawStockFactor;
/* 822 */         offsetYaw *= RenderParameters.rate * (isCrawling ? 0.2F : 1.0F);
/* 823 */         offsetYaw *= RenderParameters.phase ? 1.0F : -1.0F;
/*     */       } else {
/*     */         
/* 826 */         offsetPitch = gunType.recoilPitch;
/* 827 */         offsetPitch += gunType.randomRecoilPitch * 2.0F - gunType.randomRecoilPitch;
/* 828 */         offsetPitch *= recoilPitchGripFactor * recoilPitchBarrelFactor * recoilPitchStockFactor;
/* 829 */         offsetPitch *= gunType.recoilAimReducer;
/*     */         
/* 831 */         offsetYaw = gunType.recoilYaw;
/* 832 */         offsetYaw *= (new Random()).nextFloat() * gunType.randomRecoilYaw * 2.0F - gunType.randomRecoilYaw;
/* 833 */         offsetYaw *= recoilYawGripFactor * recoilYawBarrelFactor * recoilYawStockFactor;
/* 834 */         offsetYaw *= RenderParameters.rate * (isCrawling ? 0.2F : 1.0F);
/* 835 */         offsetYaw *= gunType.recoilAimReducer;
/* 836 */         offsetYaw *= RenderParameters.phase ? 1.0F : -1.0F;
/*     */       } 
/* 838 */       if (ModularWarfare.isLoadedModularMovements && 
/* 839 */         ClientLitener.clientPlayerState.isCrawling) {
/* 840 */         offsetPitch *= gunType.recoilCrawlPitchFactor;
/* 841 */         offsetYaw *= gunType.recoilCrawlYawFactor;
/*     */       } 
/*     */       
/* 844 */       RenderParameters.playerRecoilPitch += offsetPitch;
/* 845 */       if (Math.random() > 0.5D) {
/* 846 */         RenderParameters.playerRecoilYaw += offsetYaw;
/*     */       } else {
/* 848 */         RenderParameters.playerRecoilYaw -= offsetYaw;
/*     */       } 
/* 850 */       RenderParameters.phase = !RenderParameters.phase;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onReloadAnimation(EntityPlayer player, String wepType, int reloadTime, int reloadCount, int reloadType) {
/* 856 */     ClientTickHandler.playerReloadCooldown.put(player.func_110124_au(), Integer.valueOf(reloadTime));
/* 857 */     ItemGun gunType = (ItemGun)ModularWarfare.gunTypes.get(wepType);
/* 858 */     if (gunType != null) {
/* 859 */       if (gunType.type.animationType == WeaponAnimationType.BASIC) {
/* 860 */         ClientRenderHooks.getAnimMachine((EntityLivingBase)player).triggerReload(reloadTime, reloadCount, (ModelGun)gunType.type.model, ReloadType.getTypeFromInt(reloadType), player.func_70051_ag());
/*     */       } else {
/* 862 */         AnimationController controller = gunEnhancedRenderer.getController((EntityLivingBase)player, (GunEnhancedRenderConfig)gunType.type.enhancedModel.config);
/* 863 */         ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)player).triggerReload(controller, (EntityLivingBase)player, reloadTime, reloadCount, (ModelEnhancedGun)gunType.type.enhancedModel, ReloadType.getTypeFromInt(reloadType));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onShootFailedAnimation(EntityPlayer player, String wepType) {
/* 870 */     ItemGun gunType = (ItemGun)ModularWarfare.gunTypes.get(wepType);
/* 871 */     if (gunType != null && 
/* 872 */       gunType.type.animationType == WeaponAnimationType.ENHANCED) {
/* 873 */       AnimationController controller = gunEnhancedRenderer.getController((EntityLivingBase)player, (GunEnhancedRenderConfig)gunType.type.enhancedModel.config);
/* 874 */       ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)player).triggerShoot(controller, (ModelEnhancedGun)gunType.type.enhancedModel, gunType.type, 0, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onModeChangeAnimation(EntityPlayer player, String wepType) {
/* 881 */     ItemGun gunType = (ItemGun)ModularWarfare.gunTypes.get(wepType);
/* 882 */     if (gunType != null && 
/* 883 */       gunType.type.animationType == WeaponAnimationType.ENHANCED && 
/* 884 */       gunEnhancedRenderer.getController((EntityLivingBase)player, null) != null) {
/* 885 */       (gunEnhancedRenderer.getController((EntityLivingBase)player, null)).MODE_CHANGE = 0.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getClientWorld() {
/* 893 */     return (World)(FMLClientHandler.instance().getClient()).field_71441_e;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerEventHandlers() {
/* 898 */     super.registerEventHandlers();
/* 899 */     MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
/* 900 */     MinecraftForge.EVENT_BUS.register(new OBBPlayerManager());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlood(EntityLivingBase living, int amount, boolean onhit) {
/* 906 */     if (onhit) {
/* 907 */       addBlood(living, amount);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void playHitmarker(boolean headshot) {
/* 913 */     if (ModConfig.INSTANCE.hud.hitmarkers) {
/* 914 */       Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_194007_a(modSounds.get("hitmarker"), 1.0F, 4.0F));
/* 915 */       GunUI.addHitMarker(headshot);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBlood(EntityLivingBase living, int amount) {
/* 921 */     for (int k = 0; k < amount; k++) {
/* 922 */       float attenuator = 0.3F;
/* 923 */       double mX = (-MathHelper.func_76126_a(living.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(living.field_70125_A / 180.0F * 3.1415927F) * attenuator);
/* 924 */       double mZ = (MathHelper.func_76134_b(living.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(living.field_70125_A / 180.0F * 3.1415927F) * attenuator);
/* 925 */       double mY = (-MathHelper.func_76126_a(living.field_70125_A / 180.0F * 3.1415927F) * attenuator + 0.1F);
/* 926 */       attenuator = 0.02F;
/* 927 */       float var5 = living.func_70681_au().nextFloat() * 3.1415927F * 2.0F;
/* 928 */       attenuator *= living.func_70681_au().nextFloat();
/* 929 */       mX += Math.cos(var5) * attenuator;
/* 930 */       mY += ((living.func_70681_au().nextFloat() - living.func_70681_au().nextFloat()) * 0.1F);
/* 931 */       mZ += Math.sin(var5) * attenuator;
/* 932 */       EntityBloodFX entityBloodFX = new EntityBloodFX(living.func_130014_f_(), living.field_70165_t, living.field_70163_u + 0.5D + living.func_70681_au().nextDouble() * 0.7D, living.field_70161_v, living.field_70159_w * 2.0D + mX, living.field_70181_x + mY, living.field_70179_y * 2.0D + mZ, 0.0D);
/* 933 */       (Minecraft.func_71410_x()).field_71452_i.func_78873_a((Particle)entityBloodFX);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetSens() {
/* 939 */     ClientRenderHooks.isAimingScope = false;
/* 940 */     ClientRenderHooks.isAiming = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnExplosionParticle(World world, double x, double y, double z) {
/* 945 */     if (!world.field_72995_K) {
/* 946 */       super.spawnExplosionParticle(world, x, y, z);
/*     */       return;
/*     */     } 
/* 949 */     ParticleExplosion particleExplosion = new ParticleExplosion(world, x, y, z);
/* 950 */     (Minecraft.func_71410_x()).field_71452_i.func_78873_a((Particle)particleExplosion);
/*     */   }
/*     */   
/*     */   public void spawnRocketParticle(World world, double x, double y, double z) {
/* 954 */     if (!world.field_72995_K) {
/* 955 */       super.spawnRocketParticle(world, x, y, z);
/*     */       return;
/*     */     } 
/* 958 */     ParticleRocket particleRocket = new ParticleRocket(world, x, y, z);
/* 959 */     (Minecraft.func_71410_x()).field_71452_i.func_78873_a((Particle)particleRocket);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playFlashSound(EntityPlayer entityPlayer) {
/* 964 */     Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)new PositionedSoundRecord(ModSounds.FLASHED, SoundCategory.PLAYERS, FlashSystem.flashValue / 1000.0F, 1.0F, (float)entityPlayer.field_70165_t, (float)entityPlayer.field_70163_u, (float)entityPlayer.field_70161_v));
/* 965 */     Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)new PositionedSoundRecord(ModSounds.FLASHED, SoundCategory.PLAYERS, 5.0F, 0.2F, (float)entityPlayer.field_70165_t, (float)entityPlayer.field_70163_u, (float)entityPlayer.field_70161_v));
/* 966 */     Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)new PositionedSoundRecord(ModSounds.FLASHED, SoundCategory.PLAYERS, 5.0F, 0.1F, (float)entityPlayer.field_70165_t, (float)entityPlayer.field_70163_u, (float)entityPlayer.field_70161_v));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\ClientProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */