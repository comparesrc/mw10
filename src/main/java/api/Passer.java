/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ public class Passer<T>
/*    */ {
/*    */   public Passer(T object) {
/*  6 */     this.object = object;
/*    */   }
/*    */   public T object;
/*    */   public Passer() {
/* 10 */     this(null);
/*    */   }
/*    */   
/*    */   public T get() {
/* 14 */     return this.object;
/*    */   }
/*    */   
/*    */   public void set(T object) {
/* 18 */     this.object = object;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\Passer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */