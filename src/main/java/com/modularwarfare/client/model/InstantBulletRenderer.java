/*     */ package com.modularwarfare.client.model;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.vector.Vector3f;
/*     */ import com.modularwarfare.loader.ObjModel;
/*     */ import com.modularwarfare.loader.api.ObjModelLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InstantBulletRenderer
/*     */ {
/*     */   private static TextureManager textureManager;
/*  32 */   private static ArrayList<InstantShotTrail> trails = new ArrayList<>();
/*     */   
/*     */   public static void AddTrail(InstantShotTrail trail) {
/*  35 */     trails.add(trail);
/*     */   }
/*     */   
/*     */   public static void RenderAllTrails(float partialTicks) {
/*  39 */     for (InstantShotTrail trail : trails) {
/*  40 */       trail.Render(partialTicks);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void UpdateAllTrails() {
/*  45 */     for (int i = trails.size() - 1; i >= 0; i--) {
/*  46 */       if (((InstantShotTrail)trails.get(i)).Update())
/*  47 */         trails.remove(i); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class InstantShotTrail
/*     */   {
/*  53 */     private HashMap<String, ObjModel> modelCache = new HashMap<>();
/*     */     
/*     */     private Vector3f origin;
/*     */     private Vector3f hitPos;
/*     */     private float width;
/*     */     private float length;
/*     */     private float distanceToTarget;
/*     */     private float bulletSpeed;
/*     */     private int ticksExisted;
/*     */     private ResourceLocation texture;
/*     */     private String model;
/*     */     private boolean glow = false;
/*     */     
/*     */     public InstantShotTrail(GunType gunType, String model, String tex, boolean glowArg, Vector3f origin, Vector3f hitPos, float bulletSpeed, boolean isPunched) {
/*  67 */       this.ticksExisted = 0;
/*  68 */       this.bulletSpeed = bulletSpeed;
/*  69 */       this.origin = origin;
/*  70 */       this.hitPos = hitPos;
/*  71 */       this.length = 15.0F * (new Random()).nextFloat();
/*  72 */       if (!isPunched) {
/*  73 */         this.texture = new ResourceLocation("modularwarfare", "textures/skins/defaultbullettrail.png");
/*  74 */         this.width = 0.3F;
/*     */       } else {
/*  76 */         this.texture = new ResourceLocation("modularwarfare", "textures/skins/punchedbullettrail.png");
/*  77 */         this.width = 0.1F;
/*     */       } 
/*  79 */       if (gunType.customTrailTexture != null) {
/*  80 */         this.texture = new ResourceLocation("modularwarfare", "trail/textures/" + gunType.customTrailTexture);
/*  81 */         this.glow = gunType.customTrailGlow;
/*     */       } 
/*  83 */       if (gunType.customTrailModel != null) {
/*  84 */         this.model = "modularwarfare:trail/obj/" + gunType.customTrailModel;
/*  85 */         this.glow = gunType.customTrailGlow;
/*     */       } 
/*  87 */       if (model != null && !model.isEmpty()) {
/*  88 */         this.model = "modularwarfare:trail/obj/" + model;
/*  89 */         this.glow = glowArg;
/*     */       } 
/*  91 */       if (tex != null && !tex.isEmpty()) {
/*  92 */         this.texture = new ResourceLocation("modularwarfare", "trail/textures/" + tex);
/*  93 */         this.glow = glowArg;
/*     */       } 
/*     */ 
/*     */       
/*  97 */       Vector3f dPos = Vector3f.sub(hitPos, origin, null);
/*     */       
/*  99 */       RayTraceResult result = ModularWarfare.INSTANCE.RAY_CASTING.rayTraceBlocks((World)(Minecraft.func_71410_x()).field_71441_e, origin.toVec3(), hitPos.toVec3(), true, true, false);
/* 100 */       if (result != null && 
/* 101 */         result.field_72307_f != null) {
/* 102 */         dPos = Vector3f.sub(new Vector3f(result.field_72307_f), origin, null);
/*     */       }
/*     */       
/* 105 */       this.distanceToTarget = dPos.length();
/*     */       
/* 107 */       if (Math.abs(this.distanceToTarget) > 300.0F) {
/* 108 */         this.distanceToTarget = 300.0F;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean Update() {
/* 114 */       this.ticksExisted++;
/* 115 */       if (this.length > 0.0F) {
/* 116 */         this.length -= 0.05F;
/*     */       }
/* 118 */       return (this.ticksExisted * this.bulletSpeed >= this.distanceToTarget - this.length / 4.0F);
/*     */     }
/*     */     
/*     */     public void Render(float partialTicks) {
/* 122 */       float x_ = OpenGlHelper.lastBrightnessX;
/* 123 */       float y_ = OpenGlHelper.lastBrightnessY;
/*     */ 
/*     */ 
/*     */       
/* 127 */       if (InstantBulletRenderer.textureManager == null) {
/* 128 */         InstantBulletRenderer.textureManager = (Minecraft.func_71410_x()).field_71446_o;
/*     */       }
/* 130 */       InstantBulletRenderer.textureManager.func_110577_a(this.texture);
/*     */       
/* 132 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 134 */       GlStateManager.func_179094_E();
/*     */ 
/*     */       
/* 137 */       Entity camera = Minecraft.func_71410_x().func_175606_aa();
/* 138 */       double x = camera.field_70142_S + (camera.field_70165_t - camera.field_70142_S) * partialTicks;
/* 139 */       double y = camera.field_70137_T + (camera.field_70163_u - camera.field_70137_T) * partialTicks;
/* 140 */       double z = camera.field_70136_U + (camera.field_70161_v - camera.field_70136_U) * partialTicks;
/*     */       
/* 142 */       GL11.glTranslatef(-((float)x), -((float)y) + 0.1F, -((float)z));
/*     */       
/* 144 */       float parametric = (this.ticksExisted + partialTicks) * this.bulletSpeed;
/*     */       
/* 146 */       Vector3f dPos = Vector3f.sub(this.hitPos, this.origin, null);
/* 147 */       dPos.normalise();
/*     */       
/* 149 */       float startParametric = parametric - this.length * 0.5F;
/* 150 */       Vector3f startPos = new Vector3f(this.origin.x + dPos.x * startParametric, this.origin.y + dPos.y * startParametric, this.origin.z + dPos.z * startParametric);
/* 151 */       float endParametric = parametric + this.length * 0.5F;
/* 152 */       Vector3f endPos = new Vector3f(this.origin.x + dPos.x * endParametric, this.origin.y + dPos.y * endParametric, this.origin.z + dPos.z * endParametric);
/*     */       
/* 154 */       dPos.normalise();
/*     */       
/* 156 */       EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/* 157 */       Vector3f vectorToPlayer = new Vector3f(((EntityPlayer)entityPlayerSP).field_70165_t - this.hitPos.x, ((EntityPlayer)entityPlayerSP).field_70163_u - this.hitPos.y, ((EntityPlayer)entityPlayerSP).field_70161_v - this.hitPos.z);
/*     */       
/* 159 */       vectorToPlayer.normalise();
/*     */       
/* 161 */       Vector3f trailTangent = Vector3f.cross(dPos, vectorToPlayer, null);
/* 162 */       trailTangent.normalise();
/* 163 */       trailTangent.scale(-this.width * 0.5F);
/*     */       
/* 165 */       Vector3f normal = Vector3f.cross(trailTangent, dPos, null);
/* 166 */       normal.normalise();
/*     */       
/* 168 */       GlStateManager.func_179091_B();
/* 169 */       GL11.glNormal3f(normal.x, normal.y, normal.z);
/*     */       
/* 171 */       GL11.glEnable(3042);
/* 172 */       GL11.glEnable(2832);
/* 173 */       GL11.glHint(3153, 4353);
/*     */       
/* 175 */       if (this.model == null) {
/* 176 */         Tessellator tessellator = Tessellator.func_178181_a();
/* 177 */         tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*     */         
/* 179 */         tessellator.func_178180_c().func_181662_b((startPos.x + trailTangent.x), (startPos.y + trailTangent.y), (startPos.z + trailTangent.z)).func_187315_a(0.0D, 0.0D).func_181675_d();
/* 180 */         tessellator.func_178180_c().func_181662_b((startPos.x - trailTangent.x), (startPos.y - trailTangent.y), (startPos.z - trailTangent.z)).func_187315_a(0.0D, 1.0D).func_181675_d();
/* 181 */         tessellator.func_178180_c().func_181662_b((endPos.x - trailTangent.x), (endPos.y - trailTangent.y), (endPos.z - trailTangent.z)).func_187315_a(1.0D, 1.0D).func_181675_d();
/* 182 */         tessellator.func_178180_c().func_181662_b((endPos.x + trailTangent.x), (endPos.y + trailTangent.y), (endPos.z + trailTangent.z)).func_187315_a(1.0D, 0.0D).func_181675_d();
/*     */         
/* 184 */         tessellator.func_78381_a();
/*     */       } else {
/* 186 */         if (this.glow) {
/* 187 */           GlStateManager.func_179140_f();
/* 188 */           OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/*     */         } 
/* 190 */         GlStateManager.func_179094_E();
/*     */         
/* 192 */         Vector3f dVec = new Vector3f(this.hitPos.x - this.origin.x, 0.0F, this.hitPos.z - this.origin.z);
/* 193 */         dVec = (Vector3f)dVec.normalise();
/*     */         
/* 195 */         GlStateManager.func_179109_b(endPos.x, endPos.y, endPos.z);
/* 196 */         float yaw = (float)Math.acos(dVec.z) / 3.1415F * 180.0F;
/* 197 */         if (dVec.x < 0.0F) {
/* 198 */           yaw = -yaw;
/*     */         }
/* 200 */         dVec = new Vector3f(this.hitPos.x - this.origin.x, this.hitPos.y - this.origin.y, this.hitPos.z - this.origin.z);
/* 201 */         dVec = (Vector3f)dVec.normalise();
/* 202 */         float pitch = (float)Math.asin(dVec.y) / 3.1415F * 180.0F;
/*     */         
/* 204 */         GlStateManager.func_179114_b(yaw, 0.0F, 1.0F, 0.0F);
/* 205 */         GlStateManager.func_179114_b(pitch, -1.0F, 0.0F, 0.0F);
/*     */         
/* 207 */         ObjModel obj = this.modelCache.get(this.model);
/* 208 */         if (obj == null) {
/* 209 */           this.modelCache.put(this.model, ObjModelLoader.load(new ResourceLocation(this.model)));
/* 210 */           obj = this.modelCache.get(this.model);
/*     */         } 
/* 212 */         obj.renderAll(1.0F);
/* 213 */         GlStateManager.func_179121_F();
/* 214 */         if (this.glow) {
/* 215 */           OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, x_, y_);
/* 216 */           GlStateManager.func_179145_e();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 221 */       GL11.glDisable(3042);
/* 222 */       GL11.glDisable(2832);
/*     */       
/* 224 */       GlStateManager.func_179101_C();
/*     */       
/* 226 */       GlStateManager.func_179121_F();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\model\InstantBulletRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */