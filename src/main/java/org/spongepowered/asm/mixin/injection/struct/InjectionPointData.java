/*     */ package org.spongepowered.asm.mixin.injection.struct;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AnnotationNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionPointException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
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
/*     */ 
/*     */ public class InjectionPointData
/*     */ {
/*  57 */   private static final Pattern AT_PATTERN = createPattern();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private final Map<String, String> args = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IMixinContext context;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MethodNode method;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final AnnotationNode parent;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String at;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String type;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final InjectionPoint.Selector selector;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String target;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String slice;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int ordinal;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int opcode;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String id;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectionPointData(IMixinContext context, MethodNode method, AnnotationNode parent, String at, List<String> args, String target, String slice, int ordinal, int opcode, String id) {
/* 121 */     this.context = context;
/* 122 */     this.method = method;
/* 123 */     this.parent = parent;
/* 124 */     this.at = at;
/* 125 */     this.target = target;
/* 126 */     this.slice = Strings.nullToEmpty(slice);
/* 127 */     this.ordinal = Math.max(-1, ordinal);
/* 128 */     this.opcode = opcode;
/* 129 */     this.id = id;
/*     */     
/* 131 */     parseArgs(args);
/*     */     
/* 133 */     this.args.put("target", target);
/* 134 */     this.args.put("ordinal", String.valueOf(ordinal));
/* 135 */     this.args.put("opcode", String.valueOf(opcode));
/*     */     
/* 137 */     Matcher matcher = AT_PATTERN.matcher(at);
/* 138 */     this.type = parseType(matcher, at);
/* 139 */     this.selector = parseSelector(matcher);
/*     */   }
/*     */   
/*     */   private void parseArgs(List<String> args) {
/* 143 */     if (args == null) {
/*     */       return;
/*     */     }
/* 146 */     for (String arg : args) {
/* 147 */       if (arg != null) {
/* 148 */         int eqPos = arg.indexOf('=');
/* 149 */         if (eqPos > -1) {
/* 150 */           this.args.put(arg.substring(0, eqPos), arg.substring(eqPos + 1)); continue;
/*     */         } 
/* 152 */         this.args.put(arg, "");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAt() {
/* 162 */     return this.at;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 169 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectionPoint.Selector getSelector() {
/* 176 */     return this.selector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMixinContext getContext() {
/* 183 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodNode getMethod() {
/* 190 */     return this.method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getMethodReturnType() {
/* 197 */     return Type.getReturnType(this.method.desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationNode getParent() {
/* 204 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSlice() {
/* 211 */     return this.slice;
/*     */   }
/*     */   
/*     */   public LocalVariableDiscriminator getLocalVariableDiscriminator() {
/* 215 */     return LocalVariableDiscriminator.parse(this.parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String get(String key, String defaultValue) {
/* 227 */     String value = this.args.get(key);
/* 228 */     return (value != null) ? value : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(String key, int defaultValue) {
/* 240 */     return parseInt(get(key, String.valueOf(defaultValue)), defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean get(String key, boolean defaultValue) {
/* 252 */     return parseBoolean(get(key, String.valueOf(defaultValue)), defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITargetSelector get(String key) {
/*     */     try {
/* 264 */       return TargetSelector.parseAndValidate(get(key, ""), this.context);
/* 265 */     } catch (InvalidMemberDescriptorException ex) {
/* 266 */       throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\").%s descriptor \"%s\" on %s", new Object[] { this.at, key, this.target, 
/* 267 */             getDescription() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITargetSelector getTarget() {
/*     */     try {
/* 276 */       return TargetSelector.parseAndValidate(this.target, this.context);
/* 277 */     } catch (InvalidMemberDescriptorException ex) {
/* 278 */       throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\").target descriptor \"%s\" on %s", new Object[] { this.at, this.target, 
/* 279 */             getDescription() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 287 */     return InjectionInfo.describeInjector(this.context, this.parent, this.method);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrdinal() {
/* 294 */     return this.ordinal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOpcode() {
/* 301 */     return this.opcode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOpcode(int defaultOpcode) {
/* 312 */     return (this.opcode > 0) ? this.opcode : defaultOpcode;
/*     */   }
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
/*     */   public int getOpcode(int defaultOpcode, int... validOpcodes) {
/* 325 */     for (int validOpcode : validOpcodes) {
/* 326 */       if (this.opcode == validOpcode) {
/* 327 */         return this.opcode;
/*     */       }
/*     */     } 
/* 330 */     return defaultOpcode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 337 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 342 */     return this.type;
/*     */   }
/*     */   
/*     */   private static Pattern createPattern() {
/* 346 */     return Pattern.compile(String.format("^([^:]+):?(%s)?$", new Object[] { Joiner.on('|').join((Object[])InjectionPoint.Selector.values()) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String parseType(String at) {
/* 356 */     Matcher matcher = AT_PATTERN.matcher(at);
/* 357 */     return parseType(matcher, at);
/*     */   }
/*     */   
/*     */   private static String parseType(Matcher matcher, String at) {
/* 361 */     return matcher.matches() ? matcher.group(1) : at;
/*     */   }
/*     */   
/*     */   private static InjectionPoint.Selector parseSelector(Matcher matcher) {
/* 365 */     return (matcher.matches() && matcher.group(2) != null) ? InjectionPoint.Selector.valueOf(matcher.group(2)) : InjectionPoint.Selector.DEFAULT;
/*     */   }
/*     */   
/*     */   private static int parseInt(String string, int defaultValue) {
/*     */     try {
/* 370 */       return Integer.parseInt(string);
/* 371 */     } catch (Exception ex) {
/* 372 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean parseBoolean(String string, boolean defaultValue) {
/*     */     try {
/* 378 */       return Boolean.parseBoolean(string);
/* 379 */     } catch (Exception ex) {
/* 380 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\struct\InjectionPointData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */