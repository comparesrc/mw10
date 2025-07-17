package net.lingala.zip4j.crypto;

import net.lingala.zip4j.exception.ZipException;

public interface IDecrypter {
  int decryptData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ZipException;
  
  int decryptData(byte[] paramArrayOfbyte) throws ZipException;
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\crypto\IDecrypter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */