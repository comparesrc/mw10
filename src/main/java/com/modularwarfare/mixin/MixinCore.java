/*    */ package com.modularwarfare.mixin;
/*    */ 
/*    */ import com.modularwarfare.core.net.minecraft.entity.player.EntityLivingBase;
/*    */ import com.modularwarfare.core.net.optifine.shaders.ShadersRender;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
/*    */ import org.spongepowered.asm.launch.MixinBootstrap;
/*    */ import org.spongepowered.asm.mixin.Mixins;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @MCVersion("1.12.2")
/*    */ @SortingIndex(1001)
/*    */ @Name("modularwarfare")
/*    */ public class MixinCore
/*    */   implements IFMLLoadingPlugin
/*    */ {
/*    */   public String[] getASMTransformerClass() {
/* 25 */     ArrayList<String> arrayList = new ArrayList<>();
/* 26 */     arrayList.add(ShadersRender.class.getName());
/* 27 */     arrayList.add(EntityLivingBase.class.getName());
/*    */     try {
/* 29 */       if (Class.forName("mchhui.modularmovements.coremod.ModularMovementsPlugin") != null) {
/* 30 */         arrayList.add("mchhui.modularmovements.coremod.minecraft.EntityPlayerSP");
/* 31 */         arrayList.add("mchhui.modularmovements.coremod.minecraft.Entity");
/*    */       } 
/* 33 */     } catch (ClassNotFoundException e) {
/*    */       
/* 35 */       e.printStackTrace();
/*    */     } 
/* 37 */     return arrayList.<String>toArray(new String[arrayList.size()]);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModContainerClass() {
/* 42 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getSetupClass() {
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void injectData(Map<String, Object> data) {
/* 53 */     MixinBootstrap.init();
/* 54 */     Mixins.addConfiguration("mixins.modularwarfare.json");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAccessTransformerClass() {
/* 59 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\mixin\MixinCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */