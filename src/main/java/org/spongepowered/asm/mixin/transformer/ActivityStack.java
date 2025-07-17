/*     */ package org.spongepowered.asm.mixin.transformer;
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
/*     */ public class ActivityStack
/*     */ {
/*     */   public static final String GLUE_STRING = " -> ";
/*     */   private final Activity head;
/*     */   private Activity tail;
/*     */   private String glue;
/*     */   
/*     */   public class Activity
/*     */   {
/*     */     public String description;
/*     */     Activity last;
/*     */     Activity next;
/*     */     
/*     */     Activity(Activity last, String description) {
/*  47 */       if (last != null) {
/*  48 */         last.next = this;
/*     */       }
/*  50 */       this.last = last;
/*  51 */       this.description = description;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void append(String text) {
/*  60 */       this.description = (this.description != null) ? (this.description + text) : text;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void append(String textFormat, Object... args) {
/*  70 */       append(String.format(textFormat, args));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void end() {
/*  78 */       if (this.last != null) {
/*  79 */         ActivityStack.this.end(this);
/*  80 */         this.last = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void next(String description) {
/*  91 */       if (this.next != null) {
/*  92 */         this.next.end();
/*     */       }
/*  94 */       this.description = description;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void next(String descriptionFormat, Object... args) {
/* 105 */       if (descriptionFormat == null) {
/* 106 */         descriptionFormat = "null";
/*     */       }
/* 108 */       next(String.format(descriptionFormat, args));
/*     */     }
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
/*     */   public ActivityStack() {
/* 121 */     this(null, " -> ");
/*     */   }
/*     */   
/*     */   public ActivityStack(String root) {
/* 125 */     this(root, " -> ");
/*     */   }
/*     */   
/*     */   public ActivityStack(String root, String glue) {
/* 129 */     this.head = this.tail = new Activity(null, root);
/* 130 */     this.glue = glue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 137 */     this.tail = this.head;
/* 138 */     this.head.next = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Activity begin(String description) {
/* 148 */     return this.tail = new Activity(this.tail, (description != null) ? description : "null");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Activity begin(String descriptionFormat, Object... args) {
/* 159 */     if (descriptionFormat == null) {
/* 160 */       descriptionFormat = "null";
/*     */     }
/* 162 */     return this.tail = new Activity(this.tail, String.format(descriptionFormat, args));
/*     */   }
/*     */   
/*     */   void end(Activity activity) {
/* 166 */     this.tail = activity.last;
/* 167 */     this.tail.next = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 175 */     return toString(this.glue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(String glue) {
/* 186 */     if (this.head.description == null && this.head.next == null) {
/* 187 */       return "Unknown";
/*     */     }
/*     */     
/* 190 */     StringBuilder sb = new StringBuilder();
/* 191 */     for (Activity activity = this.head; activity != null; activity = activity.next) {
/* 192 */       if (activity.description != null) {
/* 193 */         sb.append(activity.description);
/* 194 */         if (activity.next != null) {
/* 195 */           sb.append(glue);
/*     */         }
/*     */       } 
/*     */     } 
/* 199 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\ActivityStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */