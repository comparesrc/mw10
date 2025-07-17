/*     */ package org.spongepowered.asm.mixin.injection.invoke.util;
/*     */ 
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.analysis.Analyzer;
/*     */ import org.objectweb.asm.tree.analysis.AnalyzerException;
/*     */ import org.objectweb.asm.tree.analysis.BasicInterpreter;
/*     */ import org.objectweb.asm.tree.analysis.BasicValue;
/*     */ import org.objectweb.asm.tree.analysis.Frame;
/*     */ import org.objectweb.asm.tree.analysis.Interpreter;
/*     */ import org.objectweb.asm.tree.analysis.Value;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InsnFinder
/*     */ {
/*     */   static class AnalysisResultException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private AbstractInsnNode result;
/*     */     
/*     */     public AnalysisResultException(AbstractInsnNode popNode) {
/*  53 */       this.result = popNode;
/*     */     }
/*     */     
/*     */     public AbstractInsnNode getResult() {
/*  57 */       return this.result;
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
/*     */   enum AnalyzerState
/*     */   {
/*  70 */     SEARCH,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     ANALYSE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     COMPLETE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class PopAnalyzer
/*     */     extends Analyzer<BasicValue>
/*     */   {
/*     */     protected final AbstractInsnNode node;
/*     */ 
/*     */ 
/*     */     
/*     */     class PopFrame
/*     */       extends Frame<BasicValue>
/*     */     {
/*     */       private AbstractInsnNode current;
/*     */ 
/*     */       
/*  99 */       private InsnFinder.AnalyzerState state = InsnFinder.AnalyzerState.SEARCH;
/* 100 */       private int depth = 0;
/*     */       
/*     */       public PopFrame(int locals, int stack) {
/* 103 */         super(locals, stack);
/*     */       }
/*     */ 
/*     */       
/*     */       public void execute(AbstractInsnNode insn, Interpreter<BasicValue> interpreter) throws AnalyzerException {
/* 108 */         this.current = insn;
/* 109 */         super.execute(insn, interpreter);
/*     */       }
/*     */ 
/*     */       
/*     */       public void push(BasicValue value) throws IndexOutOfBoundsException {
/* 114 */         if (this.current == InsnFinder.PopAnalyzer.this.node && this.state == InsnFinder.AnalyzerState.SEARCH) {
/* 115 */           this.state = InsnFinder.AnalyzerState.ANALYSE;
/* 116 */           this.depth++;
/* 117 */         } else if (this.state == InsnFinder.AnalyzerState.ANALYSE) {
/* 118 */           this.depth++;
/*     */         } 
/* 120 */         super.push((Value)value);
/*     */       }
/*     */ 
/*     */       
/*     */       public BasicValue pop() throws IndexOutOfBoundsException {
/* 125 */         if (this.state == InsnFinder.AnalyzerState.ANALYSE && 
/* 126 */           --this.depth == 0) {
/* 127 */           this.state = InsnFinder.AnalyzerState.COMPLETE;
/* 128 */           throw new InsnFinder.AnalysisResultException(this.current);
/*     */         } 
/*     */         
/* 131 */         return (BasicValue)super.pop();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PopAnalyzer(AbstractInsnNode node) {
/* 139 */       super((Interpreter)new BasicInterpreter());
/* 140 */       this.node = node;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Frame<BasicValue> newFrame(int locals, int stack) {
/* 145 */       return new PopFrame(locals, stack);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInsnNode findPopInsn(Target target, AbstractInsnNode node) {
/*     */     try {
/* 164 */       (new PopAnalyzer(node)).analyze(target.classNode.name, target.method);
/* 165 */     } catch (AnalyzerException ex) {
/* 166 */       if (ex.getCause() instanceof AnalysisResultException) {
/* 167 */         return ((AnalysisResultException)ex.getCause()).getResult();
/*     */       }
/* 169 */       logger.catching((Throwable)ex);
/*     */     } 
/* 171 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\invok\\util\InsnFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */