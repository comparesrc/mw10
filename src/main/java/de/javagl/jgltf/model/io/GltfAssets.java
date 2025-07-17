/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GltfAssets
/*     */ {
/*     */   public static boolean isDefault(GltfAsset gltfAsset) {
/*  60 */     ByteBuffer binaryData = gltfAsset.getBinaryData();
/*  61 */     if (binaryData != null && binaryData.capacity() > 0)
/*     */     {
/*  63 */       return false;
/*     */     }
/*  65 */     if (containsDataUriReferences(gltfAsset))
/*     */     {
/*  67 */       return false;
/*     */     }
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEmbedded(GltfAsset gltfAsset) {
/*  83 */     ByteBuffer binaryData = gltfAsset.getBinaryData();
/*  84 */     if (binaryData != null && binaryData.capacity() > 0)
/*     */     {
/*  86 */       return false;
/*     */     }
/*  88 */     if (containsFileUriReferences(gltfAsset))
/*     */     {
/*  90 */       return false;
/*     */     }
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBinary(GltfAsset gltfAsset) {
/* 108 */     if (containsFileUriReferences(gltfAsset))
/*     */     {
/* 110 */       return false;
/*     */     }
/* 112 */     if (containsDataUriReferences(gltfAsset))
/*     */     {
/* 114 */       return false;
/*     */     }
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean containsFileUriReferences(GltfAsset gltfAsset) {
/* 131 */     List<GltfReference> references = gltfAsset.getReferences();
/* 132 */     for (GltfReference reference : references) {
/*     */       
/* 134 */       String uriString = reference.getUri();
/* 135 */       if (!IO.isDataUriString(uriString))
/*     */       {
/* 137 */         return true;
/*     */       }
/*     */     } 
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean containsDataUriReferences(GltfAsset gltfAsset) {
/* 155 */     List<GltfReference> references = gltfAsset.getReferences();
/* 156 */     for (GltfReference reference : references) {
/*     */       
/* 158 */       String uriString = reference.getUri();
/* 159 */       if (!IO.isDataUriString(uriString))
/*     */       {
/* 161 */         return true;
/*     */       }
/*     */     } 
/* 164 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\GltfAssets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */