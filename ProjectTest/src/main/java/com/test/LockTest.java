/**     
 * @FileName: LockTest.java   
 * @Package:com.test   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月26日 下午4:54:35   
 * @version V1.0     
 */
package com.test;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: LockTest
 * @Description: 关于java中lock的测试类操作
 * @author: LUCKY
 * @date:2016年1月26日 下午4:54:35
 */
public class LockTest {
	private ArrayList<Integer> arrayList = new ArrayList<Integer>();

	Lock lock = new ReentrantLock();

	public void lock1() {

		Lock lock = new ReentrantLock();

		lock.lock();

		try {

			// 处理任务

		} catch (Exception ex) {

		} finally {

			lock.unlock(); // 释放锁

		}
	}

	public void lock2() {

		Lock lock = new ReentrantLock();

		if (lock.tryLock()) {

			try {

				// 处理任务

			} catch (Exception ex) {

			} finally {

				lock.unlock(); // 释放锁

			}

		} else {

			// 如果不能获取锁，则直接做其他事情

		}

	}

	public void lock3(Thread thread) {
		// 这样的话，lock是局部变量起不到锁的作用，需要提取到类的变量才起到作用
		// Lock lock=new ReentrantLock();
		lock.lock();
		try {
			System.out.println(thread.getName() + "得到了锁");
			;
			for (int i = 0; i < 5; i++) {
				arrayList.add(i);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			System.out.println(thread.getName() + "释放了锁");
			lock.unlock();
		}
	}

	public void lock4(Thread thread) {

		if (lock.tryLock()) {

			try {

				System.out.println(thread.getName() + "得到了锁");

				for (int i = 0; i < 5; i++) {

					arrayList.add(i);

				}

			} catch (Exception e) {

				// TODO: handle exception

			} finally {

				System.out.println(thread.getName() + "释放了锁");

				lock.unlock();

			}

		} else {

			System.out.println(thread.getName() + "获取锁失败");

		}

	}

	public static void main(String[] args) {

		final LockTest test = new LockTest();

		new Thread() {

			public void run() {

				test.lock4(Thread.currentThread());

			};

		}.start();

		new Thread() {

			public void run() {

				test.lock4(Thread.currentThread());

			};

		}.start();

	}
}
