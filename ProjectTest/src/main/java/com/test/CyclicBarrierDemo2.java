/**     
 * @FileName: CyclicBarrierDemo2.java   
 * @Package:com.test   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月26日 下午6:54:14   
 * @version V1.0     
 */
package com.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**  
 * @ClassName: CyclicBarrierDemo2   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年1月26日 下午6:54:14     
 */

/*
 * 从上面输出结果可以看出，每个写入线程执行完写数据操作之后，就在等待其他线程写入操作完毕。

　　当所有线程线程写入操作完毕之后，所有线程就继续进行后续的操作了

 */
public class CyclicBarrierDemo2 {

	  public static void main(String[] args) {
		  int N = 4;
		  CountDownLatch aa=new CountDownLatch(N);
		
	        CyclicBarrier barrier  = new CyclicBarrier(N);

	        for(int i=0;i<4;i++)

	            new Writer(barrier,aa).start();
	}
	  
	  
	  static class Writer extends Thread{
		  private CyclicBarrier cyclicBarrier;
		  private CountDownLatch countDownLatch;
		  public Writer(CyclicBarrier cyclicBarrier,CountDownLatch countDownLatch){
			  this.cyclicBarrier=cyclicBarrier;
			  this.countDownLatch=countDownLatch;
		  }
		  
		  public void run()
		  {
			  System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据");
			  try {
				Thread.sleep(5000);//以睡眠来模拟写入数据操作
				System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
				//让多个线程等某个信号量后才开始执行后面的操作
				cyclicBarrier.await();//让每个线程都执行到这个状态后，才可以继续向下执行
				//在本例中也可以通过countdownlatch来作为信息，只不过这里需要进行countDown操作
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			  System.out.println("所有线程写入完毕，继续处理其他任务...");
		  }
	  }
}
