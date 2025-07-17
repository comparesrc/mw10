/*    */ package de.javagl.jgltf.model.gl;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface TechniqueStatesModel
/*    */ {
/*    */   List<Integer> getEnable();
/*    */   
/*    */   TechniqueStatesFunctionsModel getTechniqueStatesFunctionsModel();
/*    */   
/*    */   static List<Integer> getAllStates() {
/* 65 */     List<Integer> allStates = Arrays.asList(new Integer[] {
/* 66 */           Integer.valueOf(3042), 
/* 67 */           Integer.valueOf(2884), 
/* 68 */           Integer.valueOf(2929), 
/* 69 */           Integer.valueOf(32823), 
/* 70 */           Integer.valueOf(32926), 
/* 71 */           Integer.valueOf(3089)
/*    */         });
/* 73 */     return allStates;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\TechniqueStatesModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */