/*    */ package org.spongepowered.tools.obfuscation.fg3;
/*    */ 
/*    */ import javax.annotation.processing.Filer;
/*    */ import javax.annotation.processing.Messager;
/*    */ import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;
/*    */ import org.spongepowered.tools.obfuscation.ObfuscationType;
/*    */ import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
/*    */ import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
/*    */ import org.spongepowered.tools.obfuscation.mapping.fg3.MappingProviderTSrg;
/*    */ import org.spongepowered.tools.obfuscation.mapping.fg3.MappingWriterTSrg;
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
/*    */ public class ObfuscationEnvironmentFG3
/*    */   extends ObfuscationEnvironment
/*    */ {
/*    */   private MappingProviderTSrg provider;
/*    */   
/*    */   protected ObfuscationEnvironmentFG3(ObfuscationType type) {
/* 45 */     super(type);
/*    */   }
/*    */ 
/*    */   
/*    */   protected IMappingProvider getMappingProvider(Messager messager, Filer filer) {
/* 50 */     return (IMappingProvider)(this.provider = new MappingProviderTSrg(messager, filer));
/*    */   }
/*    */ 
/*    */   
/*    */   protected IMappingWriter getMappingWriter(Messager messager, Filer filer) {
/* 55 */     String outputBehaviour = this.ap.getOption("mergeBehaviour");
/* 56 */     return (IMappingWriter)new MappingWriterTSrg(messager, filer, this.provider, (outputBehaviour != null && outputBehaviour.equalsIgnoreCase("merge")));
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\fg3\ObfuscationEnvironmentFG3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */