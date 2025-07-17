/*      */ package com.modularwarfare.client.gui;
/*      */ 
/*      */ import com.modularwarfare.ModularWarfare;
/*      */ import com.modularwarfare.client.ClientProxy;
/*      */ import com.modularwarfare.client.ClientRenderHooks;
/*      */ import com.modularwarfare.client.fpp.basic.configs.GunRenderConfig;
/*      */ import com.modularwarfare.client.fpp.enhanced.AnimationType;
/*      */ import com.modularwarfare.client.fpp.enhanced.animation.EnhancedStateMachine;
/*      */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*      */ import com.modularwarfare.client.fpp.enhanced.models.ModelEnhancedGun;
/*      */ import com.modularwarfare.client.fpp.enhanced.renderers.RenderGunEnhanced;
/*      */ import com.modularwarfare.client.handler.ClientTickHandler;
/*      */ import com.modularwarfare.client.model.ModelAttachment;
/*      */ import com.modularwarfare.client.model.ModelGun;
/*      */ import com.modularwarfare.client.scope.ScopeUtils;
/*      */ import com.modularwarfare.client.shader.ProjectionHelper;
/*      */ import com.modularwarfare.common.guns.AmmoType;
/*      */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*      */ import com.modularwarfare.common.guns.AttachmentType;
/*      */ import com.modularwarfare.common.guns.BulletType;
/*      */ import com.modularwarfare.common.guns.GunType;
/*      */ import com.modularwarfare.common.guns.ItemAmmo;
/*      */ import com.modularwarfare.common.guns.ItemAttachment;
/*      */ import com.modularwarfare.common.guns.ItemBullet;
/*      */ import com.modularwarfare.common.guns.ItemGun;
/*      */ import com.modularwarfare.common.guns.ItemSpray;
/*      */ import com.modularwarfare.common.guns.SprayType;
/*      */ import com.modularwarfare.common.guns.WeaponAnimationType;
/*      */ import com.modularwarfare.common.guns.WeaponFireMode;
/*      */ import com.modularwarfare.common.handler.data.VarBoolean;
/*      */ import com.modularwarfare.common.network.PacketBase;
/*      */ import com.modularwarfare.common.network.PacketGunAddAttachment;
/*      */ import com.modularwarfare.common.network.PacketGunUnloadAttachment;
/*      */ import com.modularwarfare.common.type.BaseItem;
/*      */ import com.modularwarfare.common.type.BaseType;
/*      */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*      */ import com.modularwarfare.utility.ColorUtils;
/*      */ import com.modularwarfare.utility.MWSound;
/*      */ import com.modularwarfare.utility.OptifineHelper;
/*      */ import com.modularwarfare.utility.ReloadHelper;
/*      */ import com.modularwarfare.utility.RenderHelperMW;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.GuiButton;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.util.vector.ReadableVector3f;
/*      */ import org.lwjgl.util.vector.Vector3f;
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
/*      */ public class GuiGunModify
/*      */   extends GuiScreen
/*      */ {
/*  101 */   public String title = "Config GUI";
/*      */   public ItemStack currentModify;
/*  103 */   public double autoRotate = 0.0D;
/*  104 */   public double rotateY = 0.0D;
/*  105 */   public double rotateZ = 0.0D;
/*      */   public boolean leftClicked = false;
/*      */   public int lastMouseX;
/*      */   public int lastMouseY;
/*  109 */   private final int sideButtonIdOffset = 20;
/*  110 */   private final int subSlotIdOffset = 100;
/*  111 */   private final int backpackIdOffset = 200;
/*  112 */   private int slotPreLine = 6;
/*  113 */   private List<Integer> attachmentSlotList = null;
/*  114 */   private TextureButton selectedSideButton = null;
/*  115 */   private static final ProjectionHelper projectionHelper = new ProjectionHelper();
/*  116 */   public static final ResourceLocation point = new ResourceLocation("modularwarfare", "textures/modifygui/point.png");
/*  117 */   public static final ResourceLocation arrow_down = new ResourceLocation("modularwarfare", "textures/modifygui/arrow_down.png");
/*  118 */   public static final ResourceLocation arrow_left = new ResourceLocation("modularwarfare", "textures/modifygui/arrow_left.png");
/*  119 */   public static final ResourceLocation arrow_right = new ResourceLocation("modularwarfare", "textures/modifygui/arrow_right.png");
/*  120 */   public static final ResourceLocation arrow_up = new ResourceLocation("modularwarfare", "textures/modifygui/arrow_up.png");
/*  121 */   public static final ResourceLocation barrel = new ResourceLocation("modularwarfare", "textures/modifygui/barrel.png");
/*  122 */   public static final ResourceLocation charm = new ResourceLocation("modularwarfare", "textures/modifygui/charm.png");
/*  123 */   public static final ResourceLocation flashlight = new ResourceLocation("modularwarfare", "textures/modifygui/flashlight.png");
/*  124 */   public static final ResourceLocation grip = new ResourceLocation("modularwarfare", "textures/modifygui/grip.png");
/*  125 */   public static final ResourceLocation sight = new ResourceLocation("modularwarfare", "textures/modifygui/sight.png");
/*  126 */   public static final ResourceLocation sprays = new ResourceLocation("modularwarfare", "textures/modifygui/sprays.png");
/*  127 */   public static final ResourceLocation stock = new ResourceLocation("modularwarfare", "textures/modifygui/stock.png");
/*  128 */   public static final ResourceLocation slot = new ResourceLocation("modularwarfare", "textures/modifygui/slot.png");
/*  129 */   public static final ResourceLocation quit = new ResourceLocation("modularwarfare", "textures/modifygui/quit.png");
/*  130 */   public static final ResourceLocation statu = new ResourceLocation("modularwarfare", "textures/modifygui/statu.png");
/*  131 */   public static final ResourceLocation decal_1 = new ResourceLocation("modularwarfare", "textures/modifygui/decal_1.png");
/*  132 */   public static final ResourceLocation slot_topbg = new ResourceLocation("modularwarfare", "textures/modifygui/slot_topbg.png");
/*      */   public static TextureButton QUITBUTTON;
/*      */   public static TextureButton PAGEBUTTON;
/*      */   private static double subPageX;
/*      */   private static double subPageY;
/*      */   private static double subPageWidth;
/*      */   private static double subPageHeight;
/*      */   public static boolean isEnglishLanguage = true;
/*      */   public static boolean clickOnce = false;
/*      */   
/*      */   public GuiGunModify() {
/*  143 */     updateItemModifying();
/*  144 */     func_73866_w_();
/*  145 */     isEnglishLanguage = (Minecraft.func_71410_x().func_135016_M().func_135041_c().func_135034_a().indexOf("en_") != -1);
/*      */   }
/*      */   public void updateItemModifying() {
/*  148 */     ItemStack itemMainhand = (Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND);
/*  149 */     boolean refresh = false;
/*  150 */     if (this.currentModify != null && !this.currentModify.equals(itemMainhand)) {
/*  151 */       refresh = true;
/*      */     }
/*  153 */     this.currentModify = itemMainhand;
/*  154 */     if (refresh) {
/*  155 */       func_73866_w_();
/*      */     }
/*      */   }
/*      */   
/*      */   public void func_73866_w_() {
/*  160 */     RenderGunEnhanced rge = (RenderGunEnhanced)ClientRenderHooks.customRenderers[0];
/*      */     
/*  162 */     BaseType type = ((BaseItem)this.currentModify.func_77973_b()).baseType;
/*  163 */     if (((GunType)type).animationType == WeaponAnimationType.ENHANCED) {
/*  164 */       rge.getClientController().reset(true);
/*      */     }
/*      */     
/*  167 */     this.field_146297_k = Minecraft.func_71410_x();
/*  168 */     ScaledResolution scaledresolution = new ScaledResolution(this.field_146297_k);
/*  169 */     double sFactor = this.field_146297_k.field_71443_c / 1920.0D;
/*  170 */     double scaleFactor = 1.0D * sFactor / scaledresolution.func_78325_e();
/*  171 */     this.field_146292_n.clear();
/*  172 */     int buttonSize = 90 / scaledresolution.func_78325_e();
/*  173 */     QUITBUTTON = (new TextureButton(1000, (scaledresolution.func_78326_a() - buttonSize * 1.5F), (scaledresolution.func_78328_b() - buttonSize * 1.5F), buttonSize, buttonSize, quit)).setType(TextureButton.TypeEnum.Button);
/*      */     
/*  175 */     this.field_146292_n.add(QUITBUTTON);
/*  176 */     if (PAGEBUTTON == null) {
/*  177 */       PAGEBUTTON = (new TextureButton(1001, 0.0D, 0.0D, buttonSize / 4, buttonSize, decal_1)).setType(TextureButton.TypeEnum.SideButtonVert);
/*      */     }
/*  179 */     PAGEBUTTON.field_146120_f = buttonSize / 4;
/*  180 */     PAGEBUTTON.field_146121_g = buttonSize;
/*  181 */     subPageX = 0.0D;
/*  182 */     subPageY = 0.0D;
/*  183 */     subPageWidth = ((PAGEBUTTON.state == -1) ? 'ŀ' : false) * scaleFactor;
/*  184 */     subPageHeight = 512.0D * scaleFactor;
/*  185 */     PAGEBUTTON.field_146128_h = subPageX + subPageWidth;
/*  186 */     PAGEBUTTON.field_146129_i = 0.0D;
/*      */     
/*  188 */     this.field_146292_n.add(PAGEBUTTON);
/*  189 */     this.field_146292_n.add((new TextureButton(0, 9999.0D, 9999.0D, buttonSize, buttonSize, slot)).setAttachment(AttachmentPresetEnum.Barrel).setType(TextureButton.TypeEnum.Slot));
/*  190 */     this.field_146292_n.add((new TextureButton(1, 9999.0D, 9999.0D, buttonSize, buttonSize, slot)).setAttachment(AttachmentPresetEnum.Charm).setType(TextureButton.TypeEnum.Slot));
/*  191 */     this.field_146292_n.add((new TextureButton(2, 9999.0D, 9999.0D, buttonSize, buttonSize, slot)).setAttachment(AttachmentPresetEnum.Flashlight).setType(TextureButton.TypeEnum.Slot));
/*  192 */     this.field_146292_n.add((new TextureButton(3, 9999.0D, 9999.0D, buttonSize, buttonSize, slot)).setAttachment(AttachmentPresetEnum.Grip).setType(TextureButton.TypeEnum.Slot));
/*  193 */     this.field_146292_n.add((new TextureButton(4, 9999.0D, 9999.0D, buttonSize, buttonSize, slot)).setAttachment(AttachmentPresetEnum.Sight).setType(TextureButton.TypeEnum.Slot));
/*  194 */     this.field_146292_n.add((new TextureButton(5, 9999.0D, 9999.0D, buttonSize, buttonSize, slot)).setAttachment(AttachmentPresetEnum.Skin).setType(TextureButton.TypeEnum.Slot));
/*  195 */     this.field_146292_n.add((new TextureButton(6, 9999.0D, 9999.0D, buttonSize, buttonSize, slot)).setAttachment(AttachmentPresetEnum.Stock).setType(TextureButton.TypeEnum.Slot));
/*  196 */     this.field_146292_n.add((new TextureButton(20, 9999.0D, 9999.0D, buttonSize / 3, buttonSize, decal_1)).setType(TextureButton.TypeEnum.SideButton));
/*  197 */     this.field_146292_n.add((new TextureButton(21, 9999.0D, 9999.0D, buttonSize / 3, buttonSize, decal_1)).setType(TextureButton.TypeEnum.SideButton));
/*  198 */     this.field_146292_n.add((new TextureButton(22, 9999.0D, 9999.0D, buttonSize / 3, buttonSize, decal_1)).setType(TextureButton.TypeEnum.SideButton));
/*  199 */     this.field_146292_n.add((new TextureButton(23, 9999.0D, 9999.0D, buttonSize / 3, buttonSize, decal_1)).setType(TextureButton.TypeEnum.SideButton));
/*  200 */     this.field_146292_n.add((new TextureButton(24, 9999.0D, 9999.0D, buttonSize / 3, buttonSize, decal_1)).setType(TextureButton.TypeEnum.SideButton));
/*  201 */     this.field_146292_n.add((new TextureButton(25, 9999.0D, 9999.0D, buttonSize / 3, buttonSize, decal_1)).setType(TextureButton.TypeEnum.SideButton));
/*  202 */     this.field_146292_n.add((new TextureButton(26, 9999.0D, 9999.0D, buttonSize / 3, buttonSize, decal_1)).setType(TextureButton.TypeEnum.SideButton));
/*      */     
/*  204 */     updateButtonItem();
/*      */   }
/*      */   public List<Integer> checkAttach(EntityPlayer player, GunType gunType, AttachmentPresetEnum attachmentEnum) {
/*  207 */     List<Integer> attachments = new ArrayList<>();
/*      */     
/*  209 */     for (int i = 0; i < player.field_71071_by.func_70302_i_(); i++) {
/*  210 */       ItemStack itemStack = player.field_71071_by.func_70301_a(i);
/*  211 */       if (attachmentEnum != AttachmentPresetEnum.Skin) {
/*  212 */         if (itemStack != null && itemStack.func_77973_b() instanceof ItemAttachment) {
/*  213 */           ItemAttachment itemAttachment = (ItemAttachment)itemStack.func_77973_b();
/*  214 */           AttachmentType attachType = itemAttachment.type;
/*  215 */           if (attachType.attachmentType.equals(attachmentEnum) && 
/*  216 */             gunType.acceptedAttachments.get(attachType.attachmentType) != null && ((ArrayList)gunType.acceptedAttachments.get(attachType.attachmentType)).size() >= 1 && (
/*  217 */             (ArrayList)gunType.acceptedAttachments.get(attachType.attachmentType)).contains(attachType.internalName)) {
/*  218 */             attachments.add(Integer.valueOf(i));
/*      */           
/*      */           }
/*      */         }
/*      */       
/*      */       }
/*  224 */       else if (itemStack != null && itemStack.func_77973_b() instanceof ItemSpray) {
/*  225 */         ItemSpray itemSpray = (ItemSpray)itemStack.func_77973_b();
/*  226 */         SprayType attachType = itemSpray.type;
/*  227 */         for (int j = 0; j < gunType.modelSkins.length; j++) {
/*  228 */           if ((gunType.modelSkins[j]).internalName.equalsIgnoreCase(attachType.skinName)) {
/*  229 */             attachments.add(Integer.valueOf(i));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  235 */     return attachments;
/*      */   }
/*      */   
/*      */   public void joinSubPageButtons(EntityPlayer player, ScaledResolution scaledresolution) {
/*  239 */     getClass(); TextureButton currentButton = getButton(this.selectedSideButton.field_146127_k - 20);
/*  240 */     BaseType type = ((BaseItem)this.currentModify.func_77973_b()).baseType;
/*  241 */     GunType gunType = (GunType)type;
/*  242 */     this.attachmentSlotList = checkAttach((EntityPlayer)this.field_146297_k.field_71439_g, gunType, currentButton.getAttachmentType());
/*  243 */     int buttonSize = 90 / scaledresolution.func_78325_e();
/*  244 */     boolean isSkin = currentButton.getAttachmentType().equals(AttachmentPresetEnum.Skin);
/*  245 */     if (!isSkin) {
/*  246 */       this.field_146292_n.add((new TextureButton(-1, 9999.0D, 9999.0D, buttonSize, buttonSize, slot)).setAttachment(currentButton.getAttachmentType()).setType(TextureButton.TypeEnum.SubSlot));
/*      */     }
/*  248 */     for (Integer inventoryId : this.attachmentSlotList)
/*  249 */       this.field_146292_n.add((new TextureButton(100 + inventoryId.intValue(), 9999.0D, 9999.0D, buttonSize, buttonSize, slot)).setAttachment(currentButton.getAttachmentType()).setType(TextureButton.TypeEnum.SubSlot).setItemStack(player.field_71071_by.func_70301_a(inventoryId.intValue()))); 
/*      */   }
/*      */   
/*      */   public boolean gunHasMultiSkins() {
/*  253 */     BaseType type = ((BaseItem)this.currentModify.func_77973_b()).baseType;
/*  254 */     GunType gunType = (GunType)type;
/*  255 */     return (gunType.modelSkins != null && gunType.modelSkins.length > 1);
/*      */   }
/*      */   public void updateButtonItem() {
/*  258 */     BaseType type = ((BaseItem)this.currentModify.func_77973_b()).baseType;
/*  259 */     GunType gunType = (GunType)type;
/*  260 */     List<AttachmentPresetEnum> keys = new ArrayList<>(gunType.acceptedAttachments.keySet());
/*  261 */     if (gunType.modelSkins.length > 1) {
/*  262 */       keys.add(AttachmentPresetEnum.Skin);
/*      */     }
/*  264 */     for (GuiButton button : this.field_146292_n) {
/*  265 */       if (button instanceof TextureButton) {
/*  266 */         TextureButton tb = (TextureButton)button;
/*  267 */         if (tb.getAttachmentType() == AttachmentPresetEnum.Skin && gunHasMultiSkins()) {
/*  268 */           int skinId = 0;
/*  269 */           if (this.currentModify.func_77942_o() && 
/*  270 */             this.currentModify.func_77978_p().func_74764_b("skinId")) {
/*  271 */             skinId = this.currentModify.func_77978_p().func_74762_e("skinId");
/*      */           }
/*      */           
/*  274 */           ItemSpray itemSpray = null;
/*  275 */           for (Item item : Item.field_150901_e) {
/*  276 */             if (item instanceof ItemSpray) {
/*  277 */               ItemSpray itemS = (ItemSpray)item;
/*  278 */               SprayType attachType = itemS.type;
/*  279 */               if ((gunType.modelSkins[skinId]).internalName.equalsIgnoreCase(attachType.skinName)) {
/*  280 */                 itemSpray = itemS;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*  285 */           if (itemSpray != null) {
/*  286 */             ItemStack skinItem = new ItemStack((Item)itemSpray, 1);
/*  287 */             tb.setItemStack(skinItem);
/*      */           } 
/*      */         } 
/*      */       } 
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
/*  302 */     for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/*  303 */       ItemStack itemStack = GunType.getAttachment(this.currentModify, attachment);
/*  304 */       if (keys.contains(attachment) && itemStack != null && !itemStack.func_190926_b()) {
/*  305 */         for (GuiButton button : this.field_146292_n) {
/*  306 */           if (button instanceof TextureButton) {
/*  307 */             TextureButton tb = (TextureButton)button;
/*  308 */             if (tb.getType().equals(TextureButton.TypeEnum.Slot) && tb.getAttachmentType() == attachment) {
/*  309 */               tb.setItemStack(itemStack);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*  319 */     clickOnce = false;
/*  320 */     updateItemModifying();
/*  321 */     EntityRenderer renderer = this.field_146297_k.field_71460_t;
/*  322 */     double sFactor = this.field_146297_k.field_71443_c / 1920.0D;
/*  323 */     float farPlaneDistance = this.field_146297_k.field_71474_y.field_151451_c * 16.0F;
/*  324 */     ScaledResolution scaledresolution = new ScaledResolution(Minecraft.func_71410_x());
/*  325 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*  326 */     GlStateManager.func_179128_n(5889);
/*  327 */     GlStateManager.func_179094_E();
/*  328 */     GlStateManager.func_179096_D();
/*      */     
/*  330 */     GlStateManager.func_179130_a(0.0D, scaledresolution.func_78327_c(), scaledresolution.func_78324_d(), 0.0D, 100.0D, 4000.0D);
/*  331 */     GlStateManager.func_179109_b(0.0F, 0.0F, -2000.0F);
/*  332 */     GlStateManager.func_179083_b(0, 0, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/*      */     
/*  334 */     GlStateManager.func_179128_n(5888);
/*  335 */     GlStateManager.func_179094_E();
/*  336 */     GlStateManager.func_179096_D();
/*      */     
/*  338 */     int color = ColorUtils.getARGB(100, 100, 100, 120);
/*      */     
/*  340 */     func_73733_a(0, 0, this.field_146294_l, this.field_146295_m, color, color);
/*  341 */     this.autoRotate++;
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
/*  353 */     double scale = 300.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  360 */     float f3 = ((EntityLivingBase)entityPlayerSP).field_70127_C + (((EntityLivingBase)entityPlayerSP).field_70125_A - ((EntityLivingBase)entityPlayerSP).field_70127_C) * partialTicks;
/*      */     
/*  362 */     float f4 = ((EntityLivingBase)entityPlayerSP).field_70126_B + (((EntityLivingBase)entityPlayerSP).field_70177_z - ((EntityLivingBase)entityPlayerSP).field_70126_B) * partialTicks;
/*      */ 
/*      */     
/*  365 */     renderer.func_180436_i();
/*      */ 
/*      */     
/*  368 */     GlStateManager.func_179094_E();
/*  369 */     GlStateManager.func_179114_b(f3, 1.0F, 0.0F, 0.0F);
/*  370 */     GlStateManager.func_179114_b(f4, 0.0F, 1.0F, 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  375 */     GlStateManager.func_179104_a(1032, 5634);
/*      */ 
/*      */     
/*  378 */     GlStateManager.func_179103_j(7425);
/*  379 */     GlStateManager.func_179121_F();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  385 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/*      */ 
/*      */ 
/*      */     
/*  389 */     BaseType type = ((BaseItem)this.currentModify.func_77973_b()).baseType;
/*  390 */     ItemStack itemstack = this.currentModify;
/*  391 */     GlStateManager.func_179094_E();
/*  392 */     if (((GunType)type).animationType == WeaponAnimationType.BASIC) {
/*  393 */       renderBasicModel((EntityLivingBase)entityPlayerSP, type, scale, itemstack, sFactor, scaledresolution, f4);
/*  394 */     } else if (((GunType)type).animationType == WeaponAnimationType.ENHANCED) {
/*  395 */       renderEnhancedModel((EntityLivingBase)entityPlayerSP, type, scale, itemstack, sFactor, scaledresolution, f4);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  402 */     projectionHelper.updateMatrices();
/*  403 */     GlStateManager.func_179121_F();
/*  404 */     renderSlotStuff(mouseX, mouseY, (EntityLivingBase)entityPlayerSP, type, scale, sFactor, itemstack, scaledresolution, f4);
/*  405 */     if (PAGEBUTTON.state == -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  414 */     GlStateManager.func_179128_n(5889);
/*  415 */     GlStateManager.func_179121_F();
/*  416 */     GlStateManager.func_179128_n(5888);
/*  417 */     GlStateManager.func_179121_F();
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderSlotStuff(int mouseX, int mouseY, EntityLivingBase entitylivingbaseIn, BaseType type, double scale, double sFactor, ItemStack itemstack, ScaledResolution scaledresolution, float partialTicks) {
/*  422 */     GL11.glTranslatef(0.0F, 0.0F, 1000.0F);
/*  423 */     GlStateManager.func_179084_k();
/*  424 */     GlStateManager.func_179140_f();
/*  425 */     GlStateManager.func_179090_x();
/*  426 */     GL11.glPointSize(10.0F);
/*  427 */     GL11.glColor3f(0.0F, 1.0F, 0.0F);
/*  428 */     GunType gunType = (GunType)type;
/*  429 */     boolean isBasic = (gunType.animationType == WeaponAnimationType.BASIC);
/*      */     
/*  431 */     GunEnhancedRenderConfig config = null;
/*  432 */     GunRenderConfig configBasic = null;
/*  433 */     if (isBasic) {
/*  434 */       ModelGun model = (ModelGun)gunType.model;
/*  435 */       configBasic = model.config;
/*      */     } else {
/*  437 */       config = (GunEnhancedRenderConfig)gunType.enhancedModel.config;
/*      */     } 
/*      */     
/*  440 */     ArrayList<float[]> placeList = (ArrayList)new ArrayList<>();
/*  441 */     float num_segments = 200.0F;
/*  442 */     float cx = (float)(scaledresolution.func_78327_c() / 2.0D);
/*  443 */     float cy = (float)(scaledresolution.func_78324_d() / 2.0D) - (5 * scaledresolution.func_78325_e());
/*  444 */     float rx = (float)(560.0D * sFactor / scaledresolution.func_78325_e());
/*  445 */     float ry = (float)(280.0D * sFactor / scaledresolution.func_78325_e());
/*  446 */     float theta = (float)(6.2831852D / num_segments);
/*  447 */     float c = (float)Math.cos(theta);
/*  448 */     float s = (float)Math.sin(theta);
/*      */     
/*  450 */     float x = 1.0F;
/*  451 */     float y = 0.0F;
/*      */     
/*  453 */     for (int ii = 0; ii < num_segments; ii++) {
/*      */ 
/*      */       
/*  456 */       placeList.add(new float[] { x * rx + cx, y * ry + cy });
/*      */       
/*  458 */       float t = x;
/*  459 */       x = c * x - s * y;
/*  460 */       y = s * t + c * y;
/*      */     } 
/*      */     
/*  463 */     for (GuiButton button : this.field_146292_n) {
/*  464 */       if (button instanceof TextureButton && ((TextureButton)button).getType().equals(TextureButton.TypeEnum.SideButton)) {
/*  465 */         button.field_146125_m = false;
/*      */       }
/*      */     } 
/*  468 */     ArrayList<float[]> slotPlaceList = (ArrayList)new ArrayList<>();
/*  469 */     List<AttachmentPresetEnum> keys = new ArrayList<>(gunType.acceptedAttachments.keySet());
/*  470 */     if (gunType.modelSkins.length > 1) {
/*  471 */       keys.add(AttachmentPresetEnum.Skin);
/*      */     }
/*  473 */     for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/*  474 */       ItemStack itemStack = GunType.getAttachment(itemstack, attachment);
/*  475 */       if (keys.contains(attachment)) {
/*      */ 
/*      */         
/*  478 */         Vector3f partTranslate = new Vector3f(0.0F, 0.0F, 0.0F);
/*  479 */         if (isBasic) {
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
/*  491 */           ArrayList<Vector3f> array = (ArrayList<Vector3f>)configBasic.attachments.attachmentPointMap.get(attachment);
/*  492 */           if (array != null && array.size() > 1) {
/*  493 */             partTranslate = array.get(0);
/*      */           }
/*      */         } else {
/*  496 */           if (config.attachmentGroup.containsKey(attachment.typeName)) {
/*  497 */             GunEnhancedRenderConfig.AttachmentGroup ag = (GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName);
/*  498 */             if (ag != null) {
/*  499 */               partTranslate = new Vector3f((ReadableVector3f)ag.translate);
/*      */             }
/*      */           } 
/*  502 */           if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*  503 */             AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/*  504 */             if (config.attachment.containsKey(attachmentType.internalName)) {
/*  505 */               if (((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).binding.equals("gunModel")) {
/*  506 */                 if (config.attachmentGroup.containsKey(attachment.typeName)) {
/*  507 */                   GunEnhancedRenderConfig.AttachmentGroup ag = (GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName);
/*  508 */                   if (ag != null) {
/*  509 */                     partTranslate = new Vector3f((ReadableVector3f)ag.translate);
/*      */                   }
/*      */                 }
/*      */               
/*      */               } else {
/*      */                 
/*  515 */                 partTranslate.x = ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).attachmentGuiOffset.x;
/*  516 */                 partTranslate.y = ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).attachmentGuiOffset.y;
/*  517 */                 partTranslate.z = ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).attachmentGuiOffset.z;
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  529 */         Vec3d partVec3d = projectionHelper.project(partTranslate.x, partTranslate.y, partTranslate.z);
/*  530 */         float[] nearestPoint = { 9999.0F, 9999.0F };
/*      */ 
/*      */         
/*  533 */         Vec3d scaledPartVec3d = new Vec3d(partVec3d.field_72450_a / scaledresolution.func_78325_e(), scaledresolution.func_78324_d() - partVec3d.field_72448_b / scaledresolution.func_78325_e(), 0.0D);
/*      */         
/*  535 */         for (float[] point : placeList) {
/*  536 */           double vtpD = scaledPartVec3d.func_72438_d(new Vec3d(point[0], point[1], 0.0D));
/*      */           
/*  538 */           double slotSize = 61.0D / scaledresolution.func_78325_e();
/*      */           
/*  540 */           if (scaledPartVec3d.func_72438_d(new Vec3d(nearestPoint[0], nearestPoint[1], 0.0D)) > vtpD) {
/*  541 */             boolean canJoin = true;
/*  542 */             AxisAlignedBB slotArea = new AxisAlignedBB(point[0], point[1], 0.0D, point[0], point[1], 1.0D);
/*      */             
/*  544 */             slotArea = slotArea.func_72314_b(slotSize, slotSize, 0.0D);
/*      */             
/*  546 */             for (float[] otherSlotPlace : slotPlaceList) {
/*  547 */               AxisAlignedBB otherSlotArea = new AxisAlignedBB(otherSlotPlace[0], otherSlotPlace[1], 0.0D, otherSlotPlace[0], otherSlotPlace[1], 1.0D);
/*      */               
/*  549 */               otherSlotArea = otherSlotArea.func_72314_b(slotSize, slotSize, 0.0D);
/*  550 */               if (otherSlotArea.func_72326_a(slotArea)) {
/*  551 */                 canJoin = false;
/*      */               }
/*      */             } 
/*  554 */             if (canJoin) {
/*  555 */               nearestPoint = point;
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  562 */         this.field_146297_k.field_71446_o.func_110577_a(GuiGunModify.point);
/*  563 */         GlStateManager.func_187421_b(3553, 10240, 9729);
/*  564 */         GlStateManager.func_187421_b(3553, 10241, 9729);
/*  565 */         GlStateManager.func_179098_w();
/*  566 */         double pointSize = 3.0D;
/*  567 */         RenderHelperMW.drawTexturedRect(scaledPartVec3d.field_72450_a - pointSize / 2.0D, scaledPartVec3d.field_72448_b - pointSize / 2.0D, pointSize, pointSize);
/*  568 */         GlStateManager.func_179090_x();
/*  569 */         GL11.glColor3f(0.8F, 0.8F, 0.8F);
/*  570 */         GL11.glLineWidth(2.0F);
/*  571 */         GL11.glEnable(2848);
/*  572 */         GlStateManager.func_179147_l();
/*  573 */         drawLine(scaledPartVec3d.field_72450_a, scaledPartVec3d.field_72448_b, (nearestPoint[0] + (8 / scaledresolution.func_78325_e())), (nearestPoint[1] + (8 / scaledresolution.func_78325_e())));
/*  574 */         GL11.glDisable(2848);
/*  575 */         GlStateManager.func_179084_k();
/*  576 */         GL11.glLineWidth(1.0F);
/*  577 */         GlStateManager.func_179098_w();
/*  578 */         this.field_146297_k.field_71446_o.func_110577_a(slot_topbg);
/*  579 */         GlStateManager.func_187421_b(3553, 10240, 9729);
/*  580 */         GlStateManager.func_187421_b(3553, 10241, 9729);
/*  581 */         double topBGsize = (30 / scaledresolution.func_78325_e());
/*  582 */         RenderHelperMW.drawTexturedRect((nearestPoint[0] - (45 / scaledresolution.func_78325_e())), (nearestPoint[1] - (75 / scaledresolution.func_78325_e())), topBGsize * 2.0D, topBGsize);
/*  583 */         double iconSize = (25 / scaledresolution.func_78325_e());
/*  584 */         bindAttachmentTexture(attachment);
/*  585 */         RenderHelperMW.drawTexturedRect((nearestPoint[0] - (40 / scaledresolution.func_78325_e())), nearestPoint[1] - 71.5D / scaledresolution.func_78325_e(), iconSize, iconSize);
/*      */         
/*  587 */         GlStateManager.func_179090_x();
/*  588 */         GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*      */ 
/*      */         
/*  591 */         for (GuiButton button : this.field_146292_n) {
/*  592 */           if (button instanceof TextureButton) {
/*  593 */             TextureButton tb = (TextureButton)button;
/*  594 */             if (tb.getType().equals(TextureButton.TypeEnum.Slot) && tb.getAttachmentType() == attachment) {
/*  595 */               tb.field_146128_h = (nearestPoint[0] - (tb.field_146120_f / 2));
/*  596 */               tb.field_146129_i = (nearestPoint[1] - (tb.field_146121_g / 2));
/*  597 */               tb.field_146125_m = true;
/*  598 */               TextureButton sideButton = getButton(tb.field_146127_k + 20);
/*  599 */               sideButton.hidden = true;
/*  600 */               sideButton.field_146128_h = nearestPoint[0] + tb.field_146120_f / 1.9D;
/*  601 */               sideButton.field_146129_i = (nearestPoint[1] - (tb.field_146121_g / 2));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  606 */         slotPlaceList.add(nearestPoint);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  614 */     GlStateManager.func_179098_w();
/*  615 */     for (GuiButton button : this.field_146292_n) {
/*  616 */       if (button instanceof TextureButton) {
/*  617 */         TextureButton tb = (TextureButton)button;
/*  618 */         if (tb.getType().equals(TextureButton.TypeEnum.SideButton))
/*  619 */           button.func_191745_a(this.field_146297_k, mouseX, mouseY, partialTicks); 
/*      */       } 
/*      */     } 
/*  622 */     for (GuiButton button : this.field_146292_n) {
/*  623 */       if (button instanceof TextureButton) {
/*  624 */         TextureButton tb = (TextureButton)button;
/*  625 */         if (tb.getType().equals(TextureButton.TypeEnum.Slot))
/*  626 */           button.func_191745_a(this.field_146297_k, mouseX, mouseY, partialTicks); 
/*      */       } 
/*      */     } 
/*  629 */     drawButtonSubPage(mouseX, mouseY, partialTicks);
/*      */   }
/*      */   public void drawSubPage(int mouseX, int mouseY, float partialTicks, double sFactor, ScaledResolution scaledresolution) {
/*  632 */     BaseType type = ((BaseItem)this.currentModify.func_77973_b()).baseType;
/*  633 */     GunType gunType = (GunType)type;
/*  634 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*  635 */     GlStateManager.func_179098_w();
/*  636 */     this.field_146297_k.field_71446_o.func_110577_a(statu);
/*  637 */     GlStateManager.func_187421_b(3553, 10240, 9729);
/*  638 */     GlStateManager.func_187421_b(3553, 10241, 9729);
/*  639 */     RenderHelperMW.drawTexturedRect(subPageX, subPageY, subPageWidth, subPageHeight + 512.0D);
/*  640 */     int color = 16777215;
/*  641 */     double fontScale = 2.0D * sFactor / scaledresolution.func_78325_e();
/*  642 */     GlStateManager.func_179094_E();
/*  643 */     GlStateManager.func_179152_a(0.6666667F, 0.5F, 0.5F);
/*  644 */     RenderHelperMW.renderCenteredText(this.currentModify.func_82833_r(), (int)((subPageX + subPageWidth / 2.0D) / fontScale), (int)(subPageY / fontScale + 10.0D), color);
/*  645 */     ArrayList<String> toolTips = new ArrayList<>();
/*  646 */     ((BaseItem)this.currentModify.func_77973_b()).func_77624_a(this.currentModify, null, toolTips, null);
/*      */     
/*  648 */     double indexY = 1.0D;
/*  649 */     int limitWidth = 320;
/*  650 */     for (String str : toolTips) {
/*  651 */       int strW = this.field_146297_k.field_71466_p.func_78256_a(str);
/*  652 */       while (strW > limitWidth) {
/*  653 */         str = str.substring(0, str.length() - 1);
/*  654 */         strW = this.field_146297_k.field_71466_p.func_78256_a(str);
/*      */       } 
/*  656 */       RenderHelperMW.renderText(str, 2, (int)(indexY * 10.0D) + 20, color);
/*  657 */       indexY++;
/*      */     } 
/*  659 */     GlStateManager.func_179121_F();
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawButtonSubPage(int mouseX, int mouseY, float partialTicks) {
/*  664 */     int indexX = 0;
/*  665 */     int indexY = 0;
/*  666 */     int maxX = 0;
/*  667 */     TextureButton parentButton = null;
/*  668 */     if (this.selectedSideButton != null) {
/*      */       
/*  670 */       getClass(); parentButton = getButton(this.selectedSideButton.field_146127_k - 20);
/*      */       
/*  672 */       boolean isSkin = (parentButton.getAttachmentType() == AttachmentPresetEnum.Skin);
/*  673 */       indexY = isSkin ? 1 : 2;
/*  674 */       for (GuiButton button2 : this.field_146292_n) {
/*  675 */         if (button2 instanceof TextureButton) {
/*  676 */           TextureButton tb2 = (TextureButton)button2;
/*  677 */           if (tb2.getType().equals(TextureButton.TypeEnum.SubSlot)) {
/*  678 */             if (tb2.getItemStack().func_190926_b()) {
/*  679 */               tb2.field_146128_h = parentButton.field_146128_h;
/*  680 */               parentButton.field_146129_i += parentButton.field_146121_g * 1.05D;
/*      */               continue;
/*      */             } 
/*  683 */             parentButton.field_146128_h += parentButton.field_146120_f * 1.05D * indexX;
/*  684 */             parentButton.field_146129_i += parentButton.field_146121_g * 1.05D * indexY;
/*  685 */             indexX++;
/*  686 */             if (maxX < indexX)
/*      */             {
/*  688 */               maxX = indexX;
/*      */             }
/*  690 */             if (indexX % 6 == 0) {
/*      */               
/*  692 */               indexX = 0;
/*  693 */               indexY++;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  700 */     if (indexX % 6 == 0)
/*      */     {
/*  702 */       indexY--;
/*      */     }
/*  704 */     GlStateManager.func_179097_i();
/*      */     
/*  706 */     if (parentButton != null) {
/*  707 */       GlStateManager.func_179131_c(0.0F, 0.0F, 0.0F, 1.0F);
/*  708 */       GlStateManager.func_179090_x();
/*  709 */       RenderHelperMW.drawTexturedRect(parentButton.field_146128_h, parentButton.field_146129_i + parentButton.field_146121_g, parentButton.field_146120_f * 1.05D * maxX, parentButton.field_146121_g * 1.07D * indexY);
/*  710 */       GlStateManager.func_179098_w();
/*      */     } 
/*      */     
/*  713 */     for (GuiButton button : this.field_146292_n) {
/*  714 */       if (button instanceof TextureButton) {
/*  715 */         TextureButton tb = (TextureButton)button;
/*  716 */         if (tb.getType().equals(TextureButton.TypeEnum.SubSlot))
/*  717 */           tb.func_191745_a(this.field_146297_k, mouseX, mouseY, partialTicks); 
/*      */       } 
/*      */     } 
/*  720 */     GlStateManager.func_179126_j();
/*      */   }
/*      */   public TextureButton getButton(int id) {
/*  723 */     for (GuiButton button : this.field_146292_n) {
/*  724 */       if (button.field_146127_k == id)
/*  725 */         return (TextureButton)button; 
/*      */     } 
/*  727 */     return null;
/*      */   }
/*      */   public void bindAttachmentTexture(AttachmentPresetEnum attachment) {
/*  730 */     switch (attachment) {
/*      */       case Barrel:
/*  732 */         this.field_146297_k.field_71446_o.func_110577_a(barrel);
/*      */         break;
/*      */       case Charm:
/*  735 */         this.field_146297_k.field_71446_o.func_110577_a(charm);
/*      */         break;
/*      */       case Flashlight:
/*  738 */         this.field_146297_k.field_71446_o.func_110577_a(flashlight);
/*      */         break;
/*      */       case Grip:
/*  741 */         this.field_146297_k.field_71446_o.func_110577_a(grip);
/*      */         break;
/*      */       case Sight:
/*  744 */         this.field_146297_k.field_71446_o.func_110577_a(sight);
/*      */         break;
/*      */       case Skin:
/*  747 */         this.field_146297_k.field_71446_o.func_110577_a(sprays);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case Stock:
/*  753 */         this.field_146297_k.field_71446_o.func_110577_a(stock);
/*      */         break;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderBasicModel(EntityLivingBase entitylivingbaseIn, BaseType type, double scale, ItemStack itemstack, double sFactor, ScaledResolution scaledresolution, float partialTicks) {
/*  769 */     ItemGun gun = (ItemGun)itemstack.func_77973_b();
/*  770 */     GunType gunType = gun.type;
/*  771 */     ModelGun model = (ModelGun)gunType.model;
/*  772 */     float modelScale = (model != null) ? model.config.extra.modelScale : 1.0F;
/*  773 */     GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
/*  774 */     RenderHelper.func_74519_b();
/*  775 */     GlStateManager.func_179114_b(-180.0F, 1.0F, 0.0F, 0.0F);
/*  776 */     GlStateManager.func_179091_B();
/*  777 */     GlStateManager.func_179126_j();
/*  778 */     GlStateManager.func_179109_b(0.0F, 0.0F, 600.0F);
/*  779 */     double centerOffsetY = 0.0D;
/*  780 */     double centerOffsetX = 0.0D;
/*  781 */     centerOffsetX = model.config.extra.modelGuiRotateCenter.x;
/*  782 */     centerOffsetY = model.config.extra.modelGuiRotateCenter.y;
/*  783 */     double basicS = 300.0D;
/*  784 */     scale = basicS * model.config.extra.modelGuiScale * sFactor / scaledresolution.func_78325_e();
/*  785 */     GlStateManager.func_179109_b((scaledresolution
/*  786 */         .func_78326_a() / 2), (scaledresolution
/*  787 */         .func_78328_b() / 2), 0.0F);
/*  788 */     GlStateManager.func_179139_a(scale, scale, -scale);
/*  789 */     GlStateManager.func_179139_a(modelScale * 0.8D, modelScale * 0.8D, modelScale * 0.8D);
/*  790 */     GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
/*  791 */     GlStateManager.func_179114_b((float)this.rotateY, 0.0F, 1.0F, 0.0F);
/*  792 */     GlStateManager.func_179114_b((float)this.rotateZ, 1.0F, 0.0F, 0.0F);
/*  793 */     GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
/*  794 */     GlStateManager.func_179137_b(centerOffsetX, centerOffsetY, 0.0D);
/*  795 */     float worldScale = 0.0625F;
/*  796 */     if (model != null) {
/*  797 */       int skinId = 0;
/*  798 */       if (itemstack.func_77942_o() && 
/*  799 */         itemstack.func_77978_p().func_74764_b("skinId")) {
/*  800 */         skinId = itemstack.func_77978_p().func_74762_e("skinId");
/*      */       }
/*      */ 
/*      */       
/*  804 */       String path = (skinId > 0) ? gunType.modelSkins[skinId].getSkin() : gunType.modelSkins[0].getSkin();
/*  805 */       ClientRenderHooks.customRenderers[1].bindTexture("guns", path);
/*  806 */       model.renderPart("gunModel", worldScale);
/*  807 */       model.renderPart("slideModel", worldScale);
/*  808 */       model.renderPart("boltModel", worldScale);
/*  809 */       model.renderPart("defaultBarrelModel", worldScale);
/*  810 */       model.renderPart("defaultStockModel", worldScale);
/*  811 */       model.renderPart("defaultGripModel", worldScale);
/*  812 */       model.renderPart("defaultGadgetModel", worldScale);
/*  813 */       if (ItemGun.hasAmmoLoaded(itemstack)) {
/*  814 */         model.renderPart("ammoModel", worldScale);
/*      */       }
/*      */       
/*  817 */       boolean hasScopeAttachment = false;
/*  818 */       GlStateManager.func_179094_E();
/*  819 */       for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/*  820 */         GlStateManager.func_179094_E();
/*  821 */         ItemStack itemStack = GunType.getAttachment(itemstack, attachment);
/*  822 */         if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*  823 */           AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/*  824 */           ModelAttachment attachmentModel = (ModelAttachment)attachmentType.model;
/*  825 */           if (attachmentType.attachmentType == AttachmentPresetEnum.Sight) {
/*  826 */             hasScopeAttachment = true;
/*      */           }
/*      */           
/*  829 */           boolean drawEdge = false;
/*  830 */           for (GuiButton button : this.field_146292_n) {
/*  831 */             TextureButton tb = (TextureButton)button;
/*  832 */             if (tb.getType().equals(TextureButton.TypeEnum.Slot) && tb.func_146115_a() && tb.getAttachmentType() == attachment) {
/*  833 */               drawEdge = true;
/*      */             }
/*      */           } 
/*      */           
/*  837 */           if (attachmentModel != null) {
/*      */             
/*  839 */             if (drawEdge) {
/*      */               
/*  841 */               GL11.glEnable(10754);
/*  842 */               GlStateManager.func_187409_d(1032, 6913);
/*  843 */               GlStateManager.func_187441_d(10.0F);
/*  844 */               GlStateManager.func_179094_E();
/*      */ 
/*      */ 
/*      */               
/*  848 */               GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
/*  849 */               GlStateManager.func_179140_f();
/*  850 */               GlStateManager.func_179090_x();
/*  851 */               GlStateManager.func_179097_i();
/*  852 */               renderAttachModel(attachmentModel, attachment, model, attachmentType, worldScale, itemStack, skinId, path);
/*  853 */               GlStateManager.func_179121_F();
/*  854 */               GlStateManager.func_179126_j();
/*  855 */               GlStateManager.func_179145_e();
/*  856 */               GlStateManager.func_179098_w();
/*  857 */               GlStateManager.func_187409_d(1032, 6914);
/*      */             } 
/*  859 */             renderAttachModel(attachmentModel, attachment, model, attachmentType, worldScale, itemStack, skinId, path);
/*      */           } 
/*      */         } 
/*  862 */         GlStateManager.func_179121_F();
/*      */       } 
/*  864 */       if (!hasScopeAttachment) {
/*  865 */         model.renderPart("defaultScopeModel", worldScale);
/*      */       }
/*  867 */       GlStateManager.func_179121_F();
/*      */     } 
/*      */ 
/*      */     
/*  871 */     RenderHelper.func_74518_a();
/*      */   }
/*      */   public void renderAttachModel(ModelAttachment attachmentModel, AttachmentPresetEnum attachment, ModelGun model, AttachmentType attachmentType, float worldScale, ItemStack itemStack, int skinId, String path) {
/*  874 */     Vector3f adjustedScale = new Vector3f(attachmentModel.config.extra.modelScale, attachmentModel.config.extra.modelScale, attachmentModel.config.extra.modelScale);
/*      */     
/*  876 */     GL11.glScalef(adjustedScale.x, adjustedScale.y, adjustedScale.z);
/*  877 */     if (model.config.attachments.attachmentPointMap != null && model.config.attachments.attachmentPointMap
/*  878 */       .size() >= 1 && 
/*  879 */       model.config.attachments.attachmentPointMap.containsKey(attachment)) {
/*      */       
/*  881 */       Vector3f attachmentVecTranslate = ((ArrayList<Vector3f>)model.config.attachments.attachmentPointMap.get(attachment)).get(0);
/*      */       
/*  883 */       Vector3f attachmentVecRotate = ((ArrayList<Vector3f>)model.config.attachments.attachmentPointMap.get(attachment)).get(1);
/*  884 */       GL11.glTranslatef(attachmentVecTranslate.x / attachmentModel.config.extra.modelScale, attachmentVecTranslate.y / attachmentModel.config.extra.modelScale, attachmentVecTranslate.z / attachmentModel.config.extra.modelScale);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  889 */       GL11.glRotatef(attachmentVecRotate.x, 1.0F, 0.0F, 0.0F);
/*  890 */       GL11.glRotatef(attachmentVecRotate.y, 0.0F, 1.0F, 0.0F);
/*  891 */       GL11.glRotatef(attachmentVecRotate.z, 0.0F, 0.0F, 1.0F);
/*      */     } 
/*      */     
/*  894 */     if (model.config.attachments.positionPointMap != null) {
/*  895 */       for (String internalName : model.config.attachments.positionPointMap.keySet()) {
/*  896 */         if (internalName.equals(attachmentType.internalName)) {
/*      */           
/*  898 */           Vector3f trans = ((ArrayList<Vector3f>)model.config.attachments.positionPointMap.get(internalName)).get(0);
/*      */           
/*  900 */           Vector3f rot = ((ArrayList<Vector3f>)model.config.attachments.positionPointMap.get(internalName)).get(1);
/*  901 */           GL11.glTranslatef(trans.x / attachmentModel.config.extra.modelScale * worldScale, trans.y / attachmentModel.config.extra.modelScale * worldScale, trans.z / attachmentModel.config.extra.modelScale * worldScale);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  906 */           GL11.glRotatef(rot.x, 1.0F, 0.0F, 0.0F);
/*  907 */           GL11.glRotatef(rot.y, 0.0F, 1.0F, 0.0F);
/*  908 */           GL11.glRotatef(rot.z, 0.0F, 0.0F, 1.0F);
/*      */         } 
/*      */       } 
/*      */     }
/*  912 */     skinId = 0;
/*  913 */     if (itemStack.func_77942_o() && 
/*  914 */       itemStack.func_77978_p().func_74764_b("skinId")) {
/*  915 */       skinId = itemStack.func_77978_p().func_74762_e("skinId");
/*      */     }
/*      */     
/*  918 */     if (attachmentType.sameTextureAsGun) {
/*  919 */       ClientRenderHooks.customRenderers[3].bindTexture("guns", path);
/*      */     } else {
/*      */       
/*  922 */       path = (skinId > 0) ? attachmentType.modelSkins[skinId].getSkin() : attachmentType.modelSkins[0].getSkin();
/*  923 */       ClientRenderHooks.customRenderers[3].bindTexture("attachments", path);
/*      */     } 
/*  925 */     attachmentModel.renderAttachment(worldScale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderEnhancedModel(EntityLivingBase entitylivingbaseIn, BaseType type, double scale, ItemStack itemstack, double sFactor, ScaledResolution scaledresolution, float partialTicks) {
/*  933 */     GunType gunType = (GunType)type;
/*  934 */     ModelEnhancedGun model = (ModelEnhancedGun)type.enhancedModel;
/*  935 */     GunEnhancedRenderConfig config = (GunEnhancedRenderConfig)gunType.enhancedModel.config;
/*      */     
/*  937 */     if (config.animations.containsKey(AnimationType.DEFAULT)) {
/*  938 */       boolean glowTxtureMode = ObjModelRenderer.glowTxtureMode;
/*  939 */       ObjModelRenderer.glowTxtureMode = true;
/*      */       
/*  941 */       RenderGunEnhanced rge = (RenderGunEnhanced)ClientRenderHooks.customRenderers[0];
/*      */ 
/*      */       
/*  944 */       rge.getClientController().updateCurrentItem();
/*  945 */       (rge.getClientController()).DRAW = 1.0D;
/*      */       
/*  947 */       rge.getClientController().updateActionAndTime();
/*  948 */       model.updateAnimation(rge.getClientController().getTime(), true);
/*  949 */       HashSet<String> exceptParts = new HashSet<>();
/*  950 */       exceptParts.addAll(config.defaultHidePart);
/*      */ 
/*      */       
/*  953 */       EnhancedStateMachine anim = ClientRenderHooks.getEnhancedAnimMachine(entitylivingbaseIn);
/*      */ 
/*      */ 
/*      */       
/*  957 */       ItemAttachment sight = null;
/*  958 */       float renderInsideGunOffset = 5.0F;
/*  959 */       if (GunType.getAttachment(itemstack, AttachmentPresetEnum.Sight) != null) {
/*  960 */         sight = (ItemAttachment)GunType.getAttachment(itemstack, AttachmentPresetEnum.Sight).func_77973_b();
/*  961 */         GunEnhancedRenderConfig.Attachment sightConfig = (GunEnhancedRenderConfig.Attachment)config.attachment.get(sight.type.internalName);
/*  962 */         if (sightConfig != null) {
/*      */           
/*  964 */           float ads = (float)(rge.getClientController()).ADS;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  969 */           renderInsideGunOffset = sightConfig.renderInsideGunOffset;
/*      */         } 
/*      */       } 
/*      */       
/*  973 */       for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/*  974 */         ItemStack itemStack = GunType.getAttachment(itemstack, attachment);
/*  975 */         if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*  976 */           AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/*  977 */           String binding = "gunModel";
/*  978 */           if (config.attachmentGroup.containsKey(attachment.typeName) && 
/*  979 */             ((GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName)).hidePart != null) {
/*  980 */             exceptParts.addAll(((GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName)).hidePart);
/*      */           }
/*      */           
/*  983 */           if (config.attachment.containsKey(attachmentType.internalName) && 
/*  984 */             ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).hidePart != null) {
/*  985 */             exceptParts.addAll(((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).hidePart);
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  991 */       for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/*  992 */         ItemStack itemStack = GunType.getAttachment(itemstack, attachment);
/*  993 */         if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*  994 */           AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/*  995 */           String binding = "gunModel";
/*  996 */           if (config.attachmentGroup.containsKey(attachment.typeName) && 
/*  997 */             ((GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName)).showPart != null) {
/*  998 */             exceptParts.removeAll(((GunEnhancedRenderConfig.AttachmentGroup)config.attachmentGroup.get(attachment.typeName)).showPart);
/*      */           }
/*      */           
/* 1001 */           if (config.attachment.containsKey(attachmentType.internalName) && 
/* 1002 */             ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).showPart != null) {
/* 1003 */             exceptParts.removeAll(((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).showPart);
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1009 */       exceptParts.addAll(RenderGunEnhanced.DEFAULT_EXCEPT);
/*      */       
/* 1011 */       HashSet<String> exceptPartsRendering = exceptParts;
/* 1012 */       int skinId = 0;
/* 1013 */       if (itemstack.func_77942_o() && 
/* 1014 */         itemstack.func_77978_p().func_74764_b("skinId")) {
/* 1015 */         skinId = itemstack.func_77978_p().func_74762_e("skinId");
/*      */       }
/*      */ 
/*      */       
/* 1019 */       String gunPath = (skinId > 0) ? gunType.modelSkins[skinId].getSkin() : gunType.modelSkins[0].getSkin();
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
/* 1032 */       GlStateManager.func_179126_j();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1037 */       GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
/* 1038 */       RenderHelper.func_74519_b();
/* 1039 */       GlStateManager.func_179114_b(-180.0F, 1.0F, 0.0F, 0.0F);
/* 1040 */       GlStateManager.func_179091_B();
/*      */       
/* 1042 */       GlStateManager.func_179109_b(0.0F, 0.0F, 600.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1047 */       scale = (70.0F * config.extra.modelGuiScale) * sFactor / scaledresolution.func_78325_e();
/* 1048 */       double centerOffsetY = config.extra.modelGuiRotateCenter.x;
/* 1049 */       double centerOffsetX = config.extra.modelGuiRotateCenter.y;
/*      */       
/* 1051 */       GlStateManager.func_179109_b((scaledresolution
/* 1052 */           .func_78326_a() / 2), (scaledresolution
/* 1053 */           .func_78328_b() / 2), 0.0F);
/* 1054 */       GlStateManager.func_179139_a(scale, scale, -scale);
/*      */       
/* 1056 */       GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
/* 1057 */       GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
/*      */       
/* 1059 */       GlStateManager.func_179114_b((float)this.rotateY, 0.0F, 1.0F, 0.0F);
/*      */       
/* 1061 */       GlStateManager.func_179114_b((float)this.rotateZ, 1.0F, 0.0F, 0.0F);
/* 1062 */       GlStateManager.func_179137_b(centerOffsetY, centerOffsetX, 0.0D);
/*      */       
/* 1064 */       ItemAttachment sightRendering = sight;
/* 1065 */       float worldScale = 1.0F;
/* 1066 */       boolean applySprint = false;
/* 1067 */       rge.blendTransform(model, itemstack, false, rge.getClientController().getTime(), rge
/* 1068 */           .getClientController().getSprintTime(), (float)(rge.getClientController()).SPRINT, "sprint_righthand", applySprint, true, () -> {
/*      */             if (sightRendering != null) {
/*      */               String binding = "gunModel";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               if (config.attachment.containsKey(sightRendering.type.internalName)) {
/*      */                 binding = ((GunEnhancedRenderConfig.Attachment)config.attachment.get(sightRendering.type.internalName)).binding;
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               model.applyGlobalTransformToOther(binding, ());
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             rge.bindTexture("guns", gunPath);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             model.renderPartExcept(exceptPartsRendering);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             WeaponFireMode fireMode = GunType.getFireMode(itemstack);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             if (fireMode == WeaponFireMode.SEMI) {
/*      */               model.renderPart("selector_semi");
/*      */             } else if (fireMode == WeaponFireMode.FULL) {
/*      */               model.renderPart("selector_full");
/*      */             } else if (fireMode == WeaponFireMode.BURST) {
/*      */               model.renderPart("selector_brust");
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             boolean flagDynamicAmmoRendered = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             ItemStack stackAmmo = new ItemStack(itemstack.func_77978_p().func_74775_l("ammo"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             ItemStack orignalAmmo = stackAmmo;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             stackAmmo = rge.getClientController().getRenderAmmo(stackAmmo);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             ItemStack renderAmmo = stackAmmo;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             ItemStack prognosisAmmo = ClientTickHandler.reloadEnhancedPrognosisAmmoRendering;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             ItemStack bulletStack = ItemStack.field_190927_a;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             int currentAmmoCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             VarBoolean defaultBulletFlag = new VarBoolean();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             defaultBulletFlag.b = true;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             boolean defaultAmmoFlag = true;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             if (gunType.acceptedBullets != null) {
/*      */               currentAmmoCount = itemstack.func_77978_p().func_74762_e("ammocount");
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               if (anim.reloading) {
/*      */                 currentAmmoCount += anim.getAmmoCountOffset(true);
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               bulletStack = new ItemStack(itemstack.func_77978_p().func_74775_l("bullet"));
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               if (anim.reloading) {
/*      */                 bulletStack = ClientProxy.gunEnhancedRenderer.getClientController().getRenderAmmo(bulletStack);
/*      */               }
/*      */             } else {
/*      */               Integer currentMagcount = null;
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               if (stackAmmo != null && !stackAmmo.func_190926_b() && stackAmmo.func_77942_o()) {
/*      */                 if (stackAmmo.func_77978_p().func_74764_b("magcount")) {
/*      */                   currentMagcount = Integer.valueOf(stackAmmo.func_77978_p().func_74762_e("magcount"));
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 currentAmmoCount = ReloadHelper.getBulletOnMag(stackAmmo, currentMagcount);
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 bulletStack = new ItemStack(stackAmmo.func_77978_p().func_74775_l("bullet"));
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             int currentAmmoCountRendering = currentAmmoCount;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             if (bulletStack != null && bulletStack.func_77973_b() instanceof ItemBullet) {
/*      */               BulletType bulletType = ((ItemBullet)bulletStack.func_77973_b()).type;
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               if (bulletType.isDynamicBullet && bulletType.model != null) {
/*      */                 int skinIdBullet = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 if (bulletStack.func_77942_o() && bulletStack.func_77978_p().func_74764_b("skinId")) {
/*      */                   skinIdBullet = bulletStack.func_77978_p().func_74762_e("skinId");
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 if (bulletType.sameTextureAsGun) {
/*      */                   rge.bindTexture("guns", gunPath);
/*      */                 } else {
/*      */                   String pathAmmo = (skinIdBullet > 0) ? bulletType.modelSkins[skinIdBullet].getSkin() : bulletType.modelSkins[0].getSkin();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   rge.bindTexture("bullets", pathAmmo);
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 int bullet = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 while (bullet < currentAmmoCount && bullet < 256) {
/*      */                   int renderBullet = bullet;
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   model.applyGlobalTransformToOther("bulletModel_" + bullet, ());
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   bullet++;
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 model.applyGlobalTransformToOther("bulletModel", ());
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 defaultBulletFlag.b = false;
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             ItemStack[] ammoList = { stackAmmo, orignalAmmo, prognosisAmmo };
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             String[] binddings = { "ammoModel", "ammoModelPre", "ammoModelPost" };
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             for (int x = 0; x < 3; x++) {
/*      */               ItemStack stackAmmoX = ammoList[x];
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               if (stackAmmoX != null && !stackAmmoX.func_190926_b()) {
/*      */                 if (model.existPart(binddings[x])) {
/*      */                   if (stackAmmoX.func_77973_b() instanceof ItemAmmo) {
/*      */                     ItemAmmo itemAmmo = (ItemAmmo)stackAmmoX.func_77973_b();
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*      */                     AmmoType ammoType = itemAmmo.type;
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*      */                     if (ammoType.isDynamicAmmo && ammoType.model != null) {
/*      */                       int skinIdAmmo = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*      */                       int baseAmmoCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*      */                       if (stackAmmoX.func_77942_o()) {
/*      */                         if (stackAmmoX.func_77978_p().func_74764_b("skinId")) {
/*      */                           skinIdAmmo = stackAmmoX.func_77978_p().func_74762_e("skinId");
/*      */                         }
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*      */                         if (stackAmmoX.func_77978_p().func_74764_b("magcount")) {
/*      */                           baseAmmoCount = (stackAmmoX.func_77978_p().func_74762_e("magcount") - 1) * ammoType.ammoCapacity;
/*      */                         }
/*      */                       } 
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*      */                       int baseAmmoCountRendering = baseAmmoCount;
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*      */                       if (ammoType.sameTextureAsGun) {
/*      */                         rge.bindTexture("guns", gunPath);
/*      */                       } else {
/*      */                         String pathAmmo = (skinIdAmmo > 0) ? ammoType.modelSkins[skinIdAmmo].getSkin() : ammoType.modelSkins[0].getSkin();
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*      */                         rge.bindTexture("ammo", pathAmmo);
/*      */                       } 
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*      */                       if (rge.getClientController().shouldRenderAmmo()) {
/*      */                         model.applyGlobalTransformToOther("ammoModel", ());
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*      */                         model.applyGlobalTransformToOther("bulletModel", ());
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*      */                         flagDynamicAmmoRendered = true;
/*      */ 
/*      */ 
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
/*      */ 
/*      */             
/*      */             rge.bindTexture("guns", gunPath);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             if (defaultBulletFlag.b) {
/*      */               int bullet = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               while (bullet < currentAmmoCount && bullet < 256) {
/*      */                 model.renderPart("bulletModel_" + bullet);
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 bullet++;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               model.renderPart("bulletModel");
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             if (rge.getClientController().shouldRenderAmmo() && defaultAmmoFlag) {
/*      */               model.renderPart("ammoModel");
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/*      */               ItemStack itemStack = GunType.getAttachment(itemstack, attachment);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*      */                 AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 ModelAttachment attachmentModel = (ModelAttachment)attachmentType.model;
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 if (!ScopeUtils.isIndsideGunRendering || attachment != AttachmentPresetEnum.Sight || (config.attachment.containsKey(attachmentType.internalName) && ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).renderInsideSightModel)) {
/*      */                   if (attachmentModel != null) {
/*      */                     String binding = "gunModel";
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*      */                     if (config.attachment.containsKey(attachmentType.internalName)) {
/*      */                       binding = ((GunEnhancedRenderConfig.Attachment)config.attachment.get(attachmentType.internalName)).binding;
/*      */                     }
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*      */                     model.applyGlobalTransformToOther(binding, ());
/*      */                   } 
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           });
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1483 */       if (sightRendering != null && 
/* 1484 */         !ScopeUtils.isIndsideGunRendering) {
/* 1485 */         if (!OptifineHelper.isShadersEnabled()) {
/* 1486 */           rge.copyMirrorTexture();
/* 1487 */           ClientProxy.scopeUtils.renderPostScope(partialTicks, false, true, true, 1.0F);
/* 1488 */           rge.eraseScopeGlassDepth(sightRendering.type, (ModelAttachment)sightRendering.type.model, 
/* 1489 */               ((rge.getClientController()).ADS > 0.0D), 1.0F);
/* 1490 */         } else if (!sightRendering.type.sight.modeType.isPIP) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1497 */           GL11.glPushAttrib(2048);
/*      */           
/* 1499 */           GL11.glDepthRange(0.0D, 1.0D);
/* 1500 */           rge.copyMirrorTexture();
/* 1501 */           ClientProxy.scopeUtils.renderPostScope(partialTicks, true, false, true, 1.0F);
/* 1502 */           rge.eraseScopeGlassDepth(sightRendering.type, (ModelAttachment)sightRendering.type.model, 
/* 1503 */               ((rge.getClientController()).ADS > 0.0D), 1.0F);
/* 1504 */           rge.writeScopeSoildDepth(((rge.getClientController()).ADS > 0.0D));
/*      */           
/* 1506 */           GL11.glPopAttrib();
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1513 */       (rge.getClientController()).ADS = 0.0D;
/* 1514 */       ObjModelRenderer.glowTxtureMode = glowTxtureMode;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawLine(double x1, double y1, double x2, double y2) {
/* 1520 */     GL11.glBegin(1);
/* 1521 */     GL11.glVertex3d(x1, y1, 0.0D);
/* 1522 */     GL11.glVertex3d(x2, y2, 0.0D);
/* 1523 */     GL11.glEnd();
/*      */   }
/*      */   
/*      */   public void drawPoint(double x, double y) {
/* 1527 */     GL11.glBegin(0);
/* 1528 */     GL11.glVertex3d(x, y, 0.0D);
/* 1529 */     GL11.glEnd();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_146284_a(GuiButton button) throws IOException {
/* 1534 */     if (clickOnce)
/*      */       return; 
/* 1536 */     clickOnce = true;
/* 1537 */     ScaledResolution scaledresolution = new ScaledResolution(Minecraft.func_71410_x());
/* 1538 */     if (button instanceof TextureButton) {
/* 1539 */       TextureButton tButton = (TextureButton)button;
/* 1540 */       if (tButton.getType().equals(TextureButton.TypeEnum.SideButton)) {
/* 1541 */         if (tButton.state == 0) {
/* 1542 */           this.selectedSideButton = null;
/* 1543 */           tButton.state = -1;
/* 1544 */           func_73866_w_();
/*      */         } else {
/* 1546 */           if (this.selectedSideButton != null) {
/* 1547 */             func_73866_w_();
/*      */           }
/* 1549 */           this.selectedSideButton = getButton(tButton.field_146127_k);
/* 1550 */           this.selectedSideButton.state = 0;
/* 1551 */           joinSubPageButtons((EntityPlayer)this.field_146297_k.field_71439_g, scaledresolution);
/*      */         }
/*      */       
/* 1554 */       } else if (tButton.getType().equals(TextureButton.TypeEnum.SubSlot)) {
/*      */         
/* 1556 */         if (tButton.field_146127_k == -1) {
/* 1557 */           ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunUnloadAttachment(tButton.getAttachmentType().getName(), false));
/* 1558 */           func_146284_a(this.selectedSideButton);
/*      */         } else {
/* 1560 */           getClass(); int inventoryID = tButton.field_146127_k - 100;
/* 1561 */           if (GunType.getAttachment(this.currentModify, tButton.getAttachmentType()) != this.field_146297_k.field_71439_g.field_71071_by.func_70301_a(inventoryID)) {
/* 1562 */             ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunAddAttachment(inventoryID));
/* 1563 */             func_146284_a(this.selectedSideButton);
/*      */           }
/*      */         
/*      */         }
/*      */       
/* 1568 */       } else if (button == QUITBUTTON) {
/* 1569 */         ModularWarfare.PROXY.playSound(new MWSound(this.field_146297_k.field_71439_g.func_180425_c(), "attachment.open", 1.0F, 1.0F));
/* 1570 */         Minecraft.func_71410_x().func_147108_a(null);
/* 1571 */       } else if (button == PAGEBUTTON) {
/* 1572 */         PAGEBUTTON.state = (PAGEBUTTON.state == -1) ? 0 : -1;
/* 1573 */         updateItemModifying();
/* 1574 */         func_73866_w_();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_146281_b() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_146274_d() throws IOException {
/* 1587 */     super.func_146274_d();
/* 1588 */     updateRotate();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRotate() {
/* 1594 */     int i = Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c;
/* 1595 */     int j = this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1;
/* 1596 */     int k = Mouse.getEventButton();
/*      */ 
/*      */ 
/*      */     
/* 1600 */     if (Mouse.getEventButton() == 0) {
/* 1601 */       if (Mouse.getEventButtonState()) {
/* 1602 */         this.leftClicked = true;
/*      */       } else {
/* 1604 */         this.leftClicked = false;
/*      */       } 
/*      */     }
/* 1607 */     if (this.leftClicked) {
/* 1608 */       this.rotateY += Mouse.getEventDX() * 0.2D;
/* 1609 */       this.rotateZ += Mouse.getEventDY() * 0.2D;
/*      */     } 
/* 1611 */     this.rotateZ = Math.max(-30.0D, this.rotateZ);
/* 1612 */     this.rotateZ = Math.min(30.0D, this.rotateZ);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_73864_a(int x, int y, int mouseEvent) throws IOException {
/* 1617 */     super.func_73864_a(x, y, mouseEvent);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_146286_b(int x, int y, int mouseEvent) {
/* 1622 */     super.func_146286_b(x, y, mouseEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_73869_a(char eventChar, int eventKey) {
/* 1628 */     if (eventKey == 1 || eventKey == 50) {
/*      */       try {
/* 1630 */         func_146284_a(QUITBUTTON);
/* 1631 */       } catch (IOException e) {
/*      */         
/* 1633 */         e.printStackTrace();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_73868_f() {
/* 1640 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\gui\GuiGunModify.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */