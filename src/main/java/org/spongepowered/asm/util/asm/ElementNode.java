/*     */ package org.spongepowered.asm.util.asm;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.objectweb.asm.tree.FieldNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ElementNode<TNode>
/*     */ {
/*     */   private final ClassNode owner;
/*     */   
/*     */   static class ElementNodeMethod
/*     */     extends ElementNode<MethodNode>
/*     */   {
/*     */     private MethodNode method;
/*     */     
/*     */     ElementNodeMethod(ClassNode owner, MethodNode method) {
/*  51 */       super(owner);
/*  52 */       this.method = method;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isMethod() {
/*  57 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public MethodNode getMethod() {
/*  62 */       return this.method;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/*  67 */       return this.method.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDesc() {
/*  72 */       return this.method.desc;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSignature() {
/*  77 */       return this.method.signature;
/*     */     }
/*     */ 
/*     */     
/*     */     public MethodNode get() {
/*  82 */       return this.method;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  87 */       return String.format("MethodElement[%s%s]", new Object[] { this.method.name, this.method.desc });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ElementNodeField
/*     */     extends ElementNode<FieldNode>
/*     */   {
/*     */     private FieldNode field;
/*     */ 
/*     */     
/*     */     ElementNodeField(ClassNode owner, FieldNode field) {
/* 100 */       super(owner);
/* 101 */       this.field = field;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isField() {
/* 106 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public FieldNode getField() {
/* 111 */       return this.field;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 116 */       return this.field.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDesc() {
/* 121 */       return this.field.desc;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSignature() {
/* 126 */       return this.field.signature;
/*     */     }
/*     */ 
/*     */     
/*     */     public FieldNode get() {
/* 131 */       return this.field;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 136 */       return String.format("FieldElement[%s:%s]", new Object[] { this.field.name, this.field.desc });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ElementNode(ClassNode owner) {
/* 147 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMethod() {
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isField() {
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodNode getMethod() {
/* 169 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldNode getField() {
/* 177 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassNode getOwner() {
/* 184 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOwnerName() {
/* 192 */     return (this.owner != null) ? this.owner.name : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getDesc();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getSignature();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract TNode get();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ElementNode<MethodNode> of(ClassNode owner, MethodNode method) {
/* 223 */     return new ElementNodeMethod(owner, method);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ElementNode<FieldNode> of(ClassNode owner, FieldNode field) {
/* 234 */     return new ElementNodeField(owner, field);
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
/*     */   public static <TNode> ElementNode<TNode> of(ClassNode owner, TNode node) {
/* 249 */     if (node instanceof MethodNode)
/* 250 */       return new ElementNodeMethod(owner, (MethodNode)node); 
/* 251 */     if (node instanceof FieldNode) {
/* 252 */       return new ElementNodeField(owner, (FieldNode)node);
/*     */     }
/* 254 */     throw new IllegalArgumentException("Could not create ElementNode for unknown node type: " + node.getClass().getName());
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
/*     */   public static <TNode> List<ElementNode<TNode>> listOf(ClassNode owner, List<TNode> list) {
/* 266 */     List<ElementNode<TNode>> nodes = new ArrayList<>();
/* 267 */     for (TNode node : list) {
/* 268 */       nodes.add(of(owner, node));
/*     */     }
/* 270 */     return nodes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ElementNode<FieldNode>> fieldList(ClassNode owner) {
/* 281 */     List<ElementNode<FieldNode>> fields = new ArrayList<>();
/* 282 */     for (FieldNode field : owner.fields) {
/* 283 */       fields.add(new ElementNodeField(owner, field));
/*     */     }
/* 285 */     return fields;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ElementNode<MethodNode>> methodList(ClassNode owner) {
/* 296 */     List<ElementNode<MethodNode>> methods = new ArrayList<>();
/* 297 */     for (MethodNode method : owner.methods) {
/* 298 */       methods.add(new ElementNodeMethod(owner, method));
/*     */     }
/* 300 */     return methods;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\asm\ElementNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */