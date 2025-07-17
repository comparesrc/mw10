/*     */ package org.spongepowered.asm.util;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.LocalVariableNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignaturePrinter
/*     */ {
/*     */   private final String name;
/*     */   private final Type returnType;
/*     */   private final Type[] argTypes;
/*     */   private final String[] argNames;
/*  64 */   private String modifiers = "private void";
/*     */ 
/*     */   
/*     */   private boolean fullyQualified;
/*     */ 
/*     */ 
/*     */   
/*     */   public SignaturePrinter(MethodNode method) {
/*  72 */     this(method.name, Type.VOID_TYPE, Type.getArgumentTypes(method.desc));
/*  73 */     setModifiers(method);
/*     */   }
/*     */   
/*     */   public SignaturePrinter(MethodNode method, String[] argNames) {
/*  77 */     this(method.name, Type.VOID_TYPE, Type.getArgumentTypes(method.desc), argNames);
/*  78 */     setModifiers(method);
/*     */   }
/*     */   
/*     */   public SignaturePrinter(ITargetSelectorByName member) {
/*  82 */     this(member.getName(), member.getDesc());
/*     */   }
/*     */   
/*     */   public SignaturePrinter(String name, String desc) {
/*  86 */     this(name, Type.getReturnType(desc), Type.getArgumentTypes(desc));
/*     */   }
/*     */   
/*     */   public SignaturePrinter(Type[] args) {
/*  90 */     this((String)null, (Type)null, args);
/*     */   }
/*     */   
/*     */   public SignaturePrinter(Type returnType, Type[] args) {
/*  94 */     this((String)null, returnType, args);
/*     */   }
/*     */   
/*     */   public SignaturePrinter(String name, Type returnType, Type[] args) {
/*  98 */     this.name = name;
/*  99 */     this.returnType = returnType;
/* 100 */     this.argTypes = new Type[args.length];
/* 101 */     this.argNames = new String[args.length];
/* 102 */     for (int l = 0, v = 0; l < args.length; l++) {
/* 103 */       if (args[l] != null) {
/* 104 */         this.argTypes[l] = args[l];
/* 105 */         this.argNames[l] = "var" + v++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public SignaturePrinter(String name, Type returnType, LocalVariableNode[] args) {
/* 111 */     this.name = name;
/* 112 */     this.returnType = returnType;
/* 113 */     this.argTypes = new Type[args.length];
/* 114 */     this.argNames = new String[args.length];
/* 115 */     for (int l = 0; l < args.length; l++) {
/* 116 */       if (args[l] != null) {
/* 117 */         this.argTypes[l] = Type.getType((args[l]).desc);
/* 118 */         this.argNames[l] = (args[l]).name;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public SignaturePrinter(String name, Type returnType, Type[] argTypes, String[] argNames) {
/* 124 */     this.name = name;
/* 125 */     this.returnType = returnType;
/* 126 */     this.argTypes = argTypes;
/* 127 */     this.argNames = argNames;
/* 128 */     if (this.argTypes.length > this.argNames.length) {
/* 129 */       throw new IllegalArgumentException(String.format("Types array length must not exceed names array length! (names=%d, types=%d)", new Object[] {
/* 130 */               Integer.valueOf(this.argNames.length), Integer.valueOf(this.argTypes.length)
/*     */             }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattedArgs() {
/* 138 */     return appendArgs(new StringBuilder(), true, true).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReturnType() {
/* 145 */     return getTypeName(this.returnType, false, this.fullyQualified);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModifiers(MethodNode method) {
/* 154 */     String returnType = getTypeName(Type.getReturnType(method.desc), false, this.fullyQualified);
/* 155 */     if ((method.access & 0x1) != 0) {
/* 156 */       setModifiers("public " + returnType);
/* 157 */     } else if ((method.access & 0x4) != 0) {
/* 158 */       setModifiers("protected " + returnType);
/* 159 */     } else if ((method.access & 0x2) != 0) {
/* 160 */       setModifiers("private " + returnType);
/*     */     } else {
/* 162 */       setModifiers(returnType);
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
/*     */   public SignaturePrinter setModifiers(String modifiers) {
/* 175 */     this.modifiers = modifiers.replace("${returnType}", getReturnType());
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignaturePrinter setFullyQualified(boolean fullyQualified) {
/* 187 */     this.fullyQualified = fullyQualified;
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullyQualified() {
/* 196 */     return this.fullyQualified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 204 */     String name = (this.name != null) ? this.name : "method";
/* 205 */     return appendArgs((new StringBuilder()).append(this.modifiers).append(" ").append(name), false, true).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toDescriptor() {
/* 212 */     StringBuilder args = appendArgs(new StringBuilder(), true, false);
/* 213 */     return args.append(getTypeName(this.returnType, false, this.fullyQualified)).toString();
/*     */   }
/*     */   
/*     */   private StringBuilder appendArgs(StringBuilder sb, boolean typesOnly, boolean pretty) {
/* 217 */     sb.append('(');
/* 218 */     for (int var = 0; var < this.argTypes.length; var++) {
/* 219 */       if (this.argTypes[var] != null) {
/*     */         
/* 221 */         if (var > 0) {
/* 222 */           sb.append(',');
/* 223 */           if (pretty) {
/* 224 */             sb.append(' ');
/*     */           }
/*     */         } 
/*     */         try {
/* 228 */           String name = typesOnly ? null : (Strings.isNullOrEmpty(this.argNames[var]) ? ("unnamed" + var) : this.argNames[var]);
/* 229 */           appendType(sb, this.argTypes[var], name);
/* 230 */         } catch (Exception ex) {
/*     */           
/* 232 */           throw new RuntimeException(ex);
/*     */         } 
/*     */       } 
/* 235 */     }  return sb.append(")");
/*     */   }
/*     */   
/*     */   private StringBuilder appendType(StringBuilder sb, Type type, String name) {
/* 239 */     switch (type.getSort()) {
/*     */       case 9:
/* 241 */         return appendArraySuffix(appendType(sb, type.getElementType(), name), type);
/*     */       case 10:
/* 243 */         return appendType(sb, type.getClassName(), name);
/*     */     } 
/* 245 */     sb.append(getTypeName(type, false, this.fullyQualified));
/* 246 */     if (name != null) {
/* 247 */       sb.append(' ').append(name);
/*     */     }
/* 249 */     return sb;
/*     */   }
/*     */ 
/*     */   
/*     */   private StringBuilder appendType(StringBuilder sb, String typeName, String name) {
/* 254 */     if (!this.fullyQualified) {
/* 255 */       typeName = typeName.substring(typeName.lastIndexOf('.') + 1);
/*     */     }
/* 257 */     sb.append(typeName);
/* 258 */     if (typeName.endsWith("CallbackInfoReturnable")) {
/* 259 */       sb.append('<').append(getTypeName(this.returnType, true, this.fullyQualified)).append('>');
/*     */     }
/* 261 */     if (name != null) {
/* 262 */       sb.append(' ').append(name);
/*     */     }
/* 264 */     return sb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTypeName(Type type) {
/* 275 */     return getTypeName(type, false, true);
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
/*     */   public static String getTypeName(Type type, boolean box) {
/* 287 */     return getTypeName(type, box, false);
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
/*     */   public static String getTypeName(Type type, boolean box, boolean fullyQualified) {
/*     */     String typeName;
/* 300 */     if (type == null) {
/* 301 */       return "{null?}";
/*     */     }
/* 303 */     switch (type.getSort()) { case 0:
/* 304 */         return box ? "Void" : "void";
/* 305 */       case 1: return box ? "Boolean" : "boolean";
/* 306 */       case 2: return box ? "Character" : "char";
/* 307 */       case 3: return box ? "Byte" : "byte";
/* 308 */       case 4: return box ? "Short" : "short";
/* 309 */       case 5: return box ? "Integer" : "int";
/* 310 */       case 6: return box ? "Float" : "float";
/* 311 */       case 7: return box ? "Long" : "long";
/* 312 */       case 8: return box ? "Double" : "double";
/* 313 */       case 9: return getTypeName(type.getElementType(), box, fullyQualified) + arraySuffix(type);
/*     */       case 10:
/* 315 */         typeName = type.getClassName();
/* 316 */         if (!fullyQualified) {
/* 317 */           typeName = typeName.substring(typeName.lastIndexOf('.') + 1);
/*     */         }
/* 319 */         return typeName; }
/*     */     
/* 321 */     return "Object";
/*     */   }
/*     */ 
/*     */   
/*     */   private static String arraySuffix(Type type) {
/* 326 */     return Strings.repeat("[]", type.getDimensions());
/*     */   }
/*     */ 
/*     */   
/*     */   private static StringBuilder appendArraySuffix(StringBuilder sb, Type type) {
/* 331 */     for (int i = 0; i < type.getDimensions(); i++) {
/* 332 */       sb.append("[]");
/*     */     }
/* 334 */     return sb;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\SignaturePrinter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */