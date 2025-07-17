/*     */ package com.modularwarfare;
/*     */ 
/*     */ import com.modularwarfare.api.EntityHeadShotEvent;
/*     */ import javax.script.ScriptEngine;
/*     */ import javax.script.ScriptException;
/*     */ import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BukkitHelper
/*     */ {
/*  23 */   public static ScriptEngine scriptEngine = (new NashornScriptEngineFactory()).getScriptEngine();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  40 */       scriptEngine.eval("var Bukkit=Java.type(\"org.bukkit.Bukkit\");\r\nvar BukkitEntityHeadShotEvent=Java.type(\"com.modularwarfare.BukkitHelper.BukkitEntityHeadShotEvent\");\r\nvar BukkitWeaponAttachmentEvent=Java.type(\"com.modularwarfare.BukkitHelper.BukkitWeaponAttachmentEvent\");\r\nvar CraftEntity=Java.type(\"org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity\");\r\nvar CraftItemStack=Java.type(\"org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack\");\r\nfunction toBukkitEntity(entity){\r\n    return CraftEntity.getEntity(Bukkit.getServer(),entity);\r\n}\r\nfunction toBukkitItemstack(stack){\r\n    return CraftItemStack.asBukkitCopy(stack);\r\n}\r\nfunction toForgeItemstack(stack){\r\n    return CraftItemStack.asNMSCopy(stack);\r\n}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  54 */     catch (ScriptException e) {
/*  55 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void onEntityHeadShot(EntityHeadShotEvent event) {
/*  61 */     scriptEngine.put("event", event);
/*     */     try {
/*  63 */       scriptEngine.eval("    var bukkitEvent=new BukkitEntityHeadShotEvent(toBukkitEntity(event.getVictim()),toBukkitEntity(event.getShooter()));\r\n    Bukkit.getPluginManager().callEvent(bukkitEvent);");
/*     */     }
/*  65 */     catch (ScriptException e) {
/*  66 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BukkitEntityHeadShotEvent
/*     */     extends Event
/*     */   {
/* 115 */     public static final HandlerList handlerList = new HandlerList();
/*     */ 
/*     */     
/*     */     public Entity victim;
/*     */     
/*     */     public Entity shooter;
/*     */ 
/*     */     
/*     */     public BukkitEntityHeadShotEvent(Entity victim, Entity shooter) {
/* 124 */       this.victim = victim;
/* 125 */       this.shooter = shooter;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HandlerList getHandlers() {
/* 134 */       return handlerList;
/*     */     }
/*     */     
/*     */     public static HandlerList getHandlerList() {
/* 138 */       return handlerList;
/*     */     }
/*     */   }
/*     */   
/*     */   @Cancelable
/*     */   public static class BukkitWeaponAttachmentEvent
/*     */     extends Event {
/* 145 */     public static final HandlerList handlerList = new HandlerList();
/*     */     
/*     */     public EntityPlayer player;
/*     */     
/*     */     public final boolean isUnload;
/*     */     public final boolean isUnloadAll;
/*     */     public final String unloadAttachmentType;
/*     */     public final ItemStack gun;
/*     */     public ItemStack loadAttach;
/*     */     public boolean isCanceled = false;
/*     */     
/*     */     public BukkitWeaponAttachmentEvent(EntityPlayer player, boolean isUnload, boolean isUnloadAll, String unloadAttachmentType, ItemStack gun, ItemStack loadAttach) {
/* 157 */       this.player = player;
/* 158 */       this.isUnload = isUnload;
/* 159 */       this.isUnloadAll = isUnloadAll;
/* 160 */       this.unloadAttachmentType = unloadAttachmentType;
/* 161 */       this.gun = gun;
/* 162 */       this.loadAttach = loadAttach;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public HandlerList getHandlers() {
/* 168 */       return handlerList;
/*     */     }
/*     */     
/*     */     public static HandlerList getHandlerList() {
/* 172 */       return handlerList;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\BukkitHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */