package org.spongepowered.asm.mixin.injection.selectors;

public interface ITargetSelectorByName extends ITargetSelector {
  String getOwner();
  
  String getName();
  
  String getDesc();
  
  boolean isFullyQualified();
  
  boolean isField();
  
  boolean isConstructor();
  
  boolean isClassInitialiser();
  
  boolean isInitialiser();
  
  String toDescriptor();
  
  MatchResult matches(String paramString1, String paramString2, String paramString3);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\selectors\ITargetSelectorByName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */