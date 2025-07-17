/*    */ package com.modularwarfare.common.capability.extraslots;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraftforge.common.capabilities.Capability;
/*    */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*    */ import net.minecraftforge.common.util.INBTSerializable;
/*    */ 
/*    */ public class ExtraContainerProvider implements INBTSerializable<NBTTagCompound>, ICapabilityProvider {
/*    */   private final ExtraContainer container;
/*    */   
/*    */   public ExtraContainerProvider(ExtraContainer container) {
/* 16 */     this.container = container;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
/* 21 */     return (capability == CapabilityExtra.CAPABILITY);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
/* 27 */     if (capability == CapabilityExtra.CAPABILITY) {
/* 28 */       return (T)this.container;
/*    */     }
/*    */     
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound serializeNBT() {
/* 36 */     return this.container.serializeNBT();
/*    */   }
/*    */ 
/*    */   
/*    */   public void deserializeNBT(NBTTagCompound nbt) {
/* 41 */     this.container.deserializeNBT(nbt);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\capability\extraslots\ExtraContainerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */