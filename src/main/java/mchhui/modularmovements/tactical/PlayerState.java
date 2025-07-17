/*     */ package mchhui.modularmovements.tactical;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import mchhui.modularmovements.ModularMovements;
/*     */ import mchhui.modularmovements.tactical.client.ClientLitener;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraftforge.fml.relauncher.ReflectionHelper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ public class PlayerState
/*     */ {
/*     */   public boolean isSliding = false;
/*     */   public boolean isSitting = false;
/*     */   public boolean isCrawling = false;
/*  21 */   public byte probe = 0;
/*  22 */   public float probeOffset = 0.0F;
/*  23 */   private long lastSyncTime = 0L;
/*     */   
/*     */   private long lastSit;
/*     */   
/*     */   private long lastCrawl;
/*     */   
/*     */   private long lastProbe;
/*     */   public AxisAlignedBB lastAABB;
/*     */   public AxisAlignedBB lastModAABB;
/*     */   
/*     */   public void updateOffset() {
/*  34 */     double amplifer = (System.currentTimeMillis() - this.lastSyncTime) * 0.06D;
/*  35 */     this.lastSyncTime = System.currentTimeMillis();
/*  36 */     if (this.probe == -1) {
/*  37 */       if (this.probeOffset > -1.0F) {
/*  38 */         this.probeOffset = (float)(this.probeOffset - 0.1D * amplifer);
/*     */       }
/*  40 */       if (this.probeOffset < -1.0F) {
/*  41 */         this.probeOffset = -1.0F;
/*     */       }
/*     */     } 
/*  44 */     if (this.probe == 1) {
/*  45 */       if (this.probeOffset < 1.0F) {
/*  46 */         this.probeOffset = (float)(this.probeOffset + 0.1D * amplifer);
/*     */       }
/*  48 */       if (this.probeOffset > 1.0F) {
/*  49 */         this.probeOffset = 1.0F;
/*     */       }
/*     */     } 
/*     */     
/*  53 */     if (this.probe == 0) {
/*  54 */       if (Math.abs(this.probeOffset) <= 0.1D * amplifer) {
/*  55 */         this.probeOffset = 0.0F;
/*     */       }
/*  57 */       if (this.probeOffset < 0.0F) {
/*  58 */         this.probeOffset = (float)(this.probeOffset + 0.1D * amplifer);
/*     */       }
/*  60 */       if (this.probeOffset > 0.0F) {
/*  61 */         this.probeOffset = (float)(this.probeOffset - 0.1D * amplifer);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isStanding() {
/*  67 */     return (!this.isCrawling && !this.isSitting);
/*     */   }
/*     */   
/*     */   public boolean canSit() {
/*  71 */     return (System.currentTimeMillis() - this.lastSit > TimeUnit.SECONDS.toMillis((long)ModularMovements.CONFIG.cooldown.sitCooldown));
/*     */   }
/*     */   
/*     */   public boolean canCrawl() {
/*  75 */     return (System.currentTimeMillis() - this.lastCrawl > TimeUnit.SECONDS.toMillis((long)ModularMovements.CONFIG.cooldown.crawlCooldown));
/*     */   }
/*     */   
/*     */   public boolean canProbe() {
/*  79 */     return (System.currentTimeMillis() - this.lastProbe > TimeUnit.SECONDS.toMillis((long)ModularMovements.CONFIG.cooldown.leanCooldown));
/*     */   }
/*     */   
/*     */   public void enableSit() {
/*  83 */     this.isSitting = true;
/*  84 */     disableCrawling();
/*  85 */     this.lastSit = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public void disableSit() {
/*  89 */     this.isSitting = false;
/*     */   }
/*     */   
/*     */   public void enableCrawling() {
/*  93 */     this.isCrawling = true;
/*  94 */     disableSit();
/*  95 */     this.lastCrawl = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public void disableCrawling() {
/*  99 */     this.isCrawling = false;
/*     */   }
/*     */   
/*     */   public void resetProbe() {
/* 103 */     this.probe = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void leftProbe() {
/* 108 */     this.probe = -1;
/* 109 */     this.lastProbe = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public void rightProbe() {
/* 114 */     this.probe = 1;
/* 115 */     this.lastProbe = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readCode(int code) {
/* 120 */     this.probe = (byte)(code % 10 - 1);
/* 121 */     code /= 10;
/* 122 */     this.isCrawling = (code % 10 != 0);
/* 123 */     code /= 10;
/* 124 */     this.isSitting = (code % 10 != 0);
/* 125 */     code /= 10;
/* 126 */     this.isSliding = (code % 10 != 0);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 130 */     readCode(1);
/*     */   }
/*     */   
/*     */   public int writeCode() {
/* 134 */     return (this.isSliding ? 1 : 0) * 1000 + (this.isSitting ? 1 : 0) * 100 + (this.isCrawling ? 1 : 0) * 10 + this.probe + 1;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static class ClientPlayerState extends PlayerState {
/*     */     private static Field equippedProgressMainHandField;
/*     */     
/*     */     public ClientPlayerState() {
/* 142 */       if (equippedProgressMainHandField == null) {
/* 143 */         equippedProgressMainHandField = ReflectionHelper.findField(ItemRenderer.class, "equippedProgressMainHand", "field_187469_f");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void updateEquippedItem() {
/*     */       try {
/* 150 */         equippedProgressMainHandField.set(Minecraft.func_71410_x().func_175597_ag(), Float.valueOf(-0.4F));
/* 151 */       } catch (IllegalArgumentException|IllegalAccessException e) {
/*     */         
/* 153 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void enableCrawling() {
/* 159 */       super.enableCrawling();
/* 160 */       ClientLitener.crawlingMousePosXMove = 0;
/*     */     }
/*     */     
/*     */     public void resetProbe() {
/* 164 */       super.resetProbe();
/* 165 */       if ((Minecraft.func_71410_x()).field_71439_g.func_184591_cq() != (Minecraft.func_71410_x()).field_71474_y.field_186715_A) {
/* 166 */         (Minecraft.func_71410_x()).field_71439_g.func_184819_a((Minecraft.func_71410_x()).field_71474_y.field_186715_A);
/* 167 */         updateEquippedItem();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leftProbe() {
/* 172 */       super.leftProbe();
/* 173 */       if ((Minecraft.func_71410_x()).field_71439_g.func_184591_cq() != EnumHandSide.LEFT) {
/* 174 */         (Minecraft.func_71410_x()).field_71439_g.func_184819_a(EnumHandSide.LEFT);
/* 175 */         updateEquippedItem();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void rightProbe() {
/* 180 */       super.rightProbe();
/* 181 */       if ((Minecraft.func_71410_x()).field_71439_g.func_184591_cq() != EnumHandSide.RIGHT) {
/* 182 */         (Minecraft.func_71410_x()).field_71439_g.func_184819_a(EnumHandSide.RIGHT);
/* 183 */         updateEquippedItem();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\tactical\PlayerState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */