/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import com.fasterxml.jackson.databind.PropertyNamingStrategy;
/*     */ import com.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import com.fasterxml.jackson.databind.introspect.AnnotatedField;
/*     */ import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import java.lang.reflect.Field;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class KeywordPropertyNamingStrategy
/*     */   extends PropertyNamingStrategy
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
/*  78 */     return field.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
/*  85 */     return handleKeywordNames(method.getDeclaringClass(), defaultName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
/*  92 */     return handleKeywordNames(method.getDeclaringClass(), defaultName);
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
/*     */   private String handleKeywordNames(Class<?> c, String defaultName) {
/* 112 */     if (!defaultName.endsWith("Property"))
/*     */     {
/* 114 */       return defaultName;
/*     */     }
/*     */     
/* 117 */     String baseName = defaultName.substring(0, defaultName
/* 118 */         .length() - "Property".length());
/* 119 */     Field[] fields = c.getDeclaredFields();
/* 120 */     for (Field field : fields) {
/*     */       
/* 122 */       if (field.getName().equalsIgnoreCase(defaultName))
/*     */       {
/* 124 */         return baseName;
/*     */       }
/*     */     } 
/* 127 */     return defaultName;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\KeywordPropertyNamingStrategy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */