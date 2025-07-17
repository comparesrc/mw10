/*     */ package com.modularwarfare.client.fpp.enhanced.models;
/*     */ 
/*     */ import com.modularwarfare.api.IMWModel;
/*     */ import com.modularwarfare.client.fpp.enhanced.configs.EnhancedRenderConfig;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import de.javagl.jgltf.model.NodeModel;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import mchhui.hegltf.DataAnimation;
/*     */ import mchhui.hegltf.DataNode;
/*     */ import mchhui.hegltf.GltfDataModel;
/*     */ import mchhui.hegltf.GltfRenderModel;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.lwjgl.BufferUtils;
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
/*     */ public class EnhancedModel
/*     */   implements IMWModel
/*     */ {
/*  43 */   private static final FloatBuffer MATRIX_BUFFER = BufferUtils.createFloatBuffer(16);
/*  44 */   private static final HashMap<ResourceLocation, GltfDataModel> modelCache = new HashMap<>();
/*     */   public EnhancedRenderConfig config;
/*     */   public BaseType baseType;
/*     */   public GltfRenderModel model;
/*     */   public boolean initCal = false;
/*  49 */   private HashMap<String, Matrix4f> invMatCache = new HashMap<>();
/*     */   
/*     */   public EnhancedModel(EnhancedRenderConfig config, BaseType baseType) {
/*  52 */     this.config = config;
/*  53 */     this.baseType = baseType;
/*  54 */     if (!modelCache.containsKey(getModelLocation())) {
/*  55 */       modelCache.put(getModelLocation(), GltfDataModel.load(getModelLocation()));
/*     */     }
/*  57 */     this.model = new GltfRenderModel(modelCache.get(getModelLocation()));
/*     */   }
/*     */   
/*     */   public static void clearCache() {
/*  61 */     modelCache.values().forEach(model -> model.delete());
/*     */ 
/*     */     
/*  64 */     modelCache.clear();
/*     */   }
/*     */   
/*     */   public ResourceLocation getModelLocation() {
/*  68 */     return new ResourceLocation("modularwarfare", "gltf/" + this.baseType
/*  69 */         .getAssetDir() + "/" + this.config.modelFileName);
/*     */   }
/*     */   
/*     */   public void loadAnimation(EnhancedModel other, boolean skin) {
/*  73 */     if (this.model == null || other == null || other.model == null) {
/*     */       return;
/*     */     }
/*  76 */     this.model.loadAnimation(other.model, skin);
/*     */   }
/*     */   
/*     */   public void updateAnimation(float time, boolean skin) {
/*  80 */     this.invMatCache.clear();
/*  81 */     this.initCal = this.model.updateAnimation(time, (skin || !this.initCal));
/*     */   }
/*     */   
/*     */   public DataAnimation.Transform findLocalTransform(String name, float time) {
/*  85 */     if (this.model == null) {
/*  86 */       return null;
/*     */     }
/*  88 */     DataNode node = (DataNode)this.model.geoModel.nodes.get(name);
/*  89 */     if (node == null) {
/*  90 */       return null;
/*     */     }
/*  92 */     DataAnimation ani = (DataAnimation)this.model.geoModel.animations.get(name);
/*  93 */     if (ani == null) {
/*  94 */       return null;
/*     */     }
/*  96 */     return ((DataAnimation)this.model.geoModel.animations.get(name)).findTransform(time, node.pos, node.size, node.rot);
/*     */   }
/*     */   
/*     */   public void setAnimationCalBlender(GltfRenderModel.NodeAnimationBlender blender) {
/* 100 */     this.model.setNodeAnimationCalBlender(blender);
/*     */   }
/*     */   
/*     */   public void setAnimationLoadMapper(GltfRenderModel.NodeAnimationMapper mapper) {
/* 104 */     this.model.setNodeAnimationLoadMapper(mapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void updateAnimation(float time) {
/* 112 */     updateAnimation(time, true);
/*     */   }
/*     */   
/*     */   public boolean existPart(String part) {
/* 116 */     return this.model.geoModel.nodes.containsKey(part);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public NodeModel getPart(String part) {
/* 124 */     DataNode node = (DataNode)this.model.geoModel.nodes.get(part);
/* 125 */     if (node == null) {
/* 126 */       return null;
/*     */     }
/* 128 */     return node.unsafeNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderPart(String part, float scale) {
/* 133 */     if (!this.initCal) {
/*     */       return;
/*     */     }
/* 136 */     this.model.renderPart(part);
/*     */   }
/*     */   
/*     */   public void renderPart(String part) {
/* 140 */     if (!this.initCal) {
/*     */       return;
/*     */     }
/* 143 */     this.model.renderPart(part);
/*     */   }
/*     */   
/*     */   public void renderPartExcept(HashSet<String> set) {
/* 147 */     if (!this.initCal) {
/*     */       return;
/*     */     }
/* 150 */     this.model.renderExcept(set);
/*     */   }
/*     */   
/*     */   public void renderPart(String[] only) {
/* 154 */     if (!this.initCal) {
/*     */       return;
/*     */     }
/* 157 */     this.model.renderOnly(only);
/*     */   }
/*     */   
/*     */   public Matrix4f getGlobalTransform(String name) {
/* 161 */     if (!this.initCal) {
/* 162 */       return new Matrix4f();
/*     */     }
/* 164 */     GltfRenderModel.NodeState state = (GltfRenderModel.NodeState)this.model.nodeStates.get(name);
/* 165 */     if (state == null) {
/* 166 */       return new Matrix4f();
/*     */     }
/* 168 */     return state.mat;
/*     */   }
/*     */   
/*     */   public void applyGlobalTransformToOther(String binding, Runnable run) {
/* 172 */     if (!this.initCal) {
/*     */       return;
/*     */     }
/* 175 */     GltfRenderModel.NodeState state = (GltfRenderModel.NodeState)this.model.nodeStates.get(binding);
/* 176 */     if (state == null) {
/*     */       return;
/*     */     }
/* 179 */     GlStateManager.func_179094_E();
/* 180 */     if (state != null) {
/* 181 */       GlStateManager.func_179110_a(state.mat.get(MATRIX_BUFFER));
/*     */     }
/* 183 */     run.run();
/*     */     
/* 185 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public void applyGlobalInverseTransformToOther(String binding, Runnable run) {
/* 189 */     if (!this.initCal) {
/*     */       return;
/*     */     }
/* 192 */     GltfRenderModel.NodeState state = (GltfRenderModel.NodeState)this.model.nodeStates.get(binding);
/* 193 */     GlStateManager.func_179094_E();
/* 194 */     if (state != null) {
/* 195 */       Matrix4f invmat = this.invMatCache.get(binding);
/* 196 */       if (invmat == null) {
/* 197 */         invmat = (new Matrix4f((Matrix4fc)state.mat)).invert();
/* 198 */         this.invMatCache.put(binding, invmat);
/*     */       } 
/* 200 */       GlStateManager.func_179110_a(invmat.get(MATRIX_BUFFER));
/*     */     } 
/* 202 */     run.run();
/*     */     
/* 204 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\enhanced\models\EnhancedModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */