/*    */ package org.spongepowered.asm.lib;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.throwables.CompanionPluginError;
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
/*    */ public abstract class ClassVisitor
/*    */ {
/*    */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/* 39 */     throw new CompanionPluginError("ClassVisitor.visit");
/*    */   }
/*    */   
/*    */   public void visitSource(String source, String debug) {
/* 43 */     throw new CompanionPluginError("ClassVisitor.visitSource");
/*    */   }
/*    */   
/*    */   public void visitOuterClass(String owner, String name, String desc) {
/* 47 */     throw new CompanionPluginError("ClassVisitor.visitOuterClass");
/*    */   }
/*    */   
/*    */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 51 */     throw new CompanionPluginError("ClassVisitor.visitAnnotation");
/*    */   }
/*    */   
/*    */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 55 */     throw new CompanionPluginError("ClassVisitor.visitTypeAnnotation");
/*    */   }
/*    */   
/*    */   public void visitAttribute(Attribute attr) {
/* 59 */     throw new CompanionPluginError("ClassVisitor.visitAttribute");
/*    */   }
/*    */   
/*    */   public void visitInnerClass(String name, String outerName, String innerName, int access) {
/* 63 */     throw new CompanionPluginError("ClassVisitor.visitInnerClass");
/*    */   }
/*    */   
/*    */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
/* 67 */     throw new CompanionPluginError("ClassVisitor.visitField");
/*    */   }
/*    */   
/*    */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/* 71 */     throw new CompanionPluginError("ClassVisitor.visitMethod");
/*    */   }
/*    */   
/*    */   public void visitEnd() {
/* 75 */     throw new CompanionPluginError("ClassVisitor.visitEnd");
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\lib\ClassVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */