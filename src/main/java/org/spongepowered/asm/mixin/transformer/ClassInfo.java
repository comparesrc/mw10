/*      */ package org.spongepowered.asm.mixin.transformer;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.ImmutableSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.objectweb.asm.tree.AbstractInsnNode;
/*      */ import org.objectweb.asm.tree.ClassNode;
/*      */ import org.objectweb.asm.tree.FieldInsnNode;
/*      */ import org.objectweb.asm.tree.FieldNode;
/*      */ import org.objectweb.asm.tree.FrameNode;
/*      */ import org.objectweb.asm.tree.MethodInsnNode;
/*      */ import org.objectweb.asm.tree.MethodNode;
/*      */ import org.spongepowered.asm.mixin.Final;
/*      */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*      */ import org.spongepowered.asm.mixin.Mutable;
/*      */ import org.spongepowered.asm.mixin.Shadow;
/*      */ import org.spongepowered.asm.mixin.Unique;
/*      */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*      */ import org.spongepowered.asm.mixin.gen.Accessor;
/*      */ import org.spongepowered.asm.mixin.gen.Invoker;
/*      */ import org.spongepowered.asm.service.MixinService;
/*      */ import org.spongepowered.asm.util.Annotations;
/*      */ import org.spongepowered.asm.util.ClassSignature;
/*      */ import org.spongepowered.asm.util.Locals;
/*      */ import org.spongepowered.asm.util.perf.Profiler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ClassInfo
/*      */ {
/*      */   public static final int INCLUDE_PRIVATE = 2;
/*      */   public static final int INCLUDE_STATIC = 8;
/*      */   public static final int INCLUDE_ALL = 10;
/*      */   public static final int INCLUDE_INITIALISERS = 262144;
/*      */   
/*      */   public enum SearchType
/*      */   {
/*  105 */     ALL_CLASSES,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  110 */     SUPER_CLASSES_ONLY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum TypeLookup
/*      */   {
/*  126 */     DECLARED_TYPE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  131 */     ELEMENT_TYPE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Traversal
/*      */   {
/*  155 */     NONE(null, false, (Traversal)ClassInfo.SearchType.SUPER_CLASSES_ONLY),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  160 */     ALL(null, true, (Traversal)ClassInfo.SearchType.ALL_CLASSES),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  165 */     IMMEDIATE((String)NONE, true, (Traversal)ClassInfo.SearchType.SUPER_CLASSES_ONLY),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  171 */     SUPER((String)ALL, false, (Traversal)ClassInfo.SearchType.SUPER_CLASSES_ONLY);
/*      */     
/*      */     private final Traversal next;
/*      */     
/*      */     private final boolean traverse;
/*      */     
/*      */     private final ClassInfo.SearchType searchType;
/*      */     
/*      */     Traversal(Traversal next, boolean traverse, ClassInfo.SearchType searchType) {
/*  180 */       this.next = (next != null) ? next : this;
/*  181 */       this.traverse = traverse;
/*  182 */       this.searchType = searchType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Traversal next() {
/*  189 */       return this.next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean canTraverse() {
/*  196 */       return this.traverse;
/*      */     }
/*      */     
/*      */     public ClassInfo.SearchType getSearchType() {
/*  200 */       return this.searchType;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class FrameData
/*      */   {
/*  210 */     private static final String[] FRAMETYPES = new String[] { "NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1" };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int index;
/*      */ 
/*      */ 
/*      */     
/*      */     public final int type;
/*      */ 
/*      */ 
/*      */     
/*      */     public final int locals;
/*      */ 
/*      */ 
/*      */     
/*      */     public final int size;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     FrameData(int index, int type, int locals, int size) {
/*  233 */       this.index = index;
/*  234 */       this.type = type;
/*  235 */       this.locals = locals;
/*  236 */       this.size = size;
/*      */     }
/*      */     
/*      */     FrameData(int index, FrameNode frameNode) {
/*  240 */       this.index = index;
/*  241 */       this.type = frameNode.type;
/*  242 */       this.locals = (frameNode.local != null) ? frameNode.local.size() : 0;
/*  243 */       this.size = Locals.computeFrameSize(frameNode);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  251 */       return String.format("FrameData[index=%d, type=%s, locals=%d]", new Object[] { Integer.valueOf(this.index), FRAMETYPES[this.type + 1], Integer.valueOf(this.locals) });
/*      */     } }
/*      */   static abstract class Member { private final Type type;
/*      */     private final String memberName;
/*      */     private final String memberDesc;
/*      */     private final boolean isInjected;
/*      */     private final int modifiers;
/*      */     private String currentName;
/*      */     private String currentDesc;
/*      */     private boolean decoratedFinal;
/*      */     private boolean decoratedMutable;
/*      */     private boolean unique;
/*      */     
/*  264 */     enum Type { METHOD,
/*  265 */       FIELD; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Member(Member member) {
/*  322 */       this(member.type, member.memberName, member.memberDesc, member.modifiers, member.isInjected);
/*  323 */       this.currentName = member.currentName;
/*  324 */       this.currentDesc = member.currentDesc;
/*  325 */       this.unique = member.unique;
/*      */     }
/*      */     
/*      */     protected Member(Type type, String name, String desc, int access) {
/*  329 */       this(type, name, desc, access, false);
/*      */     }
/*      */     
/*      */     protected Member(Type type, String name, String desc, int access, boolean injected) {
/*  333 */       this.type = type;
/*  334 */       this.memberName = name;
/*  335 */       this.memberDesc = desc;
/*  336 */       this.isInjected = injected;
/*  337 */       this.currentName = name;
/*  338 */       this.currentDesc = desc;
/*  339 */       this.modifiers = access;
/*      */     }
/*      */     
/*      */     public String getOriginalName() {
/*  343 */       return this.memberName;
/*      */     }
/*      */     
/*      */     public String getName() {
/*  347 */       return this.currentName;
/*      */     }
/*      */     
/*      */     public String getOriginalDesc() {
/*  351 */       return this.memberDesc;
/*      */     }
/*      */     
/*      */     public String getDesc() {
/*  355 */       return this.currentDesc;
/*      */     }
/*      */     
/*      */     public boolean isInjected() {
/*  359 */       return this.isInjected;
/*      */     }
/*      */     
/*      */     public boolean isRenamed() {
/*  363 */       return !this.currentName.equals(this.memberName);
/*      */     }
/*      */     
/*      */     public boolean isRemapped() {
/*  367 */       return !this.currentDesc.equals(this.memberDesc);
/*      */     }
/*      */     
/*      */     public boolean isPrivate() {
/*  371 */       return ((this.modifiers & 0x2) != 0);
/*      */     }
/*      */     
/*      */     public boolean isStatic() {
/*  375 */       return ((this.modifiers & 0x8) != 0);
/*      */     }
/*      */     
/*      */     public boolean isAbstract() {
/*  379 */       return ((this.modifiers & 0x400) != 0);
/*      */     }
/*      */     
/*      */     public boolean isFinal() {
/*  383 */       return ((this.modifiers & 0x10) != 0);
/*      */     }
/*      */     
/*      */     public boolean isSynthetic() {
/*  387 */       return ((this.modifiers & 0x1000) != 0);
/*      */     }
/*      */     
/*      */     public boolean isUnique() {
/*  391 */       return this.unique;
/*      */     }
/*      */     
/*      */     public void setUnique(boolean unique) {
/*  395 */       this.unique = unique;
/*      */     }
/*      */     
/*      */     public boolean isDecoratedFinal() {
/*  399 */       return this.decoratedFinal;
/*      */     }
/*      */     
/*      */     public boolean isDecoratedMutable() {
/*  403 */       return this.decoratedMutable;
/*      */     }
/*      */     
/*      */     protected void setDecoratedFinal(boolean decoratedFinal, boolean decoratedMutable) {
/*  407 */       this.decoratedFinal = decoratedFinal;
/*  408 */       this.decoratedMutable = decoratedMutable;
/*      */     }
/*      */     
/*      */     public boolean matchesFlags(int flags) {
/*  412 */       return (((this.modifiers ^ 0xFFFFFFFF | flags & 0x2) & 0x2) != 0 && ((this.modifiers ^ 0xFFFFFFFF | flags & 0x8) & 0x8) != 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public abstract ClassInfo getOwner();
/*      */ 
/*      */     
/*      */     public ClassInfo getImplementor() {
/*  420 */       return getOwner();
/*      */     }
/*      */     
/*      */     public int getAccess() {
/*  424 */       return this.modifiers;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String renameTo(String name) {
/*  432 */       this.currentName = name;
/*  433 */       return name;
/*      */     }
/*      */     
/*      */     public String remapTo(String desc) {
/*  437 */       this.currentDesc = desc;
/*  438 */       return desc;
/*      */     }
/*      */     
/*      */     public boolean equals(String name, String desc) {
/*  442 */       return ((this.memberName.equals(name) || this.currentName.equals(name)) && (this.memberDesc
/*  443 */         .equals(desc) || this.currentDesc.equals(desc)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  448 */       if (!(obj instanceof Member)) {
/*  449 */         return false;
/*      */       }
/*      */       
/*  452 */       Member other = (Member)obj;
/*  453 */       return ((other.memberName.equals(this.memberName) || other.currentName.equals(this.currentName)) && (other.memberDesc
/*  454 */         .equals(this.memberDesc) || other.currentDesc.equals(this.currentDesc)));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  459 */       return toString().hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  464 */       return String.format(getDisplayFormat(), new Object[] { this.memberName, this.memberDesc });
/*      */     }
/*      */     
/*      */     protected String getDisplayFormat() {
/*  468 */       return "%s%s";
/*      */     } }
/*      */ 
/*      */   
/*      */   enum Type
/*      */   {
/*      */     METHOD, FIELD;
/*      */   }
/*      */   
/*      */   public class Method
/*      */     extends Member
/*      */   {
/*      */     private final List<ClassInfo.FrameData> frames;
/*      */     private boolean isAccessor;
/*      */     private boolean conformed;
/*      */     
/*      */     public Method(ClassInfo.Member member) {
/*  485 */       super(member);
/*  486 */       this.frames = (member instanceof Method) ? ((Method)member).frames : null;
/*      */     }
/*      */     
/*      */     public Method(MethodNode method) {
/*  490 */       this(method, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public Method(MethodNode method, boolean injected) {
/*  495 */       super(ClassInfo.Member.Type.METHOD, method.name, method.desc, method.access, injected);
/*  496 */       this.frames = gatherFrames(method);
/*  497 */       setUnique((Annotations.getVisible(method, Unique.class) != null));
/*  498 */       this.isAccessor = (Annotations.getSingleVisible(method, new Class[] { Accessor.class, Invoker.class }) != null);
/*  499 */       boolean decoratedFinal = (Annotations.getVisible(method, Final.class) != null);
/*  500 */       boolean decoratedMutable = (Annotations.getVisible(method, Mutable.class) != null);
/*  501 */       setDecoratedFinal(decoratedFinal, decoratedMutable);
/*      */     }
/*      */     
/*      */     public Method(String name, String desc) {
/*  505 */       super(ClassInfo.Member.Type.METHOD, name, desc, 1, false);
/*  506 */       this.frames = null;
/*      */     }
/*      */     
/*      */     public Method(String name, String desc, int access) {
/*  510 */       super(ClassInfo.Member.Type.METHOD, name, desc, access, false);
/*  511 */       this.frames = null;
/*      */     }
/*      */     
/*      */     public Method(String name, String desc, int access, boolean injected) {
/*  515 */       super(ClassInfo.Member.Type.METHOD, name, desc, access, injected);
/*  516 */       this.frames = null;
/*      */     }
/*      */     
/*      */     private List<ClassInfo.FrameData> gatherFrames(MethodNode method) {
/*  520 */       List<ClassInfo.FrameData> frames = new ArrayList<>();
/*  521 */       for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  522 */         AbstractInsnNode insn = iter.next();
/*  523 */         if (insn instanceof FrameNode) {
/*  524 */           frames.add(new ClassInfo.FrameData(method.instructions.indexOf(insn), (FrameNode)insn));
/*      */         }
/*      */       } 
/*  527 */       return frames;
/*      */     }
/*      */     
/*      */     public List<ClassInfo.FrameData> getFrames() {
/*  531 */       return this.frames;
/*      */     }
/*      */ 
/*      */     
/*      */     public ClassInfo getOwner() {
/*  536 */       return ClassInfo.this;
/*      */     }
/*      */     
/*      */     public boolean isAccessor() {
/*  540 */       return this.isAccessor;
/*      */     }
/*      */     
/*      */     public boolean isConformed() {
/*  544 */       return this.conformed;
/*      */     }
/*      */ 
/*      */     
/*      */     public String renameTo(String name) {
/*  549 */       this.conformed = false;
/*  550 */       return super.renameTo(name);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String conform(String name) {
/*  558 */       boolean nameChanged = !name.equals(getName());
/*  559 */       if (this.conformed && nameChanged) {
/*  560 */         throw new IllegalStateException("Method " + this + " was already conformed. Original= " + getOriginalName() + " Current=" + 
/*  561 */             getName() + " New=" + name);
/*      */       }
/*  563 */       if (nameChanged) {
/*  564 */         renameTo(name);
/*  565 */         this.conformed = true;
/*      */       } 
/*  567 */       return name;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  572 */       if (!(obj instanceof Method)) {
/*  573 */         return false;
/*      */       }
/*      */       
/*  576 */       return super.equals(obj);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class InterfaceMethod
/*      */     extends Method
/*      */   {
/*      */     private final ClassInfo owner;
/*      */ 
/*      */ 
/*      */     
/*      */     public InterfaceMethod(ClassInfo.Member member) {
/*  590 */       super(member);
/*  591 */       this.owner = member.getOwner();
/*      */     }
/*      */ 
/*      */     
/*      */     public ClassInfo getOwner() {
/*  596 */       return this.owner;
/*      */     }
/*      */ 
/*      */     
/*      */     public ClassInfo getImplementor() {
/*  601 */       return ClassInfo.this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class Field
/*      */     extends Member
/*      */   {
/*      */     public Field(ClassInfo.Member member) {
/*  612 */       super(member);
/*      */     }
/*      */     
/*      */     public Field(FieldNode field) {
/*  616 */       this(field, false);
/*      */     }
/*      */     
/*      */     public Field(FieldNode field, boolean injected) {
/*  620 */       super(ClassInfo.Member.Type.FIELD, field.name, field.desc, field.access, injected);
/*      */       
/*  622 */       setUnique((Annotations.getVisible(field, Unique.class) != null));
/*      */       
/*  624 */       if (Annotations.getVisible(field, Shadow.class) != null) {
/*  625 */         boolean decoratedFinal = (Annotations.getVisible(field, Final.class) != null);
/*  626 */         boolean decoratedMutable = (Annotations.getVisible(field, Mutable.class) != null);
/*  627 */         setDecoratedFinal(decoratedFinal, decoratedMutable);
/*      */       } 
/*      */     }
/*      */     
/*      */     public Field(String name, String desc, int access) {
/*  632 */       super(ClassInfo.Member.Type.FIELD, name, desc, access, false);
/*      */     }
/*      */     
/*      */     public Field(String name, String desc, int access, boolean injected) {
/*  636 */       super(ClassInfo.Member.Type.FIELD, name, desc, access, injected);
/*      */     }
/*      */ 
/*      */     
/*      */     public ClassInfo getOwner() {
/*  641 */       return ClassInfo.this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  646 */       if (!(obj instanceof Field)) {
/*  647 */         return false;
/*      */       }
/*      */       
/*  650 */       return super.equals(obj);
/*      */     }
/*      */ 
/*      */     
/*      */     protected String getDisplayFormat() {
/*  655 */       return "%s:%s";
/*      */     }
/*      */   }
/*      */   
/*  659 */   private static final Logger logger = LogManager.getLogger("mixin");
/*      */   
/*  661 */   private static final Profiler profiler = MixinEnvironment.getProfiler();
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String JAVA_LANG_OBJECT = "java/lang/Object";
/*      */ 
/*      */ 
/*      */   
/*  669 */   private static final Map<String, ClassInfo> cache = new HashMap<>();
/*      */   
/*  671 */   private static final ClassInfo OBJECT = new ClassInfo(); private final String name; private final String superName; private final String outerName; private final boolean isProbablyStatic;
/*      */   
/*      */   static {
/*  674 */     cache.put("java/lang/Object", OBJECT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Set<String> interfaces;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Set<Method> initialisers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Set<Method> methods;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Set<Field> fields;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Set<MixinInfo> mixins;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  727 */   private final Map<ClassInfo, ClassInfo> correspondingTypes = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final MixinInfo mixin;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final MethodMapper methodMapper;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean isMixin;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean isInterface;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int access;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassInfo superClass;
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassInfo outerClass;
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassSignature signature;
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<MixinInfo> appliedMixins;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassInfo() {
/*  775 */     this.name = "java/lang/Object";
/*  776 */     this.superName = null;
/*  777 */     this.outerName = null;
/*  778 */     this.isProbablyStatic = true;
/*  779 */     this.initialisers = (Set<Method>)ImmutableSet.of(new Method("<init>", "()V"));
/*      */ 
/*      */     
/*  782 */     this.methods = (Set<Method>)ImmutableSet.of(new Method("getClass", "()Ljava/lang/Class;"), new Method("hashCode", "()I"), new Method("equals", "(Ljava/lang/Object;)Z"), new Method("clone", "()Ljava/lang/Object;"), new Method("toString", "()Ljava/lang/String;"), new Method("notify", "()V"), (Object[])new Method[] { new Method("notifyAll", "()V"), new Method("wait", "(J)V"), new Method("wait", "(JI)V"), new Method("wait", "()V"), new Method("finalize", "()V") });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  795 */     this.fields = Collections.emptySet();
/*  796 */     this.isInterface = false;
/*  797 */     this.interfaces = Collections.emptySet();
/*  798 */     this.access = 1;
/*  799 */     this.isMixin = false;
/*  800 */     this.mixin = null;
/*  801 */     this.mixins = Collections.emptySet();
/*  802 */     this.methodMapper = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassInfo(ClassNode classNode) {
/*  811 */     Profiler.Section timer = profiler.begin(1, "class.meta");
/*      */     try {
/*  813 */       this.name = classNode.name;
/*  814 */       this.superName = (classNode.superName != null) ? classNode.superName : "java/lang/Object";
/*  815 */       this.initialisers = new HashSet<>();
/*  816 */       this.methods = new HashSet<>();
/*  817 */       this.fields = new HashSet<>();
/*  818 */       this.isInterface = ((classNode.access & 0x200) != 0);
/*  819 */       this.interfaces = new HashSet<>();
/*  820 */       this.access = classNode.access;
/*  821 */       this.isMixin = classNode instanceof MixinInfo.MixinClassNode;
/*  822 */       this.mixin = this.isMixin ? ((MixinInfo.MixinClassNode)classNode).getMixin() : null;
/*  823 */       this.mixins = this.isMixin ? Collections.<MixinInfo>emptySet() : new HashSet<>();
/*      */       
/*  825 */       this.interfaces.addAll(classNode.interfaces);
/*      */       
/*  827 */       for (MethodNode method : classNode.methods) {
/*  828 */         addMethod(method, this.isMixin);
/*      */       }
/*      */       
/*  831 */       boolean isProbablyStatic = true;
/*  832 */       String outerName = classNode.outerClass;
/*  833 */       for (FieldNode field : classNode.fields) {
/*  834 */         if ((field.access & 0x1000) != 0 && 
/*  835 */           field.name.startsWith("this$")) {
/*  836 */           isProbablyStatic = false;
/*  837 */           if (outerName == null) {
/*  838 */             outerName = field.desc;
/*  839 */             if (outerName != null && outerName.startsWith("L")) {
/*  840 */               outerName = outerName.substring(1, outerName.length() - 1);
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  846 */         this.fields.add(new Field(field, this.isMixin));
/*      */       } 
/*      */       
/*  849 */       this.isProbablyStatic = isProbablyStatic;
/*  850 */       this.outerName = outerName;
/*  851 */       this.methodMapper = new MethodMapper(MixinEnvironment.getCurrentEnvironment(), this);
/*  852 */       this.signature = ClassSignature.ofLazy(classNode);
/*      */     } finally {
/*  854 */       timer.end();
/*      */     } 
/*      */   }
/*      */   
/*      */   void addInterface(String iface) {
/*  859 */     this.interfaces.add(iface);
/*  860 */     getSignature().addInterface(iface);
/*      */   }
/*      */   
/*      */   void addMethod(MethodNode method) {
/*  864 */     addMethod(method, true);
/*      */   }
/*      */   
/*      */   private void addMethod(MethodNode method, boolean injected) {
/*  868 */     if (method.name.startsWith("<")) {
/*  869 */       this.initialisers.add(new Method(method, injected));
/*      */     } else {
/*  871 */       this.methods.add(new Method(method, injected));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addMixin(MixinInfo mixin) {
/*  879 */     if (this.isMixin) {
/*  880 */       throw new IllegalArgumentException("Cannot add target " + this.name + " for " + mixin.getClassName() + " because the target is a mixin");
/*      */     }
/*  882 */     this.mixins.add(mixin);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addAppliedMixin(MixinInfo mixin) {
/*  889 */     if (this.appliedMixins == null) {
/*  890 */       this.appliedMixins = new HashSet<>();
/*      */     }
/*  892 */     this.appliedMixins.add(mixin);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Set<MixinInfo> getMixins() {
/*  899 */     return this.isMixin ? Collections.<MixinInfo>emptySet() : Collections.<MixinInfo>unmodifiableSet(this.mixins);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<IMixinInfo> getAppliedMixins() {
/*  906 */     return (this.appliedMixins != null) ? Collections.<IMixinInfo>unmodifiableSet((Set)this.appliedMixins) : Collections.<IMixinInfo>emptySet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMixin() {
/*  913 */     return this.isMixin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLoadable() {
/*  920 */     return (this.mixin != null && this.mixin.isLoadable());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPublic() {
/*  927 */     return ((this.access & 0x1) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAbstract() {
/*  934 */     return ((this.access & 0x400) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSynthetic() {
/*  941 */     return ((this.access & 0x1000) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isProbablyStatic() {
/*  948 */     return this.isProbablyStatic;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInner() {
/*  955 */     return (this.outerName != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInterface() {
/*  962 */     return this.isInterface;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getInterfaces() {
/*  969 */     return Collections.unmodifiableSet(this.interfaces);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  974 */     return this.name;
/*      */   }
/*      */   
/*      */   public MethodMapper getMethodMapper() {
/*  978 */     return this.methodMapper;
/*      */   }
/*      */   
/*      */   public int getAccess() {
/*  982 */     return this.access;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  989 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassName() {
/*  996 */     return this.name.replace('/', '.');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSimpleName() {
/* 1003 */     int pos = this.name.lastIndexOf('/');
/* 1004 */     return (pos < 0) ? this.name : this.name.substring(pos + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public org.objectweb.asm.Type getType() {
/* 1011 */     return org.objectweb.asm.Type.getObjectType(this.name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSuperName() {
/* 1018 */     return this.superName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo getSuperClass() {
/* 1026 */     if (this.superClass == null && this.superName != null) {
/* 1027 */       this.superClass = forName(this.superName);
/*      */     }
/*      */     
/* 1030 */     return this.superClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOuterName() {
/* 1037 */     return this.outerName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo getOuterClass() {
/* 1045 */     if (this.outerClass == null && this.outerName != null) {
/* 1046 */       this.outerClass = forName(this.outerName);
/*      */     }
/*      */     
/* 1049 */     return this.outerClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassSignature getSignature() {
/* 1058 */     return this.signature.wake();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List<ClassInfo> getTargets() {
/* 1065 */     if (this.mixin != null) {
/* 1066 */       List<ClassInfo> targets = new ArrayList<>();
/* 1067 */       targets.add(this);
/* 1068 */       targets.addAll(this.mixin.getTargets());
/* 1069 */       return targets;
/*      */     } 
/*      */     
/* 1072 */     return (List<ClassInfo>)ImmutableList.of(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Method> getMethods() {
/* 1081 */     return Collections.unmodifiableSet(this.methods);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Method> getInterfaceMethods(boolean includeMixins) {
/* 1095 */     Set<Method> methods = new HashSet<>();
/*      */     
/* 1097 */     ClassInfo supClass = addMethodsRecursive(methods, includeMixins);
/* 1098 */     if (!this.isInterface) {
/* 1099 */       while (supClass != null && supClass != OBJECT) {
/* 1100 */         supClass = supClass.addMethodsRecursive(methods, includeMixins);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 1105 */     for (Iterator<Method> it = methods.iterator(); it.hasNext();) {
/* 1106 */       if (!((Method)it.next()).isAbstract()) {
/* 1107 */         it.remove();
/*      */       }
/*      */     } 
/*      */     
/* 1111 */     return Collections.unmodifiableSet(methods);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassInfo addMethodsRecursive(Set<Method> methods, boolean includeMixins) {
/* 1124 */     if (this.isInterface) {
/* 1125 */       for (Method method : this.methods) {
/*      */         
/* 1127 */         if (!method.isAbstract())
/*      */         {
/* 1129 */           methods.remove(method);
/*      */         }
/* 1131 */         methods.add(method);
/*      */       } 
/* 1133 */     } else if (!this.isMixin && includeMixins) {
/* 1134 */       for (MixinInfo mixin : this.mixins) {
/* 1135 */         mixin.getClassInfo().addMethodsRecursive(methods, includeMixins);
/*      */       }
/*      */     } 
/*      */     
/* 1139 */     for (String iface : this.interfaces) {
/* 1140 */       forName(iface).addMethodsRecursive(methods, includeMixins);
/*      */     }
/*      */     
/* 1143 */     return getSuperClass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasSuperClass(String superClass) {
/* 1154 */     return hasSuperClass(superClass, Traversal.NONE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasSuperClass(String superClass, Traversal traversal) {
/* 1166 */     if ("java/lang/Object".equals(superClass)) {
/* 1167 */       return true;
/*      */     }
/*      */     
/* 1170 */     return (findSuperClass(superClass, traversal) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasSuperClass(ClassInfo superClass) {
/* 1181 */     return hasSuperClass(superClass, Traversal.NONE, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasSuperClass(ClassInfo superClass, Traversal traversal) {
/* 1193 */     return hasSuperClass(superClass, traversal, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasSuperClass(ClassInfo superClass, Traversal traversal, boolean includeInterfaces) {
/* 1206 */     if (OBJECT == superClass) {
/* 1207 */       return true;
/*      */     }
/*      */     
/* 1210 */     return (findSuperClass(superClass.name, traversal, includeInterfaces) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo findSuperClass(String superClass) {
/* 1221 */     return findSuperClass(superClass, Traversal.NONE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo findSuperClass(String superClass, Traversal traversal) {
/* 1233 */     return findSuperClass(superClass, traversal, false, new HashSet<>());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo findSuperClass(String superClass, Traversal traversal, boolean includeInterfaces) {
/* 1246 */     if (OBJECT.name.equals(superClass)) {
/* 1247 */       return null;
/*      */     }
/*      */     
/* 1250 */     return findSuperClass(superClass, traversal, includeInterfaces, new HashSet<>());
/*      */   }
/*      */   
/*      */   private ClassInfo findSuperClass(String superClass, Traversal traversal, boolean includeInterfaces, Set<String> traversed) {
/* 1254 */     ClassInfo superClassInfo = getSuperClass();
/* 1255 */     if (superClassInfo != null) {
/* 1256 */       for (ClassInfo superTarget : superClassInfo.getTargets()) {
/* 1257 */         if (superClass.equals(superTarget.getName())) {
/* 1258 */           return superClassInfo;
/*      */         }
/*      */         
/* 1261 */         ClassInfo found = superTarget.findSuperClass(superClass, traversal.next(), includeInterfaces, traversed);
/* 1262 */         if (found != null) {
/* 1263 */           return found;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1268 */     if (includeInterfaces) {
/* 1269 */       ClassInfo iface = findInterface(superClass);
/* 1270 */       if (iface != null) {
/* 1271 */         return iface;
/*      */       }
/*      */     } 
/*      */     
/* 1275 */     if (traversal.canTraverse()) {
/* 1276 */       for (MixinInfo mixin : this.mixins) {
/* 1277 */         String mixinClassName = mixin.getClassName();
/* 1278 */         if (traversed.contains(mixinClassName)) {
/*      */           continue;
/*      */         }
/* 1281 */         traversed.add(mixinClassName);
/* 1282 */         ClassInfo mixinClass = mixin.getClassInfo();
/* 1283 */         if (superClass.equals(mixinClass.getName())) {
/* 1284 */           return mixinClass;
/*      */         }
/* 1286 */         ClassInfo targetSuper = mixinClass.findSuperClass(superClass, Traversal.ALL, includeInterfaces, traversed);
/* 1287 */         if (targetSuper != null) {
/* 1288 */           return targetSuper;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1293 */     return null;
/*      */   }
/*      */   
/*      */   private ClassInfo findInterface(String superClass) {
/* 1297 */     for (String ifaceName : getInterfaces()) {
/* 1298 */       ClassInfo iface = forName(ifaceName);
/* 1299 */       if (superClass.equals(ifaceName)) {
/* 1300 */         return iface;
/*      */       }
/* 1302 */       ClassInfo superIface = iface.findInterface(superClass);
/* 1303 */       if (superIface != null) {
/* 1304 */         return superIface;
/*      */       }
/*      */     } 
/* 1307 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ClassInfo findCorrespondingType(ClassInfo mixin) {
/* 1321 */     if (mixin == null || !mixin.isMixin || this.isMixin) {
/* 1322 */       return null;
/*      */     }
/*      */     
/* 1325 */     ClassInfo correspondingType = this.correspondingTypes.get(mixin);
/* 1326 */     if (correspondingType == null) {
/* 1327 */       correspondingType = findSuperTypeForMixin(mixin);
/* 1328 */       this.correspondingTypes.put(mixin, correspondingType);
/*      */     } 
/* 1330 */     return correspondingType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassInfo findSuperTypeForMixin(ClassInfo mixin) {
/* 1338 */     ClassInfo superClass = this;
/*      */     
/* 1340 */     while (superClass != null && superClass != OBJECT) {
/* 1341 */       for (MixinInfo minion : superClass.mixins) {
/* 1342 */         if (minion.getClassInfo().equals(mixin)) {
/* 1343 */           return superClass;
/*      */         }
/*      */       } 
/*      */       
/* 1347 */       superClass = superClass.getSuperClass();
/*      */     } 
/*      */     
/* 1350 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasMixinInHierarchy() {
/* 1361 */     if (!this.isMixin) {
/* 1362 */       return false;
/*      */     }
/*      */     
/* 1365 */     ClassInfo supClass = getSuperClass();
/*      */     
/* 1367 */     while (supClass != null && supClass != OBJECT) {
/* 1368 */       if (supClass.isMixin) {
/* 1369 */         return true;
/*      */       }
/* 1371 */       supClass = supClass.getSuperClass();
/*      */     } 
/*      */     
/* 1374 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasMixinTargetInHierarchy() {
/* 1386 */     if (this.isMixin) {
/* 1387 */       return false;
/*      */     }
/*      */     
/* 1390 */     ClassInfo supClass = getSuperClass();
/*      */     
/* 1392 */     while (supClass != null && supClass != OBJECT) {
/* 1393 */       if (supClass.mixins.size() > 0) {
/* 1394 */         return true;
/*      */       }
/* 1396 */       supClass = supClass.getSuperClass();
/*      */     } 
/*      */     
/* 1399 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethodInHierarchy(MethodNode method, SearchType searchType) {
/* 1410 */     return findMethodInHierarchy(method.name, method.desc, searchType, Traversal.NONE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethodInHierarchy(MethodNode method, SearchType searchType, Traversal traversal) {
/* 1422 */     return findMethodInHierarchy(method.name, method.desc, searchType, traversal, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethodInHierarchy(MethodNode method, SearchType searchType, int flags) {
/* 1434 */     return findMethodInHierarchy(method.name, method.desc, searchType, Traversal.NONE, flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethodInHierarchy(MethodNode method, SearchType searchType, Traversal traversal, int flags) {
/* 1447 */     return findMethodInHierarchy(method.name, method.desc, searchType, traversal, flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethodInHierarchy(MethodInsnNode method, SearchType searchType) {
/* 1458 */     return findMethodInHierarchy(method.name, method.desc, searchType, Traversal.NONE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethodInHierarchy(MethodInsnNode method, SearchType searchType, int flags) {
/* 1470 */     return findMethodInHierarchy(method.name, method.desc, searchType, Traversal.NONE, flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethodInHierarchy(String name, String desc, SearchType searchType) {
/* 1482 */     return findMethodInHierarchy(name, desc, searchType, Traversal.NONE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethodInHierarchy(String name, String desc, SearchType searchType, Traversal traversal) {
/* 1495 */     return findMethodInHierarchy(name, desc, searchType, traversal, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethodInHierarchy(String name, String desc, SearchType searchType, Traversal traversal, int flags) {
/* 1509 */     return findInHierarchy(name, desc, searchType, traversal, flags, Member.Type.METHOD);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field findFieldInHierarchy(FieldNode field, SearchType searchType) {
/* 1520 */     return findFieldInHierarchy(field.name, field.desc, searchType, Traversal.NONE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field findFieldInHierarchy(FieldNode field, SearchType searchType, int flags) {
/* 1532 */     return findFieldInHierarchy(field.name, field.desc, searchType, Traversal.NONE, flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field findFieldInHierarchy(FieldInsnNode field, SearchType searchType) {
/* 1543 */     return findFieldInHierarchy(field.name, field.desc, searchType, Traversal.NONE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field findFieldInHierarchy(FieldInsnNode field, SearchType searchType, int flags) {
/* 1555 */     return findFieldInHierarchy(field.name, field.desc, searchType, Traversal.NONE, flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field findFieldInHierarchy(String name, String desc, SearchType searchType) {
/* 1567 */     return findFieldInHierarchy(name, desc, searchType, Traversal.NONE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field findFieldInHierarchy(String name, String desc, SearchType searchType, Traversal traversal) {
/* 1580 */     return findFieldInHierarchy(name, desc, searchType, traversal, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field findFieldInHierarchy(String name, String desc, SearchType searchType, Traversal traversal, int flags) {
/* 1594 */     return findInHierarchy(name, desc, searchType, traversal, flags, Member.Type.FIELD);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <M extends Member> M findInHierarchy(String name, String desc, SearchType searchType, Traversal traversal, int flags, Member.Type type) {
/* 1611 */     if (searchType == SearchType.ALL_CLASSES) {
/* 1612 */       M member = findMember(name, desc, flags, type);
/* 1613 */       if (member != null) {
/* 1614 */         return member;
/*      */       }
/*      */       
/* 1617 */       if (traversal.canTraverse()) {
/* 1618 */         for (MixinInfo mixin : this.mixins) {
/* 1619 */           M mixinMember = mixin.getClassInfo().findMember(name, desc, flags, type);
/* 1620 */           if (mixinMember != null) {
/* 1621 */             return cloneMember(mixinMember);
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1627 */     ClassInfo superClassInfo = getSuperClass();
/* 1628 */     if (superClassInfo != null) {
/* 1629 */       for (ClassInfo superTarget : superClassInfo.getTargets()) {
/* 1630 */         M member = superTarget.findInHierarchy(name, desc, SearchType.ALL_CLASSES, traversal.next(), flags & 0xFFFFFFFD, type);
/*      */         
/* 1632 */         if (member != null) {
/* 1633 */           return member;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1638 */     if (type == Member.Type.METHOD && (this.isInterface || MixinEnvironment.getCompatibilityLevel().supports(1))) {
/* 1639 */       for (String implemented : this.interfaces) {
/* 1640 */         ClassInfo iface = forName(implemented);
/* 1641 */         if (iface == null) {
/* 1642 */           logger.debug("Failed to resolve declared interface {} on {}", new Object[] { implemented, this.name });
/*      */           
/*      */           continue;
/*      */         } 
/* 1646 */         M member = iface.findInHierarchy(name, desc, SearchType.ALL_CLASSES, traversal.next(), flags & 0xFFFFFFFD, type);
/* 1647 */         if (member != null) {
/* 1648 */           return this.isInterface ? member : (M)new InterfaceMethod((Member)member);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1653 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <M extends Member> M cloneMember(M member) {
/* 1667 */     if (member instanceof Method) {
/* 1668 */       return (M)new Method((Member)member);
/*      */     }
/*      */     
/* 1671 */     return (M)new Field((Member)member);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethod(MethodNode method) {
/* 1681 */     return findMethod(method.name, method.desc, method.access);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethod(MethodNode method, int flags) {
/* 1692 */     return findMethod(method.name, method.desc, flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethod(MethodInsnNode method) {
/* 1702 */     return findMethod(method.name, method.desc, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethod(MethodInsnNode method, int flags) {
/* 1713 */     return findMethod(method.name, method.desc, flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method findMethod(String name, String desc, int flags) {
/* 1725 */     return findMember(name, desc, flags, Member.Type.METHOD);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field findField(FieldNode field) {
/* 1735 */     return findField(field.name, field.desc, field.access);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field findField(FieldInsnNode field, int flags) {
/* 1746 */     return findField(field.name, field.desc, flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field findField(String name, String desc, int flags) {
/* 1758 */     return findMember(name, desc, flags, Member.Type.FIELD);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <M extends Member> M findMember(String name, String desc, int flags, Member.Type memberType) {
/* 1772 */     Set<M> members = (memberType == Member.Type.METHOD) ? (Set)this.methods : (Set)this.fields;
/*      */     
/* 1774 */     for (Member member : members) {
/* 1775 */       if (member.equals(name, desc) && member.matchesFlags(flags)) {
/* 1776 */         return (M)member;
/*      */       }
/*      */     } 
/*      */     
/* 1780 */     if (memberType == Member.Type.METHOD && (flags & 0x40000) != 0) {
/* 1781 */       for (Method ctor : this.initialisers) {
/* 1782 */         if (ctor.equals(name, desc) && ctor.matchesFlags(flags)) {
/* 1783 */           return (M)ctor;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1788 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object other) {
/* 1796 */     if (!(other instanceof ClassInfo)) {
/* 1797 */       return false;
/*      */     }
/* 1799 */     return ((ClassInfo)other).name.equals(this.name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1807 */     return this.name.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ClassInfo fromClassNode(ClassNode classNode) {
/* 1820 */     ClassInfo info = cache.get(classNode.name);
/* 1821 */     if (info == null) {
/* 1822 */       info = new ClassInfo(classNode);
/* 1823 */       cache.put(classNode.name, info);
/*      */     } 
/*      */     
/* 1826 */     return info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassInfo forName(String className) {
/* 1838 */     className = className.replace('.', '/');
/*      */     
/* 1840 */     ClassInfo info = cache.get(className);
/* 1841 */     if (info == null) {
/*      */       try {
/* 1843 */         ClassNode classNode = MixinService.getService().getBytecodeProvider().getClassNode(className);
/* 1844 */         info = new ClassInfo(classNode);
/* 1845 */       } catch (Exception ex) {
/* 1846 */         logger.catching(Level.TRACE, ex);
/* 1847 */         logger.warn("Error loading class: {} ({}: {})", new Object[] { className, ex.getClass().getName(), ex.getMessage() });
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1852 */       cache.put(className, info);
/* 1853 */       logger.trace("Added class metadata for {} to metadata cache", new Object[] { className });
/*      */     } 
/*      */     
/* 1856 */     return info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassInfo forDescriptor(String descriptor, TypeLookup lookup) {
/*      */     org.objectweb.asm.Type type;
/*      */     try {
/* 1871 */       type = org.objectweb.asm.Type.getObjectType(descriptor);
/* 1872 */     } catch (IllegalArgumentException ex) {
/* 1873 */       logger.warn("Error resolving type from descriptor: {}", new Object[] { descriptor });
/* 1874 */       return null;
/*      */     } 
/* 1876 */     return forType(type, lookup);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassInfo forType(org.objectweb.asm.Type type, TypeLookup lookup) {
/* 1889 */     if (type.getSort() == 9) {
/* 1890 */       if (lookup == TypeLookup.ELEMENT_TYPE) {
/* 1891 */         return forType(type.getElementType(), TypeLookup.ELEMENT_TYPE);
/*      */       }
/* 1893 */       return OBJECT;
/* 1894 */     }  if (type.getSort() < 9) {
/* 1895 */       return null;
/*      */     }
/* 1897 */     return forName(type.getClassName().replace('.', '/'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassInfo fromCache(String className) {
/* 1912 */     return cache.get(className.replace('.', '/'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassInfo fromCache(org.objectweb.asm.Type type, TypeLookup lookup) {
/* 1928 */     if (type.getSort() == 9) {
/* 1929 */       if (lookup == TypeLookup.ELEMENT_TYPE) {
/* 1930 */         return fromCache(type.getElementType(), TypeLookup.ELEMENT_TYPE);
/*      */       }
/* 1932 */       return OBJECT;
/* 1933 */     }  if (type.getSort() < 9) {
/* 1934 */       return null;
/*      */     }
/* 1936 */     return fromCache(type.getClassName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassInfo getCommonSuperClass(String type1, String type2) {
/* 1948 */     if (type1 == null || type2 == null) {
/* 1949 */       return OBJECT;
/*      */     }
/* 1951 */     return getCommonSuperClass(forName(type1), forName(type2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassInfo getCommonSuperClass(org.objectweb.asm.Type type1, org.objectweb.asm.Type type2) {
/* 1963 */     if (type1 == null || type2 == null || type1
/* 1964 */       .getSort() != 10 || type2.getSort() != 10) {
/* 1965 */       return OBJECT;
/*      */     }
/* 1967 */     return getCommonSuperClass(forType(type1, TypeLookup.DECLARED_TYPE), forType(type2, TypeLookup.DECLARED_TYPE));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ClassInfo getCommonSuperClass(ClassInfo type1, ClassInfo type2) {
/* 1979 */     return getCommonSuperClass(type1, type2, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassInfo getCommonSuperClassOrInterface(String type1, String type2) {
/* 1991 */     if (type1 == null || type2 == null) {
/* 1992 */       return OBJECT;
/*      */     }
/* 1994 */     return getCommonSuperClassOrInterface(forName(type1), forName(type2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassInfo getCommonSuperClassOrInterface(org.objectweb.asm.Type type1, org.objectweb.asm.Type type2) {
/* 2006 */     if (type1 == null || type2 == null || type1
/* 2007 */       .getSort() != 10 || type2.getSort() != 10) {
/* 2008 */       return OBJECT;
/*      */     }
/* 2010 */     return getCommonSuperClassOrInterface(forType(type1, TypeLookup.DECLARED_TYPE), 
/* 2011 */         forType(type2, TypeLookup.DECLARED_TYPE));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassInfo getCommonSuperClassOrInterface(ClassInfo type1, ClassInfo type2) {
/* 2023 */     return getCommonSuperClass(type1, type2, true);
/*      */   }
/*      */   
/*      */   private static ClassInfo getCommonSuperClass(ClassInfo type1, ClassInfo type2, boolean includeInterfaces) {
/* 2027 */     if (type1.hasSuperClass(type2, Traversal.NONE, includeInterfaces))
/* 2028 */       return type2; 
/* 2029 */     if (type2.hasSuperClass(type1, Traversal.NONE, includeInterfaces))
/* 2030 */       return type1; 
/* 2031 */     if (type1.isInterface() || type2.isInterface()) {
/* 2032 */       return OBJECT;
/*      */     }
/*      */     
/*      */     do {
/* 2036 */       type1 = type1.getSuperClass();
/* 2037 */       if (type1 == null) {
/* 2038 */         return OBJECT;
/*      */       }
/* 2040 */     } while (!type2.hasSuperClass(type1, Traversal.NONE, includeInterfaces));
/*      */     
/* 2042 */     return type1;
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\ClassInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */