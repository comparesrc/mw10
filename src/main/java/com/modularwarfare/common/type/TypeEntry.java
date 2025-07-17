/*    */ package com.modularwarfare.common.type;
/*    */ 
/*    */ import java.util.function.BiConsumer;
/*    */ 
/*    */ public class TypeEntry
/*    */ {
/*    */   public String name;
/*    */   public Class<? extends BaseType> typeClass;
/*    */   public int id;
/*    */   public BiConsumer<BaseType, Boolean> typeAssignFunction;
/*    */   
/*    */   public TypeEntry(String name, Class<? extends BaseType> typeClass, int id, BiConsumer<BaseType, Boolean> typeAssignFunction) {
/* 13 */     this.name = name;
/* 14 */     this.typeClass = typeClass;
/* 15 */     this.id = id;
/* 16 */     this.typeAssignFunction = typeAssignFunction;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 21 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\type\TypeEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */