/*     */ package org.spongepowered.asm.mixin.injection;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.AnnotationNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.modify.AfterStoreLocal;
/*     */ import org.spongepowered.asm.mixin.injection.modify.BeforeLoadLocal;
/*     */ import org.spongepowered.asm.mixin.injection.points.AfterInvoke;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeFinalReturn;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeInvoke;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeNew;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeStringInvoke;
/*     */ import org.spongepowered.asm.mixin.injection.points.JumpInsnPoint;
/*     */ import org.spongepowered.asm.mixin.injection.points.MethodHead;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
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
/*     */ public abstract class InjectionPoint
/*     */ {
/*     */   public static final int DEFAULT_ALLOWED_SHIFT_BY = 0;
/*     */   public static final int MAX_ALLOWED_SHIFT_BY = 5;
/*     */   
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   @Target({ElementType.TYPE})
/*     */   public static @interface AtCode
/*     */   {
/*     */     String value();
/*     */   }
/*     */   
/*     */   public enum Selector
/*     */   {
/* 113 */     FIRST,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     LAST,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     ONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     public static final Selector DEFAULT = FIRST;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum RestrictTargetLevel
/*     */   {
/* 144 */     METHODS_ONLY,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     CONSTRUCTORS_AFTER_DELEGATE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     ALLOW_ALL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   enum ShiftByViolationBehaviour
/*     */   {
/* 168 */     IGNORE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     WARN,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     ERROR;
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
/* 196 */   private static Map<String, Class<? extends InjectionPoint>> types = new HashMap<>();
/*     */   private final String slice;
/*     */   
/*     */   static {
/* 200 */     register((Class)BeforeFieldAccess.class);
/* 201 */     register((Class)BeforeInvoke.class);
/* 202 */     register((Class)BeforeNew.class);
/* 203 */     register((Class)BeforeReturn.class);
/* 204 */     register((Class)BeforeStringInvoke.class);
/* 205 */     register((Class)JumpInsnPoint.class);
/* 206 */     register((Class)MethodHead.class);
/* 207 */     register((Class)AfterInvoke.class);
/* 208 */     register((Class)BeforeLoadLocal.class);
/* 209 */     register((Class)AfterStoreLocal.class);
/* 210 */     register((Class)BeforeFinalReturn.class);
/* 211 */     register((Class)BeforeConstant.class);
/*     */   }
/*     */ 
/*     */   
/*     */   private final Selector selector;
/*     */   private final String id;
/*     */   
/*     */   protected InjectionPoint() {
/* 219 */     this("", Selector.DEFAULT, null);
/*     */   }
/*     */   
/*     */   protected InjectionPoint(InjectionPointData data) {
/* 223 */     this(data.getSlice(), data.getSelector(), data.getId());
/*     */   }
/*     */   
/*     */   public InjectionPoint(String slice, Selector selector, String id) {
/* 227 */     this.slice = slice;
/* 228 */     this.selector = selector;
/* 229 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getSlice() {
/* 233 */     return this.slice;
/*     */   }
/*     */   
/*     */   public Selector getSelector() {
/* 237 */     return this.selector;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 241 */     return this.id;
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
/*     */   public boolean checkPriority(int targetPriority, int mixinPriority) {
/* 257 */     return (targetPriority < mixinPriority);
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
/*     */   public RestrictTargetLevel getTargetRestriction(IInjectionPointContext context) {
/* 269 */     return RestrictTargetLevel.METHODS_ONLY;
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
/*     */   public String toString() {
/* 291 */     return String.format("@At(\"%s\")", new Object[] { getAtCode() });
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
/*     */   protected static AbstractInsnNode nextNode(InsnList insns, AbstractInsnNode insn) {
/* 303 */     int index = insns.indexOf(insn) + 1;
/* 304 */     if (index > 0 && index < insns.size()) {
/* 305 */       return insns.get(index);
/*     */     }
/* 307 */     return insn;
/*     */   }
/*     */ 
/*     */   
/*     */   static abstract class CompositeInjectionPoint
/*     */     extends InjectionPoint
/*     */   {
/*     */     protected final InjectionPoint[] components;
/*     */ 
/*     */     
/*     */     protected CompositeInjectionPoint(InjectionPoint... components) {
/* 318 */       if (components == null || components.length < 2) {
/* 319 */         throw new IllegalArgumentException("Must supply two or more component injection points for composite point!");
/*     */       }
/*     */       
/* 322 */       this.components = components;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 330 */       return "CompositeInjectionPoint(" + getClass().getSimpleName() + ")[" + Joiner.on(',').join((Object[])this.components) + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Intersection
/*     */     extends CompositeInjectionPoint
/*     */   {
/*     */     public Intersection(InjectionPoint... points) {
/* 341 */       super(points);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 347 */       boolean found = false;
/*     */       
/* 349 */       ArrayList[] arrayOfArrayList = (ArrayList[])Array.newInstance(ArrayList.class, this.components.length);
/*     */       
/* 351 */       for (int i = 0; i < this.components.length; i++) {
/* 352 */         arrayOfArrayList[i] = new ArrayList();
/* 353 */         this.components[i].find(desc, insns, arrayOfArrayList[i]);
/*     */       } 
/*     */       
/* 356 */       ArrayList<AbstractInsnNode> alpha = arrayOfArrayList[0];
/* 357 */       for (int nodeIndex = 0; nodeIndex < alpha.size(); nodeIndex++) {
/* 358 */         AbstractInsnNode node = alpha.get(nodeIndex);
/* 359 */         boolean in = true;
/*     */         
/* 361 */         for (int b = 1; b < arrayOfArrayList.length && 
/* 362 */           arrayOfArrayList[b].contains(node); b++);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 367 */         if (in) {
/*     */ 
/*     */ 
/*     */           
/* 371 */           nodes.add(node);
/* 372 */           found = true;
/*     */         } 
/*     */       } 
/* 375 */       return found;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Union
/*     */     extends CompositeInjectionPoint
/*     */   {
/*     */     public Union(InjectionPoint... points) {
/* 386 */       super(points);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 391 */       LinkedHashSet<AbstractInsnNode> allNodes = new LinkedHashSet<>();
/*     */       
/* 393 */       for (int i = 0; i < this.components.length; i++) {
/* 394 */         this.components[i].find(desc, insns, allNodes);
/*     */       }
/*     */       
/* 397 */       nodes.addAll(allNodes);
/*     */       
/* 399 */       return (allNodes.size() > 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class Shift
/*     */     extends InjectionPoint
/*     */   {
/*     */     private final InjectionPoint input;
/*     */     
/*     */     private final int shift;
/*     */ 
/*     */     
/*     */     public Shift(InjectionPoint input, int shift) {
/* 413 */       if (input == null) {
/* 414 */         throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
/*     */       }
/*     */       
/* 417 */       this.input = input;
/* 418 */       this.shift = shift;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 426 */       return "InjectionPoint(" + getClass().getSimpleName() + ")[" + this.input + "]";
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 431 */       List<AbstractInsnNode> list = (nodes instanceof List) ? (List<AbstractInsnNode>)nodes : new ArrayList<>(nodes);
/*     */       
/* 433 */       this.input.find(desc, insns, nodes);
/*     */       
/* 435 */       for (int i = 0; i < list.size(); i++) {
/* 436 */         list.set(i, insns.get(insns.indexOf(list.get(i)) + this.shift));
/*     */       }
/*     */       
/* 439 */       if (nodes != list) {
/* 440 */         nodes.clear();
/* 441 */         nodes.addAll(list);
/*     */       } 
/*     */       
/* 444 */       return (nodes.size() > 0);
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
/*     */   public static InjectionPoint and(InjectionPoint... operands) {
/* 456 */     return new Intersection(operands);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InjectionPoint or(InjectionPoint... operands) {
/* 467 */     return new Union(operands);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InjectionPoint after(InjectionPoint point) {
/* 478 */     return new Shift(point, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InjectionPoint before(InjectionPoint point) {
/* 489 */     return new Shift(point, -1);
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
/*     */   public static InjectionPoint shift(InjectionPoint point, int count) {
/* 501 */     return new Shift(point, count);
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
/*     */   public static List<InjectionPoint> parse(IInjectionPointContext owner, List<AnnotationNode> ats) {
/* 515 */     return parse(owner.getContext(), owner.getMethod(), owner.getAnnotation(), ats);
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
/*     */   public static List<InjectionPoint> parse(IMixinContext context, MethodNode method, AnnotationNode parent, List<AnnotationNode> ats) {
/* 531 */     ImmutableList.Builder<InjectionPoint> injectionPoints = ImmutableList.builder();
/* 532 */     for (AnnotationNode at : ats) {
/* 533 */       InjectionPoint injectionPoint = parse(context, method, parent, at);
/* 534 */       if (injectionPoint != null) {
/* 535 */         injectionPoints.add(injectionPoint);
/*     */       }
/*     */     } 
/* 538 */     return (List<InjectionPoint>)injectionPoints.build();
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
/*     */   public static InjectionPoint parse(IInjectionPointContext owner, At at) {
/* 551 */     return parse(owner.getContext(), owner.getMethod(), owner.getAnnotation(), at.value(), at.shift(), at.by(), 
/* 552 */         Arrays.asList(at.args()), at.target(), at.slice(), at.ordinal(), at.opcode(), at.id());
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
/*     */   public static InjectionPoint parse(IMixinContext context, MethodNode method, AnnotationNode parent, At at) {
/* 567 */     return parse(context, method, parent, at.value(), at.shift(), at.by(), Arrays.asList(at.args()), at.target(), at.slice(), at
/* 568 */         .ordinal(), at.opcode(), at.id());
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
/*     */   public static InjectionPoint parse(IInjectionPointContext owner, AnnotationNode node) {
/* 582 */     return parse(owner.getContext(), owner.getMethod(), owner.getAnnotation(), node);
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
/*     */   public static InjectionPoint parse(IMixinContext context, MethodNode method, AnnotationNode parent, AnnotationNode node) {
/*     */     ImmutableList immutableList;
/* 598 */     String at = (String)Annotations.getValue(node, "value");
/* 599 */     List<String> args = (List<String>)Annotations.getValue(node, "args");
/* 600 */     String target = (String)Annotations.getValue(node, "target", "");
/* 601 */     String slice = (String)Annotations.getValue(node, "slice", "");
/* 602 */     At.Shift shift = (At.Shift)Annotations.getValue(node, "shift", At.Shift.class, At.Shift.NONE);
/* 603 */     int by = ((Integer)Annotations.getValue(node, "by", Integer.valueOf(0))).intValue();
/* 604 */     int ordinal = ((Integer)Annotations.getValue(node, "ordinal", Integer.valueOf(-1))).intValue();
/* 605 */     int opcode = ((Integer)Annotations.getValue(node, "opcode", Integer.valueOf(0))).intValue();
/* 606 */     String id = (String)Annotations.getValue(node, "id");
/*     */     
/* 608 */     if (args == null) {
/* 609 */       immutableList = ImmutableList.of();
/*     */     }
/*     */     
/* 612 */     return parse(context, method, parent, at, shift, by, (List<String>)immutableList, target, slice, ordinal, opcode, id);
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
/*     */   public static InjectionPoint parse(IMixinContext context, MethodNode method, AnnotationNode parent, String at, At.Shift shift, int by, List<String> args, String target, String slice, int ordinal, int opcode, String id) {
/* 637 */     InjectionPointData data = new InjectionPointData(context, method, parent, at, args, target, slice, ordinal, opcode, id);
/* 638 */     Class<? extends InjectionPoint> ipClass = findClass(context, data);
/* 639 */     InjectionPoint point = create(context, data, ipClass);
/* 640 */     return shift(context, method, parent, point, shift, by);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class<? extends InjectionPoint> findClass(IMixinContext context, InjectionPointData data) {
/* 645 */     String type = data.getType();
/* 646 */     Class<? extends InjectionPoint> ipClass = types.get(type);
/* 647 */     if (ipClass == null) {
/* 648 */       if (type.matches("^([A-Za-z_][A-Za-z0-9_]*\\.)+[A-Za-z_][A-Za-z0-9_]*$")) {
/*     */         try {
/* 650 */           ipClass = (Class)Class.forName(type);
/* 651 */           types.put(type, ipClass);
/* 652 */         } catch (Exception ex) {
/* 653 */           throw new InvalidInjectionException(context, data + " could not be loaded or is not a valid InjectionPoint", ex);
/*     */         } 
/*     */       } else {
/* 656 */         throw new InvalidInjectionException(context, data + " is not a valid injection point specifier");
/*     */       } 
/*     */     }
/* 659 */     return ipClass;
/*     */   }
/*     */   
/*     */   private static InjectionPoint create(IMixinContext context, InjectionPointData data, Class<? extends InjectionPoint> ipClass) {
/* 663 */     Constructor<? extends InjectionPoint> ipCtor = null;
/*     */     try {
/* 665 */       ipCtor = ipClass.getDeclaredConstructor(new Class[] { InjectionPointData.class });
/* 666 */       ipCtor.setAccessible(true);
/* 667 */     } catch (NoSuchMethodException ex) {
/* 668 */       throw new InvalidInjectionException(context, ipClass.getName() + " must contain a constructor which accepts an InjectionPointData", ex);
/*     */     } 
/*     */     
/* 671 */     InjectionPoint point = null;
/*     */     try {
/* 673 */       point = ipCtor.newInstance(new Object[] { data });
/* 674 */     } catch (Exception ex) {
/* 675 */       throw new InvalidInjectionException(context, "Error whilst instancing injection point " + ipClass.getName() + " for " + data.getAt(), ex);
/*     */     } 
/*     */     
/* 678 */     return point;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static InjectionPoint shift(IMixinContext context, MethodNode method, AnnotationNode parent, InjectionPoint point, At.Shift shift, int by) {
/* 684 */     if (point != null) {
/* 685 */       if (shift == At.Shift.BEFORE)
/* 686 */         return before(point); 
/* 687 */       if (shift == At.Shift.AFTER)
/* 688 */         return after(point); 
/* 689 */       if (shift == At.Shift.BY) {
/* 690 */         validateByValue(context, method, parent, point, by);
/* 691 */         return shift(point, by);
/*     */       } 
/*     */     } 
/*     */     
/* 695 */     return point;
/*     */   }
/*     */   
/*     */   private static void validateByValue(IMixinContext context, MethodNode method, AnnotationNode parent, InjectionPoint point, int by) {
/* 699 */     MixinEnvironment env = context.getMixin().getConfig().getEnvironment();
/* 700 */     ShiftByViolationBehaviour err = (ShiftByViolationBehaviour)env.getOption(MixinEnvironment.Option.SHIFT_BY_VIOLATION_BEHAVIOUR, ShiftByViolationBehaviour.WARN);
/* 701 */     if (err == ShiftByViolationBehaviour.IGNORE) {
/*     */       return;
/*     */     }
/*     */     
/* 705 */     String limitBreached = "the maximum allowed value: ";
/* 706 */     String advice = "Increase the value of maxShiftBy to suppress this warning.";
/* 707 */     int allowed = 0;
/* 708 */     if (context instanceof MixinTargetContext) {
/* 709 */       allowed = ((MixinTargetContext)context).getMaxShiftByValue();
/*     */     }
/*     */     
/* 712 */     if (by <= allowed) {
/*     */       return;
/*     */     }
/*     */     
/* 716 */     if (by > 5) {
/* 717 */       limitBreached = "MAX_ALLOWED_SHIFT_BY=";
/* 718 */       advice = "You must use an alternate query or a custom injection point.";
/* 719 */       allowed = 5;
/*     */     } 
/*     */     
/* 722 */     String message = String.format("@%s(%s) Shift.BY=%d on %s::%s exceeds %s%d. %s", new Object[] { Bytecode.getSimpleName(parent), point, 
/* 723 */           Integer.valueOf(by), context, method.name, limitBreached, Integer.valueOf(allowed), advice });
/*     */     
/* 725 */     if (err == ShiftByViolationBehaviour.WARN && allowed < 5) {
/* 726 */       LogManager.getLogger("mixin").warn(message);
/*     */       
/*     */       return;
/*     */     } 
/* 730 */     throw new InvalidInjectionException(context, message);
/*     */   }
/*     */   
/*     */   protected String getAtCode() {
/* 734 */     AtCode code = getClass().<AtCode>getAnnotation(AtCode.class);
/* 735 */     return (code == null) ? getClass().getName() : code.value();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(Class<? extends InjectionPoint> type) {
/* 745 */     AtCode code = type.<AtCode>getAnnotation(AtCode.class);
/* 746 */     if (code == null) {
/* 747 */       throw new IllegalArgumentException("Injection point class " + type + " is not annotated with @AtCode");
/*     */     }
/*     */     
/* 750 */     Class<? extends InjectionPoint> existing = types.get(code.value());
/* 751 */     if (existing != null && !existing.equals(type)) {
/* 752 */       LogManager.getLogger("mixin").debug("Overriding InjectionPoint {} with {} (previously {})", new Object[] { code.value(), type.getName(), existing
/* 753 */             .getName() });
/*     */     }
/*     */     
/* 756 */     types.put(code.value(), type);
/*     */   }
/*     */   
/*     */   public abstract boolean find(String paramString, InsnList paramInsnList, Collection<AbstractInsnNode> paramCollection);
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\InjectionPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */