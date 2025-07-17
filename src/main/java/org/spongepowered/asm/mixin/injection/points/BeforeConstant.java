/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import com.google.common.primitives.Doubles;
/*     */ import com.google.common.primitives.Floats;
/*     */ import com.google.common.primitives.Ints;
/*     */ import com.google.common.primitives.Longs;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.AnnotationNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.spongepowered.asm.mixin.injection.Constant;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @AtCode("CONSTANT")
/*     */ public class BeforeConstant
/*     */   extends InjectionPoint
/*     */ {
/* 127 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */   
/*     */   private final int ordinal;
/*     */   
/*     */   private final boolean nullValue;
/*     */   
/*     */   private final Integer intValue;
/*     */   
/*     */   private final Float floatValue;
/*     */   
/*     */   private final Long longValue;
/*     */   
/*     */   private final Double doubleValue;
/*     */   
/*     */   private final String stringValue;
/*     */   
/*     */   private final Type typeValue;
/*     */   private final int[] expandOpcodes;
/*     */   private final boolean expand;
/*     */   private final String matchByType;
/*     */   private final boolean log;
/*     */   
/*     */   public BeforeConstant(IMixinContext context, AnnotationNode node, String returnType) {
/* 150 */     super((String)Annotations.getValue(node, "slice", ""), InjectionPoint.Selector.DEFAULT, null);
/*     */     
/* 152 */     Boolean empty = (Boolean)Annotations.getValue(node, "nullValue", null);
/* 153 */     this.ordinal = ((Integer)Annotations.getValue(node, "ordinal", Integer.valueOf(-1))).intValue();
/* 154 */     this.nullValue = (empty != null && empty.booleanValue());
/* 155 */     this.intValue = (Integer)Annotations.getValue(node, "intValue", null);
/* 156 */     this.floatValue = (Float)Annotations.getValue(node, "floatValue", null);
/* 157 */     this.longValue = (Long)Annotations.getValue(node, "longValue", null);
/* 158 */     this.doubleValue = (Double)Annotations.getValue(node, "doubleValue", null);
/* 159 */     this.stringValue = (String)Annotations.getValue(node, "stringValue", null);
/* 160 */     this.typeValue = (Type)Annotations.getValue(node, "classValue", null);
/*     */     
/* 162 */     this.matchByType = validateDiscriminator(context, returnType, empty, "on @Constant annotation");
/* 163 */     this.expandOpcodes = parseExpandOpcodes(Annotations.getValue(node, "expandZeroConditions", true, Constant.Condition.class));
/* 164 */     this.expand = (this.expandOpcodes.length > 0);
/*     */     
/* 166 */     this.log = ((Boolean)Annotations.getValue(node, "log", Boolean.FALSE)).booleanValue();
/*     */   }
/*     */   
/*     */   public BeforeConstant(InjectionPointData data) {
/* 170 */     super(data);
/*     */     
/* 172 */     String strNullValue = data.get("nullValue", null);
/* 173 */     Boolean empty = (strNullValue != null) ? Boolean.valueOf(Boolean.parseBoolean(strNullValue)) : null;
/*     */     
/* 175 */     this.ordinal = data.getOrdinal();
/* 176 */     this.nullValue = (empty != null && empty.booleanValue());
/* 177 */     this.intValue = Ints.tryParse(data.get("intValue", ""));
/* 178 */     this.floatValue = Floats.tryParse(data.get("floatValue", ""));
/* 179 */     this.longValue = Longs.tryParse(data.get("longValue", ""));
/* 180 */     this.doubleValue = Doubles.tryParse(data.get("doubleValue", ""));
/* 181 */     this.stringValue = data.get("stringValue", null);
/* 182 */     String strClassValue = data.get("classValue", null);
/* 183 */     this.typeValue = (strClassValue != null) ? Type.getObjectType(strClassValue.replace('.', '/')) : null;
/*     */     
/* 185 */     this.matchByType = validateDiscriminator(data.getContext(), "V", empty, "in @At(\"CONSTANT\") args");
/* 186 */     if ("V".equals(this.matchByType)) {
/* 187 */       throw new InvalidInjectionException(data.getContext(), "No constant discriminator could be parsed in @At(\"CONSTANT\") args");
/*     */     }
/*     */     
/* 190 */     List<Constant.Condition> conditions = new ArrayList<>();
/* 191 */     String strConditions = data.get("expandZeroConditions", "").toLowerCase(Locale.ROOT);
/* 192 */     for (Constant.Condition condition : Constant.Condition.values()) {
/* 193 */       if (strConditions.contains(condition.name().toLowerCase(Locale.ROOT))) {
/* 194 */         conditions.add(condition);
/*     */       }
/*     */     } 
/*     */     
/* 198 */     this.expandOpcodes = parseExpandOpcodes(conditions);
/* 199 */     this.expand = (this.expandOpcodes.length > 0);
/*     */     
/* 201 */     this.log = data.get("log", false);
/*     */   }
/*     */   
/*     */   private String validateDiscriminator(IMixinContext context, String returnType, Boolean empty, String type) {
/* 205 */     int c = count(new Object[] { empty, this.intValue, this.floatValue, this.longValue, this.doubleValue, this.stringValue, this.typeValue });
/* 206 */     if (c == 1) {
/* 207 */       returnType = null;
/* 208 */     } else if (c > 1) {
/* 209 */       throw new InvalidInjectionException(context, "Conflicting constant discriminators specified " + type + " for " + context);
/*     */     } 
/* 211 */     return returnType;
/*     */   }
/*     */   
/*     */   private int[] parseExpandOpcodes(List<Constant.Condition> conditions) {
/* 215 */     Set<Integer> opcodes = new HashSet<>();
/* 216 */     for (Constant.Condition condition : conditions) {
/* 217 */       Constant.Condition actual = condition.getEquivalentCondition();
/* 218 */       for (int opcode : actual.getOpcodes()) {
/* 219 */         opcodes.add(Integer.valueOf(opcode));
/*     */       }
/*     */     } 
/* 222 */     return Ints.toArray(opcodes);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 227 */     boolean found = false;
/*     */     
/* 229 */     log("BeforeConstant is searching for constants in method with descriptor {}", new Object[] { desc });
/*     */     
/* 231 */     ListIterator<AbstractInsnNode> iter = insns.iterator(); int last;
/* 232 */     for (int ordinal = 0; iter.hasNext(); ) {
/* 233 */       AbstractInsnNode insn = iter.next();
/*     */       
/* 235 */       boolean matchesInsn = this.expand ? matchesConditionalInsn(last, insn) : matchesConstantInsn(insn);
/* 236 */       if (matchesInsn) {
/* 237 */         log("    BeforeConstant found a matching constant{} at ordinal {}", new Object[] { (this.matchByType != null) ? " TYPE" : " value", Integer.valueOf(ordinal) });
/* 238 */         if (this.ordinal == -1 || this.ordinal == ordinal) {
/* 239 */           log("      BeforeConstant found {}", new Object[] { Bytecode.describeNode(insn).trim() });
/* 240 */           nodes.add(insn);
/* 241 */           found = true;
/*     */         } 
/* 243 */         ordinal++;
/*     */       } 
/*     */       
/* 246 */       if (!(insn instanceof org.objectweb.asm.tree.LabelNode) && !(insn instanceof org.objectweb.asm.tree.FrameNode)) {
/* 247 */         last = insn.getOpcode();
/*     */       }
/*     */     } 
/*     */     
/* 251 */     return found;
/*     */   }
/*     */   
/*     */   private boolean matchesConditionalInsn(int last, AbstractInsnNode insn) {
/* 255 */     for (int conditionalOpcode : this.expandOpcodes) {
/* 256 */       int opcode = insn.getOpcode();
/* 257 */       if (opcode == conditionalOpcode) {
/* 258 */         if (last == 148 || last == 149 || last == 150 || last == 151 || last == 152) {
/* 259 */           log("  BeforeConstant is ignoring {} following {}", new Object[] { Bytecode.getOpcodeName(opcode), Bytecode.getOpcodeName(last) });
/* 260 */           return false;
/*     */         } 
/*     */         
/* 263 */         log("  BeforeConstant found {} instruction", new Object[] { Bytecode.getOpcodeName(opcode) });
/* 264 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     if (this.intValue != null && this.intValue.intValue() == 0 && Bytecode.isConstant(insn)) {
/* 269 */       Object value = Bytecode.getConstant(insn);
/* 270 */       log("  BeforeConstant found INTEGER constant: value = {}", new Object[] { value });
/* 271 */       return (value instanceof Integer && ((Integer)value).intValue() == 0);
/*     */     } 
/*     */     
/* 274 */     return false;
/*     */   }
/*     */   
/*     */   private boolean matchesConstantInsn(AbstractInsnNode insn) {
/* 278 */     if (!Bytecode.isConstant(insn)) {
/* 279 */       return false;
/*     */     }
/*     */     
/* 282 */     Object value = Bytecode.getConstant(insn);
/* 283 */     if (value == null) {
/* 284 */       log("  BeforeConstant found NULL constant: nullValue = {}", new Object[] { Boolean.valueOf(this.nullValue) });
/* 285 */       return (this.nullValue || "Ljava/lang/Object;".equals(this.matchByType));
/* 286 */     }  if (value instanceof Integer) {
/* 287 */       log("  BeforeConstant found INTEGER constant: value = {}, intValue = {}", new Object[] { value, this.intValue });
/* 288 */       return (value.equals(this.intValue) || "I".equals(this.matchByType));
/* 289 */     }  if (value instanceof Float) {
/* 290 */       log("  BeforeConstant found FLOAT constant: value = {}, floatValue = {}", new Object[] { value, this.floatValue });
/* 291 */       return (value.equals(this.floatValue) || "F".equals(this.matchByType));
/* 292 */     }  if (value instanceof Long) {
/* 293 */       log("  BeforeConstant found LONG constant: value = {}, longValue = {}", new Object[] { value, this.longValue });
/* 294 */       return (value.equals(this.longValue) || "J".equals(this.matchByType));
/* 295 */     }  if (value instanceof Double) {
/* 296 */       log("  BeforeConstant found DOUBLE constant: value = {}, doubleValue = {}", new Object[] { value, this.doubleValue });
/* 297 */       return (value.equals(this.doubleValue) || "D".equals(this.matchByType));
/* 298 */     }  if (value instanceof String) {
/* 299 */       log("  BeforeConstant found STRING constant: value = {}, stringValue = {}", new Object[] { value, this.stringValue });
/* 300 */       return (value.equals(this.stringValue) || "Ljava/lang/String;".equals(this.matchByType));
/* 301 */     }  if (value instanceof Type) {
/* 302 */       log("  BeforeConstant found CLASS constant: value = {}, typeValue = {}", new Object[] { value, this.typeValue });
/* 303 */       return (value.equals(this.typeValue) || "Ljava/lang/Class;".equals(this.matchByType));
/*     */     } 
/*     */     
/* 306 */     return false;
/*     */   }
/*     */   
/*     */   protected void log(String message, Object... params) {
/* 310 */     if (this.log) {
/* 311 */       logger.info(message, params);
/*     */     }
/*     */   }
/*     */   
/*     */   private static int count(Object... values) {
/* 316 */     int counter = 0;
/* 317 */     for (Object value : values) {
/* 318 */       if (value != null) {
/* 319 */         counter++;
/*     */       }
/*     */     } 
/* 322 */     return counter;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\points\BeforeConstant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */