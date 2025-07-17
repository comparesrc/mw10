/*     */ package com.modularwarfare.utility;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.launchwrapper.Launch;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraftforge.client.model.pipeline.LightUtil;
/*     */ import net.minecraftforge.fml.relauncher.FMLInjectionData;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModUtil
/*     */ {
/*     */   public static final int INVENTORY_SLOT_SIZE_PIXELS = 18;
/*     */   public static final int BACKPACK_SLOT_OFFSET_X = 76;
/*     */   public static final int BACKPACK_SLOT_OFFSET_Y = 7;
/*     */   public static final int BACKPACK_CONTENT_OFFSET_X = 180;
/*     */   public static final int BACKPACK_CONTENT_OFFSET_Y = 18;
/*  36 */   private static String OS = System.getProperty("os.name").toLowerCase();
/*     */   
/*     */   public static boolean isWindows() {
/*  39 */     return (OS.indexOf("win") >= 0);
/*     */   }
/*     */   
/*     */   public static boolean isMac() {
/*  43 */     return (OS.indexOf("mac") >= 0);
/*     */   }
/*     */   
/*     */   public static boolean isUnix() {
/*  47 */     return (OS.indexOf("nix") >= 0 || OS
/*  48 */       .indexOf("nux") >= 0 || OS
/*  49 */       .indexOf("aix") > 0);
/*     */   }
/*     */   
/*     */   public static boolean isSolaris() {
/*  53 */     return (OS.indexOf("sunos") >= 0);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void renderLightModel(IBakedModel model, int alpha) {
/*  58 */     GlStateManager.func_179094_E();
/*  59 */     GlStateManager.func_179109_b(-0.4F, -0.4F, -0.4F);
/*  60 */     Minecraft.func_71410_x().func_110434_K().func_110577_a(TextureMap.field_110575_b);
/*  61 */     GlStateManager.func_179094_E();
/*  62 */     ItemCameraTransforms.func_188034_a(model.func_177552_f().func_181688_b(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND), false);
/*  63 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  64 */     BufferBuilder vertexbuffer = tessellator.func_178180_c();
/*  65 */     vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_176599_b);
/*  66 */     for (EnumFacing enumfacing : EnumFacing.values()) {
/*  67 */       renderLightQuads(vertexbuffer, model.func_188616_a((IBlockState)null, enumfacing, 0L), alpha);
/*     */     }
/*  69 */     renderLightQuads(vertexbuffer, model.func_188616_a((IBlockState)null, (EnumFacing)null, 0L), alpha);
/*  70 */     tessellator.func_78381_a();
/*  71 */     GlStateManager.func_187407_a(GlStateManager.CullFace.BACK);
/*  72 */     GlStateManager.func_179121_F();
/*  73 */     Minecraft.func_71410_x().func_110434_K().func_110577_a(TextureMap.field_110575_b);
/*  74 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static int getBrightness(Entity ent) {
/*  79 */     BlockPos blockpos = new BlockPos(Math.floor(ent.field_70165_t), ent.field_70163_u, Math.floor(ent.field_70161_v));
/*  80 */     WorldClient worldClient = (Minecraft.func_71410_x()).field_71441_e;
/*  81 */     int skyLightSub = worldClient.func_72967_a(1.0F);
/*  82 */     int blockLight = worldClient.func_175642_b(EnumSkyBlock.BLOCK, blockpos);
/*  83 */     int skyLight = worldClient.func_175642_b(EnumSkyBlock.SKY, blockpos) - skyLightSub;
/*  84 */     return Math.max(blockLight, skyLight);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private static void renderLightQuads(BufferBuilder renderer, List<BakedQuad> quads, int alpha) {
/*  89 */     int i = 0;
/*  90 */     int argb = ColorUtils.getARGB(255, 255, 255, alpha);
/*  91 */     for (int j = quads.size(); i < j; i++) {
/*  92 */       LightUtil.renderQuadColor(renderer, quads.get(i), argb);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isIDE() {
/*  97 */     return ((Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment")).booleanValue();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String getGameFolder() {
/* 102 */     return ((File)FMLInjectionData.data()[6]).getAbsolutePath();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfar\\utility\ModUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */