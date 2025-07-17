/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import java.util.HashMap;
/*    */ import org.apache.logging.log4j.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeaponAnimations
/*    */ {
/* 11 */   public static String RIFLE = "rifle";
/* 12 */   public static String RIFLE2 = "rifle2";
/* 13 */   public static String RIFLE3 = "rifle3";
/* 14 */   public static String RIFLE4 = "rifle4";
/* 15 */   public static String PISTOL = "pistol";
/* 16 */   public static String SHOTGUN = "shotgun";
/* 17 */   public static String SNIPER = "sniper";
/* 18 */   public static String SNIPER_TOP = "sniper_top";
/* 19 */   public static String SIDE_CLIP = "sideclip";
/* 20 */   public static String TOP_RIFLE = "toprifle";
/* 21 */   private static HashMap<String, WeaponAnimation> animationMap = new HashMap<>();
/*    */   
/*    */   public static String registerAnimation(String internalName, WeaponAnimation animation) {
/* 24 */     animationMap.put(internalName, animation);
/* 25 */     return internalName;
/*    */   }
/*    */   
/*    */   public static WeaponAnimation getAnimation(String internalName) {
/* 29 */     WeaponAnimation weaponAnimation = animationMap.get(internalName);
/* 30 */     if (weaponAnimation == null)
/* 31 */       ModularWarfare.LOGGER.log(Level.ERROR, String.format("Animation named '%s' does not exist in animation registry.", new Object[] { internalName })); 
/* 32 */     return animationMap.get(internalName);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\WeaponAnimations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */