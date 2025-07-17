/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public enum PotionEffectEnum
/*    */ {
/*  9 */   SPEED,
/* 10 */   SLOWNESS,
/* 11 */   HASTE,
/* 12 */   MINING_FATIGUE,
/* 13 */   STRENGTH,
/* 14 */   INSTANT_HEALTH,
/* 15 */   INSTANT_DAMAGE,
/* 16 */   JUMP_BOOST,
/* 17 */   NAUSEA,
/* 18 */   REGENERATION,
/* 19 */   RESISTANCE,
/* 20 */   FIRE_RESISTANCE,
/* 21 */   WATER_BREATHING,
/* 22 */   INVISIBILITY,
/* 23 */   BLINDNESS,
/* 24 */   NIGHT_VISION,
/* 25 */   HUNGER,
/* 26 */   WEAKNESS,
/* 27 */   POISON,
/* 28 */   WITHER,
/* 29 */   HEALTH_BOOST,
/* 30 */   ABSORPTION,
/* 31 */   SATURATION,
/* 32 */   GLOWING,
/* 33 */   LEVITATION,
/* 34 */   LUCK,
/* 35 */   UNLUCK;
/*    */   
/*    */   public Potion getPotion() {
/* 38 */     String name = name().toLowerCase();
/* 39 */     Potion potion = (Potion)Potion.field_188414_b.func_82594_a(new ResourceLocation(name));
/* 40 */     if (potion == null) {
/* 41 */       throw new IllegalStateException("Invalid MobEffect requested: " + name);
/*    */     }
/* 43 */     return potion;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\PotionEffectEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */