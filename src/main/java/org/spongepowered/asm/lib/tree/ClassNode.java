/*    */ package org.spongepowered.asm.lib.tree;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.spongepowered.asm.lib.ClassVisitor;
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
/*    */ public class ClassNode
/*    */   extends ClassVisitor
/*    */ {
/*    */   public final int version;
/*    */   public final int access;
/*    */   public final String name;
/*    */   public final String signature;
/*    */   public final String superName;
/*    */   public final List<String> interfaces;
/*    */   public final String sourceFile;
/*    */   public final String sourceDebug;
/*    */   public final String outerClass;
/*    */   public final String outerMethod;
/*    */   public final String outerMethodDesc;
/*    */   
/*    */   public ClassNode(org.objectweb.asm.tree.ClassNode classNode) {
/* 70 */     this.version = classNode.version;
/* 71 */     this.access = classNode.access;
/*    */     
/* 73 */     this.name = classNode.name;
/* 74 */     this.signature = classNode.signature;
/*    */     
/* 76 */     this.superName = classNode.superName;
/* 77 */     this.interfaces = Collections.unmodifiableList(classNode.interfaces);
/*    */     
/* 79 */     this.sourceFile = classNode.sourceFile;
/* 80 */     this.sourceDebug = classNode.sourceDebug;
/*    */     
/* 82 */     this.outerClass = classNode.outerClass;
/* 83 */     this.outerMethod = classNode.outerMethod;
/* 84 */     this.outerMethodDesc = classNode.outerMethodDesc;
/*    */   }
/*    */   
/*    */   public void check(int api) {
/* 88 */     throw new CompanionPluginError("ClassNode.check");
/*    */   }
/*    */   
/*    */   public void accept(ClassVisitor cv) {
/* 92 */     throw new CompanionPluginError("ClassNode.accept");
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\lib\tree\ClassNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */