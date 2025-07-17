/*     */ package com.modularwarfare.client.hud;
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.api.RenderAmmoCountEvent;
/*     */ import com.modularwarfare.client.ClientProxy;
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderParameters;
/*     */ import com.modularwarfare.client.fpp.enhanced.AnimationType;
/*     */ import com.modularwarfare.client.fpp.enhanced.animation.EnhancedStateMachine;
/*     */ import com.modularwarfare.client.model.ModelAttachment;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAmmo;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.guns.ItemBullet;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.WeaponDotColorType;
/*     */ import com.modularwarfare.utility.RayUtil;
/*     */ import com.modularwarfare.utility.RenderHelperMW;
/*     */ import mchhui.modularmovements.tactical.client.ClientLitener;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GunUI {
/*  40 */   public static final ResourceLocation crosshair = new ResourceLocation("modularwarfare", "textures/gui/crosshair.png");
/*     */   
/*  42 */   public static final ResourceLocation reddot = new ResourceLocation("modularwarfare", "textures/gui/reddot.png");
/*  43 */   public static final ResourceLocation greendot = new ResourceLocation("modularwarfare", "textures/gui/greendot.png");
/*  44 */   public static final ResourceLocation bluedot = new ResourceLocation("modularwarfare", "textures/gui/bluedot.png");
/*     */   
/*  46 */   public static final ResourceLocation hitMarker = new ResourceLocation("modularwarfare", "textures/gui/hitmarker.png");
/*  47 */   public static final ResourceLocation hitMarkerHS = new ResourceLocation("modularwarfare", "textures/gui/hitmarkerhs.png");
/*     */ 
/*     */   
/*  50 */   public static int hitMarkerTime = 0;
/*     */   
/*     */   public static boolean hitMarkerheadshot;
/*     */   
/*     */   public static float bulletSnapFade;
/*  55 */   private static RenderItem itemRenderer = Minecraft.func_71410_x().func_175599_af();
/*     */   
/*     */   public static void addHitMarker(boolean headshot) {
/*  58 */     hitMarkerTime = 20;
/*  59 */     hitMarkerheadshot = headshot;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderPre(RenderGameOverlayEvent.Pre event) {
/*  64 */     GlStateManager.func_179094_E();
/*  65 */     Minecraft mc = Minecraft.func_71410_x();
/*  66 */     if (event.isCancelable()) {
/*  67 */       int width = event.getResolution().func_78326_a();
/*  68 */       int height = event.getResolution().func_78328_b();
/*  69 */       ItemStack stack = mc.field_71439_g.func_184614_ca();
/*  70 */       if (stack != null && stack.func_77973_b() instanceof ItemGun) {
/*  71 */         boolean showCrosshair; switch (event.getType()) {
/*     */           case CROSSHAIRS:
/*  73 */             event.setCanceled(true);
/*     */             break;
/*     */           case ALL:
/*  76 */             GlStateManager.func_179094_E();
/*  77 */             if (ModConfig.INSTANCE.hud.ammo_count) {
/*  78 */               RenderAmmoCountEvent ammoCountEvent = new RenderAmmoCountEvent(width, height);
/*  79 */               MinecraftForge.EVENT_BUS.post((Event)ammoCountEvent);
/*  80 */               if (!ammoCountEvent.isCanceled()) {
/*  81 */                 GlStateManager.func_179094_E();
/*  82 */                 RenderPlayerAmmo(width, height);
/*  83 */                 GlStateManager.func_179121_F();
/*     */               } 
/*     */             } 
/*  86 */             RenderHitMarker(Tessellator.func_178181_a(), width, height);
/*  87 */             RenderPlayerSnap(width, height);
/*  88 */             if (mc.func_175606_aa().equals(mc.field_71439_g) && mc.field_71474_y.field_74320_O == 0 && (ClientRenderHooks.isAimingScope || ClientRenderHooks.isAiming) && RenderParameters.collideFrontDistance <= 0.025F && 
/*  89 */               mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun) {
/*  90 */               ItemStack gunStack = mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND);
/*  91 */               if (GunType.getAttachment(gunStack, AttachmentPresetEnum.Sight) != null) {
/*  92 */                 ItemAttachment itemAttachment = (ItemAttachment)GunType.getAttachment(gunStack, AttachmentPresetEnum.Sight).func_77973_b();
/*  93 */                 if (itemAttachment != null && 
/*  94 */                   itemAttachment.type.sight.modeType != null) {
/*     */                   
/*  96 */                   float gunRotX = RenderParameters.GUN_ROT_X_LAST + (RenderParameters.GUN_ROT_X - RenderParameters.GUN_ROT_X_LAST) * ClientProxy.renderHooks.partialTicks;
/*  97 */                   float gunRotY = RenderParameters.GUN_ROT_Y_LAST + (RenderParameters.GUN_ROT_Y - RenderParameters.GUN_ROT_Y_LAST) * ClientProxy.renderHooks.partialTicks;
/*     */                   
/*  99 */                   float alpha = gunRotX;
/* 100 */                   alpha = Math.abs(alpha);
/*     */                   
/* 102 */                   if (gunRotX > -1.5D && gunRotX < 1.5D && gunRotY > -0.75D && gunRotY < 0.75D && RenderParameters.GUN_CHANGE_Y == 0.0F) {
/* 103 */                     GL11.glPushMatrix();
/*     */                     
/* 105 */                     GL11.glRotatef(gunRotX, 0.0F, -1.0F, 0.0F);
/* 106 */                     GL11.glRotatef(gunRotY, 0.0F, 0.0F, -1.0F);
/*     */                     
/* 108 */                     if (itemAttachment.type.sight.modeType.isDot) {
/* 109 */                       switch (itemAttachment.type.sight.dotColorType) {
/*     */                         case CROSSHAIRS:
/* 111 */                           mc.field_71446_o.func_110577_a(reddot);
/*     */                           break;
/*     */                         case ALL:
/* 114 */                           mc.field_71446_o.func_110577_a(bluedot);
/*     */                           break;
/*     */                         case null:
/* 117 */                           mc.field_71446_o.func_110577_a(greendot);
/*     */                           break;
/*     */                         default:
/* 120 */                           mc.field_71446_o.func_110577_a(reddot);
/*     */                           break;
/*     */                       } 
/* 123 */                       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F - alpha);
/* 124 */                       Gui.func_146110_a(width / 2, height / 2, 2.0F, 2.0F, 1, 1, 16.0F, 16.0F);
/*     */                     } else {
/* 126 */                       ResourceLocation overlayToRender = itemAttachment.type.sight.overlayType.resourceLocations.get(0);
/*     */                       
/* 128 */                       float factor = 1.0F;
/* 129 */                       if (width < 700) {
/* 130 */                         factor = 2.0F;
/*     */                       }
/* 132 */                       int size = 64 / (int)(event.getResolution().func_78325_e() * factor) + (int)RenderParameters.crouchSwitch * 5;
/* 133 */                       float scale = Math.abs(RenderParameters.playerRecoilYaw) + Math.abs(RenderParameters.playerRecoilPitch);
/* 134 */                       scale *= ((ModelAttachment)itemAttachment.type.model).config.sight.factorCrossScale;
/* 135 */                       size = (int)(size * (1.0D + ((scale > 0.8D) ? scale : 0.0F) * 0.2D) * ((ModelAttachment)itemAttachment.type.model).config.sight.rectileScale);
/* 136 */                       GL11.glTranslatef((width / 2), (height / 2), 0.0F);
/* 137 */                       if (!itemAttachment.type.sight.plumbCrossHair) {
/* 138 */                         GlStateManager.func_179114_b(RenderParameters.CROSS_ROTATE, 0.0F, 0.0F, 1.0F);
/*     */                       }
/* 140 */                       GL11.glTranslatef(-size, -size, 0.0F);
/* 141 */                       GL11.glTranslatef(RenderParameters.VAL2 / 10.0F, RenderParameters.VAL / 10.0F, 0.0F);
/* 142 */                       RenderHelperMW.renderImageAlpha(0.0D, 0.0D, overlayToRender, (size * 2), (size * 2), (1.0F - alpha));
/*     */                     } 
/*     */                     
/* 145 */                     GL11.glPopMatrix();
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 154 */             showCrosshair = (RenderParameters.adsSwitch < 0.6F && (ClientProxy.gunEnhancedRenderer.getClientController()).ADS < 0.5D);
/* 155 */             if ((Minecraft.func_71410_x()).field_71474_y.field_74320_O == 1) {
/* 156 */               showCrosshair = true;
/*     */             }
/* 158 */             if (ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)mc.field_71439_g) != null) {
/* 159 */               if ((ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)mc.field_71439_g)).reloading) {
/* 160 */                 showCrosshair = false;
/*     */               }
/* 162 */               if ((ClientProxy.gunEnhancedRenderer.getClientController()).INSPECT != 1.0D) {
/* 163 */                 showCrosshair = false;
/*     */               }
/*     */             } 
/*     */             
/* 167 */             if (mc.func_175606_aa() != mc.field_71439_g) {
/* 168 */               showCrosshair = false;
/*     */             }
/* 170 */             if (ModConfig.INSTANCE.hud.enable_crosshair && !(ClientRenderHooks.getAnimMachine((EntityLivingBase)mc.field_71439_g)).attachmentMode && showCrosshair && (ClientProxy.shoulderSurfingLoaded || mc.field_71474_y.field_74320_O == 0) && !mc.field_71439_g.func_70051_ag() && !(ClientRenderHooks.getAnimMachine((EntityLivingBase)mc.field_71439_g)).reloading && mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemGun && 
/* 171 */               RenderParameters.collideFrontDistance <= 0.2F) {
/* 172 */               GlStateManager.func_179094_E();
/*     */               
/* 174 */               if (ModConfig.INSTANCE.hud.dynamic_crosshair) {
/* 175 */                 float gunRotX = RenderParameters.GUN_ROT_X_LAST + (RenderParameters.GUN_ROT_X - RenderParameters.GUN_ROT_X_LAST) * RenderParameters.smoothing;
/* 176 */                 float gunRotY = RenderParameters.GUN_ROT_Y_LAST + (RenderParameters.GUN_ROT_Y - RenderParameters.GUN_ROT_Y_LAST) * RenderParameters.smoothing;
/* 177 */                 GL11.glRotatef(gunRotX, 0.0F, -1.0F, 0.0F);
/* 178 */                 GL11.glRotatef(gunRotY, 1.0F, 0.0F, 1.0F);
/*     */               } 
/*     */               
/* 181 */               float accuracy = RayUtil.calculateAccuracy((ItemGun)mc.field_71439_g.func_184614_ca().func_77973_b(), (EntityLivingBase)mc.field_71439_g);
/* 182 */               int move = Math.max(0, (int)(accuracy * 3.0F));
/* 183 */               mc.field_71446_o.func_110577_a(crosshair);
/* 184 */               int xPos = width / 2;
/* 185 */               int yPos = height / 2;
/*     */ 
/*     */               
/* 188 */               GlStateManager.func_179109_b(xPos, yPos, 0.0F);
/* 189 */               if (ModularWarfare.isLoadedModularMovements) {
/* 190 */                 GL11.glRotatef(15.0F * ClientLitener.cameraProbeOffset, 0.0F, 0.0F, 1.0F);
/*     */               }
/* 192 */               GlStateManager.func_179147_l();
/* 193 */               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 194 */               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 195 */               Gui.func_146110_a(0, 0, 1.0F, 1.0F, 1, 1, 16.0F, 16.0F);
/* 196 */               Gui.func_146110_a(0, 0 + move, 1.0F, 1.0F, 1, 4, 16.0F, 16.0F);
/* 197 */               Gui.func_146110_a(0, 0 - move - 3, 1.0F, 1.0F, 1, 4, 16.0F, 16.0F);
/* 198 */               Gui.func_146110_a(0 + move, 0, 1.0F, 1.0F, 4, 1, 16.0F, 16.0F);
/* 199 */               Gui.func_146110_a(0 - move - 3, 0, 1.0F, 1.0F, 4, 1, 16.0F, 16.0F);
/* 200 */               GlStateManager.func_179084_k();
/*     */               
/* 202 */               GlStateManager.func_179121_F();
/*     */             } 
/*     */             
/* 205 */             GlStateManager.func_179121_F();
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/*     */     } 
/* 213 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 214 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */   
/*     */   private void RenderHitMarker(Tessellator tessellator, int i, int j) {
/* 219 */     Minecraft mc = Minecraft.func_71410_x();
/*     */     
/* 221 */     if (hitMarkerTime > 0) {
/* 222 */       GlStateManager.func_179094_E();
/*     */       
/* 224 */       if (!hitMarkerheadshot) {
/* 225 */         mc.field_71446_o.func_110577_a(hitMarker);
/*     */       } else {
/* 227 */         mc.field_71446_o.func_110577_a(hitMarkerHS);
/*     */       } 
/*     */ 
/*     */       
/* 231 */       GlStateManager.func_179141_d();
/* 232 */       GlStateManager.func_179147_l();
/* 233 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, Math.max((hitMarkerTime - 10.0F + ClientProxy.renderHooks.partialTicks) / 10.0F, 0.0F));
/*     */       
/* 235 */       double zLevel = 0.0D;
/*     */       
/* 237 */       tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*     */       
/* 239 */       tessellator.func_178180_c().func_181662_b((i / 2) - 4.0D, (j / 2) + 5.0D, zLevel).func_187315_a(0.0D, 0.5625D).func_181675_d();
/* 240 */       tessellator.func_178180_c().func_181662_b((i / 2) + 5.0D, (j / 2) + 5.0D, zLevel).func_187315_a(0.5625D, 0.5625D).func_181675_d();
/* 241 */       tessellator.func_178180_c().func_181662_b((i / 2) + 5.0D, (j / 2) - 4.0D, zLevel).func_187315_a(0.5625D, 0.0D).func_181675_d();
/* 242 */       tessellator.func_178180_c().func_181662_b((i / 2) - 4.0D, (j / 2) - 4.0D, zLevel).func_187315_a(0.0D, 0.0D).func_181675_d();
/*     */       
/* 244 */       tessellator.func_78381_a();
/*     */       
/* 246 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 247 */       GlStateManager.func_179118_c();
/* 248 */       GlStateManager.func_179084_k();
/*     */       
/* 250 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void RenderPlayerAmmo(int i, int j) {
/* 255 */     Minecraft mc = Minecraft.func_71410_x();
/*     */     
/* 257 */     ItemStack stack = mc.field_71439_g.func_184586_b(EnumHand.MAIN_HAND);
/* 258 */     if (stack != null && stack.func_77973_b() instanceof ItemGun)
/*     */     {
/* 260 */       if (stack.func_77978_p() != null) {
/* 261 */         ItemStack ammoStack = new ItemStack(stack.func_77978_p().func_74775_l("ammo"));
/* 262 */         GunType type = ((ItemGun)stack.func_77973_b()).type;
/* 263 */         if (type.animationType.equals(WeaponAnimationType.BASIC)) {
/* 264 */           ammoStack.func_77964_b(0);
/* 265 */           if (ItemGun.hasAmmoLoaded(stack)) {
/* 266 */             renderAmmo(stack, ammoStack, i, j, 0);
/* 267 */           } else if (ItemGun.getUsedBullet(stack, ((ItemGun)stack.func_77973_b()).type) != null) {
/* 268 */             renderBullets(stack, null, i, j, 0, null);
/*     */           }
/*     */         
/* 271 */         } else if (ClientProxy.gunEnhancedRenderer.getClientController() != null) {
/* 272 */           EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)mc.field_71439_g);
/* 273 */           AnimationType reloadAni = anim.getReloadAnimationType();
/* 274 */           if (type.acceptedAmmo != null) {
/* 275 */             ammoStack = ClientProxy.gunEnhancedRenderer.getClientController().getRenderAmmo(ammoStack);
/* 276 */             ammoStack.func_77964_b(0);
/* 277 */             if (reloadAni == AnimationType.RELOAD_FIRST || reloadAni == AnimationType.RELOAD_FIRST_QUICKLY || reloadAni == AnimationType.UNLOAD) {
/* 278 */               ammoStack = ItemStack.field_190927_a;
/*     */             }
/* 280 */             renderAmmo(stack, ammoStack, i, j, 0);
/*     */           } else {
/* 282 */             boolean flag = true;
/* 283 */             ItemStack bulletStack = new ItemStack(stack.func_77978_p().func_74775_l("bullet"));
/* 284 */             if (anim.reloading) {
/* 285 */               bulletStack = ClientProxy.gunEnhancedRenderer.getClientController().getRenderAmmo(bulletStack);
/* 286 */               if (ClientProxy.gunEnhancedRenderer.getClientController().getPlayingAnimation() == AnimationType.POST_UNLOAD);
/*     */             } 
/*     */ 
/*     */             
/* 290 */             bulletStack.func_77964_b(0);
/* 291 */             int offset = anim.getAmmoCountOffset(false);
/* 292 */             if (!anim.reloading) {
/* 293 */               offset = 0;
/*     */             }
/* 295 */             if (flag) {
/* 296 */               renderBullets(stack, null, i, j, offset, bulletStack);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAmmo(ItemStack stack, ItemStack ammoStack, int i, int j, int countOffset) {
/* 306 */     Minecraft mc = Minecraft.func_71410_x();
/* 307 */     int x = 0;
/* 308 */     int top = j - 38;
/* 309 */     int left = 2;
/* 310 */     int right = Math.min(68, i / 2 - 60);
/* 311 */     int bottom = top + 22;
/*     */     
/* 313 */     if (ammoStack.func_77978_p() != null && ammoStack.func_77973_b() instanceof ItemAmmo) {
/*     */       
/* 315 */       ItemAmmo itemAmmo = (ItemAmmo)ammoStack.func_77973_b();
/* 316 */       Integer currentMagcount = null;
/* 317 */       if (ammoStack.func_77978_p().func_74764_b("magcount")) {
/* 318 */         currentMagcount = Integer.valueOf(ammoStack.func_77978_p().func_74762_e("magcount"));
/*     */       }
/* 320 */       int currentAmmoCount = ReloadHelper.getBulletOnMag(ammoStack, currentMagcount) + countOffset;
/*     */       
/* 322 */       GlStateManager.func_179094_E();
/*     */       
/* 324 */       GlStateManager.func_179109_b((i - 120), 0.0F, 0.0F);
/*     */       
/* 326 */       Gui.func_73734_a(2 + right - 3, top, right * 2 - 18, bottom, -2147483648);
/*     */       
/* 328 */       GlStateManager.func_179094_E();
/* 329 */       RenderHelper.func_74520_c();
/* 330 */       GL11.glEnable(32826);
/* 331 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/* 332 */       drawSlotInventory(mc.field_71466_p, ammoStack, 69, j - 35);
/* 333 */       GL11.glDisable(32826);
/* 334 */       RenderHelper.func_74518_a();
/* 335 */       GlStateManager.func_179121_F();
/*     */       
/* 337 */       String color = TextFormatting.WHITE + "";
/* 338 */       if (currentAmmoCount < itemAmmo.type.ammoCapacity / 6) {
/* 339 */         color = TextFormatting.RED + "";
/* 340 */         mc.field_71466_p.func_78276_b(String.valueOf(TextFormatting.YELLOW + "[R]" + TextFormatting.WHITE + " Reload"), 10, j - 30, 16777215);
/*     */       } 
/*     */       
/* 343 */       String s = String.valueOf(color + currentAmmoCount) + "/" + itemAmmo.type.ammoCapacity;
/*     */       
/* 345 */       RenderHelperMW.renderTextWithShadow(String.valueOf(s), 85, j - 30, 16777215);
/*     */       
/* 347 */       x += 16 + mc.field_71466_p.func_78256_a(s);
/*     */       
/* 349 */       ItemGun gun = (ItemGun)stack.func_77973_b();
/* 350 */       if (GunType.getFireMode(stack) != null) {
/* 351 */         RenderHelperMW.renderCenteredTextWithShadow(String.valueOf(GunType.getFireMode(stack)), 92, j - 50, 16777215);
/*     */       }
/*     */       
/* 354 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderBullets(ItemStack stack, ItemStack ammoStack, int i, int j, int countOffset, ItemStack expectItemBullet) {
/* 359 */     Minecraft mc = Minecraft.func_71410_x();
/* 360 */     int x = 0;
/* 361 */     int top = j - 38;
/* 362 */     int left = 2;
/* 363 */     int right = Math.min(68, i / 2 - 60);
/* 364 */     int bottom = top + 22;
/*     */     
/* 366 */     ItemBullet itemBullet = null;
/* 367 */     if (expectItemBullet != null && expectItemBullet.func_77973_b() instanceof ItemBullet) {
/* 368 */       itemBullet = (ItemBullet)expectItemBullet.func_77973_b();
/*     */     }
/* 370 */     if (itemBullet == null) {
/* 371 */       itemBullet = ItemGun.getUsedBullet(stack, ((ItemGun)stack.func_77973_b()).type);
/*     */     }
/* 373 */     if (itemBullet == null) {
/*     */       return;
/*     */     }
/*     */     
/* 377 */     int currentAmmoCount = stack.func_77978_p().func_74762_e("ammocount") + countOffset;
/*     */     
/* 379 */     int maxAmmo = (((ItemGun)stack.func_77973_b()).type.internalAmmoStorage == null) ? 0 : ((ItemGun)stack.func_77973_b()).type.internalAmmoStorage.intValue();
/*     */     
/* 381 */     GlStateManager.func_179094_E();
/*     */     
/* 383 */     GlStateManager.func_179109_b((i - 120), 0.0F, 0.0F);
/*     */     
/* 385 */     Gui.func_73734_a(2 + right - 3, top, right * 2 - 18, bottom, -2147483648);
/*     */     
/* 387 */     RenderHelper.func_74520_c();
/* 388 */     GL11.glEnable(32826);
/* 389 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/* 390 */     drawSlotInventory(mc.field_71466_p, new ItemStack((Item)itemBullet), 69, j - 35);
/* 391 */     GL11.glDisable(32826);
/* 392 */     RenderHelper.func_74518_a();
/*     */     
/* 394 */     String color = TextFormatting.WHITE + "";
/* 395 */     if (currentAmmoCount < maxAmmo / 6) {
/* 396 */       color = TextFormatting.RED + "";
/* 397 */       mc.field_71466_p.func_78276_b(String.valueOf(TextFormatting.YELLOW + "[R]" + TextFormatting.WHITE + " Reload"), 10, j - 30, 16777215);
/*     */     } 
/*     */     
/* 400 */     String s = String.valueOf(color + currentAmmoCount) + "/" + maxAmmo;
/*     */     
/* 402 */     mc.field_71466_p.func_175063_a(String.valueOf(s), 85.0F, (j - 30), 16777215);
/* 403 */     x += 16 + mc.field_71466_p.func_78256_a(s);
/*     */     
/* 405 */     ItemGun gun = (ItemGun)stack.func_77973_b();
/* 406 */     if (GunType.getFireMode(stack) != null) {
/* 407 */       RenderHelperMW.renderCenteredTextWithShadow(String.valueOf(GunType.getFireMode(stack)), 92, j - 50, 16777215);
/*     */     }
/*     */     
/* 410 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public void RenderPlayerSnap(int i, int j) {
/* 414 */     GL11.glPushMatrix();
/* 415 */     GlStateManager.func_179141_d();
/* 416 */     GlStateManager.func_179147_l();
/* 417 */     this; RenderHelperMW.renderImageAlpha(0.0D, 0.0D, new ResourceLocation("modularwarfare", "textures/gui/snapshadow.png"), i, j, bulletSnapFade);
/* 418 */     GlStateManager.func_179084_k();
/* 419 */     GlStateManager.func_179118_c();
/* 420 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void drawSlotInventory(FontRenderer fontRenderer, ItemStack itemstack, int i, int j) {
/* 424 */     if (itemstack == null || itemstack.func_190926_b()) {
/*     */       return;
/*     */     }
/* 427 */     GL11.glPushMatrix();
/* 428 */     GlStateManager.func_179141_d();
/* 429 */     GlStateManager.func_179147_l();
/*     */     
/* 431 */     itemRenderer.func_175042_a(itemstack, i, j);
/* 432 */     itemRenderer.func_180453_a(fontRenderer, itemstack, i, j, null);
/* 433 */     GlStateManager.func_179140_f();
/* 434 */     GlStateManager.func_179097_i();
/* 435 */     GlStateManager.func_179084_k();
/* 436 */     GlStateManager.func_179118_c();
/* 437 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\hud\GunUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */