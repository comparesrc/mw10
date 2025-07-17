/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.modularwarfare.common.type.BaseItem;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemAttachment
/*    */   extends BaseItem {
/*    */   static {
/* 14 */     factory = (type -> new ItemAttachment(type));
/*    */   }
/*    */   public static final Function<AttachmentType, ItemAttachment> factory;
/*    */   public AttachmentType type;
/*    */   
/*    */   public ItemAttachment(AttachmentType type) {
/* 20 */     super(type);
/* 21 */     this.type = type;
/* 22 */     this.render3d = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_77663_a(ItemStack unused, World world, Entity holdingEntity, int intI, boolean flag) {
/* 27 */     if (holdingEntity instanceof EntityPlayer) {
/* 28 */       EntityPlayer entityPlayer = (EntityPlayer)holdingEntity;
/*    */       
/* 30 */       if (unused != null && unused.func_77973_b() instanceof ItemAttachment && 
/* 31 */         unused.func_77978_p() == null) {
/* 32 */         NBTTagCompound nbtTagCompound = new NBTTagCompound();
/* 33 */         nbtTagCompound.func_74768_a("skinId", 1);
/* 34 */         unused.func_77982_d(nbtTagCompound);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_77651_p() {
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\guns\ItemAttachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */