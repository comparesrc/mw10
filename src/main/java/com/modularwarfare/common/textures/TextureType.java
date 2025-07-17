/*    */ package com.modularwarfare.common.textures;
/*    */ 
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class TextureType
/*    */   extends BaseType
/*    */ {
/* 12 */   public TextureEnumType textureType = TextureEnumType.Flash;
/*    */   
/*    */   public String[] imageLocation;
/* 15 */   public transient List<ResourceLocation> resourceLocations = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public void loadExtraValues() {
/* 19 */     if (this.imageLocation != null && this.imageLocation.length > 0) {
/* 20 */       for (int i = 0; i < this.imageLocation.length; i++) {
/* 21 */         this.resourceLocations.add(new ResourceLocation("modularwarfare", "textures/" + this.textureType.typeName + "/" + this.imageLocation[i]));
/*    */       }
/*    */     } else {
/* 24 */       initDefaultTextures(this.textureType);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void initDefaultTextures(TextureEnumType type) {
/*    */     int i;
/* 30 */     this.textureType = type;
/* 31 */     switch (type) {
/*    */       case Flash:
/* 33 */         for (i = 0; i < 5; i++) {
/* 34 */           this.resourceLocations.add(new ResourceLocation("modularwarfare", "textures/default/flash/mw.flash" + (i + 1) + ".png"));
/*    */         }
/*    */         break;
/*    */       case Overlay:
/* 38 */         this.resourceLocations.add(new ResourceLocation("modularwarfare", "textures/default/overlay/mw.scope4x.png"));
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reloadModel() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getAssetDir() {
/* 52 */     return "textures";
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\textures\TextureType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */