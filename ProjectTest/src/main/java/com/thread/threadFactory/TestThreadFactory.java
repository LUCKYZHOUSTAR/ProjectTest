/**     
 * @FileName: TestThreadFactory.java   
 * @Package:com.thread.threadFactory   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午5:00:33   
 * @version V1.0     
 */
package com.thread.threadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**  
 * @ClassName: TestThreadFactory   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 下午5:00:33     
 */
public class TestThreadFactory {
    
    public static void main(String[] args) {
        //创建线程（并发）池，自动伸缩(自动条件线程池大小)
        ExecutorService es =  Executors.newCachedThreadPool(new WorkThreadFactory());
 
        //同时并发5个工作线程
       
        for(int i=0;i<100;i++){
            
            es.execute(new WorkRunnable());
        }
       
//        es.execute(new WorkRunnable());
//        es.execute(new WorkRunnable());
//        es.execute(new WorkRunnable());
//        es.execute(new WorkRunnable());
        //指示当所有线程执行完毕后关闭线程池和工作线程，如果不调用此方法，jvm不会自动关闭
           es.shutdown();  
         
        try {  
             //等待线程执行完毕，不能超过2*60秒，配合shutDown
              es.awaitTermination(2*60, TimeUnit.SECONDS);  
         } catch (InterruptedException e) {  
                e.printStackTrace();  
         }  
    }
     
}
