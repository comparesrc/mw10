/*     */ package com.modularwarfare.raycast;
/*     */ 
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.ballistics.GetLivingAABBEvent;
/*     */ import com.modularwarfare.common.hitbox.hits.BulletHit;
/*     */ import com.modularwarfare.common.hitbox.hits.OBBHit;
/*     */ import com.modularwarfare.common.hitbox.playerdata.PlayerData;
/*     */ import com.modularwarfare.common.hitbox.playerdata.PlayerDataHandler;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketPlaySound;
/*     */ import com.modularwarfare.common.vector.Matrix4f;
/*     */ import com.modularwarfare.common.vector.Vector3f;
/*     */ import com.modularwarfare.raycast.obb.OBBModelBox;
/*     */ import com.modularwarfare.raycast.obb.OBBPlayerManager;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultRayCasting
/*     */   extends RayCasting
/*     */ {
/*     */   public BulletHit computeDetection(World world, float x, float y, float z, float tx, float ty, float tz, float borderSize, HashSet<Entity> excluded, boolean collideablesOnly, int ping) {
/*  50 */     Vec3d startVec = new Vec3d(x, y, z);
/*     */     
/*  52 */     Vec3d endVec = new Vec3d(tx, ty, tz);
/*     */     
/*  54 */     float minX = (x < tx) ? x : tx;
/*  55 */     float minY = (y < ty) ? y : ty;
/*  56 */     float minZ = (z < tz) ? z : tz;
/*  57 */     float maxX = (x > tx) ? x : tx;
/*  58 */     float maxY = (y > ty) ? y : ty;
/*  59 */     float maxZ = (z > tz) ? z : tz;
/*  60 */     AxisAlignedBB bb = (new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ)).func_72314_b(borderSize, borderSize, borderSize);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     List<Entity> allEntities = world.func_72839_b(null, bb);
/*  66 */     RayTraceResult blockHit = rayTraceBlocks(world, startVec, endVec, true, true, false);
/*     */     
/*  68 */     startVec = new Vec3d(x, y, z);
/*  69 */     endVec = new Vec3d(tx, ty, tz);
/*  70 */     float maxDistance = (float)endVec.func_72438_d(startVec);
/*  71 */     if (blockHit != null) {
/*  72 */       maxDistance = (float)blockHit.field_72307_f.func_72438_d(startVec);
/*  73 */       endVec = blockHit.field_72307_f;
/*     */     } 
/*     */     
/*  76 */     Vector3f rayVec = new Vector3f(endVec.field_72450_a - startVec.field_72450_a, endVec.field_72448_b - startVec.field_72448_b, endVec.field_72449_c - startVec.field_72449_c);
/*  77 */     float len = rayVec.length();
/*  78 */     Vector3f normlVec = rayVec.normalise(null);
/*  79 */     OBBModelBox ray = new OBBModelBox();
/*  80 */     float pitch = (float)Math.asin(normlVec.y);
/*  81 */     normlVec.y = 0.0F;
/*  82 */     normlVec = normlVec.normalise(null);
/*  83 */     float yaw = (float)Math.asin(normlVec.x);
/*  84 */     if (normlVec.z < 0.0F) {
/*  85 */       yaw = (float)(Math.PI - yaw);
/*     */     }
/*  87 */     Matrix4f matrix = new Matrix4f();
/*  88 */     matrix.rotate(yaw, new Vector3f(0.0F, 1.0F, 0.0F));
/*  89 */     matrix.rotate(pitch, new Vector3f(-1.0F, 0.0F, 0.0F));
/*  90 */     ray.center = new Vector3f((startVec.field_72450_a + endVec.field_72450_a) / 2.0D, (startVec.field_72448_b + endVec.field_72448_b) / 2.0D, (startVec.field_72449_c + endVec.field_72449_c) / 2.0D);
/*  91 */     ray.axis.x = new Vector3f(0.0F, 0.0F, 0.0F);
/*  92 */     ray.axis.y = new Vector3f(0.0F, 0.0F, 0.0F);
/*  93 */     ray.axis.z = Matrix4f.transform(matrix, new Vector3f(0.0F, 0.0F, len / 2.0F), null);
/*  94 */     ray.axisNormal.x = Matrix4f.transform(matrix, new Vector3f(1.0F, 0.0F, 0.0F), null);
/*  95 */     ray.axisNormal.y = Matrix4f.transform(matrix, new Vector3f(0.0F, 1.0F, 0.0F), null);
/*  96 */     ray.axisNormal.z = Matrix4f.transform(matrix, new Vector3f(0.0F, 0.0F, 1.0F), null);
/*     */     
/*  98 */     if (OBBPlayerManager.debug) {
/*  99 */       System.out.println("test0:" + startVec + "|" + (Minecraft.func_71410_x()).field_71439_g.func_174791_d());
/* 100 */       OBBPlayerManager.lines.add(new OBBPlayerManager.OBBDebugObject(ray));
/* 101 */       OBBPlayerManager.lines.add(new OBBPlayerManager.OBBDebugObject(new Vector3f(startVec), new Vector3f(endVec)));
/*     */     } 
/*     */     
/* 104 */     for (int i = 0; i < world.field_72996_f.size(); i++) {
/* 105 */       Entity obj = world.field_72996_f.get(i);
/*     */       
/* 107 */       if (((excluded != null && !excluded.contains(obj)) || excluded == null) && 
/* 108 */         obj instanceof EntityPlayer) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 142 */         OBBPlayerManager.PlayerOBBModelObject obbModelObject = OBBPlayerManager.getPlayerOBBObject(obj.func_70005_c_());
/* 143 */         OBBModelBox finalBox = null;
/* 144 */         List<OBBModelBox> boxes = obbModelObject.calculateIntercept(ray);
/* 145 */         if (boxes.size() > 0) {
/* 146 */           double t = Double.MAX_VALUE;
/* 147 */           Vector3f hitFaceNormal = null;
/*     */           
/* 149 */           Vector3f startVector = new Vector3f(startVec);
/* 150 */           for (OBBModelBox obb : boxes) {
/* 151 */             OBBModelBox.RayCastResult temp = OBBModelBox.testCollisionOBBAndRay(obb, startVector, rayVec);
/* 152 */             if (temp.t < t) {
/* 153 */               t = temp.t;
/* 154 */               hitFaceNormal = temp.normal;
/* 155 */               finalBox = obb;
/*     */             } 
/*     */           } 
/*     */           
/* 159 */           if (OBBPlayerManager.debug) {
/* 160 */             OBBPlayerManager.lines.add(new OBBPlayerManager.OBBDebugObject(new Vector3f(startVec.field_72450_a + rayVec.x * t, startVec.field_72448_b + rayVec.y * t, startVec.field_72449_c + rayVec.z * t)));
/*     */           }
/* 162 */           if (finalBox != null) {
/* 163 */             PlayerData data = PlayerDataHandler.getPlayerData((EntityPlayer)obj);
/* 164 */             RayTraceResult intercept = new RayTraceResult(obj, new Vec3d(finalBox.center.x, finalBox.center.y, finalBox.center.z));
/* 165 */             return (BulletHit)new OBBHit((EntityLivingBase)obj, finalBox.copy(), intercept);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 172 */     Entity closestHitEntity = null;
/* 173 */     Vec3d hit = null;
/* 174 */     float closestHit = maxDistance;
/* 175 */     float currentHit = 0.0F;
/*     */ 
/*     */ 
/*     */     
/* 179 */     for (Entity ent : allEntities) {
/* 180 */       if ((ent.func_70067_L() || !collideablesOnly) && ((excluded != null && !excluded.contains(ent)) || excluded == null)) {
/* 181 */         if (ent instanceof EntityLivingBase && !(ent instanceof EntityPlayer)) {
/* 182 */           EntityLivingBase entityLivingBase = (EntityLivingBase)ent;
/* 183 */           if (!ent.field_70128_L && entityLivingBase.func_110143_aJ() > 0.0F) {
/* 184 */             float entBorder = ent.func_70111_Y();
/* 185 */             AxisAlignedBB entityBb = ent.func_174813_aQ();
/* 186 */             if (entityBb != null) {
/* 187 */               entityBb = entityBb.func_72314_b(entBorder, entBorder, entBorder);
/* 188 */               GetLivingAABBEvent aabbEvent = new GetLivingAABBEvent(entityLivingBase, entityBb);
/* 189 */               MinecraftForge.EVENT_BUS.post((Event)aabbEvent);
/* 190 */               entityBb = aabbEvent.box;
/* 191 */               RayTraceResult intercept = entityBb.func_72327_a(startVec, endVec);
/*     */               
/* 193 */               if (intercept != null) {
/* 194 */                 currentHit = (float)intercept.field_72307_f.func_72438_d(startVec);
/* 195 */                 hit = intercept.field_72307_f;
/* 196 */                 if (currentHit < closestHit || currentHit == 0.0F) {
/* 197 */                   closestHit = currentHit;
/* 198 */                   closestHitEntity = ent;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }  continue;
/* 203 */         }  if (ent instanceof com.modularwarfare.common.entity.grenades.EntityGrenade) {
/* 204 */           float entBorder = ent.func_70111_Y();
/* 205 */           AxisAlignedBB entityBb = ent.func_174813_aQ();
/* 206 */           if (entityBb != null) {
/* 207 */             entityBb = entityBb.func_72314_b(entBorder, entBorder, entBorder);
/* 208 */             RayTraceResult intercept = entityBb.func_72327_a(startVec, endVec);
/* 209 */             if (intercept != null) {
/* 210 */               currentHit = (float)intercept.field_72307_f.func_72438_d(startVec);
/* 211 */               hit = intercept.field_72307_f;
/* 212 */               if (currentHit < closestHit || currentHit == 0.0F) {
/* 213 */                 closestHit = currentHit;
/* 214 */                 closestHitEntity = ent;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 221 */     if (closestHitEntity != null && hit != null) {
/* 222 */       blockHit = new RayTraceResult(closestHitEntity, hit);
/*     */     }
/*     */     
/* 225 */     return new BulletHit(blockHit);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RayTraceResult rayTraceBlocks(World world, Vec3d vec31, Vec3d vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
/* 230 */     if (!Double.isNaN(vec31.field_72450_a) && !Double.isNaN(vec31.field_72448_b) && !Double.isNaN(vec31.field_72449_c)) {
/* 231 */       if (!Double.isNaN(vec32.field_72450_a) && !Double.isNaN(vec32.field_72448_b) && !Double.isNaN(vec32.field_72449_c)) {
/* 232 */         int i = MathHelper.func_76128_c(vec32.field_72450_a);
/* 233 */         int j = MathHelper.func_76128_c(vec32.field_72448_b);
/* 234 */         int k = MathHelper.func_76128_c(vec32.field_72449_c);
/* 235 */         int l = MathHelper.func_76128_c(vec31.field_72450_a);
/* 236 */         int i1 = MathHelper.func_76128_c(vec31.field_72448_b);
/* 237 */         int j1 = MathHelper.func_76128_c(vec31.field_72449_c);
/* 238 */         BlockPos blockpos = new BlockPos(l, i1, j1);
/* 239 */         IBlockState iblockstate = world.func_180495_p(blockpos);
/* 240 */         Block block = iblockstate.func_177230_c();
/*     */         
/* 242 */         if ((!ignoreBlockWithoutBoundingBox || iblockstate.func_185890_d((IBlockAccess)world, blockpos) != Block.field_185506_k) && block.func_176209_a(iblockstate, stopOnLiquid)) {
/* 243 */           RayTraceResult raytraceresult = iblockstate.func_185910_a(world, blockpos, vec31, vec32);
/*     */           
/* 245 */           if (raytraceresult != null) {
/* 246 */             return raytraceresult;
/*     */           }
/*     */         } 
/*     */         
/* 250 */         RayTraceResult raytraceresult2 = null;
/* 251 */         int k1 = 200;
/*     */         
/* 253 */         while (k1-- >= 0) {
/* 254 */           EnumFacing enumfacing; if (Double.isNaN(vec31.field_72450_a) || Double.isNaN(vec31.field_72448_b) || Double.isNaN(vec31.field_72449_c)) {
/* 255 */             return null;
/*     */           }
/*     */           
/* 258 */           if (l == i && i1 == j && j1 == k) {
/* 259 */             return returnLastUncollidableBlock ? raytraceresult2 : null;
/*     */           }
/*     */           
/* 262 */           boolean flag2 = true;
/* 263 */           boolean flag = true;
/* 264 */           boolean flag1 = true;
/* 265 */           double d0 = 999.0D;
/* 266 */           double d1 = 999.0D;
/* 267 */           double d2 = 999.0D;
/*     */           
/* 269 */           if (i > l) {
/* 270 */             d0 = l + 1.0D;
/* 271 */           } else if (i < l) {
/* 272 */             d0 = l + 0.0D;
/*     */           } else {
/* 274 */             flag2 = false;
/*     */           } 
/*     */           
/* 277 */           if (j > i1) {
/* 278 */             d1 = i1 + 1.0D;
/* 279 */           } else if (j < i1) {
/* 280 */             d1 = i1 + 0.0D;
/*     */           } else {
/* 282 */             flag = false;
/*     */           } 
/*     */           
/* 285 */           if (k > j1) {
/* 286 */             d2 = j1 + 1.0D;
/* 287 */           } else if (k < j1) {
/* 288 */             d2 = j1 + 0.0D;
/*     */           } else {
/* 290 */             flag1 = false;
/*     */           } 
/*     */           
/* 293 */           double d3 = 999.0D;
/* 294 */           double d4 = 999.0D;
/* 295 */           double d5 = 999.0D;
/* 296 */           double d6 = vec32.field_72450_a - vec31.field_72450_a;
/* 297 */           double d7 = vec32.field_72448_b - vec31.field_72448_b;
/* 298 */           double d8 = vec32.field_72449_c - vec31.field_72449_c;
/*     */           
/* 300 */           if (flag2) {
/* 301 */             d3 = (d0 - vec31.field_72450_a) / d6;
/*     */           }
/*     */           
/* 304 */           if (flag) {
/* 305 */             d4 = (d1 - vec31.field_72448_b) / d7;
/*     */           }
/*     */           
/* 308 */           if (flag1) {
/* 309 */             d5 = (d2 - vec31.field_72449_c) / d8;
/*     */           }
/*     */           
/* 312 */           if (d3 == -0.0D) {
/* 313 */             d3 = -1.0E-4D;
/*     */           }
/*     */           
/* 316 */           if (d4 == -0.0D) {
/* 317 */             d4 = -1.0E-4D;
/*     */           }
/*     */           
/* 320 */           if (d5 == -0.0D) {
/* 321 */             d5 = -1.0E-4D;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 326 */           if (d3 < d4 && d3 < d5) {
/* 327 */             enumfacing = (i > l) ? EnumFacing.WEST : EnumFacing.EAST;
/* 328 */             vec31 = new Vec3d(d0, vec31.field_72448_b + d7 * d3, vec31.field_72449_c + d8 * d3);
/* 329 */           } else if (d4 < d5) {
/* 330 */             enumfacing = (j > i1) ? EnumFacing.DOWN : EnumFacing.UP;
/* 331 */             vec31 = new Vec3d(vec31.field_72450_a + d6 * d4, d1, vec31.field_72449_c + d8 * d4);
/*     */           } else {
/* 333 */             enumfacing = (k > j1) ? EnumFacing.NORTH : EnumFacing.SOUTH;
/* 334 */             vec31 = new Vec3d(vec31.field_72450_a + d6 * d5, vec31.field_72448_b + d7 * d5, d2);
/*     */           } 
/*     */           
/* 337 */           l = MathHelper.func_76128_c(vec31.field_72450_a) - ((enumfacing == EnumFacing.EAST) ? 1 : 0);
/* 338 */           i1 = MathHelper.func_76128_c(vec31.field_72448_b) - ((enumfacing == EnumFacing.UP) ? 1 : 0);
/* 339 */           j1 = MathHelper.func_76128_c(vec31.field_72449_c) - ((enumfacing == EnumFacing.SOUTH) ? 1 : 0);
/* 340 */           blockpos = new BlockPos(l, i1, j1);
/* 341 */           IBlockState iblockstate1 = world.func_180495_p(blockpos);
/* 342 */           Block block1 = iblockstate1.func_177230_c();
/*     */           
/* 344 */           if (ModConfig.INSTANCE.shots.shot_break_glass && (
/* 345 */             block1 instanceof net.minecraft.block.BlockGlass || block1 instanceof net.minecraft.block.BlockStainedGlassPane || block1 instanceof net.minecraft.block.BlockStainedGlass)) {
/* 346 */             world.func_175698_g(blockpos);
/* 347 */             ModularWarfare.NETWORK.sendToAllAround((PacketBase)new PacketPlaySound(blockpos, "impact.glass", 1.0F, 1.0F), new NetworkRegistry.TargetPoint(0, blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p(), 25.0D));
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 352 */           if (block1 instanceof net.minecraft.block.BlockPane) {
/* 353 */             ModularWarfare.NETWORK.sendToAllAround((PacketBase)new PacketPlaySound(blockpos, "impact.iron", 1.0F, 1.0F), new NetworkRegistry.TargetPoint(0, blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p(), 25.0D));
/*     */             
/*     */             continue;
/*     */           } 
/* 357 */           if (block1 instanceof net.minecraft.block.BlockDoor || block1 instanceof net.minecraft.block.BlockLeaves) {
/*     */             continue;
/*     */           }
/*     */           
/* 361 */           if (!ignoreBlockWithoutBoundingBox || iblockstate1.func_185904_a() == Material.field_175972_I || iblockstate1.func_185904_a() == Material.field_151567_E || iblockstate1.func_185890_d((IBlockAccess)world, blockpos) != Block.field_185506_k) {
/* 362 */             if (block1.func_176209_a(iblockstate1, stopOnLiquid)) {
/* 363 */               RayTraceResult raytraceresult1 = iblockstate1.func_185910_a(world, blockpos, vec31, vec32);
/* 364 */               if (raytraceresult1 != null)
/* 365 */                 return raytraceresult1; 
/*     */               continue;
/*     */             } 
/* 368 */             raytraceresult2 = new RayTraceResult(RayTraceResult.Type.MISS, vec31, enumfacing, blockpos);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 373 */         return returnLastUncollidableBlock ? raytraceresult2 : null;
/*     */       } 
/* 375 */       return null;
/*     */     } 
/*     */     
/* 378 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\raycast\DefaultRayCasting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */