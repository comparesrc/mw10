/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import com.google.common.base.Strings;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.objectweb.asm.Type;
/*    */ import org.objectweb.asm.tree.AnnotationNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.Constant;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyConstant;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.ModifyConstantInjector;
/*    */ import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
/*    */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
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
/*    */ 
/*    */ 
/*    */ @AnnotationType(ModifyConstant.class)
/*    */ @HandlerPrefix("constant")
/*    */ public class ModifyConstantInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/* 51 */   private static final String CONSTANT_ANNOTATION_CLASS = Constant.class.getName().replace('.', '/');
/*    */   
/*    */   public ModifyConstantInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/* 54 */     super(mixin, method, annotation, "constant");
/*    */   }
/*    */   
/*    */   protected List<AnnotationNode> readInjectionPoints() {
/*    */     ImmutableList immutableList;
/* 59 */     List<AnnotationNode> ats = super.readInjectionPoints();
/* 60 */     if (ats.isEmpty()) {
/* 61 */       AnnotationNode c = new AnnotationNode(CONSTANT_ANNOTATION_CLASS);
/* 62 */       c.visit("log", Boolean.TRUE);
/* 63 */       immutableList = ImmutableList.of(c);
/*    */     } 
/* 65 */     return (List<AnnotationNode>)immutableList;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void parseInjectionPoints(List<AnnotationNode> ats) {
/* 70 */     Type returnType = Type.getReturnType(this.method.desc);
/*    */     
/* 72 */     for (AnnotationNode at : ats) {
/* 73 */       this.injectionPoints.add(new BeforeConstant(getContext(), at, returnType.getDescriptor()));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected Injector parseInjector(AnnotationNode injectAnnotation) {
/* 79 */     return (Injector)new ModifyConstantInjector(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDescription() {
/* 84 */     return "Constant modifier method";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSliceId(String id) {
/* 89 */     return Strings.nullToEmpty(id);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\struct\ModifyConstantInjectionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */