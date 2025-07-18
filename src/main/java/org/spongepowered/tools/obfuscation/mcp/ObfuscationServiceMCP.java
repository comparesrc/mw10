/*    */ package org.spongepowered.tools.obfuscation.mcp;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*    */ import org.spongepowered.tools.obfuscation.service.IObfuscationService;
/*    */ import org.spongepowered.tools.obfuscation.service.ObfuscationTypeDescriptor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObfuscationServiceMCP
/*    */   implements IObfuscationService
/*    */ {
/*    */   public static final String SEARGE = "searge";
/*    */   public static final String NOTCH = "notch";
/*    */   public static final String REOBF_SRG_FILE = "reobfSrgFile";
/*    */   public static final String REOBF_EXTRA_SRG_FILES = "reobfSrgFiles";
/*    */   public static final String REOBF_NOTCH_FILE = "reobfNotchSrgFile";
/*    */   public static final String REOBF_EXTRA_NOTCH_FILES = "reobfNotchSrgFiles";
/*    */   public static final String OUT_SRG_SRG_FILE = "outSrgFile";
/*    */   public static final String OUT_NOTCH_SRG_FILE = "outNotchSrgFile";
/*    */   
/*    */   public Set<String> getSupportedOptions() {
/* 56 */     return (Set<String>)ImmutableSet.of("reobfSrgFile", "reobfSrgFiles", "reobfNotchSrgFile", "reobfNotchSrgFiles", "outSrgFile", "outNotchSrgFile", (Object[])new String[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<ObfuscationTypeDescriptor> getObfuscationTypes(IMixinAnnotationProcessor ap) {
/* 68 */     ImmutableList.Builder<ObfuscationTypeDescriptor> list = ImmutableList.builder();
/* 69 */     if (!ap.getOptions("mappingTypes").contains("tsrg")) {
/* 70 */       list.add(new ObfuscationTypeDescriptor("searge", "reobfSrgFile", "reobfSrgFiles", "outSrgFile", ObfuscationEnvironmentMCP.class));
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 80 */     list.add(new ObfuscationTypeDescriptor("notch", "reobfNotchSrgFile", "reobfNotchSrgFiles", "outNotchSrgFile", ObfuscationEnvironmentMCP.class));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 89 */     return (Collection<ObfuscationTypeDescriptor>)list.build();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\mcp\ObfuscationServiceMCP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */