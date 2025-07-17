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
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ 
/*     */ public class CommonEventHandler
/*     */ {
/*  47 */   public static HashMap<String, Long> playerTimeoutMap = new HashMap<>();
/*     */   
/*     */   private static int getRandomNumberInRange(int min, int max) {
/*  50 */     if (min >= max) {
/*  51 */       throw new IllegalArgumentException("max must be greater than min");
/*     */     }
/*  53 */     Random r = new Random();
/*  54 */     return r.nextInt(max - min + 1) + min;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
/*  59 */     if (ModConfig.INSTANCE.general.modified_pack_server_kick || ModConfig.INSTANCE.general.directory_pack_server_kick) {
/*  60 */       playerTimeoutMap.put(event.player.func_70005_c_(), Long.valueOf(System.currentTimeMillis()));
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onServerTick(TickEvent.ServerTickEvent event) {
/*  66 */     if (event.phase != TickEvent.Phase.END) {
/*     */       return;
/*     */     }
/*  69 */     long time = System.currentTimeMillis();
/*  70 */     ArrayList<String> list = new ArrayList<>();
/*  71 */     playerTimeoutMap.forEach((name, t) -> {
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
/*  86 */     list.forEach(name -> playerTimeoutMap.remove(name));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLivingDeath(LivingDeathEvent event) {
/*  93 */     if (ModConfig.INSTANCE.killFeed.enableKillFeed) {
/*  94 */       Entity entity = event.getEntity();
/*  95 */       if (entity instanceof EntityPlayer && 
/*  96 */         !entity.field_70170_p.field_72995_K && 
/*  97 */         event.getSource().func_76352_a() && 
/*  98 */         event.getSource().func_76346_g() instanceof EntityPlayer) {
/*  99 */         ItemStack heldStack = ((EntityPlayer)event.getSource().func_76346_g()).func_184582_a(EntityEquipmentSlot.MAINHAND);
/* 100 */         if (heldStack != null && 
/* 101 */           heldStack.func_77973_b() instanceof ItemGun) {
/* 102 */           String text = getRandomMessage(event.getSource().func_76346_g().func_145748_c_().func_150254_d(), event.getEntity().func_145748_c_().func_150254_d());
/* 103 */           ModularWarfare.NETWORK.sendToAll((PacketBase)new PacketClientKillFeedEntry(text, ModConfig.INSTANCE.killFeed.messageDuration, ((ItemGun)heldStack.func_77973_b()).type.internalName));
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
/* 114 */     if (ModConfig.INSTANCE.killFeed.messageList != null && ModConfig.INSTANCE.killFeed.messageList.size() > 0) {
/* 115 */       int r = getRandomNumberInRange(0, ModConfig.INSTANCE.killFeed.messageList.size() - 1);
/* 116 */       String choosen = ModConfig.INSTANCE.killFeed.messageList.get(r);
/* 117 */       choosen = choosen.replace("{killer}", killer).replace("{victim}", victim);
/* 118 */       choosen = choosen.replace("&", "§");
/* 119 */       return choosen;
/*     */     } 
/* 121 */     return "";
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLivingAttack(LivingAttackEvent event) {
/* 126 */     if (!(event.getEntityLiving()).field_70170_p.field_72995_K)
/*     */       return; 
/* 128 */     Entity entity = event.getEntity();
/* 129 */     if ((entity.func_130014_f_()).field_72995_K) {
/* 130 */       ModularWarfare.PROXY.addBlood(event.getEntityLiving(), 10, true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLivingHurt(LivingHurtEvent event) {
/* 136 */     Entity entity = event.getEntity();
/* 137 */     if (entity instanceof EntityItemLoot) {
/*     */       return;
/*     */     }
/*     */   }
/*     */   
/* 142 */   private static final ModularWarfareWorldListener WORLD_LISTENER = new ModularWarfareWorldListener();
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onInitWorld(WorldEvent.Load event) {
/* 146 */     World world = event.getWorld();
/* 147 */     world.func_72954_a((IWorldEventListener)WORLD_LISTENER);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUnloadWorld(WorldEvent.Unload event) {
/* 152 */     World world = event.getWorld();
/* 153 */     world.func_72848_b((IWorldEventListener)WORLD_LISTENER);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityInteractBlock(PlayerInteractEvent.RightClickBlock event) {
/* 158 */     if (ModConfig.INSTANCE.guns.guns_interaction_hand && 
/* 159 */       (event.getWorld()).field_72995_K && 
/* 160 */       (Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && 
/* 161 */       (Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun && 
/* 162 */       !(event.getWorld().func_180495_p(event.getPos()).func_177230_c() instanceof net.minecraft.block.BlockContainer)) {
/* 163 */       event.setUseBlock(Event.Result.DENY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void explosionEvent(ExplosionEvent e) {
/* 173 */     Vec3d pos = e.getExplosion().getPosition();
/* 174 */     ModularWarfare.NETWORK.sendToAll((PacketBase)new PacketExplosion(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c));
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityInteract(PlayerInteractEvent.EntityInteractSpecific event) {
/* 180 */     if (event.getTarget() instanceof EntityItemLoot) {
/* 181 */       if (!(event.getWorld()).field_72995_K && (event.getTarget()).field_70122_E && !event.getEntityPlayer().func_175149_v()) {
/* 182 */         EntityItemLoot loot = (EntityItemLoot)event.getTarget();
/* 183 */         if (loot.getCustomAge() > 20) {
/* 184 */           ItemStack stack = loot.getItem();
/* 185 */           if (stack.func_77973_b() != Items.field_190931_a && (event.getTarget()).field_70122_E) {
/* 186 */             loot.pickup(event.getEntityPlayer());
/*     */           }
/*     */         } 
/*     */       } 
/* 190 */       event.setCanceled(true);
/* 191 */       event.setCancellationResult(EnumActionResult.SUCCESS);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityJoin(EntityJoinWorldEvent event) {
/* 197 */     if ((event.getWorld()).field_72995_K) {
/*     */       return;
/*     */     }
/* 200 */     if (ModConfig.INSTANCE.drops.advanced_drops_models && 
/* 201 */       event.getEntity().getClass() == EntityItem.class) {
/* 202 */       EntityItem item = (EntityItem)event.getEntity();
/* 203 */       if (!item.func_92059_d().func_190926_b() && (
/* 204 */         item.func_92059_d().func_77973_b() instanceof com.modularwarfare.common.type.BaseItem || ModConfig.INSTANCE.drops.advanced_drops_models_everything)) {
/* 205 */         EntityItemLoot loot = new EntityItemLoot((EntityItem)event.getEntity());
/* 206 */         event.getEntity().func_70106_y();
/* 207 */         loot.setInfinitePickupDelay();
/* 208 */         event.setResult(Event.Result.DENY);
/* 209 */         event.setCanceled(true);
/* 210 */         event.getWorld().func_72838_d((Entity)loot);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\handler\CommonEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */