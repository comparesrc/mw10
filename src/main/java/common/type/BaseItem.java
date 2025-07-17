/*    */ package com.modularwarfare.common.type;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.script.ScriptHost;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.text.TextFormatting;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class BaseItem
/*    */   extends Item
/*    */ {
/*    */   public BaseType baseType;
/*    */   public boolean render3d = true;
/*    */   public ResourceLocation tooltipScript;
/*    */   
/*    */   public BaseItem(BaseType type) {
/* 24 */     func_77655_b(type.internalName);
/* 25 */     setRegistryName(type.internalName);
/* 26 */     func_77637_a((CreativeTabs)ModularWarfare.MODS_TABS.get(type.contentPack));
/*    */     
/* 28 */     this.baseType = type;
/* 29 */     if (type.maxStackSize != null) {
/* 30 */       func_77625_d(type.maxStackSize.intValue());
/*    */     } else {
/* 32 */       func_77625_d(1);
/*    */     } 
/* 34 */     this.canRepair = false;
/* 35 */     this.tooltipScript = new ResourceLocation("modularwarfare", "script/" + this.baseType.toolipScript + ".js");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setType(BaseType type) {}
/*    */ 
/*    */   
/*    */   public String generateLoreLine(String prefix, String value) {
/* 43 */     String baseDisplayLine = "%b%s: %g%s";
/* 44 */     baseDisplayLine = baseDisplayLine.replaceAll("%b", TextFormatting.BLUE.toString());
/* 45 */     baseDisplayLine = baseDisplayLine.replaceAll("%g", TextFormatting.GRAY.toString());
/* 46 */     return String.format(baseDisplayLine, new Object[] { prefix, value });
/*    */   }
/*    */   
/*    */   public String generateLoreHeader(String prefix) {
/* 50 */     String baseDisplayLine = "%b%s";
/* 51 */     baseDisplayLine = baseDisplayLine.replaceAll("%b", TextFormatting.BLUE.toString());
/* 52 */     return String.format(baseDisplayLine, new Object[] { prefix });
/*    */   }
/*    */   
/*    */   public String generateLoreListEntry(String prefix, String value) {
/* 56 */     String baseDisplayLine = " - %s %g%s";
/* 57 */     baseDisplayLine = baseDisplayLine.replaceAll("%b", TextFormatting.BLUE.toString());
/* 58 */     baseDisplayLine = baseDisplayLine.replaceAll("%g", TextFormatting.GRAY.toString());
/* 59 */     return String.format(baseDisplayLine, new Object[] { value, prefix });
/*    */   }
/*    */   
/*    */   public String generateLoreLineAlt(String prefix, String current, String max) {
/* 63 */     String baseDisplayLine = "%b%s: %g%s%dg/%g%s";
/* 64 */     baseDisplayLine = baseDisplayLine.replaceAll("%b", TextFormatting.BLUE.toString());
/* 65 */     baseDisplayLine = baseDisplayLine.replaceAll("%g", TextFormatting.GRAY.toString());
/* 66 */     baseDisplayLine = baseDisplayLine.replaceAll("%dg", TextFormatting.DARK_GRAY.toString());
/* 67 */     return String.format(baseDisplayLine, new Object[] { prefix, current, max });
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
/* 73 */     if (this.tooltipScript != null)
/* 74 */       ScriptHost.INSTANCE.callScript(this.tooltipScript, stack, tooltip, "updateTooltip"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\type\BaseItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */