/*     */ package com.modularwarfare.common.type;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.MWArmorType;
/*     */ import com.modularwarfare.api.TypeRegisterEvent;
/*     */ import com.modularwarfare.client.fpp.basic.configs.AmmoRenderConfig;
/*     */ import com.modularwarfare.client.fpp.basic.configs.ArmorRenderConfig;
/*     */ import com.modularwarfare.client.fpp.basic.configs.AttachmentRenderConfig;
/*     */ import com.modularwarfare.client.fpp.basic.configs.BackpackRenderConfig;
/*     */ import com.modularwarfare.client.fpp.basic.configs.GrenadeRenderConfig;
/*     */ import com.modularwarfare.client.fpp.basic.configs.GunRenderConfig;
/*     */ import com.modularwarfare.client.model.ModelAttachment;
/*     */ import com.modularwarfare.client.model.ModelBackpack;
/*     */ import com.modularwarfare.client.model.ModelCustomArmor;
/*     */ import com.modularwarfare.common.armor.ArmorType;
/*     */ import com.modularwarfare.common.armor.ItemMWArmor;
/*     */ import com.modularwarfare.common.armor.ItemSpecialArmor;
/*     */ import com.modularwarfare.common.backpacks.BackpackType;
/*     */ import com.modularwarfare.common.grenades.GrenadeType;
/*     */ import com.modularwarfare.common.guns.AmmoType;
/*     */ import com.modularwarfare.common.guns.AttachmentType;
/*     */ import com.modularwarfare.common.guns.BulletType;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAmmo;
/*     */ import com.modularwarfare.common.guns.SprayType;
/*     */ import com.modularwarfare.common.textures.TextureType;
/*     */ import java.util.HashMap;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ public class ContentTypes {
/*  31 */   public static ArrayList<TypeEntry> values = new ArrayList<>();
/*  32 */   private static int typeId = 0;
/*     */   
/*     */   public static void registerTypes() {
/*  35 */     registerType("textures", (Class)TextureType.class, (type, reload) -> ModularWarfare.textureTypes.put(type.internalName, (TextureType)type));
/*     */ 
/*     */ 
/*     */     
/*  39 */     registerType("guns", (Class)GunType.class, (type, reload) -> {
/*     */           assignType(ModularWarfare.gunTypes, ItemGun.factory, (GunType)type, reload);
/*     */           
/*     */           if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
/*     */             if (((GunType)type).animationType.equals(WeaponAnimationType.ENHANCED)) {
/*     */               type.enhancedModel.config = (EnhancedRenderConfig)ModularWarfare.getRenderConfig(type, GunEnhancedRenderConfig.class);
/*     */             } else {
/*     */               ((ModelGun)type.model).config = (GunRenderConfig)ModularWarfare.getRenderConfig(type, GunRenderConfig.class);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/*  51 */     registerType("ammo", (Class)AmmoType.class, (type, reload) -> {
/*     */           assignType(ModularWarfare.ammoTypes, ItemAmmo.factory, (AmmoType)type, reload);
/*     */ 
/*     */           
/*     */           if (FMLCommonHandler.instance().getSide() == Side.CLIENT && ((AmmoType)type).isDynamicAmmo) {
/*     */             ((ModelAmmo)type.model).config = (AmmoRenderConfig)ModularWarfare.getRenderConfig(type, AmmoRenderConfig.class);
/*     */           }
/*     */         });
/*     */     
/*  60 */     registerType("attachments", (Class)AttachmentType.class, (type, reload) -> {
/*     */           assignType(ModularWarfare.attachmentTypes, ItemAttachment.factory, (AttachmentType)type, reload);
/*     */           
/*     */           if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
/*     */             ((ModelAttachment)type.model).config = (AttachmentRenderConfig)ModularWarfare.getRenderConfig(type, AttachmentRenderConfig.class);
/*     */             
/*     */             ((ModelAttachment)type.model).config.init();
/*     */           } 
/*     */         });
/*     */     
/*  70 */     registerType("armor", (Class)ArmorType.class, (type, reload) -> {
/*     */           ArmorType armorType = (ArmorType)type;
/*     */           
/*     */           if (FMLCommonHandler.instance().getSide() == Side.CLIENT && !armorType.simpleArmor) {
/*     */             ((ModelCustomArmor)type.bipedModel).config = (ArmorRenderConfig)ModularWarfare.getRenderConfig(type, ArmorRenderConfig.class);
/*     */           }
/*     */           
/*     */           if (!reload.booleanValue()) {
/*     */             for (MWArmorType mwArmorType : armorType.armorTypes.keySet()) {
/*     */               if (MWArmorType.isVanilla(mwArmorType)) {
/*     */                 ModularWarfare.armorTypes.put(armorType.internalName + "_" + mwArmorType.name().toLowerCase(), new ItemMWArmor(armorType, mwArmorType));
/*     */                 
/*     */                 if (FMLCommonHandler.instance().getSide() == Side.CLIENT && !armorType.simpleArmor) {
/*     */                   ((ModelCustomArmor)type.bipedModel).config = (ArmorRenderConfig)ModularWarfare.getRenderConfig(type, ArmorRenderConfig.class);
/*     */                 }
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */               
/*     */               ModularWarfare.specialArmorTypes.put(armorType.internalName, new ItemSpecialArmor(armorType, mwArmorType));
/*     */               
/*     */               if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
/*     */                 ((ModelCustomArmor)((ItemSpecialArmor)ModularWarfare.specialArmorTypes.get(armorType.internalName)).type.bipedModel).config = (ArmorRenderConfig)ModularWarfare.getRenderConfig(type, ArmorRenderConfig.class);
/*     */               }
/*     */             } 
/*     */           } else if (ModularWarfare.armorTypes.containsKey(type.internalName)) {
/*     */             ((ItemMWArmor)ModularWarfare.armorTypes.get(type.internalName)).setType(type);
/*     */           } 
/*     */         });
/*     */     
/* 100 */     registerType("bullets", (Class)BulletType.class, (type, reload) -> assignType(ModularWarfare.bulletTypes, ItemBullet.factory, (BulletType)type, reload));
/*     */ 
/*     */ 
/*     */     
/* 104 */     registerType("sprays", (Class)SprayType.class, (type, reload) -> assignType(ModularWarfare.sprayTypes, ItemSpray.factory, (SprayType)type, reload));
/*     */ 
/*     */     
/* 107 */     registerType("backpacks", (Class)BackpackType.class, (type, reload) -> {
/*     */           assignType(ModularWarfare.backpackTypes, ItemBackpack.factory, (BackpackType)type, reload);
/*     */           
/*     */           if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
/*     */             ((ModelBackpack)type.model).config = (BackpackRenderConfig)ModularWarfare.getRenderConfig(type, BackpackRenderConfig.class);
/*     */           }
/*     */         });
/* 114 */     registerType("grenades", (Class)GrenadeType.class, (type, reload) -> {
/*     */           assignType(ModularWarfare.grenadeTypes, ItemGrenade.factory, (GrenadeType)type, reload);
/*     */ 
/*     */ 
/*     */           
/*     */           if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
/*     */             ((ModelGrenade)type.model).config = (GrenadeRenderConfig)ModularWarfare.getRenderConfig(type, GrenadeRenderConfig.class);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 125 */     TypeRegisterEvent event = new TypeRegisterEvent();
/* 126 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T extends BaseType, U extends BaseItem> void assignType(HashMap<String, U> map, Function<T, U> factory, T type, Boolean reload) {
/* 131 */     if (reload.booleanValue()) {
/* 132 */       ((BaseItem)map.get(((BaseType)type).internalName)).setType((BaseType)type);
/*     */     } else {
/* 134 */       map.put(((BaseType)type).internalName, factory.apply(type));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void registerType(String name, Class<? extends BaseType> typeClass, BiConsumer<BaseType, Boolean> typeAssignFunction) {
/* 139 */     values.add(new TypeEntry(name, typeClass, typeId, typeAssignFunction));
/* 140 */     typeId++;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\type\ContentTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */