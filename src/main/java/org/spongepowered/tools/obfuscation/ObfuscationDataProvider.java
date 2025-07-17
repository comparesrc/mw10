/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
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
/*     */ public class ObfuscationDataProvider
/*     */   implements IObfuscationDataProvider
/*     */ {
/*     */   private final IMixinAnnotationProcessor ap;
/*     */   private final List<ObfuscationEnvironment> environments;
/*     */   
/*     */   public ObfuscationDataProvider(IMixinAnnotationProcessor ap, List<ObfuscationEnvironment> environments) {
/*  56 */     this.ap = ap;
/*  57 */     this.environments = environments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ObfuscationData<T> getObfEntryRecursive(ITargetSelectorRemappable targetMember) {
/*  67 */     ITargetSelectorRemappable currentTarget = targetMember;
/*  68 */     ObfuscationData<String> obfTargetNames = getObfClass(currentTarget.getOwner());
/*  69 */     ObfuscationData<T> obfData = getObfEntry(currentTarget);
/*     */     try {
/*  71 */       while (obfData.isEmpty()) {
/*  72 */         TypeHandle targetType = this.ap.getTypeProvider().getTypeHandle(currentTarget.getOwner());
/*  73 */         if (targetType == null) {
/*  74 */           return obfData;
/*     */         }
/*     */         
/*  77 */         TypeHandle superClass = targetType.getSuperclass();
/*  78 */         obfData = getObfEntryUsing(currentTarget, superClass);
/*  79 */         if (!obfData.isEmpty()) {
/*  80 */           return applyParents(obfTargetNames, obfData);
/*     */         }
/*     */         
/*  83 */         for (TypeHandle iface : targetType.getInterfaces()) {
/*  84 */           obfData = getObfEntryUsing(currentTarget, iface);
/*  85 */           if (!obfData.isEmpty()) {
/*  86 */             return applyParents(obfTargetNames, obfData);
/*     */           }
/*     */         } 
/*     */         
/*  90 */         if (superClass == null) {
/*     */           break;
/*     */         }
/*     */         
/*  94 */         currentTarget = currentTarget.move(superClass.getName());
/*     */       } 
/*  96 */     } catch (Exception ex) {
/*  97 */       ex.printStackTrace();
/*  98 */       return getObfEntry(targetMember);
/*     */     } 
/* 100 */     return obfData;
/*     */   }
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
/*     */   private <T> ObfuscationData<T> getObfEntryUsing(ITargetSelectorRemappable targetMember, TypeHandle targetClass) {
/* 114 */     return (targetClass == null) ? new ObfuscationData<>() : getObfEntry(targetMember.move(targetClass.getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ObfuscationData<T> getObfEntry(ITargetSelectorRemappable targetMember) {
/* 125 */     if (targetMember.isField()) {
/* 126 */       return (ObfuscationData)getObfField(targetMember);
/*     */     }
/* 128 */     return (ObfuscationData)getObfMethod(targetMember.asMethodMapping());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ObfuscationData<T> getObfEntry(IMapping<T> mapping) {
/* 134 */     if (mapping != null) {
/* 135 */       if (mapping.getType() == IMapping.Type.FIELD)
/* 136 */         return (ObfuscationData)getObfField((MappingField)mapping); 
/* 137 */       if (mapping.getType() == IMapping.Type.METHOD) {
/* 138 */         return (ObfuscationData)getObfMethod((MappingMethod)mapping);
/*     */       }
/*     */     } 
/*     */     
/* 142 */     return new ObfuscationData<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> getObfMethodRecursive(ITargetSelectorRemappable targetMember) {
/* 152 */     return getObfEntryRecursive(targetMember);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> getObfMethod(ITargetSelectorRemappable method) {
/* 162 */     return getRemappedMethod(method, method.isConstructor());
/*     */   }
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> getRemappedMethod(ITargetSelectorRemappable method) {
/* 167 */     return getRemappedMethod(method, true);
/*     */   }
/*     */   
/*     */   private ObfuscationData<MappingMethod> getRemappedMethod(ITargetSelectorRemappable method, boolean remapDescriptor) {
/* 171 */     ObfuscationData<MappingMethod> data = new ObfuscationData<>();
/*     */     
/* 173 */     for (ObfuscationEnvironment env : this.environments) {
/* 174 */       MappingMethod obfMethod = env.getObfMethod(method);
/* 175 */       if (obfMethod != null) {
/* 176 */         data.put(env.getType(), obfMethod);
/*     */       }
/*     */     } 
/*     */     
/* 180 */     if (!data.isEmpty() || !remapDescriptor) {
/* 181 */       return data;
/*     */     }
/*     */     
/* 184 */     return remapDescriptor(data, method);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> getObfMethod(MappingMethod method) {
/* 194 */     return getRemappedMethod(method, method.isConstructor());
/*     */   }
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod method) {
/* 199 */     return getRemappedMethod(method, true);
/*     */   }
/*     */   
/*     */   private ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod method, boolean remapDescriptor) {
/* 203 */     ObfuscationData<MappingMethod> data = new ObfuscationData<>();
/*     */     
/* 205 */     for (ObfuscationEnvironment env : this.environments) {
/* 206 */       MappingMethod obfMethod = env.getObfMethod(method);
/* 207 */       if (obfMethod != null) {
/* 208 */         data.put(env.getType(), obfMethod);
/*     */       }
/*     */     } 
/*     */     
/* 212 */     if (!data.isEmpty() || !remapDescriptor) {
/* 213 */       return data;
/*     */     }
/*     */     
/* 216 */     return remapDescriptor(data, (ITargetSelectorRemappable)new MemberInfo((IMapping)method));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> remapDescriptor(ObfuscationData<MappingMethod> data, ITargetSelectorRemappable method) {
/* 227 */     for (ObfuscationEnvironment env : this.environments) {
/* 228 */       ITargetSelectorRemappable obfMethod = env.remapDescriptor(method);
/* 229 */       if (obfMethod != null) {
/* 230 */         data.put(env.getType(), obfMethod.asMethodMapping());
/*     */       }
/*     */     } 
/*     */     
/* 234 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingField> getObfFieldRecursive(ITargetSelectorRemappable targetMember) {
/* 244 */     return getObfEntryRecursive(targetMember);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingField> getObfField(ITargetSelectorRemappable field) {
/* 253 */     return getObfField(field.asFieldMapping());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingField> getObfField(MappingField field) {
/* 262 */     ObfuscationData<MappingField> data = new ObfuscationData<>();
/*     */     
/* 264 */     for (ObfuscationEnvironment env : this.environments) {
/* 265 */       MappingField obfField = env.getObfField(field);
/* 266 */       if (obfField != null) {
/* 267 */         if (obfField.getDesc() == null && field.getDesc() != null) {
/* 268 */           obfField = obfField.transform(env.remapDescriptor(field.getDesc()));
/*     */         }
/* 270 */         data.put(env.getType(), obfField);
/*     */       } 
/*     */     } 
/*     */     
/* 274 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<String> getObfClass(TypeHandle type) {
/* 283 */     return getObfClass(type.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<String> getObfClass(String className) {
/* 292 */     ObfuscationData<String> data = new ObfuscationData<>(className);
/*     */     
/* 294 */     for (ObfuscationEnvironment env : this.environments) {
/* 295 */       String obfClass = env.getObfClass(className);
/* 296 */       if (obfClass != null) {
/* 297 */         data.put(env.getType(), obfClass);
/*     */       }
/*     */     } 
/*     */     
/* 301 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> ObfuscationData<T> applyParents(ObfuscationData<String> parents, ObfuscationData<T> members) {
/* 313 */     for (ObfuscationType type : members) {
/* 314 */       String obfClass = parents.get(type);
/* 315 */       T obfMember = members.get(type);
/* 316 */       members.put(type, (T)MemberInfo.fromMapping((IMapping)obfMember).move(obfClass).asMapping());
/*     */     } 
/* 318 */     return members;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\ObfuscationDataProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */