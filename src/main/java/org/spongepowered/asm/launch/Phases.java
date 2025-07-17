/*    */ package org.spongepowered.asm.launch;
/*    */ 
/*    */ import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
/*    */ import java.util.EnumSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Phases
/*    */ {
/* 39 */   public static final EnumSet<ILaunchPluginService.Phase> NONE = EnumSet.noneOf(ILaunchPluginService.Phase.class);
/* 40 */   public static final EnumSet<ILaunchPluginService.Phase> BEFORE_ONLY = EnumSet.of(ILaunchPluginService.Phase.BEFORE);
/* 41 */   public static final EnumSet<ILaunchPluginService.Phase> AFTER_ONLY = EnumSet.of(ILaunchPluginService.Phase.AFTER);
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\Phases.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */