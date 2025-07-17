package org.spongepowered.asm.launch.platform;

import java.util.Collection;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.util.IConsumer;

public interface IMixinPlatformServiceAgent extends IMixinPlatformAgent {
  void init();
  
  String getSideName();
  
  Collection<IContainerHandle> getMixinContainers();
  
  @Deprecated
  void wire(MixinEnvironment.Phase paramPhase, IConsumer<MixinEnvironment.Phase> paramIConsumer);
  
  @Deprecated
  void unwire();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\IMixinPlatformServiceAgent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */