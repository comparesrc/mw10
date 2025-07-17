/*     */ package org.spongepowered.tools.obfuscation.mapping.fg3;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.io.Files;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.processing.Filer;
/*     */ import javax.annotation.processing.Messager;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.asm.obfuscation.mapping.mcp.MappingFieldSrg;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
/*     */ import org.spongepowered.tools.obfuscation.mapping.common.MappingProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MappingProviderTSrg
/*     */   extends MappingProvider
/*     */ {
/*  50 */   private List<String> inputMappings = new ArrayList<>();
/*     */   
/*     */   public MappingProviderTSrg(Messager messager, Filer filer) {
/*  53 */     super(messager, filer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(File input) throws IOException {
/*  59 */     BiMap<String, String> packageMap = this.packageMap;
/*  60 */     BiMap<String, String> classMap = this.classMap;
/*  61 */     BiMap<MappingField, MappingField> fieldMap = this.fieldMap;
/*  62 */     BiMap<MappingMethod, MappingMethod> methodMap = this.methodMap;
/*     */     
/*  64 */     String fromClass = null, toClass = null;
/*  65 */     this.inputMappings.addAll(Files.readLines(input, Charset.defaultCharset()));
/*     */     
/*  67 */     for (String line : this.inputMappings) {
/*  68 */       if (Strings.isNullOrEmpty(line) || line.startsWith("#")) {
/*     */         continue;
/*     */       }
/*     */       
/*  72 */       String[] parts = line.split(" ");
/*  73 */       if (line.startsWith("\t")) {
/*  74 */         if (fromClass == null) {
/*  75 */           throw new IllegalStateException("Error parsing TSRG file, found member declaration with no class: " + line);
/*     */         }
/*  77 */         parts[0] = parts[0].substring(1);
/*  78 */         if (parts.length == 2) {
/*  79 */           fieldMap.forcePut(new MappingField(fromClass, parts[0]), new MappingField(toClass, parts[1])); continue;
/*  80 */         }  if (parts.length == 3) {
/*  81 */           methodMap.forcePut(new MappingMethod(fromClass, parts[0], parts[1]), new MappingMethodLazy(toClass, parts[2], parts[1], (IMappingProvider)this));
/*     */           continue;
/*     */         } 
/*  84 */         throw new IllegalStateException("Error parsing TSRG file, too many arguments: " + line);
/*     */       } 
/*  86 */       if (parts.length > 1) {
/*  87 */         String from = parts[0];
/*  88 */         if (parts.length == 2) {
/*  89 */           String to = parts[1];
/*  90 */           if (from.endsWith("/")) {
/*  91 */             packageMap.forcePut(from.substring(0, from.length() - 1), to.substring(0, to.length() - 1)); continue;
/*     */           } 
/*  93 */           classMap.forcePut(from, to);
/*  94 */           fromClass = from;
/*  95 */           toClass = to; continue;
/*     */         } 
/*  97 */         if (parts.length > 2) {
/*  98 */           String to = (String)classMap.get(from);
/*  99 */           if (to == null) {
/* 100 */             throw new IllegalStateException("Error parsing TSRG file, found inline member before class mapping: " + line);
/*     */           }
/* 102 */           if (parts.length == 3) {
/* 103 */             fieldMap.forcePut(new MappingField(from, parts[1]), new MappingField(to, parts[2])); continue;
/* 104 */           }  if (parts.length == 4) {
/* 105 */             methodMap.forcePut(new MappingMethod(from, parts[1], parts[2]), new MappingMethodLazy(to, parts[3], parts[2], (IMappingProvider)this));
/*     */             continue;
/*     */           } 
/* 108 */           throw new IllegalStateException("Error parsing TSRG file, too many arguments: " + line);
/*     */         } 
/*     */         continue;
/*     */       } 
/* 112 */       throw new IllegalStateException("Error parsing TSRG, unrecognised directive: " + line);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingField getFieldMapping(MappingField field) {
/*     */     MappingFieldSrg mappingFieldSrg;
/* 120 */     if (field.getDesc() != null) {
/* 121 */       mappingFieldSrg = new MappingFieldSrg(field);
/*     */     }
/* 123 */     return (MappingField)this.fieldMap.get(mappingFieldSrg);
/*     */   }
/*     */   
/*     */   List<String> getInputMappings() {
/* 127 */     return this.inputMappings;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\mapping\fg3\MappingProviderTSrg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */