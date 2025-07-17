/*     */ package com.modularwarfare.common.guns;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketGunReload;
/*     */ import com.modularwarfare.common.type.BaseItem;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class ItemAmmo extends BaseItem {
/*     */   static {
/*  26 */     factory = (type -> new ItemAmmo(type));
/*     */   }
/*     */   public static final Function<AmmoType, ItemAmmo> factory;
/*     */   public AmmoType type;
/*     */   
/*     */   public ItemAmmo(AmmoType type) {
/*  32 */     super(type);
/*  33 */     this.type = type;
/*  34 */     if (type.maxStackSize == null)
/*  35 */       type.maxStackSize = Integer.valueOf(4); 
/*  36 */     func_77625_d(type.maxStackSize.intValue());
/*  37 */     this.render3d = false;
/*  38 */     func_77656_e(type.ammoCapacity);
/*     */   }
/*     */   
/*     */   public static boolean hasAmmo(ItemStack ammoStack) {
/*  42 */     if (ammoStack.func_77978_p() != null) {
/*  43 */       NBTTagCompound nbtTagCompound = ammoStack.func_77978_p();
/*  44 */       if (nbtTagCompound.func_74764_b("magcount")) {
/*  45 */         ItemAmmo itemAmmo = (ItemAmmo)ammoStack.func_77973_b();
/*  46 */         for (int i = 0; i < itemAmmo.type.magazineCount; i++) {
/*  47 */           if (nbtTagCompound.func_74762_e("ammocount" + i) > 0)
/*  48 */             return true; 
/*     */         } 
/*     */       } else {
/*  51 */         return (nbtTagCompound.func_74762_e("ammocount") > 0);
/*     */       } 
/*     */     } 
/*  54 */     return false;
/*     */   }
/*     */   
/*     */   public static ItemBullet getUsedBullet(ItemStack gunStack) {
/*  58 */     if (ItemGun.hasAmmoLoaded(gunStack)) {
/*  59 */       ItemStack ammoStack = new ItemStack(gunStack.func_77978_p().func_74775_l("ammo"));
/*  60 */       if (ammoStack.func_77942_o() && ammoStack.func_77978_p().func_74764_b("bullet")) {
/*  61 */         ItemStack usedBullet = new ItemStack(ammoStack.func_77978_p().func_74775_l("bullet"));
/*  62 */         ItemBullet usedBulletItem = (ItemBullet)usedBullet.func_77973_b();
/*  63 */         return usedBulletItem;
/*     */       } 
/*  65 */       GunType gunType = ((ItemGun)gunStack.func_77973_b()).type;
/*  66 */       if (gunType.acceptedAmmo != null && 
/*  67 */         ((ItemAmmo)ammoStack.func_77973_b()).type.subAmmo != null) {
/*  68 */         return (ItemBullet)ModularWarfare.bulletTypes.get(((ItemAmmo)ammoStack.func_77973_b()).type.subAmmo[0]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setType(BaseType type) {
/*  78 */     this.type = (AmmoType)type;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77663_a(ItemStack heldStack, World world, Entity holdingEntity, int intI, boolean flag) {
/*  83 */     if (heldStack.func_77978_p() == null && !world.field_72995_K) {
/*  84 */       ItemAmmo itemAmmo = (ItemAmmo)heldStack.func_77973_b();
/*  85 */       NBTTagCompound nbtTagCompound = new NBTTagCompound();
/*  86 */       nbtTagCompound.func_74768_a("ammocount", itemAmmo.type.ammoCapacity);
/*  87 */       nbtTagCompound.func_74768_a("skinId", 0);
/*  88 */       func_77656_e(itemAmmo.type.ammoCapacity);
/*  89 */       if (itemAmmo.type.magazineCount > 1) {
/*  90 */         nbtTagCompound.func_74768_a("magcount", 1);
/*  91 */         for (int i = 1; i < itemAmmo.type.magazineCount + 1; i++) {
/*  92 */           nbtTagCompound.func_74768_a("ammocount" + i, itemAmmo.type.ammoCapacity);
/*     */         }
/*     */       } 
/*  95 */       if (itemAmmo.type.subAmmo != null && 
/*  96 */         itemAmmo.type.subAmmo[0] != null) {
/*  97 */         ItemBullet bullet = (ItemBullet)ModularWarfare.bulletTypes.get(itemAmmo.type.subAmmo[0]);
/*  98 */         if (bullet != null) {
/*  99 */           ItemStack bulletStack = new ItemStack((Item)bullet);
/* 100 */           nbtTagCompound.func_74782_a("bullet", (NBTBase)bulletStack.func_77955_b(new NBTTagCompound()));
/*     */         } 
/*     */       } 
/*     */       
/* 104 */       heldStack.func_77982_d(nbtTagCompound);
/*     */     } 
/* 106 */     if (heldStack.func_77978_p() != null) {
/* 107 */       ItemAmmo itemAmmo = (ItemAmmo)heldStack.func_77973_b();
/* 108 */       NBTTagCompound tag = heldStack.func_77978_p();
/* 109 */       setDamage(heldStack, itemAmmo.type.ammoCapacity - tag.func_74762_e("ammocount"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
/* 115 */     ItemStack stack = playerIn.func_184586_b(handIn);
/* 116 */     ItemAmmo itemAmmo = (ItemAmmo)stack.func_77973_b();
/* 117 */     AmmoType ammoType = itemAmmo.type;
/* 118 */     if (ammoType.subAmmo != null) {
/* 119 */       ModularWarfare.NETWORK.sendToServer((PacketBase)new PacketGunReload());
/* 120 */       return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */     } 
/* 122 */     return new ActionResult(EnumActionResult.FAIL, stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack ammoStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
/* 131 */     super.func_77624_a(ammoStack, worldIn, tooltip, flagIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77651_p() {
/* 186 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\ItemAmmo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */