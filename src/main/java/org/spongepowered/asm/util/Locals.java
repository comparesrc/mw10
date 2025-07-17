/*     */ package org.spongepowered.asm.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.objectweb.asm.Opcodes;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.objectweb.asm.tree.FrameNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.objectweb.asm.tree.LabelNode;
/*     */ import org.objectweb.asm.tree.LocalVariableNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.objectweb.asm.tree.VarInsnNode;
/*     */ import org.objectweb.asm.tree.analysis.Analyzer;
/*     */ import org.objectweb.asm.tree.analysis.AnalyzerException;
/*     */ import org.objectweb.asm.tree.analysis.BasicValue;
/*     */ import org.objectweb.asm.tree.analysis.Frame;
/*     */ import org.objectweb.asm.tree.analysis.Interpreter;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
/*     */ import org.spongepowered.asm.util.asm.ASM;
/*     */ import org.spongepowered.asm.util.asm.MixinVerifier;
/*     */ import org.spongepowered.asm.util.throwables.LVTGeneratorError;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Locals
/*     */ {
/*  63 */   private static final String[] FRAME_TYPES = new String[] { "TOP", "INTEGER", "FLOAT", "DOUBLE", "LONG", "NULL", "UNINITIALIZED_THIS" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static final Map<String, List<LocalVariableNode>> calculatedLocalVariables = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadLocals(Type[] locals, InsnList insns, int pos, int limit) {
/*  86 */     for (; pos < locals.length && limit > 0; pos++) {
/*  87 */       if (locals[pos] != null) {
/*  88 */         insns.add((AbstractInsnNode)new VarInsnNode(locals[pos].getOpcode(21), pos));
/*  89 */         limit--;
/*     */       } 
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
/*     */   public static LocalVariableNode[] getLocalsAt(ClassNode classNode, MethodNode method, AbstractInsnNode node) {
/* 137 */     for (int i = 0; i < 3 && (node instanceof LabelNode || node instanceof org.objectweb.asm.tree.LineNumberNode); i++) {
/* 138 */       node = nextNode(method.instructions, node);
/*     */     }
/*     */     
/* 141 */     ClassInfo classInfo = ClassInfo.forName(classNode.name);
/* 142 */     if (classInfo == null) {
/* 143 */       throw new LVTGeneratorError("Could not load class metadata for " + classNode.name + " generating LVT for " + method.name);
/*     */     }
/* 145 */     ClassInfo.Method methodInfo = classInfo.findMethod(method, method.access | 0x40000);
/* 146 */     if (methodInfo == null) {
/* 147 */       throw new LVTGeneratorError("Could not locate method metadata for " + method.name + " generating LVT in " + classNode.name);
/*     */     }
/* 149 */     List<ClassInfo.FrameData> frames = methodInfo.getFrames();
/*     */     
/* 151 */     LocalVariableNode[] frame = new LocalVariableNode[method.maxLocals];
/* 152 */     int local = 0, index = 0;
/*     */ 
/*     */     
/* 155 */     if ((method.access & 0x8) == 0) {
/* 156 */       frame[local++] = new LocalVariableNode("this", Type.getObjectType(classNode.name).toString(), null, null, null, 0);
/*     */     }
/*     */ 
/*     */     
/* 160 */     for (Type argType : Type.getArgumentTypes(method.desc)) {
/* 161 */       frame[local] = new LocalVariableNode("arg" + index++, argType.toString(), null, null, null, local);
/* 162 */       local += argType.getSize();
/*     */     } 
/*     */     
/* 165 */     int initialFrameSize = local;
/* 166 */     int frameSize = local;
/* 167 */     int frameIndex = -1;
/* 168 */     int lastFrameSize = local;
/* 169 */     VarInsnNode storeInsn = null;
/*     */     
/* 171 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/* 172 */       AbstractInsnNode insn = iter.next();
/* 173 */       if (storeInsn != null) {
/* 174 */         frame[storeInsn.var] = getLocalVariableAt(classNode, method, insn, storeInsn.var);
/* 175 */         storeInsn = null;
/*     */       } 
/*     */       
/* 178 */       if (insn instanceof FrameNode) {
/* 179 */         frameIndex++;
/* 180 */         FrameNode frameNode = (FrameNode)insn;
/* 181 */         if (frameNode.type != 3 && frameNode.type != 4) {
/*     */ 
/*     */ 
/*     */           
/* 185 */           ClassInfo.FrameData frameData = (frameIndex < frames.size()) ? frames.get(frameIndex) : null;
/*     */           
/* 187 */           if (frameData != null) {
/* 188 */             if (frameData.type == 0) {
/* 189 */               frameSize = Math.min(frameSize, frameData.locals);
/* 190 */               lastFrameSize = frameSize;
/*     */             } else {
/* 192 */               frameSize = getAdjustedFrameSize(frameSize, frameData);
/*     */             } 
/*     */           } else {
/* 195 */             frameSize = getAdjustedFrameSize(frameSize, frameNode);
/*     */           } 
/*     */           
/* 198 */           if (frameNode.type == 2) {
/* 199 */             for (int framePos = frameSize; framePos < frame.length; framePos++) {
/* 200 */               frame[framePos] = null;
/*     */             }
/* 202 */             lastFrameSize = frameSize;
/*     */           }
/*     */           else {
/*     */             
/* 206 */             int framePos = (frameNode.type == 1) ? lastFrameSize : 0;
/* 207 */             lastFrameSize = frameSize;
/*     */ 
/*     */             
/* 210 */             for (int localPos = 0; framePos < frame.length; framePos++, localPos++) {
/*     */               
/* 212 */               Object localType = (localPos < frameNode.local.size()) ? frameNode.local.get(localPos) : null;
/*     */               
/* 214 */               if (localType instanceof String)
/* 215 */               { frame[framePos] = getLocalVariableAt(classNode, method, insn, framePos); }
/* 216 */               else if (localType instanceof Integer)
/* 217 */               { boolean isMarkerType = (localType == Opcodes.UNINITIALIZED_THIS || localType == Opcodes.NULL);
/* 218 */                 boolean is32bitValue = (localType == Opcodes.INTEGER || localType == Opcodes.FLOAT);
/* 219 */                 boolean is64bitValue = (localType == Opcodes.DOUBLE || localType == Opcodes.LONG);
/* 220 */                 if (localType != Opcodes.TOP)
/*     */                 {
/* 222 */                   if (isMarkerType) {
/* 223 */                     frame[framePos] = null;
/* 224 */                   } else if (is32bitValue || is64bitValue) {
/* 225 */                     frame[framePos] = getLocalVariableAt(classNode, method, insn, framePos);
/*     */                     
/* 227 */                     if (is64bitValue) {
/* 228 */                       framePos++;
/* 229 */                       frame[framePos] = null;
/*     */                     } 
/*     */                   } else {
/* 232 */                     throw new LVTGeneratorError("Unrecognised locals opcode " + localType + " in locals array at position " + localPos + " in " + classNode.name + "." + method.name + method.desc);
/*     */                   } 
/*     */                 } }
/* 235 */               else if (localType == null)
/* 236 */               { if (framePos >= initialFrameSize && framePos >= frameSize && frameSize > 0) {
/* 237 */                   frame[framePos] = null;
/*     */                 } }
/* 239 */               else if (!(localType instanceof LabelNode))
/*     */               
/*     */               { 
/* 242 */                 throw new LVTGeneratorError("Invalid value " + localType + " in locals array at position " + localPos + " in " + classNode.name + "." + method.name + method.desc); } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 246 */       } else if (insn instanceof VarInsnNode) {
/* 247 */         VarInsnNode varNode = (VarInsnNode)insn;
/* 248 */         boolean isLoad = (insn.getOpcode() >= 21 && insn.getOpcode() <= 53);
/* 249 */         if (isLoad) {
/* 250 */           frame[varNode.var] = getLocalVariableAt(classNode, method, insn, varNode.var);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 255 */           storeInsn = varNode;
/*     */         } 
/*     */       } 
/*     */       
/* 259 */       if (insn == node) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 265 */     for (int l = 0; l < frame.length; l++) {
/* 266 */       if (frame[l] != null && (frame[l]).desc == null) {
/* 267 */         frame[l] = null;
/*     */       }
/*     */     } 
/*     */     
/* 271 */     return frame;
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
/*     */   public static LocalVariableNode getLocalVariableAt(ClassNode classNode, MethodNode method, AbstractInsnNode node, int var) {
/* 287 */     return getLocalVariableAt(classNode, method, method.instructions.indexOf(node), var);
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
/*     */   private static LocalVariableNode getLocalVariableAt(ClassNode classNode, MethodNode method, int pos, int var) {
/* 302 */     LocalVariableNode localVariableNode = null;
/* 303 */     LocalVariableNode fallbackNode = null;
/*     */     
/* 305 */     for (LocalVariableNode local : getLocalVariableTable(classNode, method)) {
/* 306 */       if (local.index != var) {
/*     */         continue;
/*     */       }
/* 309 */       if (isOpcodeInRange(method.instructions, local, pos)) {
/* 310 */         localVariableNode = local; continue;
/* 311 */       }  if (localVariableNode == null) {
/* 312 */         fallbackNode = local;
/*     */       }
/*     */     } 
/*     */     
/* 316 */     if (localVariableNode == null && !method.localVariables.isEmpty()) {
/* 317 */       for (LocalVariableNode local : getGeneratedLocalVariableTable(classNode, method)) {
/* 318 */         if (local.index == var && isOpcodeInRange(method.instructions, local, pos)) {
/* 319 */           localVariableNode = local;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 324 */     return (localVariableNode != null) ? localVariableNode : fallbackNode;
/*     */   }
/*     */   
/*     */   private static boolean isOpcodeInRange(InsnList insns, LocalVariableNode local, int pos) {
/* 328 */     return (insns.indexOf((AbstractInsnNode)local.start) <= pos && insns.indexOf((AbstractInsnNode)local.end) > pos);
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
/*     */   public static List<LocalVariableNode> getLocalVariableTable(ClassNode classNode, MethodNode method) {
/* 343 */     if (method.localVariables.isEmpty()) {
/* 344 */       return getGeneratedLocalVariableTable(classNode, method);
/*     */     }
/* 346 */     return method.localVariables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<LocalVariableNode> getGeneratedLocalVariableTable(ClassNode classNode, MethodNode method) {
/* 357 */     String methodId = String.format("%s.%s%s", new Object[] { classNode.name, method.name, method.desc });
/* 358 */     List<LocalVariableNode> localVars = calculatedLocalVariables.get(methodId);
/* 359 */     if (localVars != null) {
/* 360 */       return localVars;
/*     */     }
/*     */     
/* 363 */     localVars = generateLocalVariableTable(classNode, method);
/* 364 */     calculatedLocalVariables.put(methodId, localVars);
/* 365 */     return localVars;
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
/*     */   public static List<LocalVariableNode> generateLocalVariableTable(ClassNode classNode, MethodNode method) {
/* 377 */     List<Type> interfaces = null;
/* 378 */     if (classNode.interfaces != null) {
/* 379 */       interfaces = new ArrayList<>();
/* 380 */       for (String iface : classNode.interfaces) {
/* 381 */         interfaces.add(Type.getObjectType(iface));
/*     */       }
/*     */     } 
/*     */     
/* 385 */     Type objectType = null;
/* 386 */     if (classNode.superName != null) {
/* 387 */       objectType = Type.getObjectType(classNode.superName);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 392 */     Analyzer<BasicValue> analyzer = new Analyzer((Interpreter)new MixinVerifier(ASM.API_VERSION, Type.getObjectType(classNode.name), objectType, interfaces, false));
/*     */     try {
/* 394 */       analyzer.analyze(classNode.name, method);
/* 395 */     } catch (AnalyzerException ex) {
/* 396 */       ex.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 400 */     Frame[] arrayOfFrame = analyzer.getFrames();
/*     */ 
/*     */     
/* 403 */     int methodSize = method.instructions.size();
/*     */ 
/*     */     
/* 406 */     List<LocalVariableNode> localVariables = new ArrayList<>();
/*     */     
/* 408 */     LocalVariableNode[] localNodes = new LocalVariableNode[method.maxLocals];
/* 409 */     BasicValue[] locals = new BasicValue[method.maxLocals];
/* 410 */     LabelNode[] labels = new LabelNode[methodSize];
/* 411 */     String[] lastKnownType = new String[method.maxLocals];
/*     */ 
/*     */     
/* 414 */     for (int i = 0; i < methodSize; i++) {
/* 415 */       Frame<BasicValue> f = arrayOfFrame[i];
/* 416 */       if (f != null) {
/*     */ 
/*     */         
/* 419 */         LabelNode labelNode = null;
/*     */         
/* 421 */         for (int j = 0; j < f.getLocals(); j++) {
/* 422 */           BasicValue local = (BasicValue)f.getLocal(j);
/* 423 */           if (local != null || locals[j] != null)
/*     */           {
/*     */             
/* 426 */             if (local == null || !local.equals(locals[j])) {
/*     */ 
/*     */ 
/*     */               
/* 430 */               if (labelNode == null) {
/* 431 */                 AbstractInsnNode existingLabel = method.instructions.get(i);
/* 432 */                 if (existingLabel instanceof LabelNode) {
/* 433 */                   labelNode = (LabelNode)existingLabel;
/*     */                 } else {
/* 435 */                   labels[i] = labelNode = new LabelNode();
/*     */                 } 
/*     */               } 
/*     */               
/* 439 */               if (local == null && locals[j] != null) {
/* 440 */                 localVariables.add(localNodes[j]);
/* 441 */                 (localNodes[j]).end = labelNode;
/* 442 */                 localNodes[j] = null;
/* 443 */               } else if (local != null) {
/* 444 */                 if (locals[j] != null) {
/* 445 */                   localVariables.add(localNodes[j]);
/* 446 */                   (localNodes[j]).end = labelNode;
/* 447 */                   localNodes[j] = null;
/*     */                 } 
/*     */                 
/* 450 */                 String desc = (local.getType() != null) ? local.getType().getDescriptor() : lastKnownType[j];
/* 451 */                 localNodes[j] = new LocalVariableNode("var" + j, desc, null, labelNode, null, j);
/* 452 */                 if (desc != null) {
/* 453 */                   lastKnownType[j] = desc;
/*     */                 }
/*     */               } 
/*     */               
/* 457 */               locals[j] = local;
/*     */             }  } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 462 */     LabelNode label = null;
/* 463 */     for (int k = 0; k < localNodes.length; k++) {
/* 464 */       if (localNodes[k] != null) {
/* 465 */         if (label == null) {
/* 466 */           label = new LabelNode();
/* 467 */           method.instructions.add((AbstractInsnNode)label);
/*     */         } 
/*     */         
/* 470 */         (localNodes[k]).end = label;
/* 471 */         localVariables.add(localNodes[k]);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 476 */     for (int n = methodSize - 1; n >= 0; n--) {
/* 477 */       if (labels[n] != null) {
/* 478 */         method.instructions.insert(method.instructions.get(n), (AbstractInsnNode)labels[n]);
/*     */       }
/*     */     } 
/*     */     
/* 482 */     return localVariables;
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
/*     */   private static AbstractInsnNode nextNode(InsnList insns, AbstractInsnNode insn) {
/* 494 */     int index = insns.indexOf(insn) + 1;
/* 495 */     if (index > 0 && index < insns.size()) {
/* 496 */       return insns.get(index);
/*     */     }
/* 498 */     return insn;
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
/*     */   private static int getAdjustedFrameSize(int currentSize, FrameNode frameNode) {
/* 511 */     return getAdjustedFrameSize(currentSize, frameNode.type, computeFrameSize(frameNode));
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
/*     */   private static int getAdjustedFrameSize(int currentSize, ClassInfo.FrameData frameData) {
/* 524 */     return getAdjustedFrameSize(currentSize, frameData.type, frameData.size);
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
/*     */   private static int getAdjustedFrameSize(int currentSize, int type, int size) {
/* 538 */     switch (type) {
/*     */       case -1:
/*     */       case 0:
/* 541 */         return size;
/*     */       case 1:
/* 543 */         return currentSize + size;
/*     */       case 2:
/* 545 */         return currentSize - size;
/*     */       case 3:
/*     */       case 4:
/* 548 */         return currentSize;
/*     */     } 
/* 550 */     return currentSize;
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
/*     */   public static int computeFrameSize(FrameNode frameNode) {
/* 562 */     if (frameNode.local == null) {
/* 563 */       return 0;
/*     */     }
/* 565 */     int size = 0;
/* 566 */     for (Object local : frameNode.local) {
/* 567 */       if (local instanceof Integer) {
/* 568 */         size += (local == Opcodes.DOUBLE || local == Opcodes.LONG) ? 2 : 1; continue;
/*     */       } 
/* 570 */       size++;
/*     */     } 
/*     */     
/* 573 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFrameTypeName(Object frameEntry) {
/* 583 */     if (frameEntry == null) {
/* 584 */       return "NULL";
/*     */     }
/*     */     
/* 587 */     if (frameEntry instanceof String) {
/* 588 */       return Bytecode.getSimpleName(frameEntry.toString());
/*     */     }
/*     */     
/* 591 */     if (frameEntry instanceof Integer) {
/* 592 */       int type = ((Integer)frameEntry).intValue();
/*     */       
/* 594 */       if (type >= FRAME_TYPES.length) {
/* 595 */         return "INVALID";
/*     */       }
/*     */       
/* 598 */       return FRAME_TYPES[type];
/*     */     } 
/*     */     
/* 601 */     return "?";
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\Locals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */