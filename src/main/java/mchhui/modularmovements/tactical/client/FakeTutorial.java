/*    */ package mchhui.modularmovements.tactical.client;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.tutorial.Tutorial;
/*    */ import net.minecraft.util.MouseHelper;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class FakeTutorial
/*    */   extends Tutorial {
/*    */   public FakeTutorial(Minecraft minecraft) {
/* 13 */     super(minecraft);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_193299_a(MouseHelper mouseHelper) {
/* 19 */     super.func_193299_a(mouseHelper);
/* 20 */     ClientLitener.onMouseMove(mouseHelper);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\tactical\client\FakeTutorial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */