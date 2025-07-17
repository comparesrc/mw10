package org.spongepowered.asm.mixin.transformer;

import java.util.List;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry;

public interface IMixinTransformer {
  void audit(MixinEnvironment paramMixinEnvironment);
  
  List<String> reload(String paramString, ClassNode paramClassNode);
  
  byte[] transformClassBytes(String paramString1, String paramString2, byte[] paramArrayOfbyte);
  
  IExtensionRegistry getExtensions();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\IMixinTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */