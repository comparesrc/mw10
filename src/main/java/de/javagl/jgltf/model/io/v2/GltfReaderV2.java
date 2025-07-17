/*    */ package de.javagl.jgltf.model.io.v2;
/*    */ 
/*    */ import com.fasterxml.jackson.databind.ObjectMapper;
/*    */ import de.javagl.jgltf.impl.v2.GlTF;
/*    */ import de.javagl.jgltf.model.io.JacksonUtils;
/*    */ import de.javagl.jgltf.model.io.JsonError;
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
/*    */ public final class GltfReaderV2
/*    */ {
/* 53 */   private Consumer<? super JsonError> jsonErrorConsumer = JacksonUtils.loggingJsonErrorConsumer();
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
/* 72 */     this.jsonErrorConsumer = jsonErrorConsumer;
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
/* 85 */     ObjectMapper objectMapper = JacksonUtils.createObjectMapper(this.jsonErrorConsumer);
/* 86 */     GlTF gltf = (GlTF)objectMapper.readValue(inputStream, GlTF.class);
/* 87 */     return gltf;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v2\GltfReaderV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */