/*     */ package com.modularwarfare.utility;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import moe.komi.mwprotect.IZip;
/*     */ import moe.komi.mwprotect.IZipEntry;
/*     */ import net.minecraft.client.resources.AbstractResourcePack;
/*     */ import net.minecraft.client.resources.ResourcePackFileNotFoundException;
/*     */ import net.minecraftforge.fml.common.FMLContainerHolder;
/*     */ import net.minecraftforge.fml.common.FMLModContainer;
/*     */ import net.minecraftforge.fml.common.ModContainer;
/*     */ import net.minecraftforge.fml.common.discovery.ModCandidate;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MWResourcePack
/*     */   extends AbstractResourcePack
/*     */   implements FMLContainerHolder
/*     */ {
/*     */   private ModContainer container;
/*     */   
/*     */   public MWResourcePack(ModContainer container) {
/*  33 */     super(container.getSource());
/*  34 */     this.container = container;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_130077_b() {
/*  39 */     return ((Container)getFMLContainer()).packName;
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage func_110586_a() {
/*  44 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModContainer getFMLContainer() {
/*  49 */     return this.container;
/*     */   }
/*     */ 
/*     */   
/*     */   protected InputStream func_110591_a(String resourceName) throws IOException {
/*  54 */     InputStream s = null;
/*     */     
/*     */     try {
/*  57 */       String name1 = resourceName.replaceAll("\\\\", "/");
/*  58 */       IZipEntry fileHandle = ((Container)getFMLContainer()).zipEntries.stream().filter(fileHeader -> fileHeader.getFileName().equalsIgnoreCase(name1)).findFirst().orElse(null);
/*  59 */       if (fileHandle == null) {
/*  60 */         String name2 = name1.replaceAll("/", "\\\\");
/*  61 */         fileHandle = ((Container)getFMLContainer()).zipEntries.stream().filter(fileHeader -> fileHeader.getFileName().equalsIgnoreCase(name2)).findFirst().orElse(null);
/*     */       } 
/*  63 */       if (fileHandle != null) {
/*  64 */         s = fileHandle.getInputStream();
/*     */       }
/*  66 */     } catch (IOException e) {
/*  67 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  70 */     if (s == null) {
/*  71 */       if ("pack.mcmeta".equals(resourceName)) {
/*  72 */         return new ByteArrayInputStream(("{\n \"pack\": {\n   \"description\": \"dummy MW pack for " + this.container
/*     */             
/*  74 */             .getName() + "\",\n   \"pack_format\": 3\n}\n}")
/*     */ 
/*     */             
/*  77 */             .getBytes(StandardCharsets.UTF_8));
/*     */       }
/*  79 */       throw new ResourcePackFileNotFoundException(this.field_110597_b, resourceName);
/*     */     } 
/*  81 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_110593_b(String name) {
/*  86 */     String name1 = name.replaceAll("\\\\", "/");
/*  87 */     boolean flag = (((Container)getFMLContainer()).zipEntries.stream().filter(fileHeader -> fileHeader.getFileName().equalsIgnoreCase(name1)).findFirst().orElse(null) != null);
/*  88 */     if (!flag) {
/*  89 */       String name2 = name1.replaceAll("/", "\\\\");
/*  90 */       flag = (((Container)getFMLContainer()).zipEntries.stream().filter(fileHeader -> fileHeader.getFileName().equalsIgnoreCase(name2)).findFirst().orElse(null) != null);
/*     */     } 
/*  92 */     return flag;
/*     */   }
/*     */   
/*     */   public Set<String> func_110587_b() {
/*  96 */     Set<String> set = Sets.newHashSet();
/*  97 */     set.add("modularwarfare");
/*  98 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Container
/*     */     extends FMLModContainer
/*     */   {
/*     */     private String packName;
/*     */     
/*     */     private IZip zipFile;
/*     */     
/*     */     private Set<IZipEntry> zipEntries;
/*     */     
/*     */     public Container(String className, ModCandidate container, Map<String, Object> modDescriptor, IZip zipFile, String packName) throws IOException {
/* 112 */       super(className, container, modDescriptor);
/* 113 */       this.zipFile = zipFile;
/* 114 */       this.packName = packName.substring(15);
/* 115 */       this.zipEntries = zipFile.getFileList();
/*     */     }
/*     */ 
/*     */     
/*     */     public Class<?> getCustomResourcePackClass() {
/* 120 */       return MWResourcePack.class;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfar\\utility\MWResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */