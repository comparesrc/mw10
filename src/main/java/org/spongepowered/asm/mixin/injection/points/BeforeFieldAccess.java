/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.FieldInsnNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.util.Bytecode;
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
/*     */ @AtCode("FIELD")
/*     */ public class BeforeFieldAccess
/*     */   extends BeforeInvoke
/*     */ {
/*     */   private static final String ARRAY_GET = "get";
/*     */   private static final String ARRAY_SET = "set";
/*     */   private static final String ARRAY_LENGTH = "length";
/*     */   public static final int ARRAY_SEARCH_FUZZ_DEFAULT = 8;
/*     */   private final int opcode;
/*     */   private final int arrOpcode;
/*     */   private final int fuzzFactor;
/*     */   
/*     */   public BeforeFieldAccess(InjectionPointData data) {
/* 117 */     super(data);
/* 118 */     this.opcode = data.getOpcode(-1, new int[] { 180, 181, 178, 179, -1 });
/*     */     
/* 120 */     String array = data.get("array", "");
/* 121 */     this
/*     */       
/* 123 */       .arrOpcode = "get".equalsIgnoreCase(array) ? 46 : ("set".equalsIgnoreCase(array) ? 79 : ("length".equalsIgnoreCase(array) ? 190 : 0));
/* 124 */     this.fuzzFactor = Math.min(Math.max(data.get("fuzz", 8), 1), 32);
/*     */   }
/*     */   
/*     */   public int getFuzzFactor() {
/* 128 */     return this.fuzzFactor;
/*     */   }
/*     */   
/*     */   public int getArrayOpcode() {
/* 132 */     return this.arrOpcode;
/*     */   }
/*     */   
/*     */   private int getArrayOpcode(String desc) {
/* 136 */     if (this.arrOpcode != 190) {
/* 137 */       return Type.getType(desc).getElementType().getOpcode(this.arrOpcode);
/*     */     }
/* 139 */     return this.arrOpcode;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean matchesInsn(AbstractInsnNode insn) {
/* 144 */     if (insn instanceof FieldInsnNode && (((FieldInsnNode)insn).getOpcode() == this.opcode || this.opcode == -1)) {
/* 145 */       if (this.arrOpcode == 0) {
/* 146 */         return true;
/*     */       }
/*     */       
/* 149 */       if (insn.getOpcode() != 178 && insn.getOpcode() != 180) {
/* 150 */         return false;
/*     */       }
/*     */       
/* 153 */       return (Type.getType(((FieldInsnNode)insn).desc).getSort() == 9);
/*     */     } 
/*     */     
/* 156 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean addInsn(InsnList insns, Collection<AbstractInsnNode> nodes, AbstractInsnNode insn) {
/* 161 */     if (this.arrOpcode > 0) {
/* 162 */       FieldInsnNode fieldInsn = (FieldInsnNode)insn;
/* 163 */       int accOpcode = getArrayOpcode(fieldInsn.desc);
/* 164 */       log("{} > > > > searching for array access opcode {} fuzz={}", new Object[] { this.className, Bytecode.getOpcodeName(accOpcode), Integer.valueOf(this.fuzzFactor) });
/*     */       
/* 166 */       if (findArrayNode(insns, fieldInsn, accOpcode, this.fuzzFactor) == null) {
/* 167 */         log("{} > > > > > failed to locate matching insn", new Object[] { this.className });
/* 168 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 172 */     log("{} > > > > > adding matching insn", new Object[] { this.className });
/*     */     
/* 174 */     return super.addInsn(insns, nodes, insn);
/*     */   }
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
/*     */   public static AbstractInsnNode findArrayNode(InsnList insns, FieldInsnNode fieldNode, int opcode, int searchRange) {
/* 192 */     int pos = 0;
/* 193 */     for (Iterator<AbstractInsnNode> iter = insns.iterator(insns.indexOf((AbstractInsnNode)fieldNode) + 1); iter.hasNext(); ) {
/* 194 */       AbstractInsnNode insn = iter.next();
/* 195 */       if (insn.getOpcode() == opcode)
/* 196 */         return insn; 
/* 197 */       if (insn.getOpcode() == 190 && pos == 0)
/* 198 */         return null; 
/* 199 */       if (insn instanceof FieldInsnNode) {
/* 200 */         FieldInsnNode field = (FieldInsnNode)insn;
/* 201 */         if (field.desc.equals(fieldNode.desc) && field.name.equals(fieldNode.name) && field.owner.equals(fieldNode.owner)) {
/* 202 */           return null;
/*     */         }
/*     */       } 
/* 205 */       if (pos++ > searchRange) {
/* 206 */         return null;
/*     */       }
/*     */     } 
/* 209 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\points\BeforeFieldAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */