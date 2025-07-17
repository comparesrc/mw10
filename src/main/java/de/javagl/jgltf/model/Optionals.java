/*     */ package de.javagl.jgltf.model;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class Optionals
/*     */ {
/*     */   public static <T> List<T> of(List<T> list) {
/*  51 */     return of(list, Collections.emptyList());
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
/*     */   public static <T> T get(List<T> list, int index) {
/*  66 */     if (list == null)
/*     */     {
/*  68 */       return null;
/*     */     }
/*  70 */     if (index < 0)
/*     */     {
/*  72 */       return null;
/*     */     }
/*  74 */     if (index >= list.size())
/*     */     {
/*  76 */       return null;
/*     */     }
/*  78 */     return list.get(index);
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
/*     */   public static <K, V> Map<K, V> of(Map<K, V> map) {
/*  93 */     return of(map, Collections.emptyMap());
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
/*     */   public static <T> T of(T value, T defaultValue) {
/* 108 */     return (value != null) ? value : defaultValue;
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
/*     */   
/*     */   public static <V> V get(Object key, Map<?, V> map) {
/* 124 */     if (key == null)
/*     */     {
/* 126 */       return null;
/*     */     }
/* 128 */     if (map == null)
/*     */     {
/* 130 */       return null;
/*     */     }
/* 132 */     return map.get(key);
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
/*     */   public static float[] clone(float[] array) {
/* 144 */     if (array == null)
/*     */     {
/* 146 */       return null;
/*     */     }
/* 148 */     return (float[])array.clone();
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
/*     */   public static int[] clone(int[] array) {
/* 160 */     if (array == null)
/*     */     {
/* 162 */       return null;
/*     */     }
/* 164 */     return (int[])array.clone();
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
/*     */   public static boolean[] clone(boolean[] array) {
/* 176 */     if (array == null)
/*     */     {
/* 178 */       return null;
/*     */     }
/* 180 */     return (boolean[])array.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\Optionals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */