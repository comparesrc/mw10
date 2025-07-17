/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.client.fpp.basic.renderers.RenderGunStatic;
/*    */ import net.minecraft.util.EnumHandSide;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ public class RenderHandFisrtPersonEvent
/*    */   extends Event
/*    */ {
/*    */   public final RenderGunStatic renderGunStatic;
/*    */   public final EnumHandSide side;
/*    */   
/*    */   public RenderHandFisrtPersonEvent(RenderGunStatic renderGunStatic, EnumHandSide side) {
/* 16 */     this.renderGunStatic = renderGunStatic;
/* 17 */     this.side = side;
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class Pre
/*    */     extends RenderHandFisrtPersonEvent {
/*    */     public Pre(RenderGunStatic renderGunStatic, EnumHandSide side) {
/* 24 */       super(renderGunStatic, side);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Post
/*    */     extends RenderHandFisrtPersonEvent
/*    */   {
/*    */     public Post(RenderGunStatic renderGunStatic, EnumHandSide side) {
/* 33 */       super(renderGunStatic, side);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\RenderHandFisrtPersonEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */