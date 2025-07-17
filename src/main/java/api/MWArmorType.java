/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ 
/*    */ public enum MWArmorType
/*    */ {
/*  9 */   Head(new int[0]),
/* 10 */   Chest(new int[0]),
/* 11 */   Legs(new int[0]),
/* 12 */   Feet(new int[0]),
/* 13 */   Vest(new int[] { 1 });
/*    */   
/*    */   int[] validSlots;
/*    */   
/*    */   public static MWArmorType fromVanillaSlot(EntityEquipmentSlot entityEquipmentSlot) {
/* 18 */     if (entityEquipmentSlot == EntityEquipmentSlot.HEAD) {
/* 19 */       return Head;
/*    */     }
/* 21 */     if (entityEquipmentSlot == EntityEquipmentSlot.CHEST) {
/* 22 */       return Chest;
/*    */     }
/* 24 */     if (entityEquipmentSlot == EntityEquipmentSlot.LEGS) {
/* 25 */       return Legs;
/*    */     }
/* 27 */     if (entityEquipmentSlot == EntityEquipmentSlot.FEET) {
/* 28 */       return Feet;
/*    */     }
/* 30 */     return null;
/*    */   }
/*    */   
/*    */   MWArmorType(int... validSlots) {
/* 34 */     this.validSlots = validSlots;
/*    */   }
/*    */   
/*    */   public static boolean isVanilla(MWArmorType type) {
/* 38 */     return (type == Head || type == Chest || type == Legs || type == Feet);
/*    */   }
/*    */   
/*    */   public boolean hasSlot(int slot) {
/* 42 */     for (int s : this.validSlots) {
/* 43 */       if (s == slot) return true; 
/*    */     } 
/* 45 */     return false;
/*    */   }
/*    */   
/*    */   public int[] getValidSlots() {
/* 49 */     return this.validSlots;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\MWArmorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */