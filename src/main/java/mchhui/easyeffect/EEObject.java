/*     */ package mchhui.easyeffect;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EEObject
/*     */ {
/*  18 */   public static HashMap<String, ResourceLocation> glowTextrues = new HashMap<>();
/*  19 */   public static HashMap<String, ResourceLocation> textrues = new HashMap<>();
/*     */   
/*     */   public String name;
/*     */   
/*     */   public int fps;
/*     */   
/*     */   public int unit;
/*     */   
/*     */   private int unit2;
/*     */   
/*     */   public int length;
/*     */   
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public double vx;
/*     */   public double vy;
/*     */   public double vz;
/*     */   public double ax;
/*     */   public double ay;
/*     */   public double az;
/*     */   public double size;
/*     */   public long beginTime;
/*     */   
/*     */   public EEObject(String name, int delay, int fps, int unit, int length, double x, double y, double z, double vx, double vy, double vz, double ax, double ay, double az, double size, long beginTime) {
/*  44 */     this.name = name;
/*  45 */     this.fps = fps;
/*  46 */     this.unit = unit;
/*  47 */     this.unit2 = unit * unit;
/*  48 */     this.length = length;
/*  49 */     this.x = x;
/*  50 */     this.y = y;
/*  51 */     this.z = z;
/*  52 */     this.vx = vx;
/*  53 */     this.vy = vy;
/*  54 */     this.vz = vz;
/*  55 */     this.ax = ax;
/*  56 */     this.ay = ay;
/*  57 */     this.az = az;
/*  58 */     this.size = size;
/*  59 */     this.beginTime = beginTime + delay;
/*     */   }
/*     */   
/*     */   public void render(EasyEffectRenderer render, long time, float partialTicks) {
/*  63 */     float timeSec = (float)(time - this.beginTime) / 1000.0F;
/*  64 */     if (timeSec < 0.0F) {
/*     */       return;
/*     */     }
/*     */     
/*  68 */     GlStateManager.func_179094_E();
/*     */     
/*  70 */     float renderingX = (float)(this.x + this.vx * timeSec + 0.5D * this.ax * timeSec * timeSec);
/*  71 */     float renderingY = (float)(this.y + this.vy * timeSec + 0.5D * this.ay * timeSec * timeSec);
/*  72 */     float renderingZ = (float)(this.z + this.vz * timeSec + 0.5D * this.az * timeSec * timeSec);
/*     */     
/*  74 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  76 */     GlStateManager.func_179109_b(renderingX - render.viewEntityRenderingPosX, renderingY - render.viewEntityRenderingPosY, renderingZ - render.viewEntityRenderingPosZ);
/*  77 */     GlStateManager.func_179139_a(this.size, this.size, this.size);
/*  78 */     GlStateManager.func_179114_b(render.viewEntityRenderingYaw + 90.0F, 0.0F, -1.0F, 0.0F);
/*  79 */     GlStateManager.func_179114_b(render.viewEntityRenderingPitch, 0.0F, 0.0F, 1.0F);
/*     */     
/*  81 */     if (textrues.containsKey(this.name)) {
/*  82 */       (Minecraft.func_71410_x()).field_71446_o.func_110577_a(textrues.get(this.name));
/*     */     } else {
/*  84 */       textrues.put(this.name, new ResourceLocation(this.name));
/*  85 */       if ((Minecraft.func_71410_x()).field_71446_o.func_110581_b(new ResourceLocation(this.name.replaceFirst("(?s)[.](?!.*?[.])", "_e."))) != null) {
/*  86 */         glowTextrues.put(this.name, new ResourceLocation(this.name.replaceFirst("(?s)[.](?!.*?[.])", "_e.")));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  91 */     int frame = (int)(timeSec * this.fps);
/*  92 */     if (frame >= this.unit2) {
/*  93 */       frame = this.unit2 - 1;
/*     */     }
/*     */     
/*  96 */     float offsetX = (frame % this.unit);
/*  97 */     float offsetY = (frame / this.unit);
/*     */     
/*  99 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 100 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 101 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 102 */     bufferbuilder.func_181662_b(0.0D, 0.5D, 0.5D).func_187315_a(((0.0F + offsetX) / this.unit), ((0.0F + offsetY) / this.unit)).func_181675_d();
/* 103 */     bufferbuilder.func_181662_b(0.0D, -0.5D, 0.5D).func_187315_a(((0.0F + offsetX) / this.unit), ((1.0F + offsetY) / this.unit)).func_181675_d();
/* 104 */     bufferbuilder.func_181662_b(0.0D, -0.5D, -0.5D).func_187315_a(((1.0F + offsetX) / this.unit), ((1.0F + offsetY) / this.unit)).func_181675_d();
/* 105 */     bufferbuilder.func_181662_b(0.0D, 0.5D, -0.5D).func_187315_a(((1.0F + offsetX) / this.unit), ((0.0F + offsetY) / this.unit)).func_181675_d();
/* 106 */     tessellator.func_78381_a();
/*     */ 
/*     */     
/* 109 */     if (glowTextrues.containsKey(this.name)) {
/* 110 */       GlStateManager.func_179140_f();
/* 111 */       float lastBrightnessX = OpenGlHelper.lastBrightnessX;
/* 112 */       float lastBrightnessY = OpenGlHelper.lastBrightnessY;
/* 113 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/* 114 */       GlStateManager.func_179132_a(false);
/* 115 */       GlStateManager.func_179147_l();
/* 116 */       GlStateManager.func_179143_c(514);
/* 117 */       (Minecraft.func_71410_x()).field_71446_o.func_110577_a(glowTextrues.get(this.name));
/* 118 */       bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 119 */       bufferbuilder.func_181662_b(0.0D, 0.5D, 0.5D).func_187315_a(((0.0F + offsetX) / this.unit), ((0.0F + offsetY) / this.unit)).func_181675_d();
/* 120 */       bufferbuilder.func_181662_b(0.0D, -0.5D, 0.5D).func_187315_a(((0.0F + offsetX) / this.unit), ((1.0F + offsetY) / this.unit)).func_181675_d();
/* 121 */       bufferbuilder.func_181662_b(0.0D, -0.5D, -0.5D).func_187315_a(((1.0F + offsetX) / this.unit), ((1.0F + offsetY) / this.unit)).func_181675_d();
/* 122 */       bufferbuilder.func_181662_b(0.0D, 0.5D, -0.5D).func_187315_a(((1.0F + offsetX) / this.unit), ((0.0F + offsetY) / this.unit)).func_181675_d();
/* 123 */       tessellator.func_78381_a();
/* 124 */       GlStateManager.func_179143_c(515);
/* 125 */       GlStateManager.func_179084_k();
/* 126 */       GlStateManager.func_179132_a(true);
/* 127 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, lastBrightnessX, lastBrightnessY);
/* 128 */       GlStateManager.func_179145_e();
/*     */     } 
/*     */ 
/*     */     
/* 132 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public boolean isShutdown(long time) {
/* 136 */     return ((float)(time - this.beginTime) >= this.length / 20.0F * 1000.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\easyeffect\EEObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */