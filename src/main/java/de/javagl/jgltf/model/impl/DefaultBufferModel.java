/*    */ package de.javagl.jgltf.model.impl;
/*    */ 
/*    */ import de.javagl.jgltf.model.BufferModel;
/*    */ import de.javagl.jgltf.model.io.Buffers;
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
/*    */ public final class DefaultBufferModel
/*    */   extends AbstractNamedModelElement
/*    */   implements BufferModel
/*    */ {
/*    */   private String uri;
/*    */   private ByteBuffer bufferData;
/*    */   
/*    */   public void setUri(String uri) {
/* 65 */     this.uri = uri;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBufferData(ByteBuffer bufferData) {
/* 75 */     this.bufferData = bufferData;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUri() {
/* 81 */     return this.uri;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getByteLength() {
/* 87 */     return this.bufferData.capacity();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer getBufferData() {
/* 93 */     return Buffers.createSlice(this.bufferData);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultBufferModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */