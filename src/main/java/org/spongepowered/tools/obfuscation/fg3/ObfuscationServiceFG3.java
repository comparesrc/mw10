/*    */ package org.spongepowered.tools.obfuscation.fg3;
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
/*    */ public class ObfuscationServiceFG3
/*    */   implements IObfuscationService
/*    */ {
/*    */   public static final String SEARGE = "searge";
/*    */   public static final String REOBF_TSRG_FILE = "reobfTsrgFile";
/*    */   public static final String REOBF_EXTRA_TSRG_FILES = "reobfTsrgFiles";
/*    */   public static final String OUT_TSRG_SRG_FILE = "outTsrgFile";
/*    */   public static final String TSRG_OUTPUT_BEHAVIOUR = "mergeBehaviour";
/*    */   
/*    */   public Set<String> getSupportedOptions() {
/* 53 */     return (Set<String>)ImmutableSet.of("reobfTsrgFile", "outTsrgFile", "mergeBehaviour");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<ObfuscationTypeDescriptor> getObfuscationTypes(IMixinAnnotationProcessor ap) {
/* 62 */     ImmutableList.Builder<ObfuscationTypeDescriptor> list = ImmutableList.builder();
/* 63 */     if (ap.getOptions("mappingTypes").contains("tsrg")) {
/* 64 */       list.add(new ObfuscationTypeDescriptor("searge", "reobfTsrgFile", "reobfTsrgFiles", "outTsrgFile", ObfuscationEnvironmentFG3.class));
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 74 */     return (Collection<ObfuscationTypeDescriptor>)list.build();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\fg3\ObfuscationServiceFG3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */