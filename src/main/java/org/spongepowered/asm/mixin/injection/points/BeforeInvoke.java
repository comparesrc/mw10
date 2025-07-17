/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Locale;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
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
/*     */ @AtCode("INVOKE")
/*     */ public class BeforeInvoke
/*     */   extends InjectionPoint
/*     */ {
/*     */   protected final ITargetSelector target;
/*     */   protected final boolean allowPermissive;
/*     */   protected final int ordinal;
/*     */   protected final String className;
/*     */   protected final IMixinContext context;
/*     */   
/*     */   public enum SearchType
/*     */   {
/*  82 */     STRICT,
/*  83 */     PERMISSIVE;
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
/* 116 */   protected final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */   
/*     */   private boolean log = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public BeforeInvoke(InjectionPointData data) {
/* 124 */     super(data);
/*     */     
/* 126 */     this.target = data.getTarget();
/* 127 */     this.ordinal = data.getOrdinal();
/* 128 */     this.log = data.get("log", false);
/* 129 */     this.className = getClassName();
/* 130 */     this.context = data.getContext();
/* 131 */     this
/* 132 */       .allowPermissive = (this.context.getOption(MixinEnvironment.Option.REFMAP_REMAP) && this.context.getOption(MixinEnvironment.Option.REFMAP_REMAP_ALLOW_PERMISSIVE) && !this.context.getReferenceMapper().isDefault());
/*     */   }
/*     */   
/*     */   private String getClassName() {
/* 136 */     InjectionPoint.AtCode atCode = getClass().<InjectionPoint.AtCode>getAnnotation(InjectionPoint.AtCode.class);
/* 137 */     return String.format("@At(%s)", new Object[] { (atCode != null) ? atCode.value() : getClass().getSimpleName().toUpperCase(Locale.ROOT) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeforeInvoke setLogging(boolean logging) {
/* 147 */     this.log = logging;
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 158 */     log("{} is searching for an injection point in method with descriptor {}", new Object[] { this.className, desc });
/*     */     
/* 160 */     boolean hasDescriptor = (this.target instanceof ITargetSelectorByName && ((ITargetSelectorByName)this.target).getDesc() == null);
/* 161 */     boolean found = find(desc, insns, nodes, this.target, SearchType.STRICT);
/*     */     
/* 163 */     if (!found && hasDescriptor && this.allowPermissive) {
/* 164 */       this.logger.warn("STRICT match for {} using \"{}\" in {} returned 0 results, attempting permissive search. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[] { this.className, this.target, this.context });
/*     */       
/* 166 */       found = find(desc, insns, nodes, this.target, SearchType.PERMISSIVE);
/*     */     } 
/*     */     
/* 169 */     return found;
/*     */   }
/*     */   
/*     */   protected boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes, ITargetSelector selector, SearchType searchType) {
/* 173 */     if (selector == null) {
/* 174 */       return false;
/*     */     }
/*     */     
/* 177 */     ITargetSelector target = (searchType == SearchType.PERMISSIVE) ? selector.configure(new String[] { "permissive" }) : selector;
/*     */     
/* 179 */     int ordinal = 0;
/* 180 */     int found = 0;
/*     */     
/* 182 */     ListIterator<AbstractInsnNode> iter = insns.iterator();
/* 183 */     while (iter.hasNext()) {
/* 184 */       AbstractInsnNode insn = iter.next();
/*     */       
/* 186 */       if (matchesInsn(insn)) {
/* 187 */         MemberInfo nodeInfo = new MemberInfo(insn);
/* 188 */         log("{} is considering insn {}", new Object[] { this.className, nodeInfo });
/*     */         
/* 190 */         if (target.match(insn).isExactMatch()) {
/* 191 */           log("{} > found a matching insn, checking preconditions...", new Object[] { this.className });
/*     */           
/* 193 */           if (matchesOrdinal(ordinal)) {
/* 194 */             log("{} > > > found a matching insn at ordinal {}", new Object[] { this.className, Integer.valueOf(ordinal) });
/*     */             
/* 196 */             if (addInsn(insns, nodes, insn)) {
/* 197 */               found++;
/*     */             }
/*     */             
/* 200 */             if (this.ordinal == ordinal) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */           
/* 205 */           ordinal++;
/*     */         } 
/*     */       } 
/*     */       
/* 209 */       inspectInsn(desc, insns, insn);
/*     */     } 
/*     */     
/* 212 */     if (searchType == SearchType.PERMISSIVE && found > 1) {
/* 213 */       this.logger.warn("A permissive match for {} using \"{}\" in {} matched {} instructions, this may cause unexpected behaviour. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[] { this.className, selector, this.context, 
/* 214 */             Integer.valueOf(found) });
/*     */     }
/*     */     
/* 217 */     return (found > 0);
/*     */   }
/*     */   
/*     */   protected boolean addInsn(InsnList insns, Collection<AbstractInsnNode> nodes, AbstractInsnNode insn) {
/* 221 */     nodes.add(insn);
/* 222 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean matchesInsn(AbstractInsnNode insn) {
/* 226 */     return insn instanceof org.objectweb.asm.tree.MethodInsnNode;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inspectInsn(String desc, InsnList insns, AbstractInsnNode insn) {}
/*     */ 
/*     */   
/*     */   protected boolean matchesOrdinal(int ordinal) {
/* 234 */     log("{} > > comparing target ordinal {} with current ordinal {}", new Object[] { this.className, Integer.valueOf(this.ordinal), Integer.valueOf(ordinal) });
/* 235 */     return (this.ordinal == -1 || this.ordinal == ordinal);
/*     */   }
/*     */   
/*     */   protected void log(String message, Object... params) {
/* 239 */     if (this.log)
/* 240 */       this.logger.info(message, params); 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\points\BeforeInvoke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */