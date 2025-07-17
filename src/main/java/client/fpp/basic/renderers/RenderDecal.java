/*     */ package com.modularwarfare.client.fpp.basic.renderers;
/*     */ 
/*     */ import com.modularwarfare.common.entity.decals.EntityDecal;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.block.BlockHorizontal;
/*     */ import net.minecraft.block.BlockStairs;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderDecal
/*     */   extends Render<EntityDecal>
/*     */ {
/*  33 */   public static final Factory FACTORY = new Factory();
/*     */   
/*     */   protected RenderDecal(RenderManager renderManager) {
/*  36 */     super(renderManager);
/*  37 */     this.field_76989_e = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doRenderDecal(EntityDecal var1, double var2, double var4, double var6, float var8, float var9) {
/*  42 */     float transparency = 0.75F;
/*  43 */     if (!var1.isPermanent()) {
/*  44 */       transparency = var1.getAgeRatio() * 0.85F;
/*     */     }
/*     */     
/*  47 */     if (var1 instanceof com.modularwarfare.common.entity.decals.EntityBulletHole) {
/*  48 */       transparency = var1.getAgeRatio() * 1.0F;
/*     */     }
/*     */     
/*  51 */     switch (var1.getSideID()) {
/*     */       case 0:
/*  53 */         renderDecalFloor(var1, var2, var4, var6, transparency, var9);
/*     */       case 1:
/*  55 */         renderDecalNorth(var1, var2, var4, var6, transparency, var9);
/*  56 */         renderDecalEast(var1, var2, var4, var6, transparency, var9);
/*  57 */         renderDecalSouth(var1, var2, var4, var6, transparency, var9);
/*  58 */         renderDecalWest(var1, var2, var4, var6, transparency, var9);
/*     */         break;
/*     */       case 2:
/*  61 */         renderDecalFloor(var1, var2, var4, var6, transparency, var9);
/*     */         break;
/*     */       case 3:
/*  64 */         renderDecalNorth(var1, var2, var4, var6, transparency, var9);
/*     */         break;
/*     */       case 4:
/*  67 */         renderDecalEast(var1, var2, var4, var6, transparency, var9);
/*     */         break;
/*     */       case 5:
/*  70 */         renderDecalSouth(var1, var2, var4, var6, transparency, var9);
/*     */         break;
/*     */       case 6:
/*  73 */         renderDecalWest(var1, var2, var4, var6, transparency, var9);
/*     */         break;
/*     */       case 7:
/*  76 */         renderDecalCeiling(var1, var2, var4, var6, transparency, var9);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityDecal entity) {
/*  84 */     return entity.getDecalTexture();
/*     */   }
/*     */   
/*     */   public void func_76979_b(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
/*  88 */     if (this.field_76990_c.field_78733_k != null) {
/*  89 */       doRenderDecal((EntityDecal)entityIn, x, y, z, yaw, partialTicks);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private World getWorldFromRenderManager() {
/*  95 */     return this.field_76990_c.field_78722_g;
/*     */   }
/*     */   
/*     */   private void renderDecalFloor(EntityDecal entityIn, double x, double y, double z, float shadowAlpha, float partialTicks) {
/*  99 */     GlStateManager.func_179147_l();
/* 100 */     GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 101 */     this.field_76990_c.field_78724_e.func_110577_a(getEntityTexture(entityIn));
/* 102 */     World world = getWorldFromRenderManager();
/* 103 */     GlStateManager.func_179132_a(false);
/* 104 */     double f = 1.0D;
/* 105 */     double d5 = entityIn.field_70142_S + (entityIn.field_70165_t - entityIn.field_70142_S) * partialTicks;
/* 106 */     double d0 = entityIn.field_70137_T + (entityIn.field_70163_u - entityIn.field_70137_T) * partialTicks;
/* 107 */     double d1 = entityIn.field_70136_U + (entityIn.field_70161_v - entityIn.field_70136_U) * partialTicks;
/* 108 */     int i = MathHelper.func_76128_c(d5 - f);
/* 109 */     int j = MathHelper.func_76128_c(d5 + f);
/* 110 */     int k = MathHelper.func_76128_c(d0 - f);
/* 111 */     int l = MathHelper.func_76143_f(d0);
/* 112 */     int i1 = MathHelper.func_76128_c(d1 - f);
/* 113 */     int j1 = MathHelper.func_76128_c(d1 + f);
/* 114 */     double d2 = x - d5;
/* 115 */     double d3 = y - d0;
/* 116 */     double d4 = z - d1;
/* 117 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 118 */     BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 119 */     vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181712_l);
/* 120 */     Iterator<BlockPos> var33 = BlockPos.func_177975_b(new BlockPos(i, k, i1), new BlockPos(j, l, j1)).iterator();
/*     */     
/* 122 */     while (var33.hasNext()) {
/* 123 */       BlockPos blockpos = var33.next();
/* 124 */       IBlockState iblockstate = world.func_180495_p(blockpos.func_177977_b());
/* 125 */       if (iblockstate.func_185901_i() != EnumBlockRenderType.INVISIBLE && !world.func_180495_p(blockpos).func_185914_p()) {
/* 126 */         renderDecalSingleFloor(iblockstate, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4, vertexbuffer);
/*     */       }
/*     */     } 
/*     */     
/* 130 */     tessellator.func_78381_a();
/* 131 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 132 */     GlStateManager.func_179084_k();
/* 133 */     GlStateManager.func_179132_a(true);
/*     */   }
/*     */   
/*     */   private void renderDecalCeiling(EntityDecal entityIn, double x, double y, double z, float shadowAlpha, float partialTicks) {
/* 137 */     GlStateManager.func_179147_l();
/* 138 */     GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 139 */     this.field_76990_c.field_78724_e.func_110577_a(getEntityTexture(entityIn));
/* 140 */     World world = getWorldFromRenderManager();
/* 141 */     GlStateManager.func_179132_a(false);
/* 142 */     double f = 1.0D;
/* 143 */     double d5 = entityIn.field_70142_S + (entityIn.field_70165_t - entityIn.field_70142_S) * partialTicks;
/* 144 */     double d0 = entityIn.field_70137_T + (entityIn.field_70163_u - entityIn.field_70137_T) * partialTicks;
/* 145 */     double d1 = entityIn.field_70136_U + (entityIn.field_70161_v - entityIn.field_70136_U) * partialTicks;
/* 146 */     int i = MathHelper.func_76128_c(d5 - f);
/* 147 */     int j = MathHelper.func_76128_c(d5 + f);
/* 148 */     int k = MathHelper.func_76128_c(d0 - f);
/* 149 */     int l = MathHelper.func_76143_f(d0);
/* 150 */     int i1 = MathHelper.func_76128_c(d1 - f);
/* 151 */     int j1 = MathHelper.func_76128_c(d1 + f);
/* 152 */     double d2 = x - d5;
/* 153 */     double d3 = y - d0;
/* 154 */     double d4 = z - d1;
/* 155 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 156 */     BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 157 */     vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181712_l);
/* 158 */     Iterator<BlockPos> var33 = BlockPos.func_177975_b(new BlockPos(i, k, i1), new BlockPos(j, l, j1)).iterator();
/*     */     
/* 160 */     while (var33.hasNext()) {
/* 161 */       BlockPos blockpos = var33.next();
/* 162 */       IBlockState iblockstate = world.func_180495_p(blockpos.func_177984_a());
/* 163 */       if (iblockstate.func_185901_i() != EnumBlockRenderType.INVISIBLE && !world.func_180495_p(blockpos).func_185914_p()) {
/* 164 */         renderDecalSingleCeiling(iblockstate, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4, vertexbuffer);
/*     */       }
/*     */     } 
/*     */     
/* 168 */     tessellator.func_78381_a();
/* 169 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 170 */     GlStateManager.func_179084_k();
/* 171 */     GlStateManager.func_179132_a(true);
/*     */   }
/*     */   
/*     */   private void renderDecalSingleCeiling(IBlockState state, double p_188299_2_, double p_188299_4_, double p_188299_6_, BlockPos p_188299_8_, float p_188299_9_, double p_188299_10_, double p_188299_11_, double p_188299_13_, double p_188299_15_, BufferBuilder vertexbuffer) {
/* 175 */     boolean bool = false;
/*     */     
/* 177 */     bool = state.isSideSolid((IBlockAccess)getWorldFromRenderManager(), p_188299_8_, EnumFacing.UP);
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
/* 190 */     if (bool) {
/* 191 */       double d0 = p_188299_9_;
/* 192 */       if (d0 >= 0.0D) {
/* 193 */         if (d0 > 1.0D) {
/* 194 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 197 */         AxisAlignedBB axisalignedbb = state.func_185900_c((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 198 */         double d1 = p_188299_8_.func_177958_n() + axisalignedbb.field_72340_a + p_188299_11_;
/* 199 */         double d2 = p_188299_8_.func_177958_n() + axisalignedbb.field_72336_d + p_188299_11_;
/* 200 */         double d3 = p_188299_8_.func_177956_o() + axisalignedbb.field_72337_e + p_188299_13_ - 0.015625D;
/* 201 */         if (state.func_177230_c() instanceof net.minecraft.block.BlockSlab && !state.func_177230_c().isNormalCube(state, (IBlockAccess)getWorldFromRenderManager(), p_188299_8_)) {
/* 202 */           d3 -= 0.5D;
/*     */         }
/*     */         
/* 205 */         double d4 = p_188299_8_.func_177952_p() + axisalignedbb.field_72339_c + p_188299_15_;
/* 206 */         double d5 = p_188299_8_.func_177952_p() + axisalignedbb.field_72334_f + p_188299_15_;
/* 207 */         float f = (float)((p_188299_2_ - d1) / 2.0D / p_188299_10_ + 0.5D);
/* 208 */         float f1 = (float)((p_188299_2_ - d2) / 2.0D / p_188299_10_ + 0.5D);
/* 209 */         float f2 = (float)((p_188299_6_ - d4) / 2.0D / p_188299_10_ + 0.5D);
/* 210 */         float f3 = (float)((p_188299_6_ - d5) / 2.0D / p_188299_10_ + 0.5D);
/* 211 */         vertexbuffer.func_181662_b(d2, d3, d4).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 212 */         vertexbuffer.func_181662_b(d2, d3, d5).func_187315_a(f1, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 213 */         vertexbuffer.func_181662_b(d1, d3, d5).func_187315_a(f, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 214 */         vertexbuffer.func_181662_b(d1, d3, d4).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */       } 
/* 216 */     } else if (state.func_177230_c() instanceof net.minecraft.block.BlockSlab || state.func_177230_c() instanceof BlockStairs) {
/* 217 */       double d0 = p_188299_9_;
/* 218 */       if (d0 >= 0.0D) {
/* 219 */         if (d0 > 1.0D) {
/* 220 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 223 */         AxisAlignedBB axisalignedbb = state.func_185900_c((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 224 */         double d1 = p_188299_8_.func_177958_n() + axisalignedbb.field_72340_a + p_188299_11_;
/* 225 */         double d2 = p_188299_8_.func_177958_n() + axisalignedbb.field_72336_d + p_188299_11_;
/* 226 */         double d3 = p_188299_8_.func_177956_o() + axisalignedbb.field_72337_e + p_188299_13_ - 0.015625D + 0.5D;
/* 227 */         double d4 = p_188299_8_.func_177952_p() + axisalignedbb.field_72339_c + p_188299_15_;
/* 228 */         double d5 = p_188299_8_.func_177952_p() + axisalignedbb.field_72334_f + p_188299_15_;
/* 229 */         float f = (float)((p_188299_2_ - d1) / 2.0D / p_188299_10_ + 0.5D);
/* 230 */         float f1 = (float)((p_188299_2_ - d2) / 2.0D / p_188299_10_ + 0.5D);
/* 231 */         float f2 = (float)((p_188299_6_ - d4) / 2.0D / p_188299_10_ + 0.5D);
/* 232 */         float f3 = (float)((p_188299_6_ - d5) / 2.0D / p_188299_10_ + 0.5D);
/* 233 */         vertexbuffer.func_181662_b(d2, d3, d4).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 234 */         vertexbuffer.func_181662_b(d2, d3, d5).func_187315_a(f1, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 235 */         vertexbuffer.func_181662_b(d1, d3, d5).func_187315_a(f, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 236 */         vertexbuffer.func_181662_b(d1, d3, d4).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 237 */         if (state.func_177230_c() instanceof BlockStairs && state.func_177229_b((IProperty)BlockStairs.field_176310_M) == BlockStairs.EnumShape.STRAIGHT) {
/* 238 */           d3 += 0.5D;
/* 239 */           EnumFacing facing = (EnumFacing)state.func_177229_b((IProperty)BlockHorizontal.field_185512_D);
/* 240 */           switch (facing) {
/*     */             case NORTH:
/* 242 */               d5 -= 0.5D;
/*     */               break;
/*     */             case EAST:
/* 245 */               d1 += 0.5D;
/*     */               break;
/*     */             case SOUTH:
/* 248 */               d4 += 0.5D;
/*     */               break;
/*     */             case WEST:
/* 251 */               d2 -= 0.5D;
/*     */               break;
/*     */           } 
/* 254 */           f = (float)((p_188299_2_ - d1) / 2.0D / p_188299_10_ + 0.5D);
/* 255 */           f1 = (float)((p_188299_2_ - d2) / 2.0D / p_188299_10_ + 0.5D);
/* 256 */           f2 = (float)((p_188299_6_ - d4) / 2.0D / p_188299_10_ + 0.5D);
/* 257 */           f3 = (float)((p_188299_6_ - d5) / 2.0D / p_188299_10_ + 0.5D);
/* 258 */           vertexbuffer.func_181662_b(d2, d3, d4).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 259 */           vertexbuffer.func_181662_b(d2, d3, d5).func_187315_a(f1, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 260 */           vertexbuffer.func_181662_b(d1, d3, d5).func_187315_a(f, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 261 */           vertexbuffer.func_181662_b(d1, d3, d4).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderDecalSingleFloor(IBlockState state, double p_188299_2_, double p_188299_4_, double p_188299_6_, BlockPos p_188299_8_, float p_188299_9_, double p_188299_10_, double p_188299_11_, double p_188299_13_, double p_188299_15_, BufferBuilder vertexbuffer) {
/* 269 */     boolean bool = false;
/*     */     
/* 271 */     bool = state.isSideSolid((IBlockAccess)getWorldFromRenderManager(), p_188299_8_, EnumFacing.UP);
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
/* 284 */     if (bool) {
/* 285 */       double d0 = p_188299_9_;
/* 286 */       if (d0 >= 0.0D) {
/* 287 */         if (d0 > 1.0D) {
/* 288 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 291 */         AxisAlignedBB axisalignedbb = state.func_185900_c((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 292 */         double d1 = p_188299_8_.func_177958_n() + axisalignedbb.field_72340_a + p_188299_11_;
/* 293 */         double d2 = p_188299_8_.func_177958_n() + axisalignedbb.field_72336_d + p_188299_11_;
/* 294 */         double d3 = p_188299_8_.func_177956_o() + axisalignedbb.field_72338_b + p_188299_13_ + 0.015625D;
/* 295 */         if (state.func_177230_c() instanceof net.minecraft.block.BlockSlab && !state.func_177230_c().isNormalCube(state, (IBlockAccess)getWorldFromRenderManager(), p_188299_8_)) {
/* 296 */           d3 -= 0.5D;
/*     */         }
/*     */         
/* 299 */         double d4 = p_188299_8_.func_177952_p() + axisalignedbb.field_72339_c + p_188299_15_;
/* 300 */         double d5 = p_188299_8_.func_177952_p() + axisalignedbb.field_72334_f + p_188299_15_;
/* 301 */         float f = (float)((p_188299_2_ - d1) / 2.0D / p_188299_10_ + 0.5D);
/* 302 */         float f1 = (float)((p_188299_2_ - d2) / 2.0D / p_188299_10_ + 0.5D);
/* 303 */         float f2 = (float)((p_188299_6_ - d4) / 2.0D / p_188299_10_ + 0.5D);
/* 304 */         float f3 = (float)((p_188299_6_ - d5) / 2.0D / p_188299_10_ + 0.5D);
/* 305 */         vertexbuffer.func_181662_b(d1, d3, d4).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 306 */         vertexbuffer.func_181662_b(d1, d3, d5).func_187315_a(f, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 307 */         vertexbuffer.func_181662_b(d2, d3, d5).func_187315_a(f1, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 308 */         vertexbuffer.func_181662_b(d2, d3, d4).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */       } 
/* 310 */     } else if (state.func_177230_c() instanceof net.minecraft.block.BlockSlab || state.func_177230_c() instanceof BlockStairs) {
/* 311 */       double d0 = p_188299_9_;
/* 312 */       if (d0 >= 0.0D) {
/* 313 */         if (d0 > 1.0D) {
/* 314 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 317 */         AxisAlignedBB axisalignedbb = state.func_185900_c((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 318 */         double d1 = p_188299_8_.func_177958_n() + axisalignedbb.field_72340_a + p_188299_11_;
/* 319 */         double d2 = p_188299_8_.func_177958_n() + axisalignedbb.field_72336_d + p_188299_11_;
/* 320 */         double d3 = p_188299_8_.func_177956_o() + axisalignedbb.field_72338_b + p_188299_13_ + 0.015625D - 0.5D;
/* 321 */         double d4 = p_188299_8_.func_177952_p() + axisalignedbb.field_72339_c + p_188299_15_;
/* 322 */         double d5 = p_188299_8_.func_177952_p() + axisalignedbb.field_72334_f + p_188299_15_;
/* 323 */         float f = (float)((p_188299_2_ - d1) / 2.0D / p_188299_10_ + 0.5D);
/* 324 */         float f1 = (float)((p_188299_2_ - d2) / 2.0D / p_188299_10_ + 0.5D);
/* 325 */         float f2 = (float)((p_188299_6_ - d4) / 2.0D / p_188299_10_ + 0.5D);
/* 326 */         float f3 = (float)((p_188299_6_ - d5) / 2.0D / p_188299_10_ + 0.5D);
/* 327 */         vertexbuffer.func_181662_b(d1, d3, d4).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 328 */         vertexbuffer.func_181662_b(d1, d3, d5).func_187315_a(f, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 329 */         vertexbuffer.func_181662_b(d2, d3, d5).func_187315_a(f1, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 330 */         vertexbuffer.func_181662_b(d2, d3, d4).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 331 */         if (state.func_177230_c() instanceof BlockStairs && state.func_177229_b((IProperty)BlockStairs.field_176310_M) == BlockStairs.EnumShape.STRAIGHT) {
/* 332 */           d3 += 0.5D;
/* 333 */           EnumFacing facing = (EnumFacing)state.func_177229_b((IProperty)BlockHorizontal.field_185512_D);
/* 334 */           switch (facing) {
/*     */             case NORTH:
/* 336 */               d5 -= 0.5D;
/*     */               break;
/*     */             case EAST:
/* 339 */               d1 += 0.5D;
/*     */               break;
/*     */             case SOUTH:
/* 342 */               d4 += 0.5D;
/*     */               break;
/*     */             case WEST:
/* 345 */               d2 -= 0.5D;
/*     */               break;
/*     */           } 
/* 348 */           f = (float)((p_188299_2_ - d1) / 2.0D / p_188299_10_ + 0.5D);
/* 349 */           f1 = (float)((p_188299_2_ - d2) / 2.0D / p_188299_10_ + 0.5D);
/* 350 */           f2 = (float)((p_188299_6_ - d4) / 2.0D / p_188299_10_ + 0.5D);
/* 351 */           f3 = (float)((p_188299_6_ - d5) / 2.0D / p_188299_10_ + 0.5D);
/* 352 */           vertexbuffer.func_181662_b(d1, d3, d4).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 353 */           vertexbuffer.func_181662_b(d1, d3, d5).func_187315_a(f, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 354 */           vertexbuffer.func_181662_b(d2, d3, d5).func_187315_a(f1, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 355 */           vertexbuffer.func_181662_b(d2, d3, d4).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderDecalNorth(EntityDecal entityIn, double x, double y, double z, float shadowAlpha, float partialTicks) {
/* 363 */     GlStateManager.func_179147_l();
/* 364 */     GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 365 */     this.field_76990_c.field_78724_e.func_110577_a(getEntityTexture(entityIn));
/* 366 */     World world = getWorldFromRenderManager();
/* 367 */     GlStateManager.func_179132_a(false);
/* 368 */     double f = 1.0D;
/* 369 */     double d5 = entityIn.field_70142_S + (entityIn.field_70165_t - entityIn.field_70142_S) * partialTicks;
/* 370 */     double d0 = entityIn.field_70137_T + (entityIn.field_70163_u - entityIn.field_70137_T) * partialTicks;
/* 371 */     double d1 = entityIn.field_70136_U + (entityIn.field_70161_v - entityIn.field_70136_U) * partialTicks;
/* 372 */     int i = MathHelper.func_76128_c(d5 - f);
/* 373 */     int j = MathHelper.func_76128_c(d5 + f);
/* 374 */     int k = MathHelper.func_76128_c(d0 - f);
/* 375 */     int l = MathHelper.func_76128_c(d0 + f);
/* 376 */     int i1 = MathHelper.func_76128_c(d1 - 0.25D);
/* 377 */     int j1 = MathHelper.func_76128_c(d1 + 0.25D);
/* 378 */     double d2 = x - d5;
/* 379 */     double d3 = y - d0;
/* 380 */     double d4 = z - d1;
/* 381 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 382 */     BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 383 */     vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181712_l);
/* 384 */     Iterator<BlockPos> var33 = BlockPos.func_177975_b(new BlockPos(i, k, i1), new BlockPos(j, l, j1)).iterator();
/*     */     
/* 386 */     while (var33.hasNext()) {
/* 387 */       BlockPos blockpos = var33.next();
/* 388 */       IBlockState iblockstate = world.func_180495_p(blockpos.func_177978_c());
/* 389 */       if (iblockstate.func_185901_i() != EnumBlockRenderType.INVISIBLE && !world.func_180495_p(blockpos).func_185914_p()) {
/* 390 */         renderDecalSingleNorth(iblockstate, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4);
/*     */       }
/*     */     } 
/*     */     
/* 394 */     tessellator.func_78381_a();
/* 395 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 396 */     GlStateManager.func_179084_k();
/* 397 */     GlStateManager.func_179132_a(true);
/*     */   }
/*     */   
/*     */   private void renderDecalSingleNorth(IBlockState state, double p_188299_2_, double p_188299_4_, double p_188299_6_, BlockPos p_188299_8_, float p_188299_9_, double p_188299_10_, double p_188299_11_, double p_188299_13_, double p_188299_15_) {
/* 401 */     boolean bool = false;
/* 402 */     double offset = 0.0D;
/*     */     
/* 404 */     bool = state.isSideSolid((IBlockAccess)getWorldFromRenderManager(), p_188299_8_, EnumFacing.SOUTH);
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
/* 419 */     if (bool) {
/* 420 */       Tessellator tessellator = Tessellator.func_178181_a();
/* 421 */       BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 422 */       double d0 = p_188299_9_;
/* 423 */       if (d0 >= 0.0D) {
/* 424 */         if (d0 > 1.0D) {
/* 425 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 428 */         AxisAlignedBB axisalignedbb = state.func_185900_c((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 429 */         double x1 = p_188299_8_.func_177958_n() + axisalignedbb.field_72340_a + p_188299_11_;
/* 430 */         double x2 = p_188299_8_.func_177958_n() + axisalignedbb.field_72336_d + p_188299_11_;
/* 431 */         double y1 = p_188299_8_.func_177956_o() + axisalignedbb.field_72338_b + p_188299_13_;
/* 432 */         double y2 = p_188299_8_.func_177956_o() + axisalignedbb.field_72337_e + p_188299_13_;
/* 433 */         double z1 = p_188299_8_.func_177952_p() + axisalignedbb.field_72339_c + p_188299_15_ + 0.015625D;
/* 434 */         float f = (float)((p_188299_2_ - x1) / 2.0D / p_188299_10_ + 0.5D);
/* 435 */         float f1 = (float)((p_188299_2_ - x2) / 2.0D / p_188299_10_ + 0.5D);
/* 436 */         float f2 = (float)((p_188299_4_ - y1) / 2.0D / p_188299_10_ + 0.5D);
/* 437 */         float f3 = (float)((p_188299_4_ - y2) / 2.0D / p_188299_10_ + 0.5D);
/* 438 */         vertexbuffer.func_181662_b(x2, y1, z1 - offset).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 439 */         vertexbuffer.func_181662_b(x2, y2, z1 - offset).func_187315_a(f1, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 440 */         vertexbuffer.func_181662_b(x1, y2, z1 - offset).func_187315_a(f, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 441 */         vertexbuffer.func_181662_b(x1, y1, z1 - offset).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */       } 
/* 443 */     } else if (state.func_177230_c() instanceof net.minecraft.block.BlockSlab || state.func_177230_c() instanceof BlockStairs) {
/* 444 */       Tessellator tessellator = Tessellator.func_178181_a();
/* 445 */       BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 446 */       double d0 = p_188299_9_;
/* 447 */       if (d0 >= 0.0D) {
/* 448 */         if (d0 > 1.0D) {
/* 449 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 452 */         AxisAlignedBB axisalignedbb = state.func_185900_c((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 453 */         double x1 = p_188299_8_.func_177958_n() + axisalignedbb.field_72340_a + p_188299_11_;
/* 454 */         double x2 = p_188299_8_.func_177958_n() + axisalignedbb.field_72336_d + p_188299_11_;
/* 455 */         double y1 = p_188299_8_.func_177956_o() + axisalignedbb.field_72338_b + p_188299_13_;
/* 456 */         double y2 = p_188299_8_.func_177956_o() + axisalignedbb.field_72337_e + p_188299_13_;
/* 457 */         double z1 = p_188299_8_.func_177952_p() + axisalignedbb.field_72339_c + p_188299_15_ + 0.015625D;
/* 458 */         if (state.func_177230_c() instanceof BlockStairs) {
/* 459 */           EnumFacing facing = (EnumFacing)state.func_177229_b((IProperty)BlockHorizontal.field_185512_D);
/* 460 */           if (facing == EnumFacing.NORTH) {
/* 461 */             double newz1 = z1 - 0.5D;
/* 462 */             float f4 = (float)((p_188299_2_ - x1) / 2.0D / p_188299_10_ + 0.5D);
/* 463 */             float f5 = (float)((p_188299_2_ - x2) / 2.0D / p_188299_10_ + 0.5D);
/* 464 */             float f6 = (float)((p_188299_4_ - y1) / 2.0D / p_188299_10_ + 0.5D);
/* 465 */             float f3 = (float)((p_188299_4_ - y2) / 2.0D / p_188299_10_ + 0.5D);
/* 466 */             vertexbuffer.func_181662_b(x2, y1, newz1).func_187315_a(f5, f6).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 467 */             vertexbuffer.func_181662_b(x2, y2, newz1).func_187315_a(f5, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 468 */             vertexbuffer.func_181662_b(x1, y2, newz1).func_187315_a(f4, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 469 */             vertexbuffer.func_181662_b(x1, y1, newz1).func_187315_a(f4, f6).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */           } 
/*     */           
/* 472 */           if (state.func_177229_b((IProperty)BlockStairs.field_176308_b) == BlockStairs.EnumHalf.TOP) {
/* 473 */             y1 += 0.5D;
/*     */           } else {
/* 475 */             y2 -= 0.5D;
/*     */           } 
/*     */         } 
/*     */         
/* 479 */         float f = (float)((p_188299_2_ - x1) / 2.0D / p_188299_10_ + 0.5D);
/* 480 */         float f1 = (float)((p_188299_2_ - x2) / 2.0D / p_188299_10_ + 0.5D);
/* 481 */         float f2 = (float)((p_188299_4_ - y1) / 2.0D / p_188299_10_ + 0.5D);
/* 482 */         f = (float)((p_188299_4_ - y2) / 2.0D / p_188299_10_ + 0.5D);
/* 483 */         vertexbuffer.func_181662_b(x2, y1, z1).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 484 */         vertexbuffer.func_181662_b(x2, y2, z1).func_187315_a(f1, f).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 485 */         vertexbuffer.func_181662_b(x1, y2, z1).func_187315_a(f, f).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 486 */         vertexbuffer.func_181662_b(x1, y1, z1).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderDecalEast(EntityDecal entityIn, double x, double y, double z, float shadowAlpha, float partialTicks) {
/* 493 */     GlStateManager.func_179147_l();
/* 494 */     GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 495 */     this.field_76990_c.field_78724_e.func_110577_a(getEntityTexture(entityIn));
/* 496 */     World world = getWorldFromRenderManager();
/* 497 */     GlStateManager.func_179132_a(false);
/* 498 */     double f = 1.0D;
/* 499 */     double d5 = entityIn.field_70142_S + (entityIn.field_70165_t - entityIn.field_70142_S) * partialTicks;
/* 500 */     double d0 = entityIn.field_70137_T + (entityIn.field_70163_u - entityIn.field_70137_T) * partialTicks;
/* 501 */     double d1 = entityIn.field_70136_U + (entityIn.field_70161_v - entityIn.field_70136_U) * partialTicks;
/* 502 */     int i = MathHelper.func_76128_c(d5 - 0.25D);
/* 503 */     int j = MathHelper.func_76128_c(d5 + 0.25D);
/* 504 */     int k = MathHelper.func_76128_c(d0 - f);
/* 505 */     int l = MathHelper.func_76128_c(d0 + f);
/* 506 */     int i1 = MathHelper.func_76128_c(d1 - f);
/* 507 */     int j1 = MathHelper.func_76128_c(d1 + 1.0D);
/* 508 */     double d2 = x - d5;
/* 509 */     double d3 = y - d0;
/* 510 */     double d4 = z - d1;
/* 511 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 512 */     BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 513 */     vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181712_l);
/* 514 */     Iterator<BlockPos> var33 = BlockPos.func_177975_b(new BlockPos(i, k, i1), new BlockPos(j, l, j1)).iterator();
/*     */     
/* 516 */     while (var33.hasNext()) {
/* 517 */       BlockPos blockpos = var33.next();
/* 518 */       IBlockState iblockstate = world.func_180495_p(blockpos.func_177974_f());
/* 519 */       if (iblockstate.func_185901_i() != EnumBlockRenderType.INVISIBLE && !world.func_180495_p(blockpos).func_185914_p()) {
/* 520 */         renderDecalSingleEast(iblockstate, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4);
/*     */       }
/*     */     } 
/*     */     
/* 524 */     tessellator.func_78381_a();
/* 525 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 526 */     GlStateManager.func_179084_k();
/* 527 */     GlStateManager.func_179132_a(true);
/*     */   }
/*     */   
/*     */   private void renderDecalSingleEast(IBlockState state, double p_188299_2_, double p_188299_4_, double p_188299_6_, BlockPos p_188299_8_, float p_188299_9_, double p_188299_10_, double p_188299_11_, double p_188299_13_, double p_188299_15_) {
/* 531 */     boolean bool = false;
/* 532 */     double offset = 0.0D;
/*     */     
/* 534 */     bool = state.isSideSolid((IBlockAccess)getWorldFromRenderManager(), p_188299_8_, EnumFacing.WEST);
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
/* 549 */     if (bool) {
/* 550 */       Tessellator tessellator = Tessellator.func_178181_a();
/* 551 */       BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 552 */       double d0 = p_188299_9_;
/* 553 */       if (d0 >= 0.0D) {
/* 554 */         if (d0 > 1.0D) {
/* 555 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 558 */         AxisAlignedBB axisalignedbb = state.func_185900_c((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 559 */         double x2 = p_188299_8_.func_177958_n() + axisalignedbb.field_72336_d + p_188299_11_ - 0.015625D;
/* 560 */         double y1 = p_188299_8_.func_177956_o() + axisalignedbb.field_72338_b + p_188299_13_;
/* 561 */         double y2 = p_188299_8_.func_177956_o() + axisalignedbb.field_72337_e + p_188299_13_;
/* 562 */         double z1 = p_188299_8_.func_177952_p() + axisalignedbb.field_72339_c + p_188299_15_;
/* 563 */         double z2 = p_188299_8_.func_177952_p() + axisalignedbb.field_72334_f + p_188299_15_;
/* 564 */         float f = (float)((p_188299_4_ - y1) / 2.0D / p_188299_10_ + 0.5D);
/* 565 */         float f1 = (float)((p_188299_4_ - y2) / 2.0D / p_188299_10_ + 0.5D);
/* 566 */         float f2 = (float)((p_188299_6_ - z1) / 2.0D / p_188299_10_ + 0.5D);
/* 567 */         float f3 = (float)((p_188299_6_ - z2) / 2.0D / p_188299_10_ + 0.5D);
/* 568 */         vertexbuffer.func_181662_b(x2 + offset, y1, z2).func_187315_a(f, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 569 */         vertexbuffer.func_181662_b(x2 + offset, y2, z2).func_187315_a(f1, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 570 */         vertexbuffer.func_181662_b(x2 + offset, y2, z1).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 571 */         vertexbuffer.func_181662_b(x2 + offset, y1, z1).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */       } 
/* 573 */     } else if (state.func_177230_c() instanceof net.minecraft.block.BlockSlab || state.func_177230_c() instanceof BlockStairs) {
/* 574 */       Tessellator tessellator = Tessellator.func_178181_a();
/* 575 */       BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 576 */       double d0 = p_188299_9_;
/* 577 */       if (d0 >= 0.0D) {
/* 578 */         if (d0 > 1.0D) {
/* 579 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 582 */         AxisAlignedBB axisalignedbb = state.func_185900_c((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 583 */         double x2 = p_188299_8_.func_177958_n() + axisalignedbb.field_72336_d + p_188299_11_ - 0.015625D;
/* 584 */         double y1 = p_188299_8_.func_177956_o() + axisalignedbb.field_72338_b + p_188299_13_;
/* 585 */         double y2 = p_188299_8_.func_177956_o() + axisalignedbb.field_72337_e + p_188299_13_;
/* 586 */         double z1 = p_188299_8_.func_177952_p() + axisalignedbb.field_72339_c + p_188299_15_;
/* 587 */         double z2 = p_188299_8_.func_177952_p() + axisalignedbb.field_72334_f + p_188299_15_;
/* 588 */         if (state.func_177230_c() instanceof BlockStairs) {
/* 589 */           EnumFacing facing = (EnumFacing)state.func_177229_b((IProperty)BlockHorizontal.field_185512_D);
/* 590 */           if (facing == EnumFacing.EAST) {
/* 591 */             double newx2 = x2 + 0.5D;
/* 592 */             float f4 = (float)((p_188299_4_ - y1) / 2.0D / p_188299_10_ + 0.5D);
/* 593 */             float f5 = (float)((p_188299_4_ - y2) / 2.0D / p_188299_10_ + 0.5D);
/* 594 */             float f6 = (float)((p_188299_6_ - z1) / 2.0D / p_188299_10_ + 0.5D);
/* 595 */             float f3 = (float)((p_188299_6_ - z2) / 2.0D / p_188299_10_ + 0.5D);
/* 596 */             vertexbuffer.func_181662_b(newx2, y1, z2).func_187315_a(f4, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 597 */             vertexbuffer.func_181662_b(newx2, y2, z2).func_187315_a(f5, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 598 */             vertexbuffer.func_181662_b(newx2, y2, z1).func_187315_a(f5, f6).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 599 */             vertexbuffer.func_181662_b(newx2, y1, z1).func_187315_a(f4, f6).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */           } 
/*     */           
/* 602 */           if (state.func_177229_b((IProperty)BlockStairs.field_176308_b) == BlockStairs.EnumHalf.TOP) {
/* 603 */             y1 += 0.5D;
/*     */           } else {
/* 605 */             y2 -= 0.5D;
/*     */           } 
/*     */         } 
/*     */         
/* 609 */         float f = (float)((p_188299_4_ - y1) / 2.0D / p_188299_10_ + 0.5D);
/* 610 */         float f1 = (float)((p_188299_4_ - y2) / 2.0D / p_188299_10_ + 0.5D);
/* 611 */         float f2 = (float)((p_188299_6_ - z1) / 2.0D / p_188299_10_ + 0.5D);
/* 612 */         f = (float)((p_188299_6_ - z2) / 2.0D / p_188299_10_ + 0.5D);
/* 613 */         vertexbuffer.func_181662_b(x2, y1, z2).func_187315_a(f, f).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 614 */         vertexbuffer.func_181662_b(x2, y2, z2).func_187315_a(f1, f).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 615 */         vertexbuffer.func_181662_b(x2, y2, z1).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 616 */         vertexbuffer.func_181662_b(x2, y1, z1).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderDecalSouth(EntityDecal entityIn, double x, double y, double z, float shadowAlpha, float partialTicks) {
/* 623 */     GlStateManager.func_179147_l();
/* 624 */     GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 625 */     this.field_76990_c.field_78724_e.func_110577_a(getEntityTexture(entityIn));
/* 626 */     World world = getWorldFromRenderManager();
/* 627 */     GlStateManager.func_179132_a(false);
/* 628 */     double f = 1.0D;
/* 629 */     double d5 = entityIn.field_70142_S + (entityIn.field_70165_t - entityIn.field_70142_S) * partialTicks;
/* 630 */     double d0 = entityIn.field_70137_T + (entityIn.field_70163_u - entityIn.field_70137_T) * partialTicks;
/* 631 */     double d1 = entityIn.field_70136_U + (entityIn.field_70161_v - entityIn.field_70136_U) * partialTicks;
/* 632 */     int i = MathHelper.func_76128_c(d5 - f);
/* 633 */     int j = MathHelper.func_76128_c(d5 + f);
/* 634 */     int k = MathHelper.func_76128_c(d0 - f);
/* 635 */     int l = MathHelper.func_76128_c(d0 + f);
/* 636 */     int i1 = MathHelper.func_76128_c(d1 - 0.25D);
/* 637 */     int j1 = MathHelper.func_76128_c(d1 + 0.25D);
/* 638 */     double d2 = x - d5;
/* 639 */     double d3 = y - d0;
/* 640 */     double d4 = z - d1;
/* 641 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 642 */     BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 643 */     vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181712_l);
/* 644 */     Iterator<BlockPos> var33 = BlockPos.func_177975_b(new BlockPos(i, k, i1), new BlockPos(j, l, j1)).iterator();
/*     */     
/* 646 */     while (var33.hasNext()) {
/* 647 */       BlockPos blockpos = var33.next();
/* 648 */       IBlockState iblockstate = world.func_180495_p(blockpos.func_177968_d());
/* 649 */       if (iblockstate.func_185901_i() != EnumBlockRenderType.INVISIBLE && !world.func_180495_p(blockpos).func_185914_p()) {
/* 650 */         renderDecalSingleSouth(iblockstate, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4);
/*     */       }
/*     */     } 
/*     */     
/* 654 */     tessellator.func_78381_a();
/* 655 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 656 */     GlStateManager.func_179084_k();
/* 657 */     GlStateManager.func_179132_a(true);
/*     */   }
/*     */   
/*     */   private void renderDecalSingleSouth(IBlockState state, double p_188299_2_, double p_188299_4_, double p_188299_6_, BlockPos p_188299_8_, float p_188299_9_, double p_188299_10_, double p_188299_11_, double p_188299_13_, double p_188299_15_) {
/* 661 */     boolean bool = false;
/* 662 */     double offset = 0.0D;
/*     */     
/* 664 */     bool = state.isSideSolid((IBlockAccess)getWorldFromRenderManager(), p_188299_8_, EnumFacing.NORTH);
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
/* 679 */     if (bool) {
/* 680 */       Tessellator tessellator = Tessellator.func_178181_a();
/* 681 */       BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 682 */       double d0 = p_188299_9_;
/* 683 */       if (d0 >= 0.0D) {
/* 684 */         if (d0 > 1.0D) {
/* 685 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 688 */         AxisAlignedBB axisalignedbb = state.func_185900_c((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 689 */         double x1 = p_188299_8_.func_177958_n() + axisalignedbb.field_72340_a + p_188299_11_;
/* 690 */         double x2 = p_188299_8_.func_177958_n() + axisalignedbb.field_72336_d + p_188299_11_;
/* 691 */         double y1 = p_188299_8_.func_177956_o() + axisalignedbb.field_72338_b + p_188299_13_;
/* 692 */         double y2 = p_188299_8_.func_177956_o() + axisalignedbb.field_72337_e + p_188299_13_;
/* 693 */         double z2 = p_188299_8_.func_177952_p() + axisalignedbb.field_72334_f + p_188299_15_ - 0.015625D;
/* 694 */         float f = (float)((p_188299_2_ - x1) / 2.0D / p_188299_10_ + 0.5D);
/* 695 */         float f1 = (float)((p_188299_2_ - x2) / 2.0D / p_188299_10_ + 0.5D);
/* 696 */         float f2 = (float)((p_188299_4_ - y1) / 2.0D / p_188299_10_ + 0.5D);
/* 697 */         float f3 = (float)((p_188299_4_ - y2) / 2.0D / p_188299_10_ + 0.5D);
/* 698 */         vertexbuffer.func_181662_b(x1, y1, z2 + offset).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 699 */         vertexbuffer.func_181662_b(x1, y2, z2 + offset).func_187315_a(f, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 700 */         vertexbuffer.func_181662_b(x2, y2, z2 + offset).func_187315_a(f1, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 701 */         vertexbuffer.func_181662_b(x2, y1, z2 + offset).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */       } 
/* 703 */     } else if (state.func_177230_c() instanceof net.minecraft.block.BlockSlab || state.func_177230_c() instanceof BlockStairs) {
/* 704 */       Tessellator tessellator = Tessellator.func_178181_a();
/* 705 */       BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 706 */       double d0 = p_188299_9_;
/* 707 */       if (d0 >= 0.0D) {
/* 708 */         if (d0 > 1.0D) {
/* 709 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 712 */         AxisAlignedBB axisalignedbb = state.func_185900_c((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 713 */         double x1 = p_188299_8_.func_177958_n() + axisalignedbb.field_72340_a + p_188299_11_;
/* 714 */         double x2 = p_188299_8_.func_177958_n() + axisalignedbb.field_72336_d + p_188299_11_;
/* 715 */         double y1 = p_188299_8_.func_177956_o() + axisalignedbb.field_72338_b + p_188299_13_;
/* 716 */         double y2 = p_188299_8_.func_177956_o() + axisalignedbb.field_72337_e + p_188299_13_;
/* 717 */         double z2 = p_188299_8_.func_177952_p() + axisalignedbb.field_72334_f + p_188299_15_ - 0.015625D;
/* 718 */         if (state.func_177230_c() instanceof BlockStairs) {
/* 719 */           EnumFacing facing = (EnumFacing)state.func_177229_b((IProperty)BlockHorizontal.field_185512_D);
/* 720 */           if (facing == EnumFacing.SOUTH) {
/* 721 */             double newz2 = z2 + 0.5D;
/* 722 */             float f4 = (float)((p_188299_2_ - x1) / 2.0D / p_188299_10_ + 0.5D);
/* 723 */             float f5 = (float)((p_188299_2_ - x2) / 2.0D / p_188299_10_ + 0.5D);
/* 724 */             float f6 = (float)((p_188299_4_ - y1) / 2.0D / p_188299_10_ + 0.5D);
/* 725 */             float f3 = (float)((p_188299_4_ - y2) / 2.0D / p_188299_10_ + 0.5D);
/* 726 */             vertexbuffer.func_181662_b(x1, y1, newz2).func_187315_a(f4, f6).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 727 */             vertexbuffer.func_181662_b(x1, y2, newz2).func_187315_a(f4, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 728 */             vertexbuffer.func_181662_b(x2, y2, newz2).func_187315_a(f5, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 729 */             vertexbuffer.func_181662_b(x2, y1, newz2).func_187315_a(f5, f6).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */           } 
/*     */           
/* 732 */           if (state.func_177229_b((IProperty)BlockStairs.field_176308_b) == BlockStairs.EnumHalf.TOP) {
/* 733 */             y1 += 0.5D;
/*     */           } else {
/* 735 */             y2 -= 0.5D;
/*     */           } 
/*     */         } 
/*     */         
/* 739 */         float f = (float)((p_188299_2_ - x1) / 2.0D / p_188299_10_ + 0.5D);
/* 740 */         float f1 = (float)((p_188299_2_ - x2) / 2.0D / p_188299_10_ + 0.5D);
/* 741 */         float f2 = (float)((p_188299_4_ - y1) / 2.0D / p_188299_10_ + 0.5D);
/* 742 */         f = (float)((p_188299_4_ - y2) / 2.0D / p_188299_10_ + 0.5D);
/* 743 */         vertexbuffer.func_181662_b(x1, y1, z2).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 744 */         vertexbuffer.func_181662_b(x1, y2, z2).func_187315_a(f, f).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 745 */         vertexbuffer.func_181662_b(x2, y2, z2).func_187315_a(f1, f).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 746 */         vertexbuffer.func_181662_b(x2, y1, z2).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderDecalWest(EntityDecal entityIn, double x, double y, double z, float shadowAlpha, float partialTicks) {
/* 753 */     GlStateManager.func_179147_l();
/* 754 */     GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 755 */     this.field_76990_c.field_78724_e.func_110577_a(getEntityTexture(entityIn));
/* 756 */     World world = getWorldFromRenderManager();
/* 757 */     GlStateManager.func_179132_a(false);
/* 758 */     double f = 1.0D;
/* 759 */     double d5 = entityIn.field_70142_S + (entityIn.field_70165_t - entityIn.field_70142_S) * partialTicks;
/* 760 */     double d0 = entityIn.field_70137_T + (entityIn.field_70163_u - entityIn.field_70137_T) * partialTicks;
/* 761 */     double d1 = entityIn.field_70136_U + (entityIn.field_70161_v - entityIn.field_70136_U) * partialTicks;
/* 762 */     int i = MathHelper.func_76128_c(d5 - 0.25D);
/* 763 */     int j = MathHelper.func_76128_c(d5 + 0.25D);
/* 764 */     int k = MathHelper.func_76128_c(d0 - f);
/* 765 */     int l = MathHelper.func_76128_c(d0 + f);
/* 766 */     int i1 = MathHelper.func_76128_c(d1 - f);
/* 767 */     int j1 = MathHelper.func_76128_c(d1 + 1.0D);
/* 768 */     double d2 = x - d5;
/* 769 */     double d3 = y - d0;
/* 770 */     double d4 = z - d1;
/* 771 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 772 */     BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 773 */     vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181712_l);
/* 774 */     Iterator<BlockPos> var33 = BlockPos.func_177975_b(new BlockPos(i, k, i1), new BlockPos(j, l, j1)).iterator();
/*     */     
/* 776 */     while (var33.hasNext()) {
/* 777 */       BlockPos blockpos = var33.next();
/* 778 */       IBlockState iblockstate = world.func_180495_p(blockpos.func_177976_e());
/* 779 */       if (iblockstate.func_185901_i() != EnumBlockRenderType.INVISIBLE && !world.func_180495_p(blockpos).func_185914_p()) {
/* 780 */         renderDecalSingleWest(iblockstate, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4);
/*     */       }
/*     */     } 
/*     */     
/* 784 */     tessellator.func_78381_a();
/* 785 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 786 */     GlStateManager.func_179084_k();
/* 787 */     GlStateManager.func_179132_a(true);
/*     */   }
/*     */   
/*     */   private void renderDecalSingleWest(IBlockState state, double p_188299_2_, double p_188299_4_, double p_188299_6_, BlockPos p_188299_8_, float p_188299_9_, double p_188299_10_, double p_188299_11_, double p_188299_13_, double p_188299_15_) {
/* 791 */     boolean bool = false;
/* 792 */     double offset = 0.0D;
/*     */     
/* 794 */     bool = state.isSideSolid((IBlockAccess)getWorldFromRenderManager(), p_188299_8_, EnumFacing.EAST);
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
/* 810 */     if (bool) {
/* 811 */       Tessellator tessellator = Tessellator.func_178181_a();
/* 812 */       BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 813 */       double d0 = p_188299_9_;
/* 814 */       if (d0 >= 0.0D) {
/* 815 */         if (d0 > 1.0D) {
/* 816 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 819 */         AxisAlignedBB axisalignedbb = state.func_185900_c((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 820 */         double x1 = p_188299_8_.func_177958_n() + axisalignedbb.field_72340_a + p_188299_11_ + 0.015625D;
/* 821 */         double y1 = p_188299_8_.func_177956_o() + axisalignedbb.field_72338_b + p_188299_13_;
/* 822 */         double y2 = p_188299_8_.func_177956_o() + axisalignedbb.field_72337_e + p_188299_13_;
/* 823 */         double z1 = p_188299_8_.func_177952_p() + axisalignedbb.field_72339_c + p_188299_15_;
/* 824 */         double z2 = p_188299_8_.func_177952_p() + axisalignedbb.field_72334_f + p_188299_15_;
/* 825 */         float f = (float)((p_188299_4_ - y1) / 2.0D / p_188299_10_ + 0.5D);
/* 826 */         float f1 = (float)((p_188299_4_ - y2) / 2.0D / p_188299_10_ + 0.5D);
/* 827 */         float f2 = (float)((p_188299_6_ - z1) / 2.0D / p_188299_10_ + 0.5D);
/* 828 */         float f3 = (float)((p_188299_6_ - z2) / 2.0D / p_188299_10_ + 0.5D);
/* 829 */         vertexbuffer.func_181662_b(x1 - offset, y1, z1).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 830 */         vertexbuffer.func_181662_b(x1 - offset, y2, z1).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 831 */         vertexbuffer.func_181662_b(x1 - offset, y2, z2).func_187315_a(f1, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 832 */         vertexbuffer.func_181662_b(x1 - offset, y1, z2).func_187315_a(f, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */       } 
/* 834 */     } else if (state.func_177230_c() instanceof net.minecraft.block.BlockSlab || state.func_177230_c() instanceof BlockStairs) {
/* 835 */       Tessellator tessellator = Tessellator.func_178181_a();
/* 836 */       BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 837 */       double d0 = p_188299_9_;
/* 838 */       if (d0 >= 0.0D) {
/* 839 */         if (d0 > 1.0D) {
/* 840 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 843 */         AxisAlignedBB axisalignedbb = state.func_185900_c((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 844 */         double x1 = p_188299_8_.func_177958_n() + axisalignedbb.field_72340_a + p_188299_11_ + 0.015625D;
/* 845 */         double y1 = p_188299_8_.func_177956_o() + axisalignedbb.field_72338_b + p_188299_13_;
/* 846 */         double y2 = p_188299_8_.func_177956_o() + axisalignedbb.field_72337_e + p_188299_13_;
/* 847 */         double z1 = p_188299_8_.func_177952_p() + axisalignedbb.field_72339_c + p_188299_15_;
/* 848 */         double z2 = p_188299_8_.func_177952_p() + axisalignedbb.field_72334_f + p_188299_15_;
/* 849 */         if (state.func_177230_c() instanceof BlockStairs) {
/* 850 */           EnumFacing facing = (EnumFacing)state.func_177229_b((IProperty)BlockHorizontal.field_185512_D);
/* 851 */           if (facing == EnumFacing.WEST) {
/* 852 */             double newx1 = x1 - 0.5D;
/* 853 */             float f4 = (float)((p_188299_4_ - y1) / 2.0D / p_188299_10_ + 0.5D);
/* 854 */             float f5 = (float)((p_188299_4_ - y2) / 2.0D / p_188299_10_ + 0.5D);
/* 855 */             float f6 = (float)((p_188299_6_ - z1) / 2.0D / p_188299_10_ + 0.5D);
/* 856 */             float f7 = (float)((p_188299_6_ - z2) / 2.0D / p_188299_10_ + 0.5D);
/* 857 */             vertexbuffer.func_181662_b(newx1, y1, z1).func_187315_a(f4, f6).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 858 */             vertexbuffer.func_181662_b(newx1, y2, z1).func_187315_a(f5, f6).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 859 */             vertexbuffer.func_181662_b(newx1, y2, z2).func_187315_a(f5, f7).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 860 */             vertexbuffer.func_181662_b(newx1, y1, z2).func_187315_a(f4, f7).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */           } 
/*     */           
/* 863 */           if (state.func_177229_b((IProperty)BlockStairs.field_176308_b) == BlockStairs.EnumHalf.TOP) {
/* 864 */             y1 += 0.5D;
/*     */           } else {
/* 866 */             y2 -= 0.5D;
/*     */           } 
/*     */         } 
/*     */         
/* 870 */         float f = (float)((p_188299_4_ - y1) / 2.0D / p_188299_10_ + 0.5D);
/* 871 */         float f1 = (float)((p_188299_4_ - y2) / 2.0D / p_188299_10_ + 0.5D);
/* 872 */         float f2 = (float)((p_188299_6_ - z1) / 2.0D / p_188299_10_ + 0.5D);
/* 873 */         float f3 = (float)((p_188299_6_ - z2) / 2.0D / p_188299_10_ + 0.5D);
/* 874 */         vertexbuffer.func_181662_b(x1, y1, z1).func_187315_a(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 875 */         vertexbuffer.func_181662_b(x1, y2, z1).func_187315_a(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 876 */         vertexbuffer.func_181662_b(x1, y2, z2).func_187315_a(f1, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/* 877 */         vertexbuffer.func_181662_b(x1, y1, z2).func_187315_a(f, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).func_181663_c(0.0F, 0.0F, 0.0F).func_181675_d();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class Factory
/*     */     implements IRenderFactory {
/*     */     public Render createRenderFor(RenderManager manager) {
/* 885 */       return new RenderDecal(manager);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\renderers\RenderDecal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */