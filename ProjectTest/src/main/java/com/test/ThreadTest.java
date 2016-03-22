package com.test;

import java.util.Date;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.omg.CORBA.DATA_CONVERSION;

public class ThreadTest extends Thread {

	private static ThreadLocal<Date> startdate=new ThreadLocal<Date>(){

		/* (non-Javadoc)
		 * @see java.lang.ThreadLocal#initialValue()
		 */
		@Override
		protected Date initialValue() {
			return new Date();
		}
		
	};
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(60000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//当前执行该代码块的线程
		System.out.println(Thread.currentThread().getName());
		//线程实例本身的线程
		System.out.println(this.getName());
		for(int i=0;i<10;i++){
			System.out.println(i);
			System.out.println(startdate.get().toString());
		}
		
	}

	public static void main(String[] args) throws Exception {
		ThreadTest a=new ThreadTest();
		System.out.println(a.getState().name());
//		a.interrupt();
//		a.resume();
//		a.suspend();
//		Thread.sleep(1000l);
//		TimeUnit.DAYS.sleep(1000L);
		a.start();
		// join() 方法主要是让调用改方法的thread完成run方法里面的东西后， 在执行join()方法后面的代
//		a.join();
//		System.out.println("-----------------");
//		
//		System.out.println(a.getState().name());
//		System.out.println(Thread.currentThread().getName());
		
//		thread0  thread0=new thread0();
//		Thread thread=new Thread(thread0);
//		thread.start();
//		thread.join();
//		System.out.println(a.getState().name());
	}
}




class thread0 implements Runnable{

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName());
		for(int i=0;i<10;i++){
			System.out.println(i);
		}
	}
	
}


class myThreadFactory implements ThreadFactory{

	@Override
	public Thread newThread(Runnable runnable) {
		Thread t=newThread(runnable);
		// TODO Auto-generated method stub
		return null;
	}
	
}
