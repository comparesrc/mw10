/*     */ package net.lingala.zip4j.progress;
/*     */ 
/*     */ import net.lingala.zip4j.exception.ZipException;
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
/*     */ public class ProgressMonitor
/*     */ {
/*     */   private int state;
/*     */   private long totalWork;
/*     */   private long workCompleted;
/*     */   private int percentDone;
/*     */   private int currentOperation;
/*     */   private String fileName;
/*     */   private int result;
/*     */   private Throwable exception;
/*     */   private boolean cancelAllTasks;
/*     */   private boolean pause;
/*     */   public static final int STATE_READY = 0;
/*     */   public static final int STATE_BUSY = 1;
/*     */   public static final int RESULT_SUCCESS = 0;
/*     */   public static final int RESULT_WORKING = 1;
/*     */   public static final int RESULT_ERROR = 2;
/*     */   public static final int RESULT_CANCELLED = 3;
/*     */   public static final int OPERATION_NONE = -1;
/*     */   public static final int OPERATION_ADD = 0;
/*     */   public static final int OPERATION_EXTRACT = 1;
/*     */   public static final int OPERATION_REMOVE = 2;
/*     */   public static final int OPERATION_CALC_CRC = 3;
/*     */   public static final int OPERATION_MERGE = 4;
/*     */   
/*     */   public ProgressMonitor() {
/*  57 */     reset();
/*  58 */     this.percentDone = 0;
/*     */   }
/*     */   
/*     */   public int getState() {
/*  62 */     return this.state;
/*     */   }
/*     */   
/*     */   public void setState(int state) {
/*  66 */     this.state = state;
/*     */   }
/*     */   
/*     */   public long getTotalWork() {
/*  70 */     return this.totalWork;
/*     */   }
/*     */   
/*     */   public void setTotalWork(long totalWork) {
/*  74 */     this.totalWork = totalWork;
/*     */   }
/*     */   
/*     */   public long getWorkCompleted() {
/*  78 */     return this.workCompleted;
/*     */   }
/*     */   
/*     */   public void updateWorkCompleted(long workCompleted) {
/*  82 */     this.workCompleted += workCompleted;
/*  83 */     this.percentDone = (int)(this.workCompleted * 100L / this.totalWork);
/*  84 */     if (this.percentDone > 100) {
/*  85 */       this.percentDone = 100;
/*     */     }
/*  87 */     while (this.pause) {
/*     */       try {
/*  89 */         Thread.sleep(150L);
/*  90 */       } catch (InterruptedException e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPercentDone() {
/*  97 */     return this.percentDone;
/*     */   }
/*     */   
/*     */   public void setPercentDone(int percentDone) {
/* 101 */     this.percentDone = percentDone;
/*     */   }
/*     */   
/*     */   public int getResult() {
/* 105 */     return this.result;
/*     */   }
/*     */   
/*     */   public void setResult(int result) {
/* 109 */     this.result = result;
/*     */   }
/*     */   
/*     */   public String getFileName() {
/* 113 */     return this.fileName;
/*     */   }
/*     */   
/*     */   public void setFileName(String fileName) {
/* 117 */     this.fileName = fileName;
/*     */   }
/*     */   
/*     */   public int getCurrentOperation() {
/* 121 */     return this.currentOperation;
/*     */   }
/*     */   
/*     */   public void setCurrentOperation(int currentOperation) {
/* 125 */     this.currentOperation = currentOperation;
/*     */   }
/*     */   
/*     */   public Throwable getException() {
/* 129 */     return this.exception;
/*     */   }
/*     */   
/*     */   public void setException(Throwable exception) {
/* 133 */     this.exception = exception;
/*     */   }
/*     */   
/*     */   public void endProgressMonitorSuccess() throws ZipException {
/* 137 */     reset();
/* 138 */     this.result = 0;
/*     */   }
/*     */   
/*     */   public void endProgressMonitorError(Throwable e) throws ZipException {
/* 142 */     reset();
/* 143 */     this.result = 2;
/* 144 */     this.exception = e;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 148 */     this.currentOperation = -1;
/* 149 */     this.state = 0;
/* 150 */     this.fileName = null;
/* 151 */     this.totalWork = 0L;
/* 152 */     this.workCompleted = 0L;
/*     */   }
/*     */   
/*     */   public void fullReset() {
/* 156 */     reset();
/* 157 */     this.exception = null;
/* 158 */     this.result = 0;
/* 159 */     this.percentDone = 0;
/*     */   }
/*     */   
/*     */   public boolean isCancelAllTasks() {
/* 163 */     return this.cancelAllTasks;
/*     */   }
/*     */   
/*     */   public void cancelAllTasks() {
/* 167 */     this.cancelAllTasks = true;
/*     */   }
/*     */   
/*     */   public boolean isPause() {
/* 171 */     return this.pause;
/*     */   }
/*     */   
/*     */   public void setPause(boolean pause) {
/* 175 */     this.pause = pause;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\progress\ProgressMonitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */