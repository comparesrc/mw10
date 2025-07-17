/*     */ package org.spongepowered.asm.mixin.transformer.ext.extensions;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.common.io.Files;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IExtension;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
/*     */ import org.spongepowered.asm.util.Constants;
/*     */ import org.spongepowered.asm.util.PrettyPrinter;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtensionCheckInterfaces
/*     */   implements IExtension
/*     */ {
/*     */   private static final String AUDIT_DIR = "audit";
/*     */   private static final String IMPL_REPORT_FILENAME = "mixin_implementation_report";
/*     */   private static final String IMPL_REPORT_CSV_FILENAME = "mixin_implementation_report.csv";
/*     */   private static final String IMPL_REPORT_TXT_FILENAME = "mixin_implementation_report.txt";
/*  67 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final File csv;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final File report;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private final Multimap<ClassInfo, ClassInfo.Method> interfaceMethods = (Multimap<ClassInfo, ClassInfo.Method>)HashMultimap.create();
/*     */ 
/*     */   
/*     */   private boolean strict;
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionCheckInterfaces() {
/*  91 */     File debugOutputFolder = new File(Constants.DEBUG_OUTPUT_DIR, "audit");
/*  92 */     debugOutputFolder.mkdirs();
/*  93 */     this.csv = new File(debugOutputFolder, "mixin_implementation_report.csv");
/*  94 */     this.report = new File(debugOutputFolder, "mixin_implementation_report.txt");
/*     */     
/*  96 */     this.csv.getParentFile().mkdirs();
/*     */     
/*     */     try {
/*  99 */       Files.write("Class,Method,Signature,Interface\n", this.csv, Charsets.ISO_8859_1);
/* 100 */     } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 105 */       String dateTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 106 */       Files.write("Mixin Implementation Report generated on " + dateTime + "\n", this.report, Charsets.ISO_8859_1);
/* 107 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkActive(MixinEnvironment environment) {
/* 118 */     this.strict = environment.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS_STRICT);
/* 119 */     return environment.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preApply(ITargetClassContext context) {
/* 128 */     ClassInfo targetClassInfo = context.getClassInfo();
/* 129 */     for (ClassInfo.Method m : targetClassInfo.getInterfaceMethods(false)) {
/* 130 */       this.interfaceMethods.put(targetClassInfo, m);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postApply(ITargetClassContext context) {
/* 140 */     ClassInfo targetClassInfo = context.getClassInfo();
/*     */ 
/*     */     
/* 143 */     if (targetClassInfo.isAbstract() && !this.strict) {
/* 144 */       logger.info("{} is skipping abstract target {}", new Object[] { getClass().getSimpleName(), context });
/*     */       
/*     */       return;
/*     */     } 
/* 148 */     String className = targetClassInfo.getName().replace('/', '.');
/* 149 */     int missingMethodCount = 0;
/* 150 */     PrettyPrinter printer = new PrettyPrinter();
/*     */     
/* 152 */     printer.add("Class: %s", new Object[] { className }).hr();
/* 153 */     printer.add("%-32s %-47s  %s", new Object[] { "Return Type", "Missing Method", "From Interface" }).hr();
/*     */     
/* 155 */     Set<ClassInfo.Method> interfaceMethods = targetClassInfo.getInterfaceMethods(true);
/* 156 */     Set<ClassInfo.Method> implementedMethods = new HashSet<>(targetClassInfo.getSuperClass().getInterfaceMethods(true));
/* 157 */     implementedMethods.addAll(this.interfaceMethods.removeAll(targetClassInfo));
/*     */     
/* 159 */     for (ClassInfo.Method method : interfaceMethods) {
/* 160 */       ClassInfo.Method found = targetClassInfo.findMethodInHierarchy(method.getName(), method.getDesc(), ClassInfo.SearchType.ALL_CLASSES, ClassInfo.Traversal.ALL);
/*     */ 
/*     */       
/* 163 */       if (found != null && !found.isAbstract()) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 168 */       if (implementedMethods.contains(method)) {
/*     */         continue;
/*     */       }
/*     */       
/* 172 */       if (missingMethodCount > 0) {
/* 173 */         printer.add();
/*     */       }
/*     */       
/* 176 */       SignaturePrinter signaturePrinter = (new SignaturePrinter(method.getName(), method.getDesc())).setModifiers("");
/* 177 */       String iface = method.getOwner().getName().replace('/', '.');
/* 178 */       missingMethodCount++;
/* 179 */       printer.add("%-32s%s", new Object[] { signaturePrinter.getReturnType(), signaturePrinter });
/* 180 */       printer.add("%-80s  %s", new Object[] { "", iface });
/*     */       
/* 182 */       appendToCSVReport(className, method, iface);
/*     */     } 
/*     */     
/* 185 */     if (missingMethodCount > 0) {
/* 186 */       printer.hr().add("%82s%s: %d", new Object[] { "", "Total unimplemented", Integer.valueOf(missingMethodCount) });
/* 187 */       printer.print(System.err);
/* 188 */       appendToTextReport(printer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void export(MixinEnvironment env, String name, boolean force, ClassNode classNode) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void appendToCSVReport(String className, ClassInfo.Method method, String iface) {
/*     */     try {
/* 203 */       Files.append(String.format("%s,%s,%s,%s\n", new Object[] { className, method.getName(), method.getDesc(), iface }), this.csv, Charsets.ISO_8859_1);
/* 204 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void appendToTextReport(PrettyPrinter printer) {
/* 210 */     FileOutputStream fos = null;
/*     */     
/*     */     try {
/* 213 */       fos = new FileOutputStream(this.report, true);
/* 214 */       PrintStream stream = new PrintStream(fos);
/* 215 */       stream.print("\n");
/* 216 */       printer.print(stream);
/* 217 */     } catch (Exception exception) {
/*     */     
/*     */     } finally {
/* 220 */       if (fos != null)
/*     */         try {
/* 222 */           fos.close();
/* 223 */         } catch (IOException ex) {
/* 224 */           logger.catching(ex);
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\ext\extensions\ExtensionCheckInterfaces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */