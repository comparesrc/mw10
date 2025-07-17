/*     */ package com.modularwarfare.common.backpacks;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.client.fpp.basic.configs.BackpackRenderConfig;
/*     */ import com.modularwarfare.client.model.ModelBackpack;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import com.modularwarfare.loader.MWModelBase;
/*     */ import com.modularwarfare.objects.SoundEntry;
/*     */ import java.util.ArrayList;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.ICapabilitySerializable;
/*     */ import net.minecraftforge.items.CapabilityItemHandler;
/*     */ import net.minecraftforge.items.IItemHandlerModifiable;
/*     */ import net.minecraftforge.items.ItemStackHandler;
/*     */ 
/*     */ 
/*     */ public class BackpackType
/*     */   extends BaseType
/*     */ {
/*  24 */   public int size = 16;
/*     */   
/*     */   public boolean allowSmallerBackpackStorage = false;
/*  27 */   public Integer maxWeaponStorage = null;
/*     */   
/*     */   public boolean isElytra = false;
/*     */   public boolean elytraStoppable = true;
/*     */   public boolean isJet = false;
/*     */   public boolean jetSneakHover = true;
/*     */   public boolean jetGroundDust = true;
/*  34 */   public float jetWorkForce = 0.05F;
/*     */   
/*  36 */   public float jetIdleForce = -0.2F;
/*  37 */   public float jetMaxForce = 0.4F;
/*  38 */   public float jetElytraBoost = 2.0F;
/*  39 */   public int jetElytraBoostDuration = 50;
/*  40 */   public int jetElytraBoostCoolTime = 40;
/*     */ 
/*     */   
/*     */   public void loadExtraValues() {
/*  44 */     if (this.maxStackSize == null)
/*  45 */       this.maxStackSize = Integer.valueOf(1); 
/*  46 */     loadBaseValues();
/*     */     try {
/*  48 */       for (ArrayList<SoundEntry> entryList : (Iterable<ArrayList<SoundEntry>>)this.weaponSoundMap.values()) {
/*  49 */         for (SoundEntry soundEntry : entryList) {
/*  50 */           if (soundEntry.soundName != null) {
/*  51 */             ModularWarfare.PROXY.registerSound(soundEntry.soundName);
/*  52 */             if (soundEntry.soundNameDistant != null)
/*  53 */               ModularWarfare.PROXY.registerSound(soundEntry.soundNameDistant);  continue;
/*     */           } 
/*  55 */           ModularWarfare.LOGGER
/*  56 */             .error(String.format("Sound entry event '%s' has null soundName for type '%s'", new Object[] { soundEntry.soundEvent, this.internalName }));
/*     */         }
/*     */       
/*     */       }
/*     */     
/*  61 */     } catch (Exception exception) {
/*  62 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadModel() {
/*  68 */     this.model = (MWModelBase)new ModelBackpack((BackpackRenderConfig)ModularWarfare.getRenderConfig(this, BackpackRenderConfig.class), this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAssetDir() {
/*  73 */     return "backpacks";
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Provider
/*     */     implements ICapabilitySerializable<NBTBase>
/*     */   {
/*     */     final IItemHandlerModifiable items;
/*     */     
/*     */     public Provider(BackpackType type) {
/*  83 */       this.items = (IItemHandlerModifiable)new ItemStackHandler(type.size);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
/*  88 */       return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
/*  94 */       return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) ? (T)this.items : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public NBTBase serializeNBT() {
/*  99 */       return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(this.items, null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void deserializeNBT(NBTBase nbt) {
/* 104 */       CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(this.items, null, nbt);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\backpacks\BackpackType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */