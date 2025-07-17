package com.modularwarfare.addon;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public interface IContentAddon {
  void construct(Side paramSide, AddonLoaderManager paramAddonLoaderManager);
  
  void preInit(FMLPreInitializationEvent paramFMLPreInitializationEvent, AddonLoaderManager paramAddonLoaderManager);
  
  void init(FMLInitializationEvent paramFMLInitializationEvent, AddonLoaderManager paramAddonLoaderManager);
  
  void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent, AddonLoaderManager paramAddonLoaderManager);
  
  void unload();
  
  String getName();
  
  String getVersion();
  
  String getAddonID();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\addon\IContentAddon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */