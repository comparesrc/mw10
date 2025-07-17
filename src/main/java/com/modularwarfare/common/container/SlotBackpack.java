/*    */ package com.modularwarfare.common.container;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.items.IItemHandler;
/*    */ import net.minecraftforge.items.SlotItemHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotBackpack
/*    */   extends SlotItemHandler
/*    */ {
/*    */   public SlotBackpack(IItemHandler inv, int index, int xPosition, int yPosition) {
/* 15 */     super(inv, index, xPosition, yPosition);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_75219_a() {
/* 20 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(@Nonnull ItemStack stack) {
/* 25 */     return stack.func_77973_b() instanceof com.modularwarfare.common.backpacks.ItemBackpack;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\container\SlotBackpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */