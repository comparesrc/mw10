/*    */ package de.javagl.jgltf.model.v1;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GltfIds
/*    */ {
/*    */   public static String generateId(String prefix, Map<? extends String, ?> map) {
/* 51 */     Set<? extends String> set = Collections.emptySet();
/* 52 */     if (map != null)
/*    */     {
/* 54 */       set = map.keySet();
/*    */     }
/* 56 */     return generateId(prefix, set);
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
/*    */   public static String generateId(String prefix, Set<? extends String> set) {
/* 70 */     Set<? extends String> localSet = Collections.emptySet();
/* 71 */     if (set != null)
/*    */     {
/* 73 */       localSet = set;
/*    */     }
/* 75 */     int counter = localSet.size();
/*    */     
/*    */     while (true) {
/* 78 */       String id = prefix + counter;
/* 79 */       if (!localSet.contains(id))
/*    */       {
/* 81 */         return id;
/*    */       }
/* 83 */       counter++;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\GltfIds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */