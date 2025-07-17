/*     */ package de.javagl.jgltf.model;
/*     */ 
/*     */ import de.javagl.jgltf.model.animation.Animation;
/*     */ import de.javagl.jgltf.model.animation.AnimationListener;
/*     */ import de.javagl.jgltf.model.animation.AnimationManager;
/*     */ import de.javagl.jgltf.model.animation.InterpolatorType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.logging.Logger;
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
/*     */ public class GltfAnimations
/*     */ {
/*  54 */   private static final Logger logger = Logger.getLogger(GltfAnimations.class.getName());
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
/*     */   public static AnimationManager createAnimationManager(AnimationManager.AnimationPolicy animationPolicy) {
/*  66 */     AnimationManager animationManager = new AnimationManager(animationPolicy);
/*     */     
/*  68 */     return animationManager;
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
/*     */   public static List<Animation> createModelAnimations(Iterable<? extends AnimationModel> animationModels) {
/*  81 */     Objects.requireNonNull(animationModels, "The animationModels may not be null");
/*     */     
/*  83 */     List<Animation> allModelAnimations = new ArrayList<>();
/*  84 */     for (AnimationModel animationModel : animationModels) {
/*     */       
/*  86 */       List<AnimationModel.Channel> channels = animationModel.getChannels();
/*     */       
/*  88 */       List<Animation> modelAnimations = createModelAnimationsForChannels(channels);
/*  89 */       allModelAnimations.addAll(modelAnimations);
/*     */     } 
/*  91 */     return allModelAnimations;
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
/*     */   private static List<Animation> createModelAnimationsForChannels(Iterable<? extends AnimationModel.Channel> channels) {
/* 106 */     List<Animation> modelAnimations = new ArrayList<>();
/* 107 */     for (AnimationModel.Channel channel : channels) {
/*     */       
/* 109 */       Animation modelAnimation = createModelAnimation(channel);
/* 110 */       if (modelAnimation != null)
/*     */       {
/* 112 */         modelAnimations.add(modelAnimation);
/*     */       }
/*     */     } 
/* 115 */     return modelAnimations;
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
/*     */   private static Animation createModelAnimation(AnimationModel.Channel channel) {
/* 130 */     AnimationModel.Sampler sampler = channel.getSampler();
/* 131 */     AnimationModel.Interpolation interpolation = sampler.getInterpolation();
/* 132 */     NodeModel nodeModel = channel.getNodeModel();
/* 133 */     String path = channel.getPath();
/*     */ 
/*     */     
/* 136 */     AnimationListener animationListener = createAnimationListener(nodeModel, path);
/* 137 */     if (animationListener == null)
/*     */     {
/* 139 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 143 */     InterpolatorType interpolatorType = typeForInterpolation(interpolation, path);
/*     */     
/* 145 */     AccessorModel input = sampler.getInput();
/* 146 */     AccessorData inputData = input.getAccessorData();
/* 147 */     if (!(inputData instanceof AccessorFloatData)) {
/*     */       
/* 149 */       logger.warning("Input data is not an AccessorFloatData, but " + inputData
/* 150 */           .getClass());
/* 151 */       return null;
/*     */     } 
/* 153 */     AccessorFloatData inputFloatData = (AccessorFloatData)inputData;
/*     */     
/* 155 */     AccessorModel output = sampler.getOutput();
/* 156 */     AccessorData outputData = output.getAccessorData();
/* 157 */     if (!(outputData instanceof AccessorFloatData)) {
/*     */       
/* 159 */       logger.warning("Output data is not an AccessorFloatData, but " + outputData
/* 160 */           .getClass());
/* 161 */       return null;
/*     */     } 
/* 163 */     AccessorFloatData outputFloatData = (AccessorFloatData)outputData;
/*     */ 
/*     */     
/* 166 */     Animation modelAnimation = createAnimation(inputFloatData, outputFloatData, interpolatorType);
/* 167 */     modelAnimation.addAnimationListener(animationListener);
/* 168 */     return modelAnimation;
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
/*     */   private static InterpolatorType typeForInterpolation(AnimationModel.Interpolation interpolation, String path) {
/* 182 */     switch (interpolation) {
/*     */ 
/*     */       
/*     */       case LINEAR:
/* 186 */         if (path.equals("rotation"))
/*     */         {
/* 188 */           return InterpolatorType.SLERP;
/*     */         }
/* 190 */         return InterpolatorType.LINEAR;
/*     */ 
/*     */       
/*     */       case STEP:
/* 194 */         return InterpolatorType.STEP;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     logger.warning("This interpolation type is not supported yet");
/*     */ 
/*     */     
/* 204 */     logger.warning("Interpolation type not supported: " + interpolation);
/*     */     
/* 206 */     return InterpolatorType.LINEAR;
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
/*     */   static Animation createAnimation(AccessorFloatData timeData, AccessorFloatData outputData, InterpolatorType interpolatorType) {
/* 227 */     int numKeyElements = timeData.getNumElements();
/* 228 */     float[] keys = new float[numKeyElements];
/* 229 */     for (int e = 0; e < numKeyElements; e++)
/*     */     {
/* 231 */       keys[e] = timeData.get(e);
/*     */     }
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
/* 243 */     int totalNumValueComponents = outputData.getTotalNumComponents();
/* 244 */     int numComponentsPerElement = totalNumValueComponents / numKeyElements;
/*     */     
/* 246 */     float[][] values = new float[numKeyElements][numComponentsPerElement];
/* 247 */     for (int c = 0; c < numComponentsPerElement; c++) {
/*     */       
/* 249 */       for (int i = 0; i < numKeyElements; i++) {
/*     */ 
/*     */ 
/*     */         
/* 253 */         int globalIndex = i * numComponentsPerElement + c;
/* 254 */         values[i][c] = outputData.get(globalIndex);
/*     */       } 
/*     */     } 
/* 257 */     return new Animation(keys, values, interpolatorType);
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
/*     */   private static AnimationListener createAnimationListener(NodeModel nodeModel, String path) {
/* 275 */     switch (path) {
/*     */       
/*     */       case "translation":
/* 278 */         return createTranslationAnimationListener(nodeModel);
/*     */       
/*     */       case "rotation":
/* 281 */         return createRotationAnimationListener(nodeModel);
/*     */       
/*     */       case "scale":
/* 284 */         return createScaleAnimationListener(nodeModel);
/*     */       
/*     */       case "weights":
/* 287 */         return createWeightsAnimationListener(nodeModel);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 292 */     logger.warning("Animation channel target path must be \"translation\", \"rotation\", \"scale\" or  \"weights\", but is " + path);
/*     */ 
/*     */     
/* 295 */     return null;
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
/*     */   private static AnimationListener createTranslationAnimationListener(NodeModel nodeModel) {
/* 309 */     return (animation, timeS, values) -> {
/*     */         float[] translation = nodeModel.getTranslation();
/*     */         if (translation == null) {
/*     */           translation = (float[])values.clone();
/*     */           nodeModel.setTranslation(translation);
/*     */         } else {
/*     */           System.arraycopy(values, 0, translation, 0, values.length);
/*     */         } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AnimationListener createRotationAnimationListener(NodeModel nodeModel) {
/* 335 */     return (animation, timeS, values) -> {
/*     */         float[] rotation = nodeModel.getRotation();
/*     */         if (rotation == null) {
/*     */           rotation = (float[])values.clone();
/*     */           nodeModel.setRotation(rotation);
/*     */         } else {
/*     */           System.arraycopy(values, 0, rotation, 0, values.length);
/*     */         } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AnimationListener createScaleAnimationListener(NodeModel nodeModel) {
/* 361 */     return (animation, timeS, values) -> {
/*     */         float[] scale = nodeModel.getScale();
/*     */         if (scale == null) {
/*     */           scale = (float[])values.clone();
/*     */           nodeModel.setScale(scale);
/*     */         } else {
/*     */           System.arraycopy(values, 0, scale, 0, values.length);
/*     */         } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AnimationListener createWeightsAnimationListener(NodeModel nodeModel) {
/* 387 */     return (animation, timeS, values) -> {
/*     */         float[] weights = nodeModel.getWeights();
/*     */         if (weights == null) {
/*     */           weights = (float[])values.clone();
/*     */           nodeModel.setWeights(weights);
/*     */         } else {
/*     */           System.arraycopy(values, 0, weights, 0, values.length);
/*     */         } 
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\GltfAnimations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */