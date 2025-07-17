/*    */ package com.modularwarfare.client.scope;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.RenderGlobal;
/*    */ import net.minecraft.client.renderer.culling.ICamera;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class ScopeRenderGlobal extends RenderGlobal {
/*    */   private boolean shouldLoadRenderers = true;
/*    */   
/*    */   public ScopeRenderGlobal(Minecraft mcIn) {
/* 19 */     super(mcIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_174970_a(Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
/* 24 */     this.shouldLoadRenderers = false;
/* 25 */     super.func_174970_a(viewEntity, partialTicks, camera, frameCount, playerSpectator);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_72712_a() {
/* 36 */     super.func_72712_a();
/* 37 */     this.shouldLoadRenderers = true;
/*    */   }
/*    */   
/*    */   public void func_184377_a(SoundEvent soundIn, BlockPos pos) {}
/*    */   
/*    */   public void func_184375_a(EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) {}
/*    */   
/*    */   public void func_180440_a(int soundID, BlockPos pos, int data) {}
/*    */   
/*    */   public void func_180439_a(EntityPlayer player, int type, BlockPos blockPosIn, int data) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\scope\ScopeRenderGlobal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */