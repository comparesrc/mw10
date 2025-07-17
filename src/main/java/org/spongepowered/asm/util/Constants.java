/*    */ package org.spongepowered.asm.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import org.spongepowered.asm.mixin.Mixin;
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
/*    */ public abstract class Constants
/*    */ {
/*    */   public static final String CTOR = "<init>";
/*    */   public static final String CLINIT = "<clinit>";
/*    */   public static final String IMAGINARY_SUPER = "super$";
/*    */   public static final String DEBUG_OUTPUT_PATH = ".mixin.out";
/* 40 */   public static final String MIXIN_PACKAGE = Mixin.class.getPackage().getName();
/* 41 */   public static final String MIXIN_PACKAGE_REF = MIXIN_PACKAGE.replace('.', '/');
/*    */   
/*    */   public static final String STRING = "java/lang/String";
/*    */   
/*    */   public static final String OBJECT = "java/lang/Object";
/*    */   
/*    */   public static final String CLASS = "java/lang/Class";
/*    */   
/*    */   public static final String STRING_DESC = "Ljava/lang/String;";
/*    */   public static final String OBJECT_DESC = "Ljava/lang/Object;";
/*    */   public static final String CLASS_DESC = "Ljava/lang/Class;";
/*    */   public static final String SYNTHETIC_PACKAGE = "org.spongepowered.asm.synthetic";
/*    */   public static final char UNICODE_SNOWMAN = '☃';
/* 54 */   public static final File DEBUG_OUTPUT_DIR = new File(".mixin.out");
/*    */   public static final String SIDE_DEDICATEDSERVER = "DEDICATEDSERVER";
/*    */   public static final String SIDE_SERVER = "SERVER";
/*    */   public static final String SIDE_CLIENT = "CLIENT";
/*    */   public static final String SIDE_UNKNOWN = "UNKNOWN";
/*    */   
/*    */   public static abstract class ManifestAttributes {
/*    */     public static final String TWEAKER = "TweakClass";
/*    */     public static final String MAINCLASS = "Main-Class";
/*    */     public static final String MIXINCONFIGS = "MixinConfigs";
/*    */     public static final String TOKENPROVIDERS = "MixinTokenProviders";
/*    */     public static final String MIXINCONNECTOR = "MixinConnector";
/*    */     @Deprecated
/*    */     public static final String COMPATIBILITY = "MixinCompatibilityLevel";
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\Constants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */