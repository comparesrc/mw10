/*    */ package com.modularwarfare.client.gui.api;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ 
/*    */ 
/*    */ public class Buttons
/*    */ {
/*    */   public static void draw(List<GuiButton> buttons, int mouseX, int mouseY, float partialTicks) {
/* 12 */     Minecraft mc = Minecraft.func_71410_x();
/* 13 */     for (GuiButton button : buttons) {
/* 14 */       button.func_191745_a(mc, mouseX, mouseY, partialTicks);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void click(List<GuiButton> buttons, int mouseX, int mouseY, Consumer<GuiButton> clickHandler) {
/* 19 */     Minecraft mc = Minecraft.func_71410_x();
/* 20 */     for (GuiButton button : buttons) {
/* 21 */       if (button.func_146116_c(mc, mouseX, mouseY)) {
/* 22 */         button.func_146113_a(mc.func_147118_V());
/* 23 */         clickHandler.accept(button);
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\gui\api\Buttons.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */