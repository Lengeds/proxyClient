import java.util.concurrent.TimeUnit;

import org.wqh.proxyClient.socket.AcceptClientSocket;
import org.wqh.proxyClient.thread.ThreadManager;

public class Starter {
	public static void main(String[] args) {
		System.out.println("客户端...........................");
		System.out.println("正在初始化ThreadManager...........");
		ThreadManager.init(100,200,360,TimeUnit.SECONDS);
		System.out.println("完成初始化ThreadManager...........");
		
		System.out.println("正在启动socket监听器AcceptClient...........");
		 AcceptClientSocket acs = new AcceptClientSocket();
         acs.startMonitor();
	}
}
