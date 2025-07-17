/*     */ package org.spongepowered.asm.util.logging;
/*     */ 
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.AnnotationMirror;
/*     */ import javax.lang.model.element.AnnotationValue;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MessageRouter
/*     */ {
/*     */   private static Messager messager;
/*     */   
/*     */   static class LoggingMessager
/*     */     implements Messager
/*     */   {
/*  52 */     private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */     
/*     */     public void printMessage(Diagnostic.Kind kind, CharSequence msg) {
/*  56 */       logger.log(messageKindToLoggingLevel(kind), msg);
/*     */     }
/*     */ 
/*     */     
/*     */     public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e) {
/*  61 */       logger.log(messageKindToLoggingLevel(kind), msg);
/*     */     }
/*     */ 
/*     */     
/*     */     public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a) {
/*  66 */       logger.log(messageKindToLoggingLevel(kind), msg);
/*     */     }
/*     */ 
/*     */     
/*     */     public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a, AnnotationValue v) {
/*  71 */       logger.log(messageKindToLoggingLevel(kind), msg);
/*     */     }
/*     */     
/*     */     private static Level messageKindToLoggingLevel(Diagnostic.Kind kind) {
/*  75 */       switch (kind) {
/*     */         case ERROR:
/*  77 */           return Level.ERROR;
/*     */         case WARNING:
/*     */         case MANDATORY_WARNING:
/*  80 */           return Level.WARN;
/*     */         case NOTE:
/*  82 */           return Level.INFO;
/*     */       } 
/*     */       
/*  85 */       return Level.DEBUG;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class DebugInterceptingMessager
/*     */     implements Messager
/*     */   {
/*     */     private final Messager wrapped;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DebugInterceptingMessager(Messager messager) {
/* 101 */       this.wrapped = messager;
/*     */     }
/*     */ 
/*     */     
/*     */     public void printMessage(Diagnostic.Kind kind, CharSequence msg) {
/* 106 */       if (kind != Diagnostic.Kind.OTHER) {
/* 107 */         this.wrapped.printMessage(kind, msg);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e) {
/* 113 */       if (kind != Diagnostic.Kind.OTHER) {
/* 114 */         this.wrapped.printMessage(kind, msg, e);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a) {
/* 120 */       if (kind != Diagnostic.Kind.OTHER) {
/* 121 */         this.wrapped.printMessage(kind, msg, e, a);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a, AnnotationValue v) {
/* 127 */       if (kind != Diagnostic.Kind.OTHER) {
/* 128 */         this.wrapped.printMessage(kind, msg, e, a, v);
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Messager getMessager() {
/* 149 */     if (messager == null) {
/* 150 */       messager = new LoggingMessager();
/*     */     }
/* 152 */     return messager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setMessager(Messager messager) {
/* 162 */     MessageRouter.messager = (messager == null) ? null : new DebugInterceptingMessager(messager);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\logging\MessageRouter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */