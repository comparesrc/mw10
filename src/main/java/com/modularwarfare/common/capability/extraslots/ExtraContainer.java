/*    */ package com.modularwarfare.common.capability.extraslots;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.common.network.PacketBase;
/*    */ import com.modularwarfare.common.network.PacketSyncExtraSlot;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.items.ItemStackHandler;
/*    */ 
/*    */ public class ExtraContainer extends ItemStackHandler implements IExtraItemHandler {
/*    */   public ExtraContainer(EntityPlayer player) {
/* 14 */     super(5);
/* 15 */     this.player = player;
/*    */   }
/*    */   private EntityPlayer player;
/*    */   public ExtraContainer() {
/* 19 */     super(5);
/*    */   }
/*    */   
/*    */   public void setPlayer(EntityPlayer player) {
/* 23 */     this.player = player;
/*    */   }
/*    */   
/*    */   protected void onContentsChanged(int slot) {
/* 27 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
/* 28 */       for (int i = 0; i < getSlots(); i++)
/* 29 */         ModularWarfare.NETWORK.sendToAllTracking((PacketBase)new PacketSyncExtraSlot(this.player, i, getStackInSlot(i)), (Entity)this.player);  
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\capability\extraslots\ExtraContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */