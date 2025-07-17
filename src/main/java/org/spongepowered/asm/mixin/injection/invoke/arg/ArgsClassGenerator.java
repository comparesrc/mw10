/*     */ package org.spongepowered.asm.mixin.injection.invoke.arg;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.ClassVisitor;
/*     */ import org.objectweb.asm.Label;
/*     */ import org.objectweb.asm.MethodVisitor;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.objectweb.asm.util.CheckClassAdapter;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.SyntheticClassInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
/*     */ import org.spongepowered.asm.service.ISyntheticClassInfo;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.IConsumer;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
/*     */ import org.spongepowered.asm.util.asm.MethodVisitorEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ArgsClassGenerator
/*     */   implements IClassGenerator
/*     */ {
/*  60 */   public static final String ARGS_NAME = Args.class.getName();
/*  61 */   public static final String ARGS_REF = ARGS_NAME.replace('.', '/');
/*     */   
/*     */   public static final String GETTER_PREFIX = "$";
/*     */   
/*     */   private static final String CLASS_NAME_BASE = "org.spongepowered.asm.synthetic.args.Args$";
/*     */   
/*     */   private static final String OBJECT = "java/lang/Object";
/*     */   
/*     */   private static final String OBJECT_ARRAY = "[Ljava/lang/Object;";
/*     */   
/*     */   private static final String VALUES_FIELD = "values";
/*     */   
/*     */   private static final String CTOR_DESC = "([Ljava/lang/Object;)V";
/*     */   
/*     */   private static final String SET = "set";
/*     */   
/*     */   private static final String SET_DESC = "(ILjava/lang/Object;)V";
/*     */   
/*     */   private static final String SETALL = "setAll";
/*     */   
/*     */   private static final String SETALL_DESC = "([Ljava/lang/Object;)V";
/*     */   
/*     */   private static final String NPE = "java/lang/NullPointerException";
/*     */   
/*     */   private static final String NPE_CTOR_DESC = "(Ljava/lang/String;)V";
/*     */   
/*     */   private static final String AIOOBE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException";
/*     */   
/*     */   private static final String AIOOBE_CTOR_DESC = "(I)V";
/*     */   private static final String ACE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException";
/*     */   private static final String ACE_CTOR_DESC = "(IILjava/lang/String;)V";
/*  92 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */   
/*     */   private final IConsumer<ISyntheticClassInfo> registry;
/*     */ 
/*     */   
/*     */   class ArgsClassInfo
/*     */     extends SyntheticClassInfo
/*     */   {
/*     */     final String desc;
/*     */     
/*     */     final Type[] args;
/* 103 */     int loaded = 0;
/*     */     
/*     */     ArgsClassInfo(IMixinInfo mixin, String name, String desc) {
/* 106 */       super(mixin, name);
/* 107 */       this.desc = desc;
/* 108 */       this.args = Type.getArgumentTypes(desc);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isLoaded() {
/* 113 */       return (this.loaded > 0);
/*     */     }
/*     */     
/*     */     String getSignature() {
/* 117 */       return (new SignaturePrinter("", null, this.args)).setFullyQualified(true).getFormattedArgs();
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
/* 130 */   private int nextIndex = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   private final Map<String, ArgsClassInfo> descToClass = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   private final Map<String, ArgsClassInfo> nameToClass = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArgsClassGenerator(IConsumer<ISyntheticClassInfo> registry) {
/* 148 */     this.registry = registry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 157 */     return "args";
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
/*     */   public ISyntheticClassInfo getArgsClass(String desc, IMixinInfo mixin) {
/* 173 */     String voidDesc = Bytecode.changeDescriptorReturnType(desc, "V");
/* 174 */     ArgsClassInfo info = this.descToClass.get(voidDesc);
/* 175 */     if (info == null) {
/* 176 */       String name = String.format("%s%d", new Object[] { "org.spongepowered.asm.synthetic.args.Args$", Integer.valueOf(this.nextIndex++) });
/* 177 */       logger.debug("ArgsClassGenerator assigning {} for descriptor {}", new Object[] { name, voidDesc });
/* 178 */       info = new ArgsClassInfo(mixin, name, voidDesc);
/* 179 */       this.descToClass.put(voidDesc, info);
/* 180 */       this.nameToClass.put(name, info);
/* 181 */       this.registry.accept(info);
/*     */     } 
/* 183 */     return (ISyntheticClassInfo)info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generate(String name, ClassNode classNode) {
/*     */     CheckClassAdapter checkClassAdapter;
/* 192 */     ArgsClassInfo info = this.nameToClass.get(name);
/* 193 */     if (info == null) {
/* 194 */       return false;
/*     */     }
/*     */     
/* 197 */     if (info.loaded > 0) {
/* 198 */       logger.debug("ArgsClassGenerator is re-generating {}, already did this {} times!", new Object[] { name, Integer.valueOf(info.loaded) });
/*     */     }
/*     */     
/* 201 */     ClassNode classNode1 = classNode;
/* 202 */     if (MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
/* 203 */       checkClassAdapter = new CheckClassAdapter((ClassVisitor)classNode);
/*     */     }
/*     */     
/* 206 */     checkClassAdapter.visit(50, 4129, info.getName(), null, ARGS_REF, null);
/*     */     
/* 208 */     checkClassAdapter.visitSource(name.substring(name.lastIndexOf('.') + 1) + ".java", null);
/*     */     
/* 210 */     generateCtor(info, (ClassVisitor)checkClassAdapter);
/* 211 */     generateToString(info, (ClassVisitor)checkClassAdapter);
/* 212 */     generateFactory(info, (ClassVisitor)checkClassAdapter);
/* 213 */     generateSetters(info, (ClassVisitor)checkClassAdapter);
/* 214 */     generateGetters(info, (ClassVisitor)checkClassAdapter);
/*     */     
/* 216 */     checkClassAdapter.visitEnd();
/* 217 */     info.loaded++;
/*     */     
/* 219 */     return true;
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
/*     */   private void generateCtor(ArgsClassInfo info, ClassVisitor writer) {
/* 232 */     MethodVisitor ctor = writer.visitMethod(2, "<init>", "([Ljava/lang/Object;)V", null, null);
/* 233 */     ctor.visitCode();
/* 234 */     ctor.visitVarInsn(25, 0);
/* 235 */     ctor.visitVarInsn(25, 1);
/* 236 */     ctor.visitMethodInsn(183, ARGS_REF, "<init>", "([Ljava/lang/Object;)V", false);
/* 237 */     ctor.visitInsn(177);
/* 238 */     ctor.visitMaxs(2, 2);
/* 239 */     ctor.visitEnd();
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
/*     */   private void generateToString(ArgsClassInfo info, ClassVisitor writer) {
/* 251 */     MethodVisitor toString = writer.visitMethod(1, "toString", "()Ljava/lang/String;", null, null);
/* 252 */     toString.visitCode();
/* 253 */     toString.visitLdcInsn("Args" + info.getSignature());
/* 254 */     toString.visitInsn(176);
/* 255 */     toString.visitMaxs(1, 1);
/* 256 */     toString.visitEnd();
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
/*     */   private void generateFactory(ArgsClassInfo info, ClassVisitor writer) {
/* 271 */     String ref = info.getName();
/* 272 */     String factoryDesc = Bytecode.changeDescriptorReturnType(info.desc, "L" + ref + ";");
/* 273 */     MethodVisitorEx of = new MethodVisitorEx(writer.visitMethod(9, "of", factoryDesc, null, null));
/* 274 */     of.visitCode();
/*     */ 
/*     */     
/* 277 */     of.visitTypeInsn(187, ref);
/* 278 */     of.visitInsn(89);
/*     */ 
/*     */     
/* 281 */     of.visitConstant((byte)info.args.length);
/* 282 */     of.visitTypeInsn(189, "java/lang/Object");
/*     */     
/*     */     byte index, argIndex;
/* 285 */     for (index = 0, argIndex = 0; index < info.args.length; index = (byte)(index + 1)) {
/* 286 */       Type arg = info.args[index];
/* 287 */       of.visitInsn(89);
/* 288 */       of.visitConstant(index);
/* 289 */       of.visitVarInsn(arg.getOpcode(21), argIndex);
/* 290 */       box((MethodVisitor)of, arg);
/* 291 */       of.visitInsn(83);
/* 292 */       argIndex = (byte)(argIndex + arg.getSize());
/*     */     } 
/*     */ 
/*     */     
/* 296 */     of.visitMethodInsn(183, ref, "<init>", "([Ljava/lang/Object;)V", false);
/*     */ 
/*     */     
/* 299 */     of.visitInsn(176);
/*     */     
/* 301 */     of.visitMaxs(6, Bytecode.getArgsSize(info.args));
/* 302 */     of.visitEnd();
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
/*     */   private void generateGetters(ArgsClassInfo info, ClassVisitor writer) {
/* 317 */     byte argIndex = 0;
/* 318 */     for (Type arg : info.args) {
/* 319 */       String name = "$" + argIndex;
/* 320 */       String sig = "()" + arg.getDescriptor();
/* 321 */       MethodVisitorEx get = new MethodVisitorEx(writer.visitMethod(1, name, sig, null, null));
/* 322 */       get.visitCode();
/*     */ 
/*     */       
/* 325 */       get.visitVarInsn(25, 0);
/* 326 */       get.visitFieldInsn(180, info.getName(), "values", "[Ljava/lang/Object;");
/* 327 */       get.visitConstant(argIndex);
/* 328 */       get.visitInsn(50);
/*     */ 
/*     */       
/* 331 */       unbox((MethodVisitor)get, arg);
/*     */ 
/*     */       
/* 334 */       get.visitInsn(arg.getOpcode(172));
/*     */       
/* 336 */       get.visitMaxs(2, 1);
/* 337 */       get.visitEnd();
/* 338 */       argIndex = (byte)(argIndex + 1);
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
/*     */   private void generateSetters(ArgsClassInfo info, ClassVisitor writer) {
/* 352 */     generateIndexedSetter(info, writer);
/* 353 */     generateMultiSetter(info, writer);
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
/*     */   private void generateIndexedSetter(ArgsClassInfo info, ClassVisitor writer) {
/* 368 */     MethodVisitorEx set = new MethodVisitorEx(writer.visitMethod(1, "set", "(ILjava/lang/Object;)V", null, null));
/*     */     
/* 370 */     set.visitCode();
/*     */     
/* 372 */     Label store = new Label(), checkNull = new Label();
/* 373 */     Label[] labels = new Label[info.args.length];
/* 374 */     for (int label = 0; label < labels.length; label++) {
/* 375 */       labels[label] = new Label();
/*     */     }
/*     */ 
/*     */     
/* 379 */     set.visitVarInsn(25, 0);
/* 380 */     set.visitFieldInsn(180, info.getName(), "values", "[Ljava/lang/Object;");
/*     */     
/*     */     byte b;
/* 383 */     for (b = 0; b < info.args.length; b = (byte)(b + 1)) {
/* 384 */       set.visitVarInsn(21, 1);
/* 385 */       set.visitConstant(b);
/* 386 */       set.visitJumpInsn(159, labels[b]);
/*     */     } 
/*     */ 
/*     */     
/* 390 */     throwAIOOBE(set, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 395 */     for (int index = 0; index < info.args.length; index++) {
/* 396 */       String boxingType = Bytecode.getBoxingType(info.args[index]);
/* 397 */       set.visitLabel(labels[index]);
/* 398 */       set.visitVarInsn(21, 1);
/* 399 */       set.visitVarInsn(25, 2);
/* 400 */       set.visitTypeInsn(192, (boxingType != null) ? boxingType : info.args[index].getInternalName());
/* 401 */       set.visitJumpInsn(167, (boxingType != null) ? checkNull : store);
/*     */     } 
/*     */ 
/*     */     
/* 405 */     set.visitLabel(checkNull);
/* 406 */     set.visitInsn(89);
/* 407 */     set.visitJumpInsn(199, store);
/*     */ 
/*     */     
/* 410 */     throwNPE(set, "Argument with primitive type cannot be set to NULL");
/*     */ 
/*     */     
/* 413 */     set.visitLabel(store);
/* 414 */     set.visitInsn(83);
/* 415 */     set.visitInsn(177);
/* 416 */     set.visitMaxs(6, 3);
/* 417 */     set.visitEnd();
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
/*     */   private void generateMultiSetter(ArgsClassInfo info, ClassVisitor writer) {
/* 431 */     MethodVisitorEx set = new MethodVisitorEx(writer.visitMethod(1, "setAll", "([Ljava/lang/Object;)V", null, null));
/*     */     
/* 433 */     set.visitCode();
/*     */     
/* 435 */     Label lengthOk = new Label(), nullPrimitive = new Label();
/* 436 */     int maxStack = 6;
/*     */ 
/*     */     
/* 439 */     set.visitVarInsn(25, 1);
/* 440 */     set.visitInsn(190);
/* 441 */     set.visitInsn(89);
/* 442 */     set.visitConstant((byte)info.args.length);
/*     */ 
/*     */     
/* 445 */     set.visitJumpInsn(159, lengthOk);
/*     */ 
/*     */     
/* 448 */     set.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException");
/* 449 */     set.visitInsn(89);
/* 450 */     set.visitInsn(93);
/* 451 */     set.visitInsn(88);
/* 452 */     set.visitConstant((byte)info.args.length);
/* 453 */     set.visitLdcInsn(info.getSignature());
/*     */     
/* 455 */     set.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException", "<init>", "(IILjava/lang/String;)V", false);
/* 456 */     set.visitInsn(191);
/*     */     
/* 458 */     set.visitLabel(lengthOk);
/* 459 */     set.visitInsn(87);
/*     */ 
/*     */     
/* 462 */     set.visitVarInsn(25, 0);
/* 463 */     set.visitFieldInsn(180, info.getName(), "values", "[Ljava/lang/Object;");
/*     */     byte index;
/* 465 */     for (index = 0; index < info.args.length; index = (byte)(index + 1)) {
/*     */       
/* 467 */       set.visitInsn(89);
/* 468 */       set.visitConstant(index);
/*     */ 
/*     */       
/* 471 */       set.visitVarInsn(25, 1);
/* 472 */       set.visitConstant(index);
/* 473 */       set.visitInsn(50);
/*     */ 
/*     */       
/* 476 */       String boxingType = Bytecode.getBoxingType(info.args[index]);
/* 477 */       set.visitTypeInsn(192, (boxingType != null) ? boxingType : info.args[index].getInternalName());
/*     */ 
/*     */       
/* 480 */       if (boxingType != null) {
/* 481 */         set.visitInsn(89);
/* 482 */         set.visitJumpInsn(198, nullPrimitive);
/* 483 */         maxStack = 7;
/*     */       } 
/*     */ 
/*     */       
/* 487 */       set.visitInsn(83);
/*     */     } 
/*     */     
/* 490 */     set.visitInsn(177);
/*     */     
/* 492 */     set.visitLabel(nullPrimitive);
/* 493 */     throwNPE(set, "Argument with primitive type cannot be set to NULL");
/* 494 */     set.visitInsn(177);
/*     */     
/* 496 */     set.visitMaxs(maxStack, 2);
/* 497 */     set.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void throwNPE(MethodVisitorEx method, String message) {
/* 504 */     method.visitTypeInsn(187, "java/lang/NullPointerException");
/* 505 */     method.visitInsn(89);
/* 506 */     method.visitLdcInsn(message);
/* 507 */     method.visitMethodInsn(183, "java/lang/NullPointerException", "<init>", "(Ljava/lang/String;)V", false);
/* 508 */     method.visitInsn(191);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void throwAIOOBE(MethodVisitorEx method, int arg) {
/* 516 */     method.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException");
/* 517 */     method.visitInsn(89);
/* 518 */     method.visitVarInsn(21, arg);
/* 519 */     method.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException", "<init>", "(I)V", false);
/* 520 */     method.visitInsn(191);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void box(MethodVisitor method, Type var) {
/* 531 */     String boxingType = Bytecode.getBoxingType(var);
/* 532 */     if (boxingType != null) {
/* 533 */       String desc = String.format("(%s)L%s;", new Object[] { var.getDescriptor(), boxingType });
/* 534 */       method.visitMethodInsn(184, boxingType, "valueOf", desc, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void unbox(MethodVisitor method, Type var) {
/* 545 */     String boxingType = Bytecode.getBoxingType(var);
/* 546 */     if (boxingType != null) {
/* 547 */       String unboxingMethod = Bytecode.getUnboxingMethod(var);
/* 548 */       String desc = "()" + var.getDescriptor();
/* 549 */       method.visitTypeInsn(192, boxingType);
/* 550 */       method.visitMethodInsn(182, boxingType, unboxingMethod, desc, false);
/*     */     } else {
/* 552 */       method.visitTypeInsn(192, var.getInternalName());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\invoke\arg\ArgsClassGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */