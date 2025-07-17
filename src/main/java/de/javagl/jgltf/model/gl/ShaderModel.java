/*    */ package de.javagl.jgltf.model.gl;
/*    */ 
/*    */ import de.javagl.jgltf.model.NamedModelElement;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public interface ShaderModel
/*    */   extends NamedModelElement
/*    */ {
/*    */   String getUri();
/*    */   
/*    */   ByteBuffer getShaderData();
/*    */   
/*    */   String getShaderSource();
/*    */   
/*    */   ShaderType getShaderType();
/*    */   
/*    */   public enum ShaderType
/*    */   {
/* 46 */     VERTEX_SHADER,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     FRAGMENT_SHADER;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\ShaderModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */