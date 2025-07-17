/*    */ package com.modularwarfare.raycast.obb.bbloader;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.modularwarfare.common.vector.Vector3f;
/*    */ import com.modularwarfare.raycast.obb.OBBModelBone;
/*    */ import com.modularwarfare.raycast.obb.OBBModelBox;
/*    */ import com.modularwarfare.raycast.obb.OBBModelObject;
/*    */ import com.modularwarfare.raycast.obb.OBBModelScene;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockBenchOBBInfoLoader
/*    */ {
/* 24 */   public static HashMap<ResourceLocation, BBInfo> infoCache = new HashMap<>();
/*    */   
/*    */   public static <T extends OBBModelObject> T loadOBBInfo(Class<T> clazz, ResourceLocation loc) {
/* 27 */     Gson gson = new Gson(); try {
/*    */       OBBModelObject object;
/* 29 */       BBInfo info = null;
/* 30 */       if (infoCache.containsKey(loc)) {
/* 31 */         info = infoCache.get(loc);
/*    */       } else {
/* 33 */         InputStream stream = Minecraft.func_71410_x().func_110442_L().func_110536_a(loc).func_110527_b();
/* 34 */         info = (BBInfo)gson.fromJson(new InputStreamReader(stream), BBInfo.class);
/* 35 */         stream.close();
/* 36 */         infoCache.put(loc, info);
/*    */       } 
/*    */       
/*    */       try {
/* 40 */         object = (OBBModelObject)clazz.newInstance();
/* 41 */       } catch (InstantiationException|IllegalAccessException e) {
/* 42 */         e.printStackTrace();
/* 43 */         return null;
/*    */       } 
/* 45 */       OBBModelScene scene = new OBBModelScene();
/*    */       
/* 47 */       object.scene = scene;
/*    */       
/* 49 */       HashMap<String, OBBModelBone> bones = new HashMap<>();
/* 50 */       info.groups.forEach(g -> {
/*    */             OBBModelBone bone = new OBBModelBone();
/*    */             bone.name = g.name;
/*    */             bone.oirign = new Vector3f();
/*    */             bone.oirign.x = g.origin[0];
/*    */             bone.oirign.y = g.origin[1];
/*    */             bone.oirign.z = g.origin[2];
/*    */             bones.put(g.name, bone);
/*    */           });
/* 59 */       info.groups.forEach(g -> {
/*    */             if (!g.parent.equals("undefined")) {
/*    */               OBBModelBone parent = (OBBModelBone)bones.get(g.parent);
/*    */               
/*    */               parent.children.add(bones.get(g.name));
/*    */               ((OBBModelBone)bones.get(g.name)).parent = parent;
/*    */             } else {
/*    */               scene.rootBones.add(bones.get(g.name));
/*    */             } 
/*    */           });
/* 69 */       info.cubes.forEach(c -> {
/*    */             OBBModelBox box = new OBBModelBox();
/*    */             
/*    */             Vector3f from = new Vector3f(c.from[0], c.from[1], c.from[2]);
/*    */             Vector3f to = new Vector3f(c.to[0], c.to[1], c.to[2]);
/*    */             Vector3f size = new Vector3f((to.x - from.x) / 2.0F, (to.y - from.y) / 2.0F, (to.z - from.z) / 2.0F);
/*    */             Vector3f center = new Vector3f(from.x + size.x, from.y + size.y, from.z + size.z);
/*    */             OBBModelBone bone = (OBBModelBone)bones.get(c.parent);
/*    */             if (bone == null) {
/*    */               throw new RuntimeException();
/*    */             }
/*    */             box.name = c.name;
/*    */             box.center = center;
/*    */             box.anchor = new Vector3f(box.center.x - bone.oirign.x, box.center.y - bone.oirign.y, box.center.z - bone.oirign.z);
/*    */             box.size = size;
/*    */             box.rotation = new Vector3f(0.0F, 0.0F, 0.0F);
/*    */             object.boxes.add(box);
/*    */             object.boneBinding.put(box, bone);
/*    */           });
/* 88 */       return (T)object;
/* 89 */     } catch (IOException e) {
/*    */       
/* 91 */       e.printStackTrace();
/*    */       
/* 93 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\raycast\obb\bbloader\BlockBenchOBBInfoLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */