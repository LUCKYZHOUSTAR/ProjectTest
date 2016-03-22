/**     
 * @FileName: CountDownLatchTest.java   
 * @Package:com.thread   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年3月22日 上午11:15:31   
 * @version V1.0     
 */
package com.thread;

import java.util.concurrent.CountDownLatch;

/**  
 * @ClassName: CountDownLatchTest   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年3月22日 上午11:15:31     
 */
public class CountDownLatchTest {

    static CountDownLatch latch=new CountDownLatch(5);
    static int Count=10;
    public static void main(String[] args) throws Exception {
        for(int i=0;i<10;i++){
            run();
        }
        
        latch.await();
        System.out.println("人都到齐了，开始吧");
    }
    
    
    public static void run() throws Exception{
       latch.countDown();
    }
    
    
}
