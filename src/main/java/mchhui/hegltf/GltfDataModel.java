/*     */ package mchhui.hegltf;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import de.javagl.jgltf.model.AccessorModel;
/*     */ import de.javagl.jgltf.model.AnimationModel;
/*     */ import de.javagl.jgltf.model.GltfModel;
/*     */ import de.javagl.jgltf.model.MaterialModel;
/*     */ import de.javagl.jgltf.model.MeshModel;
/*     */ import de.javagl.jgltf.model.MeshPrimitiveModel;
/*     */ import de.javagl.jgltf.model.NodeModel;
/*     */ import de.javagl.jgltf.model.SkinModel;
/*     */ import de.javagl.jgltf.model.io.GltfModelReader;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Vector2f;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector4f;
/*     */ import org.joml.Vector4i;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ public class GltfDataModel {
/*  29 */   private static final GltfModelReader READER = new GltfModelReader();
/*     */   
/*  31 */   private static final Comparator<DataAnimation.DataKeyframe> COMPARATOR_ANI = new Comparator<DataAnimation.DataKeyframe>()
/*     */     {
/*     */       
/*     */       public int compare(Object o1, Object o2)
/*     */       {
/*  36 */         return (((DataAnimation.DataKeyframe)o1).time > ((DataAnimation.DataKeyframe)o2).time) ? 1 : -1;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  41 */   private String lastPos = "unkown";
/*     */ 
/*     */   
/*  44 */   public HashMap<String, DataAnimation> animations = new HashMap<>();
/*     */ 
/*     */   
/*  47 */   public HashMap<String, DataMaterial> materials = new HashMap<>();
/*  48 */   public HashMap<String, DataNode> nodes = new HashMap<>();
/*  49 */   public HashMap<String, DataNode> rootNodes = new HashMap<>();
/*  50 */   public ArrayList<String> joints = new ArrayList<>();
/*  51 */   public ArrayList<Matrix4f> inverseBindMatrices = new ArrayList<>();
/*  52 */   public String skeleton = "";
/*     */   
/*     */   public boolean loaded = false;
/*     */   
/*  56 */   public static int count = 0;
/*     */   
/*     */   public static GltfDataModel load(ResourceLocation loc) {
/*  59 */     count++;
/*     */     
/*  61 */     GltfDataModel gltfDataModel = new GltfDataModel();
/*     */     try {
/*  63 */       InputStream inputStream = Minecraft.func_71410_x().func_110442_L().func_110536_a(loc).func_110527_b();
/*  64 */       if (inputStream == null) {
/*  65 */         throw new RuntimeException("File not found:" + loc);
/*     */       }
/*  67 */       GltfModel model = READER.readWithoutReferences(inputStream);
/*     */       try {
/*  69 */         inputStream.close();
/*  70 */       } catch (Exception e) {
/*  71 */         e.printStackTrace();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  76 */       gltfDataModel.lastPos = "materials";
/*  77 */       model.getMaterialModels().forEach(materialModel -> {
/*     */             DataMaterial mate = new DataMaterial();
/*     */             
/*     */             if (gltfDataModel.materials.containsKey(materialModel.getName())) {
/*     */               throw new RuntimeException("the same material name");
/*     */             }
/*     */             
/*     */             gltfDataModel.materials.put(materialModel.getName(), mate);
/*     */             
/*     */             mate.name = materialModel.getName();
/*     */             
/*     */             Map map = (Map)materialModel.getExtras();
/*     */             if (map != null) {
/*     */               if (map.containsKey("isGlow")) {
/*     */                 mate.isGlow = ((Boolean)map.get("isGlow")).booleanValue();
/*     */               }
/*     */               if (map.containsKey("isTranslucent")) {
/*     */                 mate.isTranslucent = ((Boolean)map.get("isTranslucent")).booleanValue();
/*     */               }
/*     */             } 
/*     */           });
/*  98 */       gltfDataModel.lastPos = "animations";
/*  99 */       model.getAnimationModels().forEach(animationModel -> animationModel.getChannels().forEach(()));
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
/* 137 */       gltfDataModel.animations.values().forEach(ani -> {
/*     */             ani.posChannel.sort(COMPARATOR_ANI);
/*     */ 
/*     */             
/*     */             ani.rotChannel.sort(COMPARATOR_ANI);
/*     */             
/*     */             ani.sizeChannel.sort(COMPARATOR_ANI);
/*     */           });
/*     */       
/* 146 */       gltfDataModel.lastPos = "skin";
/* 147 */       if (model.getSkinModels().size() > 1) {
/* 148 */         throw new RuntimeException("Skin model is more than one");
/*     */       }
/* 150 */       model.getSkinModels().forEach(skinModel -> {
/*     */             if (skinModel.getSkeleton() != null) {
/*     */               gltfDataModel.skeleton = skinModel.getSkeleton().getName();
/*     */             } else {
/*     */               gltfDataModel.skeleton = skinModel.getName();
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/*     */             skinModel.getJoints().forEach(());
/*     */ 
/*     */ 
/*     */             
/*     */             ByteBuffer invMatsBuffer = skinModel.getInverseBindMatrices().getBufferViewModel().getBufferViewData();
/*     */ 
/*     */ 
/*     */             
/*     */             while (invMatsBuffer.hasRemaining()) {
/*     */               gltfDataModel.inverseBindMatrices.add(new Matrix4f(invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat(), invMatsBuffer.getFloat()));
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 173 */       model.getNodeModels().forEach(nodeModel -> {
/*     */             gltfDataModel.lastPos = "nodes";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             DataNode node = new DataNode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             node.unsafeNode = nodeModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             if (gltfDataModel.nodes.containsKey(nodeModel.getName())) {
/*     */               throw new RuntimeException("The same node name");
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             gltfDataModel.nodes.put(nodeModel.getName(), node);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             if (nodeModel.getParent() == null) {
/*     */               gltfDataModel.rootNodes.put(nodeModel.getName(), node);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             node.name = nodeModel.getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             if (nodeModel.getParent() != null) {
/*     */               node.parent = nodeModel.getParent().getName();
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             if (nodeModel.getTranslation() != null) {
/*     */               node.pos = new Vector3f(nodeModel.getTranslation());
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             if (nodeModel.getRotation() != null) {
/*     */               node.rot = new Quaternionf(nodeModel.getRotation()[0], nodeModel.getRotation()[1], nodeModel.getRotation()[2], nodeModel.getRotation()[3]);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             if (nodeModel.getScale() != null) {
/*     */               node.size = new Vector3f(nodeModel.getScale());
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             nodeModel.getChildren().forEach(());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             gltfDataModel.lastPos = "nodes(meshes)";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             nodeModel.getMeshModels().forEach(());
/*     */           });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 306 */       gltfDataModel.loaded = true;
/* 307 */     } catch (Throwable e) {
/* 308 */       ModularWarfare.LOGGER.warn("Something is wrong when loading:" + loc);
/* 309 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 312 */     return gltfDataModel;
/*     */   }
/*     */   
/*     */   public static int getIndice(ByteBuffer buf, int type) {
/* 316 */     if (type == 5121)
/* 317 */       return buf.get() & 0xFF; 
/* 318 */     if (type == 5123) {
/* 319 */       return buf.getShort() & 0xFFFF;
/*     */     }
/* 321 */     return buf.getInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void readAccessorToList(ByteBuffer buf, List list, int type) {
/* 326 */     readAccessorToList(buf, list, type, 5126);
/*     */   }
/*     */   
/*     */   public static void readAccessorToList(ByteBuffer buf, List<Vector2f> list, int type, int mode) {
/* 330 */     while (buf.hasRemaining()) {
/* 331 */       if (type == 2) {
/* 332 */         list.add(new Vector2f(buf.getFloat(), buf.getFloat())); continue;
/* 333 */       }  if (type == 3) {
/* 334 */         list.add(new Vector3f(buf.getFloat(), buf.getFloat(), buf.getFloat())); continue;
/* 335 */       }  if (type == 4) {
/* 336 */         if (mode == 5121 || mode == 5120) {
/*     */           
/* 338 */           list.add(new Vector4i(buf.get() & 0xFF, buf.get() & 0xFF, buf.get() & 0xFF, buf.get() & 0xFF)); continue;
/* 339 */         }  if (mode == 5123 || mode == 5122) {
/* 340 */           list.add(new Vector4i(buf.getShort() & 0xFFFF, buf.getShort() & 0xFFFF, buf.getShort() & 0xFFFF, buf
/* 341 */                 .getShort() & 0xFFFF)); continue;
/* 342 */         }  if (mode == 5125 || mode == 5124) {
/*     */           
/* 344 */           list.add(new Vector4i(buf.getInt(), buf.getInt(), buf.getInt(), buf.getInt())); continue;
/* 345 */         }  if (mode == 5126) {
/* 346 */           list.add(new Vector4f(buf.getFloat(), buf.getFloat(), buf.getFloat(), buf.getFloat())); continue;
/*     */         } 
/* 348 */         throw new Error("意料之外的type:" + mode);
/*     */       } 
/*     */       
/* 351 */       throw new Error("意料之外的unit");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete() {
/* 357 */     if (this.loaded)
/* 358 */       this.nodes.forEach((k, v) -> v.meshes.forEach(())); 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\hegltf\GltfDataModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */