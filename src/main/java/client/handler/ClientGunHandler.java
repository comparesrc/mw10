package com.modularwarfare.client.handler;

import com.modularwarfare.api.WeaponFireEvent;
import com.modularwarfare.api.WeaponReloadEvent;
import com.modularwarfare.utility.event.ForgeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientGunHandler extends ForgeEvent {
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void onWeaponFire(WeaponFireEvent.Post event) {}
  
  @SubscribeEvent
  public void onWeaponReload(WeaponReloadEvent.Pre event) {}
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\handler\ClientGunHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */