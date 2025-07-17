/*     */ package com.modularwarfare.common.hitbox;
/*     */ 
/*     */ import com.modularwarfare.api.AnimationUtils;
/*     */ import com.modularwarfare.api.PlayerSnapshotCreateEvent;
/*     */ import com.modularwarfare.common.handler.ServerTickHandler;
/*     */ import com.modularwarfare.common.hitbox.maths.EnumHitboxType;
/*     */ import com.modularwarfare.common.hitbox.maths.RotatedAxes;
/*     */ import com.modularwarfare.common.vector.Vector3f;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
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
/*     */ public class PlayerSnapshot
/*     */ {
/*     */   public EntityPlayer player;
/*     */   public Vector3f pos;
/*     */   public ArrayList<PlayerHitbox> hitboxes;
/*     */   public long time;
/*     */   
/*     */   public PlayerSnapshot(EntityPlayer p) {
/*  43 */     this.player = p;
/*  44 */     this.pos = new Vector3f(p.field_70165_t, p.field_70163_u, p.field_70161_v);
/*     */     
/*  46 */     PlayerSnapshotCreateEvent.Pre event = new PlayerSnapshotCreateEvent.Pre(p, this.pos);
/*  47 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  48 */     this.pos = event.pos;
/*  49 */     this.hitboxes = new ArrayList<>();
/*     */     
/*  51 */     RotatedAxes bodyAxes = new RotatedAxes(p.field_70761_aq, 0.0F, 0.0F);
/*  52 */     RotatedAxes headAxes = new RotatedAxes(p.field_70759_as - p.field_70761_aq, p.field_70125_A, 0.0F);
/*     */     
/*  54 */     if (p.func_70093_af()) {
/*  55 */       this.hitboxes.add(new PlayerHitbox(this.player, bodyAxes, new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(-0.25F, 0.0F, -0.15F), new Vector3f(0.5F, 1.0F, 0.3F), EnumHitboxType.BODY));
/*  56 */       this.hitboxes.add(new PlayerHitbox(this.player, bodyAxes.findLocalAxesGlobally(headAxes), new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(-0.25F, 0.0F, -0.25F), new Vector3f(0.5F, 0.5F, 0.5F), EnumHitboxType.HEAD));
/*     */     } else {
/*  58 */       this.hitboxes.add(new PlayerHitbox(this.player, bodyAxes, new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(-0.25F, 0.0F, -0.15F), new Vector3f(0.5F, 1.4F, 0.3F), EnumHitboxType.BODY));
/*  59 */       this.hitboxes.add(new PlayerHitbox(this.player, bodyAxes.findLocalAxesGlobally(headAxes), new Vector3f(0.0F, 1.4F, 0.0F), new Vector3f(-0.25F, 0.0F, -0.25F), new Vector3f(0.5F, 0.5F, 0.5F), EnumHitboxType.HEAD));
/*     */     } 
/*     */ 
/*     */     
/*  63 */     float yHead = (p.field_70759_as - p.field_70761_aq) / 57.295776F;
/*  64 */     float xHead = p.field_70125_A / 57.295776F;
/*     */ 
/*     */     
/*  67 */     float zRight = 0.0F;
/*  68 */     float zLeft = 0.0F;
/*  69 */     float yRight = 0.0F;
/*  70 */     float yLeft = 0.0F;
/*  71 */     float xRight = 0.0F;
/*  72 */     float xLeft = 0.0F;
/*     */     
/*  74 */     if (p.func_184614_ca() != null && 
/*  75 */       p.func_184614_ca().func_77973_b() instanceof com.modularwarfare.common.guns.ItemGun) {
/*  76 */       if (p.field_70170_p.field_72995_K) {
/*  77 */         if (AnimationUtils.isAiming.get(this.player.getDisplayNameString()) != null) {
/*  78 */           yRight = -0.1F + yHead - 1.5707964F;
/*  79 */           yLeft = 0.1F + yHead + 0.4F - 1.5707964F;
/*  80 */           xRight = -1.5707964F + xHead;
/*  81 */           xLeft = -1.5707964F + xHead;
/*     */         } else {
/*  83 */           yRight = -1.8407964F;
/*  84 */           yLeft = -1.2407963F;
/*  85 */           xRight = -0.8717918F;
/*  86 */           xLeft = -0.8717918F;
/*     */         }
/*     */       
/*  89 */       } else if (ServerTickHandler.playerAimShootCooldown.get(p.getDisplayNameString()) != null) {
/*  90 */         yRight = -0.1F + yHead - 1.5707964F;
/*  91 */         yLeft = 0.1F + yHead + 0.4F - 1.5707964F;
/*  92 */         xRight = -1.5707964F + xHead;
/*  93 */         xLeft = -1.5707964F + xHead;
/*     */       } else {
/*  95 */         yRight = -1.8407964F;
/*  96 */         yLeft = -1.2407963F;
/*  97 */         xRight = -0.8717918F;
/*  98 */         xLeft = -0.8717918F;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 104 */     RotatedAxes leftArmAxes = (new RotatedAxes()).rotateGlobalPitchInRads(xLeft).rotateGlobalYawInRads(3.1415927F + yLeft).rotateGlobalRollInRads(-zLeft);
/* 105 */     RotatedAxes rightArmAxes = (new RotatedAxes()).rotateGlobalPitchInRads(xRight).rotateGlobalYawInRads(3.1415927F + yRight).rotateGlobalRollInRads(-zRight);
/*     */ 
/*     */     
/* 108 */     float originZRight = MathHelper.func_76126_a(-p.field_70761_aq * 3.1415927F / 180.0F) * 5.0F / 16.0F;
/* 109 */     float originXRight = -MathHelper.func_76134_b(-p.field_70761_aq * 3.1415927F / 180.0F) * 5.0F / 16.0F;
/*     */     
/* 111 */     float originZLeft = -MathHelper.func_76126_a(-p.field_70761_aq * 3.1415927F / 180.0F) * 5.0F / 16.0F;
/* 112 */     float originXLeft = MathHelper.func_76134_b(-p.field_70761_aq * 3.1415927F / 180.0F) * 5.0F / 16.0F;
/*     */ 
/*     */     
/* 115 */     if (p.func_70093_af()) {
/* 116 */       this.hitboxes.add(new PlayerHitbox(this.player, bodyAxes.findLocalAxesGlobally(leftArmAxes), new Vector3f(originXLeft, 0.9F, originZLeft), new Vector3f(-0.125F, -0.6F, -0.125F), new Vector3f(0.25F, 0.7F, 0.25F), EnumHitboxType.LEFTARM));
/* 117 */       this.hitboxes.add(new PlayerHitbox(this.player, bodyAxes.findLocalAxesGlobally(rightArmAxes), new Vector3f(originXRight, 0.9F, originZRight), new Vector3f(-0.125F, -0.6F, -0.125F), new Vector3f(0.25F, 0.7F, 0.25F), EnumHitboxType.RIGHTARM));
/*     */     } else {
/* 119 */       this.hitboxes.add(new PlayerHitbox(this.player, bodyAxes.findLocalAxesGlobally(leftArmAxes), new Vector3f(originXLeft, 1.3F, originZLeft), new Vector3f(-0.125F, -0.6F, -0.125F), new Vector3f(0.25F, 0.7F, 0.25F), EnumHitboxType.LEFTARM));
/* 120 */       this.hitboxes.add(new PlayerHitbox(this.player, bodyAxes.findLocalAxesGlobally(rightArmAxes), new Vector3f(originXRight, 1.3F, originZRight), new Vector3f(-0.125F, -0.6F, -0.125F), new Vector3f(0.25F, 0.7F, 0.25F), EnumHitboxType.RIGHTARM));
/*     */     } 
/* 122 */     MinecraftForge.EVENT_BUS.post((Event)new PlayerSnapshotCreateEvent.Post(p, this.pos, this.hitboxes));
/*     */     
/* 124 */     this.time = System.nanoTime();
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
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderSnapshot() {
/* 151 */     for (PlayerHitbox playerHitbox : this.hitboxes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerHitbox GetHitbox(EnumHitboxType type) {
/* 157 */     for (PlayerHitbox hitbox : this.hitboxes) {
/* 158 */       if (hitbox.type == type) {
/* 159 */         return hitbox;
/*     */       }
/*     */     } 
/* 162 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\hitbox\PlayerSnapshot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */