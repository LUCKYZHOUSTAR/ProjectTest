/**     
 * @FileName: Main.java   
 * @Package:com.executor   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月27日 下午1:37:33   
 * @version V1.0     
 */
package com.executor.newCachedThreadPool;

/**  
 * @ClassName: Main   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年1月27日 下午1:37:33     
 */
public class Main {

	public static void main(String[] args) {
		Server server=new Server();
		for(int i=0;i<100;i++){
			Task task=new Task("Task"+i);
			server.executeTask(task);
		}
		
		server.endServer();
	}
}
