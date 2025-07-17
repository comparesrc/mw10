/*     */ package com.modularwarfare.client;
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.api.AnimationUtils;
/*     */ import com.modularwarfare.api.RenderHandFisrtPersonEvent;
/*     */ import com.modularwarfare.client.fpp.basic.animations.AnimStateMachine;
/*     */ import com.modularwarfare.client.fpp.basic.configs.ArmorRenderConfig;
/*     */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderType;
/*     */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderer;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderAmmo;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderGrenade;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderParameters;
/*     */ import com.modularwarfare.client.fpp.enhanced.animation.EnhancedStateMachine;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.RenderType;
/*     */ import com.modularwarfare.client.fpp.enhanced.renderers.RenderGunEnhanced;
/*     */ import com.modularwarfare.client.handler.ClientTickHandler;
/*     */ import com.modularwarfare.client.hud.GunUI;
/*     */ import com.modularwarfare.client.model.ModelCustomArmor;
/*     */ import com.modularwarfare.client.scope.ScopeUtils;
/*     */ import com.modularwarfare.common.armor.ArmorType;
/*     */ import com.modularwarfare.common.armor.ItemMWArmor;
/*     */ import com.modularwarfare.common.armor.ItemSpecialArmor;
/*     */ import com.modularwarfare.common.entity.grenades.EntitySmokeGrenade;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.type.BaseItem;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import com.modularwarfare.utility.OptifineHelper;
/*     */ import com.modularwarfare.utility.RenderHelperMW;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.client.event.RenderItemInFrameEvent;
/*     */ import net.minecraftforge.client.event.RenderLivingEvent;
/*     */ import net.minecraftforge.client.event.RenderPlayerEvent;
/*     */ import net.minecraftforge.client.event.RenderSpecificHandEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ import org.lwjgl.util.glu.Project;
/*     */ 
/*     */ public class ClientRenderHooks extends ForgeEvent {
/*  67 */   public static HashMap<EntityLivingBase, AnimStateMachine> weaponBasicAnimations = new HashMap<>();
/*  68 */   public static IdentityHashMap<EntityLivingBase, EnhancedStateMachine> weaponEnhancedAnimations = new IdentityHashMap<>();
/*     */   
/*  70 */   public static CustomItemRenderer[] customRenderers = new CustomItemRenderer[20];
/*     */   public static boolean isAimingScope;
/*     */   public static boolean isAiming;
/*     */   public float partialTicks;
/*     */   private Minecraft mc;
/*  75 */   private float equippedProgress = 1.0F; private float prevEquippedProgress = 1.0F;
/*     */   
/*     */   public static boolean debug = false;
/*  78 */   public static final ResourceLocation grenade_smoke = new ResourceLocation("modularwarfare", "textures/particles/smoke.png");
/*     */ 
/*     */   
/*     */   public ClientRenderHooks() {
/*  82 */     this.mc = Minecraft.func_71410_x();
/*  83 */     customRenderers[0] = (CustomItemRenderer)(ClientProxy.gunEnhancedRenderer = new RenderGunEnhanced());
/*  84 */     customRenderers[1] = (CustomItemRenderer)(ClientProxy.gunStaticRenderer = new RenderGunStatic());
/*  85 */     customRenderers[2] = (CustomItemRenderer)(ClientProxy.ammoRenderer = new RenderAmmo());
/*  86 */     customRenderers[3] = (CustomItemRenderer)(ClientProxy.attachmentRenderer = new RenderAttachment());
/*  87 */     customRenderers[8] = (CustomItemRenderer)(ClientProxy.grenadeRenderer = new RenderGrenade());
/*     */   }
/*     */   
/*     */   public static AnimStateMachine getAnimMachine(EntityLivingBase entityPlayer) {
/*  91 */     AnimStateMachine animation = null;
/*  92 */     if (weaponBasicAnimations.containsKey(entityPlayer)) {
/*  93 */       animation = weaponBasicAnimations.get(entityPlayer);
/*     */     } else {
/*  95 */       animation = new AnimStateMachine();
/*  96 */       weaponBasicAnimations.put(entityPlayer, animation);
/*     */     } 
/*  98 */     return animation;
/*     */   }
/*     */   
/*     */   public static EnhancedStateMachine getEnhancedAnimMachine(EntityLivingBase entityPlayer) {
/* 102 */     EnhancedStateMachine animation = null;
/* 103 */     if (weaponEnhancedAnimations.containsKey(entityPlayer)) {
/* 104 */       animation = weaponEnhancedAnimations.get(entityPlayer);
/*     */     } else {
/* 106 */       animation = new EnhancedStateMachine();
/* 107 */       weaponEnhancedAnimations.put(entityPlayer, animation);
/*     */     } 
/* 109 */     return animation;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderTick(TickEvent.RenderTickEvent event) {
/* 114 */     switch (event.phase) {
/*     */       case HEAD:
/* 116 */         RenderParameters.smoothing = event.renderTickTime;
/* 117 */         SetPartialTick(event.renderTickTime);
/* 118 */         if (!Minecraft.func_71410_x().func_147110_a().isStencilEnabled()) {
/* 119 */           Minecraft.func_71410_x().func_147110_a().enableStencil();
/*     */         }
/*     */         break;
/*     */       
/*     */       case CHEST:
/* 124 */         if (this.mc.field_71439_g == null || this.mc.field_71441_e == null)
/*     */           return; 
/* 126 */         if (GunUI.hitMarkerTime > 0)
/* 127 */           GunUI.hitMarkerTime--; 
/* 128 */         ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketAimingRequest(this.mc.field_71439_g.func_70005_c_(), (isAiming || isAimingScope)));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onRender(RenderPlayerEvent.Pre event) {
/* 136 */     boolean aim = AnimationUtils.isAiming.containsKey(event.getEntityPlayer().func_70005_c_());
/* 137 */     if (!aim) {
/*     */       return;
/*     */     }
/* 140 */     (event.getEntityPlayer()).field_70761_aq = interpolateRotation((event.getEntityPlayer()).field_70761_aq, (event.getEntityPlayer()).field_70177_z, 0.1F);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderItemFrame(RenderItemInFrameEvent event) {
/* 145 */     Item item = event.getItem().func_77973_b();
/* 146 */     if (item instanceof com.modularwarfare.common.guns.ItemGun) {
/* 147 */       BaseType type = ((BaseItem)event.getItem().func_77973_b()).baseType;
/* 148 */       if (type.enhancedModel != null) {
/* 149 */         event.setCanceled(true);
/*     */         
/* 151 */         int rotation = event.getEntityItemFrame().func_82333_j();
/* 152 */         GlStateManager.func_179114_b(-rotation * 45.0F, 0.0F, 0.0F, 1.0F);
/* 153 */         RenderHelper.func_74519_b();
/* 154 */         GlStateManager.func_179114_b(rotation * 45.0F, 0.0F, 0.0F, 1.0F);
/* 155 */         GlStateManager.func_179094_E();
/* 156 */         ClientProxy.gunEnhancedRenderer.drawThirdGun(null, RenderType.ITEMFRAME, null, event.getItem());
/* 157 */         GlStateManager.func_179121_F();
/* 158 */       } else if (type.hasModel()) {
/* 159 */         event.setCanceled(true);
/*     */         
/* 161 */         int rotation = event.getEntityItemFrame().func_82333_j();
/* 162 */         GlStateManager.func_179114_b(-rotation * 45.0F, 0.0F, 0.0F, 1.0F);
/* 163 */         RenderHelper.func_74519_b();
/* 164 */         GlStateManager.func_179114_b(rotation * 45.0F, 0.0F, 0.0F, 1.0F);
/* 165 */         GlStateManager.func_179094_E();
/* 166 */         float scale = 0.75F;
/* 167 */         GlStateManager.func_179152_a(scale, scale, scale);
/* 168 */         GlStateManager.func_179109_b(0.15F, -0.15F, 0.0F);
/* 169 */         customRenderers[type.id].renderItem(CustomItemRenderType.ENTITY, EnumHand.MAIN_HAND, event.getItem(), new Object[0]);
/* 170 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onWorldRenderLast(RenderWorldLastEvent event) {
/* 178 */     for (Object o : this.mc.field_71441_e.func_72910_y()) {
/* 179 */       Entity givenEntity = (Entity)o;
/*     */       
/* 181 */       if (givenEntity instanceof EntitySmokeGrenade) {
/* 182 */         EntitySmokeGrenade smokeGrenade = (EntitySmokeGrenade)givenEntity;
/* 183 */         if (smokeGrenade.exploded && 
/* 184 */           smokeGrenade.smokeTime <= 220.0F) {
/* 185 */           RenderHelperMW.renderSmoke(grenade_smoke, smokeGrenade.field_70165_t, smokeGrenade.field_70163_u + 1.0D, smokeGrenade.field_70161_v, this.partialTicks, 600, 600, "0xFFFFFF", 0.800000011920929D);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderHeldItem(RenderSpecificHandEvent event) {
/* 195 */     event.setCanceled(renderHeldItem(event.getItemStack(), event.getHand(), event.getPartialTicks(), getFOVModifier(event.getPartialTicks())));
/*     */   }
/*     */   
/*     */   public boolean renderHeldItem(ItemStack stack, EnumHand hand, float partialTicksTime, float fov) {
/* 199 */     EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
/* 200 */     boolean result = false;
/* 201 */     if (this.mc.field_71462_r instanceof com.modularwarfare.client.gui.GuiGunModify) {
/* 202 */       return true;
/*     */     }
/*     */     
/* 205 */     if (stack != null && stack.func_77973_b() instanceof BaseItem) {
/* 206 */       BaseType type = ((BaseItem)stack.func_77973_b()).baseType;
/* 207 */       BaseItem item = (BaseItem)stack.func_77973_b();
/*     */       
/* 209 */       if (hand != EnumHand.MAIN_HAND) {
/* 210 */         result = true;
/* 211 */         return result;
/*     */       } 
/*     */       
/* 214 */       if (type.id > customRenderers.length) {
/* 215 */         return result;
/*     */       }
/* 217 */       if (item.render3d && customRenderers[type.id] != null && type.hasModel() && !type.getAssetDir().equalsIgnoreCase("attachments")) {
/* 218 */         result = true;
/*     */         
/* 220 */         float partialTicks = partialTicksTime;
/* 221 */         EntityRenderer renderer = this.mc.field_71460_t;
/* 222 */         float farPlaneDistance = this.mc.field_71474_y.field_151451_c * 16.0F;
/* 223 */         ItemRenderer itemRenderer = this.mc.func_175597_ag();
/*     */         
/* 225 */         GL11.glDepthRange(ModConfig.INSTANCE.hud.handDepthRangeMin, ModConfig.INSTANCE.hud.handDepthRangeMax);
/*     */         
/* 227 */         GlStateManager.func_179128_n(5889);
/* 228 */         GlStateManager.func_179094_E();
/* 229 */         GlStateManager.func_179096_D();
/*     */         
/* 231 */         float zFar = 2.0F * farPlaneDistance;
/* 232 */         Project.gluPerspective(fov, this.mc.field_71443_c / this.mc.field_71440_d, 1.0E-5F, zFar);
/* 233 */         GlStateManager.func_179152_a(ModConfig.INSTANCE.hud.projectionScale.x, ModConfig.INSTANCE.hud.projectionScale.y, ModConfig.INSTANCE.hud.projectionScale.z);
/*     */         
/* 235 */         GlStateManager.func_179128_n(5888);
/* 236 */         GlStateManager.func_179094_E();
/* 237 */         GlStateManager.func_179096_D();
/* 238 */         GlStateManager.func_179152_a(1.0F / zFar, 1.0F / zFar, 1.0F / zFar);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 243 */         if (Double.isNaN(RenderParameters.collideFrontDistance)) {
/* 244 */           RenderParameters.collideFrontDistance = 0.0F;
/*     */         }
/* 246 */         boolean flag = (this.mc.func_175606_aa() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.func_175606_aa()).func_70608_bn());
/*     */         
/* 248 */         if (this.mc.field_71474_y.field_74320_O == 0 && !flag && !this.mc.field_71474_y.field_74319_N && !this.mc.field_71442_b.func_78747_a() && this.mc.func_175606_aa().equals(this.mc.field_71439_g)) {
/* 249 */           renderer.func_180436_i();
/* 250 */           float f1 = 1.0F - this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks;
/* 251 */           EntityPlayerSP entityplayersp = this.mc.field_71439_g;
/* 252 */           float f2 = entityplayersp.func_70678_g(partialTicks);
/* 253 */           float f3 = entityplayersp.field_70127_C + (entityplayersp.field_70125_A - entityplayersp.field_70127_C) * partialTicks;
/* 254 */           float f4 = entityplayersp.field_70126_B + (entityplayersp.field_70177_z - entityplayersp.field_70126_B) * partialTicks;
/*     */ 
/*     */           
/* 257 */           GlStateManager.func_179140_f();
/* 258 */           GlStateManager.func_179094_E();
/* 259 */           GlStateManager.func_179114_b(f3, 1.0F, 0.0F, 0.0F);
/* 260 */           GlStateManager.func_179114_b(f4, 0.0F, 1.0F, 0.0F);
/* 261 */           RenderHelper.func_74519_b();
/* 262 */           GlStateManager.func_179121_F();
/*     */ 
/*     */           
/* 265 */           int i = this.mc.field_71441_e.func_175626_b(new BlockPos(entityplayersp.field_70165_t, entityplayersp.field_70163_u + entityplayersp.func_70047_e(), entityplayersp.field_70161_v), 0);
/* 266 */           OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (i & 0xFFFF), (i >> 16));
/*     */ 
/*     */ 
/*     */           
/* 270 */           float f5 = entityplayersp.field_71164_i + (entityplayersp.field_71155_g - entityplayersp.field_71164_i) * partialTicks;
/* 271 */           float f6 = entityplayersp.field_71163_h + (entityplayersp.field_71154_f - entityplayersp.field_71163_h) * partialTicks;
/* 272 */           GlStateManager.func_179114_b((entityplayersp.field_70125_A - f5) * 0.1F, 1.0F, 0.0F, 0.0F);
/* 273 */           GlStateManager.func_179114_b((entityplayersp.field_70177_z - f6) * 0.1F, 0.0F, 1.0F, 0.0F);
/*     */           
/* 275 */           GlStateManager.func_179091_B();
/* 276 */           GlStateManager.func_179094_E();
/*     */ 
/*     */           
/* 279 */           float f7 = -0.4F * MathHelper.func_76126_a(MathHelper.func_76129_c(f2) * 3.1415927F);
/* 280 */           float f8 = 0.2F * MathHelper.func_76126_a(MathHelper.func_76129_c(f2) * 3.1415927F * 2.0F);
/* 281 */           float f9 = -0.2F * MathHelper.func_76126_a(f2 * 3.1415927F);
/* 282 */           GlStateManager.func_179109_b(f7, f8, f9);
/*     */           
/* 284 */           GlStateManager.func_179109_b(0.56F, -0.52F, -0.71999997F);
/* 285 */           GlStateManager.func_179109_b(0.0F, f1 * -0.6F, 0.0F);
/* 286 */           GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
/* 287 */           float f10 = MathHelper.func_76126_a(f2 * f2 * 3.1415927F);
/* 288 */           float f11 = MathHelper.func_76126_a(MathHelper.func_76129_c(f2) * 3.1415927F);
/* 289 */           GlStateManager.func_179114_b(f10 * -20.0F, 0.0F, 1.0F, 0.0F);
/* 290 */           GlStateManager.func_179114_b(f11 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 291 */           GlStateManager.func_179114_b(f11 * -80.0F, 1.0F, 0.0F, 0.0F);
/* 292 */           GlStateManager.func_179152_a(0.4F, 0.4F, 0.4F);
/*     */           
/* 294 */           if (debug) {
/* 295 */             System.out.println(new float[] { f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11 });
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 301 */           if (!ScopeUtils.isIndsideGunRendering) {
/* 302 */             ClientProxy.scopeUtils.initBlur();
/* 303 */             OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, OptifineHelper.getDrawFrameBuffer());
/*     */           } 
/* 305 */           GlStateManager.func_179094_E();
/*     */ 
/*     */           
/* 308 */           if (item instanceof com.modularwarfare.common.guns.ItemGun) {
/* 309 */             if (((GunType)type).animationType.equals(WeaponAnimationType.BASIC)) {
/* 310 */               customRenderers[1].renderItem(CustomItemRenderType.EQUIPPED_FIRST_PERSON, hand, ClientTickHandler.lastItemStack.func_190926_b() ? stack : ClientTickHandler.lastItemStack, new Object[] { this.mc.field_71441_e, this.mc.field_71439_g });
/*     */             }
/*     */             else {
/*     */               
/* 314 */               if (GunType.getAttachment(this.mc.field_71439_g.func_184614_ca(), AttachmentPresetEnum.Sight) != null) {
/* 315 */                 ItemAttachment itemAttachment = (ItemAttachment)GunType.getAttachment(this.mc.field_71439_g.func_184614_ca(), AttachmentPresetEnum.Sight).func_77973_b();
/* 316 */                 if (itemAttachment.type.sight.modeType.insideGunRendering) {
/* 317 */                   renderInsideGun(stack, hand, partialTicksTime, fov);
/* 318 */                   GL11.glDepthRange(ModConfig.INSTANCE.hud.handDepthRangeMin, ModConfig.INSTANCE.hud.handDepthRangeMax);
/*     */                 } 
/*     */               } 
/* 321 */               customRenderers[0].renderItem(CustomItemRenderType.EQUIPPED_FIRST_PERSON, hand, this.mc.field_71439_g.func_184614_ca(), new Object[] { this.mc.field_71441_e, this.mc.field_71439_g });
/*     */               
/* 323 */               ScopeUtils.needRenderHand1 = true;
/*     */             } 
/*     */           } else {
/* 326 */             customRenderers[type.id].renderItem(CustomItemRenderType.EQUIPPED_FIRST_PERSON, hand, stack, new Object[] { this.mc.field_71441_e, this.mc.field_71439_g });
/*     */           } 
/*     */           
/* 329 */           GlStateManager.func_179121_F();
/*     */           
/* 331 */           GlStateManager.func_179121_F();
/*     */         } 
/*     */         
/* 334 */         GlStateManager.func_179128_n(5889);
/* 335 */         GlStateManager.func_179121_F();
/* 336 */         GlStateManager.func_179128_n(5888);
/* 337 */         GlStateManager.func_179121_F();
/*     */         
/* 339 */         GL11.glDepthRange(0.0D, 1.0D);
/* 340 */         if (this.mc.field_71474_y.field_74320_O == 0 && !flag && 
/* 341 */           !ScopeUtils.isIndsideGunRendering) {
/* 342 */           itemRenderer.func_78447_b(partialTicks);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 348 */     return result;
/*     */   }
/*     */   
/*     */   public void renderInsideGun(ItemStack stack, EnumHand hand, float partialTicksTime, float fov) {
/* 352 */     if (ScopeUtils.isIndsideGunRendering) {
/*     */       return;
/*     */     }
/* 355 */     if (!ScopeUtils.isRenderHand0 && OptifineHelper.isShadersEnabled()) {
/*     */       return;
/*     */     }
/* 358 */     ScopeUtils.isIndsideGunRendering = true;
/*     */     
/* 360 */     int tex = ClientProxy.scopeUtils.blurFramebuffer.field_147617_g;
/* 361 */     ClientProxy.scopeUtils.blurFramebuffer.func_147610_a(false);
/* 362 */     GL30.glFramebufferTexture2D(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, ScopeUtils.INSIDE_GUN_TEX, 0);
/* 363 */     GlStateManager.func_179082_a(0.0F, 0.0F, 0.0F, 0.0F);
/* 364 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 365 */     GlStateManager.func_179135_a(true, true, true, true);
/* 366 */     GlStateManager.func_179132_a(true);
/* 367 */     GlStateManager.func_179086_m(256);
/* 368 */     copyDepthBuffer();
/* 369 */     ClientProxy.scopeUtils.blurFramebuffer.func_147610_a(false);
/* 370 */     GlStateManager.func_179086_m(16384);
/* 371 */     renderHeldItem(stack, hand, partialTicksTime, fov);
/* 372 */     ClientProxy.scopeUtils.blurFramebuffer.func_147610_a(false);
/* 373 */     GL30.glFramebufferTexture2D(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, tex, 0);
/* 374 */     ClientProxy.scopeUtils.blurFramebuffer.func_147614_f();
/*     */     
/* 376 */     OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, OptifineHelper.getDrawFrameBuffer());
/* 377 */     ScopeUtils.isIndsideGunRendering = false;
/*     */   }
/*     */   
/*     */   public void copyDepthBuffer() {
/* 381 */     Minecraft mc = Minecraft.func_71410_x();
/* 382 */     GL30.glBindFramebuffer(36008, OptifineHelper.getDrawFrameBuffer());
/* 383 */     GL30.glBindFramebuffer(36009, ClientProxy.scopeUtils.blurFramebuffer.field_147616_f);
/* 384 */     GlStateManager.func_179135_a(false, false, false, false);
/* 385 */     GL30.glBlitFramebuffer(0, 0, mc.field_71443_c, mc.field_71440_d, 0, 0, mc.field_71443_c, mc.field_71440_d, 256, 9728);
/* 386 */     GlStateManager.func_179135_a(true, true, true, true);
/* 387 */     GL30.glBindFramebuffer(36008, 0);
/* 388 */     GL30.glBindFramebuffer(36009, 0);
/*     */   }
/*     */   
/*     */   public void SetPartialTick(float dT) {
/* 392 */     this.partialTicks = dT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void hidePlayerModel(AbstractClientPlayer clientPlayer, RenderPlayer renderplayer) {
/* 397 */     if (ModConfig.INSTANCE.client.hideSecondSkinWhenDressed) {
/* 398 */       if (clientPlayer.func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b()) {
/* 399 */         (renderplayer.func_177087_b()).field_178720_f.field_78807_k = false;
/*     */       } else {
/* 401 */         (renderplayer.func_177087_b()).field_178720_f.field_78807_k = true;
/*     */       } 
/* 403 */       if (clientPlayer.func_184582_a(EntityEquipmentSlot.CHEST).func_190926_b()) {
/* 404 */         (renderplayer.func_177087_b()).field_178734_a.field_78807_k = false;
/* 405 */         (renderplayer.func_177087_b()).field_178732_b.field_78807_k = false;
/* 406 */         (renderplayer.func_177087_b()).field_178730_v.field_78807_k = false;
/*     */       } else {
/* 408 */         (renderplayer.func_177087_b()).field_178734_a.field_78807_k = true;
/* 409 */         (renderplayer.func_177087_b()).field_178732_b.field_78807_k = true;
/* 410 */         (renderplayer.func_177087_b()).field_178730_v.field_78807_k = true;
/*     */       } 
/* 412 */       if (clientPlayer.func_184582_a(EntityEquipmentSlot.LEGS).func_190926_b()) {
/* 413 */         (renderplayer.func_177087_b()).field_178733_c.field_78807_k = false;
/* 414 */         (renderplayer.func_177087_b()).field_178731_d.field_78807_k = false;
/*     */       } else {
/* 416 */         (renderplayer.func_177087_b()).field_178733_c.field_78807_k = true;
/* 417 */         (renderplayer.func_177087_b()).field_178731_d.field_78807_k = true;
/*     */       } 
/*     */     } 
/*     */     
/* 421 */     (renderplayer.func_177087_b()).field_78116_c.field_78807_k = false;
/* 422 */     (renderplayer.func_177087_b()).field_78115_e.field_78807_k = false;
/* 423 */     (renderplayer.func_177087_b()).field_178724_i.field_78807_k = false;
/* 424 */     (renderplayer.func_177087_b()).field_178723_h.field_78807_k = false;
/* 425 */     (renderplayer.func_177087_b()).field_178722_k.field_78807_k = false;
/* 426 */     (renderplayer.func_177087_b()).field_178721_j.field_78807_k = false;
/* 427 */     (renderplayer.func_177087_b()).field_78116_c.field_78806_j = true;
/* 428 */     (renderplayer.func_177087_b()).field_78115_e.field_78806_j = true;
/* 429 */     (renderplayer.func_177087_b()).field_178724_i.field_78806_j = true;
/* 430 */     (renderplayer.func_177087_b()).field_178723_h.field_78806_j = true;
/* 431 */     (renderplayer.func_177087_b()).field_178722_k.field_78806_j = true;
/* 432 */     (renderplayer.func_177087_b()).field_178721_j.field_78806_j = true;
/* 433 */     clientPlayer.func_184193_aE().forEach(stack -> {
/*     */           ArmorType type = null;
/*     */           if (stack.func_77973_b() instanceof ItemMWArmor) {
/*     */             type = ((ItemMWArmor)stack.func_77973_b()).type;
/*     */           }
/*     */           if (stack.func_77973_b() instanceof ItemSpecialArmor) {
/*     */             type = ((ItemSpecialArmor)stack.func_77973_b()).type;
/*     */           }
/*     */           if (type != null) {
/*     */             ArmorRenderConfig config = ((ModelCustomArmor)type.bipedModel).config;
/*     */             if (config.extra.hidePlayerModel) {
/*     */               boolean hide = true;
/*     */               if (config.extra.isSuit) {
/*     */                 (renderplayer.func_177087_b()).field_78116_c.field_78807_k = true;
/*     */                 (renderplayer.func_177087_b()).field_78115_e.field_78807_k = true;
/*     */                 (renderplayer.func_177087_b()).field_178724_i.field_78807_k = true;
/*     */                 (renderplayer.func_177087_b()).field_178723_h.field_78807_k = true;
/*     */                 (renderplayer.func_177087_b()).field_178722_k.field_78807_k = true;
/*     */                 (renderplayer.func_177087_b()).field_178721_j.field_78807_k = true;
/*     */               } else {
/*     */                 switch (((ItemArmor)stack.func_77973_b()).field_77881_a) {
/*     */                   case HEAD:
/*     */                     (renderplayer.func_177087_b()).field_78116_c.field_78807_k = hide;
/*     */                     break;
/*     */                   case CHEST:
/*     */                     (renderplayer.func_177087_b()).field_78115_e.field_78807_k = hide;
/*     */                     (renderplayer.func_177087_b()).field_178724_i.field_78807_k = hide;
/*     */                     (renderplayer.func_177087_b()).field_178723_h.field_78807_k = hide;
/*     */                     break;
/*     */                   case LEGS:
/*     */                     (renderplayer.func_177087_b()).field_178722_k.field_78807_k = hide;
/*     */                     (renderplayer.func_177087_b()).field_178721_j.field_78807_k = hide;
/*     */                     break;
/*     */                   case FEET:
/*     */                     (renderplayer.func_177087_b()).field_178722_k.field_78807_k = hide;
/*     */                     (renderplayer.func_177087_b()).field_178721_j.field_78807_k = hide;
/*     */                     break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             if (config.extra.hideAllPlayerWearModel) {
/*     */               (renderplayer.func_177087_b()).field_178720_f.field_78807_k = true;
/*     */               (renderplayer.func_177087_b()).field_178734_a.field_78807_k = true;
/*     */               (renderplayer.func_177087_b()).field_178732_b.field_78807_k = true;
/*     */               (renderplayer.func_177087_b()).field_178730_v.field_78807_k = true;
/*     */               (renderplayer.func_177087_b()).field_178733_c.field_78807_k = true;
/*     */               (renderplayer.func_177087_b()).field_178731_d.field_78807_k = true;
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderThirdPose(RenderLivingEvent.Pre event) {
/* 489 */     if (!(event.getEntity() instanceof AbstractClientPlayer)) {
/*     */       return;
/*     */     }
/* 492 */     AbstractClientPlayer clientPlayer = (AbstractClientPlayer)event.getEntity();
/* 493 */     Render<AbstractClientPlayer> render = Minecraft.func_71410_x().func_175598_ae().func_78713_a((Entity)event.getEntity());
/* 494 */     RenderPlayer renderplayer = (RenderPlayer)render;
/*     */ 
/*     */     
/* 497 */     hidePlayerModel(clientPlayer, renderplayer);
/*     */ 
/*     */ 
/*     */     
/* 501 */     ItemStack itemstack = event.getEntity().func_184614_ca();
/* 502 */     if (itemstack != ItemStack.field_190927_a && !itemstack.func_190926_b()) {
/* 503 */       if (!(itemstack.func_77973_b() instanceof BaseItem)) {
/*     */         return;
/*     */       }
/* 506 */       BaseType type = ((BaseItem)itemstack.func_77973_b()).baseType;
/* 507 */       if (!type.hasModel()) {
/*     */         return;
/*     */       }
/* 510 */       if (itemstack.func_77973_b() instanceof ItemAttachment) {
/*     */         return;
/*     */       }
/* 513 */       if (itemstack.func_77973_b() instanceof com.modularwarfare.common.backpacks.ItemBackpack) {
/*     */         return;
/*     */       }
/*     */       
/* 517 */       ModelBiped biped = (ModelBiped)event.getRenderer().func_177087_b();
/* 518 */       EntityLivingBase entityLivingBase = event.getEntity();
/* 519 */       if (type.id == 1 && entityLivingBase instanceof EntityPlayer) {
/* 520 */         if (AnimationUtils.isAiming.containsKey(((EntityPlayer)entityLivingBase).func_70005_c_())) {
/* 521 */           biped.field_187076_m = ModelBiped.ArmPose.BOW_AND_ARROW;
/*     */         } else {
/* 523 */           biped.field_187076_m = ModelBiped.ArmPose.BLOCK;
/* 524 */           biped.field_187075_l = ModelBiped.ArmPose.BLOCK;
/*     */         } 
/*     */       } else {
/* 527 */         biped.field_187076_m = ModelBiped.ArmPose.BLOCK;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderHand(RenderHandFisrtPersonEvent.Pre event) {
/* 534 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/* 535 */     entityPlayerSP.func_184193_aE().forEach(stack -> {
/*     */           if (event.isCanceled()) {
/*     */             return;
/*     */           }
/*     */           ArmorType type = null;
/*     */           if (stack.func_77973_b() instanceof ItemMWArmor) {
/*     */             type = ((ItemMWArmor)stack.func_77973_b()).type;
/*     */           }
/*     */           if (stack.func_77973_b() instanceof ItemSpecialArmor) {
/*     */             type = ((ItemSpecialArmor)stack.func_77973_b()).type;
/*     */           }
/*     */           if (type != null) {
/*     */             ArmorRenderConfig config = type.renderConfig;
/*     */             if (config != null && config.extra.hidePlayerModel) {
/*     */               if (config.extra.isSuit) {
/*     */                 event.setCanceled(true);
/*     */               } else if (((ItemArmor)stack.func_77973_b()).field_77881_a == EntityEquipmentSlot.CHEST) {
/*     */                 event.setCanceled(true);
/*     */               } 
/*     */             }
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float getFOVModifier(float partialTicks) {
/* 562 */     Entity entity = this.mc.func_175606_aa();
/* 563 */     float f1 = 70.0F;
/*     */     
/* 565 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_110143_aJ() <= 0.0F) {
/* 566 */       float f2 = ((EntityLivingBase)entity).field_70725_aQ + partialTicks;
/* 567 */       f1 /= (1.0F - 500.0F / (f2 + 500.0F)) * 2.0F + 1.0F;
/*     */     } 
/*     */     
/* 570 */     IBlockState state = ActiveRenderInfo.func_186703_a((World)this.mc.field_71441_e, entity, partialTicks);
/*     */     
/* 572 */     if (state.func_185904_a() == Material.field_151586_h) {
/* 573 */       f1 = f1 * 60.0F / 70.0F;
/*     */     }
/* 575 */     return f1;
/*     */   }
/*     */ 
/*     */   
/*     */   private float interpolateRotation(float x, float y, float dT) {
/*     */     float f3;
/* 581 */     for (f3 = y - x; f3 < -180.0F; f3 += 360.0F);
/*     */     
/* 583 */     for (; f3 >= 180.0F; f3 -= 360.0F);
/*     */ 
/*     */     
/* 586 */     return x + dT * f3;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\ClientRenderHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */