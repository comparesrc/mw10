/*     */ package org.spongepowered.asm.util;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.objectweb.asm.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.util.throwables.ConstraintViolationException;
/*     */ import org.spongepowered.asm.util.throwables.InvalidConstraintException;
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
/*     */ public final class ConstraintParser
/*     */ {
/*     */   public static class Constraint
/*     */   {
/*  95 */     public static final Constraint NONE = new Constraint();
/*     */ 
/*     */     
/*  98 */     private static final Pattern pattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)\\((?:(<|<=|>|>=|=)?([0-9]+)(<|(-)([0-9]+)?|>|(\\+)([0-9]+)?)?)?\\)$");
/*     */     
/*     */     private final String expr;
/*     */     
/*     */     private String token;
/*     */     
/*     */     private String[] constraint;
/*     */     
/* 106 */     private int min = Integer.MIN_VALUE;
/*     */     
/* 108 */     private int max = Integer.MAX_VALUE;
/*     */     
/*     */     private Constraint next;
/*     */     
/*     */     Constraint(String expr) {
/* 113 */       this.expr = expr;
/* 114 */       Matcher matcher = pattern.matcher(expr);
/* 115 */       if (!matcher.matches()) {
/* 116 */         throw new InvalidConstraintException("Constraint syntax was invalid parsing: " + this.expr);
/*     */       }
/*     */       
/* 119 */       this.token = matcher.group(1);
/* 120 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 127 */         .constraint = new String[] { matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6), matcher.group(7), matcher.group(8) };
/*     */ 
/*     */       
/* 130 */       parse();
/*     */     }
/*     */     
/*     */     private Constraint() {
/* 134 */       this.expr = null;
/* 135 */       this.token = "*";
/* 136 */       this.constraint = new String[0];
/*     */     }
/*     */     
/*     */     private void parse() {
/* 140 */       if (!has(1)) {
/*     */         return;
/*     */       }
/*     */       
/* 144 */       this.max = this.min = val(1);
/* 145 */       boolean hasModifier = has(0);
/*     */       
/* 147 */       if (has(4)) {
/* 148 */         if (hasModifier) {
/* 149 */           throw new InvalidConstraintException("Unexpected modifier '" + elem(0) + "' in " + this.expr + " parsing range");
/*     */         }
/* 151 */         this.max = val(4);
/* 152 */         if (this.max < this.min)
/* 153 */           throw new InvalidConstraintException("Invalid range specified '" + this.max + "' is less than " + this.min + " in " + this.expr); 
/*     */         return;
/*     */       } 
/* 156 */       if (has(6)) {
/* 157 */         if (hasModifier) {
/* 158 */           throw new InvalidConstraintException("Unexpected modifier '" + elem(0) + "' in " + this.expr + " parsing range");
/*     */         }
/* 160 */         this.max = this.min + val(6);
/*     */         
/*     */         return;
/*     */       } 
/* 164 */       if (hasModifier) {
/* 165 */         if (has(3)) {
/* 166 */           throw new InvalidConstraintException("Unexpected trailing modifier '" + elem(3) + "' in " + this.expr);
/*     */         }
/* 168 */         String leading = elem(0);
/* 169 */         if (">".equals(leading)) {
/* 170 */           this.min++;
/* 171 */           this.max = Integer.MAX_VALUE;
/* 172 */         } else if (">=".equals(leading)) {
/* 173 */           this.max = Integer.MAX_VALUE;
/* 174 */         } else if ("<".equals(leading)) {
/* 175 */           this.max = --this.min;
/* 176 */           this.min = Integer.MIN_VALUE;
/* 177 */         } else if ("<=".equals(leading)) {
/* 178 */           this.max = this.min;
/* 179 */           this.min = Integer.MIN_VALUE;
/*     */         } 
/* 181 */       } else if (has(2)) {
/* 182 */         String trailing = elem(2);
/* 183 */         if ("<".equals(trailing)) {
/* 184 */           this.max = this.min;
/* 185 */           this.min = Integer.MIN_VALUE;
/*     */         } else {
/* 187 */           this.max = Integer.MAX_VALUE;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean has(int index) {
/* 193 */       return (this.constraint[index] != null);
/*     */     }
/*     */     
/*     */     private String elem(int index) {
/* 197 */       return this.constraint[index];
/*     */     }
/*     */     
/*     */     private int val(int index) {
/* 201 */       return (this.constraint[index] != null) ? Integer.parseInt(this.constraint[index]) : 0;
/*     */     }
/*     */     
/*     */     void append(Constraint next) {
/* 205 */       if (this.next != null) {
/* 206 */         this.next.append(next);
/*     */         return;
/*     */       } 
/* 209 */       this.next = next;
/*     */     }
/*     */     
/*     */     public String getToken() {
/* 213 */       return this.token;
/*     */     }
/*     */     
/*     */     public int getMin() {
/* 217 */       return this.min;
/*     */     }
/*     */     
/*     */     public int getMax() {
/* 221 */       return this.max;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void check(ITokenProvider environment) throws ConstraintViolationException {
/* 232 */       if (this != NONE) {
/* 233 */         Integer value = environment.getToken(this.token);
/* 234 */         if (value == null) {
/* 235 */           throw new ConstraintViolationException("The token '" + this.token + "' could not be resolved in " + environment, this);
/*     */         }
/* 237 */         if (value.intValue() < this.min) {
/* 238 */           throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + value + ") which is less than the minimum value " + this.min + " in " + environment, this, value
/* 239 */               .intValue());
/*     */         }
/* 241 */         if (value.intValue() > this.max) {
/* 242 */           throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + value + ") which is greater than the maximum value " + this.max + " in " + environment, this, value
/* 243 */               .intValue());
/*     */         }
/*     */       } 
/* 246 */       if (this.next != null) {
/* 247 */         this.next.check(environment);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getRangeHumanReadable() {
/* 256 */       if (this.min == Integer.MIN_VALUE && this.max == Integer.MAX_VALUE)
/* 257 */         return "ANY VALUE"; 
/* 258 */       if (this.min == Integer.MIN_VALUE)
/* 259 */         return String.format("less than or equal to %d", new Object[] { Integer.valueOf(this.max) }); 
/* 260 */       if (this.max == Integer.MAX_VALUE)
/* 261 */         return String.format("greater than or equal to %d", new Object[] { Integer.valueOf(this.min) }); 
/* 262 */       if (this.min == this.max) {
/* 263 */         return String.format("%d", new Object[] { Integer.valueOf(this.min) });
/*     */       }
/* 265 */       return String.format("between %d and %d", new Object[] { Integer.valueOf(this.min), Integer.valueOf(this.max) });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 270 */       return String.format("Constraint(%s [%d-%d])", new Object[] { this.token, Integer.valueOf(this.min), Integer.valueOf(this.max) });
/*     */     }
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
/*     */   public static Constraint parse(String expr) {
/* 286 */     if (expr == null || expr.length() == 0) {
/* 287 */       return Constraint.NONE;
/*     */     }
/*     */     
/* 290 */     String[] exprs = expr.replaceAll("\\s", "").toUpperCase(Locale.ROOT).split(";");
/* 291 */     Constraint head = null;
/* 292 */     for (String subExpr : exprs) {
/* 293 */       Constraint next = new Constraint(subExpr);
/* 294 */       if (head == null) {
/* 295 */         head = next;
/*     */       } else {
/* 297 */         head.append(next);
/*     */       } 
/*     */     } 
/*     */     
/* 301 */     return (head != null) ? head : Constraint.NONE;
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
/*     */   public static Constraint parse(AnnotationNode annotation) {
/* 314 */     String constraints = Annotations.<String>getValue(annotation, "constraints", "");
/* 315 */     return parse(constraints);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\ConstraintParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */