/*    */ package de.javagl.jgltf.model;
/*    */ 
/*    */ import de.javagl.jgltf.model.io.GltfAsset;
/*    */ import de.javagl.jgltf.model.io.v1.GltfAssetV1;
/*    */ import de.javagl.jgltf.model.io.v2.GltfAssetV2;
/*    */ import de.javagl.jgltf.model.v1.GltfModelV1;
/*    */ import de.javagl.jgltf.model.v2.GltfModelCreatorV2;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GltfModels
/*    */ {
/*    */   public static GltfModel create(GltfAsset gltfAsset) {
/* 50 */     if (gltfAsset instanceof GltfAssetV1) {
/*    */       
/* 52 */       GltfAssetV1 gltfAssetV1 = (GltfAssetV1)gltfAsset;
/* 53 */       return (GltfModel)new GltfModelV1(gltfAssetV1);
/*    */     } 
/* 55 */     if (gltfAsset instanceof GltfAssetV2) {
/*    */       
/* 57 */       GltfAssetV2 gltfAssetV2 = (GltfAssetV2)gltfAsset;
/* 58 */       return (GltfModel)GltfModelCreatorV2.create(gltfAssetV2);
/*    */     } 
/* 60 */     throw new IllegalArgumentException("The glTF asset has an unknown version: " + gltfAsset);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\GltfModels.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */