/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderer;
/*    */ import net.minecraft.util.EnumHandSide;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class RenderHandFisrtPersonEnhancedEvent
/*    */   extends Event
/*    */ {
/*    */   public CustomItemRenderer renderer;
/*    */   public EnumHandSide side;
/*    */   
/*    */   @Cancelable
/*    */   public static class PreFirstLayer
/*    */     extends RenderHandFisrtPersonEnhancedEvent {
/*    */     public PreFirstLayer(CustomItemRenderer renderer, EnumHandSide handSide) {
/* 18 */       this.renderer = renderer;
/* 19 */       this.side = handSide;
/*    */     }
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class PreSecondLayer extends RenderHandFisrtPersonEnhancedEvent {
/*    */     public PreSecondLayer(CustomItemRenderer renderer, EnumHandSide handSide) {
/* 26 */       this.renderer = renderer;
/* 27 */       this.side = handSide;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\RenderHandFisrtPersonEnhancedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */