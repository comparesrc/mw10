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
/*     */ 
/*     */   
/*     */   public boolean registerPacket(Class<? extends PacketBase> cl) {
/*  56 */     if (this.packets.size() > 256) {
/*  57 */       ModularWarfare.LOGGER.warn("Packet limit exceeded in Modular Warfare packet handler by packet " + cl.getCanonicalName() + ".");
/*  58 */       return false;
/*     */     } 
/*  60 */     if (this.packets.contains(cl)) {
/*  61 */       ModularWarfare.LOGGER.warn("Tried to register " + cl.getCanonicalName() + " packet class twice.");
/*  62 */       return false;
/*     */     } 
/*  64 */     if (this.modInitialised) {
/*  65 */       ModularWarfare.LOGGER.warn("Tried to register packet " + cl.getCanonicalName() + " after mod initialisation.");
/*  66 */       return false;
/*     */     } 
/*     */     
/*  69 */     this.packets.add(cl);
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, PacketBase msg, List<Object> out) throws Exception {
/*     */     try {
/*  77 */       ByteBuf encodedData = Unpooled.buffer();
/*     */       
/*  79 */       Class<? extends PacketBase> cl = (Class)msg.getClass();
/*     */ 
/*     */       
/*  82 */       if (!this.packets.contains(cl)) {
/*  83 */         throw new NullPointerException("Packet not registered : " + cl.getCanonicalName());
/*     */       }
/*     */       
/*  86 */       byte discriminator = (byte)this.packets.indexOf(cl);
/*  87 */       encodedData.writeByte(discriminator);
/*     */       
/*  89 */       msg.encodeInto(ctx, encodedData);
/*     */ 
/*     */       
/*  92 */       FMLProxyPacket proxyPacket = new FMLProxyPacket(new PacketBuffer(encodedData.copy()), (String)ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
/*     */       
/*  94 */       out.add(proxyPacket);
/*  95 */     } catch (Exception e) {
/*  96 */       ModularWarfare.LOGGER.error("ERROR encoding packet");
/*  97 */       ModularWarfare.LOGGER.throwing(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
/*     */     try {
/*     */       INetHandler netHandler;
/*     */       EntityPlayerMP entityPlayerMP;
/* 105 */       ByteBuf encodedData = msg.payload();
/*     */       
/* 107 */       byte discriminator = encodedData.readByte();
/* 108 */       Class<? extends PacketBase> cl = this.packets.get(discriminator);
/*     */ 
/*     */       
/* 111 */       if (cl == null) {
/* 112 */         throw new NullPointerException("Packet not registered for discriminator : " + discriminator);
/*     */       }
/*     */       
/* 115 */       PacketBase packet = cl.newInstance();
/* 116 */       packet.decodeInto(ctx, encodedData.slice());
/*     */       
/* 118 */       switch (FMLCommonHandler.instance().getEffectiveSide()) {
/*     */         case CLIENT:
/* 120 */           this.receivedPacketsClient.offer(packet);
/*     */           break;
/*     */ 
/*     */         
/*     */         case SERVER:
/* 125 */           netHandler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
/* 126 */           entityPlayerMP = ((NetHandlerPlayServer)netHandler).field_147369_b;
/* 127 */           if (!this.receivedPacketsServer.containsKey(entityPlayerMP.func_70005_c_()))
/* 128 */             this.receivedPacketsServer.put(entityPlayerMP.func_70005_c_(), new ConcurrentLinkedQueue<>()); 
/* 129 */           ((ConcurrentLinkedQueue<PacketBase>)this.receivedPacketsServer.get(entityPlayerMP.func_70005_c_())).offer(packet);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/* 134 */     } catch (Exception e) {
/* 135 */       ModularWarfare.LOGGER.error("ERROR decoding packet");
/* 136 */       ModularWarfare.LOGGER.throwing(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleClientPackets() {
/* 141 */     for (PacketBase packet = this.receivedPacketsClient.poll(); packet != null; packet = this.receivedPacketsClient.poll()) {
/* 142 */       packet.handleClientSide(getClientPlayer());
/*     */     }
/*     */   }
/*     */   
/*     */   public void handleServerPackets() {
/* 147 */     for (String playerName : this.receivedPacketsServer.keySet()) {
/* 148 */       ConcurrentLinkedQueue<PacketBase> receivedPacketsFromPlayer = this.receivedPacketsServer.get(playerName);
/* 149 */       EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_152612_a(playerName);
/* 150 */       if (player == null) {
/* 151 */         receivedPacketsFromPlayer.clear();
/*     */         continue;
/*     */       } 
/* 154 */       for (PacketBase packet = receivedPacketsFromPlayer.poll(); packet != null; packet = receivedPacketsFromPlayer.poll()) {
/* 155 */         packet.handleServerSide(player);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialise() {
/* 164 */     this.channels = NetworkRegistry.INSTANCE.newChannel("ModularWarfare", new ChannelHandler[] { (ChannelHandler)this });
/*     */     
/* 166 */     registerPacket((Class)PacketGunFire.class);
/* 167 */     registerPacket((Class)PacketPlaySound.class);
/* 168 */     registerPacket((Class)PacketPlayHitmarker.class);
/* 169 */     registerPacket((Class)PacketGunSwitchMode.class);
/* 170 */     registerPacket((Class)PacketGunReload.class);
/* 171 */     registerPacket((Class)PacketGunReloadEnhancedTask.class);
/* 172 */     registerPacket((Class)PacketGunReloadEnhancedStop.class);
/* 173 */     registerPacket((Class)PacketGunReloadSound.class);
/* 174 */     registerPacket((Class)PacketGunAddAttachment.class);
/* 175 */     registerPacket((Class)PacketGunUnloadAttachment.class);
/* 176 */     registerPacket((Class)PacketClientAnimation.class);
/* 177 */     registerPacket((Class)PacketGunTrail.class);
/* 178 */     registerPacket((Class)PacketAimingRequest.class);
/* 179 */     registerPacket((Class)PacketAimingReponse.class);
/* 180 */     registerPacket((Class)PacketDecal.class);
/* 181 */     registerPacket((Class)PacketOpenNormalInventory.class);
/* 182 */     registerPacket((Class)PacketOpenExtraArmorInventory.class);
/* 183 */     registerPacket((Class)PacketSyncBackWeapons.class);
/* 184 */     registerPacket((Class)PacketBulletSnap.class);
/* 185 */     registerPacket((Class)PacketParticle.class);
/* 186 */     registerPacket((Class)PacketPlayerHit.class);
/*     */     
/* 188 */     registerPacket((Class)PacketSyncExtraSlot.class);
/* 189 */     registerPacket((Class)PacketVerification.class);
/* 190 */     registerPacket((Class)PacketOpenGui.class);
/* 191 */     registerPacket((Class)PacketExplosion.class);
/* 192 */     registerPacket((Class)PacketClientKillFeedEntry.class);
/*     */     
/* 194 */     registerPacket((Class)PacketFlashClient.class);
/* 195 */     registerPacket((Class)PacketExpGunFire.class);
/* 196 */     registerPacket((Class)PacketGunTrailAskServer.class);
/* 197 */     registerPacket((Class)PacketExpShot.class);
/*     */     
/* 199 */     registerPacket((Class)PacketOtherPlayerAnimation.class);
/* 200 */     registerPacket((Class)PacketBackpackElytraStart.class);
/* 201 */     registerPacket((Class)PacketBackpackJet.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postInitialise() {
/* 210 */     if (this.modInitialised) {
/*     */       return;
/*     */     }
/* 213 */     this.modInitialised = true;
/*     */     
/* 215 */     Collections.sort(this.packets, new Comparator<Class<? extends PacketBase>>()
/*     */         {
/*     */           public int compare(Class<? extends PacketBase> c1, Class<? extends PacketBase> c2)
/*     */           {
/* 219 */             int com = String.CASE_INSENSITIVE_ORDER.compare(c1.getCanonicalName(), c2.getCanonicalName());
/* 220 */             if (com == 0)
/* 221 */               com = c1.getCanonicalName().compareTo(c2.getCanonicalName()); 
/* 222 */             return com;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private EntityPlayer getClientPlayer() {
/* 229 */     return (EntityPlayer)(Minecraft.func_71410_x()).field_71439_g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAll(PacketBase packet) {
/* 236 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
/* 237 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendTo(PacketBase packet, EntityPlayerMP player) {
/* 244 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
/* 245 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
/* 246 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAllAround(PacketBase packet, NetworkRegistry.TargetPoint point) {
/* 253 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
/* 254 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
/* 255 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToDimension(PacketBase packet, int dimensionID) {
/* 262 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
/* 263 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(Integer.valueOf(dimensionID));
/* 264 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToServer(PacketBase packet) {
/* 271 */     ((FMLEmbeddedChannel)this.channels.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
/* 272 */     ((FMLEmbeddedChannel)this.channels.get(Side.CLIENT)).writeAndFlush(packet);
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
/* 283 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TRACKING_ENTITY);
/* 284 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(entity);
/* 285 */     ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAll(Packet packet) {
/* 294 */     FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_148540_a(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendTo(Packet packet, EntityPlayerMP player) {
/* 301 */     player.field_71135_a.func_147359_a(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAllAround(Packet packet, NetworkRegistry.TargetPoint point) {
/* 308 */     FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_148543_a(null, point.x, point.y, point.z, point.range, point.dimension, packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToDimension(Packet packet, int dimensionID) {
/* 315 */     FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_148537_a(packet, dimensionID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToServer(Packet packet) {
/* 322 */     (Minecraft.func_71410_x()).field_71439_g.field_71174_a.func_147297_a(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAllAround(PacketBase packet, double x, double y, double z, float range, int dimension) {
/* 329 */     sendToAllAround(packet, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\NetworkHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */