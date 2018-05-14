package org.wqh.proxyClient.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.wqh.proxyClient.encryption.HalfMode;
import org.wqh.proxyClient.encryption.Mode;
import org.wqh.proxyClient.thread.ThreadManager;

public class SocketHandle {

    private ProxySocket clientSocket;
    private ProxySocket hostSocket;
    private OutputStream clientOutput = null;
    private InputStream clientInput = null;
    private InputStream hostInput = null;
    private OutputStream hostOutput = null;
    private Mode<Integer> halfMode = new HalfMode();
    public SocketHandle(Socket socket) {
        clientSocket = new ProxySocket(socket);
        hostSocket = new ProxySocket();
        //this.clientSocket.setSocket(socket);
    }
    /*public static void main(String args[]){
    	Mode<Integer> halfMode = new HalfMode();
    	StringBuilder sb = new StringBuilder();
    	sb.append(65);
   
    	
    	System.out.println(sb.toString()+"  "+sb.length());
    	
    }*/
  
    public void run() {
    
		
		
        try {
            clientInput = clientSocket.getSocket().getInputStream();
            clientOutput = clientSocket.getSocket().getOutputStream();
       
            //连接到代理服务器
         //   System.out.println("host:"+host+"    "+"post:"+port);
            Socket socket = new Socket("127.0.0.1", 9000);
            hostSocket.setSocket(socket);
            //System.out.println("************host:"+hostSocket.getSocket().getSoTimeout()+"       "+hostSocket.getSocket().getRemoteSocketAddress());
            hostInput = hostSocket.getSocket().getInputStream();
            hostOutput = hostSocket.getSocket().getOutputStream();
          
            //新开线程转发并加密客户端请求至代理服务器
            ThreadManager.ThreadPool.execute(
                         ThreadManager.excSocketThread.excMethod(
                           hostSocket,
                           "send",
                           new Object[]{clientInput}, 
                           InputStream.class));
           
            //转发代理服务器响应至客户端
            int s;
            while ( (s=hostInput.read())!=-1) {
               // System.out.print((char)halfMode.decrypt(s).intValue());
                clientOutput.write(halfMode.decrypt(s));
            }
           
        } catch(Exception e) {
     
                System.out.print("Exception catch:");
                e.printStackTrace();
        } finally {
            if (hostInput != null) {
                try {
                        hostOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (hostOutput != null) {
                try {
                        hostOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (hostSocket.getSocket() != null) {
                try {
                        System.out.println("close clientSokect!");
                        hostSocket.getSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientInput != null) {
                try {
                    clientInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientOutput != null) {
                try {
                    clientOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientSocket.getSocket() != null) {
                try {
                        clientSocket.getSocket().close();
                } catch (IOException e) {
                        
                    e.printStackTrace();
                }
            }
        }

    }
}
