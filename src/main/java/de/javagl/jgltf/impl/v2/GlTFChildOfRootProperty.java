/*    */ package de.javagl.jgltf.impl.v2;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GlTFChildOfRootProperty
/*    */   extends GlTFProperty
/*    */ {
/*    */   private String name;
/*    */   
/*    */   public void setName(String name) {
/* 34 */     if (name == null) {
/* 35 */       this.name = name;
/*    */       return;
/*    */     } 
/* 38 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 48 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\GlTFChildOfRootProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */