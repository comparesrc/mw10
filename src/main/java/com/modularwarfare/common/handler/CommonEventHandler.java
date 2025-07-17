/*     */ package com.modularwarfare.common.handler;
/*     */ 
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.entity.item.EntityItemLoot;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketClientKillFeedEntry;
/*     */ import com.modularwarfare.common.network.PacketExplosion;
/*     */ import com.modularwarfare.common.network.PacketVerification;
/*     */ import com.modularwarfare.common.world.ModularWarfareWorldListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.world.IWorldEventListener;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingAttackEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingDeathEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingHurtEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*     */ import net.minecraftforge.event.world.ExplosionEvent;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.PlayerEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommonEventHandler
/*     */ {
/*  50 */   private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
/*  51 */   public static HashMap<String, Long> playerTimeoutMap = new HashMap<>();
/*     */   
/*     */   private static int getRandomNumberInRange(int min, int max) {
/*  54 */     if (min >= max) {
/*  55 */       throw new IllegalArgumentException("max must be greater than min");
/*     */     }
/*  57 */     Random r = new Random();
/*  58 */     return r.nextInt(max - min + 1) + min;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
/*  63 */     if (ModConfig.INSTANCE.general.modified_pack_server_kick || ModConfig.INSTANCE.general.directory_pack_server_kick) {
/*  64 */       playerTimeoutMap.put(event.player.func_70005_c_(), Long.valueOf(System.currentTimeMillis()));
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onServerTick(TickEvent.ServerTickEvent event) {
/*  70 */     if (event.phase != TickEvent.Phase.END) {
/*     */       return;
/*     */     }
/*  73 */     long time = System.currentTimeMillis();
/*  74 */     ArrayList<String> list = new ArrayList<>();
/*  75 */     playerTimeoutMap.forEach((name, t) -> {
/*     */           EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_152612_a(name);
/*     */           
/*     */           if (player == null) {
/*     */             list.add(name);
/*     */           } else {
/*     */             if (time > t.longValue() + 5000L) {
/*     */               ModularWarfare.NETWORK.sendTo((PacketBase)new PacketVerification(), player);
/*     */             }
/*     */             if (time > t.longValue() + 10000L) {
/*     */               player.field_71135_a.func_194028_b((ITextComponent)new TextComponentString("[ModularWarfare] Verification timeout."));
/*     */               list.add(name);
/*     */             } 
/*     */           } 
/*     */         });
/*  90 */     list.forEach(name -> playerTimeoutMap.remove(name));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLivingDeath(LivingDeathEvent event) {
/*  97 */     if (ModConfig.INSTANCE.killFeed.enableKillFeed) {
/*  98 */       Entity entity = event.getEntity();
/*  99 */       if (entity instanceof EntityPlayer && 
/* 100 */         !entity.field_70170_p.field_72995_K && 
/* 101 */         event.getSource().func_76352_a() && 
/* 102 */         event.getSource().func_76346_g() instanceof EntityPlayer) {
/* 103 */         ItemStack heldStack = ((EntityPlayer)event.getSource().func_76346_g()).func_184582_a(EntityEquipmentSlot.MAINHAND);
/* 104 */         if (heldStack != null && 
/* 105 */           heldStack.func_77973_b() instanceof ItemGun) {
/* 106 */           String text = getRandomMessage(event.getSource().func_76346_g().func_145748_c_().func_150254_d(), event.getEntity().func_145748_c_().func_150254_d());
/* 107 */           ModularWarfare.NETWORK.sendToAll((PacketBase)new PacketClientKillFeedEntry(text, ModConfig.INSTANCE.killFeed.messageDuration, ((ItemGun)heldStack.func_77973_b()).type.internalName));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRandomMessage(String killer, String victim) {
/* 118 */     if (ModConfig.INSTANCE.killFeed.messageList != null && ModConfig.INSTANCE.killFeed.messageList.size() > 0) {
/* 119 */       int r = getRandomNumberInRange(0, ModConfig.INSTANCE.killFeed.messageList.size() - 1);
/* 120 */       String choosen = ModConfig.INSTANCE.killFeed.messageList.get(r);
/* 121 */       choosen = choosen.replace("{killer}", killer).replace("{victim}", victim);
/* 122 */       choosen = choosen.replace("&", "§");
/* 123 */       return choosen;
/*     */     } 
/* 125 */     return "";
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLivingAttack(LivingAttackEvent event) {
/* 130 */     if (!(event.getEntityLiving()).field_70170_p.field_72995_K)
/*     */       return; 
/* 132 */     Entity entity = event.getEntity();
/* 133 */     if ((entity.func_130014_f_()).field_72995_K) {
/* 134 */       ModularWarfare.PROXY.addBlood(event.getEntityLiving(), 10, true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLivingHurt(LivingHurtEvent event) {
/* 140 */     Entity entity = event.getEntity();
/* 141 */     if (entity instanceof EntityItemLoot) {
/*     */       return;
/*     */     }
/*     */   }
/*     */   
/* 146 */   private static final ModularWarfareWorldListener WORLD_LISTENER = new ModularWarfareWorldListener();
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onInitWorld(WorldEvent.Load event) {
/* 150 */     World world = event.getWorld();
/* 151 */     world.func_72954_a((IWorldEventListener)WORLD_LISTENER);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUnloadWorld(WorldEvent.Unload event) {
/* 156 */     World world = event.getWorld();
/* 157 */     world.func_72848_b((IWorldEventListener)WORLD_LISTENER);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityInteractBlock(PlayerInteractEvent.RightClickBlock event) {
/* 162 */     if (ModConfig.INSTANCE.guns.guns_interaction_hand && 
/* 163 */       (event.getWorld()).field_72995_K && 
/* 164 */       (Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && 
/* 165 */       (Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun && 
/* 166 */       !(event.getWorld().func_180495_p(event.getPos()).func_177230_c() instanceof net.minecraft.block.BlockContainer)) {
/* 167 */       event.setUseBlock(Event.Result.DENY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void explosionEvent(ExplosionEvent e) {
/* 177 */     Vec3d pos = e.getExplosion().getPosition();
/* 178 */     ModularWarfare.NETWORK.sendToAll((PacketBase)new PacketExplosion(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c));
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityInteract(PlayerInteractEvent.EntityInteractSpecific event) {
/* 184 */     if (event.getTarget() instanceof EntityItemLoot) {
/* 185 */       if (!(event.getWorld()).field_72995_K && (event.getTarget()).field_70122_E && !event.getEntityPlayer().func_175149_v()) {
/* 186 */         EntityItemLoot loot = (EntityItemLoot)event.getTarget();
/* 187 */         if (loot.getCustomAge() > 20) {
/* 188 */           ItemStack stack = loot.getItem();
/* 189 */           if (stack.func_77973_b() != Items.field_190931_a && (event.getTarget()).field_70122_E) {
/* 190 */             loot.pickup(event.getEntityPlayer());
/*     */           }
/*     */         } 
/*     */       } 
/* 194 */       event.setCanceled(true);
/* 195 */       event.setCancellationResult(EnumActionResult.SUCCESS);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityJoin(EntityJoinWorldEvent event) {
/* 201 */     if ((event.getWorld()).field_72995_K) {
/*     */       return;
/*     */     }
/* 204 */     if (ModConfig.INSTANCE.drops.advanced_drops_models && 
/* 205 */       event.getEntity().getClass() == EntityItem.class) {
/* 206 */       EntityItem item = (EntityItem)event.getEntity();
/* 207 */       if (!item.func_92059_d().func_190926_b() && (
/* 208 */         item.func_92059_d().func_77973_b() instanceof com.modularwarfare.common.type.BaseItem || ModConfig.INSTANCE.drops.advanced_drops_models_everything))
/* 209 */         this.executor.schedule(() -> item.func_184102_h().func_152344_a(()), 1L, TimeUnit.MILLISECONDS); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\handler\CommonEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */