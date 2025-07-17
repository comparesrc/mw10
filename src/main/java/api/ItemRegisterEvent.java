/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import net.minecraftforge.registries.IForgeRegistry;
/*    */ 
/*    */ public class ItemRegisterEvent
/*    */   extends Event
/*    */ {
/*    */   public IForgeRegistry<Item> registry;
/*    */   public List<Item> tabOrder;
/*    */   
/*    */   public ItemRegisterEvent(IForgeRegistry<Item> registry, List<Item> tabOrder) {
/* 15 */     this.registry = registry;
/* 16 */     this.tabOrder = tabOrder;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\ItemRegisterEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */