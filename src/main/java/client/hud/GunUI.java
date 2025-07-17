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
/* 155 */             if (ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)mc.field_71439_g) != null) {
/* 156 */               if ((ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)mc.field_71439_g)).reloading) {
/* 157 */                 showCrosshair = false;
/*     */               }
/* 159 */               if ((ClientProxy.gunEnhancedRenderer.getClientController()).INSPECT != 1.0D) {
/* 160 */                 showCrosshair = false;
/*     */               }
/*     */             } 
/*     */             
/* 164 */             if (mc.func_175606_aa() != mc.field_71439_g) {
/* 165 */               showCrosshair = false;
/*     */             }
/* 167 */             if (ModConfig.INSTANCE.hud.enable_crosshair && !(ClientRenderHooks.getAnimMachine((EntityLivingBase)mc.field_71439_g)).attachmentMode && showCrosshair && mc.field_71474_y.field_74320_O == 0 && !mc.field_71439_g.func_70051_ag() && !(ClientRenderHooks.getAnimMachine((EntityLivingBase)mc.field_71439_g)).reloading && mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemGun && 
/* 168 */               RenderParameters.collideFrontDistance <= 0.2F) {
/* 169 */               GlStateManager.func_179094_E();
/*     */               
/* 171 */               if (ModConfig.INSTANCE.hud.dynamic_crosshair) {
/* 172 */                 float gunRotX = RenderParameters.GUN_ROT_X_LAST + (RenderParameters.GUN_ROT_X - RenderParameters.GUN_ROT_X_LAST) * RenderParameters.smoothing;
/* 173 */                 float gunRotY = RenderParameters.GUN_ROT_Y_LAST + (RenderParameters.GUN_ROT_Y - RenderParameters.GUN_ROT_Y_LAST) * RenderParameters.smoothing;
/* 174 */                 GL11.glRotatef(gunRotX, 0.0F, -1.0F, 0.0F);
/* 175 */                 GL11.glRotatef(gunRotY, 1.0F, 0.0F, 1.0F);
/*     */               } 
/*     */               
/* 178 */               float accuracy = RayUtil.calculateAccuracyClient((ItemGun)mc.field_71439_g.func_184614_ca().func_77973_b(), (EntityPlayer)mc.field_71439_g);
/* 179 */               int move = Math.max(0, (int)(accuracy * 3.0F));
/* 180 */               mc.field_71446_o.func_110577_a(crosshair);
/* 181 */               int xPos = width / 2;
/* 182 */               int yPos = height / 2;
/*     */ 
/*     */               
/* 185 */               GlStateManager.func_179109_b(xPos, yPos, 0.0F);
/* 186 */               if (ModularWarfare.isLoadedModularMovements) {
/* 187 */                 GL11.glRotatef(15.0F * ClientLitener.cameraProbeOffset, 0.0F, 0.0F, 1.0F);
/*     */               }
/* 189 */               GlStateManager.func_179147_l();
/* 190 */               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 191 */               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 192 */               Gui.func_146110_a(0, 0, 1.0F, 1.0F, 1, 1, 16.0F, 16.0F);
/* 193 */               Gui.func_146110_a(0, 0 + move, 1.0F, 1.0F, 1, 4, 16.0F, 16.0F);
/* 194 */               Gui.func_146110_a(0, 0 - move - 3, 1.0F, 1.0F, 1, 4, 16.0F, 16.0F);
/* 195 */               Gui.func_146110_a(0 + move, 0, 1.0F, 1.0F, 4, 1, 16.0F, 16.0F);
/* 196 */               Gui.func_146110_a(0 - move - 3, 0, 1.0F, 1.0F, 4, 1, 16.0F, 16.0F);
/* 197 */               GlStateManager.func_179084_k();
/*     */               
/* 199 */               GlStateManager.func_179121_F();
/*     */             } 
/*     */             
/* 202 */             GlStateManager.func_179121_F();
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/*     */     } 
/* 210 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 211 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */   
/*     */   private void RenderHitMarker(Tessellator tessellator, int i, int j) {
/* 216 */     Minecraft mc = Minecraft.func_71410_x();
/*     */     
/* 218 */     if (hitMarkerTime > 0) {
/* 219 */       GlStateManager.func_179094_E();
/*     */       
/* 221 */       if (!hitMarkerheadshot) {
/* 222 */         mc.field_71446_o.func_110577_a(hitMarker);
/*     */       } else {
/* 224 */         mc.field_71446_o.func_110577_a(hitMarkerHS);
/*     */       } 
/*     */ 
/*     */       
/* 228 */       GlStateManager.func_179141_d();
/* 229 */       GlStateManager.func_179147_l();
/* 230 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, Math.max((hitMarkerTime - 10.0F + ClientProxy.renderHooks.partialTicks) / 10.0F, 0.0F));
/*     */       
/* 232 */       double zLevel = 0.0D;
/*     */       
/* 234 */       tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*     */       
/* 236 */       tessellator.func_178180_c().func_181662_b((i / 2) - 4.0D, (j / 2) + 5.0D, zLevel).func_187315_a(0.0D, 0.5625D).func_181675_d();
/* 237 */       tessellator.func_178180_c().func_181662_b((i / 2) + 5.0D, (j / 2) + 5.0D, zLevel).func_187315_a(0.5625D, 0.5625D).func_181675_d();
/* 238 */       tessellator.func_178180_c().func_181662_b((i / 2) + 5.0D, (j / 2) - 4.0D, zLevel).func_187315_a(0.5625D, 0.0D).func_181675_d();
/* 239 */       tessellator.func_178180_c().func_181662_b((i / 2) - 4.0D, (j / 2) - 4.0D, zLevel).func_187315_a(0.0D, 0.0D).func_181675_d();
/*     */       
/* 241 */       tessellator.func_78381_a();
/*     */       
/* 243 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 244 */       GlStateManager.func_179118_c();
/* 245 */       GlStateManager.func_179084_k();
/*     */       
/* 247 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void RenderPlayerAmmo(int i, int j) {
/* 252 */     Minecraft mc = Minecraft.func_71410_x();
/*     */     
/* 254 */     ItemStack stack = mc.field_71439_g.func_184586_b(EnumHand.MAIN_HAND);
/* 255 */     if (stack != null && stack.func_77973_b() instanceof ItemGun)
/*     */     {
/* 257 */       if (stack.func_77978_p() != null) {
/* 258 */         ItemStack ammoStack = new ItemStack(stack.func_77978_p().func_74775_l("ammo"));
/* 259 */         GunType type = ((ItemGun)stack.func_77973_b()).type;
/* 260 */         if (type.animationType.equals(WeaponAnimationType.BASIC)) {
/* 261 */           ammoStack.func_77964_b(0);
/* 262 */           if (ItemGun.hasAmmoLoaded(stack)) {
/* 263 */             renderAmmo(stack, ammoStack, i, j, 0);
/* 264 */           } else if (ItemGun.getUsedBullet(stack, ((ItemGun)stack.func_77973_b()).type) != null) {
/* 265 */             renderBullets(stack, null, i, j, 0, null);
/*     */           }
/*     */         
/* 268 */         } else if (ClientProxy.gunEnhancedRenderer.getClientController() != null) {
/* 269 */           EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)mc.field_71439_g);
/* 270 */           AnimationType reloadAni = anim.getReloadAnimationType();
/* 271 */           if (type.acceptedAmmo != null) {
/* 272 */             ammoStack = ClientProxy.gunEnhancedRenderer.getClientController().getRenderAmmo(ammoStack);
/* 273 */             ammoStack.func_77964_b(0);
/* 274 */             if (reloadAni == AnimationType.RELOAD_FIRST || reloadAni == AnimationType.RELOAD_FIRST_QUICKLY || reloadAni == AnimationType.UNLOAD) {
/* 275 */               ammoStack = ItemStack.field_190927_a;
/*     */             }
/* 277 */             renderAmmo(stack, ammoStack, i, j, 0);
/*     */           } else {
/* 279 */             boolean flag = true;
/* 280 */             ItemStack bulletStack = new ItemStack(stack.func_77978_p().func_74775_l("bullet"));
/* 281 */             if (anim.reloading) {
/* 282 */               bulletStack = ClientProxy.gunEnhancedRenderer.getClientController().getRenderAmmo(bulletStack);
/* 283 */               if (ClientProxy.gunEnhancedRenderer.getClientController().getPlayingAnimation() == AnimationType.POST_UNLOAD);
/*     */             } 
/*     */ 
/*     */             
/* 287 */             bulletStack.func_77964_b(0);
/* 288 */             int offset = anim.getAmmoCountOffset(false);
/* 289 */             if (!anim.reloading) {
/* 290 */               offset = 0;
/*     */             }
/* 292 */             if (flag) {
/* 293 */               renderBullets(stack, null, i, j, offset, bulletStack);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAmmo(ItemStack stack, ItemStack ammoStack, int i, int j, int countOffset) {
/* 303 */     Minecraft mc = Minecraft.func_71410_x();
/* 304 */     int x = 0;
/* 305 */     int top = j - 38;
/* 306 */     int left = 2;
/* 307 */     int right = Math.min(68, i / 2 - 60);
/* 308 */     int bottom = top + 22;
/*     */     
/* 310 */     if (ammoStack.func_77978_p() != null && ammoStack.func_77973_b() instanceof ItemAmmo) {
/*     */       
/* 312 */       ItemAmmo itemAmmo = (ItemAmmo)ammoStack.func_77973_b();
/* 313 */       Integer currentMagcount = null;
/* 314 */       if (ammoStack.func_77978_p().func_74764_b("magcount")) {
/* 315 */         currentMagcount = Integer.valueOf(ammoStack.func_77978_p().func_74762_e("magcount"));
/*     */       }
/* 317 */       int currentAmmoCount = ReloadHelper.getBulletOnMag(ammoStack, currentMagcount) + countOffset;
/*     */       
/* 319 */       GlStateManager.func_179094_E();
/*     */       
/* 321 */       GlStateManager.func_179109_b((i - 120), 0.0F, 0.0F);
/*     */       
/* 323 */       Gui.func_73734_a(2 + right - 3, top, right * 2 - 18, bottom, -2147483648);
/*     */       
/* 325 */       GlStateManager.func_179094_E();
/* 326 */       RenderHelper.func_74520_c();
/* 327 */       GL11.glEnable(32826);
/* 328 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/* 329 */       drawSlotInventory(mc.field_71466_p, ammoStack, 69, j - 35);
/* 330 */       GL11.glDisable(32826);
/* 331 */       RenderHelper.func_74518_a();
/* 332 */       GlStateManager.func_179121_F();
/*     */       
/* 334 */       String color = TextFormatting.WHITE + "";
/* 335 */       if (currentAmmoCount < itemAmmo.type.ammoCapacity / 6) {
/* 336 */         color = TextFormatting.RED + "";
/* 337 */         mc.field_71466_p.func_78276_b(String.valueOf(TextFormatting.YELLOW + "[R]" + TextFormatting.WHITE + " Reload"), 10, j - 30, 16777215);
/*     */       } 
/*     */       
/* 340 */       String s = String.valueOf(color + currentAmmoCount) + "/" + itemAmmo.type.ammoCapacity;
/*     */       
/* 342 */       RenderHelperMW.renderTextWithShadow(String.valueOf(s), 85, j - 30, 16777215);
/*     */       
/* 344 */       x += 16 + mc.field_71466_p.func_78256_a(s);
/*     */       
/* 346 */       ItemGun gun = (ItemGun)stack.func_77973_b();
/* 347 */       if (GunType.getFireMode(stack) != null) {
/* 348 */         RenderHelperMW.renderCenteredTextWithShadow(String.valueOf(GunType.getFireMode(stack)), 92, j - 50, 16777215);
/*     */       }
/*     */       
/* 351 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderBullets(ItemStack stack, ItemStack ammoStack, int i, int j, int countOffset, ItemStack expectItemBullet) {
/* 356 */     Minecraft mc = Minecraft.func_71410_x();
/* 357 */     int x = 0;
/* 358 */     int top = j - 38;
/* 359 */     int left = 2;
/* 360 */     int right = Math.min(68, i / 2 - 60);
/* 361 */     int bottom = top + 22;
/*     */     
/* 363 */     ItemBullet itemBullet = null;
/* 364 */     if (expectItemBullet != null && expectItemBullet.func_77973_b() instanceof ItemBullet) {
/* 365 */       itemBullet = (ItemBullet)expectItemBullet.func_77973_b();
/*     */     }
/* 367 */     if (itemBullet == null) {
/* 368 */       itemBullet = ItemGun.getUsedBullet(stack, ((ItemGun)stack.func_77973_b()).type);
/*     */     }
/* 370 */     if (itemBullet == null) {
/*     */       return;
/*     */     }
/*     */     
/* 374 */     int currentAmmoCount = stack.func_77978_p().func_74762_e("ammocount") + countOffset;
/*     */     
/* 376 */     int maxAmmo = (((ItemGun)stack.func_77973_b()).type.internalAmmoStorage == null) ? 0 : ((ItemGun)stack.func_77973_b()).type.internalAmmoStorage.intValue();
/*     */     
/* 378 */     GlStateManager.func_179094_E();
/*     */     
/* 380 */     GlStateManager.func_179109_b((i - 120), 0.0F, 0.0F);
/*     */     
/* 382 */     Gui.func_73734_a(2 + right - 3, top, right * 2 - 18, bottom, -2147483648);
/*     */     
/* 384 */     RenderHelper.func_74520_c();
/* 385 */     GL11.glEnable(32826);
/* 386 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/* 387 */     drawSlotInventory(mc.field_71466_p, new ItemStack((Item)itemBullet), 69, j - 35);
/* 388 */     GL11.glDisable(32826);
/* 389 */     RenderHelper.func_74518_a();
/*     */     
/* 391 */     String color = TextFormatting.WHITE + "";
/* 392 */     if (currentAmmoCount < maxAmmo / 6) {
/* 393 */       color = TextFormatting.RED + "";
/* 394 */       mc.field_71466_p.func_78276_b(String.valueOf(TextFormatting.YELLOW + "[R]" + TextFormatting.WHITE + " Reload"), 10, j - 30, 16777215);
/*     */     } 
/*     */     
/* 397 */     String s = String.valueOf(color + currentAmmoCount) + "/" + maxAmmo;
/*     */     
/* 399 */     mc.field_71466_p.func_175063_a(String.valueOf(s), 85.0F, (j - 30), 16777215);
/* 400 */     x += 16 + mc.field_71466_p.func_78256_a(s);
/*     */     
/* 402 */     ItemGun gun = (ItemGun)stack.func_77973_b();
/* 403 */     if (GunType.getFireMode(stack) != null) {
/* 404 */       RenderHelperMW.renderCenteredTextWithShadow(String.valueOf(GunType.getFireMode(stack)), 92, j - 50, 16777215);
/*     */     }
/*     */     
/* 407 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public void RenderPlayerSnap(int i, int j) {
/* 411 */     GL11.glPushMatrix();
/* 412 */     GlStateManager.func_179141_d();
/* 413 */     GlStateManager.func_179147_l();
/* 414 */     this; RenderHelperMW.renderImageAlpha(0.0D, 0.0D, new ResourceLocation("modularwarfare", "textures/gui/snapshadow.png"), i, j, bulletSnapFade);
/* 415 */     GlStateManager.func_179084_k();
/* 416 */     GlStateManager.func_179118_c();
/* 417 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void drawSlotInventory(FontRenderer fontRenderer, ItemStack itemstack, int i, int j) {
/* 421 */     if (itemstack == null || itemstack.func_190926_b()) {
/*     */       return;
/*     */     }
/* 424 */     GL11.glPushMatrix();
/* 425 */     GlStateManager.func_179141_d();
/* 426 */     GlStateManager.func_179147_l();
/*     */     
/* 428 */     itemRenderer.func_175042_a(itemstack, i, j);
/* 429 */     itemRenderer.func_180453_a(fontRenderer, itemstack, i, j, null);
/* 430 */     GlStateManager.func_179140_f();
/* 431 */     GlStateManager.func_179097_i();
/* 432 */     GlStateManager.func_179084_k();
/* 433 */     GlStateManager.func_179118_c();
/* 434 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\hud\GunUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */