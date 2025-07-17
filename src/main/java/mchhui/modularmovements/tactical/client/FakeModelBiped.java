/*    */ package mchhui.modularmovements.tactical.client;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class FakeModelBiped extends ModelBiped {
/*    */   public FakeModelBiped() {
/* 11 */     super(0.0F);
/*    */   }
/*    */   
/*    */   public FakeModelBiped(float modelSize) {
/* 15 */     super(modelSize, 0.0F, 64, 32);
/*    */   }
/*    */   
/*    */   public FakeModelBiped(float modelSize, float p_i1149_2_, int textureWidthIn, int textureHeightIn) {
/* 19 */     super(modelSize, p_i1149_2_, textureWidthIn, textureHeightIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 26 */     super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 27 */     ClientLitener.setRotationAngles(this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\tactical\client\FakeModelBiped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */