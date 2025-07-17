/*    */ package com.modularwarfare.common.backpacks;
/*    */ 
/*    */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*    */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*    */ import com.modularwarfare.common.init.ModSounds;
/*    */ import com.modularwarfare.common.type.BaseItem;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*    */ import net.minecraftforge.items.CapabilityItemHandler;
/*    */ import net.minecraftforge.items.IItemHandler;
/*    */ 
/*    */ public class ItemBackpack
/*    */   extends BaseItem {
/*    */   static {
/* 27 */     factory = (type -> new ItemBackpack(type));
/*    */   }
/*    */   public static final Function<BackpackType, ItemBackpack> factory;
/*    */   public BackpackType type;
/*    */   
/*    */   public ItemBackpack(BackpackType type) {
/* 33 */     super(type);
/* 34 */     this.field_77777_bU = 1;
/* 35 */     this.type = type;
/* 36 */     this.render3d = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setType(BaseType type) {
/* 41 */     this.type = (BackpackType)type;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
/* 48 */     return (ICapabilityProvider)new BackpackType.Provider(this.type);
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
/* 53 */     ItemStack itemstack = playerIn.func_184586_b(handIn);
/*    */     
/* 55 */     if (playerIn.hasCapability(CapabilityExtra.CAPABILITY, null)) {
/* 56 */       IExtraItemHandler backpack = (IExtraItemHandler)playerIn.getCapability(CapabilityExtra.CAPABILITY, null);
/* 57 */       if (backpack.getStackInSlot(0).func_190926_b()) {
/* 58 */         backpack.setStackInSlot(0, itemstack.func_77946_l());
/* 59 */         itemstack.func_190920_e(0);
/* 60 */         worldIn.func_184133_a(null, playerIn.func_180425_c(), ModSounds.EQUIP_EXTRA, SoundCategory.PLAYERS, 2.0F, 1.0F);
/* 61 */         return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */       } 
/*    */     } 
/*    */     
/* 65 */     return super.func_77659_a(worldIn, playerIn, handIn);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public NBTTagCompound getNBTShareTag(ItemStack stack) {
/* 71 */     NBTTagCompound tags = super.getNBTShareTag(stack);
/*    */ 
/*    */     
/* 74 */     if (stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
/* 75 */       if (tags == null) {
/* 76 */         tags = new NBTTagCompound();
/*    */       }
/*    */       
/* 79 */       IItemHandler items = (IItemHandler)stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
/* 80 */       tags.func_74782_a("_items", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(items, null));
/*    */     } 
/*    */     
/* 83 */     return tags;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt) {
/* 88 */     super.readNBTShareTag(stack, nbt);
/*    */     
/* 90 */     if (stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) && nbt != null) {
/* 91 */       IItemHandler items = (IItemHandler)stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
/* 92 */       NBTBase itemTags = nbt.func_74781_a("_items");
/* 93 */       CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(items, null, itemTags);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_77663_a(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
/* 99 */     super.func_77663_a(stack, worldIn, entityIn, itemSlot, isSelected);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\backpacks\ItemBackpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */