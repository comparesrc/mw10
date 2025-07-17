/*     */ package com.modularwarfare.script;
/*     */ 
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.modularwarfare.common.guns.WeaponFireMode;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import javax.script.Invocable;
/*     */ import javax.script.ScriptEngine;
/*     */ import javax.script.ScriptEngineManager;
/*     */ import javax.script.ScriptException;
/*     */ import jdk.nashorn.api.scripting.ClassFilter;
/*     */ import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ public class ScriptHost
/*     */ {
/*  32 */   public static ScriptHost INSTANCE = new ScriptHost();
/*  33 */   public static HashMap<ResourceLocation, ScriptClient> clients = new HashMap<>();
/*     */   
/*  35 */   private static final ScriptAPI ScriptAPI = new ScriptAPI();
/*  36 */   private static final NBTSearcher NBTSearcher = new NBTSearcher();
/*  37 */   private static final String[] allowList = new String[] { ArrayList.class
/*     */       
/*  39 */       .getName(), HashMap.class.getName(), WeaponFireMode.class.getName() };
/*  40 */   private static final ClassFilter classFilter = new ClassFilter()
/*     */     {
/*     */       public boolean exposeToScripts(String tar)
/*     */       {
/*  44 */         for (String str : ScriptHost.allowList) {
/*  45 */           if (tar.startsWith(str)) {
/*  46 */             return true;
/*     */           }
/*     */         } 
/*     */         
/*  50 */         return false;
/*     */       }
/*     */     };
/*     */   
/*     */   public static class ScriptClient
/*     */   {
/*     */     public Invocable invocable;
/*     */     public String hash;
/*     */     
/*     */     public ScriptClient(Invocable invocable, String hash) {
/*  60 */       this.invocable = invocable;
/*  61 */       this.hash = hash;
/*     */     }
/*     */     
/*     */     public Invocable getInvocable() {
/*  65 */       return this.invocable;
/*     */     }
/*     */     
/*     */     public String getHash() {
/*  69 */       return this.hash;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean callScript(ResourceLocation scriptLoc, ItemStack stack, List<String> tooltip, String function) {
/*  74 */     if (clients.containsKey(scriptLoc)) {
/*     */       try {
/*  76 */         ((ScriptClient)clients.get(scriptLoc)).getInvocable().invokeFunction(function, new Object[] { stack, tooltip });
/*  77 */       } catch (NoSuchMethodException|ScriptException e) {
/*     */         
/*  79 */         e.printStackTrace();
/*  80 */         return false;
/*     */       } 
/*     */     } else {
/*  83 */       return false;
/*     */     } 
/*  85 */     return true;
/*     */   }
/*     */   
/*     */   public void initScript(ResourceLocation scriptLoc, String text) {
/*  89 */     ScriptEngineManager engineManager = new ScriptEngineManager();
/*  90 */     NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
/*  91 */     ScriptEngine scriptEngine = factory.getScriptEngine(classFilter);
/*  92 */     if (scriptEngine != null) {
/*     */       try {
/*  94 */         scriptEngine.eval("var WeaponFireMode=Java.type('" + WeaponFireMode.class.getName() + "');");
/*  95 */         scriptEngine.eval(text);
/*  96 */         scriptEngine.put("NBTSearcher", NBTSearcher);
/*  97 */         scriptEngine.put("ScriptAPI", ScriptAPI);
/*  98 */       } catch (ScriptException e) {
/*  99 */         e.printStackTrace();
/*     */       } 
/* 101 */       if (scriptEngine instanceof Invocable) {
/* 102 */         clients.put(scriptLoc, new ScriptClient((Invocable)scriptEngine, genHash(text)));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void initScriptFromResource(String scriptLoc) {
/* 108 */     ScriptEngineManager engineManager = new ScriptEngineManager();
/* 109 */     NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
/* 110 */     ScriptEngine scriptEngine = factory.getScriptEngine(classFilter);
/* 111 */     String text = "";
/* 112 */     if (scriptEngine != null) {
/*     */       try {
/* 114 */         InputStream inputStream = ScriptHost.class.getClassLoader().getResourceAsStream("assets/modularwarfare/script/" + scriptLoc + ".js");
/* 115 */         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
/*     */         String temp;
/* 117 */         while ((temp = bufferedReader.readLine()) != null) {
/* 118 */           text = text + temp + "\n";
/*     */         }
/* 120 */         bufferedReader.close();
/* 121 */         scriptEngine.eval("var WeaponFireMode=Java.type('" + WeaponFireMode.class.getName() + "');");
/* 122 */         scriptEngine.eval(text);
/* 123 */         scriptEngine.put("NBTSearcher", NBTSearcher);
/* 124 */         scriptEngine.put("ScriptAPI", ScriptAPI);
/* 125 */       } catch (ScriptException|java.io.IOException e) {
/* 126 */         e.printStackTrace();
/*     */       } 
/* 128 */       if (scriptEngine instanceof Invocable) {
/* 129 */         clients.put(new ResourceLocation("modularwarfare", "script/" + scriptLoc + ".js"), new ScriptClient((Invocable)scriptEngine, genHash(text)));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/* 135 */     clients.clear();
/* 136 */     initScriptFromResource("mwf/tooltip_main");
/*     */   }
/*     */   
/*     */   public static String genHash(String text) {
/* 140 */     return Hashing.sha1().hashString(text, Charset.forName("UTF-8")).toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\script\ScriptHost.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */