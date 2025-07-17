/*    */ package org.spongepowered.asm.util;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
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
/*    */ public abstract class JavaVersion
/*    */ {
/* 35 */   private static double current = 0.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static double current() {
/* 43 */     if (current == 0.0D) {
/* 44 */       current = resolveCurrentVersion();
/*    */     }
/* 46 */     return current;
/*    */   }
/*    */   
/*    */   private static double resolveCurrentVersion() {
/* 50 */     String version = System.getProperty("java.version");
/* 51 */     Matcher decimalMatcher = Pattern.compile("[0-9]+\\.[0-9]+").matcher(version);
/* 52 */     if (decimalMatcher.find()) {
/* 53 */       return Double.parseDouble(decimalMatcher.group());
/*    */     }
/* 55 */     Matcher numberMatcher = Pattern.compile("[0-9]+").matcher(version);
/* 56 */     if (numberMatcher.find()) {
/* 57 */       return Double.parseDouble(numberMatcher.group());
/*    */     }
/* 59 */     return 1.6D;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\JavaVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */