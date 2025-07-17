/*      */ package com.modularwarfare.client.fpp.enhanced.renderers;
/*      */ 
/*      */ import com.modularwarfare.ModConfig;
/*      */ import com.modularwarfare.ModularWarfare;
/*      */ import com.modularwarfare.api.RenderHandFisrtPersonEnhancedEvent;
/*      */ import com.modularwarfare.api.RenderHandSleeveEnhancedEvent;
/*      */ import com.modularwarfare.client.ClientProxy;
/*      */ import com.modularwarfare.client.ClientRenderHooks;
/*      */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderType;
/*      */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderer;
/*      */ import com.modularwarfare.client.fpp.basic.renderers.RenderParameters;
/*      */ import com.modularwarfare.client.fpp.enhanced.AnimationType;
/*      */ import com.modularwarfare.client.fpp.enhanced.animation.AnimationController;
/*      */ import com.modularwarfare.client.fpp.enhanced.animation.EnhancedStateMachine;
/*      */ import com.modularwarfare.client.fpp.enhanced.configs.EnhancedRenderConfig;
/*      */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*      */ import com.modularwarfare.client.fpp.enhanced.configs.RenderType;
/*      */ import com.modularwarfare.client.fpp.enhanced.models.EnhancedModel;
/*      */ import com.modularwarfare.client.fpp.enhanced.models.ModelEnhancedGun;
/*      */ import com.modularwarfare.client.handler.ClientTickHandler;
/*      */ import com.modularwarfare.client.model.ModelAttachment;
/*      */ import com.modularwarfare.client.model.ModelCustomArmor;
/*      */ import com.modularwarfare.client.scope.ScopeUtils;
/*      */ import com.modularwarfare.client.shader.Programs;
/*      */ import com.modularwarfare.common.armor.ItemMWArmor;
/*      */ import com.modularwarfare.common.guns.AmmoType;
/*      */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*      */ import com.modularwarfare.common.guns.AttachmentType;
/*      */ import com.modularwarfare.common.guns.BulletType;
/*      */ import com.modularwarfare.common.guns.GunType;
/*      */ import com.modularwarfare.common.guns.ItemAmmo;
/*      */ import com.modularwarfare.common.guns.ItemAttachment;
/*      */ import com.modularwarfare.common.guns.ItemBullet;
/*      */ import com.modularwarfare.common.guns.ItemGun;
/*      */ import com.modularwarfare.common.guns.WeaponFireMode;
/*      */ import com.modularwarfare.common.guns.WeaponScopeModeType;
/*      */ import com.modularwarfare.common.handler.data.VarBoolean;
/*      */ import com.modularwarfare.common.textures.TextureType;
/*      */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*      */ import com.modularwarfare.utility.OptifineHelper;
/*      */ import com.modularwarfare.utility.ReloadHelper;
/*      */ import com.modularwarfare.utility.maths.Interpolation;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.ShortBuffer;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Random;
/*      */ import mchhui.hegltf.DataAnimation;
/*      */ import mchhui.hegltf.DataNode;
/*      */ import mchhui.hegltf.GltfRenderModel;
/*      */ import mchhui.modularmovements.tactical.client.ClientLitener;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.AbstractClientPlayer;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.model.ModelBiped;
/*      */ import net.minecraft.client.model.ModelPlayer;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.entity.Render;
/*      */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*      */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumHandSide;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Timer;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraftforge.common.MinecraftForge;
/*      */ import net.minecraftforge.fml.common.eventhandler.Event;
/*      */ import net.minecraftforge.fml.relauncher.ReflectionHelper;
/*      */ import net.minecraftforge.fml.relauncher.Side;
/*      */ import net.minecraftforge.fml.relauncher.SideOnly;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import org.joml.Matrix4f;
/*      */ import org.joml.Matrix4fc;
/*      */ import org.joml.Quaternionf;
/*      */ import org.joml.Quaternionfc;
/*      */ import org.joml.Vector3f;
/*      */ import org.joml.Vector3fc;
/*      */ import org.lwjgl.BufferUtils;
/*      */ import org.lwjgl.opengl.ContextCapabilities;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL20;
/*      */ import org.lwjgl.opengl.GL30;
/*      */ import org.lwjgl.opengl.GL43;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.util.vector.Matrix3f;
/*      */ import org.lwjgl.util.vector.Matrix4f;
/*      */ import org.lwjgl.util.vector.Quaternion;
/*      */ import org.lwjgl.util.vector.ReadableVector3f;
/*      */ import org.lwjgl.util.vector.Vector3f;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RenderGunEnhanced
/*      */   extends CustomItemRenderer
/*      */ {
/*  106 */   public static float sizeFactor = 10000.0F;
/*      */   
/*      */   public static boolean debug = false;
/*      */   public static boolean debug1 = false;
/*      */   public static final float PI = 3.1415927F;
/*  111 */   private ShortBuffer pixelBuffer = null;
/*      */   
/*      */   private int lastWidth;
/*      */   
/*      */   private int lastHeight;
/*      */   private Timer timer;
/*  117 */   private AnimationController controller = new AnimationController(null, null);
/*      */   
/*  119 */   private HashMap<String, AnimationController> otherControllers = new HashMap<>();
/*      */   
/*  121 */   public FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
/*      */   
/*      */   private boolean renderingMagazine = true;
/*      */   
/*      */   public static final int BULLET_MAX_RENDER = 256;
/*  126 */   private static float theata90 = (float)Math.toRadians(90.0D);
/*  127 */   public static final HashSet<String> DEFAULT_EXCEPT = new HashSet<>();
/*  128 */   private static final String[] LEFT_HAND_PART = new String[] { "leftArmModel", "leftArmLayerModel" };
/*      */ 
/*      */   
/*  131 */   private static final String[] LEFT_SLIM_HAND_PART = new String[] { "leftArmSlimModel", "leftArmLayerSlimModel" };
/*      */ 
/*      */   
/*  134 */   private static final String[] RIGHT_HAND_PART = new String[] { "rightArmModel", "rightArmLayerModel" };
/*      */ 
/*      */   
/*  137 */   private static final String[] RIGHT_SLIM_HAND_PART = new String[] { "rightArmSlimModel", "rightArmLayerSlimModel" };
/*      */ 
/*      */   
/*      */   static {
/*  141 */     for (String str : ModConfig.INSTANCE.guns.anim_guns_show_default_objects)
/*  142 */       DEFAULT_EXCEPT.add(str); 
/*      */     int i;
/*  144 */     for (i = 0; i < 256; i++) {
/*  145 */       DEFAULT_EXCEPT.add("bulletModel_" + i);
/*      */     }
/*  147 */     for (i = 0; i < 256; i++) {
/*  148 */       DEFAULT_EXCEPT.add("shellModel_" + i);
/*      */     }
/*      */   }
/*      */   
/*      */   public AnimationController getClientController() {
/*  153 */     return this.controller;
/*      */   }
/*      */   
/*      */   public HashMap<String, AnimationController> getOtherControllers() {
/*  157 */     return this.otherControllers;
/*      */   }
/*      */   
/*      */   public AnimationController getController(EntityLivingBase player, GunEnhancedRenderConfig config) {
/*  161 */     if (player == (Minecraft.func_71410_x()).field_71439_g) {
/*  162 */       if (this.controller.player != player || (config != null && this.controller.getConfig() != config)) {
/*  163 */         this.controller = new AnimationController(player, config);
/*      */       }
/*  165 */       return this.controller;
/*      */     } 
/*  167 */     String name = player.func_70005_c_();
/*  168 */     if (config == null && !this.otherControllers.containsKey(name)) {
/*  169 */       return null;
/*      */     }
/*  171 */     if (!this.otherControllers.containsKey(name)) {
/*  172 */       this.otherControllers.put(name, new AnimationController(player, config));
/*      */     }
/*  174 */     if (config != null && ((AnimationController)this.otherControllers.get(name)).getConfig() != config) {
/*  175 */       this.otherControllers.put(name, new AnimationController(player, config));
/*      */     }
/*  177 */     return this.otherControllers.get(name);
/*      */   }
/*      */   
/*      */   public void renderItem(CustomItemRenderType type, EnumHand hand, ItemStack item, Object... data) {
/*  181 */     if (!(item.func_77973_b() instanceof ItemGun)) {
/*      */       return;
/*      */     }
/*  184 */     GunType gunType = ((ItemGun)item.func_77973_b()).type;
/*  185 */     if (gunType == null) {
/*      */       return;
/*      */     }
/*  188 */     ModelEnhancedGun model = (ModelEnhancedGun)gunType.enhancedModel;
/*  189 */     if (!(Minecraft.func_71410_x().func_175606_aa() instanceof AbstractClientPlayer)) {
/*      */       return;
/*      */     }
/*      */     
/*  193 */     if (model == null) {
/*      */       return;
/*      */     }
/*  196 */     Render<AbstractClientPlayer> render = Minecraft.func_71410_x().func_175598_ae().func_78713_a((Entity)(Minecraft.func_71410_x()).field_71439_g);
/*  197 */     RenderPlayer renderplayer = (RenderPlayer)render;
/*  198 */     ModelPlayer modelPlayer = renderplayer.func_177087_b();
/*  199 */     ClientProxy.renderHooks.hidePlayerModel((AbstractClientPlayer)Minecraft.func_71410_x().func_175606_aa(), renderplayer);
/*      */     
/*  201 */     GunEnhancedRenderConfig config = (GunEnhancedRenderConfig)model.config;
/*  202 */     if (this.controller == null || this.controller.getConfig() != config || this.controller.player != (Minecraft.func_71410_x()).field_71439_g) {
/*  203 */       this.controller = new AnimationController((EntityLivingBase)(Minecraft.func_71410_x()).field_71439_g, config);
/*      */     }
/*      */ 
/*      */     
/*  207 */     if (this.timer == null) {
/*  208 */       this.timer = (Timer)ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.func_71410_x(), "timer", "field_71428_T");
/*      */     }
/*      */     
/*  211 */     if (!item.func_77942_o()) {
/*      */       return;
/*      */     }
/*  214 */     float partialTicks = this.timer.field_194147_b;
/*      */     
/*  216 */     EntityPlayerSP player = (Minecraft.func_71410_x()).field_71439_g;
/*      */     
/*  218 */     EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine((EntityLivingBase)player);
/*      */     
/*  220 */     Matrix4f mat = new Matrix4f();
/*      */     
/*  222 */     GlStateManager.func_179128_n(5888);
/*  223 */     GlStateManager.func_179096_D();
/*      */     
/*  225 */     float bx = OpenGlHelper.lastBrightnessX;
/*  226 */     float by = OpenGlHelper.lastBrightnessY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  238 */     mat.rotate(toRadians(90.0F), new Vector3f(0.0F, 1.0F, 0.0F));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  243 */     mat.scale(new Vector3f(1.0F / sizeFactor, 1.0F / sizeFactor, 1.0F / sizeFactor));
/*      */     
/*  245 */     float f5 = player.field_71164_i + (player.field_71155_g - player.field_71164_i) * partialTicks;
/*  246 */     float f6 = player.field_71163_h + (player.field_71154_f - player.field_71163_h) * partialTicks;
/*  247 */     mat.rotate(toRadians((player.field_70125_A - f5) * 0.1F), new Vector3f(1.0F, 0.0F, 0.0F));
/*  248 */     mat.rotate(toRadians((player.field_70177_z - f6) * 0.1F), new Vector3f(0.0F, 1.0F, 0.0F));
/*      */     
/*  250 */     float rotateX = 0.0F;
/*  251 */     float adsModifier = (float)(0.949999988079071D - this.controller.ADS);
/*  252 */     if (player.func_184613_cA()) {
/*  253 */       adsModifier = 0.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  259 */     mat.rotate(toRadians(90.0F), new Vector3f(0.0F, 1.0F, 0.0F));
/*  260 */     mat.translate(new Vector3f(config.global.globalTranslate.x, config.global.globalTranslate.y, config.global.globalTranslate.z));
/*  261 */     mat.scale(new Vector3f(config.global.globalScale.x, config.global.globalScale.y, config.global.globalScale.z));
/*  262 */     mat.rotate(toRadians(-90.0F), new Vector3f(0.0F, 1.0F, 0.0F));
/*  263 */     mat.rotate(config.global.globalRotate.y / 180.0F * 3.14F, new Vector3f(0.0F, 1.0F, 0.0F));
/*  264 */     mat.rotate(config.global.globalRotate.x / 180.0F * 3.14F, new Vector3f(1.0F, 0.0F, 0.0F));
/*  265 */     mat.rotate(config.global.globalRotate.z / 180.0F * 3.14F, new Vector3f(0.0F, 0.0F, 1.0F));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  270 */     float gunRotX = RenderParameters.GUN_ROT_X_LAST + (RenderParameters.GUN_ROT_X - RenderParameters.GUN_ROT_X_LAST) * ClientProxy.renderHooks.partialTicks;
/*      */     
/*  272 */     float gunRotY = RenderParameters.GUN_ROT_Y_LAST + (RenderParameters.GUN_ROT_Y - RenderParameters.GUN_ROT_Y_LAST) * ClientProxy.renderHooks.partialTicks;
/*      */     
/*  274 */     mat.rotate(toRadians(gunRotX), new Vector3f(0.0F, -1.0F, 0.0F));
/*  275 */     mat.rotate(toRadians(gunRotY), new Vector3f(0.0F, 0.0F, -1.0F));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  280 */     float f1 = player.field_70140_Q - player.field_70141_P;
/*  281 */     float f2 = -(player.field_70140_Q + f1 * partialTicks);
/*  282 */     float f3 = player.field_71107_bF + (player.field_71109_bG - player.field_71107_bF) * partialTicks;
/*  283 */     float f4 = player.field_70727_aS + (player.field_70726_aT - player.field_70727_aS) * partialTicks;
/*  284 */     mat.translate(new Vector3f(0.0F, adsModifier * Interpolation.SINE_IN.interpolate(0.0F, -0.2F * (1.0F - (float)this.controller.ADS), RenderParameters.GUN_BALANCING_Y), 0.0F));
/*  285 */     mat.translate(new Vector3f(0.0F, adsModifier * (float)(0.05000000074505806D * Math.sin((RenderParameters.SMOOTH_SWING / 10.0F)) * RenderParameters.GUN_BALANCING_Y), 0.0F));
/*      */     
/*  287 */     mat.rotate(toRadians(adsModifier * 0.1F * Interpolation.SINE_OUT.interpolate(-RenderParameters.GUN_BALANCING_Y, RenderParameters.GUN_BALANCING_Y, adsModifier * MathHelper.func_76126_a(f2 * 3.1415927F))), new Vector3f(0.0F, 1.0F, 0.0F));
/*      */     
/*  289 */     mat.translate(new Vector3f(adsModifier * MathHelper.func_76126_a(f2 * 3.1415927F) * f3 * 0.5F, adsModifier * -Math.abs(MathHelper.func_76134_b(f2 * 3.1415927F) * f3), 0.0F));
/*  290 */     mat.rotate(toRadians(adsModifier * MathHelper.func_76126_a(f2 * 3.1415927F) * f3 * 3.0F), new Vector3f(0.0F, 0.0F, 1.0F));
/*  291 */     mat.rotate(toRadians(adsModifier * Math.abs(MathHelper.func_76134_b(f2 * 3.1415927F - 0.2F) * f3) * 5.0F), new Vector3f(1.0F, 0.0F, 0.0F));
/*  292 */     mat.rotate(toRadians(adsModifier * f4), new Vector3f(1.0F, 0.0F, 0.0F));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  297 */     float collideFrontDistanceAlpha = RenderParameters.collideFrontDistance;
/*  298 */     float rotateZ = 10.0F * collideFrontDistanceAlpha;
/*  299 */     float translateX = -(15.0F * collideFrontDistanceAlpha);
/*  300 */     float translateY = -(2.0F * collideFrontDistanceAlpha);
/*  301 */     mat.translate(new Vector3f(0.0F, translateY, 0.0F));
/*  302 */     mat.rotate(toRadians(rotateZ), new Vector3f(0.0F, 0.0F, 1.0F));
/*  303 */     mat.translate(new Vector3f(translateX, 0.0F, 0.0F));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  308 */     RenderParameters.VAL = (float)(Math.sin((RenderParameters.SMOOTH_SWING / 100.0F)) * 8.0D);
/*  309 */     RenderParameters.VAL2 = (float)(Math.sin((RenderParameters.SMOOTH_SWING / 80.0F)) * 8.0D);
/*  310 */     RenderParameters.VALROT = (float)(Math.sin((RenderParameters.SMOOTH_SWING / 90.0F)) * 1.2000000476837158D);
/*  311 */     mat.translate(new Vector3f(0.0F, RenderParameters.VAL / 500.0F * (0.95F - (float)this.controller.ADS), RenderParameters.VAL2 / 500.0F * (0.95F - (float)this.controller.ADS)));
/*  312 */     mat.rotate(toRadians(adsModifier * RenderParameters.VALROT), new Vector3f(1.0F, 0.0F, 0.0F));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  317 */     mat.translate(new Vector3f((float)((0.1F * RenderParameters.GUN_BALANCING_X) * Math.cos(Math.PI * RenderParameters.SMOOTH_SWING / 50.0D)) * (1.0F - (float)this.controller.ADS), 0.0F, 0.0F));
/*  318 */     rotateX -= RenderParameters.GUN_BALANCING_X * 4.0F + (float)(RenderParameters.GUN_BALANCING_X * Math.sin(Math.PI * RenderParameters.SMOOTH_SWING / 35.0D));
/*  319 */     rotateX -= (float)Math.sin(Math.PI * RenderParameters.GUN_BALANCING_X);
/*  320 */     rotateX -= RenderParameters.GUN_BALANCING_X * 0.4F;
/*      */ 
/*      */ 
/*      */     
/*  324 */     if (ModularWarfare.isLoadedModularMovements) {
/*  325 */       rotateX += 15.0F * ClientLitener.cameraProbeOffset;
/*      */     }
/*  327 */     mat.rotate(toRadians(rotateX), new Vector3f(1.0F, 0.0F, 0.0F));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  332 */     RenderParameters.VALSPRINT = (float)Math.cos(this.controller.SPRINT_RANDOM * 2.0D * Math.PI) * gunType.moveSpeedModifier;
/*  333 */     RenderParameters.VALSPRINT2 = (float)Math.sin(this.controller.SPRINT_RANDOM * 2.0D * Math.PI) * gunType.moveSpeedModifier;
/*      */     
/*  335 */     Vector3f customSprintRotation = new Vector3f();
/*  336 */     Vector3f customSprintTranslate = new Vector3f();
/*  337 */     float springModifier = (float)(0.800000011920929D - this.controller.ADS);
/*  338 */     mat.rotate(toRadians(0.2F * RenderParameters.VALSPRINT * springModifier), new Vector3f(1.0F, 0.0F, 0.0F));
/*  339 */     mat.rotate(toRadians(RenderParameters.VALSPRINT2 * springModifier), new Vector3f(0.0F, 0.0F, 1.0F));
/*  340 */     mat.translate(new Vector3f(RenderParameters.VALSPRINT * 0.2F * springModifier, 0.0F, RenderParameters.VALSPRINT2 * 0.2F * springModifier));
/*      */     
/*  342 */     customSprintRotation = new Vector3f(config.sprint.sprintRotate.x * (float)this.controller.SPRINT, config.sprint.sprintRotate.y * (float)this.controller.SPRINT, config.sprint.sprintRotate.z * (float)this.controller.SPRINT);
/*  343 */     customSprintTranslate = new Vector3f(config.sprint.sprintTranslate.x * (float)this.controller.SPRINT, config.sprint.sprintTranslate.y * (float)this.controller.SPRINT, config.sprint.sprintTranslate.z * (float)this.controller.SPRINT);
/*      */     
/*  345 */     customSprintRotation.scale(1.0F - (float)this.controller.ADS);
/*  346 */     customSprintTranslate.scale(1.0F - (float)this.controller.ADS);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  351 */     Vector3f customHipRotation = new Vector3f(config.aim.rotateHipPosition.x, config.aim.rotateHipPosition.y, config.aim.rotateHipPosition.z);
/*  352 */     Vector3f customHipTranslate = new Vector3f(config.aim.translateHipPosition.x, config.aim.translateHipPosition.y, config.aim.translateHipPosition.z);
/*      */     
/*  354 */     Vector3f customAimRotation = new Vector3f(config.aim.rotateAimPosition.x * (float)this.controller.ADS, config.aim.rotateAimPosition.y * (float)this.controller.ADS, config.aim.rotateAimPosition.z * (float)this.controller.ADS);
/*  355 */     Vector3f customAimTranslate = new Vector3f(config.aim.translateAimPosition.x * (float)this.controller.ADS, config.aim.translateAimPosition.y * (float)this.controller.ADS, config.aim.translateAimPosition.z * (float)this.controller.ADS);
/*      */     
/*  357 */     mat.rotate(toRadians(customHipRotation.x + customSprintRotation.x + customAimRotation.x), new Vector3f(1.0F, 0.0F, 0.0F));
/*  358 */     mat.rotate(toRadians(customHipRotation.y + customSprintRotation.y + customAimRotation.y), new Vector3f(0.0F, 1.0F, 0.0F));
/*  359 */     mat.rotate(toRadians(customHipRotation.z + customSprintRotation.z + customAimRotation.z), new Vector3f(0.0F, 0.0F, 1.0F));
/*  360 */     mat.translate(new Vector3f(customHipTranslate.x + customSprintTranslate.x + customAimTranslate.x, customHipTranslate.y + customSprintTranslate.y + customAimTranslate.y, customHipTranslate.z + customSprintTranslate.z + customAimTranslate.z));
/*      */     
/*  362 */     float renderInsideGunOffset = 5.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  367 */     ItemAttachment sight = null;
/*  368 */     if (GunType.getAttachment(item, AttachmentPresetEnum.Sight) != null) {
/*  369 */       sight = (ItemAttachment)GunType.getAttachment(item, AttachmentPresetEnum.Sight).func_77973_b();
/*  370 */       GunEnhancedRenderConfig.Attachment sightConfig = (GunEnhancedRenderConfig.Attachment)config.attachment.get(sight.type.internalName);
/*  371 */       if (sightConfig != null) {
/*      */         
/*  373 */         float ads = (float)this.controller.ADS;
/*  374 */         mat.translate((Vector3f)(new Vector3f((ReadableVector3f)sightConfig.sightAimPosOffset)).scale(ads));
/*  375 */         mat.rotate(ads * sightConfig.sightAimRotOffset.y * 3.14F / 180.0F, new Vector3f(0.0F, 1.0F, 0.0F));
/*  376 */         mat.rotate(ads * sightConfig.sightAimRotOffset.x * 3.14F / 180.0F, new Vector3f(1.0F, 0.0F, 0.0F));
/*  377 */         mat.rotate(ads * sightConfig.sightAimRotOffset.z * 3.14F / 180.0F, new Vector3f(0.0F, 0.0F, 1.0F));
/*  378 */         renderInsideGunOffset = sightConfig.renderInsideGunOffset;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  386 */     float min = -1.5F;
/*  387 */     float max = 1.5F;
/*  388 */     float randomNum = (new Random()).nextFloat();
/*  389 */     float randomShake = min + randomNum * (max - min);
/*      */     
/*  391 */     float alpha = anim.lastGunRecoil + (anim.gunRecoil - anim.lastGunRecoil) * partialTicks;
/*  392 */     float bounce = Interpolation.BOUNCE_INOUT.interpolate(0.0F, 1.0F, alpha);
/*  393 */     float elastic = Interpolation.ELASTIC_OUT.interpolate(0.0F, 1.0F, alpha);
/*      */     
/*  395 */     float sin = MathHelper.func_76126_a((float)(6.283185307179586D * alpha));
/*      */     
/*  397 */     float sin10 = MathHelper.func_76126_a((float)(6.283185307179586D * alpha)) * 0.05F;
/*      */ 
/*      */     
/*  400 */     float modelBackwardsFactor = 1.0F;
/*  401 */     float modelUpwardsFactor = 1.0F;
/*  402 */     float modelShakeFactor = 1.0F;
/*      */     
/*  404 */     if (player.func_184614_ca() != null && 
/*  405 */       player.func_184614_ca().func_77973_b() instanceof ItemGun) {
/*  406 */       ItemStack itemStack = GunType.getAttachment(player.func_184614_ca(), AttachmentPresetEnum.Stock);
/*  407 */       if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*  408 */         ItemAttachment itemAttachment = (ItemAttachment)itemStack.func_77973_b();
/*  409 */         modelBackwardsFactor = ((ModelAttachment)itemAttachment.type.model).config.stock.modelRecoilBackwardsFactor;
/*  410 */         modelUpwardsFactor = ((ModelAttachment)itemAttachment.type.model).config.stock.modelRecoilUpwardsFactor;
/*  411 */         modelShakeFactor = ((ModelAttachment)itemAttachment.type.model).config.stock.modelRecoilShakeFactor;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  416 */     mat.translate(new Vector3f(-bounce * config.extra.modelRecoilBackwards * (float)(1.0D - this.controller.ADS) * modelBackwardsFactor, 0.0F, 0.0F));
/*  417 */     mat.translate(new Vector3f(0.0F, -elastic * config.extra.modelRecoilBackwards * modelBackwardsFactor * 0.05F, 0.0F));
/*      */     
/*  419 */     mat.translate(new Vector3f(0.0F, 0.0F, sin10 * anim.recoilSide * config.extra.modelRecoilUpwards * modelUpwardsFactor));
/*      */     
/*  421 */     mat.rotate(toRadians(sin * anim.recoilSide * config.extra.modelRecoilUpwards * modelUpwardsFactor), new Vector3f(0.0F, 0.0F, 1.0F));
/*  422 */     mat.rotate(toRadians(5.0F * sin10 * anim.recoilSide * config.extra.modelRecoilUpwards * modelUpwardsFactor), new Vector3f(0.0F, 0.0F, 1.0F));
/*      */     
/*  424 */     mat.rotate(toRadians(bounce * config.extra.modelRecoilUpwards), new Vector3f(0.0F, 0.0F, 1.0F));
/*      */     
/*  426 */     mat.rotate(toRadians(-alpha * randomShake * config.extra.modelRecoilShake * modelShakeFactor), new Vector3f(0.0F, 1.0F, 0.0F));
/*  427 */     mat.rotate(toRadians(-alpha * randomShake * config.extra.modelRecoilShake * modelShakeFactor), new Vector3f(1.0F, 0.0F, 0.0F));
/*      */     
/*  429 */     if (ScopeUtils.isIndsideGunRendering) {
/*  430 */       mat.translate(new Vector3f(-renderInsideGunOffset, 0.0F, 0.0F));
/*      */     }
/*      */     
/*  433 */     this.floatBuffer.clear();
/*  434 */     mat.store(this.floatBuffer);
/*  435 */     this.floatBuffer.rewind();
/*      */     
/*  437 */     GL11.glMultMatrix(this.floatBuffer);
/*      */     
/*  439 */     GlStateManager.func_179147_l();
/*  440 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  441 */     if (ScopeUtils.isIndsideGunRendering) {
/*  442 */       GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*      */     }
/*  444 */     float worldScale = 1.0F;
/*  445 */     float rotateXRendering = rotateX;
/*  446 */     RenderParameters.CROSS_ROTATE = rotateXRendering;
/*  447 */     GlStateManager.func_179103_j(7425);
/*  448 */     color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/*  450 */     boolean applySprint = (this.controller.SPRINT > 0.1D && this.controller.INSPECT >= 1.0D);
/*  451 */     boolean isRenderHand0 = (ScopeUtils.isRenderHand0 || !OptifineHelper.isShadersEnabled());
/*  452 */     HashSet<String> exceptParts = new HashSet<>();
/*  453 */     if (isRenderHand0) {
/*  454 */       exceptParts.addAll(config.defaultHidePart);
/*      */ 
/*      */       
/*  457 */       for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/*  458 */         ItemStack itemStack = GunType.getAttachment(item, attachment);
/*  459 */         if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*  460 */           AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/*  461 */           String binding = "gunModel";
/*  462 */           if (config.attachmentGroup.containsKey(attachment.typeName) && 
/*  463 */             ((GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName)).hidePart != null) {
/*  464 */             exceptParts.addAll(((GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName)).hidePart);
/*      */           }
/*      */           
/*  467 */           if (config.attachment.containsKey(attachmentType.internalName) && 
/*  468 */             ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).hidePart != null) {
/*  469 */             exceptParts.addAll(((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).hidePart);
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  475 */       for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/*  476 */         ItemStack itemStack = GunType.getAttachment(item, attachment);
/*  477 */         if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*  478 */           AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/*  479 */           String binding = "gunModel";
/*  480 */           if (config.attachmentGroup.containsKey(attachment.typeName) && 
/*  481 */             ((GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName)).showPart != null) {
/*  482 */             exceptParts.removeAll(((GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName)).showPart);
/*      */           }
/*      */           
/*  485 */           if (config.attachment.containsKey(attachmentType.internalName) && 
/*  486 */             ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).showPart != null) {
/*  487 */             exceptParts.removeAll(((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).showPart);
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  493 */       exceptParts.addAll(DEFAULT_EXCEPT);
/*      */     } 
/*      */     
/*  496 */     HashSet<String> exceptPartsRendering = exceptParts;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  505 */     ItemAttachment sightRendering = sight;
/*      */     
/*  507 */     boolean glowMode = ObjModelRenderer.glowTxtureMode;
/*  508 */     ObjModelRenderer.glowTxtureMode = true;
/*  509 */     blendTransform(model, item, !config.animations.containsKey(AnimationType.SPRINT), this.controller.getTime(), this.controller.getSprintTime(), (float)this.controller.SPRINT, "sprint_righthand", applySprint, true, () -> {
/*      */           if (isRenderHand0) {
/*      */             if (sightRendering != null) {
/*      */               String binding = "gunModel";
/*      */ 
/*      */               
/*      */               if (config.attachment.containsKey(sightRendering.type.internalName)) {
/*      */                 binding = ((GunEnhancedRenderConfig.Attachment)config.attachment.get(sightRendering.type.internalName)).binding;
/*      */               }
/*      */ 
/*      */               
/*      */               model.applyGlobalTransformToOther(binding, ());
/*      */             } 
/*      */ 
/*      */             
/*      */             if (gunType.handsTextureType != null) {
/*      */               bindCustomHands(gunType.handsTextureType);
/*      */             } else {
/*      */               bindPlayerSkin();
/*      */             } 
/*      */ 
/*      */             
/*      */             ObjModelRenderer.glowTxtureMode = false;
/*      */ 
/*      */             
/*      */             renderHandAndArmor(EnumHandSide.RIGHT, (AbstractClientPlayer)player, (EnhancedRenderConfig)config, modelPlayer, (EnhancedModel)model);
/*      */ 
/*      */             
/*      */             ObjModelRenderer.glowTxtureMode = true;
/*      */ 
/*      */             
/*      */             int skinId = 0;
/*      */ 
/*      */             
/*      */             if (item.func_77942_o() && item.func_77978_p().func_74764_b("skinId")) {
/*      */               skinId = item.func_77978_p().func_74762_e("skinId");
/*      */             }
/*      */ 
/*      */             
/*      */             String gunPath = (skinId > 0) ? gunType.modelSkins[skinId].getSkin() : gunType.modelSkins[0].getSkin();
/*      */ 
/*      */             
/*      */             bindTexture("guns", gunPath);
/*      */ 
/*      */             
/*      */             model.renderPartExcept(exceptPartsRendering);
/*      */ 
/*      */             
/*      */             WeaponFireMode fireMode = GunType.getFireMode(item);
/*      */ 
/*      */             
/*      */             if (fireMode == WeaponFireMode.SEMI) {
/*      */               model.renderPart("selector_semi");
/*      */             } else if (fireMode == WeaponFireMode.FULL) {
/*      */               model.renderPart("selector_full");
/*      */             } else if (fireMode == WeaponFireMode.BURST) {
/*      */               model.renderPart("selector_burst");
/*      */             } 
/*      */ 
/*      */             
/*      */             boolean flagDynamicAmmoRendered = false;
/*      */ 
/*      */             
/*      */             ItemStack stackAmmo = new ItemStack(item.func_77978_p().func_74775_l("ammo"));
/*      */ 
/*      */             
/*      */             ItemStack orignalAmmo = stackAmmo;
/*      */ 
/*      */             
/*      */             stackAmmo = this.controller.getRenderAmmo(stackAmmo);
/*      */ 
/*      */             
/*      */             ItemStack renderAmmo = stackAmmo;
/*      */ 
/*      */             
/*      */             ItemStack prognosisAmmo = ClientTickHandler.reloadEnhancedPrognosisAmmoRendering;
/*      */ 
/*      */             
/*      */             ItemStack bulletStack = ItemStack.field_190927_a;
/*      */ 
/*      */             
/*      */             int currentAmmoCount = 0;
/*      */ 
/*      */             
/*      */             int costAmmoCount = 0;
/*      */ 
/*      */             
/*      */             VarBoolean defaultBulletFlag = new VarBoolean();
/*      */ 
/*      */             
/*      */             defaultBulletFlag.b = true;
/*      */ 
/*      */             
/*      */             boolean defaultAmmoFlag = true;
/*      */ 
/*      */             
/*      */             if (gunType.acceptedBullets != null) {
/*      */               currentAmmoCount = item.func_77978_p().func_74762_e("ammocount");
/*      */ 
/*      */               
/*      */               if (anim.reloading) {
/*      */                 currentAmmoCount += anim.getAmmoCountOffset(true);
/*      */               }
/*      */ 
/*      */               
/*      */               bulletStack = new ItemStack(item.func_77978_p().func_74775_l("bullet"));
/*      */ 
/*      */               
/*      */               if (anim.reloading) {
/*      */                 bulletStack = ClientProxy.gunEnhancedRenderer.controller.getRenderAmmo(bulletStack);
/*      */               }
/*      */ 
/*      */               
/*      */               costAmmoCount = gunType.internalAmmoStorage.intValue() - currentAmmoCount;
/*      */             } else {
/*      */               Integer currentMagcount = null;
/*      */ 
/*      */               
/*      */               if (stackAmmo != null && !stackAmmo.func_190926_b() && stackAmmo.func_77942_o()) {
/*      */                 if (stackAmmo.func_77978_p().func_74764_b("magcount")) {
/*      */                   currentMagcount = Integer.valueOf(stackAmmo.func_77978_p().func_74762_e("magcount"));
/*      */                 }
/*      */ 
/*      */                 
/*      */                 currentAmmoCount = ReloadHelper.getBulletOnMag(stackAmmo, currentMagcount);
/*      */ 
/*      */                 
/*      */                 bulletStack = new ItemStack(stackAmmo.func_77978_p().func_74775_l("bullet"));
/*      */               } 
/*      */ 
/*      */               
/*      */               if (stackAmmo.func_77973_b() instanceof ItemAmmo) {
/*      */                 costAmmoCount = ((ItemAmmo)stackAmmo.func_77973_b()).type.ammoCapacity - currentAmmoCount;
/*      */               }
/*      */             } 
/*      */ 
/*      */             
/*      */             int currentAmmoCountRendering = currentAmmoCount;
/*      */ 
/*      */             
/*      */             int costAmmoCountRendering = costAmmoCount;
/*      */ 
/*      */             
/*      */             if (bulletStack != null && bulletStack.func_77973_b() instanceof ItemBullet) {
/*      */               BulletType bulletType = ((ItemBullet)bulletStack.func_77973_b()).type;
/*      */ 
/*      */               
/*      */               if (bulletType.isDynamicBullet && bulletType.model != null) {
/*      */                 int skinIdBullet = 0;
/*      */ 
/*      */                 
/*      */                 if (bulletStack.func_77942_o() && bulletStack.func_77978_p().func_74764_b("skinId")) {
/*      */                   skinIdBullet = bulletStack.func_77978_p().func_74762_e("skinId");
/*      */                 }
/*      */ 
/*      */                 
/*      */                 if (bulletType.sameTextureAsGun) {
/*      */                   bindTexture("guns", gunPath);
/*      */                 } else {
/*      */                   String pathAmmo = (skinIdBullet > 0) ? bulletType.modelSkins[skinIdBullet].getSkin() : bulletType.modelSkins[0].getSkin();
/*      */ 
/*      */                   
/*      */                   bindTexture("bullets", pathAmmo);
/*      */                 } 
/*      */ 
/*      */                 
/*      */                 int bullet = 0;
/*      */ 
/*      */                 
/*      */                 while (bullet < currentAmmoCount && bullet < 256) {
/*      */                   model.applyGlobalTransformToOther("bulletModel_" + bullet, ());
/*      */ 
/*      */                   
/*      */                   bullet++;
/*      */                 } 
/*      */ 
/*      */                 
/*      */                 bullet = 0;
/*      */ 
/*      */                 
/*      */                 while (bullet < costAmmoCount && bullet < 256) {
/*      */                   model.applyGlobalTransformToOther("shellModel_" + bullet, ());
/*      */ 
/*      */                   
/*      */                   bullet++;
/*      */                 } 
/*      */ 
/*      */                 
/*      */                 model.applyGlobalTransformToOther("bulletModel", ());
/*      */ 
/*      */                 
/*      */                 defaultBulletFlag.b = false;
/*      */               } 
/*      */             } 
/*      */ 
/*      */             
/*      */             ItemStack[] ammoList = { stackAmmo, orignalAmmo, prognosisAmmo };
/*      */ 
/*      */             
/*      */             String[] binddings = { "ammoModel", "ammoModelPre", "ammoModelPost" };
/*      */ 
/*      */             
/*      */             for (int x = 0; x < 3; x++) {
/*      */               ItemStack stackAmmoX = ammoList[x];
/*      */ 
/*      */               
/*      */               if (stackAmmoX != null && !stackAmmoX.func_190926_b()) {
/*      */                 if (model.existPart(binddings[x])) {
/*      */                   if (stackAmmoX.func_77973_b() instanceof ItemAmmo) {
/*      */                     ItemAmmo itemAmmo = (ItemAmmo)stackAmmoX.func_77973_b();
/*      */ 
/*      */                     
/*      */                     AmmoType ammoType = itemAmmo.type;
/*      */ 
/*      */                     
/*      */                     if (ammoType.isDynamicAmmo && ammoType.model != null) {
/*      */                       int skinIdAmmo = 0;
/*      */ 
/*      */                       
/*      */                       int baseAmmoCount = 0;
/*      */ 
/*      */                       
/*      */                       if (stackAmmoX.func_77942_o()) {
/*      */                         if (stackAmmoX.func_77978_p().func_74764_b("skinId")) {
/*      */                           skinIdAmmo = stackAmmoX.func_77978_p().func_74762_e("skinId");
/*      */                         }
/*      */ 
/*      */                         
/*      */                         if (stackAmmoX.func_77978_p().func_74764_b("magcount")) {
/*      */                           baseAmmoCount = (stackAmmoX.func_77978_p().func_74762_e("magcount") - 1) * ammoType.ammoCapacity;
/*      */                         }
/*      */                       } 
/*      */ 
/*      */                       
/*      */                       int baseAmmoCountRendering = baseAmmoCount;
/*      */ 
/*      */                       
/*      */                       if (ammoType.sameTextureAsGun) {
/*      */                         bindTexture("guns", gunPath);
/*      */                       } else {
/*      */                         String pathAmmo = (skinIdAmmo > 0) ? ammoType.modelSkins[skinIdAmmo].getSkin() : ammoType.modelSkins[0].getSkin();
/*      */ 
/*      */                         
/*      */                         bindTexture("ammo", pathAmmo);
/*      */                       } 
/*      */ 
/*      */                       
/*      */                       if (this.controller.shouldRenderAmmo()) {
/*      */                         model.applyGlobalTransformToOther("ammoModel", ());
/*      */ 
/*      */                         
/*      */                         if (bulletStack != null && bulletStack.func_77973_b() instanceof ItemBullet) {
/*      */                           BulletType bulletType = ((ItemBullet)bulletStack.func_77973_b()).type;
/*      */ 
/*      */                           
/*      */                           if (bulletType.isDynamicBullet && bulletType.model != null) {
/*      */                             int skinIdBullet = 0;
/*      */ 
/*      */                             
/*      */                             if (bulletStack.func_77942_o() && bulletStack.func_77978_p().func_74764_b("skinId")) {
/*      */                               skinIdBullet = bulletStack.func_77978_p().func_74762_e("skinId");
/*      */                             }
/*      */ 
/*      */                             
/*      */                             if (bulletType.sameTextureAsGun) {
/*      */                               bindTexture("guns", gunPath);
/*      */                             } else {
/*      */                               String pathAmmo = (skinIdBullet > 0) ? bulletType.modelSkins[skinIdBullet].getSkin() : bulletType.modelSkins[0].getSkin();
/*      */ 
/*      */                               
/*      */                               bindTexture("bullets", pathAmmo);
/*      */                             } 
/*      */ 
/*      */                             
/*      */                             int bullet = 0;
/*      */ 
/*      */                             
/*      */                             while (bullet < costAmmoCount && bullet < 256) {
/*      */                               model.applyGlobalTransformToOther("shellModel_" + bullet, ());
/*      */ 
/*      */                               
/*      */                               bullet++;
/*      */                             } 
/*      */ 
/*      */                             
/*      */                             model.applyGlobalTransformToOther("shellModel", ());
/*      */                           } 
/*      */                         } 
/*      */ 
/*      */                         
/*      */                         model.applyGlobalTransformToOther("bulletModel", ());
/*      */ 
/*      */                         
/*      */                         flagDynamicAmmoRendered = true;
/*      */ 
/*      */                         
/*      */                         defaultAmmoFlag = false;
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 }
/*      */               }
/*      */             } 
/*      */ 
/*      */             
/*      */             bindTexture("guns", gunPath);
/*      */ 
/*      */             
/*      */             if (defaultBulletFlag.b) {
/*      */               int bullet = 0;
/*      */ 
/*      */               
/*      */               while (bullet < currentAmmoCount && bullet < 256) {
/*      */                 model.renderPart("bulletModel_" + bullet);
/*      */ 
/*      */                 
/*      */                 bullet++;
/*      */               } 
/*      */ 
/*      */               
/*      */               if (bulletStack != null && !bulletStack.func_190926_b()) {
/*      */                 bullet = 0;
/*      */ 
/*      */                 
/*      */                 while (bullet < costAmmoCount && bullet < 256) {
/*      */                   model.renderPart("shellModel_" + bullet);
/*      */ 
/*      */                   
/*      */                   bullet++;
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/*      */               model.renderPart("bulletModel");
/*      */             } 
/*      */ 
/*      */             
/*      */             if (this.controller.shouldRenderAmmo() && defaultAmmoFlag) {
/*      */               model.renderPart("ammoModel");
/*      */             }
/*      */ 
/*      */             
/*      */             for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/*      */               ItemStack itemStack = GunType.getAttachment(item, attachment);
/*      */ 
/*      */               
/*      */               if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*      */                 AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/*      */ 
/*      */                 
/*      */                 ModelAttachment attachmentModel = (ModelAttachment)attachmentType.model;
/*      */ 
/*      */                 
/*      */                 if (!ScopeUtils.isIndsideGunRendering || attachment != AttachmentPresetEnum.Sight || (config.attachment.containsKey(attachmentType.internalName) && ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).renderInsideSightModel)) {
/*      */                   if (attachmentModel != null) {
/*      */                     String binding = "gunModel";
/*      */ 
/*      */                     
/*      */                     if (config.attachment.containsKey(attachmentType.internalName)) {
/*      */                       binding = ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).binding;
/*      */                     }
/*      */                     
/*      */                     model.applyGlobalTransformToOther(binding, ());
/*      */                   } 
/*      */                   
/*      */                   if (attachment == AttachmentPresetEnum.Sight) {
/*      */                     ClientRenderHooks.isAiming = false;
/*      */                     
/*      */                     ClientRenderHooks.isAimingScope = false;
/*      */                     
/*      */                     WeaponScopeModeType modeType = attachmentType.sight.modeType;
/*      */                     
/*      */                     if (modeType.isMirror) {
/*      */                       if (this.controller.ADS == 1.0D) {
/*      */                         if (!ClientRenderHooks.isAimingScope) {
/*      */                           ClientRenderHooks.isAimingScope = true;
/*      */                         }
/*      */                       } else if (ClientRenderHooks.isAimingScope) {
/*      */                         ClientRenderHooks.isAimingScope = false;
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             
/*      */             ObjModelRenderer.glowTxtureMode = false;
/*      */             
/*      */             GlStateManager.func_179147_l();
/*      */             
/*      */             GlStateManager.func_179132_a(false);
/*      */             
/*      */             boolean shouldRenderFlash = true;
/*      */             
/*      */             if (GunType.getAttachment(item, AttachmentPresetEnum.Barrel) != null) {
/*      */               AttachmentType attachmentType = ((ItemAttachment)GunType.getAttachment(item, AttachmentPresetEnum.Barrel).func_77973_b()).type;
/*      */               
/*      */               if (attachmentType.attachmentType == AttachmentPresetEnum.Barrel) {
/*      */                 shouldRenderFlash = !attachmentType.barrel.hideFlash;
/*      */               }
/*      */             } 
/*      */             
/*      */             if (shouldRenderFlash && anim.shooting && anim.getShootingAnimationType().showFlashModel() && !player.func_70090_H()) {
/*      */               GlStateManager.func_179094_E();
/*      */               
/*      */               ItemStack itemStack = GunType.getAttachment(item, AttachmentPresetEnum.Barrel);
/*      */               
/*      */               if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*      */                 AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/*      */                 
/*      */                 if (config.attachment.containsKey(attachmentType.internalName) && ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).flashModelOffset != null) {
/*      */                   GlStateManager.func_179109_b(((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).flashModelOffset.x, ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).flashModelOffset.y, ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).flashModelOffset.z);
/*      */                 }
/*      */               } 
/*      */               
/*      */               GlStateManager.func_179140_f();
/*      */               
/*      */               OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/*      */               
/*      */               TextureType flashType = gunType.flashType;
/*      */               
/*      */               bindTexture(flashType.resourceLocations.get(anim.flashCount % flashType.resourceLocations.size()));
/*      */               
/*      */               model.renderPart("flashModel");
/*      */               
/*      */               OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, bx, by);
/*      */               
/*      */               GlStateManager.func_179145_e();
/*      */               
/*      */               GlStateManager.func_179121_F();
/*      */             } 
/*      */             
/*      */             GlStateManager.func_179132_a(true);
/*      */             
/*      */             ObjModelRenderer.glowTxtureMode = true;
/*      */           } 
/*      */         });
/*      */     
/*  947 */     blendTransform(model, item, !config.animations.containsKey(AnimationType.SPRINT), this.controller.getTime(), this.controller.getSprintTime(), (float)this.controller.SPRINT, "sprint_lefthand", applySprint, false, () -> {
/*      */           if (isRenderHand0) {
/*      */             if (gunType.handsTextureType != null) {
/*      */               bindCustomHands(gunType.handsTextureType);
/*      */             } else {
/*      */               bindPlayerSkin();
/*      */             } 
/*      */             
/*      */             ObjModelRenderer.glowTxtureMode = false;
/*      */             
/*      */             renderHandAndArmor(EnumHandSide.LEFT, (AbstractClientPlayer)player, (EnhancedRenderConfig)config, modelPlayer, (EnhancedModel)model);
/*      */             
/*      */             ObjModelRenderer.glowTxtureMode = true;
/*      */           } 
/*      */         });
/*      */     
/*  963 */     if (sightRendering != null && 
/*  964 */       !ScopeUtils.isIndsideGunRendering && 
/*  965 */       !sightRendering.type.sight.modeType.isPIP) {
/*  966 */       if (!OptifineHelper.isShadersEnabled()) {
/*  967 */         copyMirrorTexture();
/*  968 */         ClientProxy.scopeUtils.renderPostScope(partialTicks, false, true, true, 1.0F);
/*  969 */         eraseScopeGlassDepth(sightRendering.type, (ModelAttachment)sightRendering.type.model, (this.controller.ADS > 0.0D), worldScale);
/*      */       }
/*  971 */       else if (isRenderHand0) {
/*  972 */         GL11.glPushAttrib(2048);
/*      */         
/*  974 */         GL11.glDepthRange(0.0D, 1.0D);
/*  975 */         copyMirrorTexture();
/*  976 */         ClientProxy.scopeUtils.renderPostScope(partialTicks, true, false, true, 1.0F);
/*  977 */         eraseScopeGlassDepth(sightRendering.type, (ModelAttachment)sightRendering.type.model, (this.controller.ADS > 0.0D), worldScale);
/*  978 */         writeScopeSoildDepth((this.controller.ADS > 0.0D));
/*      */         
/*  980 */         GL11.glPopAttrib();
/*      */       } else {
/*  982 */         ClientProxy.scopeUtils.renderPostScope(partialTicks, false, true, true, 1.0F);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  988 */     ObjModelRenderer.glowTxtureMode = glowMode;
/*  989 */     GlStateManager.func_179103_j(7424);
/*  990 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  991 */     GlStateManager.func_179084_k();
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderHandAndArmor(EnumHandSide side, AbstractClientPlayer player, EnhancedRenderConfig config, ModelPlayer modelPlayer, EnhancedModel model) {
/*  996 */     if (side == EnumHandSide.LEFT) {
/*  997 */       if (config.showHandArmorType != EnhancedRenderConfig.ShowHandArmorType.NONE) {
/*  998 */         RenderHandFisrtPersonEnhancedEvent.PreFirstLayer leftFirst = new RenderHandFisrtPersonEnhancedEvent.PreFirstLayer(this, EnumHandSide.LEFT);
/*  999 */         RenderHandFisrtPersonEnhancedEvent.PreSecondLayer leftSecond = new RenderHandFisrtPersonEnhancedEvent.PreSecondLayer(this, EnumHandSide.LEFT);
/* 1000 */         MinecraftForge.EVENT_BUS.post((Event)leftFirst);
/* 1001 */         MinecraftForge.EVENT_BUS.post((Event)leftSecond);
/* 1002 */         if (!(Minecraft.func_71410_x()).field_71439_g.func_175154_l().equals("slim")) {
/* 1003 */           if (!leftFirst.isCanceled() && 
/* 1004 */             modelPlayer.field_178724_i.field_78806_j && !modelPlayer.field_178724_i.field_78807_k) {
/* 1005 */             model.renderPart("leftArmModel");
/*      */           }
/*      */           
/* 1008 */           if (!leftSecond.isCanceled() && 
/* 1009 */             modelPlayer.field_178734_a.field_78806_j && !modelPlayer.field_178734_a.field_78807_k) {
/* 1010 */             model.renderPart("leftArmLayerModel");
/*      */           }
/*      */         } else {
/*      */           
/* 1014 */           if (!leftFirst.isCanceled() && 
/* 1015 */             modelPlayer.field_178724_i.field_78806_j && !modelPlayer.field_178724_i.field_78807_k) {
/* 1016 */             model.renderPart("leftArmSlimModel");
/*      */           }
/*      */           
/* 1019 */           if (!leftSecond.isCanceled() && 
/* 1020 */             modelPlayer.field_178734_a.field_78806_j && !modelPlayer.field_178734_a.field_78807_k) {
/* 1021 */             model.renderPart("leftArmLayerSlimModel");
/*      */           }
/*      */         } 
/*      */         
/* 1025 */         if (player.field_71071_by.func_70440_f(2) != null) {
/* 1026 */           ItemStack armorStack = player.field_71071_by.func_70440_f(2);
/* 1027 */           if (armorStack.func_77973_b() instanceof ItemMWArmor) {
/* 1028 */             int skinId = 0;
/*      */             
/* 1030 */             String path = (skinId > 0) ? ((ItemMWArmor)armorStack.func_77973_b()).type.modelSkins[skinId].getSkin() : ((ItemMWArmor)armorStack.func_77973_b()).type.modelSkins[0].getSkin();
/*      */             
/* 1032 */             if (!((ItemMWArmor)armorStack.func_77973_b()).type.simpleArmor) {
/*      */               
/* 1034 */               ModelCustomArmor modelArmor = (ModelCustomArmor)((ItemMWArmor)armorStack.func_77973_b()).type.bipedModel;
/*      */               
/* 1036 */               bindTexture("armor", path);
/* 1037 */               if (modelArmor.enhancedArmModel != null) {
/* 1038 */                 modelArmor.enhancedArmModel.loadAnimation(model, (config.showHandArmorType == EnhancedRenderConfig.ShowHandArmorType.SKIN));
/*      */                 
/* 1040 */                 if (!(Minecraft.func_71410_x()).field_71439_g.func_175154_l().equals("slim")) {
/* 1041 */                   if (config.showHandArmorType == EnhancedRenderConfig.ShowHandArmorType.STATIC) {
/* 1042 */                     modelArmor.enhancedArmModel.renderPart("leftArmModel");
/*      */                   }
/* 1044 */                   if (config.showHandArmorType == EnhancedRenderConfig.ShowHandArmorType.SKIN) {
/* 1045 */                     modelArmor.enhancedArmModel.renderPart("leftArmModel_bone");
/*      */                   }
/*      */                 } else {
/* 1048 */                   if (config.showHandArmorType == EnhancedRenderConfig.ShowHandArmorType.STATIC) {
/* 1049 */                     modelArmor.enhancedArmModel.renderPart("leftArmSlimModel");
/*      */                   }
/* 1051 */                   if (config.showHandArmorType == EnhancedRenderConfig.ShowHandArmorType.SKIN) {
/* 1052 */                     modelArmor.enhancedArmModel.renderPart("leftArmSlimModel_bone");
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/* 1059 */         MinecraftForge.EVENT_BUS.post((Event)new RenderHandSleeveEnhancedEvent.Post(this, EnumHandSide.LEFT, model));
/*      */       }
/* 1061 */       else if (!(Minecraft.func_71410_x()).field_71439_g.func_175154_l().equals("slim")) {
/* 1062 */         model.renderPart(LEFT_HAND_PART);
/*      */       } else {
/* 1064 */         model.renderPart(LEFT_SLIM_HAND_PART);
/*      */       }
/*      */     
/*      */     }
/* 1068 */     else if (config.showHandArmorType != EnhancedRenderConfig.ShowHandArmorType.NONE) {
/* 1069 */       RenderHandFisrtPersonEnhancedEvent.PreFirstLayer rightFirst = new RenderHandFisrtPersonEnhancedEvent.PreFirstLayer(this, EnumHandSide.RIGHT);
/* 1070 */       RenderHandFisrtPersonEnhancedEvent.PreSecondLayer rightSecond = new RenderHandFisrtPersonEnhancedEvent.PreSecondLayer(this, EnumHandSide.RIGHT);
/* 1071 */       MinecraftForge.EVENT_BUS.post((Event)rightFirst);
/* 1072 */       MinecraftForge.EVENT_BUS.post((Event)rightSecond);
/* 1073 */       if (!(Minecraft.func_71410_x()).field_71439_g.func_175154_l().equals("slim")) {
/* 1074 */         if (!rightFirst.isCanceled() && 
/* 1075 */           modelPlayer.field_178723_h.field_78806_j && !modelPlayer.field_178723_h.field_78807_k) {
/* 1076 */           model.renderPart("rightArmModel");
/*      */         }
/*      */         
/* 1079 */         if (!rightSecond.isCanceled() && 
/* 1080 */           modelPlayer.field_178732_b.field_78806_j && !modelPlayer.field_178732_b.field_78807_k) {
/* 1081 */           model.renderPart("rightArmLayerModel");
/*      */         }
/*      */       } else {
/*      */         
/* 1085 */         if (!rightFirst.isCanceled() && 
/* 1086 */           modelPlayer.field_178723_h.field_78806_j && !modelPlayer.field_178723_h.field_78807_k) {
/* 1087 */           model.renderPart("rightArmSlimModel");
/*      */         }
/*      */         
/* 1090 */         if (!rightSecond.isCanceled() && 
/* 1091 */           modelPlayer.field_178732_b.field_78806_j && !modelPlayer.field_178732_b.field_78807_k) {
/* 1092 */           model.renderPart("rightArmLayerSlimModel");
/*      */         }
/*      */       } 
/*      */       
/* 1096 */       if (player.field_71071_by.func_70440_f(2) != null) {
/* 1097 */         ItemStack armorStack = player.field_71071_by.func_70440_f(2);
/* 1098 */         if (armorStack.func_77973_b() instanceof ItemMWArmor) {
/* 1099 */           int skinId = 0;
/*      */           
/* 1101 */           String path = (skinId > 0) ? ((ItemMWArmor)armorStack.func_77973_b()).type.modelSkins[skinId].getSkin() : ((ItemMWArmor)armorStack.func_77973_b()).type.modelSkins[0].getSkin();
/*      */           
/* 1103 */           if (!((ItemMWArmor)armorStack.func_77973_b()).type.simpleArmor) {
/*      */             
/* 1105 */             ModelCustomArmor modelArmor = (ModelCustomArmor)((ItemMWArmor)armorStack.func_77973_b()).type.bipedModel;
/*      */             
/* 1107 */             bindTexture("armor", path);
/* 1108 */             if (modelArmor.enhancedArmModel != null) {
/* 1109 */               modelArmor.enhancedArmModel.loadAnimation(model, (config.showHandArmorType == EnhancedRenderConfig.ShowHandArmorType.SKIN));
/*      */               
/* 1111 */               if (!(Minecraft.func_71410_x()).field_71439_g.func_175154_l().equals("slim")) {
/* 1112 */                 if (config.showHandArmorType == EnhancedRenderConfig.ShowHandArmorType.STATIC) {
/* 1113 */                   modelArmor.enhancedArmModel.renderPart("rightArmModel");
/*      */                 }
/* 1115 */                 if (config.showHandArmorType == EnhancedRenderConfig.ShowHandArmorType.SKIN) {
/* 1116 */                   modelArmor.enhancedArmModel.renderPart("rightArmModel_bone");
/*      */                 }
/*      */               } else {
/* 1119 */                 if (config.showHandArmorType == EnhancedRenderConfig.ShowHandArmorType.STATIC) {
/* 1120 */                   modelArmor.enhancedArmModel.renderPart("rightArmSlimModel");
/*      */                 }
/* 1122 */                 if (config.showHandArmorType == EnhancedRenderConfig.ShowHandArmorType.SKIN) {
/* 1123 */                   modelArmor.enhancedArmModel.renderPart("rightArmSlimModel_bone");
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1130 */       MinecraftForge.EVENT_BUS.post((Event)new RenderHandSleeveEnhancedEvent.Post(this, EnumHandSide.RIGHT, model));
/*      */     }
/* 1132 */     else if (!(Minecraft.func_71410_x()).field_71439_g.func_175154_l().equals("slim")) {
/* 1133 */       model.renderPart(RIGHT_HAND_PART);
/*      */     } else {
/* 1135 */       model.renderPart(RIGHT_SLIM_HAND_PART);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawThirdGun(RenderLivingBase renderPlayer, RenderType renderType, EntityLivingBase player, ItemStack demoStack) {
/* 1147 */     drawThirdGun(renderPlayer, renderType, player, demoStack, false);
/*      */   }
/*      */   
/*      */   public void drawThirdGun(RenderLivingBase renderPlayer, RenderType renderType, EntityLivingBase player, ItemStack demoStack, boolean sneakFlag) {
/* 1151 */     if (!(demoStack.func_77973_b() instanceof ItemGun))
/*      */       return; 
/* 1153 */     GunType gunType = ((ItemGun)demoStack.func_77973_b()).type;
/* 1154 */     if (gunType == null)
/*      */       return; 
/* 1156 */     EnhancedModel model = gunType.enhancedModel;
/* 1157 */     GunEnhancedRenderConfig config = (GunEnhancedRenderConfig)model.config;
/*      */     
/* 1159 */     EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine(player);
/*      */     
/* 1161 */     if (player != null) {
/* 1162 */       AnimationController controller = ClientProxy.gunEnhancedRenderer.getController(player, config);
/* 1163 */       if (controller.getPlayingAnimation() == AnimationType.DEFAULT || controller
/* 1164 */         .getPlayingAnimation() == AnimationType.PRE_FIRE || controller
/* 1165 */         .getPlayingAnimation() == AnimationType.FIRE || controller
/* 1166 */         .getPlayingAnimation() == AnimationType.POST_FIRE) {
/* 1167 */         model.updateAnimation(controller.getTime(), true);
/*      */       } else {
/* 1169 */         model.updateAnimation((float)((GunEnhancedRenderConfig.Animation)config.animations.get(AnimationType.DEFAULT)).getStartTime(config.FPS), true);
/*      */       } 
/*      */     } else {
/* 1172 */       model.updateAnimation((float)((GunEnhancedRenderConfig.Animation)config.animations.get(AnimationType.DEFAULT)).getStartTime(config.FPS), true);
/*      */     } 
/*      */ 
/*      */     
/* 1176 */     HashSet<String> exceptParts = new HashSet<>();
/* 1177 */     exceptParts.addAll(config.defaultHidePart);
/* 1178 */     exceptParts.addAll(config.thirdHidePart);
/* 1179 */     exceptParts.removeAll(config.thirdShowPart);
/*      */ 
/*      */     
/* 1182 */     boolean glowTxtureMode = ObjModelRenderer.glowTxtureMode;
/* 1183 */     ObjModelRenderer.glowTxtureMode = true;
/* 1184 */     for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/* 1185 */       ItemStack itemStack = GunType.getAttachment(demoStack, attachment);
/* 1186 */       if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/* 1187 */         AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/* 1188 */         String binding = "gunModel";
/* 1189 */         if (config.attachmentGroup.containsKey(attachment.typeName) && 
/* 1190 */           ((GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName)).hidePart != null) {
/* 1191 */           exceptParts.addAll(((GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName)).hidePart);
/*      */         }
/*      */         
/* 1194 */         if (config.attachment.containsKey(attachmentType.internalName) && 
/* 1195 */           ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).hidePart != null) {
/* 1196 */           exceptParts.addAll(((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).hidePart);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1202 */     for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/* 1203 */       ItemStack itemStack = GunType.getAttachment(demoStack, attachment);
/* 1204 */       if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/* 1205 */         AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/* 1206 */         String binding = "gunModel";
/* 1207 */         if (config.attachmentGroup.containsKey(attachment.typeName) && 
/* 1208 */           ((GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName)).showPart != null) {
/* 1209 */           exceptParts.removeAll(((GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName)).showPart);
/*      */         }
/*      */         
/* 1212 */         if (config.attachment.containsKey(attachmentType.internalName) && 
/* 1213 */           ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).showPart != null) {
/* 1214 */           exceptParts.removeAll(((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).showPart);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1220 */     exceptParts.addAll(DEFAULT_EXCEPT);
/*      */     
/* 1222 */     float worldScale = 1.0F;
/* 1223 */     HashSet<String> exceptPartsRendering = exceptParts;
/*      */     
/* 1225 */     GlStateManager.func_179094_E();
/* 1226 */     GlStateManager.func_179103_j(7425);
/* 1227 */     ClientProxy.gunEnhancedRenderer.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/* 1229 */     if (player != null && sneakFlag) {
/* 1230 */       GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1255 */     if (renderPlayer != null && renderPlayer.func_177087_b() instanceof ModelBiped) {
/* 1256 */       ((ModelBiped)renderPlayer.func_177087_b()).field_178723_h.func_78794_c(0.0625F);
/*      */     }
/*      */     
/* 1259 */     GunEnhancedRenderConfig.ThirdPerson.RenderElement renderConfigElement = (GunEnhancedRenderConfig.ThirdPerson.RenderElement)config.thirdPerson.renderElements.get(renderType.serializedName);
/* 1260 */     GlStateManager.func_179109_b(renderConfigElement.pos.x, renderConfigElement.pos.y, renderConfigElement.pos.z);
/* 1261 */     GlStateManager.func_179152_a(0.1F, 0.1F, 0.1F);
/* 1262 */     GlStateManager.func_179152_a(renderConfigElement.size.x, renderConfigElement.size.y, renderConfigElement.size.z);
/* 1263 */     GlStateManager.func_179114_b(renderConfigElement.rot.y, 0.0F, -1.0F, 0.0F);
/* 1264 */     GlStateManager.func_179114_b(renderConfigElement.rot.x, -1.0F, 0.0F, 0.0F);
/* 1265 */     GlStateManager.func_179114_b(renderConfigElement.rot.z, 0.0F, 0.0F, -1.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1270 */     int skinId = 0;
/* 1271 */     if (demoStack.func_77942_o() && 
/* 1272 */       demoStack.func_77978_p().func_74764_b("skinId")) {
/* 1273 */       skinId = demoStack.func_77978_p().func_74762_e("skinId");
/*      */     }
/*      */     
/* 1276 */     String gunPath = (skinId > 0) ? gunType.modelSkins[skinId].getSkin() : gunType.modelSkins[0].getSkin();
/* 1277 */     ClientProxy.gunEnhancedRenderer.bindTexture("guns", gunPath);
/* 1278 */     model.renderPartExcept(exceptParts);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1283 */     boolean flagDynamicAmmoRendered = false;
/* 1284 */     ItemStack stackAmmo = ItemStack.field_190927_a;
/* 1285 */     ItemStack bulletStack = ItemStack.field_190927_a;
/* 1286 */     if (demoStack.func_77942_o()) {
/* 1287 */       stackAmmo = new ItemStack(demoStack.func_77978_p().func_74775_l("ammo"));
/*      */     }
/* 1289 */     ItemStack orignalAmmo = stackAmmo;
/*      */     
/* 1291 */     ItemStack renderAmmo = stackAmmo;
/* 1292 */     boolean defaultAmmoFlag = true;
/*      */     
/* 1294 */     VarBoolean defaultBulletFlag = new VarBoolean();
/* 1295 */     defaultBulletFlag.b = true;
/* 1296 */     int currentAmmoCount = 0;
/* 1297 */     int costAmmoCount = 0;
/*      */     
/* 1299 */     if (gunType.acceptedBullets != null && demoStack.func_77942_o()) {
/* 1300 */       currentAmmoCount = demoStack.func_77978_p().func_74762_e("ammocount");
/* 1301 */       bulletStack = new ItemStack(demoStack.func_77978_p().func_74775_l("bullet"));
/* 1302 */       costAmmoCount = gunType.internalAmmoStorage.intValue() - currentAmmoCount;
/*      */     } 
/*      */     
/* 1305 */     if (bulletStack != null && 
/* 1306 */       bulletStack.func_77973_b() instanceof ItemBullet) {
/* 1307 */       BulletType bulletType = ((ItemBullet)bulletStack.func_77973_b()).type;
/* 1308 */       if (bulletType.isDynamicBullet && bulletType.model != null) {
/* 1309 */         int skinIdBullet = 0;
/* 1310 */         if (bulletStack.func_77942_o() && 
/* 1311 */           bulletStack.func_77978_p().func_74764_b("skinId")) {
/* 1312 */           skinIdBullet = bulletStack.func_77978_p().func_74762_e("skinId");
/*      */         }
/*      */         
/* 1315 */         if (bulletType.sameTextureAsGun) {
/* 1316 */           ClientProxy.gunEnhancedRenderer.bindTexture("guns", gunPath);
/*      */         } else {
/*      */           
/* 1319 */           String pathAmmo = (skinIdBullet > 0) ? bulletType.modelSkins[skinIdBullet].getSkin() : bulletType.modelSkins[0].getSkin();
/* 1320 */           ClientProxy.gunEnhancedRenderer.bindTexture("bullets", pathAmmo);
/*      */         }  int bullet;
/* 1322 */         for (bullet = 0; bullet < currentAmmoCount && bullet < 256; bullet++) {
/* 1323 */           int renderBullet = bullet;
/* 1324 */           model.applyGlobalTransformToOther("bulletModel_" + bullet, () -> ClientProxy.gunEnhancedRenderer.renderAttachment(config, "bullet", bulletType.internalName, ()));
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1330 */         for (bullet = 0; bullet < costAmmoCount && bullet < 256; bullet++) {
/* 1331 */           int renderBullet = bullet;
/* 1332 */           model.applyGlobalTransformToOther("shellModel_" + bullet, () -> ClientProxy.gunEnhancedRenderer.renderAttachment(config, "shell", bulletType.internalName, ()));
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1338 */         model.applyGlobalTransformToOther("bulletModel", () -> ClientProxy.gunEnhancedRenderer.renderAttachment(config, "bullet", bulletType.internalName, ()));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1343 */         defaultBulletFlag.b = false;
/*      */       } 
/*      */     } 
/*      */     
/* 1347 */     ItemStack[] ammoList = { stackAmmo };
/* 1348 */     String[] binddings = { "ammoModel" };
/* 1349 */     for (int x = 0; x < 1; x++) {
/* 1350 */       ItemStack stackAmmoX = ammoList[x];
/* 1351 */       if (stackAmmoX != null && !stackAmmoX.func_190926_b())
/*      */       {
/*      */         
/* 1354 */         if (model.existPart(binddings[x]))
/*      */         {
/*      */           
/* 1357 */           if (stackAmmoX.func_77973_b() instanceof ItemAmmo) {
/* 1358 */             ItemAmmo itemAmmo = (ItemAmmo)stackAmmoX.func_77973_b();
/* 1359 */             AmmoType ammoType = itemAmmo.type;
/* 1360 */             if (ammoType.isDynamicAmmo && ammoType.model != null) {
/* 1361 */               int skinIdAmmo = 0;
/*      */               
/* 1363 */               if (ammoType.sameTextureAsGun) {
/* 1364 */                 ClientProxy.gunEnhancedRenderer.bindTexture("guns", gunPath);
/*      */               } else {
/*      */                 
/* 1367 */                 String pathAmmo = (skinIdAmmo > 0) ? ammoType.modelSkins[skinIdAmmo].getSkin() : ammoType.modelSkins[0].getSkin();
/* 1368 */                 ClientProxy.gunEnhancedRenderer.bindTexture("ammo", pathAmmo);
/*      */               } 
/*      */               
/* 1371 */               model.applyGlobalTransformToOther("ammoModel", () -> {
/*      */                     GlStateManager.func_179094_E();
/*      */ 
/*      */                     
/*      */                     if (renderAmmo.func_77978_p().func_74764_b("magcount") && config.attachment.containsKey(itemAmmo.type.internalName) && ((GunEnhancedRenderConfig.Attachment)config.attachment.get(itemAmmo.type.internalName)).multiMagazineTransform != null && renderAmmo.func_77978_p().func_74762_e("magcount") <= ((GunEnhancedRenderConfig.Attachment)config.attachment.get(itemAmmo.type.internalName)).multiMagazineTransform.size()) {
/*      */                       GunEnhancedRenderConfig.Transform ammoTransform = ((GunEnhancedRenderConfig.Attachment)config.attachment.get(itemAmmo.type.internalName)).multiMagazineTransform.get(renderAmmo.func_77978_p().func_74762_e("magcount") - 1);
/*      */ 
/*      */                       
/*      */                       GunEnhancedRenderConfig.Transform renderTransform = ammoTransform;
/*      */ 
/*      */                       
/*      */                       GlStateManager.func_179109_b(renderTransform.translate.x, renderTransform.translate.y, renderTransform.translate.z);
/*      */ 
/*      */                       
/*      */                       GlStateManager.func_179152_a(renderTransform.scale.x, renderTransform.scale.y, renderTransform.scale.z);
/*      */ 
/*      */                       
/*      */                       GlStateManager.func_179114_b(renderTransform.rotate.y, 0.0F, 1.0F, 0.0F);
/*      */                       
/*      */                       GlStateManager.func_179114_b(renderTransform.rotate.x, 1.0F, 0.0F, 0.0F);
/*      */                       
/*      */                       GlStateManager.func_179114_b(renderTransform.rotate.z, 0.0F, 0.0F, 1.0F);
/*      */                     } 
/*      */                     
/*      */                     ClientProxy.gunEnhancedRenderer.renderAttachment(config, "ammo", ammoType.internalName, ());
/*      */                     
/*      */                     GlStateManager.func_179121_F();
/*      */                   });
/*      */               
/* 1400 */               flagDynamicAmmoRendered = true;
/* 1401 */               defaultAmmoFlag = false;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1411 */     ClientProxy.gunEnhancedRenderer.bindTexture("guns", gunPath);
/*      */     
/* 1413 */     if (defaultBulletFlag.b) {
/* 1414 */       int bullet; for (bullet = 0; bullet < currentAmmoCount && bullet < 256; bullet++) {
/* 1415 */         model.renderPart("bulletModel_" + bullet);
/*      */       }
/* 1417 */       for (bullet = 0; bullet < costAmmoCount && bullet < 256; bullet++) {
/* 1418 */         model.renderPart("shellModel_" + bullet);
/*      */       }
/*      */     } 
/*      */     
/* 1422 */     if (!renderAmmo.func_190926_b() && defaultAmmoFlag) {
/* 1423 */       model.renderPart("ammoModel");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1430 */     for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/* 1431 */       ItemStack itemStack = GunType.getAttachment(demoStack, attachment);
/* 1432 */       if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/* 1433 */         AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/* 1434 */         ModelAttachment attachmentModel = (ModelAttachment)attachmentType.model;
/*      */         
/* 1436 */         if (!ScopeUtils.isIndsideGunRendering || 
/* 1437 */           attachment != AttachmentPresetEnum.Sight || (
/* 1438 */           config.attachment.containsKey(attachmentType.internalName) && 
/* 1439 */           ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).renderInsideSightModel))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1447 */           if (attachmentModel != null) {
/* 1448 */             String binding = "gunModel";
/* 1449 */             if (config.attachment.containsKey(attachmentType.internalName)) {
/* 1450 */               binding = ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).binding;
/*      */             }
/* 1452 */             model.applyGlobalTransformToOther(binding, () -> {
/*      */                   if (attachmentType.sameTextureAsGun) {
/*      */                     ClientProxy.gunEnhancedRenderer.bindTexture("guns", gunPath);
/*      */                   } else {
/*      */                     int attachmentsSkinId = 0;
/*      */ 
/*      */ 
/*      */                     
/*      */                     if (itemStack.func_77942_o() && itemStack.func_77978_p().func_74764_b("skinId")) {
/*      */                       attachmentsSkinId = itemStack.func_77978_p().func_74762_e("skinId");
/*      */                     }
/*      */ 
/*      */ 
/*      */                     
/*      */                     String attachmentsPath = (attachmentsSkinId > 0) ? attachmentType.modelSkins[attachmentsSkinId].getSkin() : attachmentType.modelSkins[0].getSkin();
/*      */ 
/*      */ 
/*      */                     
/*      */                     ClientProxy.gunEnhancedRenderer.bindTexture("attachments", attachmentsPath);
/*      */                   } 
/*      */ 
/*      */                   
/*      */                   ClientProxy.gunEnhancedRenderer.renderAttachment(config, attachment.typeName, attachmentType.internalName, ());
/*      */                 });
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1482 */     ObjModelRenderer.glowTxtureMode = glowTxtureMode;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1487 */     boolean shouldRenderFlash = true;
/* 1488 */     if (GunType.getAttachment(demoStack, AttachmentPresetEnum.Barrel) != null) {
/* 1489 */       AttachmentType attachmentType = ((ItemAttachment)GunType.getAttachment(demoStack, AttachmentPresetEnum.Barrel).func_77973_b()).type;
/* 1490 */       if (attachmentType.attachmentType == AttachmentPresetEnum.Barrel) {
/* 1491 */         shouldRenderFlash = !attachmentType.barrel.hideFlash;
/*      */       }
/*      */     } 
/*      */     
/* 1495 */     float bx = OpenGlHelper.lastBrightnessX;
/* 1496 */     float by = OpenGlHelper.lastBrightnessY;
/*      */     
/* 1498 */     if (shouldRenderFlash && anim.shooting && anim.getShootingAnimationType().showFlashModel() && !player.func_70090_H()) {
/* 1499 */       GlStateManager.func_179094_E();
/* 1500 */       ItemStack itemStack = GunType.getAttachment(demoStack, AttachmentPresetEnum.Barrel);
/* 1501 */       if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/* 1502 */         AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/* 1503 */         if (config.attachment.containsKey(attachmentType.internalName) && 
/* 1504 */           ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).flashModelOffset != null) {
/* 1505 */           GlStateManager.func_179109_b(
/* 1506 */               ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).flashModelOffset.x, 
/* 1507 */               ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).flashModelOffset.y, 
/* 1508 */               ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).flashModelOffset.z);
/*      */         }
/*      */       } 
/*      */       
/* 1512 */       GlStateManager.func_179140_f();
/* 1513 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/* 1514 */       TextureType flashType = gunType.flashType;
/* 1515 */       bindTexture(flashType.resourceLocations.get(anim.flashCount % flashType.resourceLocations.size()));
/* 1516 */       model.renderPart("flashModel");
/* 1517 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, bx, by);
/* 1518 */       GlStateManager.func_179145_e();
/* 1519 */       GlStateManager.func_179121_F();
/*      */     } 
/*      */     
/* 1522 */     GlStateManager.func_179103_j(7424);
/* 1523 */     GlStateManager.func_179121_F();
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void writeScopeGlassDepth(AttachmentType attachmentType, ModelAttachment modelAttachment, boolean isAiming, float worldScale, boolean mask) {
/* 1528 */     if (ScopeUtils.isIndsideGunRendering) {
/*      */       return;
/*      */     }
/* 1531 */     if ((Minecraft.func_71410_x()).field_71441_e != null && 
/* 1532 */       isAiming) {
/* 1533 */       GlStateManager.func_179135_a(mask, mask, mask, mask);
/* 1534 */       renderWorldOntoScope(attachmentType, modelAttachment, worldScale, false);
/* 1535 */       GlStateManager.func_179135_a(true, true, true, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyMirrorTexture() {
/* 1544 */     if (ScopeUtils.isIndsideGunRendering) {
/*      */       return;
/*      */     }
/* 1547 */     if (!OptifineHelper.isShadersEnabled()) {
/*      */       return;
/*      */     }
/* 1550 */     Minecraft mc = Minecraft.func_71410_x();
/* 1551 */     GL43.glCopyImageSubData(ClientProxy.scopeUtils.blurFramebuffer.field_147617_g, 3553, 0, 0, 0, 0, ScopeUtils.SCOPE_MASK_TEX, 3553, 0, 0, 0, 0, mc.field_71443_c, mc.field_71440_d, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void eraseScopeGlassDepth(AttachmentType attachmentType, ModelAttachment modelAttachment, boolean isAiming, float worldScale) {
/* 1560 */     if (ScopeUtils.isIndsideGunRendering) {
/*      */       return;
/*      */     }
/* 1563 */     if (!OptifineHelper.isShadersEnabled()) {
/*      */       return;
/*      */     }
/* 1566 */     if ((Minecraft.func_71410_x()).field_71441_e != null && 
/* 1567 */       isAiming) {
/* 1568 */       GlStateManager.func_179135_a(false, false, false, false);
/* 1569 */       GlStateManager.func_179128_n(5888);
/* 1570 */       GlStateManager.func_179094_E();
/* 1571 */       GlStateManager.func_179128_n(5889);
/* 1572 */       GlStateManager.func_179094_E();
/* 1573 */       ClientProxy.scopeUtils.setupOverlayRendering();
/* 1574 */       ScaledResolution resolution = new ScaledResolution(Minecraft.func_71410_x());
/*      */       
/* 1576 */       if (OptifineHelper.isShadersEnabled()) {
/* 1577 */         Shaders.pushProgram();
/* 1578 */         Shaders.useProgram(Shaders.ProgramNone);
/*      */       } 
/*      */       
/* 1581 */       GL11.glPushAttrib(2048);
/*      */       
/* 1583 */       GL11.glDepthRange(ModConfig.INSTANCE.hud.eraseScopeDepth, ModConfig.INSTANCE.hud.eraseScopeDepth);
/* 1584 */       GlStateManager.func_179092_a(516, 0.0F);
/* 1585 */       GlStateManager.func_179143_c(519);
/* 1586 */       GlStateManager.func_179144_i(ScopeUtils.SCOPE_MASK_TEX);
/* 1587 */       ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/* 1588 */       GlStateManager.func_179143_c(515);
/* 1589 */       GlStateManager.func_179092_a(518, 0.1F);
/* 1590 */       GL11.glPopAttrib();
/*      */       
/* 1592 */       if (ScopeUtils.isRenderHand0) {
/* 1593 */         GL20.glUseProgram(Programs.depthProgram);
/* 1594 */         GlStateManager.func_179144_i(ScopeUtils.DEPTH_ERASE_TEX);
/* 1595 */         ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/* 1596 */         GL20.glUseProgram(0);
/*      */       } 
/*      */       
/* 1599 */       if (OptifineHelper.isShadersEnabled()) {
/* 1600 */         Shaders.popProgram();
/*      */       }
/*      */       
/* 1603 */       GlStateManager.func_179128_n(5889);
/* 1604 */       GlStateManager.func_179121_F();
/* 1605 */       GlStateManager.func_179128_n(5888);
/* 1606 */       GlStateManager.func_179121_F();
/* 1607 */       GlStateManager.func_179135_a(true, true, true, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void writeScopeSoildDepth(boolean isAiming) {
/* 1615 */     if (ScopeUtils.isIndsideGunRendering) {
/*      */       return;
/*      */     }
/* 1618 */     if (!OptifineHelper.isShadersEnabled()) {
/*      */       return;
/*      */     }
/* 1621 */     if ((Minecraft.func_71410_x()).field_71441_e != null && 
/* 1622 */       isAiming) {
/* 1623 */       GlStateManager.func_179135_a(false, false, false, false);
/* 1624 */       GlStateManager.func_179128_n(5888);
/* 1625 */       GlStateManager.func_179094_E();
/* 1626 */       GlStateManager.func_179128_n(5889);
/* 1627 */       GlStateManager.func_179094_E();
/* 1628 */       ClientProxy.scopeUtils.setupOverlayRendering();
/* 1629 */       ScaledResolution resolution = new ScaledResolution(Minecraft.func_71410_x());
/*      */       
/* 1631 */       if (OptifineHelper.isShadersEnabled()) {
/* 1632 */         Shaders.pushProgram();
/* 1633 */         Shaders.useProgram(Shaders.ProgramNone);
/*      */       } 
/*      */       
/* 1636 */       GL20.glUseProgram(Programs.alphaDepthProgram);
/* 1637 */       GlStateManager.func_179138_g(33987);
/* 1638 */       int tex3 = GlStateManager.func_187397_v(32873);
/* 1639 */       GlStateManager.func_179144_i(ClientProxy.scopeUtils.blurFramebuffer.field_147617_g);
/* 1640 */       GlStateManager.func_179138_g(33984);
/* 1641 */       GlStateManager.func_179144_i(ScopeUtils.DEPTH_TEX);
/* 1642 */       ClientProxy.scopeUtils.drawScaledCustomSizeModalRectFlipY(0, 0, 0.0F, 0.0F, 1, 1, resolution.func_78326_a(), resolution.func_78328_b(), 1.0F, 1.0F);
/* 1643 */       GlStateManager.func_179138_g(33987);
/* 1644 */       GlStateManager.func_179144_i(tex3);
/* 1645 */       GlStateManager.func_179138_g(33984);
/* 1646 */       GL20.glUseProgram(0);
/*      */       
/* 1648 */       if (OptifineHelper.isShadersEnabled()) {
/* 1649 */         Shaders.popProgram();
/*      */       }
/*      */       
/* 1652 */       GlStateManager.func_179128_n(5889);
/* 1653 */       GlStateManager.func_179121_F();
/* 1654 */       GlStateManager.func_179128_n(5888);
/* 1655 */       GlStateManager.func_179121_F();
/* 1656 */       GlStateManager.func_179135_a(true, true, true, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void renderScopeGlass(AttachmentType attachmentType, ModelAttachment modelAttachment, boolean isAiming, float worldScale) {
/* 1664 */     if (ScopeUtils.isIndsideGunRendering) {
/*      */       return;
/*      */     }
/* 1667 */     if ((Minecraft.func_71410_x()).field_71441_e != null) {
/* 1668 */       if (isAiming) {
/* 1669 */         if (OptifineHelper.isShadersEnabled()) {
/* 1670 */           Shaders.pushProgram();
/* 1671 */           Shaders.useProgram(Shaders.ProgramNone);
/*      */         } 
/*      */         
/* 1674 */         Minecraft mc = Minecraft.func_71410_x();
/* 1675 */         float alpha = 1.0F - RenderParameters.adsSwitch;
/*      */         
/* 1677 */         if (alpha > 0.2D) {
/* 1678 */           alpha = 1.0F;
/*      */         } else {
/* 1680 */           alpha /= 0.2F;
/*      */         } 
/* 1682 */         GL20.glUseProgram(Programs.normalProgram);
/* 1683 */         GL11.glPushMatrix();
/* 1684 */         int tex = ClientProxy.scopeUtils.blurFramebuffer.field_147617_g;
/*      */         
/* 1686 */         ClientProxy.scopeUtils.blurFramebuffer.func_147610_a(false);
/*      */         
/* 1688 */         GL30.glFramebufferTexture2D(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, ScopeUtils.OVERLAY_TEX, 0);
/* 1689 */         GlStateManager.func_179082_a(0.0F, 0.0F, 0.0F, 0.0F);
/* 1690 */         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 1691 */         GlStateManager.func_179135_a(true, true, true, true);
/* 1692 */         GlStateManager.func_179132_a(true);
/* 1693 */         GlStateManager.func_179086_m(256);
/* 1694 */         copyDepthBuffer();
/* 1695 */         ClientProxy.scopeUtils.blurFramebuffer.func_147610_a(false);
/* 1696 */         GlStateManager.func_179086_m(16384);
/*      */ 
/*      */         
/* 1699 */         GlStateManager.func_179147_l();
/* 1700 */         GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1701 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 1702 */         modelAttachment.renderOverlaySolid(worldScale);
/*      */         
/* 1704 */         GL20.glUseProgram(0);
/* 1705 */         if (OptifineHelper.isShadersEnabled()) {
/* 1706 */           Shaders.popProgram();
/*      */         }
/*      */         
/* 1709 */         GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1710 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, alpha);
/* 1711 */         if (attachmentType.sight.usedDefaultOverlayModelTexture) {
/* 1712 */           renderEngine.func_110577_a(new ResourceLocation("modularwarfare", "textures/skins/black.png"));
/*      */         }
/*      */         
/* 1715 */         GlStateManager.func_179135_a(true, true, true, true);
/* 1716 */         modelAttachment.renderOverlay(worldScale);
/* 1717 */         GlStateManager.func_179135_a(true, true, true, true);
/* 1718 */         GlStateManager.func_179084_k();
/*      */ 
/*      */ 
/*      */         
/* 1722 */         ClientProxy.scopeUtils.blurFramebuffer.func_147610_a(false);
/* 1723 */         GL30.glFramebufferTexture2D(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, tex, 0);
/* 1724 */         GlStateManager.func_179086_m(256);
/* 1725 */         copyDepthBuffer();
/* 1726 */         ClientProxy.scopeUtils.blurFramebuffer.func_147610_a(false);
/* 1727 */         GlStateManager.func_179086_m(16384);
/* 1728 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*      */ 
/*      */         
/* 1731 */         GlStateManager.func_179135_a(true, true, true, false);
/* 1732 */         GlStateManager.func_179084_k();
/*      */         
/* 1734 */         renderWorldOntoScope(attachmentType, modelAttachment, worldScale, false);
/* 1735 */         GlStateManager.func_179147_l();
/* 1736 */         GlStateManager.func_179135_a(true, true, true, true);
/*      */         
/* 1738 */         ContextCapabilities contextCapabilities = GLContext.getCapabilities();
/* 1739 */         if (contextCapabilities.OpenGL43) {
/* 1740 */           GL43.glCopyImageSubData(tex, 3553, 0, 0, 0, 0, ScopeUtils.SCOPE_LIGHTMAP_TEX, 3553, 0, 0, 0, 0, mc.field_71443_c, mc.field_71440_d, 1);
/*      */         } else {
/*      */           
/* 1743 */           GL11.glBindTexture(3553, tex);
/* 1744 */           GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, mc.field_71443_c, mc.field_71440_d);
/*      */         } 
/* 1746 */         OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, OptifineHelper.getDrawFrameBuffer());
/* 1747 */         GL11.glPopMatrix();
/*      */       } else {
/*      */         
/* 1750 */         GL11.glPushMatrix();
/* 1751 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 1752 */         if (attachmentType.sight.usedDefaultOverlayModelTexture) {
/* 1753 */           renderEngine.func_110577_a(new ResourceLocation("modularwarfare", "textures/skins/black.png"));
/*      */         }
/* 1755 */         modelAttachment.renderOverlay(worldScale);
/* 1756 */         GL11.glPopMatrix();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyDepthBuffer() {
/* 1765 */     Minecraft mc = Minecraft.func_71410_x();
/* 1766 */     GL30.glBindFramebuffer(36008, OptifineHelper.getDrawFrameBuffer());
/* 1767 */     GL30.glBindFramebuffer(36009, ClientProxy.scopeUtils.blurFramebuffer.field_147616_f);
/* 1768 */     GlStateManager.func_179135_a(false, false, false, false);
/* 1769 */     GL30.glBlitFramebuffer(0, 0, mc.field_71443_c, mc.field_71440_d, 0, 0, mc.field_71443_c, mc.field_71440_d, 256, 9728);
/* 1770 */     GlStateManager.func_179135_a(true, true, true, true);
/* 1771 */     GL30.glBindFramebuffer(36008, 0);
/* 1772 */     GL30.glBindFramebuffer(36009, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   private void renderWorldOntoScope(AttachmentType type, ModelAttachment modelAttachment, float worldScale, boolean isLightOn) {
/* 1778 */     GL11.glPushMatrix();
/*      */     
/* 1780 */     if (isLightOn) {
/* 1781 */       renderEngine.func_110577_a(new ResourceLocation("modularwarfare", "textures/skins/white.png"));
/* 1782 */       GL11.glDisable(2896);
/* 1783 */       (Minecraft.func_71410_x()).field_71460_t.func_175072_h();
/*      */       
/* 1785 */       modelAttachment.renderScope(worldScale);
/*      */       
/* 1787 */       GL11.glEnable(2896);
/* 1788 */       (Minecraft.func_71410_x()).field_71460_t.func_180436_i();
/*      */     } else {
/* 1790 */       if (debug) {
/* 1791 */         renderEngine.func_110577_a(new ResourceLocation("modularwarfare", "textures/skins/black.png"));
/*      */       } else {
/* 1793 */         renderEngine.func_110577_a(new ResourceLocation("modularwarfare", "textures/skins/white.png"));
/*      */       } 
/* 1795 */       modelAttachment.renderScope(worldScale);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1828 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public void renderAttachment(GunEnhancedRenderConfig config, String type, String name, Runnable run) {
/* 1832 */     if (config.attachmentGroup.containsKey(type)) {
/* 1833 */       applyTransform((GunEnhancedRenderConfig.Transform)config.attachmentGroup.get(type));
/*      */     }
/* 1835 */     if (config.attachment.containsKey(name)) {
/* 1836 */       applyTransform((GunEnhancedRenderConfig.Transform)config.attachment.get(name));
/*      */     }
/* 1838 */     run.run();
/*      */   }
/*      */   
/*      */   public void applyTransform(GunEnhancedRenderConfig.Transform transform) {
/* 1842 */     GlStateManager.func_179109_b(transform.translate.x, transform.translate.y, transform.translate.z);
/* 1843 */     GlStateManager.func_179152_a(transform.scale.x, transform.scale.y, transform.scale.z);
/* 1844 */     GlStateManager.func_179114_b(transform.rotate.y, 0.0F, 1.0F, 0.0F);
/* 1845 */     GlStateManager.func_179114_b(transform.rotate.x, 1.0F, 0.0F, 0.0F);
/* 1846 */     GlStateManager.func_179114_b(transform.rotate.z, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public void blendTransform(final ModelEnhancedGun model, ItemStack gunStack, final boolean basicSprint, float time, final float sprintTime, final float alpha, String hand, boolean applySprint, boolean skin, Runnable runnable) {
/* 1851 */     float ammoPer = 0.0F;
/* 1852 */     if (gunStack.func_77978_p() != null) {
/* 1853 */       if (ItemGun.hasAmmoLoaded(gunStack)) {
/* 1854 */         ItemStack ammoStack = new ItemStack(gunStack.func_77978_p().func_74775_l("ammo"));
/* 1855 */         if (ammoStack.func_77978_p() != null && ammoStack.func_77973_b() instanceof ItemAmmo) {
/*      */           
/* 1857 */           ItemAmmo itemAmmo = (ItemAmmo)ammoStack.func_77973_b();
/* 1858 */           Integer currentMagcount = null;
/* 1859 */           if (ammoStack.func_77978_p().func_74764_b("magcount")) {
/* 1860 */             currentMagcount = Integer.valueOf(ammoStack.func_77978_p().func_74762_e("magcount"));
/*      */           }
/* 1862 */           int currentAmmoCount = ReloadHelper.getBulletOnMag(ammoStack, currentMagcount);
/* 1863 */           ammoPer = currentAmmoCount / itemAmmo.type.ammoCapacity;
/*      */         } 
/*      */       } 
/* 1866 */       if (ItemGun.getUsedBullet(gunStack, ((ItemGun)gunStack.func_77973_b()).type) != null);
/*      */     } 
/*      */ 
/*      */     
/* 1870 */     final float ammoPerParam = ammoPer;
/*      */     
/* 1872 */     model.setAnimationCalBlender(new GltfRenderModel.NodeAnimationBlender("FirstPersonBlender")
/*      */         {
/*      */           public void handle(DataNode node, Matrix4f mat)
/*      */           {
/* 1876 */             if (!basicSprint && 
/* 1877 */               alpha != 0.0F) {
/*      */               
/* 1879 */               Matrix4f begin_transform = mat;
/* 1880 */               DataAnimation.Transform end_transform = model.findLocalTransform(node.name, sprintTime);
/* 1881 */               if (end_transform != null)
/*      */               {
/*      */                 
/* 1884 */                 if (node.name.equals("root") || node.name.equals("sprint_lefthand") || node.name
/* 1885 */                   .equals("sprint_righthand") || node.name.equals("root_bone") || node.name
/* 1886 */                   .equals("sprint_lefthand_bone") || node.name.equals("sprint_righthand_bone")) {
/*      */ 
/*      */                   
/* 1889 */                   Quaternionf quat = new Quaternionf();
/* 1890 */                   quat.setFromUnnormalized((Matrix4fc)begin_transform);
/* 1891 */                   quat.normalize().slerp((Quaternionfc)end_transform.rot.normalize(), alpha);
/* 1892 */                   Vector3f pos = new Vector3f();
/* 1893 */                   begin_transform.getTranslation(pos);
/* 1894 */                   pos.set(pos.x + (end_transform.pos.x - pos.x) * alpha, pos.y + (end_transform.pos.y - pos.y) * alpha, pos.z + (end_transform.pos.z - pos.z) * alpha);
/*      */                   
/* 1896 */                   Vector3f size = new Vector3f();
/* 1897 */                   begin_transform.getScale(size);
/* 1898 */                   size.set(size.x + (end_transform.size.x - size.x) * alpha, size.y + (end_transform.size.y - size.y) * alpha, size.z + (end_transform.size.z - size.z) * alpha);
/*      */                   
/* 1900 */                   mat.identity();
/* 1901 */                   mat.translate((Vector3fc)pos);
/* 1902 */                   mat.scale((Vector3fc)size);
/* 1903 */                   mat.rotate((Quaternionfc)quat);
/*      */                 } 
/*      */               }
/*      */             } 
/* 1907 */             GunEnhancedRenderConfig.ObjectControl cfg = (GunEnhancedRenderConfig.ObjectControl)((GunEnhancedRenderConfig)model.config).objectControl.get(node.name);
/* 1908 */             if (cfg != null) {
/* 1909 */               float per = ammoPerParam;
/* 1910 */               if (!cfg.progress) {
/* 1911 */                 per = 1.0F - per;
/*      */               }
/*      */               
/* 1914 */               mat.translate(cfg.translate.x * per, cfg.translate.y * per, cfg.translate.z * per);
/* 1915 */               mat.rotate(cfg.rotate.y * per * 3.14F / 180.0F, 0.0F, 1.0F, 0.0F);
/* 1916 */               mat.rotate(cfg.rotate.x * per * 3.14F / 180.0F, 1.0F, 0.0F, 0.0F);
/* 1917 */               mat.rotate(cfg.rotate.z * per * 3.14F / 180.0F, 0.0F, 0.0F, 1.0F);
/*      */             } 
/*      */           }
/*      */         });
/* 1921 */     model.updateAnimation(time, skin);
/* 1922 */     runnable.run();
/* 1923 */     model.setAnimationCalBlender(null);
/*      */   }
/*      */   
/*      */   public Matrix4f getGlobalTransform(EnhancedModel model, String name) {
/* 1927 */     return model.getGlobalTransform(name);
/*      */   }
/*      */   
/*      */   private Matrix3f genMatrixFromQuaternion(Quaternion quaternion) {
/* 1931 */     Matrix3f matrix3f = new Matrix3f();
/* 1932 */     matrix3f.m00 = 1.0F - 2.0F * quaternion.y * quaternion.y - 2.0F * quaternion.z * quaternion.z;
/* 1933 */     matrix3f.m01 = 2.0F * quaternion.x * quaternion.y + 2.0F * quaternion.w * quaternion.z;
/* 1934 */     matrix3f.m02 = 2.0F * quaternion.x * quaternion.z - 2.0F * quaternion.w * quaternion.y;
/*      */     
/* 1936 */     matrix3f.m10 = 2.0F * quaternion.x * quaternion.y - 2.0F * quaternion.w * quaternion.z;
/* 1937 */     matrix3f.m11 = 1.0F - 2.0F * quaternion.x * quaternion.x - 2.0F * quaternion.z * quaternion.z;
/* 1938 */     matrix3f.m12 = 2.0F * quaternion.y * quaternion.z + 2.0F * quaternion.w * quaternion.x;
/*      */     
/* 1940 */     matrix3f.m20 = 2.0F * quaternion.x * quaternion.z + 2.0F * quaternion.w * quaternion.y;
/* 1941 */     matrix3f.m21 = 2.0F * quaternion.y * quaternion.z - 2.0F * quaternion.w * quaternion.x;
/* 1942 */     matrix3f.m22 = 1.0F - 2.0F * quaternion.x * quaternion.x - 2.0F * quaternion.y * quaternion.y;
/* 1943 */     return matrix3f;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   private void genMatrix(Matrix3f m, float[] floats) {
/* 1949 */     m.m00 = floats[0];
/* 1950 */     m.m01 = floats[4];
/* 1951 */     m.m02 = floats[8];
/*      */     
/* 1953 */     m.m10 = floats[1];
/* 1954 */     m.m11 = floats[5];
/* 1955 */     m.m12 = floats[9];
/*      */     
/* 1957 */     m.m20 = floats[2];
/* 1958 */     m.m21 = floats[6];
/* 1959 */     m.m22 = floats[10];
/*      */   }
/*      */   
/*      */   public boolean onGltfRenderCallback(String part) {
/* 1963 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static float toRadians(float angdeg) {
/* 1968 */     return angdeg / 180.0F * 3.1415927F;
/*      */   }
/*      */   
/*      */   public void color(float r, float g, float b, float a) {
/* 1972 */     this.r = r;
/* 1973 */     this.g = g;
/* 1974 */     this.b = b;
/* 1975 */     this.a = a;
/* 1976 */     GlStateManager.func_179131_c(r, g, b, a);
/*      */   }
/*      */ 
/*      */   
/*      */   public void bindTexture(String type, String fileName) {
/* 1981 */     super.bindTexture(type, fileName);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void bindTexture(ResourceLocation location) {
/* 1987 */     this.bindingTexture = location;
/* 1988 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(this.bindingTexture);
/*      */   }
/*      */   
/*      */   public void bindPlayerSkin() {
/* 1992 */     this.bindingTexture = (Minecraft.func_71410_x()).field_71439_g.func_110306_p();
/* 1993 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(this.bindingTexture);
/*      */   }
/*      */   
/*      */   public void bindCustomHands(TextureType handTextureType) {
/* 1997 */     if (handTextureType.resourceLocations != null) {
/* 1998 */       this.bindingTexture = handTextureType.resourceLocations.get(0);
/*      */     }
/* 2000 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(this.bindingTexture);
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\enhanced\renderers\RenderGunEnhanced.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */