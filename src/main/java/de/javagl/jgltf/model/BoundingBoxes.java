/*    */ package de.javagl.jgltf.model;
/*    */ 
/*    */ import java.util.Objects;
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
/*    */ public class BoundingBoxes
/*    */ {
/*    */   public static float[] computeBoundingBoxMinMax(GltfModel gltfModel) {
/* 48 */     Objects.requireNonNull(gltfModel, "The gltfModel may not be null");
/*    */     
/* 50 */     BoundingBoxComputer boundingBoxComputer = new BoundingBoxComputer(gltfModel);
/*    */     
/* 52 */     BoundingBox boundingBox = boundingBoxComputer.compute();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 60 */     float[] result = { boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMinZ(), boundingBox.getMaxX(), boundingBox.getMaxY(), boundingBox.getMaxZ() };
/*    */     
/* 62 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\BoundingBoxes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */