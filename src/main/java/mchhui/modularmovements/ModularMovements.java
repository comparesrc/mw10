/*    */ package mchhui.modularmovements;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import java.io.File;
/*    */ import mchhui.modularmovements.network.Handler;
/*    */ import mchhui.modularmovements.tactical.client.ClientLitener;
/*    */ import mchhui.modularmovements.tactical.client.MWFClientListener;
/*    */ import mchhui.modularmovements.tactical.server.MWFServerListener;
/*    */ import mchhui.modularmovements.tactical.server.ServerListener;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*    */ import net.minecraftforge.fml.common.Loader;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventHandler;
/*    */ import net.minecraftforge.fml.common.Mod.Instance;
/*    */ import net.minecraftforge.fml.common.event.FMLInitializationEvent;
/*    */ import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
/*    */ import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
/*    */ import net.minecraftforge.fml.common.network.FMLEventChannel;
/*    */ import net.minecraftforge.fml.common.network.NetworkRegistry;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ @Mod(modid = "modularmovements")
/*    */ public class ModularMovements
/*    */ {
/*    */   public static boolean enableTactical = true;
/*    */   @SideOnly(Side.CLIENT)
/*    */   public static ClientLitener TacticalClientListener;
/* 32 */   public static ServerListener TacticalServerListener = new ServerListener();
/*    */   
/*    */   public static FMLEventChannel channel;
/*    */   
/*    */   public static boolean mwfEnable = false;
/*    */   
/*    */   public static final String MOD_ID = "modularmovements";
/*    */   
/*    */   public static final String MOD_NAME = "ModularMovements";
/*    */   
/*    */   public static final String MOD_VERSION = "1.0.0f";
/*    */   
/*    */   @Instance("modularmovements")
/*    */   public static ModularMovements INSTANCE;
/*    */   
/*    */   public static File MOD_DIR;
/*    */   public static Logger LOGGER;
/*    */   public static ModularMovementsConfig CONFIG;
/*    */   
/*    */   @EventHandler
/*    */   public void onPreInit(FMLPreInitializationEvent event) {
/* 53 */     LOGGER = event.getModLog();
/*    */     
/* 55 */     MOD_DIR = new File(event.getModConfigurationDirectory().getParentFile(), "ModularMovements");
/* 56 */     if (!MOD_DIR.exists()) {
/* 57 */       MOD_DIR.mkdir();
/* 58 */       LOGGER.info("Created ModularMovements folder.");
/*    */     } 
/* 60 */     new ModularMovementsConfig(new File(MOD_DIR, "mod_config.json"));
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onInit(FMLInitializationEvent event) {
/* 66 */     if (Loader.isModLoaded("modularwarfare")) {
/* 67 */       mwfEnable = true;
/*    */     }
/* 69 */     mwfEnable = true;
/* 70 */     ModularWarfare.isLoadedModularMovements = true;
/* 71 */     channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("modularmovements");
/* 72 */     channel.register(new Handler());
/* 73 */     if (enableTactical) {
/* 74 */       if (FMLCommonHandler.instance().getSide().isClient()) {
/* 75 */         TacticalClientListener = new ClientLitener();
/* 76 */         MinecraftForge.EVENT_BUS.register(TacticalClientListener);
/* 77 */         if (mwfEnable) {
/* 78 */           MinecraftForge.EVENT_BUS.register(new MWFClientListener());
/*    */         }
/* 80 */         TacticalClientListener.onFMLInit(event);
/*    */       } 
/* 82 */       TacticalServerListener.onFMLInit(event);
/* 83 */       MinecraftForge.EVENT_BUS.register(TacticalServerListener);
/* 84 */       if (mwfEnable) {
/* 85 */         MinecraftForge.EVENT_BUS.register(new MWFServerListener());
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onInitPost(FMLPostInitializationEvent event) {
/* 92 */     if (enableTactical && 
/* 93 */       FMLCommonHandler.instance().getSide().isClient())
/* 94 */       TacticalClientListener.onFMLInitPost(event); 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\ModularMovements.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */