/*     */ package com.modularwarfare.client.gui;
/*     */ 
/*     */ import com.modularwarfare.common.backpacks.ItemBackpack;
/*     */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*     */ import com.modularwarfare.common.container.ContainerInventoryModified;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.InventoryEffectRenderer;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.items.CapabilityItemHandler;
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ 
/*     */ 
/*     */ public class GuiInventoryModified
/*     */   extends InventoryEffectRenderer
/*     */ {
/*  24 */   public static final ResourceLocation ICONS = new ResourceLocation("modularwarfare", "textures/gui/icons.png");
/*  25 */   public static final ResourceLocation INVENTORY_BG = new ResourceLocation("modularwarfare", "textures/gui/inventory.png");
/*     */   
/*     */   private float oldMouseX;
/*     */   
/*     */   private float oldMouseY;
/*     */   
/*     */   public GuiInventoryModified(EntityPlayer player) {
/*  32 */     super((Container)new ContainerInventoryModified(player.field_71071_by, !(player.func_130014_f_()).field_72995_K, player));
/*  33 */     this.field_146291_p = true;
/*  34 */     this.field_146999_f = 176;
/*     */     
/*  36 */     this.field_147000_g = 185;
/*     */   }
/*     */   
/*     */   private void resetGuiLeft() {
/*  40 */     this.field_147003_i = (this.field_146294_l - this.field_146999_f) / 2;
/*     */   }
/*     */   
/*     */   public void func_73876_c() {
/*  44 */     func_175378_g();
/*  45 */     resetGuiLeft();
/*     */   }
/*     */   
/*     */   public void func_73866_w_() {
/*  49 */     this.field_146292_n.clear();
/*  50 */     super.func_73866_w_();
/*  51 */     resetGuiLeft();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int mouseX, int mouseY) {}
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*  60 */     if (!((Minecraft.func_71410_x()).field_71439_g.field_71070_bA instanceof ContainerInventoryModified)) {
/*     */       return;
/*     */     }
/*  63 */     func_146276_q_();
/*  64 */     this.oldMouseX = mouseX;
/*  65 */     this.oldMouseY = mouseY;
/*  66 */     super.func_73863_a(mouseX, mouseY, partialTicks);
/*  67 */     func_191948_b(mouseX, mouseY);
/*     */   }
/*     */   
/*     */   protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {
/*  71 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.9F);
/*  72 */     this.field_146297_k.func_110434_K().func_110577_a(INVENTORY_BG);
/*  73 */     int k = this.field_147003_i;
/*  74 */     int l = this.field_147009_r;
/*  75 */     func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
/*  76 */     ContainerInventoryModified containter = (ContainerInventoryModified)(Minecraft.func_71410_x()).field_71439_g.field_71070_bA;
/*  77 */     IExtraItemHandler iExtraItemHandler = containter.extra;
/*  78 */     if (iExtraItemHandler.getStackInSlot(0).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null)) {
/*  79 */       ItemStack stack = iExtraItemHandler.getStackInSlot(0);
/*  80 */       IItemHandler backpackInv = (IItemHandler)stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null);
/*  81 */       int xP = 0;
/*  82 */       int yP = 0;
/*  83 */       int x = k + 180;
/*  84 */       int y = l + 18;
/*  85 */       this.field_146297_k.func_110434_K().func_110577_a(ICONS);
/*  86 */       func_73729_b(x - 5, y - 18, 18, 0, 82, 18);
/*  87 */       func_73729_b(x - 5, y, 18, 5, 82, 18);
/*  88 */       for (int i = 0; i < backpackInv.getSlots(); i++) {
/*  89 */         drawSlotBackground(x + xP * 18, -1 + y + yP * 18);
/*  90 */         if (++xP % 4 == 0) {
/*  91 */           xP = 0;
/*  92 */           yP++;
/*  93 */           if (i + 1 < backpackInv.getSlots()) {
/*  94 */             func_73729_b(x - 5, y + yP * 18, 18, 5, 82, 18);
/*     */           }
/*  96 */         } else if (i + 1 >= backpackInv.getSlots()) {
/*  97 */           yP++;
/*     */         } 
/*     */       } 
/* 100 */       func_73729_b(x - 5, -1 + y + yP * 18, 18, 33, 82, 5);
/*     */       
/* 102 */       if (stack != null) {
/* 103 */         ItemBackpack backpackItem = (ItemBackpack)stack.func_77973_b();
/* 104 */         this.field_146289_q.func_78276_b(backpackItem.type.displayName, x, y - 12, 16777215);
/*     */       } 
/* 106 */       RenderHelper.func_74518_a();
/* 107 */       GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
/*     */     } 
/* 109 */     GuiPlayerInventory.drawEntityOnScreen(k + 51, l + 75, 30, (k + 51) - this.oldMouseX, (l + 75 - 50) - this.oldMouseY, (EntityLivingBase)this.field_146297_k.field_71439_g);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) {}
/*     */   
/*     */   public void drawSlotBackground(int x, int y) {
/* 116 */     this.field_146297_k.func_110434_K().func_110577_a(ICONS);
/* 117 */     func_73729_b(x, y, 0, 0, 18, 18);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\gui\GuiInventoryModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */