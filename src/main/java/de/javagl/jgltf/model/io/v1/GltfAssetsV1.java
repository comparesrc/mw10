/*    */ package de.javagl.jgltf.model.io.v1;
/*    */ 
/*    */ import de.javagl.jgltf.model.v1.GltfModelV1;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GltfAssetsV1
/*    */ {
/*    */   public static GltfAssetV1 createDefault(GltfModelV1 gltfModel) {
/* 49 */     DefaultAssetCreatorV1 assetCreator = new DefaultAssetCreatorV1();
/* 50 */     GltfAssetV1 gltfAsset = assetCreator.create(gltfModel);
/* 51 */     return gltfAsset;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static GltfAssetV1 createBinary(GltfModelV1 gltfModel) {
/* 63 */     BinaryAssetCreatorV1 assetCreator = new BinaryAssetCreatorV1();
/* 64 */     GltfAssetV1 gltfAsset = assetCreator.create(gltfModel);
/* 65 */     return gltfAsset;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static GltfAssetV1 createEmbedded(GltfModelV1 gltfModel) {
/* 77 */     EmbeddedAssetCreatorV1 assetCreator = new EmbeddedAssetCreatorV1();
/* 78 */     GltfAssetV1 gltfAsset = assetCreator.create(gltfModel);
/* 79 */     return gltfAsset;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v1\GltfAssetsV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */