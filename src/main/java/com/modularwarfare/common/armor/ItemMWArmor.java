/*     */ package com.modularwarfare.common.armor;
/*     */ import com.modularwarfare.api.MWArmorType;
/*     */ import com.modularwarfare.client.model.ModelCustomArmor;
/*     */ import com.modularwarfare.common.init.ModSounds;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class ItemMWArmor extends ItemArmor implements ISpecialArmor {
/*     */   public ArmorType type;
/*     */   
/*     */   public ItemMWArmor(ArmorType type, MWArmorType armorSlot) {
/*  31 */     super(ItemArmor.ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.func_188451_a(armorSlot.name().toLowerCase()));
/*  32 */     type.initializeArmor(armorSlot.name().toLowerCase());
/*  33 */     type.loadExtraValues();
/*  34 */     func_77655_b(this.internalName = ((ArmorType.ArmorInfo)type.armorTypes.get(armorSlot)).internalName);
/*  35 */     setRegistryName(this.internalName);
/*  36 */     func_77637_a((CreativeTabs)ModularWarfare.MODS_TABS.get(type.contentPack));
/*  37 */     if (type.durability != null) {
/*  38 */       func_77656_e(type.durability.intValue());
/*     */     }
/*  40 */     this.baseType = type;
/*  41 */     this.type = type;
/*     */   }
/*     */   public BaseType baseType; public String internalName;
/*     */   public void setType(BaseType type) {
/*  45 */     this.type = (ArmorType)type;
/*     */   }
/*     */   
/*     */   public void func_77663_a(ItemStack unused, World world, Entity holdingEntity, int intI, boolean flag) {
/*  49 */     if (holdingEntity instanceof EntityPlayer && 
/*  50 */       unused != null && unused.func_77973_b() instanceof ItemMWArmor && unused.func_77978_p() == null) {
/*  51 */       NBTTagCompound nbtTagCompound = new NBTTagCompound();
/*  52 */       nbtTagCompound.func_74768_a("skinId", 0);
/*  53 */       unused.func_77982_d(nbtTagCompound);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77651_p() {
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String armourType) {
/*  64 */     int skinId = stack.func_77978_p().func_74762_e("skinId");
/*  65 */     String path = (skinId > 0) ? ("skins/" + this.type.modelSkins[skinId].getSkin()) : this.type.modelSkins[0].getSkin();
/*  66 */     return "modularwarfare:skins/armor/" + path + ".png";
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @Nullable
/*     */   public ModelBiped getArmorModel(EntityLivingBase living, ItemStack stack, EntityEquipmentSlot slot, ModelBiped defaultModel) {
/*  73 */     if (!this.type.simpleArmor && 
/*  74 */       !stack.func_190926_b() && 
/*  75 */       stack.func_77973_b() instanceof ItemMWArmor) {
/*  76 */       ArmorType armorType = ((ItemMWArmor)stack.func_77973_b()).type;
/*  77 */       ModelCustomArmor armorModel = (ModelCustomArmor)armorType.bipedModel;
/*  78 */       if (slot != EntityEquipmentSlot.MAINHAND && slot != EntityEquipmentSlot.OFFHAND) {
/*  79 */         armorModel.showChest((slot == EntityEquipmentSlot.CHEST));
/*  80 */         armorModel.showFeet((slot == EntityEquipmentSlot.FEET));
/*  81 */         armorModel.showHead((slot == EntityEquipmentSlot.HEAD));
/*  82 */         armorModel.showLegs((slot == EntityEquipmentSlot.LEGS));
/*     */       } 
/*     */       
/*  85 */       armorModel.func_178686_a((ModelBase)defaultModel);
/*     */       
/*  87 */       return (ModelBiped)armorModel;
/*     */     } 
/*     */ 
/*     */     
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
/*  96 */     ItemStack itemstack = playerIn.func_184586_b(handIn);
/*  97 */     EntityEquipmentSlot entityequipmentslot = EntityLiving.func_184640_d(itemstack);
/*  98 */     ItemStack itemstack1 = playerIn.func_184582_a(entityequipmentslot);
/*     */     
/* 100 */     if (itemstack1.func_190926_b()) {
/* 101 */       playerIn.func_184201_a(entityequipmentslot, itemstack.func_77946_l());
/* 102 */       itemstack.func_190920_e(0);
/* 103 */       worldIn.func_184133_a(null, playerIn.func_180425_c(), ModSounds.EQUIP_EXTRA, SoundCategory.PLAYERS, 2.0F, 1.0F);
/* 104 */       return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*     */     } 
/* 106 */     return new ActionResult(EnumActionResult.FAIL, itemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/* 111 */     return new ISpecialArmor.ArmorProperties(1, this.type.defense, 2147483647);
/*     */   }
/*     */   
/*     */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
/* 115 */     return (int)(this.type.defense * 20.0D);
/*     */   }
/*     */   
/*     */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
/* 119 */     if (this.type.durability != null)
/* 120 */       stack.func_77972_a(damage, entity); 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\armor\ItemMWArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */