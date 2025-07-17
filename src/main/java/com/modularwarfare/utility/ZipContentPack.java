/*    */ package com.modularwarfare.utility;
/*    */ 
/*    */ import com.modularwarfare.loader.ObjModel;
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
/*    */ import moe.komi.mwprotect.IZip;
/*    */ import moe.komi.mwprotect.IZipEntry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZipContentPack
/*    */ {
/*    */   public String contentPack;
/*    */   public Set<IZipEntry> fileHeaders;
/*    */   public IZip zipFile;
/* 19 */   public HashMap<String, ObjModel> models_cache = new HashMap<>();
/*    */   
/*    */   public ZipContentPack(String contentPack, Set<IZipEntry> fileHeaders, IZip zipFile) {
/* 22 */     this.contentPack = contentPack;
/* 23 */     this.fileHeaders = fileHeaders;
/* 24 */     this.zipFile = zipFile;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfar\\utility\ZipContentPack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */