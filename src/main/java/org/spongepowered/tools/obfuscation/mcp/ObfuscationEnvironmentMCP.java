/*    */ package org.spongepowered.tools.obfuscation.mcp;
/*    */ 
/*    */ import javax.annotation.processing.Filer;
/*    */ import javax.annotation.processing.Messager;
/*    */ import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;
/*    */ import org.spongepowered.tools.obfuscation.ObfuscationType;
/*    */ import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
/*    */ import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
/*    */ import org.spongepowered.tools.obfuscation.mapping.mcp.MappingProviderSrg;
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
/*    */ public class ObfuscationEnvironmentMCP
/*    */   extends ObfuscationEnvironment
/*    */ {
/*    */   protected ObfuscationEnvironmentMCP(ObfuscationType type) {
/* 43 */     super(type);
/*    */   }
/*    */ 
/*    */   
/*    */   protected IMappingProvider getMappingProvider(Messager messager, Filer filer) {
/* 48 */     return (IMappingProvider)new MappingProviderSrg(messager, filer);
/*    */   }
/*    */ 
/*    */   
/*    */   protected IMappingWriter getMappingWriter(Messager messager, Filer filer) {
/* 53 */     return (IMappingWriter)new MappingWriterSrg(messager, filer);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\mcp\ObfuscationEnvironmentMCP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */