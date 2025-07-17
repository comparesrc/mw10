/*      */ package de.javagl.jgltf.impl.v1;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GlTF
/*      */   extends GlTFProperty
/*      */ {
/*      */   private List<String> extensionsUsed;
/*      */   private Map<String, Accessor> accessors;
/*      */   private Map<String, Animation> animations;
/*      */   private Asset asset;
/*      */   private Map<String, Buffer> buffers;
/*      */   private Map<String, BufferView> bufferViews;
/*      */   private Map<String, Camera> cameras;
/*      */   private Map<String, Image> images;
/*      */   private Map<String, Material> materials;
/*      */   private Map<String, Mesh> meshes;
/*      */   private Map<String, Node> nodes;
/*      */   private Map<String, Program> programs;
/*      */   private Map<String, Sampler> samplers;
/*      */   private String scene;
/*      */   private Map<String, Scene> scenes;
/*      */   private Map<String, Shader> shaders;
/*      */   private Map<String, Skin> skins;
/*      */   private Map<String, Technique> techniques;
/*      */   private Map<String, Texture> textures;
/*      */   
/*      */   public void setExtensionsUsed(List<String> extensionsUsed) {
/*  153 */     if (extensionsUsed == null) {
/*  154 */       this.extensionsUsed = extensionsUsed;
/*      */       return;
/*      */     } 
/*  157 */     this.extensionsUsed = extensionsUsed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getExtensionsUsed() {
/*  170 */     return this.extensionsUsed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExtensionsUsed(String element) {
/*  183 */     if (element == null) {
/*  184 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  186 */     List<String> oldList = this.extensionsUsed;
/*  187 */     List<String> newList = new ArrayList<>();
/*  188 */     if (oldList != null) {
/*  189 */       newList.addAll(oldList);
/*      */     }
/*  191 */     newList.add(element);
/*  192 */     this.extensionsUsed = newList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeExtensionsUsed(String element) {
/*  207 */     if (element == null) {
/*  208 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  210 */     List<String> oldList = this.extensionsUsed;
/*  211 */     List<String> newList = new ArrayList<>();
/*  212 */     if (oldList != null) {
/*  213 */       newList.addAll(oldList);
/*      */     }
/*  215 */     newList.remove(element);
/*  216 */     if (newList.isEmpty()) {
/*  217 */       this.extensionsUsed = null;
/*      */     } else {
/*  219 */       this.extensionsUsed = newList;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> defaultExtensionsUsed() {
/*  231 */     return new ArrayList<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAccessors(Map<String, Accessor> accessors) {
/*  242 */     if (accessors == null) {
/*  243 */       this.accessors = accessors;
/*      */       return;
/*      */     } 
/*  246 */     this.accessors = accessors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Accessor> getAccessors() {
/*  257 */     return this.accessors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAccessors(String key, Accessor value) {
/*  271 */     if (key == null) {
/*  272 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  274 */     if (value == null) {
/*  275 */       throw new NullPointerException("The value may not be null");
/*      */     }
/*  277 */     Map<String, Accessor> oldMap = this.accessors;
/*  278 */     Map<String, Accessor> newMap = new LinkedHashMap<>();
/*  279 */     if (oldMap != null) {
/*  280 */       newMap.putAll(oldMap);
/*      */     }
/*  282 */     newMap.put(key, value);
/*  283 */     this.accessors = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAccessors(String key) {
/*  298 */     if (key == null) {
/*  299 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  301 */     Map<String, Accessor> oldMap = this.accessors;
/*  302 */     Map<String, Accessor> newMap = new LinkedHashMap<>();
/*  303 */     if (oldMap != null) {
/*  304 */       newMap.putAll(oldMap);
/*      */     }
/*  306 */     newMap.remove(key);
/*  307 */     if (newMap.isEmpty()) {
/*  308 */       this.accessors = null;
/*      */     } else {
/*  310 */       this.accessors = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Accessor> defaultAccessors() {
/*  322 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnimations(Map<String, Animation> animations) {
/*  333 */     if (animations == null) {
/*  334 */       this.animations = animations;
/*      */       return;
/*      */     } 
/*  337 */     this.animations = animations;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Animation> getAnimations() {
/*  348 */     return this.animations;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAnimations(String key, Animation value) {
/*  362 */     if (key == null) {
/*  363 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  365 */     if (value == null) {
/*  366 */       throw new NullPointerException("The value may not be null");
/*      */     }
/*  368 */     Map<String, Animation> oldMap = this.animations;
/*  369 */     Map<String, Animation> newMap = new LinkedHashMap<>();
/*  370 */     if (oldMap != null) {
/*  371 */       newMap.putAll(oldMap);
/*      */     }
/*  373 */     newMap.put(key, value);
/*  374 */     this.animations = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAnimations(String key) {
/*  389 */     if (key == null) {
/*  390 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  392 */     Map<String, Animation> oldMap = this.animations;
/*  393 */     Map<String, Animation> newMap = new LinkedHashMap<>();
/*  394 */     if (oldMap != null) {
/*  395 */       newMap.putAll(oldMap);
/*      */     }
/*  397 */     newMap.remove(key);
/*  398 */     if (newMap.isEmpty()) {
/*  399 */       this.animations = null;
/*      */     } else {
/*  401 */       this.animations = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Animation> defaultAnimations() {
/*  413 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAsset(Asset asset) {
/*  424 */     if (asset == null) {
/*  425 */       this.asset = asset;
/*      */       return;
/*      */     } 
/*  428 */     this.asset = asset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Asset getAsset() {
/*  439 */     return this.asset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Asset defaultAsset() {
/*  450 */     return new Asset();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBuffers(Map<String, Buffer> buffers) {
/*  461 */     if (buffers == null) {
/*  462 */       this.buffers = buffers;
/*      */       return;
/*      */     } 
/*  465 */     this.buffers = buffers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Buffer> getBuffers() {
/*  476 */     return this.buffers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addBuffers(String key, Buffer value) {
/*  490 */     if (key == null) {
/*  491 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  493 */     if (value == null) {
/*  494 */       throw new NullPointerException("The value may not be null");
/*      */     }
/*  496 */     Map<String, Buffer> oldMap = this.buffers;
/*  497 */     Map<String, Buffer> newMap = new LinkedHashMap<>();
/*  498 */     if (oldMap != null) {
/*  499 */       newMap.putAll(oldMap);
/*      */     }
/*  501 */     newMap.put(key, value);
/*  502 */     this.buffers = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeBuffers(String key) {
/*  517 */     if (key == null) {
/*  518 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  520 */     Map<String, Buffer> oldMap = this.buffers;
/*  521 */     Map<String, Buffer> newMap = new LinkedHashMap<>();
/*  522 */     if (oldMap != null) {
/*  523 */       newMap.putAll(oldMap);
/*      */     }
/*  525 */     newMap.remove(key);
/*  526 */     if (newMap.isEmpty()) {
/*  527 */       this.buffers = null;
/*      */     } else {
/*  529 */       this.buffers = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Buffer> defaultBuffers() {
/*  541 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBufferViews(Map<String, BufferView> bufferViews) {
/*  552 */     if (bufferViews == null) {
/*  553 */       this.bufferViews = bufferViews;
/*      */       return;
/*      */     } 
/*  556 */     this.bufferViews = bufferViews;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, BufferView> getBufferViews() {
/*  567 */     return this.bufferViews;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addBufferViews(String key, BufferView value) {
/*  581 */     if (key == null) {
/*  582 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  584 */     if (value == null) {
/*  585 */       throw new NullPointerException("The value may not be null");
/*      */     }
/*  587 */     Map<String, BufferView> oldMap = this.bufferViews;
/*  588 */     Map<String, BufferView> newMap = new LinkedHashMap<>();
/*  589 */     if (oldMap != null) {
/*  590 */       newMap.putAll(oldMap);
/*      */     }
/*  592 */     newMap.put(key, value);
/*  593 */     this.bufferViews = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeBufferViews(String key) {
/*  608 */     if (key == null) {
/*  609 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  611 */     Map<String, BufferView> oldMap = this.bufferViews;
/*  612 */     Map<String, BufferView> newMap = new LinkedHashMap<>();
/*  613 */     if (oldMap != null) {
/*  614 */       newMap.putAll(oldMap);
/*      */     }
/*  616 */     newMap.remove(key);
/*  617 */     if (newMap.isEmpty()) {
/*  618 */       this.bufferViews = null;
/*      */     } else {
/*  620 */       this.bufferViews = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, BufferView> defaultBufferViews() {
/*  632 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCameras(Map<String, Camera> cameras) {
/*  643 */     if (cameras == null) {
/*  644 */       this.cameras = cameras;
/*      */       return;
/*      */     } 
/*  647 */     this.cameras = cameras;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Camera> getCameras() {
/*  658 */     return this.cameras;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addCameras(String key, Camera value) {
/*  672 */     if (key == null) {
/*  673 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  675 */     if (value == null) {
/*  676 */       throw new NullPointerException("The value may not be null");
/*      */     }
/*  678 */     Map<String, Camera> oldMap = this.cameras;
/*  679 */     Map<String, Camera> newMap = new LinkedHashMap<>();
/*  680 */     if (oldMap != null) {
/*  681 */       newMap.putAll(oldMap);
/*      */     }
/*  683 */     newMap.put(key, value);
/*  684 */     this.cameras = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeCameras(String key) {
/*  699 */     if (key == null) {
/*  700 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  702 */     Map<String, Camera> oldMap = this.cameras;
/*  703 */     Map<String, Camera> newMap = new LinkedHashMap<>();
/*  704 */     if (oldMap != null) {
/*  705 */       newMap.putAll(oldMap);
/*      */     }
/*  707 */     newMap.remove(key);
/*  708 */     if (newMap.isEmpty()) {
/*  709 */       this.cameras = null;
/*      */     } else {
/*  711 */       this.cameras = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Camera> defaultCameras() {
/*  723 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setImages(Map<String, Image> images) {
/*  734 */     if (images == null) {
/*  735 */       this.images = images;
/*      */       return;
/*      */     } 
/*  738 */     this.images = images;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Image> getImages() {
/*  749 */     return this.images;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addImages(String key, Image value) {
/*  763 */     if (key == null) {
/*  764 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  766 */     if (value == null) {
/*  767 */       throw new NullPointerException("The value may not be null");
/*      */     }
/*  769 */     Map<String, Image> oldMap = this.images;
/*  770 */     Map<String, Image> newMap = new LinkedHashMap<>();
/*  771 */     if (oldMap != null) {
/*  772 */       newMap.putAll(oldMap);
/*      */     }
/*  774 */     newMap.put(key, value);
/*  775 */     this.images = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeImages(String key) {
/*  790 */     if (key == null) {
/*  791 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  793 */     Map<String, Image> oldMap = this.images;
/*  794 */     Map<String, Image> newMap = new LinkedHashMap<>();
/*  795 */     if (oldMap != null) {
/*  796 */       newMap.putAll(oldMap);
/*      */     }
/*  798 */     newMap.remove(key);
/*  799 */     if (newMap.isEmpty()) {
/*  800 */       this.images = null;
/*      */     } else {
/*  802 */       this.images = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Image> defaultImages() {
/*  814 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaterials(Map<String, Material> materials) {
/*  825 */     if (materials == null) {
/*  826 */       this.materials = materials;
/*      */       return;
/*      */     } 
/*  829 */     this.materials = materials;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Material> getMaterials() {
/*  840 */     return this.materials;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMaterials(String key, Material value) {
/*  854 */     if (key == null) {
/*  855 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  857 */     if (value == null) {
/*  858 */       throw new NullPointerException("The value may not be null");
/*      */     }
/*  860 */     Map<String, Material> oldMap = this.materials;
/*  861 */     Map<String, Material> newMap = new LinkedHashMap<>();
/*  862 */     if (oldMap != null) {
/*  863 */       newMap.putAll(oldMap);
/*      */     }
/*  865 */     newMap.put(key, value);
/*  866 */     this.materials = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeMaterials(String key) {
/*  881 */     if (key == null) {
/*  882 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  884 */     Map<String, Material> oldMap = this.materials;
/*  885 */     Map<String, Material> newMap = new LinkedHashMap<>();
/*  886 */     if (oldMap != null) {
/*  887 */       newMap.putAll(oldMap);
/*      */     }
/*  889 */     newMap.remove(key);
/*  890 */     if (newMap.isEmpty()) {
/*  891 */       this.materials = null;
/*      */     } else {
/*  893 */       this.materials = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Material> defaultMaterials() {
/*  905 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMeshes(Map<String, Mesh> meshes) {
/*  916 */     if (meshes == null) {
/*  917 */       this.meshes = meshes;
/*      */       return;
/*      */     } 
/*  920 */     this.meshes = meshes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Mesh> getMeshes() {
/*  931 */     return this.meshes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMeshes(String key, Mesh value) {
/*  945 */     if (key == null) {
/*  946 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  948 */     if (value == null) {
/*  949 */       throw new NullPointerException("The value may not be null");
/*      */     }
/*  951 */     Map<String, Mesh> oldMap = this.meshes;
/*  952 */     Map<String, Mesh> newMap = new LinkedHashMap<>();
/*  953 */     if (oldMap != null) {
/*  954 */       newMap.putAll(oldMap);
/*      */     }
/*  956 */     newMap.put(key, value);
/*  957 */     this.meshes = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeMeshes(String key) {
/*  972 */     if (key == null) {
/*  973 */       throw new NullPointerException("The key may not be null");
/*      */     }
/*  975 */     Map<String, Mesh> oldMap = this.meshes;
/*  976 */     Map<String, Mesh> newMap = new LinkedHashMap<>();
/*  977 */     if (oldMap != null) {
/*  978 */       newMap.putAll(oldMap);
/*      */     }
/*  980 */     newMap.remove(key);
/*  981 */     if (newMap.isEmpty()) {
/*  982 */       this.meshes = null;
/*      */     } else {
/*  984 */       this.meshes = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Mesh> defaultMeshes() {
/*  996 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNodes(Map<String, Node> nodes) {
/* 1007 */     if (nodes == null) {
/* 1008 */       this.nodes = nodes;
/*      */       return;
/*      */     } 
/* 1011 */     this.nodes = nodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Node> getNodes() {
/* 1022 */     return this.nodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addNodes(String key, Node value) {
/* 1036 */     if (key == null) {
/* 1037 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1039 */     if (value == null) {
/* 1040 */       throw new NullPointerException("The value may not be null");
/*      */     }
/* 1042 */     Map<String, Node> oldMap = this.nodes;
/* 1043 */     Map<String, Node> newMap = new LinkedHashMap<>();
/* 1044 */     if (oldMap != null) {
/* 1045 */       newMap.putAll(oldMap);
/*      */     }
/* 1047 */     newMap.put(key, value);
/* 1048 */     this.nodes = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNodes(String key) {
/* 1063 */     if (key == null) {
/* 1064 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1066 */     Map<String, Node> oldMap = this.nodes;
/* 1067 */     Map<String, Node> newMap = new LinkedHashMap<>();
/* 1068 */     if (oldMap != null) {
/* 1069 */       newMap.putAll(oldMap);
/*      */     }
/* 1071 */     newMap.remove(key);
/* 1072 */     if (newMap.isEmpty()) {
/* 1073 */       this.nodes = null;
/*      */     } else {
/* 1075 */       this.nodes = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Node> defaultNodes() {
/* 1087 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPrograms(Map<String, Program> programs) {
/* 1098 */     if (programs == null) {
/* 1099 */       this.programs = programs;
/*      */       return;
/*      */     } 
/* 1102 */     this.programs = programs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Program> getPrograms() {
/* 1113 */     return this.programs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPrograms(String key, Program value) {
/* 1127 */     if (key == null) {
/* 1128 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1130 */     if (value == null) {
/* 1131 */       throw new NullPointerException("The value may not be null");
/*      */     }
/* 1133 */     Map<String, Program> oldMap = this.programs;
/* 1134 */     Map<String, Program> newMap = new LinkedHashMap<>();
/* 1135 */     if (oldMap != null) {
/* 1136 */       newMap.putAll(oldMap);
/*      */     }
/* 1138 */     newMap.put(key, value);
/* 1139 */     this.programs = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePrograms(String key) {
/* 1154 */     if (key == null) {
/* 1155 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1157 */     Map<String, Program> oldMap = this.programs;
/* 1158 */     Map<String, Program> newMap = new LinkedHashMap<>();
/* 1159 */     if (oldMap != null) {
/* 1160 */       newMap.putAll(oldMap);
/*      */     }
/* 1162 */     newMap.remove(key);
/* 1163 */     if (newMap.isEmpty()) {
/* 1164 */       this.programs = null;
/*      */     } else {
/* 1166 */       this.programs = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Program> defaultPrograms() {
/* 1178 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSamplers(Map<String, Sampler> samplers) {
/* 1189 */     if (samplers == null) {
/* 1190 */       this.samplers = samplers;
/*      */       return;
/*      */     } 
/* 1193 */     this.samplers = samplers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Sampler> getSamplers() {
/* 1204 */     return this.samplers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSamplers(String key, Sampler value) {
/* 1218 */     if (key == null) {
/* 1219 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1221 */     if (value == null) {
/* 1222 */       throw new NullPointerException("The value may not be null");
/*      */     }
/* 1224 */     Map<String, Sampler> oldMap = this.samplers;
/* 1225 */     Map<String, Sampler> newMap = new LinkedHashMap<>();
/* 1226 */     if (oldMap != null) {
/* 1227 */       newMap.putAll(oldMap);
/*      */     }
/* 1229 */     newMap.put(key, value);
/* 1230 */     this.samplers = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeSamplers(String key) {
/* 1245 */     if (key == null) {
/* 1246 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1248 */     Map<String, Sampler> oldMap = this.samplers;
/* 1249 */     Map<String, Sampler> newMap = new LinkedHashMap<>();
/* 1250 */     if (oldMap != null) {
/* 1251 */       newMap.putAll(oldMap);
/*      */     }
/* 1253 */     newMap.remove(key);
/* 1254 */     if (newMap.isEmpty()) {
/* 1255 */       this.samplers = null;
/*      */     } else {
/* 1257 */       this.samplers = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Sampler> defaultSamplers() {
/* 1269 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScene(String scene) {
/* 1279 */     if (scene == null) {
/* 1280 */       this.scene = scene;
/*      */       return;
/*      */     } 
/* 1283 */     this.scene = scene;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getScene() {
/* 1293 */     return this.scene;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScenes(Map<String, Scene> scenes) {
/* 1304 */     if (scenes == null) {
/* 1305 */       this.scenes = scenes;
/*      */       return;
/*      */     } 
/* 1308 */     this.scenes = scenes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Scene> getScenes() {
/* 1319 */     return this.scenes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addScenes(String key, Scene value) {
/* 1333 */     if (key == null) {
/* 1334 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1336 */     if (value == null) {
/* 1337 */       throw new NullPointerException("The value may not be null");
/*      */     }
/* 1339 */     Map<String, Scene> oldMap = this.scenes;
/* 1340 */     Map<String, Scene> newMap = new LinkedHashMap<>();
/* 1341 */     if (oldMap != null) {
/* 1342 */       newMap.putAll(oldMap);
/*      */     }
/* 1344 */     newMap.put(key, value);
/* 1345 */     this.scenes = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeScenes(String key) {
/* 1360 */     if (key == null) {
/* 1361 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1363 */     Map<String, Scene> oldMap = this.scenes;
/* 1364 */     Map<String, Scene> newMap = new LinkedHashMap<>();
/* 1365 */     if (oldMap != null) {
/* 1366 */       newMap.putAll(oldMap);
/*      */     }
/* 1368 */     newMap.remove(key);
/* 1369 */     if (newMap.isEmpty()) {
/* 1370 */       this.scenes = null;
/*      */     } else {
/* 1372 */       this.scenes = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Scene> defaultScenes() {
/* 1384 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShaders(Map<String, Shader> shaders) {
/* 1395 */     if (shaders == null) {
/* 1396 */       this.shaders = shaders;
/*      */       return;
/*      */     } 
/* 1399 */     this.shaders = shaders;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Shader> getShaders() {
/* 1410 */     return this.shaders;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addShaders(String key, Shader value) {
/* 1424 */     if (key == null) {
/* 1425 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1427 */     if (value == null) {
/* 1428 */       throw new NullPointerException("The value may not be null");
/*      */     }
/* 1430 */     Map<String, Shader> oldMap = this.shaders;
/* 1431 */     Map<String, Shader> newMap = new LinkedHashMap<>();
/* 1432 */     if (oldMap != null) {
/* 1433 */       newMap.putAll(oldMap);
/*      */     }
/* 1435 */     newMap.put(key, value);
/* 1436 */     this.shaders = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeShaders(String key) {
/* 1451 */     if (key == null) {
/* 1452 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1454 */     Map<String, Shader> oldMap = this.shaders;
/* 1455 */     Map<String, Shader> newMap = new LinkedHashMap<>();
/* 1456 */     if (oldMap != null) {
/* 1457 */       newMap.putAll(oldMap);
/*      */     }
/* 1459 */     newMap.remove(key);
/* 1460 */     if (newMap.isEmpty()) {
/* 1461 */       this.shaders = null;
/*      */     } else {
/* 1463 */       this.shaders = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Shader> defaultShaders() {
/* 1475 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSkins(Map<String, Skin> skins) {
/* 1486 */     if (skins == null) {
/* 1487 */       this.skins = skins;
/*      */       return;
/*      */     } 
/* 1490 */     this.skins = skins;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Skin> getSkins() {
/* 1501 */     return this.skins;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSkins(String key, Skin value) {
/* 1515 */     if (key == null) {
/* 1516 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1518 */     if (value == null) {
/* 1519 */       throw new NullPointerException("The value may not be null");
/*      */     }
/* 1521 */     Map<String, Skin> oldMap = this.skins;
/* 1522 */     Map<String, Skin> newMap = new LinkedHashMap<>();
/* 1523 */     if (oldMap != null) {
/* 1524 */       newMap.putAll(oldMap);
/*      */     }
/* 1526 */     newMap.put(key, value);
/* 1527 */     this.skins = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeSkins(String key) {
/* 1542 */     if (key == null) {
/* 1543 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1545 */     Map<String, Skin> oldMap = this.skins;
/* 1546 */     Map<String, Skin> newMap = new LinkedHashMap<>();
/* 1547 */     if (oldMap != null) {
/* 1548 */       newMap.putAll(oldMap);
/*      */     }
/* 1550 */     newMap.remove(key);
/* 1551 */     if (newMap.isEmpty()) {
/* 1552 */       this.skins = null;
/*      */     } else {
/* 1554 */       this.skins = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Skin> defaultSkins() {
/* 1566 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTechniques(Map<String, Technique> techniques) {
/* 1577 */     if (techniques == null) {
/* 1578 */       this.techniques = techniques;
/*      */       return;
/*      */     } 
/* 1581 */     this.techniques = techniques;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Technique> getTechniques() {
/* 1592 */     return this.techniques;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTechniques(String key, Technique value) {
/* 1606 */     if (key == null) {
/* 1607 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1609 */     if (value == null) {
/* 1610 */       throw new NullPointerException("The value may not be null");
/*      */     }
/* 1612 */     Map<String, Technique> oldMap = this.techniques;
/* 1613 */     Map<String, Technique> newMap = new LinkedHashMap<>();
/* 1614 */     if (oldMap != null) {
/* 1615 */       newMap.putAll(oldMap);
/*      */     }
/* 1617 */     newMap.put(key, value);
/* 1618 */     this.techniques = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTechniques(String key) {
/* 1633 */     if (key == null) {
/* 1634 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1636 */     Map<String, Technique> oldMap = this.techniques;
/* 1637 */     Map<String, Technique> newMap = new LinkedHashMap<>();
/* 1638 */     if (oldMap != null) {
/* 1639 */       newMap.putAll(oldMap);
/*      */     }
/* 1641 */     newMap.remove(key);
/* 1642 */     if (newMap.isEmpty()) {
/* 1643 */       this.techniques = null;
/*      */     } else {
/* 1645 */       this.techniques = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Technique> defaultTechniques() {
/* 1657 */     return new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTextures(Map<String, Texture> textures) {
/* 1668 */     if (textures == null) {
/* 1669 */       this.textures = textures;
/*      */       return;
/*      */     } 
/* 1672 */     this.textures = textures;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Texture> getTextures() {
/* 1683 */     return this.textures;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTextures(String key, Texture value) {
/* 1697 */     if (key == null) {
/* 1698 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1700 */     if (value == null) {
/* 1701 */       throw new NullPointerException("The value may not be null");
/*      */     }
/* 1703 */     Map<String, Texture> oldMap = this.textures;
/* 1704 */     Map<String, Texture> newMap = new LinkedHashMap<>();
/* 1705 */     if (oldMap != null) {
/* 1706 */       newMap.putAll(oldMap);
/*      */     }
/* 1708 */     newMap.put(key, value);
/* 1709 */     this.textures = newMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTextures(String key) {
/* 1724 */     if (key == null) {
/* 1725 */       throw new NullPointerException("The key may not be null");
/*      */     }
/* 1727 */     Map<String, Texture> oldMap = this.textures;
/* 1728 */     Map<String, Texture> newMap = new LinkedHashMap<>();
/* 1729 */     if (oldMap != null) {
/* 1730 */       newMap.putAll(oldMap);
/*      */     }
/* 1732 */     newMap.remove(key);
/* 1733 */     if (newMap.isEmpty()) {
/* 1734 */       this.textures = null;
/*      */     } else {
/* 1736 */       this.textures = newMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Texture> defaultTextures() {
/* 1748 */     return new LinkedHashMap<>();
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\GlTF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */