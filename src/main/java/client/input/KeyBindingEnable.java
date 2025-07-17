/*    */ package com.modularwarfare.client.input;
/*    */ 
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.minecraftforge.client.settings.KeyModifier;
/*    */ 
/*    */ public class KeyBindingEnable
/*    */   extends KeyBinding {
/*    */   public KeyBindingEnable(KeyBinding keybinding) {
/*  9 */     super(keybinding.func_151464_g(), keybinding.getKeyConflictContext(), keybinding.getKeyModifier(), keybinding.func_151463_i(), keybinding.func_151466_e());
/*    */   }
/*    */   
/*    */   public void setKeyModifierAndCode(KeyModifier keyModifier, int keyCode) {
/* 13 */     super.setKeyModifierAndCode(keyModifier, keyCode);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\input\KeyBindingEnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */