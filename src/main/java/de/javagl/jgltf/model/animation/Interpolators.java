/*    */ package de.javagl.jgltf.model.animation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Interpolators
/*    */ {
/*    */   static Interpolator create(InterpolatorType interpolatorType) {
/* 44 */     if (interpolatorType == null)
/*    */     {
/* 46 */       return new LinearInterpolator();
/*    */     }
/* 48 */     switch (interpolatorType) {
/*    */       
/*    */       case SLERP:
/* 51 */         return new SlerpQuaternionInterpolator();
/*    */       
/*    */       case LINEAR:
/* 54 */         return new LinearInterpolator();
/*    */       
/*    */       case STEP:
/* 57 */         return new StepInterpolator();
/*    */     } 
/*    */     
/* 60 */     throw new IllegalArgumentException("Invalid interpolator type: " + interpolatorType);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\animation\Interpolators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */