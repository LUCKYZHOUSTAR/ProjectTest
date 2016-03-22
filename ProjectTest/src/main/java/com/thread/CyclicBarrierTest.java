/**     
 * @FileName: CyclicBarrierTest.java   
 * @Package:com.thread   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年3月22日 下午12:27:32   
 * @version V1.0     
 */
package com.thread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**  
 * @ClassName: CyclicBarrierTest   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年3月22日 下午12:27:32     
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        CyclicBarrier cyclicBarrier2=new CyclicBarrier(3, new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                System.out.println(Thread.currentThread().getName()+"主任务开始执行");
            }
        });
        for(int i=0;i<3;i++){
            new cyclieTest(cyclicBarrier2).start();
        }
    }

}

class cyclieTest extends Thread {
    private CyclicBarrier cyclicBarrier;

    public cyclieTest(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    /* (non-Javadoc)   
     *    
     * @see java.lang.Thread#run()   
     */
    @Override
    public void run() {

        try {
            System.out.println(Thread.currentThread().getName() + "当前线程正在执行");
            TimeUnit.SECONDS.sleep(3l);
            System.out.println(Thread.currentThread().getName() + "执行完了，等待其他线程执行");
            cyclicBarrier.await();//再次阻塞，一旦执行完毕，会选择一个线程执行主任务后，才继续执行后面的操作
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        System.out.println("所有线程都处理完毕，开始执行其他的任务操作");

    }

}
