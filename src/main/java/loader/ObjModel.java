/*     */ package com.modularwarfare.loader;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.loader.api.model.AbstractObjModel;
/*     */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.List;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class ObjModel
/*     */   extends AbstractObjModel
/*     */ {
/*     */   public List<ObjModelRenderer> parts;
/*  16 */   public float modelScale = 1.0F;
/*  17 */   private List<ObjModelRenderer> duplications = new ArrayList<>();
/*     */   
/*     */   public ObjModel(List<ObjModelRenderer> parts) {
/*  20 */     this.parts = parts;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjModel() {}
/*     */ 
/*     */   
/*     */   public List<ObjModelRenderer> getParts() {
/*  28 */     return this.parts;
/*     */   }
/*     */   
/*     */   void setParts(List<ObjModelRenderer> renderers) {
/*  32 */     this.parts = renderers;
/*     */   }
/*     */   
/*     */   public ObjModelRenderer getPart(String name) {
/*  36 */     for (ObjModelRenderer part : this.parts) {
/*  37 */       if (name.equals(part.getName())) {
/*  38 */         return part;
/*     */       }
/*     */     } 
/*  41 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderAll(float scale) {
/*  47 */     checkForNoDuplications();
/*  48 */     for (ObjModelRenderer part : this.parts) {
/*  49 */       part.render(scale);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearDuplications() throws ConcurrentModificationException {
/*     */     try {
/*  65 */       for (ObjModelRenderer renderer : this.duplications) {
/*  66 */         this.parts.remove(renderer);
/*     */       }
/*  68 */     } catch (ConcurrentModificationException e) {
/*  69 */       throw new ConcurrentModificationException("You must clear duplications ONLY AFTER passing ObjModelRaw#parts!!!\n" + e.getMessage());
/*     */     } 
/*     */     
/*  72 */     this.duplications.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasDuplications() {
/*  77 */     return !this.duplications.isEmpty();
/*     */   }
/*     */   
/*     */   private String[] formDuplicationList() {
/*  81 */     String[] list = new String[this.duplications.size()];
/*  82 */     for (int i = 0; i < this.duplications.size(); i++) {
/*  83 */       list[i] = ((ObjModelRenderer)this.duplications.get(i)).getName();
/*     */     }
/*     */     
/*  86 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderOnly(float scale, String... groupNames) {
/*  92 */     checkForNoDuplications();
/*  93 */     for (ObjModelRenderer part : this.parts) {
/*  94 */       for (String groupName : groupNames) {
/*  95 */         if (groupName.contains(part.getName())) {
/*  96 */           part.render(scale);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderOnly(float scale, ObjModelRenderer... partsIn) {
/* 105 */     checkForNoDuplications();
/* 106 */     for (ObjModelRenderer part : this.parts) {
/* 107 */       for (ObjModelRenderer partIn : partsIn) {
/* 108 */         if (part.equals(partIn)) {
/* 109 */           part.render(scale);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderPart(float scale, String partName) {
/* 118 */     checkForNoDuplications();
/* 119 */     for (ObjModelRenderer part : this.parts) {
/* 120 */       if (partName.contains(part.getName())) {
/* 121 */         part.render(scale);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderPart(float scale, ObjModelRenderer partIn) {
/* 129 */     checkForNoDuplications();
/* 130 */     for (ObjModelRenderer part : this.parts) {
/* 131 */       if (part.equals(partIn)) {
/* 132 */         part.render(scale);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderAllExcept(float scale, ObjModelRenderer... excludedPartsIn) {
/* 143 */     checkForNoDuplications();
/* 144 */     for (ObjModelRenderer part : this.parts) {
/* 145 */       boolean skipPart = isExcepted(part, excludedPartsIn);
/*     */       
/* 147 */       if (!skipPart) {
/* 148 */         part.render(scale);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isExcepted(ObjModelRenderer part, ObjModelRenderer[] excludedList) {
/* 154 */     for (ObjModelRenderer excludedPart : excludedList) {
/* 155 */       if (part.equals(excludedPart)) {
/* 156 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 160 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDuplication(ObjModelRenderer renderer) {
/* 165 */     this.duplications.add(renderer);
/*     */   }
/*     */   
/*     */   private void checkForNoDuplications() {
/* 169 */     if (hasDuplications()) {
/* 170 */       ModularWarfare.LOGGER.error("=============================================================");
/* 171 */       ModularWarfare.LOGGER.error("Duplications were found! You must call method ObjModelRaw#clearDuplications() after adding children to renders.");
/* 172 */       ModularWarfare.LOGGER.error("Duplications:");
/*     */       
/* 174 */       for (String str : formDuplicationList()) {
/* 175 */         ModularWarfare.LOGGER.error(str);
/*     */       }
/*     */       
/* 178 */       ModularWarfare.LOGGER.error("=============================================================");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\loader\ObjModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */