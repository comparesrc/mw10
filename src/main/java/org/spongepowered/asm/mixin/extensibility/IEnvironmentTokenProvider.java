package org.spongepowered.asm.mixin.extensibility;

import org.spongepowered.asm.mixin.MixinEnvironment;

public interface IEnvironmentTokenProvider {
  public static final int DEFAULT_PRIORITY = 1000;
  
  int getPriority();
  
  Integer getToken(String paramString, MixinEnvironment paramMixinEnvironment);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\extensibility\IEnvironmentTokenProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */