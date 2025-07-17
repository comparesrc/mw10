/*     */ package de.javagl.jgltf.model.v1.gl;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v1.Technique;
/*     */ import de.javagl.jgltf.impl.v1.TechniqueParameters;
/*     */ import de.javagl.jgltf.impl.v1.TechniqueStates;
/*     */ import de.javagl.jgltf.impl.v1.TechniqueStatesFunctions;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Techniques
/*     */ {
/*     */   static Technique createDefaultTechnique(String programId) {
/*  60 */     Technique technique = new Technique();
/*  61 */     technique.addAttributes("a_position", "position");
/*  62 */     technique.addParameters("modelViewMatrix", 
/*  63 */         createDefaultTechniqueParameters("MODELVIEW", 
/*  64 */           Integer.valueOf(35676), null));
/*  65 */     technique.addParameters("projectionMatrix", 
/*  66 */         createDefaultTechniqueParameters("PROJECTION", 
/*  67 */           Integer.valueOf(35676), null));
/*  68 */     technique.addParameters("emission", 
/*  69 */         createDefaultTechniqueParameters(null, 
/*  70 */           Integer.valueOf(35666), 
/*  71 */           Arrays.asList(new Float[] { Float.valueOf(0.5F), Float.valueOf(0.5F), Float.valueOf(0.5F), Float.valueOf(1.0F) })));
/*  72 */     technique.addParameters("position", 
/*  73 */         createDefaultTechniqueParameters("POSITION", 
/*  74 */           Integer.valueOf(35665), null));
/*  75 */     technique.setStates(createDefaultTechniqueStates());
/*  76 */     technique.setProgram(programId);
/*     */     
/*  78 */     technique.addUniforms("u_modelViewMatrix", "modelViewMatrix");
/*  79 */     technique.addUniforms("u_projectionMatrix", "projectionMatrix");
/*  80 */     technique.addUniforms("u_emission", "emission");
/*     */     
/*  82 */     return technique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static TechniqueStates createDefaultTechniqueStates() {
/*  92 */     TechniqueStates techniqueStates = new TechniqueStates();
/*  93 */     techniqueStates.setEnable(new ArrayList(techniqueStates
/*  94 */           .defaultEnable()));
/*  95 */     techniqueStates.setFunctions(createDefaultTechniqueStatesFunctions());
/*  96 */     return techniqueStates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TechniqueStatesFunctions createDefaultTechniqueStatesFunctions() {
/* 107 */     TechniqueStatesFunctions techniqueStatesFunctions = new TechniqueStatesFunctions();
/*     */     
/* 109 */     techniqueStatesFunctions.setBlendColor(techniqueStatesFunctions
/* 110 */         .defaultBlendColor());
/* 111 */     techniqueStatesFunctions.setBlendEquationSeparate(techniqueStatesFunctions
/* 112 */         .defaultBlendEquationSeparate());
/* 113 */     techniqueStatesFunctions.setBlendFuncSeparate(techniqueStatesFunctions
/* 114 */         .defaultBlendFuncSeparate());
/* 115 */     techniqueStatesFunctions.setColorMask(techniqueStatesFunctions
/* 116 */         .defaultColorMask());
/* 117 */     techniqueStatesFunctions.setCullFace(techniqueStatesFunctions
/* 118 */         .defaultCullFace());
/* 119 */     techniqueStatesFunctions.setDepthFunc(techniqueStatesFunctions
/* 120 */         .defaultDepthFunc());
/* 121 */     techniqueStatesFunctions.setDepthMask(techniqueStatesFunctions
/* 122 */         .defaultDepthMask());
/* 123 */     techniqueStatesFunctions.setDepthRange(techniqueStatesFunctions
/* 124 */         .defaultDepthRange());
/* 125 */     techniqueStatesFunctions.setFrontFace(techniqueStatesFunctions
/* 126 */         .defaultFrontFace());
/* 127 */     techniqueStatesFunctions.setLineWidth(techniqueStatesFunctions
/* 128 */         .defaultLineWidth());
/* 129 */     techniqueStatesFunctions.setPolygonOffset(techniqueStatesFunctions
/* 130 */         .defaultPolygonOffset());
/* 131 */     techniqueStatesFunctions.setScissor(techniqueStatesFunctions
/* 132 */         .defaultScissor());
/* 133 */     return techniqueStatesFunctions;
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
/*     */   private static TechniqueParameters createDefaultTechniqueParameters(String semantic, Integer type, Object value) {
/* 148 */     TechniqueParameters techniqueParameters = new TechniqueParameters();
/* 149 */     techniqueParameters.setSemantic(semantic);
/* 150 */     techniqueParameters.setType(type);
/* 151 */     techniqueParameters.setValue(value);
/* 152 */     return techniqueParameters;
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
/*     */   public static List<Integer> obtainEnabledStates(Technique technique) {
/* 164 */     TechniqueStates states = obtainTechniqueStates(technique);
/* 165 */     List<Integer> enable = states.getEnable();
/* 166 */     if (enable == null)
/*     */     {
/* 168 */       return states.defaultEnable();
/*     */     }
/* 170 */     return enable;
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
/*     */   private static TechniqueStates obtainTechniqueStates(Technique technique) {
/* 185 */     TechniqueStates states = technique.getStates();
/* 186 */     if (states == null)
/*     */     {
/* 188 */       return GltfDefaults.getDefaultTechnique().getStates();
/*     */     }
/* 190 */     return states;
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
/*     */   public static TechniqueStatesFunctions obtainTechniqueStatesFunctions(Technique technique) {
/* 207 */     TechniqueStates states = obtainTechniqueStates(technique);
/* 208 */     TechniqueStatesFunctions functions = states.getFunctions();
/* 209 */     if (functions == null) {
/*     */ 
/*     */       
/* 212 */       TechniqueStates defaultStates = GltfDefaults.getDefaultTechnique().getStates();
/* 213 */       return defaultStates.getFunctions();
/*     */     } 
/* 215 */     return functions;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\gl\Techniques.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */