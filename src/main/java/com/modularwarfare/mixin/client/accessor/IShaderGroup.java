package com.modularwarfare.mixin.client.accessor;

import java.util.List;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ShaderGroup.class})
public interface IShaderGroup {
  @Accessor("listShaders")
  List<Shader> getListShaders();
  
  @Accessor("mainFramebuffer")
  Framebuffer getMainFramebuffer();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\mixin\client\accessor\IShaderGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */