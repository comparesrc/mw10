/*    */ package org.spongepowered.tools.obfuscation.ext;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ public final class SpecialPackages
/*    */ {
/* 36 */   private static final Set<String> suppressWarningsForPackages = new HashSet<>();
/*    */   
/*    */   static {
/* 39 */     addExcludedPackage("java.");
/* 40 */     addExcludedPackage("javax.");
/* 41 */     addExcludedPackage("sun.");
/* 42 */     addExcludedPackage("com.sun.");
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final void addExcludedPackage(String packageName) {
/* 58 */     String internalName = packageName.replace('.', '/');
/* 59 */     if (!internalName.endsWith("/")) {
/* 60 */       internalName = internalName + "/";
/*    */     }
/* 62 */     suppressWarningsForPackages.add(internalName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isExcludedPackage(String internalName) {
/* 72 */     for (String prefix : suppressWarningsForPackages) {
/* 73 */       if (internalName.startsWith(prefix)) {
/* 74 */         return true;
/*    */       }
/*    */     } 
/* 77 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\ext\SpecialPackages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */