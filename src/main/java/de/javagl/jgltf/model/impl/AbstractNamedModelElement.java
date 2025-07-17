/*    */ package de.javagl.jgltf.model.impl;
/*    */ 
/*    */ import de.javagl.jgltf.model.NamedModelElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AbstractNamedModelElement
/*    */   extends AbstractModelElement
/*    */   implements NamedModelElement
/*    */ {
/*    */   private String name;
/*    */   
/*    */   public String getName() {
/* 45 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setName(String name) {
/* 56 */     this.name = name;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\AbstractNamedModelElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */