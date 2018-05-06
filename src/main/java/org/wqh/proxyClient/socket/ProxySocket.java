package org.wqh.proxyClient.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.wqh.proxyClient.encryption.HalfMode;
import org.wqh.proxyClient.encryption.Mode;

public  class ProxySocket {
	
	private Socket socket;
	
	private String protocolType;
	private Mode<Integer> halfMode = new HalfMode();
    
    public ProxySocket(){
    	
    }
    public ProxySocket(Socket socket) {
        this.socket = socket; 
          
    }
    public Socket getSocket() {
        return socket;
        
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
	
	public String getProtocolType() {
		return protocolType;
	}
	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}
	
	
	
	public void send(InputStream in)  {

		 OutputStream outputStream= null;
		    try {
				outputStream = getSocket().getOutputStream();
				int s;
				
					while((s=in.read())!=-1){
						//System.out.print(halfMode.encrypt(s)+" ");
						outputStream.write(halfMode.encrypt(s));
					//	
			            		 
					}
				
			
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	
	public int accept() {
	   try {
		  return getSocket().getInputStream().read();
	   } catch (IOException e) {
		  e.printStackTrace();
	   }
	   return -1;  
	}
}
