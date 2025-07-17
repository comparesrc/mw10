/*     */ package com.modularwarfare.client.fpp.enhanced;
/*     */ 
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.annotations.JsonAdapter;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ @JsonAdapter(AnimationType.AnimationTypeJsonAdapter.class)
/*     */ public enum AnimationType
/*     */ {
/*  14 */   DEFAULT("default"),
/*  15 */   DEFAULT_EMPTY("defaultEmpty"),
/*  16 */   DRAW("draw"),
/*  17 */   DRAW_EMPTY("drawEmpty"),
/*  18 */   AIM("aim"),
/*  19 */   INSPECT("inspect"),
/*  20 */   INSPECT_EMPTY("inspectEmpty"),
/*  21 */   PRE_LOAD("preLoad"),
/*  22 */   LOAD("load"),
/*  23 */   POST_LOAD("postLoad"),
/*  24 */   PRE_UNLOAD("preUnload"),
/*  25 */   UNLOAD("unload"),
/*  26 */   POST_UNLOAD("postUnload"),
/*  27 */   PRE_RELOAD("preReload"),
/*  28 */   PRE_RELOAD_EMPTY("preReloadEmpty"),
/*  29 */   RELOAD_FIRST("reloadFirst"),
/*  30 */   RELOAD_FIRST_EMPTY("reloadFirstEmpty"),
/*  31 */   RELOAD_SECOND("reloadSecond"),
/*  32 */   RELOAD_SECOND_EMPTY("reloadSecondEmpty"),
/*  33 */   RELOAD_FIRST_QUICKLY("reloadFirstQuickly"),
/*  34 */   RELOAD_SECOND_QUICKLY("reloadSecondQuickly"),
/*  35 */   POST_RELOAD("postReload"),
/*  36 */   POST_RELOAD_EMPTY("postReloadEmpty"),
/*  37 */   PRE_FIRE("preFire"),
/*  38 */   FIRE("fire"),
/*  39 */   FIRE_LAST("fireLast"),
/*  40 */   POST_FIRE("postFire"),
/*  41 */   POST_FIRE_EMPTY("postFireEmpty"),
/*  42 */   MODE_CHANGE("modeChange"),
/*  43 */   SPRINT("sprint"),
/*  44 */   CUSTOM("custom"),
/*     */   
/*  46 */   CUSTOM1("custom1"),
/*  47 */   CUSTOM2("custom2"),
/*  48 */   CUSTOM3("custom3"),
/*  49 */   CUSTOM4("custom4"),
/*  50 */   CUSTOM5("custom5"),
/*  51 */   CUSTOM6("custom6"),
/*  52 */   CUSTOM7("custom7"),
/*  53 */   CUSTOM8("custom8"),
/*  54 */   PRIMARY_SKILL("primarySkill"),
/*  55 */   SECONDARY_SKILL("secondarySkill");
/*     */   public String serializedName;
/*     */   
/*     */   AnimationType(String name) {
/*  59 */     this.serializedName = name;
/*     */   }
/*     */   
/*     */   public boolean showFlashModel() {
/*  63 */     if (this == FIRE) {
/*  64 */       return true;
/*     */     }
/*  66 */     if (this == FIRE_LAST) {
/*  67 */       return true;
/*     */     }
/*  69 */     return false;
/*     */   }
/*     */   
/*     */   public static class AnimationTypeJsonAdapter
/*     */     extends TypeAdapter<AnimationType>
/*     */   {
/*     */     public AnimationType read(JsonReader in) throws IOException {
/*  76 */       if (in.peek() == JsonToken.NULL) {
/*  77 */         in.nextNull();
/*  78 */         throw new AnimationTypeException("wrong animation type format");
/*     */       } 
/*  80 */       return fromString(in.nextString());
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(JsonWriter out, AnimationType t) throws IOException {
/*  85 */       out.value(t.serializedName);
/*     */     }
/*     */     
/*     */     public static class AnimationTypeException extends RuntimeException {
/*     */       public AnimationTypeException(String str) {
/*  90 */         super(str);
/*     */       }
/*     */     }
/*     */     
/*     */     public static AnimationType fromString(String modeName) {
/*  95 */       for (AnimationType animationType : AnimationType.values()) {
/*  96 */         if (animationType.serializedName.equalsIgnoreCase(modeName)) {
/*  97 */           return animationType;
/*     */         }
/*     */       } 
/* 100 */       throw new AnimationTypeException("wrong animation type:" + modeName);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\enhanced\AnimationType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */