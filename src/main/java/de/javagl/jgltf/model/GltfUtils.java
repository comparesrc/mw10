/*    */ package de.javagl.jgltf.model;
/*    */ 
/*    */ import de.javagl.jgltf.impl.v1.Asset;
/*    */ import de.javagl.jgltf.impl.v1.GlTF;
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
/*    */ public class GltfUtils
/*    */ {
/*    */   public static String getVersion(GlTF gltf) {
/* 50 */     Objects.requireNonNull(gltf, "The gltf is null");
/* 51 */     Asset asset = gltf.getAsset();
/* 52 */     if (asset == null)
/*    */     {
/* 54 */       return "1.0";
/*    */     }
/* 56 */     String version = asset.getVersion();
/* 57 */     if (version == null)
/*    */     {
/* 59 */       return "1.0";
/*    */     }
/* 61 */     return version;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\GltfUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */