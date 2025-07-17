/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.AccessorModel;
/*     */ import de.javagl.jgltf.model.MaterialModel;
/*     */ import de.javagl.jgltf.model.MeshPrimitiveModel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultMeshPrimitiveModel
/*     */   extends AbstractModelElement
/*     */   implements MeshPrimitiveModel
/*     */ {
/*     */   private final Map<String, AccessorModel> attributes;
/*     */   private AccessorModel indices;
/*     */   private MaterialModel materialModel;
/*     */   private final int mode;
/*     */   private final List<Map<String, AccessorModel>> targets;
/*     */   
/*     */   public DefaultMeshPrimitiveModel(int mode) {
/*  78 */     this.mode = mode;
/*  79 */     this.attributes = new LinkedHashMap<>();
/*  80 */     this.targets = new ArrayList<>();
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
/*     */   public AccessorModel putAttribute(String name, AccessorModel accessorModel) {
/*  94 */     Objects.requireNonNull(accessorModel, "The accessorModel may not be null");
/*     */     
/*  96 */     return this.attributes.put(name, accessorModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndices(AccessorModel indices) {
/* 106 */     this.indices = indices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaterialModel(MaterialModel materialModel) {
/* 116 */     this.materialModel = Objects.<MaterialModel>requireNonNull(materialModel, "The materialModel may not be null");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTarget(Map<String, AccessorModel> target) {
/* 127 */     Objects.requireNonNull(target, "The target may not be null");
/* 128 */     this.targets.add(target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, AccessorModel> getAttributes() {
/* 135 */     return Collections.unmodifiableMap(this.attributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessorModel getIndices() {
/* 141 */     return this.indices;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMode() {
/* 147 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MaterialModel getMaterialModel() {
/* 153 */     return this.materialModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Map<String, AccessorModel>> getTargets() {
/* 159 */     return Collections.unmodifiableList(this.targets);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultMeshPrimitiveModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */