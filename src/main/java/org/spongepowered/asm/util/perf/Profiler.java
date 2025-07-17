/*     */ package org.spongepowered.asm.util.perf;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import java.lang.reflect.Method;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Deque;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.TreeMap;
/*     */ import org.spongepowered.asm.service.MixinService;
/*     */ import org.spongepowered.asm.util.PrettyPrinter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Profiler
/*     */ {
/*     */   private static final String METRONOME_AGENT_CLASS = "org.spongepowered.metronome.Agent";
/*     */   public static final int ROOT = 1;
/*     */   public static final int FINE = 2;
/*     */   
/*     */   public class Section
/*     */   {
/*     */     static final String SEPARATOR_ROOT = " -> ";
/*     */     static final String SEPARATOR_CHILD = ".";
/*     */     private final String name;
/*     */     private boolean root;
/*     */     private boolean fine;
/*     */     protected boolean invalidated;
/*     */     private String info;
/*     */     
/*     */     Section(String name) {
/*  94 */       this.name = name;
/*  95 */       this.info = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Section getDelegate() {
/* 102 */       return this;
/*     */     }
/*     */     
/*     */     Section invalidate() {
/* 106 */       this.invalidated = true;
/* 107 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Section setRoot(boolean root) {
/* 116 */       this.root = root;
/* 117 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isRoot() {
/* 124 */       return this.root;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Section setFine(boolean fine) {
/* 133 */       this.fine = fine;
/* 134 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isFine() {
/* 141 */       return this.fine;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/* 148 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getBaseName() {
/* 156 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setInfo(String info) {
/* 165 */       this.info = info;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getInfo() {
/* 172 */       return this.info;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Section start() {
/* 181 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Section stop() {
/* 190 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Section end() {
/* 199 */       if (!this.invalidated) {
/* 200 */         Profiler.this.end(this);
/*     */       }
/* 202 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Section next(String name) {
/* 212 */       end();
/* 213 */       return Profiler.this.begin(name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void mark() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long getTime() {
/* 229 */       return 0L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long getTotalTime() {
/* 236 */       return 0L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getSeconds() {
/* 243 */       return 0.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getTotalSeconds() {
/* 250 */       return 0.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long[] getTimes() {
/* 258 */       return new long[1];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getCount() {
/* 265 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getTotalCount() {
/* 272 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getAverageTime() {
/* 280 */       return 0.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getTotalAverageTime() {
/* 288 */       return 0.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final String toString() {
/* 296 */       return this.name;
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
/*     */   class LiveSection
/*     */     extends Section
/*     */   {
/* 311 */     private int cursor = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     private long[] times = new long[0];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 321 */     private long start = 0L;
/*     */ 
/*     */     
/*     */     private long time;
/*     */     
/*     */     private long markedTime;
/*     */     
/*     */     private int count;
/*     */     
/*     */     private int markedCount;
/*     */ 
/*     */     
/*     */     LiveSection(String name, int cursor) {
/* 334 */       super(name);
/* 335 */       this.cursor = cursor;
/*     */     }
/*     */ 
/*     */     
/*     */     Profiler.Section start() {
/* 340 */       this.start = System.currentTimeMillis();
/* 341 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Profiler.Section stop() {
/* 346 */       if (this.start > 0L) {
/* 347 */         this.time += System.currentTimeMillis() - this.start;
/*     */       }
/* 349 */       this.start = 0L;
/* 350 */       this.count++;
/* 351 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Profiler.Section end() {
/* 356 */       stop();
/* 357 */       if (!this.invalidated) {
/* 358 */         Profiler.this.end(this);
/*     */       }
/* 360 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     void mark() {
/* 365 */       if (this.cursor >= this.times.length) {
/* 366 */         this.times = Arrays.copyOf(this.times, this.cursor + 4);
/*     */       }
/* 368 */       this.times[this.cursor] = this.time;
/* 369 */       this.markedTime += this.time;
/* 370 */       this.markedCount += this.count;
/* 371 */       this.time = 0L;
/* 372 */       this.count = 0;
/* 373 */       this.cursor++;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getTime() {
/* 378 */       return this.time;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getTotalTime() {
/* 383 */       return this.time + this.markedTime;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getSeconds() {
/* 388 */       return this.time * 0.001D;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getTotalSeconds() {
/* 393 */       return (this.time + this.markedTime) * 0.001D;
/*     */     }
/*     */ 
/*     */     
/*     */     public long[] getTimes() {
/* 398 */       long[] times = new long[this.cursor + 1];
/* 399 */       System.arraycopy(this.times, 0, times, 0, Math.min(this.times.length, this.cursor));
/* 400 */       times[this.cursor] = this.time;
/* 401 */       return times;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCount() {
/* 406 */       return this.count;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTotalCount() {
/* 411 */       return this.count + this.markedCount;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getAverageTime() {
/* 416 */       return (this.count > 0) ? (this.time / this.count) : 0.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getTotalAverageTime() {
/* 421 */       return (this.count > 0) ? ((this.time + this.markedTime) / (this.count + this.markedCount)) : 0.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class SubSection
/*     */     extends LiveSection
/*     */   {
/*     */     private final String baseName;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Profiler.Section root;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SubSection(String name, int cursor, String baseName, Profiler.Section root) {
/* 444 */       super(name, cursor);
/* 445 */       this.baseName = baseName;
/* 446 */       this.root = root;
/*     */     }
/*     */ 
/*     */     
/*     */     Profiler.Section invalidate() {
/* 451 */       this.root.invalidate();
/* 452 */       return super.invalidate();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getBaseName() {
/* 457 */       return this.baseName;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setInfo(String info) {
/* 462 */       this.root.setInfo(info);
/* 463 */       super.setInfo(info);
/*     */     }
/*     */ 
/*     */     
/*     */     Profiler.Section getDelegate() {
/* 468 */       return this.root;
/*     */     }
/*     */ 
/*     */     
/*     */     Profiler.Section start() {
/* 473 */       this.root.start();
/* 474 */       return super.start();
/*     */     }
/*     */ 
/*     */     
/*     */     public Profiler.Section end() {
/* 479 */       this.root.stop();
/* 480 */       return super.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public Profiler.Section next(String name) {
/* 485 */       stop();
/* 486 */       return this.root.next(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 494 */   private final Map<String, Section> sections = new TreeMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 499 */   private final List<String> phases = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 504 */   private final Deque<Section> stack = new LinkedList<>();
/*     */ 
/*     */   
/*     */   private boolean active;
/*     */ 
/*     */ 
/*     */   
/*     */   public Profiler() {
/* 512 */     this.phases.add("Initial");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActive(boolean active) {
/* 522 */     if ((!this.active && active) || !active) {
/* 523 */       reset();
/*     */     }
/* 525 */     this.active = active;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 532 */     for (Section section : this.sections.values()) {
/* 533 */       section.invalidate();
/*     */     }
/*     */     
/* 536 */     this.sections.clear();
/* 537 */     this.phases.clear();
/* 538 */     this.phases.add("Initial");
/* 539 */     this.stack.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Section get(String name) {
/* 549 */     Section section = this.sections.get(name);
/* 550 */     if (section == null) {
/* 551 */       section = this.active ? new LiveSection(name, this.phases.size() - 1) : new Section(name);
/* 552 */       this.sections.put(name, section);
/*     */     } 
/*     */     
/* 555 */     return section;
/*     */   }
/*     */   
/*     */   private Section getSubSection(String name, String baseName, Section root) {
/* 559 */     Section section = this.sections.get(name);
/* 560 */     if (section == null) {
/* 561 */       section = new SubSection(name, this.phases.size() - 1, baseName, root);
/* 562 */       this.sections.put(name, section);
/*     */     } 
/*     */     
/* 565 */     return section;
/*     */   }
/*     */   
/*     */   boolean isHead(Section section) {
/* 569 */     return (this.stack.peek() == section);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Section begin(String... path) {
/* 579 */     return begin(0, path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Section begin(int flags, String... path) {
/* 590 */     return begin(flags, Joiner.on('.').join((Object[])path));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Section begin(String name) {
/* 600 */     return begin(0, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Section begin(int flags, String name) {
/* 611 */     boolean root = ((flags & 0x1) != 0);
/* 612 */     boolean fine = ((flags & 0x2) != 0);
/*     */     
/* 614 */     String path = name;
/* 615 */     Section head = this.stack.peek();
/* 616 */     if (head != null) {
/* 617 */       path = head.getName() + (root ? " -> " : ".") + path;
/* 618 */       if (head.isRoot() && !root) {
/* 619 */         int pos = head.getName().lastIndexOf(" -> ");
/* 620 */         name = ((pos > -1) ? head.getName().substring(pos + 4) : head.getName()) + "." + name;
/* 621 */         root = true;
/*     */       } 
/*     */     } 
/*     */     
/* 625 */     Section section = get(root ? name : path);
/* 626 */     if (root && head != null && this.active) {
/* 627 */       section = getSubSection(path, head.getName(), section);
/*     */     }
/*     */     
/* 630 */     section.setFine(fine).setRoot(root);
/* 631 */     this.stack.push(section);
/*     */     
/* 633 */     return section.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void end(Section section) {
/*     */     try {
/* 644 */       for (Section head = this.stack.pop(), next = head; next != section; next = this.stack.pop()) {
/* 645 */         if (next == null && this.active) {
/* 646 */           if (head == null) {
/* 647 */             throw new IllegalStateException("Attempted to pop " + section + " but the stack is empty");
/*     */           }
/* 649 */           throw new IllegalStateException("Attempted to pop " + section + " which was not in the stack, head was " + head);
/*     */         } 
/*     */       } 
/* 652 */     } catch (NoSuchElementException ex) {
/* 653 */       if (this.active) {
/* 654 */         throw new IllegalStateException("Attempted to pop " + section + " but the stack is empty");
/*     */       }
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
/*     */   public void mark(String phase) {
/* 667 */     long currentPhaseTime = 0L;
/* 668 */     for (Section section : this.sections.values()) {
/* 669 */       currentPhaseTime += section.getTime();
/*     */     }
/*     */ 
/*     */     
/* 673 */     if (currentPhaseTime == 0L) {
/* 674 */       int size = this.phases.size();
/* 675 */       this.phases.set(size - 1, phase);
/*     */       
/*     */       return;
/*     */     } 
/* 679 */     this.phases.add(phase);
/* 680 */     for (Section section : this.sections.values()) {
/* 681 */       section.mark();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Section> getSections() {
/* 689 */     return Collections.unmodifiableCollection(this.sections.values());
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
/*     */   public PrettyPrinter printer(boolean includeFine, boolean group) {
/* 701 */     PrettyPrinter printer = new PrettyPrinter();
/*     */ 
/*     */     
/* 704 */     int colCount = this.phases.size() + 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 709 */     int[] columns = { 0, 1, 2, colCount - 2, colCount - 1 };
/*     */     
/* 711 */     Object[] headers = new Object[colCount * 2]; int pos;
/* 712 */     for (int col = 0; col < colCount; pos = ++col * 2) {
/* 713 */       headers[pos + 1] = PrettyPrinter.Alignment.RIGHT;
/* 714 */       if (col == columns[0]) {
/* 715 */         headers[pos] = (group ? "" : "  ") + "Section";
/* 716 */         headers[pos + 1] = PrettyPrinter.Alignment.LEFT;
/* 717 */       } else if (col == columns[1]) {
/* 718 */         headers[pos] = "    TOTAL";
/* 719 */       } else if (col == columns[3]) {
/* 720 */         headers[pos] = "    Count";
/* 721 */       } else if (col == columns[4]) {
/* 722 */         headers[pos] = "Avg. ";
/* 723 */       } else if (col - columns[2] < this.phases.size()) {
/* 724 */         headers[pos] = this.phases.get(col - columns[2]);
/*     */       } else {
/* 726 */         headers[pos] = "";
/*     */       } 
/*     */     } 
/*     */     
/* 730 */     printer.table(headers).th().hr().add();
/*     */     
/* 732 */     for (Section section : this.sections.values()) {
/* 733 */       if ((section.isFine() && !includeFine) || (group && section.getDelegate() != section)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 738 */       printSectionRow(printer, colCount, columns, section, group);
/*     */ 
/*     */       
/* 741 */       if (group) {
/* 742 */         for (Section subSection : this.sections.values()) {
/* 743 */           Section delegate = subSection.getDelegate();
/* 744 */           if ((subSection.isFine() && !includeFine) || delegate != section || delegate == subSection) {
/*     */             continue;
/*     */           }
/*     */           
/* 748 */           printSectionRow(printer, colCount, columns, subSection, group);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 753 */     return printer.add();
/*     */   }
/*     */   
/*     */   private void printSectionRow(PrettyPrinter printer, int colCount, int[] columns, Section section, boolean group) {
/* 757 */     boolean isDelegate = (section.getDelegate() != section);
/* 758 */     Object[] values = new Object[colCount];
/* 759 */     int col = 1;
/* 760 */     if (group) {
/* 761 */       values[0] = isDelegate ? ("  > " + section.getBaseName()) : section.getName();
/*     */     } else {
/* 763 */       values[0] = (isDelegate ? "+ " : "  ") + section.getName();
/*     */     } 
/*     */     
/* 766 */     long[] times = section.getTimes();
/* 767 */     for (long time : times) {
/* 768 */       if (col == columns[1]) {
/* 769 */         values[col++] = section.getTotalTime() + " ms";
/*     */       }
/* 771 */       if (col >= columns[2] && col < values.length) {
/* 772 */         values[col++] = time + " ms";
/*     */       }
/*     */     } 
/*     */     
/* 776 */     values[columns[3]] = Integer.valueOf(section.getTotalCount());
/* 777 */     values[columns[4]] = (new DecimalFormat("   ###0.000 ms")).format(section.getTotalAverageTime());
/*     */     
/* 779 */     for (int i = 0; i < values.length; i++) {
/* 780 */       if (values[i] == null) {
/* 781 */         values[i] = "-";
/*     */       }
/*     */     } 
/*     */     
/* 785 */     printer.tr(values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printSummary() {
/* 793 */     DecimalFormat threedp = new DecimalFormat("(###0.000");
/* 794 */     DecimalFormat onedp = new DecimalFormat("(###0.0");
/* 795 */     PrettyPrinter printer = printer(false, false);
/*     */     
/* 797 */     long prepareTime = get("mixin.prepare").getTotalTime();
/* 798 */     long readTime = get("mixin.read").getTotalTime();
/* 799 */     long applyTime = get("mixin.apply").getTotalTime();
/* 800 */     long writeTime = get("mixin.write").getTotalTime();
/* 801 */     long totalMixinTime = get("mixin").getTotalTime();
/*     */     
/* 803 */     long loadTime = get("class.load").getTotalTime();
/* 804 */     long transformTime = get("class.transform").getTotalTime();
/* 805 */     long exportTime = get("mixin.debug.export").getTotalTime();
/* 806 */     long actualTime = totalMixinTime - loadTime - transformTime - exportTime;
/* 807 */     double timeSliceMixin = actualTime / totalMixinTime * 100.0D;
/* 808 */     double timeSliceLoad = loadTime / totalMixinTime * 100.0D;
/* 809 */     double timeSliceTransform = transformTime / totalMixinTime * 100.0D;
/* 810 */     double timeSliceExport = exportTime / totalMixinTime * 100.0D;
/*     */     
/* 812 */     long worstTransformerTime = 0L;
/* 813 */     Section worstTransformer = null;
/*     */     
/* 815 */     for (Section section : getSections()) {
/* 816 */       long transformerTime = section.getName().startsWith("class.transform.") ? section.getTotalTime() : 0L;
/* 817 */       if (transformerTime > worstTransformerTime) {
/* 818 */         worstTransformerTime = transformerTime;
/* 819 */         worstTransformer = section;
/*     */       } 
/*     */     } 
/*     */     
/* 823 */     printer.hr().add("Summary").hr().add();
/*     */     
/* 825 */     String format = "%9d ms %12s seconds)";
/* 826 */     printer.kv("Total mixin time", format, new Object[] { Long.valueOf(totalMixinTime), threedp.format(totalMixinTime * 0.001D) }).add();
/* 827 */     printer.kv("Preparing mixins", format, new Object[] { Long.valueOf(prepareTime), threedp.format(prepareTime * 0.001D) });
/* 828 */     printer.kv("Reading input", format, new Object[] { Long.valueOf(readTime), threedp.format(readTime * 0.001D) });
/* 829 */     printer.kv("Applying mixins", format, new Object[] { Long.valueOf(applyTime), threedp.format(applyTime * 0.001D) });
/* 830 */     printer.kv("Writing output", format, new Object[] { Long.valueOf(writeTime), threedp.format(writeTime * 0.001D) }).add();
/*     */     
/* 832 */     printer.kv("of which", "");
/* 833 */     printer.kv("Time spent loading from disk", format, new Object[] { Long.valueOf(loadTime), threedp.format(loadTime * 0.001D) });
/* 834 */     printer.kv("Time spent transforming classes", format, new Object[] { Long.valueOf(transformTime), threedp.format(transformTime * 0.001D) }).add();
/*     */     
/* 836 */     if (worstTransformer != null) {
/* 837 */       printer.kv("Worst transformer", worstTransformer.getName());
/* 838 */       printer.kv("Class", worstTransformer.getInfo());
/* 839 */       printer.kv("Time spent", "%s seconds", new Object[] { Double.valueOf(worstTransformer.getTotalSeconds()) });
/* 840 */       printer.kv("called", "%d times", new Object[] { Integer.valueOf(worstTransformer.getTotalCount()) }).add();
/*     */     } 
/*     */     
/* 843 */     printer.kv("   Time allocation:     Processing mixins", "%9d ms %10s%% of total)", new Object[] { Long.valueOf(actualTime), onedp.format(timeSliceMixin) });
/* 844 */     printer.kv("Loading classes", "%9d ms %10s%% of total)", new Object[] { Long.valueOf(loadTime), onedp.format(timeSliceLoad) });
/* 845 */     printer.kv("Running transformers", "%9d ms %10s%% of total)", new Object[] { Long.valueOf(transformTime), onedp.format(timeSliceTransform) });
/* 846 */     if (exportTime > 0L) {
/* 847 */       printer.kv("Exporting classes (debug)", "%9d ms %10s%% of total)", new Object[] { Long.valueOf(exportTime), onedp.format(timeSliceExport) });
/*     */     }
/* 849 */     printer.add();
/*     */     
/*     */     try {
/* 852 */       Class<?> agent = MixinService.getService().getClassProvider().findAgentClass("org.spongepowered.metronome.Agent", false);
/* 853 */       Method mdGetTimes = agent.getDeclaredMethod("getTimes", new Class[0]);
/*     */ 
/*     */       
/* 856 */       Map<String, Long> times = (Map<String, Long>)mdGetTimes.invoke(null, new Object[0]);
/*     */       
/* 858 */       printer.hr().add("Transformer Times").hr().add();
/*     */       
/* 860 */       int longest = 10;
/* 861 */       for (Map.Entry<String, Long> entry : times.entrySet()) {
/* 862 */         longest = Math.max(longest, ((String)entry.getKey()).length());
/*     */       }
/*     */       
/* 865 */       for (Map.Entry<String, Long> entry : times.entrySet()) {
/* 866 */         String name = entry.getKey();
/* 867 */         long mixinTime = 0L;
/* 868 */         for (Section section : getSections()) {
/* 869 */           if (name.equals(section.getInfo())) {
/* 870 */             mixinTime = section.getTotalTime();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 875 */         if (mixinTime > 0L) {
/* 876 */           printer.add("%-" + longest + "s %8s ms %8s ms in mixin)", new Object[] { name, Long.valueOf(((Long)entry.getValue()).longValue() + mixinTime), "(" + mixinTime }); continue;
/*     */         } 
/* 878 */         printer.add("%-" + longest + "s %8s ms", new Object[] { name, entry.getValue() });
/*     */       } 
/*     */ 
/*     */       
/* 882 */       printer.add();
/*     */     }
/* 884 */     catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */     
/* 888 */     printer.print();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\perf\Profiler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */