package de.javagl.jgltf.model.io;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

public interface GltfAsset {
  Object getGltf();
  
  ByteBuffer getBinaryData();
  
  List<GltfReference> getReferences();
  
  ByteBuffer getReferenceData(String paramString);
  
  Map<String, ByteBuffer> getReferenceDatas();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\GltfAsset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */