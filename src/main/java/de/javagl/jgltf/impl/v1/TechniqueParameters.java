/*     */ package de.javagl.jgltf.impl.v1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TechniqueParameters
/*     */   extends GlTFProperty
/*     */ {
/*     */   private Integer count;
/*     */   private String node;
/*     */   private Integer type;
/*     */   private String semantic;
/*     */   private Object value;
/*     */   
/*     */   public void setCount(Integer count) {
/*  69 */     if (count == null) {
/*  70 */       this.count = count;
/*     */       return;
/*     */     } 
/*  73 */     if (count.intValue() < 1) {
/*  74 */       throw new IllegalArgumentException("count < 1");
/*     */     }
/*  76 */     this.count = count;
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
/*     */   public Integer getCount() {
/*  89 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNode(String node) {
/* 100 */     if (node == null) {
/* 101 */       this.node = node;
/*     */       return;
/*     */     } 
/* 104 */     this.node = node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNode() {
/* 115 */     return this.node;
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
/*     */   public void setType(Integer type) {
/* 131 */     if (type == null) {
/* 132 */       throw new NullPointerException("Invalid value for type: " + type + ", may not be null");
/*     */     }
/* 134 */     if (type.intValue() != 5120 && type.intValue() != 5121 && type.intValue() != 5122 && type.intValue() != 5123 && type.intValue() != 5124 && type.intValue() != 5125 && type.intValue() != 5126 && type.intValue() != 35664 && type.intValue() != 35665 && type.intValue() != 35666 && type.intValue() != 35667 && type.intValue() != 35668 && type.intValue() != 35669 && type.intValue() != 35670 && type.intValue() != 35671 && type.intValue() != 35672 && type.intValue() != 35673 && type.intValue() != 35674 && type.intValue() != 35675 && type.intValue() != 35676 && type.intValue() != 35678) {
/* 135 */       throw new IllegalArgumentException("Invalid value for type: " + type + ", valid: [5120, 5121, 5122, 5123, 5124, 5125, 5126, 35664, 35665, 35666, 35667, 35668, 35669, 35670, 35671, 35672, 35673, 35674, 35675, 35676, 35678]");
/*     */     }
/* 137 */     this.type = type;
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
/*     */   public Integer getType() {
/* 150 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSemantic(String semantic) {
/* 160 */     if (semantic == null) {
/* 161 */       this.semantic = semantic;
/*     */       return;
/*     */     } 
/* 164 */     this.semantic = semantic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSemantic() {
/* 174 */     return this.semantic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object value) {
/* 184 */     if (value == null) {
/* 185 */       this.value = value;
/*     */       return;
/*     */     } 
/* 188 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 198 */     return this.value;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\TechniqueParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */