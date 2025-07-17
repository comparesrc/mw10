/*    */ package mchhui.modularmovements.tactical.server;
/*    */ 
/*    */ import com.modularwarfare.api.PlayerSnapshotCreateEvent;
/*    */ import com.modularwarfare.api.WeaponFireEvent;
/*    */ import mchhui.modularmovements.tactical.PlayerState;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MWFServerListener
/*    */ {
/*    */   @SubscribeEvent(priority = EventPriority.LOW)
/*    */   public void onGunFirePre(WeaponFireEvent.PreServer event) {
/* 17 */     Vec3d vec3d = (new Vec3d(ServerListener.getCameraProbeOffset(Integer.valueOf(event.getWeaponUser().func_145782_y())) * -0.6D, 0.0D, 0.0D)).func_178785_b((float)(-(event.getWeaponUser()).field_70177_z * Math.PI / 180.0D));
/* 18 */     (event.getWeaponUser()).field_70165_t += vec3d.field_72450_a;
/* 19 */     (event.getWeaponUser()).field_70161_v += vec3d.field_72449_c;
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent(priority = EventPriority.HIGH)
/*    */   public void onGunFirePost(WeaponFireEvent.Post event) {
/* 25 */     Vec3d vec3d = (new Vec3d(ServerListener.getCameraProbeOffset(Integer.valueOf(event.getWeaponUser().func_145782_y())) * -0.6D, 0.0D, 0.0D)).func_178785_b((float)(-(event.getWeaponUser()).field_70177_z * Math.PI / 180.0D));
/* 26 */     (event.getWeaponUser()).field_70165_t -= vec3d.field_72450_a;
/* 27 */     (event.getWeaponUser()).field_70161_v -= vec3d.field_72449_c;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPlayerSnapshotCreate(PlayerSnapshotCreateEvent.Pre event) {
/* 32 */     if (event.player instanceof net.minecraft.entity.player.EntityPlayerMP && !event.player.field_70128_L && 
/* 33 */       ServerListener.playerStateMap.containsKey(Integer.valueOf(event.player.func_145782_y()))) {
/* 34 */       PlayerState state = ServerListener.playerStateMap.get(Integer.valueOf(event.player.func_145782_y()));
/* 35 */       if (state.probeOffset != 0.0F) {
/*    */         
/* 37 */         Vec3d vec3d = Vec3d.field_186680_a.func_72441_c(state.probeOffset * -0.5D, 0.0D, 0.0D).func_178785_b(-(event.player.field_70177_z * 3.14F / 180.0F));
/* 38 */         event.pos.x = (float)(event.pos.x + vec3d.field_72450_a);
/* 39 */         event.pos.z = (float)(event.pos.z + vec3d.field_72449_c);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\tactical\server\MWFServerListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */