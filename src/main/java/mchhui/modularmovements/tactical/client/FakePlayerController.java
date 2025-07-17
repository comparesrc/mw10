/*    */ package mchhui.modularmovements.tactical.client;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class FakePlayerController
/*    */   extends PlayerControllerMP {
/*    */   public FakePlayerController(Minecraft mcIn, NetHandlerPlayClient netHandler) {
/* 13 */     super(mcIn, netHandler);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\tactical\client\FakePlayerController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */