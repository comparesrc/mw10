/*     */ package com.modularwarfare.client.hud;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.client.ClientRenderHooks;
/*     */ import com.modularwarfare.client.input.KeyType;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.common.guns.AttachmentType;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.ItemSpray;
/*     */ import com.modularwarfare.common.guns.SprayType;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketGunAddAttachment;
/*     */ import com.modularwarfare.common.network.PacketGunUnloadAttachment;
/*     */ import com.modularwarfare.utility.RenderHelperMW;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttachmentUI
/*     */ {
/*  35 */   public int selectedAttachTypeIndex = 0;
/*  36 */   public int selectedAttachIndex = 0;
/*     */   
/*  38 */   public int sizeAttachTypeIndex = 0;
/*  39 */   public int sizeAttachAttachIndex = 0;
/*     */   
/*     */   public AttachmentPresetEnum selectedAttachEnum;
/*     */   
/*     */   @SubscribeEvent
/*     */   public void clientTick(TickEvent.ClientTickEvent event) {
/*  45 */     Minecraft mc = Minecraft.func_71410_x();
/*     */     
/*  47 */     switch (event.phase) {
/*     */       case Left:
/*  49 */         if (mc.field_71439_g != null && 
/*  50 */           mc.field_71439_g.field_70170_p != null && 
/*  51 */           mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun && 
/*  52 */           (ClientRenderHooks.getAnimMachine((EntityLivingBase)mc.field_71439_g)).attachmentMode) {
/*  53 */           ItemStack gunStack = mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND);
/*  54 */           ItemGun gun = (ItemGun)gunStack.func_77973_b();
/*     */           
/*  56 */           if (gun.type.modelSkins != null && gun.type.acceptedAttachments != null && (
/*  57 */             !gun.type.acceptedAttachments.isEmpty() || gun.type.modelSkins.length > 1)) {
/*  58 */             List<AttachmentPresetEnum> keys = new ArrayList<>(gun.type.acceptedAttachments.keySet());
/*  59 */             if (gun.type.modelSkins.length > 1) {
/*  60 */               keys.add(AttachmentPresetEnum.Skin);
/*     */             }
/*  62 */             if (this.selectedAttachTypeIndex < keys.size() && this.selectedAttachTypeIndex >= 0) {
/*  63 */               this.selectedAttachEnum = keys.get(this.selectedAttachTypeIndex);
/*  64 */               List<Integer> slotsAttachments = checkAttach((EntityPlayer)mc.field_71439_g, gun.type, this.selectedAttachEnum);
/*     */               
/*  66 */               this.sizeAttachTypeIndex = keys.size();
/*  67 */               this.sizeAttachAttachIndex = slotsAttachments.size();
/*     */               
/*  69 */               if (this.selectedAttachIndex < slotsAttachments.size()) {
/*  70 */                 if (this.selectedAttachIndex != 0 && 
/*  71 */                   GunType.getAttachment(gunStack, this.selectedAttachEnum) != mc.field_71439_g.field_71071_by.func_70301_a(((Integer)slotsAttachments.get(this.selectedAttachIndex)).intValue())) {
/*  72 */                   ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunAddAttachment(((Integer)slotsAttachments.get(this.selectedAttachIndex)).intValue()));
/*  73 */                   this.selectedAttachIndex = 0;
/*     */                 } 
/*     */                 break;
/*     */               } 
/*  77 */               this.selectedAttachIndex = 0;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender(RenderGameOverlayEvent.Post event) {
/*  92 */     if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
/*  93 */       Minecraft mc = Minecraft.func_71410_x();
/*  94 */       if (!event.isCancelable()) {
/*  95 */         int width = event.getResolution().func_78326_a();
/*  96 */         int height = event.getResolution().func_78328_b();
/*     */         
/*  98 */         if (mc.field_71439_g.field_70170_p != null && 
/*  99 */           mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun && 
/* 100 */           (ClientRenderHooks.getAnimMachine((EntityLivingBase)mc.field_71439_g)).attachmentMode) {
/*     */           
/* 102 */           RenderHelperMW.renderCenteredText(TextFormatting.YELLOW + "[Attachment mode]", width / 2, height - 32, -1);
/* 103 */           if (this.selectedAttachEnum != null) {
/* 104 */             ItemStack gunStack = mc.field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND);
/* 105 */             ItemGun gun = (ItemGun)gunStack.func_77973_b();
/*     */             
/* 107 */             if (gun.type.modelSkins != null && gun.type.acceptedAttachments != null) {
/* 108 */               if (!gun.type.acceptedAttachments.isEmpty() || gun.type.modelSkins.length > 1) {
/* 109 */                 List<AttachmentPresetEnum> keys = new ArrayList<>(gun.type.acceptedAttachments.keySet());
/* 110 */                 if (gun.type.modelSkins.length > 1) {
/* 111 */                   keys.add(AttachmentPresetEnum.Skin);
/*     */                 }
/*     */                 
/* 114 */                 GlStateManager.func_179094_E();
/* 115 */                 GlStateManager.func_179109_b(0.0F, -18.0F, 0.0F);
/*     */                 
/* 117 */                 RenderHelperMW.renderCenteredText(firstArrowType(this.selectedAttachTypeIndex) + " " + this.selectedAttachEnum + " " + secondArrowType(this.selectedAttachTypeIndex, keys.size()), width / 2 - 50, height - 40, -1);
/*     */                 
/* 119 */                 RenderHelperMW.renderCenteredText("Change", width / 2 + 10, height - 40, -1);
/* 120 */                 RenderHelperMW.renderCenteredText("Unattach", width / 2 + 60, height - 40, -1);
/*     */ 
/*     */                 
/* 123 */                 GL11.glPushMatrix();
/* 124 */                 GL11.glTranslated((width / 2 + 10), (height - 42), 0.0D);
/* 125 */                 GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 126 */                 RenderHelperMW.renderCenteredText(firstArrowAttach(this.selectedAttachIndex, this.sizeAttachAttachIndex) + "[V]", 0, 0, -1);
/* 127 */                 GL11.glPopMatrix();
/*     */                 
/* 129 */                 TextFormatting color = TextFormatting.GRAY;
/* 130 */                 if (GunType.getAttachment(gunStack, this.selectedAttachEnum) != null) {
/* 131 */                   color = TextFormatting.GREEN;
/*     */                 }
/* 133 */                 RenderHelperMW.renderCenteredText(color + "[V]", width / 2 + 60, height - 30, -1);
/*     */                 
/* 135 */                 GlStateManager.func_179121_F();
/*     */               } else {
/* 137 */                 resetAttachmentMode();
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Integer> checkAttach(EntityPlayer player, GunType gunType, AttachmentPresetEnum attachmentEnum) {
/* 150 */     List<Integer> attachments = new ArrayList<>();
/* 151 */     attachments.add(Integer.valueOf(-1));
/* 152 */     for (int i = 0; i < player.field_71071_by.func_70302_i_(); i++) {
/* 153 */       ItemStack itemStack = player.field_71071_by.func_70301_a(i);
/* 154 */       if (attachmentEnum != AttachmentPresetEnum.Skin) {
/* 155 */         if (itemStack != null && itemStack.func_77973_b() instanceof ItemAttachment) {
/* 156 */           ItemAttachment itemAttachment = (ItemAttachment)itemStack.func_77973_b();
/* 157 */           AttachmentType attachType = itemAttachment.type;
/* 158 */           if (attachType.attachmentType == attachmentEnum && 
/* 159 */             gunType.acceptedAttachments.get(attachType.attachmentType) != null && ((ArrayList)gunType.acceptedAttachments.get(attachType.attachmentType)).size() >= 1 && (
/* 160 */             (ArrayList)gunType.acceptedAttachments.get(attachType.attachmentType)).contains(attachType.internalName)) {
/* 161 */             attachments.add(Integer.valueOf(i));
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 167 */       else if (itemStack != null && itemStack.func_77973_b() instanceof ItemSpray) {
/* 168 */         ItemSpray itemSpray = (ItemSpray)itemStack.func_77973_b();
/* 169 */         SprayType attachType = itemSpray.type;
/* 170 */         for (int j = 0; j < gunType.modelSkins.length; j++) {
/* 171 */           if ((gunType.modelSkins[j]).internalName.equalsIgnoreCase(attachType.skinName)) {
/* 172 */             attachments.add(Integer.valueOf(i));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     return attachments;
/*     */   }
/*     */   
/*     */   public void processKeyInput(KeyType type) {
/* 182 */     switch (type) {
/*     */       case Left:
/* 184 */         if (this.selectedAttachTypeIndex - 1 >= 0) {
/* 185 */           this.selectedAttachTypeIndex--;
/*     */         }
/*     */         break;
/*     */       case Right:
/* 189 */         if (this.selectedAttachTypeIndex + 1 < this.sizeAttachTypeIndex) {
/* 190 */           this.selectedAttachTypeIndex++;
/*     */         }
/*     */         break;
/*     */       case Down:
/* 194 */         if (this.selectedAttachIndex - 1 >= 0) {
/* 195 */           this.selectedAttachIndex--;
/*     */         }
/* 197 */         if (this.selectedAttachIndex == 0 && 
/* 198 */           this.selectedAttachEnum != null) {
/* 199 */           ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunUnloadAttachment(this.selectedAttachEnum.getName(), false));
/*     */         }
/*     */         break;
/*     */       
/*     */       case Up:
/* 204 */         if (this.selectedAttachIndex + 1 < this.sizeAttachAttachIndex) {
/* 205 */           this.selectedAttachIndex++;
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String firstArrowType(int selectedAttachTypeIndex) {
/* 213 */     if (selectedAttachTypeIndex > 0) {
/* 214 */       return TextFormatting.GREEN + "[<]" + TextFormatting.RESET;
/*     */     }
/* 216 */     return TextFormatting.GRAY + "[<]" + TextFormatting.RESET;
/*     */   }
/*     */   
/*     */   public String secondArrowType(int selectedAttachTypeIndex, int size) {
/* 220 */     if (selectedAttachTypeIndex == size - 1) {
/* 221 */       return TextFormatting.GRAY + "[>]" + TextFormatting.RESET;
/*     */     }
/* 223 */     return TextFormatting.GREEN + "[>]" + TextFormatting.RESET;
/*     */   }
/*     */   
/*     */   public String firstArrowAttach(int selectedAttachIndex, int size) {
/* 227 */     if (selectedAttachIndex == size - 1) {
/* 228 */       return TextFormatting.GRAY + "";
/*     */     }
/* 230 */     return TextFormatting.GREEN + "";
/*     */   }
/*     */   
/*     */   public String secondArrowAttach(int selectedAttachIndex, int size) {
/* 234 */     if (selectedAttachIndex > 0 && size <= 1) {
/* 235 */       return TextFormatting.GRAY + "";
/*     */     }
/* 237 */     return TextFormatting.GREEN + "";
/*     */   }
/*     */   
/*     */   public void resetAttachmentMode() {
/* 241 */     this.selectedAttachTypeIndex = 0;
/* 242 */     this.selectedAttachIndex = 0;
/*     */     
/* 244 */     this.sizeAttachTypeIndex = 0;
/* 245 */     this.sizeAttachAttachIndex = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\hud\AttachmentUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */