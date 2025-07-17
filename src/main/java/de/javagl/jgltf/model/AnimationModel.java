/*    */ package de.javagl.jgltf.model;
/*    */ 
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
/*    */ public interface AnimationModel
/*    */   extends NamedModelElement
/*    */ {
/*    */   List<Channel> getChannels();
/*    */   
/*    */   public static interface Sampler
/*    */   {
/*    */     AccessorModel getInput();
/*    */     
/*    */     AnimationModel.Interpolation getInterpolation();
/*    */     
/*    */     AccessorModel getOutput();
/*    */   }
/*    */   
/*    */   public static interface Channel
/*    */   {
/*    */     AnimationModel.Sampler getSampler();
/*    */     
/*    */     NodeModel getNodeModel();
/*    */     
/*    */     String getPath();
/*    */   }
/*    */   
/*    */   public enum Interpolation
/*    */   {
/* 44 */     STEP,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     LINEAR,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 54 */     CUBICSPLINE;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\AnimationModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */