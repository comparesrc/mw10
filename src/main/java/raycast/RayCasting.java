package com.modularwarfare.raycast;

import com.modularwarfare.common.hitbox.hits.BulletHit;
import java.util.HashSet;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class RayCasting {
  public abstract BulletHit computeDetection(World paramWorld, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, HashSet<Entity> paramHashSet, boolean paramBoolean, int paramInt);
  
  public abstract RayTraceResult rayTraceBlocks(World paramWorld, Vec3d paramVec3d1, Vec3d paramVec3d2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\raycast\RayCasting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */