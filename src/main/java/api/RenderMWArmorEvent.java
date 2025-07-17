/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.client.model.ModelCustomArmor;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class RenderMWArmorEvent
/*    */   extends Event
/*    */ {
/*    */   public final ModelCustomArmor modelCustomArmor;
/*    */   public final ModelCustomArmor.Bones.BonePart.EnumBoneType type;
/*    */   public final float scale;
/*    */   
/*    */   public RenderMWArmorEvent(ModelCustomArmor modelCustomArmor, ModelCustomArmor.Bones.BonePart.EnumBoneType type, float scale) {
/* 15 */     this.modelCustomArmor = modelCustomArmor;
/* 16 */     this.type = type;
/* 17 */     this.scale = scale;
/*    */   }
/*    */   
/*    */   public static class Pre
/*    */     extends RenderMWArmorEvent {
/*    */     public Pre(ModelCustomArmor modelCustomArmor, ModelCustomArmor.Bones.BonePart.EnumBoneType type, float scale) {
/* 23 */       super(modelCustomArmor, type, scale);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Post
/*    */     extends RenderMWArmorEvent
/*    */   {
/*    */     public Post(ModelCustomArmor modelCustomArmor, ModelCustomArmor.Bones.BonePart.EnumBoneType type, float scale) {
/* 32 */       super(modelCustomArmor, type, scale);
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
/* 45 */       this.bones = bones;
/* 46 */       this.limbSwing = limbSwing;
/* 47 */       this.limbSwingAmount = limbSwingAmount;
/* 48 */       this.ageInTicks = ageInTicks;
/* 49 */       this.netHeadYaw = netHeadYaw;
/* 50 */       this.headPitch = headPitch;
/* 51 */       this.scaleFactor = scaleFactor;
/* 52 */       this.entityIn = entityIn;
/*    */     }
/*    */     
/*    */     public float headPitch;
/*    */     public float scaleFactor;
/*    */     public Entity entityIn;
/*    */     public ModelCustomArmor.Bones bones;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\RenderMWArmorEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */