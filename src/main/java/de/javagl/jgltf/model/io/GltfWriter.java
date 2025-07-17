/*    */ package de.javagl.jgltf.model.io;
/*    */ 
/*    */ import com.fasterxml.jackson.databind.ObjectMapper;
/*    */ import com.fasterxml.jackson.databind.SerializationFeature;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ public final class GltfWriter
/*    */ {
/*    */   private boolean indenting = true;
/*    */   
/*    */   public void setIndenting(boolean indenting) {
/* 61 */     this.indenting = indenting;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isIndenting() {
/* 71 */     return this.indenting;
/*    */   }
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
/*    */   public void write(Object gltf, OutputStream outputStream) throws IOException {
/* 85 */     ObjectMapper objectMapper = JacksonUtils.createObjectMapper();
/* 86 */     if (this.indenting)
/*    */     {
/* 88 */       objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
/*    */     }
/* 90 */     objectMapper.writeValue(outputStream, gltf);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\GltfWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */