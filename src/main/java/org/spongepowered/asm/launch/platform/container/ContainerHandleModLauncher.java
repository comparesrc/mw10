/*    */ package org.spongepowered.asm.launch.platform.container;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerHandleModLauncher
/*    */   extends ContainerHandleVirtual
/*    */ {
/*    */   public class Resource
/*    */     extends ContainerHandleURI
/*    */   {
/*    */     private String name;
/*    */     private Path path;
/*    */     
/*    */     public Resource(String name, Path path) {
/* 45 */       super(path.toUri());
/* 46 */       this.name = name;
/* 47 */       this.path = path;
/*    */     }
/*    */     
/*    */     public String getName() {
/* 51 */       return this.name;
/*    */     }
/*    */     
/*    */     public Path getPath() {
/* 55 */       return this.path;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 60 */       return String.format("ContainerHandleModLauncher.Resource(%s:%s)", new Object[] { this.name, this.path });
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ContainerHandleModLauncher(String name) {
/* 66 */     super(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addResource(String name, Path path) {
/* 76 */     add(new Resource(name, path));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addResources(List<Map.Entry<String, Path>> resources) {
/* 85 */     for (Map.Entry<String, Path> resource : resources) {
/* 86 */       addResource(resource.getKey(), resource.getValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 92 */     return String.format("ModLauncher Root Container(%x)", new Object[] { Integer.valueOf(hashCode()) });
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\container\ContainerHandleModLauncher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */