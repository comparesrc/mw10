/*     */ package org.spongepowered.asm.mixin.injection.selectors;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
/*     */ import org.spongepowered.asm.util.asm.ElementNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TargetSelector
/*     */ {
/*     */   public static class Result<TNode>
/*     */   {
/*     */     public final TNode exactMatch;
/*     */     public final List<TNode> candidates;
/*     */     
/*     */     Result(TNode exactMatch, List<TNode> candidates) {
/*  59 */       this.exactMatch = exactMatch;
/*  60 */       this.candidates = candidates;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TNode getSingleResult(boolean strict) {
/*  76 */       int resultCount = this.candidates.size();
/*  77 */       if (this.exactMatch != null) {
/*  78 */         return this.exactMatch;
/*     */       }
/*  80 */       if (resultCount == 1 || !strict) {
/*  81 */         return this.candidates.get(0);
/*     */       }
/*  83 */       throw new IllegalStateException(((resultCount == 0) ? "No" : "Multiple") + " candidates were found");
/*     */     }
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
/*     */   public static ITargetSelector parseAndValidate(String string) throws InvalidMemberDescriptorException {
/*  98 */     return parse(string, null, null).validate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ITargetSelector parseAndValidate(String string, IMixinContext context) throws InvalidMemberDescriptorException {
/* 109 */     return parse(string, context.getReferenceMapper(), context.getClassRef()).validate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ITargetSelector parse(String string) {
/* 119 */     return parse(string, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ITargetSelector parse(String string, IMixinContext context) {
/* 130 */     return parse(string, context.getReferenceMapper(), context.getClassRef());
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
/*     */   public static String parseName(String name, IMixinContext context) {
/* 145 */     ITargetSelector selector = parse(name, context);
/* 146 */     if (!(selector instanceof ITargetSelectorByName)) {
/* 147 */       return name;
/*     */     }
/* 149 */     String mappedName = ((ITargetSelectorByName)selector).getName();
/* 150 */     return (mappedName != null) ? mappedName : name;
/*     */   }
/*     */   
/*     */   private static ITargetSelector parse(String input, IReferenceMapper refMapper, String mixinClass) {
/* 154 */     return (ITargetSelector)MemberInfo.parse(input, refMapper, mixinClass);
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
/*     */   public static <TNode> Result<TNode> run(ITargetSelector target, List<ElementNode<TNode>> nodes) {
/* 166 */     List<TNode> candidates = new ArrayList<>();
/* 167 */     TNode exactMatch = runSelector(target, nodes, candidates);
/* 168 */     return new Result<>(exactMatch, candidates);
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
/*     */   public static <TNode> Result<TNode> run(Iterable<ITargetSelector> targets, List<ElementNode<TNode>> nodes) {
/* 180 */     TNode exactMatch = null;
/* 181 */     List<TNode> candidates = new ArrayList<>();
/*     */     
/* 183 */     for (ITargetSelector target : targets) {
/* 184 */       TNode selectorExactMatch = runSelector(target, nodes, candidates);
/* 185 */       if (exactMatch == null) {
/* 186 */         exactMatch = selectorExactMatch;
/*     */       }
/*     */     } 
/*     */     
/* 190 */     return new Result<>(exactMatch, candidates);
/*     */   }
/*     */   
/*     */   private static <TNode> TNode runSelector(ITargetSelector target, List<ElementNode<TNode>> nodes, List<TNode> candidates) {
/* 194 */     TNode exactMatch = null;
/* 195 */     for (ElementNode<TNode> element : nodes) {
/* 196 */       MatchResult match = target.match(element);
/* 197 */       if (match.isMatch()) {
/* 198 */         TNode node = (TNode)element.get();
/* 199 */         if (!candidates.contains(node)) {
/* 200 */           candidates.add(node);
/*     */         }
/* 202 */         if (exactMatch == null && match.isExactMatch()) {
/* 203 */           exactMatch = node;
/*     */         }
/*     */       } 
/*     */     } 
/* 207 */     return exactMatch;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\selectors\TargetSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */