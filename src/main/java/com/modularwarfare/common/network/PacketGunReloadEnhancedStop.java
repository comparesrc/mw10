/*     */ package com.modularwarfare.common.network;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.WeaponEnhancedReloadEvent;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemBullet;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.WeaponAnimationType;
/*     */ import com.modularwarfare.common.handler.ServerTickHandler;
/*     */ import com.modularwarfare.common.handler.data.DataGunReloadEnhancedTask;
/*     */ import com.modularwarfare.utility.ReloadHelper;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketGunReloadEnhancedStop
/*     */   extends PacketBase
/*     */ {
/*     */   public int reloadValidCount;
/*     */   public boolean unloaded;
/*     */   public boolean loaded;
/*     */   
/*     */   public PacketGunReloadEnhancedStop() {}
/*     */   
/*     */   public PacketGunReloadEnhancedStop(int reloadValidCount, boolean unloaded, boolean loaded) {
/*  38 */     this.reloadValidCount = reloadValidCount;
/*  39 */     this.unloaded = unloaded;
/*  40 */     this.loaded = loaded;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  45 */     data.writeInt(this.reloadValidCount);
/*  46 */     data.writeBoolean(this.unloaded);
/*  47 */     data.writeBoolean(this.loaded);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  52 */     this.reloadValidCount = data.readInt();
/*  53 */     this.unloaded = data.readBoolean();
/*  54 */     this.loaded = data.readBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleServerSide(EntityPlayerMP entityPlayer) {
/*  59 */     if (entityPlayer.func_184614_ca() != null && 
/*  60 */       entityPlayer.func_184614_ca().func_77973_b() instanceof ItemGun) {
/*  61 */       if (!ServerTickHandler.reloadEnhancedTask.containsKey(entityPlayer.func_110124_au())) {
/*     */         return;
/*     */       }
/*  64 */       DataGunReloadEnhancedTask task = (DataGunReloadEnhancedTask)ServerTickHandler.reloadEnhancedTask.get(entityPlayer.func_110124_au());
/*  65 */       ItemStack gunStack = (ItemStack)entityPlayer.field_71071_by.field_70462_a.get(task.gunSlot);
/*  66 */       ItemGun itemGun = (ItemGun)gunStack.func_77973_b();
/*  67 */       GunType gunType = itemGun.type;
/*  68 */       InventoryPlayer inventory = entityPlayer.field_71071_by;
/*     */       
/*  70 */       if (task.reloadGun != gunStack) {
/*  71 */         ServerTickHandler.reloadEnhancedTask.remove(entityPlayer.func_110124_au());
/*     */         
/*     */         return;
/*     */       } 
/*  75 */       if (gunType.animationType.equals(WeaponAnimationType.ENHANCED)) {
/*  76 */         if (gunType.acceptedAmmo != null) {
/*  77 */           handleMagGunReloadEnhanced(entityPlayer, gunStack, itemGun, gunType, inventory);
/*     */         } else {
/*  79 */           handleBulletGunReloadEnhanced(entityPlayer, gunStack, itemGun, gunType, inventory);
/*     */         } 
/*  81 */         entityPlayer.func_71111_a(entityPlayer.field_71069_bz, entityPlayer.field_71069_bz.field_75151_b
/*  82 */             .size() - 1 - 9 + task.gunSlot, gunStack);
/*     */         
/*  84 */         ModularWarfare.NETWORK.sendTo(new PacketGunReloadEnhancedTask(ItemStack.field_190927_a), entityPlayer);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleClientSide(EntityPlayer clientPlayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleBulletGunReloadEnhanced(EntityPlayerMP entityPlayer, ItemStack gunStack, ItemGun itemGun, GunType gunType, InventoryPlayer inventory) {
/* 100 */     if (!ServerTickHandler.reloadEnhancedTask.containsKey(entityPlayer.func_110124_au())) {
/*     */       return;
/*     */     }
/* 103 */     DataGunReloadEnhancedTask task = (DataGunReloadEnhancedTask)ServerTickHandler.reloadEnhancedTask.get(entityPlayer.func_110124_au());
/* 104 */     if (task.reloadGun != gunStack) {
/* 105 */       ServerTickHandler.reloadEnhancedTask.remove(entityPlayer.func_110124_au());
/*     */       
/*     */       return;
/*     */     } 
/* 109 */     if (this.reloadValidCount > task.reloadCount || this.reloadValidCount < 0) {
/* 110 */       ServerTickHandler.reloadEnhancedTask.remove(entityPlayer.func_110124_au());
/*     */       
/*     */       return;
/*     */     } 
/* 114 */     NBTTagCompound nbtTagCompound = gunStack.func_77978_p();
/* 115 */     WeaponEnhancedReloadEvent.ReloadStopFirst firstEvent = new WeaponEnhancedReloadEvent.ReloadStopFirst(entityPlayer, gunStack, task.prognosisAmmo, task.currentAmmo, this.unloaded);
/* 116 */     MinecraftForge.EVENT_BUS.post((Event)firstEvent);
/* 117 */     if (firstEvent.unload) {
/* 118 */       if (task.isUnload) {
/* 119 */         ReloadHelper.unloadBullets(entityPlayer, gunStack, Integer.valueOf(this.reloadValidCount));
/* 120 */       } else if (nbtTagCompound.func_74764_b("bullet")) {
/* 121 */         ItemBullet bulletItemToLoad = (ItemBullet)task.prognosisAmmo.func_77973_b();
/* 122 */         ItemStack currentBullet = new ItemStack(nbtTagCompound.func_74775_l("bullet"));
/* 123 */         ItemBullet currentBulletItem = (ItemBullet)currentBullet.func_77973_b();
/* 124 */         if (!currentBulletItem.baseType.internalName.equalsIgnoreCase(bulletItemToLoad.baseType.internalName)) {
/* 125 */           ReloadHelper.unloadBullets(entityPlayer, gunStack);
/*     */         }
/*     */       } 
/*     */     }
/* 129 */     if (task.isUnload) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 134 */     if (gunType.acceptedBullets != null) {
/* 135 */       if (gunStack.func_77978_p() != null && 
/* 136 */         gunType.internalAmmoStorage != null && 
/* 137 */         gunStack.func_77978_p().func_74775_l("bullet") != null && 
/* 138 */         gunStack.func_77978_p().func_74762_e("ammocount") >= gunType.internalAmmoStorage.intValue()) {
/* 139 */         ServerTickHandler.reloadEnhancedTask.remove(entityPlayer.func_110124_au());
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 146 */       if (this.loaded) {
/* 147 */         boolean result = ReloadHelper.removeItemstack((EntityPlayer)entityPlayer, task.prognosisAmmo, this.reloadValidCount);
/* 148 */         WeaponEnhancedReloadEvent.ReloadStopSecond secondEvent = new WeaponEnhancedReloadEvent.ReloadStopSecond(entityPlayer, gunStack, task.prognosisAmmo, task.currentAmmo, result);
/* 149 */         MinecraftForge.EVENT_BUS.post((Event)secondEvent);
/* 150 */         if (secondEvent.result) {
/* 151 */           ItemStack loadingItemStack = task.prognosisAmmo.func_77946_l();
/* 152 */           int ammoCount = gunStack.func_77978_p().func_74762_e("ammocount");
/* 153 */           gunStack.func_77978_p().func_74768_a("ammocount", ammoCount + this.reloadValidCount);
/* 154 */           gunStack.func_77978_p().func_74782_a("bullet", (NBTBase)loadingItemStack.func_77955_b(new NBTTagCompound()));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMagGunReloadEnhanced(EntityPlayerMP entityPlayer, ItemStack gunStack, ItemGun itemGun, GunType gunType, InventoryPlayer inventory) {
/* 163 */     if (!ServerTickHandler.reloadEnhancedTask.containsKey(entityPlayer.func_110124_au())) {
/*     */       return;
/*     */     }
/* 166 */     DataGunReloadEnhancedTask task = (DataGunReloadEnhancedTask)ServerTickHandler.reloadEnhancedTask.get(entityPlayer.func_110124_au());
/* 167 */     if (task.reloadGun != gunStack) {
/* 168 */       ServerTickHandler.reloadEnhancedTask.remove(entityPlayer.func_110124_au());
/*     */       
/*     */       return;
/*     */     } 
/* 172 */     WeaponEnhancedReloadEvent.ReloadStopFirst firstEvent = new WeaponEnhancedReloadEvent.ReloadStopFirst(entityPlayer, gunStack, task.prognosisAmmo, task.currentAmmo, this.unloaded);
/* 173 */     MinecraftForge.EVENT_BUS.post((Event)firstEvent);
/*     */     
/* 175 */     if (firstEvent.unload && (
/* 176 */       !task.currentAmmo || !this.loaded) && 
/* 177 */       ItemGun.hasAmmoLoaded(gunStack)) {
/* 178 */       ReloadHelper.unloadAmmo(entityPlayer, gunStack);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 183 */     if (task.isUnload) {
/*     */       return;
/*     */     }
/*     */     
/* 187 */     NBTTagCompound nbtTagCompound = gunStack.func_77978_p();
/* 188 */     boolean hasAmmoLoaded = ItemGun.hasAmmoLoaded(gunStack);
/* 189 */     boolean offhandedReload = false;
/* 190 */     ItemStack ammoStackToLoad = task.prognosisAmmo;
/* 191 */     Integer ammoStackSlotToLoad = null;
/* 192 */     Integer highestAmmoCount = Integer.valueOf(0);
/* 193 */     Integer multiMagToLoad = task.multiMagToLoad;
/*     */ 
/*     */ 
/*     */     
/* 197 */     boolean multiMagReload = (task.currentAmmo && hasAmmoLoaded && ammoStackToLoad.func_77978_p().func_74764_b("magcount"));
/*     */ 
/*     */     
/* 200 */     if (this.loaded) {
/* 201 */       boolean result = (task.currentAmmo || ReloadHelper.removeItemstack((EntityPlayer)entityPlayer, ammoStackToLoad, 1));
/* 202 */       WeaponEnhancedReloadEvent.ReloadStopSecond secondEvent = new WeaponEnhancedReloadEvent.ReloadStopSecond(entityPlayer, gunStack, task.prognosisAmmo, task.currentAmmo, result);
/* 203 */       MinecraftForge.EVENT_BUS.post((Event)secondEvent);
/* 204 */       if (secondEvent.result) {
/* 205 */         ItemStack loadingItemStack = ammoStackToLoad.func_77946_l();
/* 206 */         loadingItemStack.func_190920_e(1);
/* 207 */         if (multiMagReload && multiMagToLoad != null) {
/* 208 */           loadingItemStack.func_77978_p().func_74768_a("magcount", multiMagToLoad.intValue());
/*     */         }
/* 210 */         nbtTagCompound.func_74782_a("ammo", (NBTBase)loadingItemStack.func_77955_b(new NBTTagCompound()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 218 */     return "PacketGunReloadEnhancedStop[" + this.reloadValidCount + "," + this.unloaded + "," + this.loaded + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketGunReloadEnhancedStop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */