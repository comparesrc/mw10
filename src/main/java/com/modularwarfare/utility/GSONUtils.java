/*    */ package com.modularwarfare.utility;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GSONUtils
/*    */ {
/*    */   @Nullable
/*    */   public static <T> T fromJson(Gson p_193838_0_, JsonReader jsonreader, Type p_193838_2_, String name) {
/*    */     try {
/* 19 */       return (T)p_193838_0_.getAdapter(TypeToken.get(p_193838_2_)).read(jsonreader);
/* 20 */     } catch (IOException ioexception) {
/* 21 */       ModularWarfare.LOGGER.warn("[ModularWarfare] JSON Error reading file: " + name);
/* 22 */       throw new JsonParseException(ioexception);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfar\\utility\GSONUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */