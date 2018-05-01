package org.wqh.proxyClient.socket;

import java.net.ServerSocket;
import java.net.Socket;

import org.wqh.proxyClient.thread.ThreadManager;

public class AcceptClientSocket {
    
     public void startMonitor() {
         try {
             ServerSocket serverSocket = new ServerSocket(8000);
             System.out.println("启动socket监听器完成............");
             long num=0;
             while(true) {
            	 System.out.println("线程池当前有"+ThreadManager.ThreadPool.getCompletedTaskCount()+"个任务完成........现在池里有"+ThreadManager.ThreadPool.getPoolSize()+"个线程....."+ThreadManager.ThreadPool.getActiveCount()+"个任务正在运行");
            	
            	 ThreadManager.ThreadPool.execute(
            		    ThreadManager.excSocketThread.excMethod(
            		    		  this,
                   		        "startSocket",
                   		        new Object[]{serverSocket.accept(),num++},
                   		        Socket.class,
                   		        long.class)
            		     );
                   
                 
             }
            
             
           }catch(Exception e){
                   e.printStackTrace();
           }
     }
     
     public void startSocket(Socket socket,long num){
    	 System.out.println("*****收到第"+num+"个请求");
    	/// socket.set
    	 SocketHandle socketHandle = new SocketHandle(socket);
    	 ThreadManager.ThreadPool.execute(ThreadManager.excSocketThread.excMethod(socketHandle,
          		"run", new Object[]{}));
     }
     
     
}
