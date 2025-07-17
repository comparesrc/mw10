/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderer;
/*    */ import com.modularwarfare.client.fpp.enhanced.models.EnhancedModel;
/*    */ import net.minecraft.util.EnumHandSide;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class RenderHandSleeveEnhancedEvent
/*    */   extends Event
/*    */ {
/*    */   public CustomItemRenderer renderer;
/*    */   public EnumHandSide side;
/*    */   public EnhancedModel model;
/*    */   
/*    */   public static class Post
/*    */     extends RenderHandSleeveEnhancedEvent {
/*    */     public Post(CustomItemRenderer renderer, EnumHandSide side, EnhancedModel model) {
/* 18 */       this.renderer = renderer;
/* 19 */       this.side = side;
/* 20 */       this.model = model;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\api\RenderHandSleeveEnhancedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */