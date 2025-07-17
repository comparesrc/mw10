/*    */ package mchhui.modularmovements;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.FileReader;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.io.Writer;
/*    */ 
/*    */ 
/*    */ public class ModularMovementsConfig
/*    */ {
/* 15 */   public Lean lean = new Lean();
/* 16 */   public Slide slide = new Slide();
/*    */   
/* 18 */   public Sit sit = new Sit();
/* 19 */   public Crawl crawl = new Crawl();
/*    */   
/* 21 */   public Cooldown cooldown = new Cooldown();
/*    */   
/*    */   public static class Lean {
/*    */     public boolean autoHold = false;
/*    */     public boolean mouseCorrection = true;
/*    */     public boolean withGunsOnly = false;
/*    */   }
/*    */   
/*    */   public static class Slide {
/*    */     public boolean enable = true;
/* 31 */     public float maxForce = 1.0F;
/*    */   }
/*    */   
/*    */   public static class Sit {
/*    */     public boolean autoHold = true;
/*    */   }
/*    */   
/*    */   public static class Crawl {
/*    */     public boolean blockView = false;
/* 40 */     public float blockAngle = 190.0F;
/*    */     public boolean sprintCancel = true;
/*    */   }
/*    */   
/*    */   public static class Cooldown {
/* 45 */     public float sitCooldown = 0.75F;
/* 46 */     public float crawlCooldown = 0.75F;
/* 47 */     public float leanCooldown = 0.0F;
/*    */   }
/*    */   
/* 50 */   public String version = "1.0.0f";
/*    */   
/*    */   public ModularMovementsConfig(File configFile) {
/* 53 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/*    */     try {
/* 55 */       if (configFile.exists()) {
/* 56 */         JsonReader jsonReader = new JsonReader(new FileReader(configFile));
/* 57 */         ModularMovementsConfig config = (ModularMovementsConfig)gson.fromJson(jsonReader, ModularMovementsConfig.class);
/* 58 */         System.out.println("Comparing version " + config.version + " to " + "1.0.0f");
/* 59 */         if (config.version == null || !config.version.matches("1.0.0f")) {
/* 60 */           try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8")) {
/* 61 */             gson.toJson(this, writer);
/*    */           } 
/* 63 */           ModularMovements.CONFIG = this;
/*    */         } else {
/* 65 */           ModularMovements.CONFIG = config;
/*    */         } 
/*    */       } else {
/* 68 */         try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8")) {
/* 69 */           gson.toJson(this, writer);
/*    */         } 
/* 71 */         ModularMovements.CONFIG = this;
/*    */       } 
/* 73 */     } catch (Exception e) {
/* 74 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\ModularMovementsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */