/*     */ package de.javagl.jgltf.model.v1;
/*     */ 
/*     */ import de.javagl.jgltf.model.MaterialModel;
/*     */ import de.javagl.jgltf.model.gl.TechniqueModel;
/*     */ import de.javagl.jgltf.model.impl.AbstractNamedModelElement;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MaterialModelV1
/*     */   extends AbstractNamedModelElement
/*     */   implements MaterialModel
/*     */ {
/*     */   private TechniqueModel techniqueModel;
/*  64 */   private Map<String, Object> values = Collections.emptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValues(Map<String, Object> values) {
/*  76 */     if (values == null) {
/*     */       
/*  78 */       this.values = Collections.emptyMap();
/*     */     }
/*     */     else {
/*     */       
/*  82 */       this.values = Collections.unmodifiableMap(new LinkedHashMap<>(values));
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
/*     */   public void setTechniqueModel(TechniqueModel techniqueModel) {
/*  94 */     this.techniqueModel = techniqueModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TechniqueModel getTechniqueModel() {
/* 104 */     return this.techniqueModel;
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
/*     */   public Map<String, Object> getValues() {
/* 117 */     return this.values;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\MaterialModelV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */