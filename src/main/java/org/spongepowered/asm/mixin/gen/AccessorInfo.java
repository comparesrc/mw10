/*     */ package org.spongepowered.asm.mixin.gen;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.FieldNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.asm.ElementNode;
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
/*     */ public class AccessorInfo
/*     */   extends SpecialMethodInfo
/*     */ {
/*     */   protected final Class<? extends Annotation> annotationClass;
/*     */   protected final Type[] argTypes;
/*     */   protected final Type returnType;
/*     */   protected final boolean isStatic;
/*     */   protected final String specifiedName;
/*     */   protected final AccessorType type;
/*     */   private final Type targetFieldType;
/*     */   protected final ITargetSelector target;
/*     */   protected FieldNode targetField;
/*     */   protected MethodNode targetMethod;
/*     */   protected AccessorGenerator generator;
/*     */   
/*     */   public enum AccessorType
/*     */   {
/*  70 */     FIELD_GETTER((String)ImmutableSet.of("get", "is"))
/*     */     {
/*     */       AccessorGenerator getGenerator(AccessorInfo info) {
/*  73 */         return new AccessorGeneratorFieldGetter(info);
/*     */       }
/*     */     },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     FIELD_SETTER((String)ImmutableSet.of("set"))
/*     */     {
/*     */       AccessorGenerator getGenerator(AccessorInfo info) {
/*  84 */         return new AccessorGeneratorFieldSetter(info);
/*     */       }
/*     */     },
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     METHOD_PROXY((String)ImmutableSet.of("call", "invoke"))
/*     */     {
/*     */       AccessorGenerator getGenerator(AccessorInfo info) {
/*  94 */         return new AccessorGeneratorMethodProxy(info);
/*     */       }
/*     */     },
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     OBJECT_FACTORY((String)ImmutableSet.of("new", "create"))
/*     */     {
/*     */       AccessorGenerator getGenerator(AccessorInfo info) {
/* 104 */         return new AccessorGeneratorObjectFactory(info);
/*     */       }
/*     */     };
/*     */     
/*     */     private final Set<String> expectedPrefixes;
/*     */     
/*     */     AccessorType(Set<String> expectedPrefixes) {
/* 111 */       this.expectedPrefixes = expectedPrefixes;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isExpectedPrefix(String prefix) {
/* 122 */       return this.expectedPrefixes.contains(prefix);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<String> getExpectedPrefixes() {
/* 133 */       return Collections.unmodifiableSet(this.expectedPrefixes);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract AccessorGenerator getGenerator(AccessorInfo param1AccessorInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class AccessorName
/*     */   {
/* 148 */     private static final Pattern PATTERN = Pattern.compile("^(" + getPrefixList() + ")(([A-Z])(.*?))(_\\$md.*)?$");
/*     */ 
/*     */ 
/*     */     
/*     */     public final String methodName;
/*     */ 
/*     */ 
/*     */     
/*     */     public final String prefix;
/*     */ 
/*     */ 
/*     */     
/*     */     public final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private AccessorName(String methodName, String prefix, String name) {
/* 166 */       this.methodName = methodName;
/* 167 */       this.prefix = prefix;
/* 168 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 173 */       return super.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static AccessorName of(String methodName) {
/* 185 */       return of(methodName, true);
/*     */     }
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
/*     */     public static AccessorName of(String methodName, boolean toMemberCase) {
/* 201 */       Matcher nameMatcher = PATTERN.matcher(methodName);
/* 202 */       if (nameMatcher.matches()) {
/* 203 */         String prefix = nameMatcher.group(1);
/* 204 */         String namePart = nameMatcher.group(2);
/* 205 */         String firstChar = nameMatcher.group(3);
/* 206 */         String remainder = nameMatcher.group(4);
/* 207 */         boolean nameIsUpperCase = isUpperCase(Locale.ROOT, namePart);
/*     */         
/* 209 */         String name = String.format("%s%s", new Object[] { toLowerCaseIf(Locale.ROOT, firstChar, (toMemberCase && !nameIsUpperCase)), remainder });
/* 210 */         return new AccessorName(methodName, prefix, name);
/*     */       } 
/* 212 */       return null;
/*     */     }
/*     */     
/*     */     private static boolean isUpperCase(Locale locale, String string) {
/* 216 */       return string.toUpperCase(locale).equals(string);
/*     */     }
/*     */     
/*     */     private static String toLowerCaseIf(Locale locale, String string, boolean condition) {
/* 220 */       return condition ? string.toLowerCase(locale) : string;
/*     */     }
/*     */     
/*     */     private static String getPrefixList() {
/* 224 */       List<String> prefixes = new ArrayList<>();
/* 225 */       for (AccessorInfo.AccessorType type : AccessorInfo.AccessorType.values()) {
/* 226 */         prefixes.addAll(type.getExpectedPrefixes());
/*     */       }
/* 228 */       return Joiner.on('|').join(prefixes);
/*     */     }
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
/*     */   public AccessorInfo(MixinTargetContext mixin, MethodNode method) {
/* 292 */     this(mixin, method, (Class)Accessor.class);
/*     */   }
/*     */   
/*     */   protected AccessorInfo(MixinTargetContext mixin, MethodNode method, Class<? extends Annotation> annotationClass) {
/* 296 */     super(mixin, method, Annotations.getVisible(method, annotationClass));
/* 297 */     this.annotationClass = annotationClass;
/* 298 */     this.argTypes = Type.getArgumentTypes(method.desc);
/* 299 */     this.returnType = Type.getReturnType(method.desc);
/* 300 */     this.isStatic = Bytecode.isStatic(method);
/* 301 */     this.specifiedName = (String)Annotations.getValue(this.annotation);
/* 302 */     this.type = initType();
/* 303 */     this.targetFieldType = initTargetFieldType();
/* 304 */     this.target = initTarget();
/*     */   }
/*     */   
/*     */   protected AccessorType initType() {
/* 308 */     if (this.returnType.equals(Type.VOID_TYPE)) {
/* 309 */       return AccessorType.FIELD_SETTER;
/*     */     }
/* 311 */     return AccessorType.FIELD_GETTER;
/*     */   }
/*     */   
/*     */   protected Type initTargetFieldType() {
/* 315 */     switch (this.type) {
/*     */       case FIELD_GETTER:
/* 317 */         if (this.argTypes.length > 0) {
/* 318 */           throw new InvalidAccessorException(this.mixin, this + " must take exactly 0 arguments, found " + this.argTypes.length);
/*     */         }
/* 320 */         return this.returnType;
/*     */       
/*     */       case FIELD_SETTER:
/* 323 */         if (this.argTypes.length != 1) {
/* 324 */           throw new InvalidAccessorException(this.mixin, this + " must take exactly 1 argument, found " + this.argTypes.length);
/*     */         }
/* 326 */         return this.argTypes[0];
/*     */     } 
/*     */     
/* 329 */     throw new InvalidAccessorException(this.mixin, "Computed unsupported accessor type " + this.type + " for " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ITargetSelector initTarget() {
/* 334 */     MemberInfo target = new MemberInfo(getTargetName(this.specifiedName), null, this.targetFieldType.getDescriptor());
/* 335 */     this.annotation.visit("target", target.toString());
/* 336 */     return (ITargetSelector)target;
/*     */   }
/*     */   
/*     */   protected String getTargetName(String name) {
/* 340 */     if (Strings.isNullOrEmpty(name)) {
/* 341 */       String inflectedTarget = inflectTarget();
/* 342 */       if (inflectedTarget == null) {
/* 343 */         throw new InvalidAccessorException(this.mixin, String.format("Failed to inflect target name for %s, supported prefixes: %s", new Object[] { this, this.type
/* 344 */                 .getExpectedPrefixes() }));
/*     */       }
/* 346 */       return inflectedTarget;
/*     */     } 
/* 348 */     return TargetSelector.parseName(name, (IMixinContext)this.mixin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String inflectTarget() {
/* 358 */     return inflectTarget(this.method.name, this.type, toString(), (IMixinContext)this.mixin, this.mixin
/* 359 */         .getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String inflectTarget(String name, AccessorType type, String description, IMixinContext context, boolean verbose) {
/* 380 */     return inflectTarget(AccessorName.of(name), type, description, context, verbose);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String inflectTarget(AccessorName name, AccessorType type, String description, IMixinContext context, boolean verbose) {
/* 401 */     if (name != null) {
/* 402 */       if (!type.isExpectedPrefix(name.prefix) && verbose) {
/* 403 */         LogManager.getLogger("mixin").warn("Unexpected prefix for {}, found [{}] expecting {}", new Object[] { description, name.prefix, type
/* 404 */               .getExpectedPrefixes() });
/*     */       }
/* 406 */       return TargetSelector.parseName(name.name, context);
/*     */     } 
/* 408 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ITargetSelector getTarget() {
/* 417 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Type getTargetFieldType() {
/* 424 */     return this.targetFieldType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final FieldNode getTargetField() {
/* 431 */     return this.targetField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MethodNode getTargetMethod() {
/* 438 */     return this.targetMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Type getReturnType() {
/* 445 */     return this.returnType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Type[] getArgTypes() {
/* 452 */     return this.argTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStatic() {
/* 459 */     return this.isStatic;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 464 */     String typeString = (this.type != null) ? this.type.toString() : "UNPARSED_ACCESSOR";
/* 465 */     return String.format("%s->@%s[%s]::%s%s", new Object[] { this.mixin, Bytecode.getSimpleName(this.annotation), typeString, this.methodName, this.method.desc });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void locate() {
/* 475 */     this.targetField = findTargetField();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/* 483 */     this.generator = this.type.getGenerator(this);
/* 484 */     this.generator.validate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodNode generate() {
/* 495 */     MethodNode generatedAccessor = this.generator.generate();
/* 496 */     Annotations.merge(this.method, generatedAccessor);
/* 497 */     return generatedAccessor;
/*     */   }
/*     */   
/*     */   private FieldNode findTargetField() {
/* 501 */     return findTarget(ElementNode.fieldList(this.classNode));
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
/*     */   protected <TNode> TNode findTarget(List<ElementNode<TNode>> nodes) {
/* 513 */     TargetSelector.Result<TNode> result = TargetSelector.run(this.target.configure(new String[] { "orphan" }, ), nodes);
/*     */     
/*     */     try {
/* 516 */       return (TNode)result.getSingleResult(true);
/* 517 */     } catch (IllegalStateException ex) {
/* 518 */       throw new InvalidAccessorException(this, ex.getMessage() + " matching " + this.target + " in " + this.classNode.name + " for " + this);
/*     */     } 
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
/*     */   public static AccessorInfo of(MixinTargetContext mixin, MethodNode method, Class<? extends Annotation> type) {
/* 532 */     if (type == Accessor.class)
/* 533 */       return new AccessorInfo(mixin, method); 
/* 534 */     if (type == Invoker.class) {
/* 535 */       return new InvokerInfo(mixin, method);
/*     */     }
/* 537 */     throw new InvalidAccessorException(mixin, "Could not parse accessor for unknown type " + type.getName());
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\gen\AccessorInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */