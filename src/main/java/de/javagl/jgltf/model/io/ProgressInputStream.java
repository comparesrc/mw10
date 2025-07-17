/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.function.LongConsumer;
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
/*     */ public final class ProgressInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private final PropertyChangeSupport propertyChangeSupport;
/*     */   private final List<LongConsumer> totalNumBytesReadConsumers;
/*     */   private volatile long totalNumBytesRead;
/*     */   
/*     */   public ProgressInputStream(InputStream inputStream) {
/*  70 */     super(inputStream);
/*  71 */     this.propertyChangeSupport = new PropertyChangeSupport(this);
/*  72 */     this.totalNumBytesReadConsumers = new CopyOnWriteArrayList<>();
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
/*     */   long getTotalNumBytesRead() {
/*  84 */     return this.totalNumBytesRead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addPropertyChangeListener(PropertyChangeListener listener) {
/*  95 */     this.propertyChangeSupport.addPropertyChangeListener(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removePropertyChangeListener(PropertyChangeListener listener) {
/* 105 */     this.propertyChangeSupport.removePropertyChangeListener(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTotalNumBytesReadConsumer(LongConsumer consumer) {
/* 116 */     this.totalNumBytesReadConsumers.add(consumer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTotalNumBytesReadConsumer(LongConsumer consumer) {
/* 126 */     this.totalNumBytesReadConsumers.remove(consumer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 132 */     int b = super.read();
/* 133 */     if (b != -1)
/*     */     {
/* 135 */       updateProgress(1L);
/*     */     }
/* 137 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/* 143 */     return read(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/* 149 */     int read = super.read(b, off, len);
/* 150 */     if (read == -1)
/*     */     {
/* 152 */       return -1;
/*     */     }
/* 154 */     return (int)updateProgress(read);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 160 */     return updateProgress(super.skip(n));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mark(int readlimit) {
/* 166 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() throws IOException {
/* 172 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 178 */     return false;
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
/*     */   private long updateProgress(long numBytesRead) {
/* 190 */     if (numBytesRead > 0L) {
/*     */       
/* 192 */       long oldTotalNumBytesRead = this.totalNumBytesRead;
/* 193 */       this.totalNumBytesRead += numBytesRead;
/* 194 */       this.propertyChangeSupport.firePropertyChange("totalNumBytesRead", 
/* 195 */           Long.valueOf(oldTotalNumBytesRead), Long.valueOf(this.totalNumBytesRead));
/* 196 */       fireTotalNumBytesRead();
/*     */     } 
/* 198 */     return numBytesRead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireTotalNumBytesRead() {
/* 207 */     for (LongConsumer consumer : this.totalNumBytesReadConsumers)
/*     */     {
/* 209 */       consumer.accept(this.totalNumBytesRead);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\ProgressInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */