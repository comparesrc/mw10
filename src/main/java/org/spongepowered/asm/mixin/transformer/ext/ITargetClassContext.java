package org.spongepowered.asm.mixin.transformer.ext;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

public interface ITargetClassContext {
  ClassInfo getClassInfo();
  
  ClassNode getClassNode();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\ext\ITargetClassContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */