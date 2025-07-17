/*    */ package com.modularwarfare.common.handler;
/*    */ 
/*    */ import com.modularwarfare.client.gui.GuiInventoryModified;
/*    */ import com.modularwarfare.common.container.ContainerInventoryModified;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.network.IGuiHandler;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class GuiHandler
/*    */   implements IGuiHandler
/*    */ {
/*    */   @Nullable
/*    */   public Container getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/* 19 */     switch (id) {
/*    */       case 0:
/* 21 */         return (Container)new ContainerInventoryModified(player.field_71071_by, !world.field_72995_K, player);
/*    */     } 
/*    */     
/* 24 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   @SideOnly(Side.CLIENT)
/*    */   public Gui getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/* 32 */     switch (id) {
/*    */       case 0:
/* 34 */         return (Gui)new GuiInventoryModified(player);
/*    */     } 
/*    */     
/* 37 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\handler\GuiHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */