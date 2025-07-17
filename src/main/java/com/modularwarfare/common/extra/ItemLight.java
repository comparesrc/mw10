/*    */ package com.modularwarfare.common.extra;
/*    */ 
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class ItemLight
/*    */   extends Item
/*    */ {
/*    */   public ItemLight(String name) {
/* 10 */     ResourceLocation registryName = new ResourceLocation("modularwarfare", name);
/* 11 */     String translationKey = registryName.func_110623_a();
/* 12 */     setRegistryName(registryName);
/* 13 */     func_77655_b(translationKey);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\extra\ItemLight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */