/*    */ package org.spongepowered.asm.transformers;
/*    */ 
/*    */ import org.objectweb.asm.ClassReader;
/*    */ import org.objectweb.asm.ClassWriter;
/*    */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
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
/*    */ public class MixinClassWriter
/*    */   extends ClassWriter
/*    */ {
/*    */   public MixinClassWriter(int flags) {
/* 38 */     super(flags);
/*    */   }
/*    */   
/*    */   public MixinClassWriter(ClassReader classReader, int flags) {
/* 42 */     super(classReader, flags);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getCommonSuperClass(String type1, String type2) {
/* 51 */     return ClassInfo.getCommonSuperClass(type1, type2).getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\transformers\MixinClassWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */