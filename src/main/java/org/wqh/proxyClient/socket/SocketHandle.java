package org.wqh.proxyClient.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public static void main(String args[]){
    	Mode<Integer> halfMode = new HalfMode();
    	StringBuilder sb = new StringBuilder();
    	sb.append("C");
    	String s = "C";
    	for(int i=0;i<sb.length();i++){
    		int es = halfMode.encrypt(sb.codePointAt(i));
    		sb.setCharAt(i,(char)es );
    		System.out.println(sb.codePointAt(i));
    	}
    //	System.out.println(s.codePointAt(0)+" "+(int)'C');
    	
    }
  
    public void run() {
        
        try {
            clientInput = clientSocket.getSocket().getInputStream();
            clientOutput = clientSocket.getSocket().getOutputStream();
          /*  String line;
            String host = "";
            InputStreamReader in = new InputStreamReader(clientInput);
            BufferedReader bf = new BufferedReader(in);
            StringBuilder headStr = new StringBuilder();
            int row = 1;
            while (null != (line = bf.readLine())) {
                headStr.append(line + "\r\n");
                System.out.println(line);
                if(row==1){
                         String[] temp = line.split(" ");
                         if(temp[0].equals("CONNECT")){
                         hostSocket.setProtocolType("https");
                     }else{
                         hostSocket.setProtocolType("http");
                     }
                        row++; 
                }else if(line.length() != 0){
                         String[] temp = line.split(" ");
                     if (temp[0].equals("Host:")) {
                         host = temp[1];
                     }
                }else if (line.length() == 0) {
                    break;//读完报头
                }
            }
           //  hostSocket.setProtocolType();  = headStr.substring(0, headStr.indexOf(" "));
            //根据host头解析出目标服务器的host和port
            String[] hostTemp = host.split(":");
            host = hostTemp[0];
            int port = 80;
            if (hostTemp.length > 1) {
                port = Integer.valueOf(hostTemp[1]);
            }*/
            
            //连接到代理服务器
         //   System.out.println("host:"+host+"    "+"post:"+port);
            Socket socket = new Socket("127.0.0.1", 9000);
            hostSocket.setSocket(socket);
            //System.out.println("************host:"+hostSocket.getSocket().getSoTimeout()+"       "+hostSocket.getSocket().getRemoteSocketAddress());
            hostInput = hostSocket.getSocket().getInputStream();
            hostOutput = hostSocket.getSocket().getOutputStream();
            //根据HTTP method来判断是https还是http请求
          /*  if ("https".equals(hostSocket.getProtocolType())) {//https先建立隧道
               // System.out.println("************client:"+clientSocket.getSocket().getRemoteSocketAddress());
                clientOutput.write("HTTP/1.1 200 Connection Established\r\n\r\n".getBytes());
                clientOutput.flush();
            } else {//http直接将请求头转发,转发之前进行加密
            	for(int i=0;i<headStr.length();i++){
            		int es = halfMode.encrypt(headStr.codePointAt(i));
            		headStr.setCharAt(i,(char)es);
            	}
                hostOutput.write(headStr.toString().getBytes());
                //System.out.println("发送http请求成功");
                
            }
            */
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
               // System.out.print((char)s);
                clientOutput.write(halfMode.decrypt(s));
            }
           
        } catch (Exception e) {
       /*       System.out.println("********************:"+clientSocket.getSocket().isClosed()+"     "+clientSocket.getSocket().isConnected()
                                +"      "+clientSocket.getSocket().isInputShutdown()+"      "+clientSocket.getSocket().isOutputShutdown()
                                );*/
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
