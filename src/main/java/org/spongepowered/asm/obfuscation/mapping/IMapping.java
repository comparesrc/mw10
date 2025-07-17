/*    */ package org.spongepowered.asm.obfuscation.mapping;
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
/*    */ public interface IMapping<TMapping>
/*    */ {
/*    */   Type getType();
/*    */   
/*    */   TMapping move(String paramString);
/*    */   
/*    */   TMapping remap(String paramString);
/*    */   
/*    */   TMapping transform(String paramString);
/*    */   
/*    */   TMapping copy();
/*    */   
/*    */   String getName();
/*    */   
/*    */   String getSimpleName();
/*    */   
/*    */   String getOwner();
/*    */   
/*    */   String getDesc();
/*    */   
/*    */   TMapping getSuper();
/*    */   
/*    */   String serialise();
/*    */   
/*    */   public enum Type
/*    */   {
/* 38 */     FIELD,
/* 39 */     METHOD,
/* 40 */     CLASS,
/* 41 */     PACKAGE;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\obfuscation\mapping\IMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */