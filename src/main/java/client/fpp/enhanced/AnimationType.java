/*    */ package com.modularwarfare.client.fpp.enhanced;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.annotations.JsonAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ @JsonAdapter(AnimationType.AnimationTypeJsonAdapter.class)
/*    */ public enum AnimationType
/*    */ {
/* 14 */   DEFAULT("default"),
/* 15 */   DEFAULT_EMPTY("defaultEmpty"),
/* 16 */   DRAW("draw"),
/* 17 */   DRAW_EMPTY("drawEmpty"),
/* 18 */   AIM("aim"),
/* 19 */   INSPECT("inspect"),
/* 20 */   INSPECT_EMPTY("inspectEmpty"),
/* 21 */   PRE_LOAD("preLoad"),
/* 22 */   LOAD("load"),
/* 23 */   POST_LOAD("postLoad"),
/* 24 */   PRE_UNLOAD("preUnload"),
/* 25 */   UNLOAD("unload"),
/* 26 */   POST_UNLOAD("postUnload"),
/* 27 */   PRE_RELOAD("preReload"),
/* 28 */   PRE_RELOAD_EMPTY("preReloadEmpty"),
/* 29 */   RELOAD_FIRST("reloadFirst"),
/* 30 */   RELOAD_FIRST_EMPTY("reloadFirstEmpty"),
/* 31 */   RELOAD_SECOND("reloadSecond"),
/* 32 */   RELOAD_SECOND_EMPTY("reloadSecondEmpty"),
/* 33 */   RELOAD_FIRST_QUICKLY("reloadFirstQuickly"),
/* 34 */   RELOAD_SECOND_QUICKLY("reloadSecondQuickly"),
/* 35 */   POST_RELOAD("postReload"),
/* 36 */   POST_RELOAD_EMPTY("postReloadEmpty"),
/* 37 */   PRE_FIRE("preFire"),
/* 38 */   FIRE("fire"),
/* 39 */   FIRE_LAST("fireLast"),
/* 40 */   POST_FIRE("postFire"),
/* 41 */   POST_FIRE_EMPTY("postFireEmpty"),
/* 42 */   MODE_CHANGE("modeChange"),
/* 43 */   SPRINT("sprint"),
/* 44 */   PRIMARY_SKILL("primarySkill"),
/* 45 */   SECONDARY_SKILL("secondarySkill");
/*    */   public String serializedName;
/*    */   
/*    */   AnimationType(String name) {
/* 49 */     this.serializedName = name;
/*    */   }
/*    */   
/*    */   public static class AnimationTypeJsonAdapter
/*    */     extends TypeAdapter<AnimationType>
/*    */   {
/*    */     public AnimationType read(JsonReader in) throws IOException {
/* 56 */       if (in.peek() == JsonToken.NULL) {
/* 57 */         in.nextNull();
/* 58 */         throw new AnimationTypeException("wrong animation type format");
/*    */       } 
/* 60 */       return fromString(in.nextString());
/*    */     }
/*    */ 
/*    */     
/*    */     public void write(JsonWriter out, AnimationType t) throws IOException {
/* 65 */       out.value(t.serializedName);
/*    */     }
/*    */     
/*    */     public static class AnimationTypeException extends RuntimeException {
/*    */       public AnimationTypeException(String str) {
/* 70 */         super(str);
/*    */       }
/*    */     }
/*    */     
/*    */     public static AnimationType fromString(String modeName) {
/* 75 */       for (AnimationType animationType : AnimationType.values()) {
/* 76 */         if (animationType.serializedName.equalsIgnoreCase(modeName)) {
/* 77 */           return animationType;
/*    */         }
/*    */       } 
/* 80 */       throw new AnimationTypeException("wrong animation type:" + modeName);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\enhanced\AnimationType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */