/*    */ package mchhui.modularmovements.tactical.client;
/*    */ 
/*    */ import com.modularwarfare.api.GunBobbingEvent;
/*    */ import com.modularwarfare.api.PlayerSnapshotCreateEvent;
/*    */ import com.modularwarfare.api.RenderBonesEvent;
/*    */ import mchhui.modularmovements.tactical.PlayerState;
/*    */ import net.minecraft.client.model.ModelPlayer;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ public class MWFClientListener
/*    */ {
/*    */   @SubscribeEvent
/*    */   public void onGunBobbing(GunBobbingEvent event) {
/* 16 */     if (ClientLitener.clientPlayerSitMoveAmplifier > 0.0D) {
/* 17 */       event.bobbing = 0.0F;
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRenderBonesEvent(RenderBonesEvent.RotationAngles event) {
/* 23 */     ClientLitener.setRotationAngles((ModelPlayer)event.bones, event.limbSwing, event.limbSwingAmount, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scaleFactor, event.entityIn);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPlayerSnapshotCreate(PlayerSnapshotCreateEvent.Pre event) {
/* 29 */     if (event.player instanceof net.minecraft.entity.player.EntityPlayer && !event.player.field_70128_L && 
/* 30 */       ClientLitener.ohterPlayerStateMap.containsKey(Integer.valueOf(event.player.func_145782_y()))) {
/* 31 */       PlayerState state = ClientLitener.ohterPlayerStateMap.get(Integer.valueOf(event.player.func_145782_y()));
/* 32 */       if (state.probeOffset != 0.0F) {
/*    */         
/* 34 */         Vec3d vec3d = Vec3d.field_186680_a.func_72441_c(state.probeOffset * -0.5D, 0.0D, 0.0D).func_178785_b(-(event.player.field_70177_z * 3.14F / 180.0F));
/* 35 */         event.pos.x = (float)(event.pos.x + vec3d.field_72450_a);
/* 36 */         event.pos.z = (float)(event.pos.z + vec3d.field_72449_c);
/*    */       } 
/* 38 */       if (state.isSitting) {
/* 39 */         event.pos.y -= 0.8F;
/*    */       }
/* 41 */       if (state.isCrawling)
/* 42 */         event.pos.y -= 1.5F; 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\tactical\client\MWFClientListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */