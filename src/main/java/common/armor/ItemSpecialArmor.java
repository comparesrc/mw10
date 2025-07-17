/*    */ package com.modularwarfare.common.armor;
/*    */ 
/*    */ import com.modularwarfare.api.MWArmorType;
/*    */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*    */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*    */ import com.modularwarfare.common.type.BaseItem;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSpecialArmor
/*    */   extends BaseItem {
/*    */   public ArmorType type;
/*    */   public MWArmorType armorType;
/*    */   public BaseType baseType;
/*    */   
/*    */   public ItemSpecialArmor(ArmorType type, MWArmorType armorType) {
/* 24 */     super(type);
/* 25 */     if (type.durability != null) {
/* 26 */       func_77656_e(type.durability.intValue());
/*    */     }
/* 28 */     type.loadExtraValues();
/* 29 */     this.baseType = type;
/* 30 */     this.type = type;
/* 31 */     this.armorType = armorType;
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
/* 36 */     ItemStack itemstack = playerIn.func_184586_b(handIn);
/*    */     
/* 38 */     if (playerIn.hasCapability(CapabilityExtra.CAPABILITY, null)) {
/* 39 */       IExtraItemHandler backpack = (IExtraItemHandler)playerIn.getCapability(CapabilityExtra.CAPABILITY, null);
/* 40 */       if (backpack.getStackInSlot(1).func_190926_b()) {
/* 41 */         backpack.setStackInSlot(1, itemstack.func_77946_l());
/* 42 */         itemstack.func_190920_e(0);
/* 43 */         return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */       } 
/*    */     } 
/* 46 */     return super.func_77659_a(worldIn, playerIn, handIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setType(BaseType type) {
/* 51 */     this.type = (ArmorType)type;
/*    */   }
/*    */   
/*    */   public void func_77663_a(ItemStack unused, World world, Entity holdingEntity, int intI, boolean flag) {
/* 55 */     if (holdingEntity instanceof EntityPlayer) {
/* 56 */       EntityPlayer entityPlayer = (EntityPlayer)holdingEntity;
/* 57 */       if (unused != null && unused.func_77973_b() instanceof ItemMWArmor && unused.func_77978_p() == null) {
/* 58 */         NBTTagCompound nbtTagCompound = new NBTTagCompound();
/* 59 */         nbtTagCompound.func_74768_a("skinId", 0);
/* 60 */         unused.func_77982_d(nbtTagCompound);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean func_77651_p() {
/* 66 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\armor\ItemSpecialArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */