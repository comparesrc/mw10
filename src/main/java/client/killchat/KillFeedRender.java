/*    */ package com.modularwarfare.client.killchat;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.utility.RenderHelperMW;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class KillFeedRender {
/*    */   private KillFeedManager manager;
/*    */   private Minecraft mc;
/*    */   
/*    */   public KillFeedRender(KillFeedManager manager) {
/* 24 */     this.mc = Minecraft.func_71410_x();
/* 25 */     this.manager = manager;
/*    */   }
/*    */   
/*    */   public static int calculateChatboxHeight(float scale) {
/* 29 */     return MathHelper.func_76141_d(scale * 160.0F + 20.0F);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void onTick(TickEvent.ClientTickEvent event) {
/* 35 */     if (event.phase == TickEvent.Phase.START) {
/* 36 */       this.manager.getEntries().stream().filter(entry -> (entry.incrementTimeLived() > entry.getTimeLiving())).forEach(entry -> this.manager.remove(entry));
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void onRender(RenderGameOverlayEvent.Post event) {
/* 43 */     if (this.mc.field_71456_v.func_146158_b().func_146241_e()) {
/*    */       return;
/*    */     }
/* 46 */     if (this.manager.getEntries().isEmpty()) {
/*    */       return;
/*    */     }
/* 49 */     GlStateManager.func_179094_E();
/* 50 */     float scale = 1.0F;
/* 51 */     GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
/* 52 */     List<KillFeedEntry> entries = (List<KillFeedEntry>)this.manager.getEntries().stream().sorted(Comparator.comparingInt(KillFeedEntry::getTimeLived).reversed()).collect(Collectors.toList());
/* 53 */     int chatHeight = calculateChatboxHeight(this.mc.field_71474_y.field_96694_H);
/* 54 */     int bottom = event.getResolution().func_78328_b() - chatHeight - 10;
/* 55 */     int left = 5;
/* 56 */     int messagesHeight = entries.size() * this.mc.field_71466_p.field_78288_b + entries.size();
/* 57 */     bottom -= messagesHeight;
/* 58 */     int messagesWidth = 0;
/* 59 */     for (KillFeedEntry entry : entries) {
/* 60 */       messagesWidth = (int)Math.max(messagesWidth, this.mc.field_71466_p.func_78256_a(entry.getText()) * 1.0F);
/*    */     }
/*    */     
/* 63 */     int msgY = bottom;
/* 64 */     for (KillFeedEntry entry2 : entries) {
/* 65 */       this.mc.field_71466_p.func_78276_b(entry2.getText(), 17, (int)(msgY / 1.0F), 2147483647);
/* 66 */       msgY += this.mc.field_71466_p.field_78288_b + 1;
/*    */       
/* 68 */       if (entry2.isCausedByGun()) {
/* 69 */         GlStateManager.func_179094_E();
/* 70 */         float scaleGun = 0.6F;
/* 71 */         GlStateManager.func_179152_a(0.6F, 0.6F, 0.6F);
/* 72 */         RenderHelperMW.renderItemStack(new ItemStack((Item)ModularWarfare.gunTypes.get(entry2.getWeaponInternalName())), 8, (int)(msgY / 0.6F) - 17, 0.0F, true);
/* 73 */         GlStateManager.func_179121_F();
/*    */       } 
/*    */     } 
/* 76 */     GlStateManager.func_179121_F();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\killchat\KillFeedRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */