/*    */ package de.javagl.jgltf.model.v1.gl;
/*    */ 
/*    */ import de.javagl.jgltf.impl.v1.TechniqueStatesFunctions;
/*    */ import de.javagl.jgltf.model.Optionals;
/*    */ import de.javagl.jgltf.model.gl.impl.DefaultTechniqueStatesFunctionsModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TechniqueStatesFunctionsModels
/*    */ {
/*    */   public static DefaultTechniqueStatesFunctionsModel create(TechniqueStatesFunctions techniqueStatesFunctions) {
/* 50 */     DefaultTechniqueStatesFunctionsModel techniqueStatesFunctionsModel = new DefaultTechniqueStatesFunctionsModel();
/*    */     
/* 52 */     techniqueStatesFunctionsModel.setBlendColor(Optionals.clone(techniqueStatesFunctions
/* 53 */           .getBlendColor()));
/* 54 */     techniqueStatesFunctionsModel.setBlendEquationSeparate(Optionals.clone(techniqueStatesFunctions
/* 55 */           .getBlendEquationSeparate()));
/* 56 */     techniqueStatesFunctionsModel.setBlendFuncSeparate(Optionals.clone(techniqueStatesFunctions
/* 57 */           .getBlendFuncSeparate()));
/* 58 */     techniqueStatesFunctionsModel.setColorMask(Optionals.clone(techniqueStatesFunctions
/* 59 */           .getColorMask()));
/* 60 */     techniqueStatesFunctionsModel.setCullFace(Optionals.clone(techniqueStatesFunctions
/* 61 */           .getCullFace()));
/* 62 */     techniqueStatesFunctionsModel.setDepthFunc(Optionals.clone(techniqueStatesFunctions
/* 63 */           .getDepthFunc()));
/* 64 */     techniqueStatesFunctionsModel.setDepthMask(Optionals.clone(techniqueStatesFunctions
/* 65 */           .getDepthMask()));
/* 66 */     techniqueStatesFunctionsModel.setDepthRange(Optionals.clone(techniqueStatesFunctions
/* 67 */           .getDepthRange()));
/* 68 */     techniqueStatesFunctionsModel.setFrontFace(Optionals.clone(techniqueStatesFunctions
/* 69 */           .getFrontFace()));
/* 70 */     techniqueStatesFunctionsModel.setLineWidth(Optionals.clone(techniqueStatesFunctions
/* 71 */           .getLineWidth()));
/* 72 */     techniqueStatesFunctionsModel.setPolygonOffset(Optionals.clone(techniqueStatesFunctions
/* 73 */           .getPolygonOffset()));
/*    */     
/* 75 */     return techniqueStatesFunctionsModel;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\gl\TechniqueStatesFunctionsModels.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */