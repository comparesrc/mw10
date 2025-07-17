/*    */ package de.javagl.jgltf.model;
/*    */ 
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Suppliers
/*    */ {
/*    */   public static <T> Supplier<float[]> createTransformSupplier(T object, BiConsumer<T, float[]> computer) {
/* 56 */     float[] transform = new float[16];
/* 57 */     if (object == null)
/*    */     {
/* 59 */       return () -> {
/*    */           MathUtils.setIdentity4x4(transform);
/*    */           
/*    */           return transform;
/*    */         };
/*    */     }
/* 65 */     return () -> {
/*    */         computer.accept(object, transform);
/*    */         return transform;
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\Suppliers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */