/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import com.fasterxml.jackson.annotation.JsonInclude;
/*     */ import com.fasterxml.jackson.core.JsonParser;
/*     */ import com.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import com.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.fasterxml.jackson.databind.JsonNode;
/*     */ import com.fasterxml.jackson.databind.Module;
/*     */ import com.fasterxml.jackson.databind.ObjectMapper;
/*     */ import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
/*     */ import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
/*     */ import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
/*     */ import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.fasterxml.jackson.databind.module.SimpleModule;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Iterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.logging.Logger;
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
/*     */ public class JacksonUtils
/*     */ {
/*  63 */   private static final Logger logger = Logger.getLogger(JacksonUtils.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final Consumer<JsonError> LOG_JSON_ERROR_CONSUMER = new Consumer<JsonError>()
/*     */     {
/*     */ 
/*     */       
/*     */       public void accept(JsonError jsonError)
/*     */       {
/*  74 */         JacksonUtils.logger.warning("Error: " + jsonError.getMessage() + ", JSON path " + jsonError
/*  75 */             .getJsonPathString());
/*     */       }
/*     */     };
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
/*     */   private static DeserializationProblemHandler createDeserializationProblemHandler(final Consumer<? super JsonError> jsonErrorConsumer) {
/*  92 */     return new DeserializationProblemHandler()
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser jp, JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException, JsonProcessingException
/*     */         {
/* 101 */           if (jsonErrorConsumer != null)
/*     */           {
/* 103 */             jsonErrorConsumer.accept(new JsonError("Unknown property: " + propertyName, jp
/*     */                   
/* 105 */                   .getParsingContext(), null));
/*     */           }
/* 107 */           return super.handleUnknownProperty(ctxt, jp, deserializer, beanOrClass, propertyName);
/*     */         }
/*     */       };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BeanDeserializerModifier createErrorHandlingBeanDeserializerModifier(final Consumer<? super JsonError> jsonErrorConsumer) {
/* 128 */     return new BeanDeserializerModifier()
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public BeanDeserializerBuilder updateBuilder(DeserializationConfig config, BeanDescription beanDesc, BeanDeserializerBuilder builder)
/*     */         {
/* 137 */           Iterator<SettableBeanProperty> propertiesIterator = builder.getProperties();
/* 138 */           while (propertiesIterator.hasNext()) {
/*     */             
/* 140 */             SettableBeanProperty property = propertiesIterator.next();
/* 141 */             SettableBeanProperty wrappedProperty = new ErrorReportingSettableBeanProperty(property, jsonErrorConsumer);
/*     */ 
/*     */             
/* 144 */             builder.addOrReplaceProperty(wrappedProperty, true);
/*     */           } 
/* 146 */           return builder;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Consumer<JsonError> loggingJsonErrorConsumer() {
/* 159 */     return LOG_JSON_ERROR_CONSUMER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObjectMapper createObjectMapper() {
/* 169 */     ObjectMapper objectMapper = new ObjectMapper();
/* 170 */     configure(objectMapper, loggingJsonErrorConsumer());
/* 171 */     return objectMapper;
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
/*     */   public static ObjectMapper createObjectMapper(Consumer<? super JsonError> jsonErrorConsumer) {
/* 184 */     ObjectMapper objectMapper = new ObjectMapper();
/* 185 */     configure(objectMapper, jsonErrorConsumer);
/* 186 */     return objectMapper;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void configure(ObjectMapper objectMapper, final Consumer<? super JsonError> jsonErrorConsumer) {
/* 203 */     objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
/*     */ 
/*     */     
/* 206 */     objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
/*     */ 
/*     */     
/* 209 */     objectMapper.addHandler(
/* 210 */         createDeserializationProblemHandler(jsonErrorConsumer));
/*     */     
/* 212 */     objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
/*     */     
/* 214 */     objectMapper.setPropertyNamingStrategy(new KeywordPropertyNamingStrategy());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     objectMapper.registerModule((Module)new SimpleModule()
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void setupModule(Module.SetupContext context) {
/* 229 */             super.setupModule(context);
/* 230 */             context.addBeanDeserializerModifier(JacksonUtils
/* 231 */                 .createErrorHandlingBeanDeserializerModifier(jsonErrorConsumer));
/*     */           }
/*     */         });
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
/*     */   public static JsonNode readJson(ByteBuffer jsonData) throws IOException {
/* 247 */     ObjectMapper objectMapper = createObjectMapper();
/*     */     
/* 249 */     try (InputStream jsonInputStream = Buffers.createByteBufferInputStream(jsonData)) {
/*     */       
/* 251 */       return objectMapper.readTree(jsonInputStream);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\JacksonUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */