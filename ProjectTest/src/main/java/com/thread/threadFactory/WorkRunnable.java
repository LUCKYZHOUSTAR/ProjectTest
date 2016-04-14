/**     
 * @FileName: WorkRunnable.java   
 * @Package:com.thread.threadFactory   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午4:59:18   
 * @version V1.0     
 */
package com.thread.threadFactory;

/**  
 * @ClassName: WorkRunnable   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 下午4:59:18     
 */
public class WorkRunnable implements Runnable
{
    @Override
    public void run() {
        try {
            Thread.sleep(2000l);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         System.out.println("complete a task");
    }
 
}
