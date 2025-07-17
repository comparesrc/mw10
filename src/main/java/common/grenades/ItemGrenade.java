/*     */ package com.modularwarfare.common.grenades;
/*     */ import com.modularwarfare.common.entity.grenades.EntityGrenade;
/*     */ import com.modularwarfare.common.entity.grenades.EntitySmokeGrenade;
/*     */ import com.modularwarfare.common.entity.grenades.EntityStunGrenade;
/*     */ import com.modularwarfare.common.init.ModSounds;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemGrenade extends BaseItem {
/*     */   public static final Function<GrenadeType, ItemGrenade> factory;
/*     */   
/*     */   static {
/*  21 */     factory = (type -> new ItemGrenade(type));
/*     */   }
/*     */   
/*     */   public GrenadeType type;
/*     */   
/*     */   public ItemGrenade(GrenadeType type) {
/*  27 */     super(type);
/*  28 */     this.type = type;
/*  29 */     this.render3d = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
/*  34 */     if (entityLiving instanceof EntityPlayer) {
/*  35 */       EntityPlayer playerIn = (EntityPlayer)entityLiving;
/*  36 */       World worldIn = playerIn.field_70170_p;
/*  37 */       if (!worldIn.field_72995_K) {
/*  38 */         EntityGrenade grenade; EntitySmokeGrenade smoke; EntityStunGrenade stun; switch (this.type.grenadeType) {
/*     */           case Frag:
/*  40 */             grenade = new EntityGrenade(worldIn, (EntityLivingBase)playerIn, false, this.type);
/*  41 */             worldIn.func_184148_a(null, playerIn.field_70165_t, playerIn.field_70163_u, playerIn.field_70161_v, ModSounds.GRENADE_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
/*  42 */             worldIn.func_72838_d((Entity)grenade);
/*  43 */             if (!playerIn.field_71075_bZ.field_75098_d) {
/*  44 */               stack.func_190918_g(1);
/*     */             }
/*     */             break;
/*     */           case Smoke:
/*  48 */             smoke = new EntitySmokeGrenade(worldIn, (EntityLivingBase)playerIn, true, this.type);
/*  49 */             worldIn.func_184148_a(null, playerIn.field_70165_t, playerIn.field_70163_u, playerIn.field_70161_v, ModSounds.GRENADE_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
/*  50 */             worldIn.func_72838_d((Entity)smoke);
/*  51 */             if (!playerIn.field_71075_bZ.field_75098_d) {
/*  52 */               stack.func_190918_g(1);
/*     */             }
/*     */             break;
/*     */           case Stun:
/*  56 */             stun = new EntityStunGrenade(worldIn, (EntityLivingBase)playerIn, true, this.type);
/*  57 */             worldIn.func_184148_a(null, playerIn.field_70165_t, playerIn.field_70163_u, playerIn.field_70161_v, ModSounds.GRENADE_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
/*  58 */             worldIn.func_72838_d((Entity)stun);
/*  59 */             if (!playerIn.field_71075_bZ.field_75098_d) {
/*  60 */               stack.func_190918_g(1);
/*     */             }
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
/*  71 */     ItemStack stack = playerIn.func_184586_b(handIn);
/*  72 */     worldIn.func_184148_a(null, playerIn.field_70165_t, playerIn.field_70163_u, playerIn.field_70161_v, ModSounds.GRENADE_THROW, SoundCategory.PLAYERS, 0.5F, 1.0F);
/*  73 */     if (!worldIn.field_72995_K) {
/*  74 */       EntityGrenade grenade; EntitySmokeGrenade smoke; EntityStunGrenade stun; switch (this.type.grenadeType) {
/*     */         case Frag:
/*  76 */           grenade = new EntityGrenade(worldIn, (EntityLivingBase)playerIn, true, this.type);
/*  77 */           worldIn.func_72838_d((Entity)grenade);
/*     */           
/*  79 */           if (!playerIn.field_71075_bZ.field_75098_d) {
/*  80 */             stack.func_190918_g(1);
/*     */           }
/*     */           break;
/*     */         case Smoke:
/*  84 */           smoke = new EntitySmokeGrenade(worldIn, (EntityLivingBase)playerIn, true, this.type);
/*  85 */           worldIn.func_72838_d((Entity)smoke);
/*     */           
/*  87 */           if (!playerIn.field_71075_bZ.field_75098_d) {
/*  88 */             stack.func_190918_g(1);
/*     */           }
/*     */           break;
/*     */         case Stun:
/*  92 */           stun = new EntityStunGrenade(worldIn, (EntityLivingBase)playerIn, true, this.type);
/*  93 */           worldIn.func_72838_d((Entity)stun);
/*     */           
/*  95 */           if (!playerIn.field_71075_bZ.field_75098_d) {
/*  96 */             stack.func_190918_g(1);
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 103 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77651_p() {
/* 109 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\grenades\ItemGrenade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */