/*    */ package com.modularwarfare.client.killchat;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Comparator;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KillFeedManager
/*    */ {
/* 13 */   private Set<KillFeedEntry> entries = Sets.newConcurrentHashSet();
/*    */ 
/*    */   
/*    */   public Set<KillFeedEntry> getEntries() {
/* 17 */     return new HashSet<>(this.entries);
/*    */   }
/*    */   
/*    */   public void remove(KillFeedEntry entry) {
/* 21 */     this.entries.remove(entry);
/*    */   }
/*    */   
/*    */   public void add(KillFeedEntry entry) {
/* 25 */     if (this.entries.size() >= 6) {
/* 26 */       this.entries.stream().max(Comparator.comparingInt(KillFeedEntry::getTimeLived)).ifPresent(value -> this.entries.remove(value));
/*    */     }
/* 28 */     this.entries.add(entry);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\killchat\KillFeedManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */