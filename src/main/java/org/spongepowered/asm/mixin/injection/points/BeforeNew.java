/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.objectweb.asm.tree.MethodInsnNode;
/*     */ import org.objectweb.asm.tree.TypeInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorConstructor;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionPointException;
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
/*     */ @AtCode("NEW")
/*     */ public class BeforeNew
/*     */   extends InjectionPoint
/*     */ {
/*     */   private final String target;
/*     */   private final String desc;
/*     */   private final int ordinal;
/*     */   
/*     */   public BeforeNew(InjectionPointData data) {
/* 110 */     super(data);
/*     */     
/* 112 */     this.ordinal = data.getOrdinal();
/* 113 */     String target = Strings.emptyToNull(data.get("class", data.get("target", "")).replace('.', '/'));
/* 114 */     ITargetSelector member = TargetSelector.parseAndValidate(target, data.getContext());
/* 115 */     if (!(member instanceof ITargetSelectorConstructor)) {
/* 116 */       throw new InvalidInjectionPointException(data.getContext(), "Failed parsing @At(\"NEW\") target descriptor \"%s\" on %s", new Object[] { target, data
/* 117 */             .getDescription() });
/*     */     }
/* 119 */     ITargetSelectorConstructor targetSelector = (ITargetSelectorConstructor)member;
/* 120 */     this.target = targetSelector.toCtorType();
/* 121 */     this.desc = targetSelector.toCtorDesc();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDescriptor() {
/* 128 */     return (this.desc != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 134 */     boolean found = false;
/* 135 */     int ordinal = 0;
/*     */     
/* 137 */     Collection<TypeInsnNode> newNodes = new ArrayList<>();
/* 138 */     Collection<AbstractInsnNode> candidates = (this.desc != null) ? (Collection)newNodes : nodes;
/* 139 */     ListIterator<AbstractInsnNode> iter = insns.iterator();
/* 140 */     while (iter.hasNext()) {
/* 141 */       AbstractInsnNode insn = iter.next();
/*     */       
/* 143 */       if (insn instanceof TypeInsnNode && insn.getOpcode() == 187 && matchesOwner((TypeInsnNode)insn)) {
/* 144 */         if (this.ordinal == -1 || this.ordinal == ordinal) {
/* 145 */           candidates.add(insn);
/* 146 */           found = (this.desc == null);
/*     */         } 
/*     */         
/* 149 */         ordinal++;
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     if (this.desc != null) {
/* 154 */       for (TypeInsnNode newNode : newNodes) {
/* 155 */         if (findCtor(insns, newNode)) {
/* 156 */           nodes.add(newNode);
/* 157 */           found = true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 162 */     return found;
/*     */   }
/*     */   
/*     */   protected boolean findCtor(InsnList insns, TypeInsnNode newNode) {
/* 166 */     int indexOf = insns.indexOf((AbstractInsnNode)newNode);
/* 167 */     for (Iterator<AbstractInsnNode> iter = insns.iterator(indexOf); iter.hasNext(); ) {
/* 168 */       AbstractInsnNode insn = iter.next();
/* 169 */       if (insn instanceof MethodInsnNode && insn.getOpcode() == 183) {
/* 170 */         MethodInsnNode methodNode = (MethodInsnNode)insn;
/* 171 */         if ("<init>".equals(methodNode.name) && methodNode.owner.equals(newNode.desc) && methodNode.desc.equals(this.desc)) {
/* 172 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 176 */     return false;
/*     */   }
/*     */   
/*     */   private boolean matchesOwner(TypeInsnNode insn) {
/* 180 */     return (this.target == null || this.target.equals(insn.desc));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\points\BeforeNew.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */