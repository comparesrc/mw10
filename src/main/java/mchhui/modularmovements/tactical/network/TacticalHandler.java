/*     */ package mchhui.modularmovements.tactical.network;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import mchhui.modularmovements.ModularMovements;
/*     */ import mchhui.modularmovements.network.EnumFeatures;
/*     */ import mchhui.modularmovements.tactical.PlayerState;
/*     */ import mchhui.modularmovements.tactical.client.ClientLitener;
/*     */ import mchhui.modularmovements.tactical.server.ServerListener;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*     */ import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
/*     */ 
/*     */ public class TacticalHandler {
/*     */   public static void onHandle(FMLNetworkEvent.ClientCustomPacketEvent event) { int id, code, client_code;
/*     */     boolean withGunsOnly;
/*     */     float slideMaxForce;
/*     */     boolean blockView;
/*     */     float blockAngle, sitCooldown, crawlCooldown;
/*  22 */     PacketBuffer buffer = (PacketBuffer)event.getPacket().payload();
/*  23 */     EnumPacketType type = (EnumPacketType)buffer.func_179257_a(EnumPacketType.class);
/*     */     
/*  25 */     switch (type) {
/*     */       case STATE:
/*  27 */         id = buffer.readInt();
/*  28 */         code = buffer.readInt();
/*  29 */         if (id == (Minecraft.func_71410_x()).field_71439_g.func_145782_y()) {
/*     */           break;
/*     */         }
/*  32 */         if (!ClientLitener.ohterPlayerStateMap.containsKey(Integer.valueOf(id))) {
/*  33 */           ClientLitener.ohterPlayerStateMap.put(Integer.valueOf(id), new PlayerState());
/*     */         }
/*  35 */         state = (PlayerState)ClientLitener.ohterPlayerStateMap.get(Integer.valueOf(id));
/*  36 */         state.readCode(code);
/*     */         break;
/*     */       case SET_STATE:
/*  39 */         client_code = buffer.readInt();
/*  40 */         ClientLitener.clientPlayerState.readCode(client_code);
/*     */         break;
/*     */       
/*     */       case MOD_CONFIG:
/*  44 */         withGunsOnly = buffer.readBoolean();
/*  45 */         slideMaxForce = buffer.readFloat();
/*  46 */         blockView = buffer.readBoolean();
/*  47 */         blockAngle = buffer.readFloat();
/*  48 */         sitCooldown = buffer.readFloat();
/*  49 */         crawlCooldown = buffer.readFloat();
/*     */         
/*  51 */         ModularMovements.CONFIG.lean.withGunsOnly = withGunsOnly;
/*  52 */         ModularMovements.CONFIG.slide.maxForce = slideMaxForce;
/*  53 */         ModularMovements.CONFIG.crawl.blockView = blockView;
/*  54 */         ModularMovements.CONFIG.crawl.blockAngle = blockAngle;
/*  55 */         ModularMovements.CONFIG.cooldown.sitCooldown = sitCooldown;
/*  56 */         ModularMovements.CONFIG.cooldown.crawlCooldown = crawlCooldown;
/*     */         break;
/*     */     }  } private static PlayerState state; public static void onHandle(FMLNetworkEvent.ServerCustomPacketEvent event) {
/*     */     int code;
/*     */     boolean flag;
/*     */     int time;
/*  62 */     PacketBuffer buffer = (PacketBuffer)event.getPacket().payload();
/*  63 */     EntityPlayerMP entityPlayerMP = ((NetHandlerPlayServer)event.getHandler()).field_147369_b;
/*  64 */     EnumPacketType type = (EnumPacketType)buffer.func_179257_a(EnumPacketType.class);
/*     */     
/*  66 */     switch (type) {
/*     */       case STATE:
/*  68 */         code = buffer.readInt();
/*  69 */         flag = false;
/*  70 */         if (ServerListener.playerStateMap.containsKey(Integer.valueOf(entityPlayerMP.func_145782_y()))) {
/*  71 */           state = (PlayerState)ServerListener.playerStateMap.get(Integer.valueOf(entityPlayerMP.func_145782_y()));
/*  72 */           if (code != state.writeCode()) {
/*  73 */             flag = true;
/*  74 */             state.readCode(code);
/*  75 */             sendToClient((EntityPlayer)entityPlayerMP, code);
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       case NOFALL:
/*  80 */         ((EntityPlayer)entityPlayerMP).field_70143_R = 0.0F;
/*     */         break;
/*     */       case NOSTEP:
/*  83 */         time = buffer.readInt();
/*  84 */         ServerListener.playerNotStepMap.put(Integer.valueOf(entityPlayerMP.func_145782_y()), Long.valueOf(System.currentTimeMillis() + time));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendToClient(EntityPlayer entityPlayer, int code) {
/*  92 */     PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
/*  93 */     buffer.func_179249_a((Enum)EnumFeatures.Tactical);
/*  94 */     buffer.func_179249_a(EnumPacketType.STATE);
/*  95 */     buffer.writeInt(entityPlayer.func_145782_y());
/*  96 */     buffer.writeInt(code);
/*  97 */     ModularMovements.channel.sendToAll(new FMLProxyPacket(buffer, "modularmovements"));
/*     */   }
/*     */   
/*     */   public static void sendToServer(int code) {
/* 101 */     PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
/* 102 */     buffer.func_179249_a((Enum)EnumFeatures.Tactical);
/* 103 */     buffer.func_179249_a(EnumPacketType.STATE);
/* 104 */     buffer.writeInt(code);
/* 105 */     ModularMovements.channel.sendToServer(new FMLProxyPacket(buffer, "modularmovements"));
/*     */   }
/*     */   
/*     */   public static void sendStateSettng(EntityPlayerMP entityPlayer, int code) {
/* 109 */     PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
/* 110 */     buffer.func_179249_a((Enum)EnumFeatures.Tactical);
/* 111 */     buffer.func_179249_a(EnumPacketType.SET_STATE);
/* 112 */     buffer.writeInt(code);
/* 113 */     ModularMovements.channel.sendTo(new FMLProxyPacket(buffer, "modularmovements"), entityPlayer);
/*     */   }
/*     */   
/*     */   public static void sendNoFall() {
/* 117 */     PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
/* 118 */     buffer.func_179249_a((Enum)EnumFeatures.Tactical);
/* 119 */     buffer.func_179249_a(EnumPacketType.NOFALL);
/* 120 */     ModularMovements.channel.sendToServer(new FMLProxyPacket(buffer, "modularmovements"));
/*     */   }
/*     */   
/*     */   public static void sendNoStep(int time) {
/* 124 */     PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
/* 125 */     buffer.func_179249_a((Enum)EnumFeatures.Tactical);
/* 126 */     buffer.func_179249_a(EnumPacketType.NOSTEP);
/* 127 */     buffer.writeInt(time);
/* 128 */     ModularMovements.channel.sendToServer(new FMLProxyPacket(buffer, "modularmovements"));
/*     */   }
/*     */   
/*     */   public static void sendClientConfig(EntityPlayerMP entityPlayer) {
/* 132 */     PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
/* 133 */     buffer.func_179249_a((Enum)EnumFeatures.Tactical);
/* 134 */     buffer.func_179249_a(EnumPacketType.MOD_CONFIG);
/* 135 */     buffer.writeBoolean(ModularMovements.CONFIG.lean.withGunsOnly);
/* 136 */     buffer.writeFloat(ModularMovements.CONFIG.slide.maxForce);
/* 137 */     buffer.writeBoolean(ModularMovements.CONFIG.crawl.blockView);
/* 138 */     buffer.writeFloat(ModularMovements.CONFIG.crawl.blockAngle);
/* 139 */     buffer.writeFloat(ModularMovements.CONFIG.cooldown.sitCooldown);
/* 140 */     buffer.writeFloat(ModularMovements.CONFIG.cooldown.crawlCooldown);
/* 141 */     ModularMovements.channel.sendTo(new FMLProxyPacket(buffer, "modularmovements"), entityPlayer);
/*     */   }
/*     */   
/*     */   public enum EnumPacketType {
/* 145 */     STATE, SET_STATE, NOFALL, NOSTEP, MOD_CONFIG;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\tactical\network\TacticalHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */