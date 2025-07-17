/*     */ package com.modularwarfare.client.handler;
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.HandleKeyEvent;
/*     */ import com.modularwarfare.client.ClientProxy;
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderGunStatic;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.EnhancedRenderConfig;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*     */ import com.modularwarfare.client.input.KeyEntry;
/*     */ import com.modularwarfare.client.input.KeyType;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.common.guns.AttachmentType;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketGunReload;
/*     */ import com.modularwarfare.common.network.PacketGunSwitchMode;
/*     */ import com.modularwarfare.common.network.PacketOpenGui;
/*     */ import com.modularwarfare.utility.MWSound;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.client.registry.ClientRegistry;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.InputEvent;
/*     */ 
/*     */ public class KeyInputHandler extends ForgeEvent {
/*     */   private ArrayList<KeyEntry> keyBinds;
/*     */   
/*     */   public KeyInputHandler() {
/*  41 */     this.keyBinds = new ArrayList<>();
/*  42 */     this.keyBinds.add(new KeyEntry(KeyType.GunReload));
/*  43 */     this.keyBinds.add(new KeyEntry(KeyType.ClientReload));
/*  44 */     this.keyBinds.add(new KeyEntry(KeyType.FireMode));
/*  45 */     this.keyBinds.add(new KeyEntry(KeyType.Inspect));
/*  46 */     this.keyBinds.add(new KeyEntry(KeyType.GunUnload));
/*  47 */     this.keyBinds.add(new KeyEntry(KeyType.AddAttachment));
/*  48 */     this.keyBinds.add(new KeyEntry(KeyType.Flashlight));
/*     */     
/*  50 */     if (!ModConfig.INSTANCE.general.customInventory) {
/*  51 */       this.keyBinds.add(new KeyEntry(KeyType.Backpack));
/*     */     }
/*     */     
/*  54 */     this.keyBinds.add(new KeyEntry(KeyType.Left));
/*  55 */     this.keyBinds.add(new KeyEntry(KeyType.Right));
/*  56 */     this.keyBinds.add(new KeyEntry(KeyType.Up));
/*  57 */     this.keyBinds.add(new KeyEntry(KeyType.Down));
/*     */     
/*  59 */     if (ModularWarfare.DEV_ENV) {
/*  60 */       this.keyBinds.add(new KeyEntry(KeyType.DebugMode));
/*     */     }
/*     */     
/*  63 */     for (KeyEntry keyEntry : this.keyBinds) {
/*  64 */       ClientRegistry.registerKeyBinding(keyEntry.keyBinding);
/*     */     }
/*  66 */     jetpackFire = new KeyBinding(KeyType.Jetpack.displayName, KeyType.Jetpack.keyCode, "ModularWarfare");
/*  67 */     ClientRegistry.registerKeyBinding(jetpackFire);
/*     */   }
/*     */   public static KeyBinding jetpackFire;
/*     */   @SubscribeEvent
/*     */   public void onKeyInput(InputEvent.KeyInputEvent event) {
/*  72 */     for (KeyEntry keyEntry : this.keyBinds) {
/*  73 */       if (keyEntry.keyBinding.func_151468_f()) {
/*  74 */         handleKeyInput(keyEntry.keyType);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleKeyInput(KeyType keyType) {
/*  81 */     if ((Minecraft.func_71410_x()).field_71439_g != null) {
/*  82 */       ItemStack reloadStack, unloadStack; EntityPlayerSP entityPlayer = (Minecraft.func_71410_x()).field_71439_g;
/*  83 */       HandleKeyEvent event = new HandleKeyEvent(keyType);
/*  84 */       MinecraftForge.EVENT_BUS.post((Event)event);
/*     */       
/*  86 */       switch (keyType) {
/*     */ 
/*     */         
/*     */         case ClientReload:
/*  90 */           ModularWarfare.loadConfig();
/*  91 */           ScriptHost.INSTANCE.reset();
/*     */           
/*  93 */           if (entityPlayer.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun) {
/*  94 */             ItemStack gunStack = entityPlayer.func_184582_a(EntityEquipmentSlot.MAINHAND);
/*  95 */             GunType gunType = ((ItemGun)gunStack.func_77973_b()).type;
/*  96 */             for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/*  97 */               ItemStack itemStack = GunType.getAttachment(gunStack, attachment);
/*  98 */               if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/*  99 */                 AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/* 100 */                 if (attachmentType.hasModel()) {
/* 101 */                   attachmentType.reloadModel();
/*     */                 }
/*     */               } 
/*     */             } 
/* 105 */             if (gunType.hasModel() && gunType.animationType.equals(WeaponAnimationType.ENHANCED)) {
/* 106 */               gunType.enhancedModel.config = (EnhancedRenderConfig)ModularWarfare.getRenderConfig((BaseType)gunType, GunEnhancedRenderConfig.class);
/* 107 */             } else if (gunType.hasModel()) {
/* 108 */               gunType.reloadModel();
/*     */             } 
/*     */           } 
/*     */           
/* 112 */           if (entityPlayer.func_70093_af()) {
/* 113 */             ModularWarfare.PROXY.reloadModels(true);
/*     */           }
/*     */           return;
/*     */         case FireMode:
/* 117 */           if (!entityPlayer.func_175149_v() && 
/* 118 */             entityPlayer.func_184614_ca() != null && entityPlayer.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 119 */             ItemGun itemGun = (ItemGun)entityPlayer.func_184614_ca().func_77973_b();
/* 120 */             GunType gunType = itemGun.type;
/* 121 */             PacketGunSwitchMode.switchClient((EntityPlayer)entityPlayer);
/* 122 */             ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunSwitchMode());
/* 123 */             ModularWarfare.PROXY.onModeChangeAnimation((EntityPlayer)entityPlayer, gunType.internalName);
/*     */           } 
/*     */           return;
/*     */         
/*     */         case Inspect:
/* 128 */           if (!entityPlayer.func_175149_v() && 
/* 129 */             entityPlayer.func_184614_ca() != null && entityPlayer.func_184614_ca().func_77973_b() instanceof ItemGun && 
/* 130 */             ClientProxy.gunEnhancedRenderer.getController((EntityLivingBase)entityPlayer, null) != null) {
/* 131 */             (ClientProxy.gunEnhancedRenderer.getController((EntityLivingBase)entityPlayer, null)).INSPECT = 0.0D;
/*     */           }
/*     */           return;
/*     */ 
/*     */         
/*     */         case GunReload:
/* 137 */           reloadStack = entityPlayer.func_184614_ca();
/* 138 */           if (reloadStack != null && (reloadStack.func_77973_b() instanceof ItemGun || reloadStack.func_77973_b() instanceof com.modularwarfare.common.guns.ItemAmmo) && (
/* 139 */             ClientProxy.gunEnhancedRenderer.getController((EntityLivingBase)entityPlayer, null) == null || ClientProxy.gunEnhancedRenderer
/* 140 */             .getController((EntityLivingBase)entityPlayer, null).isCouldReload())) {
/* 141 */             ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReload());
/*     */           }
/*     */           return;
/*     */ 
/*     */         
/*     */         case GunUnload:
/* 147 */           unloadStack = entityPlayer.func_184614_ca();
/* 148 */           if ((ClientRenderHooks.getAnimMachine((EntityLivingBase)entityPlayer)).attachmentMode) {
/* 149 */             ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunUnloadAttachment(ClientProxy.attachmentUI.selectedAttachEnum.getName(), false));
/*     */           }
/* 151 */           else if (unloadStack != null && (unloadStack.func_77973_b() instanceof ItemGun || unloadStack.func_77973_b() instanceof com.modularwarfare.common.guns.ItemAmmo)) {
/* 152 */             ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReload(true));
/*     */           } 
/*     */           return;
/*     */ 
/*     */         
/*     */         case DebugMode:
/* 158 */           if (entityPlayer.func_70093_af()) {
/* 159 */             ModularWarfare.loadContentPacks(true);
/*     */           }
/*     */           return;
/*     */ 
/*     */         
/*     */         case AddAttachment:
/* 165 */           if (!entityPlayer.func_175149_v() && 
/* 166 */             entityPlayer.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && (Minecraft.func_71410_x()).field_71474_y.field_74320_O == 0 && 
/* 167 */             entityPlayer.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun) {
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
/* 181 */             ModularWarfare.PROXY.playSound(new MWSound(entityPlayer.func_180425_c(), "attachment.open", 1.0F, 1.0F));
/* 182 */             Minecraft.func_71410_x().func_147108_a((GuiScreen)new GuiGunModify());
/*     */           } 
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case Flashlight:
/* 189 */           if (!entityPlayer.func_175149_v() && 
/* 190 */             entityPlayer.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && (Minecraft.func_71410_x()).field_71474_y.field_74320_O == 0 && 
/* 191 */             entityPlayer.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun) {
/* 192 */             ItemStack gunStack = entityPlayer.func_184582_a(EntityEquipmentSlot.MAINHAND);
/* 193 */             if (GunType.getAttachment(gunStack, AttachmentPresetEnum.Flashlight) != null) {
/* 194 */               ItemAttachment itemAttachment = (ItemAttachment)GunType.getAttachment(gunStack, AttachmentPresetEnum.Flashlight).func_77973_b();
/* 195 */               if (itemAttachment != null) {
/* 196 */                 RenderGunStatic.isLightOn = !RenderGunStatic.isLightOn;
/*     */               }
/* 198 */               ModularWarfare.PROXY.playSound(new MWSound(entityPlayer.func_180425_c(), "attachment.apply", 1.0F, 1.0F));
/*     */             } 
/*     */           } 
/*     */           return;
/*     */ 
/*     */         
/*     */         case Backpack:
/* 205 */           if (!ModConfig.INSTANCE.general.customInventory && 
/* 206 */             !entityPlayer.func_184812_l_()) {
/* 207 */             ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketOpenGui(0));
/*     */           }
/*     */           return;
/*     */         
/*     */         case Left:
/* 212 */           ClientProxy.attachmentUI.processKeyInput(KeyType.Left);
/*     */           return;
/*     */         case Right:
/* 215 */           ClientProxy.attachmentUI.processKeyInput(KeyType.Right);
/*     */           return;
/*     */         case Up:
/* 218 */           ClientProxy.attachmentUI.processKeyInput(KeyType.Up);
/*     */           return;
/*     */         case Down:
/* 221 */           ClientProxy.attachmentUI.processKeyInput(KeyType.Down);
/*     */           return;
/*     */       } 
/*     */       
/* 225 */       ModularWarfare.LOGGER.warn("Default case called on handleKeyInput for " + keyType.toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\handler\KeyInputHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */