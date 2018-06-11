package org.wqh.proxyClient.thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager {

	public static  ThreadPoolExecutor ThreadPool ;

	public static void init(int corePoolSize,int maximumPoolSize,long keepLiveTime,TimeUnit unit){
		ThreadPool = new ThreadPoolExecutor(corePoolSize, 
				maximumPoolSize,
				keepLiveTime,
		            unit,
		            new SynchronousQueue<Runnable>());
		
		
		
	}
	
	
}
