package net.lingala.zip4j.crypto.PBKDF2;

interface PRF {
  void init(byte[] paramArrayOfbyte);
  
  byte[] doFinal(byte[] paramArrayOfbyte);
  
  int getHLen();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\crypto\PBKDF2\PRF.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */