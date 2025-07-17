/*    */ package org.spongepowered.asm.util;
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
/*    */ public final class Counter
/*    */ {
/*    */   public int value;
/*    */   
/*    */   public boolean equals(Object obj) {
/* 39 */     return (obj != null && obj.getClass() == Counter.class && ((Counter)obj).value == this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 44 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\Counter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */