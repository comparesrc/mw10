/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.modularwarfare.common.type.BaseItem;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public class ItemSpray
/*    */   extends BaseItem {
/*    */   static {
/*  9 */     factory = (type -> new ItemSpray(type));
/*    */   }
/*    */   public static final Function<SprayType, ItemSpray> factory;
/*    */   public SprayType type;
/*    */   
/*    */   public ItemSpray(SprayType type) {
/* 15 */     super(type);
/* 16 */     this.type = type;
/* 17 */     this.render3d = false;
/* 18 */     func_77656_e(type.usableMaxAmount);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_77651_p() {
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\ItemSpray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */