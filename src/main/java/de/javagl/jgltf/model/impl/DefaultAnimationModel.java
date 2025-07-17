/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.AccessorModel;
/*     */ import de.javagl.jgltf.model.AnimationModel;
/*     */ import de.javagl.jgltf.model.NodeModel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ public class DefaultAnimationModel
/*     */   extends AbstractNamedModelElement
/*     */   implements AnimationModel
/*     */ {
/*     */   private final List<AnimationModel.Channel> channels;
/*     */   
/*     */   public static class DefaultSampler
/*     */     implements AnimationModel.Sampler
/*     */   {
/*     */     private final AccessorModel input;
/*     */     private final AnimationModel.Interpolation interpolation;
/*     */     private final AccessorModel output;
/*     */     
/*     */     public DefaultSampler(AccessorModel input, AnimationModel.Interpolation interpolation, AccessorModel output) {
/*  77 */       this.input = Objects.<AccessorModel>requireNonNull(input, "The input may not be null");
/*     */       
/*  79 */       this.interpolation = Objects.<AnimationModel.Interpolation>requireNonNull(interpolation, "The interpolation may not be null");
/*     */       
/*  81 */       this.output = Objects.<AccessorModel>requireNonNull(output, "The output may not be null");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessorModel getInput() {
/*  88 */       return this.input;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AnimationModel.Interpolation getInterpolation() {
/*  94 */       return this.interpolation;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessorModel getOutput() {
/* 100 */       return this.output;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DefaultChannel
/*     */     implements AnimationModel.Channel
/*     */   {
/*     */     private final AnimationModel.Sampler sampler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final NodeModel nodeModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String path;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DefaultChannel(AnimationModel.Sampler sampler, NodeModel nodeModel, String path) {
/* 137 */       this.sampler = Objects.<AnimationModel.Sampler>requireNonNull(sampler, "The sampler may not be null");
/*     */       
/* 139 */       this.nodeModel = nodeModel;
/* 140 */       this.path = Objects.<String>requireNonNull(path, "The path may not be null");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AnimationModel.Sampler getSampler() {
/* 148 */       return this.sampler;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public NodeModel getNodeModel() {
/* 154 */       return this.nodeModel;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPath() {
/* 160 */       return this.path;
/*     */     }
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
/*     */   public DefaultAnimationModel() {
/* 176 */     this.channels = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChannel(AnimationModel.Channel channel) {
/* 186 */     Objects.requireNonNull(channel, "The channel may not be null");
/* 187 */     this.channels.add(channel);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AnimationModel.Channel> getChannels() {
/* 193 */     return Collections.unmodifiableList(this.channels);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultAnimationModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */