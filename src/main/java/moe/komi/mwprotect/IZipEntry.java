package moe.komi.mwprotect;

import java.io.IOException;
import java.io.InputStream;

public interface IZipEntry {
  String getFileName();
  
  InputStream getInputStream() throws IOException;
  
  long getHandle();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\moe\komi\mwprotect\IZipEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */