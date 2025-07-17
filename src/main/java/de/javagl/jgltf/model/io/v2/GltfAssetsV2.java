/*    */ package de.javagl.jgltf.model.io.v2;
/*    */ 
/*    */ import de.javagl.jgltf.model.GltfModel;
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
/*    */ public class GltfAssetsV2
/*    */ {
/*    */   public static GltfAssetV2 createDefault(GltfModel gltfModel) {
/* 48 */     DefaultAssetCreatorV2 assetCreator = new DefaultAssetCreatorV2();
/* 49 */     GltfAssetV2 gltfAsset = assetCreator.create(gltfModel);
/* 50 */     return gltfAsset;
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
/*    */   public static GltfAssetV2 createBinary(GltfModel gltfModel) {
/* 62 */     BinaryAssetCreatorV2 assetCreator = new BinaryAssetCreatorV2();
/* 63 */     GltfAssetV2 gltfAsset = assetCreator.create(gltfModel);
/* 64 */     return gltfAsset;
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
/*    */   public static GltfAssetV2 createEmbedded(GltfModel gltfModel) {
/* 76 */     EmbeddedAssetCreatorV2 assetCreator = new EmbeddedAssetCreatorV2();
/* 77 */     GltfAssetV2 gltfAsset = assetCreator.create(gltfModel);
/* 78 */     return gltfAsset;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v2\GltfAssetsV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */