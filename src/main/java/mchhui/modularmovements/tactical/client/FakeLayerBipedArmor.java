/*    */ package mchhui.modularmovements.tactical.client;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class FakeLayerBipedArmor extends LayerBipedArmor {
/*    */   public FakeLayerBipedArmor(RenderLivingBase<?> rendererIn) {
/* 12 */     super(rendererIn);
/* 13 */     this.renderer = rendererIn;
/*    */   }
/*    */   
/*    */   private final RenderLivingBase<?> renderer;
/*    */   
/*    */   protected void func_177177_a() {
/* 19 */     this.field_177189_c = (ModelBase)new FakeModelBiped(0.5F);
/* 20 */     this.field_177186_d = (ModelBase)new FakeModelBiped(1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\tactical\client\FakeLayerBipedArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */