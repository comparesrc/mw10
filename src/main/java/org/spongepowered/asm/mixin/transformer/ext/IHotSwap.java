package org.spongepowered.asm.mixin.transformer.ext;

import org.objectweb.asm.tree.ClassNode;

public interface IHotSwap {
  void registerMixinClass(String paramString);
  
  void registerTargetClass(String paramString, ClassNode paramClassNode);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\ext\IHotSwap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */