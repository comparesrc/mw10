/*     */ package com.modularwarfare.common.init;
/*     */ 
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraftforge.event.RegistryEvent;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
/*     */ import net.minecraftforge.registries.IForgeRegistry;
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
/*     */ @EventBusSubscriber(modid = "modularwarfare")
/*     */ @ObjectHolder("modularwarfare")
/*     */ public class ModSounds
/*     */ {
/*     */   public static SoundEvent STEP_GRASS_WALK;
/*     */   public static SoundEvent STEP_GRASS_SPRINT;
/*     */   public static SoundEvent STEP_STONE_WALK;
/*     */   public static SoundEvent STEP_STONE_SPRINT;
/*     */   public static SoundEvent STEP_GRAVEL_WALK;
/*     */   public static SoundEvent STEP_GRAVEL_SPRINT;
/*     */   public static SoundEvent STEP_METAL_WALK;
/*     */   public static SoundEvent STEP_METAL_SPRINT;
/*     */   public static SoundEvent STEP_WOOD_WALK;
/*     */   public static SoundEvent STEP_WOOD_SPRINT;
/*     */   public static SoundEvent STEP_SAND_WALK;
/*     */   public static SoundEvent STEP_SAND_SPRINT;
/*     */   public static SoundEvent STEP_SNOW_WALK;
/*     */   public static SoundEvent STEP_SNOW_SPRINT;
/*     */   public static SoundEvent EQUIP_EXTRA;
/*     */   public static SoundEvent GRENADE_THROW;
/*     */   public static SoundEvent GRENADE_HIT;
/*     */   public static SoundEvent GRENADE_ARM;
/*     */   public static SoundEvent GRENADE_SMOKE;
/*     */   public static SoundEvent GRENADE_STUN;
/*     */   public static SoundEvent FLASHED;
/*     */   public static SoundEvent EXPLOSIONS_CLOSE;
/*     */   public static SoundEvent EXPLOSIONS_DISTANT;
/*     */   public static SoundEvent EXPLOSIONS_FAR;
/*     */   public static SoundEvent WHISTLE;
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
/*  55 */     registerSound(event.getRegistry(), "human.step.grass.walk");
/*  56 */     registerSound(event.getRegistry(), "human.step.grass.sprint");
/*     */     
/*  58 */     registerSound(event.getRegistry(), "human.step.stone.walk");
/*  59 */     registerSound(event.getRegistry(), "human.step.stone.sprint");
/*     */     
/*  61 */     registerSound(event.getRegistry(), "human.step.gravel.walk");
/*  62 */     registerSound(event.getRegistry(), "human.step.gravel.sprint");
/*     */     
/*  64 */     registerSound(event.getRegistry(), "human.step.metal.walk");
/*  65 */     registerSound(event.getRegistry(), "human.step.metal.sprint");
/*     */     
/*  67 */     registerSound(event.getRegistry(), "human.step.wood.walk");
/*  68 */     registerSound(event.getRegistry(), "human.step.wood.sprint");
/*     */     
/*  70 */     registerSound(event.getRegistry(), "human.step.sand.walk");
/*  71 */     registerSound(event.getRegistry(), "human.step.sand.sprint");
/*     */     
/*  73 */     registerSound(event.getRegistry(), "human.step.snow.walk");
/*  74 */     registerSound(event.getRegistry(), "human.step.snow.sprint");
/*     */     
/*  76 */     registerSound(event.getRegistry(), "human.equip.extra");
/*     */     
/*  78 */     registerSound(event.getRegistry(), "grenade_throw");
/*  79 */     registerSound(event.getRegistry(), "grenade_hit");
/*  80 */     registerSound(event.getRegistry(), "grenade_arm");
/*     */     
/*  82 */     registerSound(event.getRegistry(), "explosions.close");
/*  83 */     registerSound(event.getRegistry(), "explosions.distant");
/*  84 */     registerSound(event.getRegistry(), "explosions.far");
/*     */     
/*  86 */     registerSound(event.getRegistry(), "human.other.whistle");
/*     */ 
/*     */     
/*  89 */     registerSound(event.getRegistry(), "smoke_grenade");
/*     */     
/*  91 */     registerSound(event.getRegistry(), "stun_grenade");
/*  92 */     registerSound(event.getRegistry(), "flashed");
/*     */     
/*  94 */     STEP_GRASS_WALK = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.grass.walk"));
/*  95 */     STEP_GRASS_SPRINT = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.grass.sprint"));
/*     */     
/*  97 */     STEP_STONE_WALK = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.stone.walk"));
/*  98 */     STEP_STONE_SPRINT = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.stone.sprint"));
/*     */     
/* 100 */     STEP_GRAVEL_WALK = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.gravel.walk"));
/* 101 */     STEP_GRAVEL_SPRINT = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.gravel.sprint"));
/*     */     
/* 103 */     STEP_METAL_WALK = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.metal.walk"));
/* 104 */     STEP_METAL_SPRINT = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.metal.sprint"));
/*     */     
/* 106 */     STEP_WOOD_WALK = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.wood.walk"));
/* 107 */     STEP_WOOD_SPRINT = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.wood.sprint"));
/*     */     
/* 109 */     STEP_SAND_WALK = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.sand.walk"));
/* 110 */     STEP_SAND_SPRINT = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.sand.sprint"));
/*     */     
/* 112 */     STEP_SNOW_WALK = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.snow.walk"));
/* 113 */     STEP_SNOW_SPRINT = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.step.snow.sprint"));
/*     */     
/* 115 */     EQUIP_EXTRA = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.equip.extra"));
/*     */     
/* 117 */     GRENADE_THROW = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "grenade_throw"));
/* 118 */     GRENADE_HIT = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "grenade_hit"));
/* 119 */     GRENADE_ARM = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "grenade_arm"));
/* 120 */     GRENADE_SMOKE = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "smoke_grenade"));
/*     */     
/* 122 */     GRENADE_STUN = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "stun_grenade"));
/* 123 */     FLASHED = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "flashed"));
/*     */     
/* 125 */     EXPLOSIONS_CLOSE = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "explosions.close"));
/* 126 */     EXPLOSIONS_DISTANT = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "explosions.distant"));
/* 127 */     EXPLOSIONS_FAR = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "explosions.far"));
/*     */ 
/*     */     
/* 130 */     WHISTLE = (SoundEvent)SoundEvent.field_187505_a.func_82594_a(new ResourceLocation("modularwarfare", "human.other.whistle"));
/*     */   }
/*     */   
/*     */   public static void registerSound(IForgeRegistry<SoundEvent> r, String name) {
/* 134 */     ResourceLocation loc = new ResourceLocation("modularwarfare", name);
/* 135 */     r.register((new SoundEvent(loc)).setRegistryName(loc));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\init\ModSounds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */