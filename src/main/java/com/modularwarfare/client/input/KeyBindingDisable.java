/*    */ package com.modularwarfare.client.input;
/*    */ 
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class KeyBindingDisable
/*    */   extends KeyBinding {
/*    */   public KeyBindingDisable(KeyBinding keybinding) {
/* 11 */     super(keybinding.func_151464_g(), keybinding.getKeyConflictContext(), keybinding.getKeyModifier(), keybinding.func_151463_i(), keybinding.func_151466_e());
/*    */   }
/*    */   
/*    */   public boolean func_151470_d() {
/* 15 */     return false;
/*    */   }
/*    */   
/*    */   public boolean func_151468_f() {
/* 19 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\input\KeyBindingDisable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */