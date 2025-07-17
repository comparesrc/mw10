/*    */ package org.spongepowered.asm.mixin;
/*    */ 
/*    */ import java.lang.annotation.Retention;
/*    */ import java.lang.annotation.RetentionPolicy;
/*    */ import java.lang.annotation.Target;
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
/*    */ @Target({})
/*    */ @Retention(RetentionPolicy.CLASS)
/*    */ public @interface Interface
/*    */ {
/*    */   Class<?> iface();
/*    */   
/*    */   String prefix();
/*    */   
/*    */   boolean unique() default false;
/*    */   
/*    */   Remap remap() default Remap.ALL;
/*    */   
/*    */   public enum Remap
/*    */   {
/* 49 */     ALL,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     FORCE(true),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 64 */     ONLY_PREFIXED,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 70 */     NONE;
/*    */ 
/*    */ 
/*    */     
/*    */     private final boolean forceRemap;
/*    */ 
/*    */ 
/*    */     
/*    */     Remap(boolean forceRemap) {
/* 79 */       this.forceRemap = forceRemap;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public boolean forceRemap() {
/* 86 */       return this.forceRemap;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\Interface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */