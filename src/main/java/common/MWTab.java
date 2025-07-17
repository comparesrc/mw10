/*    */ package com.modularwarfare.common;
/*    */ 
/*    */ import com.google.common.collect.Ordering;
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraft.util.text.TextFormatting;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class MWTab
/*    */   extends CreativeTabs
/*    */ {
/*    */   public Comparator<ItemStack> tabSorter;
/*    */   public String contentPack;
/*    */   
/*    */   public MWTab(String contentPack) {
/* 24 */     super("MW:" + contentPack);
/* 25 */     this.contentPack = contentPack;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public String func_78024_c() {
/* 30 */     String name = this.contentPack;
/* 31 */     if (name.endsWith(".zip")) {
/* 32 */       name = name.replace(".zip", "");
/* 33 */     } else if (name.endsWith(".jar")) {
/* 34 */       name = name.replace(".jar", "");
/*    */     } 
/* 36 */     return TextFormatting.RED + "[MW] " + TextFormatting.WHITE + name;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_78016_d() {
/* 41 */     ItemStack[] itemStack = { new ItemStack(Items.field_151036_c) };
/*    */     
/* 43 */     ModularWarfare.gunTypes.forEach((s, gun) -> {
/*    */           if (gun.type.contentPack.equals(this.contentPack)) {
/*    */             itemStack[0] = new ItemStack((Item)gun);
/*    */           }
/*    */         });
/*    */     
/* 49 */     return itemStack[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_78018_a(NonNullList<ItemStack> items) {
/* 54 */     super.func_78018_a(items);
/* 55 */     items.sort(this.tabSorter);
/*    */   }
/*    */   
/*    */   public void preInitialize(List<Item> order) {
/* 59 */     this.tabSorter = (Comparator<ItemStack>)Ordering.explicit(order).onResultOf(ItemStack::func_77973_b);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\MWTab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */