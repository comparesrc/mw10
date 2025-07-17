/*    */ package org.spongepowered.tools.obfuscation.mapping.fg3;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*    */ import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
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
/*    */ public class MappingMethodLazy
/*    */   extends MappingMethod
/*    */ {
/* 40 */   private static final Pattern PATTERN_CLASSNAME = Pattern.compile("L([^;]+);");
/*    */   
/*    */   private final String originalDesc;
/*    */   
/*    */   private final IMappingProvider mappingProvider;
/*    */   
/*    */   private String newDesc;
/*    */   
/*    */   public MappingMethodLazy(String owner, String simpleName, String originalDesc, IMappingProvider mappingProvider) {
/* 49 */     super(owner, simpleName, "{" + originalDesc + "}");
/* 50 */     this.originalDesc = originalDesc;
/* 51 */     this.mappingProvider = mappingProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDesc() {
/* 56 */     if (this.newDesc == null) {
/* 57 */       this.newDesc = generateDescriptor();
/*    */     }
/* 59 */     return this.newDesc;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 64 */     String desc = getDesc();
/* 65 */     return String.format("%s%s%s", new Object[] { getName(), (desc != null) ? " " : "", (desc != null) ? desc : "" });
/*    */   }
/*    */   
/*    */   private String generateDescriptor() {
/* 69 */     StringBuffer desc = new StringBuffer();
/* 70 */     Matcher matcher = PATTERN_CLASSNAME.matcher(this.originalDesc);
/* 71 */     while (matcher.find()) {
/* 72 */       String remapped = this.mappingProvider.getClassMapping(matcher.group(1));
/* 73 */       if (remapped != null) {
/* 74 */         matcher.appendReplacement(desc, Matcher.quoteReplacement("L" + remapped + ";")); continue;
/*    */       } 
/* 76 */       matcher.appendReplacement(desc, Matcher.quoteReplacement("L" + matcher.group(1) + ";"));
/*    */     } 
/*    */     
/* 79 */     matcher.appendTail(desc);
/* 80 */     return desc.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\mapping\fg3\MappingMethodLazy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */