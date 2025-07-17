/*      */ package de.javagl.jgltf.impl.v2;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
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
/*      */   private List<String> extensionsRequired;
/*      */   private List<Accessor> accessors;
/*      */   private List<Animation> animations;
/*      */   private Asset asset;
/*      */   private List<Buffer> buffers;
/*      */   private List<BufferView> bufferViews;
/*      */   private List<Camera> cameras;
/*      */   private List<Image> images;
/*      */   private List<Material> materials;
/*      */   private List<Mesh> meshes;
/*      */   private List<Node> nodes;
/*      */   private List<Sampler> samplers;
/*      */   private Integer scene;
/*      */   private List<Scene> scenes;
/*      */   private List<Skin> skins;
/*      */   private List<Texture> textures;
/*      */   
/*      */   public void setExtensionsUsed(List<String> extensionsUsed) {
/*  185 */     if (extensionsUsed == null) {
/*  186 */       this.extensionsUsed = extensionsUsed;
/*      */       return;
/*      */     } 
/*  189 */     if (extensionsUsed.size() < 1) {
/*  190 */       throw new IllegalArgumentException("Number of extensionsUsed elements is < 1");
/*      */     }
/*  192 */     this.extensionsUsed = extensionsUsed;
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
/*  205 */     return this.extensionsUsed;
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
/*  218 */     if (element == null) {
/*  219 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  221 */     List<String> oldList = this.extensionsUsed;
/*  222 */     List<String> newList = new ArrayList<>();
/*  223 */     if (oldList != null) {
/*  224 */       newList.addAll(oldList);
/*      */     }
/*  226 */     newList.add(element);
/*  227 */     this.extensionsUsed = newList;
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
/*  242 */     if (element == null) {
/*  243 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  245 */     List<String> oldList = this.extensionsUsed;
/*  246 */     List<String> newList = new ArrayList<>();
/*  247 */     if (oldList != null) {
/*  248 */       newList.addAll(oldList);
/*      */     }
/*  250 */     newList.remove(element);
/*  251 */     if (newList.isEmpty()) {
/*  252 */       this.extensionsUsed = null;
/*      */     } else {
/*  254 */       this.extensionsUsed = newList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExtensionsRequired(List<String> extensionsRequired) {
/*  271 */     if (extensionsRequired == null) {
/*  272 */       this.extensionsRequired = extensionsRequired;
/*      */       return;
/*      */     } 
/*  275 */     if (extensionsRequired.size() < 1) {
/*  276 */       throw new IllegalArgumentException("Number of extensionsRequired elements is < 1");
/*      */     }
/*  278 */     this.extensionsRequired = extensionsRequired;
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
/*      */   public List<String> getExtensionsRequired() {
/*  292 */     return this.extensionsRequired;
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
/*      */   public void addExtensionsRequired(String element) {
/*  305 */     if (element == null) {
/*  306 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  308 */     List<String> oldList = this.extensionsRequired;
/*  309 */     List<String> newList = new ArrayList<>();
/*  310 */     if (oldList != null) {
/*  311 */       newList.addAll(oldList);
/*      */     }
/*  313 */     newList.add(element);
/*  314 */     this.extensionsRequired = newList;
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
/*      */   public void removeExtensionsRequired(String element) {
/*  329 */     if (element == null) {
/*  330 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  332 */     List<String> oldList = this.extensionsRequired;
/*  333 */     List<String> newList = new ArrayList<>();
/*  334 */     if (oldList != null) {
/*  335 */       newList.addAll(oldList);
/*      */     }
/*  337 */     newList.remove(element);
/*  338 */     if (newList.isEmpty()) {
/*  339 */       this.extensionsRequired = null;
/*      */     } else {
/*  341 */       this.extensionsRequired = newList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAccessors(List<Accessor> accessors) {
/*  358 */     if (accessors == null) {
/*  359 */       this.accessors = accessors;
/*      */       return;
/*      */     } 
/*  362 */     if (accessors.size() < 1) {
/*  363 */       throw new IllegalArgumentException("Number of accessors elements is < 1");
/*      */     }
/*  365 */     this.accessors = accessors;
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
/*      */   public List<Accessor> getAccessors() {
/*  379 */     return this.accessors;
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
/*      */   public void addAccessors(Accessor element) {
/*  392 */     if (element == null) {
/*  393 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  395 */     List<Accessor> oldList = this.accessors;
/*  396 */     List<Accessor> newList = new ArrayList<>();
/*  397 */     if (oldList != null) {
/*  398 */       newList.addAll(oldList);
/*      */     }
/*  400 */     newList.add(element);
/*  401 */     this.accessors = newList;
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
/*      */   public void removeAccessors(Accessor element) {
/*  416 */     if (element == null) {
/*  417 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  419 */     List<Accessor> oldList = this.accessors;
/*  420 */     List<Accessor> newList = new ArrayList<>();
/*  421 */     if (oldList != null) {
/*  422 */       newList.addAll(oldList);
/*      */     }
/*  424 */     newList.remove(element);
/*  425 */     if (newList.isEmpty()) {
/*  426 */       this.accessors = null;
/*      */     } else {
/*  428 */       this.accessors = newList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnimations(List<Animation> animations) {
/*  444 */     if (animations == null) {
/*  445 */       this.animations = animations;
/*      */       return;
/*      */     } 
/*  448 */     if (animations.size() < 1) {
/*  449 */       throw new IllegalArgumentException("Number of animations elements is < 1");
/*      */     }
/*  451 */     this.animations = animations;
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
/*      */   public List<Animation> getAnimations() {
/*  464 */     return this.animations;
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
/*      */   public void addAnimations(Animation element) {
/*  477 */     if (element == null) {
/*  478 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  480 */     List<Animation> oldList = this.animations;
/*  481 */     List<Animation> newList = new ArrayList<>();
/*  482 */     if (oldList != null) {
/*  483 */       newList.addAll(oldList);
/*      */     }
/*  485 */     newList.add(element);
/*  486 */     this.animations = newList;
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
/*      */   public void removeAnimations(Animation element) {
/*  501 */     if (element == null) {
/*  502 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  504 */     List<Animation> oldList = this.animations;
/*  505 */     List<Animation> newList = new ArrayList<>();
/*  506 */     if (oldList != null) {
/*  507 */       newList.addAll(oldList);
/*      */     }
/*  509 */     newList.remove(element);
/*  510 */     if (newList.isEmpty()) {
/*  511 */       this.animations = null;
/*      */     } else {
/*  513 */       this.animations = newList;
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
/*      */   public void setAsset(Asset asset) {
/*  525 */     if (asset == null) {
/*  526 */       throw new NullPointerException("Invalid value for asset: " + asset + ", may not be null");
/*      */     }
/*  528 */     this.asset = asset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Asset getAsset() {
/*  538 */     return this.asset;
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
/*      */   
/*      */   public void setBuffers(List<Buffer> buffers) {
/*  554 */     if (buffers == null) {
/*  555 */       this.buffers = buffers;
/*      */       return;
/*      */     } 
/*  558 */     if (buffers.size() < 1) {
/*  559 */       throw new IllegalArgumentException("Number of buffers elements is < 1");
/*      */     }
/*  561 */     this.buffers = buffers;
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
/*      */   public List<Buffer> getBuffers() {
/*  575 */     return this.buffers;
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
/*      */   public void addBuffers(Buffer element) {
/*  588 */     if (element == null) {
/*  589 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  591 */     List<Buffer> oldList = this.buffers;
/*  592 */     List<Buffer> newList = new ArrayList<>();
/*  593 */     if (oldList != null) {
/*  594 */       newList.addAll(oldList);
/*      */     }
/*  596 */     newList.add(element);
/*  597 */     this.buffers = newList;
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
/*      */   public void removeBuffers(Buffer element) {
/*  612 */     if (element == null) {
/*  613 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  615 */     List<Buffer> oldList = this.buffers;
/*  616 */     List<Buffer> newList = new ArrayList<>();
/*  617 */     if (oldList != null) {
/*  618 */       newList.addAll(oldList);
/*      */     }
/*  620 */     newList.remove(element);
/*  621 */     if (newList.isEmpty()) {
/*  622 */       this.buffers = null;
/*      */     } else {
/*  624 */       this.buffers = newList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBufferViews(List<BufferView> bufferViews) {
/*  641 */     if (bufferViews == null) {
/*  642 */       this.bufferViews = bufferViews;
/*      */       return;
/*      */     } 
/*  645 */     if (bufferViews.size() < 1) {
/*  646 */       throw new IllegalArgumentException("Number of bufferViews elements is < 1");
/*      */     }
/*  648 */     this.bufferViews = bufferViews;
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
/*      */   public List<BufferView> getBufferViews() {
/*  662 */     return this.bufferViews;
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
/*      */   public void addBufferViews(BufferView element) {
/*  675 */     if (element == null) {
/*  676 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  678 */     List<BufferView> oldList = this.bufferViews;
/*  679 */     List<BufferView> newList = new ArrayList<>();
/*  680 */     if (oldList != null) {
/*  681 */       newList.addAll(oldList);
/*      */     }
/*  683 */     newList.add(element);
/*  684 */     this.bufferViews = newList;
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
/*      */   public void removeBufferViews(BufferView element) {
/*  699 */     if (element == null) {
/*  700 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  702 */     List<BufferView> oldList = this.bufferViews;
/*  703 */     List<BufferView> newList = new ArrayList<>();
/*  704 */     if (oldList != null) {
/*  705 */       newList.addAll(oldList);
/*      */     }
/*  707 */     newList.remove(element);
/*  708 */     if (newList.isEmpty()) {
/*  709 */       this.bufferViews = null;
/*      */     } else {
/*  711 */       this.bufferViews = newList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCameras(List<Camera> cameras) {
/*  728 */     if (cameras == null) {
/*  729 */       this.cameras = cameras;
/*      */       return;
/*      */     } 
/*  732 */     if (cameras.size() < 1) {
/*  733 */       throw new IllegalArgumentException("Number of cameras elements is < 1");
/*      */     }
/*  735 */     this.cameras = cameras;
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
/*      */   public List<Camera> getCameras() {
/*  749 */     return this.cameras;
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
/*      */   public void addCameras(Camera element) {
/*  762 */     if (element == null) {
/*  763 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  765 */     List<Camera> oldList = this.cameras;
/*  766 */     List<Camera> newList = new ArrayList<>();
/*  767 */     if (oldList != null) {
/*  768 */       newList.addAll(oldList);
/*      */     }
/*  770 */     newList.add(element);
/*  771 */     this.cameras = newList;
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
/*      */   public void removeCameras(Camera element) {
/*  786 */     if (element == null) {
/*  787 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  789 */     List<Camera> oldList = this.cameras;
/*  790 */     List<Camera> newList = new ArrayList<>();
/*  791 */     if (oldList != null) {
/*  792 */       newList.addAll(oldList);
/*      */     }
/*  794 */     newList.remove(element);
/*  795 */     if (newList.isEmpty()) {
/*  796 */       this.cameras = null;
/*      */     } else {
/*  798 */       this.cameras = newList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setImages(List<Image> images) {
/*  815 */     if (images == null) {
/*  816 */       this.images = images;
/*      */       return;
/*      */     } 
/*  819 */     if (images.size() < 1) {
/*  820 */       throw new IllegalArgumentException("Number of images elements is < 1");
/*      */     }
/*  822 */     this.images = images;
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
/*      */   public List<Image> getImages() {
/*  836 */     return this.images;
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
/*      */   public void addImages(Image element) {
/*  849 */     if (element == null) {
/*  850 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  852 */     List<Image> oldList = this.images;
/*  853 */     List<Image> newList = new ArrayList<>();
/*  854 */     if (oldList != null) {
/*  855 */       newList.addAll(oldList);
/*      */     }
/*  857 */     newList.add(element);
/*  858 */     this.images = newList;
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
/*      */   public void removeImages(Image element) {
/*  873 */     if (element == null) {
/*  874 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  876 */     List<Image> oldList = this.images;
/*  877 */     List<Image> newList = new ArrayList<>();
/*  878 */     if (oldList != null) {
/*  879 */       newList.addAll(oldList);
/*      */     }
/*  881 */     newList.remove(element);
/*  882 */     if (newList.isEmpty()) {
/*  883 */       this.images = null;
/*      */     } else {
/*  885 */       this.images = newList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaterials(List<Material> materials) {
/*  901 */     if (materials == null) {
/*  902 */       this.materials = materials;
/*      */       return;
/*      */     } 
/*  905 */     if (materials.size() < 1) {
/*  906 */       throw new IllegalArgumentException("Number of materials elements is < 1");
/*      */     }
/*  908 */     this.materials = materials;
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
/*      */   public List<Material> getMaterials() {
/*  921 */     return this.materials;
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
/*      */   public void addMaterials(Material element) {
/*  934 */     if (element == null) {
/*  935 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  937 */     List<Material> oldList = this.materials;
/*  938 */     List<Material> newList = new ArrayList<>();
/*  939 */     if (oldList != null) {
/*  940 */       newList.addAll(oldList);
/*      */     }
/*  942 */     newList.add(element);
/*  943 */     this.materials = newList;
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
/*      */   public void removeMaterials(Material element) {
/*  958 */     if (element == null) {
/*  959 */       throw new NullPointerException("The element may not be null");
/*      */     }
/*  961 */     List<Material> oldList = this.materials;
/*  962 */     List<Material> newList = new ArrayList<>();
/*  963 */     if (oldList != null) {
/*  964 */       newList.addAll(oldList);
/*      */     }
/*  966 */     newList.remove(element);
/*  967 */     if (newList.isEmpty()) {
/*  968 */       this.materials = null;
/*      */     } else {
/*  970 */       this.materials = newList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMeshes(List<Mesh> meshes) {
/*  987 */     if (meshes == null) {
/*  988 */       this.meshes = meshes;
/*      */       return;
/*      */     } 
/*  991 */     if (meshes.size() < 1) {
/*  992 */       throw new IllegalArgumentException("Number of meshes elements is < 1");
/*      */     }
/*  994 */     this.meshes = meshes;
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
/*      */   public List<Mesh> getMeshes() {
/* 1008 */     return this.meshes;
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
/*      */   public void addMeshes(Mesh element) {
/* 1021 */     if (element == null) {
/* 1022 */       throw new NullPointerException("The element may not be null");
/*      */     }
/* 1024 */     List<Mesh> oldList = this.meshes;
/* 1025 */     List<Mesh> newList = new ArrayList<>();
/* 1026 */     if (oldList != null) {
/* 1027 */       newList.addAll(oldList);
/*      */     }
/* 1029 */     newList.add(element);
/* 1030 */     this.meshes = newList;
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
/*      */   public void removeMeshes(Mesh element) {
/* 1045 */     if (element == null) {
/* 1046 */       throw new NullPointerException("The element may not be null");
/*      */     }
/* 1048 */     List<Mesh> oldList = this.meshes;
/* 1049 */     List<Mesh> newList = new ArrayList<>();
/* 1050 */     if (oldList != null) {
/* 1051 */       newList.addAll(oldList);
/*      */     }
/* 1053 */     newList.remove(element);
/* 1054 */     if (newList.isEmpty()) {
/* 1055 */       this.meshes = null;
/*      */     } else {
/* 1057 */       this.meshes = newList;
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
/*      */   public void setNodes(List<Node> nodes) {
/* 1082 */     if (nodes == null) {
/* 1083 */       this.nodes = nodes;
/*      */       return;
/*      */     } 
/* 1086 */     if (nodes.size() < 1) {
/* 1087 */       throw new IllegalArgumentException("Number of nodes elements is < 1");
/*      */     }
/* 1089 */     this.nodes = nodes;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Node> getNodes() {
/* 1111 */     return this.nodes;
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
/*      */   public void addNodes(Node element) {
/* 1124 */     if (element == null) {
/* 1125 */       throw new NullPointerException("The element may not be null");
/*      */     }
/* 1127 */     List<Node> oldList = this.nodes;
/* 1128 */     List<Node> newList = new ArrayList<>();
/* 1129 */     if (oldList != null) {
/* 1130 */       newList.addAll(oldList);
/*      */     }
/* 1132 */     newList.add(element);
/* 1133 */     this.nodes = newList;
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
/*      */   public void removeNodes(Node element) {
/* 1148 */     if (element == null) {
/* 1149 */       throw new NullPointerException("The element may not be null");
/*      */     }
/* 1151 */     List<Node> oldList = this.nodes;
/* 1152 */     List<Node> newList = new ArrayList<>();
/* 1153 */     if (oldList != null) {
/* 1154 */       newList.addAll(oldList);
/*      */     }
/* 1156 */     newList.remove(element);
/* 1157 */     if (newList.isEmpty()) {
/* 1158 */       this.nodes = null;
/*      */     } else {
/* 1160 */       this.nodes = newList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSamplers(List<Sampler> samplers) {
/* 1177 */     if (samplers == null) {
/* 1178 */       this.samplers = samplers;
/*      */       return;
/*      */     } 
/* 1181 */     if (samplers.size() < 1) {
/* 1182 */       throw new IllegalArgumentException("Number of samplers elements is < 1");
/*      */     }
/* 1184 */     this.samplers = samplers;
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
/*      */   public List<Sampler> getSamplers() {
/* 1198 */     return this.samplers;
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
/*      */   public void addSamplers(Sampler element) {
/* 1211 */     if (element == null) {
/* 1212 */       throw new NullPointerException("The element may not be null");
/*      */     }
/* 1214 */     List<Sampler> oldList = this.samplers;
/* 1215 */     List<Sampler> newList = new ArrayList<>();
/* 1216 */     if (oldList != null) {
/* 1217 */       newList.addAll(oldList);
/*      */     }
/* 1219 */     newList.add(element);
/* 1220 */     this.samplers = newList;
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
/*      */   public void removeSamplers(Sampler element) {
/* 1235 */     if (element == null) {
/* 1236 */       throw new NullPointerException("The element may not be null");
/*      */     }
/* 1238 */     List<Sampler> oldList = this.samplers;
/* 1239 */     List<Sampler> newList = new ArrayList<>();
/* 1240 */     if (oldList != null) {
/* 1241 */       newList.addAll(oldList);
/*      */     }
/* 1243 */     newList.remove(element);
/* 1244 */     if (newList.isEmpty()) {
/* 1245 */       this.samplers = null;
/*      */     } else {
/* 1247 */       this.samplers = newList;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScene(Integer scene) {
/* 1258 */     if (scene == null) {
/* 1259 */       this.scene = scene;
/*      */       return;
/*      */     } 
/* 1262 */     this.scene = scene;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getScene() {
/* 1272 */     return this.scene;
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
/*      */   public void setScenes(List<Scene> scenes) {
/* 1287 */     if (scenes == null) {
/* 1288 */       this.scenes = scenes;
/*      */       return;
/*      */     } 
/* 1291 */     if (scenes.size() < 1) {
/* 1292 */       throw new IllegalArgumentException("Number of scenes elements is < 1");
/*      */     }
/* 1294 */     this.scenes = scenes;
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
/*      */   public List<Scene> getScenes() {
/* 1307 */     return this.scenes;
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
/*      */   public void addScenes(Scene element) {
/* 1320 */     if (element == null) {
/* 1321 */       throw new NullPointerException("The element may not be null");
/*      */     }
/* 1323 */     List<Scene> oldList = this.scenes;
/* 1324 */     List<Scene> newList = new ArrayList<>();
/* 1325 */     if (oldList != null) {
/* 1326 */       newList.addAll(oldList);
/*      */     }
/* 1328 */     newList.add(element);
/* 1329 */     this.scenes = newList;
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
/*      */   public void removeScenes(Scene element) {
/* 1344 */     if (element == null) {
/* 1345 */       throw new NullPointerException("The element may not be null");
/*      */     }
/* 1347 */     List<Scene> oldList = this.scenes;
/* 1348 */     List<Scene> newList = new ArrayList<>();
/* 1349 */     if (oldList != null) {
/* 1350 */       newList.addAll(oldList);
/*      */     }
/* 1352 */     newList.remove(element);
/* 1353 */     if (newList.isEmpty()) {
/* 1354 */       this.scenes = null;
/*      */     } else {
/* 1356 */       this.scenes = newList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSkins(List<Skin> skins) {
/* 1372 */     if (skins == null) {
/* 1373 */       this.skins = skins;
/*      */       return;
/*      */     } 
/* 1376 */     if (skins.size() < 1) {
/* 1377 */       throw new IllegalArgumentException("Number of skins elements is < 1");
/*      */     }
/* 1379 */     this.skins = skins;
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
/*      */   public List<Skin> getSkins() {
/* 1392 */     return this.skins;
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
/*      */   public void addSkins(Skin element) {
/* 1405 */     if (element == null) {
/* 1406 */       throw new NullPointerException("The element may not be null");
/*      */     }
/* 1408 */     List<Skin> oldList = this.skins;
/* 1409 */     List<Skin> newList = new ArrayList<>();
/* 1410 */     if (oldList != null) {
/* 1411 */       newList.addAll(oldList);
/*      */     }
/* 1413 */     newList.add(element);
/* 1414 */     this.skins = newList;
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
/*      */   public void removeSkins(Skin element) {
/* 1429 */     if (element == null) {
/* 1430 */       throw new NullPointerException("The element may not be null");
/*      */     }
/* 1432 */     List<Skin> oldList = this.skins;
/* 1433 */     List<Skin> newList = new ArrayList<>();
/* 1434 */     if (oldList != null) {
/* 1435 */       newList.addAll(oldList);
/*      */     }
/* 1437 */     newList.remove(element);
/* 1438 */     if (newList.isEmpty()) {
/* 1439 */       this.skins = null;
/*      */     } else {
/* 1441 */       this.skins = newList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTextures(List<Texture> textures) {
/* 1457 */     if (textures == null) {
/* 1458 */       this.textures = textures;
/*      */       return;
/*      */     } 
/* 1461 */     if (textures.size() < 1) {
/* 1462 */       throw new IllegalArgumentException("Number of textures elements is < 1");
/*      */     }
/* 1464 */     this.textures = textures;
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
/*      */   public List<Texture> getTextures() {
/* 1477 */     return this.textures;
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
/*      */   public void addTextures(Texture element) {
/* 1490 */     if (element == null) {
/* 1491 */       throw new NullPointerException("The element may not be null");
/*      */     }
/* 1493 */     List<Texture> oldList = this.textures;
/* 1494 */     List<Texture> newList = new ArrayList<>();
/* 1495 */     if (oldList != null) {
/* 1496 */       newList.addAll(oldList);
/*      */     }
/* 1498 */     newList.add(element);
/* 1499 */     this.textures = newList;
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
/*      */   public void removeTextures(Texture element) {
/* 1514 */     if (element == null) {
/* 1515 */       throw new NullPointerException("The element may not be null");
/*      */     }
/* 1517 */     List<Texture> oldList = this.textures;
/* 1518 */     List<Texture> newList = new ArrayList<>();
/* 1519 */     if (oldList != null) {
/* 1520 */       newList.addAll(oldList);
/*      */     }
/* 1522 */     newList.remove(element);
/* 1523 */     if (newList.isEmpty()) {
/* 1524 */       this.textures = null;
/*      */     } else {
/* 1526 */       this.textures = newList;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\GlTF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */