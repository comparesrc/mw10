/*    */ package org.spongepowered.asm.transformers;
/*    */ 
/*    */ import org.objectweb.asm.ClassReader;
/*    */ import org.objectweb.asm.ClassVisitor;
/*    */ import org.objectweb.asm.ClassWriter;
/*    */ import org.objectweb.asm.tree.ClassNode;
/*    */ import org.spongepowered.asm.service.ILegacyClassTransformer;
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
/*    */ public abstract class TreeTransformer
/*    */   implements ILegacyClassTransformer
/*    */ {
/*    */   private ClassReader classReader;
/*    */   private ClassNode classNode;
/*    */   
/*    */   protected final ClassNode readClass(byte[] basicClass) {
/* 45 */     return readClass(basicClass, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final ClassNode readClass(byte[] basicClass, boolean cacheReader) {
/* 55 */     ClassReader classReader = new ClassReader(basicClass);
/* 56 */     if (cacheReader) {
/* 57 */       this.classReader = classReader;
/*    */     }
/*    */     
/* 60 */     ClassNode classNode = new ClassNode();
/* 61 */     classReader.accept((ClassVisitor)classNode, 8);
/* 62 */     return classNode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final byte[] writeClass(ClassNode classNode) {
/* 71 */     if (this.classReader != null && this.classNode == classNode) {
/* 72 */       this.classNode = null;
/* 73 */       ClassWriter classWriter = new MixinClassWriter(this.classReader, 3);
/* 74 */       this.classReader = null;
/* 75 */       classNode.accept((ClassVisitor)classWriter);
/* 76 */       return classWriter.toByteArray();
/*    */     } 
/*    */     
/* 79 */     this.classNode = null;
/*    */     
/* 81 */     ClassWriter writer = new MixinClassWriter(3);
/* 82 */     classNode.accept((ClassVisitor)writer);
/* 83 */     return writer.toByteArray();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\transformers\TreeTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */