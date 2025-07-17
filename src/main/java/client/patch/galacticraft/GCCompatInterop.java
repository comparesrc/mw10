package com.modularwarfare.client.patch.galacticraft;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;

public interface GCCompatInterop {
  boolean isModLoaded();
  
  boolean isFixApplied();
  
  void setFixed();
  
  void addLayers(RenderPlayer paramRenderPlayer);
  
  boolean isGCLayer(LayerRenderer<EntityPlayer> paramLayerRenderer);
  
  void applyFix();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\patch\galacticraft\GCCompatInterop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */