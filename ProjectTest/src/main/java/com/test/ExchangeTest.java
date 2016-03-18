/**     
 * @FileName: ExchangeTest.java   
 * @Package:com.test   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月27日 上午11:11:12   
 * @version V1.0     
 */
package com.test;

import java.util.concurrent.Exchanger;

/**
 * @ClassName: ExchangeTest
 * @Description: TODO
 * @author: LUCKY
 * @date:2016年1月27日 上午11:11:12
 */
public class ExchangeTest {

	public static void main(String[] args) {

		Exchanger<Integer> exchanger=new Exchanger<>();
		test2 test2=new test2(exchanger);
		test1 test1=new test1(exchanger);
		Thread thread1=new Thread(test1);
		Thread thread2=new Thread(test2);
		thread1.start();
		thread2.start();
	
	}

	static class test1 implements Runnable {
		private Exchanger<Integer> exchanger;
		private Integer data=1;

		/**  
		 *    
		 */
		public test1(Exchanger<Integer> exchanger) {
			this.exchanger=exchanger;
		}
		@Override
		public void run() {
			for(int i=0;i<10;i++){
				data=data+i;
			}
			
			try {
				//开始交互数据
				data=exchanger.exchange(data);
				System.out.println(Thread.currentThread().getName()+"交换后的数据为"+data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	static class test2 implements Runnable {
		private Exchanger<Integer> exchanger;
		private Integer data=50;

		
		public test2(Exchanger<Integer> exchanger) {
			this.exchanger=exchanger;
		}
		
		
		@Override
		public void run() {
			for(int i=0;i<10;i++){
				data=data+i;
			}
			
			try {
				data=exchanger.exchange(data);
				System.out.println(Thread.currentThread().getName()+"交换后的数据为"+data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
