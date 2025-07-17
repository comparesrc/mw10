/*    */ package com.modularwarfare.script;
/*    */ 
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagInt;
/*    */ 
/*    */ public class NBTSearcher
/*    */ {
/*    */   public NBTBase search(NBTTagCompound tag, String key, int type) {
/* 12 */     String[] keys = key.split("\\.");
/* 13 */     if (keys.length == 0) {
/* 14 */       return null;
/*    */     }
/* 16 */     if (keys.length == 1) {
/* 17 */       NBTBase nBTBase = tag.func_74781_a(keys[0]);
/* 18 */       if (nBTBase == null || nBTBase.func_74732_a() != type) {
/* 19 */         return null;
/*    */       }
/* 21 */       return nBTBase;
/*    */     } 
/*    */     
/* 24 */     NBTTagCompound nbt = tag.func_74775_l(keys[0]);
/* 25 */     for (int i = 1; i < keys.length - 1; i++) {
/* 26 */       nbt = nbt.func_74775_l(keys[i]);
/*    */     }
/* 28 */     NBTBase base = nbt.func_74781_a(keys[keys.length - 1]);
/* 29 */     if (base == null) {
/* 30 */       return null;
/*    */     }
/* 32 */     if (base.func_74732_a() == type) {
/* 33 */       return base;
/*    */     }
/* 35 */     return null;
/*    */   }
/*    */   
/*    */   public int searchInt(NBTTagCompound tag, String key) {
/* 39 */     NBTBase base = search(tag, key, 3);
/* 40 */     if (base instanceof NBTTagInt) {
/* 41 */       return ((NBTTagInt)base).func_150287_d();
/*    */     }
/* 43 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound readJson(String json) throws NBTException {
/* 48 */     return JsonToNBT.func_180713_a(json);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\script\NBTSearcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */