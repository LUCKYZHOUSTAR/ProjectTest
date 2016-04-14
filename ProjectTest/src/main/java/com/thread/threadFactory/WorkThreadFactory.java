/**     
 * @FileName: WorkThreadFactory.java   
 * @Package:com.thread.threadFactory   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午4:59:38   
 * @version V1.0     
 */
package com.thread.threadFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**  
 * @ClassName: WorkThreadFactory   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 下午4:59:38     
 */
public class WorkThreadFactory implements ThreadFactory{
    
    private AtomicInteger atomicInteger = new AtomicInteger(0);
     
    @Override
    public Thread newThread(Runnable r)
    {
        int c = atomicInteger.incrementAndGet();  
//                System.out.println("create no " + c + " Threads");  
                return new Thread(r, "create no " + c + " Threads");
//        return new WorkThread(r, atomicInteger);//通过计数器，可以更好的管理线程
    }
 
}
