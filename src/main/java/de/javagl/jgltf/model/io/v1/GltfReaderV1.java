/*    */ package de.javagl.jgltf.model.io.v1;
/*    */ 
/*    */ import com.fasterxml.jackson.databind.ObjectMapper;
/*    */ import de.javagl.jgltf.impl.v1.GlTF;
/*    */ import de.javagl.jgltf.model.io.JacksonUtils;
/*    */ import de.javagl.jgltf.model.io.JsonError;
/*    */ import de.javagl.jgltf.model.io.JsonErrorConsumers;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.function.Consumer;
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
/*    */ public final class GltfReaderV1
/*    */ {
/* 54 */   private Consumer<? super JsonError> jsonErrorConsumer = JsonErrorConsumers.createLogging();
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
/*    */   public void setJsonErrorConsumer(Consumer<? super JsonError> jsonErrorConsumer) {
/* 73 */     this.jsonErrorConsumer = jsonErrorConsumer;
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
/*    */   public GlTF read(InputStream inputStream) throws IOException {
/* 86 */     ObjectMapper objectMapper = JacksonUtils.createObjectMapper(this.jsonErrorConsumer);
/* 87 */     GlTF gltf = (GlTF)objectMapper.readValue(inputStream, GlTF.class);
/* 88 */     return gltf;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v1\GltfReaderV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */