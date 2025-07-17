/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.tree.FieldNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.util.Counter;
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
/*     */ public class MethodMapper
/*     */ {
/*  52 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */   
/*  54 */   private static final List<String> classes = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private static final Map<String, Counter> methods = new HashMap<>();
/*     */   
/*     */   private final ClassInfo info;
/*     */   
/*     */   private int nextUniqueMethodIndex;
/*     */   
/*     */   private int nextUniqueFieldIndex;
/*     */ 
/*     */   
/*     */   public MethodMapper(MixinEnvironment env, ClassInfo info) {
/*  70 */     this.info = info;
/*     */   }
/*     */   
/*     */   public ClassInfo getClassInfo() {
/*  74 */     return this.info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remapHandlerMethod(MixinInfo mixin, MethodNode handler, ClassInfo.Method method) {
/*  85 */     if (!(handler instanceof MixinInfo.MixinMethodNode) || !((MixinInfo.MixinMethodNode)handler).isInjector()) {
/*     */       return;
/*     */     }
/*     */     
/*  89 */     if (method.isUnique()) {
/*  90 */       logger.warn("Redundant @Unique on injector method {} in {}. Injectors are implicitly unique", new Object[] { method, mixin });
/*     */     }
/*     */     
/*  93 */     if (method.isRenamed()) {
/*  94 */       handler.name = method.getName();
/*     */       
/*     */       return;
/*     */     } 
/*  98 */     String handlerName = getHandlerName((MixinInfo.MixinMethodNode)handler);
/*  99 */     handler.name = method.conform(handlerName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHandlerName(MixinInfo.MixinMethodNode method) {
/* 109 */     String prefix = InjectionInfo.getInjectorPrefix(method.getInjectorAnnotation());
/* 110 */     String classUID = getClassUID(method.getOwner().getClassRef());
/* 111 */     String methodUID = getMethodUID(method.name, method.desc, !method.isSurrogate());
/* 112 */     return String.format("%s$%s%s$%s", new Object[] { prefix, classUID, methodUID, method.name });
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
/*     */   public String getUniqueName(MethodNode method, String sessionId, boolean preservePrefix) {
/* 125 */     String uniqueIndex = Integer.toHexString(this.nextUniqueMethodIndex++);
/* 126 */     String pattern = preservePrefix ? "%2$s_$md$%1$s$%3$s" : "md%s$%s$%s";
/* 127 */     return String.format(pattern, new Object[] { sessionId.substring(30), method.name, uniqueIndex });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueName(FieldNode field, String sessionId) {
/* 138 */     String uniqueIndex = Integer.toHexString(this.nextUniqueFieldIndex++);
/* 139 */     return String.format("fd%s$%s$%s", new Object[] { sessionId.substring(30), field.name, uniqueIndex });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getClassUID(String classRef) {
/* 149 */     int index = classes.indexOf(classRef);
/* 150 */     if (index < 0) {
/* 151 */       index = classes.size();
/* 152 */       classes.add(classRef);
/*     */     } 
/* 154 */     return finagle(index);
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
/*     */   private static String getMethodUID(String name, String desc, boolean increment) {
/* 166 */     String descriptor = String.format("%s%s", new Object[] { name, desc });
/* 167 */     Counter id = methods.get(descriptor);
/* 168 */     if (id == null) {
/* 169 */       id = new Counter();
/* 170 */       methods.put(descriptor, id);
/* 171 */     } else if (increment) {
/* 172 */       id.value++;
/*     */     } 
/* 174 */     return String.format("%03x", new Object[] { Integer.valueOf(id.value) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String finagle(int index) {
/* 184 */     String hex = Integer.toHexString(index);
/* 185 */     StringBuilder sb = new StringBuilder();
/* 186 */     for (int pos = 0; pos < hex.length(); pos++) {
/* 187 */       char c = hex.charAt(pos);
/* 188 */       sb.append(c = (char)(c + ((c < ':') ? 49 : 10)));
/*     */     } 
/* 190 */     return Strings.padStart(sb.toString(), 3, 'z');
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\MethodMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */