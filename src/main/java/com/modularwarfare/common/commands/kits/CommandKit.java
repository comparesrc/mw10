/*     */ package com.modularwarfare.common.commands.kits;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*     */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ 
/*     */ public class CommandKit extends CommandBase {
/*  25 */   public Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/*     */   
/*  27 */   public File KIT_FILE = new File(ModularWarfare.MOD_DIR, "kits.json");
/*     */   
/*     */   public int func_82362_a() {
/*  30 */     return 2;
/*     */   }
/*     */   
/*     */   public String func_71517_b() {
/*  34 */     return "mw-kit";
/*     */   }
/*     */   
/*     */   public String func_71518_a(ICommandSender sender) {
/*  38 */     return "/mw-kit <save/delete/give> <name> [player]";
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  43 */     if (!this.KIT_FILE.exists()) {
/*  44 */       try (Writer writer = new OutputStreamWriter(new FileOutputStream(this.KIT_FILE), "UTF-8")) {
/*  45 */         this.gson.toJson(new Kits(), writer);
/*  46 */       } catch (FileNotFoundException e) {
/*  47 */         e.printStackTrace();
/*  48 */       } catch (UnsupportedEncodingException e) {
/*  49 */         e.printStackTrace();
/*  50 */       } catch (IOException e) {
/*  51 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*  54 */     if (args.length >= 1) {
/*  55 */       if (args[0].equalsIgnoreCase("give") && args.length >= 2) {
/*  56 */         String name = args[1];
/*  57 */         EntityPlayerMP player = null;
/*  58 */         if (args.length == 2 && sender instanceof EntityPlayerMP) {
/*  59 */           player = (EntityPlayerMP)sender;
/*     */         } else {
/*  61 */           player = func_184888_a(server, sender, args[2]);
/*     */         } 
/*  63 */         if (sender != null) {
/*     */           try {
/*  65 */             JsonReader jsonReader = new JsonReader(new FileReader(this.KIT_FILE));
/*  66 */             Kits kits = (Kits)this.gson.fromJson(jsonReader, Kits.class);
/*     */             
/*  68 */             Kits.Kit kit = null;
/*  69 */             for (int i = 0; i < kits.kits.size(); i++) {
/*  70 */               if (((Kits.Kit)kits.kits.get(i)).name.equalsIgnoreCase(name)) {
/*  71 */                 kit = kits.kits.remove(i);
/*     */               }
/*     */             } 
/*     */             
/*  75 */             if (kit != null) {
/*  76 */               if (player != null) {
/*  77 */                 if (kit.force) {
/*  78 */                   player.field_71071_by.func_70443_b(JsonToNBT.func_180713_a(kit.data).func_150295_c("items", 10));
/*     */                   
/*  80 */                   IExtraItemHandler extra = (IExtraItemHandler)player.getCapability(CapabilityExtra.CAPABILITY, null);
/*  81 */                   extra.setStackInSlot(0, new ItemStack(JsonToNBT.func_180713_a(kit.backpack)));
/*  82 */                   extra.setStackInSlot(1, new ItemStack(JsonToNBT.func_180713_a(kit.vest)));
/*     */                 } else {
/*  84 */                   NBTTagList tagList = JsonToNBT.func_180713_a(kit.data).func_150295_c("items", 10);
/*  85 */                   for (int j = 0; j < tagList.func_74745_c(); j++) {
/*  86 */                     NBTTagCompound nbttagcompound = tagList.func_150305_b(j);
/*  87 */                     int k = nbttagcompound.func_74771_c("Slot") & 0xFF;
/*  88 */                     ItemStack itemstack = new ItemStack(nbttagcompound);
/*     */                     
/*  90 */                     if (!itemstack.func_190926_b()) {
/*  91 */                       if (k >= 0 && k < player.field_71071_by.field_70462_a.size()) {
/*  92 */                         player.field_71071_by.field_70462_a.add(k, itemstack);
/*  93 */                       } else if (k >= 100 && k < player.field_71071_by.field_70460_b.size() + 100) {
/*  94 */                         player.field_71071_by.field_70460_b.add(k, itemstack);
/*  95 */                       } else if (k >= 150 && k < player.field_71071_by.field_184439_c.size() + 150) {
/*  96 */                         player.field_71071_by.field_184439_c.add(k, itemstack);
/*     */                       } 
/*     */                     }
/*     */                   } 
/*     */                 } 
/* 101 */                 sender.func_145747_a((ITextComponent)new TextComponentString(ModularWarfare.MOD_PREFIX + " " + TextFormatting.YELLOW + args[2] + TextFormatting.GRAY + " has received the kit " + TextFormatting.YELLOW + name + TextFormatting.GRAY + "."));
/*     */               } else {
/* 103 */                 sender.func_145747_a((ITextComponent)new TextComponentString(ModularWarfare.MOD_PREFIX + " " + TextFormatting.YELLOW + args[2] + TextFormatting.GRAY + " is not online."));
/*     */               } 
/*     */             } else {
/* 106 */               sender.func_145747_a((ITextComponent)new TextComponentString(ModularWarfare.MOD_PREFIX + " The kit " + TextFormatting.YELLOW + name + TextFormatting.GRAY + " doest not exist."));
/*     */             } 
/* 108 */           } catch (FileNotFoundException|net.minecraft.nbt.NBTException e) {
/* 109 */             e.printStackTrace();
/*     */           } 
/*     */         } else {
/*     */           
/* 113 */           sender.func_145747_a((ITextComponent)new TextComponentString(ModularWarfare.MOD_PREFIX + " " + TextFormatting.YELLOW + args[2] + TextFormatting.GRAY + " is not connected."));
/*     */         } 
/* 115 */       } else if (args[0].equalsIgnoreCase("save") && args.length == 2) {
/* 116 */         if (sender instanceof EntityPlayerMP) {
/* 117 */           EntityPlayerMP player = (EntityPlayerMP)sender;
/*     */           
/* 119 */           NBTTagCompound compound = new NBTTagCompound();
/* 120 */           NBTTagList kitData = player.field_71071_by.func_70442_a(new NBTTagList());
/* 121 */           compound.func_74782_a("items", (NBTBase)kitData);
/*     */           
/*     */           try {
/* 124 */             JsonReader jsonReader = new JsonReader(new FileReader(this.KIT_FILE));
/* 125 */             Kits kits = (Kits)this.gson.fromJson(jsonReader, Kits.class);
/*     */             
/* 127 */             Kits.Kit kit = new Kits.Kit();
/* 128 */             kit.name = args[1];
/* 129 */             kit.data = compound.toString();
/*     */             
/* 131 */             IExtraItemHandler extra = (IExtraItemHandler)player.getCapability(CapabilityExtra.CAPABILITY, null);
/*     */ 
/*     */ 
/*     */             
/* 135 */             if (extra.getStackInSlot(0) != null) {
/* 136 */               kit.backpack = extra.getStackInSlot(0).serializeNBT().toString();
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 142 */             if (extra.getStackInSlot(1) != null) {
/* 143 */               kit.vest = extra.getStackInSlot(1).serializeNBT().toString();
/*     */             }
/*     */             
/* 146 */             boolean found = false;
/* 147 */             for (int i = 0; i < kits.kits.size(); i++) {
/* 148 */               if (((Kits.Kit)kits.kits.get(i)).name.equalsIgnoreCase(kit.name)) {
/* 149 */                 kits.kits.set(i, kit);
/* 150 */                 found = true;
/*     */               } 
/*     */             } 
/* 153 */             if (!found) kits.kits.add(kit);
/*     */             
/* 155 */             try (Writer writer = new OutputStreamWriter(new FileOutputStream(this.KIT_FILE), "UTF-8")) {
/* 156 */               this.gson.toJson(kits, writer);
/* 157 */               sender.func_145747_a((ITextComponent)new TextComponentString(ModularWarfare.MOD_PREFIX + " The kit " + TextFormatting.YELLOW + args[1] + TextFormatting.GRAY + " has been saved."));
/* 158 */             } catch (IOException e) {
/* 159 */               e.printStackTrace();
/*     */             } 
/* 161 */           } catch (FileNotFoundException e) {
/* 162 */             e.printStackTrace();
/*     */           }
/*     */         
/*     */         } 
/* 166 */       } else if (args[0].equalsIgnoreCase("delete") && args.length == 2) {
/*     */         try {
/* 168 */           JsonReader jsonReader = new JsonReader(new FileReader(this.KIT_FILE));
/* 169 */           Kits kits = (Kits)this.gson.fromJson(jsonReader, Kits.class);
/*     */           
/* 171 */           boolean found = false;
/* 172 */           for (int i = 0; i < kits.kits.size(); i++) {
/* 173 */             if (((Kits.Kit)kits.kits.get(i)).name.equalsIgnoreCase(args[1])) {
/* 174 */               kits.kits.remove(i);
/* 175 */               found = true;
/*     */             } 
/*     */           } 
/*     */           
/* 179 */           if (found) {
/* 180 */             try (Writer writer = new OutputStreamWriter(new FileOutputStream(this.KIT_FILE), "UTF-8")) {
/* 181 */               this.gson.toJson(kits, writer);
/* 182 */               sender.func_145747_a((ITextComponent)new TextComponentString(ModularWarfare.MOD_PREFIX + " The kit " + TextFormatting.YELLOW + args[1] + TextFormatting.GRAY + " has been deleted."));
/* 183 */             } catch (IOException e) {
/* 184 */               e.printStackTrace();
/*     */             } 
/*     */           } else {
/* 187 */             sender.func_145747_a((ITextComponent)new TextComponentString(ModularWarfare.MOD_PREFIX + " The kit " + TextFormatting.YELLOW + args[1] + TextFormatting.GRAY + " doesn't exist."));
/*     */           } 
/* 189 */         } catch (FileNotFoundException e) {
/* 190 */           e.printStackTrace();
/*     */         } 
/*     */       } else {
/* 193 */         sender.func_145747_a((ITextComponent)new TextComponentString(ModularWarfare.MOD_PREFIX + " /mw-kit <save/delete/give> <name> [player]"));
/*     */       } 
/*     */     } else {
/* 196 */       sender.func_145747_a((ITextComponent)new TextComponentString(ModularWarfare.MOD_PREFIX + " /mw-kit <save/delete/give> <name> [player]"));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\commands\kits\CommandKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */