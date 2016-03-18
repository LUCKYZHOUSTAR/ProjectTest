/**     
 * @FileName: AtomicIntegerFieldUpdaterDemo.java   
 * @Package:com.test   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月26日 下午4:25:33   
 * @version V1.0     
 */
package com.test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @ClassName: AtomicIntegerFieldUpdaterDemo
 * @Description: TODO
 * @author: LUCKY
 * @date:2016年1月26日 下午4:25:33
 */
public class AtomicIntegerFieldUpdaterDemo {

	class DemoData {
		public volatile int value1 = 1;
		public volatile int value2 = 2;
		public volatile int value3 = 3;
		public volatile int value4 = 4;
	}

	AtomicIntegerFieldUpdater<DemoData> getUpdater(String fieldName) {
		return AtomicIntegerFieldUpdater.newUpdater(DemoData.class, fieldName);
	}

	void doit() {
		DemoData data = new DemoData();
		System.out.println("1 ==> " + getUpdater("value1").getAndSet(data, 10));
		System.out.println("3 ==> "
				+ getUpdater("value2").incrementAndGet(data));
		System.out.println("2 ==> "
				+ getUpdater("value3").decrementAndGet(data));
		System.out.println("true ==> "
				+ getUpdater("value4").compareAndSet(data, 4, 5));
	}

	public static void main(String[] args) {
		AtomicIntegerFieldUpdaterDemo demo = new AtomicIntegerFieldUpdaterDemo();
		demo.doit();
	}
}
