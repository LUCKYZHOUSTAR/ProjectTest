/**     
 * @FileName: Server.java   
 * @Package:com.executor   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月27日 下午1:32:00   
 * @version V1.0     
 */
package com.executor.newCachedThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**  
 * @ClassName: Server   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年1月27日 下午1:32:00     
 */
public class Server {

	private ThreadPoolExecutor executor;
	
	public Server(){
		//创建一个缓存线程池
		executor=(ThreadPoolExecutor) Executors.newCachedThreadPool();
	}
	
	public void executeTask(Task task){
		System.out.printf("Server: A new task has arrived\n");
		executor.execute(task);
		System.out.printf("Server: Pool Size: %d\n",executor.getPoolSize());
		System.out.printf("Server: Active Count: %d\n",executor.getActiveCount());
		System.out.printf("Server: Completed Tasks: %d\n",executor.getCompletedTaskCount());
	}
	
	
	
	public void endServer(){
		executor.shutdown();
		
	}
}
