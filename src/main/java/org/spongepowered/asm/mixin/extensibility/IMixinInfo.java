package org.spongepowered.asm.mixin.extensibility;

import java.util.List;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;

public interface IMixinInfo {
  IMixinConfig getConfig();
  
  String getName();
  
  String getClassName();
  
  String getClassRef();
  
  byte[] getClassBytes();
  
  boolean isDetachedSuper();
  
  ClassNode getClassNode(int paramInt);
  
  List<String> getTargetClasses();
  
  int getPriority();
  
  MixinEnvironment.Phase getPhase();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\extensibility\IMixinInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */