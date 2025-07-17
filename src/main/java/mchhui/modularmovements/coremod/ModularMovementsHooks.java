/*    */ package mchhui.modularmovements.coremod;
/*    */ 
/*    */ import mchhui.modularmovements.tactical.client.ClientLitener;
/*    */ import mchhui.modularmovements.tactical.server.ServerListener;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModularMovementsHooks
/*    */ {
/*    */   public static Vec3d onGetPositionEyes(EntityPlayer player, float partialTicks) {
/*    */     Vec3d vec3d;
/* 16 */     if (partialTicks == 1.0F) {
/* 17 */       vec3d = new Vec3d(player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v);
/*    */     } else {
/* 19 */       double d0 = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * partialTicks;
/*    */       
/* 21 */       double d1 = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * partialTicks + player.func_70047_e();
/* 22 */       double d2 = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * partialTicks;
/* 23 */       vec3d = new Vec3d(d0, d1, d2);
/*    */     } 
/*    */     
/* 26 */     if (player.field_70170_p.field_72995_K) {
/* 27 */       vec3d = ClientLitener.onGetPositionEyes(player, partialTicks, vec3d);
/*    */     } else {
/* 29 */       vec3d = ServerListener.onGetPositionEyes(player, partialTicks, vec3d);
/*    */     } 
/* 31 */     return vec3d;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static AxisAlignedBB getEntityBoundingBox(Entity entity, AxisAlignedBB bb) {
/* 42 */     return bb;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\coremod\ModularMovementsHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */