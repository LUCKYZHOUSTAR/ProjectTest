/**     
 * @FileName: Executor.java   
 * @Package:com.executortest   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年3月23日 上午8:58:50   
 * @version V1.0     
 */
package com.executortest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import org.omg.CORBA.PRIVATE_MEMBER;

/**  
 * @ClassName: Executor   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年3月23日 上午8:58:50     
 */
public class Executor {
//    private   ThreadPoolExecutor executor;
    private   ExecutorService executor;
    public static void main(String[] args) {
        Executor executor=new Executor();
        for(int i=0;i<100;i++){
            Task task=new Task();
            executor.executeTask(task);
        }
        
        executor.endServer();
    }
    
    public Executor(){
//        executor=(ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor=(ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        executor=  Executors.newSingleThreadExecutor();
    }
    
    
    
    
    /*丢进去一个实现runnable的对象即可*/
    public void executeTask(Task task){
//       System.out.println("线程池中实际的线程数目"+ executor.getPoolSize());
//       System.out.println("线程池中正在执行任务的线程数"+executor.getActiveCount());
//       System.out.println("已经完成的任务数为"+executor.getCompletedTaskCount());
//       System.out.println("某一时刻最大的线程数"+executor.getLargestPoolSize());
        executor.execute(task);
    }
    
    
    public void endServer(){
        executor.shutdown();
    }
}



class Task implements Runnable{
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
