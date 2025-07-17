/*     */ package com.modularwarfare.client;
/*     */ 
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.AnimationUtils;
/*     */ import com.modularwarfare.client.handler.KeyInputHandler;
/*     */ import com.modularwarfare.client.hud.FlashSystem;
/*     */ import com.modularwarfare.client.model.InstantBulletRenderer;
/*     */ import com.modularwarfare.common.backpacks.BackpackType;
/*     */ import com.modularwarfare.common.backpacks.ItemBackpack;
/*     */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*     */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*     */ import com.modularwarfare.common.hitbox.playerdata.PlayerDataHandler;
/*     */ import com.modularwarfare.common.init.ModSounds;
/*     */ import com.modularwarfare.common.network.PacketBackpackElytraStart;
/*     */ import com.modularwarfare.common.network.PacketBackpackJet;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketOpenGui;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.inventory.GuiInventory;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.client.event.EntityViewRenderEvent;
/*     */ import net.minecraftforge.client.event.GuiOpenEvent;
/*     */ import net.minecraftforge.client.event.InputUpdateEvent;
/*     */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*     */ import net.minecraftforge.client.event.sound.PlaySoundEvent;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientEventHandler
/*     */ {
/*  51 */   public static float cemeraBobbing = 0.0F;
/*     */   public boolean lastJump;
/*     */   public static boolean isJetFly = false;
/*  54 */   public static float jetPower = 0.0F;
/*  55 */   public static int jetFireTime = 0;
/*  56 */   public static int jetCoolTime = 0;
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onInput(InputUpdateEvent event) {
/*  60 */     EntityPlayerSP player = (Minecraft.func_71410_x()).field_71439_g;
/*  61 */     if ((event.getMovementInput()).field_78901_c && !this.lastJump && !player.field_70122_E && player.field_70181_x < 0.0D && !player.func_184613_cA() && !player.field_71075_bZ.field_75100_b)
/*     */     {
/*  63 */       ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketBackpackElytraStart());
/*     */     }
/*  65 */     if ((event.getMovementInput()).field_78901_c && !this.lastJump && !player.field_70122_E && player.func_184613_cA() && !player.field_71075_bZ.field_75100_b)
/*     */     {
/*  67 */       ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketBackpackElytraStart());
/*     */     }
/*  69 */     boolean isElytra = false;
/*  70 */     boolean sneakToHover = false;
/*  71 */     if (player.hasCapability(CapabilityExtra.CAPABILITY, null)) {
/*  72 */       IExtraItemHandler extraSlots = (IExtraItemHandler)player.getCapability(CapabilityExtra.CAPABILITY, null);
/*  73 */       ItemStack itemstackBackpack = extraSlots.getStackInSlot(0);
/*     */       
/*  75 */       if (!itemstackBackpack.func_190926_b() && 
/*  76 */         itemstackBackpack.func_77973_b() instanceof ItemBackpack) {
/*  77 */         BackpackType backpack = ((ItemBackpack)itemstackBackpack.func_77973_b()).type;
/*  78 */         if (backpack.isElytra) {
/*  79 */           isElytra = true;
/*     */         }
/*  81 */         if (backpack.jetSneakHover) {
/*  82 */           sneakToHover = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     if ((event.getMovementInput()).field_78901_c) {
/*  88 */       if (!this.lastJump && !player.field_70122_E) {
/*  89 */         isJetFly = true;
/*     */       }
/*  91 */       if (isElytra) {
/*  92 */         isJetFly = false;
/*     */       }
/*  94 */     } else if (sneakToHover && (event.getMovementInput()).field_78899_d) {
/*  95 */       if (!this.lastJump && !player.field_70122_E) {
/*  96 */         isJetFly = true;
/*     */       }
/*     */     } else {
/*  99 */       isJetFly = false;
/*     */     } 
/* 101 */     this.lastJump = (event.getMovementInput()).field_78901_c;
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void cemeraSetup(EntityViewRenderEvent.CameraSetup event) {
/* 106 */     GlStateManager.func_179114_b(cemeraBobbing * 5.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderWorld(RenderWorldLastEvent event) {
/* 111 */     InstantBulletRenderer.RenderAllTrails(event.getPartialTicks());
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerTick(TickEvent.PlayerTickEvent event) {
/* 116 */     if (event.phase == TickEvent.Phase.START && event.side == Side.CLIENT && 
/* 117 */       event.player == (Minecraft.func_71410_x()).field_71439_g) {
/* 118 */       boolean isJetFly = false;
/* 119 */       BackpackType backpack = null;
/* 120 */       if (event.player.hasCapability(CapabilityExtra.CAPABILITY, null)) {
/* 121 */         IExtraItemHandler extraSlots = (IExtraItemHandler)event.player.getCapability(CapabilityExtra.CAPABILITY, null);
/* 122 */         ItemStack itemstackBackpack = extraSlots.getStackInSlot(0);
/*     */         
/* 124 */         if (!itemstackBackpack.func_190926_b() && 
/* 125 */           itemstackBackpack.func_77973_b() instanceof ItemBackpack) {
/* 126 */           backpack = ((ItemBackpack)itemstackBackpack.func_77973_b()).type;
/* 127 */           if (backpack.isJet) {
/* 128 */             this; isJetFly = (ClientEventHandler.isJetFly || Keyboard.isKeyDown(KeyInputHandler.jetpackFire.func_151463_i()));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 133 */       jetCoolTime--;
/* 134 */       if (jetCoolTime < 0) {
/* 135 */         jetCoolTime = 0;
/*     */       }
/* 137 */       if (backpack != null) {
/* 138 */         if (isJetFly && event.player.func_184613_cA() && !event.player.func_70093_af() && 
/* 139 */           jetFireTime == 0 && jetCoolTime == 0) {
/* 140 */           jetFireTime = 1;
/* 141 */           ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketBackpackJet(true));
/*     */         } 
/*     */         
/* 144 */         if (jetFireTime > 0 && jetCoolTime == 0) {
/* 145 */           double speed = event.player.field_70159_w * event.player.field_70159_w + event.player.field_70181_x * event.player.field_70181_x + event.player.field_70179_y * event.player.field_70179_y;
/*     */           
/* 147 */           speed = Math.sqrt(speed);
/* 148 */           jetFireTime++;
/* 149 */           if (jetFireTime > backpack.jetElytraBoostDuration) {
/* 150 */             jetCoolTime = backpack.jetElytraBoostCoolTime;
/* 151 */             jetFireTime = 0;
/*     */           } 
/* 153 */           Vec3d vec = event.player.func_70040_Z();
/* 154 */           vec = vec.func_186678_a(Math.max(speed, backpack.jetElytraBoost));
/* 155 */           event.player.field_70159_w = vec.field_72450_a;
/* 156 */           event.player.field_70181_x = vec.field_72448_b;
/* 157 */           event.player.field_70179_y = vec.field_72449_c;
/* 158 */           AnimationUtils.isJet.put(event.player.func_70005_c_(), Long.valueOf(System.currentTimeMillis() + 100L));
/*     */         } 
/* 160 */         if (isJetFly && !event.player.func_184613_cA()) {
/* 161 */           jetPower += backpack.jetWorkForce;
/* 162 */           if (jetPower > backpack.jetMaxForce) {
/* 163 */             jetPower = backpack.jetMaxForce;
/*     */           }
/* 165 */           if (event.player.func_70093_af()) {
/* 166 */             jetPower = 0.0F;
/*     */           }
/* 168 */           if (event.player.field_70181_x < jetPower) {
/* 169 */             event.player.field_70181_x = jetPower;
/*     */           }
/* 171 */           ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketBackpackJet());
/* 172 */           AnimationUtils.isJet.put(event.player.func_70005_c_(), Long.valueOf(System.currentTimeMillis() + 100L));
/*     */         } else {
/* 174 */           jetPower = backpack.jetIdleForce;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onGuiLaunch(GuiOpenEvent event) {
/* 183 */     if (ModConfig.INSTANCE.general.customInventory && 
/* 184 */       event.getGui() != null && event.getGui().getClass() == GuiInventory.class) {
/* 185 */       EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/* 186 */       if (!entityPlayerSP.func_184812_l_()) {
/* 187 */         event.setCanceled(true);
/* 188 */         ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketOpenGui(0));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
/* 197 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/* 198 */     if (entityPlayerSP == null)
/* 199 */       return;  PlayerDataHandler.clientSideData.clear();
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void onPlaySound(PlaySoundEvent event) {
/* 205 */     if (ModConfig.INSTANCE.walks_sounds.walk_sounds && !Loader.isModLoaded("dsurround")) {
/* 206 */       float soundVolume = ModConfig.INSTANCE.walks_sounds.volume;
/* 207 */       Minecraft mc = Minecraft.func_71410_x();
/* 208 */       ResourceLocation currentSound = event.getSound().func_147650_b();
/*     */       
/* 210 */       if (FlashSystem.flashValue > 0 && !event.getSound().func_147650_b().toString().contains("stun") && !event.getSound().func_147650_b().toString().contains("flashed"))
/* 211 */         event.setResultSound((ISound)new PositionedSoundRecord(currentSound, event.getSound().func_184365_d(), 0.05F, 1.0F - FlashSystem.flashValue / 50.0F, false, 0, ISound.AttenuationType.LINEAR, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i())
/*     */             {
/*     */             
/*     */             }); 
/* 215 */       if (currentSound.toString().equals("minecraft:entity.generic.explode")) {
/* 216 */         event.setResultSound(null);
/*     */       }
/* 218 */       if (currentSound.toString().equals("minecraft:block.grass.step")) {
/* 219 */         if (mc.field_71439_g.func_70051_ag()) {
/* 220 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_GRASS_SPRINT, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } else {
/* 222 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_GRASS_WALK, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } 
/* 224 */       } else if (currentSound.toString().equals("minecraft:block.stone.step")) {
/* 225 */         if (mc.field_71439_g.func_70051_ag()) {
/* 226 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_STONE_SPRINT, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } else {
/* 228 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_STONE_WALK, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } 
/* 230 */       } else if (currentSound.toString().equals("minecraft:block.gravel.step")) {
/* 231 */         if (mc.field_71439_g.func_70051_ag()) {
/* 232 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_GRAVEL_SPRINT, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } else {
/* 234 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_GRAVEL_WALK, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } 
/* 236 */       } else if (currentSound.toString().equals("minecraft:block.metal.step")) {
/* 237 */         if (mc.field_71439_g.func_70051_ag()) {
/* 238 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_METAL_SPRINT, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } else {
/* 240 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_METAL_WALK, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } 
/* 242 */       } else if (currentSound.toString().equals("minecraft:block.wood.step")) {
/* 243 */         if (mc.field_71439_g.func_70051_ag()) {
/* 244 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_WOOD_SPRINT, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } else {
/* 246 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_WOOD_WALK, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } 
/* 248 */       } else if (currentSound.toString().equals("minecraft:block.sand.step")) {
/* 249 */         if (mc.field_71439_g.func_70051_ag()) {
/* 250 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_SAND_SPRINT, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } else {
/* 252 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_SAND_WALK, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } 
/* 254 */       } else if (currentSound.toString().equals("minecraft:block.snow.step")) {
/* 255 */         if (mc.field_71439_g.func_70051_ag()) {
/* 256 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_SNOW_SPRINT, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } else {
/* 258 */           event.setResultSound((ISound)new PositionedSoundRecord(ModSounds.STEP_SNOW_WALK, SoundCategory.BLOCKS, (FlashSystem.flashValue > 0) ? (soundVolume * 0.05F) : soundVolume, (FlashSystem.flashValue > 0) ? (1.0F - FlashSystem.flashValue / 50.0F) : 1.0F, event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i()));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\ClientEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */