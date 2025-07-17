/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.client.model.ModelCustomArmor;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class RenderBonesEvent
/*    */   extends Event
/*    */ {
/*    */   public final ModelCustomArmor modelCustomArmor;
/*    */   public final ModelCustomArmor.Bones.BonePart.EnumBoneType type;
/*    */   public final float scale;
/*    */   
/*    */   public RenderBonesEvent(ModelCustomArmor modelCustomArmor, ModelCustomArmor.Bones.BonePart.EnumBoneType type, float scale) {
/* 21 */     this.modelCustomArmor = modelCustomArmor;
/* 22 */     this.type = type;
/* 23 */     this.scale = scale;
/*    */   }
/*    */   
/*    */   public static class Pre
/*    */     extends RenderBonesEvent {
/*    */     public Pre(ModelCustomArmor modelCustomArmor, ModelCustomArmor.Bones.BonePart.EnumBoneType type, float scale) {
/* 29 */       super(modelCustomArmor, type, scale);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Post
/*    */     extends RenderBonesEvent
/*    */   {
/*    */     public Post(ModelCustomArmor modelCustomArmor, ModelCustomArmor.Bones.BonePart.EnumBoneType type, float scale) {
/* 38 */       super(modelCustomArmor, type, scale);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class RotationAngles
/*    */     extends Event
/*    */   {
/*    */     public float limbSwing;
/*    */     public float limbSwingAmount;
/*    */     public float ageInTicks;
/*    */     public float netHeadYaw;
/*    */     
/*    */     public RotationAngles(ModelCustomArmor.Bones bones, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 51 */       this.bones = bones;
/* 52 */       this.limbSwing = limbSwing;
/* 53 */       this.limbSwingAmount = limbSwingAmount;
/* 54 */       this.ageInTicks = ageInTicks;
/* 55 */       this.netHeadYaw = netHeadYaw;
/* 56 */       this.headPitch = headPitch;
/* 57 */       this.scaleFactor = scaleFactor;
/* 58 */       this.entityIn = entityIn;
/*    */     }
/*    */     
/*    */     public float headPitch;
/*    */     public float scaleFactor;
/*    */     public Entity entityIn;
/*    */     public ModelCustomArmor.Bones bones;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\RenderBonesEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */