/*     */ package org.spongepowered.asm.service.mojang;
/*     */ 
/*     */ import net.minecraft.launchwrapper.Launch;
/*     */ import org.spongepowered.asm.service.IGlobalPropertyService;
/*     */ import org.spongepowered.asm.service.IPropertyKey;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Blackboard
/*     */   implements IGlobalPropertyService
/*     */ {
/*     */   class Key
/*     */     implements IPropertyKey
/*     */   {
/*     */     private final String key;
/*     */     
/*     */     Key(String key) {
/*  45 */       this.key = key;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  50 */       return this.key;
/*     */     }
/*     */   }
/*     */   
/*     */   public Blackboard() {
/*  55 */     Launch.classLoader.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public IPropertyKey resolveKey(String name) {
/*  60 */     return new Key(name);
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
/*     */   public final <T> T getProperty(IPropertyKey key) {
/*  73 */     return (T)Launch.blackboard.get(key.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setProperty(IPropertyKey key, Object value) {
/*  84 */     Launch.blackboard.put(key.toString(), value);
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
/*     */   
/*     */   public final <T> T getProperty(IPropertyKey key, T defaultValue) {
/*  99 */     Object value = Launch.blackboard.get(key.toString());
/* 100 */     return (value != null) ? (T)value : defaultValue;
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
/*     */   public final String getPropertyString(IPropertyKey key, String defaultValue) {
/* 114 */     Object value = Launch.blackboard.get(key.toString());
/* 115 */     return (value != null) ? value.toString() : defaultValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\mojang\Blackboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */