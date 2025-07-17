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
/* 269 */     if (mc.field_71439_g.func_184614_ca() != null && mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemGun && RenderParameters.adsSwitch != 0.0F && mc.field_71474_y.field_74320_O == 0)
/*     */     {
/* 271 */       if (GunType.getAttachment(mc.field_71439_g.func_184614_ca(), AttachmentPresetEnum.Sight) != null) {
/*     */         
/* 273 */         ItemAttachment itemAttachment = (ItemAttachment)GunType.getAttachment(mc.field_71439_g.func_184614_ca(), AttachmentPresetEnum.Sight).func_77973_b();
/* 274 */         if (itemAttachment != null && 
/* 275 */           itemAttachment.type != null && 
/* 276 */           !itemAttachment.type.sight.modeType.isPIP) {
/* 277 */           float dst = getFov(itemAttachment);
/* 278 */           if (ModConfig.INSTANCE.hud.isDynamicFov) {
/* 279 */             dst += event.getFOV() - mc.field_71474_y.field_74334_X;
/*     */           }
/* 281 */           float src = event.getFOV();
/* 282 */           event.setFOV(Math.max(1.0F, src + (dst - src) * RenderParameters.adsSwitch));
/* 283 */           if (RenderParameters.adsSwitch != 0.0F && RenderParameters.adsSwitch != 1.0F)
/*     */           {
/* 285 */             mc.field_71438_f.func_174979_m();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 292 */     if (mc.field_71439_g.func_184614_ca() != null && mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemGun && RenderParameters.adsSwitch != 0.0F && mc.field_71474_y.field_74320_O == 1) {
/*     */       
/* 294 */       float dst = 50.0F;
/* 295 */       if (ModConfig.INSTANCE.hud.isDynamicFov) {
/* 296 */         dst += event.getFOV() - mc.field_71474_y.field_74334_X;
/*     */       }
/* 298 */       float src = event.getFOV();
/* 299 */       event.setFOV(Math.max(1.0F, src + (dst - src) * RenderParameters.adsSwitch));
/* 300 */       if (RenderParameters.adsSwitch != 0.0F && RenderParameters.adsSwitch != 1.0F)
/*     */       {
/* 302 */         mc.field_71438_f.func_174979_m();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onRenderHUD(RenderGameOverlayEvent.Pre event) {
/* 309 */     if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
/*     */       return;
/*     */     }
/* 312 */     if ((Minecraft.func_71410_x()).field_71474_y.field_74320_O != 0) {
/*     */       return;
/*     */     }
/* 315 */     ItemStack stack = (Minecraft.func_71410_x()).field_71439_g.func_184614_ca();
/* 316 */     if (stack != null && stack.func_77973_b() instanceof ItemGun && 
/* 317 */       GunType.getAttachment(mc.field_71439_g.func_184614_ca(), AttachmentPresetEnum.Sight) != null) {
/* 318 */       ItemAttachment itemAttachment = (ItemAttachment)GunType.getAttachment(mc.field_71439_g.func_184614_ca(), AttachmentPresetEnum.Sight).func_77973_b();
/* 319 */       if (itemAttachment.type.sight.modeType.isPIP) {
/* 320 */         renderPostScope(event.getPartialTicks(), false, true, true, 1.0F);
/* 321 */         GlStateManager.func_179126_j();
/* 322 */         GlStateManager.func_179118_c();
/* 323 */         GlStateManager.func_179147_l();
/* 324 */         GlStateManager.func_179086_m(256);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderPostScope(float renderTickTime, boolean isDepthMode, boolean isInsideRendering, boolean isOverlayRendering, float alpha) {
/* 331 */     if (RenderParameters.adsSwitch > 0.0F) {
/* 332 */       ScaledResolution resolution = new ScaledResolution(mc);
/* 333 */       ItemAttachment attachment = null;
/*     */       
/* 335 */       if (GunType.getAttachment(mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Sight) != null) {
/* 336 */         attachment = (ItemAttachment)GunType.getAttachment(mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Sight).func_77973_b();
/*     */       }
/*     */       
/* 339 */       if (attachment == null || attachment == Items.field_190931_a) {
/*     */         return;
/*     */       }
/* 342 */       if (!attachment.type.sight.modeType.isMirror) {
/*     */         return;
/*     */       }
/*     */       
/* 346 */       if (!attachment.type.sight.modeType.isPIP || RenderGunEnhanced.debug1) {
/* 347 */         if (OptifineHelper.isRenderingDfb()) {
/*     */           
/* 349 */           GL43.glCopyImageSubData(MWFOptifineShadesHelper.getFlipTextures().getA(ModConfig.INSTANCE.hud.shadersColorTexID), 3553, 0, 0, 0, 0, MIRROR_TEX, 3553, 0, 0, 0, 0, lastWidth, lastHeight, 1);
/*     */         } else {
/* 351 */           ContextCapabilities contextCapabilities = GLContext.getCapabilities();
/* 352 */           if (contextCapabilities.OpenGL43) {
/* 353 */             GL43.glCopyImageSubData((mc.func_147110_a()).field_147617_g, 3553, 0, 0, 0, 0, MIRROR_TEX, 3553, 0, 0, 0, 0, lastWidth, lastHeight, 1);
/*     */           } else {
/*     */             
/* 356 */             GL11.glBindTexture(3553, MIRROR_TEX);
/* 357 */             GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, mc.field_71443_c, mc.field_71440_d);
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 363 */       boolean needBlur = false;
/* 364 */       if (ModConfig.INSTANCE.hud.ads_blur && 
/* 365 */         attachment != null && 
/* 366 */         attachment.type != null && 
/* 367 */         attachment.type.sight.modeType.isMirror) {
/* 368 */         needBlur = true;
/* 369 */         ClientProxy.scopeUtils.renderBlur();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 375 */       if (OptifineHelper.isShadersEnabled()) {
/* 376 */         Shaders.pushProgram();
/* 377 */         Shaders.useProgram(Shaders.ProgramNone);
/*     */       } 
/* 379 */       GlStateManager.func_179147_l();
/*     */       
/* 381 */       AttachmentRenderConfig.Sight config = ((ModelAttachment)attachment.type.model).config.sight;
/*     */       
/* 383 */       GlStateManager.func_179094_E();
/* 384 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 385 */       setupOverlayRendering();
/* 386 */       GlStateManager.func_179097_i();
/* 387 */       GlStateManager.func_179141_d();
/* 388 */       GlStateManager.func_179092_a(519, 0.0F);
/*     */       
/* 390 */       ClientProxy.scopeUtils.blurFramebuffer.func_147610_a(false);
/* 391 */       GlStateManager.func_179151_a(1.0D);
/* 392 */       GlStateManager.func_179086_m(256);
/* 393 */       GlStateManager.func_179151_a(1.0D);
/* 394 */       GL11.glDepthRange(0.0D, 0.0D);
/* 395 */       GlStateManager.func_179132_a(false);
/* 396 */       GL20.glUseProgram(Programs.scopeBorderProgram);
/* 397 */       GL20.glUniform2f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "size"), mc.field_71443_c, mc.field_71440_d);
/* 398 */       GL20.glUniform1f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "maskRange"), config.uniformMaskRange);
/* 399 */       GL20.glUniform1f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "drawRange"), config.uniformDrawRange);
/* 400 */       GL20.glUniform1f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "strength"), config.uniformStrength);
/* 401 */       GL20.glUniform1f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "scaleRangeY"), config.uniformScaleRangeY);
/* 402 */       GL20.glUniform1f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "scaleStrengthY"), config.uniformScaleStrengthY);
/* 403 */       GL20.glUniform1f(GL20.glGetUniformLocation(Programs.scopeBorderProgram, "verticality"), config.uniformVerticality);
/*     */       
/* 405 */       GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 406 */       GlStateManager.func_179135_a(true, true, true, false);
/* 407 */       GlStateManager.func_179144_i(MIRROR_TEX);
/* 408 */       ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/* 409 */       GlStateManager.func_179132_a(true);
/*     */       
/* 411 */       if (isDepthMode) {
/* 412 */         GlStateManager.func_179126_j();
/* 413 */         GlStateManager.func_179092_a(518, 1.0F);
/*     */       } else {
/* 415 */         GlStateManager.func_179092_a(516, 0.0F);
/*     */       } 
/*     */       
/* 418 */       if (mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun && 
/* 419 */         ((ItemGun)mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b()).type.animationType == WeaponAnimationType.ENHANCED) {
/* 420 */         GlStateManager.func_179144_i(INSIDE_GUN_TEX);
/* 421 */         ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/*     */       } 
/*     */ 
/*     */       
/* 425 */       GL20.glUseProgram(Programs.normalProgram);
/* 426 */       GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 427 */       GlStateManager.func_179094_E();
/* 428 */       float width = config.maskSize * resolution.func_78328_b();
/* 429 */       float height = config.maskSize * resolution.func_78328_b();
/* 430 */       GlStateManager.func_179109_b(resolution.func_78326_a() / 2.0F, resolution.func_78328_b() / 2.0F, 0.0F);
/* 431 */       GlStateManager.func_179114_b(RenderParameters.CROSS_ROTATE, 0.0F, 0.0F, 1.0F);
/* 432 */       GlStateManager.func_179109_b(-width / 2.0F, -height / 2.0F, 0.0F);
/* 433 */       ClientProxy.gunStaticRenderer.bindTexture("mask", config.maskTexture);
/* 434 */       ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, (int)width, (int)height, 1.0F, 1.0F);
/* 435 */       GlStateManager.func_179121_F();
/*     */       
/* 437 */       GlStateManager.func_179135_a(true, true, true, true);
/* 438 */       GlStateManager.func_179097_i();
/* 439 */       GlStateManager.func_179132_a(false);
/* 440 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 442 */       OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, OptifineHelper.getDrawFrameBuffer());
/*     */       
/* 444 */       GlStateManager.func_179092_a(518, 1.0F);
/* 445 */       GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*     */       
/* 447 */       if (isInsideRendering) {
/* 448 */         ClientProxy.scopeUtils.blurFramebuffer.func_147612_c();
/* 449 */         ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 452 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, alpha);
/* 453 */       if (isOverlayRendering) {
/* 454 */         if (isDepthMode) {
/* 455 */           this.blurFramebuffer.func_147610_a(false);
/*     */           
/* 457 */           GlStateManager.func_179126_j();
/* 458 */           GlStateManager.func_179132_a(true);
/* 459 */           GlStateManager.func_179092_a(518, 1.0F);
/*     */           
/* 461 */           GlStateManager.func_179135_a(false, false, false, false);
/* 462 */           GlStateManager.func_179144_i(OVERLAY_TEX);
/* 463 */           ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/* 464 */           GlStateManager.func_179135_a(true, true, true, true);
/* 465 */           GlStateManager.func_179132_a(false);
/* 466 */           GlStateManager.func_179097_i();
/*     */           
/* 468 */           OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, OptifineHelper.getDrawFrameBuffer());
/*     */         } else {
/* 470 */           GlStateManager.func_179092_a(516, 0.001F);
/*     */           
/* 472 */           GL20.glUseProgram(Programs.overlayProgram);
/* 473 */           GL20.glUniform2f(GL20.glGetUniformLocation(Programs.overlayProgram, "size"), mc.field_71443_c, mc.field_71440_d);
/* 474 */           GlStateManager.func_179138_g(33987);
/* 475 */           int tex3 = GlStateManager.func_187397_v(32873);
/* 476 */           GlStateManager.func_179144_i(this.blurFramebuffer.field_147617_g);
/* 477 */           GlStateManager.func_179138_g(33988);
/* 478 */           int tex4 = GlStateManager.func_187397_v(32873);
/* 479 */           GlStateManager.func_179144_i(SCOPE_LIGHTMAP_TEX);
/* 480 */           GlStateManager.func_179138_g(33984);
/* 481 */           GlStateManager.func_179144_i(OVERLAY_TEX);
/* 482 */           ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/* 483 */           GlStateManager.func_179138_g(33987);
/* 484 */           GlStateManager.func_179144_i(tex3);
/* 485 */           GlStateManager.func_179138_g(33988);
/* 486 */           GlStateManager.func_179144_i(tex4);
/* 487 */           GlStateManager.func_179138_g(33984);
/*     */         } 
/*     */       }
/*     */       
/* 491 */       GL20.glUseProgram(0);
/*     */       
/* 493 */       if (OptifineHelper.isShadersEnabled()) {
/* 494 */         Shaders.popProgram();
/*     */       }
/*     */       
/* 497 */       GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 498 */       GlStateManager.func_179126_j();
/* 499 */       GL11.glDepthRange(0.0D, 1.0D);
/* 500 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 501 */       GlStateManager.func_179143_c(515);
/* 502 */       GlStateManager.func_179135_a(true, true, true, true);
/* 503 */       GlStateManager.func_179132_a(true);
/* 504 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupOverlayRendering() {
/* 510 */     this; ScaledResolution scaledresolution = new ScaledResolution(mc);
/* 511 */     GlStateManager.func_179128_n(5889);
/* 512 */     GlStateManager.func_179096_D();
/* 513 */     GlStateManager.func_179130_a(0.0D, scaledresolution.func_78327_c(), scaledresolution.func_78324_d(), 0.0D, 1000.0D, 3000.0D);
/* 514 */     GlStateManager.func_179128_n(5888);
/* 515 */     GlStateManager.func_179096_D();
/* 516 */     GlStateManager.func_179109_b(0.0F, 0.0F, -2000.0F);
/*     */   }
/*     */   
/*     */   public void copyDepthBuffer() {
/* 520 */     Minecraft mc = Minecraft.func_71410_x();
/* 521 */     GL30.glBindFramebuffer(36008, OptifineHelper.getDrawFrameBuffer());
/* 522 */     GL30.glBindFramebuffer(36009, ClientProxy.scopeUtils.blurFramebuffer.field_147616_f);
/* 523 */     GlStateManager.func_179135_a(false, false, false, false);
/* 524 */     GL30.glBlitFramebuffer(0, 0, mc.field_71443_c, mc.field_71440_d, 0, 0, mc.field_71443_c, mc.field_71440_d, 256, 9728);
/* 525 */     GlStateManager.func_179135_a(true, true, true, true);
/* 526 */     GL30.glBindFramebuffer(36008, 0);
/* 527 */     GL30.glBindFramebuffer(36009, 0);
/*     */   }
/*     */   
/*     */   public void copyEraseDepthBuffer() {
/* 531 */     GL43.glCopyImageSubData(MWFOptifineShadesHelper.getDFBDepthTextures().get(0), 3553, 0, 0, 0, 0, DEPTH_ERASE_TEX, 3553, 0, 0, 0, 0, lastWidth, lastHeight, 1);
/*     */   }
/*     */   
/*     */   public void renderSunglassesPostProgram() {
/* 535 */     if (!OptifineHelper.isShadersEnabled()) {
/*     */       return;
/*     */     }
/* 538 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 539 */     Shaders.pushProgram();
/* 540 */     GlStateManager.func_179128_n(5889);
/* 541 */     GlStateManager.func_179094_E();
/* 542 */     GlStateManager.func_179128_n(5888);
/* 543 */     GlStateManager.func_179094_E();
/*     */     
/* 545 */     setupOverlayRendering();
/*     */     
/* 547 */     GL11.glPushAttrib(18688);
/*     */     
/* 549 */     GlStateManager.func_179092_a(516, 0.0F);
/* 550 */     GL11.glDepthRange(0.0D, 1.0D);
/*     */ 
/*     */     
/* 553 */     this; ScaledResolution resolution = new ScaledResolution(mc);
/* 554 */     GL20.glDrawBuffers(36065);
/*     */     
/* 556 */     GL20.glUseProgram(Programs.sunglassesProgram);
/*     */     
/* 558 */     GlStateManager.func_179084_k();
/* 559 */     GlStateManager.func_179138_g(33984);
/* 560 */     GlStateManager.func_179144_i(OVERLAY_TEX);
/* 561 */     ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/* 562 */     GlStateManager.func_179147_l();
/*     */     
/* 564 */     GL11.glPopAttrib();
/*     */     
/* 566 */     GL20.glUseProgram(0);
/*     */     
/* 568 */     GlStateManager.func_179128_n(5889);
/* 569 */     GlStateManager.func_179121_F();
/* 570 */     GlStateManager.func_179128_n(5888);
/* 571 */     GlStateManager.func_179121_F();
/* 572 */     Shaders.popProgram();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void clientTick(TickEvent.ClientTickEvent event) {
/* 577 */     switch (event.phase) {
/*     */       case START:
/* 579 */         if (ClientRenderHooks.isAimingScope) {
/* 580 */           if (mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun && RenderParameters.adsSwitch != 0.0F && mc.field_71474_y.field_74320_O == 0 && 
/* 581 */             GunType.getAttachment(mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Sight) != null) {
/* 582 */             ItemAttachment itemAttachment = (ItemAttachment)GunType.getAttachment(mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND), AttachmentPresetEnum.Sight).func_77973_b();
/* 583 */             if (itemAttachment != null && 
/* 584 */               itemAttachment.type != null) {
/* 585 */               mc.field_71474_y.field_74341_c = this.mouseSensitivityBackup * ((ModelAttachment)itemAttachment.type.model).config.sight.mouseSensitivityFactor;
/* 586 */               this.hasBeenReseted = false;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         } 
/* 591 */         if (!this.hasBeenReseted) {
/* 592 */           mc.field_71474_y.field_74341_c = this.mouseSensitivityBackup;
/*     */           
/* 594 */           this.hasBeenReseted = true; break;
/* 595 */         }  if (this.mouseSensitivityBackup != mc.field_71474_y.field_74341_c)
/* 596 */           this.mouseSensitivityBackup = mc.field_71474_y.field_74341_c; 
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float getFov(ItemAttachment itemAttachment) {
/* 602 */     return 50.0F / ((ModelAttachment)itemAttachment.type.model).config.sight.fovZoom;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWorld(Minecraft mc, ItemAttachment itemAttachment, float partialTick) {
/* 607 */     float zoom = getFov(itemAttachment);
/*     */     
/* 609 */     GL11.glPushMatrix();
/* 610 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 612 */     RenderGlobal renderBackup = mc.field_71438_f;
/*     */     
/* 614 */     long endTime = 0L;
/* 615 */     boolean hide = mc.field_71474_y.field_74319_N;
/* 616 */     int view = mc.field_71474_y.field_74320_O;
/* 617 */     int limit = mc.field_71474_y.field_74350_i;
/* 618 */     RayTraceResult mouseOver = mc.field_71476_x;
/* 619 */     float fov = mc.field_71474_y.field_74334_X;
/* 620 */     boolean bobbingBackup = mc.field_71474_y.field_74336_f;
/* 621 */     float mouseSensitivityBackup = mc.field_71474_y.field_74341_c;
/*     */     
/* 623 */     mc.field_71438_f = this.scopeRenderGlobal;
/*     */ 
/*     */     
/* 626 */     mc.field_71474_y.field_74319_N = true;
/* 627 */     mc.field_71474_y.field_74320_O = 0;
/* 628 */     mc.field_71474_y.field_74334_X = zoom;
/* 629 */     mc.field_71474_y.field_74336_f = false;
/*     */     
/* 631 */     if (mc.field_71474_y.field_74334_X < 0.0F) {
/* 632 */       mc.field_71474_y.field_74334_X = 1.0F;
/*     */     }
/*     */     
/* 635 */     if (limit != 0 && this.renderEndNanoTime != null) {
/*     */       try {
/* 637 */         endTime = this.renderEndNanoTime.getLong(mc.field_71460_t);
/* 638 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/* 642 */     int fps = Math.max(30, mc.field_71474_y.field_74350_i);
/*     */     
/* 644 */     OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, (Minecraft.func_71410_x().func_147110_a()).field_147616_f);
/*     */     
/* 646 */     int tex = (Minecraft.func_71410_x().func_147110_a()).field_147617_g;
/* 647 */     (Minecraft.func_71410_x().func_147110_a()).field_147617_g = MIRROR_TEX;
/* 648 */     GL30.glFramebufferTexture2D(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, MIRROR_TEX, 0);
/*     */     
/* 650 */     mc.field_71460_t.func_78471_a(partialTick, endTime);
/*     */     
/* 652 */     GL20.glUseProgram(0);
/*     */ 
/*     */     
/* 655 */     (Minecraft.func_71410_x().func_147110_a()).field_147617_g = tex;
/* 656 */     GL30.glFramebufferTexture2D(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, tex, 0);
/*     */ 
/*     */     
/* 659 */     if (limit != 0 && this.renderEndNanoTime != null) {
/*     */       try {
/* 661 */         this.renderEndNanoTime.setLong(mc.field_71460_t, endTime);
/* 662 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 668 */     mc.field_71476_x = mouseOver;
/* 669 */     mc.field_71474_y.field_74350_i = limit;
/* 670 */     mc.field_71474_y.field_74320_O = view;
/* 671 */     mc.field_71474_y.field_74319_N = hide;
/* 672 */     mc.field_71474_y.field_74336_f = bobbingBackup;
/* 673 */     mc.field_71474_y.field_74341_c = mouseSensitivityBackup;
/*     */     
/* 675 */     mc.field_71474_y.field_74334_X = fov;
/* 676 */     mc.field_71438_f = renderBackup;
/*     */ 
/*     */     
/* 679 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onWorldLoad(WorldEvent.Load event) {
/* 684 */     if ((event.getWorld()).field_72995_K) {
/* 685 */       this.scopeRenderGlobal.func_72732_a((WorldClient)event.getWorld());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScaledCustomSizeModalRectFlipY(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
/* 694 */     float f = 1.0F / tileWidth;
/* 695 */     float f1 = 1.0F / tileHeight;
/* 696 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 697 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 698 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 699 */     bufferbuilder.func_181662_b(x, (y + height), 0.0D)
/* 700 */       .func_187315_a((u * f), 1.0D - ((v + vHeight) * f1)).func_181675_d();
/* 701 */     bufferbuilder.func_181662_b((x + width), (y + height), 0.0D)
/* 702 */       .func_187315_a(((u + uWidth) * f), 1.0D - ((v + vHeight) * f1)).func_181675_d();
/* 703 */     bufferbuilder.func_181662_b((x + width), y, 0.0D)
/* 704 */       .func_187315_a(((u + uWidth) * f), 1.0D - (v * f1)).func_181675_d();
/* 705 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_187315_a((u * f), 1.0D - (v * f1)).func_181675_d();
/* 706 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public void initBlur() {
/* 710 */     ScaledResolution resolution = new ScaledResolution(Minecraft.func_71410_x());
/*     */     
/* 712 */     int scaleFactor = resolution.func_78325_e();
/* 713 */     int widthFactor = resolution.func_78326_a();
/* 714 */     int heightFactor = resolution.func_78328_b();
/* 715 */     int gbuffersFormat0 = -1;
/* 716 */     if (OptifineHelper.isShadersEnabled()) {
/* 717 */       gbuffersFormat0 = OptifineHelper.getGbuffersFormat()[ModConfig.INSTANCE.hud.shadersColorTexID];
/*     */     }
/* 719 */     if (this.blurFramebuffer != null) {
/* 720 */       ClientProxy.scopeUtils.blurFramebuffer.func_147614_f();
/* 721 */       OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, OptifineHelper.getDrawFrameBuffer());
/*     */     } 
/*     */     
/* 724 */     if (OptifineHelper.isShadersEnabled() == lastShadersEnabled && gbuffersFormat0 == lastGbuffersFormat0 && lastScale == scaleFactor && lastScaleWidth == widthFactor && lastScaleHeight == heightFactor && this.blurFramebuffer != null && this.blurShader != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 730 */     lastGbuffersFormat0 = gbuffersFormat0;
/* 731 */     lastScale = scaleFactor;
/* 732 */     lastScaleWidth = widthFactor;
/* 733 */     lastScaleHeight = heightFactor;
/* 734 */     lastShadersEnabled = OptifineHelper.isShadersEnabled();
/*     */     
/* 736 */     if (MIRROR_TEX != -1) {
/* 737 */       GL11.glDeleteTextures(MIRROR_TEX);
/*     */     }
/* 739 */     MIRROR_TEX = GL11.glGenTextures();
/* 740 */     GL11.glBindTexture(3553, MIRROR_TEX);
/* 741 */     if (OptifineHelper.isShadersEnabled()) {
/* 742 */       GL11.glTexImage2D(3553, 0, gbuffersFormat0, mc.field_71443_c, mc.field_71440_d, 0, OptifineHelper.getPixelFormat(gbuffersFormat0), 33639, (ByteBuffer)null);
/*     */     } else {
/* 744 */       GL11.glTexImage2D(3553, 0, 32856, mc.field_71443_c, mc.field_71440_d, 0, 6408, 5121, (ByteBuffer)null);
/*     */     } 
/* 746 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 747 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 748 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 749 */     GL11.glTexParameteri(3553, 10243, 10497);
/*     */     
/* 751 */     if (DEPTH_TEX != -1) {
/* 752 */       GL11.glDeleteTextures(DEPTH_TEX);
/*     */     }
/* 754 */     DEPTH_TEX = GL11.glGenTextures();
/* 755 */     GL11.glBindTexture(3553, DEPTH_TEX);
/* 756 */     GL11.glTexImage2D(3553, 0, 6402, mc.field_71443_c, mc.field_71440_d, 0, 6402, 5126, (FloatBuffer)null);
/* 757 */     GL11.glTexParameteri(3553, 10242, 10496);
/* 758 */     GL11.glTexParameteri(3553, 10243, 10496);
/* 759 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 760 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 761 */     GL11.glTexParameteri(3553, 34891, 6409);
/*     */     
/* 763 */     if (DEPTH_ERASE_TEX != -1) {
/* 764 */       GL11.glDeleteTextures(DEPTH_ERASE_TEX);
/*     */     }
/* 766 */     DEPTH_ERASE_TEX = GL11.glGenTextures();
/* 767 */     GL11.glBindTexture(3553, DEPTH_ERASE_TEX);
/* 768 */     GL11.glTexImage2D(3553, 0, 6402, mc.field_71443_c, mc.field_71440_d, 0, 6402, 5126, (FloatBuffer)null);
/* 769 */     GL11.glTexParameteri(3553, 10242, 10496);
/* 770 */     GL11.glTexParameteri(3553, 10243, 10496);
/* 771 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 772 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 773 */     GL11.glTexParameteri(3553, 34891, 6409);
/*     */     
/*     */     try {
/* 776 */       this.blurFramebuffer = null;
/* 777 */       if (!OptifineHelper.isShadersEnabled()) {
/* 778 */         this.blurFramebuffer = new Framebuffer(mc.field_71443_c, mc.field_71440_d, true);
/*     */       } else {
/* 780 */         this.blurFramebuffer = new Framebuffer(mc.field_71443_c, mc.field_71440_d, false);
/*     */       } 
/* 782 */       this.blurFramebuffer.func_147604_a(0.0F, 0.0F, 0.0F, 0.0F);
/* 783 */       if (mc.func_147110_a().isStencilEnabled() && !this.blurFramebuffer.isStencilEnabled()) {
/* 784 */         this.blurFramebuffer.enableStencil();
/*     */       }
/* 786 */       if (OptifineHelper.isShadersEnabled()) {
/* 787 */         this.blurFramebuffer.func_147610_a(false);
/* 788 */         GL30.glFramebufferTexture2D(36160, 36096, 3553, DEPTH_TEX, 0);
/*     */       } 
/* 790 */       this.blurShader = new ShaderGroup(mc.func_110434_K(), mc.func_110442_L(), mc.func_147110_a(), new ResourceLocation("modularwarfare", "shaders/post/blurex.json"));
/* 791 */       this.blurShader.func_148026_a(mc.field_71443_c, mc.field_71440_d);
/* 792 */     } catch (JsonSyntaxException|java.io.IOException e) {
/*     */       
/* 794 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderBlur() {
/* 799 */     for (Shader shader : ((IShaderGroup)this.blurShader).getListShaders()) {
/* 800 */       if (shader.func_148043_c().func_147991_a("Progress") != null) {
/* 801 */         shader.func_148043_c().func_147991_a("Progress").func_148090_a(RenderParameters.adsSwitch);
/*     */       }
/*     */     } 
/* 804 */     this.blurShader.func_148018_a(ClientProxy.renderHooks.partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\scope\ScopeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */