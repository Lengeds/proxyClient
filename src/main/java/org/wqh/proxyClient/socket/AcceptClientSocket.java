package org.wqh.proxyClient.socket;

import java.net.ServerSocket;
import java.net.Socket;

import org.wqh.proxyClient.thread.ThreadManager;

public class AcceptClientSocket {
    
     public void startMonitor() {
         try {
             ServerSocket serverSocket = new ServerSocket(8000);
             System.out.println("启动socket监听器完成............");
             Long num=(long)0;
             while(true) {
            	// System.out.println("线程池当前有"+ThreadManager.ThreadPool.getCompletedTaskCount()+"个任务完成........现在池里有"+ThreadManager.ThreadPool.getPoolSize()+"个线程....."+ThreadManager.ThreadPool.getActiveCount()+"个任务正在运行");
            	 Socket socket = serverSocket.accept();
            	
            	ThreadManager.ThreadPool.execute(new Thread(()->{
                    try{
                      startSocket(socket); 
                    }catch (Exception e) {
                            e.printStackTrace();
                                   }
                        }));
                       
                   
                 
             }
            
             
           }catch(Exception e){
                   e.printStackTrace();
           }
     }
     
     public void startSocket(Socket socket){
    	 //System.out.println("*****收到第"+num+"个请求");
    	// num++;
    	 SocketHandle socketHandle = new SocketHandle(socket);
    	 
    	 ThreadManager.ThreadPool.execute(new Thread(()->{
                socketHandle.run();
                }));
               
    	 
    	 
     }
     
     
}
