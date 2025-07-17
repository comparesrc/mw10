/*     */ package com.modularwarfare.common.hitbox;
/*     */ 
/*     */ import com.modularwarfare.common.hitbox.maths.EnumHitboxType;
/*     */ import com.modularwarfare.common.hitbox.maths.RotatedAxes;
/*     */ import com.modularwarfare.common.vector.Vector3f;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
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
/*     */ public class PlayerHitbox
/*     */ {
/*     */   public EntityPlayer player;
/*     */   public RotatedAxes axes;
/*     */   public Vector3f rP;
/*     */   public Vector3f o;
/*     */   public Vector3f d;
/*     */   public EnumHitboxType type;
/*     */   
/*     */   public PlayerHitbox(EntityPlayer player, RotatedAxes axes, Vector3f rotationPoint, Vector3f origin, Vector3f dimensions, EnumHitboxType type) {
/*  39 */     this.player = player;
/*  40 */     this.axes = axes;
/*  41 */     this.o = origin;
/*  42 */     this.d = dimensions;
/*  43 */     this.type = type;
/*  44 */     this.rP = rotationPoint;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getAxisAlignedBB(Vector3f pos) {
/*  63 */     Vector3f pointMin = new Vector3f(this.o.x, this.o.y, this.o.z);
/*  64 */     pointMin = this.axes.findLocalVectorGlobally(pointMin);
/*     */     
/*  66 */     Vector3f pointMax = new Vector3f(this.o.x + this.d.x, this.o.y + this.d.y, this.o.z + this.d.z);
/*  67 */     pointMax = this.axes.findLocalVectorGlobally(pointMax);
/*     */     
/*  69 */     AxisAlignedBB hitbox = new AxisAlignedBB((pos.x + this.rP.x + pointMin.x), (pos.y + this.rP.y + pointMin.y), (pos.z + this.rP.z + pointMin.z), (pos.x + this.rP.x + pointMax.x), (pos.y + this.rP.y + pointMax.y), (pos.z + this.rP.z + pointMax.z));
/*  70 */     return hitbox;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean raytrace(Vector3f origin, Vector3f motion) {
/*  76 */     origin = Vector3f.sub(origin, this.rP, null);
/*  77 */     origin = this.axes.findGlobalVectorLocally(origin);
/*  78 */     motion = this.axes.findGlobalVectorLocally(motion);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     if (motion.x != 0.0F) {
/*  85 */       if (origin.x < this.o.x) {
/*     */         
/*  87 */         float intersectTime = (this.o.x - origin.x) / motion.x;
/*  88 */         float intersectY = origin.y + motion.y * intersectTime;
/*  89 */         float intersectZ = origin.z + motion.z * intersectTime;
/*  90 */         if (intersectY >= this.o.y && intersectY <= this.o.y + this.d.y && intersectZ >= this.o.z && intersectZ <= this.o.z + this.d.z)
/*  91 */           return true; 
/*  92 */       } else if (origin.x > this.o.x + this.d.x) {
/*     */         
/*  94 */         float intersectTime = (this.o.x + this.d.x - origin.x) / motion.x;
/*  95 */         float intersectY = origin.y + motion.y * intersectTime;
/*  96 */         float intersectZ = origin.z + motion.z * intersectTime;
/*  97 */         if (intersectY >= this.o.y && intersectY <= this.o.y + this.d.y && intersectZ >= this.o.z && intersectZ <= this.o.z + this.d.z) {
/*  98 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 103 */     if (motion.z != 0.0F) {
/* 104 */       if (origin.z < this.o.z) {
/*     */         
/* 106 */         float intersectTime = (this.o.z - origin.z) / motion.z;
/* 107 */         float intersectX = origin.x + motion.x * intersectTime;
/* 108 */         float intersectY = origin.y + motion.y * intersectTime;
/* 109 */         if (intersectX >= this.o.x && intersectX <= this.o.x + this.d.x && intersectY >= this.o.y && intersectY <= this.o.y + this.d.y)
/* 110 */           return true; 
/* 111 */       } else if (origin.z > this.o.z + this.d.z) {
/*     */         
/* 113 */         float intersectTime = (this.o.z + this.d.z - origin.z) / motion.z;
/* 114 */         float intersectX = origin.x + motion.x * intersectTime;
/* 115 */         float intersectY = origin.y + motion.y * intersectTime;
/* 116 */         if (intersectX >= this.o.x && intersectX <= this.o.x + this.d.x && intersectY >= this.o.y && intersectY <= this.o.y + this.d.y) {
/* 117 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 122 */     if (motion.y != 0.0F) {
/* 123 */       if (origin.y < this.o.y) {
/*     */         
/* 125 */         float intersectTime = (this.o.y - origin.y) / motion.y;
/* 126 */         float intersectX = origin.x + motion.x * intersectTime;
/* 127 */         float intersectZ = origin.z + motion.z * intersectTime;
/* 128 */         if (intersectX >= this.o.x && intersectX <= this.o.x + this.d.x && intersectZ >= this.o.z && intersectZ <= this.o.z + this.d.z)
/* 129 */           return true; 
/* 130 */       } else if (origin.y > this.o.y + this.d.y) {
/*     */         
/* 132 */         float intersectTime = (this.o.y + this.d.y - origin.y) / motion.y;
/* 133 */         float intersectX = origin.x + motion.x * intersectTime;
/* 134 */         float intersectZ = origin.z + motion.z * intersectTime;
/* 135 */         if (intersectX >= this.o.x && intersectX <= this.o.x + this.d.x && intersectZ >= this.o.z && intersectZ <= this.o.z + this.d.z) {
/* 136 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 140 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\hitbox\PlayerHitbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */