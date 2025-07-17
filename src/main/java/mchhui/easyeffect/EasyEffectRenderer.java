/*    */ package mchhui.easyeffect;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityAnimal;
/*    */ 
/*    */ public class EasyEffectRenderer
/*    */ {
/* 10 */   public ArrayList<EEObject> objects = new ArrayList<>();
/*    */   public float viewEntityRenderingPosX;
/*    */   public float viewEntityRenderingPosY;
/*    */   public float viewEntityRenderingPosZ;
/*    */   public float viewEntityRenderingYaw;
/*    */   public float viewEntityRenderingPitch;
/*    */   
/*    */   public void render(float partialTicks) {
/* 18 */     long time = System.currentTimeMillis();
/* 19 */     Entity entity = Minecraft.func_71410_x().func_175606_aa();
/* 20 */     this.viewEntityRenderingPosX = (float)(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * partialTicks);
/* 21 */     this.viewEntityRenderingPosY = (float)(entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * partialTicks);
/* 22 */     this.viewEntityRenderingPosZ = (float)(entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * partialTicks);
/* 23 */     float yaw = entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * partialTicks + 180.0F;
/* 24 */     float pitch = entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks;
/* 25 */     float roll = 0.0F;
/* 26 */     if (entity instanceof EntityAnimal) {
/*    */       
/* 28 */       EntityAnimal entityanimal = (EntityAnimal)entity;
/* 29 */       yaw = entityanimal.field_70758_at + (entityanimal.field_70759_as - entityanimal.field_70758_at) * partialTicks + 180.0F;
/*    */     } 
/* 31 */     this.viewEntityRenderingYaw = yaw;
/* 32 */     this.viewEntityRenderingPitch = pitch;
/*    */     
/* 34 */     for (int i = 0; i < this.objects.size(); i++) {
/* 35 */       EEObject obj = this.objects.get(i);
/* 36 */       if (obj != null) {
/* 37 */         obj.render(this, time, partialTicks);
/* 38 */         if (obj.isShutdown(time)) {
/* 39 */           this.objects.remove(i);
/* 40 */           i--;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\easyeffect\EasyEffectRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */