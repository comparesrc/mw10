/*   */ package net.minecraft.entity;
/*   */ 
/*   */ public class EntityHelper {
/*   */   public static void setFlag(Entity entity, int flag, boolean state) {
/* 5 */     entity.func_70052_a(flag, state);
/*   */   }
/*   */   
/*   */   public static int getTicksElytraFlying(EntityLivingBase entityLivingBase) {
/* 9 */     return entityLivingBase.field_184629_bo;
/*   */   }
/*   */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\minecraft\entity\EntityHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */