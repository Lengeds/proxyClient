package org.wqh.proxyClient.thread;

import java.lang.reflect.Method;

public class ExcSocketThread{
    //public T t;
    
    
    public <T> Runnable excMethod(T t,String methodname,Object[] args,Class<?>... parameterTypes) {
        try {
        	Class<T> class1 = (Class<T>) t.getClass();
        	Method method=class1.getMethod(methodname,parameterTypes);
			return new Thread(()->{
				try {
					method.invoke(t,args);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
			});
			
		} catch (Exception e) {
			return new Thread(()->{e.printStackTrace();});
		} 
        
     }
    
     }
