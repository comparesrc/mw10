/*     */ package com.modularwarfare.mixin.client;
/*     */ 
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.GuiChat;
/*     */ import net.minecraft.client.gui.GuiIngame;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.advancements.GuiScreenAdvancements;
/*     */ import net.minecraft.client.gui.inventory.GuiContainerCreative;
/*     */ import net.minecraft.client.gui.inventory.GuiInventory;
/*     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.client.tutorial.Tutorial;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ import org.spongepowered.asm.mixin.Shadow;
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
/*     */ @Mixin({Minecraft.class})
/*     */ public abstract class MixinMinecraft
/*     */ {
/*     */   @Shadow
/*     */   public GameSettings field_71474_y;
/*     */   @Shadow
/*     */   public RenderGlobal field_71438_f;
/*     */   @Shadow
/*     */   public EntityRenderer field_71460_t;
/*     */   @Shadow
/*     */   public EntityPlayerSP field_71439_g;
/*     */   @Shadow
/*     */   public GuiIngame field_71456_v;
/*     */   @Shadow
/*     */   @Nullable
/*     */   public GuiScreen field_71462_r;
/*     */   @Shadow
/*     */   public PlayerControllerMP field_71442_b;
/*     */   @Shadow
/*     */   @Final
/*     */   private Tutorial field_193035_aW;
/*     */   @Shadow
/*     */   private int field_71467_ac;
/*     */   @Shadow
/*     */   public boolean field_71415_G;
/*     */   
/*     */   @Shadow
/*     */   @Nullable
/*     */   public abstract Entity func_175606_aa();
/*     */   
/*     */   @Shadow
/*     */   public abstract void func_147108_a(@Nullable GuiScreen paramGuiScreen);
/*     */   
/*     */   @Shadow
/*     */   @Nullable
/*     */   public abstract NetHandlerPlayClient func_147114_u();
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_147116_af();
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_147121_ag();
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_147112_ai();
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_147115_a(boolean paramBoolean);
/*     */   
/*     */   @Overwrite
/*     */   private void func_184117_aA() {
/*  94 */     for (; this.field_71474_y.field_151457_aa.func_151468_f(); this.field_71438_f.func_174979_m()) {
/*  95 */       this.field_71474_y.field_74320_O++;
/*     */       
/*  97 */       if (this.field_71474_y.field_74320_O > 2) {
/*  98 */         this.field_71474_y.field_74320_O = 0;
/*     */       }
/*     */       
/* 101 */       if (this.field_71474_y.field_74320_O == 0) {
/* 102 */         this.field_71460_t.func_175066_a(func_175606_aa());
/* 103 */       } else if (this.field_71474_y.field_74320_O == 1) {
/* 104 */         this.field_71460_t.func_175066_a(null);
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     while (this.field_71474_y.field_151458_ab.func_151468_f()) {
/* 109 */       this.field_71474_y.field_74326_T = !this.field_71474_y.field_74326_T;
/*     */     }
/*     */     
/* 112 */     for (int i = 0; i < 9; i++) {
/*     */       
/* 114 */       boolean flag = this.field_71474_y.field_193629_ap.func_151470_d();
/* 115 */       boolean flag1 = this.field_71474_y.field_193630_aq.func_151470_d();
/* 116 */       boolean reloading = (ClientRenderHooks.getAnimMachine((EntityLivingBase)this.field_71439_g)).reloading;
/*     */       
/* 118 */       if (this.field_71474_y.field_151456_ac[i].func_151468_f())
/*     */       {
/* 120 */         if (this.field_71439_g.func_175149_v()) {
/*     */           
/* 122 */           this.field_71456_v.func_175187_g().func_175260_a(i);
/*     */         }
/* 124 */         else if ((!this.field_71439_g.func_184812_l_() || this.field_71462_r != null || (!flag1 && !flag)) && !reloading) {
/*     */           
/* 126 */           this.field_71439_g.field_71071_by.field_70461_c = i;
/*     */         }
/*     */         else {
/*     */           
/* 130 */           GuiContainerCreative.func_192044_a((Minecraft)this, i, flag1, flag);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 135 */     while (this.field_71474_y.field_151445_Q.func_151468_f()) {
/* 136 */       if (this.field_71442_b.func_110738_j()) {
/* 137 */         this.field_71439_g.func_175163_u(); continue;
/*     */       } 
/* 139 */       this.field_193035_aW.func_193296_a();
/* 140 */       func_147108_a((GuiScreen)new GuiInventory((EntityPlayer)this.field_71439_g));
/*     */     } 
/*     */ 
/*     */     
/* 144 */     while (this.field_71474_y.field_194146_ao.func_151468_f()) {
/* 145 */       func_147108_a((GuiScreen)new GuiScreenAdvancements(this.field_71439_g.field_71174_a.func_191982_f()));
/*     */     }
/*     */     
/* 148 */     while (this.field_71474_y.field_186718_X.func_151468_f()) {
/* 149 */       if (!this.field_71439_g.func_175149_v()) {
/* 150 */         func_147114_u().func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.field_177992_a, EnumFacing.DOWN));
/*     */       }
/*     */     } 
/*     */     
/* 154 */     while (this.field_71474_y.field_74316_C.func_151468_f()) {
/* 155 */       if (!this.field_71439_g.func_175149_v()) {
/* 156 */         this.field_71439_g.func_71040_bB(GuiScreen.func_146271_m());
/*     */       }
/*     */     } 
/*     */     
/* 160 */     boolean flag2 = (this.field_71474_y.field_74343_n != EntityPlayer.EnumChatVisibility.HIDDEN);
/*     */     
/* 162 */     if (flag2) {
/* 163 */       while (this.field_71474_y.field_74310_D.func_151468_f()) {
/* 164 */         func_147108_a((GuiScreen)new GuiChat());
/*     */       }
/*     */       
/* 167 */       if (this.field_71462_r == null && this.field_71474_y.field_74323_J.func_151468_f()) {
/* 168 */         func_147108_a((GuiScreen)new GuiChat("/"));
/*     */       }
/*     */     } 
/*     */     
/* 172 */     if (this.field_71439_g.func_184587_cr()) {
/* 173 */       if (!this.field_71474_y.field_74313_G.func_151470_d()) {
/* 174 */         this.field_71442_b.func_78766_c((EntityPlayer)this.field_71439_g);
/*     */       }
/*     */ 
/*     */       
/*     */       do {
/*     */       
/* 180 */       } while (this.field_71474_y.field_74312_F.func_151468_f());
/* 181 */       while (this.field_71474_y.field_74313_G.func_151468_f());
/*     */ 
/*     */ 
/*     */       
/* 185 */       while (this.field_71474_y.field_74322_I.func_151468_f());
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 194 */       while (this.field_71474_y.field_74312_F.func_151468_f()) {
/* 195 */         func_147116_af();
/*     */       }
/*     */       
/* 198 */       while (this.field_71474_y.field_74313_G.func_151468_f()) {
/* 199 */         func_147121_ag();
/*     */       }
/*     */       
/* 202 */       while (this.field_71474_y.field_74322_I.func_151468_f()) {
/* 203 */         func_147112_ai();
/*     */       }
/*     */     } 
/*     */     
/* 207 */     if (this.field_71474_y.field_74313_G.func_151470_d() && this.field_71467_ac == 0 && !this.field_71439_g.func_184587_cr()) {
/* 208 */       func_147121_ag();
/*     */     }
/*     */     
/* 211 */     func_147115_a((this.field_71462_r == null && this.field_71474_y.field_74312_F.func_151470_d() && this.field_71415_G));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\mixin\client\MixinMinecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */