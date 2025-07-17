/*    */ package com.modularwarfare.common.armor;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.api.MWArmorModel;
/*    */ import com.modularwarfare.api.MWArmorType;
/*    */ import com.modularwarfare.client.fpp.basic.configs.ArmorRenderConfig;
/*    */ import com.modularwarfare.client.model.ModelCustomArmor;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import com.modularwarfare.loader.MWModelBipedBase;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ public class ArmorType
/*    */   extends BaseType
/*    */ {
/*    */   public Integer durability;
/*    */   public double defense;
/*    */   @Deprecated
/*    */   public boolean simpleArmor = false;
/*    */   public HashMap<MWArmorType, ArmorInfo> armorTypes;
/*    */   public transient ArmorRenderConfig renderConfig;
/*    */   
/*    */   public ArmorType() {
/* 27 */     this.armorTypes = new HashMap<>();
/*    */   }
/*    */   
/*    */   public void initializeArmor(String slot) {
/* 31 */     for (MWArmorType armorType : this.armorTypes.keySet()) {
/* 32 */       if (armorType.name().toLowerCase().equalsIgnoreCase(slot)) {
/* 33 */         ((ArmorInfo)this.armorTypes.get(armorType)).internalName = this.internalName + ((this.armorTypes.size() > 1) ? ("_" + slot) : "");
/*    */       }
/* 35 */       if (((ArmorInfo)this.armorTypes.get(armorType)).armorModels != null) {
/* 36 */         for (MWArmorModel model : MWArmorModel.values()) {
/* 37 */           if (((ArmorInfo)this.armorTypes.get(armorType)).armorModels.contains(model)) {
/* 38 */             ((ArmorInfo)this.armorTypes.get(armorType)).showArmorModels.put(model, Boolean.valueOf(true));
/*    */           } else {
/* 40 */             ((ArmorInfo)this.armorTypes.get(armorType)).showArmorModels.put(model, Boolean.valueOf(false));
/*    */           } 
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadExtraValues() {
/* 49 */     if (this.maxStackSize == null) {
/* 50 */       this.maxStackSize = Integer.valueOf(1);
/*    */     }
/* 52 */     loadBaseValues();
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void reloadModel() {
/* 58 */     this.renderConfig = (ArmorRenderConfig)ModularWarfare.getRenderConfig(this, ArmorRenderConfig.class);
/* 59 */     if (!this.simpleArmor) {
/* 60 */       this.bipedModel = (MWModelBipedBase)new ModelCustomArmor(this.renderConfig, this);
/*    */     }
/*    */   }
/*    */   
/*    */   public String getAssetDir() {
/* 65 */     return "armor";
/*    */   }
/*    */   
/*    */   public static class ArmorInfo
/*    */   {
/*    */     public String displayName;
/*    */     public ArrayList<MWArmorModel> armorModels;
/* 72 */     public transient HashMap<MWArmorModel, Boolean> showArmorModels = new HashMap<>();
/*    */     public transient String internalName;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\armor\ArmorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */