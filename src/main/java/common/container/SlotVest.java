/*    */ package com.modularwarfare.common.container;
/*    */ 
/*    */ import com.modularwarfare.api.MWArmorType;
/*    */ import com.modularwarfare.common.armor.ItemSpecialArmor;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.items.IItemHandler;
/*    */ import net.minecraftforge.items.SlotItemHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotVest
/*    */   extends SlotItemHandler
/*    */ {
/*    */   public SlotVest(IItemHandler inv, int index, int xPosition, int yPosition) {
/* 16 */     super(inv, index, xPosition, yPosition);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_75219_a() {
/* 21 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(@Nonnull ItemStack stack) {
/* 26 */     if (stack.func_77973_b() instanceof ItemSpecialArmor) {
/* 27 */       ItemSpecialArmor armor = (ItemSpecialArmor)stack.func_77973_b();
/* 28 */       return (armor.armorType == MWArmorType.Vest);
/*    */     } 
/* 30 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\container\SlotVest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */