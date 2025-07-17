/*    */ package de.javagl.jgltf.model.gl.impl;
/*    */ 
/*    */ import de.javagl.jgltf.impl.v1.TechniqueStatesFunctions;
/*    */ import de.javagl.jgltf.model.gl.TechniqueStatesFunctionsModel;
/*    */ import de.javagl.jgltf.model.gl.TechniqueStatesModel;
/*    */ import de.javagl.jgltf.model.v1.gl.TechniqueStatesFunctionsModels;
/*    */ import de.javagl.jgltf.model.v1.gl.Techniques;
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
/*    */ public class TechniqueStatesModels
/*    */ {
/*    */   public static TechniqueStatesModel createDefault() {
/* 53 */     TechniqueStatesModel techniqueStatesModel = new DefaultTechniqueStatesModel(createDefaultTechniqueStatesEnable(), createDefaultTechniqueStatesFunctions());
/* 54 */     return techniqueStatesModel;
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
/*    */   public static TechniqueStatesFunctionsModel createDefaultTechniqueStatesFunctions() {
/* 66 */     TechniqueStatesFunctions functions = Techniques.createDefaultTechniqueStatesFunctions();
/*    */     
/* 68 */     TechniqueStatesFunctionsModel techniqueStatesFunctionsModel = TechniqueStatesFunctionsModels.create(functions);
/* 69 */     return techniqueStatesFunctionsModel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static List<Integer> createDefaultTechniqueStatesEnable() {
/* 80 */     List<Integer> enable = Arrays.asList(new Integer[] {
/* 81 */           Integer.valueOf(2929), 
/* 82 */           Integer.valueOf(2884)
/*    */         });
/* 84 */     return enable;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\impl\TechniqueStatesModels.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */