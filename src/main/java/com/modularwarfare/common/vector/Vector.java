/*    */ package com.modularwarfare.common.vector;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.nio.FloatBuffer;
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
/*    */ public abstract class Vector
/*    */   implements Serializable, ReadableVector
/*    */ {
/*    */   public final float length() {
/* 58 */     return (float)Math.sqrt(lengthSquared());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract float lengthSquared();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract Vector load(FloatBuffer paramFloatBuffer);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract Vector negate();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final Vector normalise() {
/* 90 */     float len = length();
/* 91 */     if (len != 0.0F) {
/* 92 */       float l = 1.0F / len;
/* 93 */       return scale(l);
/*    */     } 
/* 95 */     return this;
/*    */   }
/*    */   
/*    */   public abstract Vector store(FloatBuffer paramFloatBuffer);
/*    */   
/*    */   public abstract Vector scale(float paramFloat);
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\vector\Vector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */