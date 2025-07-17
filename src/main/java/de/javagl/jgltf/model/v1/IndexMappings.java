/*    */ package de.javagl.jgltf.model.v1;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
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
/*    */ class IndexMappings
/*    */ {
/*    */   static Map<String, Integer> computeIndexMapping(Map<String, ?> map) {
/* 50 */     if (map == null)
/*    */     {
/* 52 */       return Collections.emptyMap();
/*    */     }
/* 54 */     Map<String, Integer> indexMapping = new LinkedHashMap<>();
/*    */     
/* 56 */     int indexCounter = 0;
/* 57 */     for (String key : map.keySet()) {
/*    */       
/* 59 */       indexMapping.put(key, Integer.valueOf(indexCounter));
/* 60 */       indexCounter++;
/*    */     } 
/* 62 */     return indexMapping;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\IndexMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */