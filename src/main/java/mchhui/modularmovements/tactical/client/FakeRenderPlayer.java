/*    */ package mchhui.modularmovements.tactical.client;
/*    */ import com.modularwarfare.client.model.layers.RenderLayerBackpack;
/*    */ import com.modularwarfare.client.model.layers.RenderLayerBody;
/*    */ import com.modularwarfare.client.model.layers.RenderLayerHeldGun;
/*    */ import com.modularwarfare.client.model.layers.ResetHiddenModelLayer;
/*    */ import mchhui.modularmovements.ModularMovements;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.MWFRenderHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class FakeRenderPlayer extends RenderPlayer {
/*    */   public FakeRenderPlayer(RenderManager renderManager, boolean useSmallArms) {
/* 22 */     super(renderManager, useSmallArms);
/* 23 */     this.field_77045_g = (ModelBase)new FakePlayerModel(0.0F, useSmallArms);
/* 24 */     for (int i = 0; i < this.field_177097_h.size(); i++) {
/* 25 */       if (((LayerRenderer)this.field_177097_h.get(i)).getClass() == LayerBipedArmor.class) {
/*    */         
/* 27 */         this.field_177097_h.remove(i);
/* 28 */         i--;
/*    */         break;
/*    */       } 
/*    */     } 
/* 32 */     if (ModularMovements.mwfEnable) {
/*    */       
/* 34 */       MWFRenderHelper helper = new MWFRenderHelper((RenderLivingBase)this);
/* 35 */       func_177094_a((LayerRenderer)new FakeLayerBipedArmor((RenderLivingBase<?>)this));
/* 36 */       helper.getLayerRenderers().add(0, new ResetHiddenModelLayer(this));
/* 37 */       func_177094_a((LayerRenderer)new RenderLayerBackpack(this, (func_177087_b()).field_178730_v));
/* 38 */       func_177094_a((LayerRenderer)new RenderLayerBody(this, (func_177087_b()).field_178730_v));
/* 39 */       func_177094_a((LayerRenderer)new RenderLayerHeldGun((RenderLivingBase)this));
/*    */     } 
/*    */   }
/*    */   
/*    */   public FakeRenderPlayer(RenderManager renderManager) {
/* 44 */     this(renderManager, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_77043_a(AbstractClientPlayer entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
/* 49 */     if (ClientLitener.applyRotations((RenderLivingBase)this, (EntityLivingBase)entityLiving, p_77043_2_, rotationYaw, partialTicks)) {
/*    */       return;
/*    */     }
/* 52 */     super.func_77043_a(entityLiving, p_77043_2_, rotationYaw, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\tactical\client\FakeRenderPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */