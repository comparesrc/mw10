/*    */ package com.modularwarfare.mixin.client;
/*    */ 
/*    */ import com.modularwarfare.client.ClientRenderHooks;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Overwrite;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({InventoryPlayer.class})
/*    */ public abstract class MixinInventoryPlayer
/*    */ {
/*    */   @Shadow
/*    */   public int field_70461_c;
/*    */   @Shadow
/*    */   @Final
/*    */   public NonNullList<ItemStack> field_70462_a;
/*    */   @Shadow
/*    */   public EntityPlayer field_70458_d;
/*    */   
/*    */   @Shadow
/*    */   public abstract ItemStack func_70301_a(int paramInt);
/*    */   
/*    */   @Overwrite
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_70453_c(int direction) {
/* 39 */     if ((ClientRenderHooks.getAnimMachine((EntityLivingBase)this.field_70458_d)).reloading) {
/*    */       return;
/*    */     }
/* 42 */     if (direction > 0)
/*    */     {
/* 44 */       direction = 1;
/*    */     }
/*    */     
/* 47 */     if (direction < 0)
/*    */     {
/* 49 */       direction = -1;
/*    */     }
/*    */     
/* 52 */     for (this.field_70461_c -= direction; this.field_70461_c < 0; this.field_70461_c += 9);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     while (this.field_70461_c >= 9)
/*    */     {
/* 59 */       this.field_70461_c -= 9;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\mixin\client\MixinInventoryPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */