/*    */ package de.javagl.jgltf.model.v1.gl;
/*    */ 
/*    */ import de.javagl.jgltf.impl.v1.Program;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Programs
/*    */ {
/*    */   static Program createDefaultProgram(String vertexShaderId, String fragmentShaderId) {
/* 56 */     Program program = new Program();
/* 57 */     program.setVertexShader(vertexShaderId);
/* 58 */     program.setFragmentShader(fragmentShaderId);
/* 59 */     program.addAttributes("a_position");
/* 60 */     return program;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\gl\Programs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */