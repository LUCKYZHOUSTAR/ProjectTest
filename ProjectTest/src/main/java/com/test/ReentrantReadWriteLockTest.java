/**     
 * @FileName: ReentrantReadWriteLockTest.java   
 * @Package:com.test   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月26日 下午5:16:08   
 * @version V1.0     
 */
package com.test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName: ReentrantReadWriteLockTest
 * @Description: 读写锁,此例子展示了两个锁都可以交替的读取数据
 * @author: LUCKY
 * @date:2016年1月26日 下午5:16:08
 */

/*
 * 说明thread1和thread2在同时进行读操作。

　　这样就大大提升了读操作的效率。

　　不过要注意的是，如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待释放读锁。

　　如果有一个线程已经占用了写锁，则此时其他线程如果申请写锁或者读锁，则申请的线程会一直等待释放写锁。

　　关于ReentrantReadWriteLock类中的其他方法感兴趣的朋友可以自行查阅API文档。

 */


/*
 * 总结来说，Lock和synchronized有以下几点不同：

　　1）Lock是一个接口，而synchronized是Java中的关键字，synchronized是内置的语言实现；

　　2）synchronized在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而Lock在发生异常时，如果没有主动通过unLock()去释放锁，则很可能造成死锁现象，因此使用Lock时需要在finally块中释放锁；

　　3）Lock可以让等待锁的线程响应中断，而synchronized却不行，使用synchronized时，等待的线程会一直等待下去，不能够响应中断；

　　4）通过Lock可以知道有没有成功获取锁，而synchronized却无法办到。

　　5）Lock可以提高多个线程进行读操作的效率。

　　在性能上来说，如果竞争资源不激烈，两者的性能是差不多的，而当竞争资源非常激烈时（即有大量线程同时竞争），此时Lock的性能要远远优于synchronized。所以说，在具体使用时要根据适当情况选择。

 */
public class ReentrantReadWriteLockTest {

	private ReentrantReadWriteLock readLock = new ReentrantReadWriteLock();

	
	public static void main(String[] args) {
		final ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();
		new Thread(new Runnable() {

			@Override
			public void run() {
				test.get(Thread.currentThread());
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				test.get(Thread.currentThread());
			}
		}).start();
	}

	public  void get(Thread thread) {
		
		readLock.readLock().lock();
		try {
			long start = System.currentTimeMillis();
			while (System.currentTimeMillis() - start <= 1) {
				Thread.sleep(500);
				System.out.println(thread.getName() + "正在进行读操作");
			}

			System.out.println(thread.getName() + "读取操作完毕");
		} catch (Exception e) {
		}finally{
			readLock.readLock().unlock();
		}
	}

}
