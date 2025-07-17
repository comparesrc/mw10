/*    */ package mchhui.modularmovements.coremod;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class ModularMovementsPlugin
/*    */ {
/*    */   public String[] getASMTransformerClass() {
/* 14 */     return new String[] { "mchhui.modularmovements.coremod.minecraft.EntityPlayerSP", "mchhui.modularmovements.coremod.minecraft.Entity" };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getModContainerClass() {
/* 20 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSetupClass() {
/* 26 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void injectData(Map<String, Object> data) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getAccessTransformerClass() {
/* 38 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\coremod\ModularMovementsPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */