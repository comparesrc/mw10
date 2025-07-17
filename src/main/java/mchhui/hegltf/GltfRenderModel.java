/*     */ package mchhui.hegltf;
/*     */ 
/*     */ import com.modularwarfare.utility.OptifineHelper;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3fc;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL15;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.opengl.GL30;
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
/*     */ public class GltfRenderModel
/*     */ {
/*  33 */   private static final HashSet<String> setObj = new HashSet<>();
/*  34 */   private static final FloatBuffer MATRIX_BUFFER = BufferUtils.createFloatBuffer(16);
/*  35 */   private static final Comparator<DataMaterial> COMPARATOR_MATE = new Comparator<DataMaterial>()
/*     */     {
/*     */       
/*     */       public int compare(Object o1, Object o2)
/*     */       {
/*  40 */         return (((DataMaterial)o1).isTranslucent && !((DataMaterial)o2).isTranslucent) ? 1 : -1;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  45 */   public HashMap<String, NodeState> nodeStates = new HashMap<>();
/*     */   
/*     */   public NodeAnimationBlender animationCalBlender;
/*     */   
/*     */   public NodeAnimationMapper animationLoadMapper;
/*     */   
/*     */   public GltfDataModel geoModel;
/*     */   public GltfDataModel lastAniModel;
/*     */   public GltfDataModel aniModel;
/*     */   protected boolean initedNodeStates = false;
/*  55 */   protected int jointMatsBufferId = -1;
/*     */   
/*     */   public static class NodeState {
/*  58 */     public Matrix4f mat = new Matrix4f();
/*     */   }
/*     */   
/*     */   public static class NodeAnimationBlender {
/*     */     public String name;
/*     */     
/*     */     public NodeAnimationBlender(String name) {
/*  65 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public void handle(DataNode node, Matrix4f mat) {}
/*     */   }
/*     */   
/*     */   public static class NodeAnimationMapper
/*     */   {
/*     */     public String name;
/*     */     
/*     */     public NodeAnimationMapper(String name) {
/*  77 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public void handle(GltfRenderModel model, GltfRenderModel other, String target) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNodeAnimationCalBlender(NodeAnimationBlender blender) {
/*  86 */     this.animationCalBlender = blender;
/*     */   }
/*     */   
/*     */   public void setNodeAnimationLoadMapper(NodeAnimationMapper mapper) {
/*  90 */     this.animationLoadMapper = mapper;
/*     */   }
/*     */   
/*     */   public GltfRenderModel(GltfDataModel geoModel) {
/*  94 */     this.geoModel = geoModel;
/*     */   }
/*     */   
/*     */   public void calculateAllNodePose(float time) {
/*  98 */     if (!this.initedNodeStates) {
/*  99 */       this.geoModel.nodes.keySet().forEach(name -> this.nodeStates.put(name, new NodeState()));
/*     */ 
/*     */       
/* 102 */       this.initedNodeStates = true;
/*     */     } 
/* 104 */     for (Map.Entry<String, DataNode> entry : this.geoModel.rootNodes.entrySet()) {
/* 105 */       calculateNodeAndChildren(entry.getValue(), null, time);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void calculateNodeAndChildren(DataNode node, Matrix4f parent, float time) {
/* 111 */     Matrix4f matrix = new Matrix4f();
/* 112 */     DataAnimation animation = this.geoModel.animations.get(node.name);
/* 113 */     if (animation != null) {
/* 114 */       DataAnimation.Transform trans = animation.findTransform(time, node.pos, node.size, node.rot);
/* 115 */       matrix.translate(trans.pos.x, trans.pos.y, trans.pos.z);
/* 116 */       matrix.rotate((Quaternionfc)trans.rot);
/* 117 */       matrix.scale(trans.size.x, trans.size.y, trans.size.z);
/*     */     } else {
/* 119 */       matrix.translate((Vector3fc)node.pos);
/* 120 */       matrix.rotate((Quaternionfc)node.rot);
/* 121 */       matrix.scale((Vector3fc)node.size);
/*     */     } 
/*     */     
/* 124 */     if (this.animationCalBlender != null) {
/* 125 */       this.animationCalBlender.handle(node, matrix);
/*     */     }
/*     */     
/* 128 */     if (parent != null) {
/* 129 */       matrix.mulLocal((Matrix4fc)parent);
/*     */     }
/*     */     
/* 132 */     ((NodeState)this.nodeStates.get(node.name)).mat = matrix;
/* 133 */     for (String name : node.childlist) {
/* 134 */       calculateNodeAndChildren(this.geoModel.nodes.get(name), matrix, time);
/*     */     }
/*     */   }
/*     */   
/*     */   public void uploadAllJointTransform() {
/* 139 */     if (this.geoModel.joints.size() == 0) {
/*     */       return;
/*     */     }
/* 142 */     if (this.jointMatsBufferId == -1) {
/* 143 */       this.jointMatsBufferId = GL15.glGenBuffers();
/* 144 */       GL15.glBindBuffer(37074, this.jointMatsBufferId);
/* 145 */       GL15.glBufferData(37074, (this.geoModel.joints.size() * 64), 35048);
/* 146 */       GL15.glBindBuffer(37074, 0);
/*     */     } 
/*     */     
/* 149 */     GL15.glBindBuffer(37074, this.jointMatsBufferId);
/* 150 */     for (int i = 0; i < this.geoModel.joints.size(); i++) {
/* 151 */       Matrix4f inv = this.geoModel.inverseBindMatrices.get(i);
/* 152 */       Matrix4f pose = ((NodeState)this.nodeStates.get(this.geoModel.joints.get(i))).mat;
/* 153 */       Matrix4f result = new Matrix4f((Matrix4fc)pose);
/* 154 */       result.mul((Matrix4fc)inv);
/* 155 */       GL15.glBufferSubData(37074, (i * 64), result.get(MATRIX_BUFFER));
/*     */     } 
/* 157 */     GL15.glBindBuffer(37074, 0);
/*     */   }
/*     */   
/*     */   public void skinNodeAndChildren(DataNode node, HashSet<String> sun, HashSet<String> moon) {
/* 161 */     if (sun != null && !sun.isEmpty() && !sun.contains(node.name)) {
/*     */       return;
/*     */     }
/* 164 */     if (moon != null && !moon.isEmpty() && moon.contains(node.name)) {
/*     */       return;
/*     */     }
/* 167 */     if (this.geoModel.joints.size() == 0) {
/*     */       return;
/*     */     }
/* 170 */     if (this.jointMatsBufferId == -1) {
/*     */       return;
/*     */     }
/* 173 */     node.meshes.values().forEach(mesh -> mesh.callSkinning());
/*     */ 
/*     */     
/* 176 */     node.childlist.forEach(child -> skinNodeAndChildren(this.geoModel.nodes.get(child), sun, moon));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean loadAnimation(GltfRenderModel other, boolean skin) {
/* 182 */     if (!other.initedNodeStates) {
/* 183 */       return false;
/*     */     }
/* 185 */     if (!this.initedNodeStates) {
/* 186 */       this.geoModel.nodes.keySet().forEach(name -> this.nodeStates.put(name, new NodeState()));
/*     */ 
/*     */       
/* 189 */       this.initedNodeStates = true;
/*     */     } 
/* 191 */     this.nodeStates.forEach((k, v) -> {
/*     */           NodeState s = other.nodeStates.get(k);
/*     */           if (s != null) {
/*     */             v.mat.set((Matrix4fc)s.mat);
/*     */           }
/*     */           if (this.animationLoadMapper != null) {
/*     */             this.animationLoadMapper.handle(this, other, k);
/*     */           }
/*     */         });
/* 200 */     if (skin && 
/* 201 */       this.geoModel.joints.size() > 0) {
/* 202 */       uploadAllJointTransform();
/* 203 */       ShaderGltf.useShader();
/* 204 */       GL30.glBindBufferBase(37074, 0, this.jointMatsBufferId);
/*     */ 
/*     */       
/* 207 */       GL11.glEnable(35977);
/* 208 */       for (Map.Entry<String, DataNode> e : this.geoModel.rootNodes.entrySet()) {
/* 209 */         skinNodeAndChildren(e.getValue(), null, null);
/*     */       }
/* 211 */       GL11.glDisable(35977);
/*     */       
/* 213 */       GL30.glBindBufferBase(37074, 0, 0);
/* 214 */       GL30.glBindBufferBase(37074, 3, 0);
/* 215 */       if (OptifineHelper.isShadersEnabled()) {
/* 216 */         GL20.glUseProgram(OptifineHelper.getProgram());
/*     */       } else {
/* 218 */         GL20.glUseProgram(0);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 223 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean updateAnimation(float time, boolean skin) {
/* 228 */     if (!this.geoModel.loaded) {
/* 229 */       return false;
/*     */     }
/* 231 */     calculateAllNodePose(time);
/* 232 */     if (skin && 
/* 233 */       this.geoModel.joints.size() > 0) {
/* 234 */       uploadAllJointTransform();
/* 235 */       ShaderGltf.useShader();
/* 236 */       GL30.glBindBufferBase(37074, 0, this.jointMatsBufferId);
/*     */ 
/*     */       
/* 239 */       GL11.glEnable(35977);
/* 240 */       for (Map.Entry<String, DataNode> e : this.geoModel.rootNodes.entrySet()) {
/* 241 */         skinNodeAndChildren(e.getValue(), null, null);
/*     */       }
/* 243 */       GL11.glDisable(35977);
/*     */       
/* 245 */       GL30.glBindBufferBase(37074, 0, 0);
/* 246 */       GL30.glBindBufferBase(37074, 3, 0);
/* 247 */       if (OptifineHelper.isShadersEnabled()) {
/* 248 */         GL20.glUseProgram(OptifineHelper.getProgram());
/*     */       } else {
/* 250 */         GL20.glUseProgram(0);
/*     */       } 
/*     */     } 
/*     */     
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(HashSet<String> sun, HashSet<String> moon) {
/* 259 */     GlStateManager.func_179128_n(5888);
/* 260 */     if (!this.geoModel.loaded) {
/*     */       return;
/*     */     }
/* 263 */     for (Map.Entry<String, DataNode> e : this.geoModel.nodes.entrySet()) {
/* 264 */       if (sun != null && !sun.isEmpty() && !sun.contains(e.getKey())) {
/*     */         continue;
/*     */       }
/* 267 */       if (moon != null && !moon.isEmpty() && moon.contains(e.getKey())) {
/*     */         continue;
/*     */       }
/* 270 */       ((DataNode)e.getValue()).meshes.values().forEach(mesh -> {
/*     */             GlStateManager.func_179094_E();
/*     */             if (!mesh.skin) {
/*     */               GlStateManager.func_179110_a(((NodeState)this.nodeStates.get(((DataNode)e.getValue()).name)).mat.get(MATRIX_BUFFER));
/*     */             }
/*     */             mesh.render();
/*     */             GlStateManager.func_179121_F();
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderAll() {
/* 282 */     render(null, null);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void renderPart(String part) {
/* 287 */     HashSet<String> set = setObj;
/* 288 */     setObj.clear();
/* 289 */     set.add(part);
/* 290 */     render(set, null);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void renderOnly(String[] part) {
/* 295 */     HashSet<String> set = setObj;
/* 296 */     setObj.clear();
/* 297 */     for (int i = 0; i < part.length; i++) {
/* 298 */       set.add(part[i]);
/*     */     }
/* 300 */     renderOnly(set);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void renderExcept(String[] part) {
/* 305 */     HashSet<String> set = setObj;
/* 306 */     setObj.clear();
/* 307 */     for (int i = 0; i < part.length; i++) {
/* 308 */       set.add(part[i]);
/*     */     }
/* 310 */     renderExcept(set);
/*     */   }
/*     */   
/*     */   public void renderOnly(HashSet<String> part) {
/* 314 */     render(part, null);
/*     */   }
/*     */   
/*     */   public void renderExcept(HashSet<String> part) {
/* 318 */     render(null, part);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\hegltf\GltfRenderModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */