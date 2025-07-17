/*     */ package com.modularwarfare.client.fpp.enhanced.configs;
/*     */ 
/*     */ import com.modularwarfare.client.fpp.enhanced.AnimationType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import org.lwjgl.util.vector.Vector2f;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GunEnhancedRenderConfig
/*     */   extends EnhancedRenderConfig
/*     */ {
/*  16 */   public HashMap<AnimationType, Animation> animations = new HashMap<>();
/*  17 */   public HashMap<String, ObjectControl> objectControl = new HashMap<>();
/*     */   
/*  19 */   public Global global = new Global();
/*  20 */   public Sprint sprint = new Sprint();
/*  21 */   public Aim aim = new Aim();
/*  22 */   public Extra extra = new Extra();
/*  23 */   public HashMap<String, Attachment> attachment = new HashMap<>();
/*  24 */   public HashMap<String, AttachmentGroup> attachmentGroup = new HashMap<>();
/*  25 */   public HashSet<String> defaultHidePart = new HashSet<>();
/*  26 */   public HashSet<String> thirdHidePart = new HashSet<>();
/*  27 */   public HashSet<String> thirdShowPart = new HashSet<>();
/*     */   
/*  29 */   public ThirdPerson thirdPerson = new ThirdPerson();
/*     */ 
/*     */   
/*     */   public static class Transform
/*     */   {
/*  34 */     public Vector3f translate = new Vector3f(0.0F, 0.0F, 0.0F);
/*  35 */     public Vector3f scale = new Vector3f(1.0F, 1.0F, 1.0F);
/*  36 */     public Vector3f rotate = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public static class ObjectControl extends Transform {
/*     */     public boolean progress;
/*     */   }
/*     */   
/*     */   public static class Animation {
/*  44 */     public double startTime = 0.0D;
/*  45 */     public double endTime = 1.0D;
/*  46 */     public double speed = 1.0D;
/*     */     
/*     */     public double getStartTime(double FPS) {
/*  49 */       return this.startTime * 1.0D / FPS;
/*     */     }
/*     */     
/*     */     public double getEndTime(double FPS) {
/*  53 */       return this.endTime * 1.0D / FPS;
/*     */     }
/*     */     
/*     */     public double getSpeed(double FPS) {
/*  57 */       double a = getEndTime(FPS) - getStartTime(FPS);
/*  58 */       if (a <= 0.0D) {
/*  59 */         a = 1.0D;
/*     */       }
/*  61 */       return this.speed / a;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Global
/*     */   {
/*  67 */     public Vector3f globalTranslate = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */     
/*  69 */     public Vector3f globalScale = new Vector3f(1.0F, 1.0F, 1.0F);
/*  70 */     public Vector3f globalRotate = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public static class Sprint {
/*  74 */     public Vector3f sprintRotate = new Vector3f(-20.0F, 30.0F, -0.0F);
/*  75 */     public Vector3f sprintTranslate = new Vector3f(0.5F, -0.1F, -0.65F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Aim
/*     */   {
/*  81 */     public Vector3f rotateHipPosition = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */     
/*  83 */     public Vector3f translateHipPosition = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */     
/*  85 */     public Vector3f rotateAimPosition = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */     
/*  87 */     public Vector3f translateAimPosition = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public static class Attachment extends Transform {
/*  91 */     public String binding = "gunModel";
/*  92 */     public Vector3f sightAimPosOffset = new Vector3f(0.0F, 0.0F, 0.0F);
/*  93 */     public Vector3f sightAimRotOffset = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */     public ArrayList<GunEnhancedRenderConfig.Transform> multiMagazineTransform;
/*  95 */     public HashSet<String> hidePart = new HashSet<>();
/*  96 */     public HashSet<String> showPart = new HashSet<>();
/*     */     public boolean renderInsideSightModel = false;
/*  98 */     public float renderInsideGunOffset = 5.0F;
/*  99 */     public Vector3f attachmentGuiOffset = new Vector3f(0.0F, 0.0F, 0.0F);
/* 100 */     public Vector3f flashModelOffset = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public static class AttachmentGroup extends Transform {
/* 104 */     public HashSet<String> hidePart = new HashSet<>();
/* 105 */     public HashSet<String> showPart = new HashSet<>();
/*     */   }
/*     */   
/*     */   public static class ThirdPerson
/*     */   {
/*     */     public static class RenderElement {
/* 111 */       public Vector3f pos = new Vector3f(0.0F, 0.0F, 0.0F);
/* 112 */       public Vector3f rot = new Vector3f(0.0F, 0.0F, 0.0F);
/* 113 */       public Vector3f size = new Vector3f(1.0F, 1.0F, 1.0F);
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean randomYaw = false;
/*     */     }
/*     */ 
/*     */     
/* 121 */     public HashMap<String, RenderElement> renderElements = new HashMap<String, RenderElement>()
/*     */       {
/*     */       
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Extra
/*     */   {
/* 136 */     public float modelRecoilBackwards = 0.15F;
/*     */ 
/*     */ 
/*     */     
/* 140 */     public float modelRecoilUpwards = 1.0F;
/*     */ 
/*     */ 
/*     */     
/* 144 */     public float modelRecoilShake = 0.5F;
/* 145 */     public float modelGuiScale = 1.0F;
/* 146 */     public Vector2f modelGuiRotateCenter = new Vector2f(0.0F, 0.0F);
/*     */     
/* 148 */     public float bobbingFactor = 1.0F;
/*     */     public float shellYawOffset;
/*     */     public float shellPitchOffset;
/*     */     public float shellForwardOffset;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\enhanced\configs\GunEnhancedRenderConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */