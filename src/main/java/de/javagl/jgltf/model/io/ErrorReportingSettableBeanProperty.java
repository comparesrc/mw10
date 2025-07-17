/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import com.fasterxml.jackson.core.JsonParser;
/*     */ import com.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.fasterxml.jackson.databind.PropertyName;
/*     */ import com.fasterxml.jackson.databind.deser.NullValueProvider;
/*     */ import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import java.io.IOException;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ErrorReportingSettableBeanProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 7398743799397469737L;
/*     */   private final SettableBeanProperty delegate;
/*     */   private final Consumer<? super JsonError> jsonErrorConsumer;
/*     */   
/*     */   ErrorReportingSettableBeanProperty(SettableBeanProperty delegate, Consumer<? super JsonError> jsonErrorConsumer) {
/*  75 */     super(delegate);
/*  76 */     this.delegate = delegate;
/*  77 */     this.jsonErrorConsumer = jsonErrorConsumer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withValueDeserializer(JsonDeserializer<?> deser) {
/*  84 */     return new ErrorReportingSettableBeanProperty(this.delegate
/*  85 */         .withValueDeserializer(deser), this.jsonErrorConsumer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withName(PropertyName newName) {
/*  91 */     return new ErrorReportingSettableBeanProperty(this.delegate
/*  92 */         .withName(newName), this.jsonErrorConsumer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setAndReturn(Object instance, Object value) throws IOException {
/*  99 */     return this.delegate.setAndReturn(instance, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(Object instance, Object value) throws IOException {
/* 105 */     this.delegate.set(instance, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedMember getMember() {
/* 111 */     return this.delegate.getMember();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> acls) {
/* 117 */     return (A)this.delegate.getAnnotation(acls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeSetAndReturn(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/* 124 */     return this.delegate.deserializeSetAndReturn(p, ctxt, instance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deserializeAndSet(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*     */     try {
/* 133 */       this.delegate.deserializeAndSet(p, ctxt, instance);
/*     */     }
/* 135 */     catch (Exception e) {
/*     */       
/* 137 */       if (this.jsonErrorConsumer != null)
/*     */       {
/* 139 */         this.jsonErrorConsumer.accept(new JsonError(e
/* 140 */               .getMessage(), p.getParsingContext(), e));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withNullProvider(NullValueProvider nva) {
/* 148 */     return new ErrorReportingSettableBeanProperty(this.delegate
/* 149 */         .withNullProvider(nva), this.jsonErrorConsumer);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\ErrorReportingSettableBeanProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */