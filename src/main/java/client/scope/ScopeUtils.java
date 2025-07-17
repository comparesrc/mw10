/*     */ package com.modularwarfare.client.scope;
/*     */ 
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.client.ClientProxy;
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import com.modularwarfare.client.fpp.basic.configs.AttachmentRenderConfig;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderParameters;
/*     */ import com.modularwarfare.client.fpp.enhanced.renderers.RenderGunEnhanced;
/*     */ import com.modularwarfare.client.model.ModelAttachment;
/*     */ import com.modularwarfare.client.shader.Programs;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.WeaponAnimationType;
/*     */ import com.modularwarfare.mixin.client.accessor.IShaderGroup;
/*     */ import com.modularwarfare.utility.OptifineHelper;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.IReloadableResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.client.shader.Shader;
/*     */ import net.minecraft.client.shader.ShaderGroup;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraftforge.client.event.EntityViewRenderEvent;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import net.optifine.shaders.MWFOptifineShadesHelper;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.ContextCapabilities;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ import org.lwjgl.opengl.GL43;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ 
/*     */ 
/*     */ public class ScopeUtils
/*     */ {
/*     */   public static int MIRROR_TEX;
/*     */   public static int OVERLAY_TEX;
/*     */   public static int INSIDE_GUN_TEX;
/*  64 */   public static ResourceLocation NOT_COMPATIBLE = new ResourceLocation("modularwarfare", "textures/gui/notcompatible.png"); public static int DEPTH_TEX; public static int DEPTH_ERASE_TEX; public static int SCOPE_MASK_TEX; public static int SCOPE_LIGHTMAP_TEX;
/*  65 */   public static ResourceLocation SCOPE_BACK = new ResourceLocation("modularwarfare", "textures/skins/scope_back.png");
/*  66 */   private static Minecraft mc = Minecraft.func_71410_x();
/*     */   
/*     */   private ScopeRenderGlobal scopeRenderGlobal;
/*     */   
/*     */   public static boolean isRenderHand0 = false;
/*     */   
/*     */   public static boolean needRenderHand1 = false;
/*     */   
/*     */   public boolean hasBeenReseted = true;
/*     */   public float mouseSensitivityBackup;
/*     */   private Field renderEndNanoTime;
/*     */   public ShaderGroup blurShader;
/*     */   public Framebuffer blurFramebuffer;
/*     */   private static int lastScale;
/*     */   private static int lastScaleWidth;
/*     */   private static int lastScaleHeight;
/*     */   private static int lastWidth;
/*     */   private static int lastHeight;
/*     */   private static boolean lastShadersEnabled;
/*     */   private static int lastGbuffersFormat0;
/*     */   public static boolean isIndsideGunRendering = false;
/*     */   
/*     */   public ScopeUtils() {
/*  89 */     this.scopeRenderGlobal = new ScopeRenderGlobal(mc);
/*  90 */     ((IReloadableResourceManager)mc.func_110442_L()).func_110542_a((IResourceManagerReloadListener)this.scopeRenderGlobal);
/*     */     try {
/*  92 */       this.renderEndNanoTime = EntityRenderer.class.getDeclaredField("renderEndNanoTime");
/*  93 */     } catch (Exception exception) {}
/*     */     
/*  95 */     if (this.renderEndNanoTime == null) {
/*  96 */       try { this.renderEndNanoTime = EntityRenderer.class.getDeclaredField("field_78534_ac"); }
/*  97 */       catch (Exception exception) {}
/*     */     }
/*  99 */     if (this.renderEndNanoTime != null) {
/* 100 */       this.renderEndNanoTime.setAccessible(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderTick(TickEvent.RenderTickEvent event) {
/* 107 */     if (event.phase == TickEvent.Phase.START)
/*     */     {
/* 109 */       if (mc.field_71439_g != null && mc.field_71462_r == null)
/*     */       {
/* 111 */         if (mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun && mc.field_71474_y.field_74320_O == 0 && 
/* 112 */           GunType.getAttachment(mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Sight) != null) {
/* 113 */           ItemAttachment itemAttachment = (ItemAttachment)GunType.getAttachment(mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Sight).func_77973_b();
/* 114 */           if (itemAttachment != null && 
/* 115 */             itemAttachment.type != null && 
/* 116 */             itemAttachment.type.sight.modeType.isMirror) {
/* 117 */             if (OVERLAY_TEX == -1 || lastWidth != mc.field_71443_c || lastHeight != mc.field_71440_d) {
/* 118 */               GL11.glPushMatrix();
/* 119 */               if (OVERLAY_TEX != -1) {
/* 120 */                 GL11.glDeleteTextures(OVERLAY_TEX);
/*     */               }
/* 122 */               OVERLAY_TEX = GL11.glGenTextures();
/* 123 */               GL11.glBindTexture(3553, OVERLAY_TEX);
/* 124 */               GL11.glTexImage2D(3553, 0, 32856, mc.field_71443_c, mc.field_71440_d, 0, 6408, 5121, (ByteBuffer)null);
/* 125 */               GL11.glTexParameteri(3553, 10240, 9728);
/* 126 */               GL11.glTexParameteri(3553, 10241, 9728);
/* 127 */               GL11.glTexParameteri(3553, 10242, 10497);
/* 128 */               GL11.glTexParameteri(3553, 10243, 10497);
/*     */               
/* 130 */               if (INSIDE_GUN_TEX != -1) {
/* 131 */                 GL11.glDeleteTextures(INSIDE_GUN_TEX);
/*     */               }
/* 133 */               INSIDE_GUN_TEX = GL11.glGenTextures();
/* 134 */               GL11.glBindTexture(3553, INSIDE_GUN_TEX);
/* 135 */               GL11.glTexImage2D(3553, 0, 32856, mc.field_71443_c, mc.field_71440_d, 0, 6408, 5121, (ByteBuffer)null);
/* 136 */               GL11.glTexParameteri(3553, 10240, 9728);
/* 137 */               GL11.glTexParameteri(3553, 10241, 9728);
/* 138 */               GL11.glTexParameteri(3553, 10242, 10496);
/* 139 */               GL11.glTexParameteri(3553, 10243, 10496);
/*     */               
/* 141 */               if (SCOPE_MASK_TEX != -1) {
/* 142 */                 GL11.glDeleteTextures(SCOPE_MASK_TEX);
/*     */               }
/* 144 */               SCOPE_MASK_TEX = GL11.glGenTextures();
/* 145 */               GL11.glBindTexture(3553, SCOPE_MASK_TEX);
/* 146 */               GL11.glTexImage2D(3553, 0, 32856, mc.field_71443_c, mc.field_71440_d, 0, 6408, 5121, (ByteBuffer)null);
/* 147 */               GL11.glTexParameteri(3553, 10240, 9728);
/* 148 */               GL11.glTexParameteri(3553, 10241, 9728);
/* 149 */               GL11.glTexParameteri(3553, 10242, 10497);
/* 150 */               GL11.glTexParameteri(3553, 10243, 10497);
/*     */               
/* 152 */               if (SCOPE_LIGHTMAP_TEX != -1) {
/* 153 */                 GL11.glDeleteTextures(SCOPE_LIGHTMAP_TEX);
/*     */               }
/* 155 */               SCOPE_LIGHTMAP_TEX = GL11.glGenTextures();
/* 156 */               GL11.glBindTexture(3553, SCOPE_LIGHTMAP_TEX);
/* 157 */               GL11.glTexImage2D(3553, 0, 32856, mc.field_71443_c, mc.field_71440_d, 0, 6408, 5121, (ByteBuffer)null);
/* 158 */               GL11.glTexParameteri(3553, 10240, 9728);
/* 159 */               GL11.glTexParameteri(3553, 10241, 9728);
/* 160 */               GL11.glTexParameteri(3553, 10242, 10497);
/* 161 */               GL11.glTexParameteri(3553, 10243, 10497);
/*     */               
/* 163 */               lastWidth = mc.field_71443_c;
/* 164 */               lastHeight = mc.field_71440_d;
/* 165 */               GL11.glPopMatrix();
/*     */             } 
/*     */             
/* 168 */             if (itemAttachment.type.sight.modeType.isPIP && (
/* 169 */               ModConfig.INSTANCE.hud.alwaysRenderPIPWorld || RenderParameters.adsSwitch != 0.0F))
/*     */             {
/* 171 */               renderWorld(mc, itemAttachment, event.renderTickTime);
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
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onRenderWorldLast(RenderWorldLastEvent event) {
/* 187 */     isRenderHand0 = false;
/*     */   }
/*     */   
/*     */   public void onPreRenderHand0() {
/* 191 */     isRenderHand0 = true;
/* 192 */     if (this.blurFramebuffer != null) {
/* 193 */       copyEraseDepthBuffer();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPreRenderHand1() {
/* 201 */     if (needRenderHand1) {
/* 202 */       needRenderHand1 = false;
/* 203 */       Shaders.setHandsRendered(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void genMirror() {
/* 208 */     boolean skip = true;
/* 209 */     if (ScopeUtils.mc.field_71439_g.func_184614_ca() != null && ScopeUtils.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemGun && RenderParameters.adsSwitch != 0.0F && ScopeUtils.mc.field_71474_y.field_74320_O == 0)
/*     */     {
/* 211 */       if (GunType.getAttachment(ScopeUtils.mc.field_71439_g.func_184614_ca(), AttachmentPresetEnum.Sight) != null) {
/*     */         
/* 213 */         ItemAttachment itemAttachment = (ItemAttachment)GunType.getAttachment(ScopeUtils.mc.field_71439_g.func_184614_ca(), AttachmentPresetEnum.Sight).func_77973_b();
/* 214 */         if (itemAttachment != null && 
/* 215 */           itemAttachment.type != null) {
/* 216 */           skip = false;
/* 217 */           if (itemAttachment.type.sight.modeType.isPIP) {
/* 218 */             skip = true;
/*     */           }
/* 220 */           if (!itemAttachment.type.sight.modeType.isMirror) {
/* 221 */             skip = true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 227 */     if (OptifineHelper.isShadersEnabled() && 
/* 228 */       Shaders.isShadowPass) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 234 */     if (skip) {
/*     */       return;
/*     */     }
/*     */     
/* 238 */     initBlur();
/*     */     
/* 240 */     Minecraft mc = Minecraft.func_71410_x();
/*     */     
/* 242 */     if (OptifineHelper.isShadersEnabled()) {
/* 243 */       Shaders.renderCompositeFinal();
/* 244 */       GL43.glCopyImageSubData((Minecraft.func_71410_x().func_147110_a()).field_147617_g, 3553, 0, 0, 0, 0, MIRROR_TEX, 3553, 0, 0, 0, 0, lastWidth, lastHeight, 1);
/* 245 */       Shaders.isCompositeRendered = false;
/* 246 */       Shaders.isRenderingWorld = true;
/* 247 */       Shaders.isRenderingDfb = true;
/* 248 */       OptifineHelper.bindGbuffersTextures();
/* 249 */       OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, MWFOptifineShadesHelper.getDFB());
/* 250 */       Shaders.setDrawBuffers(MWFOptifineShadesHelper.getDFBDrawBuffers());
/* 251 */       for (int i = 0; i < MWFOptifineShadesHelper.getUsedColorBuffers(); i++)
/* 252 */         OpenGlHelper.func_153188_a(36160, 36064 + i, 3553, MWFOptifineShadesHelper.getFlipTextures().getA(i), 0); 
/* 253 */       GlStateManager.func_179138_g(33984);
/*     */     } else {
/* 255 */       int tex = this.blurFramebuffer.field_147616_f;
/* 256 */       this.blurFramebuffer.func_147610_a(false);
/* 257 */       OpenGlHelper.func_153188_a(36160, 36064, 3553, MIRROR_TEX, 0);
/* 258 */       OpenGlHelper.func_153171_g(36008, (mc.func_147110_a()).field_147616_f);
/* 259 */       OpenGlHelper.func_153171_g(36009, this.blurFramebuffer.field_147616_f);
/* 260 */       GL30.glBlitFramebuffer(0, 0, lastWidth, lastHeight, 0, 0, lastWidth, lastHeight, 16384, 9728);
/* 261 */       this.blurFramebuffer.func_147610_a(false);
/* 262 */       OpenGlHelper.func_153188_a(36160, 36064, 3553, tex, 0);
/* 263 */       mc.func_147110_a().func_147610_a(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onFovMod(EntityViewRenderEvent.FOVModifier event) {
/* 269 */     if (mc.field_71439_g.func_184614_ca() != null && mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemGun && RenderParameters.adsSwitch != 0.0F && mc.field_71474_y.field_74320_O == 0 && 
/* 270 */       GunType.getAttachment(mc.field_71439_g.func_184614_ca(), AttachmentPresetEnum.Sight) != null) {
/* 271 */       ItemAttachment itemAttachment = (ItemAttachment)GunType.getAttachment(mc.field_71439_g.func_184614_ca(), AttachmentPresetEnum.Sight).func_77973_b();
/* 272 */       if (itemAttachment != null && 
/* 273 */         itemAttachment.type != null && 
/* 274 */         !itemAttachment.type.sight.modeType.isPIP) {
/* 275 */         float dst = getFov(itemAttachment);
/* 276 */         if (ModConfig.INSTANCE.hud.isDynamicFov) {
/* 277 */           dst += event.getFOV() - mc.field_71474_y.field_74334_X;
/*     */         }
/* 279 */         float src = event.getFOV();
/* 280 */         event.setFOV(Math.max(1.0F, src + (dst - src) * RenderParameters.adsSwitch));
/* 281 */         if (RenderParameters.adsSwitch != 0.0F && RenderParameters.adsSwitch != 1.0F)
/*     */         {
/* 283 */           mc.field_71438_f.func_174979_m();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onRenderHUD(RenderGameOverlayEvent.Pre event) {
/* 294 */     if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
/*     */       return;
/*     */     }
/* 297 */     ItemStack stack = (Minecraft.func_71410_x()).field_71439_g.func_184614_ca();
/* 298 */     if (stack != null && stack.func_77973_b() instanceof ItemGun && 
/* 299 */       GunType.getAttachment(mc.field_71439_g.func_184614_ca(), AttachmentPresetEnum.Sight) != null) {
/* 300 */       ItemAttachment itemAttachment = (ItemAttachment)GunType.getAttachment(mc.field_71439_g.func_184614_ca(), AttachmentPresetEnum.Sight).func_77973_b();
/* 301 */       if (itemAttachment.type.sight.modeType.isPIP) {
/* 302 */         renderPostScope(event.getPartialTicks(), false, true, true, 1.0F);
/* 303 */         GlStateManager.func_179126_j();
/* 304 */         GlStateManager.func_179118_c();
/* 305 */         GlStateManager.func_179147_l();
/* 306 */         GlStateManager.func_179086_m(256);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderPostScope(float renderTickTime, boolean isDepthMode, boolean isInsideRendering, boolean isOverlayRendering, float alpha) {
/* 313 */     if (RenderParameters.adsSwitch > 0.0F) {
/* 314 */       ScaledResolution resolution = new ScaledResolution(mc);
/* 315 */       ItemAttachment attachment = null;
/*     */       
/* 317 */       if (GunType.getAttachment(mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Sight) != null) {
/* 318 */         attachment = (ItemAttachment)GunType.getAttachment(mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Sight).func_77973_b();
/*     */       }
/*     */       
/* 321 */       if (attachment == null || attachment == Items.field_190931_a) {
/*     */         return;
/*     */       }
/* 324 */       if (!attachment.type.sight.modeType.isMirror) {
/*     */         return;
/*     */       }
/*     */       
/* 328 */       if (!attachment.type.sight.modeType.isPIP || RenderGunEnhanced.debug1) {
/* 329 */         if (OptifineHelper.isRenderingDfb()) {
/*     */           
/* 331 */           GL43.glCopyImageSubData(MWFOptifineShadesHelper.getFlipTextures().getA(ModConfig.INSTANCE.hud.shadersColorTexID), 3553, 0, 0, 0, 0, MIRROR_TEX, 3553, 0, 0, 0, 0, lastWidth, lastHeight, 1);
/*     */         } else {
/* 333 */           ContextCapabilities contextCapabilities = GLContext.getCapabilities();
/* 334 */           if (contextCapabilities.OpenGL43) {
/* 335 */             GL43.glCopyImageSubData((mc.func_147110_a()).field_147617_g, 3553, 0, 0, 0, 0, MIRROR_TEX, 3553, 0, 0, 0, 0, lastWidth, lastHeight, 1);
/*     */           } else {
/*     */             
/* 338 */             GL11.glBindTexture(3553, MIRROR_TEX);
/* 339 */             GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, mc.field_71443_c, mc.field_71440_d);
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 345 */       boolean needBlur = false;
/* 346 */       if (ModConfig.INSTANCE.hud.ads_blur && 
/* 347 */         attachment != null && 
/* 348 */         attachment.type != null && 
/* 349 */         attachment.type.sight.modeType.isMirror) {
/* 350 */         needBlur = true;
/* 351 */         ClientProxy.scopeUtils.renderBlur();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 357 */       if (OptifineHelper.isShadersEnabled()) {
/* 358 */         Shaders.pushProgram();
/* 359 */         Shaders.useProgram(Shaders.ProgramNone);
/*     */       } 
/* 361 */       GlStateManager.func_179147_l();
/*     */       
/* 363 */       AttachmentRenderConfig.Sight config = ((ModelAttachment)attachment.type.model).config.sight;
/*     */       
/* 365 */       GlStateManager.func_179094_E();
/* 366 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 367 */       setupOverlayRendering();
/* 368 */       GlStateManager.func_179097_i();
/* 369 */       GlStateManager.func_179141_d();
/* 370 */       GlStateManager.func_179092_a(519, 0.0F);
/*     */       
/* 372 */       ClientProxy.scopeUtils.blurFramebuffer.func_147610_a(false);
/* 373 */       GlStateManager.func_179151_a(1.0D);
/* 374 */       GlStateManager.func_179086_m(256);
/* 375 */       GlStateManager.func_179151_a(1.0D);
/* 376 */       GL11.glDepthRange(0.0D, 0.0D);
/* 377 */       GlStateManager.func_179132_a(false);
/* 378 */       GL20.glUseProgram(Programs.scopeBorderProgram);
/* 379 */       GL20.glUniform2f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "size"), mc.field_71443_c, mc.field_71440_d);
/* 380 */       GL20.glUniform1f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "maskRange"), config.uniformMaskRange);
/* 381 */       GL20.glUniform1f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "drawRange"), config.uniformDrawRange);
/* 382 */       GL20.glUniform1f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "strength"), config.uniformStrength);
/* 383 */       GL20.glUniform1f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "scaleRangeY"), config.uniformScaleRangeY);
/* 384 */       GL20.glUniform1f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "scaleStrengthY"), config.uniformScaleStrengthY);
/* 385 */       GL20.glUniform1f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "verticality"), config.uniformVerticality);
/*     */       
/* 387 */       GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 388 */       GlStateManager.func_179135_a(true, true, true, false);
/* 389 */       GlStateManager.func_179144_i(MIRROR_TEX);
/* 390 */       ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/* 391 */       GlStateManager.func_179132_a(true);
/*     */       
/* 393 */       if (isDepthMode) {
/* 394 */         GlStateManager.func_179126_j();
/* 395 */         GlStateManager.func_179092_a(518, 1.0F);
/*     */       } else {
/* 397 */         GlStateManager.func_179092_a(516, 0.0F);
/*     */       } 
/*     */       
/* 400 */       if (mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun && 
/* 401 */         ((ItemGun)mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b()).type.animationType == WeaponAnimationType.ENHANCED) {
/* 402 */         GlStateManager.func_179144_i(INSIDE_GUN_TEX);
/* 403 */         ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/*     */       } 
/*     */ 
/*     */       
/* 407 */       GL20.glUseProgram(Programs.normalProgram);
/* 408 */       GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 409 */       GlStateManager.func_179094_E();
/* 410 */       float width = config.maskSize * resolution.func_78328_b();
/* 411 */       float height = config.maskSize * resolution.func_78328_b();
/* 412 */       GlStateManager.func_179109_b(resolution.func_78326_a() / 2.0F, resolution.func_78328_b() / 2.0F, 0.0F);
/* 413 */       GlStateManager.func_179114_b(RenderParameters.CROSS_ROTATE, 0.0F, 0.0F, 1.0F);
/* 414 */       GlStateManager.func_179109_b(-width / 2.0F, -height / 2.0F, 0.0F);
/* 415 */       ClientProxy.gunStaticRenderer.bindTexture("mask", config.maskTexture);
/* 416 */       ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, (int)width, (int)height, 1.0F, 1.0F);
/* 417 */       GlStateManager.func_179121_F();
/*     */       
/* 419 */       GlStateManager.func_179135_a(true, true, true, true);
/* 420 */       GlStateManager.func_179097_i();
/* 421 */       GlStateManager.func_179132_a(false);
/* 422 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 424 */       OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, OptifineHelper.getDrawFrameBuffer());
/*     */       
/* 426 */       GlStateManager.func_179092_a(518, 1.0F);
/* 427 */       GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*     */       
/* 429 */       if (isInsideRendering) {
/* 430 */         ClientProxy.scopeUtils.blurFramebuffer.func_147612_c();
/* 431 */         ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 434 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, alpha);
/* 435 */       if (isOverlayRendering) {
/* 436 */         if (isDepthMode) {
/* 437 */           this.blurFramebuffer.func_147610_a(false);
/*     */           
/* 439 */           GlStateManager.func_179126_j();
/* 440 */           GlStateManager.func_179132_a(true);
/* 441 */           GlStateManager.func_179092_a(518, 1.0F);
/*     */           
/* 443 */           GlStateManager.func_179135_a(false, false, false, false);
/* 444 */           GlStateManager.func_179144_i(OVERLAY_TEX);
/* 445 */           ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/* 446 */           GlStateManager.func_179135_a(true, true, true, true);
/* 447 */           GlStateManager.func_179132_a(false);
/* 448 */           GlStateManager.func_179097_i();
/*     */           
/* 450 */           OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, OptifineHelper.getDrawFrameBuffer());
/*     */         } else {
/* 452 */           GlStateManager.func_179092_a(516, 0.001F);
/*     */           
/* 454 */           GL20.glUseProgram(Programs.overlayProgram);
/* 455 */           GL20.glUniform2f(GL20.glGetUniformLocation(Programs.overlayProgram, "size"), mc.field_71443_c, mc.field_71440_d);
/* 456 */           GlStateManager.func_179138_g(33987);
/* 457 */           int tex3 = GlStateManager.func_187397_v(32873);
/* 458 */           GlStateManager.func_179144_i(this.blurFramebuffer.field_147617_g);
/* 459 */           GlStateManager.func_179138_g(33988);
/* 460 */           int tex4 = GlStateManager.func_187397_v(32873);
/* 461 */           GlStateManager.func_179144_i(SCOPE_LIGHTMAP_TEX);
/* 462 */           GlStateManager.func_179138_g(33984);
/* 463 */           GlStateManager.func_179144_i(OVERLAY_TEX);
/* 464 */           ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/* 465 */           GlStateManager.func_179138_g(33987);
/* 466 */           GlStateManager.func_179144_i(tex3);
/* 467 */           GlStateManager.func_179138_g(33988);
/* 468 */           GlStateManager.func_179144_i(tex4);
/* 469 */           GlStateManager.func_179138_g(33984);
/*     */         } 
/*     */       }
/*     */       
/* 473 */       GL20.glUseProgram(0);
/*     */       
/* 475 */       if (OptifineHelper.isShadersEnabled()) {
/* 476 */         Shaders.popProgram();
/*     */       }
/*     */       
/* 479 */       GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 480 */       GlStateManager.func_179126_j();
/* 481 */       GL11.glDepthRange(0.0D, 1.0D);
/* 482 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 483 */       GlStateManager.func_179143_c(515);
/* 484 */       GlStateManager.func_179135_a(true, true, true, true);
/* 485 */       GlStateManager.func_179132_a(true);
/* 486 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupOverlayRendering() {
/* 492 */     this; ScaledResolution scaledresolution = new ScaledResolution(mc);
/* 493 */     GlStateManager.func_179128_n(5889);
/* 494 */     GlStateManager.func_179096_D();
/* 495 */     GlStateManager.func_179130_a(0.0D, scaledresolution.func_78327_c(), scaledresolution.func_78324_d(), 0.0D, 1000.0D, 3000.0D);
/* 496 */     GlStateManager.func_179128_n(5888);
/* 497 */     GlStateManager.func_179096_D();
/* 498 */     GlStateManager.func_179109_b(0.0F, 0.0F, -2000.0F);
/*     */   }
/*     */   
/*     */   public void copyDepthBuffer() {
/* 502 */     Minecraft mc = Minecraft.func_71410_x();
/* 503 */     GL30.glBindFramebuffer(36008, OptifineHelper.getDrawFrameBuffer());
/* 504 */     GL30.glBindFramebuffer(36009, ClientProxy.scopeUtils.blurFramebuffer.field_147616_f);
/* 505 */     GlStateManager.func_179135_a(false, false, false, false);
/* 506 */     GL30.glBlitFramebuffer(0, 0, mc.field_71443_c, mc.field_71440_d, 0, 0, mc.field_71443_c, mc.field_71440_d, 256, 9728);
/* 507 */     GlStateManager.func_179135_a(true, true, true, true);
/* 508 */     GL30.glBindFramebuffer(36008, 0);
/* 509 */     GL30.glBindFramebuffer(36009, 0);
/*     */   }
/*     */   
/*     */   public void copyEraseDepthBuffer() {
/* 513 */     GL43.glCopyImageSubData(MWFOptifineShadesHelper.getDFBDepthTextures().get(0), 3553, 0, 0, 0, 0, DEPTH_ERASE_TEX, 3553, 0, 0, 0, 0, lastWidth, lastHeight, 1);
/*     */   }
/*     */   
/*     */   public void renderSunglassesPostProgram() {
/* 517 */     if (!OptifineHelper.isShadersEnabled()) {
/*     */       return;
/*     */     }
/* 520 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 521 */     Shaders.pushProgram();
/* 522 */     GlStateManager.func_179128_n(5889);
/* 523 */     GlStateManager.func_179094_E();
/* 524 */     GlStateManager.func_179128_n(5888);
/* 525 */     GlStateManager.func_179094_E();
/*     */     
/* 527 */     setupOverlayRendering();
/*     */     
/* 529 */     GL11.glPushAttrib(18688);
/*     */     
/* 531 */     GlStateManager.func_179092_a(516, 0.0F);
/* 532 */     GL11.glDepthRange(0.0D, 1.0D);
/*     */ 
/*     */     
/* 535 */     this; ScaledResolution resolution = new ScaledResolution(mc);
/* 536 */     GL20.glDrawBuffers(36065);
/*     */     
/* 538 */     GL20.glUseProgram(Programs.sunglassesProgram);
/*     */     
/* 540 */     GlStateManager.func_179084_k();
/* 541 */     GlStateManager.func_179138_g(33984);
/* 542 */     GlStateManager.func_179144_i(OVERLAY_TEX);
/* 543 */     ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/* 544 */     GlStateManager.func_179147_l();
/*     */     
/* 546 */     GL11.glPopAttrib();
/*     */     
/* 548 */     GL20.glUseProgram(0);
/*     */     
/* 550 */     GlStateManager.func_179128_n(5889);
/* 551 */     GlStateManager.func_179121_F();
/* 552 */     GlStateManager.func_179128_n(5888);
/* 553 */     GlStateManager.func_179121_F();
/* 554 */     Shaders.popProgram();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void clientTick(TickEvent.ClientTickEvent event) {
/* 559 */     switch (event.phase) {
/*     */       case START:
/* 561 */         if (ClientRenderHooks.isAimingScope) {
/* 562 */           if (mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun && RenderParameters.adsSwitch != 0.0F && mc.field_71474_y.field_74320_O == 0 && 
/* 563 */             GunType.getAttachment(mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Sight) != null) {
/* 564 */             ItemAttachment itemAttachment = (ItemAttachment)GunType.getAttachment(mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Sight).func_77973_b();
/* 565 */             if (itemAttachment != null && 
/* 566 */               itemAttachment.type != null) {
/* 567 */               mc.field_71474_y.field_74341_c = this.mouseSensitivityBackup * ((ModelAttachment)itemAttachment.type.model).config.sight.mouseSensitivityFactor;
/* 568 */               this.hasBeenReseted = false;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         } 
/* 573 */         if (!this.hasBeenReseted) {
/* 574 */           mc.field_71474_y.field_74341_c = this.mouseSensitivityBackup;
/*     */           
/* 576 */           this.hasBeenReseted = true; break;
/* 577 */         }  if (this.mouseSensitivityBackup != mc.field_71474_y.field_74341_c)
/* 578 */           this.mouseSensitivityBackup = mc.field_71474_y.field_74341_c; 
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float getFov(ItemAttachment itemAttachment) {
/* 584 */     return 50.0F / ((ModelAttachment)itemAttachment.type.model).config.sight.fovZoom;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWorld(Minecraft mc, ItemAttachment itemAttachment, float partialTick) {
/* 589 */     float zoom = getFov(itemAttachment);
/*     */     
/* 591 */     GL11.glPushMatrix();
/* 592 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 594 */     RenderGlobal renderBackup = mc.field_71438_f;
/*     */     
/* 596 */     long endTime = 0L;
/* 597 */     boolean hide = mc.field_71474_y.field_74319_N;
/* 598 */     int view = mc.field_71474_y.field_74320_O;
/* 599 */     int limit = mc.field_71474_y.field_74350_i;
/* 600 */     RayTraceResult mouseOver = mc.field_71476_x;
/* 601 */     float fov = mc.field_71474_y.field_74334_X;
/* 602 */     boolean bobbingBackup = mc.field_71474_y.field_74336_f;
/* 603 */     float mouseSensitivityBackup = mc.field_71474_y.field_74341_c;
/*     */     
/* 605 */     mc.field_71438_f = this.scopeRenderGlobal;
/*     */ 
/*     */     
/* 608 */     mc.field_71474_y.field_74319_N = true;
/* 609 */     mc.field_71474_y.field_74320_O = 0;
/* 610 */     mc.field_71474_y.field_74334_X = zoom;
/* 611 */     mc.field_71474_y.field_74336_f = false;
/*     */     
/* 613 */     if (mc.field_71474_y.field_74334_X < 0.0F) {
/* 614 */       mc.field_71474_y.field_74334_X = 1.0F;
/*     */     }
/*     */     
/* 617 */     if (limit != 0 && this.renderEndNanoTime != null) {
/*     */       try {
/* 619 */         endTime = this.renderEndNanoTime.getLong(mc.field_71460_t);
/* 620 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/* 624 */     int fps = Math.max(30, mc.field_71474_y.field_74350_i);
/*     */     
/* 626 */     OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, (Minecraft.func_71410_x().func_147110_a()).field_147616_f);
/*     */     
/* 628 */     int tex = (Minecraft.func_71410_x().func_147110_a()).field_147617_g;
/* 629 */     (Minecraft.func_71410_x().func_147110_a()).field_147617_g = MIRROR_TEX;
/* 630 */     GL30.glFramebufferTexture2D(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, MIRROR_TEX, 0);
/*     */     
/* 632 */     mc.field_71460_t.func_78471_a(partialTick, endTime);
/*     */     
/* 634 */     GL20.glUseProgram(0);
/*     */ 
/*     */     
/* 637 */     (Minecraft.func_71410_x().func_147110_a()).field_147617_g = tex;
/* 638 */     GL30.glFramebufferTexture2D(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, tex, 0);
/*     */ 
/*     */     
/* 641 */     if (limit != 0 && this.renderEndNanoTime != null) {
/*     */       try {
/* 643 */         this.renderEndNanoTime.setLong(mc.field_71460_t, endTime);
/* 644 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 650 */     mc.field_71476_x = mouseOver;
/* 651 */     mc.field_71474_y.field_74350_i = limit;
/* 652 */     mc.field_71474_y.field_74320_O = view;
/* 653 */     mc.field_71474_y.field_74319_N = hide;
/* 654 */     mc.field_71474_y.field_74336_f = bobbingBackup;
/* 655 */     mc.field_71474_y.field_74341_c = mouseSensitivityBackup;
/*     */     
/* 657 */     mc.field_71474_y.field_74334_X = fov;
/* 658 */     mc.field_71438_f = renderBackup;
/*     */ 
/*     */     
/* 661 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onWorldLoad(WorldEvent.Load event) {
/* 666 */     if ((event.getWorld()).field_72995_K) {
/* 667 */       this.scopeRenderGlobal.func_72732_a((WorldClient)event.getWorld());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScaledCustomSizeModalRectFlipY(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
/* 676 */     float f = 1.0F / tileWidth;
/* 677 */     float f1 = 1.0F / tileHeight;
/* 678 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 679 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 680 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 681 */     bufferbuilder.func_181662_b(x, (y + height), 0.0D)
/* 682 */       .func_187315_a((u * f), 1.0D - ((v + vHeight) * f1)).func_181675_d();
/* 683 */     bufferbuilder.func_181662_b((x + width), (y + height), 0.0D)
/* 684 */       .func_187315_a(((u + uWidth) * f), 1.0D - ((v + vHeight) * f1)).func_181675_d();
/* 685 */     bufferbuilder.func_181662_b((x + width), y, 0.0D)
/* 686 */       .func_187315_a(((u + uWidth) * f), 1.0D - (v * f1)).func_181675_d();
/* 687 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_187315_a((u * f), 1.0D - (v * f1)).func_181675_d();
/* 688 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public void initBlur() {
/* 692 */     ScaledResolution resolution = new ScaledResolution(Minecraft.func_71410_x());
/*     */     
/* 694 */     int scaleFactor = resolution.func_78325_e();
/* 695 */     int widthFactor = resolution.func_78326_a();
/* 696 */     int heightFactor = resolution.func_78328_b();
/* 697 */     int gbuffersFormat0 = -1;
/* 698 */     if (OptifineHelper.isShadersEnabled()) {
/* 699 */       gbuffersFormat0 = OptifineHelper.getGbuffersFormat()[ModConfig.INSTANCE.hud.shadersColorTexID];
/*     */     }
/* 701 */     if (this.blurFramebuffer != null) {
/* 702 */       ClientProxy.scopeUtils.blurFramebuffer.func_147614_f();
/* 703 */       OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, OptifineHelper.getDrawFrameBuffer());
/*     */     } 
/*     */     
/* 706 */     if (OptifineHelper.isShadersEnabled() == lastShadersEnabled && gbuffersFormat0 == lastGbuffersFormat0 && lastScale == scaleFactor && lastScaleWidth == widthFactor && lastScaleHeight == heightFactor && this.blurFramebuffer != null && this.blurShader != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 712 */     lastGbuffersFormat0 = gbuffersFormat0;
/* 713 */     lastScale = scaleFactor;
/* 714 */     lastScaleWidth = widthFactor;
/* 715 */     lastScaleHeight = heightFactor;
/* 716 */     lastShadersEnabled = OptifineHelper.isShadersEnabled();
/*     */     
/* 718 */     if (MIRROR_TEX != -1) {
/* 719 */       GL11.glDeleteTextures(MIRROR_TEX);
/*     */     }
/* 721 */     MIRROR_TEX = GL11.glGenTextures();
/* 722 */     GL11.glBindTexture(3553, MIRROR_TEX);
/* 723 */     if (OptifineHelper.isShadersEnabled()) {
/* 724 */       GL11.glTexImage2D(3553, 0, gbuffersFormat0, mc.field_71443_c, mc.field_71440_d, 0, OptifineHelper.getPixelFormat(gbuffersFormat0), 33639, (ByteBuffer)null);
/*     */     } else {
/* 726 */       GL11.glTexImage2D(3553, 0, 32856, mc.field_71443_c, mc.field_71440_d, 0, 6408, 5121, (ByteBuffer)null);
/*     */     } 
/* 728 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 729 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 730 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 731 */     GL11.glTexParameteri(3553, 10243, 10497);
/*     */     
/* 733 */     if (DEPTH_TEX != -1) {
/* 734 */       GL11.glDeleteTextures(DEPTH_TEX);
/*     */     }
/* 736 */     DEPTH_TEX = GL11.glGenTextures();
/* 737 */     GL11.glBindTexture(3553, DEPTH_TEX);
/* 738 */     GL11.glTexImage2D(3553, 0, 6402, mc.field_71443_c, mc.field_71440_d, 0, 6402, 5126, (FloatBuffer)null);
/* 739 */     GL11.glTexParameteri(3553, 10242, 10496);
/* 740 */     GL11.glTexParameteri(3553, 10243, 10496);
/* 741 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 742 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 743 */     GL11.glTexParameteri(3553, 34891, 6409);
/*     */     
/* 745 */     if (DEPTH_ERASE_TEX != -1) {
/* 746 */       GL11.glDeleteTextures(DEPTH_ERASE_TEX);
/*     */     }
/* 748 */     DEPTH_ERASE_TEX = GL11.glGenTextures();
/* 749 */     GL11.glBindTexture(3553, DEPTH_ERASE_TEX);
/* 750 */     GL11.glTexImage2D(3553, 0, 6402, mc.field_71443_c, mc.field_71440_d, 0, 6402, 5126, (FloatBuffer)null);
/* 751 */     GL11.glTexParameteri(3553, 10242, 10496);
/* 752 */     GL11.glTexParameteri(3553, 10243, 10496);
/* 753 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 754 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 755 */     GL11.glTexParameteri(3553, 34891, 6409);
/*     */     
/*     */     try {
/* 758 */       this.blurFramebuffer = null;
/* 759 */       if (!OptifineHelper.isShadersEnabled()) {
/* 760 */         this.blurFramebuffer = new Framebuffer(mc.field_71443_c, mc.field_71440_d, true);
/*     */       } else {
/* 762 */         this.blurFramebuffer = new Framebuffer(mc.field_71443_c, mc.field_71440_d, false);
/*     */       } 
/* 764 */       this.blurFramebuffer.func_147604_a(0.0F, 0.0F, 0.0F, 0.0F);
/* 765 */       if (mc.func_147110_a().isStencilEnabled() && !this.blurFramebuffer.isStencilEnabled()) {
/* 766 */         this.blurFramebuffer.enableStencil();
/*     */       }
/* 768 */       if (OptifineHelper.isShadersEnabled()) {
/* 769 */         this.blurFramebuffer.func_147610_a(false);
/* 770 */         GL30.glFramebufferTexture2D(36160, 36096, 3553, DEPTH_TEX, 0);
/*     */       } 
/* 772 */       this.blurShader = new ShaderGroup(mc.func_110434_K(), mc.func_110442_L(), mc.func_147110_a(), new ResourceLocation("modularwarfare", "shaders/post/blurex.json"));
/* 773 */       this.blurShader.func_148026_a(mc.field_71443_c, mc.field_71440_d);
/* 774 */     } catch (JsonSyntaxException|java.io.IOException e) {
/*     */       
/* 776 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderBlur() {
/* 781 */     for (Shader shader : ((IShaderGroup)this.blurShader).getListShaders()) {
/* 782 */       if (shader.func_148043_c().func_147991_a("Progress") != null) {
/* 783 */         shader.func_148043_c().func_147991_a("Progress").func_148090_a(RenderParameters.adsSwitch);
/*     */       }
/*     */     } 
/* 786 */     this.blurShader.func_148018_a(ClientProxy.renderHooks.partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\scope\ScopeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */