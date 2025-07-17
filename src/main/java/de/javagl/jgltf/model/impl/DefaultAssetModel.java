/*    */ package de.javagl.jgltf.model.impl;
/*    */ 
/*    */ import de.javagl.jgltf.model.AssetModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultAssetModel
/*    */   extends AbstractNamedModelElement
/*    */   implements AssetModel
/*    */ {
/*    */   private String copyright;
/*    */   private String generator;
/*    */   
/*    */   public void setCopyright(String copyright) {
/* 54 */     this.copyright = copyright;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCopyright() {
/* 60 */     return this.copyright;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setGenerator(String generator) {
/* 70 */     this.generator = generator;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getGenerator() {
/* 76 */     return this.generator;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultAssetModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */