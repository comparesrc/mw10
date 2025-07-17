/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.objectweb.asm.tree.LdcInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @AtCode("INVOKE_STRING")
/*     */ public class BeforeStringInvoke
/*     */   extends BeforeInvoke
/*     */ {
/*     */   private static final String STRING_VOID_SIG = "(Ljava/lang/String;)V";
/*     */   private final String ldcValue;
/*     */   private boolean foundLdc;
/*     */   
/*     */   public BeforeStringInvoke(InjectionPointData data) {
/* 101 */     super(data);
/* 102 */     this.ldcValue = data.get("ldc", null);
/*     */     
/* 104 */     if (this.ldcValue == null) {
/* 105 */       throw new IllegalArgumentException(getClass().getSimpleName() + " requires named argument \"ldc\" to specify the desired target");
/*     */     }
/*     */     
/* 108 */     if (!(this.target instanceof ITargetSelectorByName) || !"(Ljava/lang/String;)V".equals(((ITargetSelectorByName)this.target).getDesc())) {
/* 109 */       throw new IllegalArgumentException(getClass().getSimpleName() + " requires target method with with signature " + "(Ljava/lang/String;)V");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 115 */     this.foundLdc = false;
/*     */     
/* 117 */     return super.find(desc, insns, nodes);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inspectInsn(String desc, InsnList insns, AbstractInsnNode insn) {
/* 122 */     if (insn instanceof LdcInsnNode) {
/* 123 */       LdcInsnNode node = (LdcInsnNode)insn;
/* 124 */       if (node.cst instanceof String && this.ldcValue.equals(node.cst)) {
/* 125 */         log("{} > found a matching LDC with value {}", new Object[] { this.className, node.cst });
/* 126 */         this.foundLdc = true;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 131 */     this.foundLdc = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean matchesOrdinal(int ordinal) {
/* 136 */     log("{} > > found LDC \"{}\" = {}", new Object[] { this.className, this.ldcValue, Boolean.valueOf(this.foundLdc) });
/* 137 */     return (this.foundLdc && super.matchesOrdinal(ordinal));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\points\BeforeStringInvoke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */