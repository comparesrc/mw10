/*     */ package org.spongepowered.asm.launch.platform;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public final class CommandLineOptions
/*     */ {
/*  37 */   private List<String> configs = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getConfigs() {
/*  43 */     return Collections.unmodifiableList(this.configs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseArgs(List<String> args) {
/*  52 */     boolean captureNext = false;
/*  53 */     for (String arg : args) {
/*  54 */       if (captureNext) {
/*  55 */         this.configs.add(arg);
/*     */       }
/*  57 */       captureNext = ("--mixin".equals(arg) || "--mixin.config".equals(arg));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CommandLineOptions defaultArgs() {
/*  67 */     return ofArgs(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CommandLineOptions ofArgs(List<String> args) {
/*  78 */     CommandLineOptions options = new CommandLineOptions();
/*  79 */     if (args == null) {
/*  80 */       String argv = System.getProperty("sun.java.command");
/*  81 */       if (argv != null) {
/*  82 */         args = Arrays.asList(argv.split(" "));
/*     */       }
/*     */     } 
/*  85 */     if (args != null) {
/*  86 */       options.parseArgs(args);
/*     */     }
/*  88 */     return options;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CommandLineOptions of(List<String> configs) {
/*  98 */     CommandLineOptions options = new CommandLineOptions();
/*  99 */     options.configs.addAll(configs);
/* 100 */     return options;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\CommandLineOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */