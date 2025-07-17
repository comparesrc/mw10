/*     */ package de.javagl.jgltf.impl.v2;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public class Animation
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private List<AnimationChannel> channels;
/*     */   private List<AnimationSampler> samplers;
/*     */   
/*     */   public void setChannels(List<AnimationChannel> channels) {
/*  66 */     if (channels == null) {
/*  67 */       throw new NullPointerException("Invalid value for channels: " + channels + ", may not be null");
/*     */     }
/*  69 */     if (channels.size() < 1) {
/*  70 */       throw new IllegalArgumentException("Number of channels elements is < 1");
/*     */     }
/*  72 */     this.channels = channels;
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
/*     */   public List<AnimationChannel> getChannels() {
/*  89 */     return this.channels;
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
/*     */   public void addChannels(AnimationChannel element) {
/* 102 */     if (element == null) {
/* 103 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 105 */     List<AnimationChannel> oldList = this.channels;
/* 106 */     List<AnimationChannel> newList = new ArrayList<>();
/* 107 */     if (oldList != null) {
/* 108 */       newList.addAll(oldList);
/*     */     }
/* 110 */     newList.add(element);
/* 111 */     this.channels = newList;
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
/*     */   public void removeChannels(AnimationChannel element) {
/* 124 */     if (element == null) {
/* 125 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 127 */     List<AnimationChannel> oldList = this.channels;
/* 128 */     List<AnimationChannel> newList = new ArrayList<>();
/* 129 */     if (oldList != null) {
/* 130 */       newList.addAll(oldList);
/*     */     }
/* 132 */     newList.remove(element);
/* 133 */     this.channels = newList;
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
/*     */   public void setSamplers(List<AnimationSampler> samplers) {
/* 152 */     if (samplers == null) {
/* 153 */       throw new NullPointerException("Invalid value for samplers: " + samplers + ", may not be null");
/*     */     }
/* 155 */     if (samplers.size() < 1) {
/* 156 */       throw new IllegalArgumentException("Number of samplers elements is < 1");
/*     */     }
/* 158 */     this.samplers = samplers;
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
/*     */   public List<AnimationSampler> getSamplers() {
/* 174 */     return this.samplers;
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
/*     */   public void addSamplers(AnimationSampler element) {
/* 187 */     if (element == null) {
/* 188 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 190 */     List<AnimationSampler> oldList = this.samplers;
/* 191 */     List<AnimationSampler> newList = new ArrayList<>();
/* 192 */     if (oldList != null) {
/* 193 */       newList.addAll(oldList);
/*     */     }
/* 195 */     newList.add(element);
/* 196 */     this.samplers = newList;
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
/*     */   public void removeSamplers(AnimationSampler element) {
/* 209 */     if (element == null) {
/* 210 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 212 */     List<AnimationSampler> oldList = this.samplers;
/* 213 */     List<AnimationSampler> newList = new ArrayList<>();
/* 214 */     if (oldList != null) {
/* 215 */       newList.addAll(oldList);
/*     */     }
/* 217 */     newList.remove(element);
/* 218 */     this.samplers = newList;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Animation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */