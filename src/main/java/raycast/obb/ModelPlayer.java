/*     */ package com.modularwarfare.raycast.obb;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ public class ModelPlayer
/*     */ {
/*  12 */   public ModelPosture bipedHead = new ModelPosture();
/*  13 */   public ModelPosture bipedBody = new ModelPosture();
/*  14 */   public ModelPosture bipedRightArm = new ModelPosture();
/*  15 */   public ModelPosture bipedLeftArm = new ModelPosture();
/*  16 */   public ModelPosture bipedRightLeg = new ModelPosture();
/*  17 */   public ModelPosture bipedLeftLeg = new ModelPosture();
/*     */   public boolean isRiding;
/*     */   public ArmPose leftArmPose;
/*     */   public ArmPose rightArmPose;
/*     */   public boolean isSneak;
/*     */   public float swingProgress;
/*     */   
/*     */   public enum ArmPose
/*     */   {
/*  26 */     EMPTY,
/*  27 */     ITEM,
/*  28 */     BLOCK,
/*  29 */     BOW_AND_ARROW;
/*     */   }
/*     */   
/*     */   public static class ModelPosture
/*     */   {
/*     */     public float rotateAngleX;
/*     */     public float rotateAngleY;
/*     */     public float rotateAngleZ;
/*     */   }
/*     */   
/*     */   public EnumHandSide getMainHand(Entity entityIn) {
/*  40 */     if (entityIn instanceof EntityLivingBase) {
/*     */       
/*  42 */       EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
/*  43 */       EnumHandSide enumhandside = entitylivingbase.func_184591_cq();
/*  44 */       return (entitylivingbase.field_184622_au == EnumHand.MAIN_HAND) ? enumhandside : enumhandside.func_188468_a();
/*     */     } 
/*     */ 
/*     */     
/*  48 */     return EnumHandSide.RIGHT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelPosture getArmForSide(EnumHandSide side) {
/*  54 */     return (side == EnumHandSide.LEFT) ? this.bipedLeftArm : this.bipedRightArm;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*  60 */     boolean flag = (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).func_184599_cB() > 4);
/*  61 */     this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;
/*     */     
/*  63 */     if (flag) {
/*     */       
/*  65 */       this.bipedHead.rotateAngleX = -0.7853982F;
/*     */     }
/*     */     else {
/*     */       
/*  69 */       this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
/*     */     } 
/*     */     
/*  72 */     this.bipedBody.rotateAngleY = 0.0F;
/*  73 */     float f = 1.0F;
/*     */     
/*  75 */     if (flag) {
/*     */       
/*  77 */       f = (float)(entityIn.field_70159_w * entityIn.field_70159_w + entityIn.field_70181_x * entityIn.field_70181_x + entityIn.field_70179_y * entityIn.field_70179_y);
/*  78 */       f /= 0.2F;
/*  79 */       f = f * f * f;
/*     */     } 
/*     */     
/*  82 */     if (f < 1.0F)
/*     */     {
/*  84 */       f = 1.0F;
/*     */     }
/*     */     
/*  87 */     this.bipedRightArm.rotateAngleX = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F / f;
/*  88 */     this.bipedLeftArm.rotateAngleX = MathHelper.func_76134_b(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
/*  89 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/*  90 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/*  91 */     this.bipedRightLeg.rotateAngleX = MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
/*  92 */     this.bipedLeftLeg.rotateAngleX = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount / f;
/*  93 */     this.bipedRightLeg.rotateAngleY = 0.0F;
/*  94 */     this.bipedLeftLeg.rotateAngleY = 0.0F;
/*  95 */     this.bipedRightLeg.rotateAngleZ = 0.0F;
/*  96 */     this.bipedLeftLeg.rotateAngleZ = 0.0F;
/*     */     
/*  98 */     if (this.isRiding) {
/*     */       
/* 100 */       this.bipedRightArm.rotateAngleX += -0.62831855F;
/* 101 */       this.bipedLeftArm.rotateAngleX += -0.62831855F;
/* 102 */       this.bipedRightLeg.rotateAngleX = -1.4137167F;
/* 103 */       this.bipedRightLeg.rotateAngleY = 0.31415927F;
/* 104 */       this.bipedRightLeg.rotateAngleZ = 0.07853982F;
/* 105 */       this.bipedLeftLeg.rotateAngleX = -1.4137167F;
/* 106 */       this.bipedLeftLeg.rotateAngleY = -0.31415927F;
/* 107 */       this.bipedLeftLeg.rotateAngleZ = -0.07853982F;
/*     */     } 
/*     */     
/* 110 */     this.bipedRightArm.rotateAngleY = 0.0F;
/* 111 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/*     */     
/* 113 */     switch (this.leftArmPose) {
/*     */       
/*     */       case EMPTY:
/* 116 */         this.bipedLeftArm.rotateAngleY = 0.0F;
/*     */         break;
/*     */       case BLOCK:
/* 119 */         this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
/* 120 */         this.bipedLeftArm.rotateAngleY = 0.5235988F;
/*     */         break;
/*     */       case ITEM:
/* 123 */         this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.31415927F;
/* 124 */         this.bipedLeftArm.rotateAngleY = 0.0F;
/*     */         break;
/*     */     } 
/* 127 */     switch (this.rightArmPose) {
/*     */       
/*     */       case EMPTY:
/* 130 */         this.bipedRightArm.rotateAngleY = 0.0F;
/*     */         break;
/*     */       case BLOCK:
/* 133 */         this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
/* 134 */         this.bipedRightArm.rotateAngleY = -0.5235988F;
/*     */         break;
/*     */       case ITEM:
/* 137 */         this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.31415927F;
/* 138 */         this.bipedRightArm.rotateAngleY = 0.0F;
/*     */         break;
/*     */     } 
/* 141 */     if (this.swingProgress > 0.0F) {
/*     */       
/* 143 */       EnumHandSide enumhandside = getMainHand(entityIn);
/* 144 */       ModelPosture modelrenderer = getArmForSide(enumhandside);
/* 145 */       float f1 = this.swingProgress;
/* 146 */       this.bipedBody.rotateAngleY = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * 6.2831855F) * 0.2F;
/*     */       
/* 148 */       if (enumhandside == EnumHandSide.LEFT)
/*     */       {
/* 150 */         this.bipedBody.rotateAngleY *= -1.0F;
/*     */       }
/*     */       
/* 153 */       this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 154 */       this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 155 */       this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
/* 156 */       f1 = 1.0F - this.swingProgress;
/* 157 */       f1 *= f1;
/* 158 */       f1 *= f1;
/* 159 */       f1 = 1.0F - f1;
/* 160 */       float f2 = MathHelper.func_76126_a(f1 * 3.1415927F);
/* 161 */       float f3 = MathHelper.func_76126_a(this.swingProgress * 3.1415927F) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
/* 162 */       modelrenderer.rotateAngleX = (float)(modelrenderer.rotateAngleX - f2 * 1.2D + f3);
/* 163 */       modelrenderer.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
/* 164 */       modelrenderer.rotateAngleZ += MathHelper.func_76126_a(this.swingProgress * 3.1415927F) * -0.4F;
/*     */     } 
/*     */     
/* 167 */     if (this.isSneak) {
/*     */       
/* 169 */       this.bipedBody.rotateAngleX = 0.5F;
/* 170 */       this.bipedRightArm.rotateAngleX += 0.4F;
/* 171 */       this.bipedLeftArm.rotateAngleX += 0.4F;
/*     */     }
/*     */     else {
/*     */       
/* 175 */       this.bipedBody.rotateAngleX = 0.0F;
/*     */     } 
/*     */     
/* 178 */     this.bipedRightArm.rotateAngleZ += MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 179 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 180 */     this.bipedRightArm.rotateAngleX += MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
/* 181 */     this.bipedLeftArm.rotateAngleX -= MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
/*     */     
/* 183 */     if (this.rightArmPose == ArmPose.BOW_AND_ARROW) {
/*     */       
/* 185 */       this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY;
/* 186 */       this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY + 0.4F;
/* 187 */       this.bipedRightArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/* 188 */       this.bipedLeftArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/*     */     }
/* 190 */     else if (this.leftArmPose == ArmPose.BOW_AND_ARROW) {
/*     */       
/* 192 */       this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY - 0.4F;
/* 193 */       this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY;
/* 194 */       this.bipedRightArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/* 195 */       this.bipedLeftArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void copyFrom(net.minecraft.client.model.ModelPlayer model) {
/* 200 */     this.bipedHead.rotateAngleX = model.field_78116_c.field_78795_f;
/* 201 */     this.bipedHead.rotateAngleY = model.field_78116_c.field_78796_g;
/* 202 */     this.bipedHead.rotateAngleZ = model.field_78116_c.field_78808_h;
/* 203 */     this.bipedBody.rotateAngleX = model.field_78115_e.field_78795_f;
/* 204 */     this.bipedBody.rotateAngleY = model.field_78115_e.field_78796_g;
/* 205 */     this.bipedBody.rotateAngleZ = model.field_78115_e.field_78808_h;
/* 206 */     this.bipedRightArm.rotateAngleX = model.field_178723_h.field_78795_f;
/* 207 */     this.bipedRightArm.rotateAngleY = model.field_178723_h.field_78796_g;
/* 208 */     this.bipedRightArm.rotateAngleZ = model.field_178723_h.field_78808_h;
/* 209 */     this.bipedLeftArm.rotateAngleX = model.field_178724_i.field_78795_f;
/* 210 */     this.bipedLeftArm.rotateAngleY = model.field_178724_i.field_78796_g;
/* 211 */     this.bipedLeftArm.rotateAngleZ = model.field_178724_i.field_78808_h;
/* 212 */     this.bipedRightLeg.rotateAngleX = model.field_178721_j.field_78795_f;
/* 213 */     this.bipedRightLeg.rotateAngleY = model.field_178721_j.field_78796_g;
/* 214 */     this.bipedRightLeg.rotateAngleZ = model.field_178721_j.field_78808_h;
/* 215 */     this.bipedLeftLeg.rotateAngleX = model.field_178722_k.field_78795_f;
/* 216 */     this.bipedLeftLeg.rotateAngleY = model.field_178722_k.field_78796_g;
/* 217 */     this.bipedLeftLeg.rotateAngleZ = model.field_178722_k.field_78808_h;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\raycast\obb\ModelPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */