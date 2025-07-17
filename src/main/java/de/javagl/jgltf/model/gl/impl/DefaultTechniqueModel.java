/*     */ package de.javagl.jgltf.model.gl.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.gl.ProgramModel;
/*     */ import de.javagl.jgltf.model.gl.TechniqueModel;
/*     */ import de.javagl.jgltf.model.gl.TechniqueParametersModel;
/*     */ import de.javagl.jgltf.model.gl.TechniqueStatesModel;
/*     */ import de.javagl.jgltf.model.impl.AbstractNamedModelElement;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultTechniqueModel
/*     */   extends AbstractNamedModelElement
/*     */   implements TechniqueModel
/*     */ {
/*     */   private ProgramModel programModel;
/*  76 */   private final Map<String, TechniqueParametersModel> parameters = new LinkedHashMap<>();
/*  77 */   private final Map<String, String> attributes = new LinkedHashMap<>();
/*  78 */   private final Map<String, String> uniforms = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private TechniqueStatesModel techniqueStatesModel;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProgramModel(ProgramModel programModel) {
/*  88 */     this.programModel = programModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ProgramModel getProgramModel() {
/*  94 */     return this.programModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, TechniqueParametersModel> getParameters() {
/* 100 */     return Collections.unmodifiableMap(this.parameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(String attributeName, String parameterName) {
/* 111 */     Objects.requireNonNull(attributeName, "The attributeName may not be null");
/*     */     
/* 113 */     Objects.requireNonNull(parameterName, "The parameterName may not be null");
/*     */     
/* 115 */     this.attributes.put(attributeName, parameterName);
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
/*     */   public void addParameter(String parameterName, TechniqueParametersModel techniqueParametersModel) {
/* 127 */     Objects.requireNonNull(parameterName, "The parameterName may not be null");
/*     */     
/* 129 */     Objects.requireNonNull(techniqueParametersModel, "The techniqueParametersModel may not be null");
/*     */     
/* 131 */     this.parameters.put(parameterName, techniqueParametersModel);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getAttributes() {
/* 137 */     return Collections.unmodifiableMap(this.attributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TechniqueParametersModel getAttributeParameters(String attributeName) {
/* 143 */     String parameterName = this.attributes.get(attributeName);
/* 144 */     return this.parameters.get(parameterName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addUniform(String uniformName, String parameterName) {
/* 155 */     Objects.requireNonNull(uniformName, "The uniformName may not be null");
/*     */     
/* 157 */     Objects.requireNonNull(parameterName, "The parameterName may not be null");
/*     */     
/* 159 */     this.uniforms.put(uniformName, parameterName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getUniforms() {
/* 165 */     return Collections.unmodifiableMap(this.uniforms);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TechniqueParametersModel getUniformParameters(String uniformName) {
/* 171 */     String parameterName = this.uniforms.get(uniformName);
/* 172 */     return this.parameters.get(parameterName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTechniqueStatesModel(TechniqueStatesModel techniqueStatesModel) {
/* 183 */     this.techniqueStatesModel = techniqueStatesModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TechniqueStatesModel getTechniqueStatesModel() {
/* 189 */     return this.techniqueStatesModel;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\impl\DefaultTechniqueModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */