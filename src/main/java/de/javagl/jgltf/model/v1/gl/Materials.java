/*    */ package de.javagl.jgltf.model.v1.gl;
/*    */ 
/*    */ import de.javagl.jgltf.impl.v1.Material;
/*    */ import java.util.Arrays;
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
/*    */ 
/*    */ 
/*    */ class Materials
/*    */ {
/*    */   static Material createDefaultMaterial(String techniqueId) {
/* 52 */     Material material = new Material();
/* 53 */     material.addValues("emission", Arrays.asList(new Double[] { Double.valueOf(0.5D), Double.valueOf(0.5D), Double.valueOf(0.5D), Double.valueOf(1.0D) }));
/* 54 */     material.setTechnique(techniqueId);
/* 55 */     return material;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\gl\Materials.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */