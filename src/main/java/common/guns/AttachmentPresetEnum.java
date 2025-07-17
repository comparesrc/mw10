/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ public enum AttachmentPresetEnum
/*    */ {
/*  8 */   Sight("sight"),
/*  9 */   Slide("slide"),
/* 10 */   Grip("grip"),
/* 11 */   Flashlight("flashlight"),
/* 12 */   Charm("charm"),
/* 13 */   Skin("skin"),
/* 14 */   Barrel("barrel"),
/* 15 */   Stock("stock");
/*    */   
/*    */   public String typeName;
/*    */   
/*    */   AttachmentPresetEnum(String typeName) {
/* 20 */     this.typeName = typeName;
/*    */   }
/*    */   
/*    */   public static AttachmentPresetEnum getAttachment(String typeName) {
/* 24 */     for (AttachmentPresetEnum attachmentEnum : values()) {
/* 25 */       if (attachmentEnum.typeName.equalsIgnoreCase(typeName)) {
/* 26 */         return attachmentEnum;
/*    */       }
/*    */     } 
/* 29 */     return Sight;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 33 */     return this.typeName;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\AttachmentPresetEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */