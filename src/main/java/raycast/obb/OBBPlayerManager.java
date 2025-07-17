/*     */ package com.modularwarfare.raycast.obb;
/*     */ 
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.vector.Vector3f;
/*     */ import com.modularwarfare.loader.ObjModel;
/*     */ import com.modularwarfare.loader.api.ObjModelLoader;
/*     */ import com.modularwarfare.raycast.obb.bbloader.BlockBenchOBBInfoLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import mchhui.modularmovements.tactical.PlayerState;
/*     */ import mchhui.modularmovements.tactical.client.ClientLitener;
/*     */ import mchhui.modularmovements.tactical.server.ServerListener;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.client.event.RenderPlayerEvent;
/*     */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OBBPlayerManager
/*     */ {
/*  49 */   public static HashMap<String, PlayerOBBModelObject> playerOBBObjectMap = new HashMap<>();
/*     */   public static EntityPlayer entityPlayer;
/*  51 */   public static ModelPlayer modelPlayer = new ModelPlayer();
/*     */   public static boolean debug = false;
/*  53 */   public static ArrayList<OBBDebugObject> lines = new ArrayList<>();
/*     */   
/*  55 */   private static final ObjModel debugBoxModel = ObjModelLoader.load(new ResourceLocation("modularwarfare:obb/model.obj"));
/*  56 */   private static final ResourceLocation debugBoxTex = new ResourceLocation("modularwarfare:obb/debugbox_red.png");
/*     */   
/*     */   public static class OBBDebugObject {
/*     */     public Vector3f start;
/*     */     public Vector3f end;
/*     */     public OBBModelBox box;
/*     */     public long aliveTime;
/*     */     public Vector3f pos;
/*     */     
/*     */     public OBBDebugObject(Vector3f pos) {
/*  66 */       this.pos = pos;
/*  67 */       this.aliveTime = System.currentTimeMillis() + 5000L;
/*     */     }
/*     */     
/*     */     public OBBDebugObject(Vector3f start, Vector3f end) {
/*  71 */       this.start = start;
/*  72 */       this.end = end;
/*  73 */       this.aliveTime = System.currentTimeMillis() + 5000L;
/*     */     }
/*     */     
/*     */     public OBBDebugObject(OBBModelBox box) {
/*  77 */       this.box = box;
/*  78 */       this.aliveTime = System.currentTimeMillis() + 5000L;
/*     */     }
/*     */     
/*     */     public void render() {
/*  82 */       if (this.aliveTime < System.currentTimeMillis()) {
/*     */         return;
/*     */       }
/*  85 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*  86 */       GlStateManager.func_187441_d(2.0F);
/*  87 */       GlStateManager.func_179103_j(7425);
/*  88 */       GlStateManager.func_179090_x();
/*  89 */       if (this.box != null) {
/*  90 */         this.box.axis.forEach(axi -> {
/*     */               Tessellator tessellator = Tessellator.func_178181_a();
/*     */               
/*     */               tessellator.func_178180_c().func_181668_a(3, DefaultVertexFormats.field_181706_f);
/*     */               
/*     */               tessellator.func_178180_c().func_181662_b(this.box.center.x, this.box.center.y, this.box.center.z).func_181669_b(255, 0, 0, 255).func_181675_d();
/*     */               tessellator.func_178180_c().func_181662_b((this.box.center.x + axi.x), (this.box.center.y + axi.y), (this.box.center.z + axi.z)).func_181669_b(0, 255, 0, 255).func_181675_d();
/*     */               tessellator.func_78381_a();
/*     */             });
/*  99 */         this.box.axisNormal.forEach(axi -> {
/*     */               Tessellator tessellator = Tessellator.func_178181_a();
/*     */               
/*     */               tessellator.func_178180_c().func_181668_a(3, DefaultVertexFormats.field_181706_f);
/*     */               
/*     */               tessellator.func_178180_c().func_181662_b(this.box.center.x, this.box.center.y, this.box.center.z).func_181669_b(0, 255, 0, 255).func_181675_d();
/*     */               tessellator.func_178180_c().func_181662_b((this.box.center.x + axi.x), (this.box.center.y + axi.y), (this.box.center.z + axi.z)).func_181669_b(0, 255, 0, 255).func_181675_d();
/*     */               tessellator.func_78381_a();
/*     */             });
/* 108 */       } else if (this.pos != null) {
/* 109 */         GlStateManager.func_179094_E();
/* 110 */         GlStateManager.func_179098_w();
/* 111 */         GlStateManager.func_179109_b(this.pos.x, this.pos.y, this.pos.z);
/* 112 */         (Minecraft.func_71410_x()).field_71446_o.func_110577_a(OBBPlayerManager.debugBoxTex);
/* 113 */         OBBPlayerManager.debugBoxModel.renderAll(1.0F);
/* 114 */         GlStateManager.func_179090_x();
/* 115 */         GlStateManager.func_179121_F();
/*     */       } else {
/* 117 */         Tessellator tessellator = Tessellator.func_178181_a();
/* 118 */         tessellator.func_178180_c().func_181668_a(3, DefaultVertexFormats.field_181706_f);
/* 119 */         tessellator.func_178180_c().func_181662_b(this.start.x, this.start.y, this.start.z).func_181669_b(0, 0, 255, 255).func_181675_d();
/* 120 */         tessellator.func_178180_c().func_181662_b(this.end.x, this.end.y, this.end.z).func_181669_b(0, 0, 255, 255).func_181675_d();
/* 121 */         tessellator.func_78381_a();
/*     */       } 
/* 123 */       GlStateManager.func_179098_w();
/* 124 */       GlStateManager.func_179103_j(7424);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PlayerOBBModelObject extends OBBModelObject {
/* 129 */     public long nextSyncTime = 0L;
/*     */     public boolean isSyncing = false;
/*     */     
/*     */     public PlayerOBBModelObject() {
/* 133 */       this.boneUpdatePoseListeners.add(bone -> {
/*     */             if (bone.name.equals("head")) {
/*     */               bone.translation.set(0.0F, 0.0F, 0.0F);
/*     */               if (OBBPlayerManager.entityPlayer.func_70093_af()) {
/*     */                 bone.translation.add(0.0F, -5.0F, 0.0F);
/*     */               }
/*     */               bone.rotation.set(OBBPlayerManager.modelPlayer.bipedHead.rotateAngleX, OBBPlayerManager.modelPlayer.bipedHead.rotateAngleY, OBBPlayerManager.modelPlayer.bipedHead.rotateAngleZ);
/*     */             } 
/*     */             if (bone.name.equals("body")) {
/*     */               bone.translation.set(0.0F, 0.0F, 0.0F);
/*     */               if (OBBPlayerManager.entityPlayer.func_70093_af()) {
/*     */                 bone.translation.add(0.0F, -5.0F, 0.0F);
/*     */               }
/*     */               bone.rotation.set(OBBPlayerManager.modelPlayer.bipedBody.rotateAngleX, OBBPlayerManager.modelPlayer.bipedBody.rotateAngleY, OBBPlayerManager.modelPlayer.bipedBody.rotateAngleZ);
/*     */             } 
/*     */             if (bone.name.equals("rightArm")) {
/*     */               bone.translation.set(0.0F, 0.0F, 0.0F);
/*     */               if (OBBPlayerManager.entityPlayer.func_70093_af()) {
/*     */                 bone.translation.add(0.0F, -5.0F, 0.0F);
/*     */               }
/*     */               bone.rotation.set(OBBPlayerManager.modelPlayer.bipedRightArm.rotateAngleX, OBBPlayerManager.modelPlayer.bipedRightArm.rotateAngleY, OBBPlayerManager.modelPlayer.bipedRightArm.rotateAngleZ);
/*     */             } 
/*     */             if (bone.name.equals("leftArm")) {
/*     */               bone.translation.set(0.0F, 0.0F, 0.0F);
/*     */               if (OBBPlayerManager.entityPlayer.func_70093_af()) {
/*     */                 bone.translation.add(0.0F, -5.0F, 0.0F);
/*     */               }
/*     */               bone.rotation.set(OBBPlayerManager.modelPlayer.bipedLeftArm.rotateAngleX, OBBPlayerManager.modelPlayer.bipedLeftArm.rotateAngleY, OBBPlayerManager.modelPlayer.bipedLeftArm.rotateAngleZ);
/*     */             } 
/*     */             if (bone.name.equals("rightLeg")) {
/*     */               bone.translation.set(0.0F, 0.0F, 0.0F);
/*     */               if (OBBPlayerManager.entityPlayer.func_70093_af()) {
/*     */                 bone.translation.add(0.0F, 0.0F, -4.0F);
/*     */               }
/*     */               bone.rotation.set(OBBPlayerManager.modelPlayer.bipedRightLeg.rotateAngleX, OBBPlayerManager.modelPlayer.bipedRightLeg.rotateAngleY, OBBPlayerManager.modelPlayer.bipedRightLeg.rotateAngleZ);
/*     */             } 
/*     */             if (bone.name.equals("leftLeg")) {
/*     */               bone.translation.set(0.0F, 0.0F, 0.0F);
/*     */               if (OBBPlayerManager.entityPlayer.func_70093_af()) {
/*     */                 bone.translation.add(0.0F, 0.0F, -4.0F);
/*     */               }
/*     */               bone.rotation.set(OBBPlayerManager.modelPlayer.bipedLeftLeg.rotateAngleX, OBBPlayerManager.modelPlayer.bipedLeftLeg.rotateAngleY, OBBPlayerManager.modelPlayer.bipedLeftLeg.rotateAngleZ);
/*     */             } 
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<OBBModelBox> calculateIntercept(OBBModelBox testBox) {
/* 186 */       List<OBBModelBox> list = new ArrayList();
/* 187 */       for (int i = 0; i < this.boxes.size(); i++) {
/* 188 */         OBBModelBox box = this.boxes.get(i);
/* 189 */         if (OBBModelBox.testCollisionOBBAndOBB(box, testBox)) {
/* 190 */           list.add(box.copy());
/*     */         }
/*     */       } 
/* 193 */       return list;
/*     */     }
/*     */   }
/*     */   
/*     */   public static PlayerOBBModelObject getPlayerOBBObject(String name) {
/* 198 */     PlayerOBBModelObject playerOBBObject = playerOBBObjectMap.get(name);
/* 199 */     if (playerOBBObject == null) {
/* 200 */       playerOBBObject = (PlayerOBBModelObject)BlockBenchOBBInfoLoader.loadOBBInfo(PlayerOBBModelObject.class, new ResourceLocation("modularwarfare:obb/player.obb.json"));
/*     */       
/* 202 */       playerOBBObjectMap.put(name, playerOBBObject);
/*     */     } 
/* 204 */     return playerOBBObject;
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onPlayerTick(TickEvent.PlayerTickEvent event) {
/* 209 */     if (event.phase != TickEvent.Phase.END) {
/*     */       return;
/*     */     }
/* 212 */     if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
/*     */       return;
/*     */     }
/* 215 */     entityPlayer = event.player;
/* 216 */     PlayerOBBModelObject playerOBBObject = playerOBBObjectMap.get(event.player.func_70005_c_());
/* 217 */     if (playerOBBObject == null) {
/* 218 */       playerOBBObject = (PlayerOBBModelObject)BlockBenchOBBInfoLoader.loadOBBInfo(PlayerOBBModelObject.class, new ResourceLocation("modularwarfare:obb/player.obb.json"));
/*     */       
/* 220 */       playerOBBObjectMap.put(event.player.func_70005_c_(), playerOBBObject);
/*     */     } 
/* 222 */     computePose((Event)event, playerOBBObject, 1.0F);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerRender(RenderPlayerEvent.Post event) {
/* 227 */     float partialTick = event.getPartialRenderTick();
/* 228 */     entityPlayer = event.getEntityPlayer();
/* 229 */     PlayerOBBModelObject playerOBBObject = playerOBBObjectMap.get(event.getEntityPlayer().func_70005_c_());
/* 230 */     if (playerOBBObject == null) {
/* 231 */       playerOBBObject = (PlayerOBBModelObject)BlockBenchOBBInfoLoader.loadOBBInfo(PlayerOBBModelObject.class, new ResourceLocation("modularwarfare:obb/player.obb.json"));
/*     */       
/* 233 */       playerOBBObjectMap.put(event.getEntityPlayer().func_70005_c_(), playerOBBObject);
/*     */     } 
/* 235 */     computePose((Event)event, playerOBBObject, partialTick);
/* 236 */     if (Minecraft.func_71410_x().func_175598_ae().func_178634_b() && ModConfig.INSTANCE.dev_mode) {
/* 237 */       GlStateManager.func_179094_E();
/* 238 */       Entity entity = Minecraft.func_71410_x().func_175606_aa();
/* 239 */       GlStateManager.func_179137_b(-(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * partialTick), -(entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * partialTick), -(entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * partialTick));
/*     */ 
/*     */ 
/*     */       
/* 243 */       playerOBBObject.renderDebugBoxes();
/* 244 */       playerOBBObject.renderDebugAixs();
/* 245 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void computePose(Event event, PlayerOBBModelObject playerOBBObject, float partialTick) {
/* 250 */     if (!playerOBBObject.isSyncing && System.currentTimeMillis() >= playerOBBObject.nextSyncTime) {
/* 251 */       playerOBBObject.isSyncing = true;
/*     */       
/* 253 */       modelPlayer.swingProgress = entityPlayer.field_70733_aJ;
/* 254 */       modelPlayer.isSneak = entityPlayer.func_70093_af();
/* 255 */       modelPlayer.isRiding = entityPlayer.func_184218_aH();
/* 256 */       ModelPlayer.ArmPose mainPose = ModelPlayer.ArmPose.EMPTY;
/* 257 */       ModelPlayer.ArmPose offPose = ModelPlayer.ArmPose.EMPTY;
/* 258 */       ItemStack itemstack = entityPlayer.func_184614_ca();
/* 259 */       ItemStack itemstack1 = entityPlayer.func_184592_cb();
/* 260 */       if (!itemstack.func_190926_b()) {
/* 261 */         mainPose = ModelPlayer.ArmPose.ITEM;
/*     */         
/* 263 */         if (entityPlayer.func_184605_cv() > 0) {
/* 264 */           EnumAction enumaction = itemstack.func_77975_n();
/*     */           
/* 266 */           if (enumaction == EnumAction.BLOCK) {
/* 267 */             mainPose = ModelPlayer.ArmPose.BLOCK;
/* 268 */           } else if (enumaction == EnumAction.BOW) {
/* 269 */             mainPose = ModelPlayer.ArmPose.BOW_AND_ARROW;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 274 */       if (!itemstack1.func_190926_b()) {
/* 275 */         offPose = ModelPlayer.ArmPose.ITEM;
/*     */         
/* 277 */         if (entityPlayer.func_184605_cv() > 0) {
/* 278 */           EnumAction enumaction1 = itemstack1.func_77975_n();
/*     */           
/* 280 */           if (enumaction1 == EnumAction.BLOCK) {
/* 281 */             offPose = ModelPlayer.ArmPose.BLOCK;
/* 282 */           } else if (enumaction1 == EnumAction.BOW) {
/* 283 */             offPose = ModelPlayer.ArmPose.BOW_AND_ARROW;
/*     */           } 
/*     */         } 
/*     */       } 
/* 287 */       if (entityPlayer.func_184591_cq() == EnumHandSide.RIGHT) {
/* 288 */         modelPlayer.rightArmPose = mainPose;
/* 289 */         modelPlayer.leftArmPose = offPose;
/*     */       } else {
/* 291 */         modelPlayer.rightArmPose = offPose;
/* 292 */         modelPlayer.leftArmPose = mainPose;
/*     */       } 
/* 294 */       if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
/* 295 */         if (event instanceof RenderPlayerEvent) {
/* 296 */           modelPlayer.copyFrom(((RenderPlayerEvent)event).getRenderer().func_177087_b());
/*     */         }
/*     */       } else {
/* 299 */         modelPlayer.setRotationAngles(entityPlayer.field_184619_aG, entityPlayer.field_184618_aE, entityPlayer.field_70173_aa, entityPlayer.field_70177_z - entityPlayer.field_70761_aq, entityPlayer.field_70125_A, 1.0F, (Entity)entityPlayer);
/*     */ 
/*     */         
/* 302 */         if (ModularWarfare.isLoadedModularMovements) {
/* 303 */           ServerListener.setRotationAngles(modelPlayer, entityPlayer.field_184619_aG, entityPlayer.field_184618_aE, entityPlayer.field_70173_aa, entityPlayer.field_70177_z - entityPlayer.field_70761_aq, entityPlayer.field_70125_A, 1.0F, (Entity)entityPlayer);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 309 */       playerOBBObject.updatePose();
/* 310 */       PlayerOBBModelObject syncOBBObejct = playerOBBObject;
/* 311 */       syncOBBObejct.scene.resetMatrix();
/* 312 */       double lx = entityPlayer.field_70142_S;
/* 313 */       double ly = entityPlayer.field_70137_T;
/* 314 */       double lz = entityPlayer.field_70136_U;
/* 315 */       double x = entityPlayer.field_70165_t;
/* 316 */       double y = entityPlayer.field_70163_u;
/* 317 */       double z = entityPlayer.field_70161_v;
/* 318 */       x = lx + (x - lx) * partialTick;
/* 319 */       y = ly + (y - ly) * partialTick;
/* 320 */       z = lz + (z - lz) * partialTick;
/* 321 */       syncOBBObejct.scene.translate((float)x, (float)y, (float)z);
/* 322 */       float yaw = entityPlayer.field_70761_aq;
/* 323 */       if (entityPlayer.func_184218_aH()) {
/* 324 */         yaw = entityPlayer.field_70177_z;
/*     */       }
/* 326 */       if (entityPlayer.func_70089_S() && entityPlayer.func_70608_bn()) {
/* 327 */         syncOBBObejct.scene.rotate(entityPlayer.func_71051_bG(), 0.0F, 1.0F, 0.0F);
/* 328 */         syncOBBObejct.scene.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 329 */         syncOBBObejct.scene.rotate(270.0F, 0.0F, 1.0F, 0.0F);
/* 330 */       } else if (entityPlayer.func_184613_cA()) {
/* 331 */         syncOBBObejct.scene.rotate(yaw / 180.0F * 3.14159F, 0.0F, -1.0F, 0.0F);
/* 332 */         float f = entityPlayer.func_184599_cB() + partialTick;
/* 333 */         float f1 = MathHelper.func_76131_a(f * f / 100.0F, 0.0F, 1.0F);
/* 334 */         syncOBBObejct.scene.rotate(-f1 * (-90.0F - entityPlayer.field_70125_A) / 180.0F * 3.14159F, 1.0F, 0.0F, 0.0F);
/* 335 */         Vec3d vec3d = entityPlayer.func_70676_i(partialTick);
/* 336 */         double d0 = entityPlayer.field_70159_w * entityPlayer.field_70159_w + entityPlayer.field_70179_y * entityPlayer.field_70179_y;
/* 337 */         double d1 = vec3d.field_72450_a * vec3d.field_72450_a + vec3d.field_72449_c * vec3d.field_72449_c;
/*     */         
/* 339 */         if (d0 > 0.0D && d1 > 0.0D) {
/*     */           
/* 341 */           double d2 = (entityPlayer.field_70159_w * vec3d.field_72450_a + entityPlayer.field_70179_y * vec3d.field_72449_c) / Math.sqrt(d0) * Math.sqrt(d1);
/* 342 */           double d3 = entityPlayer.field_70159_w * vec3d.field_72449_c - entityPlayer.field_70179_y * vec3d.field_72450_a;
/* 343 */           syncOBBObejct.scene.rotate((float)(Math.signum(d3) * Math.acos(d2)), 0.0F, 1.0F, 0.0F);
/*     */         } 
/*     */       } else {
/* 346 */         boolean flag = false;
/* 347 */         if (ModularWarfare.isLoadedModularMovements) {
/* 348 */           if (entityPlayer == (Minecraft.func_71410_x()).field_71439_g && entityPlayer.func_70089_S()) {
/* 349 */             if (ClientLitener.clientPlayerState.isSitting) {
/* 350 */               syncOBBObejct.scene.translate(0.0D, -0.5D, 0.0D);
/*     */             }
/* 352 */             if (ClientLitener.clientPlayerState.isCrawling) {
/* 353 */               syncOBBObejct.scene.rotateDegree(entityPlayer.field_70761_aq, 0.0F, -1.0F, 0.0F);
/* 354 */               syncOBBObejct.scene.rotateDegree(-90.0F, -1.0F, 0.0F, 0.0F);
/* 355 */               syncOBBObejct.scene.translate(0.0D, -1.3D, -0.1D);
/* 356 */               syncOBBObejct.scene.translate(-ClientLitener.cameraProbeOffset * 0.4D, 0.0D, 0.0D);
/* 357 */               flag = true;
/*     */             }
/* 359 */             else if (ClientLitener.cameraProbeOffset != 0.0F) {
/* 360 */               syncOBBObejct.scene.rotateDegree(180.0F - entityPlayer.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 361 */               syncOBBObejct.scene.translate(ClientLitener.cameraProbeOffset * 0.1D, 0.0D, 0.0D);
/* 362 */               syncOBBObejct.scene.rotateDegree(180.0F - entityPlayer.field_70177_z, 0.0F, -1.0F, 0.0F);
/* 363 */               syncOBBObejct.scene.rotateDegree(entityPlayer.field_70761_aq, 0.0F, -1.0F, 0.0F);
/* 364 */               syncOBBObejct.scene.rotateDegree(ClientLitener.cameraProbeOffset * -20.0F, 0.0F, 0.0F, -1.0F);
/*     */               
/* 366 */               flag = true;
/*     */             } 
/*     */           } 
/*     */           
/* 370 */           if (entityPlayer != (Minecraft.func_71410_x()).field_71439_g && entityPlayer instanceof EntityPlayer && entityPlayer
/* 371 */             .func_70089_S() && ClientLitener.ohterPlayerStateMap
/* 372 */             .containsKey(Integer.valueOf(entityPlayer.func_145782_y()))) {
/*     */             
/* 374 */             PlayerState state = (PlayerState)ClientLitener.ohterPlayerStateMap.get(Integer.valueOf(entityPlayer.func_145782_y()));
/* 375 */             if (state.isSitting)
/* 376 */               syncOBBObejct.scene.translate(0.0D, -0.5D, 0.0D); 
/* 377 */             if (state.isCrawling) {
/* 378 */               syncOBBObejct.scene.rotateDegree(entityPlayer.field_70761_aq, 0.0F, -1.0F, 0.0F);
/* 379 */               syncOBBObejct.scene.rotateDegree(-90.0F, -1.0F, 0.0F, 0.0F);
/* 380 */               syncOBBObejct.scene.translate(0.0D, -1.3D, -0.1D);
/* 381 */               syncOBBObejct.scene.translate(-state.probeOffset * 0.4D, 0.0D, 0.0D);
/* 382 */               flag = true;
/*     */             } 
/* 384 */             state.updateOffset();
/* 385 */             if (!state.isCrawling && state.probeOffset != 0.0F) {
/* 386 */               syncOBBObejct.scene.rotateDegree(180.0F - entityPlayer.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 387 */               syncOBBObejct.scene.translate(state.probeOffset * 0.1D, 0.0D, 0.0D);
/* 388 */               syncOBBObejct.scene.rotateDegree(180.0F - entityPlayer.field_70177_z, 0.0F, -1.0F, 0.0F);
/* 389 */               syncOBBObejct.scene.rotateDegree(entityPlayer.field_70761_aq, 0.0F, -1.0F, 0.0F);
/* 390 */               syncOBBObejct.scene.rotateDegree(state.probeOffset * -20.0F, 0.0F, 0.0F, -1.0F);
/* 391 */               flag = true;
/*     */             } 
/*     */           } 
/*     */         } 
/* 395 */         if (!flag) {
/* 396 */           syncOBBObejct.scene.rotate(yaw / 180.0F * 3.14159F, 0.0F, -1.0F, 0.0F);
/*     */         }
/*     */       } 
/* 399 */       syncOBBObejct.scene.scale(0.05859375F, 0.05859375F, 0.05859375F);
/* 400 */       syncOBBObejct.computePose();
/* 401 */       syncOBBObejct.isSyncing = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onrenderWorld(RenderWorldLastEvent event) {
/* 407 */     debug = false;
/* 408 */     if (Minecraft.func_71410_x().func_175598_ae().func_178634_b() && ModConfig.INSTANCE.dev_mode) {
/* 409 */       debug = true;
/* 410 */       GlStateManager.func_179094_E();
/* 411 */       Entity entity = Minecraft.func_71410_x().func_175606_aa();
/* 412 */       GlStateManager.func_179137_b(
/* 413 */           -(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * event.getPartialTicks()), 
/* 414 */           -(entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * event.getPartialTicks()), 
/* 415 */           -(entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * event.getPartialTicks()));
/* 416 */       lines.forEach(line -> line.render());
/*     */ 
/*     */       
/* 419 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\raycast\obb\OBBPlayerManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */