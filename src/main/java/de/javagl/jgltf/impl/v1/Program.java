/*     */ package de.javagl.jgltf.impl.v1;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class Program
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private List<String> attributes;
/*     */   private String fragmentShader;
/*     */   private String vertexShader;
/*     */   
/*     */   public void setAttributes(List<String> attributes) {
/*  55 */     if (attributes == null) {
/*  56 */       this.attributes = attributes;
/*     */       return;
/*     */     } 
/*  59 */     this.attributes = attributes;
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
/*     */   
/*     */   public List<String> getAttributes() {
/*  72 */     return this.attributes;
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
/*     */   
/*     */   public void addAttributes(String element) {
/*  85 */     if (element == null) {
/*  86 */       throw new NullPointerException("The element may not be null");
/*     */     }
/*  88 */     List<String> oldList = this.attributes;
/*  89 */     List<String> newList = new ArrayList<>();
/*  90 */     if (oldList != null) {
/*  91 */       newList.addAll(oldList);
/*     */     }
/*  93 */     newList.add(element);
/*  94 */     this.attributes = newList;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAttributes(String element) {
/* 109 */     if (element == null) {
/* 110 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 112 */     List<String> oldList = this.attributes;
/* 113 */     List<String> newList = new ArrayList<>();
/* 114 */     if (oldList != null) {
/* 115 */       newList.addAll(oldList);
/*     */     }
/* 117 */     newList.remove(element);
/* 118 */     if (newList.isEmpty()) {
/* 119 */       this.attributes = null;
/*     */     } else {
/* 121 */       this.attributes = newList;
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
/*     */   public List<String> defaultAttributes() {
/* 133 */     return new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFragmentShader(String fragmentShader) {
/* 144 */     if (fragmentShader == null) {
/* 145 */       throw new NullPointerException("Invalid value for fragmentShader: " + fragmentShader + ", may not be null");
/*     */     }
/* 147 */     this.fragmentShader = fragmentShader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFragmentShader() {
/* 157 */     return this.fragmentShader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVertexShader(String vertexShader) {
/* 168 */     if (vertexShader == null) {
/* 169 */       throw new NullPointerException("Invalid value for vertexShader: " + vertexShader + ", may not be null");
/*     */     }
/* 171 */     this.vertexShader = vertexShader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVertexShader() {
/* 181 */     return this.vertexShader;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Program.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */