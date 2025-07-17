/*     */ package org.spongepowered.asm.launch.platform.container;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.net.URI;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import org.spongepowered.asm.launch.platform.MainAttributes;
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
/*     */ public class ContainerHandleURI
/*     */   implements IContainerHandle
/*     */ {
/*     */   private final URI uri;
/*     */   private final File file;
/*     */   private final MainAttributes attributes;
/*     */   
/*     */   public ContainerHandleURI(URI uri) {
/*  56 */     this.uri = uri;
/*  57 */     this.file = (this.uri != null) ? new File(this.uri) : null;
/*  58 */     this.attributes = MainAttributes.of(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URI getURI() {
/*  65 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getFile() {
/*  72 */     return this.file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttribute(String name) {
/*  81 */     return this.attributes.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<IContainerHandle> getNestedContainers() {
/*  90 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/*  98 */     if (!(other instanceof ContainerHandleURI)) {
/*  99 */       return false;
/*     */     }
/* 101 */     return this.uri.equals(((ContainerHandleURI)other).uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 109 */     return this.uri.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 117 */     return String.format("ContainerHandleURI(%s)", new Object[] { this.uri });
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\container\ContainerHandleURI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */