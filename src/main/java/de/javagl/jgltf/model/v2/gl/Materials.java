/*    */ package de.javagl.jgltf.model.v2.gl;
/*    */ 
/*    */ import de.javagl.jgltf.impl.v2.Material;
/*    */ import de.javagl.jgltf.impl.v2.MaterialPbrMetallicRoughness;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Materials
/*    */ {
/*    */   public static Material createDefaultMaterial() {
/* 44 */     Material material = new Material();
/* 45 */     material.setPbrMetallicRoughness(
/* 46 */         createDefaultMaterialPbrMetallicRoughness());
/* 47 */     material.setNormalTexture(null);
/* 48 */     material.setOcclusionTexture(null);
/* 49 */     material.setEmissiveTexture(null);
/* 50 */     material.setEmissiveFactor(material.defaultEmissiveFactor());
/* 51 */     material.setAlphaMode(material.defaultAlphaMode());
/* 52 */     material.setAlphaCutoff(material.defaultAlphaCutoff());
/* 53 */     material.setDoubleSided(material.defaultDoubleSided());
/* 54 */     return material;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MaterialPbrMetallicRoughness createDefaultMaterialPbrMetallicRoughness() {
/* 65 */     MaterialPbrMetallicRoughness result = new MaterialPbrMetallicRoughness();
/*    */     
/* 67 */     result.setBaseColorFactor(result.defaultBaseColorFactor());
/* 68 */     result.setMetallicFactor(result.defaultMetallicFactor());
/* 69 */     result.setRoughnessFactor(result.defaultRoughnessFactor());
/* 70 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v2\gl\Materials.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */