/*    */ package mchhui.modularmovements.tactical.client;
/*    */ 
/*    */ import net.minecraft.client.model.ModelPlayer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class FakePlayerModel
/*    */   extends ModelPlayer {
/*    */   public FakePlayerModel(float modelSize, boolean smallArmsIn) {
/* 12 */     super(modelSize, smallArmsIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 19 */     super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 20 */     ClientLitener.setRotationAngles(this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\tactical\client\FakePlayerModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */