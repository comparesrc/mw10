package org.spongepowered.asm.service;

import java.util.Collection;

public interface ITransformerProvider {
  Collection<ITransformer> getTransformers();
  
  Collection<ITransformer> getDelegatedTransformers();
  
  void addTransformerExclusion(String paramString);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\ITransformerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */