/*    */ package org.spongepowered.asm.mixin.gen;
/*    */ 
/*    */ import org.objectweb.asm.Type;
/*    */ import org.objectweb.asm.tree.FieldNode;
/*    */ import org.spongepowered.asm.util.Bytecode;
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
/*    */ public abstract class AccessorGeneratorField
/*    */   extends AccessorGenerator
/*    */ {
/*    */   protected final FieldNode targetField;
/*    */   protected final Type targetType;
/*    */   
/*    */   public AccessorGeneratorField(AccessorInfo info) {
/* 47 */     super(info, Bytecode.isStatic(info.getTargetField()));
/* 48 */     this.targetField = info.getTargetField();
/* 49 */     this.targetType = info.getTargetFieldType();
/* 50 */     checkModifiers();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\gen\AccessorGeneratorField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */