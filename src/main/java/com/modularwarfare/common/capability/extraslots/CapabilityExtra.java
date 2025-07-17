/*     */ package com.modularwarfare.common.capability.extraslots;
/*     */ 
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketSyncExtraSlot;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.CapabilityInject;
/*     */ import net.minecraftforge.event.AttachCapabilitiesEvent;
/*     */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingDeathEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerEvent;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @EventBusSubscriber(modid = "modularwarfare")
/*     */ public class CapabilityExtra
/*     */ {
/*     */   @CapabilityInject(IExtraItemHandler.class)
/*  38 */   public static final Capability<IExtraItemHandler> CAPABILITY = null;
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
/*  43 */     if (event.getObject() instanceof EntityPlayer) {
/*  44 */       event.addCapability(new ResourceLocation("modularwarfare", "extraslots"), new ExtraContainerProvider(new ExtraContainer((EntityPlayer)event.getObject())));
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void onPlayerJoin(EntityJoinWorldEvent event) {
/*  50 */     if (!(event.getEntity() instanceof EntityPlayerMP)) {
/*     */       return;
/*     */     }
/*  53 */     EntityPlayer target = (EntityPlayer)event.getEntity();
/*  54 */     sync(target, Collections.singletonList(target));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void onStartTracking(PlayerEvent.StartTracking event) {
/*  59 */     if (!(event.getTarget() instanceof EntityPlayerMP)) {
/*     */       return;
/*     */     }
/*  62 */     sync((EntityPlayer)event.getTarget(), Collections.singletonList(event.getEntityPlayer()));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void playerDeath(LivingDeathEvent event) {
/*  67 */     if (event.getEntity() instanceof EntityPlayer) {
/*  68 */       EntityPlayer player = (EntityPlayer)event.getEntity();
/*  69 */       World world = player.field_70170_p;
/*  70 */       if (world.field_72995_K || world.func_82736_K().func_82766_b("keepInventory") || !ModConfig.INSTANCE.general.drop_extra_slots_on_death) {
/*     */         return;
/*     */       }
/*     */       
/*  74 */       for (int i = 0; i < ((IExtraItemHandler)player.getCapability(CAPABILITY, (EnumFacing)null)).getSlots(); i++) {
/*  75 */         ItemStack extra = ((IExtraItemHandler)player.getCapability(CAPABILITY, (EnumFacing)null)).getStackInSlot(i);
/*  76 */         if (extra.func_190926_b()) {
/*     */           return;
/*     */         }
/*  79 */         EntityItem item = new EntityItem(world, player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v, extra);
/*  80 */         item.func_174867_a(40);
/*  81 */         float f1 = world.field_73012_v.nextFloat() * 0.5F;
/*  82 */         float f2 = world.field_73012_v.nextFloat() * 3.1415927F * 2.0F;
/*  83 */         item.field_70159_w = (-MathHelper.func_76126_a(f2) * f1);
/*  84 */         item.field_70179_y = (MathHelper.func_76134_b(f2) * f1);
/*  85 */         item.field_70181_x = 0.20000000298023224D;
/*  86 */         world.func_72838_d((Entity)item);
/*  87 */         ((IExtraItemHandler)player.getCapability(CAPABILITY, (EnumFacing)null)).setStackInSlot(i, ItemStack.field_190927_a);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void playerClone(PlayerEvent.Clone event) {
/*  94 */     IExtraItemHandler oldHandler = (IExtraItemHandler)event.getOriginal().getCapability(CAPABILITY, (EnumFacing)null);
/*  95 */     IExtraItemHandler newHandler = (IExtraItemHandler)event.getEntityPlayer().getCapability(CAPABILITY, (EnumFacing)null);
/*  96 */     for (int i = 0; i < newHandler.getSlots(); i++) {
/*  97 */       newHandler.setStackInSlot(i, oldHandler.getStackInSlot(i).func_77946_l());
/*     */     }
/*     */   }
/*     */   
/*     */   public static void sync(EntityPlayer entity, Collection<? extends EntityPlayer> receivers) {
/* 102 */     if (entity.hasCapability(CAPABILITY, null)) {
/* 103 */       for (int i = 0; i < ((IExtraItemHandler)entity.getCapability(CAPABILITY, (EnumFacing)null)).getSlots(); i++) {
/* 104 */         PacketSyncExtraSlot msg = new PacketSyncExtraSlot(entity, i, ((IExtraItemHandler)entity.getCapability(CAPABILITY, (EnumFacing)null)).getStackInSlot(i));
/* 105 */         receivers.forEach(p -> ModularWarfare.NETWORK.sendTo((PacketBase)msg, (EntityPlayerMP)p));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Storage
/*     */     implements Capability.IStorage<IExtraItemHandler>
/*     */   {
/*     */     @Nullable
/*     */     public NBTBase writeNBT(Capability<IExtraItemHandler> capability, IExtraItemHandler instance, EnumFacing side) {
/* 115 */       return null;
/*     */     }
/*     */     
/*     */     public void readNBT(Capability<IExtraItemHandler> capability, IExtraItemHandler instance, EnumFacing side, NBTBase nbt) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\capability\extraslots\CapabilityExtra.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */