/*    */ package com.modularwarfare.common.entity.decals;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityBulletHole
/*    */   extends EntityDecal
/*    */ {
/*    */   public EntityBulletHole(World worldIn) {
/* 10 */     super(worldIn);
/* 11 */     this.maxTimeAlive = 200;
/*    */   }
/*    */   
/*    */   public ResourceLocation getDecalTexture() {
/* 15 */     String location = "modularwarfare:textures/entity/bullethole/bullethole" + getTextureNumber() + ".png";
/* 16 */     return new ResourceLocation(location);
/*    */   }
/*    */   
/*    */   public int getTextureCount() {
/* 20 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\entity\decals\EntityBulletHole.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */