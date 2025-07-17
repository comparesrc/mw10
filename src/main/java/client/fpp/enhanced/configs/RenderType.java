/*    */ package com.modularwarfare.client.fpp.enhanced.configs;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.annotations.JsonAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ @JsonAdapter(RenderType.RenderTypeJsonAdapter.class)
/*    */ public enum RenderType
/*    */ {
/* 15 */   PLAYER("player"), ITEMLOOT("itemloot"), ITEMFRAME("itemframe");
/*    */   public String serializedName;
/*    */   
/*    */   RenderType(String serializedName) {
/* 19 */     this.serializedName = serializedName;
/*    */   }
/*    */   
/*    */   public static class RenderTypeJsonAdapter
/*    */     extends TypeAdapter<RenderType>
/*    */   {
/*    */     public void write(JsonWriter out, RenderType value) throws IOException {
/* 26 */       out.value(value.serializedName);
/*    */     }
/*    */ 
/*    */     
/*    */     public RenderType read(JsonReader in) throws IOException {
/* 31 */       if (in.peek() == JsonToken.NULL) {
/* 32 */         in.nextNull();
/* 33 */         throw new RuntimeException("wrong render type format");
/*    */       } 
/* 35 */       String name = in.nextString();
/* 36 */       for (RenderType t : RenderType.values()) {
/* 37 */         if (name.equals(t.serializedName)) {
/* 38 */           return t;
/*    */         }
/*    */       } 
/* 41 */       throw new RuntimeException("wrong render type:" + name);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\enhanced\configs\RenderType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */