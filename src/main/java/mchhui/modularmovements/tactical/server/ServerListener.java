/*     */ package mchhui.modularmovements.tactical.server;
/*     */ 
/*     */ import com.modularwarfare.raycast.obb.ModelPlayer;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import mchhui.modularmovements.ModularMovements;
/*     */ import mchhui.modularmovements.tactical.PlayerState;
/*     */ import mchhui.modularmovements.tactical.network.TacticalHandler;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLInitializationEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.PlayerEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import net.minecraftforge.fml.relauncher.ReflectionHelper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerListener
/*     */ {
/*     */   public static Method setSize;
/*  34 */   public static Map<Integer, PlayerState> playerStateMap = new HashMap<>();
/*  35 */   public static Map<Integer, Long> playerNotStepMap = new HashMap<>();
/*     */   
/*     */   public void onFMLInit(FMLInitializationEvent event) {
/*  38 */     setSize = ReflectionHelper.findMethod(Entity.class, "setSize", "func_70105_a", new Class[] { float.class, float.class });
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
/*  43 */     playerStateMap.put(Integer.valueOf(event.player.func_145782_y()), new PlayerState());
/*  44 */     TacticalHandler.sendClientConfig((EntityPlayerMP)event.player);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
/*  49 */     playerStateMap.remove(Integer.valueOf(event.player.func_145782_y()));
/*  50 */     playerNotStepMap.remove(Integer.valueOf(event.player.func_145782_y()));
/*     */   }
/*     */   
/*     */   public static double getCameraProbeOffset(Integer id) {
/*  54 */     if (!playerStateMap.containsKey(id)) {
/*  55 */       return 0.0D;
/*     */     }
/*  57 */     return ((PlayerState)playerStateMap.get(id)).probeOffset;
/*     */   }
/*     */   
/*     */   public static boolean isSitting(Integer id) {
/*  61 */     if (!playerStateMap.containsKey(id)) {
/*  62 */       return false;
/*     */     }
/*  64 */     return ((PlayerState)playerStateMap.get(id)).isSitting;
/*     */   }
/*     */   
/*     */   public static boolean isCrawling(Integer id) {
/*  68 */     if (!playerStateMap.containsKey(id)) {
/*  69 */       return false;
/*     */     }
/*  71 */     return ((PlayerState)playerStateMap.get(id)).isCrawling;
/*     */   }
/*     */   
/*     */   public static void updateOffset(Integer id) {
/*  75 */     if (!playerStateMap.containsKey(id)) {
/*     */       return;
/*     */     }
/*  78 */     ((PlayerState)playerStateMap.get(id)).updateOffset();
/*     */   }
/*     */   
/*     */   public static Vec3d onGetPositionEyes(EntityPlayer player, float partialTicks, Vec3d vec3d) {
/*  82 */     if (getCameraProbeOffset(Integer.valueOf(player.func_145782_y())) != 0.0D) {
/*  83 */       return vec3d.func_178787_e((new Vec3d(getCameraProbeOffset(Integer.valueOf(player.func_145782_y())) * -0.6D, 0.0D, 0.0D))
/*  84 */           .func_178785_b((float)(-player.field_70177_z * Math.PI / 180.0D)));
/*     */     }
/*  86 */     return vec3d;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerTick(TickEvent.PlayerTickEvent event) {
/*  91 */     if (event.side != Side.SERVER) {
/*     */       return;
/*     */     }
/*  94 */     if (event.phase == TickEvent.Phase.END) {
/*  95 */       updateOffset(Integer.valueOf(event.player.func_145782_y()));
/*     */       
/*  97 */       if (isSitting(Integer.valueOf(event.player.func_145782_y()))) {
/*  98 */         if (event.player.eyeHeight != 1.1F) {
/*  99 */           event.player.eyeHeight = 1.1F;
/*     */         }
/* 101 */       } else if (isCrawling(Integer.valueOf(event.player.func_145782_y()))) {
/* 102 */         if (event.player.eyeHeight != 0.7F) {
/* 103 */           event.player.eyeHeight = 0.7F;
/*     */         }
/* 105 */       } else if (event.player.eyeHeight == 0.7F) {
/* 106 */         event.player.eyeHeight = event.player.getDefaultEyeHeight();
/* 107 */       } else if (event.player.eyeHeight == 1.1F) {
/* 108 */         event.player.eyeHeight = event.player.getDefaultEyeHeight();
/*     */       } 
/*     */       
/* 111 */       float f = event.player.field_70130_N;
/* 112 */       float f1 = event.player.field_70131_O;
/* 113 */       if (isSitting(Integer.valueOf(event.player.func_145782_y()))) {
/* 114 */         f1 = 1.2F;
/* 115 */       } else if (isCrawling(Integer.valueOf(event.player.func_145782_y()))) {
/* 116 */         f1 = 0.8F;
/*     */       } 
/*     */       
/* 119 */       if (f != event.player.field_70130_N || f1 != event.player.field_70131_O) {
/* 120 */         AxisAlignedBB axisalignedbb = event.player.func_174813_aQ();
/* 121 */         axisalignedbb = new AxisAlignedBB(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c, axisalignedbb.field_72340_a + f, axisalignedbb.field_72338_b + f1, axisalignedbb.field_72339_c + f);
/*     */ 
/*     */ 
/*     */         
/* 125 */         if (!event.player.field_70170_p.func_184143_b(axisalignedbb)) {
/*     */           try {
/* 127 */             setSize.invoke(event.player, new Object[] { Float.valueOf(f), Float.valueOf(f1) });
/* 128 */           } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException e) {
/* 129 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*     */       } 
/* 133 */       PlayerState state = playerStateMap.get(Integer.valueOf(event.player.func_145782_y()));
/* 134 */       if (state != null) {
/* 135 */         Vec3d vec3d = (new Vec3d(-0.6D, 0.0D, 0.0D)).func_178785_b((float)(-(event.player.field_70177_z - 180.0F) * Math.PI / 180.0D));
/* 136 */         state.lastAABB = event.player.func_174813_aQ();
/* 137 */         state.lastModAABB = state.lastAABB.func_191194_a(vec3d.func_186678_a(-getCameraProbeOffset(Integer.valueOf(event.player.func_145782_y()))));
/* 138 */         event.player.func_174826_a(state.lastModAABB);
/*     */       } 
/*     */     } else {
/* 141 */       PlayerState state = playerStateMap.get(Integer.valueOf(event.player.func_145782_y()));
/* 142 */       if (state != null && 
/* 143 */         state.lastAABB != null && event.player.func_174813_aQ() == state.lastModAABB) {
/* 144 */         event.player.func_174826_a(state.lastAABB);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setRotationAngles(ModelPlayer model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 152 */     if (entityIn instanceof EntityPlayer && entityIn.func_70089_S()) {
/* 153 */       PlayerState state = null;
/* 154 */       float offest = 0.0F;
/* 155 */       state = playerStateMap.get(Integer.valueOf(entityIn.func_145782_y()));
/* 156 */       if (state == null) {
/*     */         return;
/*     */       }
/* 159 */       offest = state.probeOffset;
/*     */       
/* 161 */       if (state.isSitting) {
/* 162 */         model.bipedRightLeg.rotateAngleX = -1.4137167F;
/* 163 */         model.bipedRightLeg.rotateAngleY = 0.31415927F;
/* 164 */         model.bipedRightLeg.rotateAngleZ = 0.07853982F;
/* 165 */         model.bipedLeftLeg.rotateAngleX = -1.4137167F;
/* 166 */         model.bipedLeftLeg.rotateAngleY = -0.31415927F;
/* 167 */         model.bipedLeftLeg.rotateAngleZ = -0.07853982F;
/*     */       } 
/*     */       
/* 170 */       if (state.isCrawling) {
/* 171 */         model.bipedHead.rotateAngleX = (float)(model.bipedHead.rotateAngleX - 1.2211111111111113D);
/* 172 */         model.bipedRightArm.rotateAngleX = (float)(model.bipedRightArm.rotateAngleX * 0.2D);
/* 173 */         model.bipedLeftArm.rotateAngleX = (float)(model.bipedLeftArm.rotateAngleX * 0.2D);
/* 174 */         model.bipedRightArm.rotateAngleX = (float)(model.bipedRightArm.rotateAngleX + 3.14D);
/* 175 */         model.bipedLeftArm.rotateAngleX = (float)(model.bipedLeftArm.rotateAngleX + 3.14D);
/* 176 */         if (entityIn instanceof EntityPlayer) {
/* 177 */           ItemStack itemstack = ((EntityPlayer)entityIn).func_184614_ca();
/* 178 */           if (itemstack != ItemStack.field_190927_a && !itemstack.func_190926_b() && 
/* 179 */             ModularMovements.mwfEnable && 
/* 180 */             itemstack.func_77973_b() instanceof com.modularwarfare.common.type.BaseItem) {
/* 181 */             model.bipedLeftArm.rotateAngleY = 0.0F;
/* 182 */             model.bipedRightArm.rotateAngleY = 0.0F;
/* 183 */             model.bipedLeftArm.rotateAngleX = 3.14F;
/* 184 */             model.bipedRightArm.rotateAngleX = 3.14F;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 189 */         model.bipedRightLeg.rotateAngleX = (float)(model.bipedRightLeg.rotateAngleX * 0.2D);
/* 190 */         model.bipedLeftLeg.rotateAngleX = (float)(model.bipedLeftLeg.rotateAngleX * 0.2D);
/*     */       } 
/* 192 */       if (offest >= 0.0F) {
/* 193 */         model.bipedRightLeg.rotateAngleZ = (float)(model.bipedRightLeg.rotateAngleZ + (offest * 20.0F) * 3.14D / 180.0D);
/*     */       } else {
/* 195 */         model.bipedLeftLeg.rotateAngleZ = (float)(model.bipedLeftLeg.rotateAngleZ + (offest * 20.0F) * 3.14D / 180.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlaySoundAtEntity(PlaySoundAtEntityEvent event) {
/* 202 */     if (event.getEntity() instanceof EntityPlayer && 
/* 203 */       playerNotStepMap.containsKey(Integer.valueOf(event.getEntity().func_145782_y())) && (
/* 204 */       (Long)playerNotStepMap.get(Integer.valueOf(event.getEntity().func_145782_y()))).longValue() > System.currentTimeMillis() && (
/* 205 */       (ResourceLocation)SoundEvent.field_187505_a.func_177774_c(event.getSound())).toString().contains("step"))
/* 206 */       event.setVolume(0.0F); 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\tactical\server\ServerListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */