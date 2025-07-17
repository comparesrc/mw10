/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraftforge.common.util.INBTSerializable;
/*    */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*    */ 
/*    */ 
/*    */ public class BackWeaponsManager
/*    */   implements INBTSerializable<NBTTagCompound>
/*    */ {
/* 18 */   public static BackWeaponsManager INSTANCE = new BackWeaponsManager();
/*    */   
/* 20 */   private final HashMap<String, ItemStack> WEAPONS = new HashMap<>();
/*    */   
/*    */   public BackWeaponsManager collect() {
/* 23 */     for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_181057_v()) {
/* 24 */       this.WEAPONS.put(player.func_70005_c_(), getItemInBack(player));
/*    */     }
/* 26 */     return this;
/*    */   }
/*    */   
/*    */   public BackWeaponsManager sync() {
/* 30 */     ModularWarfare.NETWORK.sendToAll(new PacketSyncBackWeapons());
/* 31 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTTagCompound serializeNBT() {
/* 37 */     NBTTagCompound nbt = new NBTTagCompound();
/* 38 */     this.WEAPONS.forEach((playerName, itemStack) -> nbt.func_74782_a(playerName, (NBTBase)itemStack.serializeNBT()));
/*    */ 
/*    */     
/* 41 */     return nbt;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deserializeNBT(NBTTagCompound nbt) {
/* 46 */     this.WEAPONS.clear();
/* 47 */     for (String s : nbt.func_150296_c()) {
/* 48 */       NBTBase base = nbt.func_74781_a(s);
/* 49 */       if (base instanceof NBTTagCompound) {
/* 50 */         NBTTagCompound compound = (NBTTagCompound)base;
/* 51 */         ItemStack stack = new ItemStack(compound);
/* 52 */         this.WEAPONS.put(s, stack);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private ItemStack getItemInBack(EntityPlayerMP mp) {
/* 59 */     ItemStack stack = ItemStack.field_190927_a;
/* 60 */     ArrayList<ItemStack> stacks = getItemsInBack(mp);
/* 61 */     if (stacks.size() > 0) {
/* 62 */       stack = stacks.get(0);
/*    */     }
/* 64 */     return stack;
/*    */   }
/*    */   
/*    */   private ArrayList<ItemStack> getItemsInBack(EntityPlayerMP mp) {
/* 68 */     ArrayList<ItemStack> guns = new ArrayList<>();
/* 69 */     for (int i = 0; i <= mp.field_71071_by.func_70302_i_(); i++) {
/* 70 */       if (guns.size() < 2 && 
/* 71 */         mp.field_71071_by.func_70301_a(i) != null && mp.field_71071_by.func_70301_a(i).func_77973_b() instanceof com.modularwarfare.common.guns.ItemGun && mp.field_71071_by.func_70301_a(i) != mp.func_184614_ca()) {
/* 72 */         guns.add(mp.field_71071_by.func_70301_a(i));
/*    */       }
/*    */     } 
/*    */     
/* 76 */     return guns;
/*    */   }
/*    */   
/*    */   public ItemStack getItemToRender(AbstractClientPlayer player) {
/* 80 */     return this.WEAPONS.getOrDefault(player.func_70005_c_(), ItemStack.field_190927_a);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\BackWeaponsManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */