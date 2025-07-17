package org.spongepowered.asm.launch;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import java.util.EnumSet;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public interface IClassProcessor {
  EnumSet<ILaunchPluginService.Phase> handlesClass(Type paramType, boolean paramBoolean, String paramString);
  
  boolean processClass(ILaunchPluginService.Phase paramPhase, ClassNode paramClassNode, Type paramType, String paramString);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\IClassProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */