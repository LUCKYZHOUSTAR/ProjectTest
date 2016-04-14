/**     
 * @FileName: WorkThread.java   
 * @Package:com.thread.threadFactory   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午4:57:36   
 * @version V1.0     
 */
package com.thread.threadFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**  
 * @ClassName: WorkThread   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 下午4:57:36     
 */

public class WorkThread extends Thread {
    private Runnable      target; //线程执行目标
    private AtomicInteger counter;

    public WorkThread(Runnable target, AtomicInteger counter) {
        this.target = target;
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            target.run();
        } finally {
            int c = counter.getAndDecrement();
            System.out.println("terminate no " + c + " Threads");
        }
    }
}
