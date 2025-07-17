/*     */ package mchhui.hegltf;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
/*     */ import org.joml.Vector4f;
/*     */ import org.joml.Vector4fc;
/*     */ 
/*     */ public class DataAnimation
/*     */ {
/*  13 */   public ArrayList<DataKeyframe> posChannel = new ArrayList<>();
/*  14 */   public ArrayList<DataKeyframe> rotChannel = new ArrayList<>();
/*  15 */   public ArrayList<DataKeyframe> sizeChannel = new ArrayList<>();
/*  16 */   public float theata90 = (float)Math.toRadians(90.0D);
/*     */   
/*     */   public Transform findTransform(float time, Vector3f pos, Vector3f size, Quaternionf rot) {
/*  19 */     Transform transform = new Transform(pos, size, rot);
/*  20 */     int left = 0;
/*  21 */     int right = 0;
/*     */     
/*  23 */     ArrayList<DataKeyframe> channel = this.posChannel;
/*  24 */     Vector4f vec = transform.pos;
/*  25 */     Vector4f vecTemp = new Vector4f();
/*  26 */     for (int i = 0; i < 2; i++) {
/*  27 */       if (i == 1) {
/*  28 */         channel = this.sizeChannel;
/*  29 */         vec = transform.size;
/*     */       } 
/*  31 */       left = 0;
/*  32 */       right = channel.size() - 1;
/*  33 */       if (channel.size() > 0) {
/*  34 */         if (time <= ((DataKeyframe)channel.get(left)).time) {
/*  35 */           vec.set((Vector4fc)((DataKeyframe)channel.get(left)).vec);
/*  36 */         } else if (time >= ((DataKeyframe)channel.get(right)).time) {
/*  37 */           vec.set((Vector4fc)((DataKeyframe)channel.get(right)).vec);
/*     */         } else {
/*     */           do {
/*  40 */             int mid = left + right >> 1;
/*  41 */             if (((DataKeyframe)channel.get(mid)).time <= time) {
/*  42 */               left = mid;
/*     */             } else {
/*  44 */               right = mid;
/*     */             } 
/*  46 */           } while (left + 1 < right);
/*     */ 
/*     */ 
/*     */           
/*  50 */           float per = (time - ((DataKeyframe)channel.get(left)).time) / (((DataKeyframe)channel.get(right)).time - ((DataKeyframe)channel.get(left)).time);
/*  51 */           if (per > 1.0F) {
/*  52 */             per = 1.0F;
/*     */           }
/*  54 */           vec.set((Vector4fc)((DataKeyframe)channel.get(left)).vec);
/*  55 */           vec.mul(1.0F - per);
/*  56 */           vecTemp.set((Vector4fc)((DataKeyframe)channel.get(right)).vec);
/*  57 */           vecTemp.mul(per);
/*  58 */           vec.add((Vector4fc)vecTemp);
/*     */         } 
/*     */       }
/*     */     } 
/*  62 */     channel = this.rotChannel;
/*  63 */     left = 0;
/*  64 */     right = channel.size() - 1;
/*  65 */     if (channel.size() > 0) {
/*  66 */       if (time <= ((DataKeyframe)channel.get(left)).time) {
/*  67 */         vec = ((DataKeyframe)channel.get(left)).vec;
/*  68 */         transform.rot = new Quaternionf(vec.x, vec.y, vec.z, vec.w);
/*  69 */       } else if (time >= ((DataKeyframe)channel.get(right)).time) {
/*  70 */         vec = ((DataKeyframe)channel.get(right)).vec;
/*  71 */         transform.rot = new Quaternionf(vec.x, vec.y, vec.z, vec.w);
/*     */       } else {
/*     */         do {
/*  74 */           int mid = left + right >> 1;
/*  75 */           if (((DataKeyframe)channel.get(mid)).time <= time) {
/*  76 */             left = mid;
/*     */           } else {
/*  78 */             right = mid;
/*     */           } 
/*  80 */         } while (left + 1 < right);
/*     */ 
/*     */ 
/*     */         
/*  84 */         float per = (time - ((DataKeyframe)channel.get(left)).time) / (((DataKeyframe)channel.get(right)).time - ((DataKeyframe)channel.get(left)).time);
/*  85 */         if (per > 1.0F) {
/*  86 */           per = 1.0F;
/*     */         }
/*  88 */         vec = ((DataKeyframe)channel.get(left)).vec;
/*  89 */         vecTemp = ((DataKeyframe)channel.get(right)).vec;
/*  90 */         Quaternionf q0 = (new Quaternionf(vec.x, vec.y, vec.z, vec.w)).normalize();
/*  91 */         Quaternionf q1 = (new Quaternionf(vecTemp.x, vecTemp.y, vecTemp.z, vecTemp.w)).normalize();
/*  92 */         transform.rot = q0.slerp((Quaternionfc)q1, per);
/*     */       } 
/*     */     }
/*  95 */     return transform;
/*     */   }
/*     */   
/*     */   public static class Transform {
/*  99 */     public Vector4f pos = new Vector4f();
/* 100 */     public Vector4f size = new Vector4f();
/* 101 */     public Quaternionf rot = new Quaternionf();
/*     */     
/*     */     public Transform(Vector3f pos, Vector3f size, Quaternionf rot) {
/* 104 */       this.pos.set((Vector3fc)pos, 0.0F);
/* 105 */       this.size.set((Vector3fc)size, 0.0F);
/* 106 */       this.rot.set((Quaternionfc)rot);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DataKeyframe {
/*     */     public float time;
/*     */     public Vector4f vec;
/*     */     
/*     */     public DataKeyframe(float time, Vector4f vec) {
/* 115 */       this.time = time;
/* 116 */       this.vec = vec;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\hegltf\DataAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */