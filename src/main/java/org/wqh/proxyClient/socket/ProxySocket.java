package org.wqh.proxyClient.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public  class ProxySocket {
	
	private Socket socket;
	private String host; 
	private int port;
	private String protocolType;
   
    
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
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
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
						System.out.print((char)s);
						outputStream.write(s);
			            		 
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
