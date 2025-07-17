/*     */ package com.modularwarfare.client.renderers;
/*     */ 
/*     */ import com.modularwarfare.client.ClientProxy;
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.RenderType;
/*     */ import com.modularwarfare.client.fpp.enhanced.models.ModelEnhancedGun;
/*     */ import com.modularwarfare.client.model.ModelAttachment;
/*     */ import com.modularwarfare.client.model.ModelGun;
/*     */ import com.modularwarfare.common.entity.item.EntityItemLoot;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.common.guns.AttachmentType;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.WeaponAnimationType;
/*     */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.client.ForgeHooksClient;
/*     */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ 
/*     */ public class RenderItemLoot
/*     */   extends Render<EntityItemLoot>
/*     */ {
/*  46 */   public static final Factory FACTORY = new Factory();
/*     */   
/*     */   private final RenderItem itemRenderer;
/*     */   
/*     */   private Random random;
/*     */   
/*     */   public RenderItemLoot(RenderManager renderManagerIn, RenderItem p_i46167_2_) {
/*  53 */     super(renderManagerIn);
/*  54 */     this.random = new Random();
/*  55 */     this.itemRenderer = p_i46167_2_;
/*  56 */     this.field_76989_e = 0.0F;
/*  57 */     this.field_76987_f = 0.0F;
/*     */   }
/*     */   
/*     */   private int transformModelCount(EntityItemLoot itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_) {
/*  61 */     ItemStack itemstack = itemIn.getItem();
/*  62 */     Item item = itemstack.func_77973_b();
/*  63 */     if (item == null) {
/*  64 */       return 0;
/*     */     }
/*  66 */     boolean flag = p_177077_9_.func_177556_c();
/*  67 */     int i = getModelCount(itemstack);
/*  68 */     float f1 = shouldBob() ? (MathHelper.func_76126_a((itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F) : 0.0F;
/*  69 */     float f2 = (p_177077_9_.func_177552_f().func_181688_b(ItemCameraTransforms.TransformType.GROUND)).field_178363_d.y;
/*  70 */     GlStateManager.func_179109_b((float)p_177077_2_, (float)p_177077_4_, (float)p_177077_6_);
/*  71 */     if (flag || this.field_76990_c.field_78733_k != null) {
/*  72 */       IBlockState bsDown = itemIn.field_70170_p.func_180495_p(new BlockPos(itemIn.field_70165_t, itemIn.field_70163_u - 0.25D, itemIn.field_70161_v));
/*  73 */       boolean inWater = (itemIn.func_70090_H() || bsDown.func_177230_c() instanceof net.minecraft.block.BlockLiquid || bsDown.func_177230_c() instanceof net.minecraftforge.fluids.IFluidBlock);
/*  74 */       if (!itemIn.field_70122_E && !inWater) {
/*  75 */         float f3 = ((itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * 57.295776F;
/*  76 */         GlStateManager.func_179114_b(itemIn.hoverStart * 360.0F, 0.0F, 1.0F, 0.0F);
/*     */       } else {
/*  78 */         GlStateManager.func_179114_b(itemIn.hoverStart * 360.0F, 0.0F, 1.0F, 0.0F);
/*     */       } 
/*     */     } 
/*  81 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*  82 */     return i;
/*     */   }
/*     */   
/*     */   protected int getModelCount(ItemStack stack) {
/*  86 */     int i = 1;
/*  87 */     if (stack.func_190916_E() > 48) {
/*  88 */       i = 5;
/*  89 */     } else if (stack.func_190916_E() > 32) {
/*  90 */       i = 4;
/*  91 */     } else if (stack.func_190916_E() > 16) {
/*  92 */       i = 3;
/*  93 */     } else if (stack.func_190916_E() > 1) {
/*  94 */       i = 2;
/*     */     } 
/*  96 */     return i;
/*     */   }
/*     */   
/*     */   public void doRender(EntityItemLoot entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 100 */     boolean glow = ObjModelRenderer.glowTxtureMode;
/* 101 */     ObjModelRenderer.glowTxtureMode = true;
/* 102 */     GlStateManager.func_179103_j(7425);
/* 103 */     ItemStack itemstack = entity.getItem();
/* 104 */     if (itemstack.func_77973_b() instanceof ItemGun) {
/* 105 */       if (((ItemGun)itemstack.func_77973_b()).type.animationType == WeaponAnimationType.BASIC) {
/* 106 */         GlStateManager.func_179092_a(516, 0.1F);
/* 107 */         GlStateManager.func_179147_l();
/* 108 */         RenderHelper.func_74519_b();
/*     */ 
/*     */ 
/*     */         
/* 112 */         GlStateManager.func_179103_j(7425);
/* 113 */         GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 114 */         GlStateManager.func_179094_E();
/* 115 */         GlStateManager.func_179109_b((float)x, (float)y, (float)z);
/* 116 */         GlStateManager.func_179094_E();
/* 117 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 118 */         GlStateManager.func_179137_b(0.15D, 0.2D, -0.08D);
/* 119 */         double height = 0.2D;
/* 120 */         GlStateManager.func_179137_b(0.0D, height, 0.0D);
/* 121 */         GlStateManager.func_179114_b(entity.field_70125_A, 1.0F, 0.0F, 0.0F);
/* 122 */         GlStateManager.func_179137_b(0.0D, -height, 0.0D);
/*     */         
/* 124 */         ItemGun gun = (ItemGun)itemstack.func_77973_b();
/* 125 */         GunType gunType = gun.type;
/* 126 */         ModelGun model = (ModelGun)gunType.model;
/* 127 */         float modelScale = (model != null) ? model.config.extra.modelScale : 1.0F;
/* 128 */         GlStateManager.func_179139_a(modelScale * 0.8D, modelScale * 0.8D, modelScale * 0.8D);
/* 129 */         float worldScale = 0.0625F;
/* 130 */         if (model != null) {
/* 131 */           int skinId = 0;
/* 132 */           if (itemstack.func_77942_o() && 
/* 133 */             itemstack.func_77978_p().func_74764_b("skinId")) {
/* 134 */             skinId = itemstack.func_77978_p().func_74762_e("skinId");
/*     */           }
/*     */ 
/*     */           
/* 138 */           String path = (skinId > 0) ? gunType.modelSkins[skinId].getSkin() : gunType.modelSkins[0].getSkin();
/* 139 */           ClientRenderHooks.customRenderers[1].bindTexture("guns", path);
/* 140 */           model.renderPart("gunModel", worldScale);
/* 141 */           model.renderPart("slideModel", worldScale);
/* 142 */           model.renderPart("boltModel", worldScale);
/* 143 */           model.renderPart("defaultBarrelModel", worldScale);
/* 144 */           model.renderPart("defaultStockModel", worldScale);
/* 145 */           model.renderPart("defaultGripModel", worldScale);
/* 146 */           model.renderPart("defaultGadgetModel", worldScale);
/* 147 */           if (ItemGun.hasAmmoLoaded(itemstack)) {
/* 148 */             model.renderPart("ammoModel", worldScale);
/*     */           }
/*     */           
/* 151 */           boolean hasScopeAttachment = false;
/* 152 */           GlStateManager.func_179094_E();
/* 153 */           for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/* 154 */             GlStateManager.func_179094_E();
/* 155 */             ItemStack itemStack = GunType.getAttachment(itemstack, attachment);
/* 156 */             if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/* 157 */               AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/* 158 */               ModelAttachment attachmentModel = (ModelAttachment)attachmentType.model;
/* 159 */               if (attachmentType.attachmentType == AttachmentPresetEnum.Sight)
/* 160 */                 hasScopeAttachment = true; 
/* 161 */               if (attachmentModel != null) {
/*     */                 
/* 163 */                 Vector3f adjustedScale = new Vector3f(attachmentModel.config.extra.modelScale, attachmentModel.config.extra.modelScale, attachmentModel.config.extra.modelScale);
/* 164 */                 GL11.glScalef(adjustedScale.x, adjustedScale.y, adjustedScale.z);
/*     */                 
/* 166 */                 if (model.config.attachments.attachmentPointMap != null && model.config.attachments.attachmentPointMap.size() >= 1 && 
/* 167 */                   model.config.attachments.attachmentPointMap.containsKey(attachment)) {
/* 168 */                   Vector3f attachmentVecTranslate = ((ArrayList<Vector3f>)model.config.attachments.attachmentPointMap.get(attachment)).get(0);
/* 169 */                   Vector3f attachmentVecRotate = ((ArrayList<Vector3f>)model.config.attachments.attachmentPointMap.get(attachment)).get(1);
/* 170 */                   GL11.glTranslatef(attachmentVecTranslate.x / attachmentModel.config.extra.modelScale, attachmentVecTranslate.y / attachmentModel.config.extra.modelScale, attachmentVecTranslate.z / attachmentModel.config.extra.modelScale);
/*     */                   
/* 172 */                   GL11.glRotatef(attachmentVecRotate.x, 1.0F, 0.0F, 0.0F);
/* 173 */                   GL11.glRotatef(attachmentVecRotate.y, 0.0F, 1.0F, 0.0F);
/* 174 */                   GL11.glRotatef(attachmentVecRotate.z, 0.0F, 0.0F, 1.0F);
/*     */                 } 
/*     */ 
/*     */                 
/* 178 */                 if (model.config.attachments.positionPointMap != null) {
/* 179 */                   for (String internalName : model.config.attachments.positionPointMap.keySet()) {
/* 180 */                     if (internalName.equals(attachmentType.internalName)) {
/* 181 */                       Vector3f trans = ((ArrayList<Vector3f>)model.config.attachments.positionPointMap.get(internalName)).get(0);
/* 182 */                       Vector3f rot = ((ArrayList<Vector3f>)model.config.attachments.positionPointMap.get(internalName)).get(1);
/* 183 */                       GL11.glTranslatef(trans.x / attachmentModel.config.extra.modelScale * worldScale, trans.y / attachmentModel.config.extra.modelScale * worldScale, trans.z / attachmentModel.config.extra.modelScale * worldScale);
/*     */                       
/* 185 */                       GL11.glRotatef(rot.x, 1.0F, 0.0F, 0.0F);
/* 186 */                       GL11.glRotatef(rot.y, 0.0F, 1.0F, 0.0F);
/* 187 */                       GL11.glRotatef(rot.z, 0.0F, 0.0F, 1.0F);
/*     */                     } 
/*     */                   } 
/*     */                 }
/*     */                 
/* 192 */                 skinId = 0;
/* 193 */                 if (itemStack.func_77942_o() && 
/* 194 */                   itemStack.func_77978_p().func_74764_b("skinId")) {
/* 195 */                   skinId = itemStack.func_77978_p().func_74762_e("skinId");
/*     */                 }
/*     */                 
/* 198 */                 if (attachmentType.sameTextureAsGun) {
/* 199 */                   ClientRenderHooks.customRenderers[3].bindTexture("guns", path);
/*     */                 } else {
/* 201 */                   path = (skinId > 0) ? attachmentType.modelSkins[skinId].getSkin() : attachmentType.modelSkins[0].getSkin();
/* 202 */                   ClientRenderHooks.customRenderers[3].bindTexture("attachments", path);
/*     */                 } 
/* 204 */                 attachmentModel.renderAttachment(worldScale);
/*     */               } 
/*     */             } 
/* 207 */             GlStateManager.func_179121_F();
/*     */           } 
/* 209 */           if (!hasScopeAttachment) {
/* 210 */             model.renderPart("defaultScopeModel", worldScale);
/*     */           }
/* 212 */           GlStateManager.func_179121_F();
/*     */         } 
/*     */         
/* 215 */         GlStateManager.func_179121_F();
/*     */         
/* 217 */         GlStateManager.func_179121_F();
/* 218 */         GlStateManager.func_179101_C();
/* 219 */         GlStateManager.func_179084_k();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 224 */         ItemGun gun = (ItemGun)itemstack.func_77973_b();
/* 225 */         GunType gunType = gun.type;
/* 226 */         ModelEnhancedGun model = (ModelEnhancedGun)gunType.enhancedModel;
/* 227 */         GunEnhancedRenderConfig config = (GunEnhancedRenderConfig)gunType.enhancedModel.config;
/*     */         
/* 229 */         float ranrot = (float)(0.10000000149011612D * (entity.field_70165_t + entity.field_70161_v - entity.field_70163_u) + (entity.func_145782_y() * 3.0F * itemstack.hashCode()));
/*     */         
/* 231 */         GlStateManager.func_179094_E();
/* 232 */         GlStateManager.func_179109_b((float)x, (float)y + 0.5F, (float)z);
/* 233 */         if (((GunEnhancedRenderConfig.ThirdPerson.RenderElement)config.thirdPerson.renderElements.get(RenderType.ITEMLOOT.serializedName)).randomYaw) {
/* 234 */           GlStateManager.func_179114_b(ranrot % 360.0F, 0.0F, 1.0F, 0.0F);
/*     */         }
/* 236 */         ClientProxy.gunEnhancedRenderer.drawThirdGun(null, RenderType.ITEMLOOT, null, itemstack);
/* 237 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */     } else {
/*     */       int i;
/* 241 */       if (itemstack != null && itemstack.func_77973_b() != null) {
/* 242 */         i = Item.func_150891_b(itemstack.func_77973_b()) + itemstack.func_77960_j();
/*     */       } else {
/* 244 */         i = 187;
/*     */       } 
/* 246 */       this.random.setSeed(i);
/* 247 */       boolean flag = false;
/* 248 */       if (func_180548_c((Entity)entity)) {
/* 249 */         this.field_76990_c.field_78724_e.func_110581_b(getEntityTexture(entity)).func_174936_b(false, false);
/* 250 */         flag = true;
/*     */       } 
/* 252 */       GlStateManager.func_179091_B();
/* 253 */       GlStateManager.func_179092_a(516, 0.1F);
/* 254 */       RenderHelper.func_74519_b();
/* 255 */       GlStateManager.func_179103_j(7425);
/* 256 */       GlStateManager.func_179147_l();
/* 257 */       GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 258 */       GlStateManager.func_179094_E();
/* 259 */       IBakedModel ibakedmodel = this.itemRenderer.func_184393_a(itemstack, entity.field_70170_p, null);
/* 260 */       int j = transformModelCount(entity, x, y, z, partialTicks, ibakedmodel);
/* 261 */       boolean flag2 = ibakedmodel.func_177556_c();
/* 262 */       if (!flag2) {
/* 263 */         float f3 = -0.0F * (j - 1) * 0.5F;
/* 264 */         float f4 = -0.0F * (j - 1) * 0.5F;
/* 265 */         float f5 = -0.09375F * (j - 1) * 0.5F;
/* 266 */         GlStateManager.func_179109_b(f3, f4, f5);
/*     */       } 
/* 268 */       if (this.field_188301_f) {
/* 269 */         GlStateManager.func_179142_g();
/* 270 */         GlStateManager.func_187431_e(func_188298_c((Entity)entity));
/*     */       } 
/* 272 */       GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
/* 273 */       GlStateManager.func_179137_b(0.0D, 0.0D, -0.03D);
/*     */       
/* 275 */       if (itemstack.func_77973_b() instanceof com.modularwarfare.common.guns.ItemBullet) {
/* 276 */         GlStateManager.func_179152_a(0.6F, 0.6F, 0.6F);
/*     */       }
/* 278 */       if (itemstack.func_77973_b() instanceof ItemGun) {
/* 279 */         GlStateManager.func_179152_a(0.9F, 0.9F, 0.9F);
/*     */       }
/*     */       
/* 282 */       for (int k = 0; k < j; k++) {
/* 283 */         if (flag2) {
/* 284 */           GlStateManager.func_179094_E();
/* 285 */           if (k > 0) {
/* 286 */             float f6 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 287 */             float f7 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 288 */             float f8 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 289 */             GlStateManager.func_179109_b(shouldSpreadItems() ? f6 : 0.0F, shouldSpreadItems() ? f7 : 0.0F, f8);
/*     */           } 
/* 291 */           ibakedmodel = ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
/* 292 */           this.itemRenderer.func_180454_a(itemstack, ibakedmodel);
/* 293 */           GlStateManager.func_179121_F();
/*     */         } else {
/* 295 */           GlStateManager.func_179094_E();
/* 296 */           if (k > 0) {
/* 297 */             float f9 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
/* 298 */             float f10 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
/* 299 */             GlStateManager.func_179109_b(f9, f10, 0.0F);
/*     */           } 
/* 301 */           ibakedmodel = ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
/* 302 */           this.itemRenderer.func_180454_a(itemstack, ibakedmodel);
/* 303 */           GlStateManager.func_179121_F();
/* 304 */           GlStateManager.func_179109_b(0.0F, 0.0F, 0.09375F);
/*     */         } 
/*     */       } 
/* 307 */       if (this.field_188301_f) {
/* 308 */         GlStateManager.func_187417_n();
/* 309 */         GlStateManager.func_179119_h();
/*     */       } 
/* 311 */       GlStateManager.func_179121_F();
/* 312 */       GlStateManager.func_179101_C();
/* 313 */       GlStateManager.func_179084_k();
/* 314 */       func_180548_c((Entity)entity);
/* 315 */       if (flag) {
/* 316 */         this.field_76990_c.field_78724_e.func_110581_b(getEntityTexture(entity)).func_174935_a();
/*     */       }
/* 318 */       super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
/*     */     } 
/* 320 */     GlStateManager.func_179103_j(7424);
/* 321 */     ObjModelRenderer.glowTxtureMode = glow;
/*     */   }
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityItemLoot entity) {
/* 325 */     return TextureMap.field_110575_b;
/*     */   }
/*     */   
/*     */   public boolean shouldSpreadItems() {
/* 329 */     return true;
/*     */   }
/*     */   
/*     */   public boolean shouldBob() {
/* 333 */     return false;
/*     */   }
/*     */   
/*     */   public static class Factory implements IRenderFactory<EntityItemLoot> {
/*     */     public Render<? super EntityItemLoot> createRenderFor(RenderManager manager) {
/* 338 */       return new RenderItemLoot(manager, Minecraft.func_71410_x().func_175599_af());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\renderers\RenderItemLoot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */