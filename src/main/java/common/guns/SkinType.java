/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SkinType
/*    */ {
/*    */   public String internalName;
/*    */   public String displayName;
/*    */   public String skinAsset;
/* 13 */   public Sampling sampling = Sampling.FLAT;
/*    */   
/*    */   public enum Sampling {
/* 16 */     FLAT,
/* 17 */     LINEAR;
/*    */   }
/*    */   
/* 20 */   public Texture[] textures = new Texture[0];
/*    */   
/*    */   public enum Texture {
/* 23 */     BASIC("skins/%s/%s.png"),
/* 24 */     GLOW("skins/%s/%s_glow.png"),
/* 25 */     SPECULAR("skins/%s/%s_s.png"),
/* 26 */     NORMAL("skins/%s/%s_n.png");
/*    */     
/*    */     public String format;
/*    */     
/*    */     Texture(String format) {
/* 31 */       this.format = format;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SkinType getDefaultSkin(BaseType baseType) {
/* 43 */     SkinType skinType = new SkinType();
/* 44 */     skinType.internalName = baseType.internalName;
/* 45 */     skinType.skinAsset = skinType.getSkin();
/* 46 */     skinType.displayName = baseType.displayName + " - Default";
/* 47 */     return skinType;
/*    */   }
/*    */   
/*    */   public String getSkin() {
/* 51 */     return (this.skinAsset != null) ? this.skinAsset : this.internalName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return this.skinAsset;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\SkinType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */