package org.spongepowered.asm.service;

import java.io.InputStream;
import java.util.Collection;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.util.ReEntranceLock;

public interface IMixinService {
  String getName();
  
  boolean isValid();
  
  void prepare();
  
  MixinEnvironment.Phase getInitialPhase();
  
  void init();
  
  void beginPhase();
  
  void checkEnv(Object paramObject);
  
  ReEntranceLock getReEntranceLock();
  
  IClassProvider getClassProvider();
  
  IClassBytecodeProvider getBytecodeProvider();
  
  ITransformerProvider getTransformerProvider();
  
  IClassTracker getClassTracker();
  
  IMixinAuditTrail getAuditTrail();
  
  Collection<String> getPlatformAgents();
  
  IContainerHandle getPrimaryContainer();
  
  Collection<IContainerHandle> getMixinContainers();
  
  InputStream getResourceAsStream(String paramString);
  
  String getSideName();
  
  MixinEnvironment.CompatibilityLevel getMinCompatibilityLevel();
  
  MixinEnvironment.CompatibilityLevel getMaxCompatibilityLevel();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\IMixinService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */