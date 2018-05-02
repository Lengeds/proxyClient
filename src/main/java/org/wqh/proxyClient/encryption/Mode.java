package org.wqh.proxyClient.encryption;
/*
 * 加密，解密类
 */
public abstract class Mode<D>{
  public int dataBits;
  public abstract D encrypt(D d);
  public abstract D decrypt(D d);

}
