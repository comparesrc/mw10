/*    */ package com.modularwarfare.utility;
/*    */ 
/*    */ import com.modularwarfare.ModConfig;
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EntityDamageSource;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.util.text.translation.I18n;
/*    */ 
/*    */ public class ModularDamageSource
/*    */   extends EntityDamageSource
/*    */ {
/*    */   public ItemGun gun;
/*    */   public boolean isHeadshot;
/*    */   
/*    */   public ModularDamageSource(String damageTypeIn, @Nullable Entity damageSourceEntityIn, ItemGun gun, boolean isHeadshot) {
/* 21 */     super(damageTypeIn, damageSourceEntityIn);
/* 22 */     this.gun = gun;
/* 23 */     this.isHeadshot = isHeadshot;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ITextComponent func_151519_b(EntityLivingBase entityLivingBaseIn) {
/* 30 */     if (!ModConfig.INSTANCE.killFeed.sendDefaultKillMessage)
/* 31 */       return null; 
/* 32 */     ItemStack itemstack = (this.field_76386_o instanceof EntityLivingBase) ? ((EntityLivingBase)this.field_76386_o).func_184614_ca() : ItemStack.field_190927_a;
/* 33 */     String s = "death.attack." + this.field_76373_n;
/* 34 */     String s1 = s + ".item";
/* 35 */     return (!itemstack.func_190926_b() && itemstack.func_82837_s() && I18n.func_94522_b(s1)) ? (ITextComponent)new TextComponentTranslation(s1, new Object[] { entityLivingBaseIn.func_145748_c_(), this.field_76386_o.func_145748_c_(), itemstack.func_151000_E() }) : (ITextComponent)new TextComponentTranslation(s, new Object[] { entityLivingBaseIn.func_145748_c_(), this.field_76386_o.func_145748_c_() });
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfar\\utility\ModularDamageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */