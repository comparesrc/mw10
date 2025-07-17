/*      */ package com.modularwarfare.client.fpp.basic.renderers;
/*      */ import com.modularwarfare.ModConfig;
/*      */ import com.modularwarfare.api.GunBobbingEvent;
/*      */ import com.modularwarfare.api.RenderHandFisrtPersonEvent;
/*      */ import com.modularwarfare.api.RenderHandSleeveEvent;
/*      */ import com.modularwarfare.api.WeaponAnimation;
/*      */ import com.modularwarfare.api.WeaponAnimations;
/*      */ import com.modularwarfare.client.ClientProxy;
/*      */ import com.modularwarfare.client.ClientRenderHooks;
/*      */ import com.modularwarfare.client.fpp.basic.animations.AnimStateMachine;
/*      */ import com.modularwarfare.client.fpp.basic.animations.ReloadType;
/*      */ import com.modularwarfare.client.fpp.basic.animations.StateEntry;
/*      */ import com.modularwarfare.client.fpp.basic.animations.StateType;
/*      */ import com.modularwarfare.client.fpp.basic.configs.GunRenderConfig;
/*      */ import com.modularwarfare.client.fpp.basic.models.objects.BreakActionData;
/*      */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderType;
/*      */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderer;
/*      */ import com.modularwarfare.client.fpp.basic.models.objects.RenderVariables;
/*      */ import com.modularwarfare.client.model.ModelAmmo;
/*      */ import com.modularwarfare.client.model.ModelAttachment;
/*      */ import com.modularwarfare.client.model.ModelBullet;
/*      */ import com.modularwarfare.client.model.ModelCustomArmor;
/*      */ import com.modularwarfare.client.model.ModelGun;
/*      */ import com.modularwarfare.client.scope.ScopeUtils;
/*      */ import com.modularwarfare.client.shader.Programs;
/*      */ import com.modularwarfare.common.armor.ItemMWArmor;
/*      */ import com.modularwarfare.common.guns.AmmoType;
/*      */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*      */ import com.modularwarfare.common.guns.AttachmentType;
/*      */ import com.modularwarfare.common.guns.GunType;
/*      */ import com.modularwarfare.common.guns.ItemAmmo;
/*      */ import com.modularwarfare.common.guns.ItemAttachment;
/*      */ import com.modularwarfare.common.guns.ItemBullet;
/*      */ import com.modularwarfare.common.guns.ItemGun;
/*      */ import com.modularwarfare.common.guns.WeaponFireMode;
/*      */ import com.modularwarfare.common.guns.WeaponScopeModeType;
/*      */ import com.modularwarfare.common.guns.WeaponType;
/*      */ import com.modularwarfare.common.textures.TextureType;
/*      */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*      */ import com.modularwarfare.utility.ModUtil;
/*      */ import com.modularwarfare.utility.OptifineHelper;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Optional;
/*      */ import java.util.Random;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.AbstractClientPlayer;
/*      */ import net.minecraft.client.model.ModelBiped;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*      */ import net.minecraft.client.renderer.entity.Render;
/*      */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumHandSide;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Timer;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraftforge.common.MinecraftForge;
/*      */ import net.minecraftforge.fml.common.eventhandler.Event;
/*      */ import net.minecraftforge.fml.relauncher.Side;
/*      */ import net.minecraftforge.fml.relauncher.SideOnly;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL20;
/*      */ import org.lwjgl.opengl.GL30;
/*      */ import org.lwjgl.util.vector.Vector3f;
/*      */ 
/*      */ public class RenderGunStatic extends CustomItemRenderer {
/*   77 */   public static float prevBobModifier = 0.0F;
/*      */   
/*      */   public static boolean isLightOn;
/*      */   
/*      */   public int oldMagCount;
/*      */   
/*      */   private float slowDiff;
/*      */   
/*      */   private ItemStack light;
/*      */   private Timer timer;
/*      */   
/*      */   public static String getStaticArmState(ModelGun model, AnimStateMachine anim) {
/*   89 */     Optional<StateEntry> currentShootState = anim.getShootState();
/*   90 */     Optional<StateEntry> currentReloadState = anim.getReloadState();
/*   91 */     float pumpCurrent = currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.PumpOut || ((StateEntry)currentShootState.get()).stateType == StateType.PumpIn) ? ((StateEntry)currentShootState.get()).currentValue : 1.0F) : 1.0F;
/*   92 */     float chargeCurrent = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Charge || ((StateEntry)currentReloadState.get()).stateType == StateType.Uncharge) ? ((StateEntry)currentReloadState.get()).currentValue : 1.0F) : (currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.Charge || ((StateEntry)currentShootState.get()).stateType == StateType.Uncharge) ? ((StateEntry)currentShootState.get()).currentValue : 1.0F) : 1.0F);
/*      */     
/*   94 */     if (model.config.arms.leftHandAmmo) {
/*   95 */       if (anim.isReloadState(StateType.MoveHands) || anim.isReloadState(StateType.ReturnHands)) return "ToFrom"; 
/*   96 */       if (anim.isShootState(StateType.MoveHands) || anim.isShootState(StateType.ReturnHands))
/*   97 */         return "ToFrom"; 
/*   98 */       if (!anim.reloading && model.isType(GunRenderConfig.Arms.EnumArm.Right, GunRenderConfig.Arms.EnumAction.Pump))
/*   99 */         return "Pump"; 
/*  100 */       if (chargeCurrent < 0.66D && model.isType(GunRenderConfig.Arms.EnumArm.Right, GunRenderConfig.Arms.EnumAction.Charge) && chargeCurrent != -1.0F)
/*  101 */         return "Charge"; 
/*  102 */       if ((anim.isReloadState(StateType.Charge) || anim.isReloadState(StateType.Uncharge)) && model.isType(GunRenderConfig.Arms.EnumArm.Right, GunRenderConfig.Arms.EnumAction.Bolt))
/*  103 */         return "Bolt"; 
/*  104 */       if ((anim.isShootState(StateType.Charge) || anim.isShootState(StateType.Uncharge)) && model.isType(GunRenderConfig.Arms.EnumArm.Right, GunRenderConfig.Arms.EnumAction.Bolt))
/*  105 */         return "Bolt"; 
/*  106 */       if (!anim.reloading && !model.isType(GunRenderConfig.Arms.EnumArm.Right, GunRenderConfig.Arms.EnumAction.Pump))
/*  107 */         return "Default"; 
/*  108 */       return "Reload";
/*      */     } 
/*  110 */     if (!anim.reloading && model.isType(GunRenderConfig.Arms.EnumArm.Left, GunRenderConfig.Arms.EnumAction.Pump))
/*  111 */       return "Pump"; 
/*  112 */     if (chargeCurrent < 0.9D && model.isType(GunRenderConfig.Arms.EnumArm.Right, GunRenderConfig.Arms.EnumAction.Charge) && chargeCurrent != -1.0F)
/*  113 */       return "Charge"; 
/*  114 */     if (chargeCurrent < 0.9D && model.isType(GunRenderConfig.Arms.EnumArm.Right, GunRenderConfig.Arms.EnumAction.Bolt))
/*  115 */       return "Bolt"; 
/*  116 */     if (!anim.reloading && !model.isType(GunRenderConfig.Arms.EnumArm.Left, GunRenderConfig.Arms.EnumAction.Pump))
/*  117 */       return "Default"; 
/*  118 */     return "Reload";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getMovingArmState(ModelGun model, AnimStateMachine anim) {
/*  124 */     WeaponAnimation wepAnim = WeaponAnimations.getAnimation(model.config.extra.reloadAnimation);
/*  125 */     Optional<StateEntry> currentShootState = anim.getShootState();
/*  126 */     Optional<StateEntry> currentReloadState = anim.getReloadState();
/*  127 */     float pumpCurrent = currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.PumpOut || ((StateEntry)currentShootState.get()).stateType == StateType.PumpIn) ? ((StateEntry)currentShootState.get()).currentValue : 1.0F) : 1.0F;
/*  128 */     float chargeCurrent = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Charge || ((StateEntry)currentReloadState.get()).stateType == StateType.Uncharge) ? ((StateEntry)currentReloadState.get()).currentValue : 1.0F) : 1.0F;
/*      */ 
/*      */     
/*  131 */     if (!model.config.arms.leftHandAmmo) {
/*  132 */       if ((anim.isShootState(StateType.PumpIn) || anim.isShootState(StateType.PumpOut)) && pumpCurrent < 0.9D && model.isType(GunRenderConfig.Arms.EnumArm.Right, GunRenderConfig.Arms.EnumAction.Charge) && pumpCurrent != -1.0F)
/*  133 */         return "Pump"; 
/*  134 */       if (anim.isReloadState(StateType.Charge) && chargeCurrent < 0.9D && model.isType(GunRenderConfig.Arms.EnumArm.Right, GunRenderConfig.Arms.EnumAction.Bolt))
/*  135 */         return "Bolt"; 
/*  136 */       if (!anim.reloading) return "Default"; 
/*  137 */       if (anim.isReloadState(StateType.Load)) return "Load";
/*      */       
/*  139 */       return "Reload";
/*      */     } 
/*  141 */     if (anim.isReloadState(StateType.Charge) && model.isType(GunRenderConfig.Arms.EnumArm.Left, GunRenderConfig.Arms.EnumAction.Charge) && chargeCurrent != -1.0F)
/*  142 */       return "Charge"; 
/*  143 */     if ((anim.isShootState(StateType.PumpIn) || anim.isShootState(StateType.PumpOut)) && !anim.reloading && model.isType(GunRenderConfig.Arms.EnumArm.Left, GunRenderConfig.Arms.EnumAction.Pump))
/*  144 */       return "Pump"; 
/*  145 */     if (!anim.reloading) return "Default"; 
/*  146 */     if (anim.isReloadState(StateType.Load)) return "Load"; 
/*  147 */     if (anim.isReloadState(StateType.Unload)) return "Unload"; 
/*  148 */     return "Reload";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderItem(CustomItemRenderType type, EnumHand hand, ItemStack item, Object... data) {
/*  154 */     if (!(item.func_77973_b() instanceof ItemGun)) {
/*      */       return;
/*      */     }
/*  157 */     GunType gunType = ((ItemGun)item.func_77973_b()).type;
/*  158 */     if (gunType == null) {
/*      */       return;
/*      */     }
/*  161 */     ModelGun model = (ModelGun)gunType.model;
/*  162 */     if (model == null) {
/*      */       return;
/*      */     }
/*  165 */     AnimStateMachine anim = (data.length >= 2) ? ((data[1] instanceof EntityPlayer) ? ClientRenderHooks.getAnimMachine((EntityLivingBase)data[1]) : new AnimStateMachine()) : new AnimStateMachine();
/*  166 */     GlStateManager.func_179103_j(7425);
/*  167 */     boolean glow = ObjModelRenderer.glowTxtureMode;
/*  168 */     ObjModelRenderer.glowTxtureMode = true;
/*  169 */     renderGun(type, item, anim, gunType, data);
/*  170 */     GlStateManager.func_179103_j(7424);
/*  171 */     ObjModelRenderer.glowTxtureMode = glow; } private void renderGun(CustomItemRenderType renderType, ItemStack item, AnimStateMachine anim, GunType gunType, Object... data) { EntityLivingBase entityLivingBase; float crouchOffset, f5, rotateX, rotateY, rotateZ, translateX, translateY, translateZ, crouchZoom, hipRecover; Vector3f customHipRotation, customHipTranslate, customAimRotation, customAimTranslate; float partialTicks, bobModifier, yawReducer; GunBobbingEvent event; EntityPlayer entityplayer; float f1, f2, f3, f4; Vector3f customAttachmentModeRotation; float gunRotX, gunRotY;
/*      */     WeaponScopeModeType modeType;
/*      */     IBakedModel lightmodel;
/*      */     int lightVar;
/*      */     float wantedDiff;
/*  176 */     Minecraft mc = Minecraft.func_71410_x();
/*  177 */     ModelGun model = (ModelGun)gunType.model;
/*      */ 
/*      */     
/*  180 */     float min = -1.5F;
/*  181 */     float max = 1.5F;
/*  182 */     float randomNum = (new Random()).nextFloat();
/*  183 */     float randomShake = min + randomNum * (max - min);
/*      */ 
/*      */     
/*  186 */     Optional<StateEntry> currentReloadState = anim.getReloadState();
/*  187 */     Optional<StateEntry> currentShootState = anim.getShootState();
/*      */     
/*  189 */     float tiltProgress = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Tilt || ((StateEntry)currentReloadState.get()).stateType == StateType.Untilt) ? ((StateEntry)currentReloadState.get()).currentValue : (anim.tiltHold ? 1.0F : 0.0F)) : 0.0F;
/*  190 */     float worldScale = 0.0625F;
/*      */     
/*  192 */     if (renderEngine == null) {
/*  193 */       renderEngine = (Minecraft.func_71410_x()).field_71446_o;
/*      */     }
/*  195 */     if (model == null) {
/*      */       return;
/*      */     }
/*  198 */     GL11.glPushMatrix();
/*      */     
/*  200 */     switch (renderType) {
/*      */       
/*      */       case ENTITY:
/*  203 */         GL11.glTranslatef(-0.5F, -0.08F, 0.0F);
/*  204 */         GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
/*      */         
/*  206 */         GL11.glTranslatef(model.config.itemFrame.translate.x * worldScale, model.config.itemFrame.translate.y * worldScale, model.config.itemFrame.translate.z * worldScale);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case EQUIPPED:
/*  212 */         entityLivingBase = (EntityLivingBase)data[1];
/*      */         
/*  214 */         crouchOffset = 0.0F;
/*  215 */         GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
/*  216 */         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/*  217 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*  218 */         GL11.glTranslatef(0.25F, 0.0F, -0.05F);
/*  219 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/*      */         
/*  221 */         GL11.glScalef(model.config.thirdPerson.thirdPersonScale, model.config.thirdPerson.thirdPersonScale, model.config.thirdPerson.thirdPersonScale);
/*  222 */         GL11.glTranslatef(model.config.thirdPerson.thirdPersonOffset.x, model.config.thirdPerson.thirdPersonOffset.y + crouchOffset, model.config.thirdPerson.thirdPersonOffset.z);
/*      */         break;
/*      */ 
/*      */       
/*      */       case BACK:
/*  227 */         entityLivingBase = (EntityLivingBase)data[1];
/*      */         
/*  229 */         GL11.glScalef(model.config.thirdPerson.thirdPersonScale, model.config.thirdPerson.thirdPersonScale, model.config.thirdPerson.thirdPersonScale);
/*  230 */         GL11.glTranslatef(-0.32F, 1.3F, -0.23F);
/*  231 */         GL11.glTranslatef(model.config.thirdPerson.backPersonOffset.x, model.config.thirdPerson.backPersonOffset.y, model.config.thirdPerson.backPersonOffset.z);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  236 */         GL11.glRotatef(90.0F, 0.0F, 20.0F, 0.0F);
/*  237 */         GL11.glRotatef(270.0F, 0.0F, 0.0F, -90.0F);
/*  238 */         GL11.glRotatef(90.0F, 20.0F, 0.0F, 0.0F);
/*      */         
/*  240 */         GL11.glRotatef(20.0F, 0.0F, 0.0F, 20.0F);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case EQUIPPED_FIRST_PERSON:
/*  246 */         entityLivingBase = (EntityLivingBase)data[1];
/*  247 */         f5 = model.config.extra.modelScale;
/*  248 */         rotateX = 0.0F;
/*  249 */         rotateY = 0.0F;
/*  250 */         rotateZ = 0.0F;
/*  251 */         translateX = 0.0F;
/*  252 */         translateY = 0.0F;
/*  253 */         translateZ = 0.0F;
/*  254 */         crouchZoom = anim.reloading ? 0.0F : (anim.isReloadState(StateType.Charge) ? 0.0F : model.config.extra.crouchZoom);
/*  255 */         hipRecover = RenderParameters.reloadSwitch;
/*      */ 
/*      */         
/*  258 */         customHipRotation = new Vector3f(model.config.aim.rotateHipPosition.x + model.config.sprint.sprintRotate.x * RenderParameters.sprintSwitch * hipRecover, model.config.aim.rotateHipPosition.y + model.config.sprint.sprintRotate.y * RenderParameters.sprintSwitch * hipRecover, model.config.aim.rotateHipPosition.z + model.config.sprint.sprintRotate.z * RenderParameters.sprintSwitch * hipRecover);
/*  259 */         customHipTranslate = new Vector3f(model.config.aim.translateHipPosition.x + model.config.sprint.sprintTranslate.x * RenderParameters.sprintSwitch * hipRecover, model.config.aim.translateHipPosition.y + 0.04F + model.config.sprint.sprintTranslate.y * RenderParameters.sprintSwitch * hipRecover, model.config.aim.translateHipPosition.z - 0.15F + model.config.sprint.sprintTranslate.z * RenderParameters.sprintSwitch * hipRecover);
/*      */         
/*  261 */         customAimRotation = new Vector3f(model.config.aim.rotateAimPosition.x, model.config.aim.rotateAimPosition.y, model.config.aim.rotateAimPosition.z);
/*  262 */         customAimTranslate = new Vector3f(model.config.aim.translateAimPosition.x, model.config.aim.translateAimPosition.y, model.config.aim.translateAimPosition.z);
/*      */         
/*  264 */         for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/*  265 */           ItemStack itemStack = GunType.getAttachment(item, attachment);
/*  266 */           if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*  267 */             AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/*  268 */             if (attachmentType.attachmentType == AttachmentPresetEnum.Sight && 
/*  269 */               model.config.attachments.aimPointMap != null) {
/*  270 */               for (String internalName : model.config.attachments.aimPointMap.keySet()) {
/*  271 */                 if (internalName.equals(attachmentType.internalName)) {
/*  272 */                   Vector3f trans = ((ArrayList<Vector3f>)model.config.attachments.aimPointMap.get(internalName)).get(0);
/*  273 */                   Vector3f rot = ((ArrayList<Vector3f>)model.config.attachments.aimPointMap.get(internalName)).get(1);
/*  274 */                   customAimTranslate.translate(trans.x * worldScale, -trans.y * worldScale, -trans.z * worldScale);
/*  275 */                   customAimRotation.translate(rot.x, rot.y, rot.z);
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  283 */         RenderParameters.VAL = (float)(Math.sin((RenderParameters.SMOOTH_SWING / 140.0F)) * 1.0D);
/*  284 */         RenderParameters.VAL2 = (float)(Math.sin((RenderParameters.SMOOTH_SWING / 100.0F)) * 1.0D);
/*  285 */         RenderParameters.VALROT = (float)(Math.sin((RenderParameters.SMOOTH_SWING / 90.0F)) * 1.2000000476837158D);
/*  286 */         RenderParameters.CROSS_ROTATE = 0.0F;
/*      */ 
/*      */         
/*  289 */         if (!anim.shooting) {
/*  290 */           RenderParameters.VALSPRINT = (float)(Math.cos((RenderParameters.SMOOTH_SWING / 5.0F)) * 5.0D) * (0.95F - RenderParameters.adsSwitch) * gunType.moveSpeedModifier;
/*      */         }
/*      */         
/*  293 */         RenderParameters.adsSwitch = anim.reloading ? 0.0F : RenderParameters.adsSwitch;
/*      */         
/*  295 */         rotateX = 0.0F + customHipRotation.x - RenderParameters.VALROT * (0.95F - RenderParameters.adsSwitch) - 0.0F + customAimRotation.x + customHipRotation.x * RenderParameters.adsSwitch;
/*  296 */         rotateY = 46.0F + customHipRotation.y - (1.0F + customAimRotation.y + customHipRotation.y) * RenderParameters.adsSwitch;
/*  297 */         rotateZ = 35.0F * RenderParameters.collideFrontDistance + 1.0F + customHipRotation.z - (1.0F + customAimRotation.z + customHipRotation.z) * RenderParameters.adsSwitch;
/*      */         
/*  299 */         translateX = -1.3F + customHipTranslate.x - (0.0F + customAimTranslate.x + customHipTranslate.x) * RenderParameters.adsSwitch;
/*  300 */         translateY = 0.7F * RenderParameters.collideFrontDistance + 0.834F + customHipTranslate.y - RenderParameters.VAL / 500.0F * (0.95F - RenderParameters.adsSwitch) - (-0.064F + customAimTranslate.y + customHipTranslate.y) * RenderParameters.adsSwitch;
/*  301 */         translateZ = -1.05F + customHipTranslate.z - RenderParameters.VAL2 / 500.0F * (0.95F - RenderParameters.adsSwitch) - (0.35F + customAimTranslate.z + customHipTranslate.z) * RenderParameters.adsSwitch;
/*      */         
/*  303 */         if (this.timer == null) {
/*  304 */           this.timer = (Timer)ReflectionHelper.getPrivateValue(Minecraft.class, mc, "timer", "field_71428_T");
/*      */         }
/*      */         
/*  307 */         partialTicks = this.timer.field_194147_b;
/*      */ 
/*      */         
/*  310 */         bobModifier = !entityLivingBase.func_70051_ag() ? ((RenderParameters.adsSwitch == 0.0F) ? (!anim.reloading ? 0.7F : 0.2F) : 0.15F) : (!anim.reloading ? ((RenderParameters.adsSwitch == 0.0F) ? 0.75F : 0.15F) : 0.4F);
/*  311 */         yawReducer = 1.0F;
/*  312 */         if (ClientRenderHooks.isAimingScope) {
/*  313 */           bobModifier *= 0.5F;
/*  314 */           yawReducer = 0.5F;
/*      */         } 
/*  316 */         event = new GunBobbingEvent(bobModifier);
/*  317 */         MinecraftForge.EVENT_BUS.post((Event)event);
/*  318 */         bobModifier = event.bobbing;
/*  319 */         entityplayer = (EntityPlayer)Minecraft.func_71410_x().func_175606_aa();
/*  320 */         f1 = (entityplayer.field_70140_Q - entityplayer.field_70141_P) * bobModifier;
/*  321 */         f2 = -(entityplayer.field_70140_Q + f1 * partialTicks) * bobModifier;
/*  322 */         f3 = (entityplayer.field_71107_bF + (entityplayer.field_71109_bG - entityplayer.field_71107_bF) * partialTicks) * bobModifier * yawReducer;
/*  323 */         f4 = (entityplayer.field_70727_aS + (entityplayer.field_70726_aT - entityplayer.field_70727_aS) * partialTicks) * bobModifier;
/*  324 */         GlStateManager.func_179109_b(MathHelper.func_76126_a(f2 * 3.1415927F) * f3 * 0.5F * anim.reloadProgress, -Math.abs(MathHelper.func_76134_b(f2 * 3.1415927F) * f3) * anim.reloadProgress, 0.0F);
/*  325 */         GlStateManager.func_179114_b(MathHelper.func_76126_a(f2 * 3.1415927F) * f3 * 3.0F, 0.0F, 0.0F, 1.0F);
/*  326 */         GlStateManager.func_179114_b(Math.abs(MathHelper.func_76134_b(f2 * 3.1415927F - 0.2F) * f3) * 5.0F, 1.0F, 0.0F, 0.0F);
/*  327 */         GlStateManager.func_179114_b(f4, 1.0F, 0.0F, 0.0F);
/*  328 */         prevBobModifier = bobModifier;
/*      */ 
/*      */         
/*  331 */         GL11.glRotatef(rotateX, 1.0F, 0.0F, 0.0F);
/*  332 */         GL11.glRotatef(rotateY, 0.0F, 1.0F, 0.0F);
/*  333 */         GL11.glRotatef(rotateZ, 0.0F, 0.0F, 1.0F);
/*  334 */         GL11.glTranslatef(translateX + crouchZoom * RenderParameters.crouchSwitch, 0.0F, 0.0F);
/*  335 */         GL11.glTranslatef(0.0F, translateY, 0.0F);
/*  336 */         GL11.glTranslatef(0.0F, 0.0F, translateZ);
/*      */         
/*  338 */         if (!(Minecraft.func_71410_x()).field_71439_g.field_70122_E) {
/*  339 */           RenderParameters.VALSPRINT *= 0.15F;
/*      */         }
/*  341 */         GL11.glRotatef((Minecraft.func_71410_x()).field_71439_g.func_70051_ag() ? RenderParameters.VALSPRINT : 0.0F, 1.0F, 1.0F, -1.0F);
/*      */         
/*  343 */         customAttachmentModeRotation = new Vector3f(model.config.attachments.attachmentModeRotate.x * RenderParameters.attachmentSwitch, model.config.attachments.attachmentModeRotate.y * RenderParameters.attachmentSwitch, model.config.attachments.attachmentModeRotate.z * RenderParameters.attachmentSwitch);
/*  344 */         GL11.glRotatef(customAttachmentModeRotation.x, 1.0F, 0.0F, 0.0F);
/*  345 */         GL11.glRotatef(customAttachmentModeRotation.y, 0.0F, 1.0F, 0.0F);
/*  346 */         GL11.glRotatef(customAttachmentModeRotation.z, 0.0F, 0.0F, 1.0F);
/*      */         
/*  348 */         gunRotX = RenderParameters.GUN_ROT_X_LAST + (RenderParameters.GUN_ROT_X - RenderParameters.GUN_ROT_X_LAST) * partialTicks;
/*  349 */         gunRotY = RenderParameters.GUN_ROT_Y_LAST + (RenderParameters.GUN_ROT_Y - RenderParameters.GUN_ROT_Y_LAST) * partialTicks;
/*      */         
/*  351 */         GL11.glRotatef(gunRotX, 0.0F, -1.0F, 0.0F);
/*  352 */         GL11.glRotatef(gunRotY, 0.0F, 0.0F, -1.0F);
/*      */         
/*  354 */         GL11.glRotatef(RenderParameters.GUN_BALANCING_X * 4.0F * (1.0F - RenderParameters.adsSwitch), -1.0F, 0.0F, 0.0F);
/*  355 */         GL11.glRotatef((float)Math.sin(Math.PI * RenderParameters.GUN_BALANCING_X) * (1.0F - RenderParameters.adsSwitch), -1.0F, 0.0F, 0.0F);
/*  356 */         GL11.glRotatef(RenderParameters.GUN_BALANCING_X * RenderParameters.adsSwitch * 0.4F, -1.0F, 0.0F, 0.0F);
/*      */         
/*  358 */         GL11.glRotatef(RenderParameters.GUN_BALANCING_Y * 2.0F * (1.0F - RenderParameters.adsSwitch), 0.0F, 0.0F, -1.0F);
/*      */ 
/*      */         
/*  361 */         GL11.glTranslatef(0.0F, (float)Math.sin(Math.PI * -RenderParameters.GUN_CHANGE_Y) * 1.5F, 0.0F);
/*  362 */         GL11.glRotatef(80.0F * (float)Math.sin(Math.PI * -RenderParameters.GUN_CHANGE_Y), 0.0F, 0.0F, -1.0F);
/*  363 */         GL11.glRotatef(-120.0F * (float)Math.sin(Math.PI * -RenderParameters.GUN_CHANGE_Y), -1.0F, 0.0F, 0.0F);
/*      */ 
/*      */         
/*  366 */         modeType = gunType.scopeModeType;
/*      */         
/*  368 */         if (GunType.getAttachment(item, AttachmentPresetEnum.Sight) != null && 
/*  369 */           GunType.getAttachment(item, AttachmentPresetEnum.Sight).func_77973_b() != null) {
/*  370 */           ItemAttachment attachmentSight = (ItemAttachment)GunType.getAttachment(item, AttachmentPresetEnum.Sight).func_77973_b();
/*  371 */           if (attachmentSight != null) {
/*  372 */             modeType = attachmentSight.type.sight.modeType;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  377 */         ClientRenderHooks.isAimingScope = false;
/*  378 */         ClientRenderHooks.isAiming = false;
/*  379 */         if (modeType.isMirror) {
/*  380 */           if (RenderParameters.adsSwitch == 1.0F) {
/*  381 */             GL11.glTranslatef(model.config.extra.gunOffsetScoping, 0.0F, 0.0F);
/*  382 */             if (!ClientRenderHooks.isAimingScope) {
/*  383 */               ClientRenderHooks.isAimingScope = true;
/*      */             }
/*      */           }
/*  386 */           else if (ClientRenderHooks.isAimingScope) {
/*  387 */             ClientRenderHooks.isAimingScope = false;
/*      */           }
/*      */         
/*      */         }
/*  391 */         else if (RenderParameters.adsSwitch == 1.0F) {
/*  392 */           if (!ClientRenderHooks.isAiming) {
/*  393 */             ClientRenderHooks.isAiming = true;
/*      */           }
/*      */         }
/*  396 */         else if (ClientRenderHooks.isAiming) {
/*  397 */           ClientRenderHooks.isAiming = false;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  406 */         if (anim.reloading && WeaponAnimations.getAnimation(model.config.extra.reloadAnimation) != null) {
/*  407 */           WeaponAnimations.getAnimation(model.config.extra.reloadAnimation).onGunAnimation(tiltProgress, anim);
/*      */         }
/*      */ 
/*      */         
/*  411 */         GL11.glTranslatef(-(anim.lastGunRecoil + (anim.gunRecoil - anim.lastGunRecoil) * RenderParameters.smoothing) * model.config.extra.modelRecoilBackwards, 0.0F, 0.0F);
/*  412 */         GL11.glRotatef((anim.lastGunRecoil + (anim.gunRecoil - anim.lastGunRecoil) * RenderParameters.smoothing) * model.config.extra.modelRecoilUpwards, 0.0F, 0.0F, 1.0F);
/*  413 */         GL11.glRotatef((-anim.lastGunRecoil + (anim.gunRecoil - anim.lastGunRecoil) * RenderParameters.smoothing) * randomShake * model.config.extra.modelRecoilShake, 0.0F, 1.0F, 0.0F);
/*  414 */         GL11.glRotatef((-anim.lastGunRecoil + (anim.gunRecoil - anim.lastGunRecoil) * RenderParameters.smoothing) * randomShake * model.config.extra.modelRecoilShake, 1.0F, 0.0F, 0.0F);
/*      */         
/*  416 */         GL11.glPushMatrix();
/*      */ 
/*      */         
/*  419 */         if (this.light == null) {
/*  420 */           this.light = new ItemStack((Item)ClientProxy.itemLight, 1);
/*      */         }
/*  422 */         lightmodel = Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178089_a(this.light);
/*      */         
/*  424 */         lightVar = ModUtil.getBrightness((Entity)(Minecraft.func_71410_x()).field_71439_g);
/*  425 */         wantedDiff = (15 - lightVar);
/*  426 */         if (wantedDiff > this.slowDiff) {
/*  427 */           this.slowDiff = Math.min(this.slowDiff + 0.1F, wantedDiff);
/*      */         }
/*  429 */         if (wantedDiff < this.slowDiff) {
/*  430 */           this.slowDiff = Math.max(this.slowDiff - 0.1F, wantedDiff);
/*      */         }
/*      */         
/*  433 */         if (isLightOn && GunType.getAttachment(item, AttachmentPresetEnum.Flashlight) != null) {
/*  434 */           float alpha = 0.25F + this.slowDiff * 0.05F;
/*  435 */           GlStateManager.func_179114_b(-90.0F, 0.0F, 1.0F, 0.0F);
/*      */           
/*  437 */           GL11.glDisable(2896);
/*  438 */           (Minecraft.func_71410_x()).field_71460_t.func_175072_h();
/*  439 */           GL11.glDisable(3042);
/*  440 */           GL11.glPushMatrix();
/*  441 */           GL11.glPushAttrib(16384);
/*  442 */           GL11.glEnable(3042);
/*  443 */           GL11.glDepthMask(false);
/*  444 */           GL11.glBlendFunc(774, 770);
/*  445 */           GlStateManager.func_179137_b(-0.33D, -0.33D, -3.0D);
/*  446 */           GlStateManager.func_179139_a(3.5D, 3.5D, 1.0D);
/*      */           
/*  448 */           ModUtil.renderLightModel(lightmodel, (int)(alpha * this.slowDiff * 10.0F));
/*  449 */           ModUtil.renderLightModel(lightmodel, (int)(alpha * 255.0F));
/*  450 */           if (alpha > 0.9D) {
/*  451 */             ModUtil.renderLightModel(lightmodel, 255);
/*      */           }
/*  453 */           GL11.glBlendFunc(770, 771);
/*  454 */           GL11.glDepthMask(true);
/*  455 */           GL11.glDisable(3042);
/*  456 */           GL11.glPopAttrib();
/*  457 */           GL11.glPopMatrix();
/*  458 */           GL11.glEnable(2896);
/*  459 */           (Minecraft.func_71410_x()).field_71460_t.func_180436_i();
/*      */         } 
/*  461 */         GL11.glPopMatrix();
/*      */         
/*  463 */         if (anim.gunRecoil > 0.1F && entityplayer.func_70051_ag()) {
/*  464 */           RenderParameters.reloadSwitch = 0.0F;
/*  465 */           RenderParameters.sprintSwitch = 0.0F;
/*      */         } 
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  474 */     if (renderType == CustomItemRenderType.EQUIPPED_FIRST_PERSON && model.hasArms()) {
/*  475 */       renderStaticArm((EntityPlayer)mc.field_71439_g, model, anim, currentReloadState);
/*      */     }
/*      */ 
/*      */     
/*  479 */     GL11.glPushMatrix();
/*      */ 
/*      */ 
/*      */     
/*  483 */     float modelScale = model.config.extra.modelScale;
/*      */ 
/*      */     
/*  486 */     int skinId = 0;
/*  487 */     if (item.func_77942_o() && 
/*  488 */       item.func_77978_p().func_74764_b("skinId")) {
/*  489 */       skinId = item.func_77978_p().func_74762_e("skinId");
/*      */     }
/*      */ 
/*      */     
/*  493 */     String path = (skinId > 0) ? gunType.modelSkins[skinId].getSkin() : gunType.modelSkins[0].getSkin();
/*  494 */     String gunPath = path;
/*  495 */     bindTexture("guns", path);
/*      */     
/*  497 */     GL11.glEnable(3553);
/*      */     
/*  499 */     GL11.glScalef(modelScale, modelScale, modelScale);
/*      */ 
/*      */     
/*  502 */     GL11.glTranslatef(3.0F * worldScale, 5.37F * worldScale, 0.01F * worldScale);
/*      */     
/*  504 */     GL11.glTranslatef(model.config.extra.translateAll.x * worldScale, -model.config.extra.translateAll.y * worldScale, -model.config.extra.translateAll.z * worldScale);
/*      */ 
/*      */     
/*  507 */     if (renderType == CustomItemRenderType.ENTITY && 
/*  508 */       !((Minecraft.func_71410_x()).field_71462_r instanceof net.minecraft.client.gui.inventory.GuiInventory)) {
/*  509 */       GlStateManager.func_179091_B();
/*  510 */       RenderHelper.func_74519_b();
/*  511 */       GlStateManager.func_179103_j(7425);
/*  512 */       GlStateManager.func_179145_e();
/*  513 */       GlStateManager.func_179126_j();
/*      */     } 
/*      */ 
/*      */     
/*  517 */     model.renderPart("gunModel", worldScale);
/*      */ 
/*      */     
/*  520 */     if (GunType.getAttachment(item, AttachmentPresetEnum.Sight) == null && !model.config.attachments.scopeIsOnSlide) {
/*  521 */       model.renderPart("defaultScopeModel", worldScale);
/*      */     }
/*      */     
/*  524 */     if (GunType.getAttachment(item, AttachmentPresetEnum.Barrel) == null) {
/*  525 */       model.renderPart("defaultBarrelModel", worldScale);
/*      */     }
/*  527 */     model.renderPart("defaultStockModel", worldScale);
/*  528 */     model.renderPart("defaultGripModel", worldScale);
/*  529 */     model.renderPart("defaultGadgetModel", worldScale);
/*      */ 
/*      */     
/*  532 */     ItemStack pumpAttachment = null;
/*  533 */     if (pumpAttachment == null) {
/*  534 */       GL11.glPushMatrix();
/*      */       
/*  536 */       float f6 = currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.PumpOut || ((StateEntry)currentShootState.get()).stateType == StateType.PumpIn) ? ((StateEntry)currentShootState.get()).currentValue : 1.0F) : 1.0F;
/*  537 */       float f7 = currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.PumpOut || ((StateEntry)currentShootState.get()).stateType == StateType.PumpIn) ? ((StateEntry)currentShootState.get()).lastValue : 1.0F) : 1.0F;
/*      */       
/*  539 */       boolean bool = !ItemGun.hasNextShot(item);
/*      */ 
/*      */       
/*  542 */       if (model.config.arms.actionType == GunRenderConfig.Arms.EnumAction.Bolt) {
/*  543 */         if (anim.isReloadState(StateType.Uncharge) || anim.isReloadState(StateType.Charge)) {
/*  544 */           StateEntry boltState = anim.getReloadState().get();
/*  545 */           f6 = boltState.currentValue;
/*  546 */           f7 = boltState.lastValue;
/*      */         } 
/*      */         
/*  549 */         if ((anim.isShootState(StateType.Charge) && !bool) || anim.isShootState(StateType.Uncharge)) {
/*  550 */           StateEntry boltState = anim.getShootState().get();
/*  551 */           f6 = boltState.currentValue;
/*  552 */           f7 = boltState.lastValue;
/*      */         } 
/*      */ 
/*      */         
/*  556 */         if ((bool || anim.reloading) && !anim.isReloadState(StateType.Uncharge)) {
/*  557 */           GL11.glTranslatef(-model.config.extra.gunSlideDistance, 0.0F, 0.0F);
/*      */         }
/*      */         
/*  560 */         GL11.glTranslatef(model.config.bolt.boltRotationPoint.x, model.config.bolt.boltRotationPoint.y, model.config.bolt.boltRotationPoint.z);
/*  561 */         GL11.glRotatef(model.config.bolt.boltRotation * (1.0F - Math.abs(f7 + (f6 - f7) * RenderParameters.smoothing)), 1.0F, 0.0F, 0.0F);
/*  562 */         GL11.glTranslatef(-model.config.bolt.boltRotationPoint.x, -model.config.bolt.boltRotationPoint.y, -model.config.bolt.boltRotationPoint.z);
/*      */       } 
/*      */       
/*  565 */       GL11.glTranslatef(-(1.0F - Math.abs(f7 + (f6 - f7) * RenderParameters.smoothing)) * model.config.bolt.pumpHandleDistance, 0.0F, 0.0F);
/*      */       
/*  567 */       if (gunType.weaponType == WeaponType.DMR && 
/*  568 */         !anim.isGunEmpty) {
/*  569 */         GL11.glTranslatef(-(anim.lastGunSlide + (anim.gunSlide - anim.lastGunSlide) * RenderParameters.smoothing) * model.config.extra.gunSlideDistance, 0.0F, 0.0F);
/*      */       }
/*      */ 
/*      */       
/*  573 */       if (model.config.arms.actionType == GunRenderConfig.Arms.EnumAction.Bolt) {
/*  574 */         model.renderPart("boltModel", worldScale);
/*      */       }
/*      */       
/*  577 */       model.renderPart("pumpModel", worldScale);
/*      */       
/*  579 */       GL11.glPopMatrix();
/*      */     } 
/*      */ 
/*      */     
/*  583 */     if (model.config.extra.chargeHandleDistance != 0.0F && gunType.weaponType == WeaponType.Shotgun) {
/*  584 */       GL11.glPushMatrix();
/*      */       
/*  586 */       float f6 = currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.PumpOut || ((StateEntry)currentShootState.get()).stateType == StateType.PumpIn) ? ((StateEntry)currentShootState.get()).currentValue : 1.0F) : 1.0F;
/*  587 */       float f7 = currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.PumpOut || ((StateEntry)currentShootState.get()).stateType == StateType.PumpIn) ? ((StateEntry)currentShootState.get()).lastValue : 1.0F) : 1.0F;
/*  588 */       GL11.glTranslatef(-(1.0F - Math.abs(f7 + (f6 - f7) * RenderParameters.smoothing)) * model.config.extra.chargeHandleDistance, 0.0F, 0.0F);
/*      */       
/*  590 */       model.renderPart("chargeModel", worldScale);
/*      */       
/*  592 */       GL11.glPopMatrix();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  597 */     if (GunType.getAttachment(item, AttachmentPresetEnum.Slide) == null) {
/*  598 */       float currentCharge = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Charge || ((StateEntry)currentReloadState.get()).stateType == StateType.Uncharge) ? ((StateEntry)currentReloadState.get()).currentValue : 1.0F) : 1.0F;
/*  599 */       float lastCharge = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Charge || ((StateEntry)currentReloadState.get()).stateType == StateType.Uncharge) ? ((StateEntry)currentReloadState.get()).lastValue : 1.0F) : 1.0F;
/*      */       
/*  601 */       if (model.config.extra.needExtraChargeModel) {
/*  602 */         GL11.glPushMatrix();
/*  603 */         GL11.glTranslatef(-(1.0F - Math.abs(lastCharge + (currentCharge - lastCharge) * RenderParameters.smoothing)) * model.config.extra.chargeHandleDistance, 0.0F, 0.0F);
/*  604 */         model.renderPart("chargeModel", worldScale);
/*  605 */         GL11.glPopMatrix();
/*      */       } 
/*      */       
/*  608 */       GL11.glPushMatrix();
/*      */ 
/*      */ 
/*      */       
/*  612 */       GL11.glPushMatrix();
/*      */       
/*  614 */       if (!anim.isGunEmpty) {
/*  615 */         GL11.glTranslatef(-(anim.lastGunSlide + (anim.gunSlide - anim.lastGunSlide) * RenderParameters.smoothing) * model.config.extra.gunSlideDistance, 0.0F, 0.0F);
/*      */       } else {
/*  617 */         GL11.glTranslatef(-model.config.extra.gunSlideDistance, 0.0F, 0.0F);
/*      */       } 
/*  619 */       GL11.glTranslatef(-(1.0F - Math.abs(lastCharge + (currentCharge - lastCharge) * RenderParameters.smoothing)) * model.config.extra.chargeHandleDistance, 0.0F, 0.0F);
/*      */       
/*  621 */       model.renderPart("slideModel", worldScale);
/*  622 */       GL11.glPopMatrix();
/*      */       
/*  624 */       if (GunType.getAttachment(item, AttachmentPresetEnum.Sight) == null && model.config.attachments.scopeIsOnSlide) {
/*  625 */         model.renderPart("defaultScopeModel", worldScale);
/*      */       }
/*      */       
/*  628 */       if (model.switchIsOnSlide) {
/*  629 */         GL11.glPushMatrix();
/*      */         
/*  631 */         WeaponFireMode fireMode = GunType.getFireMode(item);
/*  632 */         float switchAngle = (fireMode == WeaponFireMode.SEMI) ? model.switchSemiRot : ((fireMode == WeaponFireMode.FULL) ? model.switchAutoRot : ((fireMode == WeaponFireMode.BURST) ? model.switchBurstRot : 0.0F));
/*  633 */         GL11.glTranslatef(model.switchRotationPoint.x, model.switchRotationPoint.y, model.switchRotationPoint.z);
/*  634 */         GL11.glRotatef(switchAngle, 0.0F, 0.0F, 1.0F);
/*  635 */         GL11.glTranslatef(-model.switchRotationPoint.x, -model.switchRotationPoint.y, -model.switchRotationPoint.z);
/*  636 */         model.renderPart("switchModel", worldScale);
/*      */         
/*  638 */         GL11.glPopMatrix();
/*      */       } 
/*      */       
/*  641 */       GL11.glPopMatrix();
/*      */     } 
/*      */ 
/*      */     
/*  645 */     for (BreakActionData breakAction : model.config.breakAction.breakActions) {
/*  646 */       float breakProgress = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Tilt || ((StateEntry)currentReloadState.get()).stateType == StateType.Untilt) ? ((StateEntry)currentReloadState.get()).currentValue : (anim.tiltHold ? 1.0F : 0.0F)) : 0.0F;
/*  647 */       GL11.glPushMatrix();
/*      */       
/*  649 */       GL11.glTranslatef(breakAction.breakPoint.x, breakAction.breakPoint.y, breakAction.breakPoint.z);
/*  650 */       GL11.glRotatef(breakProgress * -breakAction.angle, 0.0F, 0.0F, 1.0F);
/*  651 */       GL11.glTranslatef(-breakAction.breakPoint.x, -breakAction.breakPoint.y, -breakAction.breakPoint.z);
/*  652 */       model.renderPart(breakAction.modelName, worldScale);
/*  653 */       if (GunType.getAttachment(item, AttachmentPresetEnum.Sight) == null && model.config.breakAction.scopeIsOnBreakAction && breakAction.scopePart) {
/*  654 */         model.renderPart("defaultScopeModel", worldScale);
/*      */       }
/*  656 */       GL11.glPopMatrix();
/*      */     } 
/*      */ 
/*      */     
/*  660 */     boolean isAmmoEmpty = !ItemGun.hasNextShot(item);
/*  661 */     if (model.slideLockOnEmpty) {
/*  662 */       if (isAmmoEmpty) {
/*  663 */         anim.isGunEmpty = true;
/*      */       }
/*  665 */       else if (!isAmmoEmpty && !anim.reloading) {
/*  666 */         anim.isGunEmpty = false;
/*      */       } 
/*      */     }
/*      */     
/*  670 */     GL11.glPushMatrix();
/*      */     
/*  672 */     GL11.glTranslatef(model.config.hammerAction.hammerRotationPoint.x * worldScale, model.config.hammerAction.hammerRotationPoint.y * worldScale, model.config.hammerAction.hammerRotationPoint.z * worldScale);
/*  673 */     if (!anim.isGunEmpty) {
/*  674 */       GL11.glRotatef(50.0F, 0.0F, 0.0F, 1.0F);
/*  675 */       GL11.glRotatef(-anim.hammerRotation * 2.0F, 0.0F, 0.0F, 1.0F);
/*      */     } 
/*  677 */     GL11.glTranslatef(-model.config.hammerAction.hammerRotationPoint.x * worldScale, -model.config.hammerAction.hammerRotationPoint.y * worldScale, -model.config.hammerAction.hammerRotationPoint.z * worldScale);
/*  678 */     model.renderPart("hammerModel", worldScale);
/*      */     
/*  680 */     GL11.glPopMatrix();
/*      */ 
/*      */     
/*  683 */     GL11.glPushMatrix();
/*      */     
/*  685 */     float pumpCurrent = currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.PumpOut || ((StateEntry)currentShootState.get()).stateType == StateType.PumpIn) ? ((StateEntry)currentShootState.get()).currentValue : 1.0F) : 1.0F;
/*  686 */     float pumpLast = currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.PumpOut || ((StateEntry)currentShootState.get()).stateType == StateType.PumpIn) ? ((StateEntry)currentShootState.get()).lastValue : 1.0F) : 1.0F;
/*  687 */     GL11.glTranslatef(model.leverRotationPoint.x, model.leverRotationPoint.y, model.leverRotationPoint.z);
/*  688 */     GL11.glRotatef(model.leverRotation * (1.0F - Math.abs(pumpLast + (pumpCurrent - pumpLast) * RenderParameters.smoothing)), 0.0F, 0.0F, 1.0F);
/*  689 */     GL11.glTranslatef(-model.leverRotationPoint.x, -model.leverRotationPoint.y, -model.leverRotationPoint.z);
/*  690 */     model.renderPart("leverActionModel", worldScale);
/*      */     
/*  692 */     GL11.glPopMatrix();
/*      */ 
/*      */     
/*  695 */     GL11.glPushMatrix();
/*      */     
/*  697 */     GL11.glTranslatef(model.triggerRotationPoint.x, model.triggerRotationPoint.y, model.triggerRotationPoint.z);
/*  698 */     GL11.glRotatef(model.triggerRotation * RenderParameters.triggerPullSwitch * 50.0F, 0.0F, 0.0F, 1.0F);
/*  699 */     GL11.glTranslatef(-model.triggerRotationPoint.x, -model.triggerRotationPoint.y, -model.triggerRotationPoint.z);
/*  700 */     model.renderPart("triggerModel", worldScale);
/*      */     
/*  702 */     GL11.glPopMatrix();
/*      */ 
/*      */     
/*  705 */     if (!model.switchIsOnSlide) {
/*  706 */       GL11.glPushMatrix();
/*      */       
/*  708 */       WeaponFireMode fireMode = GunType.getFireMode(item);
/*  709 */       float switchAngle = (fireMode == WeaponFireMode.SEMI) ? model.switchSemiRot : ((fireMode == WeaponFireMode.FULL) ? model.switchAutoRot : ((fireMode == WeaponFireMode.BURST) ? model.switchBurstRot : 0.0F));
/*  710 */       GL11.glTranslatef(model.switchRotationPoint.x, model.switchRotationPoint.y, model.switchRotationPoint.z);
/*  711 */       GL11.glRotatef(switchAngle, 0.0F, 0.0F, 1.0F);
/*  712 */       GL11.glTranslatef(-model.switchRotationPoint.x, -model.switchRotationPoint.y, -model.switchRotationPoint.z);
/*  713 */       model.renderPart("switchModel", worldScale);
/*      */       
/*  715 */       GL11.glPopMatrix();
/*      */     } 
/*      */     
/*  718 */     if (gunType.weaponType == WeaponType.Revolver) {
/*      */       
/*  720 */       GL11.glPushMatrix();
/*      */ 
/*      */       
/*  723 */       GL11.glTranslatef(model.config.revolverBarrel.cylinderOriginPoint.x * worldScale, model.config.revolverBarrel.cylinderOriginPoint.y * worldScale, model.config.revolverBarrel.cylinderOriginPoint.z * worldScale);
/*  724 */       float updatedTiltProgress = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Tilt || ((StateEntry)currentReloadState.get()).stateType == StateType.Untilt) ? ((StateEntry)currentReloadState.get()).currentValue : (anim.tiltHold ? 1.0F : 0.0F)) : 0.0F;
/*  725 */       GL11.glTranslatef(updatedTiltProgress * model.config.revolverBarrel.cylinderReloadTranslation.x * worldScale, updatedTiltProgress * model.config.revolverBarrel.cylinderReloadTranslation.y * worldScale, updatedTiltProgress * model.config.revolverBarrel.cylinderReloadTranslation.z * worldScale);
/*  726 */       GL11.glRotatef(anim.revolverBarrelRotation, 1.0F, 0.0F, 0.0F);
/*  727 */       GL11.glTranslatef(-model.config.revolverBarrel.cylinderOriginPoint.x * worldScale, -model.config.revolverBarrel.cylinderOriginPoint.y * worldScale, -model.config.revolverBarrel.cylinderOriginPoint.z * worldScale);
/*  728 */       model.renderPart("revolverBarrelModel", worldScale);
/*      */       
/*  730 */       GL11.glPopMatrix();
/*      */     } 
/*      */ 
/*      */     
/*  734 */     GL11.glPushMatrix();
/*      */     
/*  736 */     boolean cachedUnload = (anim.isReloadType(ReloadType.Unload) && anim.cachedAmmoStack != null);
/*  737 */     if (ItemGun.hasAmmoLoaded(item) || cachedUnload) {
/*  738 */       ItemStack stackAmmo = cachedUnload ? anim.cachedAmmoStack : new ItemStack(item.func_77978_p().func_74775_l("ammo"));
/*  739 */       if (stackAmmo.func_77973_b() instanceof ItemAmmo) {
/*  740 */         ItemAmmo itemAmmo = (ItemAmmo)stackAmmo.func_77973_b();
/*  741 */         AmmoType ammoType = itemAmmo.type;
/*  742 */         boolean shouldNormalRender = true;
/*      */         
/*  744 */         if (anim.reloading && model.config.extra.reloadAnimation != null && WeaponAnimations.getAnimation(model.config.extra.reloadAnimation) != null) {
/*      */           
/*  746 */           float ammoProgress = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Unload || ((StateEntry)currentReloadState.get()).stateType == StateType.Load) ? ((StateEntry)currentReloadState.get()).currentValue : 0.0F) : 1.0F;
/*  747 */           WeaponAnimations.getAnimation(model.config.extra.reloadAnimation).onAmmoAnimation(model, ammoProgress, anim.reloadAmmoCount, anim);
/*      */         } 
/*      */         
/*  750 */         if (ammoType.isDynamicAmmo && ammoType.model != null) {
/*  751 */           ModelAmmo modelAmmo = (ModelAmmo)ammoType.model;
/*  752 */           if (model.config.maps.ammoMap.containsKey(ammoType.internalName)) {
/*  753 */             Vector3f ammoOffset = ((RenderVariables)model.config.maps.ammoMap.get(ammoType.internalName)).offset;
/*  754 */             Vector3f ammoScale = ((RenderVariables)model.config.maps.ammoMap.get(ammoType.internalName)).scale;
/*      */             
/*  756 */             GL11.glTranslatef(ammoOffset.x, ammoOffset.y, ammoOffset.z);
/*  757 */             if (ammoType.magazineCount > 1) {
/*  758 */               int magCount = stackAmmo.func_77978_p().func_74762_e("magcount");
/*  759 */               if (!anim.reloading) {
/*  760 */                 this.oldMagCount = magCount;
/*  761 */               } else if (anim.reloading) {
/*  762 */                 magCount = this.oldMagCount;
/*      */               } 
/*  764 */               if (modelAmmo.magCountOffset.containsKey(Integer.valueOf(magCount))) {
/*  765 */                 shouldNormalRender = false;
/*  766 */                 GL11.glPushMatrix();
/*      */                 
/*  768 */                 RenderVariables magRenderVar = (RenderVariables)modelAmmo.magCountOffset.get(Integer.valueOf(magCount));
/*  769 */                 Vector3f magOffset = magRenderVar.offset;
/*  770 */                 Vector3f magRotate = magRenderVar.rotation;
/*  771 */                 GL11.glTranslatef(magOffset.x, magOffset.y, magOffset.z);
/*  772 */                 if (magRotate != null && magRenderVar.angle != null) {
/*  773 */                   GL11.glRotatef(magRenderVar.angle.floatValue(), magRotate.x, magRotate.y, magRotate.z);
/*      */                 }
/*      */                 
/*  776 */                 Vector3f adjustedScale = new Vector3f(ammoScale.x / modelScale, ammoScale.y / modelScale, ammoScale.z / modelScale);
/*  777 */                 GL11.glScalef(adjustedScale.x, adjustedScale.y, adjustedScale.z);
/*      */                 
/*  779 */                 int skinIdAmmo = 0;
/*      */                 
/*  781 */                 if (stackAmmo.func_77942_o() && 
/*  782 */                   stackAmmo.func_77978_p().func_74764_b("skinId")) {
/*  783 */                   skinIdAmmo = stackAmmo.func_77978_p().func_74762_e("skinId");
/*      */                 }
/*      */                 
/*  786 */                 if (ammoType.sameTextureAsGun) {
/*  787 */                   bindTexture("guns", path);
/*      */                 } else {
/*  789 */                   String pathAmmo = (skinIdAmmo > 0) ? ammoType.modelSkins[skinIdAmmo].getSkin() : ammoType.modelSkins[0].getSkin();
/*  790 */                   bindTexture("ammo", pathAmmo);
/*      */                 } 
/*      */                 
/*  793 */                 if (anim.shouldRenderAmmo()) {
/*  794 */                   if (!cachedUnload) {
/*  795 */                     anim.cachedAmmoStack = stackAmmo;
/*      */                   }
/*  797 */                   modelAmmo.renderAmmo(worldScale);
/*      */                 } 
/*      */                 
/*  800 */                 GL11.glPopMatrix();
/*      */               } 
/*      */             } 
/*      */             
/*  804 */             if (shouldNormalRender) {
/*  805 */               Vector3f adjustedScale = new Vector3f(ammoScale.x / modelScale, ammoScale.y / modelScale, ammoScale.z / modelScale);
/*  806 */               GL11.glScalef(adjustedScale.x, adjustedScale.y, adjustedScale.z);
/*      */             } 
/*      */           } 
/*  809 */           if (shouldNormalRender && anim.shouldRenderAmmo()) {
/*  810 */             if (!cachedUnload) {
/*  811 */               anim.cachedAmmoStack = stackAmmo;
/*      */             }
/*  813 */             int skinIdAmmo = 0;
/*      */             
/*  815 */             if (stackAmmo.func_77942_o() && 
/*  816 */               stackAmmo.func_77978_p().func_74764_b("skinId")) {
/*  817 */               skinIdAmmo = stackAmmo.func_77978_p().func_74762_e("skinId");
/*      */             }
/*      */             
/*  820 */             if (ammoType.sameTextureAsGun) {
/*  821 */               bindTexture("guns", path);
/*      */             } else {
/*  823 */               String pathAmmo = (skinIdAmmo > 0) ? ammoType.modelSkins[skinIdAmmo].getSkin() : ammoType.modelSkins[0].getSkin();
/*  824 */               bindTexture("ammo", pathAmmo);
/*      */             } 
/*  826 */             modelAmmo.renderAmmo(worldScale);
/*      */           }
/*      */         
/*  829 */         } else if (anim.shouldRenderAmmo()) {
/*  830 */           if (!cachedUnload) {
/*  831 */             anim.cachedAmmoStack = stackAmmo;
/*      */           }
/*  833 */           float updatedTiltProgress = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Tilt || ((StateEntry)currentReloadState.get()).stateType == StateType.Untilt) ? ((StateEntry)currentReloadState.get()).currentValue : (anim.tiltHold ? 1.0F : 0.0F)) : 0.0F;
/*      */           
/*  835 */           GL11.glPushMatrix();
/*      */           
/*  837 */           GL11.glTranslatef(model.cylinderRotationPoint.x, model.cylinderRotationPoint.y, model.cylinderRotationPoint.z);
/*  838 */           GL11.glRotatef(updatedTiltProgress * model.cylinderRotation, 1.0F, 0.0F, 0.0F);
/*  839 */           GL11.glTranslatef(-model.cylinderRotationPoint.x, -model.cylinderRotationPoint.y, -model.cylinderRotationPoint.z);
/*  840 */           model.renderPart("ammoModel", worldScale);
/*      */           
/*  842 */           GL11.glPopMatrix();
/*      */         }
/*      */       
/*      */       } 
/*  846 */     } else if (ItemGun.getUsedBullet(item, gunType) != null) {
/*  847 */       ItemBullet itemBullet = ItemGun.getUsedBullet(item, gunType);
/*  848 */       ModelBullet bulletModel = (ModelBullet)itemBullet.type.model;
/*      */ 
/*      */       
/*  851 */       if (gunType.weaponType == WeaponType.Revolver) {
/*  852 */         GlStateManager.func_179094_E();
/*  853 */         if (itemBullet.type.model != null && anim.reloading) {
/*  854 */           GL11.glTranslatef(model.config.revolverBarrel.cylinderOriginPoint.x * worldScale, model.config.revolverBarrel.cylinderOriginPoint.y * worldScale, model.config.revolverBarrel.cylinderOriginPoint.z * worldScale);
/*  855 */           float updatedTiltProgress = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Tilt || ((StateEntry)currentReloadState.get()).stateType == StateType.Untilt) ? ((StateEntry)currentReloadState.get()).currentValue : (anim.tiltHold ? 1.0F : 0.0F)) : 0.0F;
/*  856 */           GL11.glTranslatef(updatedTiltProgress * model.config.revolverBarrel.cylinderReloadTranslation.x * worldScale, updatedTiltProgress * model.config.revolverBarrel.cylinderReloadTranslation.y * worldScale, updatedTiltProgress * model.config.revolverBarrel.cylinderReloadTranslation.z * worldScale);
/*  857 */           GL11.glRotatef(anim.revolverBarrelRotation, 1.0F, 0.0F, 0.0F);
/*  858 */           GL11.glTranslatef(-model.config.revolverBarrel.cylinderOriginPoint.x * worldScale, -model.config.revolverBarrel.cylinderOriginPoint.y * worldScale, -model.config.revolverBarrel.cylinderOriginPoint.z * worldScale);
/*      */         } 
/*  860 */         bindTexture("bullets", itemBullet.type.modelSkins[0].getSkin());
/*      */         
/*  862 */         if (currentReloadState.isPresent() && ((StateEntry)currentReloadState.get()).stateType == StateType.Tilt) {
/*  863 */           GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/*  864 */           bulletModel.renderAll(worldScale);
/*      */         }
/*  866 */         else if (model.config.revolverBarrel.numberBullets != null) {
/*  867 */           bulletModel.renderBullet(anim.bulletsToRender, worldScale);
/*      */         } 
/*      */         
/*  870 */         GlStateManager.func_179121_F();
/*      */       } 
/*      */       
/*  873 */       if (anim.reloading && model.config.extra.reloadAnimation != null && WeaponAnimations.getAnimation(model.config.extra.reloadAnimation) != null && 
/*  874 */         anim.reloading && model.config.extra.reloadAnimation != null && WeaponAnimations.getAnimation(model.config.extra.reloadAnimation) != null) {
/*      */         
/*  876 */         float ammoProgress = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Unload || ((StateEntry)currentReloadState.get()).stateType == StateType.Load) ? ((StateEntry)currentReloadState.get()).currentValue : 0.0F) : 1.0F;
/*  877 */         WeaponAnimations.getAnimation(model.config.extra.reloadAnimation).onAmmoAnimation(model, ammoProgress, anim.reloadAmmoCount, anim);
/*      */       } 
/*      */ 
/*      */       
/*  881 */       if (itemBullet.type.model != null && anim.reloading && gunType.weaponType != WeaponType.Launcher) {
/*  882 */         GL11.glPushMatrix();
/*      */         
/*  884 */         if (model.config.maps.bulletMap.containsKey(itemBullet.baseType.internalName)) {
/*  885 */           RenderVariables renderVar = (RenderVariables)model.config.maps.bulletMap.get(itemBullet.type.internalName);
/*  886 */           Vector3f offset = renderVar.offset;
/*  887 */           GL11.glTranslatef(offset.x, offset.y, offset.z);
/*  888 */           if (renderVar.scale != null) {
/*  889 */             Vector3f scale = renderVar.scale;
/*  890 */             GL11.glScalef(scale.x, scale.y, scale.z);
/*      */           } 
/*      */         } 
/*  893 */         bindTexture("bullets", itemBullet.type.modelSkins[0].getSkin());
/*  894 */         bulletModel.renderBullet(worldScale);
/*      */         
/*  896 */         GL11.glPopMatrix();
/*      */       } 
/*      */       
/*  899 */       if (itemBullet.type.model != null && gunType.weaponType == WeaponType.Launcher) {
/*  900 */         GL11.glPushMatrix();
/*      */         
/*  902 */         if (model.config.maps.bulletMap.containsKey(itemBullet.baseType.internalName)) {
/*  903 */           RenderVariables renderVar = (RenderVariables)model.config.maps.bulletMap.get(itemBullet.type.internalName);
/*  904 */           Vector3f offset = renderVar.offset;
/*  905 */           GL11.glTranslatef(offset.x, offset.y, offset.z);
/*  906 */           if (renderVar.scale != null) {
/*  907 */             Vector3f scale = renderVar.scale;
/*  908 */             GL11.glScalef(scale.x, scale.y, scale.z);
/*      */           } 
/*      */         } 
/*  911 */         int ammoCount = item.func_77978_p().func_74762_e("ammocount");
/*  912 */         boolean isLoading = (currentReloadState.isPresent() && ((StateEntry)currentReloadState.get()).stateType == StateType.Load);
/*  913 */         if (isLoading || (ammoCount > 0 && !currentReloadState.isPresent())) {
/*  914 */           bindTexture("bullets", itemBullet.type.modelSkins[0].getSkin());
/*  915 */           bulletModel.renderBullet(worldScale);
/*      */         } 
/*      */         
/*  918 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  925 */     boolean shouldRenderFlash = true;
/*      */     
/*  927 */     if (GunType.getAttachment(item, AttachmentPresetEnum.Barrel) != null) {
/*  928 */       AttachmentType attachmentType = ((ItemAttachment)GunType.getAttachment(item, AttachmentPresetEnum.Barrel).func_77973_b()).type;
/*  929 */       if (attachmentType.attachmentType == AttachmentPresetEnum.Barrel) {
/*  930 */         shouldRenderFlash = !attachmentType.barrel.hideFlash;
/*      */       }
/*      */     } 
/*      */     
/*  934 */     TextureType flashType = gunType.flashType;
/*      */     
/*  936 */     if (anim.muzzleFlashTime > 0 && model.staticModel.getPart("flashModel") != null && !mc.field_71439_g.func_70090_H() && renderType != CustomItemRenderType.BACK && shouldRenderFlash) {
/*  937 */       GlStateManager.func_179094_E();
/*      */ 
/*      */       
/*  940 */       GL11.glEnable(3042);
/*  941 */       GL11.glEnable(2832);
/*  942 */       GL11.glHint(3153, 4353);
/*      */       
/*  944 */       renderEngine.func_110577_a(flashType.resourceLocations.get(anim.flashInt));
/*      */       
/*  946 */       float lastBrightnessX = OpenGlHelper.lastBrightnessX;
/*  947 */       float lastBrightnessY = OpenGlHelper.lastBrightnessY;
/*  948 */       GlStateManager.func_179132_a(false);
/*  949 */       GlStateManager.func_179140_f();
/*  950 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/*      */       
/*  952 */       boolean glowMode = ObjModelRenderer.glowTxtureMode;
/*  953 */       ObjModelRenderer.glowTxtureMode = false;
/*  954 */       model.renderPart("flashModel", worldScale);
/*  955 */       ObjModelRenderer.glowTxtureMode = glowMode;
/*      */       
/*  957 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, lastBrightnessX, lastBrightnessY);
/*  958 */       GlStateManager.func_179145_e();
/*  959 */       GlStateManager.func_179132_a(true);
/*      */       
/*  961 */       GL11.glDisable(3042);
/*  962 */       GL11.glDisable(2832);
/*      */       
/*  964 */       GlStateManager.func_179121_F();
/*      */     } 
/*      */     
/*  967 */     if (renderType == CustomItemRenderType.EQUIPPED_FIRST_PERSON && model.hasArms()) {
/*  968 */       renderMovingArm((EntityPlayer)mc.field_71439_g, model, anim, currentReloadState);
/*      */     }
/*      */     
/*  971 */     GL11.glPopMatrix();
/*      */     
/*  973 */     GL11.glPushMatrix();
/*      */     
/*  975 */     for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/*  976 */       ItemStack itemStack = GunType.getAttachment(item, attachment);
/*  977 */       if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*  978 */         AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/*  979 */         ModelAttachment attachmentModel = (ModelAttachment)attachmentType.model;
/*  980 */         if (attachmentModel != null) {
/*  981 */           GL11.glPushMatrix();
/*      */           
/*  983 */           skinId = 0;
/*  984 */           if (itemStack.func_77942_o() && 
/*  985 */             itemStack.func_77978_p().func_74764_b("skinId")) {
/*  986 */             skinId = itemStack.func_77978_p().func_74762_e("skinId");
/*      */           }
/*      */ 
/*      */           
/*  990 */           Vector3f adjustedScale = new Vector3f(attachmentModel.config.extra.modelScale, attachmentModel.config.extra.modelScale, attachmentModel.config.extra.modelScale);
/*  991 */           GL11.glScalef(adjustedScale.x, adjustedScale.y, adjustedScale.z);
/*      */           
/*  993 */           if (model.config.attachments.attachmentPointMap != null && model.config.attachments.attachmentPointMap.size() >= 1 && 
/*  994 */             model.config.attachments.attachmentPointMap.containsKey(attachment)) {
/*  995 */             Vector3f attachmentVecTranslate = ((ArrayList<Vector3f>)model.config.attachments.attachmentPointMap.get(attachment)).get(0);
/*  996 */             Vector3f attachmentVecRotate = ((ArrayList<Vector3f>)model.config.attachments.attachmentPointMap.get(attachment)).get(1);
/*  997 */             GL11.glTranslatef(attachmentVecTranslate.x / attachmentModel.config.extra.modelScale, attachmentVecTranslate.y / attachmentModel.config.extra.modelScale, attachmentVecTranslate.z / attachmentModel.config.extra.modelScale);
/*      */             
/*  999 */             GL11.glRotatef(attachmentVecRotate.x, 1.0F, 0.0F, 0.0F);
/* 1000 */             GL11.glRotatef(attachmentVecRotate.y, 0.0F, 1.0F, 0.0F);
/* 1001 */             GL11.glRotatef(attachmentVecRotate.z, 0.0F, 0.0F, 1.0F);
/*      */           } 
/*      */ 
/*      */           
/* 1005 */           if (model.config.attachments.positionPointMap != null) {
/* 1006 */             for (String internalName : model.config.attachments.positionPointMap.keySet()) {
/* 1007 */               if (internalName.equals(attachmentType.internalName)) {
/* 1008 */                 Vector3f trans = ((ArrayList<Vector3f>)model.config.attachments.positionPointMap.get(internalName)).get(0);
/* 1009 */                 Vector3f rot = ((ArrayList<Vector3f>)model.config.attachments.positionPointMap.get(internalName)).get(1);
/* 1010 */                 GL11.glTranslatef(trans.x / attachmentModel.config.extra.modelScale * worldScale, trans.y / attachmentModel.config.extra.modelScale * worldScale, trans.z / attachmentModel.config.extra.modelScale * worldScale);
/*      */                 
/* 1012 */                 GL11.glRotatef(rot.x, 1.0F, 0.0F, 0.0F);
/* 1013 */                 GL11.glRotatef(rot.y, 0.0F, 1.0F, 0.0F);
/* 1014 */                 GL11.glRotatef(rot.z, 0.0F, 0.0F, 1.0F);
/*      */               } 
/*      */             } 
/*      */           }
/*      */           
/* 1019 */           if (attachmentType.sameTextureAsGun) {
/* 1020 */             bindTexture("guns", gunPath);
/*      */           } else {
/* 1022 */             path = (skinId > 0) ? attachmentType.modelSkins[skinId].getSkin() : attachmentType.modelSkins[0].getSkin();
/* 1023 */             bindTexture("attachments", path);
/*      */           } 
/*      */           
/* 1026 */           if (attachmentType.attachmentType == AttachmentPresetEnum.Sight && model.config.attachments.scopeIsOnSlide) {
/* 1027 */             float currentCharge = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Charge || ((StateEntry)currentReloadState.get()).stateType == StateType.Uncharge) ? ((StateEntry)currentReloadState.get()).currentValue : 1.0F) : 1.0F;
/* 1028 */             float lastCharge = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Charge || ((StateEntry)currentReloadState.get()).stateType == StateType.Uncharge) ? ((StateEntry)currentReloadState.get()).lastValue : 1.0F) : 1.0F;
/* 1029 */             if (!anim.isGunEmpty) {
/* 1030 */               GL11.glTranslatef(-(anim.lastGunSlide + (anim.gunSlide - anim.lastGunSlide) * RenderParameters.smoothing) * model.config.extra.gunSlideDistance, 0.0F, 0.0F);
/*      */             } else {
/* 1032 */               GL11.glTranslatef(-model.config.extra.gunSlideDistance, 0.0F, 0.0F);
/*      */             } 
/* 1034 */             GL11.glTranslatef(-(1.0F - Math.abs(lastCharge + (currentCharge - lastCharge) * RenderParameters.smoothing)) * model.config.extra.chargeHandleDistance, 0.0F, 0.0F);
/*      */           } 
/*      */           
/* 1037 */           attachmentModel.renderAttachment(worldScale);
/* 1038 */           if (attachmentType.attachmentType == AttachmentPresetEnum.Sight && mc.field_71474_y.field_74320_O == 0 && renderType == CustomItemRenderType.EQUIPPED_FIRST_PERSON) {
/* 1039 */             boolean glowTxtureMode = ObjModelRenderer.glowTxtureMode;
/* 1040 */             ObjModelRenderer.glowTxtureMode = false;
/* 1041 */             renderScopeGlass(attachmentType, attachmentModel, (RenderParameters.adsSwitch != 0.0F));
/* 1042 */             ObjModelRenderer.glowTxtureMode = glowTxtureMode;
/*      */           } 
/*      */           
/* 1045 */           GL11.glPopMatrix();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1050 */     GL11.glPopMatrix();
/*      */     
/* 1052 */     GL11.glPopMatrix();
/*      */ 
/*      */     
/* 1055 */     GL11.glPopMatrix(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderStaticArm(EntityPlayer player, ModelGun model, AnimStateMachine anim, Optional<StateEntry> currentState) {
/* 1061 */     Minecraft mc = Minecraft.func_71410_x();
/* 1062 */     mc.func_110434_K().func_110577_a((Minecraft.func_71410_x()).field_71439_g.func_110306_p());
/* 1063 */     Render<AbstractClientPlayer> render = Minecraft.func_71410_x().func_175598_ae().func_78713_a((Entity)(Minecraft.func_71410_x()).field_71439_g);
/* 1064 */     RenderPlayer renderplayer = (RenderPlayer)render;
/*      */     
/* 1066 */     float tiltProgress = currentState.isPresent() ? ((((StateEntry)currentState.get()).stateType == StateType.Tilt || ((StateEntry)currentState.get()).stateType == StateType.Untilt) ? ((StateEntry)currentState.get()).currentValue : (anim.tiltHold ? 1.0F : 0.0F)) : 0.0F;
/* 1067 */     String staticArmState = getStaticArmState(model, anim);
/*      */     
/* 1069 */     GL11.glPushMatrix();
/*      */ 
/*      */     
/* 1072 */     boolean rightArm = (model.config.arms.leftHandAmmo && model.config.arms.rightArm.armPos != null);
/* 1073 */     if (staticArmState == "ToFrom" && rightArm && model.config.arms.actionArm == GunRenderConfig.Arms.EnumArm.Left) {
/* 1074 */       rightArm = false;
/*      */     }
/* 1076 */     Vector3f armScale = rightArm ? model.config.arms.rightArm.armScale : model.config.arms.leftArm.armScale;
/* 1077 */     Vector3f armRot = rightArm ? model.config.arms.rightArm.armRot : model.config.arms.leftArm.armRot;
/* 1078 */     Vector3f armPos = rightArm ? model.config.arms.rightArm.armPos : model.config.arms.leftArm.armPos;
/*      */     
/* 1080 */     Vector3f chargeArmRot = (model.config.arms.actionArm == GunRenderConfig.Arms.EnumArm.Right) ? model.config.arms.rightArm.armChargeRot : model.config.arms.leftArm.armChargeRot;
/* 1081 */     Vector3f chargeArmPos = (model.config.arms.actionArm == GunRenderConfig.Arms.EnumArm.Right) ? model.config.arms.rightArm.armChargePos : model.config.arms.leftArm.armChargePos;
/* 1082 */     Vector3f reloadArmRot = rightArm ? model.config.arms.rightArm.armReloadRot : model.config.arms.leftArm.armReloadRot;
/* 1083 */     Vector3f reloadArmPos = rightArm ? model.config.arms.rightArm.armReloadPos : model.config.arms.leftArm.armReloadPos;
/*      */     
/* 1085 */     if (staticArmState == "Pump") {
/* 1086 */       RenderArms.renderArmPump(model, anim, RenderParameters.smoothing, armRot, armPos, !model.config.arms.leftHandAmmo);
/* 1087 */     } else if (staticArmState == "Charge") {
/* 1088 */       RenderArms.renderArmCharge(model, anim, RenderParameters.smoothing, chargeArmRot, chargeArmPos, armRot, armPos, !model.config.arms.leftHandAmmo);
/* 1089 */     } else if (staticArmState == "Bolt") {
/* 1090 */       RenderArms.renderArmBolt(model, anim, RenderParameters.smoothing, chargeArmRot, chargeArmPos, !model.config.arms.leftHandAmmo);
/* 1091 */     } else if (staticArmState == "Default") {
/* 1092 */       RenderArms.renderArmDefault(model, anim, RenderParameters.smoothing, armRot, armPos, rightArm, !model.config.arms.leftHandAmmo);
/* 1093 */     } else if (staticArmState == "Reload") {
/* 1094 */       RenderArms.renderStaticArmReload(model, anim, RenderParameters.smoothing, tiltProgress, reloadArmRot, reloadArmPos, armRot, armPos, !model.config.arms.leftHandAmmo);
/* 1095 */     } else if (staticArmState == "ToFrom") {
/* 1096 */       RenderArms.renderToFrom(model, anim, RenderParameters.smoothing, chargeArmRot, chargeArmPos, armRot, armPos, !model.config.arms.leftHandAmmo);
/*      */     } 
/*      */     
/* 1099 */     GL11.glScalef(armScale.x, armScale.y, armScale.z);
/* 1100 */     renderplayer.func_177087_b().func_78087_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)player);
/* 1101 */     (renderplayer.func_177087_b()).field_178723_h.field_82906_o = 0.0F;
/* 1102 */     if (rightArm) {
/* 1103 */       if (!MinecraftForge.EVENT_BUS.post((Event)new RenderHandFisrtPersonEvent.Pre(this, EnumHandSide.RIGHT))) {
/* 1104 */         renderplayer.func_177138_b((AbstractClientPlayer)(Minecraft.func_71410_x()).field_71439_g);
/* 1105 */         MinecraftForge.EVENT_BUS.post((Event)new RenderHandFisrtPersonEvent.Post(this, EnumHandSide.RIGHT));
/*      */       } 
/* 1107 */       renderRightSleeve(player, (ModelBiped)renderplayer.func_177087_b());
/*      */     } else {
/* 1109 */       if (!MinecraftForge.EVENT_BUS.post((Event)new RenderHandFisrtPersonEvent.Pre(this, EnumHandSide.LEFT))) {
/* 1110 */         renderplayer.func_177139_c((AbstractClientPlayer)(Minecraft.func_71410_x()).field_71439_g);
/* 1111 */         MinecraftForge.EVENT_BUS.post((Event)new RenderHandFisrtPersonEvent.Post(this, EnumHandSide.LEFT));
/*      */       } 
/* 1113 */       renderLeftSleeve(player, (ModelBiped)renderplayer.func_177087_b());
/*      */     } 
/*      */     
/* 1116 */     GL11.glPopMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderMovingArm(EntityPlayer player, ModelGun model, AnimStateMachine anim, Optional<StateEntry> currentState) {
/* 1121 */     Minecraft mc = Minecraft.func_71410_x();
/* 1122 */     mc.func_110434_K().func_110577_a((Minecraft.func_71410_x()).field_71439_g.func_110306_p());
/* 1123 */     Render<AbstractClientPlayer> render = Minecraft.func_71410_x().func_175598_ae().func_78713_a((Entity)(Minecraft.func_71410_x()).field_71439_g);
/* 1124 */     RenderPlayer renderplayer = (RenderPlayer)render;
/*      */     
/* 1126 */     boolean rightArm = (model.config.arms.leftHandAmmo && model.config.arms.rightArm.armPos != null);
/* 1127 */     String movingArmState = getMovingArmState(model, anim);
/* 1128 */     WeaponAnimation weaponAnimation = WeaponAnimations.getAnimation(model.config.extra.reloadAnimation);
/*      */     
/* 1130 */     float tiltProgress = currentState.isPresent() ? ((((StateEntry)currentState.get()).stateType == StateType.Tilt || ((StateEntry)currentState.get()).stateType == StateType.Untilt) ? ((StateEntry)currentState.get()).currentValue : (anim.tiltHold ? 1.0F : 0.0F)) : 0.0F;
/*      */ 
/*      */     
/* 1133 */     Vector3f leftArmOffset = new Vector3f(0.0F, 0.0F, 0.0F);
/* 1134 */     if (player.func_184614_ca() != null && 
/* 1135 */       player.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 1136 */       ItemStack itemStack = GunType.getAttachment(player.func_184614_ca(), AttachmentPresetEnum.Grip);
/* 1137 */       if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/* 1138 */         ItemAttachment itemAttachment = (ItemAttachment)itemStack.func_77973_b();
/* 1139 */         leftArmOffset = ((ModelAttachment)itemAttachment.type.model).config.grip.leftArmOffset;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1145 */     GL11.glPushMatrix();
/*      */ 
/*      */     
/* 1148 */     GL11.glScalef(1.0F / model.config.extra.modelScale, 1.0F / model.config.extra.modelScale, 1.0F / model.config.extra.modelScale);
/*      */     
/* 1150 */     GL11.glTranslatef(leftArmOffset.x, leftArmOffset.y, leftArmOffset.z);
/*      */ 
/*      */     
/* 1153 */     if (!model.config.arms.leftHandAmmo && model.config.arms.rightArm.armPos != null && model.config.arms.rightArm.armReloadPos != null) {
/* 1154 */       GL11.glPushMatrix();
/*      */       
/* 1156 */       if (movingArmState == "Pump") {
/* 1157 */         RenderArms.renderArmPump(model, anim, RenderParameters.smoothing, model.config.arms.rightArm.armRot, model.config.arms.rightArm.armPos, model.config.arms.leftHandAmmo);
/* 1158 */       } else if (movingArmState == "Bolt") {
/* 1159 */         RenderArms.renderArmBolt(model, anim, RenderParameters.smoothing, model.config.arms.rightArm.armChargeRot, model.config.arms.rightArm.armChargePos, model.config.arms.leftHandAmmo);
/* 1160 */       } else if (movingArmState == "Default") {
/* 1161 */         GL11.glTranslatef(leftArmOffset.x, leftArmOffset.y, leftArmOffset.z);
/* 1162 */         RenderArms.renderArmDefault(model, anim, RenderParameters.smoothing, model.config.arms.rightArm.armRot, model.config.arms.rightArm.armPos, true, model.config.arms.leftHandAmmo);
/* 1163 */       } else if (movingArmState == "Load") {
/* 1164 */         RenderArms.renderArmLoad(model, anim, weaponAnimation, RenderParameters.smoothing, tiltProgress, model.config.arms.rightArm.armReloadRot, model.config.arms.rightArm.armReloadPos, model.config.arms.rightArm.armRot, model.config.arms.rightArm.armPos, model.config.arms.leftHandAmmo);
/* 1165 */       } else if (movingArmState == "Reload") {
/* 1166 */         RenderArms.renderArmReload(model, anim, weaponAnimation, RenderParameters.smoothing, tiltProgress, model.config.arms.rightArm.armReloadRot, model.config.arms.rightArm.armReloadPos, model.config.arms.rightArm.armRot, model.config.arms.rightArm.armPos, model.config.arms.leftHandAmmo);
/*      */       } 
/* 1168 */       GL11.glScalef(model.config.arms.rightArm.armScale.x, model.config.arms.rightArm.armScale.y, model.config.arms.rightArm.armScale.z);
/* 1169 */       renderplayer.func_177087_b().func_78087_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)player);
/* 1170 */       (renderplayer.func_177087_b()).field_178723_h.field_82906_o = 0.0F;
/* 1171 */       if (!MinecraftForge.EVENT_BUS.post((Event)new RenderHandFisrtPersonEvent.Pre(this, EnumHandSide.RIGHT))) {
/* 1172 */         renderplayer.func_177138_b((AbstractClientPlayer)(Minecraft.func_71410_x()).field_71439_g);
/* 1173 */         MinecraftForge.EVENT_BUS.post((Event)new RenderHandFisrtPersonEvent.Post(this, EnumHandSide.RIGHT));
/*      */       } 
/* 1175 */       renderRightSleeve(player, (ModelBiped)renderplayer.func_177087_b());
/*      */       
/* 1177 */       GL11.glPopMatrix();
/*      */     } 
/*      */     
/* 1180 */     if (model.config.arms.leftHandAmmo && model.config.arms.leftArm.armPos != null && model.config.arms.leftArm.armReloadPos != null) {
/* 1181 */       GL11.glPushMatrix();
/*      */       
/* 1183 */       GL11.glTranslatef(leftArmOffset.x, leftArmOffset.y, leftArmOffset.z);
/*      */       
/* 1185 */       if (movingArmState == "Charge") {
/* 1186 */         RenderArms.renderArmCharge(model, anim, RenderParameters.smoothing, model.config.arms.leftArm.armChargeRot, model.config.arms.leftArm.armChargePos, model.config.arms.leftArm.armRot, model.config.arms.leftArm.armPos, model.config.arms.leftHandAmmo);
/* 1187 */       } else if (movingArmState == "Pump") {
/* 1188 */         RenderArms.renderArmPump(model, anim, RenderParameters.smoothing, model.config.arms.leftArm.armRot, model.config.arms.leftArm.armPos, model.config.arms.leftHandAmmo);
/* 1189 */       } else if (movingArmState == "Default") {
/* 1190 */         GL11.glTranslatef(leftArmOffset.x, leftArmOffset.y, leftArmOffset.z);
/* 1191 */         RenderArms.renderArmDefault(model, anim, RenderParameters.smoothing, model.config.arms.leftArm.armRot, model.config.arms.leftArm.armPos, false, model.config.arms.leftHandAmmo);
/* 1192 */       } else if (movingArmState == "Load") {
/* 1193 */         RenderArms.renderArmLoad(model, anim, weaponAnimation, RenderParameters.smoothing, tiltProgress, model.config.arms.leftArm.armReloadRot, model.config.arms.leftArm.armReloadPos, model.config.arms.leftArm.armRot, model.config.arms.leftArm.armPos, model.config.arms.leftHandAmmo);
/* 1194 */       } else if (movingArmState == "Unload") {
/* 1195 */         RenderArms.renderArmUnload(model, anim, weaponAnimation, RenderParameters.smoothing, tiltProgress, model.config.arms.leftArm.armReloadRot, model.config.arms.leftArm.armReloadPos, model.config.arms.leftArm.armRot, model.config.arms.leftArm.armPos, model.config.arms.leftHandAmmo);
/* 1196 */       } else if (movingArmState == "Reload") {
/* 1197 */         RenderArms.renderArmReload(model, anim, weaponAnimation, RenderParameters.smoothing, tiltProgress, model.config.arms.leftArm.armReloadRot, model.config.arms.leftArm.armReloadPos, model.config.arms.leftArm.armRot, model.config.arms.leftArm.armPos, model.config.arms.leftHandAmmo);
/*      */       } 
/*      */       
/* 1200 */       GL11.glScalef(model.config.arms.leftArm.armScale.x, model.config.arms.leftArm.armScale.y, model.config.arms.leftArm.armScale.z);
/* 1201 */       (renderplayer.func_177087_b()).field_178724_i.field_82908_p = 0.0F;
/* 1202 */       if (!MinecraftForge.EVENT_BUS.post((Event)new RenderHandFisrtPersonEvent.Pre(this, EnumHandSide.LEFT))) {
/* 1203 */         renderplayer.func_177139_c((AbstractClientPlayer)(Minecraft.func_71410_x()).field_71439_g);
/* 1204 */         MinecraftForge.EVENT_BUS.post((Event)new RenderHandFisrtPersonEvent.Post(this, EnumHandSide.LEFT));
/*      */       } 
/* 1206 */       renderLeftSleeve(player, (ModelBiped)renderplayer.func_177087_b());
/*      */       
/* 1208 */       GL11.glPopMatrix();
/*      */     } 
/*      */     
/* 1211 */     GL11.glPopMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderLeftSleeve(EntityPlayer player, ModelBiped modelplayer) {
/* 1216 */     if (!MinecraftForge.EVENT_BUS.post((Event)new RenderHandSleeveEvent.Pre(this, EnumHandSide.LEFT, modelplayer))) {
/* 1217 */       if (player.field_71071_by.func_70440_f(2) != null) {
/* 1218 */         ItemStack armorStack = player.field_71071_by.func_70440_f(2);
/* 1219 */         if (armorStack.func_77973_b() instanceof ItemMWArmor) {
/* 1220 */           int skinId = 0;
/*      */           
/* 1222 */           String path = (skinId > 0) ? ((ItemMWArmor)armorStack.func_77973_b()).type.modelSkins[skinId].getSkin() : ((ItemMWArmor)armorStack.func_77973_b()).type.modelSkins[0].getSkin();
/*      */           
/* 1224 */           if (!((ItemMWArmor)armorStack.func_77973_b()).type.simpleArmor) {
/*      */             
/* 1226 */             ModelCustomArmor modelArmor = (ModelCustomArmor)((ItemMWArmor)armorStack.func_77973_b()).type.bipedModel;
/*      */             
/* 1228 */             bindTexture("armor", path);
/* 1229 */             GL11.glPushMatrix();
/*      */             
/* 1231 */             float modelScale = modelArmor.config.extra.modelScale;
/* 1232 */             GL11.glScalef(modelScale, modelScale, modelScale);
/* 1233 */             modelArmor.showChest(true);
/* 1234 */             modelplayer.field_178724_i.field_78795_f = 0.0F;
/* 1235 */             modelplayer.field_178724_i.field_78796_g = 0.0F;
/* 1236 */             modelplayer.field_178724_i.field_78808_h = -0.1F;
/* 1237 */             modelArmor.renderLeftArm((AbstractClientPlayer)player, modelplayer);
/*      */             
/* 1239 */             GL11.glPopMatrix();
/*      */           } else {
/*      */             
/* 1242 */             Render<AbstractClientPlayer> render = Minecraft.func_71410_x().func_175598_ae().func_78713_a((Entity)(Minecraft.func_71410_x()).field_71439_g);
/* 1243 */             RenderPlayer renderplayer = (RenderPlayer)render;
/* 1244 */             GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
/* 1245 */             bindTexture("armor", path);
/* 1246 */             renderplayer.func_177139_c((AbstractClientPlayer)(Minecraft.func_71410_x()).field_71439_g);
/*      */           } 
/*      */         } 
/*      */       } 
/* 1250 */       MinecraftForge.EVENT_BUS.post((Event)new RenderHandSleeveEvent.Post(this, EnumHandSide.LEFT, modelplayer));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderRightSleeve(EntityPlayer player, ModelBiped modelplayer) {
/* 1255 */     if (!MinecraftForge.EVENT_BUS.post((Event)new RenderHandSleeveEvent.Pre(this, EnumHandSide.RIGHT, modelplayer))) {
/* 1256 */       if (player.field_71071_by.func_70440_f(2) != null) {
/* 1257 */         ItemStack armorStack = player.field_71071_by.func_70440_f(2);
/* 1258 */         if (armorStack.func_77973_b() instanceof ItemMWArmor) {
/* 1259 */           int skinId = 0;
/*      */           
/* 1261 */           String path = (skinId > 0) ? ((ItemMWArmor)armorStack.func_77973_b()).type.modelSkins[skinId].getSkin() : ((ItemMWArmor)armorStack.func_77973_b()).type.modelSkins[0].getSkin();
/* 1262 */           if (!((ItemMWArmor)armorStack.func_77973_b()).type.simpleArmor) {
/*      */             
/* 1264 */             ModelCustomArmor modelArmor = (ModelCustomArmor)((ItemMWArmor)armorStack.func_77973_b()).type.bipedModel;
/*      */             
/* 1266 */             bindTexture("armor", path);
/* 1267 */             GL11.glPushMatrix();
/*      */             
/* 1269 */             float modelScale = modelArmor.config.extra.modelScale;
/* 1270 */             GL11.glScalef(modelScale, modelScale, modelScale);
/* 1271 */             modelArmor.showChest(true);
/* 1272 */             modelplayer.field_178723_h.field_78795_f = 0.0F;
/* 1273 */             modelplayer.field_178723_h.field_78796_g = 0.0F;
/* 1274 */             modelplayer.field_178723_h.field_78808_h = 0.1F;
/* 1275 */             modelArmor.renderRightArm((AbstractClientPlayer)player, modelplayer);
/*      */             
/* 1277 */             GL11.glPopMatrix();
/*      */           } else {
/*      */             
/* 1280 */             Render<AbstractClientPlayer> render = Minecraft.func_71410_x().func_175598_ae().func_78713_a((Entity)(Minecraft.func_71410_x()).field_71439_g);
/* 1281 */             RenderPlayer renderplayer = (RenderPlayer)render;
/* 1282 */             GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
/* 1283 */             bindTexture("armor", path);
/* 1284 */             renderplayer.func_177138_b((AbstractClientPlayer)(Minecraft.func_71410_x()).field_71439_g);
/*      */           } 
/*      */         } 
/*      */       } 
/* 1288 */       MinecraftForge.EVENT_BUS.post((Event)new RenderHandSleeveEvent.Post(this, EnumHandSide.RIGHT, modelplayer));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void renderScopeGlass(AttachmentType attachmentType, ModelAttachment modelAttachment, boolean isAiming) {
/* 1295 */     if (ScopeUtils.isIndsideGunRendering) {
/*      */       return;
/*      */     }
/*      */     
/* 1299 */     if ((Minecraft.func_71410_x()).field_71441_e != null) {
/* 1300 */       float gunRotX = RenderParameters.GUN_ROT_X_LAST + (RenderParameters.GUN_ROT_X - RenderParameters.GUN_ROT_X_LAST) * this.timer.field_194147_b;
/* 1301 */       if (isAiming && (ClientProxy.scopeUtils.blurFramebuffer != null || !ModConfig.INSTANCE.hud.ads_blur)) {
/* 1302 */         if (OptifineHelper.isShadersEnabled()) {
/* 1303 */           Shaders.pushProgram();
/*      */         }
/* 1305 */         Minecraft mc = Minecraft.func_71410_x();
/*      */         
/* 1307 */         GL20.glUseProgram(Programs.overlayProgram);
/* 1308 */         GL20.glUniform2f(GL20.glGetUniformLocation(Programs.overlayProgram, "size"), mc.field_71443_c, mc.field_71440_d);
/*      */         
/* 1310 */         GL11.glPushMatrix();
/*      */         
/* 1312 */         int tex = ClientProxy.scopeUtils.blurFramebuffer.field_147617_g;
/* 1313 */         ClientProxy.scopeUtils.blurFramebuffer.func_147610_a(false);
/* 1314 */         GL30.glFramebufferTexture2D(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, ScopeUtils.OVERLAY_TEX, 0);
/* 1315 */         GlStateManager.func_179082_a(0.0F, 0.0F, 0.0F, 0.0F);
/* 1316 */         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 1317 */         GlStateManager.func_179135_a(true, true, true, true);
/* 1318 */         GlStateManager.func_179132_a(true);
/* 1319 */         GlStateManager.func_179086_m(256);
/* 1320 */         copyDepthBuffer();
/* 1321 */         ClientProxy.scopeUtils.blurFramebuffer.func_147610_a(false);
/* 1322 */         GlStateManager.func_179086_m(16384);
/*      */ 
/*      */         
/* 1325 */         float alpha = 1.0F - RenderParameters.adsSwitch;
/*      */         
/* 1327 */         GlStateManager.func_179140_f();
/* 1328 */         GlStateManager.func_179147_l();
/* 1329 */         GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ZERO);
/* 1330 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 1331 */         modelAttachment.renderOverlaySolid(0.0625F);
/*      */         
/* 1333 */         GL20.glUseProgram(0);
/* 1334 */         if (OptifineHelper.isShadersEnabled()) {
/* 1335 */           Shaders.popProgram();
/*      */         }
/*      */         
/* 1338 */         GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1339 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, alpha);
/* 1340 */         if (attachmentType.sight.usedDefaultOverlayModelTexture) {
/* 1341 */           renderEngine.func_110577_a(new ResourceLocation("modularwarfare", "textures/skins/black.png"));
/*      */         }
/* 1343 */         modelAttachment.renderOverlay(0.0625F);
/* 1344 */         GlStateManager.func_179084_k();
/* 1345 */         GlStateManager.func_179145_e();
/*      */         
/* 1347 */         GL30.glFramebufferTexture2D(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, tex, 0);
/* 1348 */         GlStateManager.func_179086_m(256);
/* 1349 */         copyDepthBuffer();
/* 1350 */         ClientProxy.scopeUtils.blurFramebuffer.func_147610_a(false);
/* 1351 */         GlStateManager.func_179086_m(16384);
/* 1352 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*      */         
/* 1354 */         GlStateManager.func_179084_k();
/* 1355 */         renderWorldOntoScope(attachmentType, modelAttachment);
/* 1356 */         GlStateManager.func_179147_l();
/*      */         
/* 1358 */         OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, OptifineHelper.getDrawFrameBuffer());
/*      */         
/* 1360 */         GL11.glPopMatrix();
/*      */       } else {
/*      */         
/* 1363 */         GL11.glPushMatrix();
/* 1364 */         renderEngine.func_110577_a(new ResourceLocation("modularwarfare", "textures/skins/black.png"));
/* 1365 */         modelAttachment.renderOverlay(0.0625F);
/* 1366 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void copyDepthBuffer() {
/* 1373 */     Minecraft mc = Minecraft.func_71410_x();
/* 1374 */     GL30.glBindFramebuffer(36008, OptifineHelper.getDrawFrameBuffer());
/* 1375 */     GL30.glBindFramebuffer(36009, ClientProxy.scopeUtils.blurFramebuffer.field_147616_f);
/* 1376 */     GlStateManager.func_179135_a(false, false, false, false);
/* 1377 */     GL30.glBlitFramebuffer(0, 0, mc.field_71443_c, mc.field_71440_d, 0, 0, mc.field_71443_c, mc.field_71440_d, 256, 9728);
/* 1378 */     GlStateManager.func_179135_a(true, true, true, true);
/* 1379 */     GL30.glBindFramebuffer(36008, 0);
/* 1380 */     GL30.glBindFramebuffer(36009, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   private void renderWorldOntoScope(AttachmentType type, ModelAttachment modelAttachment) {
/* 1386 */     GL11.glPushMatrix();
/*      */     
/* 1388 */     if (isLightOn) {
/* 1389 */       renderEngine.func_110577_a(new ResourceLocation("modularwarfare", "textures/skins/black.png"));
/* 1390 */       GL11.glDisable(2896);
/* 1391 */       (Minecraft.func_71410_x()).field_71460_t.func_175072_h();
/* 1392 */       ModelGun.glowOn(1);
/* 1393 */       modelAttachment.renderScope(0.0625F);
/* 1394 */       ModelGun.glowOff();
/* 1395 */       GL11.glEnable(2896);
/* 1396 */       (Minecraft.func_71410_x()).field_71460_t.func_180436_i();
/*      */     } else {
/* 1398 */       renderEngine.func_110577_a(new ResourceLocation("modularwarfare", "textures/skins/black.png"));
/* 1399 */       ModelGun.glowOn(1);
/* 1400 */       modelAttachment.renderScope(0.0625F);
/* 1401 */       ModelGun.glowOff();
/*      */     } 
/* 1403 */     Minecraft mc = Minecraft.func_71410_x();
/* 1404 */     if (mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && mc.field_71474_y.field_74320_O == 0 && 
/* 1405 */       mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun) {
/* 1406 */       ItemStack gunStack = mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND);
/* 1407 */       if (GunType.getAttachment(gunStack, AttachmentPresetEnum.Flashlight) != null && 
/* 1408 */         isLightOn) {
/* 1409 */         GL11.glDisable(2896);
/* 1410 */         (Minecraft.func_71410_x()).field_71460_t.func_175072_h();
/* 1411 */         GL11.glDisable(3042);
/* 1412 */         GL11.glPushMatrix();
/* 1413 */         GL11.glPushAttrib(16384);
/* 1414 */         GL11.glEnable(3042);
/* 1415 */         GL11.glDepthMask(false);
/* 1416 */         GL11.glBlendFunc(774, 770);
/*      */         
/* 1418 */         renderEngine.func_110577_a(new ResourceLocation("modularwarfare", "textures/gui/light.png"));
/* 1419 */         modelAttachment.renderOverlay(0.0625F);
/*      */         
/* 1421 */         GL11.glBlendFunc(770, 771);
/* 1422 */         GL11.glDepthMask(true);
/* 1423 */         GL11.glDisable(3042);
/* 1424 */         GL11.glPopAttrib();
/* 1425 */         GL11.glPopMatrix();
/* 1426 */         GL11.glEnable(2896);
/* 1427 */         (Minecraft.func_71410_x()).field_71460_t.func_180436_i();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1432 */     GL11.glPopMatrix();
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\renderers\RenderGunStatic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */