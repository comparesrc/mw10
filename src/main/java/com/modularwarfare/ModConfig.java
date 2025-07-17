/*     */ package com.modularwarfare;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.vecmath.Vector3f;
/*     */ 
/*     */ public class ModConfig {
/*     */   public static transient ModConfig INSTANCE;
/*     */   public General general;
/*     */   public Client client;
/*     */   public Shots shots;
/*     */   public Guns guns;
/*     */   public Drops drops;
/*     */   public Hud hud;
/*     */   public Walk walks_sounds;
/*     */   public Casings casings_drops;
/*     */   public KillFeed killFeed;
/*     */   public boolean model_optimization;
/*     */   public boolean debug_hits_message;
/*     */   public boolean dev_mode;
/*     */   public String version;
/*     */   
/*     */   public ModConfig() {
/*  33 */     this.general = new General();
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
/*     */ 
/*     */ 
/*     */     
/*  49 */     this.client = new Client();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     this.shots = new Shots();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     this.guns = new Guns();
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
/*     */ 
/*     */     
/*  77 */     this.drops = new Drops();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     this.hud = new Hud();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     this.walks_sounds = new Walk();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     this.casings_drops = new Casings();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     this.killFeed = new KillFeed();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     this.model_optimization = true;
/* 125 */     this.debug_hits_message = false;
/* 126 */     this.dev_mode = false;
/*     */     
/* 128 */     this.version = "2023.2.4.4f"; this.general = new General(); this.client = new Client(); this.shots = new Shots(); this.guns = new Guns(); this.drops = new Drops(); this.hud = new Hud(); this.walks_sounds = new Walk(); this.casings_drops = new Casings(); this.killFeed = new KillFeed(); } public ModConfig(File configFile) { this.general = new General(); this.client = new Client(); this.shots = new Shots(); this.guns = new Guns(); this.drops = new Drops(); this.hud = new Hud(); this.walks_sounds = new Walk(); this.casings_drops = new Casings(); this.killFeed = new KillFeed(); this.model_optimization = true; this.debug_hits_message = false; this.dev_mode = false; this.version = "2023.2.4.4f";
/*     */ 
/*     */     
/* 131 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/*     */     try {
/* 133 */       if (configFile.exists()) {
/* 134 */         JsonReader jsonReader = new JsonReader(new FileReader(configFile));
/* 135 */         ModConfig config = (ModConfig)gson.fromJson(jsonReader, ModConfig.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 145 */         INSTANCE = config;
/* 146 */         System.out.println("test:" + config.client);
/*     */       } else {
/* 148 */         try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8")) {
/* 149 */           gson.toJson(this, writer);
/*     */         } 
/* 151 */         INSTANCE = this;
/*     */       } 
/* 153 */     } catch (Exception e) {
/* 154 */       e.printStackTrace();
/*     */     }  }
/*     */ 
/*     */   
/*     */   public static class General {
/*     */     public boolean customInventory = true;
/*     */     public boolean prototype_pack_extraction = true;
/*     */     public boolean animated_pack_extraction = true;
/*     */     public boolean modified_pack_server_kick = true;
/*     */     public boolean directory_pack_server_kick = true;
/*     */     public ArrayList<String> content_pack_hash_list = new ArrayList<>();
/*     */     public boolean drop_extra_slots_on_death = true;
/*     */     public float playerShadowOffset = 1.0F;
/*     */   }
/*     */   
/*     */   public static class Client {
/*     */     public boolean hideSecondSkinWhenDressed = true;
/*     */   }
/*     */   
/*     */   public static class Shots {
/*     */     public boolean shot_break_glass = false;
/*     */     public boolean knockback_entity_damage = false;
/*     */   }
/*     */   
/*     */   public static class Guns {
/*     */     public boolean guns_interaction_hand = true;
/*     */     public List<String> anim_guns_show_default_objects = Arrays.asList(new String[] { 
/*     */           "ammoModel", "leftArmModel", "leftArmLayerModel", "leftArmSlimModel", "leftArmLayerSlimModel", "rightArmModel", "rightArmLayerModel", "rightArmSlimModel", "rightArmLayerSlimModel", "flashModel", 
/*     */           "smokeModel", "sprint_righthand", "sprint_lefthand", "selector_semi", "selector_full", "selector_brust", "bulletModel" });
/*     */   }
/*     */   
/*     */   public static class Drops {
/*     */     public boolean advanced_drops_models = true;
/*     */     public int drops_despawn_time = 120;
/*     */     public boolean advanced_drops_models_everything = false;
/*     */   }
/*     */   
/*     */   public static class Hud {
/*     */     public boolean hitmarkers = true;
/*     */     public boolean enable_crosshair = true;
/*     */     public boolean dynamic_crosshair = true;
/*     */     public boolean ammo_count = true;
/*     */     public boolean snap_fade_hit = true;
/*     */     public boolean isDynamicFov = false;
/*     */     public boolean ads_blur = false;
/*     */     public float handDepthRangeMax = 0.6F;
/*     */     public float handDepthRangeMin = 0.0F;
/*     */     public Vector3f projectionScale = new Vector3f(0.125F, 0.125F, 0.125F);
/*     */     public float eraseScopeDepth = 1.0F;
/*     */     public int shadersColorTexID = 0;
/*     */     public boolean alwaysRenderPIPWorld = false;
/*     */     public boolean autoSwitchToFirstView = true;
/*     */   }
/*     */   
/*     */   public static class Walk {
/*     */     public boolean walk_sounds = true;
/*     */     public float volume = 0.3F;
/*     */   }
/*     */   
/*     */   public static class Casings {
/*     */     public int despawn_time = 10;
/*     */   }
/*     */   
/*     */   public static class KillFeed {
/*     */     public boolean enableKillFeed = true;
/*     */     public boolean sendDefaultKillMessage = false;
/*     */     public int messageDuration = 10;
/*     */     public List<String> messageList = Arrays.asList(new String[] { "&a{killer} &dkilled &c{victim}", "&a{killer} &fdestroyed &c{victim}", "&a{killer} &fshot &c{victim}" });
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\ModConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */