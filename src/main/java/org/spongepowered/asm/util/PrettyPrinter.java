/*      */ package org.spongepowered.asm.util;
/*      */ 
/*      */ import com.google.common.base.Strings;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PrettyPrinter
/*      */ {
/*      */   private static final int MIN_WIDTH = 40;
/*      */   
/*      */   public static interface IPrettyPrintable
/*      */   {
/*      */     void print(PrettyPrinter param1PrettyPrinter);
/*      */   }
/*      */   
/*      */   static interface IVariableWidthEntry
/*      */   {
/*      */     int getWidth();
/*      */   }
/*      */   
/*      */   static interface ISpecialEntry {}
/*      */   
/*      */   class KeyValue
/*      */     implements IVariableWidthEntry
/*      */   {
/*      */     private final String key;
/*      */     private final Object value;
/*      */     
/*      */     public KeyValue(String key, Object value) {
/*   86 */       this.key = key;
/*   87 */       this.value = value;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*   92 */       return String.format(PrettyPrinter.this.kvFormat, new Object[] { this.key, this.value });
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWidth() {
/*   97 */       return toString().length();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class HorizontalRule
/*      */     implements ISpecialEntry
/*      */   {
/*      */     private final char[] hrChars;
/*      */ 
/*      */     
/*      */     public HorizontalRule(char... hrChars) {
/*  110 */       this.hrChars = hrChars;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  115 */       return Strings.repeat(new String(this.hrChars), PrettyPrinter.this.width + 2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class CentredText
/*      */   {
/*      */     private final Object centred;
/*      */ 
/*      */ 
/*      */     
/*      */     public CentredText(Object centred) {
/*  128 */       this.centred = centred;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  133 */       String text = this.centred.toString();
/*  134 */       return String.format("%" + ((PrettyPrinter.this.width - text.length()) / 2 + text.length()) + "s", new Object[] { text });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Alignment
/*      */   {
/*  143 */     LEFT,
/*  144 */     RIGHT;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Table
/*      */     implements IVariableWidthEntry
/*      */   {
/*  152 */     final List<PrettyPrinter.Column> columns = new ArrayList<>();
/*      */     
/*  154 */     final List<PrettyPrinter.Row> rows = new ArrayList<>();
/*      */     
/*  156 */     String format = "%s";
/*      */     
/*  158 */     int colSpacing = 2;
/*      */     
/*      */     boolean addHeader = true;
/*      */     
/*      */     void headerAdded() {
/*  163 */       this.addHeader = false;
/*      */     }
/*      */     
/*      */     void setColSpacing(int spacing) {
/*  167 */       this.colSpacing = Math.max(0, spacing);
/*  168 */       updateFormat();
/*      */     }
/*      */     
/*      */     Table grow(int size) {
/*  172 */       while (this.columns.size() < size) {
/*  173 */         this.columns.add(new PrettyPrinter.Column(this));
/*      */       }
/*  175 */       updateFormat();
/*  176 */       return this;
/*      */     }
/*      */     
/*      */     PrettyPrinter.Column add(PrettyPrinter.Column column) {
/*  180 */       this.columns.add(column);
/*  181 */       return column;
/*      */     }
/*      */     
/*      */     PrettyPrinter.Row add(PrettyPrinter.Row row) {
/*  185 */       this.rows.add(row);
/*  186 */       return row;
/*      */     }
/*      */     
/*      */     PrettyPrinter.Column addColumn(String title) {
/*  190 */       return add(new PrettyPrinter.Column(this, title));
/*      */     }
/*      */     
/*      */     PrettyPrinter.Column addColumn(PrettyPrinter.Alignment align, int size, String title) {
/*  194 */       return add(new PrettyPrinter.Column(this, align, size, title));
/*      */     }
/*      */     
/*      */     PrettyPrinter.Row addRow(Object... args) {
/*  198 */       return add(new PrettyPrinter.Row(this, args));
/*      */     }
/*      */     
/*      */     void updateFormat() {
/*  202 */       String spacing = Strings.repeat(" ", this.colSpacing);
/*  203 */       StringBuilder format = new StringBuilder();
/*  204 */       boolean addSpacing = false;
/*  205 */       for (PrettyPrinter.Column column : this.columns) {
/*  206 */         if (addSpacing) {
/*  207 */           format.append(spacing);
/*      */         }
/*  209 */         addSpacing = true;
/*  210 */         format.append(column.getFormat());
/*      */       } 
/*  212 */       this.format = format.toString();
/*      */     }
/*      */     
/*      */     String getFormat() {
/*  216 */       return this.format;
/*      */     }
/*      */     
/*      */     Object[] getTitles() {
/*  220 */       List<Object> titles = new ArrayList();
/*  221 */       for (PrettyPrinter.Column column : this.columns) {
/*  222 */         titles.add(column.getTitle());
/*      */       }
/*  224 */       return titles.toArray();
/*      */     }
/*      */     
/*      */     public String toString() {
/*      */       int i;
/*  229 */       boolean nonEmpty = false;
/*  230 */       String[] titles = new String[this.columns.size()];
/*  231 */       for (int col = 0; col < this.columns.size(); col++) {
/*  232 */         titles[col] = ((PrettyPrinter.Column)this.columns.get(col)).toString();
/*  233 */         i = nonEmpty | (!titles[col].isEmpty() ? 1 : 0);
/*      */       } 
/*  235 */       return (i != 0) ? String.format(this.format, (Object[])titles) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWidth() {
/*  240 */       String str = toString();
/*  241 */       return (str != null) ? str.length() : 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class Column
/*      */   {
/*      */     private final PrettyPrinter.Table table;
/*      */ 
/*      */     
/*  253 */     private PrettyPrinter.Alignment align = PrettyPrinter.Alignment.LEFT;
/*      */     
/*  255 */     private int minWidth = 1;
/*      */     
/*  257 */     private int maxWidth = Integer.MAX_VALUE;
/*      */     
/*  259 */     private int size = 0;
/*      */     
/*  261 */     private String title = "";
/*      */     
/*  263 */     private String format = "%s";
/*      */     
/*      */     Column(PrettyPrinter.Table table) {
/*  266 */       this.table = table;
/*      */     }
/*      */     
/*      */     Column(PrettyPrinter.Table table, String title) {
/*  270 */       this(table);
/*  271 */       this.title = title;
/*  272 */       this.minWidth = title.length();
/*  273 */       updateFormat();
/*      */     }
/*      */     
/*      */     Column(PrettyPrinter.Table table, PrettyPrinter.Alignment align, int size, String title) {
/*  277 */       this(table, title);
/*  278 */       this.align = align;
/*  279 */       this.size = size;
/*      */     }
/*      */     
/*      */     void setAlignment(PrettyPrinter.Alignment align) {
/*  283 */       this.align = align;
/*  284 */       updateFormat();
/*      */     }
/*      */     
/*      */     void setWidth(int width) {
/*  288 */       if (width > this.size) {
/*  289 */         this.size = width;
/*  290 */         updateFormat();
/*      */       } 
/*      */     }
/*      */     
/*      */     void setMinWidth(int width) {
/*  295 */       if (width > this.minWidth) {
/*  296 */         this.minWidth = width;
/*  297 */         updateFormat();
/*      */       } 
/*      */     }
/*      */     
/*      */     void setMaxWidth(int width) {
/*  302 */       this.size = Math.min(this.size, this.maxWidth);
/*  303 */       this.maxWidth = Math.max(1, width);
/*  304 */       updateFormat();
/*      */     }
/*      */     
/*      */     void setTitle(String title) {
/*  308 */       this.title = title;
/*  309 */       setWidth(title.length());
/*      */     }
/*      */     
/*      */     private void updateFormat() {
/*  313 */       int width = Math.min(this.maxWidth, (this.size == 0) ? this.minWidth : this.size);
/*  314 */       this.format = "%" + ((this.align == PrettyPrinter.Alignment.RIGHT) ? "" : "-") + width + "s";
/*  315 */       this.table.updateFormat();
/*      */     }
/*      */     
/*      */     int getMaxWidth() {
/*  319 */       return this.maxWidth;
/*      */     }
/*      */     
/*      */     String getTitle() {
/*  323 */       return this.title;
/*      */     }
/*      */     
/*      */     String getFormat() {
/*  327 */       return this.format;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  332 */       if (this.title.length() > this.maxWidth) {
/*  333 */         return this.title.substring(0, this.maxWidth);
/*      */       }
/*      */       
/*  336 */       return this.title;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Row
/*      */     implements IVariableWidthEntry
/*      */   {
/*      */     final PrettyPrinter.Table table;
/*      */     
/*      */     final String[] args;
/*      */ 
/*      */     
/*      */     public Row(PrettyPrinter.Table table, Object... args) {
/*  351 */       this.table = table.grow(args.length);
/*  352 */       this.args = new String[args.length];
/*  353 */       for (int i = 0; i < args.length; i++) {
/*  354 */         this.args[i] = args[i].toString();
/*  355 */         ((PrettyPrinter.Column)this.table.columns.get(i)).setMinWidth(this.args[i].length());
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  361 */       Object[] args = new Object[this.table.columns.size()];
/*  362 */       for (int col = 0; col < args.length; col++) {
/*  363 */         PrettyPrinter.Column column = this.table.columns.get(col);
/*  364 */         if (col >= this.args.length) {
/*  365 */           args[col] = "";
/*      */         } else {
/*  367 */           args[col] = (this.args[col].length() > column.getMaxWidth()) ? this.args[col].substring(0, column.getMaxWidth()) : this.args[col];
/*      */         } 
/*      */       } 
/*      */       
/*  371 */       return String.format(this.table.format, args);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWidth() {
/*  376 */       return toString().length();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  389 */   private final HorizontalRule horizontalRule = new HorizontalRule(new char[] { '*' });
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  394 */   private final List<Object> lines = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Table table;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean recalcWidth = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  410 */   protected int width = 100;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  415 */   protected int wrapWidth = 80;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  420 */   protected int kvKeyWidth = 10;
/*      */   
/*  422 */   protected String kvFormat = makeKvFormat(this.kvKeyWidth);
/*      */   
/*      */   public PrettyPrinter() {
/*  425 */     this(100);
/*      */   }
/*      */   
/*      */   public PrettyPrinter(int width) {
/*  429 */     this.width = Math.max(40, width);
/*  430 */     this.wrapWidth = this.width - 20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter wrapTo(int wrapWidth) {
/*  440 */     this.wrapWidth = wrapWidth;
/*  441 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int wrapTo() {
/*  450 */     return this.wrapWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter table() {
/*  459 */     this.table = new Table();
/*  460 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter table(String... titles) {
/*  470 */     this.table = new Table();
/*  471 */     for (String title : titles) {
/*  472 */       this.table.addColumn(title);
/*      */     }
/*  474 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter table(Object... format) {
/*  499 */     this.table = new Table();
/*  500 */     Column column = null;
/*  501 */     for (Object entry : format) {
/*  502 */       if (entry instanceof String) {
/*  503 */         column = this.table.addColumn((String)entry);
/*  504 */       } else if (entry instanceof Integer && column != null) {
/*  505 */         int width = ((Integer)entry).intValue();
/*  506 */         if (width > 0) {
/*  507 */           column.setWidth(width);
/*  508 */         } else if (width < 0) {
/*  509 */           column.setMaxWidth(-width);
/*      */         } 
/*  511 */       } else if (entry instanceof Alignment && column != null) {
/*  512 */         column.setAlignment((Alignment)entry);
/*  513 */       } else if (entry != null) {
/*  514 */         column = this.table.addColumn(entry.toString());
/*      */       } 
/*      */     } 
/*  517 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter spacing(int spacing) {
/*  527 */     if (this.table == null) {
/*  528 */       this.table = new Table();
/*      */     }
/*  530 */     this.table.setColSpacing(spacing);
/*  531 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter th() {
/*  541 */     return th(false);
/*      */   }
/*      */   
/*      */   private PrettyPrinter th(boolean onlyIfNeeded) {
/*  545 */     if (this.table == null) {
/*  546 */       this.table = new Table();
/*      */     }
/*  548 */     if (!onlyIfNeeded || this.table.addHeader) {
/*  549 */       this.table.headerAdded();
/*  550 */       addLine(this.table);
/*      */     } 
/*  552 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter tr(Object... args) {
/*  564 */     th(true);
/*  565 */     addLine(this.table.addRow(args));
/*  566 */     this.recalcWidth = true;
/*  567 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add() {
/*  576 */     addLine("");
/*  577 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(String string) {
/*  587 */     addLine(string);
/*  588 */     this.width = Math.max(this.width, string.length());
/*  589 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(String format, Object... args) {
/*  601 */     String line = String.format(format, args);
/*  602 */     addLine(line);
/*  603 */     this.width = Math.max(this.width, line.length());
/*  604 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Object[] array) {
/*  614 */     return add(array, "%s");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Object[] array, String format) {
/*  625 */     for (Object element : array) {
/*  626 */       add(format, new Object[] { element });
/*      */     } 
/*      */     
/*  629 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter addIndexed(Object[] array) {
/*  639 */     int indexWidth = String.valueOf(array.length - 1).length();
/*  640 */     String format = "[%" + indexWidth + "d] %s";
/*  641 */     for (int index = 0; index < array.length; index++) {
/*  642 */       add(format, new Object[] { Integer.valueOf(index), array[index] });
/*      */     } 
/*      */     
/*  645 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter addWithIndices(Collection<?> c) {
/*  655 */     return addIndexed(c.toArray());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(IPrettyPrintable printable) {
/*  666 */     if (printable != null) {
/*  667 */       printable.print(this);
/*      */     }
/*  669 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Throwable th) {
/*  680 */     return add(th, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Throwable th, int indent) {
/*  692 */     while (th != null) {
/*  693 */       addWrapped("    %s: %s", new Object[] { th.getClass().getName(), th.getMessage() });
/*  694 */       add(th.getStackTrace(), indent);
/*  695 */       th = th.getCause();
/*      */     } 
/*  697 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(StackTraceElement[] stackTrace, int indent) {
/*  709 */     String margin = Strings.repeat(" ", indent);
/*  710 */     for (StackTraceElement st : stackTrace) {
/*  711 */       add("%s%s", new Object[] { margin, st });
/*      */     } 
/*  713 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Object object) {
/*  723 */     return add(object, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Object object, int indent) {
/*  734 */     String margin = Strings.repeat(" ", indent);
/*  735 */     return append(object, indent, margin);
/*      */   }
/*      */   
/*      */   private PrettyPrinter append(Object object, int indent, String margin) {
/*  739 */     if (object instanceof String)
/*  740 */       return add("%s%s", new Object[] { margin, object }); 
/*  741 */     if (object instanceof Iterable) {
/*  742 */       for (Object entry : object) {
/*  743 */         append(entry, indent, margin);
/*      */       }
/*  745 */       return this;
/*  746 */     }  if (object instanceof Map) {
/*  747 */       kvWidth(indent);
/*  748 */       return add((Map<?, ?>)object);
/*  749 */     }  if (object instanceof IPrettyPrintable)
/*  750 */       return add((IPrettyPrintable)object); 
/*  751 */     if (object instanceof Throwable)
/*  752 */       return add((Throwable)object, indent); 
/*  753 */     if (object.getClass().isArray()) {
/*  754 */       return add((Object[])object, indent + "%s");
/*      */     }
/*  756 */     return add("%s%s", new Object[] { margin, object });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter addWrapped(String format, Object... args) {
/*  769 */     return addWrapped(this.wrapWidth, format, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter addWrapped(int width, String format, Object... args) {
/*  783 */     String indent = "";
/*  784 */     String line = String.format(format, args).replace("\t", "    ");
/*  785 */     Matcher indentMatcher = Pattern.compile("^(\\s+)[^\\s]").matcher(line);
/*  786 */     if (indentMatcher.find()) {
/*  787 */       indent = indentMatcher.group(1);
/*      */     }
/*      */     
/*      */     try {
/*  791 */       for (String wrappedLine : getWrapped(width, line, indent)) {
/*  792 */         add(wrappedLine);
/*      */       }
/*  794 */     } catch (Exception ex) {
/*  795 */       add(line);
/*      */     } 
/*  797 */     return this;
/*      */   }
/*      */   
/*      */   private List<String> getWrapped(int width, String line, String indent) {
/*  801 */     List<String> lines = new ArrayList<>();
/*      */     
/*  803 */     while (line.length() > width) {
/*  804 */       int wrapPoint = lastBreakIndex(line, width);
/*  805 */       if (wrapPoint < 10) {
/*  806 */         wrapPoint = width;
/*      */       }
/*  808 */       String head = line.substring(0, wrapPoint);
/*  809 */       lines.add(head);
/*  810 */       line = indent + line.substring(wrapPoint + 1);
/*      */     } 
/*      */     
/*  813 */     if (line.length() > 0) {
/*  814 */       lines.add(line);
/*      */     }
/*      */     
/*  817 */     return lines;
/*      */   }
/*      */   
/*      */   private static int lastBreakIndex(String line, int width) {
/*  821 */     int lineBreakPos = line.lastIndexOf('\n', width);
/*  822 */     return (lineBreakPos > -1) ? lineBreakPos : Math.max(line.lastIndexOf(' ', width), line.lastIndexOf('\t', width));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter kv(String key, String format, Object... args) {
/*  834 */     return kv(key, String.format(format, args));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter kv(String key, Object value) {
/*  845 */     addLine(new KeyValue(key, value));
/*  846 */     return kvWidth(key.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter kvWidth(int width) {
/*  856 */     if (width > this.kvKeyWidth) {
/*  857 */       this.kvKeyWidth = width;
/*  858 */       this.kvFormat = makeKvFormat(width);
/*      */     } 
/*  860 */     this.recalcWidth = true;
/*  861 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Map<?, ?> map) {
/*  871 */     for (Map.Entry<?, ?> entry : map.entrySet()) {
/*  872 */       String key = (entry.getKey() == null) ? "null" : entry.getKey().toString();
/*  873 */       kv(key, entry.getValue());
/*      */     } 
/*  875 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter hr() {
/*  884 */     return hr('*');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter hr(char ruleChar) {
/*  895 */     addLine(new HorizontalRule(new char[] { ruleChar }));
/*  896 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter centre() {
/*  905 */     if (!this.lines.isEmpty()) {
/*  906 */       Object lastLine = this.lines.get(this.lines.size() - 1);
/*  907 */       if (lastLine instanceof String) {
/*  908 */         addLine(new CentredText(this.lines.remove(this.lines.size() - 1)));
/*      */       }
/*      */     } 
/*  911 */     return this;
/*      */   }
/*      */   
/*      */   private void addLine(Object line) {
/*  915 */     if (line == null) {
/*      */       return;
/*      */     }
/*  918 */     this.lines.add(line);
/*  919 */     this.recalcWidth |= line instanceof IVariableWidthEntry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace() {
/*  929 */     return trace(getDefaultLoggerName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(Level level) {
/*  940 */     return trace(getDefaultLoggerName(), level);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(String logger) {
/*  951 */     return trace(System.err, LogManager.getLogger(logger));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(String logger, Level level) {
/*  963 */     return trace(System.err, LogManager.getLogger(logger), level);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(Logger logger) {
/*  974 */     return trace(System.err, logger);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(Logger logger, Level level) {
/*  986 */     return trace(System.err, logger, level);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(PrintStream stream) {
/*  997 */     return trace(stream, getDefaultLoggerName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(PrintStream stream, Level level) {
/* 1009 */     return trace(stream, getDefaultLoggerName(), level);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(PrintStream stream, String logger) {
/* 1021 */     return trace(stream, LogManager.getLogger(logger));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(PrintStream stream, String logger, Level level) {
/* 1034 */     return trace(stream, LogManager.getLogger(logger), level);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(PrintStream stream, Logger logger) {
/* 1046 */     return trace(stream, logger, Level.DEBUG);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(PrintStream stream, Logger logger, Level level) {
/* 1059 */     log(logger, level);
/* 1060 */     print(stream);
/* 1061 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter print() {
/* 1070 */     return print(System.err);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter print(PrintStream stream) {
/* 1080 */     updateWidth();
/* 1081 */     printSpecial(stream, this.horizontalRule);
/* 1082 */     for (Object line : this.lines) {
/* 1083 */       if (line instanceof ISpecialEntry) {
/* 1084 */         printSpecial(stream, (ISpecialEntry)line); continue;
/*      */       } 
/* 1086 */       printString(stream, line.toString());
/*      */     } 
/*      */     
/* 1089 */     printSpecial(stream, this.horizontalRule);
/* 1090 */     return this;
/*      */   }
/*      */   
/*      */   private void printSpecial(PrintStream stream, ISpecialEntry line) {
/* 1094 */     stream.printf("/*%s*/\n", new Object[] { line.toString() });
/*      */   }
/*      */   
/*      */   private void printString(PrintStream stream, String string) {
/* 1098 */     if (string != null) {
/* 1099 */       stream.printf("/* %-" + this.width + "s */\n", new Object[] { string });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter log(Logger logger) {
/* 1110 */     return log(logger, Level.INFO);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter log(Level level) {
/* 1120 */     return log(LogManager.getLogger(getDefaultLoggerName()), level);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter log(Logger logger, Level level) {
/* 1131 */     updateWidth();
/* 1132 */     logSpecial(logger, level, this.horizontalRule);
/* 1133 */     for (Object line : this.lines) {
/* 1134 */       if (line instanceof ISpecialEntry) {
/* 1135 */         logSpecial(logger, level, (ISpecialEntry)line); continue;
/*      */       } 
/* 1137 */       logString(logger, level, line.toString());
/*      */     } 
/*      */     
/* 1140 */     logSpecial(logger, level, this.horizontalRule);
/* 1141 */     return this;
/*      */   }
/*      */   
/*      */   private void logSpecial(Logger logger, Level level, ISpecialEntry line) {
/* 1145 */     logger.log(level, "/*{}*/", new Object[] { line.toString() });
/*      */   }
/*      */   
/*      */   private void logString(Logger logger, Level level, String line) {
/* 1149 */     if (line != null) {
/* 1150 */       logger.log(level, String.format("/* %-" + this.width + "s */", new Object[] { line }));
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateWidth() {
/* 1155 */     if (this.recalcWidth) {
/* 1156 */       this.recalcWidth = false;
/* 1157 */       for (Object line : this.lines) {
/* 1158 */         if (line instanceof IVariableWidthEntry) {
/* 1159 */           this.width = Math.min(4096, Math.max(this.width, ((IVariableWidthEntry)line).getWidth()));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String makeKvFormat(int keyWidth) {
/* 1166 */     return String.format("%%%ds : %%s", new Object[] { Integer.valueOf(keyWidth) });
/*      */   }
/*      */   
/*      */   private static String getDefaultLoggerName() {
/* 1170 */     String name = (new Throwable()).getStackTrace()[2].getClassName();
/* 1171 */     int pos = name.lastIndexOf('.');
/* 1172 */     return (pos == -1) ? name : name.substring(pos + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void dumpStack() {
/* 1180 */     (new PrettyPrinter()).add(new Exception("Stack trace")).print(System.err);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void print(Throwable th) {
/* 1189 */     (new PrettyPrinter()).add(th).print(System.err);
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\PrettyPrinter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */