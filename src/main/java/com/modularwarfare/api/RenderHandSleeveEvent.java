/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.client.fpp.basic.renderers.RenderGunStatic;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.util.EnumHandSide;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ public abstract class RenderHandSleeveEvent
/*    */   extends Event
/*    */ {
/*    */   public final RenderGunStatic renderGunStatic;
/*    */   public final EnumHandSide side;
/*    */   public final ModelBiped modelplayer;
/*    */   
/*    */   public RenderHandSleeveEvent(RenderGunStatic render, EnumHandSide side, ModelBiped modelplayer) {
/* 18 */     this.renderGunStatic = render;
/* 19 */     this.side = side;
/* 20 */     this.modelplayer = modelplayer;
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class Pre
/*    */     extends RenderHandSleeveEvent
/*    */   {
/*    */     public Pre(RenderGunStatic render, EnumHandSide side, ModelBiped modelplayer) {
/* 28 */       super(render, side, modelplayer);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Post
/*    */     extends RenderHandSleeveEvent
/*    */   {
/*    */     public Post(RenderGunStatic render, EnumHandSide side, ModelBiped modelplayer) {
/* 38 */       super(render, side, modelplayer);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\api\RenderHandSleeveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */