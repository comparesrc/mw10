package org.spongepowered.asm.mixin.extensibility;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;

public interface IMixinConfigPlugin {
  void onLoad(String paramString);
  
  String getRefMapperConfig();
  
  boolean shouldApplyMixin(String paramString1, String paramString2);
  
  void acceptTargets(Set<String> paramSet1, Set<String> paramSet2);
  
  List<String> getMixins();
  
  void preApply(String paramString1, ClassNode paramClassNode, String paramString2, IMixinInfo paramIMixinInfo);
  
  void postApply(String paramString1, ClassNode paramClassNode, String paramString2, IMixinInfo paramIMixinInfo);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\extensibility\IMixinConfigPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */