/*    */ package org.spongepowered.asm.util.asm;
/*    */ 
/*    */ import org.objectweb.asm.MethodVisitor;
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
/*    */ public class MethodVisitorEx
/*    */   extends MethodVisitor
/*    */ {
/*    */   public MethodVisitorEx(MethodVisitor mv) {
/* 37 */     super(ASM.API_VERSION, mv);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void visitConstant(byte constant) {
/* 47 */     if (constant > -2 && constant < 6) {
/* 48 */       visitInsn(Bytecode.CONSTANTS_INT[constant + 1]);
/*    */       return;
/*    */     } 
/* 51 */     visitIntInsn(16, constant);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\asm\MethodVisitorEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */