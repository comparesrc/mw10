/*     */ package org.spongepowered.asm.mixin.injection.struct;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Strings;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.FieldInsnNode;
/*     */ import org.objectweb.asm.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorConstructor;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.InvalidSelectorException;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.MatchResult;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MemberInfo
/*     */   implements ITargetSelectorRemappable, ITargetSelectorConstructor
/*     */ {
/*     */   private final String owner;
/*     */   private final String name;
/*     */   private final String desc;
/*     */   private final boolean matchAll;
/*     */   private final boolean forceField;
/*     */   private final String unparsed;
/*     */   
/*     */   public MemberInfo(String name, boolean matchAll) {
/* 132 */     this(name, null, null, matchAll);
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
/*     */   public MemberInfo(String name, String owner, boolean matchAll) {
/* 145 */     this(name, owner, null, matchAll);
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
/*     */   public MemberInfo(String name, String owner, String desc) {
/* 157 */     this(name, owner, desc, false);
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
/*     */   public MemberInfo(String name, String owner, String desc, boolean matchAll) {
/* 170 */     this(name, owner, desc, matchAll, null);
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
/*     */   public MemberInfo(String name, String owner, String desc, boolean matchAll, String unparsed) {
/* 183 */     if (owner != null && owner.contains(".")) {
/* 184 */       throw new IllegalArgumentException("Attempt to instance a MemberInfo with an invalid owner format");
/*     */     }
/*     */     
/* 187 */     this.owner = owner;
/* 188 */     this.name = name;
/* 189 */     this.desc = desc;
/* 190 */     this.matchAll = matchAll;
/* 191 */     this.forceField = false;
/* 192 */     this.unparsed = unparsed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemberInfo(AbstractInsnNode insn) {
/* 202 */     this.matchAll = false;
/* 203 */     this.forceField = false;
/* 204 */     this.unparsed = null;
/*     */     
/* 206 */     if (insn instanceof MethodInsnNode) {
/* 207 */       MethodInsnNode methodNode = (MethodInsnNode)insn;
/* 208 */       this.owner = methodNode.owner;
/* 209 */       this.name = methodNode.name;
/* 210 */       this.desc = methodNode.desc;
/* 211 */     } else if (insn instanceof FieldInsnNode) {
/* 212 */       FieldInsnNode fieldNode = (FieldInsnNode)insn;
/* 213 */       this.owner = fieldNode.owner;
/* 214 */       this.name = fieldNode.name;
/* 215 */       this.desc = fieldNode.desc;
/*     */     } else {
/* 217 */       throw new IllegalArgumentException("insn must be an instance of MethodInsnNode or FieldInsnNode");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemberInfo(IMapping<?> mapping) {
/* 227 */     this.owner = mapping.getOwner();
/* 228 */     this.name = mapping.getSimpleName();
/* 229 */     this.desc = mapping.getDesc();
/* 230 */     this.matchAll = false;
/* 231 */     this.forceField = (mapping.getType() == IMapping.Type.FIELD);
/* 232 */     this.unparsed = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MemberInfo(MemberInfo remapped, MappingMethod method, boolean setOwner) {
/* 241 */     this.owner = setOwner ? method.getOwner() : remapped.owner;
/* 242 */     this.name = method.getSimpleName();
/* 243 */     this.desc = method.getDesc();
/* 244 */     this.matchAll = remapped.matchAll;
/* 245 */     this.forceField = false;
/* 246 */     this.unparsed = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MemberInfo(MemberInfo original, String owner) {
/* 256 */     this.owner = owner;
/* 257 */     this.name = original.name;
/* 258 */     this.desc = original.desc;
/* 259 */     this.matchAll = original.matchAll;
/* 260 */     this.forceField = original.forceField;
/* 261 */     this.unparsed = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITargetSelector next() {
/* 266 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOwner() {
/* 271 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 276 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDesc() {
/* 281 */     return this.desc;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMatchCount() {
/* 286 */     return this.matchAll ? Integer.MAX_VALUE : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 294 */     String owner = (this.owner != null) ? ("L" + this.owner + ";") : "";
/* 295 */     String name = (this.name != null) ? this.name : "";
/* 296 */     String qualifier = this.matchAll ? "*" : "";
/* 297 */     String desc = (this.desc != null) ? this.desc : "";
/* 298 */     String separator = desc.startsWith("(") ? "" : ((this.desc != null) ? ":" : "");
/* 299 */     return owner + name + qualifier + separator + desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String toSrg() {
/* 310 */     if (!isFullyQualified()) {
/* 311 */       throw new MixinException("Cannot convert unqualified reference to SRG mapping");
/*     */     }
/*     */     
/* 314 */     if (this.desc.startsWith("(")) {
/* 315 */       return this.owner + "/" + this.name + " " + this.desc;
/*     */     }
/*     */     
/* 318 */     return this.owner + "/" + this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toDescriptor() {
/* 326 */     if (this.desc == null) {
/* 327 */       return "";
/*     */     }
/*     */     
/* 330 */     return (new SignaturePrinter((ITargetSelectorByName)this)).setFullyQualified(true).toDescriptor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toCtorType() {
/* 338 */     if (this.unparsed == null) {
/* 339 */       return null;
/*     */     }
/*     */     
/* 342 */     String returnType = getReturnType();
/* 343 */     if (returnType != null) {
/* 344 */       return returnType;
/*     */     }
/*     */     
/* 347 */     if (this.owner != null) {
/* 348 */       return this.owner;
/*     */     }
/*     */     
/* 351 */     if (this.name != null && this.desc == null) {
/* 352 */       return this.name;
/*     */     }
/*     */     
/* 355 */     return (this.desc != null) ? this.desc : this.unparsed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toCtorDesc() {
/* 364 */     return Bytecode.changeDescriptorReturnType(this.desc, "V");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getReturnType() {
/* 373 */     if (this.desc == null || this.desc.indexOf(')') == -1 || this.desc.indexOf('(') != 0) {
/* 374 */       return null;
/*     */     }
/*     */     
/* 377 */     String returnType = this.desc.substring(this.desc.indexOf(')') + 1);
/* 378 */     if (returnType.startsWith("L") && returnType.endsWith(";")) {
/* 379 */       return returnType.substring(1, returnType.length() - 1);
/*     */     }
/* 381 */     return returnType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMapping<?> asMapping() {
/* 390 */     return isField() ? (IMapping<?>)asFieldMapping() : (IMapping<?>)asMethodMapping();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod asMethodMapping() {
/* 398 */     if (!isFullyQualified()) {
/* 399 */       throw new MixinException("Cannot convert unqualified reference " + this + " to MethodMapping");
/*     */     }
/*     */     
/* 402 */     if (isField()) {
/* 403 */       throw new MixinException("Cannot convert a non-method reference " + this + " to MethodMapping");
/*     */     }
/*     */     
/* 406 */     return new MappingMethod(this.owner, this.name, this.desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingField asFieldMapping() {
/* 414 */     if (!isField()) {
/* 415 */       throw new MixinException("Cannot convert non-field reference " + this + " to FieldMapping");
/*     */     }
/*     */     
/* 418 */     return new MappingField(this.owner, this.name, this.desc);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullyQualified() {
/* 423 */     return (this.owner != null && this.name != null && this.desc != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isField() {
/* 434 */     return (this.forceField || (this.desc != null && !this.desc.startsWith("(")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstructor() {
/* 444 */     return "<init>".equals(this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClassInitialiser() {
/* 454 */     return "<clinit>".equals(this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitialiser() {
/* 465 */     return (isConstructor() || isClassInitialiser());
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
/*     */   public MemberInfo validate() throws InvalidMemberDescriptorException {
/* 479 */     if (this.owner != null) {
/* 480 */       if (!this.owner.matches("(?i)^[\\w\\p{Sc}/]+$")) {
/* 481 */         throw new InvalidMemberDescriptorException("Invalid owner: " + this.owner);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 486 */       if (this.unparsed != null && this.unparsed.lastIndexOf('.') > 0 && this.owner.startsWith("L")) {
/* 487 */         throw new InvalidMemberDescriptorException("Malformed owner: " + this.owner + " If you are seeing this message unexpectedly and the owner appears to be correct, replace the owner descriptor with formal type L" + this.owner + "; to suppress this error");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 493 */     if (this.name != null && !this.name.matches("(?i)^<?[\\w\\p{Sc}]+>?$")) {
/* 494 */       throw new InvalidMemberDescriptorException("Invalid name: " + this.name);
/*     */     }
/*     */     
/* 497 */     if (this.desc != null) {
/* 498 */       if (!this.desc.matches("^(\\([\\w\\p{Sc}\\[/;]*\\))?\\[*[\\w\\p{Sc}/;]+$")) {
/* 499 */         throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
/*     */       }
/* 501 */       if (isField()) {
/* 502 */         if (!this.desc.equals(Type.getType(this.desc).getDescriptor())) {
/* 503 */           throw new InvalidMemberDescriptorException("Invalid field type in descriptor: " + this.desc);
/*     */         }
/*     */       } else {
/*     */         try {
/* 507 */           Type.getArgumentTypes(this.desc);
/* 508 */         } catch (Exception ex) {
/* 509 */           throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
/*     */         } 
/*     */         
/* 512 */         String retString = this.desc.substring(this.desc.indexOf(')') + 1);
/*     */         try {
/* 514 */           Type retType = Type.getType(retString);
/* 515 */           if (!retString.equals(retType.getDescriptor())) {
/* 516 */             throw new InvalidMemberDescriptorException("Invalid return type \"" + retString + "\" in descriptor: " + this.desc);
/*     */           }
/* 518 */         } catch (Exception ex) {
/* 519 */           throw new InvalidMemberDescriptorException("Invalid return type \"" + retString + "\" in descriptor: " + this.desc);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 524 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <TNode> MatchResult match(ElementNode<TNode> node) {
/* 533 */     return (node == null) ? MatchResult.NONE : matches(node.getOwnerName(), node.getName(), node.getDesc());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatchResult match(AbstractInsnNode insn) {
/* 542 */     if (insn instanceof MethodInsnNode) {
/* 543 */       MethodInsnNode method = (MethodInsnNode)insn;
/* 544 */       return matches(method.owner, method.name, method.desc);
/* 545 */     }  if (insn instanceof FieldInsnNode) {
/* 546 */       FieldInsnNode field = (FieldInsnNode)insn;
/* 547 */       return matches(field.owner, field.name, field.desc);
/*     */     } 
/* 549 */     return MatchResult.NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatchResult matches(String owner, String name, String desc) {
/* 558 */     if (this.desc != null && desc != null && !this.desc.equals(desc)) {
/* 559 */       return MatchResult.NONE;
/*     */     }
/* 561 */     if (this.owner != null && owner != null && !this.owner.equals(owner)) {
/* 562 */       return MatchResult.NONE;
/*     */     }
/* 564 */     if (this.name != null && name != null) {
/* 565 */       if (this.name.equals(name)) {
/* 566 */         return MatchResult.EXACT_MATCH;
/*     */       }
/* 568 */       if (this.name.equalsIgnoreCase(name)) {
/* 569 */         return MatchResult.MATCH;
/*     */       }
/* 571 */       return MatchResult.NONE;
/*     */     } 
/* 573 */     return MatchResult.EXACT_MATCH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 581 */     if (obj == null || !(obj instanceof ITargetSelectorByName)) {
/* 582 */       return false;
/*     */     }
/*     */     
/* 585 */     ITargetSelectorByName other = (ITargetSelectorByName)obj;
/* 586 */     boolean otherForceField = (other instanceof MemberInfo) ? ((MemberInfo)other).forceField : other.isField();
/* 587 */     boolean otherMatchAll = (other.getMatchCount() == Integer.MAX_VALUE);
/*     */     
/* 589 */     return (this.matchAll == otherMatchAll && this.forceField == otherForceField && 
/* 590 */       Objects.equal(this.owner, other.getOwner()) && 
/* 591 */       Objects.equal(this.name, other.getName()) && 
/* 592 */       Objects.equal(this.desc, other.getDesc()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 600 */     return Objects.hashCode(new Object[] { Boolean.valueOf(this.matchAll), this.owner, this.name, this.desc });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITargetSelector configure(String... args) {
/* 609 */     ITargetSelectorRemappable configured = this;
/* 610 */     for (String arg : args) {
/* 611 */       if (arg != null)
/*     */       {
/*     */ 
/*     */         
/* 615 */         if (arg.startsWith("move:")) {
/* 616 */           configured = configured.move(Strings.emptyToNull(arg.substring(5)));
/* 617 */         } else if (arg.startsWith("transform:")) {
/* 618 */           configured = configured.transform(Strings.emptyToNull(arg.substring(10)));
/* 619 */         } else if ("permissive".equals(arg)) {
/* 620 */           configured = configured.transform(null);
/* 621 */         } else if ("orphan".equals(arg)) {
/* 622 */           configured = configured.move(null);
/*     */         }  } 
/*     */     } 
/* 625 */     return (ITargetSelector)configured;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITargetSelector attach(IMixinContext context) throws InvalidSelectorException {
/* 634 */     if (this.owner != null && !this.owner.equals(context.getTargetClassRef())) {
/* 635 */       throw new TargetNotSupportedException(this.owner);
/*     */     }
/* 637 */     return (ITargetSelector)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITargetSelectorRemappable move(String newOwner) {
/* 647 */     if ((newOwner == null && this.owner == null) || (newOwner != null && newOwner.equals(this.owner))) {
/* 648 */       return this;
/*     */     }
/* 650 */     return new MemberInfo(this, newOwner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITargetSelectorRemappable transform(String newDesc) {
/* 660 */     if ((newDesc == null && this.desc == null) || (newDesc != null && newDesc.equals(this.desc))) {
/* 661 */       return this;
/*     */     }
/* 663 */     return new MemberInfo(this.name, this.owner, newDesc, this.matchAll);
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
/*     */   public ITargetSelectorRemappable remapUsing(MappingMethod srgMethod, boolean setOwner) {
/* 675 */     return new MemberInfo(this, srgMethod, setOwner);
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
/*     */   public static MemberInfo parse(String input, IReferenceMapper refMapper, String className) {
/* 687 */     String desc = null;
/* 688 */     String owner = null;
/* 689 */     String name = Strings.nullToEmpty(input).replaceAll("\\s", "");
/*     */     
/* 691 */     if (refMapper != null) {
/* 692 */       name = refMapper.remap(className, name);
/*     */     }
/*     */     
/* 695 */     int lastDotPos = name.lastIndexOf('.');
/* 696 */     int semiColonPos = name.indexOf(';');
/* 697 */     if (lastDotPos > -1) {
/* 698 */       owner = name.substring(0, lastDotPos).replace('.', '/');
/* 699 */       name = name.substring(lastDotPos + 1);
/* 700 */     } else if (semiColonPos > -1 && name.startsWith("L")) {
/* 701 */       owner = name.substring(1, semiColonPos).replace('.', '/');
/* 702 */       name = name.substring(semiColonPos + 1);
/*     */     } 
/*     */     
/* 705 */     int parenPos = name.indexOf('(');
/* 706 */     int colonPos = name.indexOf(':');
/* 707 */     if (parenPos > -1) {
/* 708 */       desc = name.substring(parenPos);
/* 709 */       name = name.substring(0, parenPos);
/* 710 */     } else if (colonPos > -1) {
/* 711 */       desc = name.substring(colonPos + 1);
/* 712 */       name = name.substring(0, colonPos);
/*     */     } 
/*     */     
/* 715 */     if ((name.indexOf('/') > -1 || name.indexOf('.') > -1) && owner == null) {
/* 716 */       owner = name;
/* 717 */       name = "";
/*     */     } 
/*     */     
/* 720 */     boolean matchAll = name.endsWith("*");
/* 721 */     if (matchAll) {
/* 722 */       name = name.substring(0, name.length() - 1);
/*     */     }
/*     */     
/* 725 */     if (name.isEmpty()) {
/* 726 */       name = null;
/*     */     }
/*     */     
/* 729 */     return new MemberInfo(name, owner, desc, matchAll, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MemberInfo fromMapping(IMapping<?> mapping) {
/* 739 */     return new MemberInfo(mapping);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\struct\MemberInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */