/*    */ package de.javagl.jgltf.model.animation;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class InterpolatorKeys
/*    */ {
/*    */   static int computeIndex(float key, float[] keys) {
/* 49 */     int index = Arrays.binarySearch(keys, key);
/* 50 */     if (index >= 0)
/*    */     {
/* 52 */       return index;
/*    */     }
/* 54 */     return Math.max(0, -index - 2);
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
/*    */   
/*    */   static float computeAlpha(float key, float[] keys, int index) {
/* 69 */     if (key <= keys[0])
/*    */     {
/* 71 */       return 0.0F;
/*    */     }
/* 73 */     if (key >= keys[keys.length - 1])
/*    */     {
/* 75 */       return 1.0F;
/*    */     }
/* 77 */     float local = key - keys[index];
/* 78 */     float delta = keys[index + 1] - keys[index];
/* 79 */     float alpha = local / delta;
/* 80 */     return alpha;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] args) {
/* 90 */     float[] keys = { 1.0F, 8.0F, 11.0F };
/* 91 */     for (float d = -1.0F; d <= 12.0F; d = (float)(d + 0.1D)) {
/*    */       
/* 93 */       int index = computeIndex(d, keys);
/* 94 */       float alpha = computeAlpha(d, keys, index);
/* 95 */       System.out.println("For " + d);
/* 96 */       System.out.println("    index " + index);
/* 97 */       System.out.println("    alpha " + alpha);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\animation\InterpolatorKeys.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */