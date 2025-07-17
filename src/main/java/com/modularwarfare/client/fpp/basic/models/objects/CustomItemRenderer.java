/*    */ package com.modularwarfare.client.fpp.basic.models.objects;
/*    */ 
/*    */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomItemRenderer
/*    */ {
/* 28 */   public static ResourceLocation NULL_TEX = new ResourceLocation("modularwarfare", "textures/nulltex.png");
/* 29 */   protected static TextureManager renderEngine = (Minecraft.func_71410_x()).field_71446_o;
/* 30 */   private HashMap<String, ResourceLocation> cachedSkins = new HashMap<>();
/* 31 */   private ArrayList<String> cachedBadSkins = new ArrayList<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceLocation bindingTexture;
/*    */ 
/*    */   
/* 38 */   public float r = 1.0F;
/* 39 */   public float g = 1.0F;
/* 40 */   public float b = 1.0F;
/* 41 */   public float a = 1.0F;
/*    */ 
/*    */   
/*    */   public void renderItem(CustomItemRenderType type, EnumHand hand, ItemStack item, Object... data) {}
/*    */   
/*    */   public void bindTexture(String type, String fileName) {
/* 47 */     ObjModelRenderer.glowType = type;
/* 48 */     ObjModelRenderer.glowPath = fileName;
/* 49 */     bindTexture(type, fileName, false, true);
/*    */   }
/*    */   
/*    */   public boolean bindTextureGlow(String type, String fileName) {
/* 53 */     if (this.cachedBadSkins.contains(type + "_" + fileName + "_glow")) {
/* 54 */       return false;
/*    */     }
/* 56 */     bindTexture(type, fileName + "_glow", true, false);
/* 57 */     return true;
/*    */   }
/*    */   
/*    */   public void bindTexture(String type, String fileName, boolean saveBad, boolean printException) {
/* 61 */     String pathFormat = "skins/%s/%s.png";
/*    */     
/* 63 */     if (renderEngine == null)
/* 64 */       renderEngine = (Minecraft.func_71410_x()).field_71446_o; 
/* 65 */     if (this.cachedBadSkins.contains(type + "_" + fileName)) {
/* 66 */       renderEngine.func_110577_a(NULL_TEX);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/*    */     try {
/* 72 */       ResourceLocation resourceLocation = new ResourceLocation("modularwarfare", String.format(pathFormat, new Object[] { type, fileName }));
/* 73 */       if (this.cachedSkins.containsKey(type + "_" + fileName)) {
/* 74 */         renderEngine.func_110577_a(this.cachedSkins.get(type + "_" + fileName)); return;
/*    */       } 
/* 76 */       if (renderEngine.func_110581_b(resourceLocation) == null) {
/* 77 */         SimpleTexture simpleTexture = new SimpleTexture(resourceLocation);
/* 78 */         simpleTexture.func_110551_a(Minecraft.func_71410_x().func_110442_L());
/*    */       } 
/*    */       
/* 81 */       renderEngine.func_110577_a(resourceLocation);
/* 82 */     } catch (Exception e) {
/*    */       
/* 84 */       ResourceLocation resourceLocation = new ResourceLocation("modularwarfare", String.format(pathFormat, new Object[] { "default", type, fileName }));
/* 85 */       this.cachedSkins.put(type + "_" + fileName, resourceLocation);
/* 86 */       if (saveBad) {
/* 87 */         this.cachedBadSkins.add(type + "_" + fileName);
/*    */       }
/* 89 */       if (printException)
/* 90 */         e.printStackTrace(); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\models\objects\CustomItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */