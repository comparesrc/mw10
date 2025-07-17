/*     */ package com.modularwarfare.client.gui.customization;
/*     */ 
/*     */ import com.modularwarfare.api.IMWModel;
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderParameters;
/*     */ import com.modularwarfare.client.fpp.enhanced.models.EnhancedModel;
/*     */ import com.modularwarfare.client.gui.api.GuiMWContainer;
/*     */ import com.modularwarfare.client.gui.api.GuiMWScreen;
/*     */ import com.modularwarfare.client.gui.api.GuiUtils;
/*     */ import com.modularwarfare.client.gui.customization.containers.ContainerGunParts;
/*     */ import com.modularwarfare.client.shader.ProjectionHelper;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.WeaponAnimationType;
/*     */ import com.modularwarfare.loader.MWModelBase;
/*     */ import com.modularwarfare.utility.ColorUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GuiMainScreen extends GuiMWScreen {
/*     */   public ItemStack gunStack;
/*     */   public ItemGun gun;
/*  31 */   public ContainerGunParts containerGunParts = new ContainerGunParts(0, 10, 10, 100, 170, (GuiScreen)this);
/*  32 */   private float zoom = 1.0F;
/*     */   
/*  34 */   private static final ProjectionHelper projectionHelper = new ProjectionHelper();
/*     */   
/*     */   public GuiMainScreen(ItemStack gunStack, ItemGun gun) {
/*  37 */     this.gunStack = gunStack;
/*  38 */     this.gun = gun;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  43 */     super.func_73866_w_();
/*  44 */     (Minecraft.func_71410_x()).field_71460_t.func_175069_a(new ResourceLocation("modularwarfare:shaders/post/blurex.json"));
/*  45 */     addContainer((GuiMWContainer)this.containerGunParts);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146281_b() {
/*  50 */     this.containerList.forEach(GuiMWContainer::onClose);
/*  51 */     (Minecraft.func_71410_x()).field_71460_t.func_181022_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146274_d() {
/*  56 */     super.func_146274_d();
/*     */     
/*  58 */     int dWheel = Mouse.getEventDWheel();
/*  59 */     if (dWheel != 0) {
/*  60 */       float inc = this.zoom + (dWheel / 120) * 0.01F;
/*  61 */       this.zoom = MathHelper.func_76131_a(inc, 1.0F, 1.75F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Vec3d getMouseVector(float z) {
/*  66 */     return projectionHelper.unproject(Mouse.getX(), Mouse.getY(), z);
/*     */   }
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*     */     EnhancedModel enhancedModel;
/*  71 */     super.func_73863_a(mouseX, mouseY, partialTicks);
/*     */     
/*  73 */     float MENU_ROTATION = ((this.field_146294_l - mouseX) / 8 - 80);
/*  74 */     float MENU_ROTATION_2 = ((this.field_146295_m - mouseY) / 12);
/*  75 */     float angleY = (float)((-10.0F + -MENU_ROTATION / 2.0F) + 9.0D * Math.sin((RenderParameters.SMOOTH_SWING / 80.0F)));
/*  76 */     float angleX = (float)((-MENU_ROTATION_2 / 2.0F + 10.0F) + 6.0D * Math.cos((RenderParameters.SMOOTH_SWING / 80.0F)));
/*     */     
/*  78 */     float backgroundGradientSwing = (float)((Math.sin((RenderParameters.SMOOTH_SWING / 80.0F)) + 1.0D) * 100.0D);
/*     */ 
/*     */     
/*  81 */     GlStateManager.func_179094_E();
/*  82 */     drawContainers(mouseX, mouseY, partialTicks);
/*  83 */     GuiUtils.renderRectWithGradient(0, 0, this.field_146294_l + (int)backgroundGradientSwing, this.field_146295_m + (int)backgroundGradientSwing, 863204211, 1711276032, 0.0D);
/*  84 */     GuiUtils.renderCenteredTextScaledWithOutline("- Weapon Modding -", this.field_146294_l / 2, 15, 16777215, -14277082, 2.5D);
/*  85 */     GuiUtils.renderCenteredTextScaled(this.gun.type.displayName + " - " + this.gun.type.weaponType.typeName, this.field_146294_l / 2, 40, 16777215, 1.0D);
/*  86 */     GuiUtils.renderTextScaled("© Copyright - ModularWarfare", 3, this.field_146295_m - 7, -1, 0.5D);
/*  87 */     GuiUtils.renderTextScaled("https://modularmods.net", this.field_146294_l - 65, this.field_146295_m - 7, -1, 0.5D);
/*  88 */     GuiUtils.renderTextScaledWithOutline("EXIT >", this.field_146294_l - 90, this.field_146295_m - 45, -1, -14277082, 2.5D);
/*  89 */     GlStateManager.func_179121_F();
/*     */ 
/*     */     
/*  92 */     GlStateManager.func_179094_E();
/*     */     
/*  94 */     GL11.glEnable(32823);
/*  95 */     GL11.glPolygonOffset(1.0F, -1000000.0F);
/*     */     
/*  97 */     GlStateManager.func_179094_E();
/*     */ 
/*     */     
/* 100 */     GlStateManager.func_179091_B();
/* 101 */     GlStateManager.func_179141_d();
/* 102 */     GlStateManager.func_179092_a(516, 0.1F);
/* 103 */     RenderHelper.func_74519_b();
/* 104 */     GlStateManager.func_179145_e();
/* 105 */     GlStateManager.func_179147_l();
/* 106 */     GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 107 */     GlStateManager.func_179126_j();
/*     */     
/* 109 */     GL11.glTranslatef(500.0F, 210.0F, 50.0F);
/* 110 */     GlStateManager.func_179152_a(-1.0F, 1.0F, 1.0F);
/* 111 */     GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
/*     */ 
/*     */     
/* 114 */     GlStateManager.func_179114_b(angleX, 1.0F, 0.0F, 0.0F);
/* 115 */     GlStateManager.func_179114_b(angleY / 3.0F, 0.0F, 1.0F, 0.0F);
/*     */     
/* 117 */     GlStateManager.func_179152_a(this.zoom, this.zoom, 1.0F);
/*     */ 
/*     */     
/* 120 */     GlStateManager.func_179152_a(1.75F, 1.75F, 1.0F);
/* 121 */     GlStateManager.func_179152_a(200.0F, 200.0F, 200.0F);
/*     */     
/* 123 */     IMWModel model = null;
/* 124 */     if (this.gun.type.animationType.equals(WeaponAnimationType.BASIC)) {
/* 125 */       MWModelBase mWModelBase = this.gun.type.model;
/*     */     } else {
/* 127 */       enhancedModel = this.gun.type.enhancedModel;
/*     */     } 
/*     */     
/* 130 */     for (String part : this.containerGunParts.partsSets.keySet()) {
/* 131 */       if (((Boolean)this.containerGunParts.partsSets.get(part)).booleanValue()) {
/* 132 */         ClientRenderHooks.customRenderers[this.gun.type.id].bindTexture(this.gun.type.getAssetDir(), this.gun.type.modelSkins[0].getSkin());
/* 133 */         if (part.equalsIgnoreCase("gunModel")) {
/* 134 */           GlStateManager.func_179094_E();
/* 135 */           GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.15F);
/* 136 */           GlStateManager.func_179132_a(false);
/* 137 */           GlStateManager.func_179147_l();
/* 138 */           GlStateManager.func_179112_b(770, 771);
/* 139 */           GlStateManager.func_179092_a(516, 0.003921569F);
/*     */           
/* 141 */           GL11.glLineWidth(5.0F);
/* 142 */           ColorUtil colorUtil = new ColorUtil(255, 255, 255);
/* 143 */           OutlineUtils.setColor((Color)colorUtil);
/* 144 */           enhancedModel.renderPart(part, 0.0625F);
/* 145 */           OutlineUtils.renderOne(5.0F);
/* 146 */           enhancedModel.renderPart(part, 0.0625F);
/* 147 */           OutlineUtils.renderTwo();
/* 148 */           enhancedModel.renderPart(part, 0.0625F);
/* 149 */           OutlineUtils.renderThree();
/* 150 */           OutlineUtils.renderFour();
/* 151 */           OutlineUtils.setColor((Color)colorUtil);
/* 152 */           enhancedModel.renderPart(part, 0.0625F);
/* 153 */           OutlineUtils.renderFive();
/* 154 */           OutlineUtils.setColor(Color.WHITE);
/* 155 */           enhancedModel.renderPart(part, 0.0625F);
/*     */           
/* 157 */           GlStateManager.func_179084_k();
/* 158 */           GlStateManager.func_179092_a(516, 0.1F);
/* 159 */           GlStateManager.func_179121_F();
/* 160 */           GlStateManager.func_179132_a(true);
/*     */         } 
/* 162 */         enhancedModel.renderPart(part, 0.065625F);
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     GlStateManager.func_179118_c();
/* 167 */     GlStateManager.func_179101_C();
/* 168 */     GlStateManager.func_179140_f();
/*     */ 
/*     */     
/* 171 */     GlStateManager.func_179121_F();
/*     */ 
/*     */     
/* 174 */     GL11.glPolygonOffset(1.0F, 1000000.0F);
/* 175 */     GL11.glDisable(32823);
/* 176 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/* 182 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\gui\customization\GuiMainScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */