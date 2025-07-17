/*    */ package com.modularwarfare.loader.part;
/*    */ 
/*    */ public class Vertex {
/*    */   public float x;
/*    */   
/*    */   public Vertex(float x, float y) {
/*  7 */     this(x, y, 0.0F);
/*    */   }
/*    */   public float y; public float z;
/*    */   public Vertex(float x, float y, float z) {
/* 11 */     this.x = x;
/* 12 */     this.y = y;
/* 13 */     this.z = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 18 */     return this.x + "/" + this.y + "/" + this.z;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\loader\part\Vertex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */