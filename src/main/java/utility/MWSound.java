/*    */ package com.modularwarfare.utility;
/*    */ 
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class MWSound
/*    */ {
/*    */   public BlockPos blockPos;
/*    */   public String soundName;
/*    */   public float volume;
/*    */   public float pitch;
/*    */   
/*    */   public MWSound(BlockPos blockPos, String sound, float volume, float pitch) {
/* 13 */     this.blockPos = blockPos;
/* 14 */     this.soundName = sound;
/* 15 */     this.volume = volume;
/* 16 */     this.pitch = pitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfar\\utility\MWSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */