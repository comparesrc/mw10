package org.spongepowered.asm.launch.platform.container;

import java.util.Collection;

public interface IContainerHandle {
  String getAttribute(String paramString);
  
  Collection<IContainerHandle> getNestedContainers();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\container\IContainerHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */