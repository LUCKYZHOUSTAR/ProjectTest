/**     
 * @FileName: AtomicIntegerTest.java   
 * @Package:com.test   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月26日 下午2:55:31   
 * @version V1.0     
 */
package com.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

/**
 * @ClassName: AtomicIntegerTest
 * @Description: 原子操作类测试
 * @author: LUCKY
 * @date:2016年1月26日 下午2:55:31
 */
public class AtomicIntegerTest {
	
	AtomicInteger count=new AtomicInteger(0);
	long li=0l;
	@Test
	public void testAll() throws InterruptedException {
		final AtomicInteger value = new AtomicInteger(10);
		
		System.out.println(value.compareAndSet(1, 2));
		System.out.println(value.get());
		System.out.println(value.compareAndSet(10, 3));
		System.out.println(value.get());
		value.set(0);
		System.out.println(value.get());

		System.out.println(value.incrementAndGet());
		System.out.println(value.get());
		System.out.println(value.getAndAdd(2));
		System.out.println(value.get());
		System.out.println(value.getAndSet(5));
		System.out.println(value.get());
		//
	
		
		//泛型类操作
		AtomicReference<Integer> test=new AtomicReference<>(10);
		test.compareAndSet(10, 3);
		System.out.println(test.get());
	}
	
	
	
	public void addCount(){
		count.getAndIncrement();
	}
	
	public long getCount(){
		return count.get();
	}
	
	
	@Test
	public void testcount(){
		for(int i=0;i<100;i++){
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
				
					System.out.println("获取当前线程的值"+Thread.currentThread().getName()+"-----"+getCount());
					addCount();
					System.out.println("获得改变后的值"+Thread.currentThread().getName()+"-----"+getCount());
				}
			});
			
			t.start();
		}
		
	}
}
