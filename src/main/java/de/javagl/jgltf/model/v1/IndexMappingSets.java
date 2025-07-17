/*    */ package de.javagl.jgltf.model.v1;
/*    */ 
/*    */ import de.javagl.jgltf.impl.v1.GlTF;
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
/*    */ class IndexMappingSets
/*    */ {
/*    */   static IndexMappingSet create(GlTF gltf) {
/* 46 */     IndexMappingSet indexMappingSet = new IndexMappingSet();
/* 47 */     indexMappingSet.generate("accessors", gltf.getAccessors());
/* 48 */     indexMappingSet.generate("animations", gltf.getAnimations());
/* 49 */     indexMappingSet.generate("buffers", gltf.getBuffers());
/* 50 */     indexMappingSet.generate("bufferViews", gltf.getBufferViews());
/* 51 */     indexMappingSet.generate("cameras", gltf.getCameras());
/* 52 */     indexMappingSet.generate("images", gltf.getImages());
/* 53 */     indexMappingSet.generate("materials", gltf.getMaterials());
/* 54 */     indexMappingSet.generate("meshes", gltf.getMeshes());
/* 55 */     indexMappingSet.generate("nodes", gltf.getNodes());
/* 56 */     indexMappingSet.generate("programs", gltf.getPrograms());
/* 57 */     indexMappingSet.generate("samplers", gltf.getSamplers());
/* 58 */     indexMappingSet.generate("scenes", gltf.getScenes());
/* 59 */     indexMappingSet.generate("shaders", gltf.getShaders());
/* 60 */     indexMappingSet.generate("skins", gltf.getSkins());
/* 61 */     indexMappingSet.generate("techniques", gltf.getTechniques());
/* 62 */     indexMappingSet.generate("textures", gltf.getTextures());
/* 63 */     return indexMappingSet;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\IndexMappingSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */