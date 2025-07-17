/*    */ package de.javagl.jgltf.model.gl.impl;
/*    */ 
/*    */ import de.javagl.jgltf.model.gl.TechniqueStatesFunctionsModel;
/*    */ import de.javagl.jgltf.model.gl.TechniqueStatesModel;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
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
/*    */ public class DefaultTechniqueStatesModel
/*    */   implements TechniqueStatesModel
/*    */ {
/*    */   private final List<Integer> enable;
/*    */   private final TechniqueStatesFunctionsModel techniqueStatesFunctionsModel;
/*    */   
/*    */   public DefaultTechniqueStatesModel(List<Integer> enable, TechniqueStatesFunctionsModel techniqueStatesFunctionsModel) {
/* 61 */     this.enable = Collections.unmodifiableList(new ArrayList<>(enable));
/*    */     
/* 63 */     this.techniqueStatesFunctionsModel = techniqueStatesFunctionsModel;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Integer> getEnable() {
/* 69 */     return this.enable;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TechniqueStatesFunctionsModel getTechniqueStatesFunctionsModel() {
/* 75 */     return this.techniqueStatesFunctionsModel;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\impl\DefaultTechniqueStatesModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */