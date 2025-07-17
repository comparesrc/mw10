/*     */ package com.modularwarfare.client.fpp.basic.configs;
/*     */ 
/*     */ import com.modularwarfare.api.WeaponAnimations;
/*     */ import com.modularwarfare.client.fpp.basic.models.objects.BreakActionData;
/*     */ import com.modularwarfare.client.fpp.basic.models.objects.RenderVariables;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import org.lwjgl.util.vector.Vector2f;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GunRenderConfig
/*     */ {
/*  16 */   public String modelFileName = "";
/*     */   
/*  18 */   public Arms arms = new Arms();
/*     */   
/*  20 */   public Sprint sprint = new Sprint();
/*     */   
/*  22 */   public ThirdPerson thirdPerson = new ThirdPerson();
/*     */   
/*  24 */   public Aim aim = new Aim();
/*     */   
/*  26 */   public Bolt bolt = new Bolt();
/*     */   
/*  28 */   public Attachments attachments = new Attachments();
/*     */   
/*  30 */   public Maps maps = new Maps();
/*     */   
/*  32 */   public BreakAction breakAction = new BreakAction();
/*     */   
/*  34 */   public HammerAction hammerAction = new HammerAction();
/*     */   
/*  36 */   public RevolverBarrel revolverBarrel = new RevolverBarrel();
/*     */   
/*  38 */   public ItemFrame itemFrame = new ItemFrame();
/*     */ 
/*     */   
/*  41 */   public Extra extra = new Extra();
/*     */   
/*     */   public static class Arms
/*     */   {
/*     */     public boolean leftHandAmmo = true;
/*  46 */     public EnumArm actionArm = EnumArm.Left;
/*     */     
/*  48 */     public EnumAction actionType = EnumAction.Charge;
/*     */     
/*  50 */     public LeftArm leftArm = new LeftArm();
/*  51 */     public RightArm rightArm = new RightArm();
/*     */     
/*     */     public enum EnumArm {
/*  54 */       Left, Right;
/*     */     }
/*     */     
/*     */     public enum EnumAction {
/*  58 */       Bolt, Pump, Charge;
/*     */     }
/*     */     
/*     */     public class LeftArm {
/*  62 */       public Vector3f armScale = new Vector3f(0.8F, 0.8F, 0.8F);
/*     */       
/*  64 */       public Vector3f armPos = new Vector3f(0.25F, -0.59F, 0.06F);
/*  65 */       public Vector3f armRot = new Vector3f(65.0F, 32.0F, -46.0F);
/*     */       
/*  67 */       public Vector3f armReloadPos = new Vector3f(-0.1F, -0.65F, 0.02F);
/*  68 */       public Vector3f armReloadRot = new Vector3f(35.0F, 0.0F, -25.0F);
/*     */       
/*  70 */       public Vector3f armChargePos = new Vector3f(0.0F, 0.0F, 0.0F);
/*  71 */       public Vector3f armChargeRot = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */     }
/*     */     
/*     */     public class RightArm {
/*  75 */       public Vector3f armScale = new Vector3f(0.8F, 0.8F, 0.8F);
/*     */       
/*  77 */       public Vector3f armPos = new Vector3f(0.26F, -0.65F, 0.0F);
/*  78 */       public Vector3f armRot = new Vector3f(0.0F, 0.0F, -90.0F);
/*     */       
/*  80 */       public Vector3f armReloadPos = new Vector3f(0.27F, -0.65F, 0.04F);
/*  81 */       public Vector3f armReloadRot = new Vector3f(0.0F, 0.0F, -90.0F);
/*     */       
/*  83 */       public Vector3f armChargePos = new Vector3f(0.47F, -0.39F, 0.14F);
/*  84 */       public Vector3f armChargeRot = new Vector3f(0.0F, 0.0F, -90.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Sprint
/*     */   {
/*  90 */     public Vector3f sprintRotate = new Vector3f(-20.0F, 30.0F, -0.0F);
/*  91 */     public Vector3f sprintTranslate = new Vector3f(0.5F, -0.1F, -0.65F);
/*     */   }
/*     */   
/*     */   public static class ThirdPerson
/*     */   {
/*  96 */     public Vector3f thirdPersonOffset = new Vector3f(0.0F, -0.1F, 0.0F);
/*  97 */     public Vector3f backPersonOffset = new Vector3f(0.0F, 0.0F, 0.0F);
/*  98 */     public float thirdPersonScale = 0.8F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Aim
/*     */   {
/* 105 */     public Vector3f rotateHipPosition = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */     
/* 107 */     public Vector3f translateHipPosition = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */     
/* 109 */     public Vector3f rotateAimPosition = new Vector3f(0.0F, 0.065F, 0.3F);
/*     */     
/* 111 */     public Vector3f translateAimPosition = new Vector3f(0.14F, 0.01F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Bolt
/*     */   {
/* 118 */     public float boltRotation = 0.0F;
/*     */ 
/*     */ 
/*     */     
/* 122 */     public Vector3f boltRotationPoint = new Vector3f();
/* 123 */     public Vector3f chargeModifier = new Vector3f(0.3F, 0.0F, 0.0F);
/*     */     
/* 125 */     public float pumpHandleDistance = 0.25F;
/*     */   }
/*     */   
/*     */   public static class Attachments
/*     */   {
/* 130 */     public HashMap<AttachmentPresetEnum, ArrayList<Vector3f>> attachmentPointMap = new HashMap<>();
/*     */     
/* 132 */     public HashMap<String, ArrayList<Vector3f>> positionPointMap = new HashMap<>();
/* 133 */     public HashMap<String, ArrayList<Vector3f>> aimPointMap = new HashMap<>();
/*     */     
/* 135 */     public Vector3f attachmentModeRotate = new Vector3f(10.0F, 30.0F, 0.0F);
/*     */     public boolean scopeIsOnSlide = false;
/*     */   }
/*     */   
/*     */   public static class Maps
/*     */   {
/* 141 */     public HashMap<String, RenderVariables> ammoMap = new HashMap<>();
/* 142 */     public HashMap<String, RenderVariables> bulletMap = new HashMap<>();
/*     */   }
/*     */   
/*     */   public static class BreakAction {
/* 146 */     public ArrayList<BreakActionData> breakActions = new ArrayList<>();
/*     */     
/*     */     public boolean scopeIsOnBreakAction = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class HammerAction
/*     */   {
/* 154 */     public Vector3f hammerRotationPoint = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public static class RevolverBarrel {
/* 158 */     public Vector3f cylinderOriginPoint = new Vector3f(0.0F, 0.0F, 0.0F);
/* 159 */     public Vector3f cylinderReloadTranslation = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */     public Integer numberBullets;
/*     */   }
/*     */   
/*     */   public static class ItemFrame {
/* 164 */     public Vector3f translate = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public static class Extra
/*     */   {
/* 169 */     public Vector3f translateAll = new Vector3f(1.0F, -1.02F, -0.07F);
/*     */     
/* 171 */     public float modelScale = 1.0F;
/*     */     
/* 173 */     public String reloadAnimation = WeaponAnimations.RIFLE;
/*     */     
/*     */     public boolean needExtraChargeModel = false;
/* 176 */     public float chargeHandleDistance = 0.0F;
/*     */ 
/*     */     
/* 179 */     public float gunOffsetScoping = 0.0F;
/*     */     
/* 181 */     public float crouchZoom = -0.035F;
/*     */ 
/*     */     
/* 184 */     public float adsSpeed = 0.02F;
/*     */ 
/*     */     
/* 187 */     public float gunSlideDistance = 0.25F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     public float modelRecoilBackwards = 0.15F;
/*     */ 
/*     */ 
/*     */     
/* 196 */     public float modelRecoilUpwards = 1.0F;
/*     */ 
/*     */ 
/*     */     
/* 200 */     public float modelRecoilShake = 0.5F;
/*     */     
/* 202 */     public float modelGuiScale = 1.0F;
/* 203 */     public Vector2f modelGuiRotateCenter = new Vector2f(0.0F, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\configs\GunRenderConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */