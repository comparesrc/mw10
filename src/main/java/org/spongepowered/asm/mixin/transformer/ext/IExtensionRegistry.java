package org.spongepowered.asm.mixin.transformer.ext;

import java.util.List;
import org.spongepowered.asm.service.ISyntheticClassRegistry;

public interface IExtensionRegistry {
  List<IExtension> getExtensions();
  
  List<IExtension> getActiveExtensions();
  
  <T extends IExtension> T getExtension(Class<? extends IExtension> paramClass);
  
  ISyntheticClassRegistry getSyntheticClassRegistry();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\ext\IExtensionRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */