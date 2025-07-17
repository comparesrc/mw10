/*    */ package org.spongepowered.asm.util.asm;
/*    */ 
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
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
/*    */ public class MethodNodeEx
/*    */   extends MethodNode
/*    */ {
/*    */   private final IMixinInfo owner;
/*    */   private final String originalName;
/*    */   
/*    */   public MethodNodeEx(int access, String name, String descriptor, String signature, String[] exceptions, IMixinInfo owner) {
/* 40 */     super(ASM.API_VERSION, access, name, descriptor, signature, exceptions);
/* 41 */     this.originalName = name;
/* 42 */     this.owner = owner;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 47 */     return String.format("%s%s", new Object[] { this.originalName, this.desc });
/*    */   }
/*    */   
/*    */   public String getQualifiedName() {
/* 51 */     return String.format("%s::%s", new Object[] { this.owner.getName(), this.originalName });
/*    */   }
/*    */   
/*    */   public String getOriginalName() {
/* 55 */     return this.originalName;
/*    */   }
/*    */   
/*    */   public IMixinInfo getOwner() {
/* 59 */     return this.owner;
/*    */   }
/*    */   
/*    */   public static String getName(MethodNode method) {
/* 63 */     return (method instanceof MethodNodeEx) ? ((MethodNodeEx)method).getOriginalName() : method.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\asm\MethodNodeEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */