/*     */ package de.javagl.jgltf.impl.v1;
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
/*     */ public class TechniqueStatesFunctions
/*     */   extends GlTFProperty
/*     */ {
/*     */   private float[] blendColor;
/*     */   private int[] blendEquationSeparate;
/*     */   private int[] blendFuncSeparate;
/*     */   private boolean[] colorMask;
/*     */   private int[] cullFace;
/*     */   private int[] depthFunc;
/*     */   private boolean[] depthMask;
/*     */   private float[] depthRange;
/*     */   private int[] frontFace;
/*     */   private float[] lineWidth;
/*     */   private float[] polygonOffset;
/*     */   private float[] scissor;
/*     */   
/*     */   public void setBlendColor(float[] blendColor) {
/* 159 */     if (blendColor == null) {
/* 160 */       this.blendColor = blendColor;
/*     */       return;
/*     */     } 
/* 163 */     if (blendColor.length < 4) {
/* 164 */       throw new IllegalArgumentException("Number of blendColor elements is < 4");
/*     */     }
/* 166 */     if (blendColor.length > 4) {
/* 167 */       throw new IllegalArgumentException("Number of blendColor elements is > 4");
/*     */     }
/* 169 */     this.blendColor = blendColor;
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
/*     */   public float[] getBlendColor() {
/* 184 */     return this.blendColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] defaultBlendColor() {
/* 195 */     return new float[] { 0.0F, 0.0F, 0.0F, 0.0F };
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
/*     */   public void setBlendEquationSeparate(int[] blendEquationSeparate) {
/* 212 */     if (blendEquationSeparate == null) {
/* 213 */       this.blendEquationSeparate = blendEquationSeparate;
/*     */       return;
/*     */     } 
/* 216 */     if (blendEquationSeparate.length < 2) {
/* 217 */       throw new IllegalArgumentException("Number of blendEquationSeparate elements is < 2");
/*     */     }
/* 219 */     if (blendEquationSeparate.length > 2) {
/* 220 */       throw new IllegalArgumentException("Number of blendEquationSeparate elements is > 2");
/*     */     }
/* 222 */     for (int blendEquationSeparateElement : blendEquationSeparate) {
/* 223 */       if (blendEquationSeparateElement != 32774 && blendEquationSeparateElement != 32778 && blendEquationSeparateElement != 32779) {
/* 224 */         throw new IllegalArgumentException("Invalid value for blendEquationSeparateElement: " + blendEquationSeparateElement + ", valid: [32774, 32778, 32779]");
/*     */       }
/*     */     } 
/* 227 */     this.blendEquationSeparate = blendEquationSeparate;
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
/*     */   public int[] getBlendEquationSeparate() {
/* 242 */     return this.blendEquationSeparate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] defaultBlendEquationSeparate() {
/* 253 */     return new int[] { 32774, 32774 };
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
/*     */   public void setBlendFuncSeparate(int[] blendFuncSeparate) {
/* 271 */     if (blendFuncSeparate == null) {
/* 272 */       this.blendFuncSeparate = blendFuncSeparate;
/*     */       return;
/*     */     } 
/* 275 */     if (blendFuncSeparate.length < 4) {
/* 276 */       throw new IllegalArgumentException("Number of blendFuncSeparate elements is < 4");
/*     */     }
/* 278 */     if (blendFuncSeparate.length > 4) {
/* 279 */       throw new IllegalArgumentException("Number of blendFuncSeparate elements is > 4");
/*     */     }
/* 281 */     for (int blendFuncSeparateElement : blendFuncSeparate) {
/* 282 */       if (blendFuncSeparateElement != 0 && blendFuncSeparateElement != 1 && blendFuncSeparateElement != 768 && blendFuncSeparateElement != 769 && blendFuncSeparateElement != 774 && blendFuncSeparateElement != 775 && blendFuncSeparateElement != 770 && blendFuncSeparateElement != 771 && blendFuncSeparateElement != 772 && blendFuncSeparateElement != 773 && blendFuncSeparateElement != 32769 && blendFuncSeparateElement != 32770 && blendFuncSeparateElement != 32771 && blendFuncSeparateElement != 32772 && blendFuncSeparateElement != 776) {
/* 283 */         throw new IllegalArgumentException("Invalid value for blendFuncSeparateElement: " + blendFuncSeparateElement + ", valid: [0, 1, 768, 769, 774, 775, 770, 771, 772, 773, 32769, 32770, 32771, 32772, 776]");
/*     */       }
/*     */     } 
/* 286 */     this.blendFuncSeparate = blendFuncSeparate;
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
/*     */   public int[] getBlendFuncSeparate() {
/* 302 */     return this.blendFuncSeparate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] defaultBlendFuncSeparate() {
/* 313 */     return new int[] { 1, 0, 1, 0 };
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
/*     */   public void setColorMask(boolean[] colorMask) {
/* 330 */     if (colorMask == null) {
/* 331 */       this.colorMask = colorMask;
/*     */       return;
/*     */     } 
/* 334 */     if (colorMask.length < 4) {
/* 335 */       throw new IllegalArgumentException("Number of colorMask elements is < 4");
/*     */     }
/* 337 */     if (colorMask.length > 4) {
/* 338 */       throw new IllegalArgumentException("Number of colorMask elements is > 4");
/*     */     }
/* 340 */     this.colorMask = colorMask;
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
/*     */   public boolean[] getColorMask() {
/* 355 */     return this.colorMask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean[] defaultColorMask() {
/* 366 */     return new boolean[] { true, true, true, true };
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
/*     */   public void setCullFace(int[] cullFace) {
/* 383 */     if (cullFace == null) {
/* 384 */       this.cullFace = cullFace;
/*     */       return;
/*     */     } 
/* 387 */     if (cullFace.length < 1) {
/* 388 */       throw new IllegalArgumentException("Number of cullFace elements is < 1");
/*     */     }
/* 390 */     if (cullFace.length > 1) {
/* 391 */       throw new IllegalArgumentException("Number of cullFace elements is > 1");
/*     */     }
/* 393 */     for (int cullFaceElement : cullFace) {
/* 394 */       if (cullFaceElement != 1028 && cullFaceElement != 1029 && cullFaceElement != 1032) {
/* 395 */         throw new IllegalArgumentException("Invalid value for cullFaceElement: " + cullFaceElement + ", valid: [1028, 1029, 1032]");
/*     */       }
/*     */     } 
/* 398 */     this.cullFace = cullFace;
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
/*     */   public int[] getCullFace() {
/* 413 */     return this.cullFace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] defaultCullFace() {
/* 424 */     return new int[] { 1029 };
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
/*     */   public void setDepthFunc(int[] depthFunc) {
/* 441 */     if (depthFunc == null) {
/* 442 */       this.depthFunc = depthFunc;
/*     */       return;
/*     */     } 
/* 445 */     if (depthFunc.length < 1) {
/* 446 */       throw new IllegalArgumentException("Number of depthFunc elements is < 1");
/*     */     }
/* 448 */     if (depthFunc.length > 1) {
/* 449 */       throw new IllegalArgumentException("Number of depthFunc elements is > 1");
/*     */     }
/* 451 */     for (int depthFuncElement : depthFunc) {
/* 452 */       if (depthFuncElement != 512 && depthFuncElement != 513 && depthFuncElement != 515 && depthFuncElement != 514 && depthFuncElement != 516 && depthFuncElement != 517 && depthFuncElement != 518 && depthFuncElement != 519) {
/* 453 */         throw new IllegalArgumentException("Invalid value for depthFuncElement: " + depthFuncElement + ", valid: [512, 513, 515, 514, 516, 517, 518, 519]");
/*     */       }
/*     */     } 
/* 456 */     this.depthFunc = depthFunc;
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
/*     */   public int[] getDepthFunc() {
/* 471 */     return this.depthFunc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] defaultDepthFunc() {
/* 482 */     return new int[] { 513 };
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
/*     */   public void setDepthMask(boolean[] depthMask) {
/* 498 */     if (depthMask == null) {
/* 499 */       this.depthMask = depthMask;
/*     */       return;
/*     */     } 
/* 502 */     if (depthMask.length < 1) {
/* 503 */       throw new IllegalArgumentException("Number of depthMask elements is < 1");
/*     */     }
/* 505 */     if (depthMask.length > 1) {
/* 506 */       throw new IllegalArgumentException("Number of depthMask elements is > 1");
/*     */     }
/* 508 */     this.depthMask = depthMask;
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
/*     */   public boolean[] getDepthMask() {
/* 522 */     return this.depthMask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean[] defaultDepthMask() {
/* 533 */     return new boolean[] { true };
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
/*     */   public void setDepthRange(float[] depthRange) {
/* 550 */     if (depthRange == null) {
/* 551 */       this.depthRange = depthRange;
/*     */       return;
/*     */     } 
/* 554 */     if (depthRange.length < 2) {
/* 555 */       throw new IllegalArgumentException("Number of depthRange elements is < 2");
/*     */     }
/* 557 */     if (depthRange.length > 2) {
/* 558 */       throw new IllegalArgumentException("Number of depthRange elements is > 2");
/*     */     }
/* 560 */     this.depthRange = depthRange;
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
/*     */   public float[] getDepthRange() {
/* 575 */     return this.depthRange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] defaultDepthRange() {
/* 586 */     return new float[] { 0.0F, 1.0F };
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
/*     */   public void setFrontFace(int[] frontFace) {
/* 603 */     if (frontFace == null) {
/* 604 */       this.frontFace = frontFace;
/*     */       return;
/*     */     } 
/* 607 */     if (frontFace.length < 1) {
/* 608 */       throw new IllegalArgumentException("Number of frontFace elements is < 1");
/*     */     }
/* 610 */     if (frontFace.length > 1) {
/* 611 */       throw new IllegalArgumentException("Number of frontFace elements is > 1");
/*     */     }
/* 613 */     for (int frontFaceElement : frontFace) {
/* 614 */       if (frontFaceElement != 2304 && frontFaceElement != 2305) {
/* 615 */         throw new IllegalArgumentException("Invalid value for frontFaceElement: " + frontFaceElement + ", valid: [2304, 2305]");
/*     */       }
/*     */     } 
/* 618 */     this.frontFace = frontFace;
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
/*     */   public int[] getFrontFace() {
/* 633 */     return this.frontFace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] defaultFrontFace() {
/* 644 */     return new int[] { 2305 };
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
/*     */   public void setLineWidth(float[] lineWidth) {
/* 661 */     if (lineWidth == null) {
/* 662 */       this.lineWidth = lineWidth;
/*     */       return;
/*     */     } 
/* 665 */     if (lineWidth.length < 1) {
/* 666 */       throw new IllegalArgumentException("Number of lineWidth elements is < 1");
/*     */     }
/* 668 */     if (lineWidth.length > 1) {
/* 669 */       throw new IllegalArgumentException("Number of lineWidth elements is > 1");
/*     */     }
/* 671 */     for (float lineWidthElement : lineWidth) {
/* 672 */       if (lineWidthElement <= 0.0D) {
/* 673 */         throw new IllegalArgumentException("lineWidthElement <= 0.0");
/*     */       }
/*     */     } 
/* 676 */     this.lineWidth = lineWidth;
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
/*     */   public float[] getLineWidth() {
/* 691 */     return this.lineWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] defaultLineWidth() {
/* 702 */     return new float[] { 1.0F };
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
/*     */   public void setPolygonOffset(float[] polygonOffset) {
/* 719 */     if (polygonOffset == null) {
/* 720 */       this.polygonOffset = polygonOffset;
/*     */       return;
/*     */     } 
/* 723 */     if (polygonOffset.length < 2) {
/* 724 */       throw new IllegalArgumentException("Number of polygonOffset elements is < 2");
/*     */     }
/* 726 */     if (polygonOffset.length > 2) {
/* 727 */       throw new IllegalArgumentException("Number of polygonOffset elements is > 2");
/*     */     }
/* 729 */     this.polygonOffset = polygonOffset;
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
/*     */   public float[] getPolygonOffset() {
/* 744 */     return this.polygonOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] defaultPolygonOffset() {
/* 755 */     return new float[] { 0.0F, 0.0F };
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
/*     */   public void setScissor(float[] scissor) {
/* 772 */     if (scissor == null) {
/* 773 */       this.scissor = scissor;
/*     */       return;
/*     */     } 
/* 776 */     if (scissor.length < 4) {
/* 777 */       throw new IllegalArgumentException("Number of scissor elements is < 4");
/*     */     }
/* 779 */     if (scissor.length > 4) {
/* 780 */       throw new IllegalArgumentException("Number of scissor elements is > 4");
/*     */     }
/* 782 */     this.scissor = scissor;
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
/*     */   public float[] getScissor() {
/* 797 */     return this.scissor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] defaultScissor() {
/* 808 */     return new float[] { 0.0F, 0.0F, 0.0F, 0.0F };
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\TechniqueStatesFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */