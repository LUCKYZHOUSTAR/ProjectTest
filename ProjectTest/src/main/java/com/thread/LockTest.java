/**     
 * @FileName: LockTest.java   
 * @Package:com.thread   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年3月21日 下午6:54:10   
 * @version V1.0     
 */
package com.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName: LockTest
 * @Description: TODO
 * @author: LUCKY
 * @date:2016年3月21日 下午6:54:10
 */
public class LockTest {

	public static void main(String[] args) {
		Lock lock = new ReentrantLock(true);
		ReentrantReadWriteLock locl=new ReentrantReadWriteLock();
		threadTest[] threadTests=new threadTest[10];
		threadTest2[] threadTest2=new threadTest2[10];
		for (int i = 0; i < 10; i++) {
			threadTests[i]= new threadTest(lock);
			threadTest2[i]=new threadTest2(locl);
		}

		
		for(int i=1;i<10;i++){
			threadTest2[i].start();
		}
		
	}

}

class threadTest extends Thread {

	private Lock lock;

	/**  
	 *    
	 */
	public threadTest(Lock lock) {
		// TODO Auto-generated constructor stub
		this.lock = lock;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public  void run() {
		// TODO Auto-generated method stub
		lock.lock();

		try {
			System.out.println(Thread.currentThread().getName() + "获得了锁");
		} catch (Exception e) {
			// TODO: handle exception
		} finally {

			System.out.println(Thread.currentThread().getName() + "释放了锁");
			lock.unlock();

		}
	}

	
	

}

class threadTest2 extends Thread {
	private ReentrantReadWriteLock readWriteLock;
	
	public threadTest2(ReentrantReadWriteLock readWriteLock) {
		this.readWriteLock = readWriteLock;
	}

	@Override
	public  void run() {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName() + "获得了锁");
		for(int i=1;i<10;i++){
			System.out.println(Thread.currentThread().getName());
		}
		print();
	}
	
	public void print(){
		readWriteLock.readLock().lock();
		try {
			System.out.println("输出相应的内容信息");
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			readWriteLock.readLock().unlock();
		}
	}
}



class threadTest3 extends Thread {
	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName() + "获得了锁");
	}
	
	
}