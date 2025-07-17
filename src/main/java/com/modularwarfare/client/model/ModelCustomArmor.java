/*     */ package com.modularwarfare.client.model;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.RenderBonesEvent;
/*     */ import com.modularwarfare.api.RenderMWArmorEvent;
/*     */ import com.modularwarfare.client.fpp.basic.configs.ArmorRenderConfig;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.EnhancedRenderConfig;
/*     */ import com.modularwarfare.client.fpp.enhanced.models.EnhancedModel;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import com.modularwarfare.loader.MWModelBipedBase;
/*     */ import com.modularwarfare.loader.api.ObjModelLoader;
/*     */ import com.modularwarfare.loader.api.model.AbstractObjModel;
/*     */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*     */ import mchhui.hegltf.GltfRenderModel;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class ModelCustomArmor
/*     */   extends MWModelBipedBase
/*     */ {
/*  30 */   public static Bones bones = new Bones(0.0F, false);
/*  31 */   public static Bones bonesSmall = new Bones(0.0F, true);
/*     */   
/*     */   private BaseType type;
/*     */   public Entity renderingEntity;
/*     */   public ArmorRenderConfig config;
/*     */   public EnhancedModel enhancedArmModel;
/*     */   
/*     */   public ModelCustomArmor(ArmorRenderConfig config, BaseType type) {
/*  39 */     this.config = config;
/*  40 */     if (this.config.modelFileName.endsWith(".obj")) {
/*  41 */       if (type.isInDirectory) {
/*  42 */         this
/*  43 */           .staticModel = (AbstractObjModel)ObjModelLoader.load(type.contentPack + "/obj/" + type.getAssetDir() + "/" + this.config.modelFileName);
/*     */       } else {
/*  45 */         this.staticModel = (AbstractObjModel)ObjModelLoader.load(type, "obj/" + type
/*  46 */             .getAssetDir() + "/" + this.config.modelFileName);
/*     */       } 
/*     */     } else {
/*  49 */       ModularWarfare.LOGGER.info("Internal error: " + this.config.modelFileName + " is not a valid format.");
/*     */     } 
/*  51 */     if (this.config.extra.enhancedArmModel != null) {
/*  52 */       this.enhancedArmModel = new EnhancedModel(new EnhancedRenderConfig(this.config.extra.enhancedArmModel, 24), type);
/*  53 */       this.enhancedArmModel.updateAnimation(0.0F);
/*  54 */       this.enhancedArmModel.setAnimationLoadMapper(new GltfRenderModel.NodeAnimationMapper(null)
/*     */           {
/*     */             public void handle(GltfRenderModel model, GltfRenderModel other, String target) {
/*  57 */               if (target.equals("leftArmSlimModel") && 
/*  58 */                 other.nodeStates.get("leftArmModel") != null) {
/*  59 */                 ((GltfRenderModel.NodeState)model.nodeStates.get(target)).mat = ((GltfRenderModel.NodeState)other.nodeStates.get("leftArmModel")).mat;
/*     */               }
/*     */               
/*  62 */               if (target.equals("rightArmSlimModel") && 
/*  63 */                 other.nodeStates.get("rightArmModel") != null) {
/*  64 */                 ((GltfRenderModel.NodeState)model.nodeStates.get(target)).mat = ((GltfRenderModel.NodeState)other.nodeStates.get("rightArmModel")).mat;
/*     */               }
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/*  70 */     this.type = type;
/*     */   }
/*     */   
/*     */   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
/*  74 */     GL11.glPushMatrix();
/*  75 */     this.renderingEntity = entity;
/*  76 */     this.field_78117_n = entity.func_70093_af();
/*  77 */     this; Bones bones = ModelCustomArmor.bones;
/*  78 */     if (entity instanceof AbstractClientPlayer && (
/*  79 */       (AbstractClientPlayer)entity).func_175154_l().equals("slim")) {
/*  80 */       this; bones = bonesSmall;
/*     */     } 
/*     */     
/*  83 */     bones.armor = this;
/*  84 */     bones.func_178686_a((ModelBase)this);
/*  85 */     bones.func_78087_a(f, f1, f2, f3, f4, f5, entity);
/*  86 */     if (this.config.extra.isSuit) {
/*  87 */       showHead(true);
/*  88 */       showChest(true);
/*  89 */       showLegs(true);
/*  90 */       showFeet(true);
/*  91 */       setShowMode(bones.field_78116_c, true);
/*  92 */       setShowMode(bones.field_78115_e, true);
/*  93 */       setShowMode(bones.field_178724_i, true);
/*  94 */       setShowMode(bones.field_178723_h, true);
/*  95 */       setShowMode(bones.field_178722_k, true);
/*  96 */       setShowMode(bones.field_178721_j, true);
/*     */     } else {
/*  98 */       copyShowMode(bones.field_78116_c, this.field_78116_c);
/*  99 */       copyShowMode(bones.field_78115_e, this.field_78115_e);
/* 100 */       copyShowMode(bones.field_178724_i, this.field_178724_i);
/* 101 */       copyShowMode(bones.field_178723_h, this.field_178723_h);
/* 102 */       copyShowMode(bones.field_178722_k, this.field_178722_k);
/* 103 */       copyShowMode(bones.field_178721_j, this.field_178721_j);
/*     */     } 
/* 105 */     bones.func_78088_a(entity, f, f1, f2, f3, f4, f5);
/*     */     
/* 107 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void renderRightArm(AbstractClientPlayer clientPlayer, ModelBiped baseBiped) {
/* 111 */     this; Bones bones = ModelCustomArmor.bones;
/* 112 */     if (clientPlayer.func_175154_l().equals("slim")) {
/* 113 */       this; bones = bonesSmall;
/*     */     } 
/* 115 */     bones.armor = this;
/* 116 */     float f = 1.0F;
/* 117 */     GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
/* 118 */     float f1 = 0.0625F;
/* 119 */     ModelPlayer modelplayer = bones;
/* 120 */     modelplayer.field_178723_h.field_78807_k = false;
/* 121 */     modelplayer.field_178723_h.field_78806_j = true;
/* 122 */     GlStateManager.func_179147_l();
/* 123 */     modelplayer.field_78095_p = 0.0F;
/* 124 */     modelplayer.field_78117_n = false;
/* 125 */     modelplayer.func_78087_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)clientPlayer);
/* 126 */     modelplayer.field_178723_h.field_78795_f = baseBiped.field_178723_h.field_78795_f;
/* 127 */     modelplayer.field_178723_h.field_78796_g = baseBiped.field_178723_h.field_78796_g;
/* 128 */     modelplayer.field_178723_h.field_78808_h = baseBiped.field_178723_h.field_78808_h;
/* 129 */     modelplayer.field_178723_h.func_78785_a(0.0625F);
/* 130 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   public void renderLeftArm(AbstractClientPlayer clientPlayer, ModelBiped baseBiped) {
/* 134 */     this; Bones bones = ModelCustomArmor.bones;
/* 135 */     if (clientPlayer.func_175154_l().equals("slim")) {
/* 136 */       this; bones = bonesSmall;
/*     */     } 
/* 138 */     bones.armor = this;
/* 139 */     float f = 1.0F;
/* 140 */     GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
/* 141 */     float f1 = 0.0625F;
/* 142 */     ModelPlayer modelplayer = bones;
/* 143 */     modelplayer.field_178724_i.field_78807_k = false;
/* 144 */     modelplayer.field_178724_i.field_78806_j = true;
/* 145 */     GlStateManager.func_179147_l();
/* 146 */     modelplayer.field_78117_n = false;
/* 147 */     modelplayer.field_78095_p = 0.0F;
/* 148 */     modelplayer.func_78087_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)clientPlayer);
/* 149 */     modelplayer.field_178724_i.field_78795_f = baseBiped.field_178724_i.field_78795_f;
/* 150 */     modelplayer.field_178724_i.field_78796_g = baseBiped.field_178724_i.field_78796_g;
/* 151 */     modelplayer.field_178724_i.field_78808_h = baseBiped.field_178724_i.field_78808_h;
/* 152 */     modelplayer.field_178724_i.func_78785_a(0.0625F);
/* 153 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   private void copyShowMode(ModelRenderer a, ModelRenderer b) {
/* 157 */     a.field_78806_j = b.field_78806_j;
/* 158 */     a.field_78807_k = b.field_78807_k;
/*     */   }
/*     */   
/*     */   private void setShowMode(ModelRenderer a, boolean b) {
/* 162 */     a.field_78806_j = b;
/* 163 */     a.field_78807_k = !b;
/*     */   }
/*     */   
/*     */   public void render(String modelPart, ModelRenderer bodyPart, float f5, float scale) {
/* 167 */     if (this.staticModel != null) {
/* 168 */       GlStateManager.func_179094_E();
/* 169 */       ObjModelRenderer part = this.staticModel.getPart(modelPart);
/* 170 */       if (part != null && 
/* 171 */         part != null) {
/* 172 */         ObjModelRenderer.glowType = "armor";
/* 173 */         ObjModelRenderer.glowPath = this.type.modelSkins[0].getSkin();
/* 174 */         boolean glow = ObjModelRenderer.glowTxtureMode;
/* 175 */         ObjModelRenderer.glowTxtureMode = true;
/* 176 */         part.render(f5);
/* 177 */         ObjModelRenderer.glowTxtureMode = glow;
/*     */       } 
/*     */ 
/*     */       
/* 181 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void showHead(boolean result) {
/* 186 */     showGroup("headModel", result);
/* 187 */     showGroup("headSlimModel", result);
/*     */   }
/*     */   
/*     */   public void showChest(boolean result) {
/* 191 */     showGroup("bodyModel", result);
/* 192 */     showGroup("leftArmModel", result);
/* 193 */     showGroup("rightArmModel", result);
/* 194 */     showGroup("bodySlimModel", result);
/* 195 */     showGroup("leftArmSlimModel", result);
/* 196 */     showGroup("rightArmSlimModel", result);
/*     */   }
/*     */   
/*     */   public void showLegs(boolean result) {
/* 200 */     showGroup("leftLegSlimModel", result);
/* 201 */     showGroup("rightLegSlimModel", result);
/*     */   }
/*     */   
/*     */   public void showFeet(boolean result) {
/* 205 */     showGroup("leftFootSlimModel", result);
/* 206 */     showGroup("rightFootSlimModel", result);
/*     */   }
/*     */   
/*     */   public void showGroup(String part, boolean result) {
/* 210 */     if (getStaticModel() != null) {
/* 211 */       ObjModelRenderer modpart = getStaticModel().getPart(part);
/* 212 */       if (modpart != null) {
/* 213 */         modpart.isHidden = !result;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public ModelBiped getMainModel() {
/* 219 */     return (ModelBiped)this;
/*     */   }
/*     */   
/*     */   public static class Bones extends ModelPlayer {
/* 223 */     public ModelCustomArmor armor = null;
/*     */     public boolean isSlim;
/*     */     
/*     */     public Bones(float modelSize, boolean smallArmsIn) {
/* 227 */       super(modelSize, smallArmsIn);
/* 228 */       this.field_187075_l = ModelBiped.ArmPose.EMPTY;
/* 229 */       this.field_187076_m = ModelBiped.ArmPose.EMPTY;
/* 230 */       this.field_78090_t = 64;
/* 231 */       this.field_78089_u = 64;
/* 232 */       this.field_78116_c = new BonePart(BonePart.EnumBoneType.HEAD, this, 0, 0);
/* 233 */       this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
/* 234 */       this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
/* 235 */       this.field_78115_e = new BonePart(BonePart.EnumBoneType.BODY, this, 16, 16);
/* 236 */       this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
/* 237 */       this.field_78115_e.func_78793_a(0.0F, 0.0F, 0.0F);
/* 238 */       this.field_178723_h = new BonePart(BonePart.EnumBoneType.RIGHTARM, this, 40, 16);
/* 239 */       this.field_178723_h.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
/* 240 */       this.field_178723_h.func_78793_a(-5.0F, 2.0F, 0.0F);
/* 241 */       this.field_178721_j = new BonePart(BonePart.EnumBoneType.RIGHTLEG, this, 0, 16);
/* 242 */       this.field_178721_j.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
/* 243 */       this.field_178721_j.func_78793_a(-1.9F, 12.0F, 0.0F);
/*     */       
/* 245 */       if (smallArmsIn) {
/* 246 */         this.field_178724_i = new BonePart(BonePart.EnumBoneType.LEFTARM, this, 32, 48);
/* 247 */         this.field_178724_i.func_78790_a(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
/* 248 */         this.field_178724_i.func_78793_a(5.0F, 2.5F, 0.0F);
/* 249 */         this.field_178723_h = new BonePart(BonePart.EnumBoneType.RIGHTARM, this, 40, 16);
/* 250 */         this.field_178723_h.func_78790_a(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
/* 251 */         this.field_178723_h.func_78793_a(-5.0F, 2.5F, 0.0F);
/*     */       } else {
/* 253 */         this.field_178724_i = new BonePart(BonePart.EnumBoneType.LEFTARM, this, 32, 48);
/* 254 */         this.field_178724_i.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
/* 255 */         this.field_178724_i.func_78793_a(5.0F, 2.0F, 0.0F);
/*     */       } 
/*     */       
/* 258 */       this.field_178722_k = new BonePart(BonePart.EnumBoneType.LEFTLEG, this, 16, 48);
/* 259 */       this.field_178722_k.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
/* 260 */       this.field_178722_k.func_78793_a(1.9F, 12.0F, 0.0F);
/* 261 */       this.isSlim = smallArmsIn;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 268 */       super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/*     */       
/* 270 */       MinecraftForge.EVENT_BUS.post((Event)new RenderBonesEvent.RotationAngles(this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 278 */       GlStateManager.func_179094_E();
/* 279 */       if (entityIn.func_70093_af()) {
/* 280 */         GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
/*     */       }
/* 282 */       GlStateManager.func_179103_j(7425);
/* 283 */       this.field_78116_c.func_78785_a(scale);
/* 284 */       this.field_78115_e.func_78785_a(scale);
/* 285 */       this.field_178723_h.func_78785_a(scale);
/* 286 */       this.field_178724_i.func_78785_a(scale);
/* 287 */       this.field_178721_j.func_78785_a(scale);
/* 288 */       this.field_178722_k.func_78785_a(scale);
/* 289 */       GlStateManager.func_179103_j(7424);
/* 290 */       GlStateManager.func_179121_F();
/*     */     }
/*     */     
/*     */     public static class BonePart extends ModelRenderer {
/* 294 */       public static float customOffestX = 0.0F;
/* 295 */       public static float customOffestY = 0.0F;
/* 296 */       public static float customOffestZ = 0.0F;
/* 297 */       private static ModelRenderer NonePart = null;
/*     */       private EnumBoneType type;
/*     */       private int displayList;
/*     */       private boolean compiled;
/*     */       private ModelCustomArmor.Bones baseModel;
/*     */       
/*     */       public BonePart(EnumBoneType type, ModelCustomArmor.Bones model, int texOffX, int texOffY) {
/* 304 */         super((ModelBase)model, texOffX, texOffY);
/* 305 */         if (NonePart == null) {
/* 306 */           NonePart = new ModelRenderer((ModelBase)model);
/*     */         }
/* 308 */         this.type = type;
/* 309 */         this.baseModel = model;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void func_78785_a(float scale) {
/* 316 */         GlStateManager.func_179109_b(this.field_82906_o, this.field_82908_p, this.field_82907_q);
/* 317 */         GlStateManager.func_179094_E();
/* 318 */         GlStateManager.func_179109_b(this.field_78800_c * scale, this.field_78797_d * scale, this.field_78798_e * scale);
/*     */ 
/*     */         
/* 321 */         if (this.field_78808_h != 0.0F) {
/* 322 */           GlStateManager.func_179114_b(this.field_78808_h * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/* 325 */         if (this.field_78796_g != 0.0F) {
/* 326 */           GlStateManager.func_179114_b(this.field_78796_g * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 329 */         if (this.field_78795_f != 0.0F) {
/* 330 */           GlStateManager.func_179114_b(this.field_78795_f * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */ 
/*     */         
/* 334 */         GlStateManager.func_179109_b(customOffestX, customOffestY, customOffestZ);
/* 335 */         int texture = GL11.glGetInteger(32873);
/* 336 */         MinecraftForge.EVENT_BUS.post((Event)new RenderBonesEvent.Pre(this.baseModel.armor, this.type, scale));
/* 337 */         MinecraftForge.EVENT_BUS.post((Event)new RenderMWArmorEvent.Pre(this.baseModel.armor, this.type, scale));
/*     */         
/* 339 */         switch (this.type) {
/*     */           case HEAD:
/* 341 */             if (!this.field_78807_k && 
/* 342 */               this.field_78806_j) {
/* 343 */               if (this.baseModel.armor.config.extra.slimSupported && this.baseModel.isSlim) {
/* 344 */                 this.baseModel.armor.render("headSlimModel", NonePart, scale, 1.0F); break;
/*     */               } 
/* 346 */               this.baseModel.armor.render("headModel", NonePart, scale, 1.0F);
/*     */             } 
/*     */             break;
/*     */ 
/*     */           
/*     */           case BODY:
/* 352 */             if (!this.field_78807_k && 
/* 353 */               this.field_78806_j) {
/* 354 */               if (this.baseModel.armor.config.extra.slimSupported && this.baseModel.isSlim) {
/* 355 */                 this.baseModel.armor.render("bodySlimModel", NonePart, scale, 1.0F); break;
/*     */               } 
/* 357 */               this.baseModel.armor.render("bodyModel", NonePart, scale, 1.0F);
/*     */             } 
/*     */             break;
/*     */ 
/*     */           
/*     */           case LEFTARM:
/* 363 */             GlStateManager.func_179109_b(-5.0F * scale, -2.0F * scale, 0.0F);
/* 364 */             if (!this.field_78807_k && 
/* 365 */               this.field_78806_j) {
/* 366 */               if (this.baseModel.armor.config.extra.slimSupported && this.baseModel.isSlim) {
/* 367 */                 this.baseModel.armor.render("leftArmSlimModel", NonePart, scale, 1.0F); break;
/*     */               } 
/* 369 */               this.baseModel.armor.render("leftArmModel", NonePart, scale, 1.0F);
/*     */             } 
/*     */             break;
/*     */ 
/*     */           
/*     */           case RIGHTARM:
/* 375 */             GlStateManager.func_179109_b(5.0F * scale, -2.0F * scale, 0.0F);
/* 376 */             if (!this.field_78807_k && 
/* 377 */               this.field_78806_j) {
/* 378 */               if (this.baseModel.armor.config.extra.slimSupported && this.baseModel.isSlim) {
/* 379 */                 this.baseModel.armor.render("rightArmSlimModel", NonePart, scale, 1.0F); break;
/*     */               } 
/* 381 */               this.baseModel.armor.render("rightArmModel", NonePart, scale, 1.0F);
/*     */             } 
/*     */             break;
/*     */ 
/*     */           
/*     */           case LEFTLEG:
/* 387 */             GlStateManager.func_179109_b(-2.0F * scale, -12.0F * scale, 0.0F);
/* 388 */             if (!this.field_78807_k && 
/* 389 */               this.field_78806_j) {
/* 390 */               if (this.baseModel.armor.config.extra.slimSupported && this.baseModel.isSlim) {
/* 391 */                 this.baseModel.armor.render("leftLegSlimModel", NonePart, scale, 1.0F);
/* 392 */                 this.baseModel.armor.render("leftFootSlimModel", NonePart, scale, 1.0F); break;
/*     */               } 
/* 394 */               this.baseModel.armor.render("leftLegModel", NonePart, scale, 1.0F);
/* 395 */               this.baseModel.armor.render("leftFootModel", NonePart, scale, 1.0F);
/*     */             } 
/*     */             break;
/*     */ 
/*     */           
/*     */           case RIGHTLEG:
/* 401 */             GlStateManager.func_179109_b(2.0F * scale, -12.0F * scale, 0.0F);
/* 402 */             if (!this.field_78807_k && 
/* 403 */               this.field_78806_j) {
/* 404 */               if (this.baseModel.armor.config.extra.slimSupported && this.baseModel.isSlim) {
/* 405 */                 this.baseModel.armor.render("rightLegSlimModel", NonePart, scale, 1.0F);
/* 406 */                 this.baseModel.armor.render("rightFootSlimModel", NonePart, scale, 1.0F); break;
/*     */               } 
/* 408 */               this.baseModel.armor.render("rightLegModel", NonePart, scale, 1.0F);
/* 409 */               this.baseModel.armor.render("rightFootModel", NonePart, scale, 1.0F);
/*     */             } 
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 416 */         MinecraftForge.EVENT_BUS.post((Event)new RenderBonesEvent.Post(this.baseModel.armor, this.type, scale));
/* 417 */         MinecraftForge.EVENT_BUS.post((Event)new RenderMWArmorEvent.Post(this.baseModel.armor, this.type, scale));
/* 418 */         GL11.glBindTexture(3553, texture);
/* 419 */         GlStateManager.func_179121_F();
/* 420 */         GlStateManager.func_179109_b(-this.field_82906_o, -this.field_82908_p, -this.field_82907_q);
/*     */       }
/*     */       
/*     */       public enum EnumBoneType
/*     */       {
/* 425 */         HEAD, BODY, LEFTARM, RIGHTARM, LEFTLEG, RIGHTLEG; } } public enum EnumBoneType { HEAD, BODY, LEFTARM, RIGHTARM, LEFTLEG, RIGHTLEG; }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\model\ModelCustomArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */