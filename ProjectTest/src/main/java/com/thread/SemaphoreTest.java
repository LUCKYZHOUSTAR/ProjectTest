/**     
 * @FileName: SemaphoreTest.java   
 * @Package:com.thread   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年3月22日 上午10:23:10   
 * @version V1.0     
 */
package com.thread;

import java.util.concurrent.Semaphore;

import org.junit.internal.runners.statements.Fail;

/**  
 * @ClassName: SemaphoreTest   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年3月22日 上午10:23:10     
 */
public class SemaphoreTest {

    static final Semaphore se = new Semaphore(5);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        if(!se.tryAcquire()){
                            System.out.println("等待中");
                        }
                        se.acquire();//线程会在此堵塞
                        System.out.println(Thread.currentThread().getName() + "获得锁");
                        Thread.sleep(1000l);
                       
                      
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }finally{
                        System.out.println(se.tryAcquire());
                        System.out.println("剩余信号量" + se.availablePermits());    
                        se.release();
                        System.out.println(Thread.currentThread().getName() + "释放锁");
                    }
                }
            }).start();
        }
    }
}
