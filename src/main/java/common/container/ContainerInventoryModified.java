/*     */ package com.modularwarfare.common.container;
/*     */ 
/*     */ import com.modularwarfare.common.backpacks.ItemBackpack;
/*     */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*     */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryCraftResult;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.inventory.SlotCrafting;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import net.minecraftforge.items.CapabilityItemHandler;
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ import net.minecraftforge.items.SlotItemHandler;
/*     */ 
/*     */ public class ContainerInventoryModified extends Container {
/*  29 */   private static final EntityEquipmentSlot[] EQUIPMENT_SLOTS = new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET };
/*     */   
/*  31 */   public final InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
/*  32 */   public final InventoryCraftResult craftResult = new InventoryCraftResult();
/*     */   private final EntityPlayer thePlayer;
/*     */   public IExtraItemHandler extra;
/*     */   public boolean isLocalWorld;
/*     */   
/*     */   public ContainerInventoryModified(InventoryPlayer playerInv, boolean isLocalWorld, EntityPlayer player) {
/*  38 */     this.isLocalWorld = isLocalWorld;
/*  39 */     this.thePlayer = player;
/*  40 */     func_75130_a((IInventory)this.craftMatrix);
/*     */     
/*  42 */     addSlots(playerInv, player);
/*     */   }
/*     */   
/*     */   public void addSlots(final InventoryPlayer playerInv, final EntityPlayer player) {
/*  46 */     this.field_75151_b.clear();
/*  47 */     this.field_75153_a.clear();
/*     */     
/*  49 */     this.extra = (IExtraItemHandler)player.getCapability(CapabilityExtra.CAPABILITY, null);
/*     */     
/*  51 */     func_75146_a((Slot)new SlotCrafting(playerInv.field_70458_d, this.craftMatrix, (IInventory)this.craftResult, 0, 154, 28));
/*     */     
/*  53 */     for (int j = 0; j < 2; j++) {
/*  54 */       for (int m = 0; m < 2; m++) {
/*  55 */         func_75146_a(new Slot((IInventory)this.craftMatrix, m + j * 2, 116 + m * 18, 18 + j * 18));
/*     */       }
/*     */     } 
/*     */     
/*  59 */     for (int k = 0; k < 4; k++) {
/*  60 */       final EntityEquipmentSlot slot = EQUIPMENT_SLOTS[k];
/*  61 */       func_75146_a(new Slot((IInventory)playerInv, 36 + 3 - k, 8, 8 + k * 18)
/*     */           {
/*     */             public int func_75219_a() {
/*  64 */               return 1;
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean func_75214_a(ItemStack stack) {
/*  69 */               return stack.func_77973_b().isValidArmor(stack, slot, (Entity)player);
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean func_82869_a(EntityPlayer playerIn) {
/*  74 */               ItemStack itemstack = func_75211_c();
/*  75 */               return (!itemstack.func_190926_b() && !playerIn.func_184812_l_() && EnchantmentHelper.func_190938_b(itemstack)) ? false : super
/*  76 */                 .func_82869_a(playerIn);
/*     */             }
/*     */ 
/*     */             
/*     */             public String func_178171_c() {
/*  81 */               return ItemArmor.field_94603_a[slot.func_188454_b()];
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/*     */     int i;
/*  87 */     for (i = 0; i < 3; i++) {
/*  88 */       for (int m = 0; m < 9; m++) {
/*  89 */         func_75146_a(new Slot((IInventory)playerInv, m + (i + 1) * 9, 8 + m * 18, 90 + i * 18));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  94 */     for (i = 0; i < 9; i++) {
/*  95 */       func_75146_a(new Slot((IInventory)playerInv, i, 8 + i * 18, 154));
/*     */     }
/*     */ 
/*     */     
/*  99 */     func_75146_a(new Slot((IInventory)playerInv, 40, 76, 62)
/*     */         {
/*     */           @Nullable
/*     */           @SideOnly(Side.CLIENT)
/*     */           public String func_178171_c() {
/* 104 */             return "minecraft:items/empty_armor_slot_shield";
/*     */           }
/*     */         });
/*     */     
/* 108 */     func_75146_a((Slot)new SlotBackpack((IItemHandler)this.extra, 0, 76, 8)
/*     */         {
/*     */           public void func_75218_e() {
/* 111 */             ContainerInventoryModified.this.updateBackpack();
/* 112 */             ContainerInventoryModified.this.addSlots(playerInv, player);
/* 113 */             super.func_75218_e();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 118 */     func_75146_a((Slot)new SlotVest((IItemHandler)this.extra, 1, 76, 26)
/*     */         {
/*     */           public void func_75218_e() {
/* 121 */             ContainerInventoryModified.this.addSlots(playerInv, player);
/* 122 */             super.func_75218_e();
/*     */           }
/*     */         });
/*     */     
/* 126 */     updateBackpack();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateBackpack() {
/* 131 */     if (this.extra.getStackInSlot(0).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
/* 132 */       final IItemHandler backpackInvent = (IItemHandler)this.extra.getStackInSlot(0).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
/*     */       
/* 134 */       int xP = 0;
/* 135 */       int yP = 0;
/* 136 */       int x = 181;
/* 137 */       int y = 19;
/*     */       
/* 139 */       for (int i = 0; i < backpackInvent.getSlots(); i++) {
/* 140 */         func_75146_a((Slot)new SlotItemHandler(backpackInvent, i, 181 + xP * 18, 18 + yP * 18)
/*     */             {
/*     */ 
/*     */               
/*     */               public boolean func_75214_a(@Nonnull ItemStack stack)
/*     */               {
/* 146 */                 if (stack.func_77973_b() instanceof ItemBackpack) {
/* 147 */                   ItemBackpack itemBackpack = (ItemBackpack)ContainerInventoryModified.this.extra.getStackInSlot(0).func_77973_b();
/* 148 */                   if (itemBackpack.type.allowSmallerBackpackStorage) {
/* 149 */                     int otherBackpackSize = ((ItemBackpack)stack.func_77973_b()).type.size;
/* 150 */                     int thisBackpackSize = backpackInvent.getSlots();
/* 151 */                     if (otherBackpackSize <= thisBackpackSize) {
/* 152 */                       return true;
/*     */                     }
/* 154 */                     return false;
/*     */                   } 
/* 156 */                   return false;
/*     */                 } 
/*     */                 
/* 159 */                 if (stack.func_77973_b() instanceof com.modularwarfare.common.guns.ItemGun) {
/* 160 */                   ItemBackpack itemBackpack = (ItemBackpack)ContainerInventoryModified.this.extra.getStackInSlot(0).func_77973_b();
/* 161 */                   if (itemBackpack.type.maxWeaponStorage != null && 
/* 162 */                     getNumberOfGuns(backpackInvent) >= itemBackpack.type.maxWeaponStorage.intValue()) {
/* 163 */                     return false;
/*     */                   }
/*     */                 } 
/*     */                 
/* 167 */                 return super.func_75214_a(stack);
/*     */               }
/*     */               
/*     */               private int getNumberOfGuns(IItemHandler backpackInvent) {
/* 171 */                 int numGuns = 0;
/* 172 */                 for (int i = 0; i < backpackInvent.getSlots(); i++) {
/* 173 */                   if (backpackInvent.getStackInSlot(i) != null && 
/* 174 */                     backpackInvent.getStackInSlot(i).func_77973_b() instanceof com.modularwarfare.common.guns.ItemGun) {
/* 175 */                     numGuns++;
/*     */                   }
/*     */                 } 
/*     */                 
/* 179 */                 return numGuns;
/*     */               }
/*     */             });
/* 182 */         xP++;
/*     */         
/* 184 */         if (xP % 4 == 0) {
/* 185 */           xP = 0;
/* 186 */           yP++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75130_a(IInventory par1IInventory) {
/* 195 */     func_192389_a(this.thePlayer.func_130014_f_(), this.thePlayer, this.craftMatrix, this.craftResult);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75134_a(EntityPlayer player) {
/* 200 */     super.func_75134_a(player);
/* 201 */     this.craftResult.func_174888_l();
/*     */     
/* 203 */     if (!player.field_70170_p.field_72995_K) {
/* 204 */       func_193327_a(player, player.field_70170_p, (IInventory)this.craftMatrix);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer par1EntityPlayer) {
/* 210 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_82846_b(EntityPlayer playerIn, int index) {
/* 215 */     ItemStack itemstack = ItemStack.field_190927_a;
/* 216 */     Slot slot = this.field_75151_b.get(index);
/*     */     
/* 218 */     if (slot != null && slot.func_75216_d()) {
/* 219 */       ItemStack itemstack1 = slot.func_75211_c();
/* 220 */       itemstack = itemstack1.func_77946_l();
/* 221 */       EntityEquipmentSlot entityequipmentslot = EntityLiving.func_184640_d(itemstack);
/*     */ 
/*     */       
/* 224 */       if (itemstack1.func_190926_b()) {
/* 225 */         slot.func_75215_d(ItemStack.field_190927_a);
/*     */       } else {
/* 227 */         slot.func_75218_e();
/*     */       } 
/*     */       
/* 230 */       if (itemstack1.func_190916_E() == itemstack.func_190916_E()) {
/* 231 */         return ItemStack.field_190927_a;
/*     */       }
/*     */       
/* 234 */       ItemStack itemstack2 = slot.func_190901_a(playerIn, itemstack1);
/*     */       
/* 236 */       if (index == 0) {
/* 237 */         playerIn.func_71019_a(itemstack2, false);
/*     */       }
/*     */     } 
/*     */     
/* 241 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94530_a(ItemStack stack, Slot slot) {
/* 246 */     return (slot.field_75224_c != this.craftResult && super.func_94530_a(stack, slot));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\container\ContainerInventoryModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */