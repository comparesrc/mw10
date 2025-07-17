/*    */ package mchhui.hegltf;
/*    */ 
/*    */ import de.javagl.jgltf.model.NodeModel;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import org.joml.Quaternionf;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DataNode
/*    */ {
/*    */   private static final String PARENT = "###SCENE###";
/*    */   public NodeModel unsafeNode;
/*    */   public String name;
/* 16 */   public String parent = "###SCENE###";
/* 17 */   public Vector3f pos = new Vector3f(0.0F, 0.0F, 0.0F);
/* 18 */   public Quaternionf rot = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);
/* 19 */   public Vector3f size = new Vector3f(1.0F, 1.0F, 1.0F);
/*    */   
/* 21 */   public ArrayList<String> childlist = new ArrayList<>();
/*    */ 
/*    */   
/* 24 */   public HashMap<String, DataMesh> meshes = new HashMap<>();
/*    */   
/*    */   public boolean isRootNode() {
/* 27 */     return (this.parent == "###SCENE###");
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\hegltf\DataNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */