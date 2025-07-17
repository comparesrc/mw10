/*     */ package mchhui.modularmovements.tactical.client;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import mchhui.modularmovements.ModularMovements;
/*     */ import mchhui.modularmovements.tactical.PlayerState;
/*     */ import mchhui.modularmovements.tactical.network.TacticalHandler;
/*     */ import mchhui.modularmovements.tactical.server.ServerListener;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.entity.EntityPlayerSPHelper;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MouseHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.EntityViewRenderEvent;
/*     */ import net.minecraftforge.client.event.InputUpdateEvent;
/*     */ import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
/*     */ import net.minecraftforge.client.event.RenderHandEvent;
/*     */ import net.minecraftforge.client.event.RenderPlayerEvent;
/*     */ import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
/*     */ import net.minecraftforge.fml.client.registry.ClientRegistry;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.event.FMLInitializationEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.InputEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import net.minecraftforge.fml.relauncher.ReflectionHelper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class ClientLitener
/*     */ {
/*  65 */   public static KeyBinding sit = new KeyBinding("Sit/Sliding", 46, "ModularMovements");
/*  66 */   public static KeyBinding crawling = new KeyBinding("Crawling", 44, "ModularMovements");
/*  67 */   public static KeyBinding leftProbe = new KeyBinding("Left Probe", 16, "ModularMovements");
/*  68 */   public static KeyBinding rightProbe = new KeyBinding("Right Probe", 18, "ModularMovements");
/*     */   
/*  70 */   public static PlayerState clientPlayerState = (PlayerState)new PlayerState.ClientPlayerState();
/*  71 */   public static Map<Integer, PlayerState> ohterPlayerStateMap = new HashMap<>();
/*     */   
/*  73 */   public static Vec3d clientPlayerSitMoveVec3d = new Vec3d(0.0D, 0.0D, 0.0D);
/*  74 */   public static double clientPlayerSitMoveAmplifierCharging = 0.0D;
/*  75 */   public static double clientPlayerSitMoveAmplifierCharged = 0.0D;
/*  76 */   public static double clientPlayerSitMoveAmplifier = 0.0D;
/*     */   
/*     */   public static boolean enableForceGravity = false;
/*  79 */   public static double forceGravity = 1.0D;
/*  80 */   public static double clientPlayerSitMoveAmplifierUser = 0.8D;
/*  81 */   public static double clientPlayerSitMoveLess = 0.1D;
/*  82 */   public static double clientPlayerSitMoveMax = 1.0D;
/*     */   
/*     */   private static World world;
/*     */   private static boolean sitKeyLock = false;
/*     */   private static boolean crawlingKeyLock = false;
/*     */   private static boolean probeKeyLock = false;
/*     */   private static boolean isSneaking = false;
/*     */   private static boolean wannaSliding = false;
/*  90 */   private static long lastSyncTime = 0L;
/*  91 */   public static int crawlingMousePosXMove = 0;
/*     */   
/*  93 */   public static double cameraOffsetY = 0.0D;
/*  94 */   public static float cameraProbeOffset = 0.0F;
/*     */   
/*     */   private static Field speedInAir;
/*     */   private static AxisAlignedBB lastAABB;
/*     */   private static AxisAlignedBB lastModAABB;
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTickClient(TickEvent.ClientTickEvent event) {
/* 102 */     if (event.phase == TickEvent.Phase.START) {
/*     */       return;
/*     */     }
/* 105 */     if ((Minecraft.func_71410_x()).field_71441_e != world) {
/* 106 */       clientPlayerSitMoveAmplifier = 0.0D;
/* 107 */       cameraOffsetY = 0.0D;
/* 108 */       cameraProbeOffset = 0.0F;
/* 109 */       clientPlayerSitMoveAmplifierCharging = 0.0D;
/* 110 */       clientPlayerSitMoveAmplifierCharged = 0.0D;
/* 111 */       clientPlayerState.reset();
/* 112 */       if ((Minecraft.func_71410_x()).field_71439_g != null) {
/* 113 */         (Minecraft.func_71410_x()).field_71439_g.func_184819_a((Minecraft.func_71410_x()).field_71474_y.field_186715_A);
/*     */       }
/*     */     } 
/* 116 */     if ((Minecraft.func_71410_x()).field_71439_g != null) {
/* 117 */       if ((Minecraft.func_71410_x()).field_71439_g.field_70165_t != (Minecraft.func_71410_x()).field_71439_g.field_70142_S) {
/* 118 */         crawlingMousePosXMove = 0;
/*     */       }
/* 120 */       if ((Minecraft.func_71410_x()).field_71439_g.field_70161_v != (Minecraft.func_71410_x()).field_71439_g.field_70136_U) {
/* 121 */         crawlingMousePosXMove = 0;
/*     */       }
/* 123 */       if ((Minecraft.func_71410_x()).field_71439_g.func_70051_ag() && !clientPlayerState.isSitting) {
/* 124 */         clientPlayerSitMoveAmplifierCharging += 0.05D;
/* 125 */         clientPlayerSitMoveAmplifierCharging = Math.min(clientPlayerSitMoveAmplifierCharging, 1.0D);
/*     */       } else {
/* 127 */         clientPlayerSitMoveAmplifierCharging = 0.0D;
/*     */       } 
/*     */     } 
/* 130 */     world = (World)(Minecraft.func_71410_x()).field_71441_e;
/*     */   }
/*     */   
/*     */   public void onFMLInit(FMLInitializationEvent event) {
/* 134 */     ClientRegistry.registerKeyBinding(sit);
/* 135 */     ClientRegistry.registerKeyBinding(crawling);
/* 136 */     ClientRegistry.registerKeyBinding(leftProbe);
/* 137 */     ClientRegistry.registerKeyBinding(rightProbe);
/* 138 */     speedInAir = ReflectionHelper.findField(EntityPlayer.class, "speedInAir", "field_71102_ce");
/*     */   }
/*     */   
/*     */   public void onFMLInitPost(FMLPostInitializationEvent event) {
/* 142 */     Field field = ReflectionHelper.findField(RenderManager.class, "skinMap", "field_178636_l");
/*     */     
/*     */     try {
/* 145 */       Map<String, RenderPlayer> skinMap = (Map<String, RenderPlayer>)field.get(Minecraft.func_71410_x().func_175598_ae());
/* 146 */       skinMap.clear();
/* 147 */       skinMap.put("default", new FakeRenderPlayer(Minecraft.func_71410_x().func_175598_ae()));
/* 148 */       skinMap.put("slim", new FakeRenderPlayer(Minecraft.func_71410_x().func_175598_ae(), true));
/* 149 */     } catch (IllegalArgumentException|IllegalAccessException e) {
/*     */       
/* 151 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 154 */     field = ReflectionHelper.findField(Minecraft.class, "tutorial", "field_193035_aW");
/*     */     try {
/* 156 */       field.set(Minecraft.func_71410_x(), new FakeTutorial(Minecraft.func_71410_x()));
/* 157 */     } catch (IllegalArgumentException|IllegalAccessException e) {
/*     */       
/* 159 */       e.printStackTrace();
/*     */     } 
/* 161 */     Minecraft.func_71410_x().func_193032_ao().func_193302_c();
/*     */   }
/*     */   
/*     */   private void onSit() {
/* 165 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/* 166 */     if (entityPlayerSP == null) {
/*     */       return;
/*     */     }
/* 169 */     if ((Minecraft.func_71410_x()).field_71462_r != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 174 */     if (clientPlayerState.canSit() && ((
/* 175 */       !sitKeyLock && isButtonDown(sit.func_151463_i())) || wannaSliding)) {
/* 176 */       if (!clientPlayerState.isSitting || wannaSliding) {
/* 177 */         if ((Minecraft.func_71410_x()).field_71439_g.field_70122_E) {
/* 178 */           sitKeyLock = true;
/* 179 */           wannaSliding = false;
/*     */           
/* 181 */           float w1 = ((EntityPlayer)entityPlayerSP).field_70130_N / 2.0F;
/* 182 */           if (clientPlayerState.isCrawling) {
/* 183 */             w1 *= 1.2F;
/*     */           }
/* 185 */           AxisAlignedBB axisalignedbb = new AxisAlignedBB(((EntityPlayer)entityPlayerSP).field_70165_t - w1, ((EntityPlayer)entityPlayerSP).field_70163_u + 0.1D, ((EntityPlayer)entityPlayerSP).field_70161_v - w1, ((EntityPlayer)entityPlayerSP).field_70165_t + w1, ((EntityPlayer)entityPlayerSP).field_70163_u + 1.2D, ((EntityPlayer)entityPlayerSP).field_70161_v + w1);
/*     */ 
/*     */           
/* 188 */           if (!((EntityPlayer)entityPlayerSP).field_70170_p.func_184143_b(axisalignedbb)) {
/* 189 */             if (!clientPlayerState.isSitting) {
/* 190 */               clientPlayerState.enableSit();
/*     */             }
/*     */             
/* 193 */             if ((Minecraft.func_71410_x()).field_71439_g.func_70051_ag() && ModularMovements.CONFIG.slide.enable) {
/* 194 */               if (wannaSliding) {
/* 195 */                 clientPlayerSitMoveAmplifierCharging = 1.0D;
/*     */               }
/* 197 */               clientPlayerSitMoveAmplifierCharged = clientPlayerSitMoveAmplifierCharging;
/* 198 */               clientPlayerSitMoveAmplifier = ModularMovements.CONFIG.slide.maxForce;
/*     */               
/* 200 */               clientPlayerSitMoveVec3d = (new Vec3d(((EntityPlayer)entityPlayerSP).field_70165_t - ((EntityPlayer)entityPlayerSP).field_70142_S, 0.0D, ((EntityPlayer)entityPlayerSP).field_70161_v - ((EntityPlayer)entityPlayerSP).field_70136_U)).func_72432_b();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 205 */         sitKeyLock = true;
/* 206 */         double d0 = 0.3D;
/* 207 */         if (!((EntityPlayer)entityPlayerSP).field_70170_p.func_184143_b(new AxisAlignedBB(((EntityPlayer)entityPlayerSP).field_70165_t - d0, ((EntityPlayer)entityPlayerSP).field_70163_u, ((EntityPlayer)entityPlayerSP).field_70161_v - d0, ((EntityPlayer)entityPlayerSP).field_70165_t + d0, ((EntityPlayer)entityPlayerSP).field_70163_u + 1.8D, ((EntityPlayer)entityPlayerSP).field_70161_v + d0))) {
/*     */ 
/*     */           
/* 210 */           clientPlayerSitMoveAmplifier = 0.0D;
/* 211 */           clientPlayerState.disableSit();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onKeyInput(InputEvent.KeyInputEvent event) {
/* 220 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*     */     
/* 222 */     onSit();
/*     */ 
/*     */     
/* 225 */     if (clientPlayerState.canCrawl() && 
/* 226 */       !crawlingKeyLock && isButtonDown(crawling.func_151463_i())) {
/* 227 */       crawlingKeyLock = true;
/* 228 */       if (!clientPlayerState.isCrawling) {
/* 229 */         if ((Minecraft.func_71410_x()).field_71439_g.field_70122_E) {
/* 230 */           clientPlayerState.enableCrawling();
/* 231 */           if ((Minecraft.func_71410_x()).field_71439_g.func_70051_ag()) {
/* 232 */             Vec3d vec3d = (new Vec3d(((EntityPlayer)entityPlayerSP).field_70165_t - ((EntityPlayer)entityPlayerSP).field_70142_S, 0.0D, ((EntityPlayer)entityPlayerSP).field_70161_v - ((EntityPlayer)entityPlayerSP).field_70136_U)).func_72432_b();
/* 233 */             (Minecraft.func_71410_x()).field_71439_g.field_70159_w = vec3d.field_72450_a * clientPlayerSitMoveAmplifierCharging;
/* 234 */             (Minecraft.func_71410_x()).field_71439_g.field_70181_x = 0.35D * clientPlayerSitMoveAmplifierCharging;
/* 235 */             (Minecraft.func_71410_x()).field_71439_g.field_70179_y = vec3d.field_72449_c * clientPlayerSitMoveAmplifierCharging;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 239 */         double d0 = 0.3D;
/* 240 */         if (!((EntityPlayer)entityPlayerSP).field_70170_p.func_184143_b(new AxisAlignedBB(((EntityPlayer)entityPlayerSP).field_70165_t - d0, ((EntityPlayer)entityPlayerSP).field_70163_u, ((EntityPlayer)entityPlayerSP).field_70161_v - d0, ((EntityPlayer)entityPlayerSP).field_70165_t + d0, ((EntityPlayer)entityPlayerSP).field_70163_u + 1.8D, ((EntityPlayer)entityPlayerSP).field_70161_v + d0)))
/*     */         {
/*     */           
/* 243 */           clientPlayerState.disableCrawling();
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 249 */     if (ModularMovements.CONFIG.lean.withGunsOnly) {
/* 250 */       if (Loader.isModLoaded("modularwarfare")) {
/* 251 */         if ((Minecraft.func_71410_x()).field_71439_g.func_184614_ca() != null) {
/* 252 */           if (!((Minecraft.func_71410_x()).field_71439_g.func_184614_ca().func_77973_b() instanceof com.modularwarfare.common.guns.ItemGun)) {
/*     */             return;
/*     */           }
/*     */         } else {
/*     */           return;
/*     */         } 
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static Vec3d onGetPositionEyes(EntityPlayer player, float partialTicks, Vec3d vec3d) {
/* 265 */     PlayerState state = null;
/* 266 */     float offest = 0.0F;
/* 267 */     if (player == (Minecraft.func_71410_x()).field_71439_g) {
/* 268 */       state = clientPlayerState;
/* 269 */       offest = cameraProbeOffset;
/*     */     } else {
/* 271 */       if (!ohterPlayerStateMap.containsKey(Integer.valueOf(player.func_145782_y()))) {
/* 272 */         return vec3d;
/*     */       }
/* 274 */       state = ohterPlayerStateMap.get(Integer.valueOf(player.func_145782_y()));
/* 275 */       offest = state.probeOffset;
/*     */     } 
/*     */     
/* 278 */     if (offest != 0.0F) {
/* 279 */       return vec3d.func_178787_e((new Vec3d(offest * -0.6D, 0.0D, 0.0D))
/* 280 */           .func_178785_b((float)(-(Minecraft.func_71410_x()).field_71439_g.field_70177_z * Math.PI / 180.0D)));
/*     */     }
/* 282 */     return vec3d;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean applyRotations(RenderLivingBase renderer, EntityLivingBase entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
/* 287 */     if (entityLiving == (Minecraft.func_71410_x()).field_71439_g && entityLiving.func_70089_S()) {
/* 288 */       if (clientPlayerState.isSitting) {
/* 289 */         GlStateManager.func_179137_b(0.0D, -0.5D, 0.0D);
/*     */       }
/* 291 */       if (clientPlayerState.isCrawling) {
/* 292 */         GlStateManager.func_179114_b(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
/* 293 */         GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
/* 294 */         GlStateManager.func_179137_b(0.0D, -1.3D, 0.1D);
/* 295 */         GlStateManager.func_179137_b(cameraProbeOffset * 0.4D, 0.0D, 0.0D);
/* 296 */         return true;
/*     */       } 
/* 298 */       if (cameraProbeOffset != 0.0F) {
/* 299 */         GlStateManager.func_179114_b(180.0F - entityLiving.field_70759_as, 0.0F, 1.0F, 0.0F);
/* 300 */         GlStateManager.func_179137_b(cameraProbeOffset * 0.1D, 0.0D, 0.0D);
/* 301 */         GlStateManager.func_179114_b(180.0F - entityLiving.field_70759_as, 0.0F, -1.0F, 0.0F);
/* 302 */         GlStateManager.func_179114_b(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
/* 303 */         GlStateManager.func_179114_b(cameraProbeOffset * -20.0F, 0.0F, 0.0F, 1.0F);
/* 304 */         return true;
/*     */       } 
/*     */     } 
/* 307 */     if (entityLiving != (Minecraft.func_71410_x()).field_71439_g && entityLiving instanceof EntityPlayer && entityLiving
/* 308 */       .func_70089_S() && 
/* 309 */       ohterPlayerStateMap.containsKey(Integer.valueOf(entityLiving.func_145782_y()))) {
/* 310 */       PlayerState state = ohterPlayerStateMap.get(Integer.valueOf(entityLiving.func_145782_y()));
/* 311 */       if (state.isSitting) {
/* 312 */         GlStateManager.func_179137_b(0.0D, -0.5D, 0.0D);
/*     */       }
/* 314 */       if (state.isCrawling) {
/* 315 */         GlStateManager.func_179114_b(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
/* 316 */         GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
/* 317 */         GlStateManager.func_179137_b(0.0D, -1.3D, 0.1D);
/* 318 */         GlStateManager.func_179137_b(state.probeOffset * 0.4D, 0.0D, 0.0D);
/* 319 */         return true;
/*     */       } 
/* 321 */       state.updateOffset();
/* 322 */       if (state.probeOffset != 0.0F) {
/* 323 */         GlStateManager.func_179114_b(180.0F - entityLiving.field_70759_as, 0.0F, 1.0F, 0.0F);
/* 324 */         GlStateManager.func_179137_b(state.probeOffset * 0.1D, 0.0D, 0.0D);
/* 325 */         GlStateManager.func_179114_b(180.0F - entityLiving.field_70759_as, 0.0F, -1.0F, 0.0F);
/* 326 */         GlStateManager.func_179114_b(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
/* 327 */         GlStateManager.func_179114_b(state.probeOffset * -20.0F, 0.0F, 0.0F, 1.0F);
/* 328 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 332 */     return false;
/*     */   }
/*     */   
/*     */   public static void onMouseMove(MouseHelper mouseHelper) {
/* 336 */     if (clientPlayerState.probe != 0 && ModularMovements.CONFIG.lean.mouseCorrection) {
/* 337 */       Vec3d vec = Vec3d.field_186680_a.func_72441_c(mouseHelper.field_74377_a, 0.0D, mouseHelper.field_74375_b);
/* 338 */       vec = vec.func_178785_b((float)((cameraProbeOffset * 10.0F) * Math.PI / 180.0D));
/* 339 */       mouseHelper.field_74377_a = Math.round((float)vec.field_72450_a);
/* 340 */       mouseHelper.field_74375_b = Math.round((float)vec.field_72449_c);
/*     */     } 
/* 342 */     if ((clientPlayerState.isCrawling & ModularMovements.CONFIG.crawl.blockView) != 0) {
/* 343 */       float angle = (float)(ModularMovements.CONFIG.crawl.blockAngle * Math.PI);
/* 344 */       if (Math.abs(crawlingMousePosXMove + mouseHelper.field_74377_a) > angle) {
/* 345 */         if (mouseHelper.field_74377_a > 0) {
/* 346 */           mouseHelper.field_74377_a = (int)(angle - crawlingMousePosXMove);
/*     */         } else {
/* 348 */           mouseHelper.field_74377_a = (int)(-angle - crawlingMousePosXMove);
/*     */         } 
/*     */       }
/* 351 */       crawlingMousePosXMove += mouseHelper.field_74377_a;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setRotationAngles(ModelPlayer model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 357 */     setRotationAngles((ModelBiped)model, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/*     */     
/* 359 */     ModelPlayer.func_178685_a(model.field_178722_k, model.field_178733_c);
/* 360 */     ModelPlayer.func_178685_a(model.field_178721_j, model.field_178731_d);
/* 361 */     ModelPlayer.func_178685_a(model.field_178724_i, model.field_178734_a);
/* 362 */     ModelPlayer.func_178685_a(model.field_178723_h, model.field_178732_b);
/* 363 */     ModelPlayer.func_178685_a(model.field_78115_e, model.field_178730_v);
/* 364 */     ModelPlayer.func_178685_a(model.field_78116_c, model.field_178720_f);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setRotationAngles(ModelBiped model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 369 */     if (entityIn instanceof EntityPlayer && entityIn.func_70089_S()) {
/* 370 */       PlayerState state = null;
/* 371 */       float offest = 0.0F;
/* 372 */       if (entityIn == (Minecraft.func_71410_x()).field_71439_g) {
/* 373 */         state = clientPlayerState;
/* 374 */         offest = cameraProbeOffset;
/*     */       } else {
/* 376 */         if (!ohterPlayerStateMap.containsKey(Integer.valueOf(entityIn.func_145782_y()))) {
/*     */           return;
/*     */         }
/* 379 */         state = ohterPlayerStateMap.get(Integer.valueOf(entityIn.func_145782_y()));
/* 380 */         offest = state.probeOffset;
/*     */       } 
/*     */       
/* 383 */       if (state.isSitting) {
/* 384 */         model.field_178721_j.field_78795_f = -1.4137167F;
/* 385 */         model.field_178721_j.field_78796_g = 0.31415927F;
/* 386 */         model.field_178721_j.field_78808_h = 0.07853982F;
/* 387 */         model.field_178722_k.field_78795_f = -1.4137167F;
/* 388 */         model.field_178722_k.field_78796_g = -0.31415927F;
/* 389 */         model.field_178722_k.field_78808_h = -0.07853982F;
/*     */       } 
/*     */       
/* 392 */       if (state.isCrawling) {
/* 393 */         model.field_78116_c.field_78795_f = (float)(model.field_78116_c.field_78795_f - 1.2211111111111113D);
/* 394 */         model.field_178723_h.field_78795_f = (float)(model.field_178723_h.field_78795_f * 0.2D);
/* 395 */         model.field_178724_i.field_78795_f = (float)(model.field_178724_i.field_78795_f * 0.2D);
/* 396 */         model.field_178723_h.field_78795_f = (float)(model.field_178723_h.field_78795_f + 3.14D);
/* 397 */         model.field_178724_i.field_78795_f = (float)(model.field_178724_i.field_78795_f + 3.14D);
/* 398 */         if (entityIn instanceof AbstractClientPlayer) {
/* 399 */           ItemStack itemstack = ((AbstractClientPlayer)entityIn).func_184614_ca();
/* 400 */           if (itemstack != ItemStack.field_190927_a && !itemstack.func_190926_b() && 
/* 401 */             ModularMovements.mwfEnable && 
/* 402 */             itemstack.func_77973_b() instanceof com.modularwarfare.common.type.BaseItem) {
/* 403 */             model.field_178724_i.field_78796_g = 0.0F;
/* 404 */             model.field_178723_h.field_78796_g = 0.0F;
/* 405 */             model.field_178724_i.field_78795_f = 3.14F;
/* 406 */             model.field_178723_h.field_78795_f = 3.14F;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 411 */         model.field_178721_j.field_78795_f = (float)(model.field_178721_j.field_78795_f * 0.2D);
/* 412 */         model.field_178722_k.field_78795_f = (float)(model.field_178722_k.field_78795_f * 0.2D);
/*     */       } 
/* 414 */       if (offest >= 0.0F) {
/* 415 */         model.field_178721_j.field_78808_h = (float)(model.field_178721_j.field_78808_h + (offest * 20.0F) * 3.14D / 180.0D);
/*     */       } else {
/* 417 */         model.field_178722_k.field_78808_h = (float)(model.field_178722_k.field_78808_h + (offest * 20.0F) * 3.14D / 180.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = false)
/*     */   public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
/* 424 */     isSneaking = event.getEntityPlayer().func_70093_af();
/* 425 */     if (!clientPlayerState.isCrawling && !clientPlayerState.isSitting) {
/*     */       return;
/*     */     }
/* 428 */     if (event.getEntityPlayer() instanceof EntityPlayerSP) {
/* 429 */       ((EntityPlayerSP)event.getEntityPlayer()).field_71158_b.field_78899_d = false;
/*     */     } else {
/* 431 */       event.getEntityPlayer().func_70095_a(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
/* 437 */     if (event.getEntityPlayer() instanceof EntityPlayerSP) {
/* 438 */       ((EntityPlayerSP)event.getEntityPlayer()).field_71158_b.field_78899_d = isSneaking;
/*     */     } else {
/* 440 */       event.getEntityPlayer().func_70095_a(isSneaking);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onCameraUpdate(EntityViewRenderEvent.CameraSetup event) {
/* 446 */     float pitch = event.getPitch();
/* 447 */     float yaw = event.getYaw();
/* 448 */     float roll = event.getRoll();
/* 449 */     double playerPosX = (Minecraft.func_71410_x()).field_71439_g.field_70165_t + ((Minecraft.func_71410_x()).field_71439_g.field_70165_t - (Minecraft.func_71410_x()).field_71439_g.field_70142_S) * event.getRenderPartialTicks();
/* 450 */     double playerPosY = (Minecraft.func_71410_x()).field_71439_g.field_70163_u + ((Minecraft.func_71410_x()).field_71439_g.field_70163_u - (Minecraft.func_71410_x()).field_71439_g.field_70137_T) * event.getRenderPartialTicks();
/* 451 */     double playerPosZ = (Minecraft.func_71410_x()).field_71439_g.field_70161_v + ((Minecraft.func_71410_x()).field_71439_g.field_70161_v - (Minecraft.func_71410_x()).field_71439_g.field_70136_U) * event.getRenderPartialTicks();
/*     */     
/* 453 */     if (clientPlayerState.probe != 0) {
/* 454 */       float f = 0.22F;
/* 455 */       float f1 = 0.2F;
/* 456 */       float f2 = 0.15F;
/* 457 */       Vec3d vec3d = null;
/* 458 */       if (clientPlayerState.probe == -1)
/*     */       {
/* 460 */         vec3d = (new Vec3d(0.6D, 0.0D, 0.0D)).func_178785_b((float)(-(yaw - 180.0F) * Math.PI / 180.0D));
/*     */       }
/* 462 */       if (clientPlayerState.probe == 1)
/*     */       {
/* 464 */         vec3d = (new Vec3d(-0.6D, 0.0D, 0.0D)).func_178785_b((float)(-(yaw - 180.0F) * Math.PI / 180.0D));
/*     */       }
/* 466 */       AxisAlignedBB axisalignedbb = (Minecraft.func_71410_x()).field_71439_g.func_174813_aQ();
/* 467 */       int a = (int)(Math.sqrt(((Minecraft.func_71410_x()).field_71439_g.field_70165_t - (Minecraft.func_71410_x()).field_71439_g.field_70142_S) * ((Minecraft.func_71410_x()).field_71439_g.field_70165_t - (Minecraft.func_71410_x()).field_71439_g.field_70142_S) + ((Minecraft.func_71410_x()).field_71439_g.field_70161_v - (Minecraft.func_71410_x()).field_71439_g.field_70136_U) * ((Minecraft.func_71410_x()).field_71439_g.field_70161_v - (Minecraft.func_71410_x()).field_71439_g.field_70136_U)) / 0.10000000149011612D);
/* 468 */       int testCount = 10 + a;
/* 469 */       for (int i = 0; i <= testCount; i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 475 */         axisalignedbb = new AxisAlignedBB(playerPosX + vec3d.field_72450_a * (i / testCount) - f, playerPosY + 0.6000000238418579D, playerPosZ + vec3d.field_72449_c * (i / testCount) - f, playerPosX + vec3d.field_72450_a * (i / testCount) + f, playerPosY + (Minecraft.func_71410_x()).field_71439_g.func_70047_e() + f1, playerPosZ + vec3d.field_72449_c * (i / testCount) + f);
/*     */         
/* 477 */         if ((Minecraft.func_71410_x()).field_71439_g.field_70170_p.func_184143_b(axisalignedbb)) {
/* 478 */           clientPlayerState.resetProbe();
/*     */         }
/*     */       } 
/*     */     } 
/* 482 */     if (cameraProbeOffset != 0.0F) {
/* 483 */       float f = 0.22F;
/* 484 */       float f1 = 0.2F;
/* 485 */       float f2 = 0.1F;
/* 486 */       Vec3d vec3d = null;
/* 487 */       vec3d = (new Vec3d(-0.6D, 0.0D, 0.0D)).func_178785_b((float)(-(yaw - 180.0F) * Math.PI / 180.0D));
/* 488 */       int testCount = 10;
/* 489 */       for (int i = 0; i <= testCount; i++) {
/* 490 */         AxisAlignedBB axisalignedbb = (Minecraft.func_71410_x()).field_71439_g.func_174813_aQ();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 495 */         axisalignedbb = new AxisAlignedBB(playerPosX + vec3d.field_72450_a * cameraProbeOffset * (i / testCount) - f, playerPosY + (Minecraft.func_71410_x()).field_71439_g.func_70047_e() - f1, playerPosZ + vec3d.field_72449_c * cameraProbeOffset * (i / testCount) - f, playerPosX + vec3d.field_72450_a * cameraProbeOffset * (i / testCount) + f, playerPosY + (Minecraft.func_71410_x()).field_71439_g.func_70047_e() + f1, playerPosZ + vec3d.field_72449_c * cameraProbeOffset * (i / testCount) + f);
/*     */         
/* 497 */         axisalignedbb = axisalignedbb.func_72314_b((f2 - f), 0.0D, (f2 - f));
/* 498 */         if ((Minecraft.func_71410_x()).field_71439_g.field_70170_p.func_184143_b(axisalignedbb)) {
/* 499 */           clientPlayerState.resetProbe();
/* 500 */           cameraProbeOffset = 0.0F;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 505 */     if (clientPlayerSitMoveAmplifier > 0.0D && 
/* 506 */       Minecraft.func_71410_x().func_175606_aa() instanceof EntityPlayer) {
/* 507 */       float partialTicks = (float)event.getRenderPartialTicks();
/* 508 */       EntityPlayer entityplayer = (EntityPlayer)Minecraft.func_71410_x().func_175606_aa();
/* 509 */       float f = entityplayer.field_70140_Q - entityplayer.field_70141_P;
/* 510 */       float f1 = -(entityplayer.field_70140_Q + f * partialTicks);
/* 511 */       float f2 = entityplayer.field_71107_bF + (entityplayer.field_71109_bG - entityplayer.field_71107_bF) * partialTicks;
/*     */       
/* 513 */       float f3 = entityplayer.field_70727_aS + (entityplayer.field_70726_aT - entityplayer.field_70727_aS) * partialTicks;
/*     */       
/* 515 */       GlStateManager.func_179114_b(f3, -1.0F, 0.0F, 0.0F);
/* 516 */       GlStateManager.func_179114_b(Math.abs(MathHelper.func_76134_b(f1 * 3.1415927F - 0.2F) * f2) * 5.0F, -1.0F, 0.0F, 0.0F);
/*     */       
/* 518 */       GlStateManager.func_179114_b(MathHelper.func_76126_a(f1 * 3.1415927F) * f2 * 3.0F, 0.0F, 0.0F, -1.0F);
/* 519 */       GlStateManager.func_179109_b(-MathHelper.func_76126_a(f1 * 3.1415927F) * f2 * 0.5F, 
/* 520 */           Math.abs(MathHelper.func_76134_b(f1 * 3.1415927F) * f2), 0.0F);
/*     */     } 
/*     */     
/* 523 */     event.setPitch(0.0F);
/* 524 */     event.setYaw(0.0F);
/* 525 */     event.setRoll(0.0F);
/* 526 */     GlStateManager.func_179114_b(10.0F * cameraProbeOffset + roll, 0.0F, 0.0F, 1.0F);
/* 527 */     GlStateManager.func_179137_b(-0.6D * cameraProbeOffset, 0.0D, 0.0D);
/* 528 */     GlStateManager.func_179114_b(pitch, 1.0F, 0.0F, 0.0F);
/* 529 */     GlStateManager.func_179137_b(0.0D, -cameraOffsetY, 0.0D);
/* 530 */     GlStateManager.func_179114_b(yaw, 0.0F, 1.0F, 0.0F);
/*     */     
/* 532 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/* 533 */     if (clientPlayerState.isSitting) {
/* 534 */       if (((EntityPlayer)entityPlayerSP).eyeHeight != 1.1F) {
/* 535 */         cameraOffsetY = (((EntityPlayer)entityPlayerSP).eyeHeight - 1.1F);
/* 536 */         ((EntityPlayer)entityPlayerSP).eyeHeight = 1.1F;
/*     */       } 
/* 538 */     } else if (clientPlayerState.isCrawling) {
/*     */       
/* 540 */       if (((EntityPlayer)entityPlayerSP).eyeHeight != 0.7F) {
/* 541 */         cameraOffsetY = (((EntityPlayer)entityPlayerSP).eyeHeight - 0.7F);
/* 542 */         ((EntityPlayer)entityPlayerSP).eyeHeight = 0.7F;
/*     */       } 
/* 544 */     } else if (((EntityPlayer)entityPlayerSP).eyeHeight == 0.7F) {
/* 545 */       cameraOffsetY = (((EntityPlayer)entityPlayerSP).eyeHeight - entityPlayerSP.getDefaultEyeHeight());
/* 546 */       ((EntityPlayer)entityPlayerSP).eyeHeight = entityPlayerSP.getDefaultEyeHeight();
/* 547 */     } else if (((EntityPlayer)entityPlayerSP).eyeHeight == 1.1F) {
/* 548 */       cameraOffsetY = (((EntityPlayer)entityPlayerSP).eyeHeight - entityPlayerSP.getDefaultEyeHeight());
/* 549 */       ((EntityPlayer)entityPlayerSP).eyeHeight = entityPlayerSP.getDefaultEyeHeight();
/*     */     } 
/*     */     
/* 552 */     double amplifer = (Minecraft.func_71386_F() - lastSyncTime) * 0.06D;
/* 553 */     lastSyncTime = Minecraft.func_71386_F();
/* 554 */     if (clientPlayerState.probe == -1) {
/* 555 */       if (cameraProbeOffset > -1.0F) {
/* 556 */         cameraProbeOffset = (float)(cameraProbeOffset - 0.1D * amplifer);
/*     */       }
/* 558 */       if (cameraProbeOffset < -1.0F) {
/* 559 */         cameraProbeOffset = -1.0F;
/*     */       }
/*     */     } 
/* 562 */     if (clientPlayerState.probe == 1) {
/* 563 */       if (cameraProbeOffset < 1.0F) {
/* 564 */         cameraProbeOffset = (float)(cameraProbeOffset + 0.1D * amplifer);
/*     */       }
/* 566 */       if (cameraProbeOffset > 1.0F) {
/* 567 */         cameraProbeOffset = 1.0F;
/*     */       }
/*     */     } 
/*     */     
/* 571 */     if (clientPlayerState.probe == 0) {
/* 572 */       if (Math.abs(cameraProbeOffset) <= 0.1D * amplifer) {
/* 573 */         cameraProbeOffset = 0.0F;
/*     */       }
/* 575 */       if (cameraProbeOffset < 0.0F) {
/* 576 */         cameraProbeOffset = (float)(cameraProbeOffset + 0.1D * amplifer);
/*     */       }
/* 578 */       if (cameraProbeOffset > 0.0F) {
/* 579 */         cameraProbeOffset = (float)(cameraProbeOffset - 0.1D * amplifer);
/*     */       }
/*     */     } 
/* 582 */     if (Math.abs(cameraOffsetY) <= 0.1D * amplifer) {
/* 583 */       cameraOffsetY = 0.0D;
/*     */     }
/* 585 */     if (cameraOffsetY < 0.0D) {
/* 586 */       cameraOffsetY += 0.1D * amplifer;
/*     */     }
/* 588 */     if (cameraOffsetY > 0.0D) {
/* 589 */       cameraOffsetY -= 0.1D * amplifer;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onInputUpdate(InputUpdateEvent event) {
/* 596 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*     */     
/* 598 */     if (clientPlayerState.isSitting) {
/* 599 */       (event.getMovementInput()).field_192832_b = (float)((event.getMovementInput()).field_192832_b * 0.3D);
/* 600 */       (event.getMovementInput()).field_78902_a = (float)((event.getMovementInput()).field_78902_a * 0.3D);
/* 601 */       if ((event.getMovementInput()).field_78901_c) {
/* 602 */         clientPlayerSitMoveAmplifier = 0.0D;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 608 */       double d0 = 0.3D;
/* 609 */       if ((event.getMovementInput()).field_192832_b != 0.0F && isButtonDown((Minecraft.func_71410_x()).field_71474_y.field_151444_V.func_151463_i()) && clientPlayerSitMoveAmplifier < clientPlayerSitMoveLess * 2.0D && !(Minecraft.func_71410_x()).field_71439_g.func_70093_af() && !((EntityPlayer)entityPlayerSP).field_70170_p.func_184143_b(new AxisAlignedBB(((EntityPlayer)entityPlayerSP).field_70165_t - d0, ((EntityPlayer)entityPlayerSP).field_70163_u, ((EntityPlayer)entityPlayerSP).field_70161_v - d0, ((EntityPlayer)entityPlayerSP).field_70165_t + d0, ((EntityPlayer)entityPlayerSP).field_70163_u + 1.8D, ((EntityPlayer)entityPlayerSP).field_70161_v + d0)))
/*     */       {
/*     */         
/* 612 */         clientPlayerState.disableSit();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 619 */     if (clientPlayerState.isCrawling) {
/* 620 */       (event.getMovementInput()).field_192832_b = (float)((event.getMovementInput()).field_192832_b * 0.4D);
/* 621 */       (event.getMovementInput()).field_78902_a = (float)((event.getMovementInput()).field_78902_a * 0.4D);
/*     */       
/* 623 */       (event.getMovementInput()).field_78901_c = false;
/* 624 */       double d0 = 0.3D;
/* 625 */       if ((event.getMovementInput()).field_78901_c && !((EntityPlayer)entityPlayerSP).field_70170_p.func_184143_b(new AxisAlignedBB(((EntityPlayer)entityPlayerSP).field_70165_t - d0, ((EntityPlayer)entityPlayerSP).field_70163_u, ((EntityPlayer)entityPlayerSP).field_70161_v - d0, ((EntityPlayer)entityPlayerSP).field_70165_t + d0, ((EntityPlayer)entityPlayerSP).field_70163_u + 1.8D, ((EntityPlayer)entityPlayerSP).field_70161_v + d0))) {
/*     */ 
/*     */         
/* 628 */         clientPlayerState.disableCrawling();
/* 629 */         clientPlayerState.disableSit();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 635 */       d0 = 0.3D;
/* 636 */       if ((event.getMovementInput()).field_192832_b != 0.0F && isButtonDown((Minecraft.func_71410_x()).field_71474_y.field_151444_V.func_151463_i()) && ((EntityPlayer)entityPlayerSP).field_70122_E && ((EntityPlayer)entityPlayerSP).field_70181_x < 0.0D && ModularMovements.CONFIG.crawl.sprintCancel && !(Minecraft.func_71410_x()).field_71439_g.func_70093_af() && !((EntityPlayer)entityPlayerSP).field_70170_p.func_184143_b(new AxisAlignedBB(((EntityPlayer)entityPlayerSP).field_70165_t - d0, ((EntityPlayer)entityPlayerSP).field_70163_u, ((EntityPlayer)entityPlayerSP).field_70161_v - d0, ((EntityPlayer)entityPlayerSP).field_70165_t + d0, ((EntityPlayer)entityPlayerSP).field_70163_u + 1.8D, ((EntityPlayer)entityPlayerSP).field_70161_v + d0)))
/*     */       {
/*     */         
/* 639 */         clientPlayerState.disableCrawling();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 646 */     if (clientPlayerState.probe != 0) {
/* 647 */       (event.getMovementInput()).field_192832_b = (float)((event.getMovementInput()).field_192832_b * 0.9D);
/* 648 */       (event.getMovementInput()).field_78902_a = (float)((event.getMovementInput()).field_78902_a * 0.9D);
/*     */     } 
/*     */     
/* 651 */     if ((Minecraft.func_71410_x()).field_71439_g != null) {
/*     */       try {
/* 653 */         speedInAir.set((Minecraft.func_71410_x()).field_71439_g, Float.valueOf(0.02F));
/* 654 */       } catch (IllegalArgumentException|IllegalAccessException e) {
/*     */         
/* 656 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTickRender(TickEvent.RenderTickEvent event) {
/* 663 */     if (event.phase == TickEvent.Phase.START) {
/* 664 */       if (!isButtonDown(sit.func_151463_i())) {
/* 665 */         sitKeyLock = false;
/*     */       }
/* 667 */       if (isButtonDown(sit.func_151463_i()) && 
/* 668 */         (Minecraft.func_71410_x()).field_71439_g != null && 
/* 669 */         (Minecraft.func_71410_x()).field_71439_g.field_70143_R > 1.0F) {
/* 670 */         wannaSliding = true;
/*     */       }
/*     */ 
/*     */       
/* 674 */       onSit();
/* 675 */       if ((Minecraft.func_71410_x()).field_71439_g != null && (Minecraft.func_71410_x()).field_71462_r == null && 
/* 676 */         clientPlayerState.canProbe() && !(Minecraft.func_71410_x()).field_71439_g.func_184218_aH() && !(Minecraft.func_71410_x()).field_71439_g.func_184613_cA()) {
/* 677 */         if (!probeKeyLock && isButtonDown(leftProbe.func_151463_i())) {
/* 678 */           probeKeyLock = true;
/* 679 */           if (clientPlayerState.probe != -1) {
/* 680 */             clientPlayerState.leftProbe();
/*     */           } else {
/* 682 */             clientPlayerState.resetProbe();
/*     */           } 
/*     */         } 
/*     */         
/* 686 */         if (!probeKeyLock && isButtonDown(rightProbe.func_151463_i())) {
/* 687 */           probeKeyLock = true;
/* 688 */           if (clientPlayerState.probe != 1) {
/* 689 */             clientPlayerState.rightProbe();
/*     */           } else {
/* 691 */             clientPlayerState.resetProbe();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 697 */       if (!isButtonDown(crawling.func_151463_i())) {
/* 698 */         crawlingKeyLock = false;
/*     */       }
/*     */       
/* 701 */       if (!isButtonDown(leftProbe.func_151463_i()) && !isButtonDown(rightProbe.func_151463_i())) {
/* 702 */         probeKeyLock = false;
/* 703 */         if (!ModularMovements.CONFIG.lean.autoHold && clientPlayerState.probe != 0) {
/* 704 */           clientPlayerState.resetProbe();
/*     */         }
/* 706 */       } else if (isButtonDown(leftProbe.func_151463_i())) {
/* 707 */         if (!ModularMovements.CONFIG.lean.autoHold && clientPlayerState.probe != -1) {
/* 708 */           probeKeyLock = false;
/*     */         }
/* 710 */       } else if (isButtonDown(rightProbe.func_151463_i()) && 
/* 711 */         !ModularMovements.CONFIG.lean.autoHold && clientPlayerState.probe != 1) {
/* 712 */         probeKeyLock = false;
/*     */       } 
/*     */       
/* 715 */       if ((Minecraft.func_71410_x()).field_71439_g != null && (
/* 716 */         (Minecraft.func_71410_x()).field_71439_g.func_184218_aH() || (Minecraft.func_71410_x()).field_71439_g.func_184613_cA())) {
/* 717 */         clientPlayerSitMoveAmplifier = 0.0D;
/* 718 */         if (clientPlayerState.isSitting) {
/* 719 */           clientPlayerState.disableSit();
/*     */         }
/* 721 */         if (clientPlayerState.isCrawling) {
/* 722 */           clientPlayerState.disableCrawling();
/*     */         }
/* 724 */         clientPlayerState.resetProbe();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isButtonDown(int id) {
/*     */     try {
/* 733 */       if (id < 0) {
/* 734 */         return Mouse.isButtonDown(id + 100);
/*     */       }
/* 736 */       return Keyboard.isKeyDown(id);
/* 737 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*     */ 
/*     */       
/* 740 */       return false;
/*     */     } 
/*     */   }
/*     */   private static boolean isButtonsDownAll(int... id) {
/* 744 */     boolean flag = true;
/* 745 */     for (int i = 0; i < id.length; i++) {
/* 746 */       flag = (flag && isButtonDown(id[i]));
/* 747 */       if (!flag) {
/*     */         break;
/*     */       }
/*     */     } 
/* 751 */     return flag;
/*     */   }
/*     */   
/*     */   private static boolean isButtonsDownOne(int... id) {
/* 755 */     boolean flag = false;
/* 756 */     for (int i = 0; i < id.length; i++) {
/* 757 */       flag = (flag || isButtonDown(id[i]));
/* 758 */       if (flag) {
/*     */         break;
/*     */       }
/*     */     } 
/* 762 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockPush(PlayerSPPushOutOfBlocksEvent event) {
/* 767 */     if (cameraProbeOffset != 0.0F) {
/*     */ 
/*     */ 
/*     */       
/* 771 */       Vec3d vec3d = (new Vec3d(-0.6D, 0.0D, 0.0D)).func_178785_b((float)(-((event.getEntityPlayer()).field_70177_z - 180.0F) * Math.PI / 180.0D)).func_186678_a(-cameraProbeOffset);
/* 772 */       EntityPlayerSPHelper.pushOutOfBlocks((EntityPlayerSP)event.getEntityPlayer(), 
/* 773 */           (event.getEntityPlayer()).field_70165_t + vec3d.field_72450_a - (event.getEntityPlayer()).field_70130_N * 0.35D, lastModAABB.field_72338_b + 0.5D, 
/*     */           
/* 775 */           (event.getEntityPlayer()).field_70161_v + vec3d.field_72449_c + (event.getEntityPlayer()).field_70130_N * 0.35D);
/* 776 */       EntityPlayerSPHelper.pushOutOfBlocks((EntityPlayerSP)event.getEntityPlayer(), 
/* 777 */           (event.getEntityPlayer()).field_70165_t + vec3d.field_72450_a - (event.getEntityPlayer()).field_70130_N * 0.35D, lastModAABB.field_72338_b + 0.5D, 
/*     */           
/* 779 */           (event.getEntityPlayer()).field_70161_v + vec3d.field_72449_c - (event.getEntityPlayer()).field_70130_N * 0.35D);
/* 780 */       EntityPlayerSPHelper.pushOutOfBlocks((EntityPlayerSP)event.getEntityPlayer(), 
/* 781 */           (event.getEntityPlayer()).field_70165_t + vec3d.field_72450_a + (event.getEntityPlayer()).field_70130_N * 0.35D, lastModAABB.field_72338_b + 0.5D, 
/*     */           
/* 783 */           (event.getEntityPlayer()).field_70161_v + vec3d.field_72449_c - (event.getEntityPlayer()).field_70130_N * 0.35D);
/* 784 */       EntityPlayerSPHelper.pushOutOfBlocks((EntityPlayerSP)event.getEntityPlayer(), 
/* 785 */           (event.getEntityPlayer()).field_70165_t + vec3d.field_72450_a + (event.getEntityPlayer()).field_70130_N * 0.35D, lastModAABB.field_72338_b + 0.5D, 
/*     */           
/* 787 */           (event.getEntityPlayer()).field_70161_v + vec3d.field_72449_c + (event.getEntityPlayer()).field_70130_N * 0.35D);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTickPlayer(TickEvent.PlayerTickEvent event) {
/* 793 */     if ((Minecraft.func_71410_x()).field_71439_g == null) {
/*     */       return;
/*     */     }
/* 796 */     if (event.player != (Minecraft.func_71410_x()).field_71439_g) {
/*     */       return;
/*     */     }
/* 799 */     if (!event.player.func_70089_S()) {
/* 800 */       clientPlayerSitMoveAmplifier = 0.0D;
/* 801 */       if (clientPlayerState.isSitting) {
/* 802 */         clientPlayerState.disableSit();
/*     */       }
/* 804 */       if (clientPlayerState.isCrawling) {
/* 805 */         clientPlayerState.disableCrawling();
/*     */       }
/*     */     } 
/* 808 */     if (event.phase != TickEvent.Phase.END) {
/* 809 */       if (lastAABB != null && event.player.func_174813_aQ() == lastModAABB) {
/* 810 */         event.player.func_174826_a(lastAABB);
/*     */       }
/*     */       
/* 813 */       if (event.player.func_70093_af()) {
/* 814 */         clientPlayerSitMoveAmplifier = 0.0D;
/*     */       }
/* 816 */       if (clientPlayerSitMoveAmplifier > 0.0D) {
/* 817 */         if (clientPlayerState.isSitting && 
/* 818 */           event.player == (Minecraft.func_71410_x()).field_71439_g) {
/* 819 */           if (enableForceGravity && event.player.field_70181_x <= 0.0D) {
/* 820 */             event.player.field_70181_x = -forceGravity;
/*     */           }
/* 822 */           event.player.field_70159_w = clientPlayerSitMoveVec3d.field_72450_a * clientPlayerSitMoveAmplifier * clientPlayerSitMoveAmplifierUser * clientPlayerSitMoveAmplifierCharged;
/*     */           
/* 824 */           event.player.field_70179_y = clientPlayerSitMoveVec3d.field_72449_c * clientPlayerSitMoveAmplifier * clientPlayerSitMoveAmplifierUser * clientPlayerSitMoveAmplifierCharged;
/*     */           
/* 826 */           if ((Minecraft.func_71410_x()).field_71439_g.field_70122_E) {
/* 827 */             (Minecraft.func_71410_x()).field_71439_g
/* 828 */               .func_184185_a(SoundEvents.field_187575_bT, 2.0F * (float)(clientPlayerSitMoveAmplifier * clientPlayerSitMoveAmplifierCharged / ModularMovements.CONFIG.slide.maxForce), 0.8F);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 835 */         clientPlayerSitMoveAmplifier -= clientPlayerSitMoveLess;
/* 836 */         if (!event.player.field_70122_E && event.player.field_70143_R > 1.0F && !event.player.func_70090_H()) {
/* 837 */           clientPlayerSitMoveAmplifier += clientPlayerSitMoveLess;
/*     */         }
/* 839 */         if (clientPlayerSitMoveAmplifier <= 0.0D) {
/* 840 */           if (ModularMovements.CONFIG.sit.autoHold) {
/* 841 */             if (!isButtonDown(sit.func_151463_i())) {
/* 842 */               clientPlayerState.disableSit();
/*     */             }
/*     */           }
/* 845 */           else if (isButtonDown(sit.func_151463_i())) {
/* 846 */             clientPlayerState.disableSit();
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 853 */     float f = event.player.field_70130_N;
/* 854 */     float f1 = event.player.field_70131_O;
/* 855 */     if (clientPlayerState.isSitting) {
/* 856 */       f1 = 1.2F;
/* 857 */     } else if (clientPlayerState.isCrawling) {
/* 858 */       f1 = 0.8F;
/*     */     } 
/* 860 */     if (f != event.player.field_70130_N || f1 != event.player.field_70131_O) {
/* 861 */       AxisAlignedBB axisalignedbb = event.player.func_174813_aQ();
/* 862 */       axisalignedbb = new AxisAlignedBB(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c, axisalignedbb.field_72340_a + f, axisalignedbb.field_72338_b + f1, axisalignedbb.field_72339_c + f);
/*     */ 
/*     */       
/* 865 */       if (!event.player.field_70170_p.func_184143_b(axisalignedbb)) {
/*     */         try {
/* 867 */           ServerListener.setSize.invoke(event.player, new Object[] { Float.valueOf(f), Float.valueOf(f1) });
/* 868 */         } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException e) {
/* 869 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 873 */     Vec3d vec3d = (new Vec3d(-0.6D, 0.0D, 0.0D)).func_178785_b((float)(-(event.player.field_70177_z - 180.0F) * Math.PI / 180.0D));
/* 874 */     lastAABB = event.player.func_174813_aQ();
/* 875 */     lastModAABB = event.player.func_174813_aQ().func_191194_a(vec3d.func_186678_a(-cameraProbeOffset));
/* 876 */     event.player.func_174826_a(lastModAABB);
/*     */     
/* 878 */     if (clientPlayerSitMoveAmplifier > 0.0D) {
/* 879 */       clientPlayerState.isSliding = true;
/* 880 */       TacticalHandler.sendNoStep(100);
/*     */     } else {
/* 882 */       clientPlayerState.isSliding = false;
/*     */     } 
/* 884 */     if (event.player.field_70143_R <= 1.0F || 
/* 885 */       wannaSliding);
/*     */ 
/*     */ 
/*     */     
/* 889 */     TacticalHandler.sendToServer(clientPlayerState.writeCode());
/*     */   }
/*     */   
/*     */   public static boolean isSliding(Integer id) {
/* 893 */     if (!ohterPlayerStateMap.containsKey(id)) {
/* 894 */       return false;
/*     */     }
/* 896 */     return ((PlayerState)ohterPlayerStateMap.get(id)).isSliding;
/*     */   }
/*     */   
/*     */   public static boolean isSitting(Integer id) {
/* 900 */     if (!ohterPlayerStateMap.containsKey(id)) {
/* 901 */       return false;
/*     */     }
/* 903 */     return ((PlayerState)ohterPlayerStateMap.get(id)).isSitting;
/*     */   }
/*     */   
/*     */   public static boolean isCrawling(Integer id) {
/* 907 */     if (!ohterPlayerStateMap.containsKey(id)) {
/* 908 */       return false;
/*     */     }
/* 910 */     return ((PlayerState)ohterPlayerStateMap.get(id)).isCrawling;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTickOtherPlayer(TickEvent.PlayerTickEvent event) {
/* 915 */     if (event.side == Side.CLIENT) {
/* 916 */       if (event.phase != TickEvent.Phase.END) {
/* 917 */         PlayerState state = ohterPlayerStateMap.get(Integer.valueOf(event.player.func_145782_y()));
/* 918 */         if (state != null && 
/* 919 */           state.lastAABB != null && event.player.func_174813_aQ() == state.lastModAABB) {
/* 920 */           event.player.func_174826_a(state.lastAABB);
/*     */         }
/*     */       } else {
/*     */         
/* 924 */         float f = event.player.field_70130_N;
/* 925 */         float f1 = event.player.field_70131_O;
/* 926 */         if (isSitting(Integer.valueOf(event.player.func_145782_y()))) {
/* 927 */           f1 = 1.2F;
/* 928 */         } else if (isCrawling(Integer.valueOf(event.player.func_145782_y()))) {
/* 929 */           f1 = 0.8F;
/*     */         } 
/* 931 */         PlayerState state = ohterPlayerStateMap.get(Integer.valueOf(event.player.func_145782_y()));
/* 932 */         float cameraProbeOffset = 0.0F;
/* 933 */         if (state != null) {
/* 934 */           cameraProbeOffset = state.probeOffset;
/*     */         }
/* 936 */         if (f != event.player.field_70130_N || f1 != event.player.field_70131_O) {
/* 937 */           AxisAlignedBB axisalignedbb = event.player.func_174813_aQ();
/* 938 */           axisalignedbb = new AxisAlignedBB(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c, axisalignedbb.field_72340_a + f, axisalignedbb.field_72338_b + f1, axisalignedbb.field_72339_c + f);
/*     */ 
/*     */ 
/*     */           
/* 942 */           if (!event.player.field_70170_p.func_184143_b(axisalignedbb)) {
/*     */             try {
/* 944 */               ServerListener.setSize.invoke(event.player, new Object[] { Float.valueOf(f), Float.valueOf(f1) });
/* 945 */             } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException e) {
/* 946 */               e.printStackTrace();
/*     */             } 
/*     */           }
/*     */         } 
/* 950 */         if (state != null) {
/* 951 */           Vec3d vec3d = (new Vec3d(-0.6D, 0.0D, 0.0D)).func_178785_b((float)(-(event.player.field_70177_z - 180.0F) * Math.PI / 180.0D));
/* 952 */           state.lastAABB = event.player.func_174813_aQ();
/* 953 */           state.lastModAABB = state.lastAABB.func_191194_a(vec3d.func_186678_a(-cameraProbeOffset));
/* 954 */           event.player.func_174826_a(state.lastModAABB);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onHandBobing(RenderHandEvent event) {
/* 962 */     if (clientPlayerSitMoveAmplifier > 0.0D && 
/* 963 */       Minecraft.func_71410_x().func_175606_aa() instanceof EntityPlayer) {
/* 964 */       float partialTicks = event.getPartialTicks();
/* 965 */       EntityPlayer entityplayer = (EntityPlayer)Minecraft.func_71410_x().func_175606_aa();
/* 966 */       float f = entityplayer.field_70140_Q - entityplayer.field_70141_P;
/* 967 */       float f1 = -(entityplayer.field_70140_Q + f * partialTicks);
/* 968 */       float f2 = entityplayer.field_71107_bF + (entityplayer.field_71109_bG - entityplayer.field_71107_bF) * partialTicks;
/*     */       
/* 970 */       float f3 = entityplayer.field_70727_aS + (entityplayer.field_70726_aT - entityplayer.field_70727_aS) * partialTicks;
/*     */       
/* 972 */       GlStateManager.func_179114_b(f3, -1.0F, 0.0F, 0.0F);
/* 973 */       GlStateManager.func_179114_b(Math.abs(MathHelper.func_76134_b(f1 * 3.1415927F - 0.2F) * f2) * 5.0F, -1.0F, 0.0F, 0.0F);
/*     */       
/* 975 */       GlStateManager.func_179114_b(MathHelper.func_76126_a(f1 * 3.1415927F) * f2 * 3.0F, 0.0F, 0.0F, -1.0F);
/* 976 */       GlStateManager.func_179109_b(-MathHelper.func_76126_a(f1 * 3.1415927F) * f2 * 0.5F, 
/* 977 */           Math.abs(MathHelper.func_76134_b(f1 * 3.1415927F) * f2), 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlaySoundAtEntity(PlaySoundAtEntityEvent event) {
/* 984 */     if ((Minecraft.func_71410_x()).field_71439_g != null && 
/* 985 */       event.getEntity() == (Minecraft.func_71410_x()).field_71439_g && 
/* 986 */       clientPlayerSitMoveAmplifier > 0.0D && (
/* 987 */       (ResourceLocation)SoundEvent.field_187505_a.func_177774_c(event.getSound())).toString().contains("step"))
/* 988 */       event.setVolume(0.0F); 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\tactical\client\ClientLitener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */