/*     */ package de.javagl.jgltf.model.gl.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.NodeModel;
/*     */ import de.javagl.jgltf.model.gl.TechniqueParametersModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultTechniqueParametersModel
/*     */   implements TechniqueParametersModel
/*     */ {
/*     */   private final int type;
/*     */   private final int count;
/*     */   private final String semantic;
/*     */   private final Object value;
/*     */   private final NodeModel nodeModel;
/*     */   
/*     */   public DefaultTechniqueParametersModel(int type, int count, String semantic, Object value, NodeModel nodeModel) {
/*  76 */     this.type = type;
/*  77 */     this.count = count;
/*  78 */     this.semantic = semantic;
/*  79 */     this.value = value;
/*  80 */     this.nodeModel = nodeModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/*  86 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/*  92 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSemantic() {
/*  98 */     return this.semantic;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 104 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeModel getNodeModel() {
/* 110 */     return this.nodeModel;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\impl\DefaultTechniqueParametersModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */