/*     */ package com.modularwarfare.common.network;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageCodec;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.EnumMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
/*     */ import net.minecraftforge.fml.common.network.FMLOutboundHandler;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry;
/*     */ import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Sharable
/*     */ public class NetworkHandler
/*     */   extends MessageToMessageCodec<FMLProxyPacket, PacketBase>
/*     */ {
/*     */   private EnumMap<Side, FMLEmbeddedChannel> channels;
/*  42 */   private LinkedList<Class<? extends PacketBase>> packets = new LinkedList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean modInitialised = false;
/*     */ 
/*     */   
/*  49 */   private ConcurrentLinkedQueue<PacketBase> receivedPacketsClient = new ConcurrentLinkedQueue<>();
/*  50 */   private ConcurrentHashMap<String, ConcurrentLinkedQueue<PacketBase>> receivedPacketsServer = new ConcurrentHashMap<>();
/*     */ 
/*     */   
/*  53 */   private Object lock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean registerPacket(Class<? extends PacketBase> cl) {
/*  59 */     if (this.packets.size() > 256) {
/*  60 */       ModularWarfare.LOGGER.warn("Packet limit exceeded in Modular Warfare packet handler by packet " + cl.getCanonicalName() + ".");
/*  61 */       return false;
/*     */     } 
/*  63 */     if (this.packets.contains(cl)) {
/*  64 */       ModularWarfare.LOGGER.warn("Tried to register " + cl.getCanonicalName() + " packet class twice.");
/*  65 */       return false;
/*     */     } 
/*  67 */     if (this.modInitialised) {
/*  68 */       ModularWarfare.LOGGER.warn("Tried to register packet " + cl.getCanonicalName() + " after mod initialisation.");
/*  69 */       return false;
/*     */     } 
/*     */     
/*  72 */     this.packets.add(cl);
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, PacketBase msg, List<Object> out) throws Exception {
/*  78 */     synchronized (this.lock) {
/*     */       
/*     */       try {
/*  81 */         ByteBuf encodedData = Unpooled.buffer();
/*     */         
/*  83 */         Class<? extends PacketBase> cl = (Class)msg.getClass();
/*     */ 
/*     */         
/*  86 */         if (!this.packets.contains(cl)) {
/*  87 */           throw new NullPointerException("Packet not registered : " + cl.getCanonicalName());
/*     */         }
/*     */         
/*  90 */         byte discriminator = (byte)this.packets.indexOf(cl);
/*  91 */         encodedData.writeByte(discriminator);
/*     */         
/*  93 */         msg.encodeInto(ctx, encodedData);
/*     */ 
/*     */         
/*  96 */         FMLProxyPacket proxyPacket = new FMLProxyPacket(new PacketBuffer(encodedData.copy()), (String)ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
/*     */         
/*  98 */         out.add(proxyPacket);
/*  99 */       } catch (Exception e) {
/* 100 */         ModularWarfare.LOGGER.error("ERROR encoding packet");
/* 101 */         ModularWarfare.LOGGER.throwing(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
/* 108 */     synchronized (this.lock) {
/*     */       try {
/*     */         INetHandler netHandler; EntityPlayerMP entityPlayerMP;
/* 111 */         ByteBuf encodedData = msg.payload();
/*     */         
/* 113 */         byte discriminator = encodedData.readByte();
/* 114 */         Class<? extends PacketBase> cl = this.packets.get(discriminator);
/*     */ 
/*     */         
/* 117 */         if (cl == null) {
/* 118 */           throw new NullPointerException("Packet not registered for discriminator : " + discriminator);
/*     */         }
/*     */         
/* 121 */         PacketBase packet = cl.newInstance();
/* 122 */         packet.decodeInto(ctx, encodedData.slice());
/*     */         
/* 124 */         switch (FMLCommonHandler.instance().getEffectiveSide()) {
/*     */           case CLIENT:
/* 126 */             this.receivedPacketsClient.offer(packet);
/*     */             break;
/*     */ 
/*     */           
/*     */           case SERVER:
/* 131 */             netHandler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
/* 132 */             entityPlayerMP = ((NetHandlerPlayServer)netHandler).field_147369_b;
/* 133 */             if (!this.receivedPacketsServer.containsKey(entityPlayerMP.func_70005_c_()))
/* 134 */               this.receivedPacketsServer.put(entityPlayerMP.func_70005_c_(), new ConcurrentLinkedQueue<>()); 
/* 135 */             ((ConcurrentLinkedQueue<PacketBase>)this.receivedPacketsServer.get(entityPlayerMP.func_70005_c_())).offer(packet);
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/* 140 */       } catch (Exception e) {
/* 141 */         ModularWarfare.LOGGER.error("ERROR decoding packet");
/* 142 */         ModularWarfare.LOGGER.throwing(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleClientPackets() {
/* 148 */     synchronized (this.lock) {
/* 149 */       for (PacketBase packet = this.receivedPacketsClient.poll(); packet != null; packet = this.receivedPacketsClient.poll()) {
/* 150 */         packet.handleClientSide(getClientPlayer());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleServerPackets() {
/* 156 */     synchronized (this.lock) {
/* 157 */       for (String playerName : this.receivedPacketsServer.keySet()) {
/* 158 */         ConcurrentLinkedQueue<PacketBase> receivedPacketsFromPlayer = this.receivedPacketsServer.get(playerName);
/* 159 */         EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_152612_a(playerName);
/* 160 */         if (player == null) {
/* 161 */           receivedPacketsFromPlayer.clear();
/*     */           continue;
/*     */         } 
/* 164 */         for (PacketBase packet = receivedPacketsFromPlayer.poll(); packet != null; packet = receivedPacketsFromPlayer.poll()) {
/* 165 */           packet.handleServerSide(player);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialise() {
/* 175 */     this.channels = NetworkRegistry.INSTANCE.newChannel("ModularWarfare", new ChannelHandler[] { (ChannelHandler)this });
/*     */     
/* 177 */     registerPacket((Class)PacketGunFire.class);
/* 178 */     registerPacket((Class)PacketPlaySound.class);
/* 179 */     registerPacket((Class)PacketPlayHitmarker.class);
/* 180 */     registerPacket((Class)PacketGunSwitchMode.class);
/* 181 */     registerPacket((Class)PacketGunReload.class);
/* 182 */     registerPacket((Class)PacketGunReloadEnhancedTask.class);
/* 183 */     registerPacket((Class)PacketGunReloadEnhancedStop.class);
/* 184 */     registerPacket((Class)PacketGunReloadSound.class);
/* 185 */     registerPacket((Class)PacketGunAddAttachment.class);
/* 186 */     registerPacket((Class)PacketGunUnloadAttachment.class);
/* 187 */     registerPacket((Class)PacketClientAnimation.class);
/* 188 */     registerPacket((Class)PacketGunTrail.class);
/* 189 */     registerPacket((Class)PacketAimingRequest.class);
/* 190 */     registerPacket((Class)PacketAimingReponse.class);
/* 191 */     registerPacket((Class)PacketDecal.class);
/* 192 */     registerPacket((Class)PacketOpenNormalInventory.class);
/* 193 */     registerPacket((Class)PacketOpenExtraArmorInventory.class);
/* 194 */     registerPacket((Class)PacketSyncBackWeapons.class);
/* 195 */     registerPacket((Class)PacketBulletSnap.class);
/* 196 */     registerPacket((Class)PacketParticle.class);
/* 197 */     registerPacket((Class)PacketPlayerHit.class);
/*     */     
/* 199 */     registerPacket((Class)PacketSyncExtraSlot.class);
/* 200 */     registerPacket((Class)PacketVerification.class);
/* 201 */     registerPacket((Class)PacketOpenGui.class);
/* 202 */     registerPacket((Class)PacketExplosion.class);
/* 203 */     registerPacket((Class)PacketClientKillFeedEntry.class);
/*     */     
/* 205 */     registerPacket((Class)PacketFlashClient.class);
/* 206 */     registerPacket((Class)PacketExpGunFire.class);
/* 207 */     registerPacket((Class)PacketGunTrailAskServer.class);
/* 208 */     registerPacket((Class)PacketExpShot.class);
/*     */     
/* 210 */     registerPacket((Class)PacketOtherPlayerAnimation.class);
/* 211 */     registerPacket((Class)PacketBackpackElytraStart.class);
/* 212 */     registerPacket((Class)PacketBackpackJet.class);
/*     */     
/* 214 */     registerPacket((Class)PacketCustomAnimation.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postInitialise() {
/* 223 */     if (this.modInitialised) {
/*     */       return;
/*     */     }
/* 226 */     this.modInitialised = true;
/*     */     
/* 228 */     Collections.sort(this.packets, new Comparator<Class<? extends PacketBase>>()
/*     */         {
/*     */           public int compare(Class<? extends PacketBase> c1, Class<? extends PacketBase> c2)
/*     */           {
/* 232 */             int com = String.CASE_INSENSITIVE_ORDER.compare(c1.getCanonicalName(), c2.getCanonicalName());
/* 233 */             if (com == 0)
/* 234 */               com = c1.getCanonicalName().compareTo(c2.getCanonicalName()); 
/* 235 */             return com;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private EntityPlayer getClientPlayer() {
/* 242 */     return (EntityPlayer)(Minecraft.func_71410_x()).field_71439_g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAll(PacketBase packet) {
/* 249 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
/* 250 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendTo(PacketBase packet, EntityPlayerMP player) {
/* 257 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
/* 258 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
/* 259 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAllAround(PacketBase packet, NetworkRegistry.TargetPoint point) {
/* 266 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
/* 267 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
/* 268 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToDimension(PacketBase packet, int dimensionID) {
/* 275 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
/* 276 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(Integer.valueOf(dimensionID));
/* 277 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToServer(PacketBase packet) {
/* 284 */     ((FMLEmbeddedChannel)this.channels.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
/* 285 */     ((FMLEmbeddedChannel)this.channels.get(Side.CLIENT)).writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAllTracking(PacketBase packet, Entity entity) {
/* 296 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TRACKING_ENTITY);
/* 297 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(entity);
/* 298 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAll(Packet packet) {
/* 307 */     FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_148540_a(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendTo(Packet packet, EntityPlayerMP player) {
/* 314 */     player.field_71135_a.func_147359_a(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAllAround(Packet packet, NetworkRegistry.TargetPoint point) {
/* 321 */     FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_148543_a(null, point.x, point.y, point.z, point.range, point.dimension, packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToDimension(Packet packet, int dimensionID) {
/* 328 */     FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_148537_a(packet, dimensionID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToServer(Packet packet) {
/* 335 */     (Minecraft.func_71410_x()).field_71439_g.field_71174_a.func_147297_a(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAllAround(PacketBase packet, double x, double y, double z, float range, int dimension) {
/* 342 */     sendToAllAround(packet, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\NetworkHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */