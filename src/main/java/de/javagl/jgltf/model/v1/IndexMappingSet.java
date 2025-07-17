/*    */ package de.javagl.jgltf.model.v1;
/*    */ 
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
/*    */ final class IndexMappingSet
/*    */ {
/* 48 */   private final Map<Object, Map<String, Integer>> indexMappings = new LinkedHashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Map<String, Integer> get(Object name) {
/* 60 */     Map<String, Integer> indexMapping = this.indexMappings.computeIfAbsent(name, n -> new LinkedHashMap<>());
/*    */     
/* 62 */     return indexMapping;
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
/*    */   
/*    */   void generate(Object name, Map<String, ?> map) {
/* 75 */     if (map != null)
/*    */     {
/* 77 */       get(name).putAll(IndexMappings.computeIndexMapping(map));
/*    */     }
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
/*    */ 
/*    */   
/*    */   Integer getIndex(String name, String key) {
/* 92 */     if (key == null)
/*    */     {
/* 94 */       return null;
/*    */     }
/* 96 */     return get(name).get(key);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\IndexMappingSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */