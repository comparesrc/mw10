package org.spongepowered.asm.mixin.injection.selectors;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.asm.ElementNode;

public interface ITargetSelector {
  ITargetSelector next();
  
  ITargetSelector configure(String... paramVarArgs);
  
  ITargetSelector validate() throws InvalidSelectorException;
  
  ITargetSelector attach(IMixinContext paramIMixinContext) throws InvalidSelectorException;
  
  int getMatchCount();
  
  <TNode> MatchResult match(ElementNode<TNode> paramElementNode);
  
  MatchResult match(AbstractInsnNode paramAbstractInsnNode);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\selectors\ITargetSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */