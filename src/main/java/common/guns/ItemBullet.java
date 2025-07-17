/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.modularwarfare.common.type.BaseItem;
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class ItemBullet extends BaseItem {
/*    */   static {
/* 17 */     factory = (type -> new ItemBullet(type));
/*    */   }
/*    */   
/*    */   public static final Function<BulletType, ItemBullet> factory;
/*    */   
/*    */   public ItemBullet(BulletType type) {
/* 23 */     super(type);
/* 24 */     this.type = type;
/* 25 */     this.render3d = false;
/*    */   }
/*    */   public BulletType type;
/*    */   
/*    */   public void setType(BaseType type) {
/* 30 */     this.type = (BulletType)type;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
/* 36 */     tooltip.add(generateLoreListEntry("Damage", this.type.bulletDamageFactor + "x"));
/*    */     
/* 38 */     if (this.type.bulletProperties != null) {
/* 39 */       Iterator<String> iterator = this.type.bulletProperties.keySet().iterator(); if (iterator.hasNext()) { String key = iterator.next();
/* 40 */         tooltip.add(generateLoreHeader("Modifiers"));
/* 41 */         BulletProperty bulletProperty = this.type.bulletProperties.get(key);
/*    */         
/* 43 */         if (bulletProperty.potionEffects != null) {
/* 44 */           tooltip.add(generateLoreHeader("Effects"));
/* 45 */           for (PotionEntry potionEntry : bulletProperty.potionEffects) {
/* 46 */             if (bulletProperty.potionEffects != null)
/* 47 */               tooltip.add(generateLoreListEntry(potionEntry.potionEffect.name(), "")); 
/*    */           } 
/*    */         }  }
/*    */     
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\ItemBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */