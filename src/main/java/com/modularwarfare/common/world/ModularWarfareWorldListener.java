/*    */ package com.modularwarfare.common.world;
/*    */ 
/*    */ import com.modularwarfare.common.hitbox.playerdata.PlayerDataHandler;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IWorldEventListener;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModularWarfareWorldListener
/*    */   implements IWorldEventListener
/*    */ {
/*    */   public void func_184376_a(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {}
/*    */   
/*    */   public void func_174959_b(BlockPos pos) {}
/*    */   
/*    */   public void func_147585_a(int x1, int y1, int z1, int x2, int y2, int z2) {}
/*    */   
/*    */   public void func_184375_a(@Nullable EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) {}
/*    */   
/*    */   public void func_184377_a(SoundEvent soundIn, BlockPos pos) {}
/*    */   
/*    */   public void func_180442_a(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {}
/*    */   
/*    */   public void func_190570_a(int id, boolean ignoreRange, boolean p_190570_3_, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... parameters) {}
/*    */   
/*    */   public void func_72703_a(Entity entityIn) {}
/*    */   
/*    */   public void func_72709_b(Entity entityIn) {
/* 59 */     if (entityIn instanceof EntityPlayer) {
/* 60 */       EntityPlayer player = (EntityPlayer)entityIn;
/* 61 */       if (player.field_70170_p == null)
/* 62 */         return;  PlayerDataHandler.onPlayerLeave(player.func_70005_c_(), player.field_70170_p.field_72995_K ? Side.CLIENT : Side.SERVER);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void func_180440_a(int soundID, BlockPos pos, int data) {}
/*    */   
/*    */   public void func_180439_a(EntityPlayer player, int type, BlockPos blockPosIn, int data) {}
/*    */   
/*    */   public void func_180441_b(int breakerId, BlockPos pos, int progress) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\world\ModularWarfareWorldListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */