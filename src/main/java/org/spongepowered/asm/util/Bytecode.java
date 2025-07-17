/*      */ package org.spongepowered.asm.util;
/*      */ 
/*      */ import com.google.common.base.Joiner;
/*      */ import com.google.common.primitives.Ints;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Field;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import org.objectweb.asm.ClassReader;
/*      */ import org.objectweb.asm.ClassVisitor;
/*      */ import org.objectweb.asm.ClassWriter;
/*      */ import org.objectweb.asm.MethodVisitor;
/*      */ import org.objectweb.asm.Opcodes;
/*      */ import org.objectweb.asm.Type;
/*      */ import org.objectweb.asm.tree.AbstractInsnNode;
/*      */ import org.objectweb.asm.tree.AnnotationNode;
/*      */ import org.objectweb.asm.tree.ClassNode;
/*      */ import org.objectweb.asm.tree.FieldInsnNode;
/*      */ import org.objectweb.asm.tree.FieldNode;
/*      */ import org.objectweb.asm.tree.FrameNode;
/*      */ import org.objectweb.asm.tree.InsnList;
/*      */ import org.objectweb.asm.tree.IntInsnNode;
/*      */ import org.objectweb.asm.tree.InvokeDynamicInsnNode;
/*      */ import org.objectweb.asm.tree.JumpInsnNode;
/*      */ import org.objectweb.asm.tree.LabelNode;
/*      */ import org.objectweb.asm.tree.LdcInsnNode;
/*      */ import org.objectweb.asm.tree.LineNumberNode;
/*      */ import org.objectweb.asm.tree.MethodInsnNode;
/*      */ import org.objectweb.asm.tree.MethodNode;
/*      */ import org.objectweb.asm.tree.TypeInsnNode;
/*      */ import org.objectweb.asm.tree.VarInsnNode;
/*      */ import org.objectweb.asm.util.CheckClassAdapter;
/*      */ import org.objectweb.asm.util.TraceClassVisitor;
/*      */ import org.spongepowered.asm.util.asm.ASM;
/*      */ import org.spongepowered.asm.util.throwables.SyntheticBridgeException;
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
/*      */ public final class Bytecode
/*      */ {
/*      */   public enum Visibility
/*      */   {
/*   71 */     PRIVATE(2),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   76 */     PROTECTED(4),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   81 */     PACKAGE(0),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   86 */     PUBLIC(1);
/*      */     
/*      */     static final int MASK = 7;
/*      */     
/*      */     final int access;
/*      */     
/*      */     Visibility(int access) {
/*   93 */       this.access = access;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isAtLeast(Visibility other) {
/*  104 */       return (other == null || other.ordinal() <= ordinal());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class DelegateInitialiser
/*      */   {
/*  117 */     public static final DelegateInitialiser NONE = new DelegateInitialiser(null, false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final MethodInsnNode insn;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final boolean isSuper;
/*      */ 
/*      */ 
/*      */     
/*      */     public final boolean isPresent;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DelegateInitialiser(MethodInsnNode insn, boolean isSuper) {
/*  137 */       this.insn = insn;
/*  138 */       this.isSuper = isSuper;
/*  139 */       this.isPresent = (insn != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  144 */       return this.isSuper ? "super" : "this";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  151 */   public static final int[] CONSTANTS_INT = new int[] { 2, 3, 4, 5, 6, 7, 8 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  158 */   public static final int[] CONSTANTS_FLOAT = new int[] { 11, 12, 13 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  165 */   public static final int[] CONSTANTS_DOUBLE = new int[] { 14, 15 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  172 */   public static final int[] CONSTANTS_LONG = new int[] { 9, 10 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  179 */   public static final int[] CONSTANTS_ALL = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 192, 193 };
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
/*  193 */   private static final Object[] CONSTANTS_VALUES = new Object[] { Type.VOID_TYPE, 
/*      */       
/*  195 */       Integer.valueOf(-1), 
/*  196 */       Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), 
/*  197 */       Long.valueOf(0L), Long.valueOf(1L), 
/*  198 */       Float.valueOf(0.0F), Float.valueOf(1.0F), Float.valueOf(2.0F), 
/*  199 */       Double.valueOf(0.0D), Double.valueOf(1.0D) };
/*      */ 
/*      */   
/*  202 */   private static final String[] CONSTANTS_TYPES = new String[] { null, "I", "I", "I", "I", "I", "I", "I", "J", "J", "F", "F", "F", "D", "D", "I", "I" };
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
/*  216 */   private static final String[] BOXING_TYPES = new String[] { null, "java/lang/Boolean", "java/lang/Character", "java/lang/Byte", "java/lang/Short", "java/lang/Integer", "java/lang/Float", "java/lang/Long", "java/lang/Double", null, null, null };
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
/*  234 */   private static final String[] UNBOXING_METHODS = new String[] { null, "booleanValue", "charValue", "byteValue", "shortValue", "intValue", "floatValue", "longValue", "doubleValue", null, null, null };
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
/*      */   public static MethodNode findMethod(ClassNode classNode, String name, String desc) {
/*  262 */     for (MethodNode method : classNode.methods) {
/*  263 */       if (method.name.equals(name) && method.desc.equals(desc)) {
/*  264 */         return method;
/*      */       }
/*      */     } 
/*  267 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AbstractInsnNode findInsn(MethodNode method, int opcode) {
/*  278 */     Iterator<AbstractInsnNode> findReturnIter = method.instructions.iterator();
/*  279 */     while (findReturnIter.hasNext()) {
/*  280 */       AbstractInsnNode insn = findReturnIter.next();
/*  281 */       if (insn.getOpcode() == opcode) {
/*  282 */         return insn;
/*      */       }
/*      */     } 
/*  285 */     return null;
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
/*      */   public static DelegateInitialiser findDelegateInit(MethodNode ctor, String superName, String ownerName) {
/*  301 */     if (!"<init>".equals(ctor.name)) {
/*  302 */       return DelegateInitialiser.NONE;
/*      */     }
/*      */     
/*  305 */     int news = 0;
/*  306 */     for (Iterator<AbstractInsnNode> iter = ctor.instructions.iterator(); iter.hasNext(); ) {
/*  307 */       AbstractInsnNode insn = iter.next();
/*  308 */       if (insn instanceof TypeInsnNode && insn.getOpcode() == 187) {
/*  309 */         news++; continue;
/*  310 */       }  if (insn instanceof MethodInsnNode && insn.getOpcode() == 183) {
/*  311 */         MethodInsnNode methodNode = (MethodInsnNode)insn;
/*  312 */         if ("<init>".equals(methodNode.name)) {
/*  313 */           if (news > 0) {
/*  314 */             news--; continue;
/*      */           } 
/*  316 */           boolean isSuper = methodNode.owner.equals(superName);
/*  317 */           if (isSuper || methodNode.owner.equals(ownerName)) {
/*  318 */             return new DelegateInitialiser(methodNode, isSuper);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  324 */     return DelegateInitialiser.NONE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void textify(ClassNode classNode, OutputStream out) {
/*  335 */     classNode.accept((ClassVisitor)new TraceClassVisitor(new PrintWriter(out)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void textify(MethodNode methodNode, OutputStream out) {
/*  346 */     TraceClassVisitor trace = new TraceClassVisitor(new PrintWriter(out));
/*  347 */     MethodVisitor mv = trace.visitMethod(methodNode.access, methodNode.name, methodNode.desc, methodNode.signature, (String[])methodNode.exceptions
/*  348 */         .toArray((Object[])new String[0]));
/*  349 */     methodNode.accept(mv);
/*  350 */     trace.visitEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void dumpClass(ClassNode classNode) {
/*  359 */     ClassWriter cw = new ClassWriter(3);
/*  360 */     classNode.accept((ClassVisitor)cw);
/*  361 */     dumpClass(cw.toByteArray());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void dumpClass(byte[] bytes) {
/*  370 */     ClassReader cr = new ClassReader(bytes);
/*  371 */     CheckClassAdapter.verify(cr, true, new PrintWriter(System.out));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void printMethodWithOpcodeIndices(MethodNode method) {
/*  380 */     System.err.printf("%s%s\n", new Object[] { method.name, method.desc });
/*  381 */     int i = 0;
/*  382 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext();) {
/*  383 */       System.err.printf("[%4d] %s\n", new Object[] { Integer.valueOf(i++), describeNode(iter.next()) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void printMethod(MethodNode method) {
/*  393 */     System.err.printf("%s%s maxStack=%d maxLocals=%d\n", new Object[] { method.name, method.desc, Integer.valueOf(method.maxStack), Integer.valueOf(method.maxLocals) });
/*  394 */     int index = 0;
/*  395 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  396 */       System.err.printf("%-4d  ", new Object[] { Integer.valueOf(index++) });
/*  397 */       printNode(iter.next());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void printNode(AbstractInsnNode node) {
/*  407 */     System.err.printf("%s\n", new Object[] { describeNode(node) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String describeNode(AbstractInsnNode node) {
/*  417 */     if (node == null) {
/*  418 */       return String.format("   %-14s ", new Object[] { "null" });
/*      */     }
/*      */     
/*  421 */     if (node instanceof LabelNode) {
/*  422 */       return String.format("[%s]", new Object[] { ((LabelNode)node).getLabel() });
/*      */     }
/*      */     
/*  425 */     String out = String.format("   %-14s ", new Object[] { node.getClass().getSimpleName().replace("Node", "") });
/*  426 */     if (node instanceof JumpInsnNode) {
/*  427 */       out = out + String.format("[%s] [%s]", new Object[] { getOpcodeName(node), ((JumpInsnNode)node).label.getLabel() });
/*  428 */     } else if (node instanceof VarInsnNode) {
/*  429 */       out = out + String.format("[%s] %d", new Object[] { getOpcodeName(node), Integer.valueOf(((VarInsnNode)node).var) });
/*  430 */     } else if (node instanceof MethodInsnNode) {
/*  431 */       MethodInsnNode mth = (MethodInsnNode)node;
/*  432 */       out = out + String.format("[%s] %s::%s%s", new Object[] { getOpcodeName(node), mth.owner, mth.name, mth.desc });
/*  433 */     } else if (node instanceof FieldInsnNode) {
/*  434 */       FieldInsnNode fld = (FieldInsnNode)node;
/*  435 */       out = out + String.format("[%s] %s::%s:%s", new Object[] { getOpcodeName(node), fld.owner, fld.name, fld.desc });
/*  436 */     } else if (node instanceof InvokeDynamicInsnNode) {
/*  437 */       InvokeDynamicInsnNode idc = (InvokeDynamicInsnNode)node;
/*  438 */       out = out + String.format("[%s] %s%s { %s %s::%s%s }", new Object[] { getOpcodeName(node), idc.name, idc.desc, 
/*  439 */             getOpcodeName(idc.bsm.getTag(), "H_GETFIELD", 1), idc.bsm.getOwner(), idc.bsm.getName(), idc.bsm.getDesc() });
/*  440 */     } else if (node instanceof LineNumberNode) {
/*  441 */       LineNumberNode ln = (LineNumberNode)node;
/*  442 */       out = out + String.format("LINE=[%d] LABEL=[%s]", new Object[] { Integer.valueOf(ln.line), ln.start.getLabel() });
/*  443 */     } else if (node instanceof LdcInsnNode) {
/*  444 */       out = out + ((LdcInsnNode)node).cst;
/*  445 */     } else if (node instanceof IntInsnNode) {
/*  446 */       out = out + ((IntInsnNode)node).operand;
/*  447 */     } else if (node instanceof FrameNode) {
/*  448 */       out = out + String.format("[%s] ", new Object[] { getOpcodeName(((FrameNode)node).type, "H_INVOKEINTERFACE", -1) });
/*      */     } else {
/*  450 */       out = out + String.format("[%s] ", new Object[] { getOpcodeName(node) });
/*      */     } 
/*  452 */     return out;
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
/*      */   public static String getOpcodeName(AbstractInsnNode node) {
/*  464 */     return (node != null) ? getOpcodeName(node.getOpcode()) : "";
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
/*      */   public static String getOpcodeName(int opcode) {
/*  476 */     return getOpcodeName(opcode, "UNINITIALIZED_THIS", 1);
/*      */   }
/*      */   
/*      */   private static String getOpcodeName(int opcode, String start, int min) {
/*  480 */     if (opcode >= min) {
/*  481 */       boolean found = false;
/*      */       
/*      */       try {
/*  484 */         for (Field f : Opcodes.class.getDeclaredFields()) {
/*  485 */           if (found || f.getName().equals(start)) {
/*      */ 
/*      */             
/*  488 */             found = true;
/*  489 */             if (f.getType() == int.class && f.getInt(null) == opcode)
/*  490 */               return f.getName(); 
/*      */           } 
/*      */         } 
/*  493 */       } catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  498 */     return (opcode >= 0) ? String.valueOf(opcode) : "UNKNOWN";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean methodHasLineNumbers(MethodNode method) {
/*  508 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext();) {
/*  509 */       if (iter.next() instanceof LineNumberNode) {
/*  510 */         return true;
/*      */       }
/*      */     } 
/*  513 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isStatic(MethodNode method) {
/*  523 */     return ((method.access & 0x8) == 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isStatic(FieldNode field) {
/*  533 */     return ((field.access & 0x8) == 8);
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
/*      */   public static int getFirstNonArgLocalIndex(MethodNode method) {
/*  547 */     return getFirstNonArgLocalIndex(Type.getArgumentTypes(method.desc), !isStatic(method));
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
/*      */   public static int getFirstNonArgLocalIndex(Type[] args, boolean includeThis) {
/*  563 */     return getArgsSize(args) + (includeThis ? 1 : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getArgsSize(Type[] args) {
/*  574 */     return getArgsSize(args, 0, args.length);
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
/*      */   public static int getArgsSize(Type[] args, int startIndex, int endIndex) {
/*  588 */     int size = 0;
/*      */     
/*  590 */     for (int index = startIndex; index < args.length && index < endIndex; index++) {
/*  591 */       size += args[index].getSize();
/*      */     }
/*      */     
/*  594 */     return size;
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
/*      */   public static void loadArgs(Type[] args, InsnList insns, int pos) {
/*  606 */     loadArgs(args, insns, pos, -1);
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
/*      */   public static void loadArgs(Type[] args, InsnList insns, int start, int end) {
/*  620 */     loadArgs(args, insns, start, end, null);
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
/*      */   public static void loadArgs(Type[] args, InsnList insns, int start, int end, Type[] casts) {
/*  635 */     int pos = start;
/*      */     
/*  637 */     for (int index = 0; index < args.length; index++) {
/*  638 */       insns.add((AbstractInsnNode)new VarInsnNode(args[index].getOpcode(21), pos));
/*  639 */       if (casts != null && index < casts.length && casts[index] != null) {
/*  640 */         insns.add((AbstractInsnNode)new TypeInsnNode(192, casts[index].getInternalName()));
/*      */       }
/*  642 */       pos += args[index].getSize();
/*  643 */       if (end >= start && pos >= end) {
/*      */         return;
/*      */       }
/*      */     } 
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
/*      */   public static Map<LabelNode, LabelNode> cloneLabels(InsnList source) {
/*  658 */     Map<LabelNode, LabelNode> labels = new HashMap<>();
/*      */     
/*  660 */     for (Iterator<AbstractInsnNode> iter = source.iterator(); iter.hasNext(); ) {
/*  661 */       AbstractInsnNode insn = iter.next();
/*  662 */       if (insn instanceof LabelNode) {
/*  663 */         labels.put((LabelNode)insn, new LabelNode(((LabelNode)insn).getLabel()));
/*      */       }
/*      */     } 
/*      */     
/*  667 */     return labels;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String generateDescriptor(Type returnType, Type... args) {
/*  678 */     return generateDescriptor(returnType, (Object[])args);
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
/*      */   public static String generateDescriptor(Object returnType, Object... args) {
/*  691 */     StringBuilder sb = (new StringBuilder()).append('(');
/*      */     
/*  693 */     for (Object arg : args) {
/*  694 */       sb.append(toDescriptor(arg));
/*      */     }
/*      */     
/*  697 */     return sb.append(')').append((returnType != null) ? toDescriptor(returnType) : "V").toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String toDescriptor(Object arg) {
/*  707 */     if (arg instanceof String)
/*  708 */       return (String)arg; 
/*  709 */     if (arg instanceof Type)
/*  710 */       return arg.toString(); 
/*  711 */     if (arg instanceof Class) {
/*  712 */       return Type.getDescriptor((Class)arg);
/*      */     }
/*  714 */     return (arg == null) ? "" : arg.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getDescriptor(Type... args) {
/*  725 */     return "(" + Joiner.on("").join((Object[])args) + ")";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getDescriptor(Type returnType, Type... args) {
/*  736 */     return getDescriptor(args) + returnType.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String changeDescriptorReturnType(String desc, String returnType) {
/*  747 */     if (desc == null || !desc.startsWith("(") || desc.lastIndexOf(')') < 1)
/*  748 */       return null; 
/*  749 */     if (returnType == null) {
/*  750 */       return desc;
/*      */     }
/*  752 */     return desc.substring(0, desc.lastIndexOf(')') + 1) + returnType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSimpleName(Class<? extends Annotation> annotationType) {
/*  763 */     return annotationType.getSimpleName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSimpleName(Type type) {
/*  773 */     return (type.getSort() < 9) ? type.getDescriptor() : getSimpleName(type.getClassName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSimpleName(AnnotationNode annotation) {
/*  784 */     return getSimpleName(annotation.desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSimpleName(String desc) {
/*  794 */     int pos = Math.max(desc.lastIndexOf('/'), 0);
/*  795 */     return desc.substring(pos + 1).replace(";", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isConstant(AbstractInsnNode insn) {
/*  806 */     if (insn == null) {
/*  807 */       return false;
/*      */     }
/*  809 */     return Ints.contains(CONSTANTS_ALL, insn.getOpcode());
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
/*      */   public static Object getConstant(AbstractInsnNode insn) {
/*  821 */     if (insn == null)
/*  822 */       return null; 
/*  823 */     if (insn instanceof LdcInsnNode)
/*  824 */       return ((LdcInsnNode)insn).cst; 
/*  825 */     if (insn instanceof IntInsnNode) {
/*  826 */       int value = ((IntInsnNode)insn).operand;
/*  827 */       if (insn.getOpcode() == 16 || insn.getOpcode() == 17) {
/*  828 */         return Integer.valueOf(value);
/*      */       }
/*  830 */       throw new IllegalArgumentException("IntInsnNode with invalid opcode " + insn.getOpcode() + " in getConstant");
/*  831 */     }  if (insn instanceof TypeInsnNode) {
/*  832 */       if (insn.getOpcode() < 192) {
/*  833 */         return null;
/*      */       }
/*  835 */       return Type.getObjectType(((TypeInsnNode)insn).desc);
/*      */     } 
/*      */     
/*  838 */     int index = Ints.indexOf(CONSTANTS_ALL, insn.getOpcode());
/*  839 */     return (index < 0) ? null : CONSTANTS_VALUES[index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type getConstantType(AbstractInsnNode insn) {
/*  850 */     if (insn == null)
/*  851 */       return null; 
/*  852 */     if (insn instanceof LdcInsnNode) {
/*  853 */       Object cst = ((LdcInsnNode)insn).cst;
/*  854 */       if (cst instanceof Integer)
/*  855 */         return Type.getType("I"); 
/*  856 */       if (cst instanceof Float)
/*  857 */         return Type.getType("F"); 
/*  858 */       if (cst instanceof Long)
/*  859 */         return Type.getType("J"); 
/*  860 */       if (cst instanceof Double)
/*  861 */         return Type.getType("D"); 
/*  862 */       if (cst instanceof String)
/*  863 */         return Type.getType("Ljava/lang/String;"); 
/*  864 */       if (cst instanceof Type) {
/*  865 */         return Type.getType("Ljava/lang/Class;");
/*      */       }
/*  867 */       throw new IllegalArgumentException("LdcInsnNode with invalid payload type " + cst.getClass() + " in getConstant");
/*  868 */     }  if (insn instanceof TypeInsnNode) {
/*  869 */       if (insn.getOpcode() < 192) {
/*  870 */         return null;
/*      */       }
/*  872 */       return Type.getType("Ljava/lang/Class;");
/*      */     } 
/*      */     
/*  875 */     int index = Ints.indexOf(CONSTANTS_ALL, insn.getOpcode());
/*  876 */     return (index < 0) ? null : Type.getType(CONSTANTS_TYPES[index]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasFlag(ClassNode classNode, int flag) {
/*  887 */     return ((classNode.access & flag) == flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasFlag(MethodNode method, int flag) {
/*  898 */     return ((method.access & flag) == flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasFlag(FieldNode field, int flag) {
/*  909 */     return ((field.access & flag) == flag);
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
/*      */   public static boolean compareFlags(MethodNode m1, MethodNode m2, int flag) {
/*  922 */     return (hasFlag(m1, flag) == hasFlag(m2, flag));
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
/*      */   public static boolean compareFlags(FieldNode f1, FieldNode f2, int flag) {
/*  935 */     return (hasFlag(f1, flag) == hasFlag(f2, flag));
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
/*      */   public static boolean isVirtual(MethodNode method) {
/*  947 */     return (method != null && !isStatic(method) && getVisibility(method).isAtLeast(Visibility.PROTECTED));
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
/*      */   public static Visibility getVisibility(MethodNode method) {
/*  965 */     return getVisibility(method.access & 0x7);
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
/*      */   public static Visibility getVisibility(FieldNode field) {
/*  983 */     return getVisibility(field.access & 0x7);
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
/*      */   private static Visibility getVisibility(int flags) {
/* 1001 */     if ((flags & 0x4) != 0)
/* 1002 */       return Visibility.PROTECTED; 
/* 1003 */     if ((flags & 0x2) != 0)
/* 1004 */       return Visibility.PRIVATE; 
/* 1005 */     if ((flags & 0x1) != 0) {
/* 1006 */       return Visibility.PUBLIC;
/*      */     }
/* 1008 */     return Visibility.PACKAGE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setVisibility(MethodNode method, Visibility visibility) {
/* 1019 */     method.access = setVisibility(method.access, visibility.access);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setVisibility(FieldNode field, Visibility visibility) {
/* 1030 */     field.access = setVisibility(field.access, visibility.access);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setVisibility(MethodNode method, int access) {
/* 1041 */     method.access = setVisibility(method.access, access);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setVisibility(FieldNode field, int access) {
/* 1052 */     field.access = setVisibility(field.access, access);
/*      */   }
/*      */   
/*      */   private static int setVisibility(int oldAccess, int newAccess) {
/* 1056 */     return oldAccess & 0xFFFFFFF8 | newAccess & 0x7;
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
/*      */   public static int getMaxLineNumber(ClassNode classNode, int min, int pad) {
/* 1068 */     int max = 0;
/* 1069 */     for (MethodNode method : classNode.methods) {
/* 1070 */       for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/* 1071 */         AbstractInsnNode insn = iter.next();
/* 1072 */         if (insn instanceof LineNumberNode) {
/* 1073 */           max = Math.max(max, ((LineNumberNode)insn).line);
/*      */         }
/*      */       } 
/*      */     } 
/* 1077 */     return Math.max(min, max + pad);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getBoxingType(Type type) {
/* 1088 */     return (type == null) ? null : BOXING_TYPES[type.getSort()];
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
/*      */   public static String getUnboxingMethod(Type type) {
/* 1101 */     return (type == null) ? null : UNBOXING_METHODS[type.getSort()];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void compareBridgeMethods(MethodNode a, MethodNode b) {
/* 1112 */     ListIterator<AbstractInsnNode> ia = a.instructions.iterator();
/* 1113 */     ListIterator<AbstractInsnNode> ib = b.instructions.iterator();
/*      */     
/* 1115 */     int index = 0;
/* 1116 */     for (; ia.hasNext() && ib.hasNext(); index++) {
/* 1117 */       AbstractInsnNode na = ia.next();
/* 1118 */       AbstractInsnNode nb = ib.next();
/* 1119 */       if (!(na instanceof LabelNode))
/*      */       {
/*      */ 
/*      */         
/* 1123 */         if (na instanceof MethodInsnNode) {
/* 1124 */           MethodInsnNode ma = (MethodInsnNode)na;
/* 1125 */           MethodInsnNode mb = (MethodInsnNode)nb;
/* 1126 */           if (!ma.name.equals(mb.name))
/* 1127 */             throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_NAME, a.name, a.desc, index, na, nb); 
/* 1128 */           if (!ma.desc.equals(mb.desc))
/* 1129 */             throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_DESC, a.name, a.desc, index, na, nb); 
/*      */         } else {
/* 1131 */           if (na.getOpcode() != nb.getOpcode())
/* 1132 */             throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INSN, a.name, a.desc, index, na, nb); 
/* 1133 */           if (na instanceof VarInsnNode) {
/* 1134 */             VarInsnNode va = (VarInsnNode)na;
/* 1135 */             VarInsnNode vb = (VarInsnNode)nb;
/* 1136 */             if (va.var != vb.var) {
/* 1137 */               throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LOAD, a.name, a.desc, index, na, nb);
/*      */             }
/* 1139 */           } else if (na instanceof TypeInsnNode) {
/* 1140 */             TypeInsnNode ta = (TypeInsnNode)na;
/* 1141 */             TypeInsnNode tb = (TypeInsnNode)nb;
/* 1142 */             if (ta.getOpcode() == 192 && !ta.desc.equals(tb.desc))
/* 1143 */               throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_CAST, a.name, a.desc, index, na, nb); 
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 1148 */     if (ia.hasNext() || ib.hasNext()) {
/* 1149 */       throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LENGTH, a.name, a.desc, index, null, null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void merge(ClassNode source, ClassNode dest) {
/* 1160 */     if (source == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1164 */     if (dest == null) {
/* 1165 */       throw new NullPointerException("Target ClassNode for merge must not be null");
/*      */     }
/*      */     
/* 1168 */     dest.version = Math.max(source.version, dest.version);
/*      */     
/* 1170 */     dest.interfaces = merge(source.interfaces, dest.interfaces);
/* 1171 */     dest.invisibleAnnotations = merge(source.invisibleAnnotations, dest.invisibleAnnotations);
/* 1172 */     dest.visibleAnnotations = merge(source.visibleAnnotations, dest.visibleAnnotations);
/* 1173 */     dest.visibleTypeAnnotations = merge(source.visibleTypeAnnotations, dest.visibleTypeAnnotations);
/* 1174 */     dest.invisibleTypeAnnotations = merge(source.invisibleTypeAnnotations, dest.invisibleTypeAnnotations);
/* 1175 */     dest.attrs = merge(source.attrs, dest.attrs);
/* 1176 */     dest.innerClasses = merge(source.innerClasses, dest.innerClasses);
/* 1177 */     dest.fields = merge(source.fields, dest.fields);
/* 1178 */     dest.methods = merge(source.methods, dest.methods);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void replace(ClassNode source, ClassNode dest) {
/* 1189 */     if (source == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1193 */     if (dest == null) {
/* 1194 */       throw new NullPointerException("Target ClassNode for replace must not be null");
/*      */     }
/*      */     
/* 1197 */     dest.name = source.name;
/* 1198 */     dest.signature = source.signature;
/* 1199 */     dest.superName = source.superName;
/*      */     
/* 1201 */     dest.version = source.version;
/* 1202 */     dest.access = source.access;
/* 1203 */     dest.sourceDebug = source.sourceDebug;
/*      */     
/* 1205 */     dest.sourceFile = source.sourceFile;
/* 1206 */     dest.outerClass = source.outerClass;
/* 1207 */     dest.outerMethod = source.outerMethod;
/* 1208 */     dest.outerMethodDesc = source.outerMethodDesc;
/*      */     
/* 1210 */     clear(dest.interfaces);
/* 1211 */     clear(dest.visibleAnnotations);
/* 1212 */     clear(dest.invisibleAnnotations);
/* 1213 */     clear(dest.visibleTypeAnnotations);
/* 1214 */     clear(dest.invisibleTypeAnnotations);
/* 1215 */     clear(dest.attrs);
/* 1216 */     clear(dest.innerClasses);
/* 1217 */     clear(dest.fields);
/* 1218 */     clear(dest.methods);
/*      */     
/* 1220 */     if (ASM.API_VERSION >= 393216) {
/* 1221 */       dest.module = source.module;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1229 */     merge(source, dest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> void clear(List<T> list) {
/* 1237 */     if (list != null) {
/* 1238 */       list.clear();
/*      */     }
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
/*      */   private static <T> List<T> merge(List<T> source, List<T> destination) {
/* 1252 */     if (source == null || source.isEmpty()) {
/* 1253 */       return destination;
/*      */     }
/*      */     
/* 1256 */     if (destination == null) {
/* 1257 */       return new ArrayList<>(source);
/*      */     }
/*      */     
/* 1260 */     destination.addAll(source);
/* 1261 */     return destination;
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\Bytecode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */