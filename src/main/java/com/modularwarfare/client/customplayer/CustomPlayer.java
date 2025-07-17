/*     */ package com.modularwarfare.client.customplayer;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import mchhui.hegltf.GltfDataModel;
/*     */ import mchhui.hegltf.GltfRenderModel;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomPlayer
/*     */ {
/*  15 */   private static HashMap<String, GltfRenderModel> models = new HashMap<>();
/*     */   
/*     */   private CustomPlayerConfig config;
/*     */   
/*     */   private float IDLE;
/*     */   private float WALK;
/*     */   private float SPRINT;
/*     */   private float LEAN_RIGHT;
/*     */   private float LEAN_LEFT;
/*     */   private float ATTACK_IDLE;
/*     */   private float GUN_IDLE;
/*     */   private float SNEAK_IDLE;
/*     */   private float CRAWL_IDLE;
/*     */   private float SNEAK;
/*     */   private float CRAWL;
/*     */   private float SLIDE;
/*     */   private float DRAW;
/*     */   private float AIM;
/*     */   private float SHOT;
/*     */   private float RELOAD;
/*     */   private float SWING;
/*  36 */   private long lastSyncTime = 0L;
/*     */   
/*  38 */   private static HashMap<String, CustomPlayer> playerMap = new HashMap<>();
/*     */   
/*     */   public static CustomPlayer getCustomPlayer(String name) {
/*  41 */     CustomPlayer player = playerMap.get(name);
/*  42 */     if (player == null) {
/*  43 */       player = new CustomPlayer();
/*  44 */       playerMap.put(name, player);
/*     */     } 
/*  46 */     return player;
/*     */   }
/*     */   
/*     */   public void bind(CustomPlayerConfig config) {
/*  50 */     this.config = config;
/*     */   }
/*     */   
/*     */   public boolean updateAnimation(GltfRenderModel model, EntityPlayer player, float ptick) {
/*  54 */     if (this.config == null) {
/*  55 */       return false;
/*     */     }
/*  57 */     if (this.lastSyncTime == 0L) {
/*  58 */       this.lastSyncTime = System.currentTimeMillis();
/*  59 */       return false;
/*     */     } 
/*     */     
/*  62 */     long time = System.currentTimeMillis();
/*  63 */     float stepTick = (float)(time - this.lastSyncTime) / 16.666666F;
/*  64 */     CustomPlayerConfig.Animation ani = this.config.animations.get("default");
/*  65 */     this.IDLE = (float)(this.IDLE + ani.getSpeed(this.config.FPS) * stepTick);
/*  66 */     if (this.IDLE > 1.0F) {
/*  67 */       this.IDLE = 0.0F;
/*     */     }
/*     */ 
/*     */     
/*  71 */     ani = this.config.animations.get("walk");
/*  72 */     if (this.WALK == 1.0F) {
/*  73 */       this.WALK = 0.0F;
/*     */     }
/*  75 */     if (player.field_70140_Q - player.field_70141_P > 0.1F) {
/*  76 */       this.WALK = (float)(this.WALK + ani.getSpeed(this.config.FPS) * stepTick);
/*     */     } else {
/*  78 */       this.WALK = 0.0F;
/*     */     } 
/*  80 */     if (this.WALK > 1.0F) {
/*  81 */       this.WALK = 1.0F;
/*     */     }
/*     */     
/*  84 */     ani = this.config.animations.get("sprint");
/*  85 */     if (this.SPRINT == 1.0F) {
/*  86 */       this.SPRINT = 0.0F;
/*     */     }
/*  88 */     if (player.field_70140_Q != player.field_70141_P && player.func_70051_ag()) {
/*  89 */       this.SPRINT = (float)(this.SPRINT + ani.getSpeed(this.config.FPS) * stepTick);
/*  90 */       this.WALK = 0.0F;
/*     */     } else {
/*  92 */       this.SPRINT = 0.0F;
/*     */     } 
/*  94 */     if (this.SPRINT > 1.0F) {
/*  95 */       this.SPRINT = 1.0F;
/*     */     }
/*     */     
/*  98 */     ani = this.config.animations.get("sneak_idle");
/*  99 */     if (this.SNEAK_IDLE == 1.0F) {
/* 100 */       this.SNEAK_IDLE = 0.0F;
/*     */     }
/* 102 */     if (player.func_70093_af()) {
/* 103 */       this.SNEAK_IDLE = (float)(this.SNEAK_IDLE + ani.getSpeed(this.config.FPS) * stepTick);
/*     */     } else {
/* 105 */       this.SNEAK_IDLE = 0.0F;
/*     */     } 
/* 107 */     if (this.SNEAK_IDLE > 1.0F) {
/* 108 */       this.SNEAK_IDLE = 1.0F;
/*     */     }
/*     */     
/* 111 */     ani = this.config.animations.get("sneak");
/* 112 */     if (this.SNEAK == 1.0F) {
/* 113 */       this.SNEAK = 0.0F;
/*     */     }
/* 115 */     if ((player.field_70165_t != player.field_70169_q || player.field_70161_v != player.field_70166_s) && player.func_70093_af()) {
/* 116 */       this.SNEAK = (float)(this.SNEAK + ani.getSpeed(this.config.FPS) * stepTick);
/*     */     } else {
/* 118 */       this.SNEAK = 0.0F;
/*     */     } 
/* 120 */     if (this.SNEAK > 1.0F) {
/* 121 */       this.SNEAK = 1.0F;
/*     */     }
/* 123 */     double frame = 0.0D;
/* 124 */     if (this.SNEAK > 0.0F) {
/* 125 */       ani = this.config.animations.get("sneak");
/* 126 */       frame = ani.getStartTime(this.config.FPS) + this.SNEAK * (ani.getEndTime(this.config.FPS) - ani.getStartTime(this.config.FPS));
/* 127 */     } else if (this.SNEAK_IDLE > 0.0F) {
/* 128 */       ani = this.config.animations.get("sneak_idle");
/*     */       
/* 130 */       frame = ani.getStartTime(this.config.FPS) + this.SNEAK_IDLE * (ani.getEndTime(this.config.FPS) - ani.getStartTime(this.config.FPS));
/* 131 */     } else if (this.SPRINT > 0.0F) {
/* 132 */       ani = this.config.animations.get("sprint");
/* 133 */       frame = ani.getStartTime(this.config.FPS) + this.SPRINT * (ani.getEndTime(this.config.FPS) - ani.getStartTime(this.config.FPS));
/* 134 */     } else if (this.WALK > 0.0F) {
/* 135 */       ani = this.config.animations.get("walk");
/* 136 */       frame = ani.getStartTime(this.config.FPS) + this.WALK * (ani.getEndTime(this.config.FPS) - ani.getStartTime(this.config.FPS));
/*     */     } else {
/* 138 */       ani = this.config.animations.get("default");
/* 139 */       frame = ani.getStartTime(this.config.FPS) + this.IDLE * (ani.getEndTime(this.config.FPS) - ani.getStartTime(this.config.FPS));
/*     */     } 
/* 141 */     model.updateAnimation((float)frame, true);
/* 142 */     this.lastSyncTime = time;
/* 143 */     return true;
/*     */   }
/*     */   
/*     */   public void render(EntityPlayer player, float ptick) {
/* 147 */     if (this.config == null) {
/*     */       return;
/*     */     }
/* 150 */     Minecraft.func_71410_x().func_110434_K()
/* 151 */       .func_110577_a(new ResourceLocation("modularwarfare", "skins/customplayer/" + this.config.tex));
/* 152 */     GltfRenderModel model = models.get(this.config.model);
/* 153 */     if (model == null) {
/*     */       
/* 155 */       model = new GltfRenderModel(GltfDataModel.load(new ResourceLocation("modularwarfare", "gltf/customplayer/" + this.config.model)));
/* 156 */       models.put(this.config.model, model);
/*     */     } 
/* 158 */     if (updateAnimation(model, player, ptick))
/* 159 */       model.renderAll(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\customplayer\CustomPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */