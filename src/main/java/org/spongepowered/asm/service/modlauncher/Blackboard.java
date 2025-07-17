/*     */ package org.spongepowered.asm.service.modlauncher;
/*     */ 
/*     */ import cpw.mods.modlauncher.Launcher;
/*     */ import cpw.mods.modlauncher.api.TypesafeMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Blackboard
/*     */   implements IGlobalPropertyService
/*     */ {
/*     */   class Key<V>
/*     */     implements IPropertyKey
/*     */   {
/*     */     final TypesafeMap.Key<V> key;
/*     */     
/*     */     public Key(TypesafeMap owner, String name, Class<V> clazz) {
/*  51 */       this.key = TypesafeMap.Key.getOrCreate(owner, name, clazz);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  56 */   private final Map<String, IPropertyKey> keys = new HashMap<>();
/*     */   
/*     */   private final TypesafeMap blackboard;
/*     */   
/*     */   public Blackboard() {
/*  61 */     this.blackboard = Launcher.INSTANCE.blackboard();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IPropertyKey resolveKey(String name) {
/*  70 */     return this.keys.computeIfAbsent(name, key -> new Key(this.blackboard, key, Object.class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getProperty(IPropertyKey key) {
/*  79 */     return getProperty(key, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(IPropertyKey key, Object value) {
/*  89 */     this.blackboard.computeIfAbsent(((Key)key).key, k -> value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPropertyString(IPropertyKey key, String defaultValue) {
/*  99 */     return getProperty(key, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getProperty(IPropertyKey key, T defaultValue) {
/* 109 */     return this.blackboard.get(((Key)key).key).orElse(defaultValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\modlauncher\Blackboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */