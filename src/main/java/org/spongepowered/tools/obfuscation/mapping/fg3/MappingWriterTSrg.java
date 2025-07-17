/*    */ package org.spongepowered.tools.obfuscation.mapping.fg3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import javax.annotation.processing.Filer;
/*    */ import javax.annotation.processing.Messager;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*    */ import org.spongepowered.tools.obfuscation.ObfuscationType;
/*    */ import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
/*    */ import org.spongepowered.tools.obfuscation.mapping.mcp.MappingWriterSrg;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MappingWriterTSrg
/*    */   extends MappingWriterSrg
/*    */ {
/*    */   private final MappingProviderTSrg provider;
/*    */   private final boolean mergeExisting;
/*    */   
/*    */   public MappingWriterTSrg(Messager messager, Filer filer, MappingProviderTSrg provider, boolean mergeExisting) {
/* 48 */     super(messager, filer);
/* 49 */     this.provider = provider;
/* 50 */     this.mergeExisting = mergeExisting;
/*    */   }
/*    */ 
/*    */   
/*    */   protected PrintWriter openFileWriter(String output, ObfuscationType type) throws IOException {
/* 55 */     return openFileWriter(output, type + " composite mappings");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void writeHeader(PrintWriter writer) {
/* 60 */     if (this.mergeExisting) {
/* 61 */       for (String line : this.provider.getInputMappings()) {
/* 62 */         writer.println(line);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected String formatFieldMapping(IMappingConsumer.MappingSet.Pair<MappingField> field) {
/* 69 */     return String.format("%s %s %s", new Object[] { ((MappingField)field.from).getOwner(), ((MappingField)field.from).getSimpleName(), ((MappingField)field.to).getSimpleName() });
/*    */   }
/*    */ 
/*    */   
/*    */   protected String formatMethodMapping(IMappingConsumer.MappingSet.Pair<MappingMethod> method) {
/* 74 */     return String.format("%s %s %s %s", new Object[] { ((MappingMethod)method.from).getOwner(), ((MappingMethod)method.from).getSimpleName(), ((MappingMethod)method.from).getDesc(), ((MappingMethod)method.to).getSimpleName() });
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\mapping\fg3\MappingWriterTSrg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */