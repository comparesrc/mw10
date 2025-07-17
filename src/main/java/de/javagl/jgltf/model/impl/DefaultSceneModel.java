/*    */ package de.javagl.jgltf.model.impl;
/*    */ 
/*    */ import de.javagl.jgltf.model.NodeModel;
/*    */ import de.javagl.jgltf.model.SceneModel;
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
/*    */ public class DefaultSceneModel
/*    */   extends AbstractNamedModelElement
/*    */   implements SceneModel
/*    */ {
/* 52 */   private final List<NodeModel> nodeModels = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addNode(NodeModel node) {
/* 62 */     this.nodeModels.add(node);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<NodeModel> getNodeModels() {
/* 68 */     return Collections.unmodifiableList(this.nodeModels);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultSceneModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */