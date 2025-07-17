/*    */ package org.spongepowered.asm.mixin.transformer;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.spongepowered.asm.mixin.throwables.MixinError;
/*    */ import org.spongepowered.asm.service.ISyntheticClassInfo;
/*    */ import org.spongepowered.asm.service.ISyntheticClassRegistry;
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
/*    */ class SyntheticClassRegistry
/*    */   implements ISyntheticClassRegistry
/*    */ {
/* 48 */   private final Map<String, ISyntheticClassInfo> classes = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ISyntheticClassInfo findSyntheticClass(String name) {
/* 59 */     if (name == null) {
/* 60 */       return null;
/*    */     }
/* 62 */     return this.classes.get(name.replace('.', '/'));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void registerSyntheticClass(ISyntheticClassInfo sci) {
/* 69 */     String name = sci.getName();
/* 70 */     ISyntheticClassInfo info = this.classes.get(name);
/* 71 */     if (info != null) {
/* 72 */       if (info == sci) {
/*    */         return;
/*    */       }
/* 75 */       throw new MixinError("Synthetic class with name " + name + " was already registered by " + info.getMixin() + ". Duplicate being registered by " + sci
/* 76 */           .getMixin());
/*    */     } 
/* 78 */     this.classes.put(name, sci);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\SyntheticClassRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */