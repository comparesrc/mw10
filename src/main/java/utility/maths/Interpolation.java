/*     */ package com.modularwarfare.utility.maths;
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Interpolation
/*     */   implements IInterpolation
/*     */ {
/*   8 */   LINEAR("linear")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  13 */       return Interpolations.lerp(a, b, x);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/*  19 */       return Interpolations.lerp(a, b, x);
/*     */     }
/*     */   },
/*  22 */   QUAD_IN("quad_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  27 */       return a + (b - a) * x * x;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/*  33 */       return a + (b - a) * x * x;
/*     */     }
/*     */   },
/*  36 */   QUAD_OUT("quad_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  41 */       return a - (b - a) * x * (x - 2.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/*  47 */       return a - (b - a) * x * (x - 2.0D);
/*     */     }
/*     */   },
/*  50 */   QUAD_INOUT("quad_inout")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  55 */       x *= 2.0F;
/*     */       
/*  57 */       if (x < 1.0F) return a + (b - a) / 2.0F * x * x;
/*     */       
/*  59 */       x--;
/*     */       
/*  61 */       return a - (b - a) / 2.0F * (x * (x - 2.0F) - 1.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/*  67 */       x *= 2.0D;
/*     */       
/*  69 */       if (x < 1.0D) return a + (b - a) / 2.0D * x * x;
/*     */       
/*  71 */       x--;
/*     */       
/*  73 */       return a - (b - a) / 2.0D * (x * (x - 2.0D) - 1.0D);
/*     */     }
/*     */   },
/*  76 */   CUBIC_IN("cubic_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  81 */       return a + (b - a) * x * x * x;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/*  87 */       return a + (b - a) * x * x * x;
/*     */     }
/*     */   },
/*  90 */   CUBIC_OUT("cubic_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  95 */       x--;
/*  96 */       return a + (b - a) * (x * x * x + 1.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 102 */       x--;
/* 103 */       return a + (b - a) * (x * x * x + 1.0D);
/*     */     }
/*     */   },
/* 106 */   CUBIC_INOUT("cubic_inout")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 111 */       x *= 2.0F;
/*     */       
/* 113 */       if (x < 1.0F) return a + (b - a) / 2.0F * x * x * x;
/*     */       
/* 115 */       x -= 2.0F;
/*     */       
/* 117 */       return a + (b - a) / 2.0F * (x * x * x + 2.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 123 */       x *= 2.0D;
/*     */       
/* 125 */       if (x < 1.0D) return a + (b - a) / 2.0D * x * x * x;
/*     */       
/* 127 */       x -= 2.0D;
/*     */       
/* 129 */       return a + (b - a) / 2.0D * (x * x * x + 2.0D);
/*     */     }
/*     */   },
/* 132 */   EXP_IN("exp_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 137 */       return a + (b - a) * (float)Math.pow(2.0D, (10.0F * (x - 1.0F)));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 143 */       return a + (b - a) * (float)Math.pow(2.0D, 10.0D * (x - 1.0D));
/*     */     }
/*     */   },
/* 146 */   EXP_OUT("exp_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 151 */       return a + (b - a) * (float)(-Math.pow(2.0D, (-10.0F * x)) + 1.0D);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 157 */       return a + (b - a) * (float)(-Math.pow(2.0D, -10.0D * x) + 1.0D);
/*     */     }
/*     */   },
/* 160 */   EXP_INOUT("exp_inout")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 165 */       if (x == 0.0F) return a; 
/* 166 */       if (x == 1.0F) return b;
/*     */       
/* 168 */       x *= 2.0F;
/*     */       
/* 170 */       if (x < 1.0F) return a + (b - a) / 2.0F * (float)Math.pow(2.0D, (10.0F * (x - 1.0F)));
/*     */       
/* 172 */       x--;
/*     */       
/* 174 */       return a + (b - a) / 2.0F * (float)(-Math.pow(2.0D, (-10.0F * x)) + 2.0D);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 180 */       if (x == 0.0D) return a; 
/* 181 */       if (x == 1.0D) return b;
/*     */       
/* 183 */       x *= 2.0D;
/*     */       
/* 185 */       if (x < 1.0D) return a + (b - a) / 2.0D * (float)Math.pow(2.0D, 10.0D * (x - 1.0D));
/*     */       
/* 187 */       x--;
/*     */       
/* 189 */       return a + (b - a) / 2.0D * (float)(-Math.pow(2.0D, -10.0D * x) + 2.0D);
/*     */     }
/*     */   },
/*     */   
/* 193 */   BACK_IN("back_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 198 */       float c1 = 1.70158F;
/* 199 */       float c3 = 2.70158F;
/*     */       
/* 201 */       return Interpolations.lerp(a, b, 2.70158F * x * x * x - 1.70158F * x * x);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 207 */       double c1 = 1.70158D;
/* 208 */       double c3 = 2.70158D;
/*     */       
/* 210 */       return Interpolations.lerp(a, b, 2.70158D * x * x * x - 1.70158D * x * x);
/*     */     }
/*     */   },
/* 213 */   BACK_OUT("back_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 218 */       float c1 = 1.70158F;
/* 219 */       float c3 = 2.70158F;
/*     */       
/* 221 */       return Interpolations.lerp(a, b, 1.0F + 2.70158F * (float)Math.pow((x - 1.0F), 3.0D) + 1.70158F * (float)Math.pow((x - 1.0F), 2.0D));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 227 */       double c1 = 1.70158D;
/* 228 */       double c3 = 2.70158D;
/*     */       
/* 230 */       return Interpolations.lerp(a, b, 1.0D + 2.70158D * Math.pow(x - 1.0D, 3.0D) + 1.70158D * Math.pow(x - 1.0D, 2.0D));
/*     */     }
/*     */   },
/* 233 */   BACK_INOUT("back_inout")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 238 */       float c1 = 1.70158F;
/* 239 */       float c2 = 2.5949094F;
/*     */ 
/*     */ 
/*     */       
/* 243 */       float factor = (x < 0.5D) ? ((float)Math.pow((2.0F * x), 2.0D) * (7.189819F * x - 2.5949094F) / 2.0F) : (((float)Math.pow((2.0F * x - 2.0F), 2.0D) * (3.5949094F * (x * 2.0F - 2.0F) + 2.5949094F) + 2.0F) / 2.0F);
/*     */       
/* 245 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 251 */       double c1 = 1.70158D;
/* 252 */       double c2 = 2.5949095D;
/*     */ 
/*     */ 
/*     */       
/* 256 */       double factor = (x < 0.5D) ? (Math.pow(2.0D * x, 2.0D) * (7.189819D * x - 2.5949095D) / 2.0D) : ((Math.pow(2.0D * x - 2.0D, 2.0D) * (3.5949095D * (x * 2.0D - 2.0D) + 2.5949095D) + 2.0D) / 2.0D);
/*     */       
/* 258 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 261 */   ELASTIC_IN("elastic_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 266 */       float c4 = 2.0943952F;
/*     */ 
/*     */       
/* 269 */       float factor = (x == 0.0F) ? 0.0F : ((x == 1.0F) ? 1.0F : (-((float)Math.pow(2.0D, (10.0F * x - 10.0F))) * (float)Math.sin(((x * 10.0F) - 10.75D) * 2.094395160675049D)));
/*     */       
/* 271 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 277 */       double c4 = 2.094395160675049D;
/*     */ 
/*     */       
/* 280 */       double factor = (x == 0.0D) ? 0.0D : ((x == 1.0D) ? 1.0D : (-Math.pow(2.0D, 10.0D * x - 10.0D) * Math.sin((x * 10.0D - 10.75D) * 2.094395160675049D)));
/*     */       
/* 282 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 285 */   ELASTIC_OUT("elastic_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 290 */       float c4 = 2.0943952F;
/*     */ 
/*     */       
/* 293 */       float factor = (x == 0.0F) ? 0.0F : ((x == 1.0F) ? 1.0F : ((float)Math.pow(2.0D, (-10.0F * x)) * (float)Math.sin(((x * 10.0F) - 0.75D) * 2.094395160675049D) + 1.0F));
/*     */       
/* 295 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 301 */       double c4 = 2.0943951023931953D;
/*     */ 
/*     */       
/* 304 */       double factor = (x == 0.0D) ? 0.0D : ((x == 1.0D) ? 1.0D : (Math.pow(2.0D, -10.0D * x) * Math.sin((x * 10.0D - 0.75D) * 2.0943951023931953D) + 1.0D));
/*     */       
/* 306 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 309 */   ELASTIC_INOUT("elastic_inout")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 314 */       float c5 = 1.3962635F;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 319 */       float factor = (x == 0.0F) ? 0.0F : ((x == 1.0F) ? 1.0F : ((x < 0.5D) ? (-((float)Math.pow(2.0D, (20.0F * x - 10.0F)) * (float)Math.sin(((20.0F * x) - 11.125D) * 1.3962634801864624D)) / 2.0F) : ((float)Math.pow(2.0D, (-20.0F * x + 10.0F)) * (float)Math.sin(((20.0F * x) - 11.125D) * 1.3962634801864624D) / 2.0F + 1.0F)));
/*     */ 
/*     */       
/* 322 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 328 */       double c5 = 1.3962634015954636D;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 333 */       double factor = (x == 0.0D) ? 0.0D : ((x == 1.0D) ? 1.0D : ((x < 0.5D) ? (-(Math.pow(2.0D, 20.0D * x - 10.0D) * Math.sin((20.0D * x - 11.125D) * 1.3962634015954636D)) / 2.0D) : (Math.pow(2.0D, -20.0D * x + 10.0D) * Math.sin((20.0D * x - 11.125D) * 1.3962634015954636D) / 2.0D + 1.0D)));
/*     */ 
/*     */       
/* 336 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 339 */   BOUNCE_IN("bounce_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 344 */       return Interpolations.lerp(a, b, 1.0F - BOUNCE_OUT.interpolate(0.0F, 1.0F, 1.0F - x));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 350 */       return Interpolations.lerp(a, b, 1.0D - BOUNCE_OUT.interpolate(0.0D, 1.0D, 1.0D - x));
/*     */     }
/*     */   },
/* 353 */   BOUNCE_OUT("bounce_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 358 */       float factor, n1 = 7.5625F;
/* 359 */       float d1 = 2.75F;
/*     */ 
/*     */       
/* 362 */       if (x < 0.36363637F) {
/*     */         
/* 364 */         factor = 7.5625F * x * x;
/*     */       }
/* 366 */       else if (x < 0.72727275F) {
/*     */         
/* 368 */         factor = 7.5625F * (x -= 0.54545456F) * x + 0.75F;
/*     */       }
/* 370 */       else if (x < 0.9090909090909091D) {
/*     */         
/* 372 */         factor = 7.5625F * (x -= 0.8181818F) * x + 0.9375F;
/*     */       }
/*     */       else {
/*     */         
/* 376 */         factor = 7.5625F * (x -= 0.95454544F) * x + 0.984375F;
/*     */       } 
/*     */       
/* 379 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 385 */       double factor, n1 = 7.5625D;
/* 386 */       double d1 = 2.75D;
/*     */ 
/*     */       
/* 389 */       if (x < 0.36363636363636365D) {
/*     */         
/* 391 */         factor = 7.5625D * x * x;
/*     */       }
/*     */       else {
/*     */         
/* 395 */         factor = 7.5625D * (x -= 0.5454545454545454D) * x + 0.75D;
/*     */ 
/*     */ 
/*     */         
/* 399 */         factor = 7.5625D * (x -= 0.8181818181818182D) * x + 0.9375D;
/*     */ 
/*     */ 
/*     */         
/* 403 */         factor = 7.5625D * (x -= 0.9545454545454546D) * x + 0.984375D;
/*     */       } 
/*     */       
/* 406 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 409 */   BOUNCE_INOUT("bounce_inout")
/*     */   {
/*     */ 
/*     */ 
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 416 */       float factor = (x < 0.5D) ? ((1.0F - BOUNCE_OUT.interpolate(0.0F, 1.0F, 1.0F - 2.0F * x)) / 2.0F) : ((1.0F + BOUNCE_OUT.interpolate(0.0F, 1.0F, 2.0F * x - 1.0F)) / 2.0F);
/*     */       
/* 418 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 426 */       double factor = (x < 0.5D) ? ((1.0D - BOUNCE_OUT.interpolate(0.0D, 1.0D, 1.0D - 2.0D * x)) / 2.0D) : ((1.0D + BOUNCE_OUT.interpolate(0.0D, 1.0D, 2.0D * x - 1.0D)) / 2.0D);
/*     */       
/* 428 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 431 */   SINE_IN("sine_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 436 */       float factor = 1.0F - (float)Math.cos(x * Math.PI / 2.0D);
/*     */       
/* 438 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 444 */       double factor = (1.0F - (float)Math.cos(x * Math.PI / 2.0D));
/*     */       
/* 446 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 449 */   SINE_OUT("sine_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 454 */       float factor = (float)Math.sin(x * Math.PI / 2.0D);
/*     */       
/* 456 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 462 */       double factor = Math.sin(x * Math.PI / 2.0D);
/*     */       
/* 464 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 467 */   SINE_INOUT("sine_inout")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 472 */       float factor = (float)(-(Math.cos(Math.PI * x) - 1.0D) / 2.0D);
/*     */       
/* 474 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 480 */       double factor = -(Math.cos(Math.PI * x) - 1.0D) / 2.0D;
/*     */       
/* 482 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 485 */   QUART_IN("quart_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 490 */       float factor = x * x * x * x;
/*     */       
/* 492 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 498 */       double factor = x * x * x * x;
/*     */       
/* 500 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 503 */   QUART_OUT("quart_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 508 */       float factor = 1.0F - (float)Math.pow((1.0F - x), 4.0D);
/*     */       
/* 510 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 516 */       double factor = 1.0D - Math.pow(1.0D - x, 4.0D);
/*     */       
/* 518 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 521 */   QUART_INOUT("quart_inout")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 526 */       float factor = (x < 0.5D) ? (8.0F * x * x * x * x) : (1.0F - (float)Math.pow((-2.0F * x + 2.0F), 4.0D) / 2.0F);
/*     */       
/* 528 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 534 */       double factor = (x < 0.5D) ? (8.0D * x * x * x * x) : (1.0D - Math.pow(-2.0D * x + 2.0D, 4.0D) / 2.0D);
/*     */       
/* 536 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 539 */   QUINT_IN("quint_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 544 */       float factor = x * x * x * x * x;
/*     */       
/* 546 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 552 */       double factor = x * x * x * x * x;
/*     */       
/* 554 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 557 */   QUINT_OUT("quint_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 562 */       float factor = 1.0F - (float)Math.pow((1.0F - x), 5.0D);
/*     */       
/* 564 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 570 */       double factor = 1.0D - Math.pow(1.0D - x, 5.0D);
/*     */       
/* 572 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 575 */   QUINT_INOUT("quint_inout")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 580 */       float factor = (x < 0.5D) ? (16.0F * x * x * x * x * x) : (1.0F - (float)Math.pow((-2.0F * x + 2.0F), 5.0D) / 2.0F);
/*     */       
/* 582 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 588 */       double factor = (x < 0.5D) ? (16.0D * x * x * x * x * x) : (1.0D - Math.pow(-2.0D * x + 2.0D, 5.0D) / 2.0D);
/*     */       
/* 590 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 593 */   CIRCLE_IN("circle_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 598 */       x = MathUtils.clamp(x, 0.0F, 1.0F);
/*     */       
/* 600 */       float factor = 1.0F - (float)Math.sqrt(1.0D - Math.pow(x, 2.0D));
/*     */       
/* 602 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 608 */       x = MathUtils.clamp(x, 0.0D, 1.0D);
/*     */       
/* 610 */       double factor = (1.0F - (float)Math.sqrt(1.0D - Math.pow(x, 2.0D)));
/*     */       
/* 612 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 615 */   CIRCLE_OUT("circle_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 620 */       x = MathUtils.clamp(x, 0.0F, 1.0F);
/*     */       
/* 622 */       float factor = (float)Math.sqrt(1.0D - Math.pow((x - 1.0F), 2.0D));
/*     */       
/* 624 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 630 */       x = MathUtils.clamp(x, 0.0D, 1.0D);
/*     */       
/* 632 */       double factor = Math.sqrt(1.0D - Math.pow(x - 1.0D, 2.0D));
/*     */       
/* 634 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   },
/* 637 */   CIRCLE_INOUT("circle_inout")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/* 642 */       x = MathUtils.clamp(x, 0.0F, 1.0F);
/*     */ 
/*     */ 
/*     */       
/* 646 */       float factor = (x < 0.5D) ? ((float)(1.0D - Math.sqrt(1.0D - Math.pow((2.0F * x), 2.0D))) / 2.0F) : ((float)(Math.sqrt(1.0D - Math.pow((-2.0F * x + 2.0F), 2.0D)) + 1.0D) / 2.0F);
/*     */       
/* 648 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double interpolate(double a, double b, double x) {
/* 654 */       x = MathUtils.clamp(x, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */       
/* 658 */       double factor = (x < 0.5D) ? ((1.0D - Math.sqrt(1.0D - Math.pow(2.0D * x, 2.0D))) / 2.0D) : ((Math.sqrt(1.0D - Math.pow(-2.0D * x + 2.0D, 2.0D)) + 1.0D) / 2.0D);
/*     */       
/* 660 */       return Interpolations.lerp(a, b, factor);
/*     */     }
/*     */   };
/*     */ 
/*     */   
/*     */   public final String key;
/*     */   
/*     */   Interpolation(String key) {
/* 668 */     this.key = key;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfar\\utility\maths\Interpolation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */